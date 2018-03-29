UPDATE public.lo3_pl
SET bijhouding_opschort_datum = 20170101, bijhouding_opschort_reden = 'W'
WHERE pl_id = (SELECT pl_id
FROM public.lo3_pl_persoon
WHERE a_nr = 8409813281)