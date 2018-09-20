/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.ormmapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ANummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredikaatCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornamen;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.logisch.kern.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.junit.Test;

/**
 * Unit test voor het testen van de ORM mapping op {@link PersoonModel}. In deze test wordt via JPA direct een persoon
 * opgehaald en weggeschreven en de resultaten worden gecontroleerd.
 */

public class PersoonMappingTest extends AbstractRepositoryTestCase {

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Test
    public void testOphalenPersoonMiddelsJPA() {
        PersoonModel persoon = em.find(PersoonModel.class, 1);
        Assert.assertNotNull(persoon);

        Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoort());

        controleerIdentificatienummers(persoon.getIdentificatienummers());
        controleerGeboorte(persoon.getGeboorte());
        controleerAanschrijving(persoon.getAanschrijving());
        controleerSamengesteldeNaam(persoon.getSamengesteldeNaam());
    }

    /**
     * Controleert de identificatienummers groep.
     *
     * @param identificatienummers de identificatienummers groep.
     */
    private void controleerIdentificatienummers(final PersoonIdentificatienummersGroep identificatienummers) {
        Assert.assertNotNull("Geen identificatienummers groep", identificatienummers);
        Assert.assertEquals(new Burgerservicenummer("123456789"), identificatienummers.getBurgerservicenummer());
        Assert.assertEquals(new ANummer("1234567890"), identificatienummers.getAdministratienummer());
    }

    /**
     * Controleert de geboorte groep.
     *
     * @param geboorte de geboort groep.
     */
    private void controleerGeboorte(final PersoonGeboorteGroep geboorte) {
        Assert.assertNotNull("Geen geboorte groep", geboorte);
        Assert.assertEquals(new Datum(18890426), geboorte.getDatumGeboorte());
        Assert.assertNotNull("Geen land in geboorte", geboorte.getLandGeboorte());
        Assert.assertEquals(BrpConstanten.NL_LAND_CODE, geboorte.getLandGeboorte().getCode());
        Assert.assertNotNull("Geen gemeente in geboorte", geboorte.getGemeenteGeboorte());
        Assert.assertEquals(new GemeenteCode("34"), geboorte.getGemeenteGeboorte().getCode());
        Assert.assertNotNull("Geen woonplaats in geboorte", geboorte.getWoonplaatsGeboorte());
        Assert.assertEquals(new Woonplaatscode("34"), geboorte.getWoonplaatsGeboorte().getCode());
        Assert.assertNull(geboorte.getBuitenlandsePlaatsGeboorte());
        Assert.assertNull(geboorte.getBuitenlandseRegioGeboorte());
        Assert.assertEquals(new LocatieOmschrijving("52.429222,2.790527"),
                            geboorte.getOmschrijvingLocatieGeboorte());
    }

    /**
     * Controleert de aanschrijving groep.
     *
     * @param aanschrijving de aanschrijving groep.
     */
    private void controleerAanschrijving(final PersoonAanschrijvingGroep aanschrijving) {
        Assert.assertNotNull("Geen aanschrijving groep", aanschrijving);
        Assert.assertEquals(JaNee.NEE, aanschrijving.getIndicatieTitelsPredikatenBijAanschrijven());
        Assert.assertEquals(JaNee.NEE, aanschrijving.getIndicatieAanschrijvingAlgoritmischAfgeleid());
        Assert.assertEquals(new Scheidingsteken(","), aanschrijving.getScheidingstekenAanschrijving());
        Assert.assertNotNull("Geen adellijketitel in aanschrijving", aanschrijving.getAdellijkeTitelAanschrijving());
        Assert.assertEquals(new AdellijkeTitelCode("R"), aanschrijving.getAdellijkeTitelAanschrijving().getCode());
        Assert.assertEquals(WijzeGebruikGeslachtsnaam.EIGEN_PARTNER, aanschrijving.getNaamgebruik());
        Assert.assertEquals(new Geslachtsnaam("Wittgensteintje"), aanschrijving.getGeslachtsnaamAanschrijving());
        Assert.assertNotNull("Geen predikaat in aanschrijving", aanschrijving.getPredikaatAanschrijving());
        Assert.assertEquals(new PredikaatCode("J"), aanschrijving.getPredikaatAanschrijving().getCode());
        Assert.assertEquals(new Voornamen("Teun"), aanschrijving.getVoornamenAanschrijving());
        Assert.assertEquals(new Voorvoegsel("van"), aanschrijving.getVoorvoegselAanschrijving());
    }

    /**
     * Controleert de samengestelde naam groep.
     *
     * @param samengesteldeNaam de samengestelde naam groep.
     */
    private void controleerSamengesteldeNaam(final PersoonSamengesteldeNaamGroep samengesteldeNaam) {
        Assert.assertNotNull("Geen samengesteldenaam groep", samengesteldeNaam);
        Assert.assertNotNull("Geen adellijketitel in aanschrijving", samengesteldeNaam.getAdellijkeTitel());
        Assert.assertEquals(new AdellijkeTitelCode("P"), samengesteldeNaam.getAdellijkeTitel().getCode());
        Assert.assertEquals(new Geslachtsnaam("Wittgenstein"), samengesteldeNaam.getGeslachtsnaam());
        Assert.assertEquals(JaNee.JA, samengesteldeNaam.getIndicatieAlgoritmischAfgeleid());
        Assert.assertEquals(JaNee.NEE, samengesteldeNaam.getIndicatieNamenreeks());
        Assert.assertNotNull("Geen predikaat in aanschrijving", samengesteldeNaam.getPredikaat());
        Assert.assertEquals(new PredikaatCode("K"), samengesteldeNaam.getPredikaat().getCode());
        Assert.assertEquals(new Scheidingsteken("-"), samengesteldeNaam.getScheidingsteken());
        Assert.assertEquals(new Voornamen("Ludwig Josef Johann"), samengesteldeNaam.getVoornamen());
        Assert.assertEquals(new Voorvoegsel("de"), samengesteldeNaam.getVoorvoegsel());
    }

}
