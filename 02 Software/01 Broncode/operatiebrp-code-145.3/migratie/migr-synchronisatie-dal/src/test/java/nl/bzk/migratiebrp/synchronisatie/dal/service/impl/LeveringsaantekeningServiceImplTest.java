/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.LeveringsaantekeningService;

import org.junit.Test;

//@ContextConfiguration("classpath:synchronisatie-beans-test.xml")
public class LeveringsaantekeningServiceImplTest extends AbstractDatabaseTest {

    @Inject
    private LeveringsaantekeningService subject;

    @Test
    public void persisteerEnBevraagLeveringsaantekening() {
        Leveringsaantekening aantekening = new Leveringsaantekening(1, 2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
        subject.persisteerLeveringsaantekening(aantekening);
        assertThat("na peristeren moet id een waarde hebben", aantekening.getId(), not(nullValue()));
        assertEquals(
                "op te slaan aantekening moet identiek zijn aan opgeslagen aantekening",
                aantekening,
                subject.bevraagLeveringsaantekening(aantekening.getId()));
    }
}
