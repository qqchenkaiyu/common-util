package com.cky.jsch;

import com.cky.strUtil.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ServerConfig implements Cloneable {

    protected String ip;
    protected Integer port;
    protected String alias;
    protected String serviceUsername;
    protected String rootUsername;
    protected String rootPassword;

    @Override
    public String toString() {
        return StringUtil.isBlank(alias) ? ip : alias;
    }

    @Override
    public ServerConfig clone() {
        try {
            return (ServerConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}