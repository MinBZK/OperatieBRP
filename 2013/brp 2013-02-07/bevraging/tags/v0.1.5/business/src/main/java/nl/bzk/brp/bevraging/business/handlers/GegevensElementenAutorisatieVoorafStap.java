/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;


/**
 * De stap in de uitvoering van een bericht waarin (vooraf) wordt gecontroleerd of de afnemer (op basis van
 * betreffende abonnement) gerechtigd is de opgevraagde gegevens in te zien. Indien mogelijk wordt in deze stap
 * reeds een filter gelegd op de op te vragen gegevenselementen, zodat uitsluitend die gegevenselementen van de
 * persoon worden opgevraagd en aan de afnemer worden geleverd waarvoor een corresponderend en geldig
 * gegevenselement aanwezig is voor het abonnement waarvoor opgevraagd of geleverd wordt.
 */
public class GegevensElementenAutorisatieVoorafStap extends AbstractBerichtVerwerkingsStap {

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean voerVerwerkingsStapUitVoorBericht(final BrpBerichtCommand bericht) {
        return DOORGAAN_MET_VERWERKING;
    }

}
