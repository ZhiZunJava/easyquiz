package com.can.easyquiz.event;

import com.can.easyquiz.domain.UserEventLog;
import org.springframework.context.ApplicationEvent;

public class UserEvent extends ApplicationEvent {
    private final UserEventLog userEventLog;

    public UserEvent(final UserEventLog userEventLog) {
        super(userEventLog);
        this.userEventLog = userEventLog;
    }

    public UserEventLog getUserEventLog() {
        return userEventLog;
    }
}
