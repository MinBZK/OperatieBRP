/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules.kern;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.beheer.webapp.configuratie.JsonConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.test.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfiguratie.class, JsonConfiguratie.class}, loader = AnnotationConfigContextLoader.class)
@Data(resources = "classpath:/data/actieviewtest.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class AbstractPartijMixInTest {

    @Inject
    private BrpJsonObjectMapper subject;

    @Test
    public void testSerialize() throws IOException {
        final Partij partij = new Partij("Gemeente Den Haag", "001234");
        final String result = subject.writeValueAsString(partij);
        Assert.assertEquals("{\"code\":\"001234\",\"naam\":\"Gemeente Den Haag\"}", result);
    }

    @Test
    public void testDeserialize() throws IOException {
        final Partij partij = subject.readValue("{\"Code\":\"190001\",\"Datum ingang\":19000101,\"Datum einde\":19000102}", Partij.class);
        assertThat(partij, is(instanceOf(Partij.class)));
    }
}
