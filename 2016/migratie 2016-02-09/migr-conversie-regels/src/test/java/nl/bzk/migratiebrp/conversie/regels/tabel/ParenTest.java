/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import org.junit.Test;

public class ParenTest {

    @Test
    public void testVoorvoegselScheidingstekenPaar() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
            new VoorvoegselScheidingstekenPaar(new BrpString("X"), new BrpCharacter('Y')),
            new VoorvoegselScheidingstekenPaar(new BrpString("X"), new BrpCharacter('Y')),
            new VoorvoegselScheidingstekenPaar(new BrpString("A"), new BrpCharacter('B')));
    }

    @Test
    public void testAdellijkeTitelPredikaatPaar() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new AdellijkeTitelPredikaatPaar(
            new BrpCharacter('X'),
            new BrpCharacter('Y'),
            BrpGeslachtsaanduidingCode.MAN), new AdellijkeTitelPredikaatPaar(
            new BrpCharacter('X'),
            new BrpCharacter('Y'),
            BrpGeslachtsaanduidingCode.MAN), new AdellijkeTitelPredikaatPaar(
            new BrpCharacter('A'),
            new BrpCharacter('B'),
            BrpGeslachtsaanduidingCode.VROUW));
    }

    @Test
    public void testAangeverAdreshoudingRedenWijzigingAdresPaar() throws NoSuchMethodException, IllegalAccessException {
        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(new AangeverRedenWijzigingVerblijfPaar(
            new BrpAangeverCode('X'),
            new BrpRedenWijzigingVerblijfCode('Y')), new AangeverRedenWijzigingVerblijfPaar(
            new BrpAangeverCode('X'),
            new BrpRedenWijzigingVerblijfCode('Y')), new AangeverRedenWijzigingVerblijfPaar(

        new BrpAangeverCode('A'), new BrpRedenWijzigingVerblijfCode('B')));
    }
}
