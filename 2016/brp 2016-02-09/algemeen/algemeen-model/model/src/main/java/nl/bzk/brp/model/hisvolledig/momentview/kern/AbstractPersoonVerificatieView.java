/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonVerificatieBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;

/**
 * View klasse voor Persoon \ Verificatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractPersoonVerificatieView implements ModelMoment, PersoonVerificatieBasis, ElementIdentificeerbaar {

    private final PersoonVerificatieHisVolledig persoonVerificatie;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonVerificatie hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractPersoonVerificatieView(
        final PersoonVerificatieHisVolledig persoonVerificatie,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        this.persoonVerificatie = persoonVerificatie;
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
     * Retourneert ID van Persoon \ Verificatie.
     *
     * @return ID.
     */
    public final Long getID() {
        return persoonVerificatie.getID();
    }

    /**
     * Retourneert Geverifieerde van Persoon \ Verificatie.
     *
     * @return Geverifieerde.
     */
    public final PersoonView getGeverifieerde() {
        if (persoonVerificatie.getGeverifieerde() != null) {
            return new PersoonView(persoonVerificatie.getGeverifieerde(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
    }

    /**
     * Retourneert Partij van Persoon \ Verificatie.
     *
     * @return Partij.
     */
    public final PartijAttribuut getPartij() {
        return persoonVerificatie.getPartij();
    }

    /**
     * Retourneert Soort van Persoon \ Verificatie.
     *
     * @return Soort.
     */
    public final NaamEnumeratiewaardeAttribuut getSoort() {
        return persoonVerificatie.getSoort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonVerificatieModel getStandaard() {
        return persoonVerificatie.getPersoonVerificatieHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_VERIFICATIE;
    }

}
