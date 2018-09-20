/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

/**
 * Configuratie object dat BRP configuratie parameters inleest uit een properties bestand dat in de classpath staat.
 * De parameters worden via deze class aan de rest van de applicatie ter beschikking gesteld.
 * LET OP: BrpConfiguratie mag enkel gebruikt worden voor parameters die RunTime mogen worden aangepast.
 */
public interface BrpBusinessConfiguratie {

    /**
     * Vraagt het database time out property op wat geconfigureerd staat in het bestande brp.properties.
     *
     * @return Het aantal seconden voordat een database time out optreedt, of de standaardwaarde indien de configuratie
     *         niet is opgegeven in het configuratie bestand.
     * @brp.bedrijfsregel BRPE0009, FTPE0004
     */
    int getDatabaseTimeOutProperty();
}
