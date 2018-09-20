/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import nl.bzk.brp.beheer.webapp.configuratie.BlobifierConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRepository;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.model.beheer.kern.Partij;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Test partij repository.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfiguratie.class, BlobifierConfiguratie.class}, loader = AnnotationConfigContextLoader.class)
@Data(resources = {"classpath:/data/testdata.xml"}, dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_KERN)
public class PartijRepositoryTest extends AbstractDatabaseTest {

    @Autowired
    private PartijRepository subject;

    @Test
    public void findAll() throws JsonProcessingException {
        final Pageable pageable = new PageRequest(0, 10);
        final Page<Partij> page = subject.findAll(null, pageable);
        Assert.assertNotNull(page);
        Assert.assertEquals(2356L, page.getTotalElements());
    }

    @Test
    public void slaPartijOp() throws Exception {
        final BrpJsonObjectMapper mapper = new BrpJsonObjectMapper();
        final ObjectReader reader = mapper.reader(Partij.class);
        final Partij value =
                reader.<Partij>readValue("{\"Identiteit\":2212,\"Code\":602601,\"Naam\":\"'t Lange Land Ziekenhuis\",\"Datum ingang\":20040601,\"Automatisch fiatteren?\":\"Nee\",\"Verstrekkingsbeperking mogelijk?\":\"Ja\",\"Datum overgang naar BRP\":20150101}");
        Partij opgeslagenPartij = subject.save(value);
        Assert.assertEquals("Waarde moet zijn opgeslagen", value.getDatumOvergangNaarBRP(), opgeslagenPartij.getDatumOvergangNaarBRP());
    }
}
