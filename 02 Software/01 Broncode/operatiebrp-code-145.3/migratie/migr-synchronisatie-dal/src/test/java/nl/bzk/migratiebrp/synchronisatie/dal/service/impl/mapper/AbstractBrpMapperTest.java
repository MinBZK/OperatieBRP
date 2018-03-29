/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Test;

/**
 * Deze test is niet voor de inhoudelijk conversie van de mapper, maar voor het generieke gedeelte waarbij actie en
 * documenten worden gemapt.
 */
public class AbstractBrpMapperTest extends BrpAbstractTest {

    private static final Partij PARTIJ_VAN_ALLES = new Partij("Partij van Alles", "012301");
    private static final Partij PARTIJ_XYZ = new Partij("Partij XYZ", "653401");
    private static final Partij PARTIJ_DEN_HAAG = new Partij("'s-Gravenhage", "051801");
    private static final Partij PARTIJ_MIGRATIE_VOORZIENING = new Partij("Migratievoorziening", BrpPartijCode.MIGRATIEVOORZIENING.getWaarde());
    private static final AdministratieveHandeling ADMIN_HANDELING = new AdministratieveHandeling(
            PARTIJ_VAN_ALLES,
            SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL,
            new Timestamp(System.currentTimeMillis()));

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<>());

    @Inject
    private BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper mapper;

    @Test
    public void testActie() {
        final PersoonIndicatieHistorie historie = maakPersoonIndicatieHistorie();

        final Set<PersoonIndicatieHistorie> historieSet = new HashSet<>();
        historieSet.add(historie);

        final BrpStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> result = mapper.map(historieSet, brpOnderzoekMapper);

        assertNotNull(result);
        assertEquals(1, result.size());
        final BrpGroep<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> groep = result.get(0);

        assertNotNull(groep);
        assertHistorie(groep.getHistorie());
        assertActieInhoud(groep.getActieInhoud());
        assertActieVerval(groep.getActieVerval());
        assertNull(groep.getActieGeldigheid());
    }

    @Test
    public void testVoorkomenTbvLeveringMutatie() {
        final PersoonIndicatieHistorie historie = maakPersoonIndicatieHistorie();
        historie.setIndicatieVoorkomenTbvLeveringMutaties(Boolean.TRUE);

        final Set<PersoonIndicatieHistorie> historieSet = new HashSet<>();
        historieSet.add(historie);

        final BrpStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> result = mapper.map(historieSet, brpOnderzoekMapper);

        assertNull(result);
    }

    @Test
    public void testVoorkomenTbvLeveringMutatie2() {
        final PersoonIndicatieHistorie historie1 = maakPersoonIndicatieHistorie();
        final PersoonIndicatieHistorie historie2 = maakPersoonIndicatieHistorie();
        historie1.setIndicatieVoorkomenTbvLeveringMutaties(Boolean.TRUE);

        final Set<PersoonIndicatieHistorie> historieSet = new HashSet<>();
        historieSet.add(historie1);
        historieSet.add(historie2);

        final BrpStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> result = mapper.map(historieSet, brpOnderzoekMapper);

        assertNotNull(result);
        assertEquals(1, result.size());
        final BrpGroep<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> groep = result.get(0);

        assertNotNull(groep);
        assertHistorie(groep.getHistorie());
        assertActieInhoud(groep.getActieInhoud());
        assertActieVerval(groep.getActieVerval());
        assertNull(groep.getActieGeldigheid());
    }

    private PersoonIndicatieHistorie maakPersoonIndicatieHistorie() {
        final PersoonIndicatieHistorie historie =
                new PersoonIndicatieHistorie(new PersoonIndicatie(
                        new Persoon(SoortPersoon.INGESCHREVENE),
                        SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT), true);
        historie.setWaarde(Boolean.TRUE);
        historie.setActieInhoud(buildBRPActieInhoud());
        historie.setActieVerval(buildBRPActieVerval());
        historie.setActieAanpassingGeldigheid(null);
        historie.setDatumAanvangGeldigheid(19940102);
        historie.setDatumEindeGeldigheid(20120403);
        historie.setDatumTijdRegistratie(timestamp("20110101123403"));
        historie.setDatumTijdVerval(timestamp("20120304183306"));
        return historie;
    }

    private void assertHistorie(final BrpHistorie historie) {
        assertNotNull(historie);
        assertEquals(new BrpDatum(19940102, null), historie.getDatumAanvangGeldigheid());
        assertEquals(new BrpDatum(20120403, null), historie.getDatumEindeGeldigheid());
        assertEquals(BrpDatumTijd.fromDatumTijd(20110101123403L, null), historie.getDatumTijdRegistratie());
        assertEquals(BrpDatumTijd.fromDatumTijd(20120304183306L, null), historie.getDatumTijdVerval());
    }

    private BRPActie buildBRPActieInhoud() {
        // Actie zonder documenten
        final BRPActie actie = new BRPActie(SoortActie.CONVERSIE_GBA, ADMIN_HANDELING, PARTIJ_VAN_ALLES, timestamp("20050303120000"));
        actie.setId(44885744L);
        return actie;
    }

    private void assertActieInhoud(final BrpActie actie) {
        assertNotNull(actie);
        assertEquals(BrpDatumTijd.fromDatumTijd(20050303120000L, null), actie.getDatumTijdRegistratie());
        assertEquals(Long.valueOf(44885744L), actie.getId());
        assertEquals(new BrpPartijCode("012301"), actie.getPartijCode());
        assertEquals(BrpSoortActieCode.CONVERSIE_GBA, actie.getSoortActieCode());
        assertNull(actie.getActieBronnen());
    }

    private BRPActie buildBRPActieVerval() {
        // Actie met documenten
        final BRPActie actie = new BRPActie(SoortActie.CONVERSIE_GBA, ADMIN_HANDELING, PARTIJ_XYZ, timestamp("20110603123313"));
        actie.setId(453874L);
        actie.koppelDocumentViaActieBron(buildDocument());
        actie.koppelDocumentViaActieBron(buildDocumentExtraInfo());
        return actie;
    }

    private Document buildDocument() {
        final Document document = new Document(new SoortDocument("Akte", "Akte_oms"), PARTIJ_DEN_HAAG);
        document.setId(1L);
        document.setAktenummer("Akte-01");

        return document;
    }

    private Document buildDocumentExtraInfo() {
        final Document document = new Document(new SoortDocument("Migratievoorziening", "Migratievoorziening_oms"), PARTIJ_MIGRATIE_VOORZIENING);
        document.setId(2L);
        document.setOmschrijving("Extra info doc");

        return document;
    }

    private void assertActieVerval(final BrpActie actie) {
        assertNotNull(actie);
        assertEquals(BrpDatumTijd.fromDatumTijd(20110603123313L, null), actie.getDatumTijdRegistratie());
        assertEquals(Long.valueOf(453874L), actie.getId());
        assertEquals(new BrpPartijCode("653401"), actie.getPartijCode());
        assertEquals(BrpSoortActieCode.CONVERSIE_GBA, actie.getSoortActieCode());
        assertNotNull(actie.getActieBronnen());
        assertEquals(2, actie.getActieBronnen().size());

        assertDocument(actie.getActieBronnen().get(0).getDocumentStapel());
        assertDocument(actie.getActieBronnen().get(1).getDocumentStapel());
    }

    private void assertDocument(final BrpStapel<BrpDocumentInhoud> stapel) {
        assertNotNull(stapel);
        assertEquals(1, stapel.size());

        final BrpDocumentInhoud inhoud = stapel.get(0).getInhoud();

        if (new BrpString("Akte-01", null).equals(inhoud.getAktenummer())) {
            assertEquals("051801", inhoud.getPartijCode().getWaarde());
            assertEquals("Akte", inhoud.getSoortDocumentCode().getWaarde());
        } else if (new BrpString("Extra info doc", null).equals(inhoud.getOmschrijving())) {
            assertEquals("199902", inhoud.getPartijCode().getWaarde());
            assertEquals("Migratievoorziening", inhoud.getSoortDocumentCode().getWaarde());
        } else {
            fail("Onverwacht document");
        }

    }
}
