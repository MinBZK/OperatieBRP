/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;

/**
 * Interface voor de factory klasse die {@link nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat} instanties creeert
 * op basis van een opgegeven bericht.
 */
public interface BerichtResultaatFactory {

    /**
     * Creeert een bericht specifieke {@link nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat}.
     * Dus op basis van het bericht, wordt er een nieuwe
     * instantie aangemaakt van een van subclass van {@link nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat}.
     *
     * @param bericht het bericht waarvoor een resultaat moet worden geinstantieerd.
     * @param berichtContext de context waarbinnen het bericht verwerkt wordt.
     * @param <T> Type voor het bericht resultaat.
     * @return het geinstantieerde bericht resultaat.
     */
     <T extends BerichtVerwerkingsResultaat> T creeerBerichtResultaat(BerichtBericht bericht,
                                                                      BerichtContext berichtContext);

}
