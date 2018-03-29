Meta:
@status             Klaar
@sleutelwoorden     Vrij bericht
@regels             R2499

Narrative:
Voor elk ingaand of uitgaand vrij bericht in de BRP wordt een voorkomen van Bericht gearchiveerd. Voor het vrije bericht zijn dit de volgende berichten (bericht naam begint met "vrb_vrb");
Verwerk vrij bericht,
Stuur vrij bericht en
Stuur vrij bericht (resultaat)

De attributen worden als volgt gevuld (niet genoemde attributen zijn altijd 'leeg');

Soort bericht.Naam;
Voor het vrije bericht zijn de volgende soort bericht namen gereserveerd; Verwerk vrij bericht, Stuur vrij bericht en Stuur vrij bericht (resultaat).

Bericht.Richting;
Indien Stuur vrij bericht "Ingaand", anders "Uitgaand".

Bericht.Data;
De inhoud van het bericht zelf (inclusief protocol headers/envelope).

Bericht.Antwoord op;
Indien Stuur vrij bericht dan altijd 'leeg', anders het ID van het Bericht dat heeft geleid tot dit resultaat of verstuurd vrij bericht.
Voor het Stuur vrij bericht (resultaat) betreft dit het ID van het bijbehorende Stuur vrij bericht en voor het Verwerk vrij bericht betreft dit het ID van het bijbehorende Stuur vrij bericht.

Bericht.Zendende partij;
Vullen met de Partij.ID van de Partij uit stuurgegeven Bericht.Zendende partij in bericht. Is bij uitgaande berichten dus de BRP zelf.

Bericht.Zendende systeem;
Overnemen uit stuurgegeven Bericht.Zendende systeem in bericht. Is bij uitgaande berichten dus de BRP zelf.

Bericht.Ontvangende partij;
Indien Verwerk vrij bericht vullen met de Partij.ID van de Partij uit stuurgegeven Bericht.Ontvangende partij in bericht. In alle andere gevallen 'leeg'.

Bericht.Ontvangende systeem;
Indien Verwerk vrij bericht overnemen uit stuurgegeven Bericht.Ontvangende systeem in bericht. In alle andere gevallen 'leeg'.

Bericht.Referentienummer;
Overnemen uit stuurgegeven Bericht.Referentienummer in bericht.

Bericht.Cross referentienummer;
Indien Stuur vrij bericht (resultaat) overnemen uit stuurgegeven Bericht.Cross referentienummer in bericht. In alle andere gevallen 'leeg'.

Bericht.Datum/tijd verzending;
Overnemen uit stuurgegeven Bericht.Datum/tijd verzending in bericht.

Bericht.Datum/tijd ontvangst;
Indien Stuur vrij bericht vullen met Datum\tijd systeem van binnenkomst bericht in de BRP service. In alle andere gevallen 'leeg'.

Bericht.Verwerking;
Indien Stuur vrij bericht (resultaat), overnemen uit resultaat Bericht.Verwerking in (antwoord) bericht; 'Geslaagd' indien verwerking van bericht zonder problemen/fouten verloopt (geen meldingen), anders 'Foutief'; foutief indien er meldingen optreden (van het niveau 'Deblokkeerbaar' of 'Fout') en/of er technische fouten optreden. Anders altijd 'leeg'.

Bericht.Hoogste meldingsniveau;
Indien Stuur vrij bericht (resultaat), overnemen uit resultaat Bericht.Hoogste meldingsniveau in (antwoord) bericht; 'Geen' indien er geen meldingen (uit bedrijfsregels) zijn opgetreden of anders het hoogste meldingsniveau van de opgetreden meldingen (afkomstig uit bedrijfsregels). Anders altijd 'leeg'

Scenario: 1.    Archivering Foutpad Stuur vrij bericht
                LT: R2472_LT02, R2499_LT04
                -Verwacht resultaat:
                - Foutief


Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/1.1_Foutiefverzoek.xml

Then heeft het antwoordbericht verwerking Foutief
!-- Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                               |
| R2472     | Soort vrij bericht bestaat niet       |

