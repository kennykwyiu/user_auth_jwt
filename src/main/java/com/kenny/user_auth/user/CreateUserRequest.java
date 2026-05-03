package com.kenny.user_auth.user;

/** Input payload when registering or creating a user (before hashing the password server-side). */
public record CreateUserRequest(String name, String email, String password) {}
