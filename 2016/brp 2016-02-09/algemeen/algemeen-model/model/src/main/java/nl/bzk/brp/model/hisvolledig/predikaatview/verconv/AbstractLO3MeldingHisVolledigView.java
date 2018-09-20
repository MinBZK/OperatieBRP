/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.verconv;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GroepAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3RubriekExclCategorieEnGroepAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SeverityAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortMeldingAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.verconv.LO3MeldingHisVolledig;
import nl.bzk.brp.model.hisvolledig.verconv.LO3MeldingHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.verconv.LO3VoorkomenHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor LO3 Melding.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractLO3MeldingHisVolledigView extends AbstractHisVolledigPredikaatView implements LO3MeldingHisVolledigBasis {

    protected final LO3MeldingHisVolledig lO3Melding;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param lO3MeldingHisVolledig lO3Melding
     * @param predikaat predikaat
     */
    public AbstractLO3MeldingHisVolledigView(final LO3MeldingHisVolledig lO3MeldingHisVolledig, final Predicate predikaat) {
        this(lO3MeldingHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param lO3MeldingHisVolledig lO3Melding
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractLO3MeldingHisVolledigView(
        final LO3MeldingHisVolledig lO3MeldingHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.lO3Melding = lO3MeldingHisVolledig;
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
        return lO3Melding.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3VoorkomenHisVolledig getLO3Voorkomen() {
        if (lO3Melding.getLO3Voorkomen() != null) {
            return new LO3VoorkomenHisVolledigView(lO3Melding.getLO3Voorkomen(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LO3SoortMeldingAttribuut getSoort() {
        return lO3Melding.getSoort();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LO3SeverityAttribuut getLogSeverity() {
        return lO3Melding.getLogSeverity();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LO3GroepAttribuut getGroep() {
        return lO3Melding.getGroep();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LO3RubriekExclCategorieEnGroepAttribuut getRubriek() {
        return lO3Melding.getRubriek();

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
