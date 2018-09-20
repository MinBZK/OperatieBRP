/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoekBasis;
import nl.bzk.brp.model.operationeel.kern.HisPartijOnderzoekModel;

/**
 * View klasse voor Partij \ Onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractPartijOnderzoekView implements ModelMoment, PartijOnderzoekBasis, ElementIdentificeerbaar {

    private final PartijOnderzoekHisVolledig partijOnderzoek;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param partijOnderzoek hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractPartijOnderzoekView(
        final PartijOnderzoekHisVolledig partijOnderzoek,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        this.partijOnderzoek = partijOnderzoek;
        this.formeelPeilmoment = formeelPeilmoment;
        this.materieelPeilmoment = materieelPeilmoment;

    }

    /**
     * Retourneert formeel peilmoment voor deze view.
     *
     * @return Formeel peilmoment voor deze view.
     */
    protected final DatumTijdAttribuut getFormeelPeilmoment() {
        return formeelPeilmoment;
    }

    /**
     * Retourneert materieel peilmoment voor deze view.
     *
     * @return Materieel peilmoment voor deze view.
     */
    protected final DatumAttribuut getMaterieelPeilmoment() {
        return materieelPeilmoment;
    }

    /**
     * Functie die aangeeft of er actuele gegevens zijn in deze view.
     *
     * @return true indien actuele gegevens aanwezig, anders false
     */
    public boolean heeftActueleGegevens() {
        return this.getStandaard() != null;
    }

    /**
     * Retourneert ID van Partij \ Onderzoek.
     *
     * @return ID.
     */
    public final Integer getID() {
        return partijOnderzoek.getID();
    }

    /**
     * Retourneert Partij van Partij \ Onderzoek.
     *
     * @return Partij.
     */
    public final PartijAttribuut getPartij() {
        return partijOnderzoek.getPartij();
    }

    /**
     * Retourneert Onderzoek van Partij \ Onderzoek.
     *
     * @return Onderzoek.
     */
    public final OnderzoekView getOnderzoek() {
        if (partijOnderzoek.getOnderzoek() != null) {
            return new OnderzoekView(partijOnderzoek.getOnderzoek(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPartijOnderzoekModel getStandaard() {
        return partijOnderzoek.getPartijOnderzoekHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PARTIJENINONDERZOEK;
    }

}
