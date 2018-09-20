/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

/**
 * Bericht detail view.
 */
public final class BerichtDetailView implements BerichtView {

    private final BerichtModel bericht;
    private final AdministratieveHandelingModel administratieveHandeling;
    private final Leveringsautorisatie leveringsautorisatie;

    /**
     * Constructor.
     *
     * @param bericht
     *            bericht
     * @param administratieveHandeling
     *            administratieve handeling
     * @param leveringsautorisatie
     *            leveringsautorisatie
     */
    public BerichtDetailView(
        final BerichtModel bericht,
        final AdministratieveHandelingModel administratieveHandeling,
        final Leveringsautorisatie leveringsautorisatie)
    {
        this.bericht = bericht;
        this.administratieveHandeling = administratieveHandeling;
        this.leveringsautorisatie = leveringsautorisatie;
    }

    /**
     * Geef bericht.
     *
     * @return bericht
     */
    public BerichtModel getBericht() {
        return bericht;
    }

    /**
     * Geef administratieve handeling.
     *
     * @return administratieve handeling
     */
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Geef leveringsautorisatie.
     *
     * @return leveringsautorisatie
     */
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }
}
