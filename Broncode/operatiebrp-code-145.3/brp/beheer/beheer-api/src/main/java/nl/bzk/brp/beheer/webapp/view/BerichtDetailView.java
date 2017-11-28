/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;

/**
 * Bericht detail view.
 */
public final class BerichtDetailView implements BerichtView {

    private final Bericht bericht;
    private final AdministratieveHandeling administratieveHandeling;
    private final Leveringsautorisatie leveringsautorisatie;

    /**
     * Constructor.
     * @param bericht bericht
     * @param administratieveHandeling administratieve handeling
     * @param leveringsautorisatie leveringsautorisatie
     */
    public BerichtDetailView(
        final Bericht bericht,
        final AdministratieveHandeling administratieveHandeling,
        final Leveringsautorisatie leveringsautorisatie) {
        this.bericht = bericht;
        this.administratieveHandeling = administratieveHandeling;
        this.leveringsautorisatie = leveringsautorisatie;
    }

    /**
     * Geef bericht.
     * @return bericht
     */
    public Bericht getBericht() {
        return bericht;
    }

    /**
     * Geef administratieve handeling.
     * @return administratieve handeling
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Geef leveringsautorisatie.
     * @return leveringsautorisatie
     */
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }
}
