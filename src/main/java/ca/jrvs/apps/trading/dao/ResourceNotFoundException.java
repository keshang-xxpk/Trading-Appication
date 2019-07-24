package ca.jrvs.apps.trading.dao;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg) { super(msg);}

    public  ResourceNotFoundException(String msg,Exception ex) {super(msg,ex);}

}
