/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

public class MutatieBerichtFilterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String WAARDE_010220_NIEUW = "waarde010220-nieuw";
    private static final String WAARDE_010230_NIEUW = "waarde010230-nieuw";
    private static final String WAARDE_010240_OUD = "waarde010240-oud";
    private static final String CODE_010230 = "01.02.30";
    private static final String CODE_01_02_40 = "01.02.40";
    private static final String WAARDE010240_NIEUW = "waarde010240-nieuw";
    private static final String CODE_010000 = "010000";
    private static final String WAARDECODE_20140101 = "20140101";
    private static final String WAARDECODE_010200 = "010200";
    private static final String CODE_010240 = "010240";
    private static final String WAARDE_010230_OUD = "waarde010230-oud";
    private static final String WAARDE_010220_OUD = "waarde010220-oud";

    private final MutatieBerichtFilter subject = new MutatieBerichtFilter();

    @Test
    public void test() {
        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0210, "waarde010210-nieuw");
        cat01.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_010220_NIEUW);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_010230_NIEUW);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0240, "");
        cat01.addElement(Lo3ElementEnum.ELEMENT_0310, "waarde010310-nieuw");

        final Lo3CategorieWaarde cat51 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 1);
        cat51.addElement(Lo3ElementEnum.ELEMENT_0210, "waarde010210-oud");
        cat51.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_010220_OUD);
        cat51.addElement(Lo3ElementEnum.ELEMENT_0230, "");
        cat51.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_010240_OUD);
        cat51.addElement(Lo3ElementEnum.ELEMENT_0310, "waarde010310-oud");

        final Lo3CategorieWaarde cat02 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_02, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0210, "waarde020210-nieuw");

        final Lo3CategorieWaarde cat52 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_52, 0, 1);
        cat52.addElement(Lo3ElementEnum.ELEMENT_0210, "waarde020210-oud");

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01, cat51, cat02, cat52);

        final List<String> rubrieken = Arrays.asList("01.02.20", CODE_010230, CODE_01_02_40, "51.02.10", "03.02.10", "52.02.10");

        final List<Lo3CategorieWaarde> gefilterd = subject.filter(null, null, null, categorieen, rubrieken);
        for (final Lo3CategorieWaarde filt : gefilterd) {
            LOGGER.info(filt.toString());
        }
        Assert.assertEquals(2, gefilterd.size());
        final Lo3CategorieWaarde filt01 = gefilterd.get(0);
        Assert.assertEquals(3, filt01.getElementen().size());
        Assert.assertEquals(WAARDE_010220_NIEUW, filt01.getElement(Lo3ElementEnum.ELEMENT_0220));
        Assert.assertEquals(WAARDE_010230_NIEUW, filt01.getElement(Lo3ElementEnum.ELEMENT_0230));
        Assert.assertEquals("", filt01.getElement(Lo3ElementEnum.ELEMENT_0240));

        final Lo3CategorieWaarde filt51 = gefilterd.get(1);
        Assert.assertEquals(3, filt51.getElementen().size());
        Assert.assertEquals(WAARDE_010220_OUD, filt51.getElement(Lo3ElementEnum.ELEMENT_0220));
        Assert.assertEquals("", filt51.getElement(Lo3ElementEnum.ELEMENT_0230));
        Assert.assertEquals(WAARDE_010240_OUD, filt51.getElement(Lo3ElementEnum.ELEMENT_0240));
    }

    @Test
    public void testOnderzoekOpCategorie() {
        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE010240_NIEUW);
        cat01.addElement(Lo3ElementEnum.ELEMENT_8310, CODE_010000);
        cat01.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDECODE_20140101);

        final Lo3CategorieWaarde cat51 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 1);
        cat51.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_010240_OUD);
        cat51.addElement(Lo3ElementEnum.ELEMENT_8310, CODE_010000);
        cat51.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDECODE_20140101);

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01, cat51);

        final List<String> filterRubrieken = Arrays.asList(CODE_01_02_40);

        final List<Lo3CategorieWaarde> gefilterd = subject.filter(null, null, null, categorieen, filterRubrieken);

        Assert.assertEquals(2, gefilterd.size());
        Assert.assertEquals(WAARDE010240_NIEUW, gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_0240));
        Assert.assertEquals(CODE_010000, gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_8310));
        Assert.assertEquals(WAARDECODE_20140101, gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_8320));

        Assert.assertEquals(WAARDE_010240_OUD, gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_0240));
        Assert.assertEquals(CODE_010000, gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_8310));
        Assert.assertEquals(WAARDECODE_20140101, gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_8320));
    }

    @Test
    public void testOnderzoekOpGroep() {
        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE010240_NIEUW);
        cat01.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDECODE_010200);
        cat01.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDECODE_20140101);

        final Lo3CategorieWaarde cat51 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 1);
        cat51.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_010240_OUD);
        cat51.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDECODE_010200);
        cat51.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDECODE_20140101);

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01, cat51);

        final List<String> filterRubrieken = Arrays.asList(CODE_01_02_40);

        final List<Lo3CategorieWaarde> gefilterd = subject.filter(null, null, null, categorieen, filterRubrieken);

        Assert.assertEquals(2, gefilterd.size());
        Assert.assertEquals(WAARDE010240_NIEUW, gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_0240));
        Assert.assertEquals(WAARDECODE_010200, gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_8310));
        Assert.assertEquals(WAARDECODE_20140101, gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_8320));

        Assert.assertEquals(WAARDE_010240_OUD, gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_0240));
        Assert.assertEquals(WAARDECODE_010200, gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_8310));
        Assert.assertEquals(WAARDECODE_20140101, gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_8320));
    }

    @Test
    public void testOnderzoekOpElement() {
        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE010240_NIEUW);
        cat01.addElement(Lo3ElementEnum.ELEMENT_8310, CODE_010240);
        cat01.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDECODE_20140101);

        final Lo3CategorieWaarde cat51 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 1);
        cat51.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_010240_OUD);
        cat51.addElement(Lo3ElementEnum.ELEMENT_8310, CODE_010240);
        cat51.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDECODE_20140101);

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01, cat51);

        final List<String> filterRubrieken = Arrays.asList(CODE_01_02_40);

        final List<Lo3CategorieWaarde> gefilterd = subject.filter(null, null, null, categorieen, filterRubrieken);

        Assert.assertEquals(2, gefilterd.size());
        Assert.assertEquals(WAARDE010240_NIEUW, gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_0240));
        Assert.assertEquals(CODE_010240, gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_8310));
        Assert.assertEquals(WAARDECODE_20140101, gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_8320));

        Assert.assertEquals(WAARDE_010240_OUD, gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_0240));
        Assert.assertEquals(CODE_010240, gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_8310));
        Assert.assertEquals(WAARDECODE_20140101, gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_8320));
    }

    @Test
    public void testOnderzoekOpNietGeautoriseerdeGegevens() {
        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_010230_NIEUW);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE010240_NIEUW);
        cat01.addElement(Lo3ElementEnum.ELEMENT_8310, CODE_010240);
        cat01.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDECODE_20140101);

        final Lo3CategorieWaarde cat51 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 1);
        cat51.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_010230_OUD);
        cat51.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_010240_OUD);
        cat51.addElement(Lo3ElementEnum.ELEMENT_8310, CODE_010240);
        cat51.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDECODE_20140101);

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01, cat51);

        final List<String> filterRubrieken = Arrays.asList(CODE_010230);

        final List<Lo3CategorieWaarde> gefilterd = subject.filter(null, null, null, categorieen, filterRubrieken);

        Assert.assertEquals(2, gefilterd.size());
        Assert.assertEquals(WAARDE_010230_NIEUW, gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_0230));
        Assert.assertNull(gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_0240));
        Assert.assertNull(gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_8310));
        Assert.assertNull(gefilterd.get(0).getElement(Lo3ElementEnum.ELEMENT_8320));

        Assert.assertEquals(WAARDE_010230_OUD, gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_0230));
        Assert.assertNull(gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_0240));
        Assert.assertNull(gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_8310));
        Assert.assertNull(gefilterd.get(1).getElement(Lo3ElementEnum.ELEMENT_8320));
    }

    @Test
    public void test_Uc1001_Gv01_01C30T120() {
        // CATEGORIE_01 -1 0 ELEMENT_8310 010240
        // CATEGORIE_01 -1 0 ELEMENT_8320 20140701
        // CATEGORIE_51 -1 1 ELEMENT_8310
        // CATEGORIE_51 -1 1 ELEMENT_8320
        // CATEGORIE_07 -1 0 ELEMENT_8010 0005
        // CATEGORIE_07 -1 0 ELEMENT_8020 20140701143501000
        // CATEGORIE_57 -1 1 ELEMENT_8010 0004
        // CATEGORIE_57 -1 1 ELEMENT_8020 20130701143501000

        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, -1, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_8310, "010240");
        cat01.addElement(Lo3ElementEnum.ELEMENT_8320, "20140701");
        final Lo3CategorieWaarde cat51 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, -1, 1);
        cat51.addElement(Lo3ElementEnum.ELEMENT_8310, "");
        cat51.addElement(Lo3ElementEnum.ELEMENT_8320, "");
        final Lo3CategorieWaarde cat07 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_07, -1, 0);
        cat07.addElement(Lo3ElementEnum.ELEMENT_8010, "0005");
        cat07.addElement(Lo3ElementEnum.ELEMENT_8020, "20140701143501000");
        final Lo3CategorieWaarde cat57 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_57, -1, 1);
        cat57.addElement(Lo3ElementEnum.ELEMENT_8010, "0004");
        cat57.addElement(Lo3ElementEnum.ELEMENT_8020, "20130701143501000");

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01, cat51, cat07, cat57);

        final List<String> filterRubrieken =
                Arrays.asList(
                    "01.01.10",
                    "01.01.20",
                    "01.02.10",
                    "01.02.20",
                    "01.02.30",
                    "01.03.10",
                    "01.03.20",
                    "01.03.30",
                    "01.04.10",
                    "01.20.10",
                    "01.20.20",
                    "01.61.10",
                    "01.81.10",
                    "01.81.20",
                    "01.82.10",
                    "01.82.20",
                    "01.82.30",
                    "01.85.10",
                    "01.86.10",
                    "51.01.10",
                    "51.01.20",
                    "51.02.10",
                    "51.02.20",
                    "51.02.30",
                    "51.03.10",
                    "51.03.20",
                    "51.03.30",
                    "51.04.10",
                    "51.20.10",
                    "51.20.20",
                    "51.61.10",
                    "51.81.10",
                    "51.81.20",
                    "51.82.10",
                    "51.82.20",
                    "51.82.30",
                    "51.85.10",
                    "51.86.10");

        final List<Lo3CategorieWaarde> gefilterd = subject.filter(null, null, null, categorieen, filterRubrieken);
        System.out.println("GEFILTERED: " + gefilterd);
        Assert.assertEquals(0, gefilterd.size());
    }

    @Test
    public void testEmpty() {
        Assert.assertEquals(0, subject.filter(null, null, null, null, null).size());
        Assert.assertEquals(0, subject.filter(null, null, null, null, Collections.<String>emptyList()).size());
    }
}
