/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.ssh;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("Alleen te testen tegen remote server")
public class SshExecKanaalTest {

    @Test
    public void test() throws KanaalException {
        final SshExecKanaal subject = new SshExecKanaal();
        subject.setNaam("ssh");
        subject.setHost("map46.modernodam.nl");
        subject.setUsername("jboss");
        subject.setPassword("M1gratie_jb0ss");
        subject.afterPropertiesSet();

        final Bericht bericht = new Bericht();
        bericht.setInhoud("pwd;ls -l");

        subject.verwerkUitgaand(null, bericht);
    }
}
