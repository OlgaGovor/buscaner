package com.phototravel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Created by PBezdienezhnykh on 028 28.7.2016.
 */
@Service
public class CacheManagerService {

    @Autowired
    CacheManager cacheManager;


    public void test() {
        for (String s : cacheManager.getCacheNames()) {
            System.out.println(s);
        }

    }


}
