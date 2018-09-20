/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.dashboard;

/**
 * Notificatiebericht voor dashbord.
 */
public class GeboorteBerichtRequest extends nl.bzk.copy.model.dashboard.AbstractBerichtRequest {

    private Persoon nieuwgeborene;

    private nl.bzk.copy.model.dashboard.Adres adresNieuwgeborene;

    private Persoon ouder1;

    private Persoon ouder2;

    public Persoon getNieuwgeborene() {
        return nieuwgeborene;
    }

    public void setNieuwgeborene(final Persoon persoon) {
        nieuwgeborene = persoon;
    }

    public nl.bzk.copy.model.dashboard.Adres getAdresNieuwgeborene() {
        return adresNieuwgeborene;
    }

    public void setAdresNieuwgeborene(final Adres adresNieuwgeborene) {
        this.adresNieuwgeborene = adresNieuwgeborene;
    }

    public Persoon getOuder1() {
        return ouder1;
    }

    public void setOuder1(final Persoon ouder1) {
        this.ouder1 = ouder1;
    }

    public Persoon getOuder2() {
        return ouder2;
    }

    public void setOuder2(final Persoon ouder2) {
        this.ouder2 = ouder2;
    }

}
