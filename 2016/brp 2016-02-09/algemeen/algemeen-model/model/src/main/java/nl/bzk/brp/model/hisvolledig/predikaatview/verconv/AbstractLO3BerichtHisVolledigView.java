/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.verconv;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3ReferentieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3BerichtenBronAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.verconv.LO3BerichtHisVolledig;
import nl.bzk.brp.model.hisvolledig.verconv.LO3BerichtHisVolledigBasis;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor LO3 Bericht.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractLO3BerichtHisVolledigView extends AbstractHisVolledigPredikaatView implements LO3BerichtHisVolledigBasis {

    protected final LO3BerichtHisVolledig lO3Bericht;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param lO3BerichtHisVolledig lO3Bericht
     * @param predikaat predikaat
     */
    public AbstractLO3BerichtHisVolledigView(final LO3BerichtHisVolledig lO3BerichtHisVolledig, final Predicate predikaat) {
        this(lO3BerichtHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param lO3BerichtHisVolledig lO3Bericht
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractLO3BerichtHisVolledigView(
        final LO3BerichtHisVolledig lO3BerichtHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.lO3Bericht = lO3BerichtHisVolledig;
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
        return lO3Bericht.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final JaNeeAttribuut getIndicatieBerichtsoortOnderdeelLO3Stelsel() {
        return lO3Bericht.getIndicatieBerichtsoortOnderdeelLO3Stelsel();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LO3ReferentieAttribuut getReferentie() {
        return lO3Bericht.getReferentie();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LO3BerichtenBronAttribuut getBron() {
        return lO3Bericht.getBron();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final AdministratienummerAttribuut getAdministratienummer() {
        return lO3Bericht.getAdministratienummer();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonHisVolledig getPersoon() {
        if (lO3Bericht.getPersoon() != null) {
            return new PersoonHisVolledigView(lO3Bericht.getPersoon(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ByteaopslagAttribuut getBerichtdata() {
        return lO3Bericht.getBerichtdata();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ChecksumAttribuut getChecksum() {
        return lO3Bericht.getChecksum();

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
