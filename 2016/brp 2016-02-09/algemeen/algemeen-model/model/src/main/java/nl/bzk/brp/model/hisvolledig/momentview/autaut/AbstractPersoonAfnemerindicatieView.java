/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieBasis;
import nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel;

/**
 * View klasse voor Persoon \ Afnemerindicatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractPersoonAfnemerindicatieView implements ModelMoment, PersoonAfnemerindicatieBasis {

    private final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatie;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonAfnemerindicatie hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractPersoonAfnemerindicatieView(
        final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatie,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        this.persoonAfnemerindicatie = persoonAfnemerindicatie;
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
     * Retourneert ID van Persoon \ Afnemerindicatie.
     *
     * @return ID.
     */
    public final Long getID() {
        return persoonAfnemerindicatie.getID();
    }

    /**
     * Retourneert Persoon van Persoon \ Afnemerindicatie.
     *
     * @return Persoon.
     */
    public final PersoonView getPersoon() {
        if (persoonAfnemerindicatie.getPersoon() != null) {
            return new PersoonView(persoonAfnemerindicatie.getPersoon(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
    }

    /**
     * Retourneert Afnemer van Persoon \ Afnemerindicatie.
     *
     * @return Afnemer.
     */
    public final PartijAttribuut getAfnemer() {
        return persoonAfnemerindicatie.getAfnemer();
    }

    /**
     * Retourneert Leveringsautorisatie van Persoon \ Afnemerindicatie.
     *
     * @return Leveringsautorisatie.
     */
    public final LeveringsautorisatieAttribuut getLeveringsautorisatie() {
        return persoonAfnemerindicatie.getLeveringsautorisatie();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonAfnemerindicatieModel getStandaard() {
        return persoonAfnemerindicatie.getPersoonAfnemerindicatieHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

}
