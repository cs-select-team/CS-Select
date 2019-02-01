package com.csselect.API.httpAPI;


public class HttpError extends Exception {

    private int errorCode;

    /**
     * Creates a new {@link HttpError} with the given errorCode
     * @param errorCode status code of the exception
     */
    public HttpError(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets the {@link HttpError}s error code
     * @return errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }
}
