package com.recrutement.modules.joboffer.exceptions;

public class AlreadyAppliedToJobOfferException extends Exception {
    public AlreadyAppliedToJobOfferException(String msg){
        super(msg);
    }
    public AlreadyAppliedToJobOfferException(){}
}