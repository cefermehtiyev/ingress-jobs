package com.example.ingressjobs.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    UNEXPECTED_ERROR("Unexpected error occurred"),
    LOGIN_FAILED_EXCEPTION("Login failed");




    private final String message;
}