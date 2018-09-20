/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

/**
 * POJO voor het bericht van de BRP.
 */
public class BerichtRequest {

    private Bijhoudingsverzoek bijhoudingsverzoek;

    public BerichtRequest() {
    }

    public BerichtRequest(final Bijhoudingsverzoek bijhoudingsverzoek) {
        this.bijhoudingsverzoek = bijhoudingsverzoek;
    }

    public Bijhoudingsverzoek getBijhoudingsverzoek() {
        return bijhoudingsverzoek;
    }

    public void setBijhoudingsverzoek(final Bijhoudingsverzoek bijhoudingsverzoek) {
        this.bijhoudingsverzoek = bijhoudingsverzoek;
    }

}
