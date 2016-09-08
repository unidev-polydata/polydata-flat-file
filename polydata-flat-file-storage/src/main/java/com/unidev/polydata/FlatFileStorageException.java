package com.unidev.polydata;

/**
 * Storage exception,  thrown if something went wrong...
 */
public class FlatFileStorageException extends RuntimeException {

    public FlatFileStorageException() {
    }

    public FlatFileStorageException(String message) {
        super(message);
    }

    public FlatFileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlatFileStorageException(Throwable cause) {
        super(cause);
    }
}
