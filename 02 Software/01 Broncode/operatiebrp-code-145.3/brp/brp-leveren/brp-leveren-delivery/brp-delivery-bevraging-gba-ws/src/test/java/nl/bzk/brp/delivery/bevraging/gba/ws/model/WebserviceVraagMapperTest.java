/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.model;

import java.util.Arrays;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.delivery.bevraging.gba.ws.Vragen;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Vraag;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Zoekparameter;
import nl.bzk.brp.gba.domain.bevraging.Adresvraag;
import nl.bzk.brp.gba.domain.bevraging.Persoonsvraag;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.vragen.ConversieLo3NaarBrpVragen;
import nl.bzk.migratiebrp.conversie.vragen.ZoekCriterium;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test klasse voor de persoonsvraag mapper.
 */
public class WebserviceVraagMapperTest {

    private static final int A_NUMMER_INT = 10110;
    private static final String A_NUMMER_WAARDE = "1234567890";
    private static final int BSN_INT = 10120;
    private static final int GESLACHTSNAAM_INT = 10210;
    private static final String PREDIKAAT = "JH";
    private static final int ADELLIJKE_TITEL_PREDIKAAT_INT = 10220;
    private static final String PARTIJ_CODE = "0626";
    private static final List<Integer> gevraagdeRubrieken = Arrays.asList(A_NUMMER_INT, BSN_INT, GESLACHTSNAAM_INT);

    @Test
    public void testPersoonsvraag() {
        final ConversieLo3NaarBrpVragen CONVERSIE_SERVICE = Mockito.mock(ConversieLo3NaarBrpVragen.class);
        final WebserviceVraagMapper<Persoonsvraag> subject = new WebserviceVraagMapper<>(Persoonsvraag.class, CONVERSIE_SERVICE);
        Vraag vraag = new Vraag();
        vraag.setIndicatieAdresvraag(Byte.valueOf("0"));
        vraag.setIndicatieZoekenInHistorie(Byte.valueOf("0"));
        vraag.setMasker(gevraagdeRubrieken);
        final Zoekparameter aNummerZoekparameter = new Zoekparameter();
        aNummerZoekparameter.setRubrieknummer(A_NUMMER_INT);
        aNummerZoekparameter.setZoekwaarde(A_NUMMER_WAARDE);
        final Zoekparameter adellijkeTitelPredikaatZoekparameter = new Zoekparameter();
        adellijkeTitelPredikaatZoekparameter.setRubrieknummer(ADELLIJKE_TITEL_PREDIKAAT_INT);
        adellijkeTitelPredikaatZoekparameter.setZoekwaarde(PREDIKAAT);
        final List<Zoekparameter> zoekRubrieken = Arrays.asList(aNummerZoekparameter, adellijkeTitelPredikaatZoekparameter);
        vraag.setParameters(zoekRubrieken);

        ZoekCriterium zoekCriterium = new ZoekCriterium("Persoon.Identificatienummers.Administratienummer", aNummerZoekparameter.getZoekwaarde());
        ZoekCriterium zoekCriteriumTitel = new ZoekCriterium("Persoon.Naamgebruik.AdellijkeTitelCode", adellijkeTitelPredikaatZoekparameter.getZoekwaarde());
        ZoekCriterium zoekCriteriumPredicaat = new ZoekCriterium("Persoon.Naamgebruik.PredicaatCode", adellijkeTitelPredikaatZoekparameter.getZoekwaarde());
        zoekCriteriumTitel.setOf(zoekCriteriumPredicaat);
        Mockito.when(CONVERSIE_SERVICE.converteer(Mockito.anyListOf(Lo3CategorieWaarde.class))).thenReturn(Arrays.asList(zoekCriterium, zoekCriteriumTitel));
        Persoonsvraag geconverteerdeVraag = subject.mapVraag(new AdhocWebserviceVraagBericht(vraag), PARTIJ_CODE);
        Assert.assertEquals(PARTIJ_CODE, geconverteerdeVraag.getPartijCode());
        Assert.assertEquals(SoortDienst.ZOEK_PERSOON, geconverteerdeVraag.getSoortDienst());
        Assert.assertEquals(geconverteerdeVraag.getZoekRubrieken().size(), geconverteerdeVraag.getZoekCriteria().size());
        Assert.assertEquals(gevraagdeRubrieken.size(), geconverteerdeVraag.getGevraagdeRubrieken().size());
    }

