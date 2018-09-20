/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Inteface voor historie classes die formeel verantwoordbaar zijn.
 *
 * @param <T> Type verantwoordingsentiteit, dat kan op dit moment een actie of dienst zijn.
 */
public interface FormeelVerantwoordbaar<T extends VerantwoordingsEntiteit> {

    /**
     * Retourneert de verantwoording die geleid heeft tot de inhoud van het historie object.
     *
     * @return verantwoording inhoud
     */
    T getVerantwoordingInhoud();

    /**
     * Retourneert de verantwoording die geleid heeft tot verval van het historie object.
     *
     * @return verantwoording verval
     */
    T getVerantwoordingVerval();

    /**
     * Zet de verantwoording die geleid heeft tot de inhoud van het historie object.
     *
     * @param verantwoodingInhoud de verantwoording inhoud
     */
    void setVerantwoordingInhoud(final T verantwoodingInhoud);

    /**
     * Zet de verantwoording die geleid heeft tot verval van het historie object.
     *
     * @param verantwoodingVerval de verantwoording verval
     */
    void setVerantwoordingVerval(final T verantwoodingVerval);
}
