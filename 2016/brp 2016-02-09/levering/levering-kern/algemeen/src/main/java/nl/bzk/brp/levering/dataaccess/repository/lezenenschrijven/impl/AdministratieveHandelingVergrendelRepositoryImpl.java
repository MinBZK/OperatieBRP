/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.AdministratieveHandelingVergrendelRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

import org.springframework.stereotype.Repository;


/**
 * De klasse AdministratieveHandelingVergrendelRepositoryImpl verzorgt de controle of een administratieve handeling al
 * verwerkt is en vergrendelt deze als dit niet het geval is.
 */
@Repository
public class AdministratieveHandelingVergrendelRepositoryImpl implements AdministratieveHandelingVergrendelRepository {

    private static final Logger LOGGER                        = LoggerFactory.getLogger();

    private static final String JPA_LOCK_TIMEOUT_PROPERTY_KEY = "javax.persistence.lock.timeout";

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager       em;

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.brp.levering.mutatielevering.repository.AdministratieveHandelingVergrendelRepository#
     * vergrendelAlsNogNietIsVerwerkt(Long)
     */
    @Override
    public final boolean vergrendelAlsNogNietIsVerwerkt(final Long administratieveHandelingId) {
        if (isNietVerwerktEnVergrendelingGeplaatst(administratieveHandelingId)) {
            return true;
        }
        return false;
    }

    /**
     * Probeert een administratieve handeling op te halen en direct een vergendeling te zetten. Vervolgens wordt
     * gecontroleert of de administratieve handeling nog niet verwerkt is wordt true ge-returned. Als de handeling al
     * verwerkt is, óf al vergrendeld is, wordt false ge-returned.
     *
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @return true als de handeling kon worden vergrendeld en nog niet verwerkt is, anders false.
     */
    private boolean isNietVerwerktEnVergrendelingGeplaatst(final Long administratieveHandelingId) {
        try {
            final AdministratieveHandelingModel administratieveHandelingModel =
                    em.find(AdministratieveHandelingModel.class, administratieveHandelingId,
                            LockModeType.PESSIMISTIC_WRITE, getPropertiesVoorDirecteVergrendelingTimeout());
            if (administratieveHandelingModel != null && administratieveHandelingModel.getLevering() == null) {
                return true;
            }
            LOGGER.warn("Administratieve handeling met id {} is reeds verwerkt.", administratieveHandelingId);
        } catch (final PersistenceException e) {
            LOGGER.warn("Administratieve handeling met id {} kon niet worden vergrendeld.", administratieveHandelingId);
        }
        return false;
    }

    /**
     * Returned een hash-map met daarin de properties die gebruikt moeten worden bij het ophalen&vergrendelen van de
     * administratieve handeling. Hierin wordt vastgelegd dat de lock-timeout op 0 moet staan, waardoor niet gewacht zal
     * worden als de lock niet verkregen kan worden.
     *
     * @return De Map met de juiste properties.
     */
    private Map<String, Object> getPropertiesVoorDirecteVergrendelingTimeout() {
        final Map<String, Object> props = new HashMap<String, Object>();
        props.put(JPA_LOCK_TIMEOUT_PROPERTY_KEY, 0);
        return props;
    }

}
