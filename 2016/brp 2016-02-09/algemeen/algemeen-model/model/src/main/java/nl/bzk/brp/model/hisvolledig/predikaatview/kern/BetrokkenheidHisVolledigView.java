/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Betrokkenheid.
 */
public abstract class BetrokkenheidHisVolledigView extends AbstractBetrokkenheidHisVolledigView implements BetrokkenheidHisVolledig,
    ElementIdentificeerbaar
{

    private RelatieHisVolledigView relatieHisVolledigView;
    private PersoonHisVolledigView persoonHisVolledigView;
    private boolean                magLeveren;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param betrokkenheidHisVolledig betrokkenheid
     * @param predikaat                predikaat
     */
    public BetrokkenheidHisVolledigView(final BetrokkenheidHisVolledig betrokkenheidHisVolledig,
        final Predicate predikaat)
    {
        super(betrokkenheidHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param betrokkenheidHisVolledig betrokkenheid
     * @param predikaat                predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                                 peilmomentVoorAltijdTonenGroepen
     */
    public BetrokkenheidHisVolledigView(final BetrokkenheidHisVolledig betrokkenheidHisVolledig,
        final Predicate predikaat, final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(betrokkenheidHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    @Override
    public RelatieHisVolledig getRelatie() {
        if (relatieHisVolledigView == null) {
            relatieHisVolledigView = (RelatieHisVolledigView) super.getRelatie();
            relatieHisVolledigView.setPersoonHisVolledig(getPersoon());
        }
        return relatieHisVolledigView;
    }

    /**
     * Geeft de relatie terug als deze geleverd mag worden. Dit wordt in de JIBX bindings gebruikt.
     *
     * @return relatie voor leveren als deze geleverd mag worden.
     */
    public RelatieHisVolledig getRelatieVoorLeveren() {
        if (relatieHisVolledigView.isMagLeveren()) {
            return relatieHisVolledigView;
        } else {
            return null;
        }
    }

    @Override
    public PersoonHisVolledig getPersoon() {
        if (persoonHisVolledigView == null) {
            persoonHisVolledigView = (PersoonHisVolledigView) super.getPersoon();
        }
        return persoonHisVolledigView;
    }

    /**
     * Geeft de objectsleutel.
     * @return de objectsleutel
     */
    public String getObjectSleutel() {
        return betrokkenheid.getID().toString();
    }

    public boolean isMagLeveren() {
        return magLeveren;
    }

    public void setMagLeveren(final boolean magLeveren) {
        this.magLeveren = magLeveren;
    }

    @Override
    public Verwerkingssoort getVerwerkingssoort() {
        return betrokkenheid.getVerwerkingssoort();
    }

    @Override
    public void setVerwerkingssoort(final Verwerkingssoort verwerkingssoort) {
        betrokkenheid.setVerwerkingssoort(verwerkingssoort);
    }
}
