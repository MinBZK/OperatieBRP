/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingAntwoordElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingBerichtSoort;
import nl.bzk.brp.bijhouding.bericht.model.GedeblokkeerdeMeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.PersoonGegevensElement;
import nl.bzk.brp.bijhouding.bericht.model.ResultaatElement;
import nl.bzk.brp.bijhouding.bericht.model.StuurgegevensElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor BijhoudingVerzoekAntwoordBerichtParser.
 */
public class VoltrekkingHuwelijkNederlandAntwoordParserTest extends AbstractParserTest {

    public static final String EENVOUDIG_BIJHOUDING_ANTWOORD_BERICHT = "voltrekkingHuwelijkNederlandAntwoordBericht.xml";

    @Test
    public void testValideerXmlBericht() {
        valideerTegenSchema(this.getClass().getResourceAsStream(EENVOUDIG_BIJHOUDING_ANTWOORD_BERICHT));
    }

    @Test
    public void testParsenAntwoordBericht() throws ParseException {
        final BijhoudingAntwoordBerichtParser parser = new BijhoudingAntwoordBerichtParser();
        final BijhoudingAntwoordBericht bericht = parser.parse(this.getClass().getResourceAsStream(EENVOUDIG_BIJHOUDING_ANTWOORD_BERICHT));
        Assert.assertNotNull(bericht);
        Assert.assertEquals(BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP_ANTWOORD, bericht.getSoort());

        assertStuurgegevens(bericht.getStuurgegevens());
        assertResultaat(bericht.getResultaat());
        assertMeldingen(bericht.getMeldingen());
        assertAdministratieveHandelingAntwoord(bericht.getAdministratieveHandelingAntwoord());
    }

    private void assertStuurgegevens(final StuurgegevensElement stuurgegevens) {
        Assert.assertNotNull(stuurgegevens);
        assertEqualStringElement("053002", stuurgegevens.getZendendePartij());
        assertEqualStringElement("BRP", stuurgegevens.getZendendeSysteem());
        assertEqualStringElement("88409eeb-1aa5-43fc-8614-43055123a199", stuurgegevens.getReferentienummer());
        assertEqualStringElement("88409eeb-1aa5-43fc-8614-43055123a165", stuurgegevens.getCrossReferentienummer());
        assertEqualDatumTijdElement("2016-03-21T10:32:03.234+01:00", stuurgegevens.getTijdstipVerzending());
    }

    private void assertResultaat(final ResultaatElement resultaat) {
        Assert.assertNotNull(resultaat);
        assertEqualStringElement("Geslaagd", resultaat.getVerwerking());
        assertEqualStringElement("Verwerkt", resultaat.getBijhouding());
        assertEqualStringElement("Deblokkeerbaar", resultaat.getHoogsteMeldingsniveau());
    }

    private void assertMeldingen(final List<MeldingElement> meldingen) {
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(2, meldingen.size());
        assertMelding1(meldingen.get(0));
        assertMelding2(meldingen.get(1));
    }

    private void assertMelding1(final MeldingElement meldingElement) {
        Assert.assertNotNull(meldingElement);
        Assert.assertEquals("Melding", meldingElement.getObjecttype());
        assertEqualStringElement("R0001", meldingElement.getRegelCode());
        assertEqualStringElement("Deblokkeerbaar", meldingElement.getSoortNaam());
        assertEqualStringElement("De foutmelding 1", meldingElement.getMelding());
    }

    private void assertMelding2(final MeldingElement meldingElement) {
        Assert.assertNotNull(meldingElement);
        assertEqualStringElement("R0002", meldingElement.getRegelCode());
        assertEqualStringElement("Deblokkeerbaar", meldingElement.getSoortNaam());
        assertEqualStringElement("De foutmelding 2", meldingElement.getMelding());
    }

    private void assertAdministratieveHandelingAntwoord(final AdministratieveHandelingAntwoordElement administratieveHandelingAntwoord) {
        Assert.assertNotNull(administratieveHandelingAntwoord);
        assertEqualStringElement("123456", administratieveHandelingAntwoord.getPartijCode());
        assertEqualDatumTijdElement("2016-03-21T10:32:13.234+01:00", administratieveHandelingAntwoord.getTijdstipRegistratie());
        assertGedeblokkeerdeMeldingen(administratieveHandelingAntwoord.getGedeblokkeerdeMeldingen());
        assertBijgehoudenPersonen(administratieveHandelingAntwoord.getBijgehoudenPersonen());
    }

    private void assertGedeblokkeerdeMeldingen(final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen) {
        Assert.assertNotNull(gedeblokkeerdeMeldingen);
        Assert.assertEquals(1, gedeblokkeerdeMeldingen.size());
        final GedeblokkeerdeMeldingElement gedeblokkeerdeMelding = gedeblokkeerdeMeldingen.get(0);
        Assert.assertEquals("GedeblokkeerdeMelding", gedeblokkeerdeMelding.getObjecttype());
        Assert.assertEquals("CI_gedeblokkeerde_melding_1", gedeblokkeerdeMelding.getReferentieId());
        assertEqualStringElement("R0013", gedeblokkeerdeMelding.getRegelCode());
        assertEqualStringElement("Een gedeblokkeerde melding 1", gedeblokkeerdeMelding.getMelding());
    }

    private void assertBijgehoudenPersonen(final List<PersoonGegevensElement> bijgehoudenPersonen) {
        Assert.assertNotNull(bijgehoudenPersonen);
        Assert.assertEquals(2, bijgehoudenPersonen.size());
        final PersoonGegevensElement persoon1 = bijgehoudenPersonen.get(0);
        final PersoonGegevensElement persoon2 = bijgehoudenPersonen.get(1);
        Assert.assertNotNull(persoon1.getIdentificatienummers());
        Assert.assertNotNull(persoon2.getIdentificatienummers());
        assertEqualStringElement("987654321", persoon1.getIdentificatienummers().getBurgerservicenummer());
        assertEqualStringElement("987654322", persoon2.getIdentificatienummers().getBurgerservicenummer());
    }
}
