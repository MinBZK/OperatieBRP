/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.berichtcmds;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;


/**
 * Abstracte implementatie van de {@link BerichtCommand} interface, die een standaard constructor biedt en reeds
 * de benodigde getters en setters implementeert.
 *
 * @param <T> Type van het verzoek object dat dit command kan verwerken..
 * @param <U> Type van het antwoord object dat dit command object retourneert.
 */
public abstract class AbstractBerichtCommand<T extends BerichtVerzoek<U>, U extends BerichtAntwoord> implements
        BerichtCommand<T, U>
{

    private final T                            verzoek;
    private final BerichtContext            context;

    /**
     * Standaard constructor waarbij het verzoek en de context van het bericht direct worden geinitialiseerd.
     *
     * @param verzoek het verzoek object van het bericht.
     * @param context de context van het bericht.
     */
    public AbstractBerichtCommand(final T verzoek, final BerichtContext context) {
        this.verzoek = verzoek;
        this.context = context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void voerUit(U antwoord);

    /**
     * Retourneert het initiele verzoek object; het object met de parameters voor het verzoek.
     * @return het verzoek object.
     */
    protected final T getVerzoek() {
        return verzoek;
    }

    /**
     * Retourneert de context waar binnen het bericht wordt afgehandeld.
     * @return de context van het bericht.
     */
    protected final BerichtContext getContext() {
        return context;
    }

}
