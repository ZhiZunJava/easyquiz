package com.can.easyquiz.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@ConfigurationProperties(prefix = "system")
public class SystemConfig {

    private PasswordConfig pwdKey;
    private List<String> securityIgnoreUrls;

    public PasswordConfig getPwdKey() {
        return pwdKey;
    }

    public void setPwdKey(PasswordConfig pwdKey) {
        this.pwdKey = pwdKey;
    }

    public List<String> getSecurityIgnoreUrls() {
        return securityIgnoreUrls;
    }

    public void setSecurityIgnoreUrls(List<String> securityIgnoreUrls) {
        this.securityIgnoreUrls = securityIgnoreUrls;
    }
}
