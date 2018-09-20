/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole.repository.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.levering.mutatiecontrole.repository.AdministratieveHandelingRepository;
import org.springframework.stereotype.Repository;


/**
 * De Class AdministratieveHandelingRepositoryImpl, de implementatie voor de interface
 * AdministratieveHandelingRepository. Deze klasse haalt Administratieve Handelingen op uit de data-laag.
 */
@Repository
public class AdministratieveHandelingRepositoryImpl implements AdministratieveHandelingRepository {

    @PersistenceContext
    private EntityManager em;

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.levering.mutatiecontrole.dataaccess.AdministratieveHandelingDao#
     * haalOnverwerkteAdministratieveHandelingenOp(java.lang.Long)
     */
    @Override
    public List<BigInteger> haalOnverwerkteAdministratieveHandelingenOp() {

        final String query =
            "SELECT ka.id FROM kern.admhnd ka WHERE ka.tsverwerkingmutatie IS NULL ORDER BY ka.tsontlening ASC";

        @SuppressWarnings("unchecked")
        final List<BigInteger> onverwerkteAdministratieveHandelingen = em.createNativeQuery(query).getResultList();

        return onverwerkteAdministratieveHandelingen;
    }
}
