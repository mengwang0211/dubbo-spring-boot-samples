package io.github.wangmeng.consumer.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import io.github.wangmeng.provider.api.SampleApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Reference(interfaceClass = SampleApiService.class)
    SampleApiService apiService;


    @RequestMapping(value = "hello")
    public ResponseEntity hello(String msg){
        return ResponseEntity.ok(apiService.sayHello(msg));
    }


}
