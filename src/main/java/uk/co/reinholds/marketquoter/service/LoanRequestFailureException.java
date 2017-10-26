package uk.co.reinholds.marketquoter.service;

public class LoanRequestFailureException extends RuntimeException {

    public LoanRequestFailureException(String msg) {
        super(msg);
    }
}
