select abonnement.id, abonnement.naam, categoriedienst.naam, effectafnemerindicaties.naam, dienst.attenderingscriterium
from autaut.abonnement
join autaut.dienst on dienst.abonnement = abonnement.id
join autaut.catalogusoptie on catalogusoptie.id = dienst.catalogusoptie
join autaut.categoriedienst on categoriedienst.id =  catalogusoptie.categoriedienst
left outer join autaut.effectafnemerindicaties on effectafnemerindicaties.id = catalogusoptie.effectafnemerindicaties