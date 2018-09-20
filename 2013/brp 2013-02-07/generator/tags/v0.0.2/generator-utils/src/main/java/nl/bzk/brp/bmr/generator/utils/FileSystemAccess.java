/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.utils;

/**
 * Schrijven van gegenereerde code naar een file.
 */
public interface FileSystemAccess {

    /**
     * Zegt of een file bestaat of niet.
     * 
     * @param fileName de naam van de file waarvan bepaald wordt of die bestaat.
     * @return <code>true</code> als de file met de gegeven naam bestaat, en anders <code>false</code>.
     */
    boolean exists(final String fileName);

    /**
     * Schrijf gegenereerde code naar file.
     *
     * @param fileName de naam van de file waarnaar code geschreven wordt.
     * @param contents de gegenereerde code die naar de file geschreven wordt.
     */
    void generateFile(final String fileName, final CharSequence contents);
}
