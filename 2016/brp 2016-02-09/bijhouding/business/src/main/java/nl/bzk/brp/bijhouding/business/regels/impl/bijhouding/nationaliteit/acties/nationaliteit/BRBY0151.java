/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit;

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
 * Nederlander mag geen namenreeks hebben.
 *
 * @brp.bedrijfsregel BRBY0151
 */
@Named("BRBY0151")
public class BRBY0151 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0151;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
            final PersoonBericht nieuweSituatie, final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (NationaliteitRegelUtil.verkrijgtNederlandseNationaliteit(nieuweSituatie)
            && isIndicatieNamenReeks(huidigeSituatie)
            // Misschien wordt het in deze zelfde actie alsnog goed gezet.
            && !nieuweSituatieHeeftNamenreeksNee(nieuweSituatie))
        {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }

        return objectenDieDeRegelOvertreden;
    }

    private boolean isIndicatieNamenReeks(final PersoonView huidigeSituatie) {
        return huidigeSituatie != null
            && huidigeSituatie.getSamengesteldeNaam() != null
            && huidigeSituatie.getSamengesteldeNaam().getIndicatieNamenreeks().getWaarde();
    }

    /**
     * Geeft aan of er een samengestelde naam groep in het bericht is met indicatie namenreeks op false.
     *
     * @param nieuweSituatie de persoon in het bericht
     * @return of er een indicatie namenreeks op false staat in het bericht
     */
    private boolean nieuweSituatieHeeftNamenreeksNee(final PersoonBericht nieuweSituatie) {
        return nieuweSituatie.getSamengesteldeNaam() != null
                && !nieuweSituatie.getSamengesteldeNaam().getIndicatieNamenreeks().getWaarde();
    }

}
