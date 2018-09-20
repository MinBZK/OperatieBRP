/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.betrokkenheid.identiteitbetrokkenheid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.basis.ModelRootObject;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * BRAL2010: Elke betrokkenheid behalve ouder moet naar een persoon verwijzen.
 *
 * @brp.bedrijfsregel BRAL2010
 */
@Named("BRAL2010")
public class BRAL2010 implements VoorActieRegelMetMomentopname<ModelRootObject, BerichtRootObject> {

    @Override
    public final Regel getRegel() {
        return Regel.BRAL2010;
    }

    @Override
    public final List<BerichtEntiteit> voerRegelUit(final ModelRootObject modelRootObject,
            final BerichtRootObject berichtRootObject, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {

        final List<BetrokkenheidBericht> betrokkenheden = zoekBetrokkenhedenBijBerichtRootObject(berichtRootObject);

        return getObjectenDieDeRegelOvertreden(betrokkenheden);
    }

    private List<BetrokkenheidBericht> zoekBetrokkenhedenBijBerichtRootObject(final BerichtRootObject berichtRootObject) {
        final List<BetrokkenheidBericht> result = new ArrayList<>();
        if (berichtRootObject instanceof PersoonBericht) {
            result.addAll(zoekBetrokkenhedenVanuitPersoon((PersoonBericht) berichtRootObject));
        } else if (berichtRootObject instanceof RelatieBericht) {
            result.addAll(zoekBetrokkenhedenVanuitRelatie((RelatieBericht) berichtRootObject));
        } else {
            throw new IllegalArgumentException("Ongeldig type root object");
        }
        return result;
    }

    private List<BetrokkenheidBericht> zoekBetrokkenhedenVanuitPersoon(final PersoonBericht persoonBericht) {
        final List<BetrokkenheidBericht> result = new ArrayList<>();
        if (persoonBericht.getBetrokkenheden() != null) {
            // De betrokkenheid onder de persoon hoeft niet gecheckt te worden, die hoort namelijk sowieso bij
            // die persoon (tevens is in het bericht het persoon attribuut van die betrokkenheid niet gevuld,
            // dus dat checken zou ook niet goed gaan).
            for (final BetrokkenheidBericht betrokkenheidBericht : persoonBericht.getBetrokkenheden()) {
                final RelatieBericht relatieBericht = betrokkenheidBericht.getRelatie();
                if (relatieBericht != null && relatieBericht.getBetrokkenheden() != null) {
                    result.addAll(relatieBericht.getBetrokkenheden());
                }
            }
        }
        return result;
    }

    private List<BetrokkenheidBericht> zoekBetrokkenhedenVanuitRelatie(final RelatieBericht relatieBericht) {
        final List<BetrokkenheidBericht> result = new ArrayList<>();
        final List<BetrokkenheidBericht> relatieBetrokkenheden = relatieBericht.getBetrokkenheden();
        if (relatieBetrokkenheden != null) {
            result.addAll(relatieBetrokkenheden);
        }
        return result;
    }

    private List<BerichtEntiteit> getObjectenDieDeRegelOvertreden(final List<BetrokkenheidBericht> betrokkenheden) {
        final List<BerichtEntiteit> result = new ArrayList<>();
        for (final BetrokkenheidBericht betrokkenheidBericht : betrokkenheden) {
            if (!betrokkenheidBericht.getRol().getWaarde().equals(SoortBetrokkenheid.OUDER)
                && betrokkenheidBericht.getPersoon() == null)
            {
                result.add(betrokkenheidBericht);
            }
        }
        return result;
    }
}
