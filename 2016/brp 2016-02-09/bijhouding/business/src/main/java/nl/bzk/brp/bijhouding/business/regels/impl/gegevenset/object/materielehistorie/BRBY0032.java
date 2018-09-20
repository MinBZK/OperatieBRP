/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.materielehistorie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.basis.ModelRootObject;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Datum einde geldigheid moet na datum aanvang liggen.
 *
 * @brp.bedrijfsregel BRBY0032
 */
@Named("BRBY0032")
public class BRBY0032 implements VoorActieRegelMetMomentopname<ModelRootObject, BerichtRootObject> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0032;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final ModelRootObject huidigeSituatie,
                                              final BerichtRootObject nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (actie.getDatumAanvangGeldigheid() != null
                && actie.getDatumEindeGeldigheid() != null
                && !actie.getDatumEindeGeldigheid().na(actie.getDatumAanvangGeldigheid()))
        {
            objectenDieDeRegelOvertreden.add((ActieBericht) actie);
        }

        return objectenDieDeRegelOvertreden;
    }
}
