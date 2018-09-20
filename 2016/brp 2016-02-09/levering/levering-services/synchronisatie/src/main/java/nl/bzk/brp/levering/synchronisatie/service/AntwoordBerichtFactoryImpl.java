/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.service;

import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonAntwoordBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenAntwoordBericht;
import nl.bzk.brp.webservice.business.service.AbstractAntwoordBerichtFactory;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import org.springframework.stereotype.Component;


/**
 * De implementatie van de AntwoordBerichtFactory, deze wordt door de webservice gebruikt voor .
 */
@Component
public class AntwoordBerichtFactoryImpl extends AbstractAntwoordBerichtFactory {

    @Override
    protected final void vulAntwoordBerichtAan(final BerichtVerwerkingsResultaat resultaat,
        final Bericht bericht,
        final AntwoordBericht antwoord)
    {
        if (SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN == bericht.getSoort().getWaarde()) {
            final SynchronisatieResultaat synchronisatieResultaat = (SynchronisatieResultaat) resultaat;

            if (synchronisatieResultaat.getStamgegeven() != null) {
                final GeefSynchronisatieStamgegevenAntwoordBericht antwoordBericht =
                    (GeefSynchronisatieStamgegevenAntwoordBericht) antwoord;
                antwoordBericht.setSynchronisatieStamgegevens(synchronisatieResultaat.getStamgegeven());
            }
        }
    }

    @Override
    protected final BerichtResultaatGroepBericht maakInitieelBerichtResultaatGroepBericht(
        final Bericht ingaandBericht,
        final BerichtVerwerkingsResultaat resultaat)
    {
        return new BerichtResultaatGroepBericht();
    }

    /**
     * Bepaalt op basis van het ingaande bericht het antwoord bericht dat geretourneerd moet worden.
     *
     * @param ingaandBericht Het ingaande bericht.
     * @return Het antwoord bericht behorende bij het ingaande bericht.
     */
    @Override
    protected final AntwoordBericht maakInitieelAntwoordBerichtVoorInkomendBericht(final Bericht ingaandBericht) {
        final AntwoordBericht antwoordBericht;
        switch (ingaandBericht.getSoort().getWaarde()) {
            case LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON:
                antwoordBericht = new GeefSynchronisatiePersoonAntwoordBericht();
                break;
            case LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN:
                antwoordBericht = new GeefSynchronisatieStamgegevenAntwoordBericht();
                break;
            default:
                throw new IllegalStateException("Mapping van ingaande en uitgaande bericht soorten is niet compleet.");
        }
        return antwoordBericht;
    }

}
