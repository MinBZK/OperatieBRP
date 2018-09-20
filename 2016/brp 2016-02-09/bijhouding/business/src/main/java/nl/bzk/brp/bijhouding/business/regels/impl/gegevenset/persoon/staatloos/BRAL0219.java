/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.business.regels.NaActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;
import nl.bzk.brp.util.RelatieUtils;


/**
 * BRAL0219: Staatloos verplicht indien Nationaliteit ontbreekt.
 * <p>
 * Bij een Persoon moet de indicator Staatloos zijn geregistreerd, als bij die Persoon geen enkele Nationaliteit staat
 * geregistreerd. Deze regel wordt op twee momenten gecontroleerd: - Als bij Inschrijving geen enkele Nationaliteit
 * wordt geregistreerd, moet Staatloos worden geregistreerd, - Als bij beëindiging of verval van de laatste
 * Nationaliteit geen nieuwe Nationaliteit wordt geregistreerd moet Staatloos worden geregistreerd.
 * </p>
 *
 * @brp.bedrijfsregel BRAL0219
 */
@Named("BRAL0219")
public class BRAL0219 implements NaActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView,
        FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRAL0219;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView familie,
            final FamilierechtelijkeBetrekkingBericht familieBericht)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        // NB: We gaan er vanuit dat deze regel alleen gecheckt wordt voor een nieuw kind, daarna wordt
        // de staatloos-nationaliteit consistentie bewaard door andere regels.
        // NB: Dit moet waarschijnlijk herzien worden als beeindigingen ondersteund gaan worden.
        final Persoon kind = RelatieUtils.haalKindUitRelatie(familie);
        final PersoonIndicatie indicatieStaatsloos = haalIndicatieStaatsloos(kind.getIndicaties());
        if (indicatieStaatsloos == null && kind.getNationaliteiten().isEmpty())
        {
            objectenDieDeRegelOvertreden.add((PersoonBericht) RelatieUtils.haalKindUitRelatie(familieBericht));
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Haalt de indicatie staatsloos uit de indicatie lijst.
     *
     * @param indicaties de indicatie lijst.
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
}
