/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonVerstrekkingsbeperkingIdentiteitGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVerstrekkingsbeperkingBasis;

/**
 * View klasse voor Persoon \ Verstrekkingsbeperking.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractPersoonVerstrekkingsbeperkingView implements ModelMoment, PersoonVerstrekkingsbeperkingBasis, ElementIdentificeerbaar {

    private final PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperking;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonVerstrekkingsbeperking hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractPersoonVerstrekkingsbeperkingView(
        final PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperking,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        this.persoonVerstrekkingsbeperking = persoonVerstrekkingsbeperking;
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
        return this.getIdentiteit() != null;
    }

    /**
     * Retourneert ID van Persoon \ Verstrekkingsbeperking.
     *
     * @return ID.
     */
    public final Integer getID() {
        return persoonVerstrekkingsbeperking.getID();
    }

    /**
     * Retourneert Persoon van Persoon \ Verstrekkingsbeperking.
     *
     * @return Persoon.
     */
    public final PersoonView getPersoon() {
        if (persoonVerstrekkingsbeperking.getPersoon() != null) {
            return new PersoonView(persoonVerstrekkingsbeperking.getPersoon(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
    }

    /**
     * Retourneert Partij van Persoon \ Verstrekkingsbeperking.
     *
     * @return Partij.
     */
    public final PartijAttribuut getPartij() {
        return persoonVerstrekkingsbeperking.getPartij();
    }

    /**
     * Retourneert Omschrijving derde van Persoon \ Verstrekkingsbeperking.
     *
     * @return Omschrijving derde.
     */
    public final OmschrijvingEnumeratiewaardeAttribuut getOmschrijvingDerde() {
        return persoonVerstrekkingsbeperking.getOmschrijvingDerde();
    }

    /**
     * Retourneert Gemeente verordening van Persoon \ Verstrekkingsbeperking.
     *
     * @return Gemeente verordening.
     */
    public final PartijAttribuut getGemeenteVerordening() {
        return persoonVerstrekkingsbeperking.getGemeenteVerordening();
    }

    /**
     * Retourneert Identiteit van Persoon \ Verstrekkingsbeperking
     *
     * @return Retourneert Identiteit van Persoon \ Verstrekkingsbeperking
     */
    public final HisPersoonVerstrekkingsbeperkingIdentiteitGroep getIdentiteit() {
        return persoonVerstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_VERSTREKKINGSBEPERKING;
    }

}
