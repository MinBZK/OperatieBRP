/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class FormeleHistorieSetImplTest {

    /**
     * __________________________
     * 2013-1-1 |___________A______________
     * <p/>
     * Er was geen record, en opeens komt er een record.
     */
    @Test
    public void testVanGeenHistorieNaar1Record() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.DUMMY));

        // Maak record A aan.
        final DatumTijdAttribuut regTijdA = nieuwDatumTijdAttribuut(2013, 1, 1);
        voegNieuweGeboortGroepToeAanPersoon(persoon, regTijdA, 123L);

        Assert.assertEquals(1, persoon.getPersoonGeboorteHistorie().getAantal());
        Assert.assertFalse(persoon.getPersoonGeboorteHistorie().isLeeg());

        final HisPersoonGeboorteModel cLaagA = persoon.getPersoonGeboorteHistorie().getHistorie().iterator().next();

        checkRecord(cLaagA, regTijdA, null, 123L, null);
    }

    /**
     * ___________________________________
     * 2013-7-30 |_____________B_____________________
     * 2013-1-1 |______________A____________________
     * <p/>
     * Er is een A record en er komt een B record.
     */
    @Test
    public void testNormaleWijziging() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.DUMMY));

        // Maak record A aan.
        final DatumTijdAttribuut regTijdA = nieuwDatumTijdAttribuut(2013, 1, 1);
        voegNieuweGeboortGroepToeAanPersoon(persoon, regTijdA, 123L);

        Assert.assertEquals(1, persoon.getPersoonGeboorteHistorie().getAantal());

        // Voeg record B toe:
        final DatumTijdAttribuut regTijdB = nieuwDatumTijdAttribuut(2013, 7, 30);
        voegNieuweGeboortGroepToeAanPersoon(persoon, regTijdB, 456L);

        Assert.assertEquals(2, persoon.getPersoonGeboorteHistorie().getAantal());

        HisPersoonGeboorteModel dLaagA = null;
        HisPersoonGeboorteModel cLaagB = null;

        for (HisPersoonGeboorteModel hisPersoonGeboorteModel : persoon.getPersoonGeboorteHistorie().getHistorie()) {
            if (hisPersoonGeboorteModel.getFormeleHistorie().getDatumTijdVerval() != null) {
                dLaagA = hisPersoonGeboorteModel;
            } else {
                cLaagB = hisPersoonGeboorteModel;
            }
        }

        Assert.assertNotNull(dLaagA);
        Assert.assertNotNull(cLaagB);

        // Check d laag A record.
        checkRecord(dLaagA, regTijdA, regTijdB, 123L, 456L);

        // Check c laag B record.
        checkRecord(cLaagB, regTijdB, null, 456L, null);
    }

    /**
     * ______________________________
     * 2014-1-1 |_____________B________________
     * 2013-5-1 ______________________________
     * 2013-1-1 |_____________A________________
     * <p/>
     * Let op: A was vervallen op 2013-5-1, en we registereren een nieuwe record pas op 2014-1-1
     */
    @Test
    public void testVanVervallenRecordNaarActieveRecord() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.DUMMY));

        // Maak record A aan.
        final DatumTijdAttribuut regTijdA = nieuwDatumTijdAttribuut(2013, 1, 1);
        voegNieuweGeboortGroepToeAanPersoon(persoon, regTijdA, 123L);

        Assert.assertEquals(1, persoon.getPersoonGeboorteHistorie().getAantal());

        // Laat de historie vervallen
        final ActieModel actieVerval =
                new ActieModel(new SoortActieAttribuut(SoortActie.CORRECTIE_ADRES), null, null, null, null, null, null);
        ReflectionTestUtils.setField(actieVerval, "iD", 456L);
        final DatumTijdAttribuut vervalTijd = nieuwDatumTijdAttribuut(2013, 5, 1);
        persoon.getPersoonGeboorteHistorie().verval(actieVerval, vervalTijd);

        Assert.assertEquals(1, persoon.getPersoonGeboorteHistorie().getAantal());

        checkRecord(persoon.getPersoonGeboorteHistorie().getHistorie().iterator().next(), regTijdA, vervalTijd, 123L,
                456L);

        // Voeg record B toe:
        final DatumTijdAttribuut regTijdB = nieuwDatumTijdAttribuut(2014, 1, 1);
        voegNieuweGeboortGroepToeAanPersoon(persoon, regTijdB, 101112L);

        Assert.assertEquals(2, persoon.getPersoonGeboorteHistorie().getAantal());

        HisPersoonGeboorteModel dLaagA = null;
        HisPersoonGeboorteModel cLaagB = null;

        for (HisPersoonGeboorteModel hisPersoonGeboorteModel : persoon.getPersoonGeboorteHistorie().getHistorie()) {
            if (hisPersoonGeboorteModel.getFormeleHistorie().getDatumTijdVerval() != null) {
                dLaagA = hisPersoonGeboorteModel;
            } else {
                cLaagB = hisPersoonGeboorteModel;
            }
        }

        Assert.assertNotNull(dLaagA);
        Assert.assertNotNull(cLaagB);

        // Check d laag A record.
        checkRecord(dLaagA, regTijdA, vervalTijd, 123L, 456L);

        // Check c laag B record.
        checkRecord(cLaagB, regTijdB, null, 101112L, null);

    }

    @Test(expected = IllegalStateException.class)
    public void testVervalGeenHistorieAanwezig() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.DUMMY));
        persoon.getPersoonGeboorteHistorie().verval(
                new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null, null, null, null, null),
                nieuwDatumTijdAttribuut(2012, 1, 1));
    }

    @Test(expected = IllegalStateException.class)
    public void testVervalGeenCLaagRecordAanwezig() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.DUMMY));

        final DatumTijdAttribuut regTijdA = nieuwDatumTijdAttribuut(2013, 1, 1);
        voegNieuweGeboortGroepToeAanPersoon(persoon, regTijdA, 123L);

        Assert.assertEquals(1, persoon.getPersoonGeboorteHistorie().getAantal());

        persoon.getPersoonGeboorteHistorie().verval(
                new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null, null, null, null, null),
                nieuwDatumTijdAttribuut(2012, 1, 1));

        // Nog een keer verval:
        persoon.getPersoonGeboorteHistorie().verval(
                new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null, null, null, null, null),
                nieuwDatumTijdAttribuut(2012, 1, 1));
    }

    @Test
    public void testIterableZijn() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.DUMMY));

        voegNieuweGeboortGroepToeAanPersoon(persoon, nieuwDatumTijdAttribuut(2013, 1, 1), 123L);
        voegNieuweGeboortGroepToeAanPersoon(persoon, nieuwDatumTijdAttribuut(2013, 4, 4), 456L);
        voegNieuweGeboortGroepToeAanPersoon(persoon, nieuwDatumTijdAttribuut(2013, 7, 7), 789L);

        final Iterator<HisPersoonGeboorteModel> iterator = persoon.getPersoonGeboorteHistorie().iterator();
        final List<Long> ids = new ArrayList<Long>();
        while (iterator.hasNext()) {
            ids.add(iterator.next().getVerantwoordingInhoud().getID());
        }
        Assert.assertTrue(ids.contains(123L));
        Assert.assertTrue(ids.contains(456L));
        Assert.assertTrue(ids.contains(789L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorMetNullWaarde() {
        new FormeleHistorieSetImpl<AbstractFormeelHistorischMetActieVerantwoording>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecordMagNietNullZijn() {
        final PersoonHisVolledigImpl persoon =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.getPersoonGeboorteHistorie().voegToe(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormeleHistorieMagNietNullZijn() {
        // Deze test gebruikt de 'kale' FormeleHistorieSetImpl, omdat een getFormeleHistorie() die
        // null retourneert in de model code op dit moment nergens mogelijk is.
        final FormeleHistorieSetImpl<AbstractFormeelHistorischMetActieVerantwoording> formeleHistorieSetImpl =
                new FormeleHistorieSetImpl<>(new HashSet<AbstractFormeelHistorischMetActieVerantwoording>());
        formeleHistorieSetImpl.voegToe(new AbstractFormeelHistorischMetActieVerantwoording() {
            @Override
            public FormeleHistorieModel getFormeleHistorie() {
                return null;
            }

            @Override
            public Verwerkingssoort getVerwerkingssoort() {
                return null;
            }

            @Override
            public void setVerwerkingssoort(final Verwerkingssoort verwerkingsSoort) {
            }

            @Override
            public boolean isMagGeleverdWorden() {
                return false;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDatumTijdAttribuutRegistatieMagNietNullZijn() {
        final PersoonHisVolledigImpl persoon =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        voegNieuweGeboortGroepToeAanPersoon(persoon, null, 123L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDatumTijdAttribuutVervalMoetNullZijn() {
        final PersoonHisVolledigImpl persoon =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final DatumTijdAttribuut regTijd = nieuwDatumTijdAttribuut(2013, 1, 1);
        final HisPersoonGeboorteModel hisGeboorte = bouwHisGeboorteGroep(persoon, regTijd, 123L);
        hisGeboorte.getFormeleHistorie().setDatumTijdVerval(regTijd);
        persoon.getPersoonGeboorteHistorie().voegToe(hisGeboorte);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testActieInhoudMagNietNullZijn() {
        final PersoonHisVolledigImpl persoon =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final DatumTijdAttribuut regTijd = nieuwDatumTijdAttribuut(2013, 1, 1);
        final HisPersoonGeboorteModel hisGeboorte = bouwHisGeboorteGroep(persoon, regTijd, 123L);
        hisGeboorte.setVerantwoordingInhoud(null);
        persoon.getPersoonGeboorteHistorie().voegToe(hisGeboorte);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testActieVervalMoetNullZijn() {
        final PersoonHisVolledigImpl persoon =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final DatumTijdAttribuut regTijd = nieuwDatumTijdAttribuut(2013, 1, 1);
        final HisPersoonGeboorteModel hisGeboorte = bouwHisGeboorteGroep(persoon, regTijd, 123L);
        hisGeboorte.setVerantwoordingVerval(hisGeboorte.getVerantwoordingInhoud());
        persoon.getPersoonGeboorteHistorie().voegToe(hisGeboorte);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorNietAanpasbaar() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.DUMMY));

        voegNieuweGeboortGroepToeAanPersoon(persoon, nieuwDatumTijdAttribuut(2013, 1, 1), 123L);
        voegNieuweGeboortGroepToeAanPersoon(persoon, nieuwDatumTijdAttribuut(2013, 4, 4), 456L);
        voegNieuweGeboortGroepToeAanPersoon(persoon, nieuwDatumTijdAttribuut(2013, 7, 7), 789L);

        final Iterator<HisPersoonGeboorteModel> iterator = persoon.getPersoonGeboorteHistorie().iterator();
        iterator.next();
        iterator.remove();
    }

    /**
     * ______________________________
     * 2013-1-1 |_____________B________________
     * 2012-5-1 ______________________________
     * 2010-1-1 |_____________A________________
     * <p/>
     * Let op: A was vervallen op 2013-5-1, en we registereren een nieuwe record pas op 2014-1-1
     */
    @Test
    public void testGetHistorieRecord() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.DUMMY));

        // Maak record A aan.
        final DatumTijdAttribuut regTijdA = nieuwDatumTijdAttribuut(2010, 1, 1);
        voegNieuweGeboortGroepToeAanPersoon(persoon, regTijdA, 123L);

        Assert.assertEquals(1, persoon.getPersoonGeboorteHistorie().getAantal());

        // Laat de historie vervallen
        final ActieModel actieVerval =
                new ActieModel(new SoortActieAttribuut(SoortActie.CORRECTIE_ADRES), null, null, null, null, null, null);
        ReflectionTestUtils.setField(actieVerval, "iD", 456L);
        final DatumTijdAttribuut vervalTijd = nieuwDatumTijdAttribuut(2012, 5, 1);
        persoon.getPersoonGeboorteHistorie().verval(actieVerval, vervalTijd);

        Assert.assertEquals(1, persoon.getPersoonGeboorteHistorie().getAantal());

        checkRecord(persoon.getPersoonGeboorteHistorie().getHistorie().iterator().next(), regTijdA, vervalTijd, 123L,
                456L);

        // Voeg record B toe:
        final DatumTijdAttribuut regTijdB = nieuwDatumTijdAttribuut(2013, 1, 1);
        voegNieuweGeboortGroepToeAanPersoon(persoon, regTijdB, 101112L);

        Assert.assertEquals(2, persoon.getPersoonGeboorteHistorie().getAantal());

        HisPersoonGeboorteModel dLaagA = null;
        HisPersoonGeboorteModel cLaagB = null;

        for (HisPersoonGeboorteModel hisPersoonGeboorteModel : persoon.getPersoonGeboorteHistorie().getHistorie()) {
            if (hisPersoonGeboorteModel.getFormeleHistorie().getDatumTijdVerval() != null) {
                dLaagA = hisPersoonGeboorteModel;
            } else {
                cLaagB = hisPersoonGeboorteModel;
            }
        }

        Assert.assertNotNull(dLaagA);
        Assert.assertNotNull(cLaagB);

        /*
         * ______________________________
         * 2013-1-1 |_____________B________________
         * 2012-5-1 ______________________________
         * 2010-1-1 |_____________A________________
         */
        final HisPersoonGeboorteModel actueleRecord = persoon.getPersoonGeboorteHistorie().getActueleRecord();
        Assert.assertEquals(cLaagB.getFormeleHistorie().getTijdstipRegistratie(), actueleRecord.getFormeleHistorie()
                .getTijdstipRegistratie());
        Assert.assertEquals(cLaagB.getFormeleHistorie().getDatumTijdVerval(), actueleRecord.getFormeleHistorie()
                .getDatumTijdVerval());

        final HisPersoonGeboorteModel nullRecord =
                persoon.getPersoonGeboorteHistorie().getHistorieRecord(nieuwDatumTijdAttribuut(2012, 12, 1));
        Assert.assertNull(nullRecord);

        final HisPersoonGeboorteModel recordA =
                persoon.getPersoonGeboorteHistorie().getHistorieRecord(nieuwDatumTijdAttribuut(2012, 4, 1));
        Assert.assertEquals(dLaagA.getFormeleHistorie().getTijdstipRegistratie(), recordA.getFormeleHistorie()
                .getTijdstipRegistratie());
        Assert.assertEquals(dLaagA.getFormeleHistorie().getDatumTijdVerval(), recordA.getFormeleHistorie()
                .getDatumTijdVerval());
    }

    @Test
    public void testActueleRecordMetTsRegGelijkAanNieuwRecordWordtVerwijderd() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.DUMMY));

        // Maak record A aan.
        final DatumTijdAttribuut regTijdA = nieuwDatumTijdAttribuut(2010, 1, 1);
        voegNieuweGeboortGroepToeAanPersoon(persoon, regTijdA, 123L);

        // Voeg record B toe:
        final DatumTijdAttribuut regTijdB = regTijdA;
        voegNieuweGeboortGroepToeAanPersoon(persoon, regTijdB, 101112L);

        Assert.assertEquals(1, persoon.getPersoonGeboorteHistorie().getAantal());
    }

    /**
     * Voegt een nieuwe historische geboortegroep instantie toe aan de opgegeven persoon. Hiervoor wordt een nieuwe
     * actie aangemaakt met opgegeven id en de tijdstip van registratie wordt gezet naar het opgegeven tijdstip.
     *
     * @param persoon de persoon waaraan een geboorte groep moet worden toegevoegd.
     * @param tsReg   het tijdstip van de registratie van deze nieuwe geboortegroep.
     * @param actieId id van de actie die gebruikt moet worden.
     */
    private void voegNieuweGeboortGroepToeAanPersoon(final PersoonHisVolledigImpl persoon,
            final DatumTijdAttribuut tsReg, final Long actieId)
    {
        final HisPersoonGeboorteModel hisGeboorte = bouwHisGeboorteGroep(persoon, tsReg, actieId);
        persoon.getPersoonGeboorteHistorie().voegToe(hisGeboorte);
    }

    private HisPersoonGeboorteModel bouwHisGeboorteGroep(final PersoonHisVolledigImpl persoon,
            final DatumTijdAttribuut tsReg, final Long actieId)
    {
        final PersoonGeboorteGroepBericht geboorteGroepBericht = new PersoonGeboorteGroepBericht();
        geboorteGroepBericht.setDatumTijdVerval(tsReg);
        final ActieModel actie =
                new ActieModel(new SoortActieAttribuut(SoortActie.CORRECTIE_ADRES), null, null, null, null, tsReg,
                        null);
        ReflectionTestUtils.setField(actie, "iD", actieId);

        return new HisPersoonGeboorteModel(persoon, geboorteGroepBericht, actie);
    }

    private void checkRecord(final HisPersoonGeboorteModel actueleFormeleHistorie,
            final DatumTijdAttribuut verwDatumTijdAttribuutRegistratie,
            final DatumTijdAttribuut verwDatumTijdAttribuutVerval, final Long verwActieInhoudId,
            final Long verwActieVervalId)
    {
        Assert.assertEquals(verwDatumTijdAttribuutRegistratie, actueleFormeleHistorie.getTijdstipRegistratie());

        if (verwDatumTijdAttribuutVerval == null) {
            Assert.assertNull(actueleFormeleHistorie.getDatumTijdVerval());
        } else {
            Assert.assertEquals(verwDatumTijdAttribuutVerval, actueleFormeleHistorie.getDatumTijdVerval());
        }

        Assert.assertEquals(verwActieInhoudId, actueleFormeleHistorie.getVerantwoordingInhoud().getID());

        if (verwActieVervalId == null) {
            Assert.assertNull(actueleFormeleHistorie.getVerantwoordingVerval());
        } else {
            Assert.assertEquals(verwActieVervalId, actueleFormeleHistorie.getVerantwoordingVerval().getID());
        }
    }

    private DatumTijdAttribuut nieuwDatumTijdAttribuut(final int jaar, final int maand, final int dag) {
        final Calendar instance = Calendar.getInstance(TimeZone.getDefault());
        instance.set(Calendar.YEAR, jaar);
        instance.set(Calendar.MONTH, maand - 1);
        instance.set(Calendar.DAY_OF_MONTH, dag);
        return new DatumTijdAttribuut(instance.getTime());
    }
}
