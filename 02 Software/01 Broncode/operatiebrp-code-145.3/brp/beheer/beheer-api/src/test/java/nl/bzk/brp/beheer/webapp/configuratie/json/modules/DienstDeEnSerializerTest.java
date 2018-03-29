/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.sql.Timestamp;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
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
public class DienstDeEnSerializerTest extends AbstractDatabaseTest {

    @Inject
    private BrpJsonObjectMapper subject;

    public DienstDeEnSerializerTest() {
        this.subject = subject;
    }

    static void assertGelijk(final Dienst heen, final Dienst weer) {
        Assert.assertEquals(heen.getId(), weer.getId());
        Assert.assertEquals(heen.getAttenderingscriterium(), weer.getAttenderingscriterium());
        Assert.assertEquals(heen.getDatumIngang(), weer.getDatumIngang());
        Assert.assertEquals(heen.getDatumEinde(), weer.getDatumEinde());
        Assert.assertEquals(heen.getEersteSelectieDatum(), weer.getEersteSelectieDatum());
        Assert.assertEquals(heen.getSoortSelectie(), weer.getSoortSelectie());
        Assert.assertEquals(heen.getHistorievormSelectie(), weer.getHistorievormSelectie());
        Assert.assertEquals(heen.getSelectieInterval(), weer.getSelectieInterval());
        Assert.assertEquals(heen.getEenheidSelectieInterval(), weer.getEenheidSelectieInterval());
        Assert.assertEquals(heen.getNadereSelectieCriterium(), weer.getNadereSelectieCriterium());
        Assert.assertEquals(heen.getSelectiePeilmomentMaterieelResultaat(), weer.getSelectiePeilmomentMaterieelResultaat());
        Assert.assertEquals(heen.getSelectiePeilmomentFormeelResultaat(), weer.getSelectiePeilmomentFormeelResultaat());
        Assert.assertEquals(heen.getIndicatieSelectieresultaatControleren(), weer.getIndicatieSelectieresultaatControleren());
        Assert.assertEquals(heen.getMaxAantalPersonenPerSelectiebestand(), weer.getMaxAantalPersonenPerSelectiebestand());
        Assert.assertEquals(heen.getMaxGrootteSelectiebestand(), weer.getMaxGrootteSelectiebestand());
        Assert.assertEquals(heen.getIndVerzVolBerBijWijzAfniNaSelectie(), weer.getIndVerzVolBerBijWijzAfniNaSelectie());
        Assert.assertEquals(heen.getLeverwijzeSelectie(), weer.getLeverwijzeSelectie());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.readerFor(Dienst.class);
        reader.<Dienst>readValue("{}");
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.readerFor(Dienst.class);
        final Dienst value = reader.readValue(
                "{\"id\":2,\"dienstbundel\":\"9001\",\"soort\":1,\"naderePopulatieBeperking\":\"popBeperk\",\"attenderingscriterium\":\"att\","
                        + "\"datumIngang\":19710101,\"datumEinde\":\"19720202\",\"eersteSelectiedatum\":\"19730303\",\"soortSelectie\":1,"
                        + "\"selectieInterval\": \"12\",\n"
                        + "\"eenheidSelectieInterval\": 2,\n"
                        + "\"nadereSelectieCriterium\": \"criterium\",\n"
                        + "\"selectiePeilmomentMaterieelResultaat\": 20130101,\n"
                        + "\"selectiePeilmomentFormeelResultaat\": \"2012-01-01 12:00:01\",\n"
                        + "\"historieVorm\": 1,\n"
                        + "\"indicatieResultaatControleren\": \"Ja\",\n"
                        + "\"maxPersonenPerSelectie\": \"33\",\n"
                        + "\"maxGrootteSelectie\": \"42\",\n"
                        + "\"indicatieVolledigBerichtBijAfnemerindicatieNaSelectie\": \"Ja\",\n"
                        + "\"leverwijzeSelectie\": 2}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Integer.valueOf(2), value.getId());
        Assert.assertEquals("att", value.getAttenderingscriterium());
        Assert.assertEquals(Integer.valueOf(19710101), value.getDatumIngang());
        Assert.assertEquals(Integer.valueOf(19720202), value.getDatumEinde());
        Assert.assertEquals(Integer.valueOf(19730303), value.getEersteSelectieDatum());
        Assert.assertEquals(Integer.valueOf(1), value.getSoortSelectie());

        Assert.assertEquals(Integer.valueOf(12), value.getSelectieInterval());
        Assert.assertEquals(Integer.valueOf(2), value.getEenheidSelectieInterval());
        Assert.assertEquals("criterium", value.getNadereSelectieCriterium());
        Assert.assertEquals(Integer.valueOf(20130101), value.getSelectiePeilmomentMaterieelResultaat());
        Assert.assertEquals(Timestamp.valueOf("2012-01-01 12:00:01"), value.getSelectiePeilmomentFormeelResultaat());
        Assert.assertEquals(Integer.valueOf(1), value.getHistorievormSelectie());
        Assert.assertEquals(true, value.getIndicatieSelectieresultaatControleren());
        Assert.assertEquals(Integer.valueOf(33), value.getMaxAantalPersonenPerSelectiebestand());
        Assert.assertEquals(Integer.valueOf(42), value.getMaxGrootteSelectiebestand());
        Assert.assertEquals(true, value.getIndVerzVolBerBijWijzAfniNaSelectie());
        Assert.assertEquals(Integer.valueOf(2), value.getLeverwijzeSelectie());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final Dienst heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);

        final ObjectReader reader = subject.readerFor(Dienst.class);
        final Dienst weer = reader.<Dienst>readValue(json);

        assertGelijk(heen, weer);
    }
}
