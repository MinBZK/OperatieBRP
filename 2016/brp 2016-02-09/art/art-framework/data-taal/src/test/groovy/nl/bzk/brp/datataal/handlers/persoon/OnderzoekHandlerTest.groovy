package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class OnderzoekHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void "test nieuw onderzoek"() {
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

    onderzoek(partij: 34401) {
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

        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumAanvang.waarde == 20150101
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].omschrijving.waarde == 'Onderzoek is gestart'
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].verwachteAfhandeldatum.waarde == 20150401
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumEinde == null

        // Deze gegevens komen uit de stamgegevens
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].verantwoordingInhoud.partij != null
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].verantwoordingInhoud.partij.waarde.code.waarde == 34401
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].verantwoordingInhoud.partij.waarde.naam.waarde == 'Gemeente Utrecht'
    }

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

    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart',
            verwachteAfhandelDatum: '2015-04-01')
    }

    onderzoek(partij: 34401) {
        wijzigOnderzoek(tsDatum: '2015-01-01', wijzigingsDatum: '2015-01-01', omschrijving: 'Onderzoek gewijzigd', aanvangsDatum: '2014-01-01', verwachteAfhandelDatum: '2014-04-01')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 2

        def historie = persoon.onderzoeken[0].onderzoek.onderzoekHistorie.actueleRecord

        assert historie.datumAanvang.waarde == 20140101
        assert historie.omschrijving.waarde == 'Onderzoek gewijzigd'
        assert historie.verwachteAfhandeldatum.waarde == 20140401
        assert historie.datumEinde == null
    }

    @Test
    void "test onderzoek met afsluiten separaat"() {
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

    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum: '2014-01-01',
            omschrijving: 'Onderzoek is gestart',
            verwachteAfhandelDatum: '2015-04-01')
    }
}

slaOp(persoon)

persoon = Persoon.metId(persoon.ID)

