USE ry;
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('?????????????????????????', '3', '1', '/xueguan/project', 'C', '0', 'xueguan:project:view', '#', 'admin', sysdate(), '', null, '?????????????????????????菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('?????????????????????????查询', @parentId, '1',  '#',  'F', '0', 'xueguan:project:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('?????????????????????????新增', @parentId, '2',  '#',  'F', '0', 'xueguan:project:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('?????????????????????????修改', @parentId, '3',  '#',  'F', '0', 'xueguan:project:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('?????????????????????????删除', @parentId, '4',  '#',  'F', '0', 'xueguan:project:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('?????????????????????????导出', @parentId, '5',  '#',  'F', '0', 'xueguan:project:export',       '#', 'admin', sysdate(), '', null, '');
