/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.migblok;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.ProcessInstantieIDAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.migblok.RedenBlokkeringAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.migblok.BlokkeringHisVolledig;
import nl.bzk.brp.model.hisvolledig.migblok.BlokkeringHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Blokkering.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractBlokkeringHisVolledigView extends AbstractHisVolledigPredikaatView implements BlokkeringHisVolledigBasis {

    protected final BlokkeringHisVolledig blokkering;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param blokkeringHisVolledig blokkering
     * @param predikaat predikaat
     */
    public AbstractBlokkeringHisVolledigView(final BlokkeringHisVolledig blokkeringHisVolledig, final Predicate predikaat) {
        this(blokkeringHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param blokkeringHisVolledig blokkering
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractBlokkeringHisVolledigView(
        final BlokkeringHisVolledig blokkeringHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.blokkering = blokkeringHisVolledig;
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
    public final Integer getID() {
        return blokkering.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final AdministratienummerAttribuut getAdministratienummer() {
        return blokkering.getAdministratienummer();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final RedenBlokkeringAttribuut getRedenBlokkering() {
        return blokkering.getRedenBlokkering();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ProcessInstantieIDAttribuut getProcessInstantieID() {
        return blokkering.getProcessInstantieID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LO3GemeenteCodeAttribuut getLO3GemeenteVestiging() {
        return blokkering.getLO3GemeenteVestiging();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LO3GemeenteCodeAttribuut getLO3GemeenteRegistratie() {
        return blokkering.getLO3GemeenteRegistratie();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DatumTijdAttribuut getTijdstipRegistratie() {
        return blokkering.getTijdstipRegistratie();

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

}
