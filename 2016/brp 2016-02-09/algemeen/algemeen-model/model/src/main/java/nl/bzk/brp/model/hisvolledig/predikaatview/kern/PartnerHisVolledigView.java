/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Partner.
 */
public final class PartnerHisVolledigView extends AbstractPartnerHisVolledigView implements PartnerHisVolledig {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param partnerHisVolledig betrokkenheid
     * @param predikaat          predikaat
     */
    public PartnerHisVolledigView(final PartnerHisVolledig partnerHisVolledig, final Predicate predikaat) {
        super(partnerHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param partnerHisVolledig betrokkenheid
     * @param predikaat          predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                           peilmomentVoorAltijdTonenGroepen
     */
    public PartnerHisVolledigView(final PartnerHisVolledig partnerHisVolledig, final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(partnerHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    /**
     * Gaat het om een partner in een Geregistreerd Partnerschap.
     *
     * @return {@code true}, als de relatie een geregistreerd partnerschap is
     */
    public boolean isGeregistreerdPartnerschap() {
        return getRelatie().getSoort().getWaarde() == SoortRelatie.GEREGISTREERD_PARTNERSCHAP;
    }

    /**
     * Gaat het om een partner in een Huwelijk.
     *
     * @return {@code true}, als de relatie een huwelijk is
     */
    public boolean isHuwelijk() {
        return getRelatie().getSoort().getWaarde() == SoortRelatie.HUWELIJK;
    }

}
