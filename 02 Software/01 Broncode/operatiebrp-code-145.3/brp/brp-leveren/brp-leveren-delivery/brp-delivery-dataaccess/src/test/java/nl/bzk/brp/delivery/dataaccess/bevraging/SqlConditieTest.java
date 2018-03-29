/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Lists;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import org.junit.Test;

public class SqlConditieTest {

    private static final String ALIAS = "alias";
    private static final String KOLOM = "kolom";

    @Test
    public void testMaakConditieLeeg() {
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM);
        final ZoekCriterium zoekCriterium = new ZoekCriterium(attribuutElement, Zoekoptie.LEEG, null);
        final boolean postgres = true;
        final boolean nietLeeg = false;

        final SqlConditie sql = SqlConditie.maakConditie(zoekCriterium, attribuutElement, ALIAS, KOLOM, nietLeeg);

        assertEquals("alias.kolom is  null", sql.getConditie().trim());
        assertEquals(Collections.EMPTY_LIST, sql.getParameters());
    }

    @Test
    public void testMaakConditieNietLeeg() {
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM);
        final ZoekCriterium zoekCriterium = new ZoekCriterium(attribuutElement, Zoekoptie.LEEG, null);
        final boolean nietLeeg = true;

        final SqlConditie sql = SqlConditie.maakConditie(zoekCriterium, attribuutElement, ALIAS, KOLOM, nietLeeg);

        assertEquals("alias.kolom is not null", sql.getConditie().trim());
        assertEquals(Collections.EMPTY_LIST, sql.getParameters());
    }

    @Test
    public void testMaakConditieVanafDatum() {
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM);
        final ZoekCriterium zoekCriterium = new ZoekCriterium(attribuutElement, Zoekoptie.VANAF_KLEIN, 19760000);
        final boolean nietLeeg = false;

        final SqlConditie sql = SqlConditie.maakConditie(zoekCriterium, attribuutElement, ALIAS, KOLOM, nietLeeg);

        assertEquals("alias.kolom between (?) and (?)", sql.getConditie().trim());
        assertEquals(Lists.newArrayList(19760000, 19769999), sql.getParameters());
    }

    @Test
    public void testMaakConditieVanafStringPostgres() {
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_BUITENLANDSEPLAATS);
        final ZoekCriterium zoekCriterium = new ZoekCriterium(attribuutElement, Zoekoptie.VANAF_KLEIN, "Buiten");
        final boolean nietLeeg = false;

        final SqlConditie sql = SqlConditie.maakConditie(zoekCriterium, attribuutElement, ALIAS, KOLOM, nietLeeg);

        assertEquals("brp_unaccent(alias.kolom) like brp_unaccent(?) and alias.kolom is not null", sql.getConditie().trim());
        assertEquals(Lists.newArrayList("Buiten%"), sql.getParameters());
    }

    @Test
    public void testMaakConditieKleinPostgres() {
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_BUITENLANDSEPLAATS);
        final ZoekCriterium zoekCriterium = new ZoekCriterium(attribuutElement, Zoekoptie.KLEIN, "Buiten");
        final boolean postgres = true;
        final boolean nietLeeg = false;

        final SqlConditie sql = SqlConditie.maakConditie(zoekCriterium, attribuutElement, ALIAS, KOLOM, nietLeeg);

        assertEquals("brp_unaccent(alias.kolom) = brp_unaccent(?)", sql.getConditie().trim());
        assertEquals(Lists.newArrayList("Buiten"), sql.getParameters());
    }

    @Test
    public void testMaakConditieExactPostgres() {
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_BUITENLANDSEPLAATS);
        final ZoekCriterium zoekCriterium = new ZoekCriterium(attribuutElement, Zoekoptie.EXACT, "Buiten");
        final boolean nietLeeg = false;

        final SqlConditie sql = SqlConditie.maakConditie(zoekCriterium, attribuutElement, ALIAS, KOLOM, nietLeeg);

        assertEquals("brp_unaccent(alias.kolom) = brp_unaccent(?) and alias.kolom = ?", sql.getConditie().trim());
        assertEquals(Lists.newArrayList("Buiten", "Buiten"), sql.getParameters());
    }
}
