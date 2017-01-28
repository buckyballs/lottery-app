package com.oci.exceptions.errorcodes;

/**
 * Created by Ishtiaq on 1/27/2017.
 */
public enum ErrorCodes {

    DUPLICATE_RECORD("INPUT", "participant.input.duplicate", "Duplicate participant entry.");

    private String source;
    private String errorCode;
    private String errorMessage;

    ErrorCodes(String source, String errorCode, String errorMessage) {
        this.source = source;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
