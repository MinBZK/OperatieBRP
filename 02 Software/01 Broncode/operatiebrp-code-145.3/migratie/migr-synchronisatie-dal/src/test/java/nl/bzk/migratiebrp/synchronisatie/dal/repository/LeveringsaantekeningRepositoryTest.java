/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsaantekeningPersoon;
import nl.bzk.algemeenbrp.test.dal.DBUnit;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional(transactionManager = "syncDalTransactionManager")
public class LeveringsaantekeningRepositoryTest extends AbstractDatabaseTest {

    @Inject
    private LeveringsaantekeningRepository subject;

    @Test
    public void saveMoetIdZetten() {
        final Leveringsaantekening leveringsaantekening = new Leveringsaantekening(1, 2, new Timestamp(2016_09_16), new Timestamp(2016_09_16));
        assertThat(leveringsaantekening.getId(), is(nullValue()));
        subject.save(leveringsaantekening);
        assertThat(leveringsaantekening.getId(), not(is(nullValue())));
    }

    @Test
    public void saveMoetUpdaten() {
        final Leveringsaantekening leveringsaantekening = new Leveringsaantekening(1, null, new Timestamp(2016_09_16), new Timestamp(2016_09_16));
        subject.save(leveringsaantekening);
        final Long id = leveringsaantekening.getId();
        assertThat(leveringsaantekening.getId(), not(is(nullValue())));
        assertThat(leveringsaantekening.getDienst(), is(nullValue()));
        leveringsaantekening.setDienst(11);
        subject.save(leveringsaantekening);
        assertEquals(new Integer(11), leveringsaantekening.getDienst());
        assertEquals(id, leveringsaantekening.getId());
    }

    @Test
    public void saveMoetRelatiePersisteren() {
        final Leveringsaantekening leveringsaantekening = new Leveringsaantekening(1, 2, new Timestamp(2016_09_16), new Timestamp(2016_09_16));
        final LeveringsaantekeningPersoon
                leveringsaantekeningPersoon =
                new LeveringsaantekeningPersoon(leveringsaantekening, 2L, new Timestamp(2016_09_16),
                        Timestamp.valueOf(leveringsaantekening.getDatumTijdKlaarzettenLevering().toLocalDateTime()));

        leveringsaantekening.getLeveringsaantekeningPersoonSet().add(leveringsaantekeningPersoon);
        subject.save(leveringsaantekening);
        assertThat("leveringsaantekeningPersoon moet ook gepersisteerd worden", leveringsaantekeningPersoon.getLeveringsaantekening(),
                not(is(nullValue())));
    }

    @Test
    @DBUnit.InsertBefore("LeveringsaantekeningTestData.xml")
    public void find() {
        final Leveringsaantekening leveringsaantekening = subject.find(1L);
        assertEquals("Moet Leveringsaantekening vinden bij id", Long.valueOf(1), leveringsaantekening.getId());
        //assertEquals(Long.valueOf(2), leveringsaantekening.getLeveringsaantekeningPersoonSet().iterator().next().getPersoon());
    }
}
