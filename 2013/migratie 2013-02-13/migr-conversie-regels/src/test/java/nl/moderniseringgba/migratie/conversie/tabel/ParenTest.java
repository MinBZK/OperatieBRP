/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AangeverAdreshoudingRedenWijzigingAdresPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.RedenVerkrijgingVerliesPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.testutils.EqualsAndHashcodeTester;

import org.junit.Test;

public class ParenTest {

    @Test
    public void testVoorvoegselScheidingstekenPaar() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new VoorvoegselScheidingstekenPaar("X", 'Y'),
                new VoorvoegselScheidingstekenPaar("X", 'Y'), new VoorvoegselScheidingstekenPaar("A", 'B'));
    }

    @Test
    public void testAdellijkeTitelPredikaatPaar() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new AdellijkeTitelPredikaatPaar('X', 'Y',
                BrpGeslachtsaanduidingCode.MAN), new AdellijkeTitelPredikaatPaar('X', 'Y',
                BrpGeslachtsaanduidingCode.MAN), new AdellijkeTitelPredikaatPaar('A', 'B',
                BrpGeslachtsaanduidingCode.VROUW));
    }

    @Test
    public void testRedenVerkrijgingVerliesPaar() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new RedenVerkrijgingVerliesPaar(
                new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("001")),
                new BrpRedenVerliesNederlandschapCode(new BigDecimal("002"))), new RedenVerkrijgingVerliesPaar(
                new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("001")),
                new BrpRedenVerliesNederlandschapCode(new BigDecimal("002"))), new RedenVerkrijgingVerliesPaar(
                new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("003")),
                new BrpRedenVerliesNederlandschapCode(new BigDecimal("004"))));
    }

    @Test
    public void testAangeverAdreshoudingRedenWijzigingAdresPaar() throws Exception {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new AangeverAdreshoudingRedenWijzigingAdresPaar(
                new BrpAangeverAdreshoudingCode("X"), new BrpRedenWijzigingAdresCode("Y")),
                new AangeverAdreshoudingRedenWijzigingAdresPaar(new BrpAangeverAdreshoudingCode("X"),
                        new BrpRedenWijzigingAdresCode("Y")), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                        new BrpAangeverAdreshoudingCode("A"), new BrpRedenWijzigingAdresCode("B")));
    }
}
