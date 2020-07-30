package com.leyou.search.client;

import com.leyou.item.api.BrandAPi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service")
public interface BrandClient extends BrandAPi {
}