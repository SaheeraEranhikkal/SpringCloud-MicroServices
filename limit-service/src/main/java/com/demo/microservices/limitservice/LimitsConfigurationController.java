package com.demo.microservices.limitservice;

import com.demo.microservices.limitservice.beans.LimitsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    @Autowired
    private Configuration config;

    @GetMapping("/limits")
    public LimitsConfiguration retrieveLimitsFromConfig(){
        return new LimitsConfiguration(config.getMaximum(),config.getMinimum());
    }
}
