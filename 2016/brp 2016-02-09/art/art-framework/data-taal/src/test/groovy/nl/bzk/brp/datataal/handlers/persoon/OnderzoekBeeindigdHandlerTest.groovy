package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class OnderzoekBeeindigdHandlerTest extends AbstractDSLIntegratieTest {


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

    onderzoekAangemaakt(partij: 34401, registratieDatum: 20140101) {
        gestartOp(aanvangsDatum: '2014-01-01',
            omschrijving: 'Onderzoek is gestart',
            verwachteAfhandelDatum: '2015-04-01')
    }
}

slaOp(persoon)

persoon = Persoon.metId(persoon.ID)

Persoon.nieuweGebeurtenissenVoor(persoon) {

    onderzoekBeeindigd(partij: 34401, registratieDatum: 20150402) {
        gestaaktOp(tsDatum: '2015-04-01', eindDatum:'2015-04-01')
    }
}

slaOp(persoon)
"""

        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl
        assert persoon.onderzoeken.size() == 1
        assert persoon.onderzoeken[0].onderzoek.onderzoekHistorie.size() == 2

        def actueel = persoon.onderzoeken[0].onderzoek.onderzoekHistorie.actueleRecord

        assert actueel.datumAanvang.waarde == 20140101
        assert actueel.omschrijving.waarde == 'Onderzoek is gestart'
        assert actueel.verwachteAfhandeldatum.waarde == 20150401
        assert actueel.datumEinde.waarde == 20150401
        assert actueel.verantwoordingInhoud.tijdstipRegistratie.naarDatum().waarde == 20150402

        def historie = persoon.onderzoeken[0].onderzoek.onderzoekHistorie.sort { it.verantwoordingInhoud.tijdstipRegistratie.naarDatum().waarde }
        assert historie[0].verantwoordingInhoud.tijdstipRegistratie.naarDatum().waarde == 20140101
        assert historie[1].verantwoordingInhoud.tijdstipRegistratie.naarDatum().waarde == 20150402
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

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(
            aanvangsDatum:'2014-01-01',
            partij:34401,
            omschrijving:'Onderzoek is gestart',
            verwachteAfhandelDatum:'2014-04-01')
    }

    onderzoekBeeindigd(partij: 34401) {
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

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart',
            verwachteAfhandelDatum: '2015-04-01')
    }

    onderzoekBeeindigd(partij: 34401) {
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

    onderzoekAangemaakt(partij: 34401) {
        gestartOp(aanvangsDatum: '2015-01-01',
            omschrijving: 'Onderzoek is gestart',
            verwachteAfhandelDatum: '2015-04-01')
    }

    onderzoekBeeindigd(partij: 34401) {
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
}
