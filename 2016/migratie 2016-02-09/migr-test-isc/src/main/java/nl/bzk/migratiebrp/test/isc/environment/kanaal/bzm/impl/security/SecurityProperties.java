/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl.security;

import java.util.Properties;

/**
 * Security properties voor de WS-Security Signature. Properties worden gevuld door Spring, zie config xml.
 */
public class SecurityProperties {
    private static Properties propertiesOut;
    private static Properties propertiesIn;
    private static final String MERLIN_ALIAS_KEY = "org.apache.ws.security.crypto.merlin.keystore.alias";
    private static final String MERLIN_PASSWD_KEY = "org.apache.ws.security.crypto.merlin.keystore.password";

    /**
     * Constructor voor {@link SecurityProperties} waarmee de in/out properties doorgegeven worden.
     * 
     * @param propertiesOut
     *            properties voor output
     * @param propertiesIn
     *            properties voor input
     */
    public SecurityProperties(final Properties propertiesOut, final Properties propertiesIn) {
        setPropertiesOut(propertiesOut);
        setPropertiesIn(propertiesIn);
    }

    /**
     * Vult de propertiesOut aan met de alias en geeft alle properties terug.
     * 
     * @param alias
     *            String
     * @return the propertiesOut
     */
    public static Properties getPropertiesOut(final String alias) {
        propertiesOut.setProperty(MERLIN_ALIAS_KEY, alias);
        return propertiesOut;
    }

    /**
     * Geef de waarde van properties in.
     *
     * @return the propertiesIn
     */
    public static Properties getPropertiesIn() {
        return propertiesIn;
    }

    /**
     * Geeft het private keystore password terug.
     * 
     * @return keystore password String
     */
    public static String getPrivateKeystorePassword() {
        return propertiesOut.getProperty(MERLIN_PASSWD_KEY);
    }

    /**
     * Zet de waarde van properties out.
     *
     * @param propertiesOut
     *            properties out
     */
    private static void setPropertiesOut(final Properties propertiesOut) {
        SecurityProperties.propertiesOut = propertiesOut;
    }

    /**
     * Zet de waarde van properties in.
     *
     * @param propertiesIn
     *            properties in
     */
    private static void setPropertiesIn(final Properties propertiesIn) {
        SecurityProperties.propertiesIn = propertiesIn;
    }
}
