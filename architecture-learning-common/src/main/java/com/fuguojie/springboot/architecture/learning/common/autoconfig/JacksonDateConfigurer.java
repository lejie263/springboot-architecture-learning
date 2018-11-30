package com.fuguojie.springboot.architecture.learning.common.autoconfig;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * 配置Jackson处理时间，无论是Date和LocalDateTime都有一样的行为
 * 序列化成毫秒
 * 反序列化支持毫秒和yyyy-MM-dd HH:mm:ss
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 10:28
 * @since JDK 1.8
 */
public class JacksonDateConfigurer {
    public static SimpleModule configure(String pattern) {
        SimpleModule module = new SimpleModule();

        // 处理LocalDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        final ZoneOffset zoneOffset = OffsetDateTime.now(ZoneOffset.systemDefault()).getOffset();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter) {
            @Override
            public void serialize(LocalDateTime value, JsonGenerator g, SerializerProvider provider) throws IOException {
                g.writeNumber(value.toInstant(zoneOffset).toEpochMilli());
            }
        });
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter) {
            @Override
            public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                if (parser.hasTokenId(JsonTokenId.ID_NUMBER_INT)) {
                    long time = parser.getLongValue();
                    return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.systemDefault());
                } else {
                    return super.deserialize(parser, context);
                }
            }
        });

        // 处理Date，Date默认的序列化就是序列化成毫秒，不用重新设置
        FastDateFormat fastDateFormat = FastDateFormat.getInstance(pattern, TimeZone.getDefault());
        module.addDeserializer(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
                if (parser.hasTokenId(JsonTokenId.ID_NUMBER_INT)) {
                    return new Date(parser.getLongValue());
                } else if (parser.hasTokenId(JsonTokenId.ID_STRING)) {
                    try {
                        return fastDateFormat.parse(parser.getText().trim());
                    } catch (ParseException e) {
                        throw new IOException(e);
                    }
                } else {
                    throw new IOException("cannot deserialize.");
                }
            }
        });

        module.addSerializer(BigInteger.class, ToStringSerializer.instance);
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        return module;
    }
}
