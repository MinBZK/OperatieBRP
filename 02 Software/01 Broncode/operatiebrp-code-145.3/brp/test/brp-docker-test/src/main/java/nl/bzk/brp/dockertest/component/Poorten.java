/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

/**
 */
public final class Poorten {

    public static final int APPSERVER_PORT = 8_080;
    public static final int HTTP_POORT = 80;
    public static final int JMS_POORT = 61_616;
    public static final int JMS_POORT_SELECTIE = 61_618;
    public static final int DB_POORT_5432 = 5_432;

    //is gedefinieerd als default in JMXAgent in alg-webapp-jmx
    public static final int JMX_POORT = 3_481;
    public static final int DEBUG_POORT = 5_005;
}
