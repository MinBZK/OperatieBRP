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
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieStaatloosHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieStaatloosHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.predikaat.AltijdTonenGroepPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.hisvolledig.predikaatview.MaterieleHistorieEntiteitComparator;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieStaatloosModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * Subtype klasse voor indicatie Staatloos?
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractPersoonIndicatieStaatloosHisVolledigView extends PersoonIndicatieHisVolledigView<HisPersoonIndicatieStaatloosModel>
        implements PersoonIndicatieStaatloosHisVolledigBasis
{

    /**
     * Constructor met predikaat en peilmoment.
     *
     * @param persoonIndicatieHisVolledig wrapped indicatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmoment voor altijd tonen groepen
     */
    public AbstractPersoonIndicatieStaatloosHisVolledigView(
        final PersoonIndicatieStaatloosHisVolledig persoonIndicatieHisVolledig,
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
    public AbstractPersoonIndicatieStaatloosHisVolledigView(
        final PersoonIndicatieStaatloosHisVolledig persoonIndicatieHisVolledig,
        final Predicate predikaat)
    {
        super(persoonIndicatieHisVolledig, predikaat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final MaterieleHistorieSet<HisPersoonIndicatieStaatloosModel> getPersoonIndicatieHistorie() {
        final Set<HisPersoonIndicatieStaatloosModel> historie =
                new HashSet<HisPersoonIndicatieStaatloosModel>(getPersoonIndicatie().getPersoonIndicatieHistorie().getHistorie());
        final Set<HisPersoonIndicatieStaatloosModel> teTonenHistorie = new HashSet<HisPersoonIndicatieStaatloosModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonIndicatieStaatloosModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        final Set<HisPersoonIndicatieStaatloosModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonIndicatieStaatloosModel>(new MaterieleHistorieEntiteitComparator<HisPersoonIndicatieStaatloosModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new MaterieleHistorieSetImpl<HisPersoonIndicatieStaatloosModel>(gesorteerdeHistorie);

    }

}
