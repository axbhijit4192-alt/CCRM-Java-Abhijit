package edu.ccrm.exception;

public class MaxCreditLimitExceededException extends Exception {
    private static final long serialVersionUID = 1L;

    public MaxCreditLimitExceededException(String msg) { super(msg); }
}
