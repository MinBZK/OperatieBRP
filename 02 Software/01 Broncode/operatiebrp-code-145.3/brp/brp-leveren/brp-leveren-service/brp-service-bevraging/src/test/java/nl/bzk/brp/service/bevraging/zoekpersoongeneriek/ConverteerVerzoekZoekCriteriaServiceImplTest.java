/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.service.algemeen.StapException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * ConverteerVerzoekZoekCriteriaServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConverteerVerzoekZoekCriteriaServiceImplTest {

    @InjectMocks
    private ConverteerVerzoekZoekCriteriaServiceImpl service;

    @Mock
    private ZoekCriteriaWaardeConverteerService converteerWaardeService;

    @Test
    public void testConverteerIndicatieZoekCriteria() throws StapException {
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium1 = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriterium1.setElementNaam(getAttribuutElement(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_WAARDE.getId()).getNaam());
        zoekCriterium1.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium1.setWaarde("J");
        final SoortIndicatie soortIndicatie1 = SoortIndicatie.BEHANDELD_ALS_NEDERLANDER;

        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium2 = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriterium2.setElementNaam(getAttribuutElement(Element.PERSOON_INDICATIE_STAATLOOS_WAARDE.getId()).getNaam());
        zoekCriterium2.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium2.setWaarde("J");
        final SoortIndicatie soortIndicatie2 = SoortIndicatie.STAATLOOS;

        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium3 = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriterium3.setElementNaam(getAttribuutElement(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_WAARDE.getId()).getNaam());
        zoekCriterium3.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium3.setWaarde("J");
        final SoortIndicatie soortIndicatie3 = SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER;

        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium4 = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriterium4.setElementNaam(getAttribuutElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE.getId()).getNaam());
        zoekCriterium4.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium4.setWaarde("J");
        final SoortIndicatie soortIndicatie4 = SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING;

        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium5 = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriterium5.setElementNaam(getAttribuutElement(Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE.getId()).getNaam());
        zoekCriterium5.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium5.setWaarde("J");
        final SoortIndicatie soortIndicatie5 = SoortIndicatie.ONDER_CURATELE;

        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium6 = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriterium6.setElementNaam(getAttribuutElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_WAARDE.getId()).getNaam());
        zoekCriterium6.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium6.setWaarde("J");
        final SoortIndicatie soortIndicatie6 = SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE;

        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium7 = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriterium7.setElementNaam(getAttribuutElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_WAARDE.getId()).getNaam());
        zoekCriterium7.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium7.setWaarde("J");
        final SoortIndicatie soortIndicatie7 = SoortIndicatie.DERDE_HEEFT_GEZAG;

        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium8 = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriterium8
                .setElementNaam(getAttribuutElement(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_WAARDE.getId()).getNaam());
        zoekCriterium8.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium8.setWaarde("J");
        final SoortIndicatie soortIndicatie8 = SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT;

        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium9 = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriterium9.setElementNaam(getAttribuutElement(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_WAARDE.getId()).getNaam());
        zoekCriterium9.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium9.setWaarde("J");
        final SoortIndicatie soortIndicatie9 = SoortIndicatie.ONVERWERKT_DOCUMENT_AANWEZIG;

        controleerSoortIndicatie(zoekCriterium1, soortIndicatie1);
        controleerSoortIndicatie(zoekCriterium2, soortIndicatie2);
        controleerSoortIndicatie(zoekCriterium3, soortIndicatie3);
        controleerSoortIndicatie(zoekCriterium4, soortIndicatie4);
        controleerSoortIndicatie(zoekCriterium5, soortIndicatie5);
        controleerSoortIndicatie(zoekCriterium6, soortIndicatie6);
        controleerSoortIndicatie(zoekCriterium7, soortIndicatie7);
        controleerSoortIndicatie(zoekCriterium8, soortIndicatie8);
        controleerSoortIndicatie(zoekCriterium9, soortIndicatie9);
    }

    private void controleerSoortIndicatie(ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium, final SoortIndicatie soortIndicatie) throws StapException {
        final Set<ZoekPersoonGeneriekVerzoek.ZoekCriteria> zoekCriteriaVerzoek = Sets.newHashSet(zoekCriterium);

        Mockito.when(converteerWaardeService.converteerWaarde(Mockito.any(), Mockito.any())).thenReturn("dummy");

        final Set<ZoekCriterium> zoekCriteria = service.maakZoekCriteria(zoekCriteriaVerzoek);

        Assert.assertEquals(1, zoekCriteria.size());
        final ZoekCriterium zoekCriteriumSoortIndicatie = Iterables.tryFind(zoekCriteria,
                zoekCriterium1 -> zoekCriterium1.getAdditioneel() != null && zoekCriterium1.getAdditioneel().getWaarde().equals(soortIndicatie.getId())).orNull();
        Assert.assertNotNull(zoekCriteriumSoortIndicatie);
    }


    @Test
    public void testConverteerZoekCriteria() throws StapException {
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriterium = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriterium.setElementNaam(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()).getNaam());
        zoekCriterium.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium.setWaarde("123");

        final Set<ZoekPersoonGeneriekVerzoek.ZoekCriteria> zoekCriteriaVerzoek = Sets.newHashSet(zoekCriterium);

        Mockito.when(converteerWaardeService.converteerWaarde(Mockito.any(), Mockito.any())).thenReturn("dummy");

        final Set<ZoekCriterium> zoekCriteria = service.maakZoekCriteria(zoekCriteriaVerzoek);

        Assert.assertEquals(zoekCriteriaVerzoek.size(), zoekCriteria.size());
    }

    @Test
    public void testConverteerZoekCriteriaOf() throws StapException {
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteriumBsn1 = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriteriumBsn1.setElementNaam(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()).getNaam());
        zoekCriteriumBsn1.setZoekOptie(Zoekoptie.EXACT);
        zoekCriteriumBsn1.setWaarde("123");

        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteriumBsnOf1 = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriteriumBsnOf1.setElementNaam(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()).getNaam());
        zoekCriteriumBsnOf1.setZoekOptie(Zoekoptie.EXACT);
        zoekCriteriumBsnOf1.setWaarde("1234");

        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteriumBsnOf2 = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriteriumBsnOf2.setElementNaam(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()).getNaam());
        zoekCriteriumBsnOf2.setZoekOptie(Zoekoptie.EXACT);
        zoekCriteriumBsnOf2.setWaarde("1234");

        zoekCriteriumBsn1.setOf(zoekCriteriumBsnOf1);
        zoekCriteriumBsnOf1.setOf(zoekCriteriumBsnOf2);

        final Set<ZoekPersoonGeneriekVerzoek.ZoekCriteria> zoekCriteriaVerzoek = Sets.newHashSet(zoekCriteriumBsn1);

        Mockito.when(converteerWaardeService.converteerWaarde(Mockito.any(), Mockito.any())).thenReturn("dummy");

        final Set<ZoekCriterium> zoekCriteria = service.maakZoekCriteria(zoekCriteriaVerzoek);

        Assert.assertEquals(zoekCriteriaVerzoek.size(), zoekCriteria.size());

        final List<ZoekCriterium> plat = slaPlat(zoekCriteria);

        Assert.assertEquals(3, plat.size());

    }

    private List<ZoekCriterium> slaPlat(final Set<ZoekCriterium> zoekCriteria) {
        final List<ZoekCriterium> zoekCriteriaPlat = new ArrayList<>();
        for (ZoekCriterium criterium : zoekCriteria) {
            voegOfToe(criterium, zoekCriteriaPlat);
        }
        return zoekCriteriaPlat;
    }

    private void voegOfToe(final ZoekCriterium criterium, final List<ZoekCriterium> zoekCriteriaPlat) {
        if (criterium == null) {
            return;
        }
        zoekCriteriaPlat.add(criterium);
        voegOfToe(criterium.getOf(), zoekCriteriaPlat);
    }
}
