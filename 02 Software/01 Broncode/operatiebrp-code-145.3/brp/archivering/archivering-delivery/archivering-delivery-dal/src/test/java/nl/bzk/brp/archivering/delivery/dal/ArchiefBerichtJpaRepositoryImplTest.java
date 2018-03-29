/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.archivering.delivery.dal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.archivering.service.dal.ArchiefBerichtRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie van ArchiefBerichtRepository.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:archivering-delivery-dal-test-context.xml")
public class ArchiefBerichtJpaRepositoryImplTest {


    @PersistenceContext(unitName = "nl.bzk.brp.archivering")
    private EntityManager em;

    @Inject
    private ArchiefBerichtRepository archiefBerichtRepository;

    @Test
    public void persist() {
        final Bericht bericht = new Bericht(Richting.INGAAND, DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now()));
        bericht.setData("data");
        archiefBerichtRepository.accept(bericht);

        final TypedQuery<Bericht> query = em.createQuery("from Bericht", Bericht.class);
        final Bericht persistedBericht = query.setFirstResult(0).setMaxResults(1).getSingleResult();

        assertThat(persistedBericht.getId(), greaterThan(0L));
    }
}
