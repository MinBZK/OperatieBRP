/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.predikaat.AltijdTonenGroepPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.FormeleHistorieEntiteitComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * Subtype klasse voor indicatie Bijzondere verblijfsrechtelijke positie?
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractPersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigView extends
        PersoonIndicatieHisVolledigView<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel> implements
        PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigBasis
{

    /**
     * Constructor met predikaat en peilmoment.
     *
     * @param persoonIndicatieHisVolledig wrapped indicatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmoment voor altijd tonen groepen
     */
    public AbstractPersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigView(
        final PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledig persoonIndicatieHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(persoonIndicatieHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    /**
     * Constructor met predikaat.
     *
     * @param persoonIndicatieHisVolledig wrapped indicatie
     * @param predikaat predikaat
     */
    public AbstractPersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigView(
        final PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledig persoonIndicatieHisVolledig,
        final Predicate predikaat)
    {
        super(persoonIndicatieHisVolledig, predikaat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel> getPersoonIndicatieHistorie() {
        final Set<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel> historie =
                new HashSet<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel>(getPersoonIndicatie().getPersoonIndicatieHistorie()
                                                                                                                .getHistorie());
        final Set<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel> teTonenHistorie =
                new HashSet<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        final Set<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel>(
                    new FormeleHistorieEntiteitComparator<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel>(gesorteerdeHistorie);

    }

}
