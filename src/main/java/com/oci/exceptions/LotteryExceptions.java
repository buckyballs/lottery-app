package com.oci.exceptions;

import com.oci.exceptions.errorcodes.ErrorCodes;

/**
 * Created by Ishtiaq on 1/27/2017.
 */
public class LotteryExceptions extends RuntimeException {

    private ErrorCodes errorCodes;

    public LotteryExceptions(ErrorCodes errorCodes) {
        this.errorCodes = errorCodes;
    }

    public LotteryExceptions(String message) {
        super(message);
    }

    public LotteryExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorCodes getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(ErrorCodes errorCodes) {
        this.errorCodes = errorCodes;
    }
}
