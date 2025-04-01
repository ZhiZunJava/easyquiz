package com.can.easyquiz.enums;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    STUDENT(1, "STUDENT"),
    ADMIN(3, "ADMIN");

    int code;
    String name;

    Role(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<Integer, Role> keyMap = new HashMap<>();

    static {
        for (Role item : Role.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static Role fromCode(Integer code) {
        return keyMap.get(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return "ROLE_" + name;
    }
}
