/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.vergelijk;

/**
 * Vergelijking.
 */
public interface Vergelijking {

    /**
     * Geef de reguliere expressie om deze vergelijking weer te geven.
     * 
     * @return regex string
     */
    String getRegex();

    /**
     * Controleer de inhoud.
     * 
     * @param context
     *            context
     * @param value
     *            te controleren waarde
     * @return true, als de waarde 'gelijk' is, anders false
     */
    boolean check(VergelijkingContext context, String value);
}
