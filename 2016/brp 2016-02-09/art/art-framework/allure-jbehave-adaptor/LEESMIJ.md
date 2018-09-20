
# JBehave adapter for Allure
Dit is een specifieke adapter voor het [Allure framework](http://allure.qatools.ru/).
Deze adapter maakt het mogelijk om JBehave rapportage te laten genereren in 
het formaat dat door Allure wordt gebruikt.

Door de `allure-maven-plugin` te configureren in de modules die testgevallen 
uitvoeren, kan deze rapportage worden gegenereerd. Momenteel is deze plugin
afhankelijk van Maven 3.1 en hoger, dus is deze plugin in de module 
`brp-art-testcases-parent` uitgeschakeld. 

Een andere punt is dat de XML dependencies van Allure conflicteren met die van
de FunqMachinator dependencies. Als deze rapportage wordt ingeschakeld moet 
er worden uitgezocht welke xml dependencies er moeten overblijven.
