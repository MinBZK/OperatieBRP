package nl.bzk.brp.datataal.handlers.persoon.afstamming

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import org.junit.Before
import org.junit.Test

class GeboorteHandlerTest extends AbstractDSLIntegratieTest {
    GeboorteHandler geboorteHandler
    def attr
    def builder

    @Before
    void maakHandler() {

        attr = new GebeurtenisAttributen(partij: 36101, aanvang: 20140101, toelichting: 'foo',
            soortHandeling: SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND)

        builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);

        geboorteHandler = new GeboorteHandler(attr, builder)
        geboorteHandler.startGebeurtenis()
    }

    @Test
    void persoonHeeftHandeling() {
        def persoon = builder.build()

        assert persoon.persoonAfgeleidAdministratiefHistorie.aantal == 1
        assert persoon.administratieveHandelingen.size() == 0
    }

    @Test
    void testJongen() {
        geboorteHandler.jongen()

        def persoon = builder.build()
        assert persoon.persoonGeslachtsaanduidingHistorie.actueleRecord.geslachtsaanduiding.waarde == Geslachtsaanduiding.MAN
        assert persoon.persoonAfgeleidAdministratiefHistorie.actueleRecord.administratieveHandeling.acties.size() == 1
    }

    @Test
    void testMeisje() {
        geboorteHandler.meisje()

        def persoon = builder.build()
        assert persoon.persoonGeslachtsaanduidingHistorie.actueleRecord.geslachtsaanduiding.waarde == Geslachtsaanduiding.VROUW
    }

    @Test
    void testGeslachtsnaam() {
        geboorteHandler.geslachtsnaam('Jansen')

        def persoon = builder.build()
        assert persoon.geslachtsnaamcomponenten.size() == 1
    }

    @Test
    void testGeslachtsnamen() {
        geboorteHandler.geslachtsnaam(
            [stam: 'Janssen']
        )
        def persoon = builder.build()

        assert persoon.geslachtsnaamcomponenten.size() == 1
    }

    @Test
    void testVoornamen() {
        def namen = ['Jan', 'Piet', 'Klaas']
        geboorteHandler.voornamen namen as String[]

        def persoon = builder.build()

        assert persoon.voornamen.size() == 3
        assert persoon.voornamen.collect { it.persoonVoornaamHistorie.actueleRecord.naam.waarde } == namen
    }

    @Test
    void testComplexeNaam() {
        geboorteHandler.voornamen 'Piet', 'Jan'
        geboorteHandler.geslachtsnaam 'Jansen'
        geboorteHandler.namen {}

        def persoon = builder.build()

        assert persoon.persoonSamengesteldeNaamHistorie.actueleRecord.geslachtsnaamstam.waarde == 'Jansen'
        assert persoon.persoonSamengesteldeNaamHistorie.actueleRecord.voornamen.waarde == 'Piet Jan'
    }

    @Test
    void testOp() {
        def te = geboorteHandler.op('2014/01/01').te
        def gemeente = te('Monster').gemeente
        gemeente('Westland')

        def persoon = builder.build()

        assert persoon.persoonGeboorteHistorie.aantal == 1
        assert persoon.persoonGeboorteHistorie.actueleRecord.woonplaatsnaamGeboorte.waarde == 'Monster'
    }

    @Test
    void integratie() {
        def dsl = """
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
        """

        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.persoonSamengesteldeNaamHistorie.actueleRecord.geslachtsnaamstam.waarde == 'Jansen'
        assert persoon.persoonSamengesteldeNaamHistorie.actueleRecord.voornamen.waarde == 'Jan Piet'
    }
}
