package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class GeslachtswijzigingHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void integratie() {
        def dsl = '''
            persoon = Persoon.uitGebeurtenissen {
                geboorte(aanvang:19900909) {
                    geslacht 'VROUW'
                }

                geslachtswijziging() {
                    geslacht 'MAN'
                }
            }
        '''

        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon.persoonGeslachtsaanduidingHistorie.size() == 3
        assert persoon.persoonGeslachtsaanduidingHistorie.actueleRecord.geslachtsaanduiding.waarde.name() == 'MAN'
        assert persoon.persoonGeslachtsaanduidingHistorie.vervallenHistorie.size() == 1
        assert persoon.persoonGeslachtsaanduidingHistorie.vervallenHistorie[0].geslachtsaanduiding.waarde.name() == 'VROUW'
    }
}
