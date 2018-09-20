Meta:
@sprintnummer       74
@epic               Change yyyynn: CorLev - Levering Autorisatie
@auteur             miser
@jiraIssue          TEAMBRP-1498
@status             Klaar
@regels             VR00081

Narrative:  Als Stelselbeheerder wil ik dat afnemers alleen een Datum aanvang geldigheid geleverd krijgen indien daarvoor gerechtigd

Scenario:   1.  Er wordt een naamswijziging gedaan op een bestaande persoon. De resulterende mutatieberichten worden voor verschillende
                abonnementen geverifiëerd.

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/abo_formele_hist_nee_voor_groep_samengesteldenaam, /levering_autorisaties/abo_alleen_identificerende_groepen_en_bijbehorende_expressies
Given de standaardpersoon Remi met bsn 939930377 en anr 6919704850 met extra gebeurtenissen:
    naamswijziging(registratieDatum: 20150617) {
          geslachtsnaam(1) wordt stam:'Zonnig', voorvoegsel:'het'
    }

Given de persoon beschrijvingen:
def greg  = uitDatabase bsn: 939930377
Persoon.nieuweGebeurtenissenVoor(greg) {
    naamswijziging(partij: 34401, aanvang: 20150618, toelichting: 'Bertus Staigerpaip is cooler dan Albert Laar', registratieDatum: 20150618) {
      geslachtsnaam([stam: 'Zonnig']).wordt([stam:'Staigerpaip'])
    }
}
slaOp(greg)


When voor persoon 939930377 wordt de laatste handeling geleverd

!-- Abonnement met autorisatie op DAG (Persoon.Geslachtsnaamcomponent.DatumAanvangGeldigheid)
When het mutatiebericht voor leveringsautorisatie Abo formele hist nee voor groep samengesteldenaam is ontvangen en wordt bekeken
Then is de aanwezigheid van 'datumAanvangGeldigheid' in 'samengesteldeNaam' nummer 1 WAAR

!-- Abonnement zonder autorisatie op DAG.
When het mutatiebericht voor leveringsautorisatie Abo alleen identificerende groepen en bijbehorende expressies is ontvangen en wordt bekeken
Then is de aanwezigheid van 'datumAanvangGeldigheid' in 'samengesteldeNaam' nummer 1 ONWAAR
