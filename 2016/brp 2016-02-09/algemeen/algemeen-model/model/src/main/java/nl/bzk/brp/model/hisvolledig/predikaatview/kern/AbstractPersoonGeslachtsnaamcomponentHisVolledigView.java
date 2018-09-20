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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.AltijdTonenGroepPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.hisvolledig.predikaatview.MaterieleHistorieEntiteitComparator;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;

/**
 * Predikaat view voor Persoon \ Geslachtsnaamcomponent.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractPersoonGeslachtsnaamcomponentHisVolledigView extends AbstractHisVolledigPredikaatView implements
        PersoonGeslachtsnaamcomponentHisVolledigBasis, ElementIdentificeerbaar
{

    protected final PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponent;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponent
     * @param predikaat predikaat
     */
    public AbstractPersoonGeslachtsnaamcomponentHisVolledigView(
        final PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponentHisVolledig,
        final Predicate predikaat)
    {
        this(persoonGeslachtsnaamcomponentHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponent
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractPersoonGeslachtsnaamcomponentHisVolledigView(
        final PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponentHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.persoonGeslachtsnaamcomponent = persoonGeslachtsnaamcomponentHisVolledig;
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
        return persoonGeslachtsnaamcomponent.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonHisVolledig getPersoon() {
        if (persoonGeslachtsnaamcomponent.getPersoon() != null) {
            return new PersoonHisVolledigView(persoonGeslachtsnaamcomponent.getPersoon(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final VolgnummerAttribuut getVolgnummer() {
        return persoonGeslachtsnaamcomponent.getVolgnummer();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final MaterieleHistorieSet<HisPersoonGeslachtsnaamcomponentModel> getPersoonGeslachtsnaamcomponentHistorie() {
        final Set<HisPersoonGeslachtsnaamcomponentModel> historie =
                new HashSet<HisPersoonGeslachtsnaamcomponentModel>(persoonGeslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorie().getHistorie());
        final Set<HisPersoonGeslachtsnaamcomponentModel> teTonenHistorie = new HashSet<HisPersoonGeslachtsnaamcomponentModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonGeslachtsnaamcomponentModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonGeslachtsnaamcomponentModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonGeslachtsnaamcomponentModel>(new MaterieleHistorieEntiteitComparator<HisPersoonGeslachtsnaamcomponentModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new MaterieleHistorieSetImpl<HisPersoonGeslachtsnaamcomponentModel>(gesorteerdeHistorie);

    }

    /**
     *
     *
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        resultaat.addAll(getPersoonGeslachtsnaamcomponentHistorie().getHistorie());
        return resultaat;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT;
    }

}
