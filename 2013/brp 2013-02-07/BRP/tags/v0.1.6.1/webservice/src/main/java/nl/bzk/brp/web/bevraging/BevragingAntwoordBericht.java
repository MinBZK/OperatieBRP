/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bevraging;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.objecttype.impl.usr.BetrokkenheidMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.impl.usr.RelatieMdl;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;
import nl.bzk.brp.web.AbstractAntwoordBericht;

/**
 * Abstracte antwoord bericht voor BRP bevragingsberichten.
 */
public class BevragingAntwoordBericht extends AbstractAntwoordBericht<OpvragenPersoonResultaat> {

    private List<PersoonMdl> personen = null;

    /**
     * Constructor.
     * @param berichtResultaat Het resultaat uit de back-end.
     */
    public BevragingAntwoordBericht(final OpvragenPersoonResultaat berichtResultaat) {
        super(berichtResultaat);
        if (berichtResultaat.getGevondenPersonen() != null && !berichtResultaat.getGevondenPersonen().isEmpty()) {
            personen = new ArrayList<PersoonMdl>();
            for (final PersoonMdl gevondenPersoon : berichtResultaat.getGevondenPersonen()) {
                personen.add(gevondenPersoon);
            }
        }

        if (personen != null) {
            for (PersoonMdl persoon : personen) {
                bewerkViewOpPersoonVoorBinding(persoon);
            }
        }
    }

    /**
     * Let op, de Persoon die in dit antwoord bericht zit bevat dubbele informatie die ervoor kan zorgen dat de
     * databinding niet goed werkt. De persoon kent mogelijk een partner betrokkenheid, deze partner betrokkenheid kent
     * een relatie met weer 2 betrokkenheden. Van deze 2 betrokkenheden is er een hetzelfde als de directe
     * betrokkenheid op de persoon in dit antwoord bericht. Hier kan de databinding niet tegen. Wat we in deze functie
     * doen is de betrokkenheid die aan de relatie hangt verwijderen. We passen dus de View op de persoon aan voor
     * correcte presentatie in het xml bericht.
     * @param persoon De te bewerken persoon.
     */
    private void bewerkViewOpPersoonVoorBinding(final PersoonMdl persoon) {
        if (persoon.getBetrokkenheden() != null) {
            for (BetrokkenheidMdl directeBetrokkenheid : persoon.getBetrokkenheden()) {
                if (SoortBetrokkenheid.PARTNER == directeBetrokkenheid.getRol()) {
                    RelatieMdl relatie = directeBetrokkenheid.getRelatie();
                    for (BetrokkenheidMdl indirecteBetrokkenheid : relatie.getBetrokkenheden()) {
                        if (indirecteBetrokkenheid.getBetrokkene().getId() != null
                            && !indirecteBetrokkenheid.getBetrokkene().getId().equals(persoon.getId()))
                        {
                            relatie.getBetrokkenheden().add(indirecteBetrokkenheid);
                        }
                    }
                }
            }
        }
    }

    public List<PersoonMdl> getPersonen() {
        return personen;
    }
}
