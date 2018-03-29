/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Test voor parallel uitvoeren:
 *
 * Dit commando lijkt goed te werken:
 * mvn verify -Dit.test=PTest* -Dparallel=classesAndMethods -DthreadCount=1 -DthreadCountClasses=3 -DthreadCountMethods=5
 * mvn verify -Dit.test=PTest* -Dparallel=classes -DthreadCount=7 -DperCoreThreadCount=false
 */
package nl.bzk.brp.dockertest.jbehave.paralleltest;