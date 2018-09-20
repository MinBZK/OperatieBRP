select ${selectItem} from kern.actie where srt= ${srtActie} and admhnd in (select admhnd from kern.pers where bsn = ${bsn})order by id desc
limit 1;