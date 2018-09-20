/*==============================================================================================*/
/* Quartz database tables creation script for Sybase ASE 12.5 */
/* Written by Pertti Laiho (email: pertti.laiho@deio.net), 9th May 2003 */
/* */
/* Compatible with Quartz version 1.1.2 */
/* */
/* Sybase ASE works ok with the MSSQL delegate class. That means in your Quartz properties */
/* file, you'll need to set: */
/* org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.MSSQLDelegate */
/*==============================================================================================*/

/*==============================================================================*/
/* Create tables: */
/*==============================================================================*/

create table QRTZ_CALENDARS (
CALENDAR_NAME varchar(80) not null,
CALENDAR image not null
)
go

create table QRTZ_CRON_TRIGGERS (
TRIGGER_NAME varchar(80) not null,
TRIGGER_GROUP varchar(80) not null,
CRON_EXPRESSION varchar(80) not null,
TIME_ZONE_ID varchar(80) null,
)
go

create table QRTZ_PAUSED_TRIGGER_GRPS (
TRIGGER_GROUP  varchar(80) not null, 
)
go

create table QRTZ_FIRED_TRIGGERS(
ENTRY_ID varchar(95) not null,
TRIGGER_NAME varchar(80) not null,
TRIGGER_GROUP varchar(80) not null,
IS_VOLATILE bit not null,
INSTANCE_NAME varchar(80) not null,
FIRED_TIME numeric(13,0) not null,
STATE varchar(16) not null,
JOB_NAME varchar(80) null,
JOB_GROUP varchar(80) null,
IS_STATEFUL bit not null,
REQUESTS_RECOVERY bit not null,
)
go

create table QRTZ_SCHEDULER_STATE (
INSTANCE_NAME varchar(80) not null,
LAST_CHECKIN_TIME numeric(13,0) not null,
CHECKIN_INTERVAL numeric(13,0) not null,
RECOVERER varchar(80) null,
)
go

create table QRTZ_LOCKS (
LOCK_NAME  varchar(40) not null, 
)
go

insert into QRTZ_LOCKS values('TRIGGER_ACCESS')
go
insert into QRTZ_LOCKS values('JOB_ACCESS')
go
insert into QRTZ_LOCKS values('CALENDAR_ACCESS')
go
insert into QRTZ_LOCKS values('STATE_ACCESS')
go


create table QRTZ_JOB_DETAILS (
JOB_NAME varchar(80) not null,
JOB_GROUP varchar(80) not null,
DESCRIPTION varchar(120) null,
JOB_CLASS_NAME varchar(128) not null,
IS_DURABLE bit not null,
IS_VOLATILE bit not null,
IS_STATEFUL bit not null,
REQUESTS_RECOVERY bit not null,
JOB_DATA image null
)
go

create table QRTZ_JOB_LISTENERS (
JOB_NAME varchar(80) not null,
JOB_GROUP varchar(80) not null,
JOB_LISTENER varchar(80) not null
)
go

create table QRTZ_SIMPLE_TRIGGERS (
TRIGGER_NAME varchar(80) not null,
TRIGGER_GROUP varchar(80) not null,
REPEAT_COUNT numeric(13,0) not null,
REPEAT_INTERVAL numeric(13,0) not null,
TIMES_TRIGGERED numeric(13,0) not null
)
go

create table QRTZ_BLOB_TRIGGERS (
TRIGGER_NAME varchar(80) not null,
TRIGGER_GROUP varchar(80) not null,
BLOB_DATA image null
)
go

create table QRTZ_TRIGGER_LISTENERS (
TRIGGER_NAME varchar(80) not null,
TRIGGER_GROUP varchar(80) not null,
TRIGGER_LISTENER varchar(80) not null
)
go

create table QRTZ_TRIGGERS (
TRIGGER_NAME varchar(80) not null,
TRIGGER_GROUP varchar(80) not null,
JOB_NAME varchar(80) not null,
JOB_GROUP varchar(80) not null,
IS_VOLATILE bit not null,
DESCRIPTION varchar(120) null,
NEXT_FIRE_TIME numeric(13,0) null,
PREV_FIRE_TIME numeric(13,0) null,
TRIGGER_STATE varchar(16) not null,
TRIGGER_TYPE varchar(8) not null,
START_TIME numeric(13,0) not null,
END_TIME numeric(13,0) null,
CALENDAR_NAME varchar(80) null,
MISFIRE_INSTR smallint null
JOB_DATA image null
)
go

