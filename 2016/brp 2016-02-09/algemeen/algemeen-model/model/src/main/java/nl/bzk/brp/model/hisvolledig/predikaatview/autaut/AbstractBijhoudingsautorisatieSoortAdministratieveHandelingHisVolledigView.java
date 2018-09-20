/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.autaut;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.autaut.BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.hisvolledig.autaut.BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.autaut.ToegangBijhoudingsautorisatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Bijhoudingsautorisatie \ Soort administratieve handeling.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractBijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigView extends AbstractHisVolledigPredikaatView implements
        BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigBasis
{

    protected final BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig bijhoudingsautorisatieSoortAdministratieveHandeling;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig
     *            bijhoudingsautorisatieSoortAdministratieveHandeling
     * @param predikaat predikaat
     */
    public AbstractBijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigView(
        final BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig bijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig,
        final Predicate predikaat)
    {
        this(bijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig
     *            bijhoudingsautorisatieSoortAdministratieveHandeling
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractBijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigView(
        final BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig bijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.bijhoudingsautorisatieSoortAdministratieveHandeling = bijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig;
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
        return bijhoudingsautorisatieSoortAdministratieveHandeling.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ToegangBijhoudingsautorisatieHisVolledig getToegangBijhoudingsautorisatie() {
        if (bijhoudingsautorisatieSoortAdministratieveHandeling.getToegangBijhoudingsautorisatie() != null) {
            return new ToegangBijhoudingsautorisatieHisVolledigView(
                bijhoudingsautorisatieSoortAdministratieveHandeling.getToegangBijhoudingsautorisatie(),
                getPredikaat(),
                getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SoortAdministratieveHandelingAttribuut getSoortAdministratieveHandeling() {
        return bijhoudingsautorisatieSoortAdministratieveHandeling.getSoortAdministratieveHandeling();

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
