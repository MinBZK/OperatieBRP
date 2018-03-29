/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

public class PriorityTest {

    private final PriorityField mandatorySubject = new PriorityField("mandatorySubject", false);
    private final PriorityField optionalSubject = new PriorityField("optionalSubject", true);

    @Test
    public void length() {
        assertEquals(1, mandatorySubject.length());
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
        mandatorySubject.setValue(SpdConstants.Priority.NORMAL);
        Assert.assertEquals(SpdConstants.Priority.NORMAL, mandatorySubject.getValue());
    }

    @Test
    public void getValueForDefault() {
        Assert.assertEquals(null, mandatorySubject.getValue());
    }

    @Test
    public void getValueForNull() {
        optionalSubject.setValue(null);
        Assert.assertEquals(null, optionalSubject.getValue());
    }

    @Test
    public void setRawValue() {
        mandatorySubject.setRawValue("2");
        Assert.assertEquals(SpdConstants.Priority.URGENT, mandatorySubject.getValue());
    }

    @Test
    public void setRawValueForEmptyStringOptional() {
        optionalSubject.setRawValue("");
        Assert.assertEquals(SpdConstants.Priority.NORMAL, optionalSubject.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setRawValueForEmptyStringMandatory() {
        mandatorySubject.setRawValue("");
        Assert.assertEquals(null, mandatorySubject.getValue());
    }

    @Test
    public void setRawValueForStringWithSpaceOptional() {
        optionalSubject.setRawValue(" ");
        Assert.assertEquals(SpdConstants.Priority.NORMAL, optionalSubject.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setRawValueForStringWithSpaceMandatory() {
        mandatorySubject.setRawValue(" ");
        Assert.assertEquals(null, mandatorySubject.getValue());
    }

    @Test
    public void setRawValueForNull() {
        optionalSubject.setRawValue(null);
        Assert.assertEquals(SpdConstants.Priority.defaultValue(), optionalSubject.getValue());
    }

    @Test
    public void isEmpty() {
        assertEquals(true, mandatorySubject.isEmpty());
    }

    @Test
    public void isNotEmpty() {
        mandatorySubject.setValue(SpdConstants.Priority.NORMAL);
        assertEquals(false, mandatorySubject.isEmpty());
    }

    @Test
    public void validateOptionalAndEmpty() {
        assertEquals(true, optionalSubject.validate());
    }

    @Test
    public void validateOptionalAndNotEmpty() {
        optionalSubject.setValue(SpdConstants.Priority.NORMAL);
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
        mandatorySubject.setValue(SpdConstants.Priority.NORMAL);
        assertEquals(true, mandatorySubject.validate());
    }

    @Test(expected = IllegalStateException.class)
    public void toSpdStringForMandatoryAndEmpty() {
        assertEquals(" ", mandatorySubject.toSpdString());
    }

    @Test
    public void toSpdStringForMandatoryAndNotEmpty() {
        mandatorySubject.setValue(SpdConstants.Priority.NORMAL);
        assertEquals("0", mandatorySubject.toSpdString());
    }

    @Test
    public void toSpdStringForOptionalAndEmpty() {
        assertEquals(" ", optionalSubject.toSpdString());
    }

    @Test
    public void toSpdStringForOptionalAndNotEmpty() {
        optionalSubject.setValue(SpdConstants.Priority.NORMAL);
        assertEquals("0", optionalSubject.toSpdString());
    }
}
