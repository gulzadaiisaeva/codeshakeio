package com.example.codeshakeio.utils;


import com.example.codeshakeio.config.ObjectMapperConfiguration;
import com.example.codeshakeio.enums.resultcode.FailureResultCode;
import com.example.codeshakeio.exception.unchecked.JsonParseFailException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Strings;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

@Slf4j
@UtilityClass
public class JsonUtils {
    public final ObjectMapper OBJECT_MAPPER = ObjectMapperConfiguration.OBJECT_MAPPER;


    public String objectAsJSON(Object objectToJSON) {
        String jsonString = "JSON_CONVERSION_UNSUCCESSFUL";

        try {
            jsonString = OBJECT_MAPPER
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(objectToJSON);
        } catch (Exception e) {
            throw new JsonParseFailException(e.getMessage(), e, FailureResultCode.JSON_PARSE_ERROR, jsonString);
        }
        return jsonString;
    }

    public Object jsonToClass(String jsonString, TypeReference<?> objClass) {
        if (Strings.isNullOrEmpty(jsonString)) {
            log.error("Empty or Null String Given To Json Parse");
        }

        Object pObj = null;

        try {
            pObj = OBJECT_MAPPER.readValue(jsonString, objClass);
        } catch (Exception e) {
            //throw new JsonParseFailException(e.getMessage(), e, FailureResultCode.JSON_PARSE_ERROR, jsonString, objClass);
            pObj = jsonString;
            log.error("JsonParseFailException occured. message {} , text {} , class {}",
                    e.getMessage(),
                    jsonString,
                    objClass);
        }
        return pObj;
    }

    /**
     * TODO: jsonToClassGeneric jsonToClass ile birleştirilmeli
     * Json formatindaki bir  stringten istenen tipte obje elde etmek için kullanılır
     *
     * @param jsonString çevrilmesi istenen json formatında string
     * @param objClass   json string çevrilmesi istenen class tipi için type reference
     * @param <T>        json string çevrilmesi istenen obje tipi
     * @return
     * @throws JsonParseFailException
     */
    public static <T> T jsonToClassGeneric(@Nonnull String jsonString, @Nonnull TypeReference<T> objClass) throws JsonParseFailException {

        if (jsonString.isEmpty()) {
            String errorMessage = String.format("Failed to deserialize class type: %s because an empty json string is given as parameter", objClass.toString());
            log.debug(errorMessage);
            throw new JsonParseFailException(errorMessage, FailureResultCode.JSON_PARSE_ERROR, jsonString, objClass);
        }

        try {
            return OBJECT_MAPPER.readValue(jsonString, objClass);
        } catch (Exception e) {
            log.debug("Failed to deserialize class type: {} json string: {}", objClass, jsonString);
            throw new JsonParseFailException(e.getMessage(), e, FailureResultCode.JSON_PARSE_ERROR, jsonString, objClass);
        }
    }
}