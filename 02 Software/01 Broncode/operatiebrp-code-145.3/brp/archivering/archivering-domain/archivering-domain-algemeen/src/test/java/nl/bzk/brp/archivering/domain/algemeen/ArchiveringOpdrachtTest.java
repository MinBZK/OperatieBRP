/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.archivering.domain.algemeen;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingswijze;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import org.junit.Test;

public class ArchiveringOpdrachtTest {

    private static final ZonedDateTime TS_REG = ZonedDateTime.of(2017, 1, 16, 13, 0, 0, 0, ZoneId.of("UTC"));

    private ArchiveringOpdracht subject() {
        final ArchiveringOpdracht subject = new ArchiveringOpdracht(Richting.INGAAND, TS_REG);
        subject.setAdministratieveHandelingId(1L);
        subject.setBijhoudingResultaat(BijhoudingResultaat.DEELS_UITGESTELD);
        subject.setCrossReferentienummer("7");
        subject.setData("8");
        subject.setDienstId(2);
        subject.setHoogsteMeldingsNiveau(SoortMelding.INFORMATIE);
        subject.setLeveringsAutorisatieId(4);
        subject.setOntvangendePartijId((short) 4);
        subject.setReferentienummer("6");
        subject.setRolId(7);
        subject.setSoortBericht(SoortBericht.BHG_AFS_REGISTREER_GEBOORTE);
        subject.setSoortSynchronisatie(SoortSynchronisatie.MUTATIE_BERICHT);
        subject.setTijdstipVerzending(ZonedDateTime.of(2017, 1, 16, 12, 0, 0, 0, ZoneId.of("UTC")));
        subject.setTijdstipOntvangst(ZonedDateTime.of(2017, 1, 16, 14, 0, 0, 0, ZoneId.of("UTC")));
        subject.setVerwerkingsresultaat(VerwerkingsResultaat.GESLAAGD);
        subject.setVerwerkingswijze(Verwerkingswijze.BIJHOUDING);
        subject.setZendendePartijId((short) 2);
        subject.setZendendeSysteem("3");

        subject.addTeArchiverenPersoon(1L);
        subject.addTeArchiverenPersoon(2L);
        return subject;
    }

    @Test
    public void normal() {
        final ArchiveringOpdracht subject = subject();
        assertEquals(Long.valueOf(1L), subject.getAdministratieveHandelingId());
        assertEquals(BijhoudingResultaat.DEELS_UITGESTELD, subject.getBijhoudingResultaat());
        assertEquals("7", subject.getCrossReferentienummer());
        assertEquals("8", subject.getData());
        assertEquals(Integer.valueOf(2), subject.getDienstId());
        assertEquals(SoortMelding.INFORMATIE, subject.getHoogsteMeldingsNiveau());
        assertEquals(Integer.valueOf(4), subject.getLeveringsAutorisatieId());
        assertEquals(4, subject.getOntvangendePartijId().shortValue());
        assertEquals("6", subject.getReferentienummer());
        assertEquals(Richting.INGAAND, subject.getRichting());
        assertEquals(Integer.valueOf(7), subject.getRolId());
        assertEquals(SoortBericht.BHG_AFS_REGISTREER_GEBOORTE, subject.getSoortBericht());
        assertEquals(SoortSynchronisatie.MUTATIE_BERICHT, subject.getSoortSynchronisatie());
        assertEquals("2017-01-16T12:00Z[UTC]", subject.getTijdstipVerzending().toString());
        assertEquals("2017-01-16T14:00Z[UTC]", subject.getTijdstipOntvangst().toString());
        assertEquals(VerwerkingsResultaat.GESLAAGD, subject.getVerwerkingsresultaat());
        assertEquals(2, subject.getZendendePartijId().shortValue());
        assertEquals("3", subject.getZendendeSysteem());
        assertEquals(2, subject.getTeArchiverenPersonen().size());
        assertEquals(Verwerkingswijze.BIJHOUDING, subject.getVerwerkingswijze());
    }

    @Test
    public void empty() {
        final ArchiveringOpdracht subject = new ArchiveringOpdracht(Richting.INGAAND, TS_REG);
        assertEquals(null, subject.getAdministratieveHandelingId());
        assertEquals(null, subject.getCrossReferentienummer());
        assertEquals(null, subject.getBijhoudingResultaat());
        assertEquals(null, subject.getData());
        assertEquals(null, subject.getDienstId());
        assertEquals(null, subject.getHoogsteMeldingsNiveau());
        assertEquals(null, subject.getLeveringsAutorisatieId());
        assertEquals(null, subject.getOntvangendePartijId());
        assertEquals(null, subject.getReferentienummer());
        assertEquals(Richting.INGAAND, subject.getRichting());
        assertEquals(null, subject.getRolId());
        assertEquals(null, subject.getSoortBericht());
        assertEquals(null, subject.getSoortSynchronisatie());
        assertEquals(null, subject.getTijdstipVerzending());
        assertEquals(null, subject.getTijdstipOntvangst());
        assertEquals(null, subject.getVerwerkingsresultaat());
        assertEquals(null, subject.getZendendePartijId());
        assertEquals(null, subject.getZendendeSysteem());
        assertEquals(null, subject.getVerwerkingswijze());
        assertEquals(0, subject.getTeArchiverenPersonen().size());
    }

    @Test
    public void emptyTijdstippen() {
        final ArchiveringOpdracht subject = subject();
        subject.setTijdstipVerzending(null);
        subject.setTijdstipOntvangst(null);
        assertEquals(null, subject.getTijdstipVerzending());
        assertEquals(null, subject.getTijdstipOntvangst());
    }

    @Test
    public void serialize() throws JsonProcessingException {
        final String expected = "{"
                + "\"richting\":\"INGAAND\","
                + "\"tijdstipRegistratie\":\"2017-01-16T13:00:00Z\","
                + "\"administratieveHandelingId\":1,"
                + "\"bijhoudingResultaat\":\"DEELS_UITGESTELD\","
                + "\"crossReferentienummer\":\"7\","
                + "\"data\":\"8\","
                + "\"dienstId\":2,"
                + "\"hoogsteMeldingsNiveau\":\"INFORMATIE\","
                + "\"leveringsAutorisatieId\":4,"
                + "\"ontvangendePartijId\":4,"
                + "\"referentienummer\":\"6\","
                + "\"rolId\":7,"
                + "\"soortBericht\":\"BHG_AFS_REGISTREER_GEBOORTE\","
                + "\"soortSynchronisatie\":\"MUTATIE_BERICHT\","
                + "\"teArchiverenPersonen\":[1,2],"
                + "\"tijdstipOntvangst\":\"2017-01-16T14:00:00Z\","
                + "\"tijdstipVerzending\":\"2017-01-16T12:00:00Z\","
                + "\"verwerkingsresultaat\":\"GESLAAGD\","
                + "\"verwerkingswijze\":\"BIJHOUDING\","
                + "\"zendendePartijId\":2,"
                + "\"zendendeSysteem\":\"3\""
                + "}";
        final String json = new JsonStringSerializer().serialiseerNaarString(subject());
        assertEquals(expected.trim(), json.trim());
    }

    @Test
    public void serializeEmpty() throws JsonProcessingException {
        final String expected = "{\"richting\":\"INGAAND\",\"tijdstipRegistratie\":\"2017-01-16T13:00:00Z\"}";
        final String json = new JsonStringSerializer().serialiseerNaarString(new ArchiveringOpdracht(Richting.INGAAND, TS_REG));
        assertEquals(expected.trim(), json.trim());
    }

