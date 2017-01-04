package com.strengthtailor.receiver;

/**
 * Created by nickw on 1/2/2017.
 */
public class RequestMessage {

    public RequestMessage() {

    }

    public RequestMessage(String request, String type, String uuid) {
        this.request = request;
        this.type = type;
        this.uuid = uuid;
    }

    private String request;
    private String type;
    private String uuid;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
