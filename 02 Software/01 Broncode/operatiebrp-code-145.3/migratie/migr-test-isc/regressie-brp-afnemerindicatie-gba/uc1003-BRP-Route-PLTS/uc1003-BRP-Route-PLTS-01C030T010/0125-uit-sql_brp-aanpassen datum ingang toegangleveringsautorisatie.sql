update autaut.toeganglevsautorisatie
set datingang = 20300101
from autaut.levsautorisatie
where levsautorisatie.id = toeganglevsautorisatie.levsautorisatie
and levsautorisatie.naam = '999971';
