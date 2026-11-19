package com.hcld2.jwtdemo.dto;

/** A simple response body for endpoints that report an operation's status. */
public record StatusResponse(String status, String message) {
}
