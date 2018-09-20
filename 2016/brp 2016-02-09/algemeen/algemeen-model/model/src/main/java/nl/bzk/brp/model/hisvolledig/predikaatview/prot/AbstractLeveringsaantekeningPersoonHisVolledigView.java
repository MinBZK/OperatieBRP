/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.prot;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.prot.LeveringsaantekeningHisVolledig;
import nl.bzk.brp.model.hisvolledig.prot.LeveringsaantekeningPersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.prot.LeveringsaantekeningPersoonHisVolledigBasis;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Leveringsaantekening \ Persoon.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractLeveringsaantekeningPersoonHisVolledigView extends AbstractHisVolledigPredikaatView implements
        LeveringsaantekeningPersoonHisVolledigBasis
{

    protected final LeveringsaantekeningPersoonHisVolledig leveringsaantekeningPersoon;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param leveringsaantekeningPersoonHisVolledig leveringsaantekeningPersoon
     * @param predikaat predikaat
     */
    public AbstractLeveringsaantekeningPersoonHisVolledigView(
        final LeveringsaantekeningPersoonHisVolledig leveringsaantekeningPersoonHisVolledig,
        final Predicate predikaat)
    {
        this(leveringsaantekeningPersoonHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param leveringsaantekeningPersoonHisVolledig leveringsaantekeningPersoon
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractLeveringsaantekeningPersoonHisVolledigView(
        final LeveringsaantekeningPersoonHisVolledig leveringsaantekeningPersoonHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.leveringsaantekeningPersoon = leveringsaantekeningPersoonHisVolledig;
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
        return leveringsaantekeningPersoon.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LeveringsaantekeningHisVolledig getLeveringsaantekening() {
        if (leveringsaantekeningPersoon.getLeveringsaantekening() != null) {
            return new LeveringsaantekeningHisVolledigView(
                leveringsaantekeningPersoon.getLeveringsaantekening(),
                getPredikaat(),
                getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Integer getPersoonId() {
        return leveringsaantekeningPersoon.getPersoonId();

    }

    /**
     *
     *
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        return resultaat;
    }

}