Persoon.nieuweGebeurtenissenVoor(persoon) {

    onderzoek(partij: 34401) {
        gestaaktOp(tsDatum: '2015-04-01', eindDatum:'2015-04-01')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 2

        def historie = persoon.onderzoeken[0].onderzoek.onderzoekHistorie.actueleRecord

        assert historie.datumAanvang.waarde == 20140101
        assert historie.omschrijving.waarde == 'Onderzoek is gestart'
        assert historie.verwachteAfhandeldatum.waarde == 20150401
        assert historie.datumEinde.waarde == 20150401
    }

    @Test
    void "test onderzoek met afsluiten"() {
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

    onderzoek(partij: 34401) {
        gestartOp(
            aanvangsDatum:'2014-01-01',
            partij:34401,
            omschrijving:'Onderzoek is gestart',
            verwachteAfhandelDatum:'2014-04-01')
    }

    onderzoek(partij: 34401) {
        afgeslotenOp(tsDatum: '2015-04-01', eindDatum:'2015-02-06')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 2

        def historie = persoon.onderzoeken[0].onderzoek.onderzoekHistorie.actueleRecord

        assert historie.datumAanvang.waarde == 20140101
        assert historie.omschrijving.waarde == 'Onderzoek is gestart'
        assert historie.verwachteAfhandeldatum.waarde == 20140401
        assert historie.datumEinde.waarde == 20150206
    }

    @Test
    void "test onderzoek gestaakt"() {
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

    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart',
            verwachteAfhandelDatum: '2015-04-01')

        gestaaktOp(tsDatum: '2015-01-01', eindDatum:'2015-02-06')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 2

        def historie = persoon.onderzoeken[0].onderzoek.onderzoekHistorie.sort { it.datumEinde?.waarde }

        assert historie[0].datumAanvang.waarde == 20150101
        assert historie[0].omschrijving.waarde == 'Onderzoek is gestart'
        assert historie[0].verwachteAfhandeldatum.waarde == 20150401
        assert historie[0].datumEinde == null

        assert historie[1].datumAanvang.waarde == 20150101
        assert historie[1].omschrijving.waarde == 'Onderzoek is gestart'
        assert historie[1].verwachteAfhandeldatum.waarde == 20150401
        assert historie[1].datumEinde.waarde == 20150206
    }

    @Test
    void "test onderzoek afgesloten"() {
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

    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart',
            verwachteAfhandelDatum: '2015-04-01')

        afgeslotenOp(tsDatum: '2015-01-01', eindDatum:'2015-02-06')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 2

        def historie = persoon.onderzoeken[0].onderzoek.onderzoekHistorie.sort { it.datumEinde?.waarde }

        assert historie[0].datumAanvang.waarde == 20150101
        assert historie[0].omschrijving.waarde == 'Onderzoek is gestart'
        assert historie[0].verwachteAfhandeldatum.waarde == 20150401
        assert historie[0].datumEinde == null

        assert historie[1].datumAanvang.waarde == 20150101
        assert historie[1].omschrijving.waarde == 'Onderzoek is gestart'
        assert historie[1].verwachteAfhandeldatum.waarde == 20150401
        assert historie[1].datumEinde.waarde == 20150206
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

    onderzoek(partij: 34401) {
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

        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumAanvang.waarde == 20150101
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].omschrijving.waarde == 'Onderzoek is gestart op geboorte datum'
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].verwachteAfhandeldatum.waarde == 20150401
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumEinde == null

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

    onderzoek(partij: 34401) {
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

    onderzoek(partij: 34401) {
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

    onderzoek(partij: 34401) {
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

    onderzoek(partij: 34401) {
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

        assert opgeslagen.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumAanvang.waarde == 20150101
        assert opgeslagen.onderzoeken[0].onderzoek.onderzoekHistorie[0].omschrijving.waarde == 'Onderzoek is gestart op adres groep'
        assert opgeslagen.onderzoeken[0].onderzoek.onderzoekHistorie[0].verwachteAfhandeldatum.waarde == 20150401
        assert opgeslagen.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumEinde == null

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

    onderzoek(partij: 34401) {
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

    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-01')

        gegevensInOnderzoek('Persoon.Adres.Standaard')
    }
}

slaOp(persoon)
opgeslagen = Persoon.metId(persoon.ID)
Persoon.nieuweGebeurtenissenVoor(opgeslagen) {

    onderzoek(partij: 34401) {
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

    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-01')

        gegevensInOnderzoek('Persoon.Adres.Standaard')
    }
}

slaOp(persoon)
opgeslagen = Persoon.metId(persoon.ID)
Persoon.nieuweGebeurtenissenVoor(opgeslagen) {

    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-02',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-02')

        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}

slaOp(opgeslagen)
derdePersoon = Persoon.metId(opgeslagen.ID)
Persoon.nieuweGebeurtenissenVoor(derdePersoon) {

    onderzoek(partij: 34401) {
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

    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-01')

        gegevensInOnderzoek('Persoon.Adres.Standaard')
    }
}

slaOp(persoon)
opgeslagen = Persoon.metId(persoon.ID)
Persoon.nieuweGebeurtenissenVoor(opgeslagen) {

    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-02',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-02')

        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}

slaOp(opgeslagen)
derdePersoon = Persoon.metId(opgeslagen.ID)
Persoon.nieuweGebeurtenissenVoor(derdePersoon) {

    onderzoek(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-03',
            omschrijving: 'Onderzoek is gestart op adres groep',
            verwachteAfhandelDatum: '2015-04-03')

        gegevensInOnderzoek('Persoon.Geboorte.BuitenlandsePlaats')
    }
}

slaOp(derdePersoon)
derdePersoon = Persoon.metId(derdePersoon.ID)
Persoon.nieuweGebeurtenissenVoor(derdePersoon) {

    onderzoek(partij: 34401) {
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

    onderzoek(partij: 34401) {
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
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumAanvang.waarde == 20150104
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].omschrijving.waarde == 'Onderzoek is gestart op Voornaam object'
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].verwachteAfhandeldatum.waarde == 20150404
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie[0].datumEinde == null

        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek.size() == 1
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].objectSleutelGegeven != null
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].element.waarde.naam != null
        assert persoon.onderzoeken[0].onderzoek.gegevensInOnderzoek[0].element.waarde.naam.waarde == 'Persoon.Voornaam'
    }
}
