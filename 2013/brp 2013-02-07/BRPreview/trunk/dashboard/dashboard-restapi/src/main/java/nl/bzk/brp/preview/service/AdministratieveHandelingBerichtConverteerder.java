/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import java.util.Calendar;

import nl.bzk.brp.preview.model.AdministratieveHandelingBericht;
import nl.bzk.brp.preview.model.Bericht;

/**
 * Deze klasse wordt gebruikt om administratieve handeling berichten naar een generiek Bericht te converteren.
 */
public final class AdministratieveHandelingBerichtConverteerder {

    /**
     * Instantieert een nieuwe administratieve handeling bericht converteerder.
     */
    private AdministratieveHandelingBerichtConverteerder() {
    }

    /**
     * Converteerd een administratieve handeling bericht naar een generiek bericht object dat kan worden opgeslagen
     * in de database.
     *
     * @param administratieveHandelingBericht
     *         Het Administratieve Handeling Bericht.
     * @return Het gevulde Bericht object.
     */
    public static Bericht converteerNaarBericht(final AdministratieveHandelingBericht administratieveHandelingBericht) {
        final Bericht bericht = new Bericht();

        bericht.setPartij(administratieveHandelingBericht.getPartij());
        bericht.setBericht(administratieveHandelingBericht.creeerBerichtTekst());
        bericht.setBerichtDetails(administratieveHandelingBericht.creeerDetailsTekst());
        bericht.setSoortBijhouding(administratieveHandelingBericht.getSoortBijhouding());
        final Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.setTime(administratieveHandelingBericht.getTijdRegistratie());
        bericht.setVerzondenOp(verzondenOp);
        bericht.setBurgerservicenummers(administratieveHandelingBericht.creeerBsnLijst());
        bericht.setBurgerZakenModule(administratieveHandelingBericht.getBzm());
        bericht.setPrevalidatie(administratieveHandelingBericht.isPrevalidatie());

        bericht.setAantalMeldingen(administratieveHandelingBericht.getMeldingen().size());

        return bericht;
    }

}
