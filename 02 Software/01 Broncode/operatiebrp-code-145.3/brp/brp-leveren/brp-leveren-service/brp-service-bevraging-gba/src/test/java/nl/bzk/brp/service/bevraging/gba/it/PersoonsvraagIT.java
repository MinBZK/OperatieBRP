/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.it;

import javax.inject.Inject;
import nl.bzk.brp.service.bevraging.algemeen.BevragingCallback;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.gba.persoon.PersoonsvraagVerzoek;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// test genegeerd omdat de wiring niet klopt en test het dus helemaal niet doet.
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/bevraging-service-beans-gba.xml", "/maakbericht-service-beans.xml", "/gba-it-mocks-and-overrides.xml",
        "/gba-it-datasource.xml"})
public class PersoonsvraagIT {

    @Inject
    private BevragingVerzoekVerwerker<PersoonsvraagVerzoek> persoonsvraagVerzoekVerwerker;

    @Test
    public void testWiring() {
        final PersoonsvraagVerzoek verzoek = new PersoonsvraagVerzoek();
        final PersoonsvraagCallback callback = new PersoonsvraagCallback();
        persoonsvraagVerzoekVerwerker.verwerk(verzoek, callback);
    }

    public static final class PersoonsvraagCallback implements BevragingCallback<String> {
        private BevragingResultaat resultaat;

        @Override
        public void verwerkResultaat(final BevragingVerzoek bevragingVerzoek, final BevragingResultaat resultaat) {
            this.resultaat = resultaat;
        }

        @Override
        public String getResultaat() {
            return "resultaat";
        }

        public BevragingResultaat getBevragingsresultaat() {
            return resultaat;
        }
    }
}
