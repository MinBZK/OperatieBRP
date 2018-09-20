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
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;

/**
 * View klasse voor Persoon \ Geslachtsnaamcomponent.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractPersoonGeslachtsnaamcomponentView implements ModelMoment, PersoonGeslachtsnaamcomponentBasis, ElementIdentificeerbaar {

    private final PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponent;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonGeslachtsnaamcomponent hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractPersoonGeslachtsnaamcomponentView(
        final PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponent,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        this.persoonGeslachtsnaamcomponent = persoonGeslachtsnaamcomponent;
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
     * Retourneert ID van Persoon \ Geslachtsnaamcomponent.
     *
     * @return ID.
     */
    public final Integer getID() {
        return persoonGeslachtsnaamcomponent.getID();
    }

    /**
     * Retourneert Persoon van Persoon \ Geslachtsnaamcomponent.
     *
     * @return Persoon.
     */
    public final PersoonView getPersoon() {
        if (persoonGeslachtsnaamcomponent.getPersoon() != null) {
            return new PersoonView(persoonGeslachtsnaamcomponent.getPersoon(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
    }

    /**
     * Retourneert Volgnummer van Persoon \ Geslachtsnaamcomponent.
     *
     * @return Volgnummer.
     */
    public final VolgnummerAttribuut getVolgnummer() {
        return persoonGeslachtsnaamcomponent.getVolgnummer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonGeslachtsnaamcomponentModel getStandaard() {
        return persoonGeslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorie()
                                            .getHistorieRecord(getMaterieelPeilmoment(), getFormeelPeilmoment());
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT;
    }

}
