/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Basis interface voor attributen.
 *
 * @param <T> Het type van de waarde van het attribuut.
 */
public interface Attribuut<T> extends Onderzoekbaar {

    /**
     * Retourneert de waarde van het attribuut.
     *
     * @return Waarde van het attribuut.
     */
    T getWaarde();

    /**
     * Geeft aan of het attribuut een waarde heeft (niet null is).
     *
     * @return of het attribuut een waarde heeft.
     */
    boolean heeftWaarde();

    /**
     * Zet de boolean die bepaald of het attribuut geleverd mag worden.
     *
     * @param magGeleverdWorden De boolean.
     */
    void setMagGeleverdWorden(boolean magGeleverdWorden);

    /**
     * Geeft de boolean die bepaald of het attribuut geleverd mag worden.
     *
     * @return De boolean true als er geleverd mag worden, anders false.
     */
    boolean isMagGeleverdWorden();

    /**
     * Zet de groep waaraan dit attribuut gekoppeld is.
     * @param groep de groep
     */
    void setGroep(Groep groep);

    /**
     * Geeft de groep terug waaraan dit attribuut gekoppeld is.
     *
     * @return de groep
     */
    Groep getGroep();



}
