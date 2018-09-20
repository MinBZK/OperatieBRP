/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import static nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut.bouwDatumTijd;
import static org.junit.Assert.assertEquals;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.util.VerantwoordingTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test om het administratieve handeling delta predikaat te testen op een materiele historie groep.
 * De volgende historie wordt hieronder in setUp() opgezet voor de voornaam:
 *
 * Naam	    tsreg	    tsverval	datAanvGel	datEindeGel	actieInhoud	actieAanpGeld	actieVerval
 * Peter	21-4-1970	1-1-1991	21-4-1970	            actie1		                actie2
 * Peter	1-1-1991	            21-4-1970	1-1-1991	actie1	    actie2
 * Oussama	1-1-1991	1-1-2001	1-1-1991	            actie2		                actie3
 * Oussama	1-1-2001	            1-1-1991	1-1-2001	actie2	    actie3
 * Koenraad	1-1-2001	            1-1-2001		        actie3
 *
 * Actie 1: 21-4-1970, Perer wordt geboren en krijgt dus een naam.
 * Actie 2: 1-1-1991, Peter gaat Oussama heten.
 * Actie 3: 1-1-2001, Oussama gaat Koenraad heten.
 *
 * De specs voor het Delta predikaat zijn als volgt:
 *
 * De functionele specs hiervoor zijn als volgt (VR00079):
 * Bij een Mutatielevering worden in het resultaat alleen groepen opgenomen die voldoen aan minstens één van de volgende voorwaarden:
 * - ActieInhoud hoort bij de Administratieve handelingen en ActieAanpassingGeldigheid is leeg
 * - ActieAanpassinGeldigheid hoort bij de Administratieve handeling
 * - ActieVerval hoort bij de Admninistratieve handeling
 */
public class PersoonHisVolledigAdministratieveHandelingDetltaViewVoornamenTest {

    private static final Long ADM_HND_ID_1 = 1L;
    private static final Long ADM_HND_ID_2 = 2L;
    private static final Long ADM_HND_ID_3 = 3L;

