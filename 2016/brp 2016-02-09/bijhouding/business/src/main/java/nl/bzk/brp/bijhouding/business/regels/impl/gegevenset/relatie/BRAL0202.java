/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.relatie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.RelatieView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.apache.commons.lang.StringUtils;

/**
 * Persoon is hoogstens één keer betrokken in relatie.
 *
 * @brp.bedrijfsregel BRAL0202
 */
@Named("BRAL0202")
public class BRAL0202 implements VoorActieRegelMetMomentopname<RelatieView, RelatieBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRAL0202;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final RelatieView huidigeSituatie,
                                              final RelatieBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final Set<Integer> databaseIdsPersonen = new HashSet<>();

        // als huidige situatie niet null, haal eerste de bestaande personen op en zet ze in de lijst
        if (huidigeSituatie != null) {
            for (final Betrokkenheid betrokkenheid : huidigeSituatie.getBetrokkenheden()) {
                // huwelijk kan niet met 2 NI, dus we kunnen dit alles afhandelen met object sleutels.
                // zelfde met fam.recht.betr (adoptie)
                final PersoonModel pers = (PersoonModel) betrokkenheid.getPersoon();
                databaseIdsPersonen.add(pers.getID());
            }
        }

        for (final Betrokkenheid betrokkenheid : nieuweSituatie.getBetrokkenheden()) {
            // huwelijk kan niet met 2 NI, dus we kunnen dit alles afhandelen met object sleutels.
            // zelfde met fam.recht.betr (adoptie)
            final PersoonBericht pers = (PersoonBericht) betrokkenheid.getPersoon();
            if (pers != null
                    && StringUtils.isNotBlank(pers.getObjectSleutel())
                    && !databaseIdsPersonen.add(pers.getObjectSleutelDatabaseID()))
            {
                objectenDieDeRegelOvertreden.add(pers);
            }
        }

        return objectenDieDeRegelOvertreden;
    }


}
