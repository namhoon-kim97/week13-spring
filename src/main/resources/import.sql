-- user 테이블에 role 컬럼 길이 수정
ALTER TABLE `user` MODIFY COLUMN `role` VARCHAR(20) NOT NULL;

-- 사용자 데이터 삽입
INSERT INTO `user` (username, password, role) VALUES ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'ADMIN');
INSERT INTO `user` (username, password, role) VALUES ('user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'USER');
