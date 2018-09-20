/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.autaut;

import java.net.InetAddress;

import nl.bzk.brp.domein.autaut.basis.BasisAuthenticatiemiddel;


public interface Authenticatiemiddel extends BasisAuthenticatiemiddel {

    /**
     * Het IP adres dat door de Partij gebruikt wordt voor het aansluiten op de BRP.
     * De BRP ondersteunt de mogelijkheid om zogenaamde 'whitelisting' te gebruiken: in dat geval mag een bepaald
     * bericht van een Partij (eventueel: een Partij in een bepaalde rol) alleen afkomstig zijn van een bepaald IP
     * adres. Als er sprake is van een gevuld IP adres, dan worden alleen berichten afkomstig van dat IP adres als
     * potentieel authentieke berichten beschouwd. Deze voorzorgmaatregel is additioneel bovenop maatregelen als
     * Certificaten, en vervangt die beveiligingsdoeleinden niet.
     *
     * @return het IP adres dat door de Partij gebruikt wordt voor het aansluiten op de BRP.
     */
    public InetAddress getIPAdresAsInetAddress();

    /**
     * Zet het IP adres dat door de Partij gebruikt wordt voor het aansluiten op de BRP.
     *
     * @param inetAdres het IP adres dat door de Partij gebruikt wordt voor het aansluiten op de BRP.
     */
    public void setIPAdresAsInetAddress(final InetAddress inetAdres);
}
