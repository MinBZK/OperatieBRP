/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.service.AbstractBerichtVerwerker;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;


/**
 * Standaard implementatie van de BerichtVerwerker.
 */
@Service
public class OnderhoudAfnemerindicatiesBerichtVerwerker
    extends AbstractBerichtVerwerker<RegistreerAfnemerindicatieBericht, OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Valideert of een bericht de verplichte objecten bevat voor het type van de berichten. Als dat niet zo is, wordt er een melding toegevoegd aan het
     * resultaat en wordt het resultaat gemarkeerd als foutief.
     *
     * @param bericht          het bericht dat gevalideerd dient te worden.
     * @param berichtResultaat het bericht resultaat waar eventuele meldingen aan toegevoegd dienen te worden en dat op 'foutief' gezet wordt als het
     *                         bericht niet valide is.
     */
    @Override
    protected void valideerBerichtOpVerplichteObjecten(final RegistreerAfnemerindicatieBericht bericht,
        final OnderhoudAfnemerindicatiesResultaat berichtResultaat)
    {
        final BerichtStandaardGroepBericht berichtStandaard = bericht.getStandaard();
        if (bericht.getStuurgegevens() == null) {
            LOGGER.error("Geen stuurgegevens gevonden.");
            voegFoutmeldingToe(berichtResultaat);
        } else if (berichtStandaard.getAdministratieveHandeling() == null) {
            LOGGER.error("Geen administratieve handeling gevonden.");
            voegFoutmeldingToe(berichtResultaat);
        } else if (CollectionUtils.isEmpty(berichtStandaard.getAdministratieveHandeling().getActies())) {
            voegFoutmeldingToe(berichtResultaat);
            LOGGER.error("Geen acties gevonden.");
        } else {
            // Elke actie in een bericht dient minimaal een root object te hebben
            for (Actie actie : berichtStandaard.getAdministratieveHandeling().getActies()) {
                if (actie.getRootObject() == null) {
                    LOGGER.error("Geen valide actie gevonden.");
                    voegFoutmeldingToe(berichtResultaat);
                }
            }
        }
    }

    /**
     * Voegt een foutmeldign toe aan het resultaat.
     *
     * @param berichtResultaat Het resultaat.
     */
    private void voegFoutmeldingToe(final OnderhoudAfnemerindicatiesResultaat berichtResultaat) {
        berichtResultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0002, null, ""));
    }

}
