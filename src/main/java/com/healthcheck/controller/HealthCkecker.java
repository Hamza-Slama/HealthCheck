package com.healthcheck.controller;


import com.healthcheck.model.Endpoints;
import com.healthcheck.model.ResponseEndpoints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/checks")
public class HealthCkecker {
    private ArrayList<Endpoints> endpointsArrayList = new ArrayList<>();
    private RestTemplate restTemplate = new RestTemplate();
    private Map<String, Long> siteToCounterMap = new HashMap<>();
    private int id = 0;

    @GetMapping(value = "/")
    public ResponseEntity<Object> getListOfEndPoints() throws InterruptedException {
        return ResponseEntity.status(200).body(endpointsArrayList);
    }


    @PostMapping(value = "/")
    public ResponseEntity<Object> addEndPoints(@RequestParam(name = "endpoints") String endpoints,
                                               @RequestParam(name = "method") HttpMethod method) {
        endpointsArrayList.add(new Endpoints(id, endpoints, method.name()));
        id++;
        return ResponseEntity.status(200).body("EndPoint added to list");
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteEndPoints(@PathVariable int id) {
        endpointsArrayList.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping(value = "/start")
    public ResponseEntity<Object> checkAllEndPoints() {

        ArrayList<ResponseEndpoints> responseEndpointsArrayList = new ArrayList<>();
        endpointsArrayList.forEach(it -> responseEndpointsArrayList.add(getHealthCheckFromEndpoint(it)));
        return ResponseEntity.status(200).body(responseEndpointsArrayList);
    }

    public ResponseEndpoints getHealthCheckFromEndpoint(Endpoints endpoints) {
        long start = System.currentTimeMillis();
        try {
            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
            mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
            restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
            ResponseEntity<String> response = this.restTemplate.exchange(endpoints.getEndpoints(), getHttpMethod(endpoints.getMedthod()),
                    null, String.class);
            long end = System.currentTimeMillis();
//            if (siteToCounterMap.containsKey(endpoints.getEndpoints())) siteToCounterMap.put(endpoints.getEndpoints(),siteToCounterMap.get(endpoints.getEndpoints())+1 );
//            else siteToCounterMap.put(endpoints.getEndpoints(),1l );
            siteToCounterMap.merge(endpoints.getEndpoints(), siteToCounterMap.getOrDefault(endpoints.getEndpoints(), 0l) + 1, (oldValue, newValue) -> newValue);
            return new ResponseEndpoints(response.getStatusCodeValue(), endpoints.getEndpoints(),
                    endpoints.getMedthod(), end - start, siteToCounterMap.get(endpoints.getEndpoints()));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
//            if (siteToCounterMap.containsKey(endpoints.getEndpoints())) siteToCounterMap.put(endpoints.getEndpoints(),siteToCounterMap.get(endpoints.getEndpoints())+1 );
//            else siteToCounterMap.put(endpoints.getEndpoints(),1l );
            siteToCounterMap.merge(endpoints.getEndpoints(), siteToCounterMap.getOrDefault(endpoints.getEndpoints(), 0l) + 1, (oldValue, newValue) -> newValue);
            long end = System.currentTimeMillis();
            return new ResponseEndpoints(e.getRawStatusCode(), endpoints.getEndpoints(), endpoints.getMedthod(), end - start, siteToCounterMap.get(endpoints.getEndpoints()));
        }
    }

    public HttpMethod getHttpMethod(String method) {
        for (HttpMethod h : HttpMethod.values()) {
            if (h.name().contains(method)) return h;
        }
        return HttpMethod.GET;
    }


}
