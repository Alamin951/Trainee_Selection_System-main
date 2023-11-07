package com.example.JSS.exception;

public class DuplicateEmailException extends Exception{
    public DuplicateEmailException(String meg){
        super(meg);
    }
}
