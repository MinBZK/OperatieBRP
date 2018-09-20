/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.antwoord;

import static nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte.FOUT;
import static nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte.SYSTEEM;

import java.util.ArrayList;
import java.util.List;


/**
 * Abstracte implementatie van de {@link BrpAntwoord} interface, die een standaard constructor biedt en reeds de
 * benodigde getters en setters implementeert.
 */
public abstract class AbstractBerichtAntwoord implements BerichtAntwoord {

    private final List<BerichtVerwerkingsFout> fouten;
    private Long                               leveringId;

    /**
     * Standaard constructor waarbij de lijst van fouten in het antwoord naar een lege lijst wordt geinitialiseerd.
     */
    protected AbstractBerichtAntwoord() {
        fouten = new ArrayList<BerichtVerwerkingsFout>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtVerwerkingsFout> getFouten() {
        return fouten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void voegFoutToe(final BerichtVerwerkingsFout fout) {
        if (fout.getZwaarte() == FOUT || fout.getZwaarte() == SYSTEEM) {
            wisContent();
        }
        fouten.add(fout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLeveringId(final Long leveringId) {
        this.leveringId = leveringId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getLeveringId() {
        return leveringId;
    }

}
