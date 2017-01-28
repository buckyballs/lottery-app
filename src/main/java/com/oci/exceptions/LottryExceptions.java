package com.oci.exceptions;

import com.oci.exceptions.errorcodes.ErrorCodes;

/**
 * Created by Ishtiaq on 1/27/2017.
 */
public class LottryExceptions extends RuntimeException {

    private ErrorCodes errorCodes;

    public LottryExceptions(ErrorCodes errorCodes) {
        this.errorCodes = errorCodes;
    }

    public LottryExceptions(String message) {
        super(message);
    }

    public LottryExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorCodes getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(ErrorCodes errorCodes) {
        this.errorCodes = errorCodes;
    }
}
