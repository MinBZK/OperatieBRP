Meta:

@status             Klaar
@usecase            LV.0.MB, LV.0.MB.VB
@regels             R1320
@sleutelwoorden     Maak BRP bericht, Maak mutatiebericht

Narrative:
Met de 'verwerkingssoort' wordt de Afnemer per geleverd object en groepsvoorkomen geïnformeerd wat er in de BRP is
'gemuteerd' door de onderhanden handeling. Voor objecten wordt dit afgeleid op basis van de verwerkingssoorten van
ALLE groepsvoorkomens (zie R1317 - Bepalen verwerkingssoort van groepsvoorkomens) die zich in het object bevinden, op de volgende manier:

Ga uit van de 'gereconstrueerde persoon' na de onderhanden handeling (robuustheid, 'Gereconstrueerde persoon na Administratieve handeling' (R1556))

Ga uit van alle groepsvoorkomens die zich in het object bevinden (in gegevensstructuur, niet in de berichtstructuur.
Ofwel: geen rekening houden met onderlinge nesting van objecten in het bericht; maar hanteer wel het ongefilterde object!)

Neem echter geen groepsvoorkomens mee die geen deel zijn van de juridische persoonslijst.
Ofwel specifiek: Bij een betrokken persoon van de Soort Betrokkenheid = "Ouder" (O) en "Partner" (P) zijn alleen groepen Persoon.Identificatienummers,
Persoon.Geboorte , Persoon.Geslachtsaanduiding en Persoon.Samengestelde naam relevant;
Bij een betrokken Persoon van de Soort Betrokkenheid = "Kind" (K) zijn alleen groepen Persoon.Identificatienummers, Persoon.Geboorte en Persoon.Samengestelde naam relevant.

Neem geen groepen mee die historiepatroon "Geen" hebben. (Dit zijn de groepen "Identititeit" die geen actieverwijzingen hebben,
waardoor de verwerkingssoort eigenlijk niet bepaald kan worden. Bovendien worden deze in de berichten 'platgeslagen' met het object zelf.
Aandachtspunt is de afwijkende groep Betrokkenheid.Identiteit met historiepatroon "Formele bestaansperiode".
Deze moet wel worden meegenomen in de bepaling van de verwerkingssoort)

Voor objecten is de afleiding als volgt:
De verwerkingssoort van de onderhanden Administratieve handeling (in het bericht boven de 'bijgehouden personen') is altijd 'Toevoeging'
De verwerkingssoort van de bijgehouden Persoon (de 'hoofdpersoon') is altijd 'Wijziging'
Als ALLE groepsvoorkomens onder een object de verwerkingssoort 'Referentie' hebben dan is de verwerkingssoort van het object 'Referentie'.
Als er tenminste één groepsvoorkomen is met verwerkingssoort 'Identificatie' en alle overige groepsvoorkomens hebben verwerkingssoort
'Identificatie' of 'Referentie', dan is de verwerkingssoort van het object 'Identificatie'
Als ALLE groepsvoorkomens onder een object de verwerkingssoort 'Toevoeging' hebben dan is de verwerkingssoort van het object 'Toevoeging'.
Als tenminste één groepsvoorkomen verwerkingssoort 'Vervallen' heeft en alle overige voorkomens van groepen met historie zijn al eerder vervallen
(d.w.z. Datum/tijd verval en Actie verval zijn gevuld), dan is de verwerkingssoort van het object 'Vervallen'.
NB: Groepen zonder historie bij deze stap dus negeren.

In alle andere gevallen is de verwerkingssoort van het object 'Wijziging'.

(Toelichting 1 : De verwerkingssoort beschrijft wat er gebeurd is vanuit het gezichtspunt van de BRP en kan dus per handeling worden afgeleid.)

(Toelichting 2: Objecten met verwerkingssoort 'Referentie' komen alleen in een mutatiebericht voor als het ongewijzigde, wettelijk verplicht te
leveren gegevens bevat of omdat een bijgehouden Onderzoek naar een groep in dat object verwijst.)


Scenario: 1.   Onderzoek gestart op adres hoofdpersoon deze zit in de doelbinding.
                LT: R1320_LT01, R1320_LT02, R1320_LT03
                 Verwacht resultaat R1320_LT01: Mutatiebericht met vulling
                 - Verwerkingssoort administratieve handeling = toevoeging
                 - Verwerkingssoort bijgehouden persoon = wijziging
                 - Adres heeft verwerkingssoort referentie
                 - Verwerkingssoort identificerende groepen = identificatie
                 - GEEN andere groepen aanwezig

