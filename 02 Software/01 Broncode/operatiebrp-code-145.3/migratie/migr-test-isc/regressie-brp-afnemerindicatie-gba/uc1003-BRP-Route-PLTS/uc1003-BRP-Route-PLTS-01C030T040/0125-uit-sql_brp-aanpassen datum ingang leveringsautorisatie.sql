update autaut.levsautorisatie
set datingang = 20300101
from autaut.toeganglevsautorisatie
where levsautorisatie.id = toeganglevsautorisatie.levsautorisatie
and levsautorisatie.naam = '999971';
