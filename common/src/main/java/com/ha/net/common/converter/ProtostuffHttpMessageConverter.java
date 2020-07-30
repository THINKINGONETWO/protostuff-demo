package com.ha.net.common.converter;

import org.springframework.http.converter.AbstractHttpMessageConverter;
import com.google.common.base.Stopwatch;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import net.jpountz.lz4.LZ4BlockOutputStream;
import org.apache.commons.io.IOUtils;

import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.xerial.snappy.SnappyOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author jiangwentao
 */
public class ProtostuffHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private Logger logger = LoggerFactory.getLogger(ProtostuffHttpMessageConverter.class);
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public static final MediaType MEDIA_TYPE = new MediaType("application", "x-protobuf", DEFAULT_CHARSET);
    public static final MediaType MEDIA_TYPE_LZ4 = new MediaType("application", "x-protobuf-lz4", DEFAULT_CHARSET);
    public static final MediaType MEDIA_TYPE_GZIP = new MediaType("application", "x-protobuf-gzip", DEFAULT_CHARSET);
    public static final MediaType MEDIA_TYPE_SNAPPY = new MediaType("application", "x-protobuf-snappy",
            DEFAULT_CHARSET);

    /**
     * Construct a new instance.
     */
    public ProtostuffHttpMessageConverter() {
        super(MEDIA_TYPE, MEDIA_TYPE_LZ4, MEDIA_TYPE_GZIP, MEDIA_TYPE_SNAPPY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canRead(final Class<?> clazz, final MediaType mediaType) {
        return canRead(mediaType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canWrite(final Class<?> clazz, final MediaType mediaType) {
        return canWrite(mediaType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object readInternal(final Class<?> clazz, final HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        logger.info("=====================decode begin==========");
        if (MEDIA_TYPE.isCompatibleWith(inputMessage.getHeaders().getContentType())) {
            final Schema<?> schema = RuntimeSchema.getSchema(clazz);
            final Object value = schema.newMessage();
            InputStream stream = null;
            try {
                stream = inputMessage.getBody();
                ProtobufIOUtil.mergeFrom(stream, value, (Schema<Object>) schema);
                logger.info("================decode result : " + value.toString());
                logger.info("============================decode end==================");
                return value;
            } catch (Exception e) {
                logger.error("Exception happen e = " + e.getMessage(), e);
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }

        throw new HttpMessageNotReadableException(
                "Unrecognized HTTP media type " + inputMessage.getHeaders().getContentType().getType() + ".");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean supports(final Class<?> clazz) {
        // Should not be called, since we override canRead/canWrite.
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeInternal(final Object o, final HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        logger.info("===========================encode begin===================");
        if (logger.isDebugEnabled()) {
            logger.info("Current type: {}", outputMessage.getHeaders().getContentType());
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        OutputStream stream = null;

        try {
            stream = outputMessage.getBody();
            if (MEDIA_TYPE.isCompatibleWith(outputMessage.getHeaders().getContentType())) {
            } else if (MEDIA_TYPE_GZIP.isCompatibleWith(outputMessage.getHeaders().getContentType())) {
                stream = new GZIPOutputStream(stream);
            } else if (MEDIA_TYPE_LZ4.isCompatibleWith(outputMessage.getHeaders().getContentType())) {
                stream = new LZ4BlockOutputStream(stream);
            } else if (MEDIA_TYPE_SNAPPY.isCompatibleWith(outputMessage.getHeaders().getContentType())) {
                stream = new SnappyOutputStream(stream);
            } else {
                throw new HttpMessageNotWritableException(
                        "Unrecognized HTTP media type " + outputMessage.getHeaders().getContentType().getType() + ".");
            }

            ProtobufIOUtil.writeTo(stream, o, RuntimeSchema.getSchema((Class<Object>) o.getClass()),
                    LinkedBuffer.allocate());
            stream.flush();
            logger.info("===========================encode result :" + stream.toString());
            logger.info("===========================encode end===================");
        } finally {
            IOUtils.closeQuietly(stream);
        }
        if (logger.isDebugEnabled()) {
            logger.info("Output spend {}", stopwatch.toString());
        }

    }
}
