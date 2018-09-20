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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;

/**
 * View klasse voor Persoon \ Indicatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractPersoonIndicatieView implements ModelMoment, PersoonIndicatieBasis, ElementIdentificeerbaar {

    private final PersoonIndicatieHisVolledig persoonIndicatie;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonIndicatie hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractPersoonIndicatieView(
        final PersoonIndicatieHisVolledig persoonIndicatie,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        this.persoonIndicatie = persoonIndicatie;
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
     * Retourneert ID van Persoon \ Indicatie.
     *
     * @return ID.
     */
    public final Integer getID() {
        return persoonIndicatie.getID();
    }

    /**
     * Retourneert Persoon van Persoon \ Indicatie.
     *
     * @return Persoon.
     */
    public final PersoonView getPersoon() {
        if (persoonIndicatie.getPersoon() != null) {
            return new PersoonView(persoonIndicatie.getPersoon(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
    }

    /**
     * Retourneert Soort van Persoon \ Indicatie.
     *
     * @return Soort.
     */
    public final SoortIndicatieAttribuut getSoort() {
        return persoonIndicatie.getSoort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonIndicatieModel getStandaard() {
        return (HisPersoonIndicatieModel) persoonIndicatie.getPersoonIndicatieHistorie().getHistorieRecord(
            getMaterieelPeilmoment(),
            getFormeelPeilmoment());
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_INDICATIE;
    }

}
