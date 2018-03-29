/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.service.algemeen;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BerichtPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingswijze;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiveringOpdrachtConverteerder;
import org.junit.Test;

public class ArchiveringOpdrachtConverteerderTest {

    private static final ZonedDateTime TS_REG = ZonedDateTime.of(2017, 1, 16, 13, 0, 0, 0, ZoneId.of("UTC"));

    private ArchiveringOpdracht archiveringOpdracht() {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.INGAAND, TS_REG);
        archiveringOpdracht.setAdministratieveHandelingId(1L);
        archiveringOpdracht.setBijhoudingResultaat(BijhoudingResultaat.DEELS_UITGESTELD);
        archiveringOpdracht.setCrossReferentienummer("2");
        archiveringOpdracht.setData("data");
        archiveringOpdracht.setDienstId(2);
        archiveringOpdracht.setHoogsteMeldingsNiveau(SoortMelding.INFORMATIE);
        archiveringOpdracht.setLeveringsAutorisatieId(4);
        archiveringOpdracht.setOntvangendePartijId((short) 4);
        archiveringOpdracht.setReferentienummer("6");
        archiveringOpdracht.setRolId(7);
        archiveringOpdracht.setSoortBericht(SoortBericht.BHG_AFS_REGISTREER_GEBOORTE);
        archiveringOpdracht.setSoortSynchronisatie(SoortSynchronisatie.MUTATIE_BERICHT);
        archiveringOpdracht.setTijdstipVerzending(ZonedDateTime.of(2017, 1, 16, 12, 0, 0, 0, ZoneId.of("UTC")));
        archiveringOpdracht.setTijdstipOntvangst(ZonedDateTime.of(2017, 1, 16, 14, 0, 0, 0, ZoneId.of("UTC")));
        archiveringOpdracht.setVerwerkingsresultaat(VerwerkingsResultaat.GESLAAGD);
        archiveringOpdracht.setVerwerkingswijze(Verwerkingswijze.BIJHOUDING);
        archiveringOpdracht.setZendendePartijId((short) 2);
        archiveringOpdracht.setZendendeSysteem("3");
        archiveringOpdracht.addTeArchiverenPersoon(1L);
        archiveringOpdracht.addTeArchiverenPersoon(2L);
        return archiveringOpdracht;
    }

    @Test
    public void testReturnType() {
        final Bericht bericht = ArchiveringOpdrachtConverteerder.converteer(archiveringOpdracht());
        assertThat(bericht, isA(Bericht.class));
    }

    @Test
    public void testWaarden() {
        final Bericht bericht = ArchiveringOpdrachtConverteerder.converteer(archiveringOpdracht());
        assertEquals(Long.valueOf(1L), bericht.getAdministratieveHandeling());
        assertEquals(BijhoudingResultaat.DEELS_UITGESTELD, bericht.getBijhoudingResultaat());
        assertEquals("2", bericht.getCrossReferentieNummer());
        assertEquals("data", bericht.getData());
        assertEquals(Integer.valueOf(2), bericht.getDienst());
        assertEquals(SoortMelding.INFORMATIE, bericht.getHoogsteMeldingsNiveau());
        assertEquals(Integer.valueOf(4), bericht.getLeveringsAutorisatie());
        assertEquals(Short.valueOf((short) 4), bericht.getOntvangendePartij());
        assertEquals("6", bericht.getReferentieNummer());
        assertEquals(Richting.INGAAND, bericht.getRichting());
        assertEquals(Integer.valueOf(7), bericht.getRol());
        assertEquals(SoortBericht.BHG_AFS_REGISTREER_GEBOORTE, bericht.getSoortBericht());
        assertEquals(SoortSynchronisatie.MUTATIE_BERICHT, bericht.getSoortSynchronisatie());
        assertEquals("2017-01-16 12:00:00.0", bericht.getDatumTijdVerzending().toString());
        assertEquals("2017-01-16 13:00:00.0", bericht.getDatumTijdRegistratie().toString());
        assertEquals("2017-01-16 14:00:00.0", bericht.getDatumTijdOntvangst().toString());
        assertEquals(VerwerkingsResultaat.GESLAAGD, bericht.getVerwerkingsResultaat());
        assertEquals(Verwerkingswijze.BIJHOUDING, bericht.getVerwerkingswijze());
        assertEquals(Short.valueOf((short) 2), bericht.getZendendePartij());
        assertEquals("3", bericht.getZendendeSysteem());
        assertEquals(2, bericht.getPersonen().size());
    }

    @Test
    public void testPersonen() {
        final Bericht bericht = ArchiveringOpdrachtConverteerder.converteer(archiveringOpdracht());
        assertEquals("1,2", bericht.getPersonen().stream().map(e -> e.getPersoon().toString()).collect(Collectors.joining(",")));
        assertEquals(Arrays.asList(bericht, bericht), bericht.getPersonen().stream().map(BerichtPersoon::getBericht).collect(Collectors.toList()));
    }

    @Test
    public void testLegeWaarden() {
        final ArchiveringOpdracht opdracht = new ArchiveringOpdracht(Richting.INGAAND, TS_REG);
        final Bericht bericht = ArchiveringOpdrachtConverteerder.converteer(opdracht);

        assertEquals(Richting.INGAAND, bericht.getRichting());
        assertEquals("2017-01-16 13:00:00.0", bericht.getDatumTijdRegistratie().toString());
        assertEquals(null, bericht.getAdministratieveHandeling());
        assertEquals(null, bericht.getBijhoudingResultaat());
        assertEquals(null, bericht.getCrossReferentieNummer());
        assertEquals(null, bericht.getData());
        assertEquals(null, bericht.getDienst());
        assertEquals(null, bericht.getHoogsteMeldingsNiveau());
        assertEquals(null, bericht.getLeveringsAutorisatie());
        assertEquals(null, bericht.getOntvangendePartij());
        assertEquals(null, bericht.getReferentieNummer());
        assertEquals(null, bericht.getRol());
        assertEquals(null, bericht.getSoortBericht());
        assertEquals(null, bericht.getSoortSynchronisatie());
        assertEquals(null, bericht.getDatumTijdVerzending());
        assertEquals(null, bericht.getDatumTijdOntvangst());
        assertEquals(null, bericht.getVerwerkingsResultaat());
        assertEquals(null, bericht.getVerwerkingswijze());
        assertEquals(null, bericht.getZendendePartij());
        assertEquals(null, bericht.getZendendeSysteem());
    }
}
