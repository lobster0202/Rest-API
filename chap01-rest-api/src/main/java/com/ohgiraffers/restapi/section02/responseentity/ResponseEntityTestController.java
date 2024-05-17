package com.ohgiraffers.restapi.section02.responseentity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/entity")
public class ResponseEntityTestController {

    /* 섹션2에서는 컨트롤러를 어떻게 사용하는지에 대해서 배우는 것 */

    private List<UserDTO> users;

    /* ArrayLsit 타입의 객체인 users에 DTO 타입의 객체를 넣어준다. */
    public ResponseEntityTestController() {
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

        /* 응답 데이터 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", users);
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

    /* 새로운 자원을 추가하는 http 메소드 */
    @PostMapping("/users")
    /* 처리를 할게 아니라서 응답 메세지를 전달할게 없음(body에 들어 갈 내용이 없다.) */
    /* */
    public ResponseEntity<Void> registUser(@RequestBody UserDTO newUser) {

        int lastUserNo = users.get(users.size() - 1).getNo();
        newUser.setNo(lastUserNo + 1);
        newUser.setEnrollDate(new Date());
        users.add(newUser);


        /* 코드 해석 :

        users.get(users.size() - 1).getNo() : 지금 만든 애의 유저 번호  */
        return ResponseEntity
                .created(URI.create("/entity/users/" + users.get(users.size() - 1).getNo()))
                .build();
    }


    /* 하다하다 이제 put 매핑도 나와버리네 */
    @PutMapping("/users/{userNo}")
    public ResponseEntity<Void> modifyUser(@PathVariable int userNo,
                                           @RequestBody UserDTO modifyInfo) {

        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).toList().get(0);

        foundUser.setId(modifyInfo.getId());
        foundUser.setPwd(modifyInfo.getPwd());
        foundUser.setName(modifyInfo.getName());

        return ResponseEntity
                .created(URI.create("/entity/users/" + userNo))
                .build();
    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<Void> removeUser(@PathVariable int userNo) {

        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).toList().get(0);

        users.remove(foundUser);

        /* noContent : */
        return ResponseEntity
                .noContent()
                .build();

    }
    
}