    @Test
    public void deserialize() {
        final String json = "{"
                + "\"administratieveHandelingId\":1,"
                + "\"bijhoudingResultaat\":\"DEELS_UITGESTELD\","
                + "\"crossReferentienummer\":\"7\","
                + "\"data\":\"8\","
                + "\"dienstId\":2,"
                + "\"hoogsteMeldingsNiveau\":\"INFORMATIE\","
                + "\"leveringsAutorisatieId\":4,"
                + "\"ontvangendePartijId\":4,"
                + "\"referentienummer\":\"6\","
                + "\"richting\":\"INGAAND\","
                + "\"rolId\":7,"
                + "\"soortBericht\":\"BHG_AFS_REGISTREER_GEBOORTE\","
                + "\"soortSynchronisatie\":\"MUTATIE_BERICHT\","
                + "\"teArchiverenPersonen\":[1,2],"
                + "\"tijdstipOntvangst\":\"2017-01-16T14:00:00Z\","
                + "\"tijdstipVerzending\":\"2017-01-16T12:00:00Z\","
                + "\"verwerkingsresultaat\":\"GESLAAGD\","
                + "\"verwerkingswijze\":\"BIJHOUDING\","
                + "\"zendendePartijId\":2,"
                + "\"zendendeSysteem\":\"3\""
                + "}";
        final ArchiveringOpdracht subject = new JsonStringSerializer().deserialiseerVanuitString(json, ArchiveringOpdracht.class);
        assertEquals(Long.valueOf(1L), subject.getAdministratieveHandelingId());
        assertEquals(BijhoudingResultaat.DEELS_UITGESTELD, subject.getBijhoudingResultaat());
        assertEquals("7", subject.getCrossReferentienummer());
        assertEquals("8", subject.getData());
        assertEquals(Integer.valueOf(2), subject.getDienstId());
        assertEquals(SoortMelding.INFORMATIE, subject.getHoogsteMeldingsNiveau());
        assertEquals(Integer.valueOf(4), subject.getLeveringsAutorisatieId());
        assertEquals(4, subject.getOntvangendePartijId().shortValue());
        assertEquals("6", subject.getReferentienummer());
        assertEquals(Richting.INGAAND, subject.getRichting());
        assertEquals(Integer.valueOf(7), subject.getRolId());
        assertEquals(SoortBericht.BHG_AFS_REGISTREER_GEBOORTE, subject.getSoortBericht());
        assertEquals(SoortSynchronisatie.MUTATIE_BERICHT, subject.getSoortSynchronisatie());
        assertEquals("2017-01-16T12:00Z[UTC]", subject.getTijdstipVerzending().toString());
        assertEquals("2017-01-16T14:00Z[UTC]", subject.getTijdstipOntvangst().toString());
        assertEquals(VerwerkingsResultaat.GESLAAGD, subject.getVerwerkingsresultaat());
        assertEquals(2, subject.getZendendePartijId().shortValue());
        assertEquals("3", subject.getZendendeSysteem());
        assertEquals(Verwerkingswijze.BIJHOUDING, subject.getVerwerkingswijze());
        assertEquals(2, subject.getTeArchiverenPersonen().size());
    }

    @Test
    public void deserializeEmpty() {
        final ArchiveringOpdracht subject = new JsonStringSerializer().deserialiseerVanuitString("{\"richting\":\"INGAAND\"}", ArchiveringOpdracht.class);
        assertEquals(null, subject.getAdministratieveHandelingId());
        assertEquals(null, subject.getBijhoudingResultaat());
        assertEquals(null, subject.getCrossReferentienummer());
        assertEquals(null, subject.getData());
        assertEquals(null, subject.getDienstId());
        assertEquals(null, subject.getHoogsteMeldingsNiveau());
        assertEquals(null, subject.getLeveringsAutorisatieId());
        assertEquals(null, subject.getOntvangendePartijId());
        assertEquals(null, subject.getReferentienummer());
        assertEquals(Richting.INGAAND, subject.getRichting());
        assertEquals(null, subject.getRolId());
        assertEquals(null, subject.getSoortBericht());
        assertEquals(null, subject.getSoortSynchronisatie());
        assertEquals(null, subject.getTijdstipVerzending());
        assertEquals(null, subject.getTijdstipOntvangst());
        assertEquals(null, subject.getVerwerkingsresultaat());
        assertEquals(null, subject.getZendendePartijId());
        assertEquals(null, subject.getZendendeSysteem());
        assertEquals(null, subject.getVerwerkingswijze());
        assertEquals(0, subject.getTeArchiverenPersonen().size());
    }

    @Test
    public void deserializeUnknownSoortBericht() {
        final String json = "{"
                + "\"richting\":\"INGAAND\","
                + "\"soortBericht\":\"DOES_NOT_EXIST\""
                + "}";
        final ArchiveringOpdracht subject = new JsonStringSerializer().deserialiseerVanuitString(json, ArchiveringOpdracht.class);
        assertEquals(Richting.INGAAND, subject.getRichting());
        assertEquals(SoortBericht.ONBEKEND, subject.getSoortBericht());
    }
}