!-- Controle R2499_LT04
!-- Then in ber heeft select * from ber.ber where ber.crossreferentienr='77398ffc-2bb4-65ae-7526-30453125c247' and ber.srt=160 de volgende gegevens:

Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 160                                  |
| richting              | 2                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
| zendendepartij        | 2001                                 |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | 32000                                |
| crossreferentienr     | 77398ffc-2bb4-65ae-7526-30453125c247 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
|   tsontv              | NULL                                 |
| verwerkingswijze      | NULL                                 |
| srtsynchronisatie     | NULL                                 |
| verwerking            | 2                                    |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | 5                                    |

Then tijdstipverzending in bericht is correct gearchiveerd
!-- Antwoord op handmatig gecontroleerd

Scenario: 2.    Archivering goedpad stuur vrij bericht
                LT: R2472_LT01, R2499_LT01, R2499_LT02, R2499_LT03, R2482_LT01
                -Verwacht resultaat:
                - Geslaagd
                - Archivering volgens R2499
                - Vulling asynchroon bericht volgens R2482_LT01

Given vrijbericht verzoek voor partij 'Gemeente VrijBerichtZender' uit bestand /testcases/VB0AV_Vrij_Bericht/Requests/1.2_Geslaagdverzoek.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controle R2499_LT02
!-- Then in ber heeft select * from ber.ber where ber.referentienr='77398ffc-2bb4-65ae-7526-30453125c255' and ber.srt=159 de volgende gegevens:

Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 159                                  |
| richting              | 1                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
| zendendepartij        | 32000                               |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | NULL                                 |
| referentienr          | 77398ffc-2bb4-65ae-7526-30453125c255 |
| crossreferentienr     | NULL                                 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
|-- tsontv            --|-- <wordt gecheckt in aparte stap>  --|
| verwerkingswijze      | NULL                                 |
| srtsynchronisatie     | NULL                                 |
| verwerking            | NULL                                 |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | NULL                                 |

Then tijdstipontvangst is actueel
!-- Controle op data is handmatig uitgevoerd
!-- Controle op antwoord op is handmatig uitgevoerd
!-- Controle op tsVerzending is handmatig uitgevoerd

!-- Controle R2499_LT03
!-- Then in ber heeft select * from ber.ber where ber.crossreferentienr='77398ffc-2bb4-65ae-7526-30453125c255' and ber.srt=160 de volgende gegevens:

Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 160                                  |
| richting              | 2                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
| zendendepartij        | 2001                                |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | 32000                                  |
| crossreferentienr     | 77398ffc-2bb4-65ae-7526-30453125c255 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
|   tsontv              | NULL                                 |
| verwerkingswijze      | NULL                                 |
| srtsynchronisatie     | NULL                                 |
| verwerking            | 1                                    |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | 1                                    |

!-- Controle op data is handmatig uitgevoerd
!-- Controle op antwoord op is handmatig uitgevoerd
!-- Controle op tsVerzending is handmatig uitgevoerd
!-- Controle op referentienummer is handmatig

When alle berichten zijn geleverd
Then is er een vrijbericht ontvangen voor partij Gemeente VrijBerichtOntvanger

!-- R2482_LT01
And is het ontvangen bericht voor expressie //brp:vrb_vrbVerwerkVrijBericht gelijk aan /testcases/VB0AV_Vrij_Bericht/Expecteds/Vrij_Bericht.xml

!-- Controle R2499_LT01
!-- Then in ber heeft select * from ber.ber where ber.srt=161 de volgende gegevens:
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 161                                  |
| richting              | 2                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
| zendendepartij        | 2001                                 |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | 32001                                 |
| crossreferentienr     | NULL                                 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
|   tsontv              | NULL                                 |
| verwerkingswijze      | NULL                                 |
| srtsynchronisatie     | NULL                                 |
| verwerking            | 1                                 |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | 1                                 |

!-- Controle op data is handmatig uitgevoerd
!-- Controle op antwoord op is handmatig uitgevoerd
!-- Controle op tsVerzending is handmatig uitgevoerd
!-- Controle op referentienummer is handmatig

