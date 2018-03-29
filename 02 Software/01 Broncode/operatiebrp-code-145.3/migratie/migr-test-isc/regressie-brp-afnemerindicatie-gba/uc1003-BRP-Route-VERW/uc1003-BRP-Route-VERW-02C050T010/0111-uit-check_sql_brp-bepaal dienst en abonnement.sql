select partij.id partij_id
,      toeganglevsautorisatie.id toeganglevering_id
,      levsautorisatie.id autorisatie_id
,      dienst.id dienst_id
from   kern.partij
join   kern.partijrol on partijrol.partij = partij.id
join   autaut.toeganglevsautorisatie on toeganglevsautorisatie.geautoriseerde = partijrol.id
join   autaut.levsautorisatie on toeganglevsautorisatie.levsautorisatie = levsautorisatie.id
join   autaut.dienstbundel on dienstbundel.levsautorisatie = levsautorisatie.id
join   autaut.dienst on dienst.dienstbundel = dienstbundel.id
where  partij.code = '$$partijcode$$'
and    dienst.srt = 20