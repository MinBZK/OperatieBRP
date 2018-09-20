/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.prot;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.prot.LeveringsaantekeningHisVolledig;
import nl.bzk.brp.model.hisvolledig.prot.LeveringsaantekeningHisVolledigBasis;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Leveringsaantekening.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractLeveringsaantekeningHisVolledigView extends AbstractHisVolledigPredikaatView implements LeveringsaantekeningHisVolledigBasis
{

    protected final LeveringsaantekeningHisVolledig leveringsaantekening;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param leveringsaantekeningHisVolledig leveringsaantekening
     * @param predikaat predikaat
     */
    public AbstractLeveringsaantekeningHisVolledigView(final LeveringsaantekeningHisVolledig leveringsaantekeningHisVolledig, final Predicate predikaat) {
        this(leveringsaantekeningHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param leveringsaantekeningHisVolledig leveringsaantekening
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractLeveringsaantekeningHisVolledigView(
        final LeveringsaantekeningHisVolledig leveringsaantekeningHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.leveringsaantekening = leveringsaantekeningHisVolledig;
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
        return leveringsaantekening.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Integer getToegangLeveringsautorisatieId() {
        return leveringsaantekening.getToegangLeveringsautorisatieId();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Integer getDienstId() {
        return leveringsaantekening.getDienstId();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumTijdAttribuut getDatumTijdKlaarzettenLevering() {
        return leveringsaantekening.getDatumTijdKlaarzettenLevering();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumAttribuut getDatumMaterieelSelectie() {
        return leveringsaantekening.getDatumMaterieelSelectie();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumAttribuut getDatumAanvangMaterielePeriodeResultaat() {
        return leveringsaantekening.getDatumAanvangMaterielePeriodeResultaat();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumAttribuut getDatumEindeMaterielePeriodeResultaat() {
        return leveringsaantekening.getDatumEindeMaterielePeriodeResultaat();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumTijdAttribuut getDatumTijdAanvangFormelePeriodeResultaat() {
        return leveringsaantekening.getDatumTijdAanvangFormelePeriodeResultaat();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumTijdAttribuut getDatumTijdEindeFormelePeriodeResultaat() {
        return leveringsaantekening.getDatumTijdEindeFormelePeriodeResultaat();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Long getAdministratieveHandelingId() {
        return leveringsaantekening.getAdministratieveHandelingId();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SoortSynchronisatieAttribuut getSoortSynchronisatie() {
        return leveringsaantekening.getSoortSynchronisatie();

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
