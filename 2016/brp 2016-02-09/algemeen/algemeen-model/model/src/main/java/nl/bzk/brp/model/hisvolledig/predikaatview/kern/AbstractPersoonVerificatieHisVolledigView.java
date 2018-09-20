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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.predikaat.AltijdTonenGroepPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.FormeleHistorieEntiteitComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;

/**
 * Predikaat view voor Persoon \ Verificatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractPersoonVerificatieHisVolledigView extends AbstractHisVolledigPredikaatView implements PersoonVerificatieHisVolledigBasis,
        ElementIdentificeerbaar
{

    protected final PersoonVerificatieHisVolledig persoonVerificatie;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonVerificatieHisVolledig persoonVerificatie
     * @param predikaat predikaat
     */
    public AbstractPersoonVerificatieHisVolledigView(final PersoonVerificatieHisVolledig persoonVerificatieHisVolledig, final Predicate predikaat) {
        this(persoonVerificatieHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonVerificatieHisVolledig persoonVerificatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractPersoonVerificatieHisVolledigView(
        final PersoonVerificatieHisVolledig persoonVerificatieHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.persoonVerificatie = persoonVerificatieHisVolledig;
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
    public final Long getID() {
        return persoonVerificatie.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonHisVolledig getGeverifieerde() {
        if (persoonVerificatie.getGeverifieerde() != null) {
            return new PersoonHisVolledigView(persoonVerificatie.getGeverifieerde(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PartijAttribuut getPartij() {
        return persoonVerificatie.getPartij();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final NaamEnumeratiewaardeAttribuut getSoort() {
        return persoonVerificatie.getSoort();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonVerificatieModel> getPersoonVerificatieHistorie() {
        final Set<HisPersoonVerificatieModel> historie =
                new HashSet<HisPersoonVerificatieModel>(persoonVerificatie.getPersoonVerificatieHistorie().getHistorie());
        final Set<HisPersoonVerificatieModel> teTonenHistorie = new HashSet<HisPersoonVerificatieModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonVerificatieModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonVerificatieModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonVerificatieModel>(new FormeleHistorieEntiteitComparator<HisPersoonVerificatieModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonVerificatieModel>(gesorteerdeHistorie);

    }

    /**
     *
     *
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        resultaat.addAll(getPersoonVerificatieHistorie().getHistorie());
        return resultaat;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_VERIFICATIE;
    }

}
