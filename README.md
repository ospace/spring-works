# spring-works
스프링 작업물들

## 스프링 시큐리티 예제
* spring-security-01
  * 간단한 폼인증
  * 프로젝트 경로: https://github.com/ospace/spring-works/tree/main/spring-security-01
  
* spring-security-02
  * 사용자 접근제어
  * 프로젝트 경로: https://github.com/ospace/spring-works/tree/main/spring-security-02
 
* spring-security-03
  * LDAP 인증
  * 프로젝트 경로: https://github.com/ospace/spring-works/tree/main/spring-security-03

* spring-security-04
  * OAuth2 인증
  * 프로젝트 경로: https://github.com/ospace/spring-works/tree/main/spring-security-04

## 그래들 프로젝트 예제
멀티 프로젝트 예제
* gradle1-multi
* gradle2-multi

## simple_security3
Form 기반을 json 데이터로 API 인증 제공

단, login 호출시에는 데이터형은 form형태로 보내야함.(이게 전부인데 ㅡㅡ;;) 그외의 응답은 성공/실패는 json 데이터 형으로 처리됨. 그렇기 때문에 로그인 응답에 대해 클라이언트 측에서 처리 가능.
