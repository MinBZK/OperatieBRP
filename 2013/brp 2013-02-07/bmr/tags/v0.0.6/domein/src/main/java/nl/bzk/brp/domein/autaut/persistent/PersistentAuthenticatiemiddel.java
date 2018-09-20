/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.autaut.persistent;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.domein.autaut.Authenticatiemiddel;
import nl.bzk.brp.domein.autaut.persistent.basis.AbstractPersistentAuthenticatiemiddel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Entity
@Table(name = "Authenticatiemiddel", schema = "AutAut")
public class PersistentAuthenticatiemiddel extends AbstractPersistentAuthenticatiemiddel implements Authenticatiemiddel
{

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistentAuthenticatiemiddel.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public InetAddress getIPAdresAsInetAddress() {
        InetAddress inetAddress = null;

        String ipAdres = super.getIPAdres();
        if (ipAdres != null) {
            try {
                inetAddress = InetAddress.getByName(ipAdres);
            } catch (UnknownHostException e) {
                LOGGER.error("Converteren van Inet Adres ({}) uit de database voor authenticatiemiddel met id {} is "
                    + "mislukt.", ipAdres, getID());
            }
        }
        return inetAddress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIPAdresAsInetAddress(final InetAddress inetAdres) {
        super.setIPAdres(inetAdres.getHostAddress());
    }
}
