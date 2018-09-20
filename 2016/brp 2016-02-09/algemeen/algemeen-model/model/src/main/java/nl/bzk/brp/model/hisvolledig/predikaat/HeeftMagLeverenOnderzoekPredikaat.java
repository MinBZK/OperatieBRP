/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import org.apache.commons.collections.Predicate;

/**
 * HeeftMagLeverenOnderzoekPredikaat.
 */
public class HeeftMagLeverenOnderzoekPredikaat implements Predicate {

    @Override
    public final boolean evaluate(final Object o) {
        if (o instanceof Groep && o instanceof HisOnderzoekModel) {
            final HisOnderzoekModel hisOnderzoekModel = (HisOnderzoekModel) o;
            for (Attribuut attribuut : hisOnderzoekModel.getAttributen()) {
                if (attribuut.isMagGeleverdWorden()) {
                    return true;
                }
            }
            final OnderzoekHisVolledig onderzoek = hisOnderzoekModel.getOnderzoek();
            if (onderzoek.getOnderzoekHistorie().isLeeg()) {
                return false;
            }
            for (final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig : onderzoek.getGegevensInOnderzoek()) {
                if (gegevenInOnderzoekHisVolledig.getElement() != null && gegevenInOnderzoekHisVolledig.getElement().isMagGeleverdWorden()) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
