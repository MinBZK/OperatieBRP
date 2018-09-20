/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TimeZone;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.basis.MaterieleHistorieModel;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class MaterieleHistorieSetImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Bouwt een nieuwe {@link HisPersoonAdresModel} instantie op en voegt deze toe de opgegeven
     * {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl} adressen lijst.
     *
     * @param adresHisVolledig de {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl} instantie
     *            waar het adres aan toegevoegd wordt.
     * @param tsReg het tijdstip van de registratie van de toevoeging.
     * @param beginGeldigheid het begin van de geldigheid van de toevoeging.
     * @param eindeGeldigheid het eind van de geldigheid van de toevoeging.
     * @param actieId het id van de actie waarmee de toevoeging toegevoegd dient te worden.
     * @param huisletter huisletter van het adres.
     */
    private void voegAdresToeAanVolledigeAdresHistorie(final PersoonAdresHisVolledigImpl adresHisVolledig,
            final DatumTijdAttribuut tsReg, final Integer beginGeldigheid, final Integer eindeGeldigheid,
            final Long actieId, final String huisletter)
    {
        final DatumEvtDeelsOnbekendAttribuut datumBeginGeldigheid = new DatumEvtDeelsOnbekendAttribuut(beginGeldigheid);
        DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid = null;
        if (eindeGeldigheid != null) {
            datumEindeGeldigheid = new DatumEvtDeelsOnbekendAttribuut(eindeGeldigheid);
        }

        // Bouw het A record op.
        final HisPersoonAdresModel hisAdres =
            bouwHisPersoonAdresRecord(tsReg, datumBeginGeldigheid, datumEindeGeldigheid, actieId, huisletter);

        // Voeg het A record toe.
        adresHisVolledig.getPersoonAdresHistorie().voegToe(hisAdres);
    }

    /**
     * Bouwt een nieuwe {@link HisPersoonAdresModel} instantie op.
     *
     * @param tsReg het tijdstip van de registratie van de toevoeging.
     * @param beginGeldigheid het begin van de geldigheid van de toevoeging.
     * @param eindeGeldigheid het eind van de geldigheid van de toevoeging.
     * @param actieId het id van de actie waarmee de toevoeging toegevoegd dient te worden.
     * @param huisletter huisletter van het adres.
     */
    private HisPersoonAdresModel bouwHisPersoonAdresRecord(final DatumTijdAttribuut tsReg,
            final DatumEvtDeelsOnbekendAttribuut beginGeldigheid, final DatumEvtDeelsOnbekendAttribuut eindeGeldigheid,
            final Long actieId, final String huisletter)
    {
        final ActieModel actie = bouwActie(tsReg, actieId);
        final PersoonAdresStandaardGroepBericht adres = new PersoonAdresStandaardGroepBericht();
        adres.setDatumAanvangGeldigheid(beginGeldigheid);
        if (eindeGeldigheid != null) {
            adres.setDatumEindeGeldigheid(eindeGeldigheid);
        }
        adres.setHuisletter(new HuisletterAttribuut(huisletter));
        return new HisPersoonAdresModel(null, adres, adres, actie);
    }

    private ActieModel bouwActie(final DatumTijdAttribuut tsReg, final Long actieId) {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.CORRECTIE_ADRES), null, null, null, null, tsReg, null);
        ReflectionTestUtils.setField(actie, "iD", actieId);
        return actie;
    }

    private PersoonAdresHisVolledigImpl maakNieuwPersoonAdresHisVolledig() {
        return new PersoonAdresHisVolledigImpl(new PersoonHisVolledigImpl(new SoortPersoonAttribuut(
                SoortPersoon.INGESCHREVENE)));
    }

    /**
     * ___________________________
     * |_____A'__________|____B____
     * |_____A_____________________
     * <p/>
     * 1-1-10 1-5-10 Beginsituatie: A record. B wordt toegevoegd. EindSituatie: 1 D laag record en 2 C laag records.
     */
    @Test
    public void testVerhuizingAdresANaarB() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Initialiseer de beginsituatie:
        final DatumTijdAttribuut tsRegA = nieuwRegTijd(2010, 1, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegA, 20100101, null, 123L, "A");

        // Controleer de beginsituatie:
        Assert.assertEquals(1, persoonAdresHisVolledig.getPersoonAdresHistorie().getAantal());
        Assert.assertFalse(persoonAdresHisVolledig.getPersoonAdresHistorie().isLeeg());
        for (HisPersoonAdresModel hisPersoonAdresModel : persoonAdresHisVolledig.getPersoonAdresHistorie()
                .getHistorie())
        {
            Assert.assertEquals(new DatumEvtDeelsOnbekendAttribuut(20100101), hisPersoonAdresModel
                    .getMaterieleHistorie().getDatumAanvangGeldigheid());
            Assert.assertNull(hisPersoonAdresModel.getMaterieleHistorie().getDatumEindeGeldigheid());
            Assert.assertEquals(tsRegA, hisPersoonAdresModel.getMaterieleHistorie().getTijdstipRegistratie());
            Assert.assertNull(hisPersoonAdresModel.getMaterieleHistorie().getDatumTijdVerval());
            Assert.assertEquals(123L, hisPersoonAdresModel.getVerantwoordingInhoud().getID().longValue());
            Assert.assertNull(hisPersoonAdresModel.getVerantwoordingVerval());
            Assert.assertNull(hisPersoonAdresModel.getVerantwoordingAanpassingGeldigheid());
        }

        // Voer de adres wijziging B door:
        final DatumTijdAttribuut tsRegB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegB, 20100501, null, 456L, "B");

        // Controleer de eind situatie.
        HisPersoonAdresModel dLaagA = null;
        HisPersoonAdresModel cLaagAAccent = null;
        HisPersoonAdresModel cLaagB = null;

        // Controleer de aantallen:
        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 3, 2, 1);

        for (HisPersoonAdresModel hisPersoonAdresModel : persoonAdresHisVolledig.getPersoonAdresHistorie()
                .getHistorie())
        {
            if (hisPersoonAdresModel.getDatumTijdVerval() != null) {
                // A
                dLaagA = hisPersoonAdresModel;
            } else if (hisPersoonAdresModel.getDatumEindeGeldigheid() != null) {
                // A'
                cLaagAAccent = hisPersoonAdresModel;
            } else if (hisPersoonAdresModel.getDatumAanvangGeldigheid() != null) {
                // B
                cLaagB = hisPersoonAdresModel;
            }
        }

        Assert.assertNotNull(dLaagA);
        Assert.assertNotNull(cLaagAAccent);
        Assert.assertNotNull(cLaagB);

        // Controleer D laag record (A):
        checkRecord(dLaagA, tsRegB, tsRegA, new DatumEvtDeelsOnbekendAttribuut(20100101), null, 456L, 123L, null);

        // Controleer beeindigd C laag record (A'):
        checkRecord(cLaagAAccent, null, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100101),
                new DatumEvtDeelsOnbekendAttribuut(20100501), null, 123L, 456L);

        // Controleer C laag record (B)
        checkRecord(cLaagB, null, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100501), null, null, 456L, null);
    }

    /**
     * ________________________________________________
     * | A' |____B'__________|_______C_____
     * |_________________|____B_________________________
     * |_____A__________________________________________
     * 1-1-10 1-5-10 1-2-12
     * <p/>
     * Beginsituatie: A record. B wordt later toegevoegd op 1-5-2010. C wordt later toegevoeg op 1-2-2012. EindSituatie:
     * 2 D laag records (A en B) en 3 C laag records (A', B' en C).
     */
    @Test
    public void testVerhuizingVanANaarBNaarC() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = maakNieuwPersoonAdresHisVolledig();

        // Beginsituatie:
        final DatumTijdAttribuut tsRegA = nieuwRegTijd(2010, 1, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegA, 20100101, null, 123L, "A");

        // TussenLiggendeSituatie:
        final DatumTijdAttribuut tsRegB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegB, 20100501, null, 456L, "B");

        // Laatste: voeg C toe:
        final DatumTijdAttribuut tsRegC = nieuwRegTijd(2012, 2, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegC, 20120201, null, 789L, "C");

        // Controleer de aantallen:
        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 5, 3, 2);

        // Controleer de eind situatie.
        HisPersoonAdresModel dLaagA = null;
        HisPersoonAdresModel dLaagB = null;
        HisPersoonAdresModel cLaagAAccent = null;
        HisPersoonAdresModel cLaagBAccent = null;
        HisPersoonAdresModel cLaagC = null;

        /*
         * ________________________________________________
         * | A' |____B'__________|_______C_____
         * |_________________|____B_________________________
         * |_____A__________________________________________
         * 1-1-10 1-5-10 1-2-12
         */
        for (HisPersoonAdresModel record : persoonAdresHisVolledig.getPersoonAdresHistorie().getHistorie()) {
            if (record.getDatumTijdVerval() != null) {
                // D laag
                if (record.getHuisletter().getWaarde().equals("B")) {
                    dLaagB = record;
                } else if (record.getHuisletter().getWaarde().equals("A")) {
                    dLaagA = record;
                }
            } else {
                // C laag
                if (record.getHuisletter().getWaarde().equals("A")) {
                    cLaagAAccent = record;
                } else if (record.getHuisletter().getWaarde().equals("B")) {
                    cLaagBAccent = record;
                } else if (record.getHuisletter().getWaarde().equals("C")) {
                    cLaagC = record;
                }
            }
        }

        Assert.assertNotNull(dLaagA);
        Assert.assertNotNull(dLaagB);
        Assert.assertNotNull(cLaagAAccent);
        Assert.assertNotNull(cLaagBAccent);
        Assert.assertNotNull(cLaagC);

        // Controleer de records in de eind situatie zoals geschetst:
        /*
         * ________________________________________________
         * | A' |____B'__________|_______C_____
         * |_________________|____B_________________________
         * |_____A__________________________________________
         * 1-1-10 1-5-10 1-2-12
         */

        checkRecord(dLaagA, tsRegB, tsRegA, new DatumEvtDeelsOnbekendAttribuut(20100101), null, 456L, 123L, null);
        checkRecord(dLaagB, tsRegC, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100501), null, 789L, 456L, null);
        checkRecord(cLaagAAccent, null, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100101),
                new DatumEvtDeelsOnbekendAttribuut(20100501), null, 123L, 456L);
        checkRecord(cLaagBAccent, null, tsRegC, new DatumEvtDeelsOnbekendAttribuut(20100501),
                new DatumEvtDeelsOnbekendAttribuut(20120201), null, 456L, 789L);
        checkRecord(cLaagC, null, tsRegC, new DatumEvtDeelsOnbekendAttribuut(20120201), null, null, 789L, null);

    }

    /**
     * ______________________________________________________________________
     * | |____B''______|____________D____________|_____C'_____
     * | A' |____B'____________________|_______C_________________
     * |_________________|____B_______________________________________________
     * |_____A________________________________________________________________
     * 1-1-10 1-5-10 1-2-11 1-2-12 1-5-12
     * <p/>
     * Beginsituatie: A record. B wordt later toegevoegd op 1-5-2010. C wordt later toegevoeg op 1-2-2012. D wordt later
     * toegevoegd op 1-5-2012 EN overlapt B en C. EindSituatie: 4 D laag records (A, B, B' en C) en 4 C laag records
     * (A', B'', C' en D).
     */
    @Test
    public void testCorrectieMetGedeeltelijkeOverlappingOpBenC() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Beginsituatie:
        final DatumTijdAttribuut tsRegA = nieuwRegTijd(2010, 1, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegA, 20100101, null, 123L, "A");

        // TussenLiggendeSituatie:
        final DatumTijdAttribuut tsRegB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegB, 20100501, null, 456L, "B");

        // Laatste: voeg C toe:
        final DatumTijdAttribuut tsRegC = nieuwRegTijd(2012, 2, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegC, 20120201, null, 789L, "C");

        // Voeg de overlappende record D toe:
        final DatumTijdAttribuut tsRegD = nieuwRegTijd(2012, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegD, 20110201, 20120501, 101112L, "D");

        // Controleer de aantallen:
        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 8, 4, 4);

        HisPersoonAdresModel dLaagA = null;
        HisPersoonAdresModel dLaagB = null;
        HisPersoonAdresModel cLaagAAccent = null;
        HisPersoonAdresModel dLaagBAccent = null;
        HisPersoonAdresModel dLaagC = null;
        HisPersoonAdresModel cLaagBAccentAccent = null;
        HisPersoonAdresModel cLaagCAccent = null;
        HisPersoonAdresModel cLaagD = null;

        /*
         * ______________________________________________________________________
         * | |____B''______|____________D____________|_____C'_____
         * | A' |____B'____________________|_______C_________________
         * |_________________|____B_______________________________________________
         * |_____A________________________________________________________________
         * 1-1-10 1-5-10 1-2-11 1-2-12 1-5-12
         */

        for (HisPersoonAdresModel hisPersoonAdresModel : persoonAdresHisVolledig.getPersoonAdresHistorie()
                .getHistorie())
        {
            if (hisPersoonAdresModel.getMaterieleHistorie().getDatumTijdVerval() != null) {
                // D laag record
                if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("A")) {
                    dLaagA = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("C")) {
                    dLaagC = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("B")) {
                    // Is het B' of B???
                    if (hisPersoonAdresModel.getMaterieleHistorie().getDatumEindeGeldigheid() != null) {
                        dLaagBAccent = hisPersoonAdresModel;
                    } else {
                        dLaagB = hisPersoonAdresModel;
                    }
                }
            } else {
                // C laag record
                if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("A")) {
                    cLaagAAccent = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("B")) {
                    cLaagBAccentAccent = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("C")) {
                    cLaagCAccent = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("D")) {
                    cLaagD = hisPersoonAdresModel;
                }
            }
        }

        Assert.assertNotNull(dLaagA);
        Assert.assertNotNull(dLaagB);
        Assert.assertNotNull(cLaagAAccent);
        Assert.assertNotNull(dLaagBAccent);
        Assert.assertNotNull(dLaagC);
        Assert.assertNotNull(cLaagBAccentAccent);
        Assert.assertNotNull(cLaagCAccent);
        Assert.assertNotNull(cLaagD);

        // Controleer alle de records:
        /*
         * ______________________________________________________________________
         * | |____B''______|____________D____________|_____C'_____
         * | A' |____B'____________________|_______C_________________
         * |_________________|____B_______________________________________________
         * |_____A________________________________________________________________
         * 1-1-10 1-5-10 1-2-11 1-2-12 1-5-12
         */

        checkRecord(dLaagA, tsRegB, tsRegA, new DatumEvtDeelsOnbekendAttribuut(20100101), null, 456L, 123L, null);
        checkRecord(dLaagB, tsRegC, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100501), null, 789L, 456L, null);
        checkRecord(cLaagAAccent, null, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100101),
                new DatumEvtDeelsOnbekendAttribuut(20100501), null, 123L, 456L);
        checkRecord(dLaagBAccent, tsRegD, tsRegC, new DatumEvtDeelsOnbekendAttribuut(20100501),
                new DatumEvtDeelsOnbekendAttribuut(20120201), 101112L, 456L, 789L);
        checkRecord(dLaagC, tsRegD, tsRegC, new DatumEvtDeelsOnbekendAttribuut(20120201), null, 101112L, 789L, null);
        checkRecord(cLaagBAccentAccent, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20100501),
                new DatumEvtDeelsOnbekendAttribuut(20110201), null, 456L, 101112L);
        checkRecord(cLaagCAccent, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20120501), null, null, 789L, 101112L);
        checkRecord(cLaagD, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20110201),
                new DatumEvtDeelsOnbekendAttribuut(20120501), null, 101112L, null);
    }

    /**
     * ______________________________________________________________________
     * | |_______________________D______________|_____C'______
     * | A' |____B'____________________|_______C_________________
     * |_________________|____B_______________________________________________
     * |_____A________________________________________________________________
     * 1-1-10 1-5-10 1-2-12 1-5-12
     * <p/>
     * Beginsituatie: A record. B wordt later toegevoegd op 1-5-2010. C wordt later toegevoeg op 1-2-2012. D wordt later
     * toegevoegd op 1-5-2012 EN overlapt B volledig en overlapt C deels. EindSituatie: 4 D laag records (A, B, B' en C)
     * en 3 C laag records (A', D en C').
     */
    @Test
    public void testCorrectieMetVolledigeOverlappingOpBenGedeeltelijkeOverlappingOpC() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Beginsituatie:
        final DatumTijdAttribuut tsRegA = nieuwRegTijd(2010, 1, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegA, 20100101, null, 123L, "A");

        // TussenLiggendeSituatie:
        final DatumTijdAttribuut tsRegB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegB, 20100501, null, 456L, "B");

        // Laatste: voeg C toe:
        final DatumTijdAttribuut tsRegC = nieuwRegTijd(2012, 2, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegC, 20120201, null, 789L, "C");

        // Voeg de overlappende record D toe:
        final DatumTijdAttribuut tsRegD = nieuwRegTijd(2012, 6, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegD, 20100501, 20120501, 101112L, "D");

        // Controleer de aantallen:
        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 7, 3, 4);

        HisPersoonAdresModel dLaagA = null;
        HisPersoonAdresModel dLaagB = null;
        HisPersoonAdresModel cLaagAAccent = null;
        HisPersoonAdresModel dLaagBAccent = null;
        HisPersoonAdresModel dLaagC = null;
        HisPersoonAdresModel cLaagCAccent = null;
        HisPersoonAdresModel cLaagD = null;

        /*
         * ______________________________________________________________________
         * | |_______________________D______________|_____C'______
         * | A' |____B'____________________|_______C_________________
         * |_________________|____B_______________________________________________
         * |_____A________________________________________________________________
         * 1-1-10 1-5-10 1-2-12 1-5-12
         */
        for (HisPersoonAdresModel hisPersoonAdresModel : persoonAdresHisVolledig.getPersoonAdresHistorie()
                .getHistorie())
        {
            if (hisPersoonAdresModel.getMaterieleHistorie().getDatumTijdVerval() != null) {
                // D laag record
                if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("A")) {
                    dLaagA = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("C")) {
                    dLaagC = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("B")) {
                    // Is het B' of B???
                    if (hisPersoonAdresModel.getMaterieleHistorie().getDatumEindeGeldigheid() != null) {
                        dLaagBAccent = hisPersoonAdresModel;
                    } else {
                        dLaagB = hisPersoonAdresModel;
                    }
                }
            } else {
                // C laag record
                if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("A")) {
                    cLaagAAccent = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("C")) {
                    cLaagCAccent = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("D")) {
                    cLaagD = hisPersoonAdresModel;
                }
            }
        }

        /*
         * ______________________________________________________________________
         * | |_______________________D______________|_____C'______
         * | A' |____B'____________________|_______C_________________
         * |_________________|____B_______________________________________________
         * |_____A________________________________________________________________
         * 1-1-10 1-5-10 1-2-12 1-5-12
         */
        checkRecord(dLaagA, tsRegB, tsRegA, new DatumEvtDeelsOnbekendAttribuut(20100101), null, 456L, 123L, null);
        checkRecord(dLaagB, tsRegC, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100501), null, 789L, 456L, null);
        checkRecord(cLaagAAccent, null, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100101),
                new DatumEvtDeelsOnbekendAttribuut(20100501), null, 123L, 456L);
        checkRecord(dLaagBAccent, tsRegD, tsRegC, new DatumEvtDeelsOnbekendAttribuut(20100501),
                new DatumEvtDeelsOnbekendAttribuut(20120201), 101112L, 456L, 789L);
        checkRecord(dLaagC, tsRegD, tsRegC, new DatumEvtDeelsOnbekendAttribuut(20120201), null, 101112L, 789L, null);
        checkRecord(cLaagCAccent, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20120501), null, null, 789L, 101112L);
        checkRecord(cLaagD, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20100501),
                new DatumEvtDeelsOnbekendAttribuut(20120501), null, 101112L, null);
    }

    /**
     * ______________________________________________________________________
     * | |____B''______|____________D_________________________
     * | A' |____B'____________________|_______C_________________
     * |_________________|____B_______________________________________________
     * |_____A________________________________________________________________
     * 1-1-10 1-5-10 1-2-11 1-2-12 1-5-12
     * <p/>
     * Beginsituatie: A record. B wordt later toegevoegd op 1-5-2010. C wordt later toegevoeg op 1-2-2012. D wordt later
     * toegevoegd op 1-5-2012 EN overlapt deels B en overlapt volledig C. EindSituatie: 4 D laag records (A, B, B' en C)
     * en 3 C laag records (A', B'' en D).
     */
    @Test
    public void testCorrectieMetVolledigeOverlappingOpCenDeelsOpB() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Beginsituatie:
        final DatumTijdAttribuut tsRegA = nieuwRegTijd(2010, 1, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegA, 20100101, null, 123L, "A");

        // TussenLiggendeSituatie:
        final DatumTijdAttribuut tsRegB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegB, 20100501, null, 456L, "B");

        // Laatste: voeg C toe:
        final DatumTijdAttribuut tsRegC = nieuwRegTijd(2012, 2, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegC, 20120201, null, 789L, "C");

        // Voeg de overlappende record D toe:
        final DatumTijdAttribuut tsRegD = nieuwRegTijd(2012, 6, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegD, 20110201, null, 101112L, "D");

        // Controleer de aantallen:
        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 7, 3, 4);

        HisPersoonAdresModel dLaagA = null;
        HisPersoonAdresModel dLaagB = null;
        HisPersoonAdresModel dLaagBAccent = null;
        HisPersoonAdresModel dLaagC = null;
        HisPersoonAdresModel cLaagBAccentAccent = null;
        HisPersoonAdresModel cLaagAAccent = null;
        HisPersoonAdresModel cLaagD = null;

        /*
         * ______________________________________________________________________
         * | |____B''______|____________D_________________________
         * | A' |____B'____________________|_______C_________________
         * |_________________|____B_______________________________________________
         * |_____A________________________________________________________________
         * 1-1-10 1-5-10 1-2-11 1-2-12 1-5-12
         */

        for (HisPersoonAdresModel hisPersoonAdresModel : persoonAdresHisVolledig.getPersoonAdresHistorie()
                .getHistorie())
        {
            if (hisPersoonAdresModel.getMaterieleHistorie().getDatumTijdVerval() != null) {
                // D laag record
                if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("A")) {
                    dLaagA = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("C")) {
                    dLaagC = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("B")) {
                    // Is het B' of B???
                    if (hisPersoonAdresModel.getMaterieleHistorie().getDatumEindeGeldigheid() != null) {
                        dLaagBAccent = hisPersoonAdresModel;
                    } else {
                        dLaagB = hisPersoonAdresModel;
                    }
                }
            } else {
                // C laag record
                if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("A")) {
                    cLaagAAccent = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("D")) {
                    cLaagD = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("B")) {
                    cLaagBAccentAccent = hisPersoonAdresModel;
                }
            }
        }

        /*
         * ______________________________________________________________________
         * | |____B''______|____________D_________________________
         * | A' |____B'____________________|_______C_________________
         * |_________________|____B_______________________________________________
         * |_____A________________________________________________________________
         * 1-1-10 1-5-10 1-2-11 1-2-12 1-5-12
         */

        checkRecord(dLaagA, tsRegB, tsRegA, new DatumEvtDeelsOnbekendAttribuut(20100101), null, 456L, 123L, null);
        checkRecord(dLaagB, tsRegC, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100501), null, 789L, 456L, null);
        checkRecord(cLaagAAccent, null, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100101),
                new DatumEvtDeelsOnbekendAttribuut(20100501), null, 123L, 456L);
        checkRecord(dLaagBAccent, tsRegD, tsRegC, new DatumEvtDeelsOnbekendAttribuut(20100501),
                new DatumEvtDeelsOnbekendAttribuut(20120201), 101112L, 456L, 789L);
        checkRecord(dLaagC, tsRegD, tsRegC, new DatumEvtDeelsOnbekendAttribuut(20120201), null, 101112L, 789L, null);
        checkRecord(cLaagBAccentAccent, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20100501),
                new DatumEvtDeelsOnbekendAttribuut(20110201), null, 456L, 101112L);
        checkRecord(cLaagD, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20110201), null, null, 101112L, null);
    }

    /**
     * ______________________________________________________________________________
     * |______A''__|__________D______________________________________|________C'______
     * | A' |____B'____________________________|_______C_________________
     * |_________________|____B_______________________________________________________
     * |_____A________________________________________________________________________
     * 1-1-10 1-3-10 1-5-10 1-2-12 1-5-12
     * <p/>
     * Beginsituatie: A record. B wordt later toegevoegd op 1-5-2010. C wordt later toegevoeg op 1-2-2012. D wordt later
     * toegevoegd op 1-5-2012 EN overlapt B volledig en overlapt C en A' deels. EindSituatie: 5 D laag records (A, A',
     * B, B' en C) en 3 C laag records (A'', D en C').
     */
    @Test
    public void testVolledigeOverlappingOpBenDeelsOverlappingOpAenC() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Beginsituatie A:
        final DatumTijdAttribuut tsRegA = nieuwRegTijd(2010, 1, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegA, 20100101, null, 123L, "A");

        // TussenLiggendeSituatie B:
        final DatumTijdAttribuut tsRegB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegB, 20100501, null, 456L, "B");

        // Laatste: voeg C toe:
        final DatumTijdAttribuut tsRegC = nieuwRegTijd(2012, 2, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegC, 20120201, null, 789L, "C");

        // Voeg de overlappende record D toe:
        final DatumTijdAttribuut tsRegD = nieuwRegTijd(2012, 6, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegD, 20100301, 20120501, 101112L, "D");

        // Controleer de aantallen:
        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 8, 3, 5);

        /*
         * ______________________________________________________________________________
         * |______A''__|__________D______________________________________|________C'______
         * | A' |____B'____________________________|_______C_________________
         * |_________________|____B_______________________________________________________
         * |_____A________________________________________________________________________
         * 1-1-10 1-3-10 1-5-10 1-2-12 1-5-12
         */

        HisPersoonAdresModel dLaagA = null;
        HisPersoonAdresModel dLaagB = null;
        HisPersoonAdresModel dLaagAAccent = null;
        HisPersoonAdresModel dLaagBAccent = null;
        HisPersoonAdresModel dLaagC = null;
        HisPersoonAdresModel cLaagAAccentAccent = null;
        HisPersoonAdresModel cLaagCAccent = null;
        HisPersoonAdresModel cLaagD = null;

        /*
         * ______________________________________________________________________________
         * |______A''__|__________D______________________________________|________C'______
         * | A' |____B'____________________________|_______C_________________
         * |_________________|____B_______________________________________________________
         * |_____A________________________________________________________________________
         * 1-1-10 1-3-10 1-5-10 1-2-12 1-5-12
         */

        for (HisPersoonAdresModel hisPersoonAdresModel : persoonAdresHisVolledig.getPersoonAdresHistorie()
                .getHistorie())
        {
            if (hisPersoonAdresModel.getMaterieleHistorie().getDatumTijdVerval() != null) {
                // D laag record
                if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("A")) {
                    // Is het A' of A???
                    if (hisPersoonAdresModel.getMaterieleHistorie().getDatumEindeGeldigheid() != null) {
                        dLaagAAccent = hisPersoonAdresModel;
                    } else {
                        dLaagA = hisPersoonAdresModel;
                    }
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("C")) {
                    dLaagC = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("B")) {
                    // Is het B' of B???
                    if (hisPersoonAdresModel.getMaterieleHistorie().getDatumEindeGeldigheid() != null) {
                        dLaagBAccent = hisPersoonAdresModel;
                    } else {
                        dLaagB = hisPersoonAdresModel;
                    }
                }
            } else {
                // C laag record
                if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("A")) {
                    cLaagAAccentAccent = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("D")) {
                    cLaagD = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("C")) {
                    cLaagCAccent = hisPersoonAdresModel;
                }
            }
        }

        /*
         * ______________________________________________________________________________
         * |______A''__|__________D______________________________________|________C'______
         * | A' |____B'____________________________|_______C_________________
         * |_________________|____B_______________________________________________________
         * |_____A________________________________________________________________________
         * 1-1-10 1-3-10 1-5-10 1-2-12 1-5-12
         */
        // Controleer alle records:
        checkRecord(dLaagA, tsRegB, tsRegA, new DatumEvtDeelsOnbekendAttribuut(20100101), null, 456L, 123L, null);
        checkRecord(dLaagAAccent, tsRegD, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100101),
                new DatumEvtDeelsOnbekendAttribuut(20100501), 101112L, 123L, 456L);
        checkRecord(dLaagB, tsRegC, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100501), null, 789L, 456L, null);
        checkRecord(dLaagBAccent, tsRegD, tsRegC, new DatumEvtDeelsOnbekendAttribuut(20100501),
                new DatumEvtDeelsOnbekendAttribuut(20120201), 101112L, 456L, 789L);
        checkRecord(dLaagC, tsRegD, tsRegC, new DatumEvtDeelsOnbekendAttribuut(20120201), null, 101112L, 789L, null);
        checkRecord(cLaagAAccentAccent, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20100101),
                new DatumEvtDeelsOnbekendAttribuut(20100301), null, 123L, 101112L);
        checkRecord(cLaagD, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20100301),
                new DatumEvtDeelsOnbekendAttribuut(20120501), null, 101112L, null);
        checkRecord(cLaagCAccent, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20120501), null, null, 789L, 101112L);
    }

    /**
     * ______________________________________________________________________________
     * | |____B''__|______D________|___B'''_|
     * | A' |____B'____________________________|_______C_________________
     * |_________________|____B_______________________________________________________
     * |_____A________________________________________________________________________
     * 1-1-10 1-5-10 1-7-10 1-9-10 1-2-12
     * <p/>
     * Beginsituatie: A record. B wordt later toegevoegd op 1-5-2010. C wordt later toegevoeg op 1-2-2012. D wordt later
     * toegevoegd op 1-5-2012 EN overlapt B' deels, maar er ontstaat een gat wat opgevuld moet worden door B'' en B'''.
     * EindSituatie: 3 D laag records (A, B, B') en 5 C laag records (A', B'', D, B''' en C).
     */
    @Test
    public void testNieuweRecordValtBinnenB() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Beginsituatie A:
        final DatumTijdAttribuut tsRegA = nieuwRegTijd(2010, 1, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegA, 20100101, null, 123L, "A");

        // TussenLiggendeSituatie B:
        final DatumTijdAttribuut tsRegB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegB, 20100501, null, 456L, "B");

        // Laatste: voeg C toe:
        final DatumTijdAttribuut tsRegC = nieuwRegTijd(2012, 2, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegC, 20120201, null, 789L, "C");

        // Voeg de overlappende record D toe:
        final DatumTijdAttribuut tsRegD = nieuwRegTijd(2012, 6, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegD, 20100701, 20100901, 101112L, "D");

        // Controleer de aantallen:
        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 8, 5, 3);

        /*
         * ______________________________________________________________________________
         * | |____B''__|______D________|___B'''_|
         * | A' |____B'____________________________|_______C_________________
         * |_________________|____B_______________________________________________________
         * |_____A________________________________________________________________________
         * 1-1-10 1-5-10 1-7-10 1-9-10 1-2-12
         */

        HisPersoonAdresModel dLaagA = null;
        HisPersoonAdresModel dLaagB = null;
        HisPersoonAdresModel dLaagBAccent = null;
        HisPersoonAdresModel cLaagAAccent = null;
        HisPersoonAdresModel cLaagBAccentAccent = null;
        HisPersoonAdresModel cLaagBAccentAccentAccent = null;
        HisPersoonAdresModel cLaagC = null;
        HisPersoonAdresModel cLaagD = null;

        for (HisPersoonAdresModel hisPersoonAdresModel : persoonAdresHisVolledig.getPersoonAdresHistorie()
                .getHistorie())
        {
            if (hisPersoonAdresModel.getMaterieleHistorie().getDatumTijdVerval() != null) {
                // D Laag
                if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("A")) {
                    dLaagA = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("B")) {
                    // Is het de B of B' ???
                    if (hisPersoonAdresModel.getMaterieleHistorie().getDatumEindeGeldigheid() == null) {
                        dLaagB = hisPersoonAdresModel;
                    } else {
                        dLaagBAccent = hisPersoonAdresModel;
                    }
                }
            } else {
                // C Laag
                if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("A")) {
                    cLaagAAccent = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("C")) {
                    cLaagC = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("D")) {
                    cLaagD = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("B")) {
                    // Is het B'' of B''' ???
                    if (hisPersoonAdresModel.getMaterieleHistorie().getDatumAanvangGeldigheid()
                            .op(new DatumEvtDeelsOnbekendAttribuut(20100501)))
                    {
                        cLaagBAccentAccent = hisPersoonAdresModel;
                    } else if (hisPersoonAdresModel.getMaterieleHistorie().getDatumAanvangGeldigheid()
                            .op(new DatumEvtDeelsOnbekendAttribuut(20100901)))
                    {
                        cLaagBAccentAccentAccent = hisPersoonAdresModel;
                    }
                }
            }
        }

        Assert.assertNotNull(dLaagA);
        Assert.assertNotNull(dLaagB);
        Assert.assertNotNull(dLaagBAccent);
        Assert.assertNotNull(cLaagAAccent);
        Assert.assertNotNull(cLaagBAccentAccent);
        Assert.assertNotNull(cLaagBAccentAccentAccent);
        Assert.assertNotNull(cLaagC);
        Assert.assertNotNull(cLaagD);

        // Controleer alle records:

        /*
         * ______________________________________________________________________________
         * | |____B''__|______D________|___B'''_|
         * | A' |____B'____________________________|_______C_________________
         * |_________________|____B_______________________________________________________
         * |_____A________________________________________________________________________
         * 1-1-10 1-5-10 1-7-10 1-9-10 1-2-12
         */

        checkRecord(dLaagA, tsRegB, tsRegA, new DatumEvtDeelsOnbekendAttribuut(20100101), null, 456L, 123L, null);
        checkRecord(dLaagB, tsRegC, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100501), null, 789L, 456L, null);
        checkRecord(dLaagBAccent, tsRegD, tsRegC, new DatumEvtDeelsOnbekendAttribuut(20100501),
                new DatumEvtDeelsOnbekendAttribuut(20120201), 101112L, 456L, 789L);
        checkRecord(cLaagAAccent, null, tsRegB, new DatumEvtDeelsOnbekendAttribuut(20100101),
                new DatumEvtDeelsOnbekendAttribuut(20100501), null, 123L, 456L);
        checkRecord(cLaagBAccentAccent, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20100501),
                new DatumEvtDeelsOnbekendAttribuut(20100701), null, 456L, 101112L);
        checkRecord(cLaagBAccentAccentAccent, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20100901),
                new DatumEvtDeelsOnbekendAttribuut(20120201), null, 456L, 101112L);
        checkRecord(cLaagD, null, tsRegD, new DatumEvtDeelsOnbekendAttribuut(20100701),
                new DatumEvtDeelsOnbekendAttribuut(20100901), null, 101112L, null);
        checkRecord(cLaagC, null, tsRegC, new DatumEvtDeelsOnbekendAttribuut(20120201), null, null, 789L, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorMetNullWaarde() {
        new MaterieleHistorieSetImpl<AbstractMaterieelHistorischMetActieVerantwoording>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecordMagNietNullZijn() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();
        persoonAdresHisVolledig.getPersoonAdresHistorie().voegToe(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaterieleHistorieMagNietNullZijn() {
        // Deze test gebruikt de 'kale' MaterieleHistorieSetImpl, omdat een getMaterieleHistorie() die
        // null retourneert in de model code op dit moment nergens mogelijk is.
        final MaterieleHistorieSetImpl<AbstractMaterieelHistorischMetActieVerantwoording> materieleHistorieSetImpl =
            new MaterieleHistorieSetImpl<>(new HashSet<AbstractMaterieelHistorischMetActieVerantwoording>());
        materieleHistorieSetImpl.voegToe(new AbstractMaterieelHistorischMetActieVerantwoording() {

            @Override
            public MaterieelHistorisch kopieer() {
                return null;
            }

            @Override
            public MaterieleHistorieModel getMaterieleHistorie() {
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
    public void testDatumAanvangGeldigheidMagNietNullZijn() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();
        final DatumTijdAttribuut tsReg = nieuwRegTijd(2010, 1, 1);
        final HisPersoonAdresModel hisPersoonAdresModel = bouwHisPersoonAdresRecord(tsReg, null, null, 123L, "A");
        persoonAdresHisVolledig.getPersoonAdresHistorie().voegToe(hisPersoonAdresModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDatumTijdRegistatieMagNietNullZijn() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, null, 20100101, null, 123L, "A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDatumTijdVervalMoetNullZijn() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();
        final DatumTijdAttribuut tsReg = nieuwRegTijd(2010, 1, 1);
        final HisPersoonAdresModel hisPersoonAdresModel =
            bouwHisPersoonAdresRecord(tsReg, new DatumEvtDeelsOnbekendAttribuut(20100101), null, 123L, "A");
        hisPersoonAdresModel.getMaterieleHistorie().setDatumTijdVerval(tsReg);
        persoonAdresHisVolledig.getPersoonAdresHistorie().voegToe(hisPersoonAdresModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testActieInhoudMagNietNullZijn() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();
        final DatumTijdAttribuut tsReg = nieuwRegTijd(2010, 1, 1);
        final HisPersoonAdresModel hisPersoonAdresModel =
            bouwHisPersoonAdresRecord(tsReg, new DatumEvtDeelsOnbekendAttribuut(20100101), null, 123L, "A");
        hisPersoonAdresModel.setVerantwoordingInhoud(null);
        persoonAdresHisVolledig.getPersoonAdresHistorie().voegToe(hisPersoonAdresModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testActieVervalMoetNullZijn() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();
        final DatumTijdAttribuut tsReg = nieuwRegTijd(2010, 1, 1);
        final HisPersoonAdresModel hisPersoonAdresModel =
            bouwHisPersoonAdresRecord(tsReg, new DatumEvtDeelsOnbekendAttribuut(20100101), null, 123L, "A");
        hisPersoonAdresModel.setVerantwoordingVerval(hisPersoonAdresModel.getVerantwoordingInhoud());
        persoonAdresHisVolledig.getPersoonAdresHistorie().voegToe(hisPersoonAdresModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testActieAanpassingGeldigheidMoetNullZijn() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();
        final DatumTijdAttribuut tsReg = nieuwRegTijd(2010, 1, 1);
        final HisPersoonAdresModel hisPersoonAdresModel =
            bouwHisPersoonAdresRecord(tsReg, new DatumEvtDeelsOnbekendAttribuut(20100101), null, 123L, "A");
        hisPersoonAdresModel.setVerantwoordingAanpassingGeldigheid(hisPersoonAdresModel.getVerantwoordingInhoud());
        persoonAdresHisVolledig.getPersoonAdresHistorie().voegToe(hisPersoonAdresModel);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testInterneSetIsNietAanpasbaar() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Beginsituatie A:
        final DatumTijdAttribuut regTijdA = nieuwRegTijd(2010, 1, 1);
        final ActieModel actieA =
            new ActieModel(new SoortActieAttribuut(SoortActie.CORRECTIE_ADRES), null, null, null, null, null, null);
        ReflectionTestUtils.setField(actieA, "iD", 123L);
        final PersoonAdresStandaardGroepBericht adresA = new PersoonAdresStandaardGroepBericht();
        adresA.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20100101));
        adresA.setHuisletter(new HuisletterAttribuut("A"));
        adresA.setDatumTijdRegistratie(regTijdA);
        final HisPersoonAdresModel recordA = new HisPersoonAdresModel(null, adresA, adresA, actieA);

        // Voeg A toe:
        persoonAdresHisVolledig.getPersoonAdresHistorie().getHistorie().add(recordA);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorNietAanpasbaar() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, nieuwRegTijd(2010, 1, 1), 20100101, null, 123L,
                "A");
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, nieuwRegTijd(2010, 5, 1), 20100501, null, 456L,
                "B");
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, nieuwRegTijd(2012, 1, 1), 20120101, null, 789L,
                "C");

        final Iterator<HisPersoonAdresModel> iterator = persoonAdresHisVolledig.getPersoonAdresHistorie().iterator();
        iterator.next();
        iterator.remove();
    }

    /**
     * ______________________________________________________________________
     * | |____B''______|____________D____________|_____C'_____
     * | A' |____B'____________________|_______C_________________
     * |_________________|____B_______________________________________________
     * |_____A________________________________________________________________
     * 1-1-10 1-5-10 1-2-11 1-2-12 1-5-12
     * <p/>
     * Beginsituatie: A record. B wordt later toegevoegd op 1-5-2010. C wordt later toegevoeg op 1-2-2012. D wordt later
     * toegevoegd op 1-5-2012 EN overlapt B en C. EindSituatie: 4 D laag records (A, B, B' en C) en 4 C laag records
     * (A', B'', C' en D).
     */
    @Test
    public void testGetHistorieRecord() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = new PersoonAdresHisVolledigImpl(null);

        // Beginsituatie:
        final DatumTijdAttribuut tsRegA = nieuwRegTijd(2010, 1, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegA, 20100101, null, 123L, "A");

        // TussenLiggendeSituatie:
        final DatumTijdAttribuut tsRegB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegB, 20100501, null, 456L, "B");

        // Laatste: voeg C toe:
        final DatumTijdAttribuut tsRegC = nieuwRegTijd(2012, 2, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegC, 20120201, null, 789L, "C");

        // Voeg de overlappende record D toe:
        final DatumTijdAttribuut tsRegD = nieuwRegTijd(2012, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegD, 20110201, 20120501, 101112L, "D");

        // Controleer de aantallen:
        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 8, 4, 4);

        HisPersoonAdresModel dLaagA = null;
        HisPersoonAdresModel dLaagB = null;
        HisPersoonAdresModel cLaagAAccent = null;
        HisPersoonAdresModel dLaagBAccent = null;
        HisPersoonAdresModel dLaagC = null;
        HisPersoonAdresModel cLaagBAccentAccent = null;
        HisPersoonAdresModel cLaagCAccent = null;
        HisPersoonAdresModel cLaagD = null;

        /*
         * ______________________________________________________________________
         * | |____B''______|____________D____________|_____C'_____
         * | A' |____B'____________________|_______C_________________
         * |_________________|____B_______________________________________________
         * |_____A________________________________________________________________
         * 1-1-10 1-5-10 1-2-11 1-2-12 1-5-12
         */

        for (HisPersoonAdresModel hisPersoonAdresModel : persoonAdresHisVolledig.getPersoonAdresHistorie()
                .getHistorie())
        {
            if (hisPersoonAdresModel.getMaterieleHistorie().getDatumTijdVerval() != null) {
                // D laag record
                if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("A")) {
                    dLaagA = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("C")) {
                    dLaagC = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("B")) {
                    // Is het B' of B???
                    if (hisPersoonAdresModel.getMaterieleHistorie().getDatumEindeGeldigheid() != null) {
                        dLaagBAccent = hisPersoonAdresModel;
                    } else {
                        dLaagB = hisPersoonAdresModel;
                    }
                }
            } else {
                // C laag record
                if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("A")) {
                    cLaagAAccent = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("B")) {
                    cLaagBAccentAccent = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("C")) {
                    cLaagCAccent = hisPersoonAdresModel;
                } else if (hisPersoonAdresModel.getHuisletter().getWaarde().equals("D")) {
                    cLaagD = hisPersoonAdresModel;
                }
            }
        }

        Assert.assertNotNull(dLaagA);
        Assert.assertNotNull(dLaagB);
        Assert.assertNotNull(cLaagAAccent);
        Assert.assertNotNull(dLaagBAccent);
        Assert.assertNotNull(dLaagC);
        Assert.assertNotNull(cLaagBAccentAccent);
        Assert.assertNotNull(cLaagCAccent);
        Assert.assertNotNull(cLaagD);

        /*
         * ______________________________________________________________________
         * | |____B''______|____________D____________|_____C'_____
         * | A' |____B'____________________|_______C_________________
         * |_________________|____B_______________________________________________
         * |_____A________________________________________________________________
         * 1-1-10 1-5-10 1-2-11 1-2-12 1-5-12
         */

        final HisPersoonAdresModel actueleRecord = persoonAdresHisVolledig.getPersoonAdresHistorie().getActueleRecord();
        Assert.assertEquals(cLaagCAccent.getMaterieleHistorie().getDatumAanvangGeldigheid(), actueleRecord
                .getMaterieleHistorie().getDatumAanvangGeldigheid());
        Assert.assertEquals(cLaagCAccent.getMaterieleHistorie().getDatumEindeGeldigheid(), actueleRecord
                .getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertEquals(cLaagCAccent.getMaterieleHistorie().getTijdstipRegistratie(), actueleRecord
                .getMaterieleHistorie().getTijdstipRegistratie());
        Assert.assertEquals(cLaagCAccent.getMaterieleHistorie().getDatumTijdVerval(), actueleRecord
                .getMaterieleHistorie().getDatumTijdVerval());

        final HisPersoonAdresModel recordD =
            persoonAdresHisVolledig.getPersoonAdresHistorie().getHistorieRecord(new DatumAttribuut(20120301),
                    new DatumTijdAttribuut(new Date()));
        Assert.assertEquals(cLaagD.getMaterieleHistorie().getDatumAanvangGeldigheid(), recordD.getMaterieleHistorie()
                .getDatumAanvangGeldigheid());
        Assert.assertEquals(cLaagD.getMaterieleHistorie().getDatumEindeGeldigheid(), recordD.getMaterieleHistorie()
                .getDatumEindeGeldigheid());
        Assert.assertEquals(cLaagD.getMaterieleHistorie().getTijdstipRegistratie(), recordD.getMaterieleHistorie()
                .getTijdstipRegistratie());
        Assert.assertEquals(cLaagD.getMaterieleHistorie().getDatumTijdVerval(), recordD.getMaterieleHistorie()
                .getDatumTijdVerval());

        final HisPersoonAdresModel recordAAccent =
            persoonAdresHisVolledig.getPersoonAdresHistorie().getHistorieRecord(new DatumAttribuut(20100401),
                    nieuwRegTijd(2010, 5, 1));
        Assert.assertEquals(cLaagAAccent.getMaterieleHistorie().getDatumAanvangGeldigheid(), recordAAccent
                .getMaterieleHistorie().getDatumAanvangGeldigheid());
        Assert.assertEquals(cLaagAAccent.getMaterieleHistorie().getDatumEindeGeldigheid(), recordAAccent
                .getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertEquals(cLaagAAccent.getMaterieleHistorie().getTijdstipRegistratie(), recordAAccent
                .getMaterieleHistorie().getTijdstipRegistratie());
        Assert.assertEquals(cLaagAAccent.getMaterieleHistorie().getDatumTijdVerval(), recordAAccent
                .getMaterieleHistorie().getDatumTijdVerval());

        debugOut(persoonAdresHisVolledig.getPersoonAdresHistorie());
    }

    /**
     * ________________________________
     * |___A'___|_____C________|____D___
     * |___A____________|_________B_____
     * <p/>
     * 1-1-10 1-3-10 1-5-10 1-7-10 Beginsituatie: A en B record. C en D worden beide toegevoegd. EindSituatie: 2 D laag
     * records en 3 C laag records.
     * <p/>
     * Let op: er wordt een B' aangemaakt na het toevoegen van C, maar dat wordt bij het toevoegen van D weer
     * verwijderd, aangezien B' dan een gelijke tijdstip registratie en tijdstip verval zou krijgen en wordt daarmee
     * overbodig.
     */
    @Test
    public void testWegvallenOnnodigDLaagRecord() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Initialiseer de beginsituatie:
        final DatumTijdAttribuut tsRegAB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegAB, 20100101, null, 123L, "A");
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegAB, 20100501, null, 123L, "B");

        // Voer de adres wijzigingen C en D door:
        final DatumTijdAttribuut tsRegCD = nieuwRegTijd(2010, 7, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegCD, 20100301, 20100701, 456L, "C");
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegCD, 20100701, null, 456L, "D");

        // Controleer de uiteindelijke aantallen:
        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 5, 3, 2);
    }

    /**
     * ____________________________________
     * |___A'___|___B'___|____C___|___A''___
     * |________________A___________________
     * <p/>
     * 1-1-10 1-3-10 1-5-10 1-7-10 Beginsituatie: A record. B en C worden beide toegevoegd. EindSituatie: 1 D laag
     * record en 4 C laag records.
     * <p/>
     * Let op: er wordt een A' aangemaakt na het toevoegen van B, maar dat wordt bij het toevoegen van D weer
     * verwijderd, aangezien A' dan een gelijke tijdstip registratie en tijdstip verval zou krijgen en wordt daarmee
     * overbodig. Dit geldt ook voor het B record, dat meteen weer vervalt als C wordt toegevoegd. Er blijft dus alleen
     * een B' als C laag record over, geen B als D laag record.
     */
    @Test
    public void testWegvallenOnnodigeDLaagRecords() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Initialiseer de beginsituatie:
        final DatumTijdAttribuut tsRegA = nieuwRegTijd(2010, 1, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegA, 20100101, null, 123L, "A");

        // Voer de adres wijzigingen B en C door:
        final DatumTijdAttribuut tsRegBC = nieuwRegTijd(2010, 7, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegBC, 20100301, 20100701, 456L, "B");
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegBC, 20100501, 20100701, 456L, "C");

        // Controleer de uiteindelijke aantallen:
        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 5, 4, 1);
    }

    /**
     * _________ _________
     * |___A'___|_____C________|____B'__
     * |___A____________|_________B_____
     * <p/>
     * 1-1-10 1-3-10 1-5-10 1-7-10 Beginsituatie: A en B record. Over periode C wordt de historie beëindigd (C zelf is
     * dus geen record!). EindSituatie: 2 D laag records en 2 C laag records.
     * <p/>
     * Test voor beeindigen. Slechts 1 scenario, aangezien de historie logica voor beëindigen volledig overlapt met die
     * voor een record toevoegen.
     */
    @Test
    public void testBeeindiging() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Initialiseer de beginsituatie:
        final DatumTijdAttribuut tsRegAB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegAB, 20100101, null, 123L, "A");
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegAB, 20100501, null, 123L, "B");

        // Voer de adres beeindiging over periode C door:
        final DatumTijdAttribuut tsRegC = nieuwRegTijd(2010, 7, 1);

        final MaterieleHistorieImpl materieleHistorie =
            new MaterieleHistorieImpl(new DatumEvtDeelsOnbekendAttribuut(20100301), new DatumEvtDeelsOnbekendAttribuut(
                    20100701));
        final ActieModel actieModel = bouwActie(tsRegC, 456L);
        final HisPersoonAdresModel hisPersoonAdresModel =
            new HisPersoonAdresModel(null, new PersoonAdresStandaardGroepBericht(), materieleHistorie, actieModel);
        persoonAdresHisVolledig.getPersoonAdresHistorie().beeindig(hisPersoonAdresModel, actieModel);

        // Controleer de uiteindelijke aantallen:
        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 4, 2, 2);
    }

    /**
     * _________ _________
     * |___A____________________________
     * <p/>
     * 1-5-10 Beginsituatie: A record. Periode A komt te vervallen. EindSituatie: 1 D laag record
     * </p>
     * Test voor verval van het actuele record.
     */
    @Test
    public void testVervalActueleRecord_SlechtsEenRecord() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Initialiseer de beginsituatie:
        final DatumTijdAttribuut tsRegAB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegAB, 20100101, null, 123L, "A");

        // Voer de adres verval door:
        final DatumTijdAttribuut tsRegC = nieuwRegTijd(2010, 7, 1);
        final ActieModel actieModel = bouwActie(tsRegC, 456L);

        persoonAdresHisVolledig.getPersoonAdresHistorie().vervalActueleRecords(actieModel, tsRegC);

        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 1, 0, 1);
    }

    /**
     * ___________________________
     * |_____A'__________|____B____
     * |_____A_____________________
     * </p>
     * BeginSituatie: A record, A' record, B record
     * EindSituatie: 3 D laag records
     * </p>
     * Test voor het verval van de gehele historie.
     */
    @Test
    public void testVervalActueleRecord_MeerdereRecords() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Initialiseer de beginsituatie:
        final DatumTijdAttribuut tsRegA = nieuwRegTijd(2010, 1, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegA, 20100101, null, 123L, "A");

        // Voer de adres wijziging B door:
        final DatumTijdAttribuut tsRegB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegB, 20100501, null, 456L, "B");

        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 3, 2, 1);

        // Voer de adres verval door:
        final DatumTijdAttribuut tsRegC = nieuwRegTijd(2010, 7, 1);
        final ActieModel actieModel = bouwActie(tsRegC, 456L);

        persoonAdresHisVolledig.getPersoonAdresHistorie().vervalActueleRecords(actieModel, tsRegC);

        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 3, 1, 2);
    }

    @Test(expected = IllegalStateException.class)
    public void testVervalActueleRecord_GeenRecord() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Voer de adres verval door:
        final DatumTijdAttribuut tsRegC = nieuwRegTijd(2010, 7, 1);
        final ActieModel actieModel = bouwActie(tsRegC, 456L);

        persoonAdresHisVolledig.getPersoonAdresHistorie().vervalActueleRecords(actieModel, tsRegC);
    }

    /**
     * ___________________________
     * |_____A'__________|____B____
     * |_____A_____________________
     * </p>
     * BeginSituatie: A record, A' record, B record
     * EindSituatie: 3 D laag records
     * </p>
     * Test voor het verval van de gehele historie.
     */
    @Test
    public void testVervalGeheleHistorie() {
        final PersoonAdresHisVolledigImpl persoonAdresHisVolledig = this.maakNieuwPersoonAdresHisVolledig();

        // Initialiseer de beginsituatie:
        final DatumTijdAttribuut tsRegA = nieuwRegTijd(2010, 1, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegA, 20100101, null, 123L, "A");

        // Voer de adres wijziging B door:
        final DatumTijdAttribuut tsRegB = nieuwRegTijd(2010, 5, 1);
        voegAdresToeAanVolledigeAdresHistorie(persoonAdresHisVolledig, tsRegB, 20100501, null, 456L, "B");

        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 3, 2, 1);

        // Voer de adres verval door:
        final DatumTijdAttribuut tsRegC = nieuwRegTijd(2010, 7, 1);
        final ActieModel actieModel = bouwActie(tsRegC, 456L);

        persoonAdresHisVolledig.getPersoonAdresHistorie().vervalGeheleHistorie(actieModel, tsRegC);

        checkAantallenTotaalCLaagDLaag(persoonAdresHisVolledig, 3, 0, 3);
    }

    private void debugOut(final MaterieleHistorieSet<HisPersoonAdresModel> hisPersoonAdresLijst) {
        final StringBuilder debug = new StringBuilder();
        final String tabTab = "\t\t";
        debug.append("DatumAanvGel").append(tabTab).append("DatumEindGel").append(tabTab).append("DatumTijdReg")
                .append(tabTab).append("DatumTijdVerv").append("\n");
        for (HisPersoonAdresModel o : hisPersoonAdresLijst.getHistorie()) {
            debug.append(o.getMaterieleHistorie().getDatumAanvangGeldigheid()).append(tabTab)
                    .append(o.getMaterieleHistorie().getDatumEindeGeldigheid()).append(tabTab)
                    .append(o.getMaterieleHistorie().getTijdstipRegistratie()).append(tabTab)
                    .append(o.getMaterieleHistorie().getDatumTijdVerval()).append(tabTab).append("\n");
        }
        LOGGER.debug(debug.toString());
    }

    private DatumTijdAttribuut nieuwRegTijd(final int jaar, final int maand, final int dag) {
        final Calendar instance = Calendar.getInstance(TimeZone.getDefault());
        instance.set(Calendar.YEAR, jaar);
        instance.set(Calendar.MONTH, maand - 1);
        instance.set(Calendar.DAY_OF_MONTH, dag);
        return new DatumTijdAttribuut(instance.getTime());
    }

    private void checkAantallenTotaalCLaagDLaag(final PersoonAdresHisVolledigImpl persoonAdresHisVolledig,
            final int totaal, final int claag, final int dlaag)
    {
        Assert.assertEquals("Ongeldig totaal", totaal, claag + dlaag);
        Assert.assertEquals(totaal, persoonAdresHisVolledig.getPersoonAdresHistorie().getAantal());
        Assert.assertEquals(claag, persoonAdresHisVolledig.getPersoonAdresHistorie().getNietVervallenHistorie().size());
        Assert.assertEquals(dlaag, persoonAdresHisVolledig.getPersoonAdresHistorie().getVervallenHistorie().size());
    }

    /**
     * Checkt record.
     *
     * @param actueleEntiteit the actuele entiteit
     * @param verwDatumTijdVerval the verw datum tijd verval
     * @param verwDatumTijdRegistriatie the verw datum tijd registriatie
     * @param verwDatumAanvangGeldigheid the verw datum aanvang geldigheid
     * @param verwDatumEindeGeldigheid the verw datum einde geldigheid
     * @param verwActieVervalId the verw actie verval id
     * @param verwActieInhoudId the verw actie inhoud id
     * @param verwActieAanpassingGeldigheidId the verw actie aanpassing geldigheid id
     */
    private void checkRecord(final HisPersoonAdresModel actueleEntiteit, final DatumTijdAttribuut verwDatumTijdVerval,
            final DatumTijdAttribuut verwDatumTijdRegistriatie,
            final DatumEvtDeelsOnbekendAttribuut verwDatumAanvangGeldigheid,
            final DatumEvtDeelsOnbekendAttribuut verwDatumEindeGeldigheid, final Long verwActieVervalId,
            final Long verwActieInhoudId, final Long verwActieAanpassingGeldigheidId)
    {
        final MaterieleHistorieModel actueel = actueleEntiteit.getMaterieleHistorie();

        Assert.assertEquals(verwDatumTijdVerval, actueel.getDatumTijdVerval());
        Assert.assertEquals(verwDatumTijdRegistriatie, actueel.getTijdstipRegistratie());
        Assert.assertEquals(verwDatumAanvangGeldigheid, actueel.getDatumAanvangGeldigheid());
        Assert.assertEquals(verwDatumEindeGeldigheid, actueel.getDatumEindeGeldigheid());

        if (verwActieVervalId == null) {
            Assert.assertNull(actueleEntiteit.getVerantwoordingVerval());
        } else {
            Assert.assertEquals(verwActieVervalId, actueleEntiteit.getVerantwoordingVerval().getID());
        }

        if (verwActieInhoudId == null) {
            Assert.assertNull(actueleEntiteit.getVerantwoordingInhoud());
        } else {
            Assert.assertEquals(verwActieInhoudId, actueleEntiteit.getVerantwoordingInhoud().getID());
        }

        if (verwActieAanpassingGeldigheidId == null) {
            Assert.assertNull(actueleEntiteit.getVerantwoordingAanpassingGeldigheid());
        } else {
            Assert.assertEquals(verwActieAanpassingGeldigheidId, actueleEntiteit
                    .getVerantwoordingAanpassingGeldigheid().getID());
        }
    }

}
