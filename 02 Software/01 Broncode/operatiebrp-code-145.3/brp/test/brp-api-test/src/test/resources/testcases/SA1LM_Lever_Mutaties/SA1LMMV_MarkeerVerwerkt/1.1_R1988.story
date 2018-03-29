Meta:
@status             Klaar
@regels             R1988
@sleutelwoorden     Markeer administratieve handeling als verwerkt

Narrative:
        Het attribuut Administratieve handeling.Tijdstip levering wordt gevuld met het tijdstip waarop ALLE berichten
        die naar aanleiding van die Administratieve handeling geleverd moeten worden,
        zijn aangemaakt (of waarvan is geconcludeerd dat er geen bericht aangemaakt kan worden,
        omdat er geen te leveren inhoud van het bericht is).

Scenario: 1.    Markeer een levering als verwerkt, als alle berichten die naar aanleiding van de handeling zijn aangemaakt
                LT: R1988_LT01
                Verwacht resultaat: Administratieve handeling.Tijdstip levering wordt gevuld met het tijdstip waarop ALLE berichten zijn aangemaakt

Given leveringsautorisatie uit autorisatie/R1988_levering_obv_doelbinding

Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

When voor persoon 590984809 wordt de laatste handeling geleverd

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie levering aan meerdere partijen is ontvangen en wordt bekeken

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie levering aan meerdere partijen is ontvangen en wordt bekeken

When het volledigbericht voor partij Gemeente Haarlem en leveringsautorisatie levering aan meerdere partijen is ontvangen en wordt bekeken

Then is het aantal ontvangen berichten 3

!-- controleer dat Administratieve handeling.Tijdstip levering wordt gevuld met het tijdstip waarop ALLE berichten zijn aangemaakt
!-- controle op status "Geleverd" voor de administratieve handeling
!-- controleer dat kern.admhnd.tslev groter is dan het meest recente ber.ber.tsverzending
!--FIXME PendingStep, step maken om dit te controleren in stub
!-- Then in kern heeft select adm.*, ber.tsverzending from kern.admhnd adm join ber.ber ber on (adm.id = ber.admhnd) where (adm.tslev > ber.tsverzending and ber.ontvangendepartij=adm.partij) de volgende gegevens:
!-- | veld   | waarde |
!-- | srt    | 30     |
!-- | partij | 178    |

!-- Then in kern heeft select statuslev from kern.admhnd where partij=(select id from kern.partij where naam='Gemeente BRP 1') de volgende gegevens:
!-- | veld      | waarde |
!-- | statuslev | 4   |
