/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

public class Tb02SoortAkteTest {
    /**
     * Test of values method, of class AkteEnum.
     */
    @Test
    public void testValues() {
        Assert.assertEquals("Aantal akten moet 18 zijn", 18, Tb02SoortAkte.values().length);
    }

    /**
     * Test of valueOf method, of class AkteEnum.
     */
    @Test
    public void testValueOf_String() {
        Tb02SoortAkte.AKTE_3B.equals(Tb02SoortAkte.valueOf("AKTE_3B"));
    }

    /**
     * Test of zijnJuisteCategorieenAanwezig method, of class AkteEnum.
     */
    @Test
    public void testZijnJuisteCategorieenAanwezigMetAlleenVerplichtElementenAanwezig() throws Exception {
        final List<Lo3CategorieWaarde> waarden =
                Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                    }
                }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                        put(Lo3ElementEnum.ELEMENT_0610, "");
                        put(Lo3ElementEnum.ELEMENT_0620, "");
                        put(Lo3ElementEnum.ELEMENT_0630, "");
                        put(Lo3ElementEnum.ELEMENT_1510, "");
                        put(Lo3ElementEnum.ELEMENT_8110, "");
                        put(Lo3ElementEnum.ELEMENT_8120, "");
                        put(Lo3ElementEnum.ELEMENT_8510, "");
                    }
                }));

        final List<String> overtredingen = new ArrayList<>();
        Tb02SoortAkte.AKTE_3A.controleerCategorieenEnElementen("36A123456789", waarden, overtredingen);
        Assert.assertTrue(overtredingen.isEmpty());

    }

    /**
     * Test of zijnJuisteCategorieenAanwezig method, of class AkteEnum.
     */
    @Test
    public void testZijnJuisteCategorieenAanwezigMetAlleVerplichteEnOptioneleElementenAanwezig() throws Exception {
        final List<Lo3CategorieWaarde> waarden =
                Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0120, "");
                        put(Lo3ElementEnum.ELEMENT_0210, "");
                        put(Lo3ElementEnum.ELEMENT_0220, "");
                        put(Lo3ElementEnum.ELEMENT_0230, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");

                    }
                }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0120, "");
                        put(Lo3ElementEnum.ELEMENT_0210, "");
                        put(Lo3ElementEnum.ELEMENT_0220, "");
                        put(Lo3ElementEnum.ELEMENT_0230, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                        put(Lo3ElementEnum.ELEMENT_0610, "");
                        put(Lo3ElementEnum.ELEMENT_0620, "");
                        put(Lo3ElementEnum.ELEMENT_0630, "");
                        put(Lo3ElementEnum.ELEMENT_1510, "");
                        put(Lo3ElementEnum.ELEMENT_8110, "");
                        put(Lo3ElementEnum.ELEMENT_8120, "");
                        put(Lo3ElementEnum.ELEMENT_8510, "");
                    }
                }));

        final List<String> overtredingen = new ArrayList<>();
        Tb02SoortAkte.AKTE_3A.controleerCategorieenEnElementen("36A123456789", waarden, overtredingen);
        Assert.assertTrue(overtredingen.isEmpty());
    }

    /**
     * Test of zijnJuisteCategorieenAanwezig method, of class AkteEnum.
     */
    @Test
    public void testZijnJuisteCategorieenAanwezigMetOntbrekendeVerplichtElementen() throws Exception {
        final List<Lo3CategorieWaarde> waarden =
                Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0120, "");
                    }
                }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0610, "");
                        put(Lo3ElementEnum.ELEMENT_0620, "");
                        put(Lo3ElementEnum.ELEMENT_0630, "");
                        put(Lo3ElementEnum.ELEMENT_8110, "");
                        put(Lo3ElementEnum.ELEMENT_8120, "");
                        put(Lo3ElementEnum.ELEMENT_8510, "");
                    }
                }));

        final List<String> overtredingen = new ArrayList<>();
        Tb02SoortAkte.AKTE_3A.controleerCategorieenEnElementen("36A123456789", waarden, overtredingen);
        Assert.assertEquals(12, overtredingen.size());
    }

    /**
     * Test of zijnJuisteCategorieenAanwezig method, of class AkteEnum.
     */
    @Test
    public void testZijnJuisteCategorieenAanwezigMetNietToegestaneElementenAanwezig() throws Exception {
        final List<Lo3CategorieWaarde> waarden =
                Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                        put(Lo3ElementEnum.ELEMENT_0610, "");
                        put(Lo3ElementEnum.ELEMENT_0620, "");
                        put(Lo3ElementEnum.ELEMENT_0630, "");
                    }
                }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "");
                        put(Lo3ElementEnum.ELEMENT_0310, "");
                        put(Lo3ElementEnum.ELEMENT_0320, "");
                        put(Lo3ElementEnum.ELEMENT_0330, "");
                        put(Lo3ElementEnum.ELEMENT_0410, "");
                        put(Lo3ElementEnum.ELEMENT_0610, "");
                        put(Lo3ElementEnum.ELEMENT_0620, "");
                        put(Lo3ElementEnum.ELEMENT_0630, "");
                        put(Lo3ElementEnum.ELEMENT_1510, "");
                        put(Lo3ElementEnum.ELEMENT_8110, "");
                        put(Lo3ElementEnum.ELEMENT_8120, "");
                        put(Lo3ElementEnum.ELEMENT_8510, "");
                    }
                }));

        final List<String> overtredingen = new ArrayList<>();
        Tb02SoortAkte.AKTE_3A.controleerCategorieenEnElementen("36A123456789", waarden, overtredingen);
        Assert.assertEquals(3, overtredingen.size());
    }

    /**
     * Test of zijnJuisteCategorieenAanwezig method, of class AkteEnum.
     */
    @Test
    public void testZijnJuisteCategorieenAanwezigMetDubbeleCategorieen() throws Exception {
        final List<Lo3CategorieWaarde> waarden =
                Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                    }
                }), new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                    }
                }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0610, "");
                        put(Lo3ElementEnum.ELEMENT_0620, "");
                        put(Lo3ElementEnum.ELEMENT_0630, "");
                        put(Lo3ElementEnum.ELEMENT_1510, "");
                        put(Lo3ElementEnum.ELEMENT_8110, "");
                        put(Lo3ElementEnum.ELEMENT_8120, "");
                        put(Lo3ElementEnum.ELEMENT_8510, "");
                    }
                }));

        final List<String> overtredingen = new ArrayList<>();
        Tb02SoortAkte.AKTE_3A.controleerCategorieenEnElementen("36A123456789", waarden, overtredingen);
        Assert.assertEquals(11, overtredingen.size());
    }

    /**
     * Test of valueOf method, of class AkteEnum.
     */
    @Test
    public void testValueOf_char_char_BestaandeAkte() {
        Assert.assertEquals(Tb02SoortAkte.AKTE_3A, Tb02SoortAkte.bepaalAkteObvAktenummer("3.A...."));
        Assert.assertEquals(null, Tb02SoortAkte.bepaalAkteObvAktenummer("9.Z...."));
    }
}
