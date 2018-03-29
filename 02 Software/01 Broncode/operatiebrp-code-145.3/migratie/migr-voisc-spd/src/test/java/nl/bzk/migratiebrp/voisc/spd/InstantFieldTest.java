/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Instant;

import org.junit.Test;

public class InstantFieldTest {

    private final InstantField mandatorySubject = new InstantField("mandatorySubject", false);
    private final InstantField optionalSubject = new InstantField("optionalSubject", true);

    @Test
    public void length() {
        assertEquals(11, mandatorySubject.length());
    }

    @Test
    public void mandatory() {
        assertEquals(false, mandatorySubject.isOptional());
    }

    @Test
    public void optional() {
        assertEquals(true, new InstantField("name", true).isOptional());
    }

    @Test
    public void getValue() {
        mandatorySubject.setValue(Instant.parse("2016-11-03T15:10:30Z"));
        assertEquals("2016-11-03T15:10:30Z", mandatorySubject.getValue().toString());
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
        mandatorySubject.setRawValue("1611031510Z");
        assertThat(mandatorySubject.getValue(), instanceOf(Instant.class));
    }

    @Test
    public void setRawValueForEmptyString() {
        optionalSubject.setRawValue("");
        assertEquals(null, optionalSubject.getValue());
    }

    @Test
    public void setRawValueForStringWithSpaces() {
        optionalSubject.setRawValue("           ");
        assertEquals(null, optionalSubject.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setRawValueForTooLongString() {
        optionalSubject.setRawValue("161103151020Z");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setRawValueForTooShortString() {
        optionalSubject.setRawValue("16110315Z");
    }

    @Test
    public void setRawValueForNullOptional() {
        optionalSubject.setRawValue(null);
        assertEquals(null, optionalSubject.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setRawValueForNullMandatory() {
        mandatorySubject.setRawValue(null);
    }

    @Test
    public void isEmpty() {
        assertEquals(true, mandatorySubject.isEmpty());
    }

    @Test
    public void isNotEmpty() {
        mandatorySubject.setValue(Instant.parse("2016-11-03T15:10:30Z"));
        assertEquals(false, mandatorySubject.isEmpty());
    }

    @Test
    public void validateOptionalAndEmpty() {
        assertEquals(true, optionalSubject.validate());
    }

    @Test
    public void validateOptionalAndNotEmpty() {
        optionalSubject.setValue(Instant.parse("2016-11-03T15:10:30Z"));
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
        mandatorySubject.setValue(Instant.parse("2016-11-03T15:10:30Z"));
        assertEquals(true, mandatorySubject.validate());
    }

    @Test(expected = IllegalStateException.class)
    public void toSpdStringForMandatoryAndEmpty() {
        assertEquals("           ", mandatorySubject.toSpdString());
    }

    @Test
    public void toSpdStringForMandatoryAndNotEmpty() {
        mandatorySubject.setValue(Instant.parse("2016-11-03T15:10:30Z"));
        assertEquals("1611031510Z", mandatorySubject.toSpdString());
    }

    @Test
    public void toSpdStringForOptionalAndEmpty() {
        assertEquals("           ", optionalSubject.toSpdString());
    }

    @Test
    public void toSpdStringForOptionalAndNotEmpty() {
        optionalSubject.setValue(Instant.parse("2016-11-03T15:10:30Z"));
        assertEquals("1611031510Z", optionalSubject.toSpdString());
    }
}
