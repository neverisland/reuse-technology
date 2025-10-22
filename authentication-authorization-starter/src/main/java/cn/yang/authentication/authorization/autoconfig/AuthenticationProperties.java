package cn.yang.authentication.authorization.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : QingHai
 */
@Component
@ConfigurationProperties(prefix = "authentication.authorization")
public class AuthenticationProperties {

    /**
     * 是否启用
     */
    private Boolean enabled = false;

    /**
     * 系统用户初始密码
     */
    private String systemUserInitPassword = "yangyi";

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getSystemUserInitPassword() {
        return systemUserInitPassword;
    }

    public void setSystemUserInitPassword(String systemUserInitPassword) {
        this.systemUserInitPassword = systemUserInitPassword;
    }

    @Override
    public String toString() {
        return "AuthenticationProperties{" +
                "enabled=" + enabled +
                ", systemUserInitPassword='" + systemUserInitPassword + '\'' +
                '}';
    }
}
