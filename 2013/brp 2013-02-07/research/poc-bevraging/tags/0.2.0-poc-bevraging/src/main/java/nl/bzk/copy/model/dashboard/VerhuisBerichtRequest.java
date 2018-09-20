/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.dashboard;

/**
 * Superclass voor alle notificatieberichten.
 */
public class VerhuisBerichtRequest extends nl.bzk.copy.model.dashboard.AbstractBerichtRequest {

    private nl.bzk.copy.model.dashboard.Persoon persoon;

    private nl.bzk.copy.model.dashboard.Adres oudAdres;

    private nl.bzk.copy.model.dashboard.Adres nieuwAdres;

    public nl.bzk.copy.model.dashboard.Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public nl.bzk.copy.model.dashboard.Adres getOudAdres() {
        return oudAdres;
    }

    public void setOudAdres(final nl.bzk.copy.model.dashboard.Adres oudAdres) {
        this.oudAdres = oudAdres;
    }

    public nl.bzk.copy.model.dashboard.Adres getNieuwAdres() {
        return nieuwAdres;
    }

    public void setNieuwAdres(final Adres nieuwAdres) {
        this.nieuwAdres = nieuwAdres;
    }

}
