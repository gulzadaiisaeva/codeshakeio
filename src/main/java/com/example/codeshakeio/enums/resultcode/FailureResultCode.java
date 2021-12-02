package com.example.codeshakeio.enums.resultcode;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FailureResultCode implements ResultCode {

    /**
     * DATA
     */
    OBJECT_NOT_DEFINED(400883),
    OBJECT_ALREADY_EXIST(400879),
    PROCESS_ALREADY_COMPLETED(400874),


    /**
     * JSON
     */
    JSON_PARSE_ERROR(400301), JSON_CREATION_ERROR(400302),

    /**
     * Pageable
     */

    PAGE_NOT_FOUND(400404), RECORD_NOT_FOUND(401404),

    /**
     * Request
     */
    REQUEST_BODY_INVALID(400801), SERVICE_REQUEST_FAILED(400802),
    VALIDATION_ERROR(400804), BAD_REQUEST(400805),
    DB_INSERTION_FAILURE(400812),
    MISSING_HEADERS(400812),

    /**
     * Other
     */
    RUNTIME_COMMAND_EXECUTION_FAILED(400901), ILLEGAL_APP_STATE_ERROR(400902), UNRECOGNIZED_ERROR(400903),
    NONEXISTENT_DATA_FAILURE(400904), ACCESS_NOT_GRANTED(400905), DTO_CONVERSION_FAILED(400906),
    REQUEST_USER_NOT_FOUND(400907), DISALLOWED_OPERATION_EXCEPTION(400908),
    PERSISTENCE_ERROR(400910), DB_LOGGING_FAILED(400911), UNRECOGNIZED_RESULT_CODE(400912), DATA_FETCH_FAILURE(400913),
    DB_REMOVE_FAILURE(400914), DB_UPDATE_FAILURE(400915), DATA_INTEGRITY_FAILURE(400916),
    UNAUTHORIZED_EXCEPTION(401900);

    public final Integer errorCode;

    FailureResultCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    @Override
    public Integer toInt() {
        return errorCode;
    }

    @Override
    public Integer getResultCode() {
        return errorCode;
    }
}
