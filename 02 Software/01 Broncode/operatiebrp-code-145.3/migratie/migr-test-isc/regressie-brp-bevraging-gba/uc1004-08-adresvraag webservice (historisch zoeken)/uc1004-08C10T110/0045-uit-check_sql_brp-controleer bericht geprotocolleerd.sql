select leva.id as autorisatie, dienst.id as dienst, partij.id as partij 
from autaut.dienst dienst 
join autaut.dienstbundel db on db.id = dienst.dienstbundel 
join autaut.levsautorisatie leva on leva.id = db.levsautorisatie 
join autaut.toeganglevsautorisatie toegang on toegang.levsautorisatie = leva.id 
join kern.partijrol on partijrol.id = toegang.geautoriseerde 
join kern.partij on partij.id = partijrol.partij
where dienst.srt = 5; 