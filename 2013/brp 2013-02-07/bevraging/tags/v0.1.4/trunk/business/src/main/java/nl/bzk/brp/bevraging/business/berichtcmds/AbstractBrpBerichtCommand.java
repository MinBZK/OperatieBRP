/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.berichtcmds;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BRPAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BRPVerzoek;


/**
 * Abstracte implementatie van de {@link BrpBerichtCommand} interface, die een standaard constructor biedt en reeds
 * de benodigde getters en setters implementeert.
 *
 * @param <T> Type van het verzoek object.
 * @param <U> Type van het antwoord object.
 */
public abstract class AbstractBrpBerichtCommand<T extends BRPVerzoek, U extends BRPAntwoord> implements
        BrpBerichtCommand<T, U>
{

    private final List<BerichtVerwerkingsFout> fouten;
    private final T                            verzoek;
    private final BrpBerichtContext            context;
    private U                                  antwoord;

    /**
     * Standaard constructor waarbij het verzoek en de context van het bericht direct worden geinitialiseerd.
     *
     * @param verzoek het verzoek object van het bericht.
     * @param context de context van het bericht.
     */
    public AbstractBrpBerichtCommand(final T verzoek, final BrpBerichtContext context) {
        fouten = new ArrayList<BerichtVerwerkingsFout>();
        this.verzoek = verzoek;
        this.context = context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void voerUit();

    /**
     * {@inheritDoc}
     */
    @Override
    public final T getVerzoek() {
        return verzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final U getAntwoord() {
        return antwoord;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BrpBerichtContext getContext() {
        return context;
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
        fouten.add(fout);
    }

    /**
     * Zet het antwoord object.
     *
     * @param antwoord het antwoord object.
     */
    protected final void setAntwoord(final U antwoord) {
        this.antwoord = antwoord;
    }

}
