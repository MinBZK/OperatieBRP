/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.OnderzoekBasis;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;

/**
 * View klasse voor Onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractOnderzoekView implements ModelMoment, OnderzoekBasis, ElementIdentificeerbaar {

    private final OnderzoekHisVolledig onderzoek;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param onderzoek hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractOnderzoekView(final OnderzoekHisVolledig onderzoek, final DatumTijdAttribuut formeelPeilmoment, final DatumAttribuut materieelPeilmoment)
    {
        this.onderzoek = onderzoek;
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
        return this.getStandaard() != null || this.getAfgeleidAdministratief() != null;
    }

    /**
     * Retourneert ID van Onderzoek.
     *
     * @return ID.
     */
    public final Integer getID() {
        return onderzoek.getID();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisOnderzoekModel getStandaard() {
        return onderzoek.getOnderzoekHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisOnderzoekAfgeleidAdministratiefModel getAfgeleidAdministratief() {
        return onderzoek.getOnderzoekAfgeleidAdministratiefHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<GegevenInOnderzoekView> getGegevensInOnderzoek() {
        final Set<GegevenInOnderzoekView> result = new HashSet<>();
        for (GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig : onderzoek.getGegevensInOnderzoek()) {
            final GegevenInOnderzoekView view =
                    new GegevenInOnderzoekView(gegevenInOnderzoekHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            result.add(view);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<PersoonOnderzoekView> getPersonenInOnderzoek() {
        final Set<PersoonOnderzoekView> result = new HashSet<>();
        for (PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : onderzoek.getPersonenInOnderzoek()) {
            final PersoonOnderzoekView view = new PersoonOnderzoekView(persoonOnderzoekHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<PartijOnderzoekView> getPartijenInOnderzoek() {
        final Set<PartijOnderzoekView> result = new HashSet<>();
        for (PartijOnderzoekHisVolledig partijOnderzoekHisVolledig : onderzoek.getPartijenInOnderzoek()) {
            final PartijOnderzoekView view = new PartijOnderzoekView(partijOnderzoekHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ONDERZOEK;
    }

}
