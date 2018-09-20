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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.predikaat.AltijdTonenGroepPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.hisvolledig.predikaatview.MaterieleHistorieEntiteitComparator;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;

/**
 * Predikaat view voor Persoon \ Nationaliteit.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractPersoonNationaliteitHisVolledigView extends AbstractHisVolledigPredikaatView implements
        PersoonNationaliteitHisVolledigBasis, ElementIdentificeerbaar
{

    protected final PersoonNationaliteitHisVolledig persoonNationaliteit;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonNationaliteitHisVolledig persoonNationaliteit
     * @param predikaat predikaat
     */
    public AbstractPersoonNationaliteitHisVolledigView(final PersoonNationaliteitHisVolledig persoonNationaliteitHisVolledig, final Predicate predikaat) {
        this(persoonNationaliteitHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonNationaliteitHisVolledig persoonNationaliteit
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractPersoonNationaliteitHisVolledigView(
        final PersoonNationaliteitHisVolledig persoonNationaliteitHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.persoonNationaliteit = persoonNationaliteitHisVolledig;
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
        return persoonNationaliteit.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonHisVolledig getPersoon() {
        if (persoonNationaliteit.getPersoon() != null) {
            return new PersoonHisVolledigView(persoonNationaliteit.getPersoon(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final NationaliteitAttribuut getNationaliteit() {
        return persoonNationaliteit.getNationaliteit();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final MaterieleHistorieSet<HisPersoonNationaliteitModel> getPersoonNationaliteitHistorie() {
        final Set<HisPersoonNationaliteitModel> historie =
                new HashSet<HisPersoonNationaliteitModel>(persoonNationaliteit.getPersoonNationaliteitHistorie().getHistorie());
        final Set<HisPersoonNationaliteitModel> teTonenHistorie = new HashSet<HisPersoonNationaliteitModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonNationaliteitModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonNationaliteitModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonNationaliteitModel>(new MaterieleHistorieEntiteitComparator<HisPersoonNationaliteitModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new MaterieleHistorieSetImpl<HisPersoonNationaliteitModel>(gesorteerdeHistorie);

    }

    /**
     *
     *
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        resultaat.addAll(getPersoonNationaliteitHistorie().getHistorie());
        return resultaat;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_NATIONALITEIT;
    }

}
