/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.toegangsbewaking;

import java.util.Set;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.lev.AbonnementSoortBericht;
import org.springframework.stereotype.Service;


/**
 * Standaard implementatie van de {@link ToegangsBewakingService} welke op basis van een opgegeven abonnement en het
 * bericht de autorisatie kan uitvoeren.
 */
@Service
public class StandaardToegangsBewakingService implements ToegangsBewakingService {

    @Override
    public boolean isFunctioneelGeautoriseerd(final Abonnement abonnement, final BrpBerichtCommand berichtCommand) {
        if (abonnement == null || berichtCommand == null) {
            throw new IllegalArgumentException("Abonnement en berichtCommand parameters mogen niet null zijn.");
        }

        SoortBericht soortBericht = berichtCommand.getSoortBericht();
        return bevatSoortBerichtenSetSpecifiekSoortBericht(abonnement.getSoortBerichten(), soortBericht);
    }

    /**
     * Controleert of de opgegeven {@link AbonnementSoortBericht} set de opgegeven {@link SoortBericht} bevat.
     *
     * @param soortBerichten de set aan abonnement specifieke soort berichten
     * @param soortBericht het soort bericht dat wordt gecontroleerd.
     * @return indicatie of het opgegeven soort bericht in de opgegeven set aanwezig is.
     */
    private boolean bevatSoortBerichtenSetSpecifiekSoortBericht(final Set<AbonnementSoortBericht> soortBerichten,
            final SoortBericht soortBericht)
    {
        if (soortBerichten == null) {
            return false;
        }

        boolean result = false;
        for (AbonnementSoortBericht abonnementSoortBericht : soortBerichten) {
            if (abonnementSoortBericht.getSoortBericht() == soortBericht) {
                result = true;
                break;
            }
        }
        return result;
    }

}
