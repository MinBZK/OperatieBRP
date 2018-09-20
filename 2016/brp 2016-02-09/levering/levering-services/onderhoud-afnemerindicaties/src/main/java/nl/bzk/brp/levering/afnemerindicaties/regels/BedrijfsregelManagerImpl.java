/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.regels;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import nl.bzk.brp.business.regels.AbstractBedrijfsregelManager;
import nl.bzk.brp.business.regels.BedrijfsregelManager;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.dataaccess.repository.RegelRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.attribuuttype.brm.RegelCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;

import org.apache.commons.configuration.ConfigurationException;


/**
 * Implementatie van de bedrijfsregelmanager. Kan voor 6 verschillende contexten de uit te voeren regels retourneren.
 * Namelijk de regels die voor- en na uitvoer van een bericht van toepassing zijn, de regels die voor- en na uitvoer
 * van een actie van toepassing zijn en de regels die voor en na verwerking van toepassing zijn.
 */
public class BedrijfsregelManagerImpl extends AbstractBedrijfsregelManager implements BedrijfsregelManager {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private RegelRepository     regelRepository;

    /**
     * Initialisatie methode die, op basis van de gezette namen voor configuratie bestanden, alle regel mappings
     * instantieert en vult met de properties uit de configuratie bestanden.
     *
     * @throws ConfigurationException indien er een fout optreedt bij het lezen van de configuratie bestanden.
     */
    @PostConstruct
    public final void init() throws ConfigurationException {
        LOGGER.info("Geen regels toegevoegd aan onderhoud afnemerindicates, dit gebeurt in de bibliotheek. {}");
    }

    @Override
    public final RegelParameters getRegelParametersVoorRegel(final RegelInterface regelInterface) {
        return regelRepository.getRegelParametersVoorRegel(new RegelCodeAttribuut(regelInterface.getRegel().getCode()));
    }

    @Override
    public final RegelParameters getRegelParametersVoorRegel(final Regel regel) {
        return regelRepository.getRegelParametersVoorRegel(new RegelCodeAttribuut(regel.getCode()));
    }
}
