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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.ActieBronHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HisVolledigComparatorFactory;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Actie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractActieHisVolledigView extends AbstractHisVolledigPredikaatView implements ActieHisVolledigBasis, ElementIdentificeerbaar {

    protected final ActieHisVolledig actie;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param actieHisVolledig actie
     * @param predikaat predikaat
     */
    public AbstractActieHisVolledigView(final ActieHisVolledig actieHisVolledig, final Predicate predikaat) {
        this(actieHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param actieHisVolledig actie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractActieHisVolledigView(
        final ActieHisVolledig actieHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.actie = actieHisVolledig;
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
        return actie.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SoortActieAttribuut getSoort() {
        return actie.getSoort();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratieveHandelingHisVolledig getAdministratieveHandeling() {
        if (actie.getAdministratieveHandeling() != null) {
            return new AdministratieveHandelingHisVolledigView(actie.getAdministratieveHandeling(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PartijAttribuut getPartij() {
        return actie.getPartij();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return actie.getDatumAanvangGeldigheid();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return actie.getDatumEindeGeldigheid();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumTijdAttribuut getTijdstipRegistratie() {
        return actie.getTijdstipRegistratie();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumEvtDeelsOnbekendAttribuut getDatumOntlening() {
        return actie.getDatumOntlening();

    }

    /**
     * Retourneert of de predikaat historie records kent voor Bronnen of niet.
     *
     * @return true indien de predikaat historie records kent
     */
    public boolean heeftBronnen() {
        return true;
    }

    /**
     * Retourneert of de predikaat historie records kent die geleverd mogen worden voor Bronnen of niet.
     *
     * @return true indien de predikaat historie records kent die geleverd mogen worden
     */
    public boolean heeftBronnenVoorLeveren() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends ActieBronHisVolledig> getBronnen() {
        final SortedSet<ActieBronHisVolledig> resultaat = new TreeSet<ActieBronHisVolledig>(HisVolledigComparatorFactory.getComparatorVoorActieBron());
        for (ActieBronHisVolledig actieBron : actie.getBronnen()) {
            final ActieBronHisVolledigView predikaatView = new ActieBronHisVolledigView(actieBron, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
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
        return ElementEnum.ACTIE;
    }

}
