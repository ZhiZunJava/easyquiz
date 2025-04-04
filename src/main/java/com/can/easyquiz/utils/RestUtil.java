package com.can.easyquiz.utils;

import com.can.easyquiz.annotation.RestResponse;
import com.can.easyquiz.enums.SystemCodeEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RestUtil {
    private static final Logger logger = LoggerFactory.getLogger(RestUtil.class);

    /**
     * Response.
     *
     * @param response   the response
     * @param systemCodeEnum the system code
     */
    public static void response(HttpServletResponse response, SystemCodeEnum systemCodeEnum) {
        response(response, systemCodeEnum.getCode(), systemCodeEnum.getMessage());
    }

    /**
     * Response.
     *
     * @param response   the response
     * @param systemCode the system code
     * @param msg        the msg
     */
    public static void response(HttpServletResponse response, int systemCode, String msg) {
        response(response, systemCode, msg, null);
    }


    /**
     * Response.
     *
     * @param response   the response
     * @param systemCode the system code
     * @param msg        the msg
     * @param content    the content
     */
    public static void response(HttpServletResponse response, int systemCode, String msg, Object content) {
        try {
            RestResponse res = new RestResponse<>(systemCode, msg, content);
            String resStr = JsonUtil.toJsonStr(res);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(resStr);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
