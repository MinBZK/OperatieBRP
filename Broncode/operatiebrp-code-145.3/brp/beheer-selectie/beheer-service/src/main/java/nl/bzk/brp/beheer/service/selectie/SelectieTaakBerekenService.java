/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Interface welke de services voor het berekenen van selectietaken op basis van beschikbare diensten in een bepaalde periode beschrijft.
 */
interface SelectieTaakBerekenService {

    /**
     * Bereken selectietaken op basis van een begin- en einddatum.
     * @param beginDatum de begindatum
     * @param eindDatum de einddatum
     * @return selectietaken
     */
    Collection<SelectieTaakDTO> berekenSelectieTaken(LocalDate beginDatum, LocalDate eindDatum);

}
