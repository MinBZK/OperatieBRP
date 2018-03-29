/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * VerstrekkingsBeperkingService.
 */
public interface VerstrekkingsbeperkingService {

    /**
     * Bepaalt of de persoon een geldige verstrekkingsbeperking heeft voor de partij.
     * @param persoonslijst De persoonsgegevens.
     * @param partij De partij.
     * @return True als er een geldige verstrekkingsbeperking is, anders false.
     */
    boolean heeftGeldigeVerstrekkingsbeperking(Persoonslijst persoonslijst, Partij partij);

    /**
     * Maakt een {@link RegelValidatie} voor het bepalen van een verstrekkingsbeperking.
     * @param persoonslijst De persoonsgegevens.
     * @param partij De partij.
     * @return een {@link RegelValidatie}
     */
    RegelValidatie maakRegelValidatie(Persoonslijst persoonslijst, Partij partij);
}
