/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.repository.ber.BerichtRepository;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.beheer.webapp.test.Datas;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class}, loader = AnnotationConfigContextLoader.class)
@Datas({@Data(resources = "classpath:/data/testdata.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER),
        @Data(resources = "classpath:/data/ber.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_ARCHIEVERING)})
public class BerichtenRepositoryTest extends AbstractDatabaseTest {

    @Inject
    private BerichtRepository subject;

    @Test
    public void findAll() throws JsonProcessingException {
        final Pageable pageable = new PageRequest(0, 10);
        final Page<Bericht> page = subject.findAll(null, pageable);
        Assert.assertNotNull(page);
        Assert.assertEquals(36L, page.getTotalElements());
    }
}
