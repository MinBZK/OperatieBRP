select * from prot.levsaantek
inner join prot.levsaantekpers
on levsaantek.id = levsaantekpers.levsaantek
where pers = $$persoonid$$;