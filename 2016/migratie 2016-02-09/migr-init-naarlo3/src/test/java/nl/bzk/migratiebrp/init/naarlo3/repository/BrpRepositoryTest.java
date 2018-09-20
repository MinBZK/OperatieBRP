/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarlo3.repository;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:naarlo3-test-beans.xml")
public class BrpRepositoryTest {

    @Inject
    private BrpRepository brpRepository;

    private int berichtenTeller;

    private final ANummerVerwerker verwerker = new ANummerVerwerker() {
        private int teller;

        @Override
        public void addANummer(final long aNummer) {
            teller += 1;
        }

        @Override
        public Void call() throws Exception {
            berichtenTeller += teller;
            teller = 0;
            return null;
        }
    };

    @Test
    public void testFindANummersVanIngeschrevenPersonen() {
        berichtenTeller = 0;
        brpRepository.verwerkANummersVanIngeschrevenPersonen(verwerker, 1);
        assertEquals(20, berichtenTeller);
    }

}
