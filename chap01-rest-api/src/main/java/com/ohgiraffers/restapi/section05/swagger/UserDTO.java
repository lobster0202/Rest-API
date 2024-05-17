package com.ohgiraffers.restapi.section05.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
/* @Schema: 또 설명 ㅡㅡ
* 특정 클래스, 필드, 메서드 등에 대해 스키마를 정의하는 데 사용*/
@Schema(description = "회원정보 DTO")
public class UserDTO {

    @Schema(description = "회원 번호(PK)")
    private int no;

    @Schema(description = "회원 ID")
    private String id;

    @Schema(description = "회원 비밀번호")
    private String pwd;

    @Schema(description = "회원 이름")
    private String name;

    @Schema(description = "회원 날짜")
    private Date enrollDate;
}