    private PersoonHisVolledigImpl persoonHisVolledig = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(
            SoortPersoon.INGESCHREVENE));

    @Before
    public void setUp() {
        //Actie 1
        final AdministratieveHandelingModel admHndActie1 =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(null, null, null, bouwDatumTijd(1970, 4, 21));
        final ActieModel actie1 =
                VerantwoordingTestUtil.maakActie(admHndActie1, null, null, new DatumEvtDeelsOnbekendAttribuut(19700421),
                                                 null, bouwDatumTijd(1970, 4, 21), null, null);
        ReflectionTestUtils.setField(actie1, "iD", ADM_HND_ID_1);
        ReflectionTestUtils.setField(admHndActie1, "iD", ADM_HND_ID_1);

        //Actie 2
        final AdministratieveHandelingModel admHndActie2 =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(null, null, null, bouwDatumTijd(1991, 1, 1));
        final ActieModel actie2 =
                VerantwoordingTestUtil.maakActie(admHndActie2, null, null, new DatumEvtDeelsOnbekendAttribuut(19910101),
                                                 null, bouwDatumTijd(1991, 1, 1), null, null);
        ReflectionTestUtils.setField(actie2, "iD", ADM_HND_ID_2);
        ReflectionTestUtils.setField(admHndActie2, "iD", ADM_HND_ID_2);

        //Actie 3
        final AdministratieveHandelingModel admHndActie3 =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(null, null, null, bouwDatumTijd(2001, 1, 1));
        final ActieModel actie3 =
                VerantwoordingTestUtil.maakActie(admHndActie3, null, null, new DatumEvtDeelsOnbekendAttribuut(20010101),
                                                 null, bouwDatumTijd(2001, 1, 1), null, null);
        ReflectionTestUtils.setField(actie3, "iD", ADM_HND_ID_3);
        ReflectionTestUtils.setField(admHndActie3, "iD", ADM_HND_ID_3);


        final PersoonVoornaamHisVolledigImpl voornaam =
                new PersoonVoornaamHisVolledigImplBuilder(null, new VolgnummerAttribuut(1), true)
                        .nieuwStandaardRecord(actie1).naam("Peter").eindeRecord(1)
                        .nieuwStandaardRecord(actie2).naam("Oussama").eindeRecord(2)
                        .nieuwStandaardRecord(actie3).naam("Koenraad").eindeRecord(3)
                        .build();
        persoonHisVolledig.getVoornamen().add(voornaam);
    }

    @Test
    public void testViewDeltaPredikaatHandeling1() {
        final AdministratieveHandelingDeltaPredikaat predikaat = new AdministratieveHandelingDeltaPredikaat(ADM_HND_ID_1);
        final PersoonHisVolledigView persoonHisVolledigView =
                new PersoonHisVolledigView(persoonHisVolledig, predikaat, DatumTijdAttribuut.nu());

        final PersoonVoornaamHisVolledig voornaam = persoonHisVolledigView.getVoornamen().iterator().next();
        assertEquals(1, voornaam.getPersoonVoornaamHistorie().getHistorie().size());
        assertEquals(ADM_HND_ID_1,
                     voornaam.getPersoonVoornaamHistorie().getHistorie().iterator().next()
                             .getVerantwoordingInhoud().getAdministratieveHandeling().getID());
        /**
         * Naam	    tsreg	    tsverval	datAanvGel	datEindeGel	actieInhoud	actieAanpGeld	actieVerval
         * Peter	21-4-1970	1-1-1991	21-4-1970	            actie1		                actie2
         */
        final String verwachteStringRepresentatieVanRecord = "Peter-19700421-19910101-19700421-null-actie1-null-actie2";
        Assert.assertEquals(
                verwachteStringRepresentatieVanRecord,
                bouwStringRepresentatieVanHistorieRecord(voornaam.getPersoonVoornaamHistorie().getHistorie().iterator().next()));
    }

    @Test
    public void testViewDeltaPredikaatHandeling2() {
        final AdministratieveHandelingDeltaPredikaat predikaat = new AdministratieveHandelingDeltaPredikaat(ADM_HND_ID_2);
        final PersoonHisVolledigView persoonHisVolledigView =
                new PersoonHisVolledigView(persoonHisVolledig, predikaat, DatumTijdAttribuut.nu());

        final PersoonVoornaamHisVolledig voornaam = persoonHisVolledigView.getVoornamen().iterator().next();
        assertEquals(3, voornaam.getPersoonVoornaamHistorie().getHistorie().size());
        /*
        * Naam	    tsreg	    tsverval	datAanvGel	datEindeGel	actieInhoud	actieAanpGeld	actieVerval
        * Peter	    21-4-1970	1-1-1991	21-4-1970	            actie1		                actie2
        * Peter	    1-1-1991	            21-4-1970	1-1-1991	actie1	    actie2
        * Oussama	1-1-1991	1-1-2001	1-1-1991	            actie2		                actie3
        */

        final String verwachteStringRepresentatieVanRecord1 = "Peter-19700421-19910101-19700421-null-actie1-null-actie2";
        final String verwachteStringRepresentatieVanRecord2 = "Peter-19910101-null-19700421-19910101-actie1-actie2-null";
        final String verwachteStringRepresentatieVanRecord3 = "Oussama-19910101-20010101-19910101-null-actie2-null-actie3";

        boolean record1Gematcht = false;
        boolean record2Gematcht = false;
        boolean record3Gematcht = false;

        for (final HisPersoonVoornaamModel hisPersoonVoornaamModel : voornaam.getPersoonVoornaamHistorie().getHistorie()) {
            final String recordStringRepresentatie = bouwStringRepresentatieVanHistorieRecord(hisPersoonVoornaamModel);
            if (recordStringRepresentatie.equals(verwachteStringRepresentatieVanRecord1)) {
                record1Gematcht = true;
            } else if (recordStringRepresentatie.equals(verwachteStringRepresentatieVanRecord2)) {
                record2Gematcht = true;
            } else if (recordStringRepresentatie.equals(verwachteStringRepresentatieVanRecord3)) {
                record3Gematcht = true;
            } else {
                Assert.fail("Dit record wordt niet verwacht uit de view! : " + recordStringRepresentatie);
            }
        }

        Assert.assertTrue("Record 1 zit niet in de geretourneede historie van de view!", record1Gematcht);
        Assert.assertTrue("Record 2 zit niet in de geretourneede historie van de view!", record2Gematcht);
        Assert.assertTrue("Record 3 zit niet in de geretourneede historie van de view!", record3Gematcht);
    }

    @Test
    public void testViewDeltaPredikaatHandeling3() {
        final AdministratieveHandelingDeltaPredikaat predikaat = new AdministratieveHandelingDeltaPredikaat(ADM_HND_ID_3);
        final PersoonHisVolledigView persoonHisVolledigView =
                new PersoonHisVolledigView(persoonHisVolledig, predikaat, DatumTijdAttribuut.nu());

        final PersoonVoornaamHisVolledig voornaam = persoonHisVolledigView.getVoornamen().iterator().next();
        assertEquals(3, voornaam.getPersoonVoornaamHistorie().getHistorie().size());
       /*
        * Oussama	1-1-1991	1-1-2001	1-1-1991	            actie2		                actie3
        * Oussama	1-1-2001	            1-1-1991	1-1-2001	actie2	    actie3
        * Koenraad	1-1-2001	            1-1-2001		        actie3
        */
        final String verwachteStringRepresentatieVanRecord1 = "Oussama-19910101-20010101-19910101-null-actie2-null-actie3";
        final String verwachteStringRepresentatieVanRecord2 = "Oussama-20010101-null-19910101-20010101-actie2-actie3-null";
        final String verwachteStringRepresentatieVanRecord3 = "Koenraad-20010101-null-20010101-null-actie3-null-null";

        boolean record1Gematcht = false;
        boolean record2Gematcht = false;
        boolean record3Gematcht = false;

        for (final HisPersoonVoornaamModel hisPersoonVoornaamModel : voornaam.getPersoonVoornaamHistorie().getHistorie()) {
            final String recordStringRepresentatie = bouwStringRepresentatieVanHistorieRecord(hisPersoonVoornaamModel);
            if (recordStringRepresentatie.equals(verwachteStringRepresentatieVanRecord1)) {
                record1Gematcht = true;
            } else if (recordStringRepresentatie.equals(verwachteStringRepresentatieVanRecord2)) {
                record2Gematcht = true;
            } else if (recordStringRepresentatie.equals(verwachteStringRepresentatieVanRecord3)) {
                record3Gematcht = true;
            } else {
                Assert.fail("Dit record wordt niet verwacht uit de view! : " + recordStringRepresentatie);
            }
        }

        Assert.assertTrue("Record 1 zit niet in de geretourneede historie van de view!", record1Gematcht);
        Assert.assertTrue("Record 2 zit niet in de geretourneede historie van de view!", record2Gematcht);
        Assert.assertTrue("Record 3 zit niet in de geretourneede historie van de view!", record3Gematcht);

    }

    private String bouwStringRepresentatieVanHistorieRecord(final HisPersoonVoornaamModel record) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(record.getNaam().getWaarde()).append("-")
                     .append(datumTijdNaarString(record.getTijdstipRegistratie())).append("-");
        if(record.getDatumTijdVerval() != null) {
            stringBuilder.append(datumTijdNaarString(record.getDatumTijdVerval())).append("-");
        } else {
            stringBuilder.append("null").append("-");
        }

        stringBuilder.append(record.getDatumAanvangGeldigheid().getWaarde()).append("-");

        if (record.getDatumEindeGeldigheid() != null) {
            stringBuilder.append(record.getDatumEindeGeldigheid().getWaarde()).append("-");
        } else {
            stringBuilder.append("null").append("-");
        }

        stringBuilder.append("actie").append(record.getVerantwoordingInhoud().getID());

        if (record.getVerantwoordingAanpassingGeldigheid() != null) {
            stringBuilder.append("-").append("actie").append(record.getVerantwoordingAanpassingGeldigheid().getID());
        } else {
            stringBuilder.append("-null");
        }

        if (record.getVerantwoordingVerval() != null) {
            stringBuilder.append("-").append("actie").append(record.getVerantwoordingVerval().getID());
        } else {
            stringBuilder.append("-null");
        }
        return stringBuilder.toString();
    }

    private String datumTijdNaarString(final DatumTijdAttribuut datumTijdAttribuut) {
        return new DatumAttribuut(datumTijdAttribuut.getWaarde()).getWaarde().toString();
    }
}
