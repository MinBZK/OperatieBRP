/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import org.junit.Assert;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractJbpmDaoTest;
import org.hibernate.jdbc.Work;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TaakGerelateerdeInformatieDaoTest extends AbstractJbpmDaoTest {

    @Autowired
    private TaakGerelateerdeInformatieDao subject;

    public TaakGerelateerdeInformatieDaoTest() {
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

                // create table JBPM_TASKINSTANCE (ID_ bigint not null, CLASS_ char(1) not null,
                // VERSION_ integer not null, NAME_ varchar(255), DESCRIPTION_ varchar(4000),
                // ACTORID_ varchar(255), CREATE_ timestamp, START_ timestamp, END_ timestamp,
                // DUEDATE_ timestamp, PRIORITY_ integer, ISCANCELLED_ boolean, ISSUSPENDED_ boolean,
                // ISOPEN_ boolean, ISSIGNALLING_ boolean, ISBLOCKING_ boolean, TASK_ bigint,
                // TOKEN_ bigint, PROCINST_ bigint, SWIMLANINSTANCE_ bigint, TASKMGMTINSTANCE_ bigint,
                // primary key (ID_));

                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     "insert into jbpm_taskinstance(id_, class_, version_, priority_, iscancelled_, issuspended_, isopen_, issignalling_, "
                                             + "isblocking_) "
                                             + "values(?, 'T', 0, 3, false, false, false, false, false)")) {
                    statement.setLong(1, 5454L);
                    statement.executeUpdate();
                    statement.setLong(1, 5455L);
                    statement.executeUpdate();
                }
            }
        });
    }

    @Test
    public void test() throws SQLException {
        Collection<TaskInstance> tasks = subject.zoekOpAdministratienummers("1234567890");
        Assert.assertEquals(0, tasks.size());

        final TaskInstance taskInstance5454 = (TaskInstance) getSession().get(TaskInstance.class, 5454L);
        Assert.assertNotNull(taskInstance5454);
        subject.registreerAdministratienummers(taskInstance5454, "23345454", "1234567890", "45987347");

        tasks = subject.zoekOpAdministratienummers("1234567890");
        Assert.assertEquals(1, tasks.size());
        Assert.assertEquals(5454L, tasks.iterator().next().getId());

        final TaskInstance taskInstance5455 = (TaskInstance) getSession().get(TaskInstance.class, 5455L);
        Assert.assertNotNull(taskInstance5455);
        subject.registreerAdministratienummers(taskInstance5455, "1234567890", "5468474");

        tasks = subject.zoekOpAdministratienummers("1234567890");
        Assert.assertEquals(2, tasks.size());

    }
}
