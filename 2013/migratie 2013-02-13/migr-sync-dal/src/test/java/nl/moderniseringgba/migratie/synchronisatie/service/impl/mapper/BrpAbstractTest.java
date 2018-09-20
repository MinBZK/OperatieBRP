/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("synchronisatie-brp-mapper-beans.xml")
public abstract class BrpAbstractTest {

    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    static {
        TIMESTAMP_FORMAT.setTimeZone(BrpDatumTijd.BRP_TIJDZONE);
    }

    protected static Timestamp timestamp(final String date) {
        try {
            return new Timestamp(TIMESTAMP_FORMAT.parse(date).getTime());
        } catch (final ParseException e) {
            throw new RuntimeException("Invalid timestamp '" + date + "'.", e);
        }
    }
}
