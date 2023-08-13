package dnd.project.domain.lecture.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.response.LectureScopeListReadResponse;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.review.repository.ReviewRepository;
import dnd.project.domain.user.entity.Authority;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(LectureQueryRepositoryTest.TestQuerydslConfig.class)
@DataJpaTest
class LectureQueryRepositoryTest {

    @Autowired
    LectureQueryRepository lectureQueryRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    static final List<Lecture> LECTURES = List.of(
            Lecture.builder()
                    .title("스프링 부트 - 핵심 원리와 활용")
                    .source("Inflearn")
                    .url("url")
                    .price("99000")
                    .name("김영한")
                    .mainCategory("프로그래밍")
                    .subCategory("웹")
                    .keywords("스프링,스프링부트")
                    .content("실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다.")
                    .imageUrl("url")
                    .build(),

            Lecture.builder()
                    .title("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술")
                    .source("Inflearn")
                    .url("url")
                    .price("99,000")
                    .name("김영한")
                    .mainCategory("프로그래밍")
                    .subCategory("웹")
                    .keywords("스프링,스프링MVC")
                    .content("웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다.")
                    .imageUrl("url")
                    .build(),

            Lecture.builder()
                    .title("스프링 DB 2편 - 데이터 접근 활용 기술")
                    .source("Inflearn")
                    .url("url")
                    .price("99,000")
                    .name("김영한")
                    .mainCategory("프로그래밍")
                    .subCategory("웹")
                    .keywords("스프링,DB")
                    .content("백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다.")
                    .imageUrl("url")
                    .build(),

            Lecture.builder()
                    .title("배달앱 클론코딩 [with React Native]")
                    .source("Inflearn")
                    .url("url")
                    .price("71,500")
                    .name("조현영")
                    .mainCategory("프로그래밍")
                    .subCategory("앱")
                    .keywords("리액트 네이티브")
                    .content("리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다.")
                    .imageUrl("url")
                    .build(),

            Lecture.builder()
                    .title("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지")
                    .source("Inflearn")
                    .url("url")
                    .price("205,700")
                    .name("앨런(Allen)")
                    .mainCategory("프로그래밍")
                    .subCategory("앱")
                    .keywords("iOS")
                    .content("탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초")
                    .imageUrl("url")
                    .build()
    );

