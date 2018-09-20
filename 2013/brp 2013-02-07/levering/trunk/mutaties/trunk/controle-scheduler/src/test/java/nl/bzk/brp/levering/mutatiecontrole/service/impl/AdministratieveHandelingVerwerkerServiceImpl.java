/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.levering.mutatiecontrole.service.AdministratieveHandelingVerwerkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Deze service zorgt voor de verwerking van mutaties/administratieve handelingen.
 */
@Service
@Transactional
public class AdministratieveHandelingVerwerkerServiceImpl implements AdministratieveHandelingVerwerkerService {

    private final Logger logger = LoggerFactory.getLogger(AdministratieveHandelingVerwerkerServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * Verwerkt een administratieve handeling met het opgegeven id. Dit is het startpunt voor de verwerking van een
     * mutatie.
     *
     * @param administratieveHandelingId Het id van de administratieve handeling.
     */
    @Override
    public void verwerkAdministratieveHandeling(final Long administratieveHandelingId) {
        plaatsTijdstipVerwerkingMutatie(administratieveHandelingId);
    }

    /**
     * Plaats tijdstip verwerking mutatie bij administratieve handeling waarvan Id bekend is.
     *
     * @param administratieveHandelingId de administratieve handeling id
     */
    private void plaatsTijdstipVerwerkingMutatie(final Long administratieveHandelingId) {
        final String query =
            "UPDATE kern.admhnd SET tsverwerkingmutatie = now() WHERE id = " + administratieveHandelingId;

        em.createNativeQuery(query).executeUpdate();
    }

}
