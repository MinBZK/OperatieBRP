/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BijhoudingsplanBericht;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandelingStandaardGroepBasis;

/**
 * Deze groep is opgenomen om de BijhoudingsPlan onderop te kunnen sorteren. Omdat attributen per groep gesorteerd zijn,
 * was dit niet mogelijk als het attribuut in de Identiteitsgroep zat.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractAdministratieveHandelingStandaardGroepBericht implements Groep, AdministratieveHandelingStandaardGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 21582;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(21549);
    private BijhoudingsplanBericht bijhoudingsplan;

    /**
     * Retourneert Bijhoudingsplan van Standaard.
     *
     * @return Bijhoudingsplan.
     */
    public BijhoudingsplanBericht getBijhoudingsplan() {
        return bijhoudingsplan;
    }

    /**
     * Zet Bijhoudingsplan van Standaard.
     *
     * @param bijhoudingsplan Bijhoudingsplan.
     */
    public void setBijhoudingsplan(final BijhoudingsplanBericht bijhoudingsplan) {
        this.bijhoudingsplan = bijhoudingsplan;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);
    }

}
