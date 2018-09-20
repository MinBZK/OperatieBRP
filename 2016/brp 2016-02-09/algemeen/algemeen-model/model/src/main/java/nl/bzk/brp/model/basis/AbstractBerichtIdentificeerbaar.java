/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import org.jibx.runtime.IUnmarshallingContext;


/**
 * Basis klasse voor alle in een bericht identificeerbare elementen (elementen die dus een communicatie id kunnen hebben).
 */
public abstract class AbstractBerichtIdentificeerbaar implements BerichtIdentificeerbaar {

    private String communicatieID;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCommunicatieID() {
        return communicatieID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommunicatieID(final String communicatieId) {
        this.communicatieID = communicatieId;
    }

    /**
     * Hook voor Jibx om dit object te registreren als IdentificeerbaarObject.
     *
     * @param ctx de jibx context
     */
    public void jibxPostSet(final IUnmarshallingContext ctx) {
        voegToeIdentificeerbaarObjectAanJixbUserContext(ctx, this);
    }

    /**
     * Voegt een identificerend object toe aan de jibx context.
     *
     * @param ctx    context van jibx
     * @param object het object dat toegoevoegd moet worden aan de context
     */
    private void voegToeIdentificeerbaarObjectAanJixbUserContext(final IUnmarshallingContext ctx,
        final BerichtIdentificeerbaar object)
    {
        if (ctx.getUserContext() == null) {
            final CommunicatieIdMap communicatieIds = new CommunicatieIdMap();
            communicatieIds.put(object);
            ctx.setUserContext(communicatieIds);
            ((CommunicatieIdMapBevattend) ctx.getStackObject(ctx.getStackDepth() - 1))
                .setCommunicatieIdMap(communicatieIds);
        } else {
            ((CommunicatieIdMap) ctx.getUserContext()).put(object);
        }
    }

}
