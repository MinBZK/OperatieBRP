/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.kern.HisVolledigComparatorFactory;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Administratieve handeling.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractAdministratieveHandelingHisVolledigView extends AbstractHisVolledigPredikaatView implements
        AdministratieveHandelingHisVolledigBasis, ElementIdentificeerbaar
{

    protected final AdministratieveHandelingHisVolledig administratieveHandeling;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param administratieveHandelingHisVolledig administratieveHandeling
     * @param predikaat predikaat
     */
    public AbstractAdministratieveHandelingHisVolledigView(
        final AdministratieveHandelingHisVolledig administratieveHandelingHisVolledig,
        final Predicate predikaat)
    {
        this(administratieveHandelingHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param administratieveHandelingHisVolledig administratieveHandeling
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractAdministratieveHandelingHisVolledigView(
        final AdministratieveHandelingHisVolledig administratieveHandelingHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.administratieveHandeling = administratieveHandelingHisVolledig;
        this.peilmomentVoorAltijdTonenGroepen = peilmomentVoorAltijdTonenGroepen;

    }

    /**
     * Geeft de peilmomentVoorAltijdTonenGroepen terug.
     *
     * @return de peilmomentVoorAltijdTonenGroepen
     */
    public final DatumTijdAttribuut getPeilmomentVoorAltijdTonenGroepen() {
        return peilmomentVoorAltijdTonenGroepen;
    }

    /**
     * Zet de waarde van het veld peilmomentVoorAltijdTonenGroepen.
     *
     * @param peilmomentVoorAltijdTonenGroepen
     */
    public final void setPeilmomentVoorAltijdTonenGroepen(final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen) {
        this.peilmomentVoorAltijdTonenGroepen = peilmomentVoorAltijdTonenGroepen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Long getID() {
        return administratieveHandeling.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SoortAdministratieveHandelingAttribuut getSoort() {
        return administratieveHandeling.getSoort();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PartijAttribuut getPartij() {
        return administratieveHandeling.getPartij();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final OntleningstoelichtingAttribuut getToelichtingOntlening() {
        return administratieveHandeling.getToelichtingOntlening();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumTijdAttribuut getTijdstipRegistratie() {
        return administratieveHandeling.getTijdstipRegistratie();

    }

    /**
     * Retourneert of de predikaat historie records kent voor Gedeblokkeerde meldingen of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public boolean heeftGedeblokkeerdeMeldingen() {
        return true;
    }

    /**
     * Retourneert of de predikaat historie records kent voor Acties of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public boolean heeftActies() {
        return true;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Gedeblokkeerde meldingen of
     * niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public boolean heeftGedeblokkeerdeMeldingenVoorLeveren() {
        return true;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Acties of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public boolean heeftActiesVoorLeveren() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig> getGedeblokkeerdeMeldingen() {
        final SortedSet<AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig> resultaat =
                new TreeSet<AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig>(
                    HisVolledigComparatorFactory.getComparatorVoorAdministratieveHandelingGedeblokkeerdeMelding());
        for (AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig administratieveHandelingGedeblokkeerdeMelding : administratieveHandeling.getGedeblokkeerdeMeldingen())
        {
            final AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigView predikaatView =
                    new AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigView(
                        administratieveHandelingGedeblokkeerdeMelding,
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends ActieHisVolledig> getActies() {
        final SortedSet<ActieHisVolledig> resultaat = new TreeSet<ActieHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorActie());
        for (ActieHisVolledig actie : administratieveHandeling.getActies()) {
            final ActieHisVolledigView predikaatView = new ActieHisVolledigView(actie, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
            resultaat.add(predikaatView);
        }
        return resultaat;

    }

    /**
     *
     *
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        return resultaat;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ADMINISTRATIEVEHANDELING;
    }

}
