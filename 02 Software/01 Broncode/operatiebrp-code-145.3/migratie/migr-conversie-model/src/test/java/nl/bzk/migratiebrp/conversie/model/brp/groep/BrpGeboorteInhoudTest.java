/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import org.junit.Test;

public class BrpGeboorteInhoudTest {

    private static final String CODE_1234 = "1234";
    private static final String CODE_6030 = "6030";
    private static final String WOONPLAATSNAAM_GEBOORTE = "woonplaatsnaamGeboorte";
    private static final String BLGP = "blgp";
    private static final String BRC = "brc";
    private static final String OMSCHRIJVING = "omschrijving";
    public static final int JAN_01_1980 = 1980_01_01;

    /**
     * returns BrpGeboorteInhoud met waardes GeboorteDatum = 1980-01-01 gemeente code = 1234 Woonplaats geboorte =
     * woonplaatsnaamGeboorte buitenlandse plaats geboorte = blgp buitenlandse regio geboorte = brc land of gebied code
     * = 6030 omschrijving geb locatie = omschrijving
     * @return BrpGeboorteInhoud
     */
    public static BrpGeboorteInhoud getBrpGeboorteInhoud() {
        return BrpGeboorteInhoudTest.getBrpGeboorteInhoud(JAN_01_1980);
    }

    /**
     * returns BrpGeboorteInhoud met meegegeven GeboorteDatum en gemeente code = 1234 Woonplaats geboorte =
     * woonplaatsnaamGeboorte buitenlandse plaats geboorte = blgp buitenlandse regio geboorte = brc land of gebied code
     * = 6030 omschrijving geb locatie = omschrijving
     * @param datum datum
     * @return BrpGeboorteInhoud
     */
    public static BrpGeboorteInhoud getBrpGeboorteInhoud(int datum) {
        return new BrpGeboorteInhoud(new BrpDatum(datum, null), null, new BrpString(WOONPLAATSNAAM_GEBOORTE), null, null, null, new BrpString(
                OMSCHRIJVING));
    }

    public static BrpStapel<BrpGeboorteInhoud> createStapel() {
        List<BrpGroep<BrpGeboorteInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpGeboorteInhoud> groep = new BrpGroep<>(getBrpGeboorteInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    @Test
    public void testHashCode() {
        final BrpGeboorteInhoud inhoud1 =
                new BrpGeboorteInhoud(
                        new BrpDatum(JAN_01_1980, null),
                        new BrpGemeenteCode(CODE_1234),
                        null,
                        null,
                        null,
                        new BrpLandOfGebiedCode(CODE_6030),
                        null);
        final BrpGeboorteInhoud inhoud2 =
                new BrpGeboorteInhoud(
                        new BrpDatum(JAN_01_1980, null),
                        new BrpGemeenteCode(CODE_1234),
                        null,
                        null,
                        null,
                        new BrpLandOfGebiedCode(CODE_6030),
                        null);
        assertEquals(inhoud1.hashCode(), inhoud2.hashCode());
    }

    @Test
    public void testBrpGeboorteInhoud() {
        new BrpGeboorteInhoud(
                new BrpDatum(JAN_01_1980, null),
                new BrpGemeenteCode(CODE_1234),
                null,
                null,
                null,
                new BrpLandOfGebiedCode(CODE_6030),
                null);
    }

    @Test
    public void testIsLeeg() {
        final BrpGeboorteInhoud inhoud1 =
                new BrpGeboorteInhoud(
                        new BrpDatum(JAN_01_1980, null),
                        new BrpGemeenteCode(CODE_1234),
                        null,
                        null,
                        null,
                        new BrpLandOfGebiedCode(CODE_6030),
                        null);
        final BrpGeboorteInhoud inhoud2 = new BrpGeboorteInhoud(null, null, null, null, null, null, null);
        assertFalse(inhoud1.isLeeg());
        assertTrue(inhoud2.isLeeg());
    }

    @Test
    public void testEqualsObject() {
        final BrpGeboorteInhoud inhoud1 =
                new BrpGeboorteInhoud(
                        new BrpDatum(JAN_01_1980, null),
                        new BrpGemeenteCode(CODE_1234),
                        null,
                        null,
                        null,
                        new BrpLandOfGebiedCode(CODE_6030),
                        null);
        final BrpGeboorteInhoud inhoud2 =
                new BrpGeboorteInhoud(
                        new BrpDatum(JAN_01_1980, null),
                        new BrpGemeenteCode(CODE_1234),
                        null,
                        null,
                        null,
                        new BrpLandOfGebiedCode(CODE_6030),
                        null);
        final BrpGeboorteInhoud inhoud3 =
                new BrpGeboorteInhoud(
                        new BrpDatum(JAN_01_1980, null),
                        new BrpGemeenteCode(CODE_1234),
                        null,
                        new BrpString(BLGP),
                        null,
                        new BrpLandOfGebiedCode(CODE_6030),
                        null);

        final BrpGeboorteInhoud inhoud4 =
                new BrpGeboorteInhoud(
                        new BrpDatum(JAN_01_1980, null),
                        new BrpGemeenteCode(CODE_1234),
                        new BrpString(WOONPLAATSNAAM_GEBOORTE),
                        new BrpString(BLGP),
                        new BrpString(BRC),
                        new BrpLandOfGebiedCode(CODE_6030),
                        new BrpString(OMSCHRIJVING));
        final BrpGeboorteInhoud inhoud5 =
                new BrpGeboorteInhoud(
                        new BrpDatum(JAN_01_1980, null),
                        new BrpGemeenteCode(CODE_1234),
                        new BrpString(WOONPLAATSNAAM_GEBOORTE),
                        new BrpString(BLGP),
                        new BrpString(BRC),
                        new BrpLandOfGebiedCode(CODE_6030),
                        new BrpString(OMSCHRIJVING));

        assertEquals(inhoud1, inhoud2);
        assertNotSame(inhoud1, inhoud3);
        assertEquals(inhoud4, inhoud5);
    }

    @Test
    public void testToString() {
        final BrpGeboorteInhoud inhoud1 =
                new BrpGeboorteInhoud(
                        new BrpDatum(JAN_01_1980, null),
                        new BrpGemeenteCode(CODE_1234),
                        null,
                        null,
                        null,
                        new BrpLandOfGebiedCode(CODE_6030),
                        null);
        final BrpGeboorteInhoud inhoud2 =
                new BrpGeboorteInhoud(
                        new BrpDatum(JAN_01_1980, null),
                        new BrpGemeenteCode(CODE_1234),
                        null,
                        null,
                        null,
                        new BrpLandOfGebiedCode(CODE_6030),
                        null);
        assertEquals(inhoud1.toString(), inhoud2.toString());
    }
}
