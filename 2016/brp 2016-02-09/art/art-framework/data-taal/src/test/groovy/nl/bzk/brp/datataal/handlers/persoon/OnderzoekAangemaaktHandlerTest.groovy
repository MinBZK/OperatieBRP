package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class OnderzoekAangemaaktHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void "test nieuw onderzoek"() {
        String dsl = """
persoon = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind', registratieDatum: 19660514) {
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

    onderzoekAangemaakt(partij: 34401, registratieDatum: 20141231) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart',
            verwachteAfhandelDatum: '2015-04-01')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 1

        def onderzoek = persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0]
        assert onderzoek.datumAanvang.waarde == 20150101
        assert onderzoek.omschrijving.waarde == 'Onderzoek is gestart'
        assert onderzoek.verwachteAfhandeldatum.waarde == 20150401
        assert onderzoek.datumEinde == null

        // Deze gegevens komen uit de stamgegevens
        assert onderzoek.verantwoordingInhoud.partij != null
        assert onderzoek.verantwoordingInhoud.partij.waarde.code.waarde == 34401
        assert onderzoek.verantwoordingInhoud.partij.waarde.naam.waarde == 'Gemeente Utrecht'
        assert onderzoek.verantwoordingInhoud.tijdstipRegistratie.naarDatum().waarde == 20141231
    }

    @Test
    void "test onderzoek met attribuut in onderzoek"() {
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
            omschrijving: 'Onderzoek is gestart op geboorte datum',
            verwachteAfhandelDatum: '2015-04-01')

        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 1

        def onderzoek = persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0]
        assert onderzoek.datumAanvang.waarde == 20150101
        assert onderzoek.omschrijving.waarde == 'Onderzoek is gestart op geboorte datum'
        assert onderzoek.verwachteAfhandeldatum.waarde == 20150401
        assert onderzoek.datumEinde == null

        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek.size() == 1
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].voorkomenSleutelGegeven != null
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].element != null
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].elementIdentificatie != null
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

        def onderzoek = persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0]
        assert onderzoek.datumAanvang.waarde == 20150101
        assert onderzoek.omschrijving.waarde == 'Onderzoek is gestart op adres groep'
        assert onderzoek.verwachteAfhandeldatum.waarde == 20150401
        assert onderzoek.datumEinde == null

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

        def onderzoek = persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0]
        assert onderzoek.datumAanvang.waarde == 20150101
        assert onderzoek.omschrijving.waarde == 'Onderzoek is gestart op adres groep'
        assert onderzoek.verwachteAfhandeldatum.waarde == 20150401
        assert onderzoek.datumEinde == null

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

        def onderzoek = persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0]
        assert onderzoek.datumAanvang.waarde == 20150101
        assert onderzoek.omschrijving.waarde == 'Onderzoek is gestart op adres groep'
        assert onderzoek.verwachteAfhandeldatum.waarde == 20150401
        assert onderzoek.datumEinde == null

        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek.size() == 4
    }

    @Test
    void "test persoon opslaan laden en onderzoek toepassen"() {
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

opgeslagen = Persoon.metId(persoon.ID)

Persoon.nieuweGebeurtenissenVoor(opgeslagen) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-01')

        gegevensInOnderzoek('Persoon.Adres.Standaard')
    }
}

slaOp(opgeslagen)
"""

        def opgeslagen = new PersoonDSLExecutor().execute(dsl).opgeslagen as PersoonHisVolledigImpl
        assert opgeslagen != null
        assert opgeslagen.ID != null
        assert opgeslagen.onderzoeken.size() == 1
        assert opgeslagen.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 1

        def onderzoek = opgeslagen.onderzoeken[0].onderzoek.onderzoekHistorie[0]
        assert onderzoek.datumAanvang.waarde == 20150101
        assert onderzoek.omschrijving.waarde == 'Onderzoek is gestart op adres groep'
        assert onderzoek.verwachteAfhandeldatum.waarde == 20150401
        assert onderzoek.datumEinde == null

        assert opgeslagen.onderzoeken[0].onderzoek.gegevensInOnderzoek.size() == 1
        assert opgeslagen.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].voorkomenSleutelGegeven != null
        assert opgeslagen.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].element != null
        assert opgeslagen.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].elementIdentificatie != null
    }

    @Test
    void "test meerdere onderzoeken stap 1"() {
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
Persoon.nieuweGebeurtenissenVoor(persoon) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-01')

        gegevensInOnderzoek('Persoon.Adres.Standaard')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon != null
        assert persoon.ID != null
        assert persoon.onderzoeken.size() == 1

        persoon.onderzoeken.each { onderzoek ->
            assert onderzoek.onderzoek.gegevensInOnderzoek.size() == 1
        }
    }

    @Test
    void "test meerdere onderzoeken stap 2"() {
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
Persoon.nieuweGebeurtenissenVoor(persoon) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-01')

        gegevensInOnderzoek('Persoon.Adres.Standaard')
    }
}

