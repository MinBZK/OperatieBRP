/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.internbericht;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Before;
import org.junit.Test;


public class SynchronisatieBerichtGegevensTest {

    private static final Long        ADMINISTRATIEVE_HANDELING_ID    = 123L;
    private static final Integer     TOEGANG_LEVERINGSAUTORISATIE_ID = 456;
    private static final Integer     DATUM1                          = 20130101;
    private static final Integer     DATUM2                          = 20130102;
    private static final SoortDienst SOORT_DIENST                    =
        SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE;

    private final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens =
        new SynchronisatieBerichtGegevens();

    private BerichtStuurgegevensGroepModel modelStuurgegevens;
    private List<Integer>                  geleverdePersoonsIds;

    @Before
    public void init() {
        final BerichtStuurgegevensGroepBericht berichtStuurgegevens = new BerichtStuurgegevensGroepBericht();
        berichtStuurgegevens.setZendendePartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM);
        berichtStuurgegevens.setZendendeSysteem(new SysteemNaamAttribuut("applicatieNaam"));
        berichtStuurgegevens.setOntvangendePartij(StatischeObjecttypeBuilder.PARTIJ_MINISTERIE_BZK);
        berichtStuurgegevens.setOntvangendeSysteem(null);
        berichtStuurgegevens.setReferentienummer(new ReferentienummerAttribuut("referentieNummer"));
        berichtStuurgegevens.setCrossReferentienummer(null);
        berichtStuurgegevens.setDatumTijdVerzending(DatumTijdAttribuut.bouwDatumTijd(2013, 31, 3));
        berichtStuurgegevens.setDatumTijdOntvangst(null);

        modelStuurgegevens = new BerichtStuurgegevensGroepModel(berichtStuurgegevens);
        synchronisatieBerichtGegevens.setStuurgegevens(modelStuurgegevens);

        synchronisatieBerichtGegevens.setAdministratieveHandelingId(ADMINISTRATIEVE_HANDELING_ID);

        geleverdePersoonsIds = new ArrayList<>();
        geleverdePersoonsIds.add(1);
        geleverdePersoonsIds.add(2);
        geleverdePersoonsIds.add(3);
        geleverdePersoonsIds.add(4);

        synchronisatieBerichtGegevens.setGeleverdePersoonsIds(geleverdePersoonsIds);

        synchronisatieBerichtGegevens.setToegangLeveringsautorisatieIdId(TOEGANG_LEVERINGSAUTORISATIE_ID);

        synchronisatieBerichtGegevens.setStelsel(Stelsel.BRP);

        synchronisatieBerichtGegevens.setAdministratieveHandelingTijdstipRegistratie(
                DatumTijdAttribuut.bouwDatumTijd(1983, 1, 3));

        synchronisatieBerichtGegevens.setSoortSynchronisatie(new SoortSynchronisatieAttribuut(
                SoortSynchronisatie.MUTATIEBERICHT));
        synchronisatieBerichtGegevens.setDienstId(123);
        synchronisatieBerichtGegevens.setSoortDienst(SOORT_DIENST);

