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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.kern.ErkenningOngeborenVruchtHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.NaamskeuzeOngeborenVruchtHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.FormeleHistorieEntiteitComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;

/**
 * Predikaat view voor Betrokkenheid.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractBetrokkenheidHisVolledigView extends AbstractHisVolledigPredikaatView implements BetrokkenheidHisVolledigBasis,
        ElementIdentificeerbaar
{

    protected final BetrokkenheidHisVolledig betrokkenheid;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param betrokkenheidHisVolledig betrokkenheid
     * @param predikaat predikaat
     */
    public AbstractBetrokkenheidHisVolledigView(final BetrokkenheidHisVolledig betrokkenheidHisVolledig, final Predicate predikaat) {
        this(betrokkenheidHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param betrokkenheidHisVolledig betrokkenheid
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractBetrokkenheidHisVolledigView(
        final BetrokkenheidHisVolledig betrokkenheidHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.betrokkenheid = betrokkenheidHisVolledig;
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
        return betrokkenheid.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RelatieHisVolledig getRelatie() {
        final RelatieHisVolledig relatie = betrokkenheid.getRelatie();
        final RelatieHisVolledigView predikaatView;
        if (relatie instanceof ErkenningOngeborenVruchtHisVolledig) {
            predikaatView =
                    new ErkenningOngeborenVruchtHisVolledigView(
                        (ErkenningOngeborenVruchtHisVolledig) relatie,
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else if (relatie instanceof FamilierechtelijkeBetrekkingHisVolledig) {
            predikaatView =
                    new FamilierechtelijkeBetrekkingHisVolledigView(
                        (FamilierechtelijkeBetrekkingHisVolledig) relatie,
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else if (relatie instanceof GeregistreerdPartnerschapHisVolledig) {
            predikaatView =
                    new GeregistreerdPartnerschapHisVolledigView(
                        (GeregistreerdPartnerschapHisVolledig) relatie,
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else if (relatie instanceof HuwelijkHisVolledig) {
            predikaatView = new HuwelijkHisVolledigView((HuwelijkHisVolledig) relatie, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        } else if (relatie instanceof NaamskeuzeOngeborenVruchtHisVolledig) {
            predikaatView =
                    new NaamskeuzeOngeborenVruchtHisVolledigView(
                        (NaamskeuzeOngeborenVruchtHisVolledig) relatie,
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else {
            predikaatView = null;
        }
        return predikaatView;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SoortBetrokkenheidAttribuut getRol() {
        return betrokkenheid.getRol();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonHisVolledig getPersoon() {
        if (betrokkenheid.getPersoon() != null) {
            return new PersoonHisVolledigView(betrokkenheid.getPersoon(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisBetrokkenheidModel> getBetrokkenheidHistorie() {
        final Set<HisBetrokkenheidModel> historie = new HashSet<HisBetrokkenheidModel>(betrokkenheid.getBetrokkenheidHistorie().getHistorie());
        final Set<HisBetrokkenheidModel> teTonenHistorie = new HashSet<HisBetrokkenheidModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisBetrokkenheidModel> gesorteerdeHistorie =
                new TreeSet<HisBetrokkenheidModel>(new FormeleHistorieEntiteitComparator<HisBetrokkenheidModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisBetrokkenheidModel>(gesorteerdeHistorie);

    }

    /**
     *
     *
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        resultaat.addAll(getBetrokkenheidHistorie().getHistorie());
        return resultaat;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public ElementEnum getElementIdentificatie() {
        return ElementEnum.BETROKKENHEID;
    }

}
