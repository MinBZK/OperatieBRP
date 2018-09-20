/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link nl.bzk.brp.model.validatie.validator.GeenSpatieValidator} class.
 */
public class GeenSpatieValidatorTest {

    private final GeenSpatieValidator validator = new GeenSpatieValidator();

    @Test
    public void testNullObject() {
        Assert.assertEquals(true, this.validator.isValid(null, null));
    }

    @Test
    public void testNullLeegString() {
        Assert.assertEquals(true, this.validator.isValid(new VoornaamAttribuut(""), null));
    }

    @Test
    public void testTextZonderSpatie() {
        Assert.assertEquals(true, this.validator.isValid(new VoornaamAttribuut("uystweljhgsfkjsdkj"), null));
    }

    @Test
    public void testSingleString() {
        Assert.assertEquals(false, this.validator.isValid(new VoornaamAttribuut(" "), null));
    }

    @Test
    public void testTextMetSpatie() {
        Assert.assertEquals(false, this.validator.isValid(new VoornaamAttribuut("uystwe ljhg"), null));
    }

    @Test
    public void testTextMetMeerdereSpatie() {
        Assert.assertEquals(false, this.validator.isValid(new VoornaamAttribuut("uystwe ljhg sfkjsdkj"), null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerkeerdeType() {
        Assert.assertEquals(false, this.validator.isValid(new GeslachtsnaamstamAttribuut(" "), null));
    }

}
