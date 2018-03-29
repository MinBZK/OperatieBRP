UPDATE public.activiteit 
SET activiteit_type=202, activiteit_subtype=1751 
WHERE toestand=8999 AND communicatie_partner='510001';
UPDATE public.activiteit 
SET activiteit_type=107
WHERE toestand=8000 AND communicatie_partner='510001';