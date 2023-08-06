package dnd.project.domain.user.response;

import dnd.project.domain.user.entity.Users;
import lombok.*;


public class UserResponse {

    @NoArgsConstructor
    @Getter
    public static class Login {
        private Long id;
        private String email;
        private String name;
        private String accessToken;
        private String refreshToken;

        @Builder
        private Login(Long id, String email, String name, String accessToken, String refreshToken) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public static Login response(Users user, String atk, String rtk) {
            return Login.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getNickName())
                    .accessToken(atk)
                    .refreshToken(rtk)
                    .build();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class Detail {
        private Long id;
        private String email;
        private String name;

        @Builder
        private Detail(Long id, String email, String name) {
            this.id = id;
            this.email = email;
            this.name = name;
        }

        public static UserResponse.Detail response(Users user) {
            return UserResponse.Detail.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getNickName())
                    .build();
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    @Builder
    public static class OAuth {
        private String email;
        private String name;
    }
}