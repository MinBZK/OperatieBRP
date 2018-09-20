/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.exception;

/**
 * Exceptie die optreedt als wordt geprobeerd een bedrijfsregel te valideren maar de validatie klasse voor die regel
 * bestaat niet of als wordt geprobeerd een berichtverzoek te valideren waarvoor de validatie niet geschikt is.
 */
public class OnbekendeValidatieExceptie extends Exception {

}
