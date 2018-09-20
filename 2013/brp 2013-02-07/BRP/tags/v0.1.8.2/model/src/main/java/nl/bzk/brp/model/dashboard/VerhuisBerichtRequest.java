/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.dashboard;

/**
 * Superclass voor alle notificatieberichten.
 */
public class VerhuisBerichtRequest extends AbstractBerichtRequest {

    private Persoon persoon;

    private Adres   oudAdres;

    private Adres   nieuwAdres;

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public Adres getOudAdres() {
        return oudAdres;
    }

    public void setOudAdres(final Adres oudAdres) {
        this.oudAdres = oudAdres;
    }

    public Adres getNieuwAdres() {
        return nieuwAdres;
    }

    public void setNieuwAdres(final Adres nieuwAdres) {
        this.nieuwAdres = nieuwAdres;
    }

}
