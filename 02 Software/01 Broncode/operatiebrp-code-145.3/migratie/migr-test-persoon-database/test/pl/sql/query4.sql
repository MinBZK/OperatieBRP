--MR Betrokkenheid
select multirealiteitregel.id as MR_ID, multirealiteitregel.geldigvoor, pers.anr, pers.voornamen, pers.voorvoegsel, pers.geslnaam, srtmultirealiteitregel.naam, multirealiteitregel.betr, srtbetr.naam
from kern.multirealiteitregel
join kern.betr on multirealiteitregel.betr = betr.id
join kern.srtbetr on srtbetr.id = betr.rol
join kern.pers on pers.id = multirealiteitregel.geldigvoor
join kern.srtmultirealiteitregel on multirealiteitregel.srt = srtmultirealiteitregel.id;
