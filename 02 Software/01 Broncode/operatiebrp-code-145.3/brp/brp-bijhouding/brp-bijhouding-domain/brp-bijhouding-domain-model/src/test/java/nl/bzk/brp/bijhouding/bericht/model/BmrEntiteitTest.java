/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unittest om de verschillende default methodes te testen van de {@link BmrEntiteit}. Ook worden hier een aantal specifieke implementatie getest die de default
 * methode overriden.
 */
@RunWith(MockitoJUnitRunner.class)
public class BmrEntiteitTest {

    private ElementBuilder builder;
    private BijhoudingVerzoekBericht bericht;

    @Before
    public void setup() {
        bericht = mock(BijhoudingVerzoekBericht.class);
        builder = new ElementBuilder();
    }

    @Test
    public void testPersoonElement() {
        final String objectSleutel = "4940240812142708458_7510841056894151746_8058258521516688504";
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        final PersoonGegevensElement element = builder.maakPersoonGegevensElement("CI_persoon", objectSleutel, null, params);
        element.setVerzoekBericht(bericht);

        final BijhoudingPersoon bijhoudingEntiteit = new BijhoudingPersoon();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectSleutel)).thenReturn(bijhoudingEntiteit);

        assertTrue(element.heeftObjectSleutel());
        assertEquals(objectSleutel, element.getObjectSleutel());
        assertEquals(BijhoudingPersoon.class, element.getEntiteitType());
        assertEquals(bijhoudingEntiteit, element.getEntiteit());
        assertTrue(element.inObjectSleutelIndex());
    }

    @Test
    public void testRelatieElement() {
        final String objectSleutel = "4940240812142708458_7510841056894151746_8058258521516688504";
        final RelatieElement element = builder.maakHuwelijkElement("CI_huwelijk", objectSleutel, null, Collections.emptyList());
        element.setVerzoekBericht(bericht);

        final BijhoudingRelatie bijhoudingEntiteit = new BijhoudingRelatie(new Relatie(SoortRelatie.HUWELIJK));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, objectSleutel)).thenReturn(bijhoudingEntiteit);

        assertTrue(element.heeftObjectSleutel());
        assertEquals(objectSleutel, element.getObjectSleutel());
        assertEquals(BijhoudingRelatie.class, element.getEntiteitType());
        assertEquals(bijhoudingEntiteit, element.getEntiteit());
        assertTrue(element.inObjectSleutelIndex());
    }

    @Test
    public void testOnderzoekElement() {
        final String objectSleutel = "4940240812142708458_7510841056894151746_8058258521516688504";
        final OnderzoekElement element = builder.maakOnderzoekElement("CI_onderzoek", objectSleutel, null, Collections.emptyList());
        element.setVerzoekBericht(bericht);

        final BijhoudingOnderzoek bijhoudingEntiteit =
                BijhoudingOnderzoek.decorate(new Onderzoek(new Partij("partij", "000010"), new Persoon(SoortPersoon.INGESCHREVENE)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingOnderzoek.class, objectSleutel)).thenReturn(bijhoudingEntiteit);

        assertTrue(element.heeftObjectSleutel());
        assertEquals(objectSleutel, element.getObjectSleutel());
        assertEquals(BijhoudingOnderzoek.class, element.getEntiteitType());
        assertEquals(bijhoudingEntiteit, element.getEntiteit());
        assertTrue(element.inObjectSleutelIndex());
    }

    @Test
    public void testBetrokkenheidElement() {
        final String objectSleutel = "4940240812142708458_7510841056894151746_8058258521516688504";
        final BetrokkenheidElement
                element =
                builder.maakBetrokkenheidElement("CI_betrokkenheid", objectSleutel, null, BetrokkenheidElementSoort.PARTNER, (HuwelijkElement) null);
        element.setVerzoekBericht(bericht);

        final BijhoudingBetrokkenheid bijhoudingEntiteit =
                BijhoudingBetrokkenheid.decorate(new Betrokkenheid(SoortBetrokkenheid.PARTNER, new Relatie(SoortRelatie.HUWELIJK)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingBetrokkenheid.class, objectSleutel)).thenReturn(bijhoudingEntiteit);

        assertTrue(element.heeftObjectSleutel());
        assertEquals(objectSleutel, element.getObjectSleutel());
        assertEquals(BijhoudingBetrokkenheid.class, element.getEntiteitType());
        assertEquals(bijhoudingEntiteit, element.getEntiteit());
        assertTrue(element.inObjectSleutelIndex());
    }

    @Test
    public void testNationaliteitElement() {
        final String objectSleutel = "1";
        final NationaliteitElement element =
                builder.maakNationaliteitElementVerlies("CI_nationaliteit", objectSleutel, null, "017");
        element.setVerzoekBericht(bericht);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);

        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("Nederlands", "0001"));
        nationaliteit.setId(1L);
        persoon.addPersoonNationaliteit(nationaliteit);

        assertTrue(element.heeftObjectSleutel());
        assertEquals(objectSleutel, element.getObjectSleutel());
        assertEquals(BijhoudingPersoonNationaliteit.class, element.getEntiteitType());
        assertFalse(element.inObjectSleutelIndex());
        assertNull(element.getEntiteit());

        element.bepaalBijhoudingPersoonNationaliteitEntiteit(bijhoudingPersoon);
        assertEquals(nationaliteit, element.getEntiteit().getDelegate());
    }

    @Test
    public void testBetrokkenheidElement_GeenRecordVoorSleutel() {
        final String objectSleutel = "2";
        final NationaliteitElement element =
                builder.maakNationaliteitElementVerlies("CI_nationaliteit", objectSleutel, null, "017");
        element.setVerzoekBericht(bericht);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);

        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("Nederlands", "0001"));
        nationaliteit.setId(1L);
        persoon.addPersoonNationaliteit(nationaliteit);

        assertTrue(element.heeftObjectSleutel());
        assertEquals(objectSleutel, element.getObjectSleutel());
        assertEquals(BijhoudingPersoonNationaliteit.class, element.getEntiteitType());
        assertFalse(element.inObjectSleutelIndex());
        assertNull(element.getEntiteit());

        element.bepaalBijhoudingPersoonNationaliteitEntiteit(bijhoudingPersoon);
        assertNull(element.getEntiteit());
    }

    @Test
    public void testBetrokkenheidElement_SleutelGeenGetal() {
        final String objectSleutel = "2a";
        final NationaliteitElement element =
                builder.maakNationaliteitElementVerlies("CI_nationaliteit", objectSleutel, null, "017");
        element.setVerzoekBericht(bericht);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);

        assertTrue(element.heeftObjectSleutel());
        assertEquals(objectSleutel, element.getObjectSleutel());
        assertEquals(BijhoudingPersoonNationaliteit.class, element.getEntiteitType());
        assertFalse(element.inObjectSleutelIndex());
        assertNull(element.getEntiteit());

        element.bepaalBijhoudingPersoonNationaliteitEntiteit(bijhoudingPersoon);
        assertNull(element.getEntiteit());
    }

    @Test
    public void testBetrokkenheidElement_GeenObjectsleutel() {
        final NationaliteitElement element =
                builder.maakNationaliteitElementVerlies("CI_nationaliteit", null, "1", "017");
        element.setVerzoekBericht(bericht);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);

        assertFalse(element.heeftObjectSleutel());
        assertNull(element.getObjectSleutel());
        assertEquals(BijhoudingPersoonNationaliteit.class, element.getEntiteitType());
        assertFalse(element.inObjectSleutelIndex());
        assertNull(element.getEntiteit());

        element.bepaalBijhoudingPersoonNationaliteitEntiteit(bijhoudingPersoon);
        assertNull(element.getEntiteit());
    }
}
