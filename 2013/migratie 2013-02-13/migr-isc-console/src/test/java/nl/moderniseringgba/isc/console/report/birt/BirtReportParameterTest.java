/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.console.report.birt;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import nl.moderniseringgba.isc.console.report.ReportException;

import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class BirtReportParameterTest {

    @Test
    public void testInvalidInput() throws Exception {
        final IScalarParameterDefn def = Mockito.mock(IScalarParameterDefn.class);

        final Object input = new Date();
        final Object result = BirtReportParameters.convertTo(def, input);

        Assert.assertSame(input, result);
    }

    @Test
    public void testStringToDate() throws Exception {
        final IScalarParameterDefn def = Mockito.mock(IScalarParameterDefn.class);
        Mockito.when(def.getDataType()).thenReturn(IParameterDefn.TYPE_DATE);
        Mockito.when(def.getDisplayFormat()).thenReturn("dd-MM-yyyy");

        final Object input = "22-11-2001";
        final Object result = BirtReportParameters.convertTo(def, input);

        final Calendar cal = new GregorianCalendar(2001, 10, 22);
        Assert.assertEquals(new Date(cal.getTimeInMillis()), result);
    }

    @Test(expected = ReportException.class)
    public void testInvalidStringToDate() throws Exception {
        final IScalarParameterDefn def = Mockito.mock(IScalarParameterDefn.class);
        Mockito.when(def.getDataType()).thenReturn(IParameterDefn.TYPE_DATE);
        Mockito.when(def.getDisplayFormat()).thenReturn("dd-MM-yyyy");

        final Object input = "asdassddas";
        BirtReportParameters.convertTo(def, input);
    }

    @Test
    public void testStringsToInteger() throws Exception {
        final IScalarParameterDefn def = Mockito.mock(IScalarParameterDefn.class);
        Mockito.when(def.getDataType()).thenReturn(IParameterDefn.TYPE_INTEGER);

        final Object input = new String[] { "345534" };
        final Object result = BirtReportParameters.convertTo(def, input);

        Assert.assertEquals(Integer.valueOf(345534), result);
    }

    @Test(expected = ReportException.class)
    public void testInvalidStringToInteger() throws Exception {
        final IScalarParameterDefn def = Mockito.mock(IScalarParameterDefn.class);
        Mockito.when(def.getDataType()).thenReturn(IParameterDefn.TYPE_INTEGER);
        Mockito.when(def.getDisplayFormat()).thenReturn("dd-MM-yyyy");

        final Object input = "asdassddas";
        BirtReportParameters.convertTo(def, input);
    }

    @Test
    public void testStringsToString() throws Exception {
        final IScalarParameterDefn def = Mockito.mock(IScalarParameterDefn.class);
        Mockito.when(def.getDataType()).thenReturn(IParameterDefn.TYPE_STRING);

        final Object input = new String[] { "Blablabla" };
        final Object result = BirtReportParameters.convertTo(def, input);

        Assert.assertEquals("Blablabla", result);
    }
}
