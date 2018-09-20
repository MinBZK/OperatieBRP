/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.ist;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.ist.StapelHisVolledig;
import nl.bzk.brp.model.hisvolledig.ist.StapelRelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.ist.StapelRelatieHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.kern.ErkenningOngeborenVruchtHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.NaamskeuzeOngeborenVruchtHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.ErkenningOngeborenVruchtHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.FamilierechtelijkeBetrekkingHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.GeregistreerdPartnerschapHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.HuwelijkHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.NaamskeuzeOngeborenVruchtHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Stapel \ Relatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractStapelRelatieHisVolledigView extends AbstractHisVolledigPredikaatView implements StapelRelatieHisVolledigBasis {

    protected final StapelRelatieHisVolledig stapelRelatie;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param stapelRelatieHisVolledig stapelRelatie
     * @param predikaat predikaat
     */
    public AbstractStapelRelatieHisVolledigView(final StapelRelatieHisVolledig stapelRelatieHisVolledig, final Predicate predikaat) {
        this(stapelRelatieHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param stapelRelatieHisVolledig stapelRelatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractStapelRelatieHisVolledigView(
        final StapelRelatieHisVolledig stapelRelatieHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.stapelRelatie = stapelRelatieHisVolledig;
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
        return stapelRelatie.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StapelHisVolledig getStapel() {
        if (stapelRelatie.getStapel() != null) {
            return new StapelHisVolledigView(stapelRelatie.getStapel(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RelatieHisVolledig getRelatie() {
        final RelatieHisVolledig relatie = stapelRelatie.getRelatie();
        final RelatieHisVolledigView predikaatView;
        if (relatie instanceof ErkenningOngeborenVruchtHisVolledig) {
            predikaatView =
                    new ErkenningOngeborenVruchtHisVolledigView(
                        (ErkenningOngeborenVruchtHisVolledig) relatie,
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else if (relatie instanceof FamilierechtelijkeBetrekkingHisVolledig) {
            predikaatView =
                    new FamilierechtelijkeBetrekkingHisVolledigView(
                        (FamilierechtelijkeBetrekkingHisVolledig) relatie,
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else if (relatie instanceof GeregistreerdPartnerschapHisVolledig) {
            predikaatView =
                    new GeregistreerdPartnerschapHisVolledigView(
                        (GeregistreerdPartnerschapHisVolledig) relatie,
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else if (relatie instanceof HuwelijkHisVolledig) {
            predikaatView = new HuwelijkHisVolledigView((HuwelijkHisVolledig) relatie, getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        } else if (relatie instanceof NaamskeuzeOngeborenVruchtHisVolledig) {
            predikaatView =
                    new NaamskeuzeOngeborenVruchtHisVolledigView(
                        (NaamskeuzeOngeborenVruchtHisVolledig) relatie,
                        getPredikaat(),
                        getPeilmomentVoorAltijdTonenGroepen());
        } else {
            predikaatView = null;
        }
        return predikaatView;

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
