/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.ouderlijkgezag;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.business.regels.NaActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Kind;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.RelatieUtils;


/**
 * Op DatumAanvangGeldigheid van de groep OuderlijkGezag, mag betreffende Ouder van Kind niet gehuwd zijn met (de)
 * andere Ouder van Kind.
 */
@Named("BRBY2018")
public class BRBY2018 implements NaActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final List<BerichtEntiteit> voerRegelUit(final PersoonView kind, final PersoonBericht persoonBericht) {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        // Controleer of de ouders gehuwd zijn.
        final List<Persoon> ouders = RelatieUtils.haalOudersUitKind(kind);
        if (ouders.size() > 1) {
            final PersoonView ouder1 = (PersoonView) ouders.get(0);
            final PersoonView ouder2 = (PersoonView) ouders.get(1);

            if (RelatieUtils.zijnPersonenGehuwd(ouder1, ouder2)
                    && minstensEenVanDeOudersHeeftOuderlijkGezag(kind))
            {
                objectenDieDeRegelOvertreden.add(persoonBericht);
            }

        }
        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of een ouder een ouderlijk gezag groep heeft over het kind.
     * Aanname: kind bevat een kindbetrokkenheid
     * @param kind kind
     * @return true indue de groep staat geregistreerd, anders false
     */
    private boolean minstensEenVanDeOudersHeeftOuderlijkGezag(final Persoon kind) {
        boolean resultaat = false;
        final Kind kindBetr = RelatieUtils.haalKindBetrokkenheidUitPersoon(kind);
        for (final Betrokkenheid betrokkenheid : kindBetr.getRelatie().getBetrokkenheden()) {
            if (SoortBetrokkenheid.OUDER == betrokkenheid.getRol().getWaarde()) {
                final Ouder ouderBetr = (Ouder) betrokkenheid;
                if (ouderBetr.getOuderlijkGezag() != null) {
                    resultaat = true;
                    break;
                }
            }
        }
        return resultaat;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY2018;
    }
}
