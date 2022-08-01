## Muvirec(Music Video Recommendation)
# 😀프로젝트 설명
- 유튜브 링크를 통해 뮤직비디오를 공유하며 댓글과 평점을 통해 소통할 수 있는 커뮤니티 웹 사이트입니다.

# ⚙ 개발 환경
- 운영체제 : Windows 10
- IDE : STS4
- JDK 버전 : JDK 11
- Spring Boot 버전 : 2.7.1
- 데이터 베이스 : MySQL
- 빌드 툴 : Maven
- 형상 관리 : GitHub

# 📢의존성
- Spring Boot DevTools
- Spring Web
- Lombok
- Spring Data JPA
- MySQL
- Spring Security
- JSTL
- Jasper

# 💻구현

### 1.메인페이지
<img src="https://user-images.githubusercontent.com/84663172/182158908-2d5550d4-642b-4929-b4d0-bbc84479bdd1.png" width="700"/>

- 메인 페이지 대표 동영상은 조회수가 가장 높은 것으로 선정
- 포스트 검색,페이징 동영상 지속적인 재생을 위해 비동기식 화면 전환


### 2.로그인,회원가입,회원정보변경

<img src="https://user-images.githubusercontent.com/84663172/182145487-b30e6354-cb51-49ef-81ef-f982da3abbd2.png"/>

#### 일반 로그인&회원가입(스프링 시큐리티)

  - 로그인 시 BCryptPasswordEncoder를 통해 유저의 정보를 인증하고 header.jsp에 인증 절차를 거친 유저의 정보를 인가(세션)
  - 회원가입 시 비밀번호 BCryptPasswordEncoder 암호화 후 DB 저장


SecurityConfig.java

```
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//PrincipalDetailService에서 조회한 비밀번호와 유저가 입력한 비밀번호를 암호화하여 비교
  auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
}//configure
```

#### 회원수정

<img src="https://user-images.githubusercontent.com/84663172/182146768-96d7004b-79f4-499d-a4a4-53931229602f.png"/>

  - onkeyup 이벤트로 아이디 중복체크, 포커스 아웃 시 비밀번호 확인 체크(Ajax)
  - 영속화된 데이터 변경 감지 후 update 수행
  - 변경된 정보로 AuthenticationToken을 받아 SecurityContextHolder에 등록하여 세션 강제 갱신
  

#### 카카오 로그인&회원가입 
  - Oauth Client를 사용하지 않고 직접 구현
  - Access Token으로 사용자 정보 획득 후 로그인 진행
    - 기존 회원 : 로그인 & 세션
    - 신규 회원 : 회원 정보 생성 후 로그인 & 세션
  - 회원수정 : 세션의 oauth 컬럼을 비교하여 카카오 로그인이면 비활성화
UserService.java
```   
//Access Token 요청에 필요한 바디값
MultiValueMap<String, String> tkParams = new LinkedMultiValueMap<>();
tkParams.add("grant_type", "authorization_code");
tkParams.add("client_id", client); //client_id ignore 
tkParams.add("redirect_uri", "http://호스트주소/auth/kakao/callback");
tkParams.add("code", code);
    
//Access Token 응답 후 사용자 정보 요청에 필요한 바디값
HttpHeaders pfHeader = new HttpHeaders();
pfHeader.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
pfHeader.add("Authorization", "Bearer "+oauthToken.getAccess_token());//엑세스 토큰을 보냄
```
### 3.포스트

#### 포스트 상세 페이지
![localhost_8080_posts_17](https://user-images.githubusercontent.com/84663172/182150758-bb09fa58-7de9-4d16-839f-0718b2f2c4bb.png)

- 삭제,수정
  - 세션 값 비교 관리자,작성자와 일치하면 버튼 활성화
  - 삭제 시 delete 수행 (cascade)
  - 수정 시 영속화된 데이터 변경 감지 후 update 수행

- 댓글
  - 세션 값 비교 관리자,작성자와 일치하면 삭제 버튼 활성화
  - 댓글 등록 시 동영상 재생이 지속될 수 있도록 비동기식으로 댓글 갱신

- 평점
  - 세션을 통해 Rating을 조회하여 존재하면 등록한 평점을 보여주고 존재하지 않으면 평점 등록 가능
  - 모든 평점의 평균값을 구해 상세 페이지 평점 항목에 표시
<img width="363" alt="image" src="https://user-images.githubusercontent.com/84663172/182155449-f2431419-5b1d-4110-a363-aa009f6589bb.png">

#### 포스트 작성
![localhost_8080_posts_add-form](https://user-images.githubusercontent.com/84663172/182157212-3d06a0d4-2705-48c2-a589-1f06cb539933.png)

- 정규식을 통해 유튜브 url 유효성과 videoId를 추출하여 DB 저장
```
var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
var match = url.match(regExp);
if (match && match[7].length == 11) {
	return match[7];
} else {
	alert("유효하지 않은 링크입니다.");
}//end else
```
### 내가 작성한 포스트
![localhost_8080_users_my-post (1)](https://user-images.githubusercontent.com/84663172/182157955-591edbf0-a704-48fe-b50d-2c975bfdf9e2.png)

- Pageable을 사용하여 페이징 
- 클릭 시 상세페이지 이동