slaOp(persoon)
opgeslagen = Persoon.metId(persoon.ID)
Persoon.nieuweGebeurtenissenVoor(opgeslagen) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-02',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-02')

        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}

slaOp(opgeslagen)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).opgeslagen as PersoonHisVolledigImpl
        assert persoon != null
        assert persoon.ID != null
        assert persoon.onderzoeken.size() == 2

        persoon.onderzoeken.each { onderzoek ->
            assert onderzoek.onderzoek.gegevensInOnderzoek.size() == 1
        }
    }

    @Test
    void "test meerdere onderzoeken stap 3"() {
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
Persoon.nieuweGebeurtenissenVoor(persoon) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-01')

        gegevensInOnderzoek('Persoon.Adres.Standaard')
    }
}

slaOp(persoon)
opgeslagen = Persoon.metId(persoon.ID)
Persoon.nieuweGebeurtenissenVoor(opgeslagen) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-02',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-02')

        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}

slaOp(opgeslagen)
derdePersoon = Persoon.metId(opgeslagen.ID)
Persoon.nieuweGebeurtenissenVoor(derdePersoon) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-03',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-03')

        gegevensInOnderzoek('Persoon.Geboorte.BuitenlandsePlaats')
    }
}

slaOp(derdePersoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).derdePersoon as PersoonHisVolledigImpl
        assert persoon != null
        assert persoon.ID != null
        assert persoon.onderzoeken.size() == 3

        persoon.onderzoeken.each { onderzoek ->
            assert onderzoek.onderzoek.gegevensInOnderzoek.size() == 1
        }
    }

    @Test
    void "test meerdere onderzoeken stap 4"() {
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
Persoon.nieuweGebeurtenissenVoor(persoon) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-01')

        gegevensInOnderzoek('Persoon.Adres.Standaard')
    }
}

slaOp(persoon)
opgeslagen = Persoon.metId(persoon.ID)
Persoon.nieuweGebeurtenissenVoor(opgeslagen) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-02',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-02')

        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}

slaOp(opgeslagen)
derdePersoon = Persoon.metId(opgeslagen.ID)
Persoon.nieuweGebeurtenissenVoor(derdePersoon) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-03',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-03')

        gegevensInOnderzoek('Persoon.Geboorte.BuitenlandsePlaats')
    }
}

slaOp(derdePersoon)
derdePersoon = Persoon.metId(derdePersoon.ID)
Persoon.nieuweGebeurtenissenVoor(derdePersoon) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-04',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-04')

        gegevensInOnderzoek('Persoon.Geboorte.Woonplaatsnaam')
    }
}

slaOp(derdePersoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).derdePersoon as PersoonHisVolledigImpl
        assert persoon != null
        assert persoon.ID != null
        assert persoon.onderzoeken.size() == 4

        persoon.onderzoeken.each { onderzoek ->
            assert onderzoek.onderzoek.gegevensInOnderzoek.size() == 1
        }
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

    onderzoekAangemaakt(partij: 34401) {
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

        def onderzoek = persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0]
        assert onderzoek.datumAanvang.waarde == 20150101
        assert onderzoek.omschrijving.waarde == 'Onderzoek is gestart op geboorte datum'
        assert onderzoek.verwachteAfhandeldatum.waarde == 20150401
        assert onderzoek.datumEinde == null

        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek.size() == 0
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].voorkomenSleutelGegeven != null
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].element != null
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].elementIdentificatie != null

        assert persoon.onderzoeken[0].onderzoek.personenInOnderzoek.size() == 0
        assert persoon.onderzoeken[0].onderzoek.personenInOnderzoek[0].persoon != null
    }


    @Test
    void "test object in onderzoek"() {
        String dsl = """
def partner = Persoon.uitGebeurtenissen {
  geboorte() {
    namen {
      geslachtsnaam 'Jansen'
      voornamen 'Piet'
    }
  }
}

slaOp(partner)

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

    huwelijk() {
        op '2010/03/09' te "Naaldwijk" gemeente "Westland"
        met partner
        naamgebruik 'PARTNER, EIGEN'
    }
}

slaOp(persoon)
persoon = Persoon.metId(persoon.ID)
Persoon.nieuweGebeurtenissenVoor(persoon) {

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-04',
            omschrijving: 'Onderzoek is gestart op Voornaam object',
            verwachteAfhandelDatum: '2015-04-04')

        gegevensInOnderzoek('Persoon.Voornaam')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 1

        def onderzoek = persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0]
        assert onderzoek.datumAanvang.waarde == 20150104
        assert onderzoek.omschrijving.waarde == 'Onderzoek is gestart op Voornaam object'
        assert onderzoek.verwachteAfhandeldatum.waarde == 20150404
        assert onderzoek.datumEinde == null

        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek.size() == 1
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].objectSleutelGegeven != null
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].element.waarde.naam != null
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].element.waarde.naam.waarde == 'Persoon.Voornaam'
    }
}
