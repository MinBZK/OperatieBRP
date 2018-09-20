package nl.bzk.brp.datataal.handlers.persoon.afstamming

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class VerbeteringGeboorteakteHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void hoortNaamAanTePassen() {
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

    verbeteringGeboorteakte(partij: 54101, aanvang: 20101010, toelichting:'rijmelarij'){
        staatloos()
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.persoonAfgeleidAdministratiefHistorie.aantal == 2
        assert persoon.indicatieStaatloos.persoonIndicatieHistorie.actueleRecord.waarde.waarde == Ja.J
    }

    @Test
    void hoortGemeenteEnGeboorteplaatsAanTePassen() {
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

    verbeteringGeboorteakte(partij: 54101, aanvang: 20101010, toelichting:'rijmelarij'){
        op '20140101' te 'Overveen' gemeente 'Bloemendaal'
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.persoonGeboorteHistorie.aantal == 2
        assert persoon.persoonGeboorteHistorie.actueleRecord.woonplaatsnaamGeboorte.waarde == 'Overveen'
        assert persoon.persoonGeboorteHistorie.actueleRecord.gemeenteGeboorte.waarde.naam.waarde == 'Bloemendaal'

    }

    @Test
    void "hoort alleen geboorte datum aan te passen"() {
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

    verbeteringGeboorteakte(partij: 54101, aanvang: 20101010, toelichting:'rijmelarij') {
        op '20140101' te()  gemeente()
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.persoonGeboorteHistorie.aantal == 2
        assert persoon.persoonGeboorteHistorie.actueleRecord.datumGeboorte.waarde == 20140101
        assert persoon.persoonGeboorteHistorie.actueleRecord.woonplaatsnaamGeboorte.waarde == 'Monster'
        assert persoon.persoonGeboorteHistorie.actueleRecord.gemeenteGeboorte.waarde.naam.waarde == 'Westland'
    }


    @Test
    void "hoort plaats aan te passen met vorige opgegeven plaats"() {

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

    verbeteringGeboorteakte(partij: 54101, aanvang: 20101010, toelichting:'rijmelarij'){
        op '20140101' te() gemeente 'Amsterdam'
    }
}
"""
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.persoonGeboorteHistorie.aantal == 2
        assert persoon.persoonGeboorteHistorie.actueleRecord.woonplaatsnaamGeboorte.waarde == 'Monster'
        assert persoon.persoonGeboorteHistorie.actueleRecord.gemeenteGeboorte.waarde.naam.waarde == 'Amsterdam'

    }
}
