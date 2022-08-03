package com.oceandiary.api.room.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SimpleRedisTestServiceImpl {

    @Cacheable(value="simpleData")
    public List<String> getSimpleData(String param){
        log.info("start getSimpleData");
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        return list;
    }

    @CacheEvict(value="simpleData" , allEntries = true)
    public void simpleDataEvict(){
        log.info("cache evict");
    }

}