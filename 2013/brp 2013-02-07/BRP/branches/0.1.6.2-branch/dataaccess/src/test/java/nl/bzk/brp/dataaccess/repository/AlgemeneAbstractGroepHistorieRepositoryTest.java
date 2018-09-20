/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.jpa.historie.GroepHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.groep.bericht.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Algemene unit test voor het testen van de functionaliteit in de
 * {@link nl.bzk.brp.dataaccess.repository.jpa.historie.AbstractGroepHistorieRepository} class. Dit wordt gedaan
 * middels de {@link nl.bzk.brp.dataaccess.repository.jpa.historie.PersoonAanschrijvingHistorieRepository}, wat een
 * implementatie is van eerder genoemde abstracte class.
 * <p/>
 * De test data voor deze test bestaat uit een 5 tal records in de his_persaanschr tabel, te weten 3 actuele records
 * en 2 vervallen records. Daarnaast is er ook nog een extra actie beschikbaar met id '104'.
 * <p/>
 * -------------------------------------------------------------------------------------------------------
 * | id  | pers | dataanvgel | dateindegel | geslnaamaanschr | tsreg               | tsverval            |
 * -------------------------------------------------------------------------------------------------------
 * | 101 | 2    | 2010-01-01 |             | Test1           | 2010-01-01 10:00:00 | 2011-01-02 12:12:12 |
 * | 102 | 2    | 2010-01-01 | 2011-01-01  | Test1           | 2011-01-02 12:12:12 |                     |
 * | 103 | 2    | 2011-01-01 |             | Test2           | 2011-01-02 12:12:12 | 2011-11-12 13:13:13 |
 * | 104 | 2    | 2011-01-01 | 2011-11-11  | Test2           | 2011-11-12 13:13:13 |                     |
 * | 105 | 2    | 2011-11-11 |             | Test3           | 2011-11-12 13:13:13 |                     |
 * -------------------------------------------------------------------------------------------------------
 */
public class AlgemeneAbstractGroepHistorieRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private GroepHistorieRepository<PersoonModel> persoonAanschrijvingHistorieRepository;
    @PersistenceContext
    private EntityManager                         em;

    @Test
    public void testNieuwRecordZonderEindeEnEenDeelsOverlap() {
        // Verwacht reeds 5 records aanwezig (3 actueel, 2 vervallen)
        Assert.assertEquals(5, this.countRowsInTable("kern.his_persaanschr"));

        PersoonAanschrijvingGroepBericht aanschrijvingGroep = new PersoonAanschrijvingGroepBericht();
        aanschrijvingGroep.setIndAanschrijvingAlgorthmischAfgeleid(JaNee.Ja);
        aanschrijvingGroep.setGeslachtsnaam(new GeslachtsnaamComponent("Tester"));
        persoonAanschrijvingHistorieRepository.persisteerHistorie(bouwPersoon(aanschrijvingGroep), bouwActie(),
            new Datum(20120101), null);
        em.flush();

        // Verwacht nu 7 records (4 actueel, 3 vervallen)
        Assert.assertEquals(7, this.countRowsInTable("kern.his_persaanschr"));
    }


    @Test
    public void testNieuwRecordZonderEindeEnEenDeelsEnGeheelOverlap() {
        // Verwacht reeds 5 records aanwezig (3 actueel, 2 vervallen)
        Assert.assertEquals(5, this.countRowsInTable("kern.his_persaanschr"));

        PersoonAanschrijvingGroepBericht aanschrijvingGroep = new PersoonAanschrijvingGroepBericht();
        aanschrijvingGroep.setIndAanschrijvingAlgorthmischAfgeleid(JaNee.Ja);
        aanschrijvingGroep.setGeslachtsnaam(new GeslachtsnaamComponent("Tester"));
        persoonAanschrijvingHistorieRepository.persisteerHistorie(bouwPersoon(aanschrijvingGroep), bouwActie(),
            new Datum(20110909), null);
        em.flush();

        // Verwacht nu 7 records (3 actueel, 4 vervallen)
        Assert.assertEquals(7, this.countRowsInTable("kern.his_persaanschr"));
    }

    @Test
    public void testNieuwRecordMetEindeEnOverlaptTweeBestaande() {
        // Verwacht reeds 5 records aanwezig (3 actueel, 2 vervallen)
        Assert.assertEquals(5, this.countRowsInTable("kern.his_persaanschr"));

        PersoonAanschrijvingGroepBericht aanschrijvingGroep = new PersoonAanschrijvingGroepBericht();
        aanschrijvingGroep.setIndAanschrijvingAlgorthmischAfgeleid(JaNee.Ja);
        aanschrijvingGroep.setGeslachtsnaam(new GeslachtsnaamComponent("Tester"));
        persoonAanschrijvingHistorieRepository.persisteerHistorie(bouwPersoon(aanschrijvingGroep), bouwActie(),
            new Datum(20110909), new Datum(20120101));
        em.flush();

        // Verwacht nu 8 records (4 actueel, 4 vervallen)
        Assert.assertEquals(8, this.countRowsInTable("kern.his_persaanschr"));
    }

    /**
     * Instantieert en retourneert een {@link PersoonModel} instantie met de id '2' en de opgegeven aanschrijving.
     *
     * @param aanschrijvingGroep de aanschrijving
     * @return een nieuwe persoon met opgegeven aanschrijving
     */
    private PersoonModel bouwPersoon(final PersoonAanschrijvingGroepBericht aanschrijvingGroep) {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setAanschrijving(aanschrijvingGroep);

        PersoonModel persoon = new PersoonModel(persoonBericht);
        ReflectionTestUtils.setField(persoon, "id", 2L);
        return persoon;
    }

    /**
     * Instantieert en retourneert een {@link ActieModel} instantie met de id '104'.
     * @return een nieuwe actie model met id 104
     */
    private ActieModel bouwActie() {
        ActieBericht actieBericht = new ActieBericht();

        ActieModel actie = new ActieModel(actieBericht);
        ReflectionTestUtils.setField(actie, "id", 104L);
        return actie;
    }
}
