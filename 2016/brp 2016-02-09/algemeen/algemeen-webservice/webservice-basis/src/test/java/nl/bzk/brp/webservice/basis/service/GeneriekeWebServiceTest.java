/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.basis.service;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bijhouding.CorrigeerAdresAntwoordBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;
import nl.bzk.brp.webservice.business.service.AutorisatieException;
import nl.bzk.brp.webservice.business.service.BerichtVerwerker;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtContextBasis;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaatImpl;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;


/**
 * Unit test voor generieke zaken die gelden voor alle berichten. In deze test worden zaken getest als het ontbreken van authenticatie informatie,
 * meldingen in het antwoord bericht, etc.
 */
public class GeneriekeWebServiceTest
    extends AbstractWebServiceTest<BerichtBericht, AbstractBerichtContextBasis, BerichtVerwerkingsResultaat>
{

    @Mock
    @SuppressWarnings("rawtypes")
    private BerichtVerwerker berichtVerwerker;

    private boolean isGeautoriseerd = true;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        initMocks(AntwoordBericht.class, null);
        Mockito.when(
            berichtVerwerker.verwerkBericht(Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class)))
            .thenReturn(new BerichtVerwerkingsResultaatImpl(null));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testValideEnGeauthenticeerdBericht() {
        final BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verify(getBerichtVerwerker()).verwerkBericht(Matchers.any(BerichtBericht.class),
            Matchers.any(BerichtContext.class));

        Assert.assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtMetNietBijPartijHorendCertificaat() {
        isGeautoriseerd = false;

        final BerichtVerwerkingsResultaat resultaat = getWebServiceZonderAuthenticatiemiddel().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertTrue(resultaat.bevatStoppendeFouten());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Regel.AUTH0001, resultaat.getMeldingen().get(0).getRegel());
    }


    @Test
    public void testBerichtWaarBerichtIdsNietInMessageZitten() {
        initBerichtArchiveringIds(false);

        final BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertTrue(resultaat.bevatStoppendeFouten());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Regel.ALG0001, resultaat.getMeldingen().get(0).getRegel());
    }

    @Test
    public void testBerichtWaarInkomendBerichtIdNietInMessageZitten() {
        initBerichtArchiveringIds(false, true);

        final BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertTrue(resultaat.bevatStoppendeFouten());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Regel.ALG0001, resultaat.getMeldingen().get(0).getRegel());
    }

    @Test
    public void testBerichtWaarUitgaandBerichtIdsNietInMessageZitten() {
        initBerichtArchiveringIds(true, false);

        final BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertTrue(resultaat.bevatStoppendeFouten());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Regel.ALG0001, resultaat.getMeldingen().get(0).getRegel());
    }

    @Test
    @Ignore
    public void testBerichtMetFoutInAuthenticatieService() {
        //FIXME fix in functionele stories autaut
        final BerichtVerwerkingsResultaat resultaat = getWebServiceZonderAuthenticatiemiddel().voerBerichtUit(getBericht());
        Mockito.verifyZeroInteractions(getBerichtVerwerker());

        Assert.assertTrue(resultaat.bevatStoppendeFouten());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Regel.AUTH0001, resultaat.getMeldingen().get(0).getRegel());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testBerichtMetMeldingenInBerichtVerwerker() {
        initBerichtVerwerker(Collections.singletonList(new Melding(SoortMelding.WAARSCHUWING, Regel.BRAL0001)),
            BerichtVerwerkingsResultaatImpl.class, false);

        final BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verify(getBerichtVerwerker()).verwerkBericht(Matchers.any(BerichtBericht.class),
            Matchers.any(BerichtContext.class));

        Assert.assertTrue(resultaat.bevatStoppendeFouten());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Regel.BRAL0001, resultaat.getMeldingen().get(0).getRegel());
    }

    @Test
    public void testMaaktEenAntwoordBericht() {
        Mockito.when(getAntwoordBerichtFactory().bouwAntwoordBericht(Mockito.any(Bericht.class),
            Mockito.any(BerichtVerwerkingsResultaat.class)))
            .thenReturn(new CorrigeerAdresAntwoordBericht());
        initBerichtVerwerker(Collections.singletonList(new Melding(SoortMelding.WAARSCHUWING, Regel.BRAL0001)),
            BerichtVerwerkingsResultaatImpl.class, false);

        final AbstractWebService webservice = getWebService();
        final BerichtBericht inBericht = getBericht();
        final BerichtVerwerkingsResultaat resultaat = webservice.voerBerichtUit(inBericht);

        final AntwoordBericht antwoord =
            webservice.stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(inBericht, resultaat);

        Assert.assertNotNull(antwoord);

    }

    @Test
    public void testMaaktEenAntwoordBerichtZonderAdministratieveHandelingEnParameters() {
        Mockito.when(getAntwoordBerichtFactory().bouwAntwoordBericht(Mockito.any(Bericht.class),
            Mockito.any(BerichtVerwerkingsResultaat.class)))
            .thenReturn(new CorrigeerAdresAntwoordBericht());
        initBerichtVerwerker(Collections.singletonList(new Melding(SoortMelding.WAARSCHUWING, Regel.BRAL0001)),
            BerichtVerwerkingsResultaatImpl.class, false);

        final AbstractWebService webservice = getWebService();
        final BerichtBericht inBericht = getBericht();
        final BerichtVerwerkingsResultaat resultaat = webservice.voerBerichtUit(inBericht);

        Mockito.when(resultaat.getAdministratieveHandeling()).thenReturn(null);
        inBericht.setParameters(null);

        final AntwoordBericht antwoord =
            webservice.stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(inBericht, resultaat);

        Assert.assertNotNull(antwoord);

    }


    @Test
    @SuppressWarnings("unchecked")
    public void testBerichtMetFoutInBerichtVerwerker() {
        Mockito.doThrow(new IllegalArgumentException()).when(getBerichtVerwerker())
            .verwerkBericht(Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class));

        final BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(getBericht());
        Mockito.verify(getBerichtVerwerker()).verwerkBericht(Matchers.any(BerichtBericht.class),
            Matchers.any(BerichtContext.class));

        Assert.assertTrue(resultaat.bevatStoppendeFouten());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Regel.ALG0001, resultaat.getMeldingen().get(0).getRegel());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testBerichtReferentieNummer() {
        final BerichtBericht abstractBRPBericht = getBericht();

        final BerichtVerwerkingsResultaat resultaat = getWebService().voerBerichtUit(abstractBRPBericht);
        Mockito.verify(getBerichtVerwerker()).verwerkBericht(Matchers.any(BerichtBericht.class),
            Matchers.any(BerichtContext.class));

        Assert.assertEquals(0, resultaat.getMeldingen().size());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected AbstractWebService<BerichtBericht, AbstractBerichtContextBasis, BerichtVerwerkingsResultaat> getNieuweWebServiceVoorTest() {
        return new AbstractWebService<BerichtBericht, AbstractBerichtContextBasis, BerichtVerwerkingsResultaat>() {

            @Override
            protected void checkAutorisatie(AutorisatieOffloadGegevens autenticatieOffloadGegevens, BerichtBericht bericht) throws AutorisatieException {
                if (!isGeautoriseerd) {
                    throw new AutorisatieException();
                }
            }

            @Override
            protected BerichtVerwerkingsResultaatImpl verwerkBericht(final BerichtBericht bericht,
                final AbstractBerichtContextBasis context)
            {
                return berichtVerwerker.verwerkBericht(bericht, context);
            }

            @Override
            protected AbstractBerichtContextBasis bouwBerichtContext(
                final ReferentienummerAttribuut berichtReferentieNummer, final BerichtenIds berichtenIds,
                final Partij gautoriseerde, final CommunicatieIdMap identificeerbareObjecten)
            {
                return null;
            }

            @Override
            protected BerichtVerwerkingsResultaatImpl getResultaatInstantie(final List<Melding> meldingen) {
                return new BerichtVerwerkingsResultaatImpl(meldingen);
            }
        };
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected BerichtVerwerker getBerichtVerwerker() {
        return berichtVerwerker;
    }


    @Override
    protected AbstractWebService<BerichtBericht, AbstractBerichtContextBasis, BerichtVerwerkingsResultaat> getWebService() {
        final AbstractWebService<BerichtBericht, AbstractBerichtContextBasis, BerichtVerwerkingsResultaat> webService = super.getWebService();
        webService.setAutorisatieService(new AutorisatieService() {
            @Override
            public AutorisatieOffloadGegevens geefAutorisatieOffloadGegevens() {
                final Partij partij = TestPartijBuilder.maker().metCode(123).maak();
                return new AutorisatieOffloadGegevens(partij, partij);
            }

        });

        return webService;
    }


    protected AbstractWebService<BerichtBericht, AbstractBerichtContextBasis, BerichtVerwerkingsResultaat> getWebServiceZonderAuthenticatiemiddel() {
        final AbstractWebService<BerichtBericht, AbstractBerichtContextBasis, BerichtVerwerkingsResultaat> webService = super.getWebService();
        webService.setAutorisatieService(new AutorisatieService() {
            @Override
            public AutorisatieOffloadGegevens geefAutorisatieOffloadGegevens() {
                return null;
            }
        });

        return webService;
    }

}
