update autaut.levsautorisatie
set dateinde = 20160101
from autaut.toeganglevsautorisatie
where levsautorisatie.id = toeganglevsautorisatie.levsautorisatie
and levsautorisatie.naam = '999971';
