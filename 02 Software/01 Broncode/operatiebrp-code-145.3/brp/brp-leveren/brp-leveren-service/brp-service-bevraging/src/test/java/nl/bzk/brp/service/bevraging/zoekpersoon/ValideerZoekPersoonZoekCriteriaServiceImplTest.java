/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoon;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ValideerZoekCriteriaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * ValideerZoekCriteriaServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ValideerZoekPersoonZoekCriteriaServiceImplTest {

    @InjectMocks
    private ValideerZoekCriteriaService<ZoekPersoonVraag> valideerZoekCriteriaService = new ValideerZoekPersoonZoekCriteriaServiceImpl();


    @Test
    public void testAlleenAdresGegegevens() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId());
        final ZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "4", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2288, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testNietAlleenAdresGegegevens() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId());
        final ZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "4", Zoekoptie.EXACT);

        final AttribuutElement attribuutElement2 = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
        final AbstractZoekPersoonVerzoek.ZoekCriteria criterium2 = maakCriterium(attribuutElement2, "4", Zoekoptie.EXACT);
        bevragingVerzoek.getZoekCriteriaPersoon().add(criterium2);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement, attribuutElement2);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }


    private Autorisatiebundel maakAutorisatiebundel(final boolean historisch, final AttribuutElement... attribuutElements) {
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.ZOEK_PERSOON;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienst.setDienstbundel(dienstbundel);
        final Set<DienstbundelGroep> dienstbundelGroepSet = new HashSet<>();

        if (attribuutElements != null) {
            for (AttribuutElement attribuutElement : attribuutElements) {
                final Element groep = Element.parseId(attribuutElement.getGroepId());
                final DienstbundelGroep dienstbundelGroep = new DienstbundelGroep(dienstbundel, groep, historisch, historisch, historisch);
                dienstbundelGroepSet.add(dienstbundelGroep);
                dienstbundel.setDienstbundelGroepSet(dienstbundelGroepSet);
                final Set<DienstbundelGroepAttribuut> dienstbundelGroepAttrSet = new HashSet<>();
                final Element attribuut = Element.parseId(attribuutElement.getId());
                final DienstbundelGroepAttribuut dienstbundelGroepAttr = new DienstbundelGroepAttribuut(dienstbundelGroep, attribuut);
                dienstbundelGroepAttrSet.add(dienstbundelGroepAttr);
                dienstbundelGroep.setDienstbundelGroepAttribuutSet(dienstbundelGroepAttrSet);
            }
        }

        return autorisatiebundel;
    }

    private ZoekPersoonVerzoek maakBevragingVerzoek(final AttribuutElement attribuutElement, final String waarde, final Zoekoptie zoekoptie) {
        final ZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        final AbstractZoekPersoonVerzoek.ZoekCriteria criterium = maakCriterium(attribuutElement, waarde, zoekoptie);
        bevragingVerzoek.getZoekCriteriaPersoon().add(criterium);
        return bevragingVerzoek;
    }

    private ZoekPersoonVerzoek.ZoekCriteria maakCriterium(final AttribuutElement attribuutElement, final String waarde,
                                                          final Zoekoptie zoekoptie) {
        final ZoekPersoonVerzoek.ZoekCriteria criterium = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        criterium.setElementNaam(attribuutElement.getNaam());
        criterium.setWaarde(waarde);
        criterium.setZoekOptie(zoekoptie);
        return criterium;
    }

}
