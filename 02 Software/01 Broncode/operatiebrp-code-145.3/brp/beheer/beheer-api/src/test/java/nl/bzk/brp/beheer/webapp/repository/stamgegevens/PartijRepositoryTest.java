/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.JsonConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRepository;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class, JsonConfiguratie.class},
        loader = AnnotationConfigContextLoader.class)
@Data(resources = {"classpath:/data/testdata.xml"}, dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_KERN)
public class PartijRepositoryTest extends AbstractDatabaseTest {

    @Inject
    private PartijRepository subject;
    @Inject
    private BrpJsonObjectMapper mapper;

    @Test
    public void findAll() throws JsonProcessingException {
        final Pageable pageable = new PageRequest(0, 10);
        final Page<Partij> page = subject.findAll(null, pageable);
        Assert.assertNotNull(page);
        Assert.assertTrue(page.getTotalElements() > 2000);
    }

    @Test
    public void slaPartijOp() throws Exception {
        final ObjectReader reader = mapper.readerFor(Partij.class);
        final Partij value = reader.<Partij>readValue(
                "{\"identiteit\":2212,\"code\":602601,\"naam\":\"'t Lange Land Ziekenhuis\",\"datumIngang\":20040601,\"automatischFiatteren\":\"Nee\","
                        + "\"verstrekkingsbeperkingMogelijk\":\"Ja\",\"datumOvergangNaarBrp\":20150101}");
        final Partij opgeslagenPartij = subject.save(value);
        Assert.assertEquals("Waarde moet zijn opgeslagen", value.getDatumOvergangNaarBrp(), opgeslagenPartij.getDatumOvergangNaarBrp());
    }
}
