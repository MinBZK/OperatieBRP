/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.copy.model.objecttype.logisch.basis.AuthenticatieMiddelBasis;
import nl.bzk.copy.model.objecttype.operationeel.basis.AbstractAuthenticatieMiddelModel;


/**
 * .
 */
@Entity
@Access(AccessType.FIELD)
@Table(schema = "autaut", name = "Authenticatiemiddel")
@SuppressWarnings("serial")
public class AuthenticatieMiddelModel extends AbstractAuthenticatieMiddelModel {

    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param authenticatiemiddel Object type dat gekopieerd dient te worden.
     */
    public AuthenticatieMiddelModel(final AuthenticatieMiddelBasis authenticatiemiddel) {
        super(authenticatiemiddel);
    }

    /**
     * Default constructor. Vereist voor Hibernate.
     */
    private AuthenticatieMiddelModel() {

    }
}
