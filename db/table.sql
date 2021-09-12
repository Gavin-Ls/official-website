CREATE DATABASE IF NOT EXISTS official_website DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

drop table if exists official_website.leave_message;
CREATE TABLE leave_message
(
  id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  session_id varchar(50) NOT NULL COMMENT '会话',
  phone varchar(20) COMMENT '电话',
  content text COMMENT '聊天内容',
  state int(3) DEFAULT 1 NOT NULL COMMENT '状态 1-管理员待回复 2-用户待回复',
  create_time datetime DEFAULT current_timestamp NOT NULL COMMENT '创建时间',
  update_time datetime DEFAULT current_timestamp NOT NULL COMMENT '更新时间'
);
CREATE INDEX leave_message_phone_index ON leave_message (phone);
ALTER TABLE leave_message COMMENT = '留言';

drop table if exists official_website.chat_message;
CREATE TABLE chat_message
(
  id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  session_id varchar(50) NOT NULL COMMENT '会话',
  content text COMMENT '聊天内容',
  create_time datetime DEFAULT current_timestamp NOT NULL COMMENT '创建时间',
  update_time datetime DEFAULT current_timestamp NOT NULL COMMENT '更新时间'
);
ALTER TABLE chat_message COMMENT = '聊天';