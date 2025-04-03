package com.can.easyquiz.annotation;

import com.can.easyquiz.enums.SystemCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestResponse<T> {
    /**
     * -- GETTER --
     *  Gets code.
     *
     *
     * -- SETTER --
     *  Sets code.
     *
     @return the code
      * @param code the code
     */
    private int code;
    /**
     * -- GETTER --
     *  Gets message.
     *
     *
     * -- SETTER --
     *  Sets message.
     *
     @return the message
      * @param message the message
     */
    private String message;
    /**
     * -- GETTER --
     *  Gets response.
     *
     *
     * -- SETTER --
     *  Sets response.
     *
     @return the response
      * @param response the response
     */
    private T response;

    /**
     * Instantiates a new Rest response.
     *
     * @param code    the code
     * @param message the message
     */
    public RestResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Instantiates a new Rest response.
     *
     * @param code     the code
     * @param message  the message
     * @param response the response
     */
    public RestResponse(int code, String message, T response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    /**
     * Fail rest response.
     *
     * @param code the code
     * @param msg  the msg
     * @return the rest response
     */
    public static RestResponse fail(Integer code, String msg) {
        return new RestResponse<>(code, msg);
    }

    /**
     * Ok rest response.
     *
     * @return the rest response
     */
    public static RestResponse ok() {
        SystemCode systemCode = SystemCode.OK;
        return new RestResponse<>(systemCode.getCode(), systemCode.getMessage());
    }

    /**
     * Ok rest response.
     *
     * @param <F>      the type parameter
     * @param response the response
     * @return the rest response
     */
    public static <F> RestResponse<F> ok(F response) {
        SystemCode systemCode = SystemCode.OK;
        return new RestResponse<>(systemCode.getCode(), systemCode.getMessage(), response);
    }

}
