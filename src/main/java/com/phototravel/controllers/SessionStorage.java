package com.phototravel.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Scope("session")
public class SessionStorage {

    private Date dt;

    public SessionStorage() {
        dt = new Date();
    }
}
