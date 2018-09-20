/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.verconv;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.verconv.LO3VoorkomenHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor LO3 Voorkomen.
 *
 */
public final class LO3VoorkomenHisVolledigView extends AbstractLO3VoorkomenHisVolledigView implements LO3VoorkomenHisVolledig {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param lO3VoorkomenHisVolledig lO3Voorkomen
     * @param predikaat predikaat
     */
    public LO3VoorkomenHisVolledigView(final LO3VoorkomenHisVolledig lO3VoorkomenHisVolledig, final Predicate predikaat) {
        super(lO3VoorkomenHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param lO3VoorkomenHisVolledig lO3Voorkomen
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public LO3VoorkomenHisVolledigView(
        final LO3VoorkomenHisVolledig lO3VoorkomenHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(lO3VoorkomenHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