    @BeforeEach
    void beforeAll() {
        lectureRepository.saveAll(LECTURES);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 없음")
    @Test
    void findAll1() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, null, 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(3);
        assertThat(lectures.getTotalElements()).isEqualTo(5);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 스프링")
    @Test
    void findAll2() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "스프링", 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(2);
        assertThat(lectures.getTotalElements()).isEqualTo(3);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 김영한")
    @Test
    void findAll3() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "김영한", 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(2);
        assertThat(lectures.getTotalElements()).isEqualTo(3);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 iOS")
    @Test
    void findAll4() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "iOS", 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(1);
        assertThat(lectures.getTotalElements()).isEqualTo(1);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 백엔드")
    @Test
    void findAll5() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "백엔드", 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(1);
        assertThat(lectures.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 앱")
    @Test
    void findAll6() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "앱", 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(1);
        assertThat(lectures.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 네이티브")
    @Test
    void findAll7() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "네이티브", 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(1);
        assertThat(lectures.getTotalElements()).isEqualTo(1);
    }

    @DisplayName("강의 검색 - 메인 카테고리 프로그래밍, 키워드 없음")
    @Test
    void findAll8() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍", null, null, 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(3);
        assertThat(lectures.getTotalElements()).isEqualTo(5);
    }

    @DisplayName("강의 검색 - 메인 카테고리 프로그래밍, 서브 카테고리 앱, 키워드 없음")
    @Test
    void findAll9() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍", "앱", null, 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(1);
        assertThat(lectures.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("강의 검색 - 메인 카테고리 프로그래밍, 서브 카테고리 웹, 키워드 없음")
    @Test
    void findAll10() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍", "웹", null, 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(2);
        assertThat(lectures.getTotalElements()).isEqualTo(3);
    }

    @DisplayName("강의 Scope 비로그인 조회 - 랜덤한 별점 4.0 이상 수강 후기들")
    @Test
    void findByHighScoresWithNotLogin() {
        // given

        // 사용자 생성
        Users user1 = saveUser("test1@test.com", "테스터1");
        Users user2 = saveUser("test2@test.com", "테스터2");
        Users user3 = saveUser("test3@test.com", "테스터3");
        userRepository.saveAll(List.of(user1, user2, user3));

        // 강의 생성
        Lecture lecture1 = getLecture("웹 디자인의 기초", "프로그래밍");
        Lecture lecture2 = getLecture("자바 프로그래밍 실전 입문", "프로그래밍");
        Lecture lecture3 = getLecture("맛있는 파스타 요리", "요리");
        Lecture lecture4 = getLecture("게임 개발의 첫걸음", "게임");
        Lecture lecture5 = getLecture("그림으로 배우는 일본어", "디자인");
        Lecture lecture6 = getLecture("프론트엔드 개발 마스터 클래스", "프로그래밍");
        Lecture lecture7 = getLecture("컴퓨터 비전 기초", "프로그래밍");
        Lecture lecture8 = getLecture("디지털 아트 워크샵", "디자인");
        Lecture lecture9 = getLecture("영화 속 레시피", "요리");
        Lecture lecture10 = getLecture("모바일 게임 디자인", "디자인");
        Lecture lecture11 = getLecture("너무 쉬운 스프링 개발서", "프로그래밍");
        Lecture lecture12 = getLecture("파이썬 프로그래밍 기초", "프로그래밍");

        lectureRepository.saveAll(List.of(
                lecture1, lecture2, lecture3,
                lecture4, lecture5, lecture6,
                lecture7, lecture8, lecture9,
                lecture10, lecture11, lecture12));


        // 리뷰 생성
        Review review1 = getReview(lecture1, user1, 5.0, "강의력 좋은, ", "내용이 알찹니다!");
        Review review2 = getReview(lecture2, user1, 0.5, "도움이 안되었어요, ", "윽... 너무별로");
        Review review3 = getReview(lecture3, user2, 1.0, "도움이 안되었어요, ", "소리가 안들리는데요");
        Review review4 = getReview(lecture4, user1, 4.5, "강의력 좋은, 이해가 잘돼요", "내용이 알찹니다!");
        Review review5 = getReview(lecture5, user1, 1.5, "매우 적극적, ", "내용이 알찹니다!");
        Review review6 = getReview(lecture6, user1, 2.0, "매우 적극적, 도움이 많이 됐어요, ", "내용이 알찹니다!");
        Review review7 = getReview(lecture7, user1, 2.5, "내용이 자세해요", "내용이 알찹니다!");
        Review review8 = getReview(lecture8, user1, 3.5, "강의력 좋은, 듣기 좋은 목소리", "내용이 알찹니다!");
        Review review9 = getReview(lecture9, user1, 4.0, "듣기 좋은 목소리", "내용이 알찹니다!");
        Review review10 = getReview(lecture10, user1, 3.0, "보통이에요, ", "내용이 알찹니다!");

        reviewRepository.saveAll(List.of(
                review1, review2, review3, review4,
                review5, review6, review7, review8,
                review9, review10
        ));

        // when
        List<LectureScopeListReadResponse.DetailReview> detailReviews = lectureQueryRepository.findByHighScores(null);

        // then
        assertThat(detailReviews)
                .hasSize(3)
                .extracting("id", "score")
                .contains(
                        tuple(review1.getId(), review1.getScore()),
                        tuple(review4.getId(), review4.getScore()),
                        tuple(review9.getId(), review9.getScore())
                );
    }

    @DisplayName("강의 Scope 로그인 후 조회 - 랜덤한 별점 4.0 이상 수강 후기들")
    @Test
    void findByHighScoresWithLogin() {
        // given

        // 사용자 생성
        Users user1 = saveUser("test1@test.com", "테스터1", "요리,프로그래밍");
        Users user2 = saveUser("test2@test.com", "테스터2");
        Users user3 = saveUser("test3@test.com", "테스터3");
        userRepository.saveAll(List.of(user1, user2, user3));

        // 강의 생성
        Lecture lecture1 = getLecture("웹 디자인의 기초", "프로그래밍");
        Lecture lecture2 = getLecture("자바 프로그래밍 실전 입문", "프로그래밍");
        Lecture lecture3 = getLecture("맛있는 파스타 요리", "요리");
        Lecture lecture4 = getLecture("게임 개발의 첫걸음", "게임");
        Lecture lecture5 = getLecture("그림으로 배우는 일본어", "디자인");
        Lecture lecture6 = getLecture("프론트엔드 개발 마스터 클래스", "프로그래밍");
        Lecture lecture7 = getLecture("컴퓨터 비전 기초", "프로그래밍");
        Lecture lecture8 = getLecture("디지털 아트 워크샵", "디자인");
        Lecture lecture9 = getLecture("영화 속 레시피", "요리");
        Lecture lecture10 = getLecture("모바일 게임 디자인", "디자인");
        Lecture lecture11 = getLecture("너무 쉬운 스프링 개발서", "프로그래밍");
        Lecture lecture12 = getLecture("파이썬 프로그래밍 기초", "프로그래밍");

        lectureRepository.saveAll(List.of(
                lecture1, lecture2, lecture3,
                lecture4, lecture5, lecture6,
                lecture7, lecture8, lecture9,
                lecture10, lecture11, lecture12));


        // 리뷰 생성
        Review review1 = getReview(lecture1, user1, 5.0, "강의력 좋은, ", "내용이 알찹니다!");
        Review review2 = getReview(lecture2, user1, 0.5, "도움이 안되었어요, ", "윽... 너무별로");
        Review review3 = getReview(lecture3, user2, 1.0, "도움이 안되었어요, ", "소리가 안들리는데요");
        Review review4 = getReview(lecture4, user1, 4.5, "강의력 좋은, 이해가 잘돼요", "내용이 알찹니다!");
        Review review5 = getReview(lecture5, user1, 1.5, "매우 적극적, ", "내용이 알찹니다!");
        Review review6 = getReview(lecture6, user1, 2.0, "매우 적극적, 도움이 많이 됐어요, ", "내용이 알찹니다!");
        Review review7 = getReview(lecture7, user1, 2.5, "내용이 자세해요", "내용이 알찹니다!");
        Review review8 = getReview(lecture8, user1, 3.5, "강의력 좋은, 듣기 좋은 목소리", "내용이 알찹니다!");
        Review review9 = getReview(lecture9, user1, 4.0, "듣기 좋은 목소리", "내용이 알찹니다!");
        Review review10 = getReview(lecture10, user1, 3.0, "보통이에요, ", "내용이 알찹니다!");

        reviewRepository.saveAll(List.of(
                review1, review2, review3, review4,
                review5, review6, review7, review8,
                review9, review10
        ));

        // when
        List<LectureScopeListReadResponse.DetailReview> detailReviews =
                lectureQueryRepository.findByHighScores(user1.getInterests());

        // then
        assertThat(detailReviews)
                .hasSize(2)
                .extracting("id", "score")
                .contains(
                        tuple(review1.getId(), review1.getScore()),
                        tuple(review9.getId(), review9.getScore())
                );
    }

    @DisplayName("강의 Scope 관심분야 Null 조회 - 강의력 좋은 순")
    @Test
    void findByBestLecturesWithNotLogin() {
        // given

        // 사용자 생성
        Users user1 = saveUser("test1@test.com", "테스터1");
        Users user2 = saveUser("test2@test.com", "테스터2");
        Users user3 = saveUser("test3@test.com", "테스터3");
        userRepository.saveAll(List.of(user1, user2, user3));

        // 강의 생성
        Lecture lecture1 = getLecture("웹 디자인의 기초", "프로그래밍");
        Lecture lecture2 = getLecture("자바 프로그래밍 실전 입문", "프로그래밍");
        Lecture lecture3 = getLecture("맛있는 파스타 요리", "요리");
        Lecture lecture4 = getLecture("게임 개발의 첫걸음", "게임");
        Lecture lecture5 = getLecture("그림으로 배우는 일본어", "디자인");
        Lecture lecture6 = getLecture("프론트엔드 개발 마스터 클래스", "프로그래밍");
        Lecture lecture7 = getLecture("컴퓨터 비전 기초", "프로그래밍");
        Lecture lecture8 = getLecture("디지털 아트 워크샵", "디자인");
        Lecture lecture9 = getLecture("영화 속 레시피", "요리");
        Lecture lecture10 = getLecture("모바일 게임 디자인", "디자인");
        Lecture lecture11 = getLecture("너무 쉬운 스프링 개발서", "프로그래밍");
        Lecture lecture12 = getLecture("파이썬 프로그래밍 기초", "프로그래밍");

        lectureRepository.saveAll(List.of(
                lecture1, lecture2, lecture3,
                lecture4, lecture5, lecture6,
                lecture7, lecture8, lecture9,
                lecture10, lecture11, lecture12));

        // 리뷰 생성
        // 우선순위 2
        Review review1a = getReview(lecture1, user1, 5.0, "이해가 잘돼요, ", "내용이 알찹니다!");
        Review review1b = getReview(lecture1, user2, 5.0, "뛰어난 강의력, ", "아주 퍼펙트한 강의 이군요");
        Review review1c = getReview(lecture1, user3, 5.0, "뛰어난 강의력, ", "이 강의를 듣고 개념이 달라졌습니다.");

        // 우선순위 1
        Review review4a = getReview(lecture4, user1, 4.5, "뛰어난 강의력, 이해가 잘돼요", "내용이 알찹니다!");
        Review review4b = getReview(lecture4, user2, 5.0, "뛰어난 강의력, 매우 적극적", "내용이 알찹니다!");
        Review review4c = getReview(lecture4, user3, 5.0, "뛰어난 강의력, ", "내용이 알찹니다!");

        // 우선순위 3
        Review review7a = getReview(lecture7, user1, 4.0, "내용이 자세해요", "내용이 알찹니다!");
        Review review7b = getReview(lecture7, user2, 3.5, "뛰어난 강의력, ", "내용이 알찹니다!");
        Review review7c = getReview(lecture7, user3, 4.0, "내용이 자세해요, ", "내용이 알찹니다!");

        // 우선순위 4
        Review review8 = getReview(lecture8, user1, 3.5, "뛰어난 강의력, 듣기 좋은 목소리", "내용이 알찹니다!");

        reviewRepository.saveAll(List.of(
                review1a, review1b, review1c,
                review4a, review4b, review4c,
                review7a, review7b, review7c,
                review8
        ));

        // when
        List<LectureScopeListReadResponse.DetailLecture> detailLectures =
                lectureQueryRepository.findByBestLectures(null);

        // then
        assertThat(detailLectures)
                .extracting("id", "title")
                .containsExactly(
                        tuple(lecture4.getId(), lecture4.getTitle()),
                        tuple(lecture1.getId(), lecture1.getTitle()),
                        tuple(lecture7.getId(), lecture7.getTitle()),
                        tuple(lecture8.getId(), lecture8.getTitle())
                );
    }

    @DisplayName("강의 Scope 조회 - 강의력 좋은 순")
    @Test
    void findByBestLecturesWithLogin() {
        // given
        // 사용자 생성
        Users user1 = saveUser("test1@test.com", "테스터1","요리,프로그래밍");
        Users user2 = saveUser("test2@test.com", "테스터2");
        Users user3 = saveUser("test3@test.com", "테스터3");

        userRepository.saveAll(List.of(user1, user2, user3));

        // 강의 생성
        Lecture lecture1 = getLecture("웹 디자인의 기초", "프로그래밍");
        Lecture lecture2 = getLecture("자바 프로그래밍 실전 입문", "프로그래밍");
        Lecture lecture3 = getLecture("맛있는 파스타 요리", "요리");
        Lecture lecture4 = getLecture("게임 개발의 첫걸음", "게임");
        Lecture lecture5 = getLecture("그림으로 배우는 일본어", "디자인");
        Lecture lecture6 = getLecture("프론트엔드 개발 마스터 클래스", "프로그래밍");
        Lecture lecture7 = getLecture("컴퓨터 비전 기초", "프로그래밍");
        Lecture lecture8 = getLecture("디지털 아트 워크샵", "디자인");
        Lecture lecture9 = getLecture("영화 속 레시피", "요리");
        Lecture lecture10 = getLecture("모바일 게임 디자인", "디자인");
        Lecture lecture11 = getLecture("너무 쉬운 스프링 개발서", "프로그래밍");
        Lecture lecture12 = getLecture("파이썬 프로그래밍 기초", "프로그래밍");

        lectureRepository.saveAll(List.of(
                lecture1, lecture2, lecture3,
                lecture4, lecture5, lecture6,
                lecture7, lecture8, lecture9,
                lecture10, lecture11, lecture12));

        // 리뷰 생성
        // 우선순위 2
        Review review1a = getReview(lecture1, user1, 5.0, "이해가 잘돼요, ", "내용이 알찹니다!");
        Review review1b = getReview(lecture1, user2, 5.0, "뛰어난 강의력, ", "아주 퍼펙트한 강의 이군요");
        Review review1c = getReview(lecture1, user3, 5.0, "뛰어난 강의력, ", "이 강의를 듣고 개념이 달라졌습니다.");

        // 우선순위 1
        Review review4a = getReview(lecture4, user1, 4.5, "뛰어난 강의력, 이해가 잘돼요", "내용이 알찹니다!");
        Review review4b = getReview(lecture4, user2, 5.0, "뛰어난 강의력, 매우 적극적", "내용이 알찹니다!");
        Review review4c = getReview(lecture4, user3, 5.0, "뛰어난 강의력, ", "내용이 알찹니다!");

        // 우선순위 3
        Review review7a = getReview(lecture7, user1, 4.0, "내용이 자세해요", "내용이 알찹니다!");
        Review review7b = getReview(lecture7, user2, 3.5, "뛰어난 강의력, ", "내용이 알찹니다!");
        Review review7c = getReview(lecture7, user3, 4.0, "내용이 자세해요, ", "내용이 알찹니다!");

        // 우선순위 4
        Review review8 = getReview(lecture8, user1, 3.5, "뛰어난 강의력, 듣기 좋은 목소리", "내용이 알찹니다!");

        reviewRepository.saveAll(List.of(
                review1a, review1b, review1c,
                review4a, review4b, review4c,
                review7a, review7b, review7c,
                review8
        ));

        // when
        List<LectureScopeListReadResponse.DetailLecture> detailLectures =
                lectureQueryRepository.findByBestLectures(user1.getInterests());

        // then
        assertThat(detailLectures)
                .extracting("id", "title")
                .hasSize(2)
                .containsExactly(
                        tuple(lecture1.getId(), lecture1.getTitle()),
                        tuple(lecture7.getId(), lecture7.getTitle())
                );
    }

    private static Review getReview(
            Lecture lecture, Users randomUser, double score, String tags, String content
    ) {
        return Review.builder()
                .user(randomUser)
                .lecture(lecture)
                .score(score)
                .tags(tags)
                .content(content)
                .build();
    }

    private static Lecture getLecture(String randomLectureTitle, String randomMainCategory) {
        return Lecture.builder()
                .title(randomLectureTitle)
                .source("출처")
                .url("URL")
                .price("가격")
                .name("이름")
                .mainCategory(randomMainCategory)
                .subCategory("하위 카테고리")
                .keywords("키워드1, 키워드2, 키워드3")
                .content("강의 내용")
                .imageUrl("이미지 URL")
                .build();
    }

    private Users saveUser(String email, String nickname) {
        return Users.builder()
                .email(email)
                .password("password")
                .imageUrl("이미지 URL ")
                .nickName(nickname)
                .interests("관심사1, 관심사2")
                .authority(Authority.ROLE_USER)
                .build();
    }

    private Users saveUser(String email, String nickname, String interests) {
        return Users.builder()
                .email(email)
                .password("password")
                .imageUrl("이미지 URL ")
                .nickName(nickname)
                .interests(interests)
                .authority(Authority.ROLE_USER)
                .build();
    }

    @RequiredArgsConstructor
    @TestConfiguration
    public static class TestQuerydslConfig {
        private final EntityManager entityManager;

        @Bean
        public JPAQueryFactory queryFactory() {
            return new JPAQueryFactory(entityManager);
        }

        @Bean
        public LectureQueryRepository lectureQueryRepository() {
            return new LectureQueryRepository(queryFactory());
        }
    }
}