package io.github.wangmeng.provider;

import com.alibaba.dubbo.config.annotation.Service;
import io.github.wangmeng.provider.api.SampleApiService;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = SampleApiService.class)
public class MsgServiceImpl implements SampleApiService {


    @Override
    public String sayHello(String param) {
        return "hello:" + param;
    }
}
