/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository;

/**
 * Interface voor bepalen of een runtime al is gestart.
 * 
 */
public interface RuntimeRepository {

    /**
     * Voegt de meegegeven runtime toe aan de runtime tabel.
     * 
     * @param runtimeNaam
     *            De runtime die we willen opslaan.
     * @return True indien de runtime kon worden opgeslagen, fals in andere gevallen.
     */
    boolean voegRuntimeToe(String runtimeNaam);

    /**
     * Verwijdert de meegegeven runtime uit de runtime tabel.
     * 
     * @param runtimeNaam
     *            De runtime waarvoor we de status willen wijzigen.
     */
    void verwijderRuntime(String runtimeNaam);

}
