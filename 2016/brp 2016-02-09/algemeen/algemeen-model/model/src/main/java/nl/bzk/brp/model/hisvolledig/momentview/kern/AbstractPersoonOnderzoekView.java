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
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonOnderzoekBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOnderzoekModel;

/**
 * View klasse voor Persoon \ Onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractPersoonOnderzoekView implements ModelMoment, PersoonOnderzoekBasis, ElementIdentificeerbaar {

    private final PersoonOnderzoekHisVolledig persoonOnderzoek;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonOnderzoek hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractPersoonOnderzoekView(
        final PersoonOnderzoekHisVolledig persoonOnderzoek,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        this.persoonOnderzoek = persoonOnderzoek;
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
     * Retourneert ID van Persoon \ Onderzoek.
     *
     * @return ID.
     */
    public final Integer getID() {
        return persoonOnderzoek.getID();
    }

    /**
     * Retourneert Persoon van Persoon \ Onderzoek.
     *
     * @return Persoon.
     */
    public final PersoonView getPersoon() {
        if (persoonOnderzoek.getPersoon() != null) {
            return new PersoonView(persoonOnderzoek.getPersoon(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
    }

    /**
     * Retourneert Onderzoek van Persoon \ Onderzoek.
     *
     * @return Onderzoek.
     */
    public final OnderzoekView getOnderzoek() {
        if (persoonOnderzoek.getOnderzoek() != null) {
            return new OnderzoekView(persoonOnderzoek.getOnderzoek(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonOnderzoekModel getStandaard() {
        return persoonOnderzoek.getPersoonOnderzoekHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_ONDERZOEK;
    }

}
