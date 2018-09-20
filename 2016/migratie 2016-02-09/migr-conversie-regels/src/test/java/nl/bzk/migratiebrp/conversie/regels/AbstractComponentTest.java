/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public abstract class AbstractComponentTest {

    /**
     * Valideert of de lo3String gelijk is aan de brpString.
     * 
     * @param lo3String
     *            LO3 string
     * @param brpString
     *            BRP string
     */
    public boolean assertLo3BrpEquals(final Lo3String lo3String, final BrpString brpString) {
        boolean result = true;
        final String lo3StringAsString = Lo3String.unwrap(lo3String);
        final String brpStringAsString = BrpString.unwrap(brpString);

        if (lo3StringAsString != null) {
            if (brpStringAsString == null || !brpStringAsString.equals(lo3StringAsString)) {
                result = false;
            }
        } else {
            if (brpStringAsString != null) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Valideert of de lo3Character gelijk is aan de brpCharacter.
     * 
     * @param lo3Character
     *            LO3 character
     * @param brpCharacter
     *            BRP character
     */
    public boolean assertLo3BrpEquals(final Lo3Character lo3Character, final BrpCharacter brpCharacter) {
        boolean result = true;

        final Character lo3CharacterAsChar = Lo3Character.unwrap(lo3Character);
        final Character brpCharacterAsChar = BrpCharacter.unwrap(brpCharacter);

        if (lo3CharacterAsChar != null) {
            if (brpCharacterAsChar == null || !brpCharacterAsChar.equals(lo3CharacterAsChar)) {
                result = false;
            }
        } else {
            if (brpCharacterAsChar != null) {
                result = false;
            }
        }
        return result;
    }
}
