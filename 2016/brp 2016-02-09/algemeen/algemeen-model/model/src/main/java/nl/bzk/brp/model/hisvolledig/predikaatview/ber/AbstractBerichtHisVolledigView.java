/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.ber;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.ber.BerichtHisVolledig;
import nl.bzk.brp.model.hisvolledig.ber.BerichtHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.ber.BerichtPersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HisVolledigComparatorFactory;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Bericht.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractBerichtHisVolledigView extends AbstractHisVolledigPredikaatView implements BerichtHisVolledigBasis {

    protected final BerichtHisVolledig bericht;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param berichtHisVolledig bericht
     * @param predikaat predikaat
     */
    public AbstractBerichtHisVolledigView(final BerichtHisVolledig berichtHisVolledig, final Predicate predikaat) {
        this(berichtHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param berichtHisVolledig bericht
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractBerichtHisVolledigView(
        final BerichtHisVolledig berichtHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.bericht = berichtHisVolledig;
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
        return bericht.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SoortBerichtAttribuut getSoort() {
        return bericht.getSoort();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final RichtingAttribuut getRichting() {
        return bericht.getRichting();

    }

    /**
     * Retourneert of de predikaat historie records kent voor Personen of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public boolean heeftPersonen() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends BerichtPersoonHisVolledig> getPersonen() {
        final SortedSet<BerichtPersoonHisVolledig> resultaat =
                new TreeSet<BerichtPersoonHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorBerichtPersoon());
        for (BerichtPersoonHisVolledig berichtPersoon : bericht.getPersonen()) {
            final BerichtPersoonHisVolledigView predikaatView =
                    new BerichtPersoonHisVolledigView(berichtPersoon, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
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
