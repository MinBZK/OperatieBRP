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
import nl.bzk.brp.model.hisvolledig.kern.GeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GeregistreerdPartnerschapHisVolledigBasis;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Geregistreerd partnerschap.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractGeregistreerdPartnerschapHisVolledigView extends HuwelijkGeregistreerdPartnerschapHisVolledigView implements
        GeregistreerdPartnerschapHisVolledigBasis, ElementIdentificeerbaar
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param geregistreerdPartnerschapHisVolledig relatie
     * @param predikaat predikaat
     */
    public AbstractGeregistreerdPartnerschapHisVolledigView(
        final GeregistreerdPartnerschapHisVolledig geregistreerdPartnerschapHisVolledig,
        final Predicate predikaat)
    {
        this(geregistreerdPartnerschapHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param geregistreerdPartnerschapHisVolledig relatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractGeregistreerdPartnerschapHisVolledigView(
        final GeregistreerdPartnerschapHisVolledig geregistreerdPartnerschapHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(geregistreerdPartnerschapHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);

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
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.GEREGISTREERDPARTNERSCHAP;
    }

}
