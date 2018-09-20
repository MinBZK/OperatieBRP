/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels;

import java.util.List;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.model.validatie.Melding;


/**
 * Interface waaraan elke bedrijfsregel implementatie moet voldoen. Een bedrijfsregel executeert op de context van het
 * resultaat bericht.
 *
 */
public interface BerichtContextBedrijfsRegel {

    /**
     * Retourneert de code waarmee de bedrijfsregel kan worden geidentificeerd.
     *
     * @return Bedrijfsregel code.
     */
    String getCode();

    /**
     * Functie die de bedrijfsregel implementatie bevat voor uitvoer.
     *
     * @param context de context waarvoor de bedrijfsregel moet worden uitgevoerd.
     * @return Melding die de bedrijfsregel kan retourneren indien niet aan de bedrijfsregel wordt voldaan.
     */
    List<Melding> executeer(final BerichtContext context);
}
