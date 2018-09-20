/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import nl.moderniseringgba.isc.jbpm.AbstractJbpmDaoTest;

import org.junit.Test;

public class JbpmFoutenDaoTest extends AbstractJbpmDaoTest {

    private final JbpmFoutenDao subject = new JbpmFoutenDao();

    public JbpmFoutenDaoTest() {
        super("/sql/fouten.sql");
    }

    @Test
    public void test() {
        final long id = subject.registreerFout("bla", "blab lab alablabal", "uc307", 1L, "0560", "0600");
        subject.voegResolutieToe(id, "Ja");
    }

}
