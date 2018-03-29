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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.JsonConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
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
public class DienstbundelGroepDeEnSerializerTest extends AbstractDatabaseTest {

    @Inject
    private BrpJsonObjectMapper subject;

    @Test(expected = IllegalArgumentException.class)
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.readerFor(DienstbundelGroep.class);
        reader.<DienstbundelGroep>readValue("{}");
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.readerFor(DienstbundelGroep.class);
        final DienstbundelGroep value = reader.<DienstbundelGroep>readValue(
                "{\"id\":9001,\"dienstbundel\":9001,\"groepId\":3673,\"indformelehistorie\":false,\"indmaterielehistorie\":false,\"indverantwoording\":false}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Integer.valueOf(9001), value.getId());
        Assert.assertEquals(Integer.valueOf(9001), value.getDienstbundel().getId());
        Assert.assertEquals(3673, value.getGroep().getId());
        Assert.assertEquals(Boolean.FALSE, value.getIndicatieFormeleHistorie());
        Assert.assertEquals(Boolean.FALSE, value.getIndicatieMaterieleHistorie());
        Assert.assertEquals(Boolean.FALSE, value.getIndicatieVerantwoording());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testNieuw() throws IOException {
        final ObjectReader reader = subject.readerFor(DienstbundelGroep.class);
        final DienstbundelGroep value = reader.<DienstbundelGroep>readValue(
                "{\"dienstbundel\":9001,\"groepId\":3673,\"indformelehistorie\":false,\"indmaterielehistorie\":false,\"indverantwoording\":false}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getId());
        Assert.assertEquals(Integer.valueOf(9001), value.getDienstbundel().getId());
        Assert.assertEquals(3673, value.getGroep().getId());
        Assert.assertEquals(Boolean.FALSE, value.getIndicatieFormeleHistorie());
        Assert.assertEquals(Boolean.FALSE, value.getIndicatieMaterieleHistorie());
        Assert.assertEquals(Boolean.FALSE, value.getIndicatieVerantwoording());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final DienstbundelGroep heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);

        final ObjectReader reader = subject.readerFor(DienstbundelGroep.class);
        final DienstbundelGroep weer = reader.<DienstbundelGroep>readValue(json);

        Assert.assertEquals(heen.getId(), weer.getId());
        Assert.assertEquals(heen.getDienstbundel().getId(), weer.getDienstbundel().getId());
        Assert.assertEquals(heen.getGroep().getId(), weer.getGroep().getId());
        Assert.assertEquals(heen.getIndicatieFormeleHistorie(), weer.getIndicatieFormeleHistorie());
        Assert.assertEquals(heen.getIndicatieMaterieleHistorie(), weer.getIndicatieMaterieleHistorie());
        Assert.assertEquals(heen.getIndicatieVerantwoording(), weer.getIndicatieVerantwoording());

    }
}
