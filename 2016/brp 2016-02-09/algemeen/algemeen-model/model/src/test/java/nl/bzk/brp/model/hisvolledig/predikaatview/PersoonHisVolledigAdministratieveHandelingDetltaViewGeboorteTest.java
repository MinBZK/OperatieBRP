/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import static nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut.bouwDatumTijd;

import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.util.VerantwoordingTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test om het administratieve handeling delta predikaat te testen op een formele historie groep.
 * De volgende historie wordt hieronder in setUp() opgezet voor de geboorte groep:
 *
 * DatumGeboorte	    tsreg	    tsverval		actieInhoud		actieVerval
 * 04-04-1983	        01-01-2001	01-01-2002	    actie1		    actie2
 * 04-04-1987	        01-01-2002	01-01-2003      actie2          actie3
 * 04-04-1985	        01-01-2003	        	    actie3
 *
 *
 * De specs voor het Delta predikaat zijn als volgt:
 *
 * De functionele specs hiervoor zijn als volgt (VR00079):
 * Bij een Mutatielevering worden in het resultaat alleen groepen opgenomen die voldoen aan minstens één van de volgende voorwaarden:
 * - ActieInhoud hoort bij de Administratieve handelingen en ActieAanpassingGeldigheid is leeg
 * - ActieAanpassinGeldigheid hoort bij de Administratieve handeling
 * - ActieVerval hoort bij de Admninistratieve handeling
 */
public class PersoonHisVolledigAdministratieveHandelingDetltaViewGeboorteTest {

    private static final Long ADM_HND_ID_1 = 1L;
    private static final Long ADM_HND_ID_2 = 2L;
    private static final Long ADM_HND_ID_3 = 3L;

    private PersoonHisVolledigImpl persoonHisVolledig;

    @Before
    public void setup() {
        //Actie 1
        final AdministratieveHandelingModel admHndActie1 =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(null, null, null, bouwDatumTijd(2001, 1, 1));
        final ActieModel actie1 =
                VerantwoordingTestUtil.maakActie(admHndActie1, null, null, null,
                                                 null, bouwDatumTijd(2001, 1, 1), null, null);
        ReflectionTestUtils.setField(actie1, "iD", ADM_HND_ID_1);
        ReflectionTestUtils.setField(admHndActie1, "iD", ADM_HND_ID_1);

        //Actie 2
        final AdministratieveHandelingModel admHndActie2 =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(null, null, null, bouwDatumTijd(2002, 1, 1));
        final ActieModel actie2 =
                VerantwoordingTestUtil.maakActie(admHndActie2, null, null, null,
                                                 null, bouwDatumTijd(2002, 1, 1), null, null);
        ReflectionTestUtils.setField(actie2, "iD", ADM_HND_ID_2);
        ReflectionTestUtils.setField(admHndActie2, "iD", ADM_HND_ID_2);

        //Actie 3
        final AdministratieveHandelingModel admHndActie3 =
                VerantwoordingTestUtil.bouwAdministratieveHandeling(null, null, null, bouwDatumTijd(2003, 1, 1));
        final ActieModel actie3 =
                VerantwoordingTestUtil.maakActie(admHndActie3, null, null, null,
                                                 null, bouwDatumTijd(2003, 1, 1), null, null);
        ReflectionTestUtils.setField(actie3, "iD", ADM_HND_ID_3);
        ReflectionTestUtils.setField(admHndActie3, "iD", ADM_HND_ID_3);

        persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(actie1).datumGeboorte(19830404).eindeRecord()
                .nieuwGeboorteRecord(actie2).datumGeboorte(19870404).eindeRecord()
                .nieuwGeboorteRecord(actie3).datumGeboorte(19850404).eindeRecord()
                .build();

    }

    @Test
    public void testViewDeltaPredikaatHandeling1() {
        final AdministratieveHandelingDeltaPredikaat predikaat = new AdministratieveHandelingDeltaPredikaat(ADM_HND_ID_1);
        final PersoonHisVolledigView persoonHisVolledigView =
                new PersoonHisVolledigView(persoonHisVolledig, predikaat, DatumTijdAttribuut.nu());

        final FormeleHistorieSet<HisPersoonGeboorteModel> persoonGeboorteHistorie =
                persoonHisVolledigView.getPersoonGeboorteHistorie();

        /**
         * DatumGeboorte	    tsreg	    tsverval		actieInhoud		actieVerval
         * 04-04-1983	        01-01-2001	01-01-2002	    actie1		    actie2
         */
        Assert.assertEquals(1, persoonGeboorteHistorie.getAantal());
        final String verwachteStringPresentatieVanRecord = "19830404-20010101-20020101-actie1-actie2";
        Assert.assertEquals(verwachteStringPresentatieVanRecord,
                            bouwStringRepresentatieVanHistorieRecord(persoonGeboorteHistorie.getHistorie().iterator().next()));
    }

    @Test
    public void testViewDeltaPredikaatHandeling2() {
        final AdministratieveHandelingDeltaPredikaat predikaat = new AdministratieveHandelingDeltaPredikaat(ADM_HND_ID_2);
        final PersoonHisVolledigView persoonHisVolledigView =
                new PersoonHisVolledigView(persoonHisVolledig, predikaat, DatumTijdAttribuut.nu());

        final FormeleHistorieSet<HisPersoonGeboorteModel> persoonGeboorteHistorie =
                persoonHisVolledigView.getPersoonGeboorteHistorie();
        /**
         * DatumGeboorte	    tsreg	    tsverval		actieInhoud		actieVerval
         * 04-04-1983	        01-01-2001	01-01-2002	    actie1		    actie2
         * 04-04-1987	        01-01-2002	01-01-2003      actie2          actie3
         */
        Assert.assertEquals(2, persoonGeboorteHistorie.getAantal());
        final String verwachteStringPresentatieVanRecord1 = "19830404-20010101-20020101-actie1-actie2";
        final String verwachteStringPresentatieVanRecord2 = "19870404-20020101-20030101-actie2-actie3";

        boolean record1Gematcht = false;
        boolean record2Gematcht = false;

        for (HisPersoonGeboorteModel hisPersoonGeboorteModel : persoonGeboorteHistorie.getHistorie()) {
            final String recordStringRepresentatie = bouwStringRepresentatieVanHistorieRecord(hisPersoonGeboorteModel);
            if (recordStringRepresentatie.equals(verwachteStringPresentatieVanRecord1)) {
                record1Gematcht = true;
            } else if (recordStringRepresentatie.equals(verwachteStringPresentatieVanRecord2)) {
                record2Gematcht = true;
            } else {
                Assert.fail("Dit record wordt niet verwacht uit de view! : " + recordStringRepresentatie);
            }
        }

        Assert.assertTrue("Record 1 zit niet in de geretourneede historie van de view!", record1Gematcht);
        Assert.assertTrue("Record 2 zit niet in de geretourneede historie van de view!", record2Gematcht);
    }

    @Test
    public void testViewDeltaPredikaatHandeling3() {
        final AdministratieveHandelingDeltaPredikaat predikaat = new AdministratieveHandelingDeltaPredikaat(ADM_HND_ID_3);
        final PersoonHisVolledigView persoonHisVolledigView =
                new PersoonHisVolledigView(persoonHisVolledig, predikaat, DatumTijdAttribuut.nu());

        final FormeleHistorieSet<HisPersoonGeboorteModel> persoonGeboorteHistorie =
                persoonHisVolledigView.getPersoonGeboorteHistorie();
        /**
        * 04-04-1987	        01-01-2002	01-01-2003      actie2          actie3
        * 04-04-1985	        01-01-2003	        	    actie3
        */
        Assert.assertEquals(2, persoonGeboorteHistorie.getAantal());
        final String verwachteStringPresentatieVanRecord1 = "19870404-20020101-20030101-actie2-actie3";
        final String verwachteStringPresentatieVanRecord2 = "19850404-20030101-null-actie3-null";

        boolean record1Gematcht = false;
        boolean record2Gematcht = false;

        for (HisPersoonGeboorteModel hisPersoonGeboorteModel : persoonGeboorteHistorie.getHistorie()) {
            final String recordStringRepresentatie = bouwStringRepresentatieVanHistorieRecord(hisPersoonGeboorteModel);
            if (recordStringRepresentatie.equals(verwachteStringPresentatieVanRecord1)) {
                record1Gematcht = true;
            } else if (recordStringRepresentatie.equals(verwachteStringPresentatieVanRecord2)) {
                record2Gematcht = true;
            } else {
                Assert.fail("Dit record wordt niet verwacht uit de view! : " + recordStringRepresentatie);
            }
        }

        Assert.assertTrue("Record 1 zit niet in de geretourneede historie van de view!", record1Gematcht);
        Assert.assertTrue("Record 2 zit niet in de geretourneede historie van de view!", record2Gematcht);
    }

    private String bouwStringRepresentatieVanHistorieRecord(final HisPersoonGeboorteModel record) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(record.getDatumGeboorte().getWaarde()).append("-")
                     .append(datumTijdNaarString(record.getTijdstipRegistratie())).append("-");
        if(record.getDatumTijdVerval() != null) {
            stringBuilder.append(datumTijdNaarString(record.getDatumTijdVerval())).append("-");
        } else {
            stringBuilder.append("null").append("-");
        }

        stringBuilder.append("actie").append(record.getVerantwoordingInhoud().getID());

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
