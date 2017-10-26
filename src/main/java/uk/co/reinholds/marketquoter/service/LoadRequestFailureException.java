package uk.co.reinholds.marketquoter.service;

public class LoadRequestFailureException extends RuntimeException {

    public LoadRequestFailureException(String msg) {
        super(msg);
    }
}
