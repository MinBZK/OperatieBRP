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
import nl.bzk.brp.beheer.webapp.view.DienstbundelLo3RubriekView;
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
@Data(resources = "classpath:/data/testdata.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
@Transactional(transactionManager = RepositoryConfiguratie.TRANSACTION_MANAGER)
public class DienstbundelLo3RubriekDeEnSerializerTest extends AbstractDatabaseTest {

    @Inject
    private BrpJsonObjectMapper subject;

    @Test(expected = IllegalArgumentException.class)
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.readerFor(DienstbundelLo3RubriekView.class);
        reader.<DienstbundelLo3RubriekView>readValue("{}");
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.readerFor(DienstbundelLo3RubriekView.class);
        final DienstbundelLo3RubriekView value =
                reader.<DienstbundelLo3RubriekView>readValue("{\"id\":2,\"dienstbundel\":\"9001\",\"rubriek\":1,\"actief\":\"Ja\"}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Integer.valueOf(2), value.getId());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testNieuw() throws IOException {
        final ObjectReader reader = subject.readerFor(DienstbundelLo3RubriekView.class);
        final DienstbundelLo3RubriekView value =
                reader.<DienstbundelLo3RubriekView>readValue("{\"dienstbundel\":\"9001\",\"rubriek\":\"2\",\"actief\":\"Ja\"}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getId());
        Assert.assertNotNull(value.getLo3Rubriek());
        Assert.assertEquals(Integer.valueOf((short) 2), value.getLo3Rubriek().getId());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final DienstbundelLo3RubriekView heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);

        final ObjectReader reader = subject.readerFor(DienstbundelLo3RubriekView.class);
        final DienstbundelLo3RubriekView weer = reader.<DienstbundelLo3RubriekView>readValue(json);

        Assert.assertEquals(heen.getId(), weer.getId());

    }
}
