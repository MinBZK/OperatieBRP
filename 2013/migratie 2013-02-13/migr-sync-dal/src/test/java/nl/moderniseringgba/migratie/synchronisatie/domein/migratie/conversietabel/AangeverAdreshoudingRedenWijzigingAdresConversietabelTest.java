/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AangeverAdreshoudingRedenWijzigingAdresPaar;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.AangeverAdreshoudingRedenWijzigingAdresConversietabel;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.AangifteAdreshouding;

import org.junit.Before;
import org.junit.Test;

/**
 * Test de AangeverAdreshoudingRedenWijzigingAdresConversietabel.
 * 
 */
public class AangeverAdreshoudingRedenWijzigingAdresConversietabelTest {

    private AangeverAdreshoudingRedenWijzigingAdresConversietabel conversietabel;

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        conversietabel = new AangeverAdreshoudingRedenWijzigingAdresConversietabel(maakTestData());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOntbrekendeLo3ConversieFout() {
        conversietabel.converteerNaarBrp(Lo3AangifteAdreshoudingEnum.INGESCHREVENE.asElement());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOntbrekendeBrpConversieFout() {
        conversietabel.converteerNaarLo3(maakBrpPaar("I", "P"));
    }

    @Test
    public void testConverteerNaarBrp() {
        testVerwachtBrpResultaat(null, "A",
                conversietabel.converteerNaarBrp(Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement()));
        testVerwachtBrpResultaat(null, "M",
                conversietabel.converteerNaarBrp(Lo3AangifteAdreshoudingEnum.MINISTRIEEL_BESLUIT.asElement()));
        testVerwachtBrpResultaat("G", "P",
                conversietabel.converteerNaarBrp(Lo3AangifteAdreshoudingEnum.GEZAGHOUDER.asElement()));
        testVerwachtBrpResultaat("H", "P",
                conversietabel.converteerNaarBrp(Lo3AangifteAdreshoudingEnum.HOOFD_INSTELLING.asElement()));
        testVerwachtBrpResultaat("K", "P",
                conversietabel.converteerNaarBrp(Lo3AangifteAdreshoudingEnum.KIND.asElement()));
        testVerwachtBrpResultaat("M", "P",
                conversietabel.converteerNaarBrp(Lo3AangifteAdreshoudingEnum.GEMACHTIGDE.asElement()));
        testVerwachtBrpResultaat("O", "P",
                conversietabel.converteerNaarBrp(Lo3AangifteAdreshoudingEnum.OUDER.asElement()));
        testVerwachtBrpResultaat("P", "P",
                conversietabel.converteerNaarBrp(Lo3AangifteAdreshoudingEnum.PARTNER.asElement()));
        testVerwachtBrpResultaat(null, "B",
                conversietabel.converteerNaarBrp(Lo3AangifteAdreshoudingEnum.TECHNISCHE_WIJZIGING.asElement()));
        testVerwachtBrpResultaat(null, "I",
                conversietabel.converteerNaarBrp(Lo3AangifteAdreshoudingEnum.INFRASTRUCTURELE_WIJZIGING.asElement()));
        testVerwachtBrpResultaat(null, null,
                conversietabel.converteerNaarBrp(Lo3AangifteAdreshoudingEnum.ONBEKEND.asElement()));
    }

    @Test
    public void testConverteerNaarLO3() {
        testVerwachtLo3Resultaat("A", conversietabel.converteerNaarLo3(maakBrpPaar(null, "A")));
        testVerwachtLo3Resultaat("B", conversietabel.converteerNaarLo3(maakBrpPaar(null, "M")));
        testVerwachtLo3Resultaat("G", conversietabel.converteerNaarLo3(maakBrpPaar("G", "P")));
        testVerwachtLo3Resultaat("H", conversietabel.converteerNaarLo3(maakBrpPaar("H", "P")));
        testVerwachtLo3Resultaat("K", conversietabel.converteerNaarLo3(maakBrpPaar("K", "P")));
        testVerwachtLo3Resultaat("M", conversietabel.converteerNaarLo3(maakBrpPaar("M", "P")));
        testVerwachtLo3Resultaat("O", conversietabel.converteerNaarLo3(maakBrpPaar("O", "P")));
        testVerwachtLo3Resultaat("P", conversietabel.converteerNaarLo3(maakBrpPaar("P", "P")));
        testVerwachtLo3Resultaat("T", conversietabel.converteerNaarLo3(maakBrpPaar(null, "B")));
        testVerwachtLo3Resultaat("W", conversietabel.converteerNaarLo3(maakBrpPaar(null, "I")));
        testVerwachtLo3Resultaat(".", conversietabel.converteerNaarLo3(maakBrpPaar(null, null)));
    }

    private List<AangifteAdreshouding> maakTestData() throws NoSuchFieldException, IllegalAccessException {
        final List<AangifteAdreshouding> result = new ArrayList<AangifteAdreshouding>();
        result.add(maakTestAangifteAdreshouding("A", null, "A"));
        result.add(maakTestAangifteAdreshouding("B", null, "M"));
        result.add(maakTestAangifteAdreshouding("G", "G", "P"));
        result.add(maakTestAangifteAdreshouding("H", "H", "P"));
        // result.add(maakTestAangifteAdreshouding("I", "I", "P")); voor testen van ontbrekende mapping
        result.add(maakTestAangifteAdreshouding("K", "K", "P"));
        result.add(maakTestAangifteAdreshouding("M", "M", "P"));
        result.add(maakTestAangifteAdreshouding("O", "O", "P"));
        result.add(maakTestAangifteAdreshouding("P", "P", "P"));
        result.add(maakTestAangifteAdreshouding("T", null, "B"));
        result.add(maakTestAangifteAdreshouding("W", null, "I"));
        result.add(maakTestAangifteAdreshouding(".", null, null));
        return result;
    }

    private void testVerwachtLo3Resultaat(
            final String expectedLo3Code,
            final Lo3AangifteAdreshouding lo3AangifteAdreshouding) {
        if (lo3AangifteAdreshouding == null) {
            assertNull(expectedLo3Code);
        } else {
            assertEquals(expectedLo3Code, lo3AangifteAdreshouding.getCode());
        }
    }

    private void testVerwachtBrpResultaat(
            final String expectedBrpAangeverAdreshoudingCode,
            final String expectedBrpRedenwijzigingAdresCode,
            final AangeverAdreshoudingRedenWijzigingAdresPaar brpPaar) {
        if (brpPaar.getBrpAangeverAdreshoudingCode() == null) {
            assertNull(expectedBrpAangeverAdreshoudingCode);
        } else {
            assertEquals(expectedBrpAangeverAdreshoudingCode, brpPaar.getBrpAangeverAdreshoudingCode().getCode());
        }
        if (brpPaar.getBrpRedenWijzigingAdresCode() == null) {
            assertNull(expectedBrpRedenwijzigingAdresCode);
        } else {
            assertEquals(expectedBrpRedenwijzigingAdresCode, brpPaar.getBrpRedenWijzigingAdresCode().getCode());
        }
    }

    private AangeverAdreshoudingRedenWijzigingAdresPaar maakBrpPaar(
            final String brpAangeverAdreshoudingCode,
            final String brpRedenwijzigingAdresCode) {
        BrpAangeverAdreshoudingCode brpAangeverAdreshouding = null;
        if (brpAangeverAdreshoudingCode != null) {
            brpAangeverAdreshouding = new BrpAangeverAdreshoudingCode(brpAangeverAdreshoudingCode);
        }
        BrpRedenWijzigingAdresCode brpRedenWijzigingAdres = null;
        if (brpRedenwijzigingAdresCode != null) {
            brpRedenWijzigingAdres = new BrpRedenWijzigingAdresCode(brpRedenwijzigingAdresCode);
        }
        return new AangeverAdreshoudingRedenWijzigingAdresPaar(brpAangeverAdreshouding, brpRedenWijzigingAdres);
    }

    private AangifteAdreshouding maakTestAangifteAdreshouding(
            final String lo3Code,
            final String brpAangeverAdreshoudingCode,
            final String brpRedenwijzigingAdresCode) throws NoSuchFieldException, IllegalAccessException {
        final AangifteAdreshouding result = new AangifteAdreshouding();
        final Field lo3CodeField = AangifteAdreshouding.class.getDeclaredField("lo3Code");
        final Field brpAangeverAdreshoudingCodeField =
                AangifteAdreshouding.class.getDeclaredField("brpAangeverAdreshoudingCode");
        final Field lbrpRedenwijzigingAdresCodeField =
                AangifteAdreshouding.class.getDeclaredField("brpRedenwijzigingAdresCode");
        lo3CodeField.setAccessible(true);
        lo3CodeField.set(result, lo3Code);
        brpAangeverAdreshoudingCodeField.setAccessible(true);
        brpAangeverAdreshoudingCodeField.set(result, brpAangeverAdreshoudingCode);
        lbrpRedenwijzigingAdresCodeField.setAccessible(true);
        lbrpRedenwijzigingAdresCodeField.set(result, brpRedenwijzigingAdresCode);
        return result;
    }
}
