package com.zj.service;

import com.alibaba.dubbo.config.annotation.Service;

import java.util.Map;


public interface ReportService {

    Map<String, Object> getBusinessMessage() throws Exception;

}
