/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import org.springframework.stereotype.Service;

/**
 * Implementatie van OnderzoekenFilterService.
 *
 * @brp.bedrijfsregel R2063, R2065
 */
@Regels({Regel.R2065, Regel.R2063})
@Service
public class OnderzoekenFilterServiceImpl implements OnderzoekenFilterService {


    @Override
    public final void filterOnderzoekGegevensNaarOntbrekendeGegevens(final List<PersoonHisVolledig> persoonHisVolledigViews) {
        for (final PersoonHisVolledig persoonHisVolledig : persoonHisVolledigViews) {
            for (final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : persoonHisVolledig.getOnderzoeken()) {
                final OnderzoekHisVolledig onderzoek = persoonOnderzoekHisVolledig.getOnderzoek();
                if (onderzoek != null) {
                    final Set<GegevenInOnderzoekHisVolledig> teVerwijderen = new HashSet<>();
                    final Set<? extends GegevenInOnderzoekHisVolledig> gegevensInOnderzoek = onderzoek
                        .getGegevensInOnderzoek();
                    for (final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig : gegevensInOnderzoek) {
                        if ((gegevenInOnderzoekHisVolledig.getObjectSleutelGegeven() == null
                            || gegevenInOnderzoekHisVolledig.getObjectSleutelGegeven().getWaarde() == null) && (gegevenInOnderzoekHisVolledig
                            .getVoorkomenSleutelGegeven() == null || gegevenInOnderzoekHisVolledig
                            .getVoorkomenSleutelGegeven().getWaarde() == null))
                        {
                            teVerwijderen.add(gegevenInOnderzoekHisVolledig);
                        }
                    }
                    gegevensInOnderzoek.removeAll(teVerwijderen);
                }
            }
        }
    }
}
