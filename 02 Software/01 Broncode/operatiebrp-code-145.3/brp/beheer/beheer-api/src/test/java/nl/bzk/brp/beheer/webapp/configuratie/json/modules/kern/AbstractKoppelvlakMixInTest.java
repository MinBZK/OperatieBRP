/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules.kern;

import java.io.IOException;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Koppelvlak;
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
public class AbstractKoppelvlakMixInTest {

    @Inject
    private BrpJsonObjectMapper subject;

    @Test
    public void testSerialize() throws IOException {
        final String result = subject.writeValueAsString(Koppelvlak.BIJHOUDING);
        Assert.assertEquals("{\"id\":1,\"naam\":\"Bijhouding\",\"stelsel\":\"BRP\"}", result);
    }

}
