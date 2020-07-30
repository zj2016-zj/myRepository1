package com.zj.client;

import com.zj.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "serviceProvider")
public interface UserClient {
    @GetMapping("/provider/user/{id}")
     User getUserById(@PathVariable("id") Integer id);
}
