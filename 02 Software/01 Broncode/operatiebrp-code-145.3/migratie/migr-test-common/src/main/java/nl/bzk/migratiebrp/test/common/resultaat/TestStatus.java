/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.resultaat;

/**
 * Enum met de mogelijke test-uitkomsten van de conversie van (een onderdeel van) een testgeval.
 */
public enum TestStatus {
    /**
     * OK.
     */
    OK, /**
     * Niet OK.
     */
    NOK, /**
     * Er is een exception opgetreden.
     */
    EXCEPTIE, /**
     * Er is geen verwachting gevonden voor dit testgeval.
     */
    GEEN_VERWACHTING
}
