select anummer
,      case when inhoud_na_terugconversie is null then 'leeg' else 'gevuld' end as inhoud_na_terugconversie
,      foutmelding_terugconversie
from   initvul.initvullingresult
order by 1