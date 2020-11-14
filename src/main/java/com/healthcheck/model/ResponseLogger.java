package com.healthcheck.model;

import com.fasterxml.jackson.databind.JsonNode;

public class ResponseLogger {

    private String time ;
    private JsonNode payload;

    public ResponseLogger(String time, JsonNode payload) {
        this.time = time;
        this.payload = payload;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public JsonNode getPayload() {
        return payload;
    }

    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }
}
