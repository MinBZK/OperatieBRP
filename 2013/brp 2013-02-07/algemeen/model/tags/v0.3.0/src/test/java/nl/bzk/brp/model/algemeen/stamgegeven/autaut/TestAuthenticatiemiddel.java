/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.IPAdres;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;

/**
*
* Authenticatiemiddel ten behoeve van unnitest.
*
*/
public class TestAuthenticatiemiddel extends Authenticatiemiddel {
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
    public TestAuthenticatiemiddel(final Partij partij, final Rol rol, final Functie functie,
            final Certificaat certificaatTbvSSL, final Certificaat certificaatTbvOndertekening, final IPAdres iPAdres,
            final StatusHistorie authenticatiemiddelStatusHis)
    {
        super(partij, rol, functie, certificaatTbvSSL, certificaatTbvOndertekening, iPAdres,
                authenticatiemiddelStatusHis);
    }

}
