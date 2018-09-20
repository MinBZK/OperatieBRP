/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import nl.bzk.migratiebrp.bericht.model.AkteOnbekendException;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;

import org.springframework.stereotype.Component;

/**
 * Zorgt dat het correcte VerwerkToevalligeGebeurtenisVerzoekBericht wordt aangemaakt.
 */
@Component
class VerwerkToevalligeGebeurtenisVerzoekBerichtFactory {

    /**
     * Geef de juiste bericht verwerker voor deze input.
     *
     * @param input
     *            bericht wat vertaald moet worden
     * @return Bericht verwerker voor het gegeven input bericht
     * @throws AkteOnbekendException
     *             indien akte niet bekend is
     */
    public final BerichtVerwerker<VerwerkToevalligeGebeurtenisVerzoekBericht, Tb02Bericht> maakVerwerker(final Tb02Bericht input)
        throws AkteOnbekendException
    {
        BerichtVerwerker<VerwerkToevalligeGebeurtenisVerzoekBericht, Tb02Bericht> resultaat;
        switch (input.getSoortAkte()) {
            case AKTE_1H:
            case AKTE_1M:
            case AKTE_1S:
                resultaat = new ToevalligeGebeurtenisPersoonswijziging();
                break;
            case AKTE_3A:
            case AKTE_5A:
                resultaat = new ToevalligeGebeurtenisSluiting();
                break;
            case AKTE_3B:
            case AKTE_5B:
                resultaat = new ToevalligeGebeurtenisOntbinding();
                break;
            case AKTE_5H:
            case AKTE_3H:
                resultaat = new ToevalligeGebeurtenisOmzetting();
                break;
            default:
                throw new IllegalStateException("Akte wordt door deze factory niet ondersteund");
        }

        return resultaat;
    }
}
