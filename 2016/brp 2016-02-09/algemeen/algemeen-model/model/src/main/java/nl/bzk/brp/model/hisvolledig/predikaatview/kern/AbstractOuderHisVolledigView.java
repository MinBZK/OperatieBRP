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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.predikaat.AltijdTonenGroepPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.hisvolledig.predikaatview.MaterieleHistorieEntiteitComparator;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;

/**
 * Predikaat view voor Ouder.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractOuderHisVolledigView extends BetrokkenheidHisVolledigView implements OuderHisVolledigBasis, ElementIdentificeerbaar {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param ouderHisVolledig betrokkenheid
     * @param predikaat predikaat
     */
    public AbstractOuderHisVolledigView(final OuderHisVolledig ouderHisVolledig, final Predicate predikaat) {
        this(ouderHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param ouderHisVolledig betrokkenheid
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractOuderHisVolledigView(
        final OuderHisVolledig ouderHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(ouderHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final MaterieleHistorieSet<HisOuderOuderschapModel> getOuderOuderschapHistorie() {
        final Set<HisOuderOuderschapModel> historie =
                new HashSet<HisOuderOuderschapModel>(((OuderHisVolledig) betrokkenheid).getOuderOuderschapHistorie().getHistorie());
        final Set<HisOuderOuderschapModel> teTonenHistorie = new HashSet<HisOuderOuderschapModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisOuderOuderschapModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisOuderOuderschapModel> gesorteerdeHistorie =
                new TreeSet<HisOuderOuderschapModel>(new MaterieleHistorieEntiteitComparator<HisOuderOuderschapModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new MaterieleHistorieSetImpl<HisOuderOuderschapModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final MaterieleHistorieSet<HisOuderOuderlijkGezagModel> getOuderOuderlijkGezagHistorie() {
        final Set<HisOuderOuderlijkGezagModel> historie =
                new HashSet<HisOuderOuderlijkGezagModel>(((OuderHisVolledig) betrokkenheid).getOuderOuderlijkGezagHistorie().getHistorie());
        final Set<HisOuderOuderlijkGezagModel> teTonenHistorie = new HashSet<HisOuderOuderlijkGezagModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisOuderOuderlijkGezagModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisOuderOuderlijkGezagModel> gesorteerdeHistorie =
                new TreeSet<HisOuderOuderlijkGezagModel>(new MaterieleHistorieEntiteitComparator<HisOuderOuderlijkGezagModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new MaterieleHistorieSetImpl<HisOuderOuderlijkGezagModel>(gesorteerdeHistorie);

    }

    /**
     *
     *
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = super.getAlleHistorieRecords();
        resultaat.addAll(getOuderOuderschapHistorie().getHistorie());
        resultaat.addAll(getOuderOuderlijkGezagHistorie().getHistorie());
        return resultaat;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.OUDER;
    }

}
