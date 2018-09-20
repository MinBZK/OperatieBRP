/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Basis klasse voor alle groepen in een bericht en biedt de standaard implementatie voor de methodes van de {@link BerichtEntiteitGroep} interface.
 */
@SuppressWarnings("serial")
public abstract class AbstractBerichtEntiteitGroep extends AbstractBerichtIdentificeerbaar implements
    BerichtEntiteitGroep, BerichtIdentificeerbaar
{

    private String voorkomenId;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVoorkomenId() {
        return voorkomenId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVoorkomenId(final String id) {
        this.voorkomenId = id;
    }

}
