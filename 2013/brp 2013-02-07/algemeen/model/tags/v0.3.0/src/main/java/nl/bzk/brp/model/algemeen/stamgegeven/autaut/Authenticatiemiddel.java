/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.IPAdres;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis.AbstractAuthenticatiemiddel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;


/**
 * Een middel waarmee een Partij zich kan authenticeren.
 *
 * Een Authenticatiemiddel wordt gebruikt om de "Authenticiteit" van een Partij te verifi�ren ('is degene die zich
 * voordoet als een Partij ook daadwerkelijk deze Partij?').
 *
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "Authenticatiemiddel")
public class Authenticatiemiddel extends AbstractAuthenticatiemiddel {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Authenticatiemiddel() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param partij partij van Authenticatiemiddel.
     * @param rol rol van Authenticatiemiddel.
     * @param functie functie van Authenticatiemiddel.
     * @param certificaatTbvSSL certificaatTbvSSL van Authenticatiemiddel.
     * @param certificaatTbvOndertekening certificaatTbvOndertekening van Authenticatiemiddel.
     * @param iPAdres iPAdres van Authenticatiemiddel.
     * @param authenticatiemiddelStatusHis authenticatiemiddelStatusHis van Authenticatiemiddel.
     */
    protected Authenticatiemiddel(final Partij partij, final Rol rol, final Functie functie,
            final Certificaat certificaatTbvSSL, final Certificaat certificaatTbvOndertekening, final IPAdres iPAdres,
            final StatusHistorie authenticatiemiddelStatusHis)
    {
        super(partij, rol, functie, certificaatTbvSSL, certificaatTbvOndertekening, iPAdres,
                authenticatiemiddelStatusHis);
    }

}
