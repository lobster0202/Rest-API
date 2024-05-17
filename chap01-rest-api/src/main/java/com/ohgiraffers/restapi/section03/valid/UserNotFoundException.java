package com.ohgiraffers.restapi.section03.valid;

import org.springframework.http.ResponseEntity;

/* throw가 걸려있는 상태에서 클래스를 만들면 상위 타입인
* throwable을 상속받는다. */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String msg) {
        super(msg);


    }
}
