/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonReisdocumentView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * BRBY0044: Te onttrekken reisdocument moet geldig reisdocument van persoon zijn.
 *
 * In een Bericht met de Bijhouding OntrekkingReisdocument, moet de ID van Reisdocument verwijzen naar een Reisdocument
 * in de BRP waarin DatumOnttrekking ontbreekt.
 *
 * @brp.bedrijfsregel BRBY0044
 */
@Named("BRBY0044")
public class BRBY0044 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht>
{

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie, final PersoonBericht nieuweSituatie,
            final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (nieuweSituatie.getReisdocumenten() != null) {
            for (PersoonReisdocumentBericht persoonReisdocument : nieuweSituatie.getReisdocumenten()) {
                if (isVervallenReisdocument(Integer.valueOf(persoonReisdocument.getObjectSleutel()),
                        huidigeSituatie))
                {
                    objectenDieDeRegelOvertreden.add(persoonReisdocument);
                }
            }
        }
        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of het reisdocument vervallen is of niet.
     *
     * @param technischeSleutel van het te controleren reisdocument
     * @param huidigeSituatie de huidige persoon
     * @return true indien het reisdocument vervallen is, anders false
     */
    private boolean isVervallenReisdocument(final Integer technischeSleutel, final PersoonView huidigeSituatie) {
        boolean resultaat = false;
        for (PersoonReisdocumentView persoonReisdocument : huidigeSituatie.getReisdocumenten()) {
            if (persoonReisdocument.getID().equals(technischeSleutel)
                    && persoonReisdocument.getStandaard().getDatumInhoudingVermissing() != null)
            {
                resultaat = true;
            }
        }
        return resultaat;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0044;
    }
}
