/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.groep.bericht.PersoonOpschortingGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenOpschorting;
import nl.bzk.brp.model.validatie.Melding;


/**
 *
 * VR00012: Verwerken Groep Overlijden.
 *
 * @brp.bedrijfsregel VR00012
 */
public class VR00012 implements ActieBedrijfsRegel<Persoon> {

    @Inject
    private ReferentieDataRepository referentieRepository;

    @Override
    public String getCode() {

        return "VR00012";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie, final Actie actie) {
        executeer(nieuweSituatie);

        return new ArrayList<Melding>();
    }

    /**
     * Voert de werkelijke afleiding uit voor opschorting.
     *
     * @param persoon de persoon waarvoor de afleiding geldt.
     */
    private void executeer(final Persoon persoon) {
        // TODO tijdelijk wordt land overlijden altijd op NL gezet zonder te kijken naar Bronnen, het is op dit moment
        // nog niet duidelijk hoe bronnen behandeld moet worden.
        // 2-11-2012: Bevestigd met Jeroen.
        if (persoon.getOverlijden().getLandOverlijden() == null) {
            ((PersoonBericht) persoon).getOverlijden().setLandOverlijdenCode(BrpConstanten.NL_LAND_CODE);
            ((PersoonBericht) persoon).getOverlijden().setLandOverlijden(
                    referentieRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE));
        }

        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschorting(RedenOpschorting.OVERLIJDEN);

        ((PersoonBericht) persoon).setOpschorting(opschorting);
    }
}
