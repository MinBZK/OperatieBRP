/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Unit test voor generieke zaken die gelden voor alle berichten. In deze test worden zaken getest als het ontbreken
 * van authenticatie informatie, meldingen in het antwoord bericht, etc.
 */
public class GeneriekeWebServiceTest extends AbstractWebServiceTest<BerichtBericht, BerichtVerwerkingsResultaat> {

    @Mock
    private BerichtVerwerker berichtVerwerker;

    @Before
    public void setup() {
        initMocks(BerichtBericht.class, null);
        Mockito.when(berichtVerwerker.verwerkBericht(Matchers.any(BerichtBericht.class),
                Matchers.any(BerichtContext.class)))
                .thenReturn(new BerichtVerwerkingsResultaat(null));
    }

    @Test
    public void testValideEnGeauthenticeerdBericht() {
        BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verify(getBerichtVerwerker())
               .verwerkBericht(Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class));

        Assert.assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtZonderPartijId() {
        initBericht(false, null);

        BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtZonderCertificaat() {
        initWSContext(true, false);

        BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtZonderSignature() {
        initWSContext(false, false);

        BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetNietBijPartijHorendCertificaat() {
        initAuthenticatieService(0);

        BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetBijPartijHorendeMeerdereCertificaten() {
        initAuthenticatieService(2);

        BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtWaarBerichtIdsNietInMessageZitten() {
        initBerichtArchiveringIds(false);

        BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetFoutInAuthenticatieService() {
        Mockito.when(getAuthenticatieService().haalAuthenticatieMiddelenOp(Matchers.anyShort(),
            Matchers.any(X509Certificate.class))).thenThrow(new IllegalArgumentException());

        BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetMeldingenInBerichtVerwerker() {
        initBerichtVerwerker(Arrays.asList(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL0001)),
            BerichtVerwerkingsResultaat.class, false);

        BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verify(getBerichtVerwerker())
               .verwerkBericht(Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class));

        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertEquals(MeldingCode.BRAL0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetFoutInBerichtVerwerker() {
        Mockito.doThrow(new IllegalArgumentException()).when(getBerichtVerwerker())
               .verwerkBericht(Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class));

        BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verify(getBerichtVerwerker())
               .verwerkBericht(Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class));

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtReferentieNummer() {
        BerichtBericht abstractBRPBericht = getBericht();

        BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(abstractBRPBericht);
        Mockito.verify(getBerichtVerwerker())
               .verwerkBericht(Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class));

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        /*Assert.assertEquals(getBericht().getBerichtStuurgegevens().getReferentienummer(),
                resultaat.getBerichtCrossReferentieNummer());*/
    }

    /** {@inheritDoc} */
    @Override
    protected AbstractWebService getNieuweWebServiceVoorTest() {
        return new AbstractWebService<BerichtBericht, BerichtVerwerkingsResultaat>() {
            @Override
            protected BerichtVerwerkingsResultaat verwerkBericht(final BerichtBericht bericht, final BerichtContext context) {
                return berichtVerwerker.verwerkBericht(bericht, context);
            }

            @Override
            protected BerichtVerwerkingsResultaat getResultaatInstantie(final List<Melding> meldingen) {
                return new BerichtVerwerkingsResultaat(meldingen);
            }
        };
    }

    @Override
    protected BerichtVerwerker getBerichtVerwerker() {
        return berichtVerwerker;
    }
}
