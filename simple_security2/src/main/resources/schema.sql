<<<<<<< HEAD
-- https://www.erdcloud.com/d/HdY7jQ7XFxs4MQBwv

--DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS users
-- CREATE TABLE users
(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR2(50) NOT NULL UNIQUE,
  password VARCHAR2(128) NOT NULL,
  enabled  BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS authorities
-- CREATE TABLE authorities
(
  username  VARCHAR2(255) NOT NULL,
  authority VARCHAR2(255) NOT NULL,
--  UNIQUE KEY ix_auth_username (username, authority),
  CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);

-- CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

CREATE TABLE IF NOT EXISTS groups
-- CREATE TABLE groups
(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  group_name VARCHAR2(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS group_authorities
-- CREATE TABLE group_authorities
(
    group_id BIGINT UNSIGNED NOT NULL,
    authority VARCHAR2(64) NOT NULL,
    CONSTRAINT fk_group_authorities_group FOREIGN KEY(group_id) REFERENCES groups(id)
);

CREATE TABLE IF NOT EXISTS group_members
-- CREATE TABLE group_members
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    group_id BIGINT NOT NULL,
    CONSTRAINT fk_group_members_group FOREIGN KEY(group_id) REFERENCES groups(id)
);

CREATE TABLE IF NOT EXISTS persistent_logins
-- CREATE TABLE persistent_logins
(
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) PRIMARY KEY,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL
);
=======
--https://www.baeldung.com/spring-security-acl
>>>>>>> testonly

-- ACL에 사용할 모든 사용자 혹은 권한을 sid기준으로 목록화
CREATE TABLE IF NOT EXISTS acl_sid
-- CREATE TABLE acl_sid
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    principal BOOLEAN NOT NULL,
    sid VARCHAR(128) NOT NULL,
    UNIQUE KEY unique_acl_sid (sid, principal)
) ENGINE=InnoDB;

-- ACL에서 처리할 대상 클래스 정보
-- 클래스 정보가 등록되어 있어야 해당 클래스에 대해 ACL 적용됨
CREATE TABLE IF NOT EXISTS acl_class
-- CREATE TABLE acl_class
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    class VARCHAR(128) NOT NULL,
    UNIQUE KEY uk_acl_class (class)
) ENGINE=InnoDB;

-- acl_class을 참조하는 object_id_class
-- 실제 클래스가 수행된 테이블에 대한 참조키인 object_id_identity
-- 각 오브젝트 책임자인 owner_sid 컬럼
-- 오브젝트 중첩구조를 표현하는 parent_object와 entities_inheriting으로 구성됨
CREATE TABLE IF NOT EXISTS acl_object_identity
-- CREATE TABLE acl_object_identity
-- owner_sid: Access Control Entries에 변경할 수 있는 객체 주체
--    Alice가 owner_id인 BANK_ACCOUNT에 대해 Bob이 ACE에 추가할 때에
--    권한전략에 의해 owner_id에 기반해서 변경하려는게 Alice인지 여부 확인 
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    object_id_class BIGINT UNSIGNED NOT NULL,
    object_id_identity BIGINT NOT NULL,
    parent_object BIGINT UNSIGNED,
    owner_sid BIGINT UNSIGNED,
    entries_inheriting BOOLEAN NOT NULL,
    UNIQUE KEY uk_acl_object_identity (object_id_class, object_id_identity),
    CONSTRAINT fk_acl_object_identity_parent FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_object_identity_class FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
    CONSTRAINT fk_acl_object_identity_owner FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB;

-- 사용자와 보안관련 테이블을 참조해서 사용자가 각 오브젝트에 대한 create, update, delete, read을 엑세스 제어
-- acl_object_identity는 acl_object_identity 테이블 참조키, sid는 수행할 유저의 side를 참조키로 연결.
-- mask는 별도로 관리된 비트 숫자로 등록(bit 0: read, bit 1: write, bit 2: create, bit 3: delete)
-- granting이 true이면 mask로 지정된 허가를 수행, false이면 취소하고 블록킹
-- ace_order는 접근권한 엔트리의 우선순위 결정
-- https://docs.spring.io/spring-security/site/docs/3.0.x/reference/domain-acls.html
CREATE TABLE IF NOT EXISTS acl_entry
-- CREATE TABLE acl_entry

(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    acl_object_identity BIGINT UNSIGNED NOT NULL,
    ace_order INTEGER NOT NULL,
    sid BIGINT UNSIGNED NOT NULL,
    mask INTEGER UNSIGNED NOT NULL,
    granting BOOLEAN NOT NULL,
    audit_success BOOLEAN NOT NULL,
    audit_failure BOOLEAN NOT NULL,
    UNIQUE KEY unique_acl_entry (acl_object_identity, ace_order),
    CONSTRAINT fk_acl_entry_object FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_entry_acl FOREIGN KEY (sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB;

<<<<<<< HEAD
--CREATE TABLE IF NOT EXISTS notice_message
-- CREATE TABLE notice_message
--(
--    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
--    content VARCHAR(100) NOT NULL
--) ENGINE=InnoDB;
=======

CREATE TABLE IF NOT EXISTS post
-- CREATE TABLE post
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content VARCHAR(255) NOT NULL
) ENGINE=InnoDB;
>>>>>>> testonly
