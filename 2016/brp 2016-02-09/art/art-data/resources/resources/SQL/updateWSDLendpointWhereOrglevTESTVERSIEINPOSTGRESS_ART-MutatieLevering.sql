update kern.partij set wsdlendpoint = 'http://localhost:8080/brp-kennisgeving-ontvanger/levering/kennisgevingService' where code = '051801';


select wsdlendpoint from kern.partij where partij.code = '051801';
select wsdlendpoint from kern.partij where partij.code = '020201';


UPDATE info_ids_leveranciers A SET functionelecode_wpl = (SELECT  code FROM kern.plaats B WHERE A.functionelecode_wpl=B.id);


 id smallint NOT NULL,
  naam character varying(80) NOT NULL,
  srt smallint,
  code integer NOT NULL,
  dateinde integer,
  dataanv integer,
  sector smallint,
  wsdlendpoint character varying(200),
  partijstatushis character varying(1) NOT NULL,