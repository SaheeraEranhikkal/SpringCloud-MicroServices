package com.demo.microservices.currencyconversionservice;

import com.demo.microservices.currencyconversionservice.beans.CurrencyConversionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="currency-exchange-service",url = "http://localhost:8000")
//@FeignClient(name="currency-exchange-service")
// Invoking exchange Service through zuul gateway instead of hitting directly
@FeignClient(name="netflix-zuul-api-gateway-server")
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

   // @GetMapping("/currency-exchange/from/{from}/to/{to}")
    // Zuul : URL format ll be host/{application name}/{URI}
    @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionBean retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
}
