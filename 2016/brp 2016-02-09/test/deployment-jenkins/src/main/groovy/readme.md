# Readme

Het bijgaande Groovy script kan gebruikt worden om de versie(s) op te vragen
 van de gedeployde artifacten. Door middel van een request naar de beschikbare
 versie.html.

Het script kan wordne uitgevoerd vanaf de commandline met een recente Groovy
versie. Het is getest met Groovy 2.2, maar werkt waarschijnlijk ook met eerdere
versies (2.x).

Gebruik het als volgt:

    $ groovy checkDeploy.groovy

In het script staan twee variablen, de lokatie van de gebruikte keystore
(`pt-links-mGBA_client.jks`) en het wachtwoord voor de keystore.
