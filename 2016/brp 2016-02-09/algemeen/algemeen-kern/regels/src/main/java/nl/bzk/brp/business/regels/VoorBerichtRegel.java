/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import java.util.List;

import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;


/**
 * Interface voor alle regels die van toepassing zijn op een geheel bericht.
 *
 * @param <B> Het specifieke type bericht dat deze regel verwacht. (kan ook het generieke BerichtBericht zijn).
 */
public interface VoorBerichtRegel<B extends BerichtBericht> extends RegelInterface {

    /**
     * Functie die de bedrijfsregel implementatie bevat voor uitvoer.
     *
     * @param bericht het bericht dat bij de BRP is binnengekomen
     * @return Lijst van objecten die de regel overtreden.
     */
    List<BerichtIdentificeerbaar> voerRegelUit(final B bericht);

}
