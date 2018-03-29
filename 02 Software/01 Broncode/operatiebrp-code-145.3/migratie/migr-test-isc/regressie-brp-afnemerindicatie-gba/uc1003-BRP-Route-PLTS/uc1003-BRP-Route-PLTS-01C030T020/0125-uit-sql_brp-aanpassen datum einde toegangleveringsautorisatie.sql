update autaut.toeganglevsautorisatie
set dateinde = 20160101
from autaut.levsautorisatie
where levsautorisatie.id = toeganglevsautorisatie.levsautorisatie
and levsautorisatie.naam = '999971';
