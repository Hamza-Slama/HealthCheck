package com.healthcheck.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Endpoints {

    private int id ;
    private String endpoints;
    private String medthod;

    public Endpoints(int id, String endpoints, String medthod) {
        this.id = id;
        this.endpoints = endpoints;
        this.medthod = medthod;
    }
}
