package com.yujin.freelecspringboot2webservice;

import com.yujin.freelecspringboot2webservice.web.HelloController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class) // JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다(스프링 부트 테스트와 JUnit 사이에 연결자 역할)
@WebMvcTest(controllers = HelloController.class) // Web에 집중할 수 있는 어노테이션. 선언 시, Controller, ControllerAdvice 등 사용 가능
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 빈을 주입 받는다.
    private MockMvc mvc; // 웹 API 테스트 시 사용. 스프링 MVC 테스트의 시작점(GET, POST 등 테스트 가능)

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))  // /hello로 GET 요청
                .andExpect(status().isOk())  // 200인지 아닌지 검증
                .andExpect(content().string(hello));  // 응답 본문의 내용 검증. Controller에서 "hello"를 리턴하기 때문에 이 값을 검증
    }

    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)    // API 테스트할 때 사용될 요청 파라미터 설정. 단, 값은 String만 허용(숫자/날짜 등의 데이터도 으록할 때는 문자열로 변경)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))    // JSON응답값을 필드별로 검증할 수 있는 메소드. $를 기준으로 필드명을 명시
                .andExpect(jsonPath("$.amount").value(amount)); // .value로도 사용가능
    }
}
