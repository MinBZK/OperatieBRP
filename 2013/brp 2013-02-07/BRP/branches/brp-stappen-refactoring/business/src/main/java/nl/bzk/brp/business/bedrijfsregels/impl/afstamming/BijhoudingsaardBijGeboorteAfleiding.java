/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingsaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.validatie.Melding;


/** Afleidingsregel: Tijdelijke oplossing. Bij geboorte wordt de bijhoudingsaard op ingezetene gezet. */
public class BijhoudingsaardBijGeboorteAfleiding implements
    ActieBedrijfsRegel<FamilierechtelijkeBetrekking>
{

    @Override
    public String getCode() {
        // TODO, BijhoudingsaardBijGeboorteAfleiding moet nog een code/nummer krijgen.
        return "BijhoudingsaardBijGeboorteAfleiding";
    }

    @Override
    public List<Melding> executeer(final FamilierechtelijkeBetrekking huidigeSituatie,
        final FamilierechtelijkeBetrekking nieuweSituatie, final Actie actie)
    {
        // deze regel zet bij de geboorte per definitie de bijhoudingsaard naar 'ingezetene'.
        List<Melding> meldingen = new ArrayList<Melding>();
        if (((FamilierechtelijkeBetrekkingBericht) nieuweSituatie).getKindBetrokkenheid()
                                                                  .getPersoon() instanceof PersoonBericht)
        {
            PersoonBericht kind =
                ((FamilierechtelijkeBetrekkingBericht) nieuweSituatie).getKindBetrokkenheid().getPersoon();
            if (kind.getBijhoudingsaard() == null) {
                kind.setBijhoudingsaard(new PersoonBijhoudingsaardGroepBericht());
            }
            if (kind.getBijhoudingsaard().getBijhoudingsaard() == null) {
                kind.getBijhoudingsaard().setBijhoudingsaard(Bijhoudingsaard.INGEZETENE);
            }
        }

        return meldingen;
    }

}
