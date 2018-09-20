/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.logisch.basis;

import nl.bzk.brp.model.attribuuttype.IPAdres;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Certificaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Functie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Rol;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * .
 *
 */
public interface AuthenticatieMiddelBasis extends ObjectType {

    /**
     * .
     *
     * @return .
     */
    Partij getPartij();

    /**
     * .
     *
     * @return .
     */
    Rol getRol();

    /**
     * .
     *
     * @return .
     */
    Functie getFunctie();

    /**
     * .
     *
     * @return .
     */
    Certificaat getOndertekeningsCertificaat();

    /**
     * .
     *
     * @return .
     */
    Certificaat getSslCertificaat();

    /**
     * .
     *
     * @return .
     */
    IPAdres getIpAdres();

    /**
     * .
     *
     * @return .
     */
    StatusHistorie getStatushistorie();
}
