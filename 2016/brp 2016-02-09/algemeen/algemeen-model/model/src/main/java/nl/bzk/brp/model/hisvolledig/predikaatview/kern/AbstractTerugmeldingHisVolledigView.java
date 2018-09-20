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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInTerugmeldingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HisVolledigComparatorFactory;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.TerugmeldingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.TerugmeldingHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.predikaat.AltijdTonenGroepPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.FormeleHistorieEntiteitComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.operationeel.kern.HisTerugmeldingContactpersoonModel;
import nl.bzk.brp.model.operationeel.kern.HisTerugmeldingModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;

/**
 * Predikaat view voor Terugmelding.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractTerugmeldingHisVolledigView extends AbstractHisVolledigPredikaatView implements TerugmeldingHisVolledigBasis,
        ElementIdentificeerbaar
{

    protected final TerugmeldingHisVolledig terugmelding;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param terugmeldingHisVolledig terugmelding
     * @param predikaat predikaat
     */
    public AbstractTerugmeldingHisVolledigView(final TerugmeldingHisVolledig terugmeldingHisVolledig, final Predicate predikaat) {
        this(terugmeldingHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param terugmeldingHisVolledig terugmelding
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractTerugmeldingHisVolledigView(
        final TerugmeldingHisVolledig terugmeldingHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.terugmelding = terugmeldingHisVolledig;
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
        return terugmelding.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PartijAttribuut getTerugmeldendePartij() {
        return terugmelding.getTerugmeldendePartij();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonHisVolledig getPersoon() {
        if (terugmelding.getPersoon() != null) {
            return new PersoonHisVolledigView(terugmelding.getPersoon(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PartijAttribuut getBijhoudingsgemeente() {
        return terugmelding.getBijhoudingsgemeente();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumTijdAttribuut getTijdstipRegistratie() {
        return terugmelding.getTijdstipRegistratie();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisTerugmeldingModel> getTerugmeldingHistorie() {
        final Set<HisTerugmeldingModel> historie = new HashSet<HisTerugmeldingModel>(terugmelding.getTerugmeldingHistorie().getHistorie());
        final Set<HisTerugmeldingModel> teTonenHistorie = new HashSet<HisTerugmeldingModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisTerugmeldingModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisTerugmeldingModel> gesorteerdeHistorie =
                new TreeSet<HisTerugmeldingModel>(new FormeleHistorieEntiteitComparator<HisTerugmeldingModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisTerugmeldingModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisTerugmeldingContactpersoonModel> getTerugmeldingContactpersoonHistorie() {
        final Set<HisTerugmeldingContactpersoonModel> historie =
                new HashSet<HisTerugmeldingContactpersoonModel>(terugmelding.getTerugmeldingContactpersoonHistorie().getHistorie());
        final Set<HisTerugmeldingContactpersoonModel> teTonenHistorie = new HashSet<HisTerugmeldingContactpersoonModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisTerugmeldingContactpersoonModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisTerugmeldingContactpersoonModel> gesorteerdeHistorie =
                new TreeSet<HisTerugmeldingContactpersoonModel>(new FormeleHistorieEntiteitComparator<HisTerugmeldingContactpersoonModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisTerugmeldingContactpersoonModel>(gesorteerdeHistorie);

    }

    /**
     * Retourneert of de predikaat historie records kent voor Gegevens of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public boolean heeftGegevens() {
        return true;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Gegevens of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public boolean heeftGegevensVoorLeveren() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends GegevenInTerugmeldingHisVolledig> getGegevens() {
        final SortedSet<GegevenInTerugmeldingHisVolledig> resultaat =
                new TreeSet<GegevenInTerugmeldingHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorGegevenInTerugmelding());
        for (GegevenInTerugmeldingHisVolledig gegevenInTerugmelding : terugmelding.getGegevens()) {
            final GegevenInTerugmeldingHisVolledigView predikaatView =
                    new GegevenInTerugmeldingHisVolledigView(gegevenInTerugmelding, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
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
        resultaat.addAll(getTerugmeldingHistorie().getHistorie());
        resultaat.addAll(getTerugmeldingContactpersoonHistorie().getHistorie());
        return resultaat;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.TERUGMELDING;
    }

}