/*==============================================================================*/
/* Create primary key constraints: */
/*==============================================================================*/

alter table QRTZ_CALENDARS
add constraint PK_qrtz_calendars primary key clustered (CALENDAR_NAME)
go

alter table QRTZ_CRON_TRIGGERS
add constraint PK_qrtz_cron_triggers primary key clustered (TRIGGER_NAME, TRIGGER_GROUP)
go

alter table QRTZ_FIRED_TRIGGERS
add constraint PK_qrtz_fired_triggers primary key clustered (ENTRY_ID)
go

alter table QRTZ_PAUSED_TRIGGER_GRPS
add constraint PK_qrtz_paused_trigger_grps primary key clustered (TRIGGER_GROUP)
go

alter table QRTZ_SCHEDULER_STATE
add constraint PK_qrtz_scheduler_state primary key clustered (INSTANCE_NAME)
go

alter table QRTZ_LOCKS
add constraint PK_qrtz_locks primary key clustered (LOCK_NAME)
go

alter table QRTZ_JOB_DETAILS
add constraint PK_qrtz_job_details primary key clustered (JOB_NAME, JOB_GROUP)
go

alter table QRTZ_JOB_LISTENERS
add constraint PK_qrtz_job_listeners primary key clustered (JOB_NAME, JOB_GROUP, JOB_LISTENER)
go

alter table QRTZ_SIMPLE_TRIGGERS
add constraint PK_qrtz_simple_triggers primary key clustered (TRIGGER_NAME, TRIGGER_GROUP)
go

alter table QRTZ_TRIGGER_LISTENERS
add constraint PK_qrtz_trigger_listeners primary key clustered (TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_LISTENER)
go

alter table QRTZ_TRIGGERS
add constraint PK_qrtz_triggers primary key clustered (TRIGGER_NAME, TRIGGER_GROUP)
go

alter table QRTZ_BLOB_TRIGGERS
add constraint PK_qrtz_blob_triggers primary key clustered (TRIGGER_NAME, TRIGGER_GROUP)
go


/*==============================================================================*/
/* Create foreign key constraints: */
/*==============================================================================*/

alter table QRTZ_CRON_TRIGGERS
add constraint FK_cron_triggers_triggers foreign key (TRIGGER_NAME,TRIGGER_GROUP)
references QRTZ_TRIGGERS (TRIGGER_NAME,TRIGGER_GROUP)
go

alter table QRTZ_JOB_LISTENERS
add constraint FK_job_listeners_job_details foreign key (JOB_NAME,JOB_GROUP)
references QRTZ_JOB_DETAILS (JOB_NAME,JOB_GROUP)
go

alter table QRTZ_SIMPLE_TRIGGERS
add constraint FK_simple_triggers_triggers foreign key (TRIGGER_NAME,TRIGGER_GROUP)
references QRTZ_TRIGGERS (TRIGGER_NAME,TRIGGER_GROUP)
go

alter table QRTZ_TRIGGER_LISTENERS
add constraint FK_trigger_listeners_triggers foreign key (TRIGGER_NAME,TRIGGER_GROUP)
references QRTZ_TRIGGERS (TRIGGER_NAME,TRIGGER_GROUP)
go

alter table QRTZ_TRIGGERS
add constraint FK_triggers_job_details foreign key (JOB_NAME,JOB_GROUP)
references QRTZ_JOB_DETAILS (JOB_NAME,JOB_GROUP)
go

alter table QRTZ_BLOB_TRIGGERS
add constraint FK_blob_triggers_triggers foreign key (TRIGGER_NAME,TRIGGER_GROUP)
references QRTZ_TRIGGERS (TRIGGER_NAME,TRIGGER_GROUP)
go

/*==============================================================================*/
/* End of script. */
/*==============================================================================*/
