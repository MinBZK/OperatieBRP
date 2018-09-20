/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;

/**
 * De nationaliteit &nationaliteit staat reeds bij de persoon geregistreerd.
 *
 * @brp.bedrijfsregel BRBY0141
 */
@Named("BRBY0141")
public class BRBY0141 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0141;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
                                              final PersoonBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        //Bij een nieuw persoon zoals eerste inschrijving is de huidigeSituatie null
        if (huidigeSituatie != null) {
            for (PersoonNationaliteitBericht nationaliteit : nieuweSituatie.getNationaliteiten()) {
                if (isAanwezigInLijst(huidigeSituatie.getNationaliteiten(), nationaliteit)) {
                    objectenDieDeRegelOvertreden.add(nationaliteit);
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Checkt of de nationaliteit al aanwezig is in de lijst, op basis van de code.
     *
     * @param nationaliteiten de lijst
     * @param nationaliteit de nationaliteit
     * @return true indien aanwezig, anders false
     */
    private boolean isAanwezigInLijst(final Collection<? extends PersoonNationaliteit> nationaliteiten,
                                      final PersoonNationaliteit nationaliteit)
    {
        boolean isAanwezig = false;

        for (PersoonNationaliteit nation : nationaliteiten) {
            if (nation.getNationaliteit().getWaarde().getCode().equals(
                    nationaliteit.getNationaliteit().getWaarde().getCode()))
            {
                isAanwezig = true;
                break;
            }
        }

        return isAanwezig;
    }



}
