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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.FormeleHistorieEntiteitComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;

/**
 * Predikaat view voor Persoon \ Verstrekkingsbeperking.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractPersoonVerstrekkingsbeperkingHisVolledigView extends AbstractHisVolledigPredikaatView implements
        PersoonVerstrekkingsbeperkingHisVolledigBasis, ElementIdentificeerbaar
{

    protected final PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperking;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperking
     * @param predikaat predikaat
     */
    public AbstractPersoonVerstrekkingsbeperkingHisVolledigView(
        final PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperkingHisVolledig,
        final Predicate predikaat)
    {
        this(persoonVerstrekkingsbeperkingHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperking
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractPersoonVerstrekkingsbeperkingHisVolledigView(
        final PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperkingHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.persoonVerstrekkingsbeperking = persoonVerstrekkingsbeperkingHisVolledig;
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
        return persoonVerstrekkingsbeperking.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonHisVolledig getPersoon() {
        if (persoonVerstrekkingsbeperking.getPersoon() != null) {
            return new PersoonHisVolledigView(persoonVerstrekkingsbeperking.getPersoon(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PartijAttribuut getPartij() {
        return persoonVerstrekkingsbeperking.getPartij();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final OmschrijvingEnumeratiewaardeAttribuut getOmschrijvingDerde() {
        return persoonVerstrekkingsbeperking.getOmschrijvingDerde();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PartijAttribuut getGemeenteVerordening() {
        return persoonVerstrekkingsbeperking.getGemeenteVerordening();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonVerstrekkingsbeperkingModel> getPersoonVerstrekkingsbeperkingHistorie() {
        final Set<HisPersoonVerstrekkingsbeperkingModel> historie =
                new HashSet<HisPersoonVerstrekkingsbeperkingModel>(persoonVerstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorie().getHistorie());
        final Set<HisPersoonVerstrekkingsbeperkingModel> teTonenHistorie = new HashSet<HisPersoonVerstrekkingsbeperkingModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonVerstrekkingsbeperkingModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonVerstrekkingsbeperkingModel>(new FormeleHistorieEntiteitComparator<HisPersoonVerstrekkingsbeperkingModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonVerstrekkingsbeperkingModel>(gesorteerdeHistorie);

    }

    /**
     *
     *
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        resultaat.addAll(getPersoonVerstrekkingsbeperkingHistorie().getHistorie());
        return resultaat;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_VERSTREKKINGSBEPERKING;
    }

}
