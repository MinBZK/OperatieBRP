/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ErkennerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HisVolledigComparatorFactory;
import nl.bzk.brp.model.hisvolledig.kern.InstemmerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.NaamgeverHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieBehandeldAlsNederlanderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieDerdeHeeftGezagHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieOnderCurateleHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieStaatloosHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieVastgesteldNietNederlanderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.AltijdTonenGroepPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.FormeleHistorieEntiteitComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.hisvolledig.predikaatview.MaterieleHistorieEntiteitComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.autaut.PersoonAfnemerindicatieHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNaamgebruikModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNummerverwijzingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonPersoonskaartModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonUitsluitingKiesrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfsrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.functors.AndPredicate;

/**
 * Predikaat view voor Persoon.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractPersoonHisVolledigView extends AbstractHisVolledigPredikaatView implements PersoonHisVolledigBasis, ElementIdentificeerbaar {

    protected final PersoonHisVolledig persoon;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonHisVolledig persoon
     * @param predikaat predikaat
     */
    public AbstractPersoonHisVolledigView(final PersoonHisVolledig persoonHisVolledig, final Predicate predikaat) {
        this(persoonHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonHisVolledig persoon
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractPersoonHisVolledigView(
        final PersoonHisVolledig persoonHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.persoon = persoonHisVolledig;
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
        return persoon.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SoortPersoonAttribuut getSoort() {
        return persoon.getSoort();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonAfgeleidAdministratiefModel> getPersoonAfgeleidAdministratiefHistorie() {
        final Set<HisPersoonAfgeleidAdministratiefModel> historie =
                new HashSet<HisPersoonAfgeleidAdministratiefModel>(persoon.getPersoonAfgeleidAdministratiefHistorie().getHistorie());
        final Set<HisPersoonAfgeleidAdministratiefModel> teTonenHistorie = new HashSet<HisPersoonAfgeleidAdministratiefModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonAfgeleidAdministratiefModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonAfgeleidAdministratiefModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonAfgeleidAdministratiefModel>(new FormeleHistorieEntiteitComparator<HisPersoonAfgeleidAdministratiefModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonAfgeleidAdministratiefModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final MaterieleHistorieSet<HisPersoonIdentificatienummersModel> getPersoonIdentificatienummersHistorie() {
        final Set<HisPersoonIdentificatienummersModel> historie =
                new HashSet<HisPersoonIdentificatienummersModel>(persoon.getPersoonIdentificatienummersHistorie().getHistorie());
        final Set<HisPersoonIdentificatienummersModel> teTonenHistorie = new HashSet<HisPersoonIdentificatienummersModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonIdentificatienummersModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonIdentificatienummersModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonIdentificatienummersModel>(new MaterieleHistorieEntiteitComparator<HisPersoonIdentificatienummersModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new MaterieleHistorieSetImpl<HisPersoonIdentificatienummersModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final MaterieleHistorieSet<HisPersoonSamengesteldeNaamModel> getPersoonSamengesteldeNaamHistorie() {
        final Set<HisPersoonSamengesteldeNaamModel> historie =
                new HashSet<HisPersoonSamengesteldeNaamModel>(persoon.getPersoonSamengesteldeNaamHistorie().getHistorie());
        final Set<HisPersoonSamengesteldeNaamModel> teTonenHistorie = new HashSet<HisPersoonSamengesteldeNaamModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonSamengesteldeNaamModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonSamengesteldeNaamModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonSamengesteldeNaamModel>(new MaterieleHistorieEntiteitComparator<HisPersoonSamengesteldeNaamModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new MaterieleHistorieSetImpl<HisPersoonSamengesteldeNaamModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonGeboorteModel> getPersoonGeboorteHistorie() {
        final Set<HisPersoonGeboorteModel> historie = new HashSet<HisPersoonGeboorteModel>(persoon.getPersoonGeboorteHistorie().getHistorie());
        final Set<HisPersoonGeboorteModel> teTonenHistorie = new HashSet<HisPersoonGeboorteModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonGeboorteModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonGeboorteModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonGeboorteModel>(new FormeleHistorieEntiteitComparator<HisPersoonGeboorteModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonGeboorteModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final MaterieleHistorieSet<HisPersoonGeslachtsaanduidingModel> getPersoonGeslachtsaanduidingHistorie() {
        final Set<HisPersoonGeslachtsaanduidingModel> historie =
                new HashSet<HisPersoonGeslachtsaanduidingModel>(persoon.getPersoonGeslachtsaanduidingHistorie().getHistorie());
        final Set<HisPersoonGeslachtsaanduidingModel> teTonenHistorie = new HashSet<HisPersoonGeslachtsaanduidingModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonGeslachtsaanduidingModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonGeslachtsaanduidingModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonGeslachtsaanduidingModel>(new MaterieleHistorieEntiteitComparator<HisPersoonGeslachtsaanduidingModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new MaterieleHistorieSetImpl<HisPersoonGeslachtsaanduidingModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonInschrijvingModel> getPersoonInschrijvingHistorie() {
        final Set<HisPersoonInschrijvingModel> historie = new HashSet<HisPersoonInschrijvingModel>(persoon.getPersoonInschrijvingHistorie().getHistorie());
        final Set<HisPersoonInschrijvingModel> teTonenHistorie = new HashSet<HisPersoonInschrijvingModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonInschrijvingModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonInschrijvingModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonInschrijvingModel>(new FormeleHistorieEntiteitComparator<HisPersoonInschrijvingModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonInschrijvingModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final MaterieleHistorieSet<HisPersoonNummerverwijzingModel> getPersoonNummerverwijzingHistorie() {
        final Set<HisPersoonNummerverwijzingModel> historie =
                new HashSet<HisPersoonNummerverwijzingModel>(persoon.getPersoonNummerverwijzingHistorie().getHistorie());
        final Set<HisPersoonNummerverwijzingModel> teTonenHistorie = new HashSet<HisPersoonNummerverwijzingModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonNummerverwijzingModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonNummerverwijzingModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonNummerverwijzingModel>(new MaterieleHistorieEntiteitComparator<HisPersoonNummerverwijzingModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new MaterieleHistorieSetImpl<HisPersoonNummerverwijzingModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final MaterieleHistorieSet<HisPersoonBijhoudingModel> getPersoonBijhoudingHistorie() {
        final Set<HisPersoonBijhoudingModel> historie = new HashSet<HisPersoonBijhoudingModel>(persoon.getPersoonBijhoudingHistorie().getHistorie());
        final Set<HisPersoonBijhoudingModel> teTonenHistorie = new HashSet<HisPersoonBijhoudingModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonBijhoudingModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonBijhoudingModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonBijhoudingModel>(new MaterieleHistorieEntiteitComparator<HisPersoonBijhoudingModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new MaterieleHistorieSetImpl<HisPersoonBijhoudingModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonOverlijdenModel> getPersoonOverlijdenHistorie() {
        final Set<HisPersoonOverlijdenModel> historie = new HashSet<HisPersoonOverlijdenModel>(persoon.getPersoonOverlijdenHistorie().getHistorie());
        final Set<HisPersoonOverlijdenModel> teTonenHistorie = new HashSet<HisPersoonOverlijdenModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonOverlijdenModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonOverlijdenModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonOverlijdenModel>(new FormeleHistorieEntiteitComparator<HisPersoonOverlijdenModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonOverlijdenModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonNaamgebruikModel> getPersoonNaamgebruikHistorie() {
        final Set<HisPersoonNaamgebruikModel> historie = new HashSet<HisPersoonNaamgebruikModel>(persoon.getPersoonNaamgebruikHistorie().getHistorie());
        final Set<HisPersoonNaamgebruikModel> teTonenHistorie = new HashSet<HisPersoonNaamgebruikModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonNaamgebruikModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonNaamgebruikModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonNaamgebruikModel>(new FormeleHistorieEntiteitComparator<HisPersoonNaamgebruikModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonNaamgebruikModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final MaterieleHistorieSet<HisPersoonMigratieModel> getPersoonMigratieHistorie() {
        final Set<HisPersoonMigratieModel> historie = new HashSet<HisPersoonMigratieModel>(persoon.getPersoonMigratieHistorie().getHistorie());
        final Set<HisPersoonMigratieModel> teTonenHistorie = new HashSet<HisPersoonMigratieModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonMigratieModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonMigratieModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonMigratieModel>(new MaterieleHistorieEntiteitComparator<HisPersoonMigratieModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new MaterieleHistorieSetImpl<HisPersoonMigratieModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonVerblijfsrechtModel> getPersoonVerblijfsrechtHistorie() {
        final Set<HisPersoonVerblijfsrechtModel> historie =
                new HashSet<HisPersoonVerblijfsrechtModel>(persoon.getPersoonVerblijfsrechtHistorie().getHistorie());
        final Set<HisPersoonVerblijfsrechtModel> teTonenHistorie = new HashSet<HisPersoonVerblijfsrechtModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonVerblijfsrechtModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonVerblijfsrechtModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonVerblijfsrechtModel>(new FormeleHistorieEntiteitComparator<HisPersoonVerblijfsrechtModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonVerblijfsrechtModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonUitsluitingKiesrechtModel> getPersoonUitsluitingKiesrechtHistorie() {
        final Set<HisPersoonUitsluitingKiesrechtModel> historie =
                new HashSet<HisPersoonUitsluitingKiesrechtModel>(persoon.getPersoonUitsluitingKiesrechtHistorie().getHistorie());
        final Set<HisPersoonUitsluitingKiesrechtModel> teTonenHistorie = new HashSet<HisPersoonUitsluitingKiesrechtModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonUitsluitingKiesrechtModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonUitsluitingKiesrechtModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonUitsluitingKiesrechtModel>(new FormeleHistorieEntiteitComparator<HisPersoonUitsluitingKiesrechtModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonUitsluitingKiesrechtModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonDeelnameEUVerkiezingenModel> getPersoonDeelnameEUVerkiezingenHistorie() {
        final Set<HisPersoonDeelnameEUVerkiezingenModel> historie =
                new HashSet<HisPersoonDeelnameEUVerkiezingenModel>(persoon.getPersoonDeelnameEUVerkiezingenHistorie().getHistorie());
        final Set<HisPersoonDeelnameEUVerkiezingenModel> teTonenHistorie = new HashSet<HisPersoonDeelnameEUVerkiezingenModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonDeelnameEUVerkiezingenModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonDeelnameEUVerkiezingenModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonDeelnameEUVerkiezingenModel>(new FormeleHistorieEntiteitComparator<HisPersoonDeelnameEUVerkiezingenModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonDeelnameEUVerkiezingenModel>(gesorteerdeHistorie);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FormeleHistorieSet<HisPersoonPersoonskaartModel> getPersoonPersoonskaartHistorie() {
        final Set<HisPersoonPersoonskaartModel> historie =
                new HashSet<HisPersoonPersoonskaartModel>(persoon.getPersoonPersoonskaartHistorie().getHistorie());
        final Set<HisPersoonPersoonskaartModel> teTonenHistorie = new HashSet<HisPersoonPersoonskaartModel>();
        teTonenHistorie.addAll(historie);

        CollectionUtils.filter(teTonenHistorie, getPredikaat());
        if (HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonPersoonskaartModel.class)
            && teTonenHistorie.isEmpty()
            && getPeilmomentVoorAltijdTonenGroepen() != null)
        {
            CollectionUtils.select(historie, AltijdTonenGroepPredikaat.bekendOp(getPeilmomentVoorAltijdTonenGroepen()), teTonenHistorie);
        }
        CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);
        final Set<HisPersoonPersoonskaartModel> gesorteerdeHistorie =
                new TreeSet<HisPersoonPersoonskaartModel>(new FormeleHistorieEntiteitComparator<HisPersoonPersoonskaartModel>());
        gesorteerdeHistorie.addAll(teTonenHistorie);

        return new FormeleHistorieSetImpl<HisPersoonPersoonskaartModel>(gesorteerdeHistorie);

    }

    /**
     * Retourneert of de predikaat historie records kent voor Voornamen of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public final boolean heeftVoornamen() {
        for (PersoonVoornaamHisVolledig persoonVoornaamHisVolledig : getVoornamen()) {
            if (!persoonVoornaamHisVolledig.getPersoonVoornaamHistorie().isLeeg()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Geslachtsnaamcomponenten of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public final boolean heeftGeslachtsnaamcomponenten() {
        for (PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponentHisVolledig : getGeslachtsnaamcomponenten()) {
            if (!persoonGeslachtsnaamcomponentHisVolledig.getPersoonGeslachtsnaamcomponentHistorie().isLeeg()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Verificaties of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public final boolean heeftVerificaties() {
        for (PersoonVerificatieHisVolledig persoonVerificatieHisVolledig : getVerificaties()) {
            if (!persoonVerificatieHisVolledig.getPersoonVerificatieHistorie().isLeeg()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Nationaliteiten of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public final boolean heeftNationaliteiten() {
        for (PersoonNationaliteitHisVolledig persoonNationaliteitHisVolledig : getNationaliteiten()) {
            if (!persoonNationaliteitHisVolledig.getPersoonNationaliteitHistorie().isLeeg()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Adressen of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public final boolean heeftAdressen() {
        for (PersoonAdresHisVolledig persoonAdresHisVolledig : getAdressen()) {
            if (!persoonAdresHisVolledig.getPersoonAdresHistorie().isLeeg()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Indicaties of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public final boolean heeftIndicaties() {
        for (PersoonIndicatieHisVolledig persoonIndicatieHisVolledig : getIndicaties()) {
            if (!persoonIndicatieHisVolledig.getPersoonIndicatieHistorie().isLeeg()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Reisdocumenten of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public final boolean heeftReisdocumenten() {
        for (PersoonReisdocumentHisVolledig persoonReisdocumentHisVolledig : getReisdocumenten()) {
            if (!persoonReisdocumentHisVolledig.getPersoonReisdocumentHistorie().isLeeg()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Betrokkenheden of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public abstract boolean heeftBetrokkenheden();

    /**
     * Retourneert of de predikaat historie records kent voor Onderzoeken of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public final boolean heeftOnderzoeken() {
        for (PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : getOnderzoeken()) {
            if (!persoonOnderzoekHisVolledig.getPersoonOnderzoekHistorie().isLeeg()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Verstrekkingsbeperkingen of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public final boolean heeftVerstrekkingsbeperkingen() {
        for (PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperkingHisVolledig : getVerstrekkingsbeperkingen()) {
            if (!persoonVerstrekkingsbeperkingHisVolledig.getPersoonVerstrekkingsbeperkingHistorie().isLeeg()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Afnemerindicaties of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public final boolean heeftAfnemerindicaties() {
        for (PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatieHisVolledig : getAfnemerindicaties()) {
            if (!persoonAfnemerindicatieHisVolledig.getPersoonAfnemerindicatieHistorie().isLeeg()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Voornamen of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public final boolean heeftVoornamenVoorLeveren() {
        for (PersoonVoornaamHisVolledig persoonVoornaamHisVolledig : getVoornamen()) {
            if (!persoonVoornaamHisVolledig.getPersoonVoornaamHistorie().isLeeg()) {
                for (HisPersoonVoornaamModel hisPersoonVoornaamModel : persoonVoornaamHisVolledig.getPersoonVoornaamHistorie().getHistorie()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Geslachtsnaamcomponenten of
     * niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public final boolean heeftGeslachtsnaamcomponentenVoorLeveren() {
        for (PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponentHisVolledig : getGeslachtsnaamcomponenten()) {
            if (!persoonGeslachtsnaamcomponentHisVolledig.getPersoonGeslachtsnaamcomponentHistorie().isLeeg()) {
                for (HisPersoonGeslachtsnaamcomponentModel hisPersoonGeslachtsnaamcomponentModel : persoonGeslachtsnaamcomponentHisVolledig.getPersoonGeslachtsnaamcomponentHistorie()
                                                                                                                                           .getHistorie())
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Verificaties of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public final boolean heeftVerificatiesVoorLeveren() {
        for (PersoonVerificatieHisVolledig persoonVerificatieHisVolledig : getVerificaties()) {
            if (!persoonVerificatieHisVolledig.getPersoonVerificatieHistorie().isLeeg()) {
                for (HisPersoonVerificatieModel hisPersoonVerificatieModel : persoonVerificatieHisVolledig.getPersoonVerificatieHistorie().getHistorie()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Nationaliteiten of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public final boolean heeftNationaliteitenVoorLeveren() {
        for (PersoonNationaliteitHisVolledig persoonNationaliteitHisVolledig : getNationaliteiten()) {
            if (!persoonNationaliteitHisVolledig.getPersoonNationaliteitHistorie().isLeeg()) {
                for (HisPersoonNationaliteitModel hisPersoonNationaliteitModel : persoonNationaliteitHisVolledig.getPersoonNationaliteitHistorie()
                                                                                                                .getHistorie())
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Adressen of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public final boolean heeftAdressenVoorLeveren() {
        for (PersoonAdresHisVolledig persoonAdresHisVolledig : getAdressen()) {
            if (!persoonAdresHisVolledig.getPersoonAdresHistorie().isLeeg()) {
                for (HisPersoonAdresModel hisPersoonAdresModel : persoonAdresHisVolledig.getPersoonAdresHistorie().getHistorie()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Indicaties of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public final boolean heeftIndicatiesVoorLeveren() {
        for (PersoonIndicatieHisVolledig<?> persoonIndicatieHisVolledig : getIndicaties()) {
            if (!persoonIndicatieHisVolledig.getPersoonIndicatieHistorie().isLeeg()) {
                for (HisPersoonIndicatieModel hisPersoonIndicatieModel : persoonIndicatieHisVolledig.getPersoonIndicatieHistorie().getHistorie()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Reisdocumenten of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public final boolean heeftReisdocumentenVoorLeveren() {
        for (PersoonReisdocumentHisVolledig persoonReisdocumentHisVolledig : getReisdocumenten()) {
            if (!persoonReisdocumentHisVolledig.getPersoonReisdocumentHistorie().isLeeg()) {
                for (HisPersoonReisdocumentModel hisPersoonReisdocumentModel : persoonReisdocumentHisVolledig.getPersoonReisdocumentHistorie()
                                                                                                             .getHistorie())
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Betrokkenheden of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public abstract boolean heeftBetrokkenhedenVoorLeveren();

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Onderzoeken of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public final boolean heeftOnderzoekenVoorLeveren() {
        for (PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : getOnderzoeken()) {
            if (!persoonOnderzoekHisVolledig.getPersoonOnderzoekHistorie().isLeeg()) {
                for (HisPersoonOnderzoekModel hisPersoonOnderzoekModel : persoonOnderzoekHisVolledig.getPersoonOnderzoekHistorie().getHistorie()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Verstrekkingsbeperkingen of
     * niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public final boolean heeftVerstrekkingsbeperkingenVoorLeveren() {
        for (PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperkingHisVolledig : getVerstrekkingsbeperkingen()) {
            if (!persoonVerstrekkingsbeperkingHisVolledig.getPersoonVerstrekkingsbeperkingHistorie().isLeeg()) {
                for (HisPersoonVerstrekkingsbeperkingModel hisPersoonVerstrekkingsbeperkingModel : persoonVerstrekkingsbeperkingHisVolledig.getPersoonVerstrekkingsbeperkingHistorie()
                                                                                                                                           .getHistorie())
                {
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
    public Set<? extends PersoonVoornaamHisVolledig> getVoornamen() {
        final SortedSet<PersoonVoornaamHisVolledig> resultaat =
                new TreeSet<PersoonVoornaamHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorPersoonVoornaam());
        for (PersoonVoornaamHisVolledig persoonVoornaam : persoon.getVoornamen()) {
            final PersoonVoornaamHisVolledigView predikaatView =
                    new PersoonVoornaamHisVolledigView(persoonVoornaam, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends PersoonGeslachtsnaamcomponentHisVolledig> getGeslachtsnaamcomponenten() {
        final SortedSet<PersoonGeslachtsnaamcomponentHisVolledig> resultaat =
                new TreeSet<PersoonGeslachtsnaamcomponentHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorPersoonGeslachtsnaamcomponent());
        for (PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponent : persoon.getGeslachtsnaamcomponenten()) {
            final PersoonGeslachtsnaamcomponentHisVolledigView predikaatView =
                    new PersoonGeslachtsnaamcomponentHisVolledigView(persoonGeslachtsnaamcomponent, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends PersoonVerificatieHisVolledig> getVerificaties() {
        final SortedSet<PersoonVerificatieHisVolledig> resultaat =
                new TreeSet<PersoonVerificatieHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorPersoonVerificatie());
        for (PersoonVerificatieHisVolledig persoonVerificatie : persoon.getVerificaties()) {
            final PersoonVerificatieHisVolledigView predikaatView =
                    new PersoonVerificatieHisVolledigView(persoonVerificatie, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends PersoonNationaliteitHisVolledig> getNationaliteiten() {
        final SortedSet<PersoonNationaliteitHisVolledig> resultaat =
                new TreeSet<PersoonNationaliteitHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorPersoonNationaliteit());
        for (PersoonNationaliteitHisVolledig persoonNationaliteit : persoon.getNationaliteiten()) {
            final PersoonNationaliteitHisVolledigView predikaatView =
                    new PersoonNationaliteitHisVolledigView(persoonNationaliteit, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends PersoonAdresHisVolledig> getAdressen() {
        final SortedSet<PersoonAdresHisVolledig> resultaat =
                new TreeSet<PersoonAdresHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorPersoonAdres());
        for (PersoonAdresHisVolledig persoonAdres : persoon.getAdressen()) {
            final PersoonAdresHisVolledigView predikaatView =
                    new PersoonAdresHisVolledigView(persoonAdres, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends PersoonReisdocumentHisVolledig> getReisdocumenten() {
        final SortedSet<PersoonReisdocumentHisVolledig> resultaat =
                new TreeSet<PersoonReisdocumentHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorPersoonReisdocument());
        for (PersoonReisdocumentHisVolledig persoonReisdocument : persoon.getReisdocumenten()) {
            final PersoonReisdocumentHisVolledigView predikaatView =
                    new PersoonReisdocumentHisVolledigView(persoonReisdocument, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends BetrokkenheidHisVolledig> getBetrokkenheden() {
        final SortedSet<BetrokkenheidHisVolledig> resultaat =
                new TreeSet<BetrokkenheidHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorBetrokkenheid());
        for (BetrokkenheidHisVolledig betrokkenheid : persoon.getBetrokkenheden()) {
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
     * {@inheritDoc}
     */
    @Override
    public Set<? extends PersoonOnderzoekHisVolledig> getOnderzoeken() {
        final SortedSet<PersoonOnderzoekHisVolledig> resultaat =
                new TreeSet<PersoonOnderzoekHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorPersoonOnderzoek());
        for (PersoonOnderzoekHisVolledig persoonOnderzoek : persoon.getOnderzoeken()) {
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
    public Set<? extends PersoonVerstrekkingsbeperkingHisVolledig> getVerstrekkingsbeperkingen() {
        final SortedSet<PersoonVerstrekkingsbeperkingHisVolledig> resultaat =
                new TreeSet<PersoonVerstrekkingsbeperkingHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorPersoonVerstrekkingsbeperking());
        for (PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperking : persoon.getVerstrekkingsbeperkingen()) {
            final PersoonVerstrekkingsbeperkingHisVolledigView predikaatView =
                    new PersoonVerstrekkingsbeperkingHisVolledigView(persoonVerstrekkingsbeperking, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends PersoonAfnemerindicatieHisVolledig> getAfnemerindicaties() {
        final SortedSet<PersoonAfnemerindicatieHisVolledig> resultaat =
                new TreeSet<PersoonAfnemerindicatieHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorPersoonAfnemerindicatie());
        for (PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatie : persoon.getAfnemerindicaties()) {
            final PersoonAfnemerindicatieHisVolledigView predikaatView =
                    new PersoonAfnemerindicatieHisVolledigView(persoonAfnemerindicatie, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
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
        resultaat.addAll(getPersoonAfgeleidAdministratiefHistorie().getHistorie());
        resultaat.addAll(getPersoonIdentificatienummersHistorie().getHistorie());
        resultaat.addAll(getPersoonSamengesteldeNaamHistorie().getHistorie());
        resultaat.addAll(getPersoonGeboorteHistorie().getHistorie());
        resultaat.addAll(getPersoonGeslachtsaanduidingHistorie().getHistorie());
        resultaat.addAll(getPersoonInschrijvingHistorie().getHistorie());
        resultaat.addAll(getPersoonNummerverwijzingHistorie().getHistorie());
        resultaat.addAll(getPersoonBijhoudingHistorie().getHistorie());
        resultaat.addAll(getPersoonOverlijdenHistorie().getHistorie());
        resultaat.addAll(getPersoonNaamgebruikHistorie().getHistorie());
        resultaat.addAll(getPersoonMigratieHistorie().getHistorie());
        resultaat.addAll(getPersoonVerblijfsrechtHistorie().getHistorie());
        resultaat.addAll(getPersoonUitsluitingKiesrechtHistorie().getHistorie());
        resultaat.addAll(getPersoonDeelnameEUVerkiezingenHistorie().getHistorie());
        resultaat.addAll(getPersoonPersoonskaartHistorie().getHistorie());
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieDerdeHeeftGezagHisVolledig getIndicatieDerdeHeeftGezag() {
        final PersoonIndicatieDerdeHeeftGezagHisVolledigView view;
        if (persoon.getIndicatieDerdeHeeftGezag() != null) {
            view =
                    new PersoonIndicatieDerdeHeeftGezagHisVolledigView(
                        persoon.getIndicatieDerdeHeeftGezag(),
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else {
            view = null;
        }
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieOnderCurateleHisVolledig getIndicatieOnderCuratele() {
        final PersoonIndicatieOnderCurateleHisVolledigView view;
        if (persoon.getIndicatieOnderCuratele() != null) {
            view =
                    new PersoonIndicatieOnderCurateleHisVolledigView(
                        persoon.getIndicatieOnderCuratele(),
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else {
            view = null;
        }
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledig getIndicatieVolledigeVerstrekkingsbeperking() {
        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigView view;
        if (persoon.getIndicatieVolledigeVerstrekkingsbeperking() != null) {
            view =
                    new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigView(
                        persoon.getIndicatieVolledigeVerstrekkingsbeperking(),
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else {
            view = null;
        }
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieVastgesteldNietNederlanderHisVolledig getIndicatieVastgesteldNietNederlander() {
        final PersoonIndicatieVastgesteldNietNederlanderHisVolledigView view;
        if (persoon.getIndicatieVastgesteldNietNederlander() != null) {
            view =
                    new PersoonIndicatieVastgesteldNietNederlanderHisVolledigView(
                        persoon.getIndicatieVastgesteldNietNederlander(),
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else {
            view = null;
        }
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieBehandeldAlsNederlanderHisVolledig getIndicatieBehandeldAlsNederlander() {
        final PersoonIndicatieBehandeldAlsNederlanderHisVolledigView view;
        if (persoon.getIndicatieBehandeldAlsNederlander() != null) {
            view =
                    new PersoonIndicatieBehandeldAlsNederlanderHisVolledigView(
                        persoon.getIndicatieBehandeldAlsNederlander(),
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else {
            view = null;
        }
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledig getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument()
    {
        final PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigView view;
        if (persoon.getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument() != null) {
            view =
                    new PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigView(
                        persoon.getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument(),
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else {
            view = null;
        }
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieStaatloosHisVolledig getIndicatieStaatloos() {
        final PersoonIndicatieStaatloosHisVolledigView view;
        if (persoon.getIndicatieStaatloos() != null) {
            view = new PersoonIndicatieStaatloosHisVolledigView(persoon.getIndicatieStaatloos(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        } else {
            view = null;
        }
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledig getIndicatieBijzondereVerblijfsrechtelijkePositie() {
        final PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigView view;
        if (persoon.getIndicatieBijzondereVerblijfsrechtelijkePositie() != null) {
            view =
                    new PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigView(
                        persoon.getIndicatieBijzondereVerblijfsrechtelijkePositie(),
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else {
            view = null;
        }
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public Set<? extends PersoonIndicatieHisVolledig> getIndicaties() {
        return Collections.unmodifiableSet(new HashSet<PersoonIndicatieHisVolledig>(CollectionUtils.select(Arrays.asList(
            getIndicatieDerdeHeeftGezag(),
            getIndicatieOnderCuratele(),
            getIndicatieVolledigeVerstrekkingsbeperking(),
            getIndicatieVastgesteldNietNederlander(),
            getIndicatieBehandeldAlsNederlander(),
            getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument(),
            getIndicatieStaatloos(),
            getIndicatieBijzondereVerblijfsrechtelijkePositie()), PredicateUtils.notNullPredicate())));
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON;
    }

}
