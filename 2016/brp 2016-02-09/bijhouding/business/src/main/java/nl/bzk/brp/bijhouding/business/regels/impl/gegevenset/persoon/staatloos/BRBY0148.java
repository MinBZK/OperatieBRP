/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;


/**
 * BRBY0148: Indicator Staatloos mag niet opnieuw worden toegekend.
 *
 * @brp.bedrijfsregel BRBY0148
 */
@Named("BRBY0148")
public class BRBY0148 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie, final PersoonBericht nieuweSituatie,
            final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        // is er momenteel een indicatie staatsloos geregistreerd?
        if (huidigeSituatie != null
            && heeftIndicatieStaatsloos(huidigeSituatie.getIndicaties())
            // bevat het bericht een indicatie staatsloos?
            && nieuweSituatie.getIndicaties() != null)
        {
            final PersoonIndicatieBericht indicatieStaatsloos = (PersoonIndicatieBericht) haalIndicatieStaatsloos(nieuweSituatie.getIndicaties());
            if (indicatieStaatsloos != null) {
                objectenDieDeRegelOvertreden.add(indicatieStaatsloos);
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Bepaal of de indicatie staatsloos aanwezig is.
     *
     * @param indicaties
     *            de indicatie lijst.
     * @return true wanneer indicatie staatsloos aanwezig is of false wanneer
     *         dat niet het geval is
     */
    private boolean heeftIndicatieStaatsloos(final Collection<? extends PersoonIndicatie> indicaties) {
        final PersoonIndicatie indicatieStaatsloos = haalIndicatieStaatsloos(indicaties);
        return indicatieStaatsloos != null;
    }

    /**
     * Haalt de indicatie staatsloos uit de indicatie lijst.
     *
     * @param indicaties
     *            de indicatie lijst.
     * @return de indicatie staatsloos of null als die er niet is.
     */
    private PersoonIndicatie haalIndicatieStaatsloos(final Collection<? extends PersoonIndicatie> indicaties) {
        for (final PersoonIndicatie indicatie : indicaties) {
            if (SoortIndicatie.INDICATIE_STAATLOOS == indicatie.getSoort().getWaarde()) {
                return indicatie;
            }
        }
        return null;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0148;
    }
}
