/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link AdministratienummerAttribuutValidator} class.
 */
public class AdministratienummerValidatorTest {

    private final AdministratienummerValidator validator = new AdministratienummerValidator();

    @Test
    public void testGeldigeAnummerValidatie() {
        Assert.assertTrue(validator.isValid(null, null));
        Assert.assertTrue(validator.isValid(new AdministratienummerAttribuut(7934628529L), null));
        Assert.assertTrue(validator.isValid(new AdministratienummerAttribuut(1373895379L), null));
        Assert.assertTrue(validator.isValid(new AdministratienummerAttribuut(2481379523L), null));
        Assert.assertTrue(validator.isValid(new AdministratienummerAttribuut(2785294396L), null));
        Assert.assertTrue(validator.isValid(new AdministratienummerAttribuut(4935964130L), null));

        // Regel 3 som delen door 11 geeft rest 5
        Assert.assertTrue(validator.isValid(new AdministratienummerAttribuut(1082678674L), null));
        Assert.assertTrue(validator.isValid(new AdministratienummerAttribuut(1968406546L), null));
        Assert.assertTrue(validator.isValid(new AdministratienummerAttribuut(5306471698L), null));
    }

    @Test
    public void testOnGeldigeAnummerValidatie() {
        Assert.assertFalse(validator.isValid(new AdministratienummerAttribuut(1L), null));

        // Regel 1: Eerste getal mag geen 0 zijn
        Assert.assertFalse(validator.isValid(new AdministratienummerAttribuut(734628529L), null));

        // Regel 2: Twee nummers mogen niet achter elkaar hetzelfde zijn
        Assert.assertFalse(validator.isValid(new AdministratienummerAttribuut(7734628529L), null));
        Assert.assertFalse(validator.isValid(new AdministratienummerAttribuut(7934628599L), null));

        // Regel 3: Som van alle getallen moet deelbaar door 11 of som +6 moet deelbaar zijn door 11
        Assert.assertFalse(validator.isValid(new AdministratienummerAttribuut(6734628523L), null));

        // Regel 4: Voeldoet niet, niet deelbaar door 11
        Assert.assertFalse(validator.isValid(new AdministratienummerAttribuut(4935964135L), null));
    }
}
