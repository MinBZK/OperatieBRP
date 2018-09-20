/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.autaut;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRolAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.autaut.ToegangBijhoudingsautorisatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.autaut.ToegangBijhoudingsautorisatieHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.predikaat.AltijdTonenGroepPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.FormeleHistorieEntiteitComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.operationeel.autaut.HisToegangBijhoudingsautorisatieModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;

/**
 * Predikaat view voor Toegang bijhoudingsautorisatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractToegangBijhoudingsautorisatieHisVolledigView extends AbstractHisVolledigPredikaatView implements
        ToegangBijhoudingsautorisatieHisVolledigBasis
{

    protected final ToegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatie;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param toegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatie
     * @param predikaat predikaat
     */
    public AbstractToegangBijhoudingsautorisatieHisVolledigView(
        final ToegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatieHisVolledig,
        final Predicate predikaat)
    {
        this(toegangBijhoudingsautorisatieHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param toegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractToegangBijhoudingsautorisatieHisVolledigView(
        final ToegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatieHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.toegangBijhoudingsautorisatie = toegangBijhoudingsautorisatieHisVolledig;
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
        return toegangBijhoudingsautorisatie.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PartijRolAttribuut getGeautoriseerde() {
        return toegangBijhoudingsautorisatie.getGeautoriseerde();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PartijAttribuut getOndertekenaar() {
        return toegangBijhoudingsautorisatie.getOndertekenaar();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PartijAttribuut getTransporteur() {
        return toegangBijhoudingsautorisatie.getTransporteur();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisToegangBijhoudingsautorisatieModel> getToegangBijhoudingsautorisatieHistorie() {
        final Set<HisToegangBijhoudingsautorisatieModel> historie =
                new HashSet<HisToegangBijhoudingsautorisatieModel>(toegangBijhoudingsautorisatie.getToegangBijhoudingsautorisatieHistorie().getHistorie());
        final Set<HisToegangBijhoudingsautorisatieModel> teTonenHistorie =
                new TreeSet<HisToegangBijhoudingsautorisatieModel>(new FormeleHistorieEntiteitComparator<HisToegangBijhoudingsautorisatieModel>());
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisToegangBijhoudingsautorisatieModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        return new FormeleHistorieSetImpl<HisToegangBijhoudingsautorisatieModel>(teTonenHistorie);

    }

    /**
     *
     *
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        resultaat.addAll(getToegangBijhoudingsautorisatieHistorie().getHistorie());
        return resultaat;
    }

}
