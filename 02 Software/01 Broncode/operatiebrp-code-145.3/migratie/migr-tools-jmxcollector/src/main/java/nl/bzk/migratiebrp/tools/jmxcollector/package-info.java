/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Package bevat classes om JMX attributen bij verschillende hosts op te vragen
 * en te versturen naar een graphite server. In Connecties zijn de verschillende
 * jmx mbean servers geconfigureerd. In Metingen is een lijst van attributen
 * geconfigureerd welke worden opgehaald en verstuurd. Meting is een individuele
 * meting die wordt opgehaald. Het eigenlijke ophalen van een attribuut wordt door
 * Meting gedaan en er wordt een MetingResultaat teruggegeven.
 *
 * Meter zorgt ervoor dat per interval de metingen worden verricht en de
 * resultaten worden verstuurd naar de Graphiteserver.
 */
package nl.bzk.migratiebrp.tools.jmxcollector;
