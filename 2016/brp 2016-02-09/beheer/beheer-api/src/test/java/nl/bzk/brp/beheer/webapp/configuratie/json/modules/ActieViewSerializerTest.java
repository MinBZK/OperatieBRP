/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;
import nl.bzk.brp.beheer.webapp.configuratie.BlobifierConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.repository.kern.ActieViewRepository;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.model.beheer.view.Actie;
import nl.bzk.brp.model.beheer.view.ActieView;
import nl.bzk.brp.model.beheer.view.Acties;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfiguratie.class, BlobifierConfiguratie.class}, loader = AnnotationConfigContextLoader.class)
@Data(resources = "classpath:/data/actieviewtest.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class ActieViewSerializerTest extends AbstractDatabaseTest {

    private final BrpJsonObjectMapper subject = new BrpJsonObjectMapper();
    @Autowired
    private ActieViewRepository actieViewRepository;

    @Before
    public void setupSerializer() throws ReflectiveOperationException {
        final Field counterField = SerializerUtils.class.getDeclaredField("COUNTER");
        counterField.setAccessible(true);
        final AtomicLong counter = (AtomicLong) counterField.get(null);
        counter.set(0);
    }

    @Test
    @Transactional
    public void serializeAlles() throws IOException {
        // Reset inner sequence Serializer

        final Actie actie = actieViewRepository.findOne(101L);
        Assert.assertNotNull(actie);
        final ActieView actieView = Acties.asActieView(actie);
        Assert.assertNotNull(actieView);

        final String result = subject.writeValueAsString(actieView);
        final String expected = IOUtils.toString(ActieViewSerializerTest.class.getResource("/data/actieviewtest.json"));
        Assert.assertEquals(expected, result);
    }
}
