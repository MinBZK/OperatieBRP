/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

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

public class BrpOverlijdenInhoudTest {

    private static final String STRING_1234 = "1234";
    private static final String STRING_6030 = "6030";
    private static final String OMSCHRIJVING = "omschrijving";

    private final BrpOverlijdenInhoud inhoud1 = new BrpOverlijdenInhoud(
            new BrpDatum(20080102, null),
            new BrpGemeenteCode(STRING_1234),
            new BrpString("WP"),
            new BrpString("BP"),
            new BrpString("BR"),
            new BrpLandOfGebiedCode(STRING_6030),
            new BrpString(OMSCHRIJVING));
    private final BrpOverlijdenInhoud inhoud2gelijkAan1 = new BrpOverlijdenInhoud(new BrpDatum(20080102, null), new BrpGemeenteCode(
            STRING_1234), new BrpString("WP"), new BrpString("BP"), new BrpString("BR"), new BrpLandOfGebiedCode(
            STRING_6030), new BrpString(OMSCHRIJVING));
    private final BrpOverlijdenInhoud inhoud3ongelijkAan1 = new BrpOverlijdenInhoud(
            new BrpDatum(20080102, null),
            new BrpGemeenteCode("3333"),
            new BrpString("WP"),
            new BrpString("BP"),
            new BrpString("BR"),
            new BrpLandOfGebiedCode(STRING_6030),
            new BrpString(OMSCHRIJVING));
    private final BrpOverlijdenInhoud inhoud4ongelijkAan1 = new BrpOverlijdenInhoud(new BrpDatum(20080202, null), new BrpGemeenteCode(
            STRING_1234), new BrpString("WP"), new BrpString("BP"), new BrpString("BR"), new BrpLandOfGebiedCode(
            STRING_6030), new BrpString(OMSCHRIJVING));

    public static BrpOverlijdenInhoud createInhoud() {
        return new BrpOverlijdenInhoud(
                new BrpDatum(20080102, null),
                new BrpGemeenteCode(STRING_1234),
                new BrpString("WP"),
                null,
                null,
                BrpLandOfGebiedCode.NEDERLAND,
                new BrpString(OMSCHRIJVING));
    }

    public static BrpStapel<BrpOverlijdenInhoud> createStapel() {
        List<BrpGroep<BrpOverlijdenInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpOverlijdenInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    @Test
    public void testHashCode() {
        assertEquals(inhoud1.hashCode(), inhoud2gelijkAan1.hashCode());
    }

    @Test(expected = NullPointerException.class)
    public void testBrpOverlijdenInhoudDatumNull() {
        new BrpOverlijdenInhoud(
                null,
                new BrpGemeenteCode(STRING_1234),
                new BrpString("WP"),
                new BrpString("BP"),
                new BrpString("BR"),
                new BrpLandOfGebiedCode(STRING_6030),
                new BrpString(OMSCHRIJVING));
    }

    @Test(expected = NullPointerException.class)
    public void testBrpOverlijdenInhoudLandCodeNull() {
        new BrpOverlijdenInhoud(
                new BrpDatum(20080102, null),
                new BrpGemeenteCode(STRING_1234),
                new BrpString("WP"),
                new BrpString("BP"),
                new BrpString("BR"),
                null,
                new BrpString(OMSCHRIJVING));
    }

    @Test
    public void testGetDatum() {
        assertEquals(new BrpDatum(20080102, null), inhoud1.getDatum());
    }

    @Test
    public void testGetGemeenteCode() {
        assertEquals(new BrpGemeenteCode(STRING_1234), inhoud1.getGemeenteCode());
    }

    @Test
    public void testGetPlaatsCode() {
        assertEquals(new BrpString("WP"), inhoud1.getWoonplaatsnaamOverlijden());
    }

    @Test
    public void testGetBuitenlandsePlaats() {
        assertEquals(new BrpString("BP"), inhoud1.getBuitenlandsePlaats());
    }

    @Test
    public void testGetBuitenlandseRegio() {
        assertEquals(new BrpString("BR"), inhoud1.getBuitenlandseRegio());
    }

    @Test
    public void testGetLandCode() {
        assertEquals(new BrpLandOfGebiedCode(STRING_6030), inhoud1.getLandOfGebiedCode());
    }

    @Test
    public void testGetOmschrijvingLocatie() {
        assertEquals(new BrpString(OMSCHRIJVING), inhoud1.getOmschrijvingLocatie());
    }

    @Test
    public void testIsLeeg() {
        assertFalse(inhoud1.isLeeg());
        assertFalse(new BrpOverlijdenInhoud(
                new BrpDatum(20090102, null),
                null,
                null,
                null,
                null,
                new BrpLandOfGebiedCode(STRING_6030),
                null).isLeeg());
    }

    @Test
    public void testEqualsObject() {
        assertEquals(inhoud1, inhoud2gelijkAan1);
        assertNotSame(inhoud1, inhoud3ongelijkAan1);
        assertNotSame(inhoud1, inhoud4ongelijkAan1);
    }

    @Test
    public void testToString() {
        assertEquals(inhoud1.toString(), inhoud2gelijkAan1.toString());
    }

}
