/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Datum aanvang Geldigheid actie moet exact zelfde zijn als datum.
 *
 * @brp.bedrijfsregel BRBY0908
 *
 */
@Named("BRBY0908")
public class BRBY0908 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0908;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie, final PersoonBericht nieuweSituatie,
            final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (nieuweSituatie.getOverlijden() != null
                && nieuweSituatie.getOverlijden().getDatumOverlijden() != null
                && !actie.getDatumAanvangGeldigheid().equals(nieuweSituatie.getOverlijden().getDatumOverlijden()))
        {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }

        return objectenDieDeRegelOvertreden;
    }

}
