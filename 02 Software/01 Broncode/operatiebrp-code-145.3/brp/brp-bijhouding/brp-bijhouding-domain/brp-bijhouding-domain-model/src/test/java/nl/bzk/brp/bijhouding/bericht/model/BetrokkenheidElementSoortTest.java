/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Test;

/**
 * Testen voor {@link BetrokkenheidElementSoort}.
 */
public class BetrokkenheidElementSoortTest extends AbstractElementTest {

    @Test
    public void testGetElementNamen() throws Exception {
        List<String> list = BetrokkenheidElementSoort.getElementNamen();
        assertTrue(list.size() > 0);
    }

    @Test(expected = OngeldigeWaardeException.class)
    public void parseWaardeOngGeldig() throws OngeldigeWaardeException {
        BetrokkenheidElementSoort.parseElementNaam("onzin");
    }

    @Test
    public void parseWaardeGeldig() throws OngeldigeWaardeException {
        BetrokkenheidElementSoort.parseElementNaam(BetrokkenheidElementSoort.KIND.getElementNaam());
    }

    @Bedrijfsregel(Regel.R2617)
    @Test
    public void testR2617_PersoonIsBestaandePersoon() {
        final ElementBuilder builder = new ElementBuilder();
        final String sleutel = "sleutel";
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon", sleutel);
        final OuderschapElement ouderschapElement = builder.maakOuderschapElement("ouderschap", true);
        final BetrokkenheidElement
                betrokkenheidElement =
                builder.maakBetrokkenheidElement("betrokkenheid", BetrokkenheidElementSoort.OUDER, persoonElement, ouderschapElement);

        persoonElement.setVerzoekBericht(getBericht());
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, sleutel)).thenReturn(bijhoudingPersoon);
        final List<MeldingElement> meldingen = betrokkenheidElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2617)
    @Test
    public void testR2617_Ouderschap_Nouwkig() {
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.geslachtsnaamcomponenten(
                Collections.singletonList(builder.maakGeslachtsnaamcomponentElement("geslachtsnaamComponent", null, null, null, null, "Stam")));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParameters);
        final OuderschapElement ouderschapElement = builder.maakOuderschapElement("ouderschap", false);
        final BetrokkenheidElement
                betrokkenheidElement =
                builder.maakBetrokkenheidElement("betrokkenheid", BetrokkenheidElementSoort.OUDER, persoonElement, ouderschapElement);

        final List<MeldingElement> meldingen = betrokkenheidElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2617)
    @Test
    public void testR2617_GeenPersoon() {
        final ElementBuilder builder = new ElementBuilder();
        final OuderschapElement ouderschapElement = builder.maakOuderschapElement("ouderschap", true);
        final BetrokkenheidElement
                betrokkenheidElement =
                builder.maakBetrokkenheidElement("betrokkenheid", BetrokkenheidElementSoort.OUDER, null, ouderschapElement);

        final List<MeldingElement> meldingen = betrokkenheidElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2617, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2617)
    @Test
    public void testR2617_GeenOuderschap() {
        final ElementBuilder builder = new ElementBuilder();
        final BetrokkenheidElement
                betrokkenheidElement =
                builder.maakBetrokkenheidElement("betrokkenheid", BetrokkenheidElementSoort.OUDER, null, null);

        final List<MeldingElement> meldingen = betrokkenheidElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }
}
