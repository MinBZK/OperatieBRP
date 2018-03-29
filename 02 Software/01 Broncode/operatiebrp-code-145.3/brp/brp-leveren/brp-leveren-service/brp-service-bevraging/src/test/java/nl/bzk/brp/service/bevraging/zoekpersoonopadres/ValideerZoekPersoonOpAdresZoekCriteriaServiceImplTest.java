/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoonopadres;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
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

/**
 * ValideerZoekPersoonOpAdresZoekCriteriaServiceImplTest.
 */
public class ValideerZoekPersoonOpAdresZoekCriteriaServiceImplTest {

    private ValideerZoekCriteriaService<ZoekPersoonOpAdresVraag> valideerZoekPersoonOpAdresZoekCriteriaService = new
            ValideerZoekPersoonOpAdresZoekCriteriaServiceImpl();

    @Test
    public void testValideWoonplaatsIdentificerend() {
        final ZoekPersoonOpAdresVerzoek bevragingVerzoek = new ZoekPersoonOpAdresVerzoek();
        final AttribuutElement woonplaats = getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId());
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel();
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(woonplaats, "2", Zoekoptie.EXACT));

        final Set<Melding> meldingen = valideerZoekPersoonOpAdresZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testValideWoonplaatsIdentificerendNietExact() {
        final ZoekPersoonOpAdresVerzoek bevragingVerzoek = new ZoekPersoonOpAdresVerzoek();
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId());
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel();
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(attribuutElement, "2", Zoekoptie.LEEG));

        final Set<Melding> meldingen = valideerZoekPersoonOpAdresZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testValideGemeenteCodeIdentificerend() {
        final ZoekPersoonOpAdresVerzoek bevragingVerzoek = new ZoekPersoonOpAdresVerzoek();
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId());
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel();
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(attribuutElement, "2", Zoekoptie.EXACT));

        final Set<Melding> meldingen = valideerZoekPersoonOpAdresZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testGeenAdresGegevens() {
        final ZoekPersoonOpAdresVerzoek bevragingVerzoek = new ZoekPersoonOpAdresVerzoek();
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel();
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(attribuutElement, "2", Zoekoptie.EXACT));

        final Set<Melding> meldingen = valideerZoekPersoonOpAdresZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testNietValideAlleenHuisnummerPlusWelIdentificerend() {
        final ZoekPersoonOpAdresVerzoek bevragingVerzoek = new ZoekPersoonOpAdresVerzoek();
        final AttribuutElement gemeente = getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId());
        final AttribuutElement huisnummer = getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId());
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel();
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(gemeente, "2", Zoekoptie.EXACT));
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(huisnummer, "2", Zoekoptie.EXACT));

        final Set<Melding> meldingen = valideerZoekPersoonOpAdresZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testValideHuisnummerPlusNor() {
        final ZoekPersoonOpAdresVerzoek bevragingVerzoek = new ZoekPersoonOpAdresVerzoek();
        final AttribuutElement gemeente = getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId());
        final AttribuutElement huisnummer = getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId());
        final AttribuutElement naamopenbareruimte = getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId());
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel();
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(gemeente, "2", Zoekoptie.EXACT));
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(huisnummer, "2", Zoekoptie.EXACT));
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(naamopenbareruimte, "2", Zoekoptie.EXACT));

        final Set<Melding> meldingen = valideerZoekPersoonOpAdresZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testNietValideHuisnummerPlusNorNietExact() {
        final ZoekPersoonOpAdresVerzoek bevragingVerzoek = new ZoekPersoonOpAdresVerzoek();
        final AttribuutElement gemeente = getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId());
        final AttribuutElement huisnummer = getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId());
        final AttribuutElement naamopenbareruimte = getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId());
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel();
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(gemeente, "2", Zoekoptie.EXACT));
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(huisnummer, "2", Zoekoptie.EXACT));
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(naamopenbareruimte, "2", Zoekoptie.VANAF_KLEIN));

        final Set<Melding> meldingen = valideerZoekPersoonOpAdresZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertFalse(meldingen.isEmpty());
    }


    @Test
    public void testValideAlleenNorPlusWelIdentificerendPostcodeExact() {
        final ZoekPersoonOpAdresVerzoek bevragingVerzoek = new ZoekPersoonOpAdresVerzoek();
        final AttribuutElement postcode = getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId());
        final AttribuutElement nor = getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId());
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel();
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(postcode, "2", Zoekoptie.EXACT));
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(nor, "2", Zoekoptie.EXACT));

        final Set<Melding> meldingen = valideerZoekPersoonOpAdresZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testValideAlleenNorPlusWelIdentificerendPostcodeVanafKlein() {
        final ZoekPersoonOpAdresVerzoek bevragingVerzoek = new ZoekPersoonOpAdresVerzoek();
        final AttribuutElement postcode = getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId());
        final AttribuutElement nor = getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId());
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel();
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(postcode, "2", Zoekoptie.VANAF_KLEIN));
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(nor, "2", Zoekoptie.EXACT));

        final Set<Melding> meldingen = valideerZoekPersoonOpAdresZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testNietValideNorPlusWoonplaatsPlusWelIdentificerend() {
        final ZoekPersoonOpAdresVerzoek bevragingVerzoek = new ZoekPersoonOpAdresVerzoek();
        final AttribuutElement woonplaats = getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId());
        final AttribuutElement gemeente = getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId());
        final AttribuutElement nor = getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId());
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel();
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(woonplaats, "2", Zoekoptie.EXACT));
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(gemeente, "2", Zoekoptie.EXACT));
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(nor, "2", Zoekoptie.EXACT));

        final Set<Melding> meldingen = valideerZoekPersoonOpAdresZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testNietValideNorPlusWoonplaatsLeegPlusWelIdentificerend() {
        final ZoekPersoonOpAdresVerzoek bevragingVerzoek = new ZoekPersoonOpAdresVerzoek();
        final AttribuutElement woonplaats = getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId());
        final AttribuutElement postcode = getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId());
        final AttribuutElement nor = getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId());
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel();
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(postcode, "2", Zoekoptie.EXACT));
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(woonplaats, "2", Zoekoptie.LEEG));
        bevragingVerzoek.getZoekCriteriaPersoon().add(maakCriterium(nor, "2", Zoekoptie.EXACT));

        final Set<Melding> meldingen = valideerZoekPersoonOpAdresZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertFalse(meldingen.isEmpty());
    }


    private AbstractZoekPersoonVerzoek.ZoekCriteria maakCriterium(final AttribuutElement attribuutElement, final String waarde,
                                                                  final Zoekoptie zoekoptie) {
        final AbstractZoekPersoonVerzoek.ZoekCriteria criterium = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        criterium.setElementNaam(attribuutElement.getNaam());
        criterium.setWaarde(waarde);
        criterium.setZoekOptie(zoekoptie);
        return criterium;
    }

    private Autorisatiebundel maakAutorisatiebundel() {
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
        return autorisatiebundel;
    }
}
