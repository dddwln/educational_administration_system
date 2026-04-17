-- 学管平台：角色、菜单、业务表初始化
-- 适配 RuoYi-Vue 当前 sys_role/sys_menu/sys_role_menu 结构。

-- 1. 角色
insert into sys_role(role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
select '教师', 'teacher', 3, '3', 1, 1, '0', '0', 'admin', sysdate(), '教师角色'
where not exists (select 1 from sys_role where role_key = 'teacher');

insert into sys_role(role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
select '学生', 'student', 4, '3', 1, 1, '0', '0', 'admin', sysdate(), '学生角色'
where not exists (select 1 from sys_role where role_key = 'student');

-- 2. 菜单。menu_id 从 2000 开始，避开若依初始菜单。
insert ignore into sys_menu values(2000, '学管平台', 0, 1, 'xueguan', null, '', '', 1, 0, 'M', '0', '0', '', 'education', 'admin', sysdate(), '', null, '学管平台目录');
insert ignore into sys_menu values(2001, '材料上传', 2000, 1, 'material', 'xueguan/material/index', '', '', 1, 0, 'C', '0', '0', 'xg:material:list', 'upload', 'admin', sysdate(), '', null, '学生/教师材料上传');
insert ignore into sys_menu values(2002, '材料审核', 2000, 2, 'audit', 'xueguan/material/index', '{"audit":"1"}', '', 1, 0, 'C', '0', '0', 'xg:material:audit', 'validCode', 'admin', sysdate(), '', null, '管理员材料审核');
insert ignore into sys_menu values(2003, '数据大屏', 2000, 3, 'dashboard', 'xueguan/dashboard/index', '', '', 1, 0, 'C', '0', '0', 'xg:dashboard:view', 'dashboard', 'admin', sysdate(), '', null, '学管数据大屏');
insert ignore into sys_menu values(2100, '材料查询', 2001, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'xg:material:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(2101, '材料新增', 2001, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'xg:material:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(2102, '材料修改', 2001, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'xg:material:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(2103, '材料删除', 2001, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'xg:material:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(2104, '审核操作', 2002, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'xg:material:audit', '#', 'admin', sysdate(), '', null, '');

-- 3. 菜单授权
insert into sys_role_menu(role_id, menu_id)
select r.role_id, m.menu_id
from sys_role r join sys_menu m
where r.role_key = 'teacher'
  and m.menu_id in (2000, 2001, 2003, 2100, 2101, 2102, 2103)
  and not exists (select 1 from sys_role_menu rm where rm.role_id = r.role_id and rm.menu_id = m.menu_id);

insert into sys_role_menu(role_id, menu_id)
select r.role_id, m.menu_id
from sys_role r join sys_menu m
where r.role_key = 'student'
  and m.menu_id in (2000, 2001, 2003, 2100, 2101, 2102, 2103)
  and not exists (select 1 from sys_role_menu rm where rm.role_id = r.role_id and rm.menu_id = m.menu_id);

-- 管理员已拥有 admin 超级角色；如果你使用非超级管理员账号，可给该角色加入 2000/2002/2003/2104。

-- 4. 隐藏无关入口。角色未授权时本来不可见，这里再全局隐藏若依官网、系统工具。
update sys_menu set visible = '1' where menu_id in (3, 4, 115, 116, 117);

-- 5. 文档中的基础表
create table if not exists xg_faculty_info (
  faculty_id varchar(20) primary key comment '教工号',
  user_id bigint(20) null comment '关联 sys_user.user_id',
  name varchar(50) not null comment '姓名',
  gender varchar(10) null comment '性别',
  birth_date date null comment '出生年月',
  department varchar(100) null comment '部门/学院',
  id_card varchar(18) null unique comment '身份证号',
  highest_degree varchar(50) null comment '最高学位',
  highest_education varchar(50) null comment '最高学历',
  graduated_from varchar(100) null comment '毕业院校',
  major varchar(100) null comment '所学专业',
  entry_date date null comment '入职时间',
  title varchar(50) null comment '专业技术职务',
  is_double_qualified tinyint(1) default 0 comment '是否双师型教师',
  is_teaching_undergrad tinyint(1) default 1 comment '是否为本科生上课',
  talent_title varchar(100) null comment '人才称号',
  email varchar(100) null comment '邮箱',
  phone varchar(20) null comment '手机号',
  maternal_info text null comment '妇幼及子女信息备注',
  create_by varchar(64) default '',
  create_time datetime,
  update_by varchar(64) default '',
  update_time datetime
) engine=innodb default charset=utf8mb4 comment='教职工基本信息表';

create table if not exists xg_faculty_education (
  history_id bigint(20) primary key auto_increment comment '学习经历ID',
  faculty_id varchar(20) not null comment '教工号',
  school_name varchar(100) not null comment '毕业院校',
  major varchar(100) null comment '所学专业',
  start_date date null comment '入学日期',
  end_date date null comment '毕业日期',
  degree_obtained varchar(50) null comment '获得学位',
  cert_image_path varchar(255) null comment '毕业证/学位证扫描件路径',
  create_by varchar(64) default '',
  create_time datetime,
  index idx_faculty_id(faculty_id)
) engine=innodb default charset=utf8mb4 comment='教职工学习经历表';

create table if not exists xg_student_profile (
  student_id bigint(20) primary key auto_increment,
  user_id bigint(20) null comment '关联 sys_user.user_id',
  student_no varchar(20) not null comment '学号',
  student_name varchar(50) not null comment '姓名',
  department varchar(100) null comment '学院/专业',
  grade varchar(20) null comment '年级',
  phone varchar(20) null,
  email varchar(100) null,
  profile_text text null comment '个人信息/简介',
  create_by varchar(64) default '',
  create_time datetime,
  update_by varchar(64) default '',
  update_time datetime,
  unique key uk_student_no(student_no)
) engine=innodb default charset=utf8mb4 comment='学生个人信息表';

create table if not exists xg_competition_award (
  award_id bigint(20) primary key auto_increment,
  owner_user_id bigint(20) null comment '填报人',
  student_no varchar(20) not null comment '获奖学生学号',
  student_name varchar(50) not null comment '获奖学生姓名',
  comp_name varchar(200) not null comment '竞赛名称',
  award_date char(6) not null comment '获奖时间YYYYMM',
  award_level varchar(50) null comment '奖项等级',
  comp_scope varchar(50) null comment '赛级',
  team_members text null comment '团队成员',
  mentor_name varchar(50) null comment '指导教师',
  official_url varchar(500) null comment '公示网址或批文链接',
  audit_status char(1) default '0' comment '0待审 1通过 2驳回',
  create_by varchar(64) default '',
  create_time datetime,
  update_by varchar(64) default '',
  update_time datetime
) engine=innodb default charset=utf8mb4 comment='竞赛获奖统计表';

create table if not exists xg_research_paper (
  paper_id bigint(20) primary key auto_increment,
  owner_user_id bigint(20) null comment '填报教师',
  title varchar(500) not null comment '论文题目',
  pub_outlet varchar(200) not null comment '期刊/会议',
  paper_type varchar(50) not null comment 'SCI/CCF/卓越期刊',
  author_rank text null comment '作者排序及单位',
  is_corresponding tinyint(1) default 0 comment '是否通讯作者',
  is_first_unit tinyint(1) default 0 comment '是否第一单位',
  paper_score decimal(10,3) null comment '论文积分/影响因子',
  publication_date date null comment '发表日期',
  doi varchar(100) null comment 'DOI',
  remarks text null comment '备注',
  audit_status char(1) default '0' comment '0待审 1通过 2驳回',
  create_by varchar(64) default '',
  create_time datetime,
  update_by varchar(64) default '',
  update_time datetime
) engine=innodb default charset=utf8mb4 comment='科研论文信息表';

create table if not exists xg_research_project (
  project_no varchar(50) primary key comment '项目编号',
  project_name varchar(500) not null comment '项目名称',
  source varchar(200) null comment '项目来源',
  leader_user_id bigint(20) null comment '负责人用户ID',
  leader_name varchar(50) not null comment '负责人',
  contract_date date null comment '合同签订日期',
  end_date date null comment '合同截止日期',
  total_amount decimal(15,2) default 0 comment '合同总金额',
  actual_amount decimal(15,2) default 0 comment '已到账金额',
  create_by varchar(64) default '',
  create_time datetime,
  update_by varchar(64) default '',
  update_time datetime
) engine=innodb default charset=utf8mb4 comment='科研项目管理表';

create table if not exists xg_college_file (
  file_id bigint(20) primary key auto_increment,
  file_name varchar(200) not null comment '文件名称',
  file_no varchar(50) null comment '发文字号',
  file_type varchar(50) null comment '制度类型',
  publish_date date null comment '出台日期',
  status varchar(20) null comment '废改立情况',
  file_path varchar(255) null comment '关联文件路径',
  create_by varchar(64) default '',
  create_time datetime,
  update_by varchar(64) default '',
  update_time datetime
) engine=innodb default charset=utf8mb4 comment='学院文件记录表';

-- 6. 统一上传材料和审核流
create table if not exists xg_material (
  material_id bigint(20) primary key auto_increment,
  owner_user_id bigint(20) not null comment '上传人用户ID',
  owner_role varchar(20) not null comment 'teacher/student',
  dept_id bigint(20) null comment '上传人部门ID',
  material_category varchar(30) not null comment '材料分类编码',
  material_type varchar(50) not null comment '材料分类名称',
  title varchar(200) not null comment '标题',
  project_no varchar(50) null comment '关联项目编号',
  related_biz_id varchar(64) null comment '关联业务ID',
  material_data text null comment '材料具体项JSON',
  amount decimal(15,2) null comment '奖励金额/项目金额等',
  material_date date null comment '获得时间/入职日期/考核日期等',
  file_url varchar(500) null comment '文件访问URL',
  file_name varchar(255) null comment '文件路径',
  original_filename varchar(255) null comment '原始文件名',
  audit_status char(1) default '0' comment '0待审核 1通过 2驳回 3草稿',
  audit_by varchar(64) null comment '审核人',
  audit_time datetime null comment '审核时间',
  reject_reason varchar(500) null comment '驳回原因',
  remarks text null comment '备注',
  create_by varchar(64) default '',
  create_time datetime,
  update_by varchar(64) default '',
  update_time datetime,
  index idx_owner_user(owner_user_id),
  index idx_audit_status(audit_status),
  index idx_category(material_category),
  index idx_create_time(create_time)
) engine=innodb default charset=utf8mb4 comment='上传材料表';

create table if not exists xg_audit_record (
  audit_id bigint(20) primary key auto_increment,
  material_id bigint(20) not null,
  audit_action varchar(20) not null comment 'submit/pass/reject/archive',
  audit_status char(1) not null comment '审核后状态',
  audit_opinion varchar(500) null comment '审核意见',
  audit_by varchar(64) not null,
  audit_time datetime not null,
  index idx_material_id(material_id)
) engine=innodb default charset=utf8mb4 comment='材料审核记录表';
