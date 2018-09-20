/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.basis;


/**
 * Basis class voor alle bericht dynamische object type classes.
 */
public abstract class AbstractObjectTypeBericht implements ObjectType, Identificeerbaar {

    private String verzendendId;

    @Override
    public void setVerzendendId(final String verzendendId) {
        this.verzendendId = verzendendId;
    }

    @Override
    public String getVerzendendId() {
        return verzendendId;
    }
}
