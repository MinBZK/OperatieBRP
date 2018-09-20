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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.RelatieUtils;


/**
 * De persoon die in het bericht is aangemerkt als de ouder uit wie het kind NIET geboren is, moet op de datum geboorte
 * van het kind van het mannelijke
 * geslacht zijn.
 * <p/>
 * Deze regel gaat van uit dat de ouders van het kind van het mannelijk + vrouwelijk geslacht is. En gaat er van uit dat
 * de man niet de ouder is uit wie het kind is voortgekomen. Ouders van 2x man en 2x vrouw zijn hiermee uitgesloten.
 * <p/>
 *
 * @brp.bedrijfsregel BRBY0170
 */
@Named("BRBY0170")
public class BRBY0170 implements
        VoorActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0170;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
            final FamilierechtelijkeBetrekkingBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final List<Persoon> nietMoeders = RelatieUtils.haalAlleNietMoedersUitRelatie(nieuweSituatie);

        for (final Persoon ouderPersoon : nietMoeders) {
            final PersoonBericht ouderPersoonBericht = (PersoonBericht) ouderPersoon;

            Geslachtsaanduiding geslachtsaanduiding;

            if (ouderPersoonBericht.getSoort().getWaarde() == SoortPersoon.INGESCHREVENE) {
                final PersoonView pVader = bestaandeBetrokkenen.get(ouderPersoonBericht.getIdentificerendeSleutel());
                geslachtsaanduiding = pVader.getGeslachtsaanduiding().getGeslachtsaanduiding().getWaarde();
            } else {
                geslachtsaanduiding = ouderPersoonBericht.getGeslachtsaanduiding().getGeslachtsaanduiding().getWaarde();
            }

            if (geslachtsaanduiding != Geslachtsaanduiding.MAN) {
                // de niet moeder moet man zijn.
                objectenDieDeRegelOvertreden.add(ouderPersoonBericht);
            }
        }

        return objectenDieDeRegelOvertreden;
    }
}
