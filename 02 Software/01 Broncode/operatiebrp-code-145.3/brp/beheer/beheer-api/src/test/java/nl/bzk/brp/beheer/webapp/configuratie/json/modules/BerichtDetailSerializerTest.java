/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingswijze;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.view.BerichtDetailView;
import org.junit.Assert;
import org.junit.Test;

public class BerichtDetailSerializerTest {

    private static final Timestamp TIJDSTIP = Timestamp.valueOf(LocalDateTime.now());
    private static final SimpleDateFormat SDF = new SimpleDateFormat(AdministratieveHandelingModule.DATUM_TIJD_FORMAAT);
    private static final String TIJDSTIP_ALS_STRING = SDF.format(TIJDSTIP);
    private static final Partij PARTIJ = new Partij("bijhouder", "001234");

    private BrpJsonObjectMapper subject =
            new BrpJsonObjectMapper(new Module[]{new BerichtModule(new BerichtListSerializer(), new BerichtDetailSerializer())});

    @Test
    public void empty() throws IOException {
        Assert.assertEquals("{}", write(new BerichtDetailView(null, null, null)));

        final Bericht bericht = new Bericht(Richting.INGAAND, DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now()));

        Assert.assertEquals(
                "{\"administratieveHandelingSoortNaam\":\"GBA - Bijhouding actueel\",\"richting\":\"Ingaand\"}",
                write(berichtDetailView(bericht)));
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

        final Bericht berichtAntwoord = new Bericht(Richting.INGAAND, DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now()));
        berichtAntwoord.setId(44L);
        berichtAntwoord.setSoortBericht(SoortBericht.BHG_AFS_REGISTREER_ERKENNING_R);

        bericht.setData("data");

        Assert.assertEquals(
                "{\"id\":2,\"soort\":26,\"soortNaam\":\"bhg_afsCorrigeerAfstamming\",\"referentienummer\":\"ref\",\"crossReferentienummer\":\"cross\","
                        + "\"verzenddatum\":\""
                        + TIJDSTIP_ALS_STRING
                        + "\","
                        + "\"ontvangstdatum\":\""
                        + TIJDSTIP_ALS_STRING
                        + "\",\"soortSynchronisatie\":1,\"soortSynchronisatieNaam\":\"Mutatiebericht\",\"verwerkingswijze\":1,"
                        + "\"verwerkingswijzeNaam\":\"Bijhouding\",\"bijhouding\":1,\"bijhoudingNaam\":\"Verwerkt\","
                        + "\"administratieveHandelingSoortNaam\":\"GBA - Bijhouding actueel\","
                        + "\"richting\":\"Ingaand\",\"zendendeSysteem\":\"zendsys\",\"verwerking\":2,"
                        + "\"verwerkingNaam\":\"Foutief\",\"hoogsteMeldingsniveau\":\"Waarschuwing\",\"data\":\"data\"}",
                write(berichtDetailView(bericht)));
    }

    @Test
    public void verzendendePartij() throws IOException {
        final Bericht bericht = new Bericht(Richting.INGAAND, DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now()));
        bericht.setId(2L);
        bericht.setZendendePartij((short) 1234);

        Assert.assertEquals(
                "{\"id\":2,\"zendendePartij\":1234,\"administratieveHandelingSoortNaam\":\"GBA - Bijhouding actueel\",\"richting\":\"Ingaand\"}",
                write(berichtDetailView(bericht)));
    }

    @Test
    public void ontvangendePartij() throws IOException {
        final Bericht bericht = new Bericht(Richting.INGAAND, DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now()));
        bericht.setId(2L);
        bericht.setOntvangendePartij((short) 1234);

        Assert.assertEquals(
                "{\"id\":2,\"ontvangendePartij\":1234,\"administratieveHandelingSoortNaam\":\"GBA - Bijhouding actueel\",\"richting\":\"Ingaand\"}",
                write(berichtDetailView(bericht)));
    }

    private BerichtDetailView berichtDetailView(final Bericht bericht) {
        final Leveringsautorisatie abonnement = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
        abonnement.setNaam("aboNaam");

        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(PARTIJ, SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, TIJDSTIP);

        return new BerichtDetailView(bericht, administratieveHandeling, abonnement);
    }

    private Object write(final BerichtDetailView object) throws JsonProcessingException {
        final ObjectWriter writer = subject.writer();
        return writer.writeValueAsString(object);
    }
}
