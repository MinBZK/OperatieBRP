/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.security.cert.X509Certificate;
import java.util.Arrays;

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class GeneriekeWebServiceTest extends AbstractWebServiceTest {

    @Test
    public void testValideEnGeauthenticeerdBericht() {
        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBerichtVerwerker(), getBericht());
        Mockito.verify(getBerichtVerwerker())
               .verwerkBericht(Mockito.any(BRPBericht.class), Mockito.any(BerichtContext.class));

        Assert.assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtZonderPartijId() {
        initBericht(false);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBerichtVerwerker(), getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtZonderCertificaat() {
        initWSContext(true, false);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBerichtVerwerker(), getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtZonderSignature() {
        initWSContext(false, false);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBerichtVerwerker(), getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetNietBijPartijHorendCertificaat() {
        initAuthenticatieService(0);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBerichtVerwerker(), getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetBijPartijHorendeMeerdereCertificaten() {
        initAuthenticatieService(2);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBerichtVerwerker(), getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.AUTH0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtWaarBerichtIdsNietInMessageZitten() {
        initBerichtArchiveringIds(false);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBerichtVerwerker(), getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetFoutInAuthenticatieService() {
        Mockito.when(getAuthenticatieService().haalAuthenticatieMiddelenOp(Mockito.any(Integer.class),
            Mockito.any(X509Certificate.class))).thenThrow(new IllegalArgumentException());

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBerichtVerwerker(), getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetMeldingenInBerichtVerwerker() {
        initBerichtVerwerker(Arrays.asList(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL0001)),
            BerichtResultaat.class, false);

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBerichtVerwerker(), getBericht());
        Mockito.verify(getBerichtVerwerker())
               .verwerkBericht(Mockito.any(BRPBericht.class), Mockito.any(BerichtContext.class));

        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(MeldingCode.BRAL0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtMetFoutInBerichtVerwerker() {
        Mockito.doThrow(new IllegalArgumentException()).when(getBerichtVerwerker())
               .verwerkBericht(Mockito.any(BRPBericht.class), Mockito.any(BerichtContext.class));

        BerichtResultaat resultaat = getWebService().voerBerichtUit(getBerichtVerwerker(), getBericht());
        Mockito.verify(getBerichtVerwerker())
               .verwerkBericht(Mockito.any(BRPBericht.class), Mockito.any(BerichtContext.class));

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    /** {@inheritDoc} */
    @Override
    protected AbstractWebService getNieuweWebServiceVoorTest() {
        return new AbstractWebService<BRPBericht>() {
        };
    }

    /** {@inheritDoc} */
    @Override
    protected Class<BRPBericht> getNieuwBerichtClassVoorTest() {
        return BRPBericht.class;
    }
}
