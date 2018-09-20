package nl.brp.acceptance.helper
import nl.bzk.brp.expressietaal.parser.BRPExpressies
import nl.bzk.brp.expressietaal.parser.ParserResultaat
import org.junit.Test

/*
 Deze test is bedoeld om expressies te kunnen verifieren, voordat ze gebruikt worden in testabonnementen.
 */
class ExpressieEvaluatieTest {


    @Test
    public void testEvaluatie() {
        evalueert(BRPExpressies.parse("persoon.geboorte.datum"));
    }

    @Test
    public void testEvaluatieVergelijking() {
        evalueert(BRPExpressies.parse("persoon.geboorte.datum > 2000/01/01"));
    }

    @Test
    public void testEvaluatieGewijzigd() {
        evalueert(BRPExpressies.parse("GEWIJZIGD(persoon, persoon, [identificatienummers.bsn])"));
        evalueert(BRPExpressies.parse("GEWIJZIGD(persoon, persoon, [adressen], [postcode])"));
    }

    def static evalueert(final ParserResultaat parserResultaat) {
        assert parserResultaat.getFout() == null
        assert parserResultaat.succes()
    }
}
