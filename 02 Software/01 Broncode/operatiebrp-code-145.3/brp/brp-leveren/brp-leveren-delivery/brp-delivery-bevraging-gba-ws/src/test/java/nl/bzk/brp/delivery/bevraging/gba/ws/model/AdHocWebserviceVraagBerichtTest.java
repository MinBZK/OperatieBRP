/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.model;

import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.param;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.delivery.bevraging.gba.ws.Vragen;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Vraag;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Zoekparameter;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test klasse voor de vraag util.
 */
public class AdHocWebserviceVraagBerichtTest {

    private static final String A_NUMMER = "010110";
    private static final int A_NUMMER_INT = 10110;
    private static final String BSN = "010120";
    private static final int BSN_INT = 10120;
    private static final String GESLACHTSNAAM = "010210";
    private static final int GESLACHTSNAAM_INT = 10210;
    private static final String ZOEK_ARGUMENT_A_NUMMER = "1324567890";
    private static final String ZOEK_ARGUMENT_BSN = "987654321";
    private static final String ZOEK_ARGUMENT_GESLACHTSNAAM = "Janssen";

    @Test
    public void testLeegBericht() {
        Vraag legeVraag = new Vraag();
        AdhocWebserviceVraagBericht leegAdHocWebserviceVraagBericht = new AdhocWebserviceVraagBericht(legeVraag);
        Assert.assertEquals(Collections.emptyList(), leegAdHocWebserviceVraagBericht.getGevraagdeRubrieken());
        Assert.assertEquals(Collections.emptyList(), leegAdHocWebserviceVraagBericht.getZoekCriteria());
        Assert.assertEquals(Collections.emptyList(), leegAdHocWebserviceVraagBericht.getZoekRubrieken());
        Assert.assertEquals(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS, leegAdHocWebserviceVraagBericht.getSoortDienst());
    }

