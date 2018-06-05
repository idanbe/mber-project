package com.example.administrator.game_4_in_a_row;

public class Properties extends java.util.Properties {

    private String config_url;
    private String hash_url;
    private String log_url;
    private String user_data_url;

    public Properties() {}

    public String getConfig_url() {
        return config_url;
    }

    public void setConfig_url(String config_url) {
        this.config_url = config_url;
    }

    public String getHash_url() {
        return hash_url;
    }

    public void setHash_url(String hash_url) {
        this.hash_url = hash_url;
    }

    public String getLog_url() {
        return log_url;
    }

    public void setLog_url(String log_url) {
        this.log_url = log_url;
    }

    public String getUser_data_url() {
        return user_data_url;
    }

    public void setUser_data_url(String user_data_url) {
        this.user_data_url = user_data_url;
    }
}
