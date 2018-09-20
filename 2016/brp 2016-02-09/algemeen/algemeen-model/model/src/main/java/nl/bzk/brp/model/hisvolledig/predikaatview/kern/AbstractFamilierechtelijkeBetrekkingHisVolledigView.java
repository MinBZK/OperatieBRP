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
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigBasis;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Familierechtelijke Betrekking.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractFamilierechtelijkeBetrekkingHisVolledigView extends RelatieHisVolledigView implements
        FamilierechtelijkeBetrekkingHisVolledigBasis, ElementIdentificeerbaar
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param familierechtelijkeBetrekkingHisVolledig relatie
     * @param predikaat predikaat
     */
    public AbstractFamilierechtelijkeBetrekkingHisVolledigView(
        final FamilierechtelijkeBetrekkingHisVolledig familierechtelijkeBetrekkingHisVolledig,
        final Predicate predikaat)
    {
        this(familierechtelijkeBetrekkingHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param familierechtelijkeBetrekkingHisVolledig relatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractFamilierechtelijkeBetrekkingHisVolledigView(
        final FamilierechtelijkeBetrekkingHisVolledig familierechtelijkeBetrekkingHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(familierechtelijkeBetrekkingHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);

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
        return ElementEnum.FAMILIERECHTELIJKEBETREKKING;
    }

}
