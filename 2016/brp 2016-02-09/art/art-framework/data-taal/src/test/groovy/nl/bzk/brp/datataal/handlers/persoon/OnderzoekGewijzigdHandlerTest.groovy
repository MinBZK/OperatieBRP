package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class OnderzoekGewijzigdHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void "test onderzoek met wijziging"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Jan', 'Piet'
            geslachtsnaam 'Jansen'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
    }
}

slaOp(persoon)
persoon = Persoon.metId(persoon.ID)
Persoon.nieuweGebeurtenissenVoor(persoon) {

    onderzoekAangemaakt(partij: 34401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart',
            verwachteAfhandelDatum: '2015-04-01')
    }

    onderzoekGewijzigd(partij: 34401, registratieDatum: 20150102) {
        wijzigOnderzoek(tsDatum: '2015-01-01', wijzigingsDatum: '2015-01-01', omschrijving: 'Onderzoek gewijzigd', aanvangsDatum: '2014-01-01', verwachteAfhandelDatum: '2014-04-01')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 2

        def actueel = persoon.onderzoeken[0].onderzoek.onderzoekHistorie.actueleRecord

        assert actueel.datumAanvang.waarde == 20140101
        assert actueel.omschrijving.waarde == 'Onderzoek gewijzigd'
        assert actueel.verwachteAfhandeldatum.waarde == 20140401
        assert actueel.datumEinde == null

        def historie = persoon.onderzoeken[0].onderzoek.onderzoekHistorie.sort { it.verantwoordingInhoud.tijdstipRegistratie.naarDatum().waarde }
        assert historie[0].verantwoordingInhoud.tijdstipRegistratie.naarDatum().waarde == 20150101
        assert historie[1].verantwoordingInhoud.tijdstipRegistratie.naarDatum().waarde == 20150102
    }


    @Test
    void "test onderzoek met groep in onderzoek"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Jan', 'Piet'
            geslachtsnaam 'Jansen'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
    }

    verhuizing(partij: 'Gemeente Gorinchem', aanvang: 19841023) {
        naarGemeente 'Gorinchem', straat: 'Dorpelstraat', nummer: 15, postcode: '4208AA', woonplaats: 'Gorinchem'
    }
}

slaOp(persoon)

persoon = Persoon.metId(persoon.ID)

Persoon.nieuweGebeurtenissenVoor(persoon) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-01')

        gegevensInOnderzoek('Persoon.Geboorte.BuitenlandsePlaats')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 1

        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumAanvang.waarde == 20150101
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].omschrijving.waarde == 'Onderzoek is gestart op adres groep'
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].verwachteAfhandeldatum.waarde == 20150401
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumEinde == null

        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek.size() == 1
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].voorkomenSleutelGegeven != null
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].element != null
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].elementIdentificatie != null
    }

    @Test
    void "test onderzoek met meerdere gegevens in onderzoek"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Jan', 'Piet'
            geslachtsnaam 'Jansen'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
    }

    verhuizing(partij: 'Gemeente Gorinchem', aanvang: 19841023) {
        naarGemeente 'Gorinchem', straat: 'Dorpelstraat', nummer: 15, postcode: '4208AA', woonplaats: 'Gorinchem'
    }
}

slaOp(persoon)

persoon = Persoon.metId(persoon.ID)

Persoon.nieuweGebeurtenissenVoor(persoon) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-01')

        gegevensInOnderzoek('Persoon.Geboorte.BuitenlandsePlaats')
        gegevensInOnderzoek('Persoon.Geboorte.Datum')
        gegevensInOnderzoek('Persoon.Adres.Standaard')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 1

        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumAanvang.waarde == 20150101
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].omschrijving.waarde == 'Onderzoek is gestart op adres groep'
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].verwachteAfhandeldatum.waarde == 20150401
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumEinde == null

        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek.size() == 3
    }

    @Test
    void "test onderzoek met niet bestaande gegevens in onderzoek"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Jan', 'Piet'
            geslachtsnaam 'Jansen'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
    }


    verhuizing(partij: 'Gemeente Gorinchem', aanvang: 19841023) {
        naarGemeente 'Gorinchem', straat: 'Dorpelstraat', nummer: 15, postcode: '4208AA', woonplaats: 'Gorinchem'
    }
}

slaOp(persoon)

persoon = Persoon.metId(persoon.ID)

Persoon.nieuweGebeurtenissenVoor(persoon) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-01')

        gegevensInOnderzoek('Persoon.Identiteit')
        gegevensInOnderzoek('Persoon.Identificatienummers')
        gegevensInOnderzoek('Persoon.ObjectSleutel')
        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 1

        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumAanvang.waarde == 20150101
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].omschrijving.waarde == 'Onderzoek is gestart op adres groep'
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].verwachteAfhandeldatum.waarde == 20150401
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumEinde == null

        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek.size() == 4
    }

    //@Test
    void "test nieuw onderzoek persoon in onderzoek"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        geslacht 'MAN'
        namen {
            voornamen 'Jan', 'Piet'
            geslachtsnaam 'Jansen'
        }
        identificatienummers bsn: 123434538, anummer: 8934753756
    }

    verhuizing(partij: 'Gemeente Gorinchem', aanvang: 19841023) {
        naarGemeente 'Gorinchem', straat: 'Dorpelstraat', nummer: 15, postcode: '4208AA', woonplaats: 'Gorinchem'
    }
}

slaOp(persoon)
persoon = Persoon.metId(persoon.ID)
Persoon.nieuweGebeurtenissenVoor(persoon) {

    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart op geboorte datum',
            verwachteAfhandelDatum: '2015-04-01')

        persoonInOnderzoek()
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 1

        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumAanvang.waarde == 20150101
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].omschrijving.waarde == 'Onderzoek is gestart op geboorte datum'
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].verwachteAfhandeldatum.waarde == 20150401
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumEinde == null

        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek.size() == 0
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].voorkomenSleutelGegeven != null
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].element != null
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].elementIdentificatie != null

        assert persoon.onderzoeken[0].onderzoek.personenInOnderzoek.size() == 0
        assert persoon.onderzoeken[0].onderzoek.personenInOnderzoek[0].persoon != null
    }
}
