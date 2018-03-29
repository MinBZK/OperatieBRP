/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import static org.mockito.Mockito.when;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.service.algemeen.AutorisatieExceptionRegelMatcher;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.cache.PartijCache;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link VrijBerichtAutorisatieServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtAutorisatieServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @InjectMocks
    private VrijBerichtAutorisatieServiceImpl autorisatieService;
    @Mock
    private PartijCache partijCache;
    private Partij zenderVrijBericht;
    private Partij ontvangerVrijBericht;
    private Partij ondertekenaar;
    private Partij transporteur;
    private VrijBerichtVerzoek verzoek;

    @Before
    public void before() {
        zenderVrijBericht = new Partij("zenderVrijBericht", "000123");
        zenderVrijBericht.setDatumIngang(DatumUtil.vandaag());
        zenderVrijBericht.setAfleverpuntVrijBericht("afleverpunt");
        zenderVrijBericht.setOin("111");
        ontvangerVrijBericht = new Partij("ontvangerVrijBericht", "000456");
        ontvangerVrijBericht.setDatumIngang(DatumUtil.vandaag());
        ondertekenaar = new Partij("ondertekenaar", "000789");
        ondertekenaar.setDatumIngang(DatumUtil.vandaag());
        transporteur = new Partij("transporteur", "000987");
        transporteur.setDatumIngang(DatumUtil.vandaag());
        when(partijCache.geefPartij("000123")).thenReturn(zenderVrijBericht);
        when(partijCache.geefPartij("000456")).thenReturn(ontvangerVrijBericht);
        verzoek = new VrijBerichtVerzoek();
        verzoek.getParameters().setZenderVrijBericht("000123");
        verzoek.getParameters().setOntvangerVrijBericht("000456");
        verzoek.setOin(new OIN("111", "111"));
        verzoek.getStuurgegevens().setZendendePartijCode(zenderVrijBericht.getCode());
    }

    @Test
    public void autorisatieExceptieR2460BijZendendePartijDatumIngangNaVandaag() throws AutorisatieException {
        zenderVrijBericht.setDatumIngang(DatumUtil.datumRondVandaag(-1));
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2460));

        autorisatieService.valideerAutorisatie(verzoek);
    }

    @Test
    public void autorisatieExceptieR2460BijZendendePartijDatumEindeVoorVandaag() throws AutorisatieException {
        zenderVrijBericht.setDatumIngang(DatumUtil.vandaag());
        zenderVrijBericht.setDatumEinde(DatumUtil.datumRondVandaag(1));
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2460));

        autorisatieService.valideerAutorisatie(verzoek);
    }

    @Test
    public void autorisatieExceptieR2461BijOntvangendePartijNull() throws AutorisatieException {
        when(partijCache.geefPartij("000456")).thenReturn(null);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2461));

        autorisatieService.valideerAutorisatie(verzoek);
    }

    @Test
    public void autorisatieExceptieR2461BijOntvangendePartijDatumEindeVoorVandaag() throws AutorisatieException {
        zenderVrijBericht.setDatumIngang(DatumUtil.vandaag());
        ontvangerVrijBericht.setDatumIngang(DatumUtil.vandaag());
        ontvangerVrijBericht.setDatumEinde(DatumUtil.datumRondVandaag(1));
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2461));

        autorisatieService.valideerAutorisatie(verzoek);
    }

    @Test
    public void autorisatieExceptieR2461BijOntvangendePartijDatumIngangNaVandaag() throws AutorisatieException {
        zenderVrijBericht.setDatumIngang(DatumUtil.vandaag());
        ontvangerVrijBericht.setDatumIngang(DatumUtil.datumRondVandaag(-1));
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2461));

        autorisatieService.valideerAutorisatie(verzoek);
    }

    @Test
    public void autorisatieExceptieR2470BijGeenAfleverpuntZendendePartij() throws AutorisatieException {
        zenderVrijBericht.setDatumIngang(DatumUtil.vandaag());
        ontvangerVrijBericht.setDatumIngang(DatumUtil.vandaag());
        zenderVrijBericht.setAfleverpuntVrijBericht(null);
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2470));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2454BijZendendePartijZonderDatumIngangVB() throws AutorisatieException {
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2454));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2456BijOntvangendePartijZonderDatumIngangVB() throws AutorisatieException {
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2456));

        autorisatieService.valideerAutorisatieBrpOntvanger(verzoek);
    }

    @Test
    public void autorisatieExceptieR2457BijZendendePartijIndagVBFalse() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(false);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2457));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2457BijZendendePartijDatumIngangVBNaVandaag() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.datumRondVandaag(-1));
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2457));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2457BijZendendePartijDatumEindeVBVoorVandaag() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setDatumEindeVrijBericht(DatumUtil.datumRondVandaag(1));
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2457));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2459BijOntvangendePartijIndagVBFalse() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(false);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2459));

        autorisatieService.valideerAutorisatieBrpOntvanger(verzoek);
    }

    @Test
    public void autorisatieExceptieR2459BijOntvangendePartijDatumIngangNaVandaag() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.datumRondVandaag(-1));
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2459));

        autorisatieService.valideerAutorisatieBrpOntvanger(verzoek);
    }

    @Test
    public void autorisatieExceptieR2459BijOntvangendePartijDatumEindeVoorVandaag() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        ontvangerVrijBericht.setDatumEindeVrijBericht(DatumUtil.datumRondVandaag(1));
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2459));

        autorisatieService.valideerAutorisatieBrpOntvanger(verzoek);
    }

    @Test
    public void autorisatieExceptieR2462BijZendendePartijGeblokkeerdeVBAutorisatie() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setVrijBerichtGeblokkeerd(true);
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2462));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2463BijOntvangendePartijGeblokkeerdeVBAutorisatie() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setVrijBerichtGeblokkeerd(null);
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        ontvangerVrijBericht.setVrijBerichtGeblokkeerd(true);
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2463));

        autorisatieService.valideerAutorisatieBrpOntvanger(verzoek);
    }

    @Test
    public void autorisatieExceptieR2519BijOntvangendePartijGeenAfleverPunt() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setVrijBerichtGeblokkeerd(null);
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2519));

        autorisatieService.valideerAutorisatieBrpOntvanger(verzoek);
    }


    @Test
    public void autorisatieExceptieR2464BijOntbrekendeOinZelfOndertekenaar() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setOin(null);
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2464));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2464BijFouteOinZelfOndertekenaar() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setOin("222");
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2464));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2464BijGeenOinAndereOndertekenaar() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setOndertekenaarVrijBericht(ondertekenaar);
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2464));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2464BijFouteOinAndereOndertekenaar() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setOndertekenaarVrijBericht(ondertekenaar);
        ondertekenaar.setOin("222");
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2464));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void geenAutorisatieExceptieCorrecteOinAndereGeldigeOndertekenaar() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setOndertekenaarVrijBericht(ondertekenaar);
        verzoek.setOin(new OIN("222", "111"));
        ondertekenaar.setOin("222");
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2468CorrecteOinAndereOngeldigeOndertekenaar() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setOndertekenaarVrijBericht(ondertekenaar);
        verzoek.setOin(new OIN("222", "111"));
        ondertekenaar.setOin("222");
        ondertekenaar.setDatumIngang(DatumUtil.datumRondVandaag(-1));
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2468));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2464BijOntbrekendeOinZelfTransporteur() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setOin(null);
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        // Niet mogelijk hier een R2467 uit te laten komen. Voor volledigheid wel opgenomen en zou moeten falen als volgorde verandert.
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2464));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2467BijFouteOinZelfOndertekenaar() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        verzoek.setOin(new OIN("111", "222"));
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2467));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2467BijGeenOinAndereTransporteur() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setTransporteurVrijBericht(transporteur);
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2467));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2467BijFouteOinAndereTransporteur() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setTransporteurVrijBericht(transporteur);
        transporteur.setOin("222");
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2467));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void geenAutorisatieExceptieCorrecteOinAndereGeldigeTransporteur() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setTransporteurVrijBericht(transporteur);
        verzoek.setOin(new OIN("111", "222"));
        transporteur.setOin("222");
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2469CorrecteOinAndereOngeldigeTransporteur() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setTransporteurVrijBericht(transporteur);
        verzoek.setOin(new OIN("111", "222"));
        transporteur.setOin("222");
        transporteur.setDatumIngang(DatumUtil.datumRondVandaag(-1));
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2469));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2498zenderVrijBerichtIsOngelijkAanZendendePartij() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        verzoek.getStuurgegevens().setZendendePartijCode("999");
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2498));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void autorisatieExceptieR2498BijZendendePartijNull() throws AutorisatieException {
        when(partijCache.geefPartij("000123")).thenReturn(null);
        expectedException.expect(new AutorisatieExceptionRegelMatcher(Regel.R2498));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void geenAutorisatieExceptieZonderDatumEinde() throws AutorisatieException {
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

    @Test
    public void geenAutorisatieExceptieMetDatumEinde() throws AutorisatieException {
        zenderVrijBericht.setDatumEinde(DatumUtil.datumRondVandaag(-1));
        ontvangerVrijBericht.setDatumEinde(DatumUtil.datumRondVandaag(-1));
        zenderVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        ontvangerVrijBericht.setActueelEnGeldigVoorVrijBericht(true);
        zenderVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        zenderVrijBericht.setDatumEindeVrijBericht(DatumUtil.datumRondVandaag(-1));
        ontvangerVrijBericht.setDatumIngangVrijBericht(DatumUtil.vandaag());
        ontvangerVrijBericht.setDatumEindeVrijBericht(DatumUtil.datumRondVandaag(-1));

        autorisatieService.valideerAutorisatieBrpZender(verzoek);
    }

}
