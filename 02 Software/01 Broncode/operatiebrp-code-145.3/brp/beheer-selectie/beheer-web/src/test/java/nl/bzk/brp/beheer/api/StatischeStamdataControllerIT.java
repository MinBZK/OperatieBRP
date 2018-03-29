/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.api;

import javax.inject.Inject;
import nl.bzk.brp.beheer.BeheerWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeheerWebApplication.class)
@ActiveProfiles("dev, common")
public class StatischeStamdataControllerIT {

    @Inject
    private StatischeStamdataController controller;

    @Test(expected = BadSqlGrammarException.class)
    public void geeftFoutBijNietBestaandeTabel() {
        controller.getStamdata("tabel");
    }
}
