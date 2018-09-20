/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Huwelijk / Geregistreerd partnerschap.
 */
public abstract class HuwelijkGeregistreerdPartnerschapHisVolledigView extends
    AbstractHuwelijkGeregistreerdPartnerschapHisVolledigView implements
    HuwelijkGeregistreerdPartnerschapHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param huwelijkGeregistreerdPartnerschapHisVolledig
     *                  relatie
     * @param predikaat predikaat
     */
    public HuwelijkGeregistreerdPartnerschapHisVolledigView(
        final HuwelijkGeregistreerdPartnerschapHisVolledig huwelijkGeregistreerdPartnerschapHisVolledig,
        final Predicate predikaat)
    {
        super(huwelijkGeregistreerdPartnerschapHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param huwelijkGeregistreerdPartnerschapHisVolledig
     *                  relatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                  peilmomentVoorAltijdTonenGroepen
     */
    public HuwelijkGeregistreerdPartnerschapHisVolledigView(
        final HuwelijkGeregistreerdPartnerschapHisVolledig huwelijkGeregistreerdPartnerschapHisVolledig,
        final Predicate predikaat, final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(huwelijkGeregistreerdPartnerschapHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    @Override
    public PartnerHisVolledigView geefPartnerVan(final PersoonHisVolledig persoon) {
        for (BetrokkenheidHisVolledig betrokkeneHuwelijk : getBetrokkenheden()) {
            if (betrokkeneHuwelijk.getPersoon().getID() == null && betrokkeneHuwelijk.getPersoon() != persoon
                || (betrokkeneHuwelijk.getPersoon().getID() != null && !betrokkeneHuwelijk.getPersoon().getID().equals(persoon.getID())))
            {
                // Na het vinden van de partner kan er direct gestopt worden.
                return (PartnerHisVolledigView) betrokkeneHuwelijk;
            }
        }

        return null;

    }

    @Override
    public PartnerHisVolledigView geefPartnerVan(final PartnerHisVolledig partner) {
        return geefPartnerVan(partner.getPersoon());
    }
}
