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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;

/**
 * View klasse voor Persoon \ Nationaliteit.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractPersoonNationaliteitView implements ModelMoment, PersoonNationaliteitBasis, ElementIdentificeerbaar {

    private final PersoonNationaliteitHisVolledig persoonNationaliteit;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonNationaliteit hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractPersoonNationaliteitView(
        final PersoonNationaliteitHisVolledig persoonNationaliteit,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        this.persoonNationaliteit = persoonNationaliteit;
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
     * Retourneert ID van Persoon \ Nationaliteit.
     *
     * @return ID.
     */
    public final Integer getID() {
        return persoonNationaliteit.getID();
    }

    /**
     * Retourneert Persoon van Persoon \ Nationaliteit.
     *
     * @return Persoon.
     */
    public final PersoonView getPersoon() {
        if (persoonNationaliteit.getPersoon() != null) {
            return new PersoonView(persoonNationaliteit.getPersoon(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
    }

    /**
     * Retourneert Nationaliteit van Persoon \ Nationaliteit.
     *
     * @return Nationaliteit.
     */
    public final NationaliteitAttribuut getNationaliteit() {
        return persoonNationaliteit.getNationaliteit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonNationaliteitModel getStandaard() {
        return persoonNationaliteit.getPersoonNationaliteitHistorie().getHistorieRecord(getMaterieelPeilmoment(), getFormeelPeilmoment());
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_NATIONALITEIT;
    }

}
