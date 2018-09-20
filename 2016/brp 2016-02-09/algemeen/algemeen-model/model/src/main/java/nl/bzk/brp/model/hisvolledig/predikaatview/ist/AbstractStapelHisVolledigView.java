/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.ist;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.ist.StapelHisVolledig;
import nl.bzk.brp.model.hisvolledig.ist.StapelHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.ist.StapelRelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.ist.StapelVoorkomenHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HisVolledigComparatorFactory;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Stapel.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractStapelHisVolledigView extends AbstractHisVolledigPredikaatView implements StapelHisVolledigBasis {

    protected final StapelHisVolledig stapel;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param stapelHisVolledig stapel
     * @param predikaat predikaat
     */
    public AbstractStapelHisVolledigView(final StapelHisVolledig stapelHisVolledig, final Predicate predikaat) {
        this(stapelHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param stapelHisVolledig stapel
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractStapelHisVolledigView(
        final StapelHisVolledig stapelHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.stapel = stapelHisVolledig;
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
        return stapel.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonHisVolledig getPersoon() {
        if (stapel.getPersoon() != null) {
            return new PersoonHisVolledigView(stapel.getPersoon(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LO3CategorieAttribuut getCategorie() {
        return stapel.getCategorie();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final VolgnummerAttribuut getVolgnummer() {
        return stapel.getVolgnummer();

    }

    /**
     * Retourneert of de predikaat historie records kent voor Stapel relaties of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public boolean heeftStapelRelaties() {
        return true;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Stapel voorkomens of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public boolean heeftStapelVoorkomens() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends StapelRelatieHisVolledig> getStapelRelaties() {
        final SortedSet<StapelRelatieHisVolledig> resultaat =
                new TreeSet<StapelRelatieHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorStapelRelatie());
        for (StapelRelatieHisVolledig stapelRelatie : stapel.getStapelRelaties()) {
            final StapelRelatieHisVolledigView predikaatView =
                    new StapelRelatieHisVolledigView(stapelRelatie, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends StapelVoorkomenHisVolledig> getStapelVoorkomens() {
        final SortedSet<StapelVoorkomenHisVolledig> resultaat =
                new TreeSet<StapelVoorkomenHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorStapelVoorkomen());
        for (StapelVoorkomenHisVolledig stapelVoorkomen : stapel.getStapelVoorkomens()) {
            final StapelVoorkomenHisVolledigView predikaatView =
                    new StapelVoorkomenHisVolledigView(stapelVoorkomen, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
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
        return resultaat;
    }

}
