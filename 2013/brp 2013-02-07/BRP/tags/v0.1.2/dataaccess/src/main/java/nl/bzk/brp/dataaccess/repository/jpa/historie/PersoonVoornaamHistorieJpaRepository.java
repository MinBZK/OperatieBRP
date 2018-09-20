/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.repository.historie.PersoonVoornaamHistorieRepository;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaam;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonVoornaam;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersistentPersoonVoornaam} class en standaard implementatie van de
 * {@link PersoonVoornaamHistorieRepository} class.
 */
@Repository
public class PersoonVoornaamHistorieJpaRepository implements PersoonVoornaamHistorieRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void opslaanHistorie(final PersistentPersoonVoornaam voornaam, final Integer datumAanvangGeldigheid,
            final Integer datumEindeGeldigheid, final Date registratieTijd)
    {
        // TODO bouw logica voor bijhouden historie

        HisPersoonVoornaam hisPersoonVoornaam = new HisPersoonVoornaam();
        hisPersoonVoornaam.setPersoonVoornaam(voornaam);

        hisPersoonVoornaam.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisPersoonVoornaam.setDatumEindeGeldigheid(null);
        hisPersoonVoornaam.setDatumTijdRegistratie(registratieTijd);
        hisPersoonVoornaam.setNaam(voornaam.getNaam());

        em.persist(hisPersoonVoornaam);
    }

}
