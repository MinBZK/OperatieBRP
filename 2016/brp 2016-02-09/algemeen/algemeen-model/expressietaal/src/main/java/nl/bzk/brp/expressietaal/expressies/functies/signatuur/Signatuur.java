/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies.signatuur;

import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;

/**
 * Signatuur voor functies en operatoren.
 */
public interface Signatuur {
    /**
     * Controleert of de gegeven argumenten voldoen aan de signatuur. Geeft TRUE terug als de argumenten voldoen,
     * anders FALSE.
     *
     * @param argumenten Te controleren argumenten.
     * @param context    De bekende symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen context
     *                   is.
     * @return TRUE als argumenten voldoen aan signatuur, anders FALSE.
     */
    boolean matchArgumenten(List<Expressie> argumenten, Context context);
}
