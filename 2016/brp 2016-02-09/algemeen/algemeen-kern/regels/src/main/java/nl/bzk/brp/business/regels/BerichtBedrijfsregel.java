/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import java.util.List;

import nl.bzk.brp.business.regels.context.RegelContext;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;


/**
 * Interface voor bedrijfsregels die berichtobjecten valideren. Deze bedrijfsregels voeren een controle uit en
 * retourneren uiteindelijk een lijst met objecten die de bedrijfsregel overtreden.
 *
 * @param <C> de regel context waarvan gebruik wordt gemaakt.
 */
public interface BerichtBedrijfsregel<C extends RegelContext> extends RegelInterface {

    /**
     * De methode die de bedrijfsregel valideert op basis van objecten in de regelcontext.
     *
     * @param regelContext de regel context.
     * @return een lijst met objecten die de bedrijfsregel overtreden.
     */
    List<BerichtIdentificeerbaar> valideer(C regelContext);

    /**
     * Geeft het type van de context terug. Hierdoor weet het omliggende mechanisme welke context voor de regel gevuld
     * dient te worden.
     *
     * @return Het type van de context.
     */
    Class<C> getContextType();
}
