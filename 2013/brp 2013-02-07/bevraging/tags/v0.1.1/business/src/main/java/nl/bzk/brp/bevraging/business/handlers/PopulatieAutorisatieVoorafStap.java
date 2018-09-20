/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;


/**
 * De stap in de uitvoering van een bericht waarin (vooraf) de populatie autorisatie wordt gecontroleerd. Hierbij wordt
 * er, indien er een bericht wordt verwerkt waarin expliciet een bsn of bsns bevraagd worden, reeds bepaald of de
 * afnemer wel gerechtigd is de opgevraagde personen in te zien. Dit daar uitsluitend personen aan de afnemer mogen
 * worden geleverd of door de afnemer mogen worden opgevraagd die binnen de op dat moment geldige populatie vallen van
 * het abonnement waarvoor opgevraagd of geleverd wordt.
 */
public class PopulatieAutorisatieVoorafStap extends AbstractBerichtVerwerkingsStap {

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean voerVerwerkingsStapUitVoorBericht(final BrpBerichtCommand bericht) {
        return DOORGAAN_MET_VERWERKING;
    }

}
