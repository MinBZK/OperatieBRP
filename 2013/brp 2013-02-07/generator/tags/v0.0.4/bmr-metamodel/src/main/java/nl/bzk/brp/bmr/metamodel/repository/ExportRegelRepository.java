/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.repository;

public interface ExportRegelRepository {

    /**
     * Haal de export identifier (GUID) op als die bekend is. Als er nog geen export regel is voor de combinatie van
     * interneIdentifier en naam, voeg die dan toe, samen met de syncId, zodat we weten wat we naar Enteprise Architect
     * ge&euml;xporteerd hebben.
     *
     * @param interneIdentifier Een naam voor het soort element waarvoor we een identifier onderhouden.
     * @param naam De naam van het element waarvoor we een identifier onderhouden.
     * @param syncId De syncId van het element waarvoor we een identifier onderhouden.
     * @return De GUID van Enterprise Architect, of een zelfgemaakt tijdelijk substituut.
     */
    String getExportIdentifier(final String interneIdentifier, final String naam, final Integer syncId);

    /**
     * Bewaar de export identifier (GUID) van Enterprise Architect.
     *
     * @param interneIdentifier Een naam voor het soort element waarvoor we een identifier onderhouden.
     * @param naam De naam van het element waarvoor we een identifier onderhouden.
     * @param exportIdentifier De GUID van Enterprise Architect.
     */
    void setExportIdentifier(final String interneIdentifier, final String naam, final String exportIdentifier);
}
