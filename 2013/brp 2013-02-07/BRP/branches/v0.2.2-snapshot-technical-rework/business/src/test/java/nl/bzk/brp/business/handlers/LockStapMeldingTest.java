/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.service.BsnLocker;
import nl.bzk.brp.model.objecttype.bericht.BerichtBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class LockStapMeldingTest extends AbstractStapTest {

    @Inject
    private BsnLocker      bsnLocker;

    private LockStap       lockStap;
    private BerichtBericht bericht;
    private BerichtContext context;

    @Before
    public void setUp() {
        lockStap = new LockStap();
        bericht = new BerichtBericht() {

            @Override
            public Collection<String> getReadBsnLocks() {
                return Arrays.asList("1435", "3242312", "4523");
            }

            @Override
            public Collection<String> getWriteBsnLocks() {
                return Arrays.asList("43524", "324133", "1234");
            }

            @Override
            public SoortBericht getSoortBericht() {
                return null;
            }

            @Override
            public void setVerzendendId(final String verzendendId) {

            }

            @Override
            public String getVerzendendId() {
                return null;
            }
        };
        ReflectionTestUtils.setField(lockStap, "bsnLocker", bsnLocker);

        context = new BerichtContext(new BerichtenIds(2L, 3L), Integer.valueOf(1), new Partij(), "ref") {

            @Override
            public long getIngaandBerichtId() {
                return 1L;
            }

        };
    }

    @Test
    public void testReadWriteMetAfwijking() {
        final BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);

        try {
            lockStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), null);

            Thread myThread = new Thread() {

                @Override
                public void run() {
                    try {
                        lockStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);
                    } finally {
                        lockStap.naVerwerkingsStapVoorBericht(bericht, bouwBerichtContext(), resultaat);
                    }
                }

            };

            myThread.start();
            myThread.join();

            Assert.assertEquals(1, resultaat.getMeldingen().size());
            Melding melding = resultaat.getMeldingen().get(0);
            Assert.assertSame(SoortMelding.FOUT, melding.getSoort());
            Assert.assertSame(MeldingCode.ALG0001, melding.getCode());
            Assert.assertEquals(
                    "De burgerservicenummers in bericht met ID 2 worden gelijktijdig door een ander bericht geraadpleegd of "
                        + "gemuteerd", melding.getOmschrijving());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lockStap.naVerwerkingsStapVoorBericht(bericht, bouwBerichtContext(), resultaat);
        }
    }

}
