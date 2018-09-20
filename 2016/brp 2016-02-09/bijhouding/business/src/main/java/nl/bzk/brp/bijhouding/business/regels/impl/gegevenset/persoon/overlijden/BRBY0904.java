/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Het land overlijden moet verwijzen naar een geldig voorkomen in stamgegeven "Land" op peildatum datum overlijden.
 *
 * @brp.bedrijfsregel BRBY0904
 */
@Named("BRBY0904")
public class BRBY0904 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0904;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie, final PersoonBericht nieuweSituatie,
            final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (nieuweSituatie.getOverlijden() != null) {
            final PersoonOverlijdenGroepBericht overlijden = nieuweSituatie.getOverlijden();

            if (overlijden.getLandGebiedOverlijden() != null && overlijden.getDatumOverlijden() != null)
            {
                final LandGebiedAttribuut landGebiedAttribuut = nieuweSituatie.getOverlijden().getLandGebiedOverlijden();
                final LandGebied landGebied = landGebiedAttribuut.getWaarde();

                if (!overlijden.getDatumOverlijden().isGeldigTussen(landGebied.getDatumAanvangGeldigheid(), landGebied.getDatumEindeGeldigheid())) {
                    objectenDieDeRegelOvertreden.add(nieuweSituatie);
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }
}
