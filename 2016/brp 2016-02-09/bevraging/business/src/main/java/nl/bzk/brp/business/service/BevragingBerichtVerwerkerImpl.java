/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContext;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.service.AbstractBerichtVerwerker;


/**
 * Standaard implementatie van de BerichtVerwerker.
 */
public final class BevragingBerichtVerwerkerImpl extends
        AbstractBerichtVerwerker<BerichtBericht, BevragingBerichtContext, BevragingResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Valideert of een bericht de verplichte objecten bevat voor het type van de berichten. Als dat niet zo is, wordt
     * er een melding toegevoegd aan het
     * resultaat en wordt het resultaat gemarkeerd als foutief.
     *
     * @param bericht het bericht dat gevalideerd dient te worden.
     * @param berichtResultaat het bericht resultaat waar eventuele meldingen aan toegevoegd dienen te worden en dat op
     *            'foutief' gezet wordt als het
     *            bericht niet valide is.
     */
    @Override
    protected void valideerBerichtOpVerplichteObjecten(final BerichtBericht bericht,
            final BevragingResultaat berichtResultaat)
    {
        if (bericht.getStuurgegevens() == null) {
            LOGGER.error("Geen stuurgegevens gevonden.");
            voegFoutmeldingToe(berichtResultaat);
        } else if (!(bericht instanceof BevragingsBericht)) {
            LOGGER.error("Geen bevragingbericht gevonden.");
            voegFoutmeldingToe(berichtResultaat);
        } else if (bericht.getZoekcriteriaPersoon() == null) {
            LOGGER.error("Geen zoekcriteria persoon gevonden.");
            voegFoutmeldingToe(berichtResultaat);
        }
    }

    /**
     * Voegt een foutmeldign toe aan het resultaat.
     *
     * @param berichtResultaat Het resultaat.
     */
    private void voegFoutmeldingToe(final BevragingResultaat berichtResultaat) {
        berichtResultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0002, null, ""));
    }

}
