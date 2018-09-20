/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ErkennerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HisVolledigComparatorFactory;
import nl.bzk.brp.model.hisvolledig.kern.InstemmerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.NaamgeverHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.predikaat.AltijdTonenGroepPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.FormeleHistorieEntiteitComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;

/**
 * Predikaat view voor Relatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractRelatieHisVolledigView extends AbstractHisVolledigPredikaatView implements RelatieHisVolledigBasis, ElementIdentificeerbaar {

    protected final RelatieHisVolledig relatie;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param relatieHisVolledig relatie
     * @param predikaat predikaat
     */
    public AbstractRelatieHisVolledigView(final RelatieHisVolledig relatieHisVolledig, final Predicate predikaat) {
        this(relatieHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param relatieHisVolledig relatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractRelatieHisVolledigView(
        final RelatieHisVolledig relatieHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.relatie = relatieHisVolledig;
        this.peilmomentVoorAltijdTonenGroepen = peilmomentVoorAltijdTonenGroepen;

    }

    /**
     * Geeft de peilmomentVoorAltijdTonenGroepen terug.
     *
     * @return de peilmomentVoorAltijdTonenGroepen
     */
    public final DatumTijdAttribuut getPeilmomentVoorAltijdTonenGroepen() {
        return peilmomentVoorAltijdTonenGroepen;
    }

    /**
     * Zet de waarde van het veld peilmomentVoorAltijdTonenGroepen.
     *
     * @param peilmomentVoorAltijdTonenGroepen
     */
    public final void setPeilmomentVoorAltijdTonenGroepen(final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen) {
        this.peilmomentVoorAltijdTonenGroepen = peilmomentVoorAltijdTonenGroepen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Integer getID() {
        return relatie.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SoortRelatieAttribuut getSoort() {
        return relatie.getSoort();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisRelatieModel> getRelatieHistorie() {
        final Set<HisRelatieModel> historie = new HashSet<HisRelatieModel>(relatie.getRelatieHistorie().getHistorie());
        final Set<HisRelatieModel> teTonenHistorie = new HashSet<HisRelatieModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisRelatieModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisRelatieModel> gesorteerdeHistorie = new TreeSet<HisRelatieModel>(new FormeleHistorieEntiteitComparator<HisRelatieModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisRelatieModel>(gesorteerdeHistorie);

    }

    /**
     * Retourneert of de predikaat historie records kent voor Betrokkenheden of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public abstract boolean heeftBetrokkenheden();

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Betrokkenheden of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public abstract boolean heeftBetrokkenhedenVoorLeveren();

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends BetrokkenheidHisVolledig> getBetrokkenheden() {
        final SortedSet<BetrokkenheidHisVolledig> resultaat =
                new TreeSet<BetrokkenheidHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorBetrokkenheid());
        for (BetrokkenheidHisVolledig betrokkenheid : relatie.getBetrokkenheden()) {
            final BetrokkenheidHisVolledigView predikaatView;
            if (betrokkenheid instanceof ErkennerHisVolledig) {
                predikaatView = new ErkennerHisVolledigView((ErkennerHisVolledig) betrokkenheid, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            } else if (betrokkenheid instanceof InstemmerHisVolledig) {
                predikaatView = new InstemmerHisVolledigView((InstemmerHisVolledig) betrokkenheid, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            } else if (betrokkenheid instanceof KindHisVolledig) {
                predikaatView = new KindHisVolledigView((KindHisVolledig) betrokkenheid, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            } else if (betrokkenheid instanceof NaamgeverHisVolledig) {
                predikaatView = new NaamgeverHisVolledigView((NaamgeverHisVolledig) betrokkenheid, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            } else if (betrokkenheid instanceof OuderHisVolledig) {
                predikaatView = new OuderHisVolledigView((OuderHisVolledig) betrokkenheid, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            } else if (betrokkenheid instanceof PartnerHisVolledig) {
                predikaatView = new PartnerHisVolledigView((PartnerHisVolledig) betrokkenheid, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            } else {
                throw new IllegalArgumentException("Onbekend type Betrokkenheid.");
            }
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     *
     *
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        resultaat.addAll(getRelatieHistorie().getHistorie());
        return resultaat;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public ElementEnum getElementIdentificatie() {
        return ElementEnum.RELATIE;
    }

}
