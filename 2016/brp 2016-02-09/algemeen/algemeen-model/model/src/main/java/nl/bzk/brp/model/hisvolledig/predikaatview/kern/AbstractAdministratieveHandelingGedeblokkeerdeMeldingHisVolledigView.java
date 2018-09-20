/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GedeblokkeerdeMeldingHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Administratieve handeling \ Gedeblokkeerde melding.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractAdministratieveHandelingGedeblokkeerdeMeldingHisVolledigView extends AbstractHisVolledigPredikaatView implements
        AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigBasis, ElementIdentificeerbaar
{

    protected final AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig administratieveHandelingGedeblokkeerdeMelding;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param administratieveHandelingGedeblokkeerdeMeldingHisVolledig administratieveHandelingGedeblokkeerdeMelding
     * @param predikaat predikaat
     */
    public AbstractAdministratieveHandelingGedeblokkeerdeMeldingHisVolledigView(
        final AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig administratieveHandelingGedeblokkeerdeMeldingHisVolledig,
        final Predicate predikaat)
    {
        this(administratieveHandelingGedeblokkeerdeMeldingHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param administratieveHandelingGedeblokkeerdeMeldingHisVolledig administratieveHandelingGedeblokkeerdeMelding
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractAdministratieveHandelingGedeblokkeerdeMeldingHisVolledigView(
        final AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig administratieveHandelingGedeblokkeerdeMeldingHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.administratieveHandelingGedeblokkeerdeMelding = administratieveHandelingGedeblokkeerdeMeldingHisVolledig;
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
        return administratieveHandelingGedeblokkeerdeMelding.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratieveHandelingHisVolledig getAdministratieveHandeling() {
        if (administratieveHandelingGedeblokkeerdeMelding.getAdministratieveHandeling() != null) {
            return new AdministratieveHandelingHisVolledigView(
                administratieveHandelingGedeblokkeerdeMelding.getAdministratieveHandeling(),
                getPredikaat(),
                getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GedeblokkeerdeMeldingHisVolledig getGedeblokkeerdeMelding() {
        if (administratieveHandelingGedeblokkeerdeMelding.getGedeblokkeerdeMelding() != null) {
            return new GedeblokkeerdeMeldingHisVolledigView(
                administratieveHandelingGedeblokkeerdeMelding.getGedeblokkeerdeMelding(),
                getPredikaat(),
                getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
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

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING;
    }

}
