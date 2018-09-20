/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Persoon \ Indicatie.
 * @param <T>  het type HisPersoonIndicatieModel
 */
public abstract class PersoonIndicatieHisVolledigView<T extends HisPersoonIndicatieModel> implements
    PersoonIndicatieHisVolledig<T>
{

    private final PersoonIndicatieHisVolledig<T> persoonIndicatie;
    private final Predicate                      predikaat;
    private final DatumTijdAttribuut             peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonIndicatieHisVolledig persoonIndicatie
     * @param predikaat                   predikaat
     */
    public PersoonIndicatieHisVolledigView(final PersoonIndicatieHisVolledig<T> persoonIndicatieHisVolledig,
        final Predicate predikaat)
    {
        this(persoonIndicatieHisVolledig, predikaat, null);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonIndicatieHisVolledig persoonIndicatie
     * @param predikaat                   predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                                    peilmomentVoorAltijdTonenGroepen
     */
    public PersoonIndicatieHisVolledigView(final PersoonIndicatieHisVolledig<T> persoonIndicatieHisVolledig,
        final Predicate predikaat, final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        this.persoonIndicatie = persoonIndicatieHisVolledig;
        this.predikaat = predikaat;
        this.peilmomentVoorAltijdTonenGroepen = peilmomentVoorAltijdTonenGroepen;
    }

    /**
     * Geeft de predikaat terug.
     *
     * @return de predikaat
     */
    public Predicate getPredikaat() {
        return predikaat;
    }

    public PersoonIndicatieHisVolledig<T> getPersoonIndicatie() {
        return persoonIndicatie;
    }

    /**
     * Geeft de peilmomentVoorAltijdTonenGroepen terug.
     *
     * @return de peilmomentVoorAltijdTonenGroepen
     */
    public DatumTijdAttribuut getPeilmomentVoorAltijdTonenGroepen() {
        return peilmomentVoorAltijdTonenGroepen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getID() {
        return persoonIndicatie.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonHisVolledig getPersoon() {
        return new PersoonHisVolledigView(persoonIndicatie.getPersoon(), predikaat, null);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortIndicatieAttribuut getSoort() {
        return persoonIndicatie.getSoort();

    }

    @Override
    public abstract HistorieSet<T> getPersoonIndicatieHistorie();

    /**
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        resultaat.addAll(getPersoonIndicatieHistorie().getHistorie());
        return resultaat;
    }

}
