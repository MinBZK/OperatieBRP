/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class NumericFieldTest {

    private final NumericField mandatorySubject = new NumericField("mandatorySubject", 4, false, false);
    private final NumericField optionalSubject = new NumericField("optionalSubject", 4, true, false);

    @Test
    public void length() {
        assertEquals(4, mandatorySubject.length());
    }

    @Test
    public void mandatory() {
        assertEquals(false, mandatorySubject.isOptional());
    }

    @Test
    public void optional() {
        assertEquals(true, optionalSubject.isOptional());
    }

    @Test
    public void getValue() {
        mandatorySubject.setValue(1234);
        assertThat(mandatorySubject.getValue(), is(equalTo(1234)));
    }

    @Test
    public void getValueForDefault() {
        assertEquals(null, mandatorySubject.getValue());
    }

    @Test
    public void getValueForNull() {
        optionalSubject.setValue(null);
        assertEquals(null, optionalSubject.getValue());
    }

    @Test
    public void setRawValue() {
        mandatorySubject.setRawValue("1234");
        assertThat(mandatorySubject.getValue(), is(equalTo(1234)));
    }

    @Test
    public void setRawValueForEmptyString() {
        optionalSubject.setRawValue("");
        assertEquals(null, optionalSubject.getValue());
    }

    @Test
    public void setRawValueForNull() {
        optionalSubject.setRawValue(null);
        assertEquals(null, optionalSubject.getValue());
    }

    @Test
    public void isEmpty() {
        assertEquals(true, mandatorySubject.isEmpty());
    }

    @Test
    public void isNotEmpty() {
        mandatorySubject.setValue(1234);
        assertEquals(false, mandatorySubject.isEmpty());
    }

    @Test
    public void validateOptionalAndEmpty() {
        assertEquals(true, optionalSubject.validate());
    }

    @Test
    public void validateOptionalAndNotEmpty() {
        optionalSubject.setValue(1234);
        assertEquals(true, optionalSubject.validate());
    }

    @Test(expected = IllegalStateException.class)
    public void validateMandatoryAndEmpty() {
        assertEquals(true, mandatorySubject.validate());
    }

    @Test
    public void validateMandatoryAndEmptyMessage() {
        try {
            mandatorySubject.validate();
            fail();
        } catch (final IllegalStateException ex) {
            assertEquals("field mandatorySubject is mandatory and empty", ex.getMessage());
        }
    }

    @Test
    public void validateMandatoryAndNotEmpty() {
        mandatorySubject.setValue(1234);
        assertEquals(true, mandatorySubject.validate());
    }

    @Test(expected = IllegalStateException.class)
    public void toSpdStringForMandatoryAndEmpty() {
        assertEquals("    ", mandatorySubject.toSpdString());
    }

    @Test
    public void toSpdStringForMandatoryAndNotEmpty() {
        mandatorySubject.setValue(1234);
        assertThat(mandatorySubject.toSpdString(), is(equalTo("1234")));
    }

    @Test
    public void toSpdStringForOptionalAndEmpty() {
        assertEquals("    ", optionalSubject.toSpdString());
    }

    @Test
    public void toSpdStringForOptionalAndNotEmpty() {
        optionalSubject.setValue(1234);
        assertThat(optionalSubject.toSpdString(), is(equalTo("1234")));
    }

    @Test
    public void toSpdStringShorterNumber() {
        mandatorySubject.setValue(123);
        assertThat(mandatorySubject.toSpdString(), is(equalTo("0123")));
    }
}
