package com.ohgiraffers.restapi.section04.hateoas;

import com.ohgiraffers.restapi.section02.responseentity.ResponseMessage;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/hateoas")
public class HateoasTestController {
    private List<UserDTO> users;

    /* ArrayLsit 타입의 객체인 users에 DTO 타입의 객체를 넣어준다. */
    public HateoasTestController() {
        users = new ArrayList<>();
        users.add(new UserDTO(1, "user01", "pass01", "홍길동", new Date()));
        users.add(new UserDTO(2, "user02", "pass02", "유관순", new Date()));
        users.add(new UserDTO(3, "user03", "pass03", "판다", new Date()));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() {

        /* ResponseEntity에 해당하는 엔티티들을 설정해주기 위해 메소드를 작성한다. */

        /* 응답 헤더 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        /* hateoas 설정 */
        /* 원래는 List<UserDTO> 였는데 Hateoas의 EntityModel에 담기위해 가공하는 것. */
        List<EntityModel<UserDTO>> usersWithRel = users.stream().map(
                user ->
                        EntityModel.of(
                                user,
                                /* user 정보 + */
                                /* userDTO + 참조 링크가 있는 EntityModel을 만드는 것 */
                                linkTo(methodOn(HateoasTestController.class).findUserByNo(user.getNo())).withSelfRel(),
                                linkTo(methodOn(HateoasTestController.class).findAllUsers()).withRel("users")
                        )
        ).toList();

        /* 응답 데이터 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", usersWithRel);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        /* responseMessage :  body , headers : header , HttpStatus.OK : HttpStatusCode */
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);

    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {
        /* 응답 헤더 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).toList().get(0);

        /* 응답 데이터 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", foundUser);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(responseMessage);

    }




}
