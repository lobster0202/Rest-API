package com.ohgiraffers.restapi.section03.valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/* Bean이 자동으로 생성되고 여기서 생성하는 핸들러들이 전역적으로 행동할 수 있다. */
@ControllerAdvice
public class ExceptionController {

    /* @ExceptionHandler : UserNotFoundException.class 이 타입의 Exception이 발생하면
    * 아래의 메소드가 실행된다는 뜻 */

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserNotFoundException e) {

        String code = "ERROR_CODE_00000";
        String description = "회원 정보 조회 실패";
        String detail = e.getMessage();


        /* body 부분 : new ErrorResponse(code, description, detail) */
        return new ResponseEntity<>(new ErrorResponse(code, description, detail),HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException e) {

        String code = "";
        String description = "";
        String detail = "";

        /* 에러가 있다면 */
        if(e.getBindingResult().hasErrors()) {
            detail
                    = e.getBindingResult().getFieldError().getDefaultMessage();
            String bindResultCode
                    = e.getBindingResult().getFieldError().getCode();
            System.out.println(bindResultCode);
            switch(bindResultCode) {
                case "NotNull" :
                    code = "ERROR_CODE_00001";
                    description = "필수 값이 누락되었습니다.";
                    break;

                case "NotBlank" :
                    code = "ERROR_CODE_00002";
                    description = "필수 값이 공백으로 처리되었습니다.";
                    break;

                case "Size" :
                    code = "ERROR_CODE_00003";
                    description = "알맞은 크기의 값이 입력되지 않았습니다.";
                    break;

                case "Length" :
                    code = "ERROR_CODE_00004";
                    description = "알맞은 크기의 값이 입력되지 않았습니다.";
                    break;

                case "Past" :
                    code = "ERROR_CODE_00005";
                    description = "날짜가 알맞게 입력되지 않았습니다.";
                    break;

                case "Future" :
                    code = "ERROR_CODE_00006";
                    description = "알맞은 알맞게 입력되지 않았습니다.";
                    break;

            }

        }

        ErrorResponse errorResponse
                = new ErrorResponse(code, description, detail);

        return new ResponseEntity<>(new ErrorResponse(code, description, detail), HttpStatus.BAD_REQUEST);

    }

}
