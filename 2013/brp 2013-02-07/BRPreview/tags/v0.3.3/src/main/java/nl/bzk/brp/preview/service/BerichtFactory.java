/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.service;

import java.util.Calendar;

import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.OndersteundeBijhoudingsTypes;


/**
 * A factory for creating Bericht objects.
 *
 */
public class BerichtFactory {

    /** De constante _SIZE die het aantal opties aangeeft in de diverse arrays. */
    private static final int                            _SIZE     = 6;

    /** De constante steden. */
    private static final String[]                       steden    = { "Amsterdam", "Haarlem", "Groningen", "Rotterdam",
        "Utrecht", "'s-Gravenhage"                               };

    private static final String[]                       bzms      = { "CentricBZM", "ProcuraBZM", "AtosBZM", "VierBZM",
        "VijfBZM", "ZesBZM"                                      };
    private static final String[]                       berichten =
                                                                      {
        "Amsterdam heeft een nieuwgeborene ingeschreven. Johan is op 2 mei 2012 geboren te Amsterdam. Fred en Marjan zijn de ouders.",
        "Haarlem heeft een verhuizing geprevalideerd", "Groningen heeft een verhuizing geprevaleerd",
        "Rotterdam heeft een nieuwgeborene ingeschreven", "Utrecht heeft een binnengemeentelijke verhuizing gemeld",
        "'s Gravenhage heeft een verhuizing geprevalideerd"          };

    private static final OndersteundeBijhoudingsTypes[] soorten   = { OndersteundeBijhoudingsTypes.GEBOORTE,
        OndersteundeBijhoudingsTypes.VERHUIZING, OndersteundeBijhoudingsTypes.VERHUIZING,
        OndersteundeBijhoudingsTypes.VERHUIZING, OndersteundeBijhoudingsTypes.GEBOORTE,
        OndersteundeBijhoudingsTypes.VERHUIZING                  };

    /**
     * Maak een bericht met random waarden.
     *
     * @return de bericht
     */
    public Bericht maakBericht() {
        Bericht bericht;
        Calendar verzendDatum = Calendar.getInstance();
        int berichtId = getNextIndex();
        verzendDatum.add(Calendar.HOUR, -berichtId);

        bericht = new Bericht();
        bericht.setPartij(steden[berichtId]);
        bericht.setBericht(berichten[berichtId]);
        bericht.setBerichtDetails("Hier komen verdere details die alleen bij de laatste berichten getoond worden.");
        bericht.setAantalMeldingen(0);
        bericht.setVerzondenOp(verzendDatum);
        bericht.setBurgerZakenModule(bzms[berichtId]);
        bericht.setSoortBijhouding(soorten[berichtId]);

        return bericht;
    }

    /**
     * Gets the next index.
     *
     * @return the next index
     */
    private static int getNextIndex() {
        return (int) Math.floor(Math.random() * _SIZE);
    }

    /**
     * Gets the single instance of BerichtFactory.
     *
     * @return single instance of BerichtFactory
     */
    public static final BerichtFactory getInstance() {
        return new BerichtFactory();
    }

}
