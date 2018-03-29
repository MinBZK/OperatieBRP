/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.persoon;

import org.junit.Test;

public class PersoonsvraagArchiveerBerichtServiceTest {

    private final PersoonsvraagArchiveerBerichtServiceImpl subject = new PersoonsvraagArchiveerBerichtServiceImpl();

    @Test
    public void test() {
        // Test NO-OP
        subject.archiveer(null, null, null);
    }
}
