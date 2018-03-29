/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ActieBron;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.OnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteitPaar;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractDeltaTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapperImpl;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor {@link OnderzoekDeltaProces}.
 */
public class OnderzoekDeltaProcesTest extends AbstractDeltaTest {

    private static final String LO3_ONDERZOEK = "010000";
    private static final int ONDERZOEK_START_DATUM = 20150101;

    private DeltaProces onderzoekDeltaProces;
    private Persoon bestaandPersoon;
    private Persoon nieuwPersoon;
    private Partij partij;
    private DeltaBepalingContext context;
    private BrpBoolean trueWaarde;
    private Lo3Herkomst lo3Herkomst;
    private Timestamp timestamp;
    private AdministratieveHandeling administratieveHandeling;
    private Lo3Onderzoek lo3Onderzoek;

    @Before
    public void setUp() {
        final Timestamp timestamp2 = Timestamp.valueOf("2015-09-01 01:00:00.000");
        timestamp = Timestamp.valueOf("2015-01-01 01:00:00.000");

        bestaandPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        nieuwPersoon = new Persoon(SoortPersoon.INGESCHREVENE);

        partij = maakPartij();
        administratieveHandeling = new AdministratieveHandeling(partij, SoortAdministratieveHandeling.GBA_INITIELE_VULLING, timestamp);

        bestaandPersoon.addPersoonAfgeleidAdministratiefHistorie(new PersoonAfgeleidAdministratiefHistorie(
                (short) 1,
                bestaandPersoon,
                administratieveHandeling,
                timestamp));
        bestaandPersoon.addPersoonInschrijvingHistorie(new PersoonInschrijvingHistorie(bestaandPersoon, 20150101, 1L, timestamp));
        bestaandPersoon.addPersoonBijhoudingHistorie(new PersoonBijhoudingHistorie(
                bestaandPersoon,
                partij,
                Bijhoudingsaard.INGEZETENE,
                NadereBijhoudingsaard.ACTUEEL));
        bestaandPersoon.addPersoonSamengesteldeNaamHistorie(maakPersoonSamengesteldeNaamHistorie(bestaandPersoon, false));

        nieuwPersoon.addPersoonInschrijvingHistorie(new PersoonInschrijvingHistorie(nieuwPersoon, 20150101, 2L, timestamp2));
        nieuwPersoon.addPersoonAfgeleidAdministratiefHistorie(new PersoonAfgeleidAdministratiefHistorie(
                (short) 1,
                nieuwPersoon,
                administratieveHandeling,
                timestamp2));
        nieuwPersoon.addPersoonSamengesteldeNaamHistorie(maakPersoonSamengesteldeNaamHistorie(nieuwPersoon, false));

        onderzoekDeltaProces = new OnderzoekDeltaProces();

        context = new DeltaBepalingContext(nieuwPersoon, bestaandPersoon, null, false);

        lo3Herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0);
        lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(LO3_ONDERZOEK, null), new Lo3Datum(ONDERZOEK_START_DATUM), null, lo3Herkomst);

        trueWaarde = new BrpBoolean(true, lo3Onderzoek);
        SynchronisatieLogging.init();
    }

    @Test
    public void testToevoegenOnderzoekAanPersoon() throws ReflectiveOperationException {
        assertTrue(bestaandPersoon.getOnderzoeken().isEmpty());
        final PersoonIndicatieHistorie indicatieHistorie = maakPersoonIndicatie(bestaandPersoon);

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon, maakPartij());
        onderzoekMapper.mapOnderzoek(indicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE);
        final Onderzoek onderzoek = onderzoekMapper.getOnderzoekSet().iterator().next();

        onderzoekDeltaProces.verwerkVerschillen(context);

        controleerGekoppeldOnderzoek(onderzoek);
        controleerOnderzoek(onderzoek);
    }

    @Test
    public void testDeltaGeenOnderzoek() {
        assertTrue(bestaandPersoon.getOnderzoeken().isEmpty());

        onderzoekDeltaProces.verwerkVerschillen(context);
        // Er waren geen onderzoeken dus persoon moet nog steeds geen onderzoeken bevatten
        assertTrue(bestaandPersoon.getOnderzoeken().isEmpty());
    }

    @Test
    public void testDeltaOnderzoekToegevoegdNietBestaandVoorkomen() throws ReflectiveOperationException {
        assertTrue(bestaandPersoon.getOnderzoeken().isEmpty());

        final PersoonIndicatieHistorie indicatieHistorie = maakPersoonIndicatie(bestaandPersoon);

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon, maakPartij());
        onderzoekMapper.mapOnderzoek(indicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE);
        final Set<Onderzoek> onderzoeken = onderzoekMapper.getOnderzoekSet();
        assertEquals(1, onderzoeken.size());

        onderzoekDeltaProces.verwerkVerschillen(context);
        final Onderzoek onderzoek = onderzoeken.iterator().next();
        controleerGekoppeldOnderzoek(onderzoek);
        controleerOnderzoek(onderzoek);
    }

    @Test
    public void testDeltaOnderzoekToegevoegdBestaandVoorkomen() throws ReflectiveOperationException {
        assertTrue(bestaandPersoon.getOnderzoeken().isEmpty());

        final PersoonIndicatieHistorie bestaandPersoonIndicatieHistorie = maakPersoonIndicatie(bestaandPersoon);
        final PersoonIndicatieHistorie nieuwPersoonIndicatieHistorie = maakPersoonIndicatie(nieuwPersoon);

        context.addDeltaEntiteitPaarSetInhoud(
                Collections.singletonList(new DeltaEntiteitPaar(bestaandPersoonIndicatieHistorie, nieuwPersoonIndicatieHistorie)));

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon, maakPartij());
        onderzoekMapper.mapOnderzoek(nieuwPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE);
        final Set<Onderzoek> onderzoeken = onderzoekMapper.getOnderzoekSet();
        assertEquals(1, onderzoeken.size());

        onderzoekDeltaProces.verwerkVerschillen(context);
        final Onderzoek gekoppeldOnderzoek = onderzoeken.iterator().next();
        controleerGekoppeldOnderzoek(gekoppeldOnderzoek);
        controleerOnderzoek(gekoppeldOnderzoek);
        assertEquals(1, gekoppeldOnderzoek.getGegevenInOnderzoekSet().size());

        final GegevenInOnderzoek gegevenInOnderzoek = gekoppeldOnderzoek.getGegevenInOnderzoekSet().iterator().next();
        final Entiteit entiteit = gegevenInOnderzoek.getEntiteitOfVoorkomen();
        assertNotSame(nieuwPersoonIndicatieHistorie, entiteit);
        assertSame(bestaandPersoonIndicatieHistorie, entiteit);
    }

    @Test
    public void testDeltaOnderzoekToegevoegdBestaandVoorkomenALaag() throws ReflectiveOperationException {
        assertTrue(bestaandPersoon.getOnderzoeken().isEmpty());

        final PersoonIndicatieHistorie bestaandPersoonIndicatieHistorie = maakPersoonIndicatie(bestaandPersoon);
        final PersoonIndicatieHistorie nieuwPersoonIndicatieHistorie = maakPersoonIndicatie(nieuwPersoon);
        final PersoonIndicatie nieuwPersoonIndicatie = nieuwPersoonIndicatieHistorie.getPersoonIndicatie();
        final PersoonIndicatie bestaandPersoonIndicatie = bestaandPersoonIndicatieHistorie.getPersoonIndicatie();

        context.addDeltaEntiteitPaarSetInhoud(
                Collections.singletonList(new DeltaEntiteitPaar(bestaandPersoonIndicatieHistorie, nieuwPersoonIndicatieHistorie)));
        context.addDeltaEntiteitPaarSetInhoud(Collections.singletonList(new DeltaEntiteitPaar(bestaandPersoonIndicatie, nieuwPersoonIndicatie)));

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon, maakPartij());
        onderzoekMapper.mapOnderzoek(nieuwPersoonIndicatie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE);
        final Set<Onderzoek> onderzoeken = onderzoekMapper.getOnderzoekSet();
        assertEquals(1, onderzoeken.size());

        onderzoekDeltaProces.verwerkVerschillen(context);
        final Onderzoek gekoppeldOnderzoek = onderzoeken.iterator().next();
        controleerGekoppeldOnderzoek(gekoppeldOnderzoek);
        controleerOnderzoek(gekoppeldOnderzoek);
        assertEquals(1, gekoppeldOnderzoek.getGegevenInOnderzoekSet().size());

        final GegevenInOnderzoek gegevenInOnderzoek = gekoppeldOnderzoek.getGegevenInOnderzoekSet().iterator().next();
        final Entiteit entiteit = gegevenInOnderzoek.getEntiteitOfVoorkomen();
        assertNotSame(nieuwPersoonIndicatie, entiteit);
        assertSame(bestaandPersoonIndicatie, entiteit);
    }

    @Test
    public void testDeltaOnderzoekVerwijderd() throws ReflectiveOperationException {
        assertTrue(bestaandPersoon.getOnderzoeken().isEmpty());

        final PersoonIndicatieHistorie bestaandPersoonIndicatieHistorie = maakPersoonIndicatie(bestaandPersoon);

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(bestaandPersoon, maakPartij());
        onderzoekMapper.mapOnderzoek(bestaandPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE);
        final Onderzoek bestaandOnderzoek = onderzoekMapper.getOnderzoekSet().iterator().next();

        controleerGekoppeldOnderzoek(onderzoekMapper.getOnderzoekSet().iterator().next());

        onderzoekDeltaProces.verwerkVerschillen(context);
        final OnderzoekHistorie bestaandOnderzoekHistorie = bestaandOnderzoek.getOnderzoekHistorieSet().iterator().next();
        assertNotNull(bestaandOnderzoekHistorie.getActieVervalTbvLeveringMutaties());
        assertTrue(bestaandOnderzoekHistorie.getIndicatieVoorkomenTbvLeveringMutaties());
        assertNotNull(bestaandOnderzoekHistorie.getActieVerval());
        assertNotNull(bestaandOnderzoekHistorie.getDatumTijdVerval());
    }

    @Test
    public void testDeltaOnderzoekGewijzigd() throws ReflectiveOperationException {
        assertTrue(bestaandPersoon.getOnderzoeken().isEmpty());
        final PersoonIndicatieHistorie bestaandPersoonIndicatieHistorie = maakPersoonIndicatie(bestaandPersoon);
        final PersoonIndicatieHistorie nieuwPersoonIndicatieHistorie = maakPersoonIndicatie(nieuwPersoon);

        context.addDeltaEntiteitPaarSetInhoud(
                Collections.singletonList(new DeltaEntiteitPaar(bestaandPersoonIndicatieHistorie, nieuwPersoonIndicatieHistorie)));

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(bestaandPersoon, maakPartij());
        onderzoekMapper.mapOnderzoek(bestaandPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE);
        final Onderzoek bestaandOnderzoek = onderzoekMapper.getOnderzoekSet().iterator().next();
        controleerGekoppeldOnderzoek(bestaandOnderzoek);

        final Integer onderzoekEinddatum = 20150102;
        final BrpBoolean trueWaardeGeslotenOnderzoek =
                new BrpBoolean(true, new Lo3Onderzoek(
                        new Lo3Integer(LO3_ONDERZOEK, null),
                        new Lo3Datum(ONDERZOEK_START_DATUM),
                        new Lo3Datum(onderzoekEinddatum),
                        lo3Herkomst));

        final OnderzoekMapper nieuwOnderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon, maakPartij());
        nieuwOnderzoekMapper.mapOnderzoek(nieuwPersoonIndicatieHistorie, trueWaardeGeslotenOnderzoek, Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE);
        onderzoekDeltaProces.verwerkVerschillen(context);

        controleerGekoppeldOnderzoek(bestaandOnderzoek);
        assertEquals(2, bestaandOnderzoek.getOnderzoekHistorieSet().size());
        final OnderzoekHistorie
                onderzoekHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandOnderzoek.getOnderzoekHistorieSet());
        assertNotNull(onderzoekHistorie);
        assertEquals(onderzoekEinddatum, onderzoekHistorie.getDatumEinde());
        assertEquals(StatusOnderzoek.AFGESLOTEN, onderzoekHistorie.getStatusOnderzoek());
    }

    @Test
    public void testOnderzoekOngewijzigd() throws ReflectiveOperationException {
        final PersoonIndicatieHistorie bestaandPersoonIndicatieHistorie = maakPersoonIndicatie(bestaandPersoon);
        final PersoonIndicatieHistorie nieuwPersoonIndicatieHistorie = maakPersoonIndicatie(nieuwPersoon);

        context.addDeltaEntiteitPaarSetInhoud(
                Collections.singletonList(new DeltaEntiteitPaar(bestaandPersoonIndicatieHistorie, nieuwPersoonIndicatieHistorie)));

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(bestaandPersoon, maakPartij());
        onderzoekMapper.mapOnderzoek(bestaandPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE);
        final Onderzoek bestaandOnderzoek = onderzoekMapper.getOnderzoekSet().iterator().next();

        controleerGekoppeldOnderzoek(bestaandOnderzoek);

        final OnderzoekMapper nieuwOnderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon, maakPartij());
        nieuwOnderzoekMapper.mapOnderzoek(nieuwPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE);
        onderzoekDeltaProces.verwerkVerschillen(context);

        controleerGekoppeldOnderzoek(bestaandOnderzoek);
    }

    @Test
    public void testOnderzoekOpBrpNietActueel() throws ReflectiveOperationException {
        final PersoonIndicatieHistorie bestaandPersoonIndicatieHistorie = maakPersoonIndicatie(bestaandPersoon);
        final PersoonIndicatieHistorie nieuwPersoonIndicatieHistorie = maakPersoonIndicatie(nieuwPersoon);
        nieuwPersoonIndicatieHistorie.setDatumTijdVerval(timestamp);

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(bestaandPersoon, maakPartij());
        onderzoekMapper.mapOnderzoek(bestaandPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE);
        final Onderzoek bestaandOnderzoek = onderzoekMapper.getOnderzoekSet().iterator().next();

        controleerGekoppeldOnderzoek(bestaandOnderzoek);

        final OnderzoekMapper nieuwOnderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon, maakPartij());
        nieuwOnderzoekMapper.mapOnderzoek(nieuwPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE);
        onderzoekDeltaProces.verwerkVerschillen(context);

        assertTrue(context.isBijhoudingOverig());
    }

    @Test
    public void testOnderzoekOpAdministratiefGegeven() throws ReflectiveOperationException {
        final PersoonIndicatieHistorie nieuwPersoonIndicatieHistorie = maakPersoonIndicatie(nieuwPersoon);
        nieuwPersoonIndicatieHistorie.setDatumTijdVerval(timestamp);

        final Partij partij = maakPartij();
        final BRPActie brpActie = maakBrpActie(administratieveHandeling, timestamp);
        nieuwPersoonIndicatieHistorie.setActieInhoud(brpActie);
        final Document document = new Document(new SoortDocument("test", "test"), partij);
        final ActieBron actieBron = new ActieBron(brpActie);
        actieBron.setDocument(document);
        document.addActieBron(actieBron);
        brpActie.addActieBron(actieBron);

        final BrpPartijCode partijCode = new BrpPartijCode("000001", lo3Onderzoek);

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon, partij);
        onderzoekMapper.mapOnderzoek(document, partijCode, Element.DOCUMENT_PARTIJCODE);
        final Onderzoek nieuwOnderzoek = (Onderzoek) onderzoekMapper.getOnderzoekSet().toArray()[0];
        onderzoekDeltaProces.verwerkVerschillen(context);

        controleerGekoppeldOnderzoek(nieuwOnderzoek);
        assertTrue(context.isBijhoudingOverig());
    }

    @Test
    public void testBepaalVerschillen() {
        assertTrue(context.getDeltaEntiteitPaarSet().isEmpty());
        onderzoekDeltaProces.bepaalVerschillen(context);
        assertTrue(context.getDeltaEntiteitPaarSet().isEmpty());
    }

    private PersoonIndicatieHistorie maakPersoonIndicatie(final Persoon persoon) throws ReflectiveOperationException {
        final PersoonIndicatie indicatie = new PersoonIndicatie(persoon, SoortIndicatie.ONDER_CURATELE);
        indicatie.setId((long) 1);

        final PersoonIndicatieHistorie historie = new PersoonIndicatieHistorie(indicatie, trueWaarde.getWaarde());
        historie.setId((long) 1);
        historie.setDatumTijdRegistratie(timestamp);

        indicatie.addPersoonIndicatieHistorie(historie);
        persoon.addPersoonIndicatie(indicatie);
        return historie;
    }

    private void controleerGekoppeldOnderzoek(final Onderzoek onderzoek) {
        if (onderzoek == null) {
            assertEquals(0, bestaandPersoon.getOnderzoeken().size());
        } else {
            assertEquals(1, bestaandPersoon.getOnderzoeken().size());
            final Onderzoek gekoppeldOnderzoek = bestaandPersoon.getOnderzoeken().iterator().next();
            assertEquals(onderzoek, gekoppeldOnderzoek);

            assertTrue(onderzoek.getOnderzoekHistorieSet().size() > 0);
            final Persoon gekoppeldePersoon = onderzoek.getPersoon();
            assertEquals(bestaandPersoon, gekoppeldePersoon);

            assertNotNull(onderzoek.getPartij());
            final Partij gekoppeldePartij = onderzoek.getPartij();
            assertEquals(partij.getCode(), gekoppeldePartij.getCode());
        }
    }

    private void controleerOnderzoek(final Onderzoek onderzoek) {

        assertEquals(1, onderzoek.getOnderzoekHistorieSet().size());
        final OnderzoekHistorie onderzoekHistorie = onderzoek.getOnderzoekHistorieSet().iterator().next();
        assertNull(onderzoek.getOmschrijving());
        assertNull(onderzoek.getDatumAanvang());
        assertNull(onderzoek.getDatumEinde());
        assertNull(onderzoek.getStatusOnderzoek());

        assertNotNull(onderzoekHistorie.getOmschrijving());
        assertNotNull(onderzoekHistorie.getDatumAanvang());
        assertNotNull(onderzoekHistorie.getStatusOnderzoek());
        assertNotNull(onderzoekHistorie.getActieInhoud());
        assertNull(onderzoekHistorie.getDatumEinde());

        assertNull(onderzoekHistorie.getIndicatieVoorkomenTbvLeveringMutaties());
        assertNull(onderzoekHistorie.getActieVervalTbvLeveringMutaties());
    }

    public Partij maakPartij() {
        return new Partij("Gemeente 's-Gravenhage", "051801");
    }

}
