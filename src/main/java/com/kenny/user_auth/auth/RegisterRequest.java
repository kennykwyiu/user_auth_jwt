package com.kenny.user_auth.auth;

/** JSON body for signup: only email + password as requested */
public record RegisterRequest(String email, String password) {}
