/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.adapter.excel;

/**
 * Voor het afhandelen van fouten die tijdens het parsen van een XSL bestand optreden.
 * 
 * 
 * 
 */
public class ExcelAdapterException extends Exception {

    private static final long serialVersionUID = -2551179271427383995L;

    /**
     * Maakt een nieuwe ExcelParserException aan die aangeeft dat er iets fout ging tijdens het parsen van een Excel
     * bestand.
     * 
     * @param kolom
     *            De kolom waarin de fout optreedt
     * @param cause
     *            De onderliggende Exception
     */
    public ExcelAdapterException(final String kolom, final Exception cause) {
        this(kolom, cause.getMessage());
        this.initCause(cause);
    }

    /**
     * Maakt een nieuwe ExcelParserException aan die aangeeft dat er iets fout ging tijdens het parsen van een Excel
     * bestand.
     * 
     * @param kolom
     *            De kolom waarin de fout optreedt
     * @param message
     *            De reden van de fout
     */
    public ExcelAdapterException(final String kolom, final String message) {
        super("kolom " + kolom + ": " + message);
    }
}
