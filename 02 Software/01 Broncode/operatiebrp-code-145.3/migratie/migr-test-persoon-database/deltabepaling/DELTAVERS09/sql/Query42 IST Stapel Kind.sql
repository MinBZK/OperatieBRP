select
a.categorie, a.volgnr as stapelnr, c.volgnr as voorkomennr, 
d.rdneinde as relatieRdnEinde, i.code as codeRelatieRdnEinde,
c.admhnd, e.naam as naamadhmhnd,
c.srtdoc, f.naam as docnaam, c.rubr8220datdoc, c.docoms, c.rubr8310aandgegevensinonderz, c.rubr8320datingangonderzoek, c.rubr8330dateindeonderzoek,
c.rubr8410onjuiststrijdigopenb, c.rubr8510ingangsdatgel, c.rubr8610datvanopneming, c.rubr6210datingangfamilierech, c.aktenr, c.anr, c.bsn, c.voornamen, c.predicaat, c.adellijketitel,
c.geslachtbijadellijketitelpre, c.voorvoegsel, c.scheidingsteken, c.geslnaamstam, c.datgeboorte, c.gemgeboorte, c.blplaatsgeboorte, c.omslocgeboorte, c.landgebiedgeboorte, c.geslachtsaand,
c.dataanv, c.gemaanv, c.blplaatsaanv, c.omslocaanv, c.landgebiedaanv, c.rdneinde, g.code as codeRdneinde, c.dateinde, c.gemeinde, c.blplaatseinde, c.omsloceinde, c.landgebiedeinde,
c.srtrelatie, h.naam as naamSrtRelatie, c.indouder1heeftgezag, c.indouder2heeftgezag, c.indderdeheeftgezag, c.indondercuratele
from ist.stapel a 

join ist.stapelrelatie b on b.stapel = a.id
join ist.stapelvoorkomen c on c.stapel = a.id 
join kern.relatie d on d.id = b.relatie
join kern.admhnd e1 on e1.id = c.admhnd
join kern.srtadmhnd e on e.id = e1.srt
left join kern.srtdoc f on f.id = c.srtdoc
left join kern.rdneinderelatie g on g.id = c.rdneinde
left join kern.srtrelatie h on h.id = c.srtrelatie
left join kern.rdneinderelatie i on i.id = d.rdneinde
join kern.betr j on j.relatie = d.id
where d.srt = 3
and j.rol <> 2
--and j.indouder is null
and categorie like '%9%'
 
