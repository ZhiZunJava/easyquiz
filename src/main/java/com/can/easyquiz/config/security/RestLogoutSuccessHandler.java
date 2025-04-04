package com.can.easyquiz.config.security;

import com.can.easyquiz.domain.User;
import com.can.easyquiz.domain.UserEventLog;
import com.can.easyquiz.enums.SystemCodeEnum;
import com.can.easyquiz.event.UserEvent;
import com.can.easyquiz.service.UserService;
import com.can.easyquiz.utils.RestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;

    /**
     * Instantiates a new Rest logout success handler.
     *
     * @param eventPublisher the event publisher
     * @param userService    the user service
     */
    @Autowired
    public RestLogoutSuccessHandler(ApplicationEventPublisher eventPublisher, UserService userService) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        if (null != springUser) {
            User user = userService.getUserByUserName(springUser.getUsername());
            UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
            userEventLog.setContent(user.getUserName() + " 登出了轻松考考试系统");
            eventPublisher.publishEvent(new UserEvent(userEventLog));
        }
        RestUtil.response(response, SystemCodeEnum.OK);
    }
}
