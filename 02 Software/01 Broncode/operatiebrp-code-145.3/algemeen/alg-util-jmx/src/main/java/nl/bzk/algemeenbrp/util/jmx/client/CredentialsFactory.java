/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.client;

import org.springframework.beans.factory.FactoryBean;

/**
 * JMX Credentials factory.
 */
public final class CredentialsFactory implements FactoryBean<String[]> {

    private static final int CREDENTIALS_SIZE = 2;

    private final String[] credentials = new String[CREDENTIALS_SIZE];

    /**
     * Zet de gebruikersnaam.
     * @param username gebruikersnaam
     */
    public void setUsername(final String username) {
        credentials[0] = username;
    }

    /**
     * Zet het wachtwoord.
     * @param password wachtwoord
     */
    public void setPassword(final String password) {
        credentials[1] = password;
    }

    @Override
    public String[] getObject() {
        final String[] result = new String[credentials.length];
        System.arraycopy(credentials, 0, result, 0, credentials.length);
        return result;
    }

    @Override
    public Class<?> getObjectType() {
        return String[].class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
