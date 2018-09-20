/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.ouderlijkgezag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0003;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.util.RelatieUtils;


/**
 * BRBY2016: Kind onder ouderlijk gezag moet minderjarig zijn.
 *
 * Op DatumAanvangGeldigheid van de groep OuderlijkGezag, moet het Kind minderjarig zijn.
 */
@Named("BRBY2016")
public class BRBY2016 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Inject
    private BRBY0003 brby0003;

    @Override
    public final Regel getRegel() {
        return Regel.BRBY2016;
    }

    @Override
    public final List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
        final PersoonBericht nieuweSituatie,
        final Actie actie,
        final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        // Als er een betrokkenheid in het bericht aanwezig is, dan zijn er via kind -> fam.recht.betr.
        // -> betrokkenheden een of meer ouder betrokkenheden aanwezig
        if (nieuweSituatie.getBetrokkenheden() != null) {
            final List<Ouder> ouderBetrokkenheden = RelatieUtils.haalOuderBetrokkenhedenUitRelatie(
                nieuweSituatie.getBetrokkenheden().iterator().next().getRelatie());
            for (final Betrokkenheid ouderBetrokkenheid : ouderBetrokkenheden) {
                // Als deze ouder betrokkenheid ouderlijk gezag heeft
                final OuderBericht ouderBericht = (OuderBericht) ouderBetrokkenheid;
                if (JaNeeAttribuut.JA.equals(ouderBericht.getOuderlijkGezag().getIndicatieOuderHeeftGezag())
                    // Dan moet het kind minderjarig zijn
                    && !brby0003.isMinderjarig(huidigeSituatie, new DatumAttribuut(actie.getDatumAanvangGeldigheid())))
                {
                    objectenDieDeRegelOvertreden.add(nieuweSituatie);
                    break;
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }

}
