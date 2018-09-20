/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaamBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;

/**
 * View klasse voor Persoon \ Voornaam.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractPersoonVoornaamView implements ModelMoment, PersoonVoornaamBasis, ElementIdentificeerbaar {

    private final PersoonVoornaamHisVolledig persoonVoornaam;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonVoornaam hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractPersoonVoornaamView(
        final PersoonVoornaamHisVolledig persoonVoornaam,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        this.persoonVoornaam = persoonVoornaam;
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
     * Retourneert ID van Persoon \ Voornaam.
     *
     * @return ID.
     */
    public final Integer getID() {
        return persoonVoornaam.getID();
    }

    /**
     * Retourneert Persoon van Persoon \ Voornaam.
     *
     * @return Persoon.
     */
    public final PersoonView getPersoon() {
        if (persoonVoornaam.getPersoon() != null) {
            return new PersoonView(persoonVoornaam.getPersoon(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
    }

    /**
     * Retourneert Volgnummer van Persoon \ Voornaam.
     *
     * @return Volgnummer.
     */
    public final VolgnummerAttribuut getVolgnummer() {
        return persoonVoornaam.getVolgnummer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonVoornaamModel getStandaard() {
        return persoonVoornaam.getPersoonVoornaamHistorie().getHistorieRecord(getMaterieelPeilmoment(), getFormeelPeilmoment());
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_VOORNAAM;
    }

}
