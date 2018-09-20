/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.service;

import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.synchronisatie.AbstractSynchronisatieBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.service.AbstractBerichtVerwerker;

import org.springframework.stereotype.Service;


/**
 * Standaard implementatie van de BerichtVerwerker.
 */
@Service
public class SynchronisatieBerichtVerwerker extends
        AbstractBerichtVerwerker<AbstractSynchronisatieBericht, SynchronisatieBerichtContext, SynchronisatieResultaat>
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
    protected void valideerBerichtOpVerplichteObjecten(final AbstractSynchronisatieBericht bericht,
            final SynchronisatieResultaat berichtResultaat)
    {
        if (bericht.getStuurgegevens() == null) {
            LOGGER.error("Geen stuurgegevens gevonden.");
            voegFoutmeldingToe(berichtResultaat);
        } else if (bericht instanceof GeefSynchronisatiePersoonBericht && bericht.getZoekcriteriaPersoon() == null) {
            LOGGER.error("Geen zoekcriteria persoon gevonden voor synchroniseer persoon.");
            voegFoutmeldingToe(berichtResultaat);
        } else if (bericht instanceof GeefSynchronisatieStamgegevenBericht
                && (bericht.getParameters() == null || bericht.getParameters().getStamgegeven() == null))
        {
            LOGGER.error("Geen stamgegeven gevonden voor synchroniseer stamgegeven.");
            voegFoutmeldingToe(berichtResultaat);
        }

    }

    /**
     * Voegt een foutmeldign toe aan het resultaat.
     *
     * @param berichtResultaat Het resultaat.
     */
    private void voegFoutmeldingToe(final SynchronisatieResultaat berichtResultaat) {
        berichtResultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0002, null, ""));
    }

}
