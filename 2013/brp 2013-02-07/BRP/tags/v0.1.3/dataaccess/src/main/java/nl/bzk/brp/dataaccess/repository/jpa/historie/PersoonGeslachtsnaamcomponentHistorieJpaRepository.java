/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.repository.historie.PersoonGeslachtsnaamcomponentHistorieRepository;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonGeslachtsnaamcomponent;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersistentPersoonGeslachtsnaamcomponent} class en standaard implementatie van de
 * {@link PersoonGeslachtsnaamcomponentHistorieRepository} class.
 */
@Repository
public class PersoonGeslachtsnaamcomponentHistorieJpaRepository implements
        PersoonGeslachtsnaamcomponentHistorieRepository
{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void opslaanHistorie(final PersistentPersoonGeslachtsnaamcomponent geslachtsnaamcomponent,
            final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid, final Date tijdstipRegistratie)
    {
        // Sla op in C-laag
        HisPersoonGeslachtsnaamcomponent hisPersoonGeslachtsnaamcomponent = new HisPersoonGeslachtsnaamcomponent();
        hisPersoonGeslachtsnaamcomponent.setPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent);

        hisPersoonGeslachtsnaamcomponent.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisPersoonGeslachtsnaamcomponent.setDatumEindeGeldigheid(null);
        hisPersoonGeslachtsnaamcomponent.setDatumTijdRegistratie(tijdstipRegistratie);
        hisPersoonGeslachtsnaamcomponent.setNaam(geslachtsnaamcomponent.getNaam());
        hisPersoonGeslachtsnaamcomponent.setVoorvoegsel(geslachtsnaamcomponent.getVoorvoegsel());

        hisPersoonGeslachtsnaamcomponent.setScheidingsteken(geslachtsnaamcomponent.getScheidingsteken());
        hisPersoonGeslachtsnaamcomponent.setPredikaat(geslachtsnaamcomponent.getPredikaat());
        hisPersoonGeslachtsnaamcomponent.setAdellijkeTitel(geslachtsnaamcomponent.getAdellijkeTitel());

        em.persist(hisPersoonGeslachtsnaamcomponent);
    }

}
