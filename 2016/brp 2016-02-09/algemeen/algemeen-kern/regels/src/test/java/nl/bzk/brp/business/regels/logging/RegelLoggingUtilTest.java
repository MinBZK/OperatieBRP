/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.logging;

import java.util.Arrays;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class RegelLoggingUtilTest {

    private static final Integer BSN1 = 111222333;
    private static final Long    ANR1 = 3251361057L;

    private static final String BRLV0001_GEFAALD =
        "Regel gefaald - BRLV0001: Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.";
    private static final String POSTFIX_BSN_ANR  = " (BSN: 111222333) (ANR: 3251361057)";

    @Test
    public void testGeefLogmeldingSucces() {
        final String logMelding = RegelLoggingUtil.geefLogmeldingSucces(Regel.BRLV0001);
        Assert.assertEquals("Regel geslaagd - BRLV0001", logMelding);
    }

    @Test
    public void testGeefLogmeldingSuccesMetPrefix() {
        final String logMelding = RegelLoggingUtil.geefLogmeldingSucces("prefix123 ", Regel.BRLV0001);
        Assert.assertEquals("prefix123 BRLV0001", logMelding);
    }

    @Test
    public void testGeefLogmeldingFout() {
        final String logMelding = RegelLoggingUtil.geefLogmeldingFout(Regel.BRLV0001);
        Assert.assertEquals(BRLV0001_GEFAALD, logMelding);
    }

    @Test
    public void testGeefLogmeldingFoutViaPersoonView() {
        final String logMelding = RegelLoggingUtil.geefLogmeldingFout(Regel.BRLV0001, new PersoonView(
            TestPersoonJohnnyJordaan.maak()));
        Assert.assertEquals(BRLV0001_GEFAALD + " (BSN: 987654321) (ANR: 4253217569)", logMelding);
    }

    @Test
    public void testGeefLogmeldingFoutViaPersoonViewZonderBsnEnAnummer() {
        final String waarde = "waarde";
        final PersoonView persoon = new PersoonView(TestPersoonJohnnyJordaan.maak());
        ReflectionTestUtils.setField(persoon.getIdentificatienummers().getAdministratienummer(), waarde, null);
        ReflectionTestUtils.setField(persoon.getIdentificatienummers().getBurgerservicenummer(), waarde, null);
        persoon.getIdentificatienummers().getAdministratienummer();
        final String logMelding = RegelLoggingUtil.geefLogmeldingFout(Regel.BRLV0001, persoon);
        Assert.assertEquals(BRLV0001_GEFAALD, logMelding);
    }

    @Test
    public void testGeefLogmeldingFoutViaBsnsEnAnrs() {
        final String logMelding = RegelLoggingUtil.geefLogmeldingFout(Regel.BRLV0001, Arrays.asList(BSN1),
            Arrays.asList(ANR1));
        Assert.assertEquals(BRLV0001_GEFAALD + POSTFIX_BSN_ANR, logMelding);
    }

    @Test
    public void testGeefLogmeldingFoutViaZoekCriteriaPersoon() {
        final BerichtBericht bericht = new GeefDetailsPersoonBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut(BSN1));
        bericht.getZoekcriteriaPersoon().setAdministratienummer(new AdministratienummerAttribuut(ANR1));
        final String logMelding = RegelLoggingUtil.geefLogmeldingFout(Regel.BRLV0001, bericht);
        Assert.assertEquals(BRLV0001_GEFAALD + POSTFIX_BSN_ANR, logMelding);
    }

    @Test
    public void testGeefLogmeldingFoutViaBerichtZonderZoekCriteriaPersoon() {
        final BerichtBericht bericht = new GeefDetailsPersoonBericht();
        final String logMelding = RegelLoggingUtil.geefLogmeldingFout(Regel.BRLV0001, bericht);
        Assert.assertEquals(BRLV0001_GEFAALD, logMelding);
    }

    @Test
    public void testGeefLogmeldingFoutViaZoekCriteriaPersoonZonderBsnEnAnr() {
        final BerichtBericht bericht = new GeefDetailsPersoonBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(null);
        bericht.getZoekcriteriaPersoon().setAdministratienummer(null);
        final String logMelding = RegelLoggingUtil.geefLogmeldingFout(Regel.BRLV0001, bericht);
        Assert.assertEquals(BRLV0001_GEFAALD, logMelding);
    }

    @Test
    public void testGeefLogmeldingFoutViaPersoonViewEnPrefix() {
        final String prefix = "prefix 123 ";
        final String logMelding = RegelLoggingUtil.geefLogmeldingFout(prefix, Regel.BRLV0001, new PersoonView(TestPersoonJohnnyJordaan.maak()));
        Assert.assertEquals("prefix 123 BRLV0001: Er bestaat geen geldige afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie. "
            + "(BSN: 987654321) (ANR: 4253217569)", logMelding);
    }

    @Test
    public void testGeefLogmeldingFoutViaNietOndersteundObject() {
        final String logMelding = RegelLoggingUtil.geefLogmeldingFout(Regel.BRLV0001,
            new PersoonModel(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE)));
        Assert.assertEquals(BRLV0001_GEFAALD, logMelding);
    }
}
