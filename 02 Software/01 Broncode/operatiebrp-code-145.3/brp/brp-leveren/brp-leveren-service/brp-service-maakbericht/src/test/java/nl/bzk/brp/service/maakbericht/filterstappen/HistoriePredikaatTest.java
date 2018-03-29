/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.PeilmomentHistorievormPredicate;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * BevragingHistoriePredikaatTest.
 */
public class HistoriePredikaatTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Test
    public void applyMaterieelFormeelGeldigHistorieVormGeen_PeilmomentNaTsRegEnVoorDatumEinde() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        final Actie actieInhoud = TestVerantwoording.maakActie(1, actieInhoudTsReg);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metActieInhoud(actieInhoud);
        recordBuilder.metDatumAanvangGeldigheid(maakDatumInteger(jaarTsReg, maandTsReg, dagTsReg));
        recordBuilder.metDatumEindeGeldigheid(maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg));
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(jaarTsReg, maandTsReg + 6, dagTsReg);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate bevragingHistoriePredikaat = new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel,
                HistorieVorm.GEEN);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(true));
    }


    @Test
    public void applyMaterieelFormeelGeldigHistorieVormGeen_MaterieelPeilmomentVoorDatumAanvang() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        final Actie actieInhoud = TestVerantwoording.maakActie(1, actieInhoudTsReg);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metActieInhoud(actieInhoud);
        recordBuilder.metDatumAanvangGeldigheid(maakDatumInteger(jaarTsReg, maandTsReg, dagTsReg));
        recordBuilder.metDatumEindeGeldigheid(maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg));
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(2013, 6, dagTsReg);
        final ZonedDateTime peilDatumFormeel = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);
        final PeilmomentHistorievormPredicate bevragingHistoriePredikaat = new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel,
                HistorieVorm.GEEN);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(false));
    }

    @Test
    public void applyMaterieelFormeelGeldigHistorieVormGeen_PeilmomentVoorTsReg() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        final Actie actieInhoud = TestVerantwoording.maakActie(1, actieInhoudTsReg);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metActieInhoud(actieInhoud);
        recordBuilder.metDatumAanvangGeldigheid(maakDatumInteger(jaarTsReg, maandTsReg, dagTsReg));
        recordBuilder.metDatumEindeGeldigheid(maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg));
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(jaarTsReg - 1, maandTsReg, dagTsReg);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate bevragingHistoriePredikaat = new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel,
                HistorieVorm.GEEN);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(false));
    }

    @Test
    public void applyMaterieelFormeelGeldigHistorieVormGeen_PeilmomentNaTsRegEnGeenDatumEinde() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        final Actie actieInhoud = TestVerantwoording.maakActie(1, actieInhoudTsReg);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metActieInhoud(actieInhoud);
        recordBuilder.metDatumAanvangGeldigheid(maakDatumInteger(jaarTsReg, maandTsReg, dagTsReg));
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(jaarTsReg, maandTsReg + 6, dagTsReg);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate bevragingHistoriePredikaat = new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel,
                HistorieVorm.GEEN);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(true));
    }

    @Test
    public void applyMaterieelFormeelGeldigHistorieVormGeen_PeilmomentNaEindeGeldig() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        final Actie actieInhoud = TestVerantwoording.maakActie(1, actieInhoudTsReg);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metActieInhoud(actieInhoud);
        recordBuilder.metDatumAanvangGeldigheid(maakDatumInteger(jaarTsReg, maandTsReg, dagTsReg));
        recordBuilder.metDatumEindeGeldigheid(maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg));
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg + 1);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate bevragingHistoriePredikaat = new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel,
                HistorieVorm.GEEN);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(false));
    }

    @Test
    public void applyMaterieelFormeelGeldigHistorieVormMaterieel_PeilmomentNaEindeGeldig() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        final Actie actieInhoud = TestVerantwoording.maakActie(1, actieInhoudTsReg);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metActieInhoud(actieInhoud);
        recordBuilder.metDatumAanvangGeldigheid(maakDatumInteger(jaarTsReg, maandTsReg, dagTsReg));
        recordBuilder.metDatumEindeGeldigheid(maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg));
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg + 1);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate
                bevragingHistoriePredikaat =
                new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel, HistorieVorm
                        .MATERIEEL);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(true));
    }


    @Test
    public void applyMaterieelFormeelGeldigHistorieVormFormeel_PeilmomentNaEindeGeldig() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        final Actie actieInhoud = TestVerantwoording.maakActie(1, actieInhoudTsReg);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metActieInhoud(actieInhoud);
        recordBuilder.metDatumAanvangGeldigheid(maakDatumInteger(jaarTsReg, maandTsReg, dagTsReg));
        recordBuilder.metDatumEindeGeldigheid(maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg));
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg + 1);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate
                bevragingHistoriePredikaat =
                new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel, HistorieVorm
                        .MATERIEEL_FORMEEL);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(true));
    }

    @Test
    public void applyMaterieelFormeelGeldigHistorieVormGeen_PeilmomentNaVerval() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);
        final ZonedDateTime actieInhoudTsVerval = maakDatumTijdAttribuut(jaarTsReg + 1, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        final Actie actieInhoud = TestVerantwoording.maakActie(1, actieInhoudTsReg);
        final Actie actieVerval = TestVerantwoording.maakActie(1, actieInhoudTsVerval);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metActieInhoud(actieInhoud);
        recordBuilder.metActieVerval(actieVerval);
        recordBuilder.metDatumAanvangGeldigheid(maakDatumInteger(jaarTsReg, maandTsReg, dagTsReg));
        //recordBuilder.metDatumEindeGeldigheid(maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg));
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg + 1);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate
                bevragingHistoriePredikaat =
                new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel, HistorieVorm
                        .GEEN);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(false));
    }

    @Test
    public void applyMaterieelFormeelGeldigHistorieVormFormeel_PeilmomentNaVerval() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);
        final ZonedDateTime actieInhoudTsVerval = maakDatumTijdAttribuut(jaarTsReg + 1, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        final Actie actieInhoud = TestVerantwoording.maakActie(1, actieInhoudTsReg);
        final Actie actieVerval = TestVerantwoording.maakActie(1, actieInhoudTsVerval);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metActieInhoud(actieInhoud);
        recordBuilder.metActieInhoud(actieVerval);
        recordBuilder.metDatumAanvangGeldigheid(maakDatumInteger(jaarTsReg, maandTsReg, dagTsReg));
        recordBuilder.metDatumEindeGeldigheid(maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg));
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg + 1);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate
                bevragingHistoriePredikaat =
                new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel, HistorieVorm
                        .MATERIEEL_FORMEEL);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(true));
    }


    @Test
    public void applyMaterieelFormeelGeldigHistorieVormFormeel_PeilmomentNaTsReg_AfnemerInd() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metAttribuut(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId()), actieInhoudTsReg);
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);
        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg + 1);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate
                bevragingHistoriePredikaat =
                new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel, HistorieVorm
                        .MATERIEEL_FORMEEL);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(true));
    }


    @Test
    public void applyMaterieelFormeelGeldigHistorieVormFormeel_PeilmomentVoorTsReg_AfnemerInd() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metAttribuut(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId()), actieInhoudTsReg);
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);
        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(jaarTsReg - 1, maandTsReg, dagTsReg + 1);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate
                bevragingHistoriePredikaat =
                new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel, HistorieVorm
                        .MATERIEEL_FORMEEL);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(false));
    }

    @Test
    public void applyMaterieelFormeelGeldigHistorieVormGeen_PeilmomentNaVerval_AfnemerInd() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);
        final ZonedDateTime actieVervalTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metAttribuut(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId()), actieInhoudTsReg);
        recordBuilder.metAttribuut(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL.getId()), actieVervalTsReg);
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);
        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate
                bevragingHistoriePredikaat =
                new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel, HistorieVorm
                        .GEEN);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(false));
    }

    @Test
    public void applyMaterieelFormeelGeldigHistorieVormFormeel_PeilmomentNaVerval_AfnemerInd() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        //record
        final int jaarTsReg = 2014;
        final int maandTsReg = 1;
        final int dagTsReg = 1;
        final ZonedDateTime actieInhoudTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);
        final ZonedDateTime actieVervalTsReg = maakDatumTijdAttribuut(jaarTsReg, maandTsReg, dagTsReg);

        Calendar calendarActie = Calendar.getInstance();
        calendarActie.set(jaarTsReg, maandTsReg, dagTsReg);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metAttribuut(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId()), actieInhoudTsReg);
        recordBuilder.metAttribuut(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL.getId()), actieVervalTsReg);
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);
        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(jaarTsReg + 1, maandTsReg, dagTsReg);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate
                bevragingHistoriePredikaat =
                new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel, HistorieVorm
                        .MATERIEEL_FORMEEL);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(true));
    }

    @Test
    public void applyGeenHistorieGroep() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);

        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        //peildata
        final Integer peilDatumMaterieel = maakDatumInteger(2015, 11, 1);
        final ZonedDateTime peilDatumFormeel = LocalDate.parse(peilDatumMaterieel.toString(), FORMATTER).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final PeilmomentHistorievormPredicate bevragingHistoriePredikaat = new PeilmomentHistorievormPredicate(peilDatumMaterieel, peilDatumFormeel,
                HistorieVorm.GEEN);

        final boolean apply = bevragingHistoriePredikaat.apply(metaRecord);

        assertThat(apply, is(true));
    }


    private ZonedDateTime maakDatumTijdAttribuut(final int jaar, final int maand, final int dag) {
        return ZonedDateTime.of(jaar, maand, dag, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
    }

    private Integer maakDatumInteger(final int jaar, final int maand, final int dag) {
        final StringBuilder datumBuilder = new StringBuilder();
        datumBuilder.append(jaar);
        datumBuilder.append(StringUtils.leftPad(String.valueOf(maand), 2, '0'));
        datumBuilder.append(StringUtils.leftPad(String.valueOf(dag), 2, '0'));
        return Integer.parseInt(datumBuilder.toString());
    }
}
