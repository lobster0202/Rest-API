package com.ohgiraffers.restapi.section01.response;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/response")
public class ResponseRestController {


    /* 섹션 1의 목표는 PostMan을 사용하여 타입에 따라서 반응이 어떻게 나오는지 보는 것이다. */

    /* 1. 문자열 응답 */
    @GetMapping("/hello")
    public String helloWorld() {

        return "Hello World";

    }

    /* 2. 기본 자료형 응답 */
    @GetMapping("/random")
    public int getRandomNumber() {

        return (int)(Math.random() * 10 ) + 1 ;

    }

    /* 3. Object 응답 */
    @GetMapping("/message")
    public Message getMessage() {

        return new Message(200,"메세지를 응답합니다.");

    }

    /* 4. List 응답 */
    @GetMapping("/list")
    public List<String> getList() {

        return List.of(new String[]{"사과", "바나나", "복숭아"});

    }

    /* 5. Map 응답 */
    @GetMapping("/map")
    public Map<Integer, String> getMap() {
        List<Message> messagesList = new ArrayList<>();
        messagesList.add(new Message(200,"정상 응답"));
        messagesList.add(new Message(404,"페이지를 찾을 수 없습니다."));
        messagesList.add(new Message(500,"니 잘못"));

        /* List를 stream으로 만들어주고
        *  다시 Map으로 만들어준다.
        * Map에는 key값과 value 값이 있는데
        * 여기서 key 값은 Message::getHttpStatusCode, value 값은 Message::getMessage다.  */
        return messagesList.stream().collect(Collectors.toMap(Message::getHttpStatusCode, Message::getMessage));

    }

    /* 6. 파일 응답 */
    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {

        return getClass().getResourceAsStream("/images/202212008462_500.png").readAllBytes();

    }

    /* 7. ResponseEntity 응답 */
    @GetMapping("/entity")
    /* ResponseEntity 응답에서 중요한 요소는 : body(http 메소드의 바디), headers, statusCode가 중요한데
    * body로 들어갈 타입이 제네릭으로 돼있는 것 */
    public ResponseEntity<Message> getEntity() {

        return ResponseEntity.ok(new Message(123, "hello world!"));
    }






}
