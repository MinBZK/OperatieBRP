package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import org.junit.Test

class BeeindigAdresHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void "beeindig adres met meegegeven datum"() {
        String dsl = """
            persoon = Persoon.uitGebeurtenissen {
                        geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
                            op '1978/10/22' te 'Rotterdam' gemeente 'Rotterdam'
                        }

                        verhuizing(partij: 'Gemeente Gorinchem', aanvang: 19781023) {
                            naarGemeente 'Gorinchem', straat: 'Dorpstraat', nummer: 13, postcode: '4207AA', woonplaats: 'Gorinchem'
                        }

                        verhuizing(partij: 'Gemeente Gorinchem', aanvang: 19841023) {
                            naarGemeente 'Gorinchem', straat: 'Dorpelstraat', nummer: 15, postcode: '4208AA', woonplaats: 'Gorinchem'
                        }

                        beeindigAdres() {
                            op 20010911
                        }

            }
            """

        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.adressen.size() == 1

        int aantalVervallenRecords = 0
        int aantalBeeindigdeRecords = 0
        int laatsteDatumEindeGeldigheid = 0
        persoon.adressen.each{
            assert it.persoonAdresHistorie.actueleRecord == null
            it.persoonAdresHistorie.each {
                if (it.datumTijdVerval != null) {
                    aantalVervallenRecords++
                }
                if (it.datumEindeGeldigheid != null) {
                    aantalBeeindigdeRecords++
                    if (it.datumEindeGeldigheid.waarde > laatsteDatumEindeGeldigheid) {
                        laatsteDatumEindeGeldigheid = it.datumEindeGeldigheid.waarde.intValue()
                    }
                }
            }
        }

        assert aantalVervallenRecords == 2
        assert aantalBeeindigdeRecords == 2
        assert laatsteDatumEindeGeldigheid == 20010911
    }

    @Test
    void "beeindig adres zonder meegegeven datum"() {
        String dsl = """
            persoon = Persoon.uitGebeurtenissen {
                        geboorte(partij: 36101, aanvang: 19781023, toelichting: '1e kind') {
                            op '1978/10/22' te 'Rotterdam' gemeente 'Rotterdam'
                        }

                        verhuizing(partij: 'Gemeente Gorinchem', aanvang: 19781023) {
                            naarGemeente 'Gorinchem', straat: 'Dorpstraat', nummer: 13, postcode: '4207AA', woonplaats: 'Gorinchem'
                        }

                        verhuizing(partij: 'Gemeente Gorinchem', aanvang: 19841023) {
                            naarGemeente 'Gorinchem', straat: 'Dorpelstraat', nummer: 15, postcode: '4208AA', woonplaats: 'Gorinchem'
                        }

                        beeindigAdres() {
                        }
            }
            """
        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon != null
        assert persoon.adressen.size() == 1
        persoon.adressen.each{
            assert it.persoonAdresHistorie.actueleRecord == null
        }

        int aantalVervallenRecords = 0
        int aantalBeeindigdeRecords = 0
        int laatsteDatumEindeGeldigheid = 0
        persoon.adressen.each{
            assert it.persoonAdresHistorie.actueleRecord == null
            it.persoonAdresHistorie.each {
                if (it.datumTijdVerval != null) {
                    aantalVervallenRecords++
                }
                if (it.datumEindeGeldigheid != null) {
                    aantalBeeindigdeRecords++
                    if (it.datumEindeGeldigheid.waarde > laatsteDatumEindeGeldigheid) {
                        laatsteDatumEindeGeldigheid = it.datumEindeGeldigheid.waarde.intValue()
                    }
                }
            }
        }

        assert aantalVervallenRecords == 2
        assert aantalBeeindigdeRecords == 2

        // De default datumaanvang is ingesteld op tien dagen voor de huidige datum. Zie {@link GebeurtenisAttributen.java}.
        def verwachteDefaultDatum = DatumAttribuut.vandaag()
        verwachteDefaultDatum.voegDagToe(-10)
        assert laatsteDatumEindeGeldigheid == verwachteDefaultDatum.waarde
    }
}
