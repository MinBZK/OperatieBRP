/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.List;

import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 * Basis visitor voor relaties.
 */
public abstract class AbstractRelatieVisitor {

    /**
     * Bepaal het actuele record (record waarbij de actie inhoud voorkomt in acties).
     *
     * @param historieSet historie set
     * @param acties acties
     * @param <T> historie type
     * @return het actuele record, kan null zijn
     */
    protected final <T extends HistorieEntiteit & FormeelVerantwoordbaar<ActieModel>> T bepaalActueel(
        final HistorieSet<T> historieSet,
        final List<Long> acties)
    {
        for (final T historie : historieSet) {
            final ActieModel actieInhoud = historie.getVerantwoordingInhoud();
            if (acties.contains(actieInhoud.getID())) {
                return historie;
            }
        }
        return null;
    }

    /**
     * Bepaal het beeindigde record (record waarbij de actie aanpassing geldigheid voorkomt in acties).
     *
     * @param historieSet historie set
     * @param acties acties
     * @param <T> historie type
     * @return het actuele record, kan null zijn
     */
    protected final <T extends HistorieEntiteit & MaterieelVerantwoordbaar<ActieModel>> T bepaalBeeindiging(
        final HistorieSet<T> historieSet,
        final List<Long> acties)
    {
        for (final T historie : historieSet) {
            final ActieModel actieAanpassingGeldigheid = historie.getVerantwoordingAanpassingGeldigheid();
            if (actieAanpassingGeldigheid != null && acties.contains(actieAanpassingGeldigheid.getID())) {
                return historie;
            }
        }
        return null;
    }

    /**
     * Bepaal het vervallen record (record waarbij de actie verval voorkomt in acties).
     *
     * @param historieSet historie set
     * @param acties acties
     * @param <T> historie type
     * @return het vervallen record, kan null zijn
     */
    protected final <T extends HistorieEntiteit & FormeelVerantwoordbaar<ActieModel>> T bepaalVerval(
        final HistorieSet<T> historieSet,
        final List<Long> acties)
    {
        T resultaat = null;
        boolean heeftIndVoorkomenTbvLevMuts = false;
        for (final T historie : historieSet) {
            final ActieModel verantwoordingVerval = historie.getVerantwoordingVerval();
            if (verantwoordingVerval != null && acties.contains(verantwoordingVerval.getID()) && !heeftIndVoorkomenTbvLevMuts) {
                resultaat = historie;
            }

            if (historie instanceof VerantwoordingTbvLeveringMutaties) {
                final ActieModel actieVervalTbvLevMuts = ((VerantwoordingTbvLeveringMutaties) historie).getVerantwoordingVervalTbvLeveringMutaties();
                if (actieVervalTbvLevMuts != null && !heeftIndVoorkomenTbvLevMuts) {
                    resultaat = historie;
                    heeftIndVoorkomenTbvLevMuts = ((VerantwoordingTbvLeveringMutaties) historie).getIndicatieVoorkomenTbvLeveringMutaties() != null;
                }
            }
        }
        return resultaat;
    }

}