Given leveringsautorisatie uit autorisatie/Abo_geen_popbep_doelbinding_Haarlem

Given persoonsbeelden uit specials:MaakBericht/R1320_Anne_Bakker_Onderzoek_Aanvang_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then heeft het bericht 2 groepen 'onderzoek'

!-- R1320_LT01 Verwerkingssoort objecttype 'Administratievehandeling' = 'Toevoeging'
Then is er voor xpath //brp:synchronisatie[contains(@brp:verwerkingssoort, 'Toevoeging')] een node aanwezig in het levering bericht

!-- R1320_LT01 Verwerkingssoort objecttype 'Hoofdpersoon' = 'Wijziging'
Then is er voor xpath //brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon[contains(@brp:verwerkingssoort, 'Wijziging')] een node aanwezig in het levering bericht

!-- R1320_LT02 Verwerkingssoort bij groepsvoorkomens is enkel 'Referentie'
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep 	                | nummer | verwerkingssoort |
| adres                     | 1      | Referentie       |

!-- R1320_LT03 Verwerkingssoort bij groepsvoorkomens is enkel 'Toevoeging'
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep     | nummer | verwerkingssoort |
| onderzoek | 1      | Toevoeging       |
| onderzoek | 2      | Toevoeging       |

Then is het synchronisatiebericht gelijk aan expecteds/R1320_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 2.    Voltrekking huwelijk in Nederland. Controle op de Verwerkingssoort van de objecten.
                 LT: R1320_LT04, R1320_LT07
                 voltrekking huwelijk in Nederland
                 Verwacht resultaat: Mutatiebericht met vulling
                 - Verwerkingssoort administratieve handeling   = toevoeging
                 - Verwerkingssoort bijgehouden persoon         = wijziging
                 - Verwerkingssoort betrokken persoon           = Identificatie (want alleen groepen met Identificatie)
                 - identificerende groepen hoofdpersoon aanwezig
                 - identificerende groepen betrokkenheid aanwezig
                 - gemuteerde inhoudelijke groep, huwelijk, aanwezig

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003

When voor persoon 422531881 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

!-- R1320_LT04 Verwerkingssoort bij object = Identificatie
!-- Alle onderliggende voorkomens bij het object hebben verwerkingssoort 'Identificatie'
Then is er voor xpath //brp:huwelijk/brp:betrokkenheden/brp:partner/brp:persoon[contains(@brp:verwerkingssoort, 'Identificatie')] een node aanwezig in het levering bericht

Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep                | nummer | verwerkingssoort |
| synchronisatie       | 1      | Toevoeging       |
| persoon              | 1      | Wijziging        |
| identificatienummers | 1      | Identificatie    |
| samengesteldeNaam    | 1      | Identificatie    |
| geboorte             | 1      | Identificatie    |
| geslachtsaanduiding  | 1      | Identificatie    |
| persoon              | 2      | Identificatie    |
| identificatienummers | 2      | Identificatie    |
| samengesteldeNaam    | 2      | Identificatie    |
| geboorte             | 2      | Identificatie    |
| geslachtsaanduiding  | 2      | Identificatie    |
| huwelijk             | 1      | Toevoeging       |
| partner              | 1      | Toevoeging       |

Then is het synchronisatiebericht gelijk aan expecteds/R1320_expected_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3.    Beeindiging Huwelijk in Nederland, mutatiebericht wordt geleverd. Controle op de Verwerkingssoort van de
                objecten.
                 LT: R1320_LT07
                 Beeindiging Huwelijk
                 Verwacht resultaat: Mutatiebericht met vulling
                 - Verwerkingssoort administratieve handeling   = toevoeging
                 - Verwerkingssoort bijgehouden persoon         = wijziging
                 - Verwerkingssoort betrokken persoon           = wijziging
                 - identificerende groepen hoofdpersoon aanwezig
                 - identificerende groepen betrokkenheid aanwezig
                 - gemuteerde inhoudelijke groep, huwelijk, aanwezig
                 - geen andere groepen aanwezig (controle op adres)

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding

Given persoonsbeelden uit specials:MaakBericht/R1320_Anne_Bakker_GBA_Bijhouding_Beeindiging_huwelijk_xls

When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

!-- R1320_LT07
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep    | nummer | verwerkingssoort |
| partner  | 1      | Referentie        |
| huwelijk | 1      | Wijziging        |

Then is het synchronisatiebericht gelijk aan expecteds/R1320_expected_scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon
