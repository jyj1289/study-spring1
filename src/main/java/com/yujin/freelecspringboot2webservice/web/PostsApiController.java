package com.yujin.freelecspringboot2webservice.web;

import com.yujin.freelecspringboot2webservice.service.posts.PostsService;
import com.yujin.freelecspringboot2webservice.web.dto.PostsListResponseDto;
import com.yujin.freelecspringboot2webservice.web.dto.PostsResponseDto;
import com.yujin.freelecspringboot2webservice.web.dto.PostsSaveRequestDto;
import com.yujin.freelecspringboot2webservice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts/list")
    public List<PostsListResponseDto> findAll(){
        return postsService.findAllDesc();
    }

}
