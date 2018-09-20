/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.verconv;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuderAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.OuderHisVolledigView;
import nl.bzk.brp.model.hisvolledig.verconv.LO3AanduidingOuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.verconv.LO3AanduidingOuderHisVolledigBasis;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor LO3 Aanduiding Ouder.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractLO3AanduidingOuderHisVolledigView extends AbstractHisVolledigPredikaatView implements LO3AanduidingOuderHisVolledigBasis {

    protected final LO3AanduidingOuderHisVolledig lO3AanduidingOuder;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param lO3AanduidingOuderHisVolledig lO3AanduidingOuder
     * @param predikaat predikaat
     */
    public AbstractLO3AanduidingOuderHisVolledigView(final LO3AanduidingOuderHisVolledig lO3AanduidingOuderHisVolledig, final Predicate predikaat) {
        this(lO3AanduidingOuderHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param lO3AanduidingOuderHisVolledig lO3AanduidingOuder
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractLO3AanduidingOuderHisVolledigView(
        final LO3AanduidingOuderHisVolledig lO3AanduidingOuderHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.lO3AanduidingOuder = lO3AanduidingOuderHisVolledig;
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
        return lO3AanduidingOuder.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OuderHisVolledig getOuder() {
        if (lO3AanduidingOuder.getOuder() != null) {
            return new OuderHisVolledigView(lO3AanduidingOuder.getOuder(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LO3SoortAanduidingOuderAttribuut getSoort() {
        return lO3AanduidingOuder.getSoort();

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
