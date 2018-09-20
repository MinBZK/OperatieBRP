/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.util.RelatieUtils;


/**
 * De persoon die in het bericht als ouder uit wie het kind is voortgekomen is aangeduid (berichtelement ouder uit wie
 * het kind is voortgekomen is aanwezig
 * en heeft de waarde Ja) moet in de BRP op de datum geboorte als vrouw zijn vastgelegd (geslachtsaanduiding uit groep
 * geslachtsaanduiding heeft de waarde
 * V).
 *
 * @brp.bedrijfsregel BRPUC00112
 */
@Named("BRPUC00112")
public class BRPUC00112 implements
        VoorActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRPUC00112;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
            final FamilierechtelijkeBetrekkingBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final PersoonBericht moederBericht = (PersoonBericht) RelatieUtils.haalMoederUitRelatie(nieuweSituatie);
        if (null != moederBericht) {
            // moeder is per definitie een ingezetene bij een geboorte, anders moet dit via een ander berichtgeving.
            // geslachtsaanduiding uit de db controleren.
            final PersoonView moederModel = bestaandeBetrokkenen.get(moederBericht.getIdentificerendeSleutel());

            if (moederModel != null
                    && !moederModel.getGeslachtsaanduiding().getGeslachtsaanduiding().getWaarde()
                    .equals(Geslachtsaanduiding.VROUW))
            {
                objectenDieDeRegelOvertreden.add(moederBericht);
            }
        }

        return objectenDieDeRegelOvertreden;
    }
}
