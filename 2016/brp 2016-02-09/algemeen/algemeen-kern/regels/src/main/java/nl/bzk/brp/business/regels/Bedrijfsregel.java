/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import nl.bzk.brp.business.regels.context.RegelContext;


/**
 * Interface voor reguliere bedrijfsregels. Deze bedrijfsregels voeren een controle uit en retourneren uiteindelijk
 * of de bedrijfsregel is overtreden door middel van een boolean waarde.
 *
 * @param <C> de regel context waarvan gebruik wordt gemaakt.
 */
public interface Bedrijfsregel<C extends RegelContext> extends RegelInterface {

    /**
     * Resultaat van validatie dat aangeeft dat de regel valide is, dus verwerking door kan gaan.
     */
    boolean VALIDE = Boolean.TRUE;

    /**
     * Resultaat van validatie dat aangeeft dat de regel *NIET* valide is, dus verwerking niet door kan gaan.
     */
    boolean INVALIDE = Boolean.FALSE;

    /**
     * De methode die de bedrijfsregel valideert op basis van objecten in de regelcontext.
     *
     * @param regelContext de regel context.
     * @return {@link #VALIDE} als de bedrijfsregel valideert, anders {@link #INVALIDE}.
     */
    boolean valideer(C regelContext);

    /**
     * Geeft het type van de context terug. Hierdoor weet het omliggende mechanisme welke context voor de regel gevuld
     * dient te worden.
     *
     * @return Het type van de context.
     */
    Class<C> getContextType();
}
