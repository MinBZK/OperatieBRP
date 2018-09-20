/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsverantwoordelijkheidGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verantwoordelijke;
import nl.bzk.brp.model.validatie.Melding;


/**
 * Afleidingsregel: Tijdelijke oplossing. Bij geboorte krijgt standaard het College de verantwoordelijkheid.
 */
public class BijhoudingsverantwoordelijkheidBijGeboorteAfleiding implements BedrijfsRegel<Relatie> {

    @Override
    public String getCode() {
        // TODO, BijhoudingsverantwoordelijkheidBijGeboorteAfleiding moet nog een code/nummer krijgen.
        return "BijhoudingsverantwoordelijkheidBijGeboorteAfleiding";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
        final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        // deze regel zet bij de geboorte per definitie de college van bestuur als verantwoordelijke
        // is gemoved van de DAL als hardcoded.
        List<Melding> meldingen = new ArrayList<Melding>();
        if (nieuweSituatie.getKindBetrokkenheid().getBetrokkene() instanceof PersoonBericht) {

            PersoonBericht kind = (PersoonBericht) nieuweSituatie.getKindBetrokkenheid().getBetrokkene();
            if (kind.getBijhoudingsverantwoordelijkheid() == null) {
                kind.setBijhoudingsverantwoordelijkheid(new PersoonBijhoudingsverantwoordelijkheidGroepBericht());
            }
            if (kind.getBijhoudingsverantwoordelijkheid().getVerantwoordelijke() == null) {
                kind.getBijhoudingsverantwoordelijkheid().setVerantwoordelijke(Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS);
            }
        }

        return meldingen;
    }

}
