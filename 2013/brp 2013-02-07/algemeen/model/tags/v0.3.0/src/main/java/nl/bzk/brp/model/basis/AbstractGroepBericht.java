/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;


import nl.bzk.brp.model.binding.BindingUtil;

import org.jibx.runtime.IUnmarshallingContext;

/**
 * Basis klasse voor alle groepen.
 */
@SuppressWarnings("serial")
public abstract class AbstractGroepBericht implements Groep, Identificeerbaar {

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
        return this.communicatieID;
    }

    @Override
    public String getEntiteitType() {
        return this.entiteitType;
    }

    @Override
    public String getReferentieID() {
        return this.referentieID;
    }

    @Override
    public void setReferentieID(final String  referentieID) {
        this.referentieID = referentieID;
    }

    public String getTechnischeSleutel() {
        return this.technischeSleutel;
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

    /**
     * Entiteit type is een stuff attribuut waarmee die verteld om welk soort (Logische Ontwerp) type het gaat.
     * @param entiteitType de type
     */
    public void setEntiteitType(final String entiteitType) {
        this.entiteitType = entiteitType;
    }
}
