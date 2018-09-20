/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.internbericht;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Historievorm;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.operationeel.lev.LeveringModel;
import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class ProtocolleringOpdrachtTest {

    private static final String JSON_TEST_STRING =
        "{\"soortDienst\":\"MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING\","
            + "\"levering\":{\"administratieveHandelingId\":24,"
            + "\"datumTijdAanvangFormelePeriodeResultaat\":946684861000,"
            + "\"datumTijdEindeFormelePeriodeResultaat\":1388534461000,"
            + "\"datumTijdKlaarzettenLevering\":1262304061000,\"dienstId\":2,"
            + "\"soortSynchronisatie\":{\"waarde\":\"VOLLEDIGBERICHT\"},\"toegangLeveringsautorisatieId\":1},"
            + "\"personen\":[{\"levering\":{\"administratieveHandelingId\":24,"
            + "\"datumTijdAanvangFormelePeriodeResultaat\":946684861000,"
            + "\"datumTijdEindeFormelePeriodeResultaat\":1388534461000,"
            + "\"datumTijdKlaarzettenLevering\":1262304061000,\"dienstId\":2,"
            + "\"soortSynchronisatie\":{\"waarde\":\"VOLLEDIGBERICHT\"},\"toegangLeveringsautorisatieId\":1},\"persoonId\":1}"
            + ",{\"levering\":{\"administratieveHandelingId\":24,"
            + "\"datumTijdAanvangFormelePeriodeResultaat\":946684861000,"
            + "\"datumTijdEindeFormelePeriodeResultaat\":1388534461000,"
            + "\"datumTijdKlaarzettenLevering\":1262304061000,\"dienstId\":2,"
            + "\"soortSynchronisatie\":{\"waarde\":\"VOLLEDIGBERICHT\"},\"toegangLeveringsautorisatieId\":1},"
            + "\"persoonId\":2}]}";

    private final JsonStringSerializer<ProtocolleringOpdracht> serialiseerder =
        new JsonStringSerializer<>(
            ProtocolleringOpdracht.class);

    private final DatumAttribuut     datumMaterieelSelectie         =
        DatumTijdAttribuut
            .bouwDatumTijd(
                2012,
                1,
                1)
            .naarDatum();
    private final DatumAttribuut     datumAanvangMaterielePeriode   =
        DatumTijdAttribuut
            .bouwDatumTijd(
                2000,
                1,
                1)
            .naarDatum();
    private final DatumAttribuut     datumEindeMaterielePeriode     =
        DatumTijdAttribuut
            .bouwDatumTijd(
                2013,
                12,
                12)
            .naarDatum();
    private final DatumTijdAttribuut datumTijdAanvangFormelePeriode =
        DatumTijdAttribuut
            .bouwDatumTijd(
                2000,
                1,
                1,
                1,
                1,
                1);
    private final DatumTijdAttribuut datumTijdEindeFormelePeriode   =
        DatumTijdAttribuut
            .bouwDatumTijd(
                2014,
                1,
                1,
                1,
                1,
                1);

    @Test
    public void testSerialiseren() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, null, null, null,
                datumTijdAanvangFormelePeriode, datumTijdEindeFormelePeriode, null);

        assertThat(JSON_TEST_STRING, sameJSONAs(serialiseerder.serialiseerNaarString(protocolleringOpdracht))
            .allowingExtraUnexpectedFields().allowingAnyArrayOrdering());
    }

    @Test
    public void testHeenEnTerug() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            serialiseerder.deserialiseerVanuitString(JSON_TEST_STRING);

        assertThat(JSON_TEST_STRING, sameJSONAs(serialiseerder.serialiseerNaarString(protocolleringOpdracht))
            .allowingExtraUnexpectedFields().allowingAnyArrayOrdering());
    }

    @Test
    public void kanValiderenViaJson() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            serialiseerder.deserialiseerVanuitString(JSON_TEST_STRING);

        assertTrue(protocolleringOpdracht.isValide());
    }

    @Test
    public void valideGeenSoortSynchronisatieNietVerplichtBijGeenCategorieDienst() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE,
                datumMaterieelSelectie, datumAanvangMaterielePeriode, datumEindeMaterielePeriode,
                datumTijdAanvangFormelePeriode, datumTijdEindeFormelePeriode, null);
        ReflectionTestUtils.setField(protocolleringOpdracht.getLevering(), "soortSynchronisatie", null);

        assertFalse(protocolleringOpdracht.isValide());
    }

    @Test
    public void nietValideGeenSoortDienst() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(null, datumMaterieelSelectie, datumAanvangMaterielePeriode,
                datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode, datumTijdEindeFormelePeriode, null);

        assertFalse(protocolleringOpdracht.isValide());
    }


    @Test
    public void nietValideDoorSoortSynchronisatieNull() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.ATTENDERING, datumMaterieelSelectie,
                datumAanvangMaterielePeriode, datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode,
                datumTijdEindeFormelePeriode, null);
        ReflectionTestUtils.setField(protocolleringOpdracht.getLevering(), "soortSynchronisatie", null);

        assertFalse(protocolleringOpdracht.isValide());
    }

    @Test
    public void nietValideDoorSoortSynchronisatieWaardeNull() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.ATTENDERING, datumMaterieelSelectie,
                datumAanvangMaterielePeriode, datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode,
                datumTijdEindeFormelePeriode, null);
        ReflectionTestUtils.setField(protocolleringOpdracht.getLevering().getSoortSynchronisatie(), "waarde", null);

        assertFalse(protocolleringOpdracht.isValide());
    }

    @Test(expected = IllegalArgumentException.class)
    public void faaltDoorFoutieveSoortDienst() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.DUMMY, datumMaterieelSelectie, datumAanvangMaterielePeriode,
                datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode, datumTijdEindeFormelePeriode, null);

        protocolleringOpdracht.isValide();
    }

    @Test
    public void valideAttendering() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.ATTENDERING, null, null, null, datumTijdAanvangFormelePeriode,
                datumTijdEindeFormelePeriode, null);

        assertTrue(protocolleringOpdracht.isValide());
    }

    @Test
    public void nietValideAttendering() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.ATTENDERING, datumMaterieelSelectie,
                datumAanvangMaterielePeriode, datumEindeMaterielePeriode, null, datumTijdEindeFormelePeriode,
                null);

        assertFalse(protocolleringOpdracht.isValide());
    }

    @Test
    public void valideAfnemerindicatie() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null,
                datumAanvangMaterielePeriode, null, datumTijdAanvangFormelePeriode,
                datumTijdEindeFormelePeriode,
                null);

        assertTrue(protocolleringOpdracht.isValide());
    }

    @Test
    public void nietValideAfnemerindicatie() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE,
                datumMaterieelSelectie, datumAanvangMaterielePeriode, datumEindeMaterielePeriode, null,
                datumTijdEindeFormelePeriode, null);

        assertFalse(protocolleringOpdracht.isValide());
    }

    @Test
    public void valideGeefMedebewonersVanPersoon() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON, datumMaterieelSelectie,
                datumAanvangMaterielePeriode, datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode,
                datumTijdEindeFormelePeriode, null);

        assertTrue(protocolleringOpdracht.isValide());
    }

    @Test
    public void nietValideGeefMedebewonersVanPersoon() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON, datumMaterieelSelectie,
                datumAanvangMaterielePeriode, datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode, null,
                null);

        assertFalse(protocolleringOpdracht.isValide());
    }

    @Test
    public void valideSynchronisatiePersoon() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.SYNCHRONISATIE_PERSOON, null, datumAanvangMaterielePeriode,
                null,
                datumTijdAanvangFormelePeriode, datumTijdEindeFormelePeriode, null);

        assertTrue(protocolleringOpdracht.isValide());
    }

    @Test
    public void valideSynchronisatiePersoonZonderDatumAanvangMaterieel() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.SYNCHRONISATIE_PERSOON, null, null, null,
                datumTijdAanvangFormelePeriode, datumTijdEindeFormelePeriode, null);

        assertTrue(protocolleringOpdracht.isValide());
    }

    @Test
    public void nietValideSynchronisatiePersoon() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.SYNCHRONISATIE_PERSOON, datumMaterieelSelectie,
                datumAanvangMaterielePeriode, datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode, null,
                null);

        assertFalse(protocolleringOpdracht.isValide());
    }

    @Test
    public void nietValideGeefDetailsPersoonZonderHistorievorm() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.GEEF_DETAILS_PERSOON, datumMaterieelSelectie,
                datumAanvangMaterielePeriode, datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode,
                datumTijdEindeFormelePeriode, null);

        assertFalse(protocolleringOpdracht.isValide());
    }

    @Test
    public void valideGeefDetailsPersoonHistorievormGeen() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.GEEF_DETAILS_PERSOON, null, datumAanvangMaterielePeriode,
                datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode, datumTijdEindeFormelePeriode,
                Historievorm.GEEN);

        assertTrue(protocolleringOpdracht.isValide());
    }

    @Test
    public void nietValideGeefDetailsPersoonHistorievormGeen() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.GEEF_DETAILS_PERSOON, null, datumAanvangMaterielePeriode,
                datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode, null, Historievorm.GEEN);

        assertFalse(protocolleringOpdracht.isValide());
    }

    @Test
    public void valideGeefDetailsPersoonHistorievormMaterieel() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.GEEF_DETAILS_PERSOON, null, null, datumEindeMaterielePeriode,
                datumTijdAanvangFormelePeriode, datumTijdEindeFormelePeriode, Historievorm.MATERIEEL);

        assertTrue(protocolleringOpdracht.isValide());
    }

    @Test
    public void nietValideGeefDetailsPersoonHistorievormMaterieel() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.GEEF_DETAILS_PERSOON, null, datumAanvangMaterielePeriode,
                datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode, null, Historievorm.MATERIEEL);

        assertFalse(protocolleringOpdracht.isValide());
    }

    @Test
    public void valideGeefDetailsPersoonHistorievormMaterieelFormeel() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.GEEF_DETAILS_PERSOON, null, null, datumEindeMaterielePeriode,
                null, datumTijdEindeFormelePeriode, Historievorm.MATERIEEL_FORMEEL);

        assertTrue(protocolleringOpdracht.isValide());
    }

    @Test
    public void nietValideGeefDetailsPersoonHistorievormMaterieelFormeel() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.GEEF_DETAILS_PERSOON, null, datumAanvangMaterielePeriode,
                datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode, null,
                Historievorm.MATERIEEL_FORMEEL);

        assertFalse(protocolleringOpdracht.isValide());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nietValideGeefDetailsPersoonHistorievormOnbekend() {
        final ProtocolleringOpdracht protocolleringOpdracht =
            maakProtocolleringOpdracht(SoortDienst.GEEF_DETAILS_PERSOON, null, datumAanvangMaterielePeriode,
                datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode, datumTijdEindeFormelePeriode,
                Historievorm.DUMMY);

        assertFalse(protocolleringOpdracht.isValide());
    }

    private ProtocolleringOpdracht maakProtocolleringOpdracht(final SoortDienst SoortDienst,
        final DatumAttribuut datumMaterieelSelectie, final DatumAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeMaterielePeriode, final DatumTijdAttribuut datumTijdAanvangFormelePeriode,
        final DatumTijdAttribuut datumTijdEindeFormelePeriode, final Historievorm historievorm)
    {
        final Integer toegangAbonnementId = 1;
        final Integer dienstId = 2;

        final DatumTijdAttribuut datumTijdKlaarzettenLevering = DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1, 1, 1, 1);
        final Long administratieveHandelingId = 24L;
        final SoortSynchronisatieAttribuut soortSynchronisatie =
            new SoortSynchronisatieAttribuut(SoortSynchronisatie.VOLLEDIGBERICHT);

        final LeveringModel levering =
            new LeveringModel(toegangAbonnementId, dienstId, datumTijdKlaarzettenLevering, datumMaterieelSelectie,
                datumAanvangMaterielePeriode, datumEindeMaterielePeriode, datumTijdAanvangFormelePeriode,
                datumTijdEindeFormelePeriode, administratieveHandelingId, soortSynchronisatie);

        final LeveringPersoonModel leveringPersoon1 = new LeveringPersoonModel(levering, 1);
        final LeveringPersoonModel leveringPersoon2 = new LeveringPersoonModel(levering, 2);
        final Set<LeveringPersoonModel> leveringPersonen = new HashSet<>();
        leveringPersonen.add(leveringPersoon1);
        leveringPersonen.add(leveringPersoon2);

        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht(levering, leveringPersonen);

        protocolleringOpdracht.setSoortDienst(SoortDienst);
        protocolleringOpdracht.setHistorievorm(historievorm);

        return protocolleringOpdracht;
    }

}
