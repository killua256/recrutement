package com.recrutement.modules.management.exceptions;

public class AlreadyAppliedToJobOfferException extends Exception {
    public AlreadyAppliedToJobOfferException(String msg){
        super(msg);
    }
    public AlreadyAppliedToJobOfferException(){}
}
