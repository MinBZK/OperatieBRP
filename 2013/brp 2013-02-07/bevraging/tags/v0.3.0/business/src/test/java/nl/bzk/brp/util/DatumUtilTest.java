/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;
import org.junit.Test;

public class DatumUtilTest {

    @Test
    public void testGetDatumVandaagInteger() {
        final Integer vandaagint = DatumUtil.getDatumVandaagInteger();
        final Date vandaagDatum = Calendar.getInstance().getTime();
        Assert.assertEquals(DatumUtil.zetDatumOmInInteger(vandaagDatum), vandaagint);
    }

    @Test
    public void testZetIntegerOmInDatum() throws ParseException {
        final Integer vandaagint = DatumUtil.getDatumVandaagInteger();
        final String vandaagDatumString = String.valueOf(vandaagint);

        final Calendar outputDatum = Calendar.getInstance();
        outputDatum.setTime(DatumUtil.zetIntegerOmInDatum(vandaagint));

        String monthPrefix = "";
        if (outputDatum.get(Calendar.MONTH) + 1 < 10) {
            monthPrefix += "0";
        }
        String dayPrefix = "";
        if (outputDatum.get(Calendar.DAY_OF_MONTH) < 10) {
            dayPrefix += "0";
        }
        Assert.assertEquals(String.valueOf(outputDatum.get(Calendar.YEAR)), vandaagDatumString.substring(0, 4));
        Assert.assertEquals(monthPrefix + (outputDatum.get(Calendar.MONTH) + 1), vandaagDatumString.substring(4, 6));
        Assert.assertEquals(dayPrefix + outputDatum.get(Calendar.DAY_OF_MONTH), vandaagDatumString.substring(6, 8));
    }
}
