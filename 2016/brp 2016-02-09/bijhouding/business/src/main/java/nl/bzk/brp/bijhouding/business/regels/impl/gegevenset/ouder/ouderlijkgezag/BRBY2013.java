/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.ouderlijkgezag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.OuderView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.util.RelatieUtils;


/**
 * BRBY2013: Ouderlijk gezag alleen mogelijk als ouder ouderschap heeft.
 *
 * Op DatumAanvangGeldigheid van de groep OuderlijkGezag, moet de Ouder een
 * geldig Ouderschap hebben over Kind.
 */
@Named("BRBY2013")
public class BRBY2013 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie, final PersoonBericht nieuweSituatie,
            final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        // Als er een betrokkenheid in het bericht aanwezig is, dan zijn er via
        // kind -> fam.recht.betr.
        // -> betrokkenheden een of meer ouder betrokkenheden aanwezig
        if (nieuweSituatie.getBetrokkenheden() != null) {
            final List<Ouder> ouderBetrokkenheden =
                RelatieUtils.haalOuderBetrokkenhedenUitRelatie(nieuweSituatie.getBetrokkenheden().iterator().next()
                        .getRelatie());
            for (final Ouder ouderBetrokkenheid : ouderBetrokkenheden) {

                // Als deze (bericht)ouder betrokkenheid ouderlijk gezag heeft
                final OuderBericht ouderBericht = (OuderBericht) ouderBetrokkenheid;
                if (JaNeeAttribuut.JA.equals(ouderBericht.getOuderlijkGezag().getIndicatieOuderHeeftGezag())) {

                    // Is dit alleen mogelijk wanneer deze ouder ouderschap heeft
                    for (final Ouder ouder : RelatieUtils.haalOuderschappenUitKind(huidigeSituatie)) {
                        if (ouderBericht.getObjectSleutel().equals(((OuderView) ouder).getID().toString())
                            && ouder.getOuderschap() == null)
                        {
                            // ouder heeft geen ouderschap
                            objectenDieDeRegelOvertreden.add(ouderBericht);
                            break;
                        }
                    }
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY2013;
    }
}
