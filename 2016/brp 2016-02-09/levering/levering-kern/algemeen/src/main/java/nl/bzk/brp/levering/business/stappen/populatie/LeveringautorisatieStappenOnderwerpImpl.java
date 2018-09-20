/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.stappen.populatie;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;


/**
 * Het onderwerp voor de leveringsautorisatie-stappen.
 */
public class LeveringautorisatieStappenOnderwerpImpl implements LeveringautorisatieStappenOnderwerp {

    private Leveringinformatie leveringAutorisatie;
    private Long               administratieveHandelingId;
    private Stelsel            stelsel;

    /**
     * Set een leveringsautorisatie element.
     *
     * @param leveringAutorisatie        De leveringAutorisatie.
     * @param administratieveHandelingId De ID van de administratieve handeling
     * @param stelsel                    Het stelsel waarop geleverd gaat worden
     */
    public LeveringautorisatieStappenOnderwerpImpl(final Leveringinformatie leveringAutorisatie,
        final Long administratieveHandelingId,
        final Stelsel stelsel)
    {
        this.leveringAutorisatie = leveringAutorisatie;
        this.administratieveHandelingId = administratieveHandelingId;
        this.stelsel = stelsel;
    }

    /**
     * Geeft een leveringsautorisatie cache element.
     *
     * @return Het leveringsautorisatie cache element.
     */
    public final Leveringinformatie getLeveringinformatie() {
        return leveringAutorisatie;
    }

    @Override
    public final Stelsel getStelsel() {
        return stelsel;
    }

    /**
     * Geeft de administratieve handeling ID terug.
     *
     * @return de ID
     */
    public final Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

}
