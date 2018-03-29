/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import javax.inject.Inject;
import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.JsonConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.beheer.webapp.view.DienstbundelGroepAttribuutView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class, JsonConfiguratie.class},
        loader = AnnotationConfigContextLoader.class)
@Data(resources = "classpath:/data/testdata_dienstbundelgroepattribuut.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
@Transactional(transactionManager = RepositoryConfiguratie.TRANSACTION_MANAGER)
public class DienstbundelGroepAttribuutDeEnSerializerTest extends AbstractDatabaseTest {

    @Inject
    private BrpJsonObjectMapper subject;

    @Test(expected = IllegalArgumentException.class)
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.readerFor(DienstbundelGroepAttribuutView.class);
        reader.<DienstbundelGroepAttribuutView>readValue("{}");
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.readerFor(DienstbundelGroepAttribuutView.class);
        final DienstbundelGroepAttribuutView value =
                reader.<DienstbundelGroepAttribuutView>readValue("{\"id\":2,\"dienstbundelGroep\":\"2\",\"attribuut\":3514,\"actief\":\"Ja\"}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Integer.valueOf(2), value.getId());
        Assert.assertNotNull(value.getDienstbundelGroep());
        Assert.assertEquals(Integer.valueOf(2), value.getDienstbundelGroep().getId());
        Assert.assertNotNull(value.getAttribuut());
        Assert.assertEquals(3514, value.getAttribuut().getId());
        controleerHeenEnWeer(value);
    }

    @Test
    public void testNieuw() throws IOException {
        final ObjectReader reader = subject.readerFor(DienstbundelGroepAttribuutView.class);
        final DienstbundelGroepAttribuutView value =
                reader.<DienstbundelGroepAttribuutView>readValue("{\"dienstbundelGroep\":\"9001\",\"attribuut\":\"3514\",\"actief\":\"Ja\"}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getId());
        Assert.assertNotNull(value.getDienstbundelGroep());
        Assert.assertEquals(Integer.valueOf(9001), value.getDienstbundelGroep().getId());
        Assert.assertNotNull(value.getAttribuut());
        Assert.assertEquals(3514, value.getAttribuut().getId());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final DienstbundelGroepAttribuutView heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);

        final ObjectReader reader = subject.readerFor(DienstbundelGroepAttribuutView.class);
        final DienstbundelGroepAttribuutView weer = reader.<DienstbundelGroepAttribuutView>readValue(json);

        Assert.assertEquals(heen.getId(), weer.getId());

    }
}
