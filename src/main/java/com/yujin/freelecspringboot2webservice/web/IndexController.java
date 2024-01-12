package com.yujin.freelecspringboot2webservice.web;

import com.yujin.freelecspringboot2webservice.config.auth.LoginUser;
import com.yujin.freelecspringboot2webservice.config.auth.dto.SessionUser;
import com.yujin.freelecspringboot2webservice.service.posts.PostsService;
import com.yujin.freelecspringboot2webservice.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){   // Model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다. 여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달한다.
        // 기존에(User) httpSession.getAttribute("user") 로 가져오던 세션 정보 값이 개선 되었다.
        // 이제는 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있게 되었다.
        // Sessionuser user = (SessionUser) httpSession.getAttribute("user")
        model.addAttribute("posts", postsService.findAllDesc());
        if(user != null){   // 세션에 저장된 값이 있을 때만 model에 userName으로 등록한다. 세션에 저장된 값이 없으면 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보이게 된다.
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    /*
    * 내장 톰캣의 메모리에 저장되기 때문에 내장 톰캣처럼 애플리케이션 실행 시 실행되는 구조에선 항상 초기화가 된다.
    * 즉, 배포할 때마다 톰캣이 재시작된다는 것이다.
    * 2대 이상의 서버에서 서비스하고 있다면 톰캣마다 세션 동기화 설정을 해야ㅁ만 한다. 그래서 실제 현업에서는 세션 저장소에 대해 다음의 3가지 중 한가지를 선택한다.
    *
    * 1. 톰캣 세션을 사용한다.
    *   - 일반적으로 별다른 설정을 하지 않을 때 기본적으로 선택되는 방식이다.
    *   - 이렇게 될 경우 톰캣에 세션이 저장되기 때문에 2대 이상의 WAS 가 구동되는 환경에서는 톰캣들 간의 세션 공유를 위한 추가 설정이 필요하다.
    *
    * 2. MySql 과 같은 데이터베이스를 세션 저장소로 사용한다.
    *   - 여러 WAS 간의 공용 세션을 사용할 수 있는 가장 쉬운 방법이다.
    *   - 많은 설정이 필요 없지만, 결국 로그인 요청마다 DB IO 가 발생하여 성능상 이슈가 발생할 수 있다.
    *   - 보통 로그인 요청이 많이 없는 백오피스, 사내 시스템 용도에서 사용한다.
    *
    * 3. Redis, Memcached 와 같은 메모리 DB 를 세션 저장소로 사용한다.
    *   - B2C 서비스에서 가장 많이 사용하는 방식이다.
    *   - 실제 서비스로 사용하기 위해서는 Embedded Redis 와 같은 방식이 아닌 외부 메모리 서버가 필요하다.
    * */

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
