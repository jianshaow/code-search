/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2008-3-3 15:09:44                            */
/*==============================================================*/


drop table if exists annotation_decl;

drop table if exists annotation_type_decl;

drop table if exists class_file;

drop table if exists class_type_decl;

drop table if exists constructor_decl;

drop table if exists constructor_modifier;

drop table if exists constructor_param;

drop table if exists declaration;

drop table if exists enum_type_decl;

drop table if exists field_decl;

drop table if exists formal_param_modifier;

drop table if exists formal_parameter;

drop table if exists import_decl;

drop table if exists interface_type_decl;

drop table if exists jar_file;

drop table if exists method_decl;

drop table if exists method_modifier;

drop table if exists method_param;

drop table if exists modifier;

drop table if exists package_decl;

drop table if exists pkg;

drop table if exists type;

drop table if exists type_declaration;

drop table if exists type_modifier;

/*==============================================================*/
/* Table: annotation_decl                                       */
/*==============================================================*/
create table annotation_decl
(
   id                   varchar(32) not null,
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: annotation_type_decl                                  */
/*==============================================================*/
create table annotation_type_decl
(
   id                   varchar(32) not null,
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: class_file                                            */
/*==============================================================*/
create table class_file
(
   id                   varchar(32) not null,
   jar_id               varchar(32),
   name                 varchar(256),
   in_jar_path          varchar(256),
   type                 integer(1),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: class_type_decl                                       */
/*==============================================================*/
create table class_type_decl
(
   id                   varchar(32) not null,
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: constructor_decl                                      */
/*==============================================================*/
create table constructor_decl
(
   id                   varchar(32) not null,
   class_id             varchar(32),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: constructor_modifier                                  */
/*==============================================================*/
create table constructor_modifier
(
   id                   varchar(32) not null,
   constructor_id       varchar(32),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: constructor_param                                     */
/*==============================================================*/
create table constructor_param
(
   id                   varchar(32) not null,
   constructor_id       varchar(32),
   sequence_no          integer,
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: declaration                                           */
/*==============================================================*/
create table declaration
(
   id                   varchar(32) not null,
   file_id              varchar(32),
   identifier           varchar(256),
   sequence_no          integer,
   start_line           integer,
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: enum_type_decl                                        */
/*==============================================================*/
create table enum_type_decl
(
   id                   varchar(32) not null,
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: field_decl                                            */
/*==============================================================*/
create table field_decl
(
   id                   varchar(32) not null,
   type_decl_id         varchar(32),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: formal_param_modifier                                 */
/*==============================================================*/
create table formal_param_modifier
(
   id                   varchar(32) not null,
   formal_param_id      varchar(32),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: formal_parameter                                      */
/*==============================================================*/
create table formal_parameter
(
   id                   varchar(32) not null,
   type_id              varchar(32),
   identifier           varchar(256),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: import_decl                                           */
/*==============================================================*/
create table import_decl
(
   id                   varchar(32) not null,
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: interface_type_decl                                   */
/*==============================================================*/
create table interface_type_decl
(
   id                   varchar(32) not null,
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: jar_file                                              */
/*==============================================================*/
create table jar_file
(
   id                   varchar(32) not null,
   name                 varchar(256),
   url                  varchar(1000),
   spec_title           varchar(256),
   spec_vendor          varchar(256),
   spec_version         varchar(256),
   impl_title           varchar(256),
   impl_vendor          varchar(256),
   impl_version         varchar(256),
   created_by           varchar(256),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: method_decl                                           */
/*==============================================================*/
create table method_decl
(
   id                   varchar(32) not null,
   type_decl_id         varchar(32),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: method_modifier                                       */
/*==============================================================*/
create table method_modifier
(
   id                   varchar(32) not null,
   method_id            varchar(32),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: method_param                                          */
/*==============================================================*/
create table method_param
(
   id                   varchar(32) not null,
   method_id            varchar(32),
   sequence_no          integer,
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: modifier                                              */
/*==============================================================*/
create table modifier
(
   id                   varchar(32) not null,
   identifier           varchar(256),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: package_decl                                          */
/*==============================================================*/
create table package_decl
(
   id                   varchar(32) not null,
   package_id           varchar(32),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: pkg                                                   */
/*==============================================================*/
create table pkg
(
   id                   varchar(32) not null,
   identifier           varchar(256),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: type                                                  */
/*==============================================================*/
create table type
(
   id                   varchar(32) not null,
   package_id           varchar(32),
   identifier           varchar(256),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: type_declaration                                      */
/*==============================================================*/
create table type_declaration
(
   id                   varchar(32) not null,
   type_id              varchar(32),
   primary key (id)
)
type = InnoDB;

/*==============================================================*/
/* Table: type_modifier                                         */
/*==============================================================*/
create table type_modifier
(
   id                   varchar(32) not null,
   type_decl_id         varchar(32),
   primary key (id)
)
type = InnoDB;

alter table annotation_decl add constraint FK_Reference_5 foreign key (id)
      references declaration (id) on delete restrict on update restrict;

alter table annotation_type_decl add constraint FK_Reference_11 foreign key (id)
      references type_declaration (id) on delete restrict on update restrict;

alter table class_file add constraint FK_Reference_12 foreign key (jar_id)
      references jar_file (id) on delete restrict on update restrict;

alter table class_type_decl add constraint FK_Reference_10 foreign key (id)
      references type_declaration (id) on delete restrict on update restrict;

alter table constructor_decl add constraint FK_Reference_13 foreign key (class_id)
      references class_type_decl (id) on delete restrict on update restrict;

alter table constructor_decl add constraint FK_Reference_32 foreign key (id)
      references declaration (id) on delete restrict on update restrict;

alter table constructor_modifier add constraint FK_Reference_26 foreign key (constructor_id)
      references constructor_decl (id) on delete restrict on update restrict;

alter table constructor_modifier add constraint FK_Reference_27 foreign key (id)
      references modifier (id) on delete restrict on update restrict;

alter table constructor_param add constraint FK_Reference_20 foreign key (constructor_id)
      references constructor_decl (id) on delete restrict on update restrict;

alter table constructor_param add constraint FK_Reference_21 foreign key (id)
      references formal_parameter (id) on delete restrict on update restrict;

alter table declaration add constraint FK_Reference_7 foreign key (file_id)
      references class_file (id) on delete restrict on update restrict;

alter table enum_type_decl add constraint FK_Reference_9 foreign key (id)
      references type_declaration (id) on delete restrict on update restrict;

alter table field_decl add constraint FK_Reference_15 foreign key (type_decl_id)
      references type_declaration (id) on delete restrict on update restrict;

alter table field_decl add constraint FK_Reference_2 foreign key (id)
      references declaration (id) on delete restrict on update restrict;

alter table formal_param_modifier add constraint FK_Reference_24 foreign key (formal_param_id)
      references formal_parameter (id) on delete restrict on update restrict;

alter table formal_param_modifier add constraint FK_Reference_25 foreign key (id)
      references modifier (id) on delete restrict on update restrict;

alter table formal_parameter add constraint FK_Reference_19 foreign key (type_id)
      references type (id) on delete restrict on update restrict;

alter table import_decl add constraint FK_Reference_1 foreign key (id)
      references declaration (id) on delete restrict on update restrict;

alter table interface_type_decl add constraint FK_Reference_8 foreign key (id)
      references type_declaration (id) on delete restrict on update restrict;

alter table method_decl add constraint FK_Reference_14 foreign key (type_decl_id)
      references type_declaration (id) on delete restrict on update restrict;

alter table method_decl add constraint FK_Reference_4 foreign key (id)
      references declaration (id) on delete restrict on update restrict;

alter table method_modifier add constraint FK_Reference_30 foreign key (method_id)
      references method_decl (id) on delete restrict on update restrict;

alter table method_modifier add constraint FK_Reference_31 foreign key (id)
      references modifier (id) on delete restrict on update restrict;

alter table method_param add constraint FK_Reference_22 foreign key (method_id)
      references method_decl (id) on delete restrict on update restrict;

alter table method_param add constraint FK_Reference_23 foreign key (id)
      references formal_parameter (id) on delete restrict on update restrict;

alter table package_decl add constraint FK_Reference_33 foreign key (package_id)
      references pkg (id) on delete restrict on update restrict;

alter table package_decl add constraint FK_Reference_34 foreign key (id)
      references declaration (id) on delete restrict on update restrict;

alter table type add constraint FK_Reference_16 foreign key (package_id)
      references pkg (id) on delete restrict on update restrict;

alter table type_declaration add constraint FK_Reference_18 foreign key (type_id)
      references type (id) on delete restrict on update restrict;

alter table type_declaration add constraint FK_Reference_6 foreign key (id)
      references declaration (id) on delete restrict on update restrict;

alter table type_modifier add constraint FK_Reference_28 foreign key (type_decl_id)
      references type_declaration (id) on delete restrict on update restrict;

alter table type_modifier add constraint FK_Reference_29 foreign key (id)
      references modifier (id) on delete restrict on update restrict;

