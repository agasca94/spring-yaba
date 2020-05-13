package com.gasca.yaba.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 7886726021596756169L;

    public ResourceNotFoundException(Long id) {
        super("Could not find the resource with id " + id);
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " with id " + id + " not found");
    }

    public ResourceNotFoundException(String resource) {
        super(resource + " not found");
    }
}
