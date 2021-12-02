package com.example.codeshakeio.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomHeaderNames {

    // --------RESPONSE HEADERS-----------------
    public static final String REQUEST_HIT_TIME = "Request_Hit_Time";
    public static final String RESPONSE_TIME = "Response_Time";
    public static final String REQUEST_PATH = "Request_Path";
    public static final String RESULT_CODE = "Result_Code";
    public static final String EXCEPTION_TIME = "Exception_Time";

}