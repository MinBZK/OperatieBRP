/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.generiek;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitStandaardGroepModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Generieke unit test voor het testen van de A-Laag afleiding. In deze unit test wordt de A-Laag afleiding van
 * {@link PersoonNationaliteitHisVolledigImpl} getest, maar in principe zouden alle A-Laag afleidingen conform
 * hetzelfde principe moeten werken en daarmee dus voldoen conform de tests in deze klasse.
 */
public class ALaagAfleidingTest {

    private static final String STANDAARD = "standaard";
    private final PersoonHisVolledigImpl persoon       = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(
            SoortPersoon.INGESCHREVENE));
    private final NationaliteitAttribuut nationaliteit = new NationaliteitAttribuut(new Nationaliteit(
            new NationaliteitcodeAttribuut("6030"),
            new NaamEnumeratiewaardeAttribuut("Nederland"),
            null, null));

    private PersoonNationaliteitHisVolledigImpl nationaliteitHisVolledig;

    @Before
    public void init() {
        nationaliteitHisVolledig = new PersoonNationaliteitHisVolledigImpl(persoon, nationaliteit);
        ReflectionTestUtils.setField(nationaliteitHisVolledig, STANDAARD,
                new PersoonNationaliteitStandaardGroepModel(new RedenVerkrijgingNLNationaliteitAttribuut(
                        new RedenVerkrijgingNLNationaliteit(new RedenVerkrijgingCodeAttribuut((short) 10), null, null,
                                null)), null, null, null, null, null));
    }

    @Test
    public void testAlaagAfleidingZonderHisRecord() {
        // Test de initiele vulling qua a-laag zonder his records:
        nationaliteitHisVolledig.leidALaagAf();
        Assert.assertNull(ReflectionTestUtils.getField(nationaliteitHisVolledig, STANDAARD));
    }

    @Test
    public void testAlaagAfleidingMetEnkelActueelHisRecord() {
        nationaliteitHisVolledig.getPersoonNationaliteitHistorie().voegToe(
                bouwNieuwPersNatRecords((short) 1, 20010101, null, 20010101));

        // Test de initiele vulling qua a-laag zonder his records:
        nationaliteitHisVolledig.leidALaagAf();
        Assert.assertNotNull(ReflectionTestUtils.getField(nationaliteitHisVolledig, STANDAARD));
        Assert.assertEquals(Short.valueOf((short) 1), ((PersoonNationaliteitStandaardGroepModel) ReflectionTestUtils
                .getField(nationaliteitHisVolledig, STANDAARD)).getRedenVerkrijging().getWaarde().getCode()
                .getWaarde());
    }

    @Test
    public void testAlaagAfleidingMetMeerdereHisRecordsEnActueelRecord() {
        nationaliteitHisVolledig.getPersoonNationaliteitHistorie().voegToe(
                bouwNieuwPersNatRecords((short) 1, 20010101, null, 20010101));
        nationaliteitHisVolledig.getPersoonNationaliteitHistorie().voegToe(
                bouwNieuwPersNatRecords((short) 2, 20010101, 20100202, 20100202));
        nationaliteitHisVolledig.getPersoonNationaliteitHistorie().voegToe(
                bouwNieuwPersNatRecords((short) 3, 20130101, null, 20130101));

        // Test de initiele vulling qua a-laag zonder his records:
        nationaliteitHisVolledig.leidALaagAf();
        Assert.assertNotNull(ReflectionTestUtils.getField(nationaliteitHisVolledig, STANDAARD));
        Assert.assertEquals(Short.valueOf((short) 3), ((PersoonNationaliteitStandaardGroepModel) ReflectionTestUtils
                .getField(nationaliteitHisVolledig, STANDAARD)).getRedenVerkrijging().getWaarde().getCode()
                .getWaarde());
    }

    @Test
    public void testAlaagAfleidingMetMeerdereHisRecordsMaarGeenActueelRecord() {
        nationaliteitHisVolledig.getPersoonNationaliteitHistorie().voegToe(
                bouwNieuwPersNatRecords((short) 1, 20010101, 20100101, 20010101));
        nationaliteitHisVolledig.getPersoonNationaliteitHistorie().voegToe(
                bouwNieuwPersNatRecords((short) 2, 20080303, 20110202, 20100202));
        nationaliteitHisVolledig.getPersoonNationaliteitHistorie().voegToe(
                bouwNieuwPersNatRecords((short) 3, 20110101, 20130505, 20130101));

        // Test de initiele vulling qua a-laag zonder his records:
        nationaliteitHisVolledig.leidALaagAf();
        Assert.assertNull(ReflectionTestUtils.getField(nationaliteitHisVolledig, STANDAARD));
    }

    /**
     * Instantieert en retourneert een {@link HisPersoonNationaliteitModel} instantie met opgegeven reden verkrijging
     * Nederlandse nationaliteit en opgegeven materiele historie tijdsaspecten.
     *
     * @param redenVerkrijgingCode de code van de reden verkrijging Nederlandse nationaliteit.
     * @param datumAanvang         datum van aanvang van nationaliteit.
     * @param datumEinde           datum van einde van nationaliteit.
     * @param tijdstipRegistratie  tijdstip van registratie.
     * @return een HisPersoonNationaliteitModel.
     */
    private HisPersoonNationaliteitModel bouwNieuwPersNatRecords(final Short redenVerkrijgingCode,
            final Integer datumAanvang, final Integer datumEinde, final Integer tijdstipRegistratie)
    {
        final PersoonNationaliteitStandaardGroepBericht groep = new PersoonNationaliteitStandaardGroepBericht();
        groep.setRedenVerkrijging(new RedenVerkrijgingNLNationaliteitAttribuut(new RedenVerkrijgingNLNationaliteit(
                new RedenVerkrijgingCodeAttribuut(redenVerkrijgingCode), null, null, null)));

        if (datumAanvang != null) {
            groep.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvang));
        }
        if (datumEinde != null) {
            groep.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumEinde));
        }
        if (tijdstipRegistratie != null) {
            groep.setDatumTijdRegistratie(new DatumTijdAttribuut(new DatumAttribuut(tijdstipRegistratie).toDate()));
        }

        final ActieBericht actie = new ActieRegistratieAdresBericht();
        actie.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(tijdstipRegistratie).toDate()));
        return new HisPersoonNationaliteitModel(nationaliteitHisVolledig, groep, groep, new ActieModel(actie, null));
    }

}
