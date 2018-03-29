/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.service.algemeen.StapException;

/**
 * ZoekCriteriaConverteerService.
 */
@FunctionalInterface
interface ZoekCriteriaWaardeConverteerService {

    /**
     * converteerWaarde.
     * @param attribuutElement attribuutElement
     * @param waarde waarde
     * @return de geconverteerde waarde
     * @throws StapException fout bij converteren
     */
    Object converteerWaarde(AttribuutElement attribuutElement, String waarde) throws StapException;
}
