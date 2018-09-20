# UI bouwstraat #

## Achtergrond ##
Waar maven het bouwgereedschap van keus is voor het bouwen van java projecten, is het veel minder geschikt voor frontend toepassingen. Voor frontend toepassingen welke gebruik maken van html, javascript en ccs is het handiger voor development van andere gereedschappen gebruikt te maken. Voor het beheer project is voor een combinatie van nodejs, bower, gulp en karma gekozen.

## Opzet van de omgeving ##
De gekozen gereedschappen (nodejs, bower, gulp en karma) hebben elk een eigen taak in de bouwstraat.
### nodejs ###
Nodejs is de engine, vergelijkbaar met een jvm, waarop de bouwstraat draait. Gereedschappen als bower, gulp en karma maken gebruik van nodejs om hun werk te doen.
### Gulp ###
Om de bouw van Javascript te doen, code te controleren, testen uitvoeren en een server op te starten waarmee de toepassing kan draaien wordt Gulp gebruikt.
### Karma ###
Als laatste is daar Karma welke voor het draaien van testen wordt gebruikt. Gulp start de Karma test op. Karma zorgt ervoor dat de browser wordt gestart, de Javascript wordt geladen en de unittesten worden gedraaid. De unittest van Javascript worden met Jasmine geschreven.

## Uitbreiding ##
In de toekomst kan ook Protractor en cucumberjs worden gebruikt voor end-to-end testen. Voor nu is daar niet voor gekozen en wordt de huidige test tooling hiervoor gebruikt.

## Installeren omgeving ##
Om te kunnen ontwikkelen aan de beheer-ui is het nodig dat een aantal gereedschappen worden geïnstalleerd.

#### nodejs ####
Ga naar https://nodejs.org/ en klik op de groene install knop. Versie 0.12.2 wordt nu gebruikt. Volg verder de instructies om het te installeren.

#### Gulp ####
```
windows:\> npm install -g gulp

linux@unix:~$ sudo npm install -g gulp
```
## De omgeving gebruiken ##
#### Starten API laag ####
Voor windows:
```
windows:\> cd <brp-code>\beheer\beheer\beheer-api
windows:\> mvn clean install jetty:run
```

Voor linux:
```
linux@unix:~$ cd <brp-code>/beheer/beheer/beheer-api
linux@unix:~$ mvn clean install jetty:run
```
Indien je tegen de inmemory database wil draaien voeg dan aan het laatste commando ``` -Pinmemory ``` toe.

De beheer-api draait nu op http://localhost:8080

#### UI laag ####
Eenmalig
```
windows:\> cd <brp-code>\beheer\beheer\beheer-ui
windows:\> npm install

linux@unix:~$ cd /<brp-code>/beheer/beheer/beheer-ui
linux@unix:<brp-code>/beheer/beheer/beheer-ui$ npm install
```
De build zelf
```
windows:\> gulp

linux@unix:<brp-code>/beheer/beheer/beheer-ui$ gulp
```
De beheer-ui draait nu op http://localhost:8888
