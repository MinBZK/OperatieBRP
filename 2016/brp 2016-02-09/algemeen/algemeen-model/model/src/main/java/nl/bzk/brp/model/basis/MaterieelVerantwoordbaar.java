/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Inteface voor historie classes die materieel verantwoordbaar zijn.
 *
 * @param <T> Type verantwoordingsentiteit, dat kan op dit moment een actie of dienst zijn.
 */
public interface MaterieelVerantwoordbaar<T extends VerantwoordingsEntiteit> extends FormeelVerantwoordbaar<T> {

    /**
     * Retourneert de verantwoording die geleid heeft tot de aanpassing geldigheid van het historie object.
     *
     * @return verantwoording aanpassing geldigheid
     */
    T getVerantwoordingAanpassingGeldigheid();

    /**
     * Zet de verantwoording die geleid heeft tot de aanpassing geldigheid van het historie object.
     *
     * @param verantwoodingAanpassingGeldigheid
     *         de verantwoording aanpassing geldigheid
     */
    void setVerantwoordingAanpassingGeldigheid(final T verantwoodingAanpassingGeldigheid);
}
