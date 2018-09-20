/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.ormmapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import junit.framework.Assert;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Administratienummer;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.Scheidingsteken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.logisch.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeboorteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.WijzeGebruikGeslachtsnaam;
import org.junit.Test;

/**
 * Unit test voor het testen van de ORM mapping op {@link PersoonModel}. In deze test wordt via JPA direct een persoon
 * opgehaald en weggeschreven en de resultaten worden gecontroleerd.
 */

public class PersoonMappingTest extends AbstractRepositoryTestCase {

    @PersistenceContext( unitName = "nl.bzk.brp")
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
        Assert.assertEquals(new Administratienummer(Long.valueOf(1234567890L)),
            identificatienummers.getAdministratienummer());
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
        Assert.assertEquals(new Gemeentecode((short) 34), geboorte.getGemeenteGeboorte().getGemeentecode());
        Assert.assertNotNull("Geen woonplaats in geboorte", geboorte.getWoonplaatsGeboorte());
        Assert.assertEquals(new PlaatsCode((short) 34), geboorte.getWoonplaatsGeboorte().getCode());
        Assert.assertNull(geboorte.getBuitenlandseGeboortePlaats());
        Assert.assertNull(geboorte.getBuitenlandseRegioGeboorte());
        Assert.assertEquals(new Omschrijving("52.429222,2.790527"), geboorte.getOmschrijvingGeboorteLocatie());
    }

    /**
     * Controleert de aanschrijving groep.
     *
     * @param aanschrijving de aanschrijving groep.
     */
    private void controleerAanschrijving(final PersoonAanschrijvingGroep aanschrijving) {
        Assert.assertNotNull("Geen aanschrijving groep", aanschrijving);
        Assert.assertEquals(JaNee.Ja, aanschrijving.getIndAanschrijvenMetAdellijkeTitel());
        Assert.assertEquals(JaNee.Nee, aanschrijving.getIndAanschrijvingAlgorthmischAfgeleid());
        Assert.assertEquals(new Scheidingsteken(","), aanschrijving.getScheidingsteken());
        Assert.assertNotNull("Geen adellijketitel in aanschrijving", aanschrijving.getAdellijkeTitel());
        Assert.assertEquals(new AdellijkeTitelCode("R"), aanschrijving.getAdellijkeTitel().getAdellijkeTitelCode());
        Assert.assertEquals(WijzeGebruikGeslachtsnaam.EIGEN_PARTNER, aanschrijving.getGebruikGeslachtsnaam());
        Assert.assertEquals(new Geslachtsnaamcomponent("Wittgensteintje"), aanschrijving.getGeslachtsnaam());
        Assert.assertNotNull("Geen predikaat in aanschrijving", aanschrijving.getPredikaat());
        Assert.assertEquals(new PredikaatCode("J"), aanschrijving.getPredikaat().getCode());
        Assert.assertEquals(new Voornaam("Teun"), aanschrijving.getVoornamen());
        Assert.assertEquals(new Voorvoegsel("van"), aanschrijving.getVoorvoegsel());
    }

    /**
     * Controleert de samengestelde naam groep.
     *
     * @param samengesteldeNaam de samengestelde naam groep.
     */
    private void controleerSamengesteldeNaam(final PersoonSamengesteldeNaamGroep samengesteldeNaam) {
        Assert.assertNotNull("Geen samengesteldenaam groep", samengesteldeNaam);
        Assert.assertNotNull("Geen adellijketitel in aanschrijving", samengesteldeNaam.getAdellijkeTitel());
        Assert.assertEquals(new AdellijkeTitelCode("P"), samengesteldeNaam.getAdellijkeTitel().getAdellijkeTitelCode());
        Assert.assertEquals(new Geslachtsnaamcomponent("Wittgenstein"), samengesteldeNaam.getGeslachtsnaam());
        Assert.assertEquals(JaNee.Ja, samengesteldeNaam.getIndAlgorithmischAfgeleid());
        Assert.assertEquals(JaNee.Nee, samengesteldeNaam.getIndNamenreeksAlsGeslachtsNaam());
        Assert.assertNotNull("Geen predikaat in aanschrijving", samengesteldeNaam.getPredikaat());
        Assert.assertEquals(new PredikaatCode("K"), samengesteldeNaam.getPredikaat().getCode());
        Assert.assertEquals(new Scheidingsteken("-"), samengesteldeNaam.getScheidingsteken());
        Assert.assertEquals(new Voornaam("Ludwig Josef Johann"), samengesteldeNaam.getVoornamen());
        Assert.assertEquals(new Voorvoegsel("de"), samengesteldeNaam.getVoorvoegsel());
    }

}
