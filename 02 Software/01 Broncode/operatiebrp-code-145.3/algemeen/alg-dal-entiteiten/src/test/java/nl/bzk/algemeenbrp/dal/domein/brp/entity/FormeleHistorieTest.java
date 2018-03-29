/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testen voor {@link FormeleHistorie}.
 */
public class FormeleHistorieTest {

    private final static LandOfGebied NL = new LandOfGebied("6030", "Nederland");

    protected Persoon persoon;

    @Before
    public void setup() {
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
    }

    /**
     * <ul>
     * <li>2013-1-1 |___________A______________</li>
     * </ul>
     * <p>
     * Er was geen record, en opeens komt er een record.
     */
    @Test
    public void testVanGeenHistorieNaar1Record() {
        // setup
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2013, 1, 1);
        final PersoonInschrijvingHistorie inschrijvingA =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieA, maakAdministratieveHandling(datumTijdRegistratieA));
        // execute
        FormeleHistorie.voegToe(inschrijvingA, persoon.getPersoonInschrijvingHistorieSet());
        // valideer
        assertEquals(1, persoon.getPersoonInschrijvingHistorieSet().size());

        final FormeleHistorie inschrijvingResultA = persoon.getPersoonInschrijvingHistorieSet().iterator().next();

        assertVoorkomen(inschrijvingResultA, inschrijvingA.getDatumTijdRegistratie(), null, inschrijvingA.getActieInhoud().getId(), null);
    }

    /**
     * <ul>
     * <li>2013-1-1 |___________B______________</li>
     * </ul>
     * <p>
     * Er was geen record, en eerst wordt record A toegevoegd en daarna record B met dezelfde tsreg
     */
    @Test
    public void testDubbeleToevoegingZelfdeTsReg() {
        // setup
        final Timestamp datumTijdRegistratieAenB = maakDatumTijd(2013, 1, 1);
        final AdministratieveHandeling administratieveHandeling = maakAdministratieveHandling(datumTijdRegistratieAenB);
        final PersoonInschrijvingHistorie inschrijvingA = maakInschrijvingVoorkomen(persoon, datumTijdRegistratieAenB, administratieveHandeling);
        final PersoonInschrijvingHistorie inschrijvingB = maakInschrijvingVoorkomen(persoon, datumTijdRegistratieAenB, administratieveHandeling);

        // execute
        FormeleHistorie.voegToe(inschrijvingA, persoon.getPersoonInschrijvingHistorieSet());
        FormeleHistorie.voegToe(inschrijvingB, persoon.getPersoonInschrijvingHistorieSet());
        // valideer
        assertEquals(1, persoon.getPersoonInschrijvingHistorieSet().size());

        final FormeleHistorie inschrijvingResultB = persoon.getPersoonInschrijvingHistorieSet().iterator().next();

        assertVoorkomen(inschrijvingResultB, inschrijvingB.getDatumTijdRegistratie(), null, inschrijvingB.getActieInhoud().getId(), null);
    }

    /**
     * <ul>
     * <li>2013-07-30 |_____________B_____________________</li>
     * <li>2013-01-01 |_____________A_____________________</li>
     * </ul>
     * <p>
     * Er is een A record en er komt een B record.
     */
    @Test
    public void testNormaleWijziging() {
        // Maak record A aan.
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2013, 1, 1);
        final PersoonInschrijvingHistorie inschrijvingA =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieA, maakAdministratieveHandling(datumTijdRegistratieA));
        // execute A
        FormeleHistorie.voegToe(inschrijvingA, persoon.getPersoonInschrijvingHistorieSet());

        assertEquals(1, persoon.getPersoonInschrijvingHistorieSet().size());

        // Voeg record B toe:
        final Timestamp datumTijdRegistratieB = maakDatumTijd(2013, 7, 30);
        final PersoonInschrijvingHistorie inschrijvingB =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieB, maakAdministratieveHandling(datumTijdRegistratieB));
        // execute B
        FormeleHistorie.voegToe(inschrijvingB, persoon.getPersoonInschrijvingHistorieSet());

        assertEquals(2, persoon.getPersoonInschrijvingHistorieSet().size());

        FormeleHistorie inschrijvingResultA = null;
        FormeleHistorie inschrijvingResultB = null;

        for (final PersoonInschrijvingHistorie voorkomen : persoon.getPersoonInschrijvingHistorieSet()) {
            if (voorkomen.getDatumTijdVerval() != null) {
                inschrijvingResultA = voorkomen;
            } else {
                inschrijvingResultB = voorkomen;
            }
        }

        assertNotNull(inschrijvingResultA);
        assertNotNull(inschrijvingResultB);

        // Check d laag A record.
        assertVoorkomen(
            inschrijvingResultA,
            datumTijdRegistratieA,
            datumTijdRegistratieB,
            inschrijvingA.getActieInhoud().getId(),
            inschrijvingB.getActieInhoud().getId());

        // Check c laag B record.
        assertVoorkomen(inschrijvingResultB, datumTijdRegistratieB, null, inschrijvingB.getActieInhoud().getId(), null);
    }

    /**
     * <ul>
     * <li>2013-07-30 |_____________B'_____________________</li>
     * <li>2013-07-30 |_____________B_____________________</li>
     * <li>2013-01-01 |_____________A_____________________</li>
     * </ul>
     * <p>
     * Er is een A record en er komt een B record en vervolgens komt er nog een B' (zelfde inhoud en tsreg, andere
     * actie) record op, waarbij de oorspronkelijke B verwijderd wordt.
     */
    @Test
    public void testNormaleWijzigingMetDezelfdeWijzigingNogmaalsDoorgevoerd() {
        // Maak record A aan.
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2013, 1, 1);
        final PersoonInschrijvingHistorie inschrijvingA =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieA, maakAdministratieveHandling(datumTijdRegistratieA));
        // execute A
        FormeleHistorie.voegToe(inschrijvingA, persoon.getPersoonInschrijvingHistorieSet());

        assertEquals(1, persoon.getPersoonInschrijvingHistorieSet().size());

        // Voeg record B toe:
        final Timestamp datumTijdRegistratieB = maakDatumTijd(2013, 7, 30);
        final PersoonInschrijvingHistorie inschrijvingB =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieB, maakAdministratieveHandling(datumTijdRegistratieB));
        // execute B
        FormeleHistorie.voegToe(inschrijvingB, persoon.getPersoonInschrijvingHistorieSet());

        assertEquals(2, persoon.getPersoonInschrijvingHistorieSet().size());

        FormeleHistorie inschrijvingResultA = null;
        FormeleHistorie inschrijvingResultB = null;

        for (final PersoonInschrijvingHistorie voorkomen : persoon.getPersoonInschrijvingHistorieSet()) {
            if (voorkomen.getDatumTijdVerval() != null) {
                inschrijvingResultA = voorkomen;
            } else {
                inschrijvingResultB = voorkomen;
            }
        }

        assertNotNull(inschrijvingResultA);
        assertNotNull(inschrijvingResultB);

        // Controleer A record vervallen met actie van B record
        assertEquals(inschrijvingB.getActieInhoud(), inschrijvingA.getActieVerval());

        final PersoonInschrijvingHistorie inschrijvingBAccent =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieB, maakAdministratieveHandling(datumTijdRegistratieB));
        // execute B
        FormeleHistorie.voegToe(inschrijvingBAccent, persoon.getPersoonInschrijvingHistorieSet());

        assertEquals(2, persoon.getPersoonInschrijvingHistorieSet().size());

        inschrijvingResultA = null;
        inschrijvingResultB = null;

        for (final PersoonInschrijvingHistorie voorkomen : persoon.getPersoonInschrijvingHistorieSet()) {
            if (voorkomen.getDatumTijdVerval() != null) {
                inschrijvingResultA = voorkomen;
            } else {
                inschrijvingResultB = voorkomen;
            }
        }

        assertNotNull(inschrijvingResultA);
        assertNotNull(inschrijvingResultB);

        // Controleer A record vervallen met actie van B' record
        assertEquals(inschrijvingBAccent.getActieInhoud(), inschrijvingA.getActieVerval());
    }

    /**
     * <ul>
     * <li>2014-1-1 |_____________B________________</li>
     * <li>2013-5-1 _______________________________</li>
     * <li>2013-1-1 |_____________A________________</li>
     * </ul>
     * <p>
     * Let op: A was vervallen op 2013-5-1, en we registereren een nieuwe record pas op 2014-1-1
     */
    @Test
    public void testVanVervallenRecordNaarActieveRecord() {
        // Maak record A aan.
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2013, 1, 1);
        final PersoonInschrijvingHistorie inschrijvingA =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieA, maakAdministratieveHandling(datumTijdRegistratieA));
        // execute A
        FormeleHistorie.voegToe(inschrijvingA, persoon.getPersoonInschrijvingHistorieSet());

        assertEquals(1, persoon.getPersoonInschrijvingHistorieSet().size());

        // Laat de historie vervallen
        final Timestamp datumTijdVervalA = maakDatumTijd(2013, 5, 1);
        final BRPActie actieVervalA = maakActie(maakAdministratieveHandling(datumTijdVervalA));
        inschrijvingA.setDatumTijdVerval(datumTijdVervalA);
        inschrijvingA.setActieVerval(actieVervalA);

        assertEquals(1, persoon.getPersoonInschrijvingHistorieSet().size());

        assertVoorkomen(
            persoon.getPersoonInschrijvingHistorieSet().iterator().next(),
            datumTijdRegistratieA,
            datumTijdVervalA,
            inschrijvingA.getActieInhoud().getId(),
            inschrijvingA.getActieVerval().getId());

        // Voeg record B toe:
        final Timestamp datumTijdRegistratieB = maakDatumTijd(2014, 1, 1);
        final PersoonInschrijvingHistorie inschrijvingB =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieB, maakAdministratieveHandling(datumTijdRegistratieB));
        // execute B
        FormeleHistorie.voegToe(inschrijvingB, persoon.getPersoonInschrijvingHistorieSet());

        assertEquals(2, persoon.getPersoonInschrijvingHistorieSet().size());

        FormeleHistorie inschrijvingResultA = null;
        FormeleHistorie inschrijvingResultB = null;

        for (final PersoonInschrijvingHistorie voorkomen : persoon.getPersoonInschrijvingHistorieSet()) {
            if (voorkomen.getDatumTijdVerval() != null) {
                inschrijvingResultA = voorkomen;
            } else {
                inschrijvingResultB = voorkomen;
            }
        }

        assertNotNull(inschrijvingResultA);
        assertNotNull(inschrijvingResultB);

        // Check d laag A record.
        assertVoorkomen(inschrijvingResultA, datumTijdRegistratieA, datumTijdVervalA, inschrijvingA.getActieInhoud().getId(), actieVervalA.getId());

        // Check c laag B record.
        assertVoorkomen(inschrijvingResultB, datumTijdRegistratieB, null, inschrijvingB.getActieInhoud().getId(), null);

    }

    /**
     * <ul>
     * <li>2013-07-30 |_____________B_____________________</li>
     * <li>2013-01-01 |_____________A_____________________</li>
     * </ul>
     * <p>
     * Er is een A record en er komt een B record.
     */
    @Test
    public void testNormaleWijzigingEnDanVervallen() {
        // Maak record A aan.
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2013, 1, 1);
        final PersoonInschrijvingHistorie inschrijvingA =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieA, maakAdministratieveHandling(datumTijdRegistratieA));
        // execute A
        FormeleHistorie.voegToe(inschrijvingA, persoon.getPersoonInschrijvingHistorieSet());

        // Voeg record B toe:
        final Timestamp datumTijdRegistratieB = maakDatumTijd(2013, 7, 30);
        final PersoonInschrijvingHistorie inschrijvingB =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieB, maakAdministratieveHandling(datumTijdRegistratieB));
        // execute B
        FormeleHistorie.voegToe(inschrijvingB, persoon.getPersoonInschrijvingHistorieSet());
        assertEquals(2,persoon.getPersoonInschrijvingHistorieSet().size());
        final Timestamp dtVerval = maakDatumTijd(2013, 8, 1);
        final BRPActie actieVerval = maakActie(maakAdministratieveHandling(dtVerval));
        FormeleHistorie.laatActueelVoorkomenVervallen(persoon.getPersoonInschrijvingHistorieSet(),actieVerval);
        assertEquals(2,persoon.getPersoonInschrijvingHistorieSet().size());
        for(PersoonInschrijvingHistorie his : persoon.getPersoonInschrijvingHistorieSet()){
            assertNotNull(his.getActieVerval());
            assertNotNull(his.getActieVerval());
            if(his.getDatumTijdRegistratie().equals(datumTijdRegistratieA)){
                assertEquals(datumTijdRegistratieB,his.getDatumTijdVerval());
            } else if(his.getDatumTijdRegistratie().equals(datumTijdRegistratieB)){
                assertEquals(dtVerval,his.getDatumTijdVerval());
            }else {
                Assert.fail();
            }
        }

    }


    @Test(expected = NullPointerException.class)
    public void testActueelVoorkomenisNull() {
        // setup + execute + validate
        FormeleHistorie.voegToe(null, persoon.getPersoonInschrijvingHistorieSet());
    }

    @Test(expected = NullPointerException.class)
    public void testActueelVoorkomenisNietActueelDoorTsReg() {
        // setup
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2013, 1, 1);
        final PersoonInschrijvingHistorie inschrijvingA =
                new PersoonInschrijvingHistorie(persoon, DatumUtil.vanDatumNaarInteger(new Date(datumTijdRegistratieA.getTime())), 0L, datumTijdRegistratieA);
        FormeleHistorie.voegToe(inschrijvingA, persoon.getPersoonInschrijvingHistorieSet());
    }

    @Test(expected = NullPointerException.class)
    public void testActueelVoorkomenisNietActueelDoorActieInhoud() {
        // setup
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2013, 1, 1);
        final PersoonInschrijvingHistorie inschrijvingA =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieA, maakAdministratieveHandling(datumTijdRegistratieA));
        inschrijvingA.setActieInhoud(null);

        FormeleHistorie.voegToe(inschrijvingA, persoon.getPersoonInschrijvingHistorieSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testActueelVoorkomenisNietActueelDoorTsVerval() {
        // setup
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2013, 1, 1);
        final PersoonInschrijvingHistorie inschrijvingA =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieA, maakAdministratieveHandling(datumTijdRegistratieA));
        final Timestamp datumTijdVervalA = maakDatumTijd(2013, 5, 1);
        inschrijvingA.setDatumTijdVerval(datumTijdVervalA);

        FormeleHistorie.voegToe(inschrijvingA, persoon.getPersoonInschrijvingHistorieSet());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testActueelVoorkomenisNietActueelDoorActieVerval() {
        // setup
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2013, 1, 1);
        final PersoonInschrijvingHistorie inschrijvingA =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieA, maakAdministratieveHandling(datumTijdRegistratieA));
        final BRPActie actieVervalA = maakActie(maakAdministratieveHandling(maakDatumTijd(2013, 5, 1)));
        inschrijvingA.setActieVerval(actieVervalA);

        FormeleHistorie.voegToe(inschrijvingA, persoon.getPersoonInschrijvingHistorieSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToevoegenMaterieleHistorieFout() {
        // setup + execute
        FormeleHistorie.voegToe(new PersoonIDHistorie(persoon), persoon.getPersoonIDHistorieSet());
    }

    @Test
    public void testLaatVervallen() {
        // setup
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2013, 1, 1);
        final PersoonInschrijvingHistorie inschrijvingA =
                maakInschrijvingVoorkomen(persoon, datumTijdRegistratieA, maakAdministratieveHandling(datumTijdRegistratieA));
        final BRPActie actieVervalA = maakActie(maakAdministratieveHandling(maakDatumTijd(2013, 5, 1)));
        // validate setup
        assertTrue(inschrijvingA.isActueel());
        // execute
        inschrijvingA.laatVervallen(actieVervalA, 'O');
        // validate execute
        assertFalse(inschrijvingA.isActueel());
        assertEquals(Character.valueOf('O'), inschrijvingA.getNadereAanduidingVerval());
    }

    protected static Timestamp maakDatumTijd(final int jaar, final int maand, final int dag) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DatumUtil.nu());
        calendar.set(Calendar.YEAR, jaar);
        calendar.set(Calendar.MONTH, maand - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dag);
        return new Timestamp(calendar.getTimeInMillis());
    }

    protected static PersoonIDHistorie maakIDVoorkomen(
        final Persoon persoon,
        final Timestamp datumTijdRegistratie,
        final int datumAanvangGeldigheid,
        final AdministratieveHandeling administratieveHandeling)
    {
        final PersoonIDHistorie id = new PersoonIDHistorie(persoon);
        id.setDatumTijdRegistratie(datumTijdRegistratie);
        id.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        id.setActieInhoud(maakActie(administratieveHandeling));
        return id;
    }

    private static PersoonInschrijvingHistorie maakInschrijvingVoorkomen(
        final Persoon persoon,
        final Timestamp datumTijdRegistratie,
        final AdministratieveHandeling administratieveHandeling)
    {
        final PersoonInschrijvingHistorie inschrijving =
                new PersoonInschrijvingHistorie(persoon, DatumUtil.vanDatumNaarInteger(new Date(datumTijdRegistratie.getTime())), 0L, datumTijdRegistratie);
        inschrijving.setDatumTijdRegistratie(datumTijdRegistratie);
        inschrijving.setActieInhoud(maakActie(administratieveHandeling));
        return inschrijving;
    }

    protected static AdministratieveHandeling maakAdministratieveHandling(final Timestamp datumTijdRegistratie) {
        return new AdministratieveHandeling(new Partij("test", "000000"), SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND, datumTijdRegistratie);
    }

    protected static BRPActie maakActie(final AdministratieveHandeling administratieveHandeling) {
        final BRPActie result =
                new BRPActie(
                    SoortActie.REGISTRATIE_GEBORENE,
                    administratieveHandeling,
                    administratieveHandeling.getPartij(),
                    administratieveHandeling.getDatumTijdRegistratie());
        result.setId(new Random().nextLong());
        return result;
    }

    private static void assertVoorkomen(
        final FormeleHistorie voorkomenResultaat,
        final Timestamp verwachteDatumTijdRegistratie,
        final Timestamp verwachteDatumTijdVerval,
        final Long verwachteActieInhoudId,
        final Long verwachteActieVervalId)
    {
        assertEquals(verwachteDatumTijdRegistratie, voorkomenResultaat.getDatumTijdRegistratie());

        if (verwachteDatumTijdVerval == null) {
            Assert.assertNull(voorkomenResultaat.getDatumTijdVerval());
        } else {
            assertEquals(verwachteDatumTijdVerval, voorkomenResultaat.getDatumTijdVerval());
        }

        assertEquals(verwachteActieInhoudId, voorkomenResultaat.getActieInhoud().getId());

        if (verwachteActieVervalId == null) {
            Assert.assertNull(voorkomenResultaat.getActieVerval());
        } else {
            assertEquals(verwachteActieVervalId, voorkomenResultaat.getActieVerval().getId());
        }
    }
}
