package com.coursed.service;

/**
 * Created by Trach on 11/6/2016.
 */

public interface SecurityService {
    String findLoggedInUsername();
    void autoLogin(String username, String password);
}
