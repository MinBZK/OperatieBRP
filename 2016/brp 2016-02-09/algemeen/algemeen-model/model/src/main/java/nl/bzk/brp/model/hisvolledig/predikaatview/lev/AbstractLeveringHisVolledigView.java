/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.lev;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.lev.LeveringHisVolledig;
import nl.bzk.brp.model.hisvolledig.lev.LeveringHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Levering.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractLeveringHisVolledigView extends AbstractHisVolledigPredikaatView implements LeveringHisVolledigBasis {

    protected final LeveringHisVolledig levering;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param leveringHisVolledig levering
     * @param predikaat predikaat
     */
    public AbstractLeveringHisVolledigView(final LeveringHisVolledig leveringHisVolledig, final Predicate predikaat) {
        this(leveringHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param leveringHisVolledig levering
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractLeveringHisVolledigView(
        final LeveringHisVolledig leveringHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.levering = leveringHisVolledig;
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
        return levering.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Integer getToegangAbonnementId() {
        return levering.getToegangAbonnementId();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Integer getDienstId() {
        return levering.getDienstId();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumTijdAttribuut getDatumTijdKlaarzettenLevering() {
        return levering.getDatumTijdKlaarzettenLevering();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumAttribuut getDatumMaterieelSelectie() {
        return levering.getDatumMaterieelSelectie();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumAttribuut getDatumAanvangMaterielePeriodeResultaat() {
        return levering.getDatumAanvangMaterielePeriodeResultaat();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumAttribuut getDatumEindeMaterielePeriodeResultaat() {
        return levering.getDatumEindeMaterielePeriodeResultaat();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumTijdAttribuut getDatumTijdAanvangFormelePeriodeResultaat() {
        return levering.getDatumTijdAanvangFormelePeriodeResultaat();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumTijdAttribuut getDatumTijdEindeFormelePeriodeResultaat() {
        return levering.getDatumTijdEindeFormelePeriodeResultaat();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Long getAdministratieveHandelingId() {
        return levering.getAdministratieveHandelingId();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SoortSynchronisatieAttribuut getSoortSynchronisatie() {
        return levering.getSoortSynchronisatie();

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
