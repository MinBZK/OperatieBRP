/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Persoon \ Onderzoek.
 */
public final class PersoonOnderzoekHisVolledigView extends AbstractPersoonOnderzoekHisVolledigView implements
    PersoonOnderzoekHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonOnderzoekHisVolledig persoonOnderzoek
     * @param predikaat                   predikaat
     */
    public PersoonOnderzoekHisVolledigView(final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig,
        final Predicate predikaat)
    {
        super(persoonOnderzoekHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonOnderzoekHisVolledig      persoonOnderzoek
     * @param predikaat                        predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public PersoonOnderzoekHisVolledigView(final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig,
        final Predicate predikaat, final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(persoonOnderzoekHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    /**
     * @return heeft onderzoek gegevens die geleverd mogen worden.
     */
    public boolean heeftGegevensInOnderzoek() {
        final OnderzoekHisVolledig onderzoek = getOnderzoek();
        if (onderzoek != null) {
            for (final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig : onderzoek.getGegevensInOnderzoek()) {
                if (((GegevenInOnderzoekHisVolledigView) gegevenInOnderzoekHisVolledig).magGegevenGeleverdWorden()) {
                    return true;
                }
            }
        }
        return false;
    }
}
