/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("synchronisatie-brp-mapper-beans.xml")
public abstract class BrpAbstractTest {

    private final SimpleDateFormat dateFormat;

    public BrpAbstractTest() {
        dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        dateFormat.setTimeZone(BrpDatumTijd.BRP_TIJDZONE);
    }

    protected Timestamp timestamp(final String date) {
        try {
            return new Timestamp(dateFormat.parse(date).getTime());
        } catch (final ParseException e) {
            throw new RuntimeException("Invalid timestamp '" + date + "'.", e);
        }
    }
}
