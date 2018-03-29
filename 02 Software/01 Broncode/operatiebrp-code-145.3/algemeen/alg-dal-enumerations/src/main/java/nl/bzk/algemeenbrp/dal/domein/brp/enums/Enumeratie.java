/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Interface voor alle enumeraties.
 */
public interface Enumeratie {

    /**
     * Geef de waarde van id van Enumeratie.
     *
     * @return de waarde van id van Enumeratie
     */
    int getId();

    /**
     * Geef de waarde van code van Enumeratie.
     *
     * @return de waarde van code van Enumeratie
     * @throws UnsupportedOperationException als de Enumeratie geen code bevat.
     */
    String getCode();

    /**
     * @return true als deze Enumeratie een code heeft, anders false.
     */
    boolean heeftCode();

    /**
     * @return Geeft de naam terug die gebruikt wordt voor de Enumeratie.
     */
    String getNaam();
}
