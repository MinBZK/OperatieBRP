drop table if exists resultaatMeting4;

create table resultaatMeting4 (
	verwerkingssysteem varchar(6) NOT NULL,
	bericht_id varchar(36) NOT NULL,
	status varchar(60) NOT NULL,
	indicatie_beheerder boolean NOT NULL,
	CONSTRAINT meting4_primary_key PRIMARY KEY (bericht_id, verwerkingssysteem)
);

INSERT INTO resultaatMeting4 (
select 
	'GBA-V' as Verwerkingssysteem,
	meting1.bericht_id as berichtId, 
	conv.statusIsc as status, 
	meting1.indicatie_beheerder as indicatie_beheerder
from resultaatmeting1 meting1
join converteerGbavStatusNaarIscStatus conv on conv.statusGbav = meting1.status 
where meting1.bericht_id not in (select meting2.bericht_id from resultaatmeting2 meting2 where meting2.status = conv.statusIsc)
	and conv.statusIsc <> 'Ok'
union all
select 
	'BRP' as Verwerkingssysteem,
	meting2.bericht_id as berichtId, 
	meting2.status as status, 
	meting2.indicatie_beheerder as indicatie_beheerder 
from resultaatmeting2 meting2
join converteerGbavStatusNaarIscStatus conv on conv.statusIsc = meting2.status 
where meting2.bericht_id not in (select meting1.bericht_id from resultaatmeting1 meting1 where meting1.status = conv.statusGbav)
	and conv.statusIsc <> 'Ok')
;

select * from resultaatMeting4;
