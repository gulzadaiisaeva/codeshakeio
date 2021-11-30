package com.example.codeshakeio.enums.resultcode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;


public interface ResultCode extends Serializable {

    Integer UNINITIALIZED_RESULT_CODE = -1;

    @JsonCreator
    static ResultCode fromInt(Integer value) throws Exception {

        if (null == value || ResultCode.UNINITIALIZED_RESULT_CODE.equals(value)) {
            return null;
        }
        Optional<FailureResultCode> fromFailureCodes = Arrays.stream(FailureResultCode.values()).filter(enm -> enm.errorCode.equals(value)).findFirst();
        Optional<SuccessResultCode> fromSuccessCodes = Arrays.stream(SuccessResultCode.values()).filter(enm -> enm.successCode.equals(value)).findFirst();
        Optional<InterruptionResultCode> fromInterruptionCodes = Arrays.stream(InterruptionResultCode.values()).filter(enm -> enm.interruptionCode.equals(value)).findFirst();

        if (fromFailureCodes.isPresent()) {
            return fromFailureCodes.get();
        } else if (fromSuccessCodes.isPresent()) {
            return fromSuccessCodes.get();
        } else if (fromInterruptionCodes.isPresent()) {
            return fromInterruptionCodes.get();
        } else {
            throw new Exception(String.format("UnRecognized Result Code: %d", value));
        }
    }

    Integer getResultCode();

    @JsonValue
    Integer toInt();
}
