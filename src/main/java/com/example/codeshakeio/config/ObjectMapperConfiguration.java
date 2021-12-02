package com.example.codeshakeio.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Configuration
public class ObjectMapperConfiguration {
    public static final String DATE_FORMAT = "yyyy.MM.dd";
    public static final String DATE_TIME_FORMAT = "yyyy.MM.dd HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final LocalDateTimeSerializer LOCAL_DATE_TIME_SERIALIZER = new LocalDateTimeSerializer(
            DATE_TIME_FORMATTER);
    public static final LocalDateTimeDeserializer LOCAL_DATE_TIME_DESERIALIZER = new LocalDateTimeDeserializer(
            DATE_TIME_FORMATTER);
    public static final ObjectMapper OBJECT_MAPPER = generateObjectMapper();
    public static final LocalDateSerializer LOCAL_DATE_SERIALIZER = new LocalDateSerializer(DATE_FORMATTER);
    public static final LocalDateDeserializer LOCAL_DATE_DESERIALIZER = new LocalDateDeserializer(DATE_FORMATTER);

    private static ObjectMapper generateObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, LOCAL_DATE_TIME_SERIALIZER);
        javaTimeModule.addDeserializer(LocalDateTime.class, LOCAL_DATE_TIME_DESERIALIZER);

        objectMapper.registerModule(javaTimeModule);
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        objectMapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
        objectMapper.writerWithDefaultPrettyPrinter();
        return objectMapper;
    }

    @Bean
    public ObjectMapper serializingObjectMapper() {
        log.info("serializingObjectMapper started");
        return generateObjectMapper();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(DATE_TIME_FORMAT);
            builder.serializers(LOCAL_DATE_TIME_SERIALIZER);
            builder.serializers(LOCAL_DATE_SERIALIZER);
            builder.deserializers(LOCAL_DATE_TIME_DESERIALIZER);
            builder.deserializers(LOCAL_DATE_DESERIALIZER);
        };
    }


    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(createMappingJacksonHttpMessageConverter());

        return restTemplate;
    }

    private MappingJackson2HttpMessageConverter createMappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(generateObjectMapper());
        return converter;
    }
}
