/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Onderzoek.
 */
public final class OnderzoekHisVolledigView extends AbstractOnderzoekHisVolledigView implements OnderzoekHisVolledig {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param onderzoekHisVolledig onderzoek
     * @param predikaat            predikaat
     */
    public OnderzoekHisVolledigView(final OnderzoekHisVolledig onderzoekHisVolledig, final Predicate predikaat) {
        super(onderzoekHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param onderzoekHisVolledig onderzoek
     * @param predikaat            predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                             peilmomentVoorAltijdTonenGroepen
     */
    public OnderzoekHisVolledigView(final OnderzoekHisVolledig onderzoekHisVolledig, final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(onderzoekHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    @Override
    public Set<? extends PersoonOnderzoekHisVolledig> getPersonenInOnderzoek() {
        return null;
    }

    @Override
    public Set<? extends PartijOnderzoekHisVolledig> getPartijenInOnderzoek() {
        return null;
    }
}
