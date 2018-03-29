/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.bevraging;

import static org.junit.Assert.assertEquals;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import org.junit.Test;

public class ZoekCriteriumTest {

    @Test
    public void nullwaarde() {
        assertEquals(null, criterium(null).getWaarde());
    }

    @Test
    public void exactIsNietCaseInsensitive() {
        assertEquals(Zoekoptie.EXACT, criterium("\\exacte_waarde").getZoekOptie(true));
        assertEquals(Zoekoptie.EXACT, criterium("\\exacte_waarde").getZoekOptie(false));
    }

    @Test
    public void waardeIsCaseInsensitive() {
        assertEquals(Zoekoptie.KLEIN, criterium("waarde").getZoekOptie(true));
        assertEquals(Zoekoptie.EXACT, criterium("waarde").getZoekOptie(false));
    }

    @Test
    public void isCaseInSensitiveMetGetal() {
        assertEquals(Zoekoptie.KLEIN, criterium("waarde01").getZoekOptie(true));
        assertEquals(Zoekoptie.EXACT, criterium("waarde01").getZoekOptie(false));
    }

    @Test
    public void isCaseInSensitiveMetHoofdletter() {
        assertEquals(Zoekoptie.EXACT, criterium("waarDe").getZoekOptie(true));
        assertEquals(Zoekoptie.EXACT, criterium("waarDe").getZoekOptie(false));
    }

    @Test
    public void isCaseInSensitiveMetAndereKarakters() {
        assertEquals(Zoekoptie.EXACT, criterium("waarde01+-_,!@#$%^&").getZoekOptie(false));
        assertEquals(Zoekoptie.KLEIN, criterium("waarde01+-_,!@#$%^&").getZoekOptie(true));
    }

    @Test
    public void getWaardeExact() {
        assertEquals("\\exacte_waarde", criterium("\\exacte_waarde").getWaarde());
    }

    @Test
    public void getSlimZoekenWaardeExact() {
        assertEquals("exacte_waarde", criterium("\\exacte_waarde").getSlimZoekenWaarde());
    }

    @Test
    public void getWaarde() {
        assertEquals("geen_exacte_waarde", criterium("geen_exacte_waarde").getWaarde());
    }

    @Test
    public void getSlimZoekenWaarde() {
        assertEquals("geen_exacte_waarde", criterium("geen_exacte_waarde").getSlimZoekenWaarde());
    }

    @Test
    public void isWildcard() {
        assertEquals(Zoekoptie.VANAF_KLEIN, criterium("wildcard_waarde*").getZoekOptie(true));
        assertEquals(Zoekoptie.EXACT, criterium("wildcard_waarde*").getZoekOptie(false));
    }

    @Test
    public void getWaardeWildcard() {
        assertEquals("wildcard_waarde*", criterium("wildcard_waarde*").getWaarde());
    }

    @Test
    public void getSlimZoekenWaardeWildcard() {
        assertEquals("wildcard_waarde", criterium("wildcard_waarde*").getSlimZoekenWaarde());
    }

    @Test
    public void isDiakrietZonderDiakriet() {
        assertEquals(Zoekoptie.KLEIN, criterium("waarde").getZoekOptie(true));
        assertEquals(Zoekoptie.KLEIN, criterium("ß").getZoekOptie(true));
        assertEquals(Zoekoptie.EXACT, criterium("waarde").getZoekOptie(false));
        assertEquals(Zoekoptie.EXACT, criterium("ß").getZoekOptie(false));
    }

    @Test
    public void isDiakrietMetDiakriet() {
        assertEquals(Zoekoptie.EXACT, criterium("Àa").getZoekOptie(false));
        assertEquals(Zoekoptie.EXACT, criterium("ža").getZoekOptie(false));
        assertEquals(Zoekoptie.EXACT, criterium("aÀa").getZoekOptie(false));
        assertEquals(Zoekoptie.EXACT, criterium("aža").getZoekOptie(true));
        assertEquals(Zoekoptie.EXACT, criterium("aÀ").getZoekOptie(true));
        assertEquals(Zoekoptie.EXACT, criterium("až").getZoekOptie(true));
    }

    @Test
    public void isDiakrietMetDiakrietEnWildcard() {
        assertEquals(Zoekoptie.EXACT, criterium("Àa*").getZoekOptie(false));
        assertEquals(Zoekoptie.EXACT, criterium("ža*").getZoekOptie(false));
        assertEquals(Zoekoptie.EXACT, criterium("aÀa*").getZoekOptie(false));
        assertEquals(Zoekoptie.VANAF_EXACT, criterium("aža*").getZoekOptie(true));
        assertEquals(Zoekoptie.VANAF_EXACT, criterium("aÀ*").getZoekOptie(true));
        assertEquals(Zoekoptie.VANAF_EXACT, criterium("až*").getZoekOptie(true));
    }

    @Test
    public void isDiakrietMetNietToegestaneDiakriet() {
        assertEquals(Zoekoptie.EXACT, criterium("w̹orde").getZoekOptie(true));
        assertEquals(Zoekoptie.EXACT, criterium("w̹orde").getZoekOptie(false));
    }

    private ZoekCriterium criterium(final String waarde) {
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde(waarde);
        return criterium;
    }
}
