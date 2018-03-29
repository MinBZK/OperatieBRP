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
import org.hibernate.jdbc.Work;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcesDaoTest extends AbstractJbpmDaoTest {

    @Autowired
    private ProcesDao subject;

    public ProcesDaoTest() {
        super("/sql/mig-create.sql");
    }

    @Before
    public void setup() {
        getSession().doWork(new Work() {
            @Override
            public void execute(final Connection connection) throws SQLException {
                try (PreparedStatement statement =
                             connection.prepareStatement("insert into jbpm_processinstance(id_, version_, issuspended_) values(?, 0, false)")) {
                    statement.setLong(1, 42L);
                    statement.executeUpdate();
                    statement.setLong(1, 1764L);
                    statement.executeUpdate();
                }
            }
        });
    }

    @Test
    public void registreerProcesRelatie() throws SQLException {
        subject.registreerProcesRelatie(42L, 1764L);
    }

    @Test
    public void registreerGerateerdGegeven() {
        subject.toevoegenGerelateerdGegeven(42L, "ADH", "2345343423");
    }
}
