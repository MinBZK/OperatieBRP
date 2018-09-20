/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.dashboard;

/**
 * Adrescorrectie notificatie voor dashboard.
 */
public class AdresCorrectieBerichtRequest extends VerhuisBerichtRequest {

    //TODO: wat moet er in zitten dat anders is dan het VerhuisBericht, anders dit niet nodig!
    private Persoon persoon;

    private Adres   oudAdres;

    private Adres   nieuwAdres;

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    @Override
    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    @Override
    public Adres getOudAdres() {
        return oudAdres;
    }

    @Override
    public void setOudAdres(final Adres oudAdres) {
        this.oudAdres = oudAdres;
    }

    @Override
    public Adres getNieuwAdres() {
        return nieuwAdres;
    }

    @Override
    public void setNieuwAdres(final Adres nieuwAdres) {
        this.nieuwAdres = nieuwAdres;
    }

}
