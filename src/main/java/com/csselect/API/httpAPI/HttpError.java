package com.csselect.API.httpAPI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * class that hold an httpsError code
 * can be thrown in the {@link Servlet#get(HttpServletRequest, HttpServletResponse)} and
 *                      {@link Servlet#post(HttpServletRequest, HttpServletResponse)} methods and
 *                      the errorCode will be sent to the user
 */
public class HttpError extends Exception {

    private final int errorCode;

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
