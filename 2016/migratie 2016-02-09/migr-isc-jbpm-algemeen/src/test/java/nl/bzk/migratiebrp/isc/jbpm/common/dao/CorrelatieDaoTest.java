/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractJbpmDaoTest;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Correlatie;
import org.hibernate.jdbc.Work;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CorrelatieDaoTest extends AbstractJbpmDaoTest {

    @Autowired
    private CorrelatieDao subject;

    public CorrelatieDaoTest() {
        super("/sql/mig-create.sql");
    }

    @Before
    public void setup() {
        getSession().doWork(new Work() {
            @Override
            public void execute(final Connection connection) throws SQLException {
                try (PreparedStatement statement =
                        connection.prepareStatement("insert into jbpm_processinstance(id_, version_, issuspended_) values(?, 0, false)")) {
                    statement.setLong(1, 1L);
                    statement.executeUpdate();
                    statement.setLong(1, 5L);
                    statement.executeUpdate();
                }
            }
        });
    }

    @Test
    public void test() throws Exception {
        Assert.assertNull(subject.zoeken("1", "TEST", "0500", "0600"));

        subject.opslaan("1", "TEST", "0500", "0600", 1L, 2L, 3L);

        Assert.assertNull(subject.zoeken("1", null, "0500", "0600"));
        Assert.assertNull(subject.zoeken("1", "BLA", "0500", "0600"));
        Assert.assertNull(subject.zoeken("1", "TEST", null, "0600"));
        Assert.assertNull(subject.zoeken("1", "TEST", "0501", "0600"));
        Assert.assertNull(subject.zoeken("1", "TEST", "0500", null));
        Assert.assertNull(subject.zoeken("1", "TEST", "0500", "0601"));
        final Correlatie processData = subject.zoeken("1", "TEST", "0500", "0600");
        Assert.assertNotNull(processData);
        Assert.assertEquals(1L, processData.getProcessInstance().getId());
        Assert.assertEquals(Long.valueOf(2L), processData.getTokenId());
        Assert.assertEquals(Long.valueOf(3L), processData.getNodeId());

        subject.opslaan("1", "TEST", "0700", "0800", 5L, 6L, 7L);
        final Correlatie processData2 = subject.zoeken("1", "TEST", "0700", "0800");
        Assert.assertNotNull(processData2);
        Assert.assertEquals(5L, processData2.getProcessInstance().getId());
        Assert.assertEquals(Long.valueOf(6L), processData2.getTokenId());
        Assert.assertEquals(Long.valueOf(7L), processData2.getNodeId());

    }
}
