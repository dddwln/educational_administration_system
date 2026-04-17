-- 学管平台材料上传：动态具体项字段迁移
alter table xg_material add column material_data text null comment '材料具体项JSON' after related_biz_id;
alter table xg_material add column amount decimal(15,2) null comment '奖励金额/项目金额等' after material_data;
alter table xg_material add column material_date date null comment '获得时间/入职日期/考核日期等' after amount;

