/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven;

import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;


/**
 * Interface voor de DAO voor toegang tot administratieve handeling data (BRP).
 */
public interface AdministratieveHandelingVerwerkerRepository {

    /**
     * Haalt voor een administratieve handeling de id's van de betrokken personen op.
     *
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @return Een lijst van id's van de betrokken personen.
     */
    List<Integer> haalAdministratieveHandelingPersoonIds(Long administratieveHandelingId);

    /**
     * Markeert de administratieve handeling als verwerkt, zodat deze niet nogmaals verwerkt zal worden.
     *
     * @param administratieveHandelingId Het id van de administratieve handeling.
     */
    void markeerAdministratieveHandelingAlsVerwerkt(Long administratieveHandelingId);

    /**
     * Verifieert of er administratieve handelingen bestaan voor deze administratieve handeling die de huidige personen
     * treft met uitzondering van soorten administratieve handelingen die overgeslagen mogen worden.
     *
     * @param administratieveHandeling                 de huidige administratieve handeling
     * @param persoonIds                               de geraakte personen
     * @param overslaanSoortAdministratieveHandelingen soort administratieve handelingen die overgeslagen mogen worden
     * @return <code>true</code>, als er geen ah van de betreffende personen is voor de huidige
     */
    boolean magAdministratieveHandelingVerwerktWorden(
            AdministratieveHandelingModel administratieveHandeling,
            List<Integer> persoonIds,
            List<SoortAdministratieveHandeling> overslaanSoortAdministratieveHandelingen);
}
