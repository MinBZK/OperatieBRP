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
 * Nederlander moet een voornaam hebben.
 *
 * @brp.bedrijfsregel BRBY0153
 */
@Named("BRBY0153")
public class BRBY0153 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0153;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
            final PersoonBericht nieuweSituatie, final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (NationaliteitRegelUtil.verkrijgtNederlandseNationaliteit(nieuweSituatie)
            && huidigeSituatie != null
            && huidigeSituatie.getVoornamen().isEmpty()
            // Misschien wordt het in deze zelfde actie alsnog goed gezet.
            && !nieuweSituatieHeeftVoornamen(nieuweSituatie))
        {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Geeft aan of er voornamen in het bericht zitten.
     *
     * @param nieuweSituatie de persoon in het bericht
     * @return of er voornamen in het bericht zitten
     */
    private boolean nieuweSituatieHeeftVoornamen(final PersoonBericht nieuweSituatie) {
        return nieuweSituatie.getVoornamen() != null && nieuweSituatie.getVoornamen().size() > 0;
    }

}
