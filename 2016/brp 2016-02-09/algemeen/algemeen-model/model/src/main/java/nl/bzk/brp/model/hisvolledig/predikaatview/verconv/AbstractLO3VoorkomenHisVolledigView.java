/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.verconv;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.verconv.LO3BerichtHisVolledig;
import nl.bzk.brp.model.hisvolledig.verconv.LO3VoorkomenHisVolledig;
import nl.bzk.brp.model.hisvolledig.verconv.LO3VoorkomenHisVolledigBasis;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor LO3 Voorkomen.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractLO3VoorkomenHisVolledigView extends AbstractHisVolledigPredikaatView implements LO3VoorkomenHisVolledigBasis {

    protected final LO3VoorkomenHisVolledig lO3Voorkomen;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param lO3VoorkomenHisVolledig lO3Voorkomen
     * @param predikaat predikaat
     */
    public AbstractLO3VoorkomenHisVolledigView(final LO3VoorkomenHisVolledig lO3VoorkomenHisVolledig, final Predicate predikaat) {
        this(lO3VoorkomenHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param lO3VoorkomenHisVolledig lO3Voorkomen
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractLO3VoorkomenHisVolledigView(
        final LO3VoorkomenHisVolledig lO3VoorkomenHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.lO3Voorkomen = lO3VoorkomenHisVolledig;
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
        return lO3Voorkomen.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3BerichtHisVolledig getLO3Bericht() {
        if (lO3Voorkomen.getLO3Bericht() != null) {
            return new LO3BerichtHisVolledigView(lO3Voorkomen.getLO3Bericht(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LO3CategorieAttribuut getLO3Categorie() {
        return lO3Voorkomen.getLO3Categorie();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final VolgnummerAttribuut getLO3Stapelvolgnummer() {
        return lO3Voorkomen.getLO3Stapelvolgnummer();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final VolgnummerAttribuut getLO3Voorkomenvolgnummer() {
        return lO3Voorkomen.getLO3Voorkomenvolgnummer();

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
