package com.shyam.carsaleservice.secuirity;

public class MyCustomErrorDTO {
    private Integer code;
    private String message;
    private Boolean status;

    public MyCustomErrorDTO(Integer code,String message,Boolean status){
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
