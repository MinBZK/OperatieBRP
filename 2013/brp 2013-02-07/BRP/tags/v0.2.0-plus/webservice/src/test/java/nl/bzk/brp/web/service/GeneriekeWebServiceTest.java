/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.dto.AbstractBRPBericht;
import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
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
public class GeneriekeWebServiceTest extends AbstractWebServiceTest<AbstractBRPBericht, BerichtResultaat> {

    @Mock
    private BerichtVerwerker berichtVerwerker;

    @Before
    public void setup() {
        initMocks(AbstractBRPBericht.class);
        Mockito.when(berichtVerwerker.verwerkBericht(Matchers.any(BRPBericht.class), Matchers.any(BerichtContext.class)))
                .thenReturn(new BerichtResultaat(null));
    }

    @Test
    public void testValideEnGeauthenticeerdBericht() {
        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verify(getBerichtVerwerker())
               .verwerkBericht(Matchers.any(BRPBericht.class), Matchers.any(BerichtContext.class));

        Assert.assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtZonderPartijId() {
        initBericht(false);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtZonderCertificaat() {
        initWSContext(true, false);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtZonderSignature() {
        initWSContext(false, false);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetNietBijPartijHorendCertificaat() {
        initAuthenticatieService(0);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetBijPartijHorendeMeerdereCertificaten() {
        initAuthenticatieService(2);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtWaarBerichtIdsNietInMessageZitten() {
        initBerichtArchiveringIds(false);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetFoutInAuthenticatieService() {
        Mockito.when(getAuthenticatieService().haalAuthenticatieMiddelenOp(Matchers.anyShort(),
            Matchers.any(X509Certificate.class))).thenThrow(new IllegalArgumentException());

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetMeldingenInBerichtVerwerker() {
        initBerichtVerwerker(Arrays.asList(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL0001)),
            BerichtResultaat.class, false);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verify(getBerichtVerwerker())
               .verwerkBericht(Matchers.any(BRPBericht.class), Matchers.any(BerichtContext.class));

        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(MeldingCode.BRAL0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetFoutInBerichtVerwerker() {
        Mockito.doThrow(new IllegalArgumentException()).when(getBerichtVerwerker())
               .verwerkBericht(Matchers.any(BRPBericht.class), Matchers.any(BerichtContext.class));

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verify(getBerichtVerwerker())
               .verwerkBericht(Matchers.any(BRPBericht.class), Matchers.any(BerichtContext.class));

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtReferentieNummer() {
        AbstractBRPBericht abstractBRPBericht = getBericht();

        BerichtResultaat resultaat = getWebService().voerBerichtUit(abstractBRPBericht);
        Mockito.verify(getBerichtVerwerker())
               .verwerkBericht(Matchers.any(BRPBericht.class), Matchers.any(BerichtContext.class));

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        /*Assert.assertEquals(getBericht().getBerichtStuurgegevens().getReferentienummer(),
                resultaat.getBerichtCrossReferentieNummer());*/
    }

    /** {@inheritDoc} */
    @Override
    protected AbstractWebService getNieuweWebServiceVoorTest() {
        return new AbstractWebService<AbstractBRPBericht, BerichtResultaat>() {
            @Override
            protected BerichtResultaat verwerkBericht(final BRPBericht bericht, final BerichtContext context) {
                return berichtVerwerker.verwerkBericht(bericht, context);
            }

            @Override
            protected BerichtResultaat getResultaatInstantie(final List<Melding> meldingen) {
                return new BerichtResultaat(meldingen);
            }
        };
    }

    @Override
    protected BerichtVerwerker getBerichtVerwerker() {
        return berichtVerwerker;
    }
}
