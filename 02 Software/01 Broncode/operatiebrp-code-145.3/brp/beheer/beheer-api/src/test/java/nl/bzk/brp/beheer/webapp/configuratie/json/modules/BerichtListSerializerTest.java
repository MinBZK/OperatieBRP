/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingswijze;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.JsonConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.beheer.webapp.view.BerichtListView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class, JsonConfiguratie.class},
        loader = AnnotationConfigContextLoader.class)
@Data(resources = "classpath:/data/actieviewtest.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class BerichtListSerializerTest {

    private static final Timestamp TIJDSTIP = Timestamp.valueOf(LocalDateTime.now());
    private static final SimpleDateFormat SDF = new SimpleDateFormat(AdministratieveHandelingModule.DATUM_TIJD_FORMAAT);
    private static final String TIJDSTIP_ALS_STRING = SDF.format(TIJDSTIP);

    @Inject
    private BrpJsonObjectMapper subject;

    @Test
    public void empty() throws IOException {
        Assert.assertEquals("{}", write(new BerichtListView(null)));

        final Bericht berichtModel = new Bericht(Richting.INGAAND, DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now()));
        Assert.assertEquals("{}", write(new BerichtListView(berichtModel)));
    }

    @Test
    public void complete() throws IOException {
        final Bericht bericht = new Bericht(Richting.INGAAND, DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now()));
        bericht.setId(2L);
        bericht.setSoortBericht(SoortBericht.BHG_AFS_CORRIGEER_AFSTAMMING);
        bericht.setZendendeSysteem("zendsys");
        bericht.setReferentieNummer("ref");
        bericht.setCrossReferentieNummer("cross");
        bericht.setDatumTijdOntvangst(TIJDSTIP);
        bericht.setDatumTijdVerzending(TIJDSTIP);

        bericht.setVerwerkingswijze(Verwerkingswijze.BIJHOUDING);
        bericht.setSoortSynchronisatie(SoortSynchronisatie.MUTATIE_BERICHT);

        bericht.setBijhoudingResultaat(BijhoudingResultaat.VERWERKT);
        bericht.setVerwerkingsResultaat(VerwerkingsResultaat.FOUTIEF);
        bericht.setHoogsteMeldingsNiveau(SoortMelding.WAARSCHUWING);

        Assert.assertEquals(
                "{\"id\":2,\"soort\":26,\"referentienummer\":\"ref\",\"crossReferentienummer\":\"cross\",\"verzenddatum\":\"" + TIJDSTIP_ALS_STRING
                        + "\",\"ontvangstdatum\":\"" + TIJDSTIP_ALS_STRING + "\",\"soortSynchronisatie\":1,\"verwerkingswijze\":1,\"bijhouding\":1}",
                write(new BerichtListView(bericht)));
    }

    private Object write(final BerichtListView object) throws JsonProcessingException {
        final String result = subject.writeValueAsString(object);
        System.out.println("toJson=" + result);
        return result;
    }
}
