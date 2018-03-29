/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model;

/**
 * Definieert het generieke gedrag van persoonslijsten.
 */
public interface Persoonslijst {

    /**
     * Geef de waarde van actueel administratienummer.
     * @return het actuele A-nummer van deze persoonslijst
     */
    String getActueelAdministratienummer();

    /**
     * Geef de waarde van actuele burgerservicenummer.
     * @return het actuele burgerservicenummer van deze persoonslijst
     */
    String getActueelBurgerservicenummer();
}
