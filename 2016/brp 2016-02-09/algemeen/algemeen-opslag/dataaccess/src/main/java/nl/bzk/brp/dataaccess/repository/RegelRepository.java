/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.attribuuttype.brm.RegelCodeAttribuut;

/**
 * Repository voor Regels die in de database staan.
 */
public interface RegelRepository {

    /**
     * Haalt regel informatie op voor een bepaalde regel a.v.d logische identificatie code van die regel.
     * De regel parameters is een DTO klasse die informatie voor een specifieke regel bevat.
     *
     * @param regelCode de regel code van de regel
     * @return Regel parameters dto
     */
    RegelParameters getRegelParametersVoorRegel(RegelCodeAttribuut regelCode);

}
