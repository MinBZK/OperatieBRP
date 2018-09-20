/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import nl.bzk.brp.business.dto.bevraging.zoekcriteria.AbstractZoekCriteria;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;


/**
 * .
 *
// * @param <C> De zoekcriteria, moet afgeleid zijn van AbstractZoekCriteria
 */
public abstract class AbstractVraag extends AbstractObjectTypeBericht {

    /**
     * Deze methode geeft de bsn die in de vraag gebruikt wordt.
     * (en wel als onderdeel van de zoek criteria in geval van VraagBericht).
     * Dit zou anders moeten zijn.
     * @return de bsn of null als niet van toepassing is.
     */
    public abstract String getBurgerservicenummerForLocks();

    /**
     * .
     * @return .
     */
    public abstract AbstractZoekCriteria getZoekCriteria();

}
