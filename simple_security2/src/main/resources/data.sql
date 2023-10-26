<<<<<<< HEAD
-- password: 1111
INSERT INTO `users`
  (`username`, `password`, `enabled`)
VALUES
  ('master', '$2a$10$.qPMRoGUXR0vkDeWVBDZ2OGhcRW5rYDzulUjMMXKZZ.POxXViu7hi', TRUE),
  ('admin', '$2a$10$.qPMRoGUXR0vkDeWVBDZ2OGhcRW5rYDzulUjMMXKZZ.POxXViu7hi', TRUE),
  ('user', '$2a$10$.qPMRoGUXR0vkDeWVBDZ2OGhcRW5rYDzulUjMMXKZZ.POxXViu7hi', TRUE);

INSERT INTO `authorities`
  (`username`, `authority`)
VALUES
  ('admin', 'ROLE_ADMIN'),
  ('user', 'ROLE_USER');

INSERT INTO `groups`(`group_name`)
VALUES ('administrator');

INSERT INTO `group_authorities`
  (`group_id`, `authority`)
VALUES
  (1, 'ROLE_ADMIN'),
  (1, 'ROLE_USER');

INSERT INTO `group_members`
  (`id`, `username`, `group_id`)
VALUES
  (1, 'master', 1);

INSERT INTO `acl_sid`
  (`id`, `principal`, `sid`)
VALUES
  (1, 1, 'master'),
  (2, 1, 'admin'),
  (3, 1, 'user'),
  (4, 0, 'ROLE_ADMIN');

INSERT INTO `acl_class`
  (`id`, `class`)
VALUES
  (1, 'com.tistory.ospace.simplesecurity2.entity.User');

-- 
INSERT INTO `acl_object_identity`
  (`id`, `object_id_class`, `object_id_identity`, `parent_object`, `owner_sid`, `entries_inheriting`) 
VALUES
  (1,    1,                 1,                    NULL,            1,           0),
  (2,    1,                 2,                    NULL,            2,           0),
  (3,    1,                 3,                    NULL,            3,           0);

INSERT INTO `acl_entry` 
  (`id`, `acl_object_identity`, `ace_order`, `sid`, `mask`, `granting`, `audit_success`, `audit_failure`) 
VALUES
  (1,    1,                     1,           1,     1,      1,          1,               1),
  (2,    1,                     2,           1,     2,      1,          1,               1),
  (3, 1, 3, 3,  1, 1, 1, 1),
  (4, 2, 1, 2,  1, 1, 1, 1),
  (5, 2, 2, 3,  1, 1, 1, 1),
  (6, 3, 1, 3,  1, 1, 1, 1),
  (7, 3, 2, 3,  2, 1, 1, 1),  
  (8, 2, 3, 1,  1, 1, 0, 1),
  (9, 3, 3, 1,  1, 1, 0, 1);
  
--INSERT INTO notice_message(id,content) VALUES
--  (1,'First Level Message'),
--  (2,'Second Level Message'),
--  (3,'Third Level Message');
=======
-- 
INSERT INTO acl_sid (id, principal, sid) VALUES
  (1, 1, 'admin'),
  (2, 1, 'user'),
  (3, 0, 'ROLE_MASTER');

-- 
INSERT INTO acl_class (id, class) VALUES
  (1, 'com.tistory.ospace.simplesecurity2.entity.Post');

-- (1, 2,  NULL, 3, 0)
-- 1: object_id_class는 acl_class의 1번인 Post기준
-- 2: Post 객체에서 기본키 id가 2번 객체 대상
-- NULL: 부모객체는 없음(테이블내에서 부모객체 지정)
-- 3: 소유자는 acl_sid에서 3번인 ROLE_MASTER
-- 0: 상속구조는 없음
INSERT INTO acl_object_identity
  (id, object_id_class, object_id_identity, 
  parent_object, owner_sid, entries_inheriting) 
VALUES
  (1, 1, 1,  NULL, 3, 0),
  (2, 1, 2,  NULL, 3, 0),
  (3, 1, 3,  NULL, 3, 0),
  (4, 1, 4,  NULL, 3, 0);

-- https://docs.spring.io/spring-security/site/docs/3.0.x/reference/domain-acls.html
-- https://medium.com/sfl-newsroom/spring-security-expression-based-access-control-56411a60ab3b
-- https://hodolman.com/19
-- mask: permission
-- bit 0: read, bit 1: write, bit 2: create, bit 3: delete
-- (1, 2, 3,  4, 1, 0, 1)
-- 1: acl_object_identity 1번 대상을 지정
-- 2: 적용 순서 두번째로 적용
-- sid: ACE(Access Control Entries)에서 변경할 수 있는 객체 주체로 acl_sid 3번인 ROLE_MASTER
-- 4: 권한은 생성권한 부여(1: 읽기권한, 2: 쓰기 권한, 4: 생성 권한, 8: 삭제권한)
-- 1: 1은 허용, 0은 거부
-- 0: 성공 로깅 비활성화
-- 1: 실패 로깅 활성화
-- Sample
-- (x, x, x, 1, 1, 1, 1) : 읽기 허용
-- (x, x, x, 3, 1, 1, 1) : 읽기, 쓰기 허용
-- (x, x, x, 7, 1, 1, 1) : 읽기, 쓰기, 생성 허용
-- (x, x, x, 11, 1, 1, 1) : 읽기, 쓰기, 삭제 허용
-- (x, x, x, 19, 1, 1, 1) : 읽기, 쓰기, 생성, 삭제 허용
INSERT INTO acl_entry 
  (acl_object_identity, ace_order, sid, mask,
   granting, audit_success, audit_failure) 
VALUES
  (1, 1, 3, 1,  1, 1, 1),
  (1, 2, 1, 1,  1, 1, 1),
  (1, 3, 1, 2,  1, 1, 1),
  (2, 1, 1, 1,  1, 1, 1),
  (2, 2, 3, 1,  1, 1, 1),
  (3, 1, 1, 1,  1, 1, 1),
  (3, 2, 2, 1,  1, 1, 1),
  (3, 3, 2, 2,  1, 1, 1),
  (3, 4, 3, 1,  1, 1, 1),
  (4, 1, 1, 1,  1, 1, 1),
  (4, 2, 2, 1,  1, 1, 1),
  (4, 3, 3, 1,  1, 1, 1)
  --(1, 1, 1, 1,  1, 1, 1),
  
  --(1, 3, 3, 1,  1, 1, 1),
  --(2, 1, 2, 1,  1, 1, 1),
  --(2, 2, 3, 1,  1, 1, 1),
  --(2, 3, 1, 1,  1, 0, 1),
  --(3, 1, 3, 1,  1, 1, 1),
  --(3, 2, 3, 2,  1, 1, 1),
  --(3, 3, 1, 1,  1, 0, 1)
  ;

-- 
INSERT INTO post(title, content) VALUES
  ('1st', 'First Post'),
  ('2nd', 'Second Post'),
  ('3rd', 'Third Post'),
  ('4rd', 'Fourth Post');
>>>>>>> testonly
