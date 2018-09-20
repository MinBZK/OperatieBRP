/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;


/**
 * Interface voor Huwelijk / Geregistreerd partnerschap.
 */
public interface HuwelijkGeregistreerdPartnerschapHisVolledig extends HuwelijkGeregistreerdPartnerschapHisVolledigBasis
{
    /**
     * Geeft de (andere) partner van een partner betrokkenheid in een relatie.
     *
     * @param persoon de persoon waarvan de wederhelft in deze relatie wordt gezocht
     * @return de andere partner
     */
    public PartnerHisVolledig geefPartnerVan(final PersoonHisVolledig persoon);

    /**
     * Geeft de (andere) partner van een partner betrokkenheid in een relatie.
     *
     * @param partner de partner betrokkenheid waarvan de wederhelft in deze relatie wordt gezocht
     * @return de andere partner
     */
    public PartnerHisVolledig geefPartnerVan(final PartnerHisVolledig partner);
}
