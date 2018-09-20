/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.ormmapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNaamgebruikGroep;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor het testen van de ORM mapping op {@link nl.bzk.brp.model.operationeel.kern.PersoonModel}. In deze test wordt via JPA direct een persoon
 * opgehaald en weggeschreven en de resultaten worden gecontroleerd.
 */

public class PersoonMappingTest extends AbstractRepositoryTestCase {

    public static final String GEEN_ADELLIJKETITEL_IN_AANSCHRIJVING = "Geen adellijketitel in aanschrijving";
    public static final String GEEN_PREDIKAAT_IN_AANSCHRIJVING = "Geen predikaat in aanschrijving";
    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Test
    public void testOphalenPersoonMiddelsJPA() {
        final PersoonModel persoon = em.find(PersoonModel.class, 1);
        Assert.assertNotNull(persoon);

        Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoort().getWaarde());

        controleerIdentificatienummers(persoon.getIdentificatienummers());
        controleerGeboorte(persoon.getGeboorte());
        controleerNaamgebruik(persoon.getNaamgebruik());
        controleerSamengesteldeNaam(persoon.getSamengesteldeNaam());
    }

    /**
     * Controleert de identificatienummers groep.
     *
     * @param identificatienummers de identificatienummers groep.
     */
    private void controleerIdentificatienummers(final PersoonIdentificatienummersGroep identificatienummers) {
        Assert.assertNotNull("Geen identificatienummers groep", identificatienummers);
        Assert.assertEquals(new BurgerservicenummerAttribuut(123456789), identificatienummers.getBurgerservicenummer());
        Assert.assertEquals(
                new AdministratienummerAttribuut(1234567890L), identificatienummers.getAdministratienummer());
    }

    /**
     * Controleert de geboorte groep.
     *
     * @param geboorte de geboort groep.
     */
    private void controleerGeboorte(final PersoonGeboorteGroep geboorte) {
        Assert.assertNotNull("Geen geboorte groep", geboorte);
        Assert.assertEquals(new DatumEvtDeelsOnbekendAttribuut(18890426), geboorte.getDatumGeboorte());
        Assert.assertNotNull("Geen land in geboorte", geboorte.getLandGebiedGeboorte());
        Assert.assertEquals(LandGebiedCodeAttribuut.NEDERLAND, geboorte.getLandGebiedGeboorte().getWaarde().getCode());
        Assert.assertNotNull("Geen gemeente in geboorte", geboorte.getGemeenteGeboorte());
        Assert.assertEquals(new GemeenteCodeAttribuut((short) 34),
                            geboorte.getGemeenteGeboorte().getWaarde().getCode());
        Assert.assertNotNull("Geen woonplaats in geboorte", geboorte.getWoonplaatsnaamGeboorte());
        Assert.assertEquals("Almere", geboorte.getWoonplaatsnaamGeboorte().getWaarde());
        Assert.assertNull(geboorte.getBuitenlandsePlaatsGeboorte());
        Assert.assertNull(geboorte.getBuitenlandseRegioGeboorte());
        Assert.assertEquals(new LocatieomschrijvingAttribuut("52.429222,2.790527"), geboorte.getOmschrijvingLocatieGeboorte());
    }

    /**
     * Controleert de aanschrijving groep.
     *
     * @param aanschrijving de aanschrijving groep.
     */
    private void controleerNaamgebruik(final PersoonNaamgebruikGroep aanschrijving) {
        Assert.assertNotNull("Geen aanschrijving groep", aanschrijving);
        Assert.assertEquals(JaNeeAttribuut.NEE, aanschrijving.getIndicatieNaamgebruikAfgeleid());
        Assert.assertEquals(new ScheidingstekenAttribuut(","), aanschrijving.getScheidingstekenNaamgebruik());
        Assert.assertNotNull(GEEN_ADELLIJKETITEL_IN_AANSCHRIJVING, aanschrijving.getAdellijkeTitelNaamgebruik());
        Assert.assertEquals(new AdellijkeTitelCodeAttribuut("R"),
                            aanschrijving.getAdellijkeTitelNaamgebruik().getWaarde().getCode());
        Assert.assertEquals(Naamgebruik.EIGEN_PARTNER, aanschrijving.getNaamgebruik().getWaarde());
        Assert.assertEquals(new GeslachtsnaamstamAttribuut("Wittgensteintje"),
                            aanschrijving.getGeslachtsnaamstamNaamgebruik());
        Assert.assertNotNull(GEEN_PREDIKAAT_IN_AANSCHRIJVING, aanschrijving.getPredicaatNaamgebruik());
        Assert.assertEquals(new PredicaatCodeAttribuut("J"),
                            aanschrijving.getPredicaatNaamgebruik().getWaarde().getCode());
        Assert.assertEquals(new VoornamenAttribuut("Teun"), aanschrijving.getVoornamenNaamgebruik());
        Assert.assertEquals(new VoorvoegselAttribuut("van"), aanschrijving.getVoorvoegselNaamgebruik());
    }

    /**
     * Controleert de samengestelde naam groep.
     *
     * @param samengesteldeNaam de samengestelde naam groep.
     */
    private void controleerSamengesteldeNaam(final PersoonSamengesteldeNaamGroep samengesteldeNaam) {
        Assert.assertNotNull("Geen samengesteldenaam groep", samengesteldeNaam);
        Assert.assertNotNull(GEEN_ADELLIJKETITEL_IN_AANSCHRIJVING, samengesteldeNaam.getAdellijkeTitel());
        Assert.assertEquals(new AdellijkeTitelCodeAttribuut("P"),
                            samengesteldeNaam.getAdellijkeTitel().getWaarde().getCode());
        Assert.assertEquals(new GeslachtsnaamstamAttribuut("Wittgenstein"), samengesteldeNaam.getGeslachtsnaamstam());
        Assert.assertEquals(JaNeeAttribuut.JA, samengesteldeNaam.getIndicatieAfgeleid());
        Assert.assertEquals(JaNeeAttribuut.NEE, samengesteldeNaam.getIndicatieNamenreeks());
        Assert.assertNotNull(GEEN_PREDIKAAT_IN_AANSCHRIJVING, samengesteldeNaam.getPredicaat());
        Assert.assertEquals(new PredicaatCodeAttribuut("K"), samengesteldeNaam.getPredicaat().getWaarde().getCode());
        Assert.assertEquals(new ScheidingstekenAttribuut("-"), samengesteldeNaam.getScheidingsteken());
        Assert.assertEquals(new VoornamenAttribuut("Ludwig Josef Johann"), samengesteldeNaam.getVoornamen());
        Assert.assertEquals(new VoorvoegselAttribuut("de"), samengesteldeNaam.getVoorvoegsel());
    }

}
