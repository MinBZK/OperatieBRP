package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class VerstrekkingsbeperkingHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void "dient verstrekkings beperking op partij toe te voegen aan een persoon"() {
        String dsl = """
            persoon = Persoon.uitGebeurtenissen {
                geboorte() { }

                verstrekkingsbeperking(partij: 36101, aanvang: 19791023, toelichting: 'constructor beperking') {
                    registratieBeperkingen([partij:36101])
                }
            }
        """
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.verstrekkingsbeperkingen.size() == 1
    }

    @Test
    void "dient verstrekkings beperking op omschrijving derde toe te voegen aan een persoon"() {
        String dsl = """
            persoon = Persoon.uitGebeurtenissen {
                geboorte() { }

                verstrekkingsbeperking(partij: 36101, aanvang: 19791023, toelichting: 'constructor beperking') {
                    registratieBeperkingen([omschrijving: "beperking", gemeenteVerordening: 36101])
                }
            }
        """
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.verstrekkingsbeperkingen.size() == 1
    }

    @Test
    void "dient een verstrekkings beperking te vervallen die wel bestaat"() {
        String dsl = """
            persoon = Persoon.uitGebeurtenissen {
                geboorte() { }

                verstrekkingsbeperking(partij: 36101, aanvang: 19791023, toelichting: 'constructor beperking') {
                    registratieBeperkingen([partij:36101])
                    vervalBeperkingen([partij:36101])
                }
            }
        """
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
    }

    @Test(expected = IllegalArgumentException.class)
    void "dient een verstrekkings beperking te vervallen die niet bestaat"() {
        String dsl = """
            persoon = Persoon.uitGebeurtenissen {
                geboorte() { }

                verstrekkingsbeperking(partij: 36101, aanvang: 19791023, toelichting: 'constructor beperking') {
                    registratieBeperkingen([omschrijving: "beperking", gemeenteVerordening: 36101])
                    vervalBeperkingen([omschrijving: "beperkingOnjuist", gemeenteVerordening: 36101])
                }
            }
        """
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
    }

    @Test
    void "dient volledige verstrekkingsbeperking te hebben"() {
        String dsl = """
            persoon = Persoon.uitGebeurtenissen {
                geboorte() { }

                verstrekkingsbeperking() {
                   volledig ja
                }
            }
            """
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.indicatieVolledigeVerstrekkingsbeperking.persoonIndicatieHistorie.actueleRecord != null
        assert persoon.indicatieVolledigeVerstrekkingsbeperking.persoonIndicatieHistorie.aantal == 1
    }

    @Test
    void "dient historie te hebben voor indicatie volledige verstrekkingsbeperking maar geen actueel record meer"() {
        String dsl = """
            persoon = Persoon.uitGebeurtenissen {
                geboorte() { }

                verstrekkingsbeperking(partij: 36101, aanvang: 19791023, toelichting: 'constructor beperking') {
                   volledig ja
                }
                verstrekkingsbeperking() {
                   volledig nee
                }
            }
            """
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.indicatieVolledigeVerstrekkingsbeperking.persoonIndicatieHistorie.actueleRecord == null
        assert persoon.indicatieVolledigeVerstrekkingsbeperking.persoonIndicatieHistorie.aantal == 1
    }

    @Test
    void "dient correct te werken met default waardes voor gebeurtenis"() {
        String dsl = """
            persoon = Persoon.uitGebeurtenissen {
                geboorte() { }

                verstrekkingsbeperking() { volledig ja }
                verstrekkingsbeperking() { volledig nee }
            }
            """
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.indicatieVolledigeVerstrekkingsbeperking.persoonIndicatieHistorie.actueleRecord == null
        assert persoon.indicatieVolledigeVerstrekkingsbeperking.persoonIndicatieHistorie.aantal == 1
    }
}
