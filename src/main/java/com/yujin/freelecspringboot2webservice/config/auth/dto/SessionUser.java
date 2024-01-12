package com.yujin.freelecspringboot2webservice.config.auth.dto;

import com.yujin.freelecspringboot2webservice.domain.user.User;
import lombok.Getter;

// User 클래스를 그대로 사용하면 안되는 이유 :
// 세션에 저장하기 위해서는 User클래스에 직렬화를 구현해야한다. 하지만 User 클래스는 엔티티이기 때문에 언제 다른 엔티티와 관계가 형성될지 모른다. 예를 들어 @OneToMany, 등 자식 엔티티를 갖고 있다면 직렬화 대상에 자식들까지 포함되기 때문에 성능 이슈, 부수 효과가 발생할 확률이 높다. 그래서 직렬화 기능을 가진 세션 Dto를 추가로 만드는 것이 이후 운영 및 유지보수 때 많은 도움이 된다.
@Getter
public class SessionUser {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