        synchronisatieBerichtGegevens.setDatumAanvangMaterielePeriodeResultaat(
                new DatumEvtDeelsOnbekendAttribuut(DATUM1));
        synchronisatieBerichtGegevens.setDatumEindeMaterielePeriodeResultaat(
                new DatumEvtDeelsOnbekendAttribuut(DATUM2));
        synchronisatieBerichtGegevens.setDatumTijdAanvangFormelePeriodeResultaat(
                DatumTijdAttribuut.bouwDatumTijd(1983, 2, 3));
        synchronisatieBerichtGegevens.setDatumTijdEindeFormelePeriodeResultaat(
                DatumTijdAttribuut.bouwDatumTijd(2013, 1, 3));

    }

    @Test
    public void testConstructorMetArgumenten() {
        final SynchronisatieBerichtGegevens gegevens = new SynchronisatieBerichtGegevens(ADMINISTRATIEVE_HANDELING_ID,
            TOEGANG_LEVERINGSAUTORISATIE_ID);

        assertThat(gegevens.getAdministratieveHandelingId(), is(ADMINISTRATIEVE_HANDELING_ID));
        assertThat(gegevens.getToegangLeveringsautorisatieId(), is(TOEGANG_LEVERINGSAUTORISATIE_ID));
    }

    @Test
    public void testGetAdministratieveHandelingId() throws Exception {
        assertEquals(ADMINISTRATIEVE_HANDELING_ID,
                     synchronisatieBerichtGegevens.getAdministratieveHandelingId());
    }

    @Test
    public void testGetStuurgegevens() {
        assertEquals(modelStuurgegevens,
                     synchronisatieBerichtGegevens.getStuurgegevens());
    }

    @Test
    public void testGetGeleverdePersoonsIds() {
        assertEquals(geleverdePersoonsIds,
                     synchronisatieBerichtGegevens.getGeleverdePersoonsIds());
    }

    @Test
    public void testGetToegangAbonnementId() {
        assertEquals(TOEGANG_LEVERINGSAUTORISATIE_ID,
                     synchronisatieBerichtGegevens.getToegangLeveringsautorisatieId());
    }

    @Test
    public void testGetDatumTijdFormelePeriodeResultaat() {
        assertEquals(DatumTijdAttribuut.bouwDatumTijd(1983, 2, 3),
                     synchronisatieBerichtGegevens.getDatumTijdAanvangFormelePeriodeResultaat());
        assertEquals(DatumTijdAttribuut.bouwDatumTijd(2013, 1, 3),
                     synchronisatieBerichtGegevens.getDatumTijdEindeFormelePeriodeResultaat());
    }

    @Test
    public void testGetDatumMaterielePeriodeResultaat() {
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(DATUM1),
                     synchronisatieBerichtGegevens.getDatumAanvangMaterielePeriodeResultaat());
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(DATUM2),
                     synchronisatieBerichtGegevens.getDatumEindeMaterielePeriodeResultaat());
    }

    @Test
    public void testGetCatalogusOptie() {
        assertEquals(SOORT_DIENST,
                     synchronisatieBerichtGegevens.getSoortDienst());
    }

    @Test
    public void testGetenSetPartijIds() {
        synchronisatieBerichtGegevens.setZendendePartijId((short) 321);
        synchronisatieBerichtGegevens.setOntvangendePartijId((short) 789);

        assertTrue((short) 321 == synchronisatieBerichtGegevens.getZendendePartijId());
        assertTrue((short) 789 == synchronisatieBerichtGegevens.getOntvangendePartijId());
    }

    @Test
    public void testOverigeGetters() {
        assertThat(synchronisatieBerichtGegevens.getSoortDienst(), equalTo(SOORT_DIENST));

        assertThat(synchronisatieBerichtGegevens.getSoortSynchronisatie(),
                   equalTo(new SoortSynchronisatieAttribuut(SoortSynchronisatie.MUTATIEBERICHT)));

        assertThat(synchronisatieBerichtGegevens.getAdministratieveHandelingTijdstipRegistratie(),
                   equalTo(DatumTijdAttribuut.bouwDatumTijd(1983, 1, 3)));

        assertThat(synchronisatieBerichtGegevens.getStelsel(), equalTo(Stelsel.BRP));
        assertThat(synchronisatieBerichtGegevens.getDienstId(), equalTo(123));
    }

    @Test
    public void testToString() {
        // Eerste gedeelte van de de toString-methode is niet te voorspellen, omdat de toString op object niveau wordt
        // aangeroepen en vervolgens wordt de inhoud ge-append. Dit laatste wordt afgedekt in deze test.
        assertThat(synchronisatieBerichtGegevens.toString(), endsWith(
                "[admhndID=123,toegangLeveringsautorisatieId=456,dienstID=123,stelsel=BRP,persoonIDs=[1, 2, 3, 4],"
                    + "administratieveHandelingTijdstipRegistratie=Mon Jan 03 12:00:00 CET 1983,soortSynchronisatie=MUTATIEBERICHT,soortDienst=MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE,datumAanvangMaterielePeriodeResultaat=2013-01-01,datumEindeMaterielePeriodeResultaat=2013-01-02,datumTijdAanvangFormelePeriodeResultaat=Thu Feb 03 12:00:00 CET 1983,datumTijdEindeFormelePeriodeResultaat=Thu Jan 03 12:00:00 CET 2013,zendendePartijId=<null>,ontvangendePartijId=<null>]"));
    }
}
