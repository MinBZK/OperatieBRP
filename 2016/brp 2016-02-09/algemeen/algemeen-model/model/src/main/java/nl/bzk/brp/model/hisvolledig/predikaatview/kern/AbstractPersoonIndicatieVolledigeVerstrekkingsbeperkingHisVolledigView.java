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
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.predikaat.AltijdTonenGroepPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.FormeleHistorieEntiteitComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * Subtype klasse voor indicatie Volledige verstrekkingsbeperking?
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractPersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigView extends
        PersoonIndicatieHisVolledigView<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel> implements
        PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigBasis
{

    /**
     * Constructor met predikaat en peilmoment.
     *
     * @param persoonIndicatieHisVolledig wrapped indicatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmoment voor altijd tonen groepen
     */
    public AbstractPersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigView(
        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledig persoonIndicatieHisVolledig,
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
    public AbstractPersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigView(
        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledig persoonIndicatieHisVolledig,
        final Predicate predikaat)
    {
        super(persoonIndicatieHisVolledig, predikaat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel> getPersoonIndicatieHistorie() {
        final Set<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel> historie =
                new HashSet<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel>(getPersoonIndicatie().getPersoonIndicatieHistorie().getHistorie());
        final Set<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel> teTonenHistorie =
                new HashSet<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        final Set<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel>(
                    new FormeleHistorieEntiteitComparator<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel>(gesorteerdeHistorie);

    }

}
