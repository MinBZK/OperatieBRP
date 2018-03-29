/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Iterables;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelAfdruk;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class PersoonslijstBeeldFactoryTest {

    private final Persoonslijst plNuNu = maakPersoonMetHandelingen(1);

    @Test
    public void testAdministratievehandelingId() {
        Assert.assertTrue(plNuNu.beeldVan().administratievehandeling(3) == plNuNu);

        final Persoonslijst pl3 = plNuNu.beeldVan().administratievehandeling(3);
        Assert.assertEquals(3, pl3.getAdministratieveHandeling().getId().intValue());
        final Persoonslijst pl2 = plNuNu.beeldVan().administratievehandeling(2);
        Assert.assertEquals(2, pl2.getAdministratieveHandeling().getId().intValue());
        final Persoonslijst pl1 = plNuNu.beeldVan().administratievehandeling(1);
        assertEquals(1, pl1.getAdministratieveHandeling().getId().intValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNietBestaandAdministratievehandelingId() {
        plNuNu.beeldVan().administratievehandeling(99);
    }

    @Test
    public void testVorigeHandeling() {

        final Persoonslijst plTweede = plNuNu.beeldVan().vorigeHandeling();
        final Persoonslijst plEerste = plTweede.beeldVan().vorigeHandeling();
        final Persoonslijst plNietBestaand = plEerste.beeldVan().vorigeHandeling();

        assertEquals(3, plNuNu.getAdministratieveHandeling().getId().intValue());
        assertEquals(2, plTweede.getAdministratieveHandeling().getId().intValue());
        assertEquals(1, plEerste.getAdministratieveHandeling().getId().intValue());
        assertNull(plNietBestaand);

        assertEquals(3, plNuNu.getAdministratieveHandelingen().size());
        assertEquals(2, plTweede.getAdministratieveHandelingen().size());
        assertEquals(1, plEerste.getAdministratieveHandelingen().size());

        System.out.println("handeling1:");
        System.out.println(ModelAfdruk.maakAfdruk(plNuNu.getMetaObject()));

        System.out.println("\nhandeling2");
        System.out.println(ModelAfdruk.maakAfdruk(plTweede.getMetaObject()));

        System.out.println("\nhandeling3");
        System.out.println(ModelAfdruk.maakAfdruk(plEerste.getMetaObject()));
    }

    @Test
    public void testFormeelPuntOpTsRegLaatsteHandeling() {
        final Persoonslijst persoonslijst = plNuNu.beeldVan()
                .formeelPeilmoment(plNuNu.getAdministratieveHandeling().getTijdstipRegistratie());
        assertTrue(persoonslijst == plNuNu);
    }

    @Test
    public void testFormeelPuntTussenLaatsteEnVoorlaatsteHandeling() {
        final Persoonslijst persoonslijst = plNuNu.beeldVan()
                .formeelPeilmoment(plNuNu.getAdministratieveHandeling().getTijdstipRegistratie().minusDays(1));
        assertFalse(persoonslijst == plNuNu);
        assertEquals(2, persoonslijst.getAdministratieveHandeling().getId().intValue());
    }

    @Test
    public void testToekomstigFormeelPunt() {
        final Persoonslijst persoonslijst = plNuNu.beeldVan()
                .formeelPeilmoment(DatumUtil.nuAlsZonedDateTime());
        assertTrue(plNuNu == persoonslijst);
    }

    @Test(expected = IllegalStateException.class)
    public void testNietBestaandFormeelPunt() {
        final Persoonslijst persoonslijst = plNuNu.beeldVan()
                .formeelPeilmoment(DatumUtil.nuAlsZonedDateTime().minusYears(10));
    }

    @Test
    public void testNuNu() {
        Assert.assertTrue(plNuNu.isNuNuBeeld());
        final Persoonslijst persoonslijst = plNuNu.beeldVan().vorigeHandeling();
        Assert.assertNotNull(persoonslijst);
        Assert.assertFalse(persoonslijst.isNuNuBeeld());
        Assert.assertTrue(persoonslijst.getNuNuBeeld() == plNuNu);
    }

    @Test
    public void testFormeelActueel() {
        assertEquals(111, plNuNu.<Number>getActueleAttribuutWaarde(ElementHelper
                .getAttribuutElement(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE)).orElse(0).intValue());
        assertEquals("000003", plNuNu.<String>getActueleAttribuutWaarde(ElementHelper
                .getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE)).orElse("0"));
        final Persoonslijst plTweede = plNuNu.beeldVan().vorigeHandeling();
        assertEquals(222, plTweede.<Number>getActueleAttribuutWaarde(ElementHelper
                .getAttribuutElement(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE)).orElse(0).intValue());
        assertEquals("000003", plTweede.<String>getActueleAttribuutWaarde(ElementHelper
                .getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE)).orElse("0"));
        final Persoonslijst plEerste = plTweede.beeldVan().vorigeHandeling();
        assertEquals(333, plEerste.<Number>getActueleAttribuutWaarde(ElementHelper
                .getAttribuutElement(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE)).orElse(0).intValue());
        assertEquals("000003", plEerste.<String>getActueleAttribuutWaarde(ElementHelper
                .getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE)).orElse("0"));

    }

    @Test
    public void testOpschonenTsVervalBijFormeelTerugreizen() {
        final Persoonslijst pl2 = plNuNu.beeldVan().vorigeHandeling();
        final MetaGroep geboorteGroep = Iterables
                .getOnlyElement(pl2.getModelIndex().geefGroepenVanElement(ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE.getId())));
        final MetaRecord actueleRecord = pl2.getActueleRecord(geboorteGroep).orElseThrow(IllegalStateException::new);
        Assert.assertNull(actueleRecord.getActieVerval());
    }

    @Test
    public void testHisLaatsteFormeelBestaatNiet() {
        final MetaGroep geboorteGroep = Iterables
                .getOnlyElement(plNuNu.getModelIndex().geefGroepenVanElement(
                        ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE.getId())));
        final Optional<MetaRecord> materieelLaatsteHistorieRecord = plNuNu.getMaterieelLaatsteHistorieRecord(geboorteGroep);
        Assert.assertFalse(materieelLaatsteHistorieRecord.isPresent());
    }

    @Test
    public void testMaterieelActueel() {
        Assert.assertEquals("Soest", plNuNu.<String>getActueleAttribuutWaarde(ElementHelper
                .getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM)).orElse(null));
        final Persoonslijst plTweede = plNuNu.beeldVan().vorigeHandeling();
        assertEquals("Rijswijk", plTweede.<String>getActueleAttribuutWaarde(ElementHelper
                .getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM)).orElse(null));
        final Persoonslijst plEerste = plTweede.beeldVan().vorigeHandeling();
        Assert.assertEquals("Purmerend", plEerste.<String>getActueleAttribuutWaarde(ElementHelper
                .getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM)).orElse(null));
    }

    @Test
    public void testHisLaatste() {

        //his laatste van laatste handeling
        {
            final MetaGroep geboorteGroep = Iterables
                    .getOnlyElement(plNuNu.getModelIndex().geefGroepenVanElement(
                            ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())));
            final Optional<MetaRecord> materieelLaatsteHistorieRecord = plNuNu.getMaterieelLaatsteHistorieRecord(geboorteGroep);
            assertTrue(materieelLaatsteHistorieRecord.isPresent());
            assertEquals(4, materieelLaatsteHistorieRecord.get().getVoorkomensleutel());
            assertNotNull(materieelLaatsteHistorieRecord.get().getActieAanpassingGeldigheid());
        }
        //his laatste van 2e handeling
        {
            final Persoonslijst persoonslijst = plNuNu.beeldVan().vorigeHandeling();
            final MetaGroep geboorteGroep = Iterables
                    .getOnlyElement(persoonslijst.getModelIndex().geefGroepenVanElement(
                            ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())));
            final Optional<MetaRecord> materieelLaatsteHistorieRecord = persoonslijst.getMaterieelLaatsteHistorieRecord(geboorteGroep);
            assertTrue(materieelLaatsteHistorieRecord.isPresent());
            assertEquals(2, materieelLaatsteHistorieRecord.get().getVoorkomensleutel());
            assertNotNull(materieelLaatsteHistorieRecord.get().getActieAanpassingGeldigheid());
        }
        //his laatste van 1e handeling
        {
            final Persoonslijst persoonslijst = plNuNu.beeldVan().vorigeHandeling().beeldVan().vorigeHandeling();
            final MetaGroep geboorteGroep = Iterables
                    .getOnlyElement(persoonslijst.getModelIndex().geefGroepenVanElement(
                            ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())));
            final Optional<MetaRecord> materieelLaatsteHistorieRecord = persoonslijst.getMaterieelLaatsteHistorieRecord(geboorteGroep);
            assertFalse(materieelLaatsteHistorieRecord.isPresent());
        }
    }

    @Test
    public void testMaterieelPeilmomentGelijkAanTsRegHandeling() {

        final int materieelPeilMoment = DatumUtil.vanDatumNaarInteger(plNuNu.getAdministratieveHandeling().getTijdstipRegistratie().toLocalDate());
        final Persoonslijst persoonslijst = plNuNu.beeldVan().materieelPeilmoment(materieelPeilMoment);

        assertTrue(persoonslijst != plNuNu);

        assertTrue(plNuNu.getMetaObject().deepEquals(plNuNu.beeldVan().filter(filter -> {}).getMetaObject()));
        assertTrue(persoonslijst.getMetaObject().deepEquals(plNuNu.getMetaObject()));

        assertEquals("Soest", plNuNu.<String>getActueleAttribuutWaarde(ElementHelper
                .getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM)).orElse(null));
    }

    @Test
    public void testMaterieelPeilmomentVerschuivingActueel() {
        {
            final int materieelPeilMoment = DatumUtil.vanDatumNaarInteger(plNuNu.getAdministratieveHandeling()
                    .getTijdstipRegistratie().minusMonths(1).toLocalDate());
            final Persoonslijst persoonslijst = plNuNu.beeldVan().materieelPeilmoment(materieelPeilMoment);
            assertEquals("Rijswijk", persoonslijst.<String>getActueleAttribuutWaarde(ElementHelper
                    .getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM)).orElse(null));
        }
        {
            final int materieelPeilMoment = DatumUtil.vanDatumNaarInteger(plNuNu.getAdministratieveHandeling()
                    .getTijdstipRegistratie().minusYears(1).minusMonths(1).toLocalDate());
            final Persoonslijst persoonslijst = plNuNu.beeldVan().materieelPeilmoment(materieelPeilMoment);
            assertEquals("Purmerend", persoonslijst.<String>getActueleAttribuutWaarde(ElementHelper
                    .getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM)).orElse(null));
        }
    }

    /**
     * Test dat actieverval wordt verwijderd indien actievervalTbvLevMuts bestaat EN in toekomstige handeling zit, maar actieverval NIET wordt verwijderd
     * indien actievervalTbvLevMuts bestaat maar niet in toekomstige handeling zit.
     */
    @Test
    public void testActieVervalVerwijderen() {

        Persoonslijst pl = TestBuilders.PERSOON_MET_MATERIELE_EN_FORMELE_MUTLEV_HISTORIE;
        final Set<Long> recordIdMetActieVerval = pl.getModelIndex().geefAttributen(Element.PERSOON_ADRES_ACTIEVERVALTBVLEVERINGMUTATIES)
                .stream().map(MetaAttribuut::getParentRecord)
                .filter(metaRecord -> metaRecord.getActieVerval() != null)
                .map(MetaRecord::getVoorkomensleutel)
                .collect(Collectors.toSet());
        assertEquals(2, recordIdMetActieVerval.size());

        pl = pl.beeldVan().vorigeHandeling();
        {
            System.out.println(pl.toStringVolledig());
            final MetaGroep groep = pl.getModelIndex().geefGroepenVanElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD))
                    .stream().findFirst().orElseThrow(AssertionError::new);
            final MetaRecord actueleRecord = pl.getActueleRecord(groep).orElseThrow(IllegalStateException::new);
            Assert.assertNull(actueleRecord.getActieVerval());
            Assert.assertNotNull(actueleRecord.getActieVervalTbvLeveringMutaties());
            final Set<MetaRecord> geboorteGroepRecords =
                    Iterables.getOnlyElement(pl.getModelIndex().geefGroepenVanElement(ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE))).getRecords();
            final MetaRecord recordActieTbvLevMutNietInToekomstigeHnd = geboorteGroepRecords.stream()
                    .filter(metaRecord -> metaRecord.getVoorkomensleutel() == 3).findAny().orElseThrow(IllegalStateException::new);
            Assert.assertNotNull(recordActieTbvLevMutNietInToekomstigeHnd.getActieVerval());
            Assert.assertNotNull(recordActieTbvLevMutNietInToekomstigeHnd.getActieVervalTbvLeveringMutaties());

        }
        pl = pl.beeldVan().vorigeHandeling();
        {
            final MetaGroep groep = pl.getModelIndex().geefGroepenVanElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD))
                    .stream().findFirst().orElseThrow(AssertionError::new);
            final MetaRecord actueleRecord = pl.getActueleRecord(groep).orElseThrow(IllegalStateException::new);
            Assert.assertNull(actueleRecord.getActieVerval());
            Assert.assertNotNull(actueleRecord.getActieVervalTbvLeveringMutaties());
        }
    }

    /**
     * Test dat actieverval NIET wordt verwijderd indien actievervalTbvLevMuts bestaat maar niet in toekomstige handeling zit.
     */
    @Test
    public void testActieVervalNietVerwijderen_ActieVervalTbvLevMutsNietInToekomstigeHnd() {

        Persoonslijst pl = TestBuilders.PERSOON_MET_MATERIELE_EN_FORMELE_MUTLEV_HISTORIE;
        final Set<Long> recordIdMetMutlev = pl.getModelIndex().geefAttributen(Element.PERSOON_ADRES_ACTIEVERVALTBVLEVERINGMUTATIES)
                .stream().map(MetaAttribuut::getParentRecord)
                .filter(metaRecord -> metaRecord.getActieVerval() != null)
                .map(MetaRecord::getVoorkomensleutel)
                .collect(Collectors.toSet());
        assertEquals(2, recordIdMetMutlev.size());

        pl = pl.beeldVan().vorigeHandeling();
        {
            final MetaGroep groep = pl.getModelIndex().geefGroepenVanElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD))
                    .stream().findFirst().orElseThrow(AssertionError::new);
            final MetaRecord actueleRecord = pl.getActueleRecord(groep).orElseThrow(IllegalStateException::new);
            Assert.assertNotNull(actueleRecord.getActieVervalTbvLeveringMutaties());
        }
        pl = pl.beeldVan().vorigeHandeling();
        {
            final MetaGroep groep = pl.getModelIndex().geefGroepenVanElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD))
                    .stream().findFirst().orElseThrow(AssertionError::new);
            final MetaRecord actueleRecord = pl.getActueleRecord(groep).orElseThrow(IllegalStateException::new);
            Assert.assertNotNull(actueleRecord.getActieVervalTbvLeveringMutaties());
        }
    }

    public static Persoonslijst maakPersoonMetHandelingen(int persId) {

        final ZonedDateTime time = DatumUtil.nuAlsZonedDateTime();
        final AdministratieveHandeling ah1 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(3, "000123", time, SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        final AdministratieveHandeling ah2 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(2, "000123", time.minusYears(1), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(2, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        final AdministratieveHandeling ah3 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", time.minusYears(2), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(3, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
            .metId(persId)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord()
                    .metId(1)
                    .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                    .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 111)
                .eindeRecord()
                .metRecord()
                    .metId(2)
                    .metActieInhoud(Iterables.getOnlyElement(ah2.getActies()))
                    .metActieVerval(Iterables.getOnlyElement(ah1.getActies()))
                    .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 222)
                .eindeRecord()
                .metRecord()
                    .metId(3)
                    .metActieInhoud(Iterables.getOnlyElement(ah3.getActies()))
                    .metActieVerval(Iterables.getOnlyElement(ah2.getActies()))
                    .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 333)
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId())
                    .metRecord()
                        .metId(0)
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(5)
                        .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah1.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Soest")
                    .eindeRecord()
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieAanpassingGeldigheid(Iterables.getOnlyElement(ah1.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah2.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metDatumEindeGeldigheid(DatumUtil.vanDatumNaarInteger(ah1.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Rijswijk")
                    .eindeRecord()
                    .metRecord()
                        .metId(3)
                        .metActieInhoud(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieVerval(Iterables.getOnlyElement(ah1.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah2.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Rijswijk")
                    .eindeRecord()
                    .metRecord()
                        .metId(2)
                        .metActieAanpassingGeldigheid(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieInhoud(Iterables.getOnlyElement(ah3.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah3.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metDatumEindeGeldigheid(DatumUtil.vanDatumNaarInteger(ah2.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Purmerend")
                    .eindeRecord()
                    .metRecord()
                        .metId(1)
                        .metActieVerval(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieInhoud(Iterables.getOnlyElement(ah3.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah3.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Purmerend")
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
            .metObjectElement(Element.PERSOON_AFNEMERINDICATIE.getId())
            .metId(1)
            .metGroep()
                .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT.getId())
                    .metRecord()
                        .metId(2)
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE.getId(), 1)
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE.getId(), "000003")
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PERSOON.getId(), 223)
                    .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId())
                    .metRecord()
                        .metId(2)
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE.getId(), 10102001)
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD.getId(), 3)
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId(), DatumUtil.nuAlsZonedDateTime())
                    .eindeRecord()
                    .metRecord()
                        .metId(3)
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE.getId(), 10102001)
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD.getId(), 3)
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId(), DatumUtil.nuAlsZonedDateTime())
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTVERVAL.getId(), 3)
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL.getId(), DatumUtil.nuAlsZonedDateTime())
                    .eindeRecord()
            .eindeGroep()
        .eindeObject()
        .build();
        //@formatter:on

        return new Persoonslijst(persoonMeta, 0L);
    }
}
