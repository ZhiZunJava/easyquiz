package com.can.easyquiz.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum UserStatusEnum {
    Enable(1, "启用"),
    Disable(2, "禁用");

    int code;
    String name;

    UserStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<Integer, UserStatusEnum> keyMap = new HashMap<>();

    static {
        for (UserStatusEnum item : UserStatusEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static UserStatusEnum fromCode(Integer code) {
        return keyMap.get(code);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

}
