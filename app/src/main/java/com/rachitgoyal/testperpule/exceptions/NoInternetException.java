package com.rachitgoyal.testperpule.exceptions;

/**
 * Created by Rachit Goyal on 16/01/19.
 */
public class NoInternetException extends Exception {

    public NoInternetException(String detailMessage) {
        super("No Internet Connection : " + detailMessage);
    }

    public NoInternetException() {
        super("No Internet Connection");
    }
}
