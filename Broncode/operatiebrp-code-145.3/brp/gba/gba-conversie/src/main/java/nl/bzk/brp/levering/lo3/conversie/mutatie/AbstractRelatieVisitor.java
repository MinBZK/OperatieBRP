/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.Collection;
import java.util.List;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Basis visitor voor relaties.
 */
public abstract class AbstractRelatieVisitor {

    /**
     * Bepaal het actuele record (record waarbij de actie inhoud voorkomt in acties).
     * @param historieSet historie set
     * @param acties acties
     * @return het actuele record, kan null zijn
     */
    protected final MetaRecord bepaalActueel(final Collection<MetaRecord> historieSet, final List<Long> acties) {
        for (final MetaRecord historie : historieSet) {
            final Actie actieInhoud = historie.getActieInhoud();
            if (acties.contains(actieInhoud.getId())) {
                return historie;
            }
        }
        return null;
    }

    /**
     * Bepaal het beeindigde record (record waarbij de actie aanpassing geldigheid voorkomt in acties).
     * @param historieSet historie set
     * @param acties acties
     * @return het actuele record, kan null zijn
     */
    protected final MetaRecord bepaalBeeindiging(final Collection<MetaRecord> historieSet, final List<Long> acties) {
        for (final MetaRecord historie : historieSet) {
            final Actie actieAanpassingGeldigheid = historie.getActieAanpassingGeldigheid();
            if (actieAanpassingGeldigheid != null && acties.contains(actieAanpassingGeldigheid.getId())) {
                return historie;
            }
        }
        return null;
    }

    /**
     * Bepaal het vervallen record (record waarbij de actie verval voorkomt in acties).
     * @param historieSet historie set
     * @param acties acties
     * @return het vervallen record, kan null zijn
     */
    protected final MetaRecord bepaalVerval(final Collection<MetaRecord> historieSet, final List<Long> acties) {
        MetaRecord resultaat = null;
        boolean heeftIndVoorkomenTbvLevMuts = false;
        for (final MetaRecord historie : historieSet) {
            final Actie verantwoordingVerval = historie.getActieVerval();
            if (verantwoordingVerval != null && acties.contains(verantwoordingVerval.getId()) && !heeftIndVoorkomenTbvLevMuts) {
                resultaat = historie;
            }

            final Actie actieVervalTbvLevMuts = historie.getActieVervalTbvLeveringMutaties();
            if (actieVervalTbvLevMuts != null && acties.contains(actieVervalTbvLevMuts.getId()) && !heeftIndVoorkomenTbvLevMuts) {
                resultaat = historie;
                heeftIndVoorkomenTbvLevMuts = Boolean.TRUE.equals(historie.isIndicatieTbvLeveringMutaties());
            }
        }
        return resultaat;
    }

}