    @Test
    public void testAdresvraagAdresIdentificatie() {
        final ConversieLo3NaarBrpVragen CONVERSIE_SERVICE = Mockito.mock(ConversieLo3NaarBrpVragen.class);
        final WebserviceVraagMapper<Adresvraag> subject = new WebserviceVraagMapper<>(Adresvraag.class, CONVERSIE_SERVICE);
        Vraag vraag = Vragen.adresvraag(10110, Vragen.param(80910, "Somewhere town"));
        vraag.setIndicatieAdresvraag((byte) 1);
        vraag.setIndicatieZoekenInHistorie((byte) 0);
        vraag.setMasker(gevraagdeRubrieken);
        final Zoekparameter aNummerZoekparameter = new Zoekparameter();
        aNummerZoekparameter.setRubrieknummer(A_NUMMER_INT);
        aNummerZoekparameter.setZoekwaarde(A_NUMMER_WAARDE);

        ZoekCriterium zoekCriterium = new ZoekCriterium("Persoon.Adres.Woonplaatsnaam", aNummerZoekparameter.getZoekwaarde());
        Mockito.when(CONVERSIE_SERVICE.converteer(Mockito.anyListOf(Lo3CategorieWaarde.class))).thenReturn(Arrays.asList(zoekCriterium));
        Adresvraag geconverteerdeVraag = subject.mapVraag(new AdhocWebserviceVraagBericht(vraag), PARTIJ_CODE);
        Assert.assertEquals(PARTIJ_CODE, geconverteerdeVraag.getPartijCode());
        Assert.assertEquals(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS, geconverteerdeVraag.getSoortDienst());
        Assert.assertEquals(geconverteerdeVraag.getZoekRubrieken().size(), geconverteerdeVraag.getZoekCriteria().size());
        Assert.assertEquals(gevraagdeRubrieken.size(), geconverteerdeVraag.getGevraagdeRubrieken().size());
    }

    @Test
    public void testAdresvraagPersoonsIdentificatie() {
        final ConversieLo3NaarBrpVragen CONVERSIE_SERVICE = Mockito.mock(ConversieLo3NaarBrpVragen.class);
        final WebserviceVraagMapper<Adresvraag> subject = new WebserviceVraagMapper<>(Adresvraag.class, CONVERSIE_SERVICE);
        Vraag vraag = Vragen.adresvraag(10110, Vragen.param(10110, "1234567890"));
        vraag.setIndicatieAdresvraag((byte) 1);
        vraag.setIndicatieZoekenInHistorie((byte) 0);
        vraag.setMasker(gevraagdeRubrieken);
        final Zoekparameter aNummerZoekparameter = new Zoekparameter();
        aNummerZoekparameter.setRubrieknummer(A_NUMMER_INT);
        aNummerZoekparameter.setZoekwaarde(A_NUMMER_WAARDE);

        ZoekCriterium zoekCriterium = new ZoekCriterium("Persoon.Identificatienummers.Administratienummer", aNummerZoekparameter.getZoekwaarde());
        Mockito.when(CONVERSIE_SERVICE.converteer(Mockito.anyListOf(Lo3CategorieWaarde.class))).thenReturn(Arrays.asList(zoekCriterium));
        Adresvraag geconverteerdeVraag = subject.mapVraag(new AdhocWebserviceVraagBericht(vraag), PARTIJ_CODE);
        Assert.assertEquals(PARTIJ_CODE, geconverteerdeVraag.getPartijCode());
        Assert.assertEquals(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS, geconverteerdeVraag.getSoortDienst());
        Assert.assertEquals(geconverteerdeVraag.getZoekRubrieken().size(), geconverteerdeVraag.getZoekCriteria().size());
        Assert.assertEquals(gevraagdeRubrieken.size(), geconverteerdeVraag.getGevraagdeRubrieken().size());
    }

