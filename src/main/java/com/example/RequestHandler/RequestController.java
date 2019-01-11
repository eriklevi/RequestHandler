package com.example.RequestHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
class ServiceInstanceRestController {

    @Autowired private LoadBalancerClient loadBalancer;

    @PreAuthorize("hasAuthority('PROBE')")
    @RequestMapping(value = "/getbroker", method = RequestMethod.GET)
    public String getBrokerInfo() {

        ServiceInstance serviceInstance=loadBalancer.choose("MOQUETTE");

        System.out.println(serviceInstance.getUri());

        String baseUrl = serviceInstance.getUri().toString();

        baseUrl = baseUrl+"/config";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response=null;
        try{
            response=restTemplate.exchange(baseUrl,
                    HttpMethod.GET, getHeaders(),String.class);
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
        System.out.println(response.getBody());
        return response.getBody();
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }


}


