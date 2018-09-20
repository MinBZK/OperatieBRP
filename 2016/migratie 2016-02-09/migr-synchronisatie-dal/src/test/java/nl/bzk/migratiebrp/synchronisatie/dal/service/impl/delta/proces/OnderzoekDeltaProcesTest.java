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

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.ActieBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteitPaar;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Document;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.GegevenInOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.OnderzoekHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StatusOnderzoek;
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

        bestaandPersoon = maakPersoon(true);
        nieuwPersoon = maakPersoon(false);
        bestaandPersoon.getPersoonSamengesteldeNaamHistorieSet().add(maakPersoonSamengesteldeNaamHistorie(bestaandPersoon, false));
        nieuwPersoon.getPersoonSamengesteldeNaamHistorieSet().add(maakPersoonSamengesteldeNaamHistorie(nieuwPersoon, false));

        partij = maakPartij();
        administratieveHandeling = new AdministratieveHandeling(partij, SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
        administratieveHandeling.setDatumTijdRegistratie(timestamp);

        bestaandPersoon.setAdministratieveHandeling(administratieveHandeling);
        bestaandPersoon.setDatumtijdstempel(timestamp);
        nieuwPersoon.setDatumtijdstempel(timestamp2);

        nieuwPersoon.setAdministratieveHandeling(administratieveHandeling);
        bestaandPersoon.setBijhoudingspartij(partij);
        onderzoekDeltaProces = new OnderzoekDeltaProces();

        context = new DeltaBepalingContext(nieuwPersoon, bestaandPersoon, null, false);

        lo3Herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0);
        lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(LO3_ONDERZOEK, null), new Lo3Datum(ONDERZOEK_START_DATUM), null, lo3Herkomst);

        trueWaarde = new BrpBoolean(true, lo3Onderzoek);
        SynchronisatieLogging.init();
    }

    @Test
    public void testToevoegenOnderzoekAanPersoon() throws ReflectiveOperationException {
        controleerOngekoppeldOnderzoek(null);
        final PersoonIndicatieHistorie indicatieHistorie = maakPersoonIndicatie(bestaandPersoon);

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon);
        onderzoekMapper.mapOnderzoek(indicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE);
        final Onderzoek onderzoek = onderzoekMapper.getOnderzoekSet().iterator().next();

        onderzoekDeltaProces.verwerkVerschillen(context);

        controleerGekoppeldOnderzoek(onderzoek);
        controleerOnderzoek(onderzoek);
    }

    @Test
    public void testDeltaGeenOnderzoek() {
        controleerOngekoppeldOnderzoek(null);

        onderzoekDeltaProces.verwerkVerschillen(context);
        // Er waren geen onderzoeken dus persoon moet nog steeds geen onderzoeken bevatten
        controleerOngekoppeldOnderzoek(null);
    }

    @Test
    public void testDeltaOnderzoekToegevoegdNietBestaandVoorkomen() throws ReflectiveOperationException {
        controleerOngekoppeldOnderzoek(null);

        final PersoonIndicatieHistorie indicatieHistorie = maakPersoonIndicatie(bestaandPersoon);

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon);
        onderzoekMapper.mapOnderzoek(indicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE);
        final Set<Onderzoek> onderzoeken = onderzoekMapper.getOnderzoekSet();
        assertEquals(1, onderzoeken.size());

        onderzoekDeltaProces.verwerkVerschillen(context);
        final Onderzoek onderzoek = onderzoeken.iterator().next();
        controleerGekoppeldOnderzoek(onderzoek);
        controleerOnderzoek(onderzoek);
    }

    @Test
    public void testDeltaOnderzoekToegevoegdBestaandVoorkomen() throws ReflectiveOperationException {
        controleerOngekoppeldOnderzoek(null);

        final PersoonIndicatieHistorie bestaandPersoonIndicatieHistorie = maakPersoonIndicatie(bestaandPersoon);
        final PersoonIndicatieHistorie nieuwPersoonIndicatieHistorie = maakPersoonIndicatie(nieuwPersoon);

        context.addDeltaEntiteitPaarSetInhoud(
            Collections.singletonList(new DeltaEntiteitPaar(bestaandPersoonIndicatieHistorie, nieuwPersoonIndicatieHistorie)));

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon);
        onderzoekMapper.mapOnderzoek(nieuwPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE);
        final Set<Onderzoek> onderzoeken = onderzoekMapper.getOnderzoekSet();
        assertEquals(1, onderzoeken.size());

        onderzoekDeltaProces.verwerkVerschillen(context);
        final Onderzoek gekoppeldOnderzoek = onderzoeken.iterator().next();
        controleerGekoppeldOnderzoek(gekoppeldOnderzoek);
        controleerOnderzoek(gekoppeldOnderzoek);
        assertEquals(1, gekoppeldOnderzoek.getGegevenInOnderzoekSet().size());

        final GegevenInOnderzoek gegevenInOnderzoek = gekoppeldOnderzoek.getGegevenInOnderzoekSet().iterator().next();
        final DeltaEntiteit entiteit = gegevenInOnderzoek.getObjectOfVoorkomen();
        assertNotSame(nieuwPersoonIndicatieHistorie, entiteit);
        assertSame(bestaandPersoonIndicatieHistorie, entiteit);
    }

    @Test
    public void testDeltaOnderzoekToegevoegdBestaandVoorkomenALaag() throws ReflectiveOperationException {
        controleerOngekoppeldOnderzoek(null);

        final PersoonIndicatieHistorie bestaandPersoonIndicatieHistorie = maakPersoonIndicatie(bestaandPersoon);
        final PersoonIndicatieHistorie nieuwPersoonIndicatieHistorie = maakPersoonIndicatie(nieuwPersoon);
        final PersoonIndicatie nieuwPersoonIndicatie = nieuwPersoonIndicatieHistorie.getPersoonIndicatie();
        final PersoonIndicatie bestaandPersoonIndicatie = bestaandPersoonIndicatieHistorie.getPersoonIndicatie();

        context.addDeltaEntiteitPaarSetInhoud(
                Collections.singletonList(new DeltaEntiteitPaar(bestaandPersoonIndicatieHistorie, nieuwPersoonIndicatieHistorie)));
        context.addDeltaEntiteitPaarSetInhoud(
                Collections.singletonList(new DeltaEntiteitPaar(bestaandPersoonIndicatie, nieuwPersoonIndicatie)));

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon);
        onderzoekMapper.mapOnderzoek(nieuwPersoonIndicatie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE);
        final Set<Onderzoek> onderzoeken = onderzoekMapper.getOnderzoekSet();
        assertEquals(1, onderzoeken.size());

        onderzoekDeltaProces.verwerkVerschillen(context);
        final Onderzoek gekoppeldOnderzoek = onderzoeken.iterator().next();
        controleerGekoppeldOnderzoek(gekoppeldOnderzoek);
        controleerOnderzoek(gekoppeldOnderzoek);
        assertEquals(1, gekoppeldOnderzoek.getGegevenInOnderzoekSet().size());

        final GegevenInOnderzoek gegevenInOnderzoek = gekoppeldOnderzoek.getGegevenInOnderzoekSet().iterator().next();
        final DeltaEntiteit entiteit = gegevenInOnderzoek.getObjectOfVoorkomen();
        assertNotSame(nieuwPersoonIndicatie, entiteit);
        assertSame(bestaandPersoonIndicatie, entiteit);
    }

    @Test
    public void testDeltaOnderzoekVerwijderd() throws ReflectiveOperationException {
        controleerOngekoppeldOnderzoek(null);

        final PersoonIndicatieHistorie bestaandPersoonIndicatieHistorie = maakPersoonIndicatie(bestaandPersoon);

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(bestaandPersoon);
        onderzoekMapper.mapOnderzoek(bestaandPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE);
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
        controleerOngekoppeldOnderzoek(null);
        final PersoonIndicatieHistorie bestaandPersoonIndicatieHistorie = maakPersoonIndicatie(bestaandPersoon);
        final PersoonIndicatieHistorie nieuwPersoonIndicatieHistorie = maakPersoonIndicatie(nieuwPersoon);

        context.addDeltaEntiteitPaarSetInhoud(
                Collections.singletonList(new DeltaEntiteitPaar(bestaandPersoonIndicatieHistorie, nieuwPersoonIndicatieHistorie)));

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(bestaandPersoon);
        onderzoekMapper.mapOnderzoek(bestaandPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE);
        final Onderzoek bestaandOnderzoek = onderzoekMapper.getOnderzoekSet().iterator().next();
        controleerGekoppeldOnderzoek(bestaandOnderzoek);

        final Integer onderzoekEinddatum = 20150102;
        final BrpBoolean trueWaardeGeslotenOnderzoek =
                new BrpBoolean(
                    true,
                    new Lo3Onderzoek(
                        new Lo3Integer(LO3_ONDERZOEK, null),
                        new Lo3Datum(ONDERZOEK_START_DATUM),
                        new Lo3Datum(onderzoekEinddatum),
                        lo3Herkomst));

        final OnderzoekMapper nieuwOnderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon);
        nieuwOnderzoekMapper.mapOnderzoek(nieuwPersoonIndicatieHistorie, trueWaardeGeslotenOnderzoek, Element.PERSOON_INDICATIE_ONDERCURATELE);
        onderzoekDeltaProces.verwerkVerschillen(context);

        controleerGekoppeldOnderzoek(bestaandOnderzoek);
        assertEquals(onderzoekEinddatum, bestaandOnderzoek.getDatumEinde());
        assertEquals(StatusOnderzoek.AFGESLOTEN, bestaandOnderzoek.getStatusOnderzoek());
        assertEquals(2, bestaandOnderzoek.getOnderzoekHistorieSet().size());
        assertEquals(2, bestaandOnderzoek.getOnderzoekAfgeleidAdministratiefHistorieSet().size());
    }

    @Test
    public void testOnderzoekOngewijzigd() throws ReflectiveOperationException {
        final PersoonIndicatieHistorie bestaandPersoonIndicatieHistorie = maakPersoonIndicatie(bestaandPersoon);
        final PersoonIndicatieHistorie nieuwPersoonIndicatieHistorie = maakPersoonIndicatie(nieuwPersoon);

        context.addDeltaEntiteitPaarSetInhoud(
                Collections.singletonList(new DeltaEntiteitPaar(bestaandPersoonIndicatieHistorie, nieuwPersoonIndicatieHistorie)));

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(bestaandPersoon);
        onderzoekMapper.mapOnderzoek(bestaandPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE);
        final Onderzoek bestaandOnderzoek = onderzoekMapper.getOnderzoekSet().iterator().next();

        controleerGekoppeldOnderzoek(bestaandOnderzoek);

        final OnderzoekMapper nieuwOnderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon);
        nieuwOnderzoekMapper.mapOnderzoek(nieuwPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE);
        onderzoekDeltaProces.verwerkVerschillen(context);

        controleerGekoppeldOnderzoek(bestaandOnderzoek);
    }

    @Test
    public void testOnderzoekOpBrpNietActueel() throws ReflectiveOperationException {
        final PersoonIndicatieHistorie bestaandPersoonIndicatieHistorie = maakPersoonIndicatie(bestaandPersoon);
        final PersoonIndicatieHistorie nieuwPersoonIndicatieHistorie = maakPersoonIndicatie(nieuwPersoon);
        nieuwPersoonIndicatieHistorie.setDatumTijdVerval(timestamp);

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(bestaandPersoon);
        onderzoekMapper.mapOnderzoek(bestaandPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE);
        final Onderzoek bestaandOnderzoek = onderzoekMapper.getOnderzoekSet().iterator().next();

        controleerGekoppeldOnderzoek(bestaandOnderzoek);

        OnderzoekMapper nieuwOnderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon);
        nieuwOnderzoekMapper.mapOnderzoek(nieuwPersoonIndicatieHistorie, trueWaarde, Element.PERSOON_INDICATIE_ONDERCURATELE);
        onderzoekDeltaProces.verwerkVerschillen(context);

        assertTrue(context.isBijhoudingOverig());
    }

    @Test
    public void testOnderzoekOpAdministratiefGegeven() throws ReflectiveOperationException {
        final PersoonIndicatieHistorie nieuwPersoonIndicatieHistorie = maakPersoonIndicatie(nieuwPersoon);
        nieuwPersoonIndicatieHistorie.setDatumTijdVerval(timestamp);

        final BRPActie brpActie = maakBrpActie(administratieveHandeling, timestamp);
        nieuwPersoonIndicatieHistorie.setActieInhoud(brpActie);
        final Document document = new Document(new SoortDocument("test", "test"));
        final ActieBron actieBron = new ActieBron(brpActie);
        actieBron.setDocument(document);
        document.addActieBron(actieBron);
        brpActie.addActieBron(actieBron);

        final BrpPartijCode partijCode = new BrpPartijCode(1, lo3Onderzoek);

        OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(nieuwPersoon);
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
        indicatie.setId(1);

        final PersoonIndicatieHistorie historie = new PersoonIndicatieHistorie(indicatie, trueWaarde.getWaarde());
        historie.setId(1);
        historie.setDatumTijdRegistratie(timestamp);

        indicatie.addPersoonIndicatieHistorie(historie);
        persoon.addPersoonIndicatie(indicatie);
        return historie;
    }

    private void controleerGekoppeldOnderzoek(final Onderzoek onderzoek) {
        if (onderzoek == null) {
            assertEquals(0, bestaandPersoon.getPersoonOnderzoekSet().size());
        } else {
            assertEquals(1, bestaandPersoon.getPersoonOnderzoekSet().size());
            final Onderzoek gekoppeldOnderzoek = bestaandPersoon.getPersoonOnderzoekSet().iterator().next().getOnderzoek();
            assertEquals(onderzoek, gekoppeldOnderzoek);

            assertEquals(1, onderzoek.getPersoonOnderzoekSet().size());
            final Persoon gekoppeldePersoon = onderzoek.getPersoonOnderzoekSet().iterator().next().getPersoon();
            assertEquals(bestaandPersoon, gekoppeldePersoon);

            assertEquals(1, onderzoek.getPartijOnderzoekSet().size());
            final Partij gekoppeldePartij = onderzoek.getPartijOnderzoekSet().iterator().next().getPartij();
            assertEquals(partij, gekoppeldePartij);
        }
    }

    private void controleerOnderzoek(final Onderzoek onderzoek) {
        assertEquals(1, onderzoek.getOnderzoekAfgeleidAdministratiefHistorieSet().size());

        assertEquals(1, onderzoek.getOnderzoekHistorieSet().size());
        final OnderzoekHistorie onderzoekHistorie = onderzoek.getOnderzoekHistorieSet().iterator().next();
        assertEquals(onderzoek.getOmschrijving(), onderzoekHistorie.getOmschrijving());
        assertEquals(onderzoek.getDatumAanvang(), onderzoekHistorie.getDatumAanvang());
        assertEquals(onderzoek.getDatumEinde(), onderzoekHistorie.getDatumEinde());
        assertEquals(onderzoek.getVerwachteAfhandelDatum(), onderzoekHistorie.getVerwachteAfhandelDatum());
        assertEquals(onderzoek.getStatusOnderzoek(), onderzoekHistorie.getStatusOnderzoek());
        assertNotNull(onderzoekHistorie.getActieInhoud());
        assertNull(onderzoekHistorie.getIndicatieVoorkomenTbvLeveringMutaties());
        assertNull(onderzoekHistorie.getActieVervalTbvLeveringMutaties());
    }

    private void controleerOngekoppeldOnderzoek(final Onderzoek onderzoek) {
        assertTrue(bestaandPersoon.getPersoonOnderzoekSet().isEmpty());
        if (onderzoek != null) {
            assertTrue(onderzoek.getPersoonOnderzoekSet().isEmpty());
            assertTrue(onderzoek.getOnderzoekAfgeleidAdministratiefHistorieSet().isEmpty());
            assertTrue(onderzoek.getOnderzoekHistorieSet().isEmpty());
            assertTrue(onderzoek.getPartijOnderzoekSet().isEmpty());
        }
    }
}
