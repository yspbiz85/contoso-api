package com.contoso.api.exceptions;

public class GraphException extends RuntimeException {
    public GraphException(String message) {
        super(message);
    }
}
