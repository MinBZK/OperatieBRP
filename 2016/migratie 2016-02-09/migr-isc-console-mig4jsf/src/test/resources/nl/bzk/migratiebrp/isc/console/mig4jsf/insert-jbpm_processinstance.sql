insert into jbpm_processinstance(id_, version_,start_,end_,processdefinition_,superprocesstoken_)
    values(2222, 1, now(), null, 3333, null);

insert into jbpm_processinstance(id_, version_,start_,end_,processdefinition_,superprocesstoken_)
    values(3333, 1, now(), parsedatetime('01-03-2014', 'dd-MM-yyyy'), 3333, null);

insert into jbpm_processinstance(id_, version_,start_,end_,processdefinition_,superprocesstoken_)
    values(4444, 1, now(), parsedatetime('01-03-2012', 'dd-MM-yyyy'), 3333, null);

insert into jbpm_processinstance(id_, version_,start_,end_,processdefinition_,superprocesstoken_)
    values(5555, 1, now(), parsedatetime('01-03-2011', 'dd-MM-yyyy'), 3333, null);

insert into jbpm_processinstance(id_, version_,start_,end_,processdefinition_,superprocesstoken_)
    values(6666, 1, now(), parsedatetime('01-03-2012', 'dd-MM-yyyy'), 3333, null);

insert into jbpm_processinstance(id_, version_,start_,end_,processdefinition_,superprocesstoken_)
    values(7777, 1, now(), now(), 4444, null);

insert into jbpm_processinstance(id_, version_,start_,end_,processdefinition_,superprocesstoken_)
    values(8888, 1, now(), now(), 3333, 4);

insert into jbpm_processinstance(id_, version_,start_,end_,processdefinition_,superprocesstoken_)
    values(9999, 1, parsedatetime('21-05-2013', 'dd-MM-yyyy'), null, 4321, null);

commit;
