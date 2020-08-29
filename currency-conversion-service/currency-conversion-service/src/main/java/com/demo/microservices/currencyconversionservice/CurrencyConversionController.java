package com.demo.microservices.currencyconversionservice;

import com.demo.microservices.currencyconversionservice.beans.CurrencyConversionBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    Logger logger =  LoggerFactory.getLogger("CurrencyConversionController.class");


    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean retrieveExchangeValue(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        Map<String,String> uriVariables = new HashMap();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

        ResponseEntity<CurrencyConversionBean> response = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class,uriVariables);
        CurrencyConversionBean responseBody = response.getBody();

        return new CurrencyConversionBean(responseBody.getId(),from,to,responseBody.getConversionMultiple(),quantity,
                quantity.multiply(responseBody.getConversionMultiple()),responseBody.getPort());
    }


    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean retrieveExchangeValueFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {


        CurrencyConversionBean responseBody = currencyExchangeServiceProxy.retrieveExchangeValue(from,to);

        logger.info("{} -->"+ responseBody);
        return new CurrencyConversionBean(responseBody.getId(),from,to,responseBody.getConversionMultiple(),quantity,
                quantity.multiply(responseBody.getConversionMultiple()),responseBody.getPort());


    }
}
