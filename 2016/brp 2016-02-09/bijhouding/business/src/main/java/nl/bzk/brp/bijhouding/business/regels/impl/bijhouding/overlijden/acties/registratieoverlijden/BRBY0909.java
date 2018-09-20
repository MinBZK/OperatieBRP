/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0003;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.RelatieUtils;


/**
 * Overledene mag geen minderjarige kinderen hebben.
 *
 * @brp.bedrijfsregel BRBY0909
 *
 */
@Named("BRBY0909")
public class BRBY0909 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Inject
    private BRBY0003 brby0003;

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0909;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie, final PersoonBericht nieuweSituatie,
        final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final DatumEvtDeelsOnbekendAttribuut datumOverlijden = nieuweSituatie.getOverlijden().getDatumOverlijden();
        for (final Persoon kind : RelatieUtils.haalAlleKinderenUitPersoon(huidigeSituatie)) {
            if (kind.getOverlijden() == null && kindWasOpDeWereld(kind, datumOverlijden)
                && brby0003.isMinderjarig(kind, new DatumAttribuut(datumOverlijden)))
            {
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
                // We hoeven de persoon niet meerdere keren toe te voegen voor meerdere kinderen.
                break;
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Checkt of kind al op de wereld was gebracht op datum.
     * @param kind het kind
     * @param datum de peildatum
     * @return true indien het kind bestond anders false.
     */
    private boolean kindWasOpDeWereld(final Persoon kind, final DatumEvtDeelsOnbekendAttribuut datum) {
        return kind.getGeboorte().getDatumGeboorte().voor(datum);
    }

}
