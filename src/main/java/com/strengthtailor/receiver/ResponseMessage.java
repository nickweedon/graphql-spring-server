package com.strengthtailor.receiver;

import java.util.Map;

/**
 * Created by nickw on 1/2/2017.
 */
public class ResponseMessage {

    public ResponseMessage(String type, Map<String, Object> response) {
        this.type = type;
        this.response = response;
    }
    private String type;
    private Map<String, Object> response;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getResponse() {
        return response;
    }

    public void setResponse(Map<String, Object> response) {
        this.response = response;
    }
}
