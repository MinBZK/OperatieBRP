-- Zet de partij 0626 en centrale voorziening 300201 om naar BRP.
update kern.partij set datovergangnaarbrp = 20000101 where code in ('199902');
update kern.his_partij set datovergangnaarbrp = 20000101 where partij in (select id from kern.partij where code in ('199902'));
