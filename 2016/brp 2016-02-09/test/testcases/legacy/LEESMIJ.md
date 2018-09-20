
## Legacy converter
Om het gebruik van de ARTEngine uit te faseren en de in gebruik name van de
Funqmaachine te bevorderen, is deze converter geschreven. Op basis van de
bestaande ART-excelbestanden worden er JBehave stories gemaakt die dezelfde
functionaliteit hebben.

## Conversie
De conversie kopieert alle voor een ART benodigde artifacten (Excel, expected
bestanden) naar een directory. Hierdoor is op een plek alle benodigdheden te vinden.

Het proces is grof weg:

1.  Lees de excel sheet
1.  groepeer de regels op basis van de kolom 'SendRequest', een scenario begint bij een regel
    waarbij deze kolom een waarde heeft
1.  Vertaal deze naar JBehave steps
1.  kopieer benodige bestanden
1.  vertaal bestanden naar Freemarker compatible stijl

### Specifieke zaken
Bij de conversie wordt rekening gehouden met de volgende (hoofdzaken):

+   Redirection in de ARTs, het gebruik van resources uit een andere ART dan
    de huidige ART
+   Delays, het wachten op leveringen. De Funqmachine heeft een andere wacht/
    retry mechanisme dan de ARTEngine. Om dit verschil te overkomen, moet er
    voor de Funqmachine gewacht worden tot alle leveringen zijn gedaan.
+   De Funqmachine gebruikt Freemarker als template engine. Deze is strikter
    in afwezig zijn van waardes en het formaat van de sleutels die worden
    gebruikt in een template dan SoapUI. Sleutels worden omgezet naar
    Freemarker compatible stijl.
+   De ART-sheet in het excelbestand wordt verwijdert. Formules die naar deze
    sheet verwijzen worden vervangen door de waarde van de formule.

### Valkuilen

#### Templates
De templates voor SOAP requests hebben vanuit de ART een <soap/> Envelope en
Header xml-tag. Deze moeten worden verwijderd, omdat de Funqmachine deze zelf
toevoegt als er een request wordt gedaan.

De Freemarker directive voor __objectsleutel__ heeft twee waardes nodig. Die
van _bsn_ kan worden afgeleid uit een template. Die van _partij_ is voor de
conversie onbekend, en moet dus met de hand worden toegevoegd (zeer
waarschijnlijk dezelfde waarde als voor verzendende partij).

#### Database / Queries
Sommige validatie queries verwachten expliciet geen resultaat. De Funqmachine
wacht voor database queries juist _wel_ op een resultaat. Door de manier van
valideren in de ARTEngine was dit voorheen geen probleem. Mogelijk moet
hiervoor een oplossing worden gemaakt.
__Opmerking:__ Zeer waarschijnlijk gaat het alleen om validatie van geleverde
berichten in de Ber.Ber tabel, wat vervangen kan worden door een "verwacht
geen leveringen"-step in de Funqmachine.
