update autaut.dienst
set dateinde = 20160101
from autaut.dienstbundel, autaut.levsautorisatie
where dienst.dienstbundel = dienstbundel.id and
dienstbundel.levsautorisatie = levsautorisatie.id and
levsautorisatie.naam = '999971' and
dienst.srt = '3';