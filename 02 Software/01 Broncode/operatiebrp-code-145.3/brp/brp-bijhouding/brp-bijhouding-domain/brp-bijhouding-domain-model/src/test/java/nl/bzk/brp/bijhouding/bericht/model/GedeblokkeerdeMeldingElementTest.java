/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.junit.Before;
import org.junit.Test;

/**
 * UT voor AbstractElementTest.
 */
public class GedeblokkeerdeMeldingElementTest extends AbstractElementTest {

    private static final String NVT = "Niet van toepassing.";
    private static final String R2269 = "De ondertekenaar is geen geldige partij.";
    private Map<String, String> attributes;

    @Before
    public void setup() {
        attributes = new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_test").objecttype("obj").build();
    }

    @Test
    public void testNietBestaandeRegelR2428() {
        final List<MeldingElement> meldingen = new GedeblokkeerdeMeldingElement(attributes, new StringElement("R0000"), null).valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2428.getCode(), meldingen.get(0).getRegelCode().getWaarde());
    }

    @Test
    public void testNietBlokkerendeRegelR2428() {
        final List<MeldingElement> meldingen = new GedeblokkeerdeMeldingElement(attributes, new StringElement("R2209"), null).valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2428.getCode(), meldingen.get(0).getRegelCode().getWaarde());
    }

    @Test
    public void testBlokkerendeMeldingCorrect() {
        final List<MeldingElement> meldingen = new GedeblokkeerdeMeldingElement(attributes, new StringElement("R2448"), null).valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test

    public void instanceTest() {
        final GedeblokkeerdeMeldingElement
                meldingElement =
                new GedeblokkeerdeMeldingElement(attributes, new StringElement("R2269"), new StringElement(R2269));
        final GedeblokkeerdeMeldingElement meldingElementInstance = GedeblokkeerdeMeldingElement.getInstanceVoorAntwoord(meldingElement);
        assertEquals(meldingElement.getMelding(), meldingElementInstance.getMelding());
        assertEquals(meldingElement.getReferentie(), meldingElementInstance.getReferentie());
        assertEquals(meldingElement.getRegelCode(), meldingElementInstance.getRegelCode());
    }

    public void instanceTestNVT() {
        final GedeblokkeerdeMeldingElement
                meldingElement =
                new GedeblokkeerdeMeldingElement(attributes, new StringElement("R0000"), new StringElement(""));
        final GedeblokkeerdeMeldingElement meldingElementInstance = GedeblokkeerdeMeldingElement.getInstanceVoorAntwoord(meldingElement);
        assertEquals(NVT, meldingElementInstance.getMelding());
        assertEquals(meldingElement.getReferentie(), meldingElementInstance.getReferentie());
        assertEquals(meldingElement.getRegelCode(), meldingElementInstance.getRegelCode());
    }
}
