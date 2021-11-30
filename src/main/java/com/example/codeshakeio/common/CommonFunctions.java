package com.example.codeshakeio.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonFunctions {

    public static String concatenateStrings(String... args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg);
        }
        return sb.toString();
    }

}