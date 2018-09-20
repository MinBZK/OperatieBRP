/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledigBasis;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Huwelijk/Geregistreerd partnerschap.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractHuwelijkGeregistreerdPartnerschapHisVolledigView extends RelatieHisVolledigView implements
        HuwelijkGeregistreerdPartnerschapHisVolledigBasis, ElementIdentificeerbaar
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param huwelijkGeregistreerdPartnerschapHisVolledig relatie
     * @param predikaat predikaat
     */
    public AbstractHuwelijkGeregistreerdPartnerschapHisVolledigView(
        final HuwelijkGeregistreerdPartnerschapHisVolledig huwelijkGeregistreerdPartnerschapHisVolledig,
        final Predicate predikaat)
    {
        this(huwelijkGeregistreerdPartnerschapHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param huwelijkGeregistreerdPartnerschapHisVolledig relatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractHuwelijkGeregistreerdPartnerschapHisVolledigView(
        final HuwelijkGeregistreerdPartnerschapHisVolledig huwelijkGeregistreerdPartnerschapHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(huwelijkGeregistreerdPartnerschapHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);

    }

    /**
     *
     *
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = super.getAlleHistorieRecords();
        return resultaat;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public ElementEnum getElementIdentificatie() {
        return ElementEnum.HUWELIJKGEREGISTREERDPARTNERSCHAP;
    }

}
