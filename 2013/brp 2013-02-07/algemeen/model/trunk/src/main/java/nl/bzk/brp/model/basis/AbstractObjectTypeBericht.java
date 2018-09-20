/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;


import nl.bzk.brp.model.binding.BindingUtil;

import org.jibx.runtime.IUnmarshallingContext;

/**
 * Basis class voor alle bericht dynamische object type classes.
 *
 */
public abstract class AbstractObjectTypeBericht implements ObjectType, Identificeerbaar {

    private String communicatieID;
    private String entiteitType;
    private String referentieID;
    private String technischeSleutel;

    @Override
    public void setCommunicatieID(final String communicatieId) {
        this.communicatieID = communicatieId;
    }

    @Override
    public String getCommunicatieID() {
        return communicatieID;
    }

    public String getEntiteitType() {
        return entiteitType;
    }

    public String getReferentieID() {
        return referentieID;
    }

    public void setReferentieID(final String  referentieID) {
        this.referentieID = referentieID;
    }

    public String getTechnischeSleutel() {
        return technischeSleutel;
    }

    public void setTechnischeSleutel(final String technischeSleutel) {
        this.technischeSleutel = technischeSleutel;
    }

    /**
     * Hook voor Jibx om dit object te registreren als IdentificeerbaarObject.
     * @param ctx de jibx context
     */
    public void jibxPostSet(final IUnmarshallingContext ctx) {
        BindingUtil.voegToeIdentificeerbaarObjectAanJixbUserContext(ctx, this);
    }
}