    @Test
    public void testGevuldBericht() {
        Zoekparameter ZOEKPARAMETER_A_NUMMER = new Zoekparameter();
        Zoekparameter ZOEKPARAMETER_BSN = new Zoekparameter();
        Zoekparameter ZOEKPARAMETER_GESLACHTSNAAM = new Zoekparameter();

        Vraag vraag = new Vraag();
        vraag.getMasker().addAll(Arrays.asList(A_NUMMER_INT, BSN_INT, GESLACHTSNAAM_INT, A_NUMMER_INT));
        ZOEKPARAMETER_A_NUMMER.setRubrieknummer(A_NUMMER_INT);
        ZOEKPARAMETER_A_NUMMER.setZoekwaarde(ZOEK_ARGUMENT_A_NUMMER);
        ZOEKPARAMETER_BSN.setRubrieknummer(BSN_INT);
        ZOEKPARAMETER_BSN.setZoekwaarde(ZOEK_ARGUMENT_BSN);
        ZOEKPARAMETER_GESLACHTSNAAM.setRubrieknummer(GESLACHTSNAAM_INT);
        ZOEKPARAMETER_GESLACHTSNAAM.setZoekwaarde(ZOEK_ARGUMENT_GESLACHTSNAAM);
        vraag.getParameters().addAll(Arrays.asList(ZOEKPARAMETER_A_NUMMER, ZOEKPARAMETER_BSN, ZOEKPARAMETER_GESLACHTSNAAM));
        vraag.setIndicatieAdresvraag((byte) 0);
        AdhocWebserviceVraagBericht adHocWebserviceVraagBericht = new AdhocWebserviceVraagBericht(vraag);
        Assert.assertEquals(Arrays.asList(A_NUMMER, BSN, GESLACHTSNAAM, A_NUMMER),
                adHocWebserviceVraagBericht.getGevraagdeRubrieken());
        Assert.assertFalse(adHocWebserviceVraagBericht.isZoekenInHistorie());

        Map<Lo3ElementEnum, String> elementMapCategorie01 = new TreeMap<>();
        elementMapCategorie01.put(Lo3ElementEnum.ELEMENT_0110, ZOEK_ARGUMENT_A_NUMMER);
        elementMapCategorie01.put(Lo3ElementEnum.ELEMENT_0120, ZOEK_ARGUMENT_BSN);
        elementMapCategorie01.put(Lo3ElementEnum.ELEMENT_0210, ZOEK_ARGUMENT_GESLACHTSNAAM);
        Lo3CategorieWaarde referentieCategorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0, elementMapCategorie01);
        Assert.assertEquals(Collections.singletonList(referentieCategorieWaarde), adHocWebserviceVraagBericht.getZoekCriteria());
        Assert.assertEquals(Arrays.asList(A_NUMMER, BSN, GESLACHTSNAAM), adHocWebserviceVraagBericht.getZoekRubrieken());
        Assert.assertEquals(SoortDienst.ZOEK_PERSOON, adHocWebserviceVraagBericht.getSoortDienst());
    }

    @Test
    public void testHistorischZoeken() {
        Zoekparameter ZOEKPARAMETER_A_NUMMER = new Zoekparameter();
        Zoekparameter ZOEKPARAMETER_BSN = new Zoekparameter();
        Zoekparameter ZOEKPARAMETER_GESLACHTSNAAM = new Zoekparameter();

        Vraag vraag = new Vraag();
        vraag.getMasker().addAll(Arrays.asList(A_NUMMER_INT, BSN_INT, GESLACHTSNAAM_INT, A_NUMMER_INT));
        ZOEKPARAMETER_A_NUMMER.setRubrieknummer(A_NUMMER_INT);
        ZOEKPARAMETER_A_NUMMER.setZoekwaarde(ZOEK_ARGUMENT_A_NUMMER);
        ZOEKPARAMETER_BSN.setRubrieknummer(BSN_INT);
        ZOEKPARAMETER_BSN.setZoekwaarde(ZOEK_ARGUMENT_BSN);
        ZOEKPARAMETER_GESLACHTSNAAM.setRubrieknummer(GESLACHTSNAAM_INT);
        ZOEKPARAMETER_GESLACHTSNAAM.setZoekwaarde(ZOEK_ARGUMENT_GESLACHTSNAAM);
        vraag.getParameters().addAll(Arrays.asList(ZOEKPARAMETER_A_NUMMER, ZOEKPARAMETER_BSN, ZOEKPARAMETER_GESLACHTSNAAM));
        vraag.setIndicatieAdresvraag((byte) 0);
        vraag.setIndicatieZoekenInHistorie((byte) 1);
        AdhocWebserviceVraagBericht adHocWebserviceVraagBericht = new AdhocWebserviceVraagBericht(vraag);
        Assert.assertEquals(Arrays.asList(A_NUMMER, BSN, GESLACHTSNAAM, A_NUMMER), adHocWebserviceVraagBericht.getGevraagdeRubrieken());
        Assert.assertTrue(adHocWebserviceVraagBericht.isZoekenInHistorie());
    }

    @Test
    public void isPersoonIdentificatieVoorAdresvraagMetPersoonsidentificatie() {
        Vraag vraag = Vragen.adresvraag(10110, param(10110, "1234657890"));
        Assert.assertEquals(true, new AdhocWebserviceVraagBericht(vraag).isPersoonIdentificatie());
        Assert.assertEquals(false, new AdhocWebserviceVraagBericht(vraag).isAdresIdentificatie());
    }

    @Test
    public void isPersoonIdentificatieVoorAdresvraagMetPersoonsidentificatieEnAdresidentificatie() {
        Vraag vraag = Vragen.adresvraag(10110,
                param(10240, "Jansen"),
                param(81020, "1709"),
                param(81110, "Dorpsstraat"),
                param(81120, "666"));
        Assert.assertEquals(true, new AdhocWebserviceVraagBericht(vraag).isPersoonIdentificatie());
        Assert.assertEquals(false, new AdhocWebserviceVraagBericht(vraag).isAdresIdentificatie());
    }

    @Test
    public void isAdresIdentificatieVoorAdresvraagOpStraatnaam() {
        Vraag vraag = Vragen.adresvraag(10110, param(81110, "Dorpsstraat"));
        Assert.assertEquals(false, new AdhocWebserviceVraagBericht(vraag).isPersoonIdentificatie());
        Assert.assertEquals(true, new AdhocWebserviceVraagBericht(vraag).isAdresIdentificatie());
    }

    @Test
    public void isAdresIdentificatieVoorAdresvraagOpPostcode() {
        Vraag vraag = Vragen.adresvraag(10110, param(81160, "1234AA"));
        Assert.assertEquals(false, new AdhocWebserviceVraagBericht(vraag).isPersoonIdentificatie());
        Assert.assertEquals(true, new AdhocWebserviceVraagBericht(vraag).isAdresIdentificatie());
    }

    @Test
    public void isAdresIdentificatieVoorAdresvraagOpPostcodeEnStraatnaam() {
        Vraag vraag = Vragen.adresvraag(10110, param(81160, "1234AA"), param(81110, "Dorpsstraat"));
        Assert.assertEquals(false, new AdhocWebserviceVraagBericht(vraag).isPersoonIdentificatie());
        Assert.assertEquals(true, new AdhocWebserviceVraagBericht(vraag).isAdresIdentificatie());
    }

    @Test
    public void isPersoonIdentificatieVoorAdresvraagOpPostcodeEnANummer() {
        Vraag vraag = Vragen.adresvraag(10110, param(81160, "1234AA"), param(10110, "1234567890"));
        Assert.assertEquals(true, new AdhocWebserviceVraagBericht(vraag).isPersoonIdentificatie());
        Assert.assertEquals(false, new AdhocWebserviceVraagBericht(vraag).isAdresIdentificatie());
    }

    @Test
    public void isPersoonIdentificatieVoorPersoonsvraagOpPostcode() {
        Vraag vraag = Vragen.persoonsvraag(10110, param(81160, "1234AA"));
        Assert.assertEquals(true, new AdhocWebserviceVraagBericht(vraag).isPersoonIdentificatie());
        Assert.assertEquals(false, new AdhocWebserviceVraagBericht(vraag).isAdresIdentificatie());
    }

    @Test
    public void isAdresIdentificatieVoorAdresOpStraatnaamHuisnummerLocatiebeschrijvingEnVoornaam() {
        Vraag vraag = Vragen.persoonsvraag(10110,
                param(81110, "Dorpsstraat"),
                param(81115, "22"),
                param(81210, "Locatiebeschrijving"),
                param(10210, "Voornaam"));
        Assert.assertEquals(false, new AdhocWebserviceVraagBericht(vraag).isPersoonIdentificatie());
        Assert.assertEquals(false, new AdhocWebserviceVraagBericht(vraag).isAdresIdentificatie());
    }

    @Test
    public void isGeenIdentificatieVoorAdresvraagOpHuisnummer() {
        Vraag vraag = Vragen.adresvraag(10110, param(81120, "2"));
        Assert.assertEquals(false, new AdhocWebserviceVraagBericht(vraag).isPersoonIdentificatie());
        Assert.assertEquals(false, new AdhocWebserviceVraagBericht(vraag).isAdresIdentificatie());
    }

    @Test
    public void adresvraagPersoonIdentificatieHistorischZoeken() {
        Vraag vraag = Vragen.adresvraag(10110, param(10110, "1234657890"));
        vraag.setIndicatieZoekenInHistorie((byte) 1);
        Assert.assertEquals(true, new AdhocWebserviceVraagBericht(vraag).isZoekenInHistorie());
    }

    @Test
    public void adresvraagAdresIdentificatieHistorischZoeken() {
        Vraag vraag = Vragen.adresvraag(10110, param(81110, "Dorpstraat"));
        vraag.setIndicatieZoekenInHistorie((byte) 1);
        Assert.assertEquals(false, new AdhocWebserviceVraagBericht(vraag).isZoekenInHistorie());
    }

    @Test
    public void persoonsvraagHistorischZoeken() {
        Vraag vraag = Vragen.persoonsvraag(10110, param(10110, "1234567890"));
        vraag.setIndicatieZoekenInHistorie((byte) 1);
        Assert.assertEquals(true, new AdhocWebserviceVraagBericht(vraag).isZoekenInHistorie());
    }

    @Test(expected = IllegalArgumentException.class)
    public void persoonsvraagOngeldigeRubriek() {
        Vraag vraag = Vragen.persoonsvraag(10110, param(990110, "1234567890"));
        vraag.setIndicatieZoekenInHistorie((byte) 1);
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(vraag);
        vraagBericht.getZoekCriteria();
    }

    @Test(expected = IllegalArgumentException.class)
    public void persoonsvraagTeKorteRubriek() {
        Vraag vraag = Vragen.persoonsvraag(10110, param(1011, "1234567890"));
        vraag.setIndicatieZoekenInHistorie((byte) 1);
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(vraag);
        vraagBericht.getZoekCriteria();
    }
}
