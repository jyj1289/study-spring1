package com.yujin.freelecspringboot2webservice.web;

import com.yujin.freelecspringboot2webservice.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON을 반환하는 컨트롤러로 만들어준다.
public class HelloController {

    @GetMapping("/hello")   // Get의 요청을 받을 수 있는 API
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, // RequestParam : 외부에서 API로 너긴 파라미터를 가져오는 어노테이션, 여기서는 외부에서 name이란 이름으로 넘긴 파라미터를 메서드 파리미터 name에 저장하게 된다.
                                     @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
}
