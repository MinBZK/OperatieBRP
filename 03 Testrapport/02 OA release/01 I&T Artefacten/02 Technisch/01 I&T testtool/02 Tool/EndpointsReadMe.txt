Endpoints.properties wordt gebruikt om te bepalen waar een BRP request heen gestuurd moet worden.
Eerst wordt met de endpointPlaceholder bepaalt wat voor soort request het is:
van het request wordt de 4e regel genomen, deze wordt gestript van de <>, en de : wordt vervangen
door een _.
Het resultaat komt overeen met één van de namen die vervolgens in dit document staan,
dus bijvoorbeeld brp_lvg_bvgGeefDetailsPersoon.
Achter deze namen staat het endpoint waar het betreffende soort request heen gestuurd
moet worden.