package com.healthcheck.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ResponseEndpoints {
    private int code;
    private String endpoints;
    private String medthod;
    private long timing;
    private long counter;

    public ResponseEndpoints(int code, String endpoints, String medthod) {
        this.code = code;
        this.endpoints = endpoints;
        this.medthod = medthod;
    }

    public ResponseEndpoints(int code, String endpoints, String medthod, long timing) {
        this.code = code;
        this.endpoints = endpoints;
        this.medthod = medthod;
        this.timing = timing;
    }

    public ResponseEndpoints(int code, String endpoints, String medthod, long timing, long counter) {
        this.code = code;
        this.endpoints = endpoints;
        this.medthod = medthod;
        this.timing = timing;
        this.counter = counter;
    }
}
