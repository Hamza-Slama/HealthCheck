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

    public ResponseEndpoints(int code, String endpoints, String medthod) {
        this.code = code;
        this.endpoints = endpoints;
        this.medthod = medthod;
    }
}
