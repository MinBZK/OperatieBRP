/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 */
public class BRBY0444ValidatorTest {

    private final BRBY0444Validator validator = new BRBY0444Validator();

    @Test
    public void testDatumInDeToekomst() {
        Assert.assertFalse(validator.isValid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.morgen()), null));
    }

    @Test
    public void testDatumInHetVerleden() {
        Assert.assertTrue(validator.isValid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()), null));
    }

    @Test
    public void testDatumVandaag() {
        Assert.assertTrue(validator.isValid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), null));
    }
}
