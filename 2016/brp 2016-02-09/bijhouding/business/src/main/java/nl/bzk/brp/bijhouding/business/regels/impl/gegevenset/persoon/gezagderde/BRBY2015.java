/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.gezagderde;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0003;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * Op DatumAanvangGeldigheid van de groep GezagDerde, moet de Persoon minderjarig zijn.
 *
 * @brp.bedrijfsregel BRBY2015
 */
@Named("BRBY2015")
public class BRBY2015 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Inject
    private BRBY0003 brby0003;

    @Override
    public final Regel getRegel() {
        return Regel.BRBY2015;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
        final PersoonBericht nieuweSituatie,
        final Actie actie,
        final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (null != nieuweSituatie.getIndicaties()) {
            final PersoonIndicatieBericht derdeHeeftGezag = haalIndicatieOp(nieuweSituatie.getIndicaties());
            if (derdeHeeftGezag != null
                && !brby0003.isMinderjarig(huidigeSituatie, new DatumAttribuut(actie.getDatumAanvangGeldigheid())))
            {
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Haalt de indicatie derde heeft gezag uit de indicatie lijst.
     *
     * @param indicaties de indicatie lijst.
     * @return de indicatie derde heeft gezag of null als die er niet is.
     */
    private PersoonIndicatieBericht haalIndicatieOp(final List<PersoonIndicatieBericht> indicaties) {
        for (PersoonIndicatieBericht indicatie : indicaties) {
            if (SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG == indicatie.getSoort().getWaarde()) {
                return indicatie;
            }
        }
        return null;
    }

}
