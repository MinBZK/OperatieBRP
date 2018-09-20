/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RegistreerResolutieActionTest {
    // Subject
    private final RegistreerResolutieAction subject = new RegistreerResolutieAction();
    // Dependencies
    private FoutenDao foutenDao;

    @Before
    public void setup() {
        foutenDao = Mockito.mock(FoutenDao.class);
        subject.setFoutenDao(foutenDao);
    }

    @Test
    public void test() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(FoutafhandelingConstants.REGISTRATIE_ID, 13L);
        parameters.put(FoutafhandelingConstants.RESTART, "dinges");

        subject.execute(parameters);

        Mockito.verify(foutenDao).voegResolutieToe(13L, "dinges");
    }
}
