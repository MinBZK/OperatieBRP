/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.repository;

import java.util.List;


/**
 * Interface voor de DAO voor toegang tot administratieve handeling data (BRP).
 */
public interface AdministratieveHandelingVerwerkerRepository {

    /**
     * Haalt voor een administratieve handeling de id's van de betrokken personen op.
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @return Een lijst van id's van de betrokken personen.
     */
    List<Integer> haalAdministratieveHandelingPersoonIds(Long administratieveHandelingId);

    /**
     * Haalt voor een administratieve handeling de bsn's van de betrokken personen op.
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @return Een lijst van bsn's van de betrokken personen.
     */
    List<Integer> haalAdministratieveHandelingPersoonBsns(final Long administratieveHandelingId);
}
