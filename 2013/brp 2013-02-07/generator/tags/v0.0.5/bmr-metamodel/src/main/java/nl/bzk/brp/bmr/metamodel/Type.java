/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import javax.persistence.Entity;


/**
 * Gemeenschappelijk supertype van ObjectType en AttribuutType.
 */
@Entity
public abstract class Type extends GelaagdElement {

    /**
     * Handige methode om te bepalen of een instantie een AttribuutType is.
     *
     * @return <code>true</code> als dit een AttribuutType is, anders <code>false</code>.
     */
    public abstract boolean isAttribuutType();

    /**
     * Handige methode om te bepalen of een instantie een ObjectType is.
     *
     * @return <code>true</code> als dit een ObjectType is, anders <code>false</code>.
     */
    public abstract boolean isObjectType();
}
