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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocumentBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;

/**
 * View klasse voor Persoon \ Reisdocument.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractPersoonReisdocumentView implements ModelMoment, PersoonReisdocumentBasis, ElementIdentificeerbaar {

    private final PersoonReisdocumentHisVolledig persoonReisdocument;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonReisdocument hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractPersoonReisdocumentView(
        final PersoonReisdocumentHisVolledig persoonReisdocument,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        this.persoonReisdocument = persoonReisdocument;
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
     * Retourneert ID van Persoon \ Reisdocument.
     *
     * @return ID.
     */
    public final Integer getID() {
        return persoonReisdocument.getID();
    }

    /**
     * Retourneert Persoon van Persoon \ Reisdocument.
     *
     * @return Persoon.
     */
    public final PersoonView getPersoon() {
        if (persoonReisdocument.getPersoon() != null) {
            return new PersoonView(persoonReisdocument.getPersoon(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
    }

    /**
     * Retourneert Soort van Persoon \ Reisdocument.
     *
     * @return Soort.
     */
    public final SoortNederlandsReisdocumentAttribuut getSoort() {
        return persoonReisdocument.getSoort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonReisdocumentModel getStandaard() {
        return persoonReisdocument.getPersoonReisdocumentHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_REISDOCUMENT;
    }

}
