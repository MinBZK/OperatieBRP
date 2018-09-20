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
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HisVolledigComparatorFactory;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.AltijdTonenGroepPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.FormeleHistorieEntiteitComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPartijOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOnderzoekModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;

/**
 * Predikaat view voor Onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractOnderzoekHisVolledigView extends AbstractHisVolledigPredikaatView implements OnderzoekHisVolledigBasis,
        ElementIdentificeerbaar
{

    protected final OnderzoekHisVolledig onderzoek;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param onderzoekHisVolledig onderzoek
     * @param predikaat predikaat
     */
    public AbstractOnderzoekHisVolledigView(final OnderzoekHisVolledig onderzoekHisVolledig, final Predicate predikaat) {
        this(onderzoekHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param onderzoekHisVolledig onderzoek
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractOnderzoekHisVolledigView(
        final OnderzoekHisVolledig onderzoekHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.onderzoek = onderzoekHisVolledig;
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
        return onderzoek.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisOnderzoekModel> getOnderzoekHistorie() {
        final Set<HisOnderzoekModel> historie = new HashSet<HisOnderzoekModel>(onderzoek.getOnderzoekHistorie().getHistorie());
        final Set<HisOnderzoekModel> teTonenHistorie = new HashSet<HisOnderzoekModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisOnderzoekModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisOnderzoekModel> gesorteerdeHistorie = new TreeSet<HisOnderzoekModel>(new FormeleHistorieEntiteitComparator<HisOnderzoekModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisOnderzoekModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisOnderzoekAfgeleidAdministratiefModel> getOnderzoekAfgeleidAdministratiefHistorie() {
        final Set<HisOnderzoekAfgeleidAdministratiefModel> historie =
                new HashSet<HisOnderzoekAfgeleidAdministratiefModel>(onderzoek.getOnderzoekAfgeleidAdministratiefHistorie().getHistorie());
        final Set<HisOnderzoekAfgeleidAdministratiefModel> teTonenHistorie = new HashSet<HisOnderzoekAfgeleidAdministratiefModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisOnderzoekAfgeleidAdministratiefModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisOnderzoekAfgeleidAdministratiefModel> gesorteerdeHistorie =
                new TreeSet<HisOnderzoekAfgeleidAdministratiefModel>(new FormeleHistorieEntiteitComparator<HisOnderzoekAfgeleidAdministratiefModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisOnderzoekAfgeleidAdministratiefModel>(gesorteerdeHistorie);

    }

    /**
     * Retourneert of de predikaat historie records kent voor Gegevens in onderzoek of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public boolean heeftGegevensInOnderzoek() {
        return true;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Personen in onderzoek of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public final boolean heeftPersonenInOnderzoek() {
        for (PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : getPersonenInOnderzoek()) {
            if (!persoonOnderzoekHisVolledig.getPersoonOnderzoekHistorie().isLeeg()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Partijen in onderzoek of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public final boolean heeftPartijenInOnderzoek() {
        for (PartijOnderzoekHisVolledig partijOnderzoekHisVolledig : getPartijenInOnderzoek()) {
            if (!partijOnderzoekHisVolledig.getPartijOnderzoekHistorie().isLeeg()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Gegevens in onderzoek of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public boolean heeftGegevensInOnderzoekVoorLeveren() {
        return true;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Personen in onderzoek of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public final boolean heeftPersonenInOnderzoekVoorLeveren() {
        for (PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : getPersonenInOnderzoek()) {
            if (!persoonOnderzoekHisVolledig.getPersoonOnderzoekHistorie().isLeeg()) {
                for (HisPersoonOnderzoekModel hisPersoonOnderzoekModel : persoonOnderzoekHisVolledig.getPersoonOnderzoekHistorie().getHistorie()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Partijen in onderzoek of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public final boolean heeftPartijenInOnderzoekVoorLeveren() {
        for (PartijOnderzoekHisVolledig partijOnderzoekHisVolledig : getPartijenInOnderzoek()) {
            if (!partijOnderzoekHisVolledig.getPartijOnderzoekHistorie().isLeeg()) {
                for (HisPartijOnderzoekModel hisPartijOnderzoekModel : partijOnderzoekHisVolledig.getPartijOnderzoekHistorie().getHistorie()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends GegevenInOnderzoekHisVolledig> getGegevensInOnderzoek() {
        final SortedSet<GegevenInOnderzoekHisVolledig> resultaat =
                new TreeSet<GegevenInOnderzoekHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorGegevenInOnderzoek());
        for (GegevenInOnderzoekHisVolledig gegevenInOnderzoek : onderzoek.getGegevensInOnderzoek()) {
            final GegevenInOnderzoekHisVolledigView predikaatView =
                    new GegevenInOnderzoekHisVolledigView(gegevenInOnderzoek, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends PersoonOnderzoekHisVolledig> getPersonenInOnderzoek() {
        final SortedSet<PersoonOnderzoekHisVolledig> resultaat =
                new TreeSet<PersoonOnderzoekHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorPersoonOnderzoek());
        for (PersoonOnderzoekHisVolledig persoonOnderzoek : onderzoek.getPersonenInOnderzoek()) {
            final PersoonOnderzoekHisVolledigView predikaatView =
                    new PersoonOnderzoekHisVolledigView(persoonOnderzoek, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends PartijOnderzoekHisVolledig> getPartijenInOnderzoek() {
        final SortedSet<PartijOnderzoekHisVolledig> resultaat =
                new TreeSet<PartijOnderzoekHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorPartijOnderzoek());
        for (PartijOnderzoekHisVolledig partijOnderzoek : onderzoek.getPartijenInOnderzoek()) {
            final PartijOnderzoekHisVolledigView predikaatView =
                    new PartijOnderzoekHisVolledigView(partijOnderzoek, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
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
        resultaat.addAll(getOnderzoekHistorie().getHistorie());
        resultaat.addAll(getOnderzoekAfgeleidAdministratiefHistorie().getHistorie());
        return resultaat;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ONDERZOEK;
    }

}
