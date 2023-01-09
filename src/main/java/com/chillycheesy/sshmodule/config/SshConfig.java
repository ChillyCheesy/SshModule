package com.chillycheesy.sshmodule.config;

import com.chillycheesy.modulo.config.Configuration;

public class SshConfig {
    private final String host;
    private final int port;
    private final String user;
    private final String password;

    public SshConfig(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public static SshConfig loadDefaultConfig(Configuration defaultConfig) {
        String host = defaultConfig.getString("sshConfig.host");
        int port = defaultConfig.getInteger("sshConfig.port");
        String user = defaultConfig.getString("sshConfig.user");
        String password = defaultConfig.getString("sshConfig.password");
        return new SshConfig(host, port, user, password);
    }

    public static SshConfig loadCustomConfig(String host, int port, String user, String password) {
        return new SshConfig(host, port, user, password);
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "SshConfig{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
