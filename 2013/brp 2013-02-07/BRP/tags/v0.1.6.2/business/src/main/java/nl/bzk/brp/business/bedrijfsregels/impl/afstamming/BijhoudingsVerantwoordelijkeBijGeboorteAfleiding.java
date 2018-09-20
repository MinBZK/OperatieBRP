/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsVerantwoordelijkheidGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verantwoordelijke;
import nl.bzk.brp.model.validatie.Melding;


/**
 * Afleidingsregel: Tijdelijke oplossing. Bij geboorte krijgt standaard het College de verantwoordelijkheid.
 */
public class BijhoudingsVerantwoordelijkeBijGeboorteAfleiding implements BedrijfsRegel<Relatie> {

    @Override
    public String getCode() {
        // TODO, BijhoudingsVerantwoordelijkeBijGeboorteAfleiding moet nog een code/nummer krijgen.
        return "BijhoudingsVerantwoordelijkeBijGeboorteAfleiding";
    }

    @Override
    public Melding executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
        final Datum datumAanvangGeldigheid)
    {
        // deze regel zet bij de geboorte per definitie de college van bestuur als verantwoordelijke
        // is gemoved van de DAL als hardcoded.
        Melding melding = null;
        if (nieuweSituatie.getKindBetrokkenheid().getBetrokkene() instanceof PersoonBericht) {

            PersoonBericht kind = (PersoonBericht) nieuweSituatie.getKindBetrokkenheid().getBetrokkene();
            if (kind.getBijhoudingVerantwoordelijke() == null) {
                kind.setBijhoudingVerantwoordelijke(new PersoonBijhoudingsVerantwoordelijkheidGroepBericht());
            }
            if (kind.getBijhoudingVerantwoordelijke().getVerantwoordelijke() == null) {
                kind.getBijhoudingVerantwoordelijke().setVerantwoordelijke(Verantwoordelijke.COLLEGE);
            }
        }

        return melding;
    }

}
