create table JBPM_ACTION (ID_ fixed(19,0) not null, class char(1) not null, NAME_ varchar(255) null, ISPROPAGATIONALLOWED_ boolean null, ACTIONEXPRESSION_ varchar(255) null, ISASYNC_ boolean null, REFERENCEDACTION_ fixed(19,0) null, ACTIONDELEGATION_ fixed(19,0) null, EVENT_ fixed(19,0) null, PROCESSDEFINITION_ fixed(19,0) null, EXPRESSION_ varchar(4000) null, TIMERNAME_ varchar(255) null, DUEDATE_ varchar(255) null, REPEAT_ varchar(255) null, TRANSITIONNAME_ varchar(255) null, TIMERACTION_ fixed(19,0) null, EVENTINDEX_ int null, EXCEPTIONHANDLER_ fixed(19,0) null, EXCEPTIONHANDLERINDEX_ int null, primary key (ID_))
create table JBPM_BYTEARRAY (ID_ fixed(19,0) not null, NAME_ varchar(255) null, FILEDEFINITION_ fixed(19,0) null, primary key (ID_))
create table JBPM_BYTEBLOCK (PROCESSFILE_ fixed(19,0) not null, BYTES_ long byte null, INDEX_ int not null, primary key (PROCESSFILE_, INDEX_))
create table JBPM_COMMENT (ID_ fixed(19,0) not null, VERSION_ int not null, ACTORID_ varchar(255) null, TIME_ timestamp null, MESSAGE_ varchar(4000) null, TOKEN_ fixed(19,0) null, TASKINSTANCE_ fixed(19,0) null, TOKENINDEX_ int null, TASKINSTANCEINDEX_ int null, primary key (ID_))
create table JBPM_DECISIONCONDITIONS (DECISION_ fixed(19,0) not null, TRANSITIONNAME_ varchar(255) null, EXPRESSION_ varchar(255) null, INDEX_ int not null, primary key (DECISION_, INDEX_))
create table JBPM_DELEGATION (ID_ fixed(19,0) not null, CLASSNAME_ varchar(4000) null, CONFIGURATION_ varchar(4000) null, CONFIGTYPE_ varchar(255) null, PROCESSDEFINITION_ fixed(19,0) null, primary key (ID_))
create table JBPM_EVENT (ID_ fixed(19,0) not null, EVENTTYPE_ varchar(255) null, TYPE_ char(1) null, GRAPHELEMENT_ fixed(19,0) null, PROCESSDEFINITION_ fixed(19,0) null, NODE_ fixed(19,0) null, TRANSITION_ fixed(19,0) null, TASK_ fixed(19,0) null, primary key (ID_))
create table JBPM_EXCEPTIONHANDLER (ID_ fixed(19,0) not null, EXCEPTIONCLASSNAME_ varchar(4000) null, TYPE_ char(1) null, GRAPHELEMENT_ fixed(19,0) null, PROCESSDEFINITION_ fixed(19,0) null, GRAPHELEMENTINDEX_ int null, NODE_ fixed(19,0) null, TRANSITION_ fixed(19,0) null, TASK_ fixed(19,0) null, primary key (ID_))
create table JBPM_ID_GROUP (ID_ fixed(19,0) not null, CLASS_ char(1) not null, NAME_ varchar(255) null, TYPE_ varchar(255) null, PARENT_ fixed(19,0) null, primary key (ID_))
create table JBPM_ID_MEMBERSHIP (ID_ fixed(19,0) not null, CLASS_ char(1) not null, NAME_ varchar(255) null, ROLE_ varchar(255) null, USER_ fixed(19,0) null, GROUP_ fixed(19,0) null, primary key (ID_))
create table JBPM_ID_PERMISSIONS (ENTITY_ fixed(19,0) not null, CLASS_ varchar(255) null, NAME_ varchar(255) null, ACTION_ varchar(255) null)
create table JBPM_ID_USER (ID_ fixed(19,0) not null, CLASS_ char(1) not null, NAME_ varchar(255) null, EMAIL_ varchar(255) null, PASSWORD_ varchar(255) null, primary key (ID_))
create table JBPM_JOB (ID_ fixed(19,0) not null, CLASS_ char(1) not null, VERSION_ int not null, DUEDATE_ timestamp null, PROCESSINSTANCE_ fixed(19,0) null, TOKEN_ fixed(19,0) null, TASKINSTANCE_ fixed(19,0) null, ISSUSPENDED_ boolean null, ISEXCLUSIVE_ boolean null, LOCKOWNER_ varchar(255) null, LOCKTIME_ timestamp null, EXCEPTION_ varchar(4000) null, RETRIES_ int null, NAME_ varchar(255) null, REPEAT_ varchar(255) null, TRANSITIONNAME_ varchar(255) null, ACTION_ fixed(19,0) null, GRAPHELEMENTTYPE_ varchar(255) null, GRAPHELEMENT_ fixed(19,0) null, NODE_ fixed(19,0) null, primary key (ID_))
create table JBPM_LOG (ID_ fixed(19,0) not null, CLASS_ char(1) not null, INDEX_ int null, DATE_ timestamp null, TOKEN_ fixed(19,0) null, PARENT_ fixed(19,0) null, MESSAGE_ varchar(4000) null, EXCEPTION_ varchar(4000) null, ACTION_ fixed(19,0) null, NODE_ fixed(19,0) null, ENTER_ timestamp null, LEAVE_ timestamp null, DURATION_ fixed(19,0) null, NEWLONGVALUE_ fixed(19,0) null, TRANSITION_ fixed(19,0) null, CHILD_ fixed(19,0) null, SOURCENODE_ fixed(19,0) null, DESTINATIONNODE_ fixed(19,0) null, VARIABLEINSTANCE_ fixed(19,0) null, OLDBYTEARRAY_ fixed(19,0) null, NEWBYTEARRAY_ fixed(19,0) null, OLDDATEVALUE_ timestamp null, NEWDATEVALUE_ timestamp null, OLDDOUBLEVALUE_ double precision null, NEWDOUBLEVALUE_ double precision null, OLDLONGIDCLASS_ varchar(255) null, OLDLONGIDVALUE_ fixed(19,0) null, NEWLONGIDCLASS_ varchar(255) null, NEWLONGIDVALUE_ fixed(19,0) null, OLDSTRINGIDCLASS_ varchar(255) null, OLDSTRINGIDVALUE_ varchar(255) null, NEWSTRINGIDCLASS_ varchar(255) null, NEWSTRINGIDVALUE_ varchar(255) null, OLDLONGVALUE_ fixed(19,0) null, OLDSTRINGVALUE_ varchar(4000) null, NEWSTRINGVALUE_ varchar(4000) null, TASKINSTANCE_ fixed(19,0) null, TASKACTORID_ varchar(255) null, TASKOLDACTORID_ varchar(255) null, SWIMLANEINSTANCE_ fixed(19,0) null, primary key (ID_))
create table JBPM_MODULEDEFINITION (ID_ fixed(19,0) not null, CLASS_ char(1) not null, NAME_ varchar(4000) null, PROCESSDEFINITION_ fixed(19,0) null, STARTTASK_ fixed(19,0) null, primary key (ID_))
create table JBPM_MODULEINSTANCE (ID_ fixed(19,0) not null, CLASS_ char(1) not null, VERSION_ int not null, PROCESSINSTANCE_ fixed(19,0) null, TASKMGMTDEFINITION_ fixed(19,0) null, NAME_ varchar(255) null, primary key (ID_))
create table JBPM_NODE (ID_ fixed(19,0) not null, CLASS_ char(1) not null, NAME_ varchar(255) null, DESCRIPTION_ varchar(4000) null, PROCESSDEFINITION_ fixed(19,0) null, ISASYNC_ boolean null, ISASYNCEXCL_ boolean null, ACTION_ fixed(19,0) null, SUPERSTATE_ fixed(19,0) null, SUBPROCNAME_ varchar(255) null, SUBPROCESSDEFINITION_ fixed(19,0) null, DECISIONEXPRESSION_ varchar(255) null, DECISIONDELEGATION fixed(19,0) null, SCRIPT_ fixed(19,0) null, SIGNAL_ int null, CREATETASKS_ boolean null, ENDTASKS_ boolean null, NODECOLLECTIONINDEX_ int null, primary key (ID_))
create table JBPM_POOLEDACTOR (ID_ fixed(19,0) not null, VERSION_ int not null, ACTORID_ varchar(255) null, SWIMLANEINSTANCE_ fixed(19,0) null, primary key (ID_))
create table JBPM_PROCESSDEFINITION (ID_ fixed(19,0) not null, CLASS_ char(1) not null, NAME_ varchar(255) null, DESCRIPTION_ varchar(4000) null, VERSION_ int null, ISTERMINATIONIMPLICIT_ boolean null, STARTSTATE_ fixed(19,0) null, primary key (ID_))
create table JBPM_PROCESSINSTANCE (ID_ fixed(19,0) not null, VERSION_ int not null, KEY_ varchar(255) null, START_ timestamp null, END_ timestamp null, ISSUSPENDED_ boolean null, PROCESSDEFINITION_ fixed(19,0) null, ROOTTOKEN_ fixed(19,0) null, SUPERPROCESSTOKEN_ fixed(19,0) null, primary key (ID_))
create table JBPM_RUNTIMEACTION (ID_ fixed(19,0) not null, VERSION_ int not null, EVENTTYPE_ varchar(255) null, TYPE_ char(1) null, GRAPHELEMENT_ fixed(19,0) null, PROCESSINSTANCE_ fixed(19,0) null, ACTION_ fixed(19,0) null, PROCESSINSTANCEINDEX_ int null, primary key (ID_))
create table JBPM_SWIMLANE (ID_ fixed(19,0) not null, NAME_ varchar(255) null, ACTORIDEXPRESSION_ varchar(255) null, POOLEDACTORSEXPRESSION_ varchar(255) null, ASSIGNMENTDELEGATION_ fixed(19,0) null, TASKMGMTDEFINITION_ fixed(19,0) null, primary key (ID_))
create table JBPM_SWIMLANEINSTANCE (ID_ fixed(19,0) not null, VERSION_ int not null, NAME_ varchar(255) null, ACTORID_ varchar(255) null, SWIMLANE_ fixed(19,0) null, TASKMGMTINSTANCE_ fixed(19,0) null, primary key (ID_))
create table JBPM_TASK (ID_ fixed(19,0) not null, NAME_ varchar(255) null, DESCRIPTION_ varchar(4000) null, PROCESSDEFINITION_ fixed(19,0) null, ISBLOCKING_ boolean null, ISSIGNALLING_ boolean null, CONDITION_ varchar(255) null, DUEDATE_ varchar(255) null, PRIORITY_ int null, ACTORIDEXPRESSION_ varchar(255) null, POOLEDACTORSEXPRESSION_ varchar(255) null, TASKMGMTDEFINITION_ fixed(19,0) null, TASKNODE_ fixed(19,0) null, STARTSTATE_ fixed(19,0) null, ASSIGNMENTDELEGATION_ fixed(19,0) null, SWIMLANE_ fixed(19,0) null, TASKCONTROLLER_ fixed(19,0) null, primary key (ID_))
create table JBPM_TASKACTORPOOL (TASKINSTANCE_ fixed(19,0) not null, POOLEDACTOR_ fixed(19,0) not null, primary key (TASKINSTANCE_, POOLEDACTOR_))
create table JBPM_TASKCONTROLLER (ID_ fixed(19,0) not null, TASKCONTROLLERDELEGATION_ fixed(19,0) null, primary key (ID_))
create table JBPM_TASKINSTANCE (ID_ fixed(19,0) not null, CLASS_ char(1) not null, VERSION_ int not null, NAME_ varchar(255) null, DESCRIPTION_ varchar(4000) null, ACTORID_ varchar(255) null, CREATE_ timestamp null, START_ timestamp null, END_ timestamp null, DUEDATE_ timestamp null, PRIORITY_ int null, ISCANCELLED_ boolean null, ISSUSPENDED_ boolean null, ISOPEN_ boolean null, ISSIGNALLING_ boolean null, ISBLOCKING_ boolean null, TASK_ fixed(19,0) null, TOKEN_ fixed(19,0) null, PROCINST_ fixed(19,0) null, SWIMLANINSTANCE_ fixed(19,0) null, TASKMGMTINSTANCE_ fixed(19,0) null, primary key (ID_))
create table JBPM_TOKEN (ID_ fixed(19,0) not null, VERSION_ int not null, NAME_ varchar(255) null, START_ timestamp null, END_ timestamp null, NODEENTER_ timestamp null, NEXTLOGINDEX_ int null, ISABLETOREACTIVATEPARENT_ boolean null, ISTERMINATIONIMPLICIT_ boolean null, ISSUSPENDED_ boolean null, LOCK_ varchar(255) null, NODE_ fixed(19,0) null, PROCESSINSTANCE_ fixed(19,0) null, PARENT_ fixed(19,0) null, SUBPROCESSINSTANCE_ fixed(19,0) null, primary key (ID_))
create table JBPM_TOKENVARIABLEMAP (ID_ fixed(19,0) not null, VERSION_ int not null, TOKEN_ fixed(19,0) null, CONTEXTINSTANCE_ fixed(19,0) null, primary key (ID_))
create table JBPM_TRANSITION (ID_ fixed(19,0) not null, NAME_ varchar(255) null, DESCRIPTION_ varchar(4000) null, PROCESSDEFINITION_ fixed(19,0) null, FROM_ fixed(19,0) null, TO_ fixed(19,0) null, CONDITION_ varchar(255) null, FROMINDEX_ int null, primary key (ID_))
create table JBPM_VARIABLEACCESS (ID_ fixed(19,0) not null, VARIABLENAME_ varchar(255) null, ACCESS_ varchar(255) null, MAPPEDNAME_ varchar(255) null, SCRIPT_ fixed(19,0) null, PROCESSSTATE_ fixed(19,0) null, TASKCONTROLLER_ fixed(19,0) null, INDEX_ int null, primary key (ID_))
create table JBPM_VARIABLEINSTANCE (ID_ fixed(19,0) not null, CLASS_ char(1) not null, VERSION_ int not null, NAME_ varchar(255) null, CONVERTER_ char(1) null, TOKEN_ fixed(19,0) null, TOKENVARIABLEMAP_ fixed(19,0) null, PROCESSINSTANCE_ fixed(19,0) null, BYTEARRAYVALUE_ fixed(19,0) null, DATEVALUE_ timestamp null, DOUBLEVALUE_ double precision null, LONGIDCLASS_ varchar(255) null, LONGVALUE_ fixed(19,0) null, STRINGIDCLASS_ varchar(255) null, STRINGVALUE_ varchar(4000) null, TASKINSTANCE_ fixed(19,0) null, primary key (ID_))
create index IDX_ACTION_REFACT on JBPM_ACTION (REFERENCEDACTION_)
create index IDX_ACTION_EVENT on JBPM_ACTION (EVENT_)
create index IDX_ACTION_ACTNDL on JBPM_ACTION (ACTIONDELEGATION_)
create index IDX_ACTION_PROCDF on JBPM_ACTION (PROCESSDEFINITION_)
create index IDX_CRTETIMERACT_TA on JBPM_ACTION (TIMERACTION_)
alter table JBPM_ACTION foreign key FK_ACTION_EVENT (EVENT_) references JBPM_EVENT
alter table JBPM_ACTION foreign key FK_ACTION_EXPTHDL (EXCEPTIONHANDLER_) references JBPM_EXCEPTIONHANDLER
alter table JBPM_ACTION foreign key FK_ACTION_PROCDEF (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION
alter table JBPM_ACTION foreign key FK_CRTETIMERACT_TA (TIMERACTION_) references JBPM_ACTION
alter table JBPM_ACTION foreign key FK_ACTION_ACTNDEL (ACTIONDELEGATION_) references JBPM_DELEGATION
alter table JBPM_ACTION foreign key FK_ACTION_REFACT (REFERENCEDACTION_) references JBPM_ACTION
alter table JBPM_BYTEARRAY foreign key FK_BYTEARR_FILDEF (FILEDEFINITION_) references JBPM_MODULEDEFINITION
alter table JBPM_BYTEBLOCK foreign key FK_BYTEBLOCK_FILE (PROCESSFILE_) references JBPM_BYTEARRAY
create index IDX_COMMENT_TOKEN on JBPM_COMMENT (TOKEN_)
create index IDX_COMMENT_TSK on JBPM_COMMENT (TASKINSTANCE_)
alter table JBPM_COMMENT foreign key FK_COMMENT_TOKEN (TOKEN_) references JBPM_TOKEN
alter table JBPM_COMMENT foreign key FK_COMMENT_TSK (TASKINSTANCE_) references JBPM_TASKINSTANCE
alter table JBPM_DECISIONCONDITIONS foreign key FK_DECCOND_DEC (DECISION_) references JBPM_NODE
create index IDX_DELEG_PRCD on JBPM_DELEGATION (PROCESSDEFINITION_)
alter table JBPM_DELEGATION foreign key FK_DELEGATION_PRCD (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION
alter table JBPM_EVENT foreign key FK_EVENT_PROCDEF (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION
alter table JBPM_EVENT foreign key FK_EVENT_NODE (NODE_) references JBPM_NODE
alter table JBPM_EVENT foreign key FK_EVENT_TRANS (TRANSITION_) references JBPM_TRANSITION
alter table JBPM_EVENT foreign key FK_EVENT_TASK (TASK_) references JBPM_TASK
alter table JBPM_ID_GROUP foreign key FK_ID_GRP_PARENT (PARENT_) references JBPM_ID_GROUP
alter table JBPM_ID_MEMBERSHIP foreign key FK_ID_MEMSHIP_GRP (GROUP_) references JBPM_ID_GROUP
alter table JBPM_ID_MEMBERSHIP foreign key FK_ID_MEMSHIP_USR (USER_) references JBPM_ID_USER
create index IDX_JOB_NODE on JBPM_JOB (NODE_)
create index IDX_JOB_ACTION on JBPM_JOB (ACTION_)
create index IDX_JOB_TSKINST on JBPM_JOB (TASKINSTANCE_)
create index IDX_JOB_PRINST on JBPM_JOB (PROCESSINSTANCE_)
create index IDX_JOB_TOKEN on JBPM_JOB (TOKEN_)
alter table JBPM_JOB foreign key FK_JOB_TOKEN (TOKEN_) references JBPM_TOKEN
alter table JBPM_JOB foreign key FK_JOB_NODE (NODE_) references JBPM_NODE
alter table JBPM_JOB foreign key FK_JOB_PRINST (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE
alter table JBPM_JOB foreign key FK_JOB_ACTION (ACTION_) references JBPM_ACTION
alter table JBPM_JOB foreign key FK_JOB_TSKINST (TASKINSTANCE_) references JBPM_TASKINSTANCE
create index IDX_LOG_NEWBYTES on JBPM_LOG (NEWBYTEARRAY_)
create index IDX_LOG_NODE on JBPM_LOG (NODE_)
create index IDX_LOG_TASKINST on JBPM_LOG (TASKINSTANCE_)
create index IDX_LOG_TRANSITION on JBPM_LOG (TRANSITION_)
create index IDX_LOG_DESTNODE on JBPM_LOG (DESTINATIONNODE_)
create index IDX_LOG_CHILDTOKEN on JBPM_LOG (CHILD_)
create index IDX_LOG_TOKEN on JBPM_LOG (TOKEN_)
create index IDX_LOG_SWIMINST on JBPM_LOG (SWIMLANEINSTANCE_)
create index IDX_LOG_PARENT on JBPM_LOG (PARENT_)
create index IDX_LOG_VARINST on JBPM_LOG (VARIABLEINSTANCE_)
create index IDX_LOG_ACTION on JBPM_LOG (ACTION_)
create index IDX_LOG_SOURCENODE on JBPM_LOG (SOURCENODE_)
create index IDX_LOG_OLDBYTES on JBPM_LOG (OLDBYTEARRAY_)
alter table JBPM_LOG foreign key FK_LOG_SOURCENODE (SOURCENODE_) references JBPM_NODE
alter table JBPM_LOG foreign key FK_LOG_TOKEN (TOKEN_) references JBPM_TOKEN
alter table JBPM_LOG foreign key FK_LOG_OLDBYTES (OLDBYTEARRAY_) references JBPM_BYTEARRAY
alter table JBPM_LOG foreign key FK_LOG_NEWBYTES (NEWBYTEARRAY_) references JBPM_BYTEARRAY
alter table JBPM_LOG foreign key FK_LOG_CHILDTOKEN (CHILD_) references JBPM_TOKEN
alter table JBPM_LOG foreign key FK_LOG_DESTNODE (DESTINATIONNODE_) references JBPM_NODE
alter table JBPM_LOG foreign key FK_LOG_TASKINST (TASKINSTANCE_) references JBPM_TASKINSTANCE
alter table JBPM_LOG foreign key FK_LOG_SWIMINST (SWIMLANEINSTANCE_) references JBPM_SWIMLANEINSTANCE
alter table JBPM_LOG foreign key FK_LOG_PARENT (PARENT_) references JBPM_LOG
alter table JBPM_LOG foreign key FK_LOG_NODE (NODE_) references JBPM_NODE
alter table JBPM_LOG foreign key FK_LOG_ACTION (ACTION_) references JBPM_ACTION
alter table JBPM_LOG foreign key FK_LOG_VARINST (VARIABLEINSTANCE_) references JBPM_VARIABLEINSTANCE
alter table JBPM_LOG foreign key FK_LOG_TRANSITION (TRANSITION_) references JBPM_TRANSITION
create index IDX_TSKDEF_START on JBPM_MODULEDEFINITION (STARTTASK_)
create index IDX_MODDEF_PROCDF on JBPM_MODULEDEFINITION (PROCESSDEFINITION_)
alter table JBPM_MODULEDEFINITION foreign key FK_TSKDEF_START (STARTTASK_) references JBPM_TASK
alter table JBPM_MODULEDEFINITION foreign key FK_MODDEF_PROCDEF (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION
create index IDX_TASKMGTINST_TMD on JBPM_MODULEINSTANCE (TASKMGMTDEFINITION_)
create index IDX_MODINST_PRINST on JBPM_MODULEINSTANCE (PROCESSINSTANCE_)
alter table JBPM_MODULEINSTANCE foreign key FK_TASKMGTINST_TMD (TASKMGMTDEFINITION_) references JBPM_MODULEDEFINITION
alter table JBPM_MODULEINSTANCE foreign key FK_MODINST_PRCINST (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE
create index IDX_PSTATE_SBPRCDEF on JBPM_NODE (SUBPROCESSDEFINITION_)
create index IDX_NODE_SCRIPT on JBPM_NODE (SCRIPT_)
create index IDX_NODE_SUPRSTATE on JBPM_NODE (SUPERSTATE_)
create index IDX_NODE_PROCDEF on JBPM_NODE (PROCESSDEFINITION_)
create index IDX_DECISION_DELEG on JBPM_NODE (DECISIONDELEGATION)
create index IDX_NODE_ACTION on JBPM_NODE (ACTION_)
alter table JBPM_NODE foreign key FK_PROCST_SBPRCDEF (SUBPROCESSDEFINITION_) references JBPM_PROCESSDEFINITION
alter table JBPM_NODE foreign key FK_NODE_PROCDEF (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION
alter table JBPM_NODE foreign key FK_NODE_SCRIPT (SCRIPT_) references JBPM_ACTION
alter table JBPM_NODE foreign key FK_NODE_ACTION (ACTION_) references JBPM_ACTION
alter table JBPM_NODE foreign key FK_DECISION_DELEG (DECISIONDELEGATION) references JBPM_DELEGATION
alter table JBPM_NODE foreign key FK_NODE_SUPERSTATE (SUPERSTATE_) references JBPM_NODE
create index IDX_PLDACTR_ACTID on JBPM_POOLEDACTOR (ACTORID_)
create index IDX_TSKINST_SWLANE on JBPM_POOLEDACTOR (SWIMLANEINSTANCE_)
alter table JBPM_POOLEDACTOR foreign key FK_POOLEDACTOR_SLI (SWIMLANEINSTANCE_) references JBPM_SWIMLANEINSTANCE
create index IDX_PROCDEF_STRTST on JBPM_PROCESSDEFINITION (STARTSTATE_)
alter table JBPM_PROCESSDEFINITION foreign key FK_PROCDEF_STRTSTA (STARTSTATE_) references JBPM_NODE
create index IDX_PROCIN_ROOTTK on JBPM_PROCESSINSTANCE (ROOTTOKEN_)
create index IDX_PROCIN_SPROCTK on JBPM_PROCESSINSTANCE (SUPERPROCESSTOKEN_)
create index IDX_PROCIN_KEY on JBPM_PROCESSINSTANCE (KEY_)
create index IDX_PROCIN_PROCDEF on JBPM_PROCESSINSTANCE (PROCESSDEFINITION_)
alter table JBPM_PROCESSINSTANCE foreign key FK_PROCIN_PROCDEF (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION
alter table JBPM_PROCESSINSTANCE foreign key FK_PROCIN_ROOTTKN (ROOTTOKEN_) references JBPM_TOKEN
alter table JBPM_PROCESSINSTANCE foreign key FK_PROCIN_SPROCTKN (SUPERPROCESSTOKEN_) references JBPM_TOKEN
create index IDX_RTACTN_PRCINST on JBPM_RUNTIMEACTION (PROCESSINSTANCE_)
create index IDX_RTACTN_ACTION on JBPM_RUNTIMEACTION (ACTION_)
alter table JBPM_RUNTIMEACTION foreign key FK_RTACTN_PROCINST (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE
alter table JBPM_RUNTIMEACTION foreign key FK_RTACTN_ACTION (ACTION_) references JBPM_ACTION
create index IDX_SWL_TSKMGMTDEF on JBPM_SWIMLANE (TASKMGMTDEFINITION_)
create index IDX_SWL_ASSDEL on JBPM_SWIMLANE (ASSIGNMENTDELEGATION_)
alter table JBPM_SWIMLANE foreign key FK_SWL_ASSDEL (ASSIGNMENTDELEGATION_) references JBPM_DELEGATION
alter table JBPM_SWIMLANE foreign key FK_SWL_TSKMGMTDEF (TASKMGMTDEFINITION_) references JBPM_MODULEDEFINITION
create index IDX_SWIMLINST_SL on JBPM_SWIMLANEINSTANCE (SWIMLANE_)
create index IDX_SWIMLANEINST_TM on JBPM_SWIMLANEINSTANCE (TASKMGMTINSTANCE_)
alter table JBPM_SWIMLANEINSTANCE foreign key FK_SWIMLANEINST_TM (TASKMGMTINSTANCE_) references JBPM_MODULEINSTANCE
alter table JBPM_SWIMLANEINSTANCE foreign key FK_SWIMLANEINST_SL (SWIMLANE_) references JBPM_SWIMLANE
create index IDX_TASK_STARTST on JBPM_TASK (STARTSTATE_)
create index IDX_TASK_SWIMLANE on JBPM_TASK (SWIMLANE_)
create index IDX_TSK_TSKCTRL on JBPM_TASK (TASKCONTROLLER_)
create index IDX_TASK_ASSDEL on JBPM_TASK (ASSIGNMENTDELEGATION_)
create index IDX_TASK_TSKNODE on JBPM_TASK (TASKNODE_)
create index IDX_TASK_PROCDEF on JBPM_TASK (PROCESSDEFINITION_)
create index IDX_TASK_TASKMGTDF on JBPM_TASK (TASKMGMTDEFINITION_)
alter table JBPM_TASK foreign key FK_TSK_TSKCTRL (TASKCONTROLLER_) references JBPM_TASKCONTROLLER
alter table JBPM_TASK foreign key FK_TASK_ASSDEL (ASSIGNMENTDELEGATION_) references JBPM_DELEGATION
alter table JBPM_TASK foreign key FK_TASK_TASKNODE (TASKNODE_) references JBPM_NODE
alter table JBPM_TASK foreign key FK_TASK_PROCDEF (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION
alter table JBPM_TASK foreign key FK_TASK_STARTST (STARTSTATE_) references JBPM_NODE
alter table JBPM_TASK foreign key FK_TASK_TASKMGTDEF (TASKMGMTDEFINITION_) references JBPM_MODULEDEFINITION
alter table JBPM_TASK foreign key FK_TASK_SWIMLANE (SWIMLANE_) references JBPM_SWIMLANE
alter table JBPM_TASKACTORPOOL foreign key FK_TSKACTPOL_PLACT (POOLEDACTOR_) references JBPM_POOLEDACTOR
alter table JBPM_TASKACTORPOOL foreign key FK_TASKACTPL_TSKI (TASKINSTANCE_) references JBPM_TASKINSTANCE
create index IDX_TSKCTRL_DELEG on JBPM_TASKCONTROLLER (TASKCONTROLLERDELEGATION_)
alter table JBPM_TASKCONTROLLER foreign key FK_TSKCTRL_DELEG (TASKCONTROLLERDELEGATION_) references JBPM_DELEGATION
create index IDX_TASKINST_TOKN on JBPM_TASKINSTANCE (TOKEN_)
create index IDX_TASKINST_TSK on JBPM_TASKINSTANCE (TASK_, PROCINST_)
create index IDX_TSKINST_TMINST on JBPM_TASKINSTANCE (TASKMGMTINSTANCE_)
create index IDX_TSKINST_SLINST on JBPM_TASKINSTANCE (SWIMLANINSTANCE_)
create index IDX_TASK_ACTORID on JBPM_TASKINSTANCE (ACTORID_)
alter table JBPM_TASKINSTANCE foreign key FK_TSKINS_PRCINS (PROCINST_) references JBPM_PROCESSINSTANCE
alter table JBPM_TASKINSTANCE foreign key FK_TASKINST_TMINST (TASKMGMTINSTANCE_) references JBPM_MODULEINSTANCE
alter table JBPM_TASKINSTANCE foreign key FK_TASKINST_TOKEN (TOKEN_) references JBPM_TOKEN
alter table JBPM_TASKINSTANCE foreign key FK_TASKINST_SLINST (SWIMLANINSTANCE_) references JBPM_SWIMLANEINSTANCE
alter table JBPM_TASKINSTANCE foreign key FK_TASKINST_TASK (TASK_) references JBPM_TASK
create index IDX_TOKEN_PROCIN on JBPM_TOKEN (PROCESSINSTANCE_)
create index IDX_TOKEN_SUBPI on JBPM_TOKEN (SUBPROCESSINSTANCE_)
create index IDX_TOKEN_NODE on JBPM_TOKEN (NODE_)
create index IDX_TOKEN_PARENT on JBPM_TOKEN (PARENT_)
alter table JBPM_TOKEN foreign key FK_TOKEN_PARENT (PARENT_) references JBPM_TOKEN
alter table JBPM_TOKEN foreign key FK_TOKEN_NODE (NODE_) references JBPM_NODE
alter table JBPM_TOKEN foreign key FK_TOKEN_PROCINST (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE
alter table JBPM_TOKEN foreign key FK_TOKEN_SUBPI (SUBPROCESSINSTANCE_) references JBPM_PROCESSINSTANCE
create index IDX_TKVARMAP_CTXT on JBPM_TOKENVARIABLEMAP (CONTEXTINSTANCE_)
create index IDX_TKVVARMP_TOKEN on JBPM_TOKENVARIABLEMAP (TOKEN_)
alter table JBPM_TOKENVARIABLEMAP foreign key FK_TKVARMAP_CTXT (CONTEXTINSTANCE_) references JBPM_MODULEINSTANCE
alter table JBPM_TOKENVARIABLEMAP foreign key FK_TKVARMAP_TOKEN (TOKEN_) references JBPM_TOKEN
create index IDX_TRANSIT_TO on JBPM_TRANSITION (TO_)
create index IDX_TRANSIT_FROM on JBPM_TRANSITION (FROM_)
create index IDX_TRANS_PROCDEF on JBPM_TRANSITION (PROCESSDEFINITION_)
alter table JBPM_TRANSITION foreign key FK_TRANSITION_TO (TO_) references JBPM_NODE
alter table JBPM_TRANSITION foreign key FK_TRANS_PROCDEF (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION
alter table JBPM_TRANSITION foreign key FK_TRANSITION_FROM (FROM_) references JBPM_NODE
alter table JBPM_VARIABLEACCESS foreign key FK_VARACC_TSKCTRL (TASKCONTROLLER_) references JBPM_TASKCONTROLLER
alter table JBPM_VARIABLEACCESS foreign key FK_VARACC_SCRIPT (SCRIPT_) references JBPM_ACTION
alter table JBPM_VARIABLEACCESS foreign key FK_VARACC_PROCST (PROCESSSTATE_) references JBPM_NODE
create index IDX_VARINST_TKVARMP on JBPM_VARIABLEINSTANCE (TOKENVARIABLEMAP_)
create index IDX_VARINST_PRCINS on JBPM_VARIABLEINSTANCE (PROCESSINSTANCE_)
create index IDX_BYTEINST_ARRAY on JBPM_VARIABLEINSTANCE (BYTEARRAYVALUE_)
create index IDX_VARINST_TK on JBPM_VARIABLEINSTANCE (TOKEN_)
alter table JBPM_VARIABLEINSTANCE foreign key FK_VARINST_TK (TOKEN_) references JBPM_TOKEN
alter table JBPM_VARIABLEINSTANCE foreign key FK_VARINST_TKVARMP (TOKENVARIABLEMAP_) references JBPM_TOKENVARIABLEMAP
alter table JBPM_VARIABLEINSTANCE foreign key FK_VARINST_PRCINST (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE
alter table JBPM_VARIABLEINSTANCE foreign key FK_VAR_TSKINST (TASKINSTANCE_) references JBPM_TASKINSTANCE
alter table JBPM_VARIABLEINSTANCE foreign key FK_BYTEINST_ARRAY (BYTEARRAYVALUE_) references JBPM_BYTEARRAY
create sequence hibernate_sequence