    @Test
    public void historischZoeken() {
        final ConversieLo3NaarBrpVragen CONVERSIE_SERVICE = Mockito.mock(ConversieLo3NaarBrpVragen.class);
        final WebserviceVraagMapper<Persoonsvraag> subject = new WebserviceVraagMapper<>(Persoonsvraag.class, CONVERSIE_SERVICE);
        final Zoekparameter aNummerZoekparameter = new Zoekparameter();
        aNummerZoekparameter.setRubrieknummer(A_NUMMER_INT);
        aNummerZoekparameter.setZoekwaarde(A_NUMMER_WAARDE);
        final Zoekparameter adellijkeTitelPredikaatZoekparameter = new Zoekparameter();
        adellijkeTitelPredikaatZoekparameter.setRubrieknummer(ADELLIJKE_TITEL_PREDIKAAT_INT);
        adellijkeTitelPredikaatZoekparameter.setZoekwaarde(PREDIKAAT);
        ZoekCriterium zoekCriterium = new ZoekCriterium("Persoon.Identificatienummers.Administratienummer", aNummerZoekparameter.getZoekwaarde());
        ZoekCriterium zoekCriteriumTitel = new ZoekCriterium("Persoon.Naamgebruik.AdellijkeTitelCode", adellijkeTitelPredikaatZoekparameter.getZoekwaarde());
        ZoekCriterium zoekCriteriumPredicaat = new ZoekCriterium("Persoon.Naamgebruik.PredicaatCode", adellijkeTitelPredikaatZoekparameter.getZoekwaarde());
        zoekCriteriumTitel.setOf(zoekCriteriumPredicaat);
        Vraag vraag = Vragen.persoonsvraag(10110, Vragen.param(10110, "1324567890"));
        vraag.setIndicatieAdresvraag((byte) 0);
        vraag.setIndicatieZoekenInHistorie((byte) 1);
        Mockito.when(CONVERSIE_SERVICE.converteer(Mockito.anyListOf(Lo3CategorieWaarde.class))).thenReturn(Arrays.asList(zoekCriterium, zoekCriteriumTitel));
        subject.mapVraag(new AdhocWebserviceVraagBericht(vraag), PARTIJ_CODE);

    }

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalStateException.class)
    public void exceptieIllegalStateExceptionTest() {
        final ConversieLo3NaarBrpVragen CONVERSIE_SERVICE = Mockito.mock(ConversieLo3NaarBrpVragen.class);
        final WebserviceVraagMapper<Persoonsvraag> subject = new WebserviceVraagMapper<>(Persoonsvraag.class, CONVERSIE_SERVICE);
        Vraag vraag = Vragen.persoonsvraag(10110, Vragen.param(10110, "1324567890"));
        Mockito.when(CONVERSIE_SERVICE.converteer(Mockito.anyListOf(Lo3CategorieWaarde.class))).thenThrow(IllegalAccessException.class);
        subject.mapVraag(new AdhocWebserviceVraagBericht(vraag), PARTIJ_CODE);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalStateException.class)
    public void exceptieInstantiationExceptionTest() {
        final ConversieLo3NaarBrpVragen CONVERSIE_SERVICE = Mockito.mock(ConversieLo3NaarBrpVragen.class);
        final WebserviceVraagMapper<Persoonsvraag> subject = new WebserviceVraagMapper<>(Persoonsvraag.class, CONVERSIE_SERVICE);
        Vraag vraag = Vragen.persoonsvraag(10110, Vragen.param(10110, "1324567890"));
        Mockito.when(CONVERSIE_SERVICE.converteer(Mockito.anyListOf(Lo3CategorieWaarde.class))).thenThrow(InstantiationException.class);
        subject.mapVraag(new AdhocWebserviceVraagBericht(vraag), PARTIJ_CODE);
    }
}
