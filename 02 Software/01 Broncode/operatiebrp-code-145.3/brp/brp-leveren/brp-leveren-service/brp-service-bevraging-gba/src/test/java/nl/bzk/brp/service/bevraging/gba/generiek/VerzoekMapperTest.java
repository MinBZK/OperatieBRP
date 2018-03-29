/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.generiek;

import java.util.Arrays;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.gba.domain.bevraging.Persoonsvraag;
import nl.bzk.brp.gba.domain.bevraging.ZoekCriterium;
import nl.bzk.brp.service.bevraging.gba.persoon.PersoonsvraagVerzoek;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class VerzoekMapperTest {

    private Autorisatiebundel bundel;

    @Mock
    private ToegangLeveringsAutorisatie toegangLeveringsAutorisatie;

    @Mock
    private Partij ondertekenaar;

    @Mock
    private Partij transporteur;

    @Mock
    private PartijRol geautoriseerdeRol;

    @Mock
    private Partij geautoriseerde;

    @Mock
    private Dienst dienst;

    @Mock
    private Leveringsautorisatie leveringsautorisatie;

    @Test
    public void testMapAlgemeen() {
        bundel = new Autorisatiebundel(toegangLeveringsAutorisatie, dienst);
        Mockito.when(toegangLeveringsAutorisatie.getOndertekenaar()).thenReturn(ondertekenaar);
        Mockito.when(ondertekenaar.getOin()).thenReturn("oinOndertekenaar");
        Mockito.when(toegangLeveringsAutorisatie.getTransporteur()).thenReturn(transporteur);
        Mockito.when(transporteur.getOin()).thenReturn("oinTransporteur");
        Mockito.when(dienst.getId()).thenReturn(42);
        Mockito.when(toegangLeveringsAutorisatie.getLeveringsautorisatie()).thenReturn(leveringsautorisatie);
        Mockito.when(leveringsautorisatie.getId()).thenReturn(24);
        Mockito.when(geautoriseerdeRol.getRol()).thenReturn(Rol.AFNEMER);
        Mockito.when(toegangLeveringsAutorisatie.getGeautoriseerde()).thenReturn(geautoriseerdeRol);
        Mockito.when(geautoriseerdeRol.getPartij()).thenReturn(geautoriseerde);
        Mockito.when(geautoriseerde.getCode()).thenReturn("000012");

        PersoonsvraagVerzoek verzoek = VerzoekMapper.mapAlgemeen(new PersoonsvraagVerzoek(), bundel, "berichtreferentie", SoortDienst.ZOEK_PERSOON);

        Assert.assertEquals("oinOndertekenaar", verzoek.getOin().getOinWaardeOndertekenaar());
        Assert.assertEquals("oinTransporteur", verzoek.getOin().getOinWaardeTransporteur());
        Assert.assertEquals(SoortDienst.ZOEK_PERSOON, verzoek.getSoortDienst());
        Assert.assertEquals("berichtreferentie", verzoek.getParameters().getCommunicatieId());
        Assert.assertEquals("42", verzoek.getParameters().getDienstIdentificatie());
        Assert.assertEquals("24", verzoek.getParameters().getLeveringsAutorisatieId());
        Assert.assertEquals(Rol.AFNEMER.getNaam(), verzoek.getParameters().getRolNaam());
        Assert.assertEquals("berichtreferentie", verzoek.getStuurgegevens().getCommunicatieId());
        Assert.assertEquals("berichtreferentie", verzoek.getStuurgegevens().getReferentieNummer());
        Assert.assertEquals("000012", verzoek.getStuurgegevens().getZendendePartijCode());
        Assert.assertNull(verzoek.getXmlBericht());
        Assert.assertNull(verzoek.getStuurgegevens().getTijdstipVerzending());
        Assert.assertNull(verzoek.getStuurgegevens().getZendendSysteem());

    }

    @Test
    public void testMapAlgemeenZonderOINvanOndertekenaarEnTransporteur() {
        bundel = new Autorisatiebundel(toegangLeveringsAutorisatie, dienst);
        Mockito.when(toegangLeveringsAutorisatie.getOndertekenaar()).thenReturn(ondertekenaar);
        Mockito.when(ondertekenaar.getOin()).thenReturn(null);
        Mockito.when(toegangLeveringsAutorisatie.getTransporteur()).thenReturn(transporteur);
        Mockito.when(transporteur.getOin()).thenReturn(null);
        Mockito.when(dienst.getId()).thenReturn(42);
        Mockito.when(toegangLeveringsAutorisatie.getLeveringsautorisatie()).thenReturn(leveringsautorisatie);
        Mockito.when(leveringsautorisatie.getId()).thenReturn(24);
        Mockito.when(geautoriseerdeRol.getRol()).thenReturn(Rol.AFNEMER);
        Mockito.when(toegangLeveringsAutorisatie.getGeautoriseerde()).thenReturn(geautoriseerdeRol);
        Mockito.when(geautoriseerdeRol.getPartij()).thenReturn(geautoriseerde);
        Mockito.when(geautoriseerde.getCode()).thenReturn("000012");

        PersoonsvraagVerzoek verzoek = VerzoekMapper.mapAlgemeen(new PersoonsvraagVerzoek(), bundel, "berichtreferentie", SoortDienst.ZOEK_PERSOON);

        Assert.assertNull(verzoek.getOin().getOinWaardeOndertekenaar());
        Assert.assertNull(verzoek.getOin().getOinWaardeTransporteur());
        Assert.assertEquals(SoortDienst.ZOEK_PERSOON, verzoek.getSoortDienst());
        Assert.assertEquals("berichtreferentie", verzoek.getParameters().getCommunicatieId());
        Assert.assertEquals("42", verzoek.getParameters().getDienstIdentificatie());
        Assert.assertEquals("24", verzoek.getParameters().getLeveringsAutorisatieId());
        Assert.assertEquals(Rol.AFNEMER.getNaam(), verzoek.getParameters().getRolNaam());
        Assert.assertEquals("berichtreferentie", verzoek.getStuurgegevens().getCommunicatieId());
        Assert.assertEquals("berichtreferentie", verzoek.getStuurgegevens().getReferentieNummer());
        Assert.assertEquals("000012", verzoek.getStuurgegevens().getZendendePartijCode());
        Assert.assertNull(verzoek.getXmlBericht());
        Assert.assertNull(verzoek.getStuurgegevens().getTijdstipVerzending());
        Assert.assertNull(verzoek.getStuurgegevens().getZendendSysteem());

    }

    @Test
    public void testMapZoekPersoonVerzoekLeeg() {
        bundel = new Autorisatiebundel(toegangLeveringsAutorisatie, dienst);
        Mockito.when(toegangLeveringsAutorisatie.getOndertekenaar()).thenReturn(ondertekenaar);
        Mockito.when(ondertekenaar.getOin()).thenReturn("oinOndertekenaar");
        Mockito.when(toegangLeveringsAutorisatie.getTransporteur()).thenReturn(transporteur);
        Mockito.when(transporteur.getOin()).thenReturn("oinTransporteur");
        Mockito.when(dienst.getId()).thenReturn(42);
        Mockito.when(toegangLeveringsAutorisatie.getLeveringsautorisatie()).thenReturn(leveringsautorisatie);
        Mockito.when(leveringsautorisatie.getId()).thenReturn(24);
        Mockito.when(geautoriseerdeRol.getRol()).thenReturn(Rol.AFNEMER);
        Mockito.when(toegangLeveringsAutorisatie.getGeautoriseerde()).thenReturn(geautoriseerdeRol);
        Mockito.when(geautoriseerdeRol.getPartij()).thenReturn(geautoriseerde);
        Mockito.when(geautoriseerde.getCode()).thenReturn("000012");

        Persoonsvraag vraag = new Persoonsvraag();
        PersoonsvraagVerzoek verzoek = VerzoekMapper.mapZoekPersoonVerzoek(PersoonsvraagVerzoek.class, vraag, bundel, "berichtreferentie");

        Assert.assertTrue(verzoek.getGevraagdeRubrieken().isEmpty());
        Assert.assertTrue(verzoek.getZoekRubrieken().isEmpty());
        Assert.assertTrue(verzoek.getZoekCriteriaPersoon().isEmpty());
        Assert.assertNull(verzoek.getParameters().getZoekBereikParameters().getZoekBereik());
        Assert.assertNull(verzoek.getParameters().getZoekBereikParameters().getPeilmomentMaterieel());
        Assert.assertEquals("oinOndertekenaar", verzoek.getOin().getOinWaardeOndertekenaar());
        Assert.assertEquals("oinTransporteur", verzoek.getOin().getOinWaardeTransporteur());
        Assert.assertNull(verzoek.getSoortDienst());
        Assert.assertEquals("berichtreferentie", verzoek.getParameters().getCommunicatieId());
        Assert.assertEquals("42", verzoek.getParameters().getDienstIdentificatie());
        Assert.assertEquals("24", verzoek.getParameters().getLeveringsAutorisatieId());
        Assert.assertEquals(Rol.AFNEMER.getNaam(), verzoek.getParameters().getRolNaam());
        Assert.assertEquals("berichtreferentie", verzoek.getStuurgegevens().getCommunicatieId());
        Assert.assertEquals("berichtreferentie", verzoek.getStuurgegevens().getReferentieNummer());
        Assert.assertEquals("000012", verzoek.getStuurgegevens().getZendendePartijCode());
        Assert.assertNull(verzoek.getXmlBericht());
        Assert.assertNull(verzoek.getStuurgegevens().getTijdstipVerzending());
        Assert.assertNull(verzoek.getStuurgegevens().getZendendSysteem());
    }

    @Test
    public void testMapZoekPersoonVerzoek() {
        bundel = new Autorisatiebundel(toegangLeveringsAutorisatie, dienst);
        Mockito.when(toegangLeveringsAutorisatie.getOndertekenaar()).thenReturn(ondertekenaar);
        Mockito.when(ondertekenaar.getOin()).thenReturn("oinOndertekenaar");
        Mockito.when(toegangLeveringsAutorisatie.getTransporteur()).thenReturn(transporteur);
        Mockito.when(transporteur.getOin()).thenReturn("oinTransporteur");
        Mockito.when(dienst.getId()).thenReturn(42);
        Mockito.when(toegangLeveringsAutorisatie.getLeveringsautorisatie()).thenReturn(leveringsautorisatie);
        Mockito.when(leveringsautorisatie.getId()).thenReturn(24);
        Mockito.when(geautoriseerdeRol.getRol()).thenReturn(Rol.AFNEMER);
        Mockito.when(toegangLeveringsAutorisatie.getGeautoriseerde()).thenReturn(geautoriseerdeRol);
        Mockito.when(geautoriseerdeRol.getPartij()).thenReturn(geautoriseerde);
        Mockito.when(geautoriseerde.getCode()).thenReturn("000012");

        Persoonsvraag vraag = new Persoonsvraag();
        ZoekCriterium criterium1 = new ZoekCriterium("Persoon.Identificatienummers.Administratienummer");
        criterium1.setWaarde("1234567890");
        ZoekCriterium criterium2 = new ZoekCriterium("Persoon.Identificatienummers.Burgerservicenummer");
        criterium2.setWaarde("123456789");
        vraag.setZoekCriteria(Arrays.asList(criterium1, criterium2));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "01.01.20"));
        vraag.setZoekRubrieken(Arrays.asList("01.01.10", "01.01.20"));
        vraag.setZoekenInHistorie();
        PersoonsvraagVerzoek verzoek = VerzoekMapper.mapZoekPersoonVerzoek(PersoonsvraagVerzoek.class, vraag, bundel, "berichtreferentie");

        Assert.assertEquals(2, verzoek.getGevraagdeRubrieken().size());
        Assert.assertEquals(2, verzoek.getZoekRubrieken().size());
        Assert.assertEquals(2, verzoek.getZoekCriteriaPersoon().size());
        Assert.assertEquals(Zoekbereik.MATERIELE_PERIODE, verzoek.getParameters().getZoekBereikParameters().getZoekBereik());
        Assert.assertNull(verzoek.getParameters().getZoekBereikParameters().getPeilmomentMaterieel());
        Assert.assertEquals("oinOndertekenaar", verzoek.getOin().getOinWaardeOndertekenaar());
        Assert.assertEquals("oinTransporteur", verzoek.getOin().getOinWaardeTransporteur());
        Assert.assertNull(verzoek.getSoortDienst());
        Assert.assertEquals("berichtreferentie", verzoek.getParameters().getCommunicatieId());
        Assert.assertEquals("42", verzoek.getParameters().getDienstIdentificatie());
        Assert.assertEquals("24", verzoek.getParameters().getLeveringsAutorisatieId());
        Assert.assertEquals(Rol.AFNEMER.getNaam(), verzoek.getParameters().getRolNaam());
        Assert.assertEquals("berichtreferentie", verzoek.getStuurgegevens().getCommunicatieId());
        Assert.assertEquals("berichtreferentie", verzoek.getStuurgegevens().getReferentieNummer());
        Assert.assertEquals("000012", verzoek.getStuurgegevens().getZendendePartijCode());
        Assert.assertNull(verzoek.getXmlBericht());
        Assert.assertNull(verzoek.getStuurgegevens().getTijdstipVerzending());
        Assert.assertNull(verzoek.getStuurgegevens().getZendendSysteem());
    }
}
