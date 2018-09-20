/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import nl.bzk.brp.dataaccess.repository.RegelRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.attribuuttype.brm.RegelCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bevraging.SoortBevraging;
import org.apache.commons.configuration.ConfigurationException;

/**
 * Implementatie van de regel manager. Kan voor 4 verschillende contexten de uit te voeren regels retourneren. Namelijk de regels die voor- en na uitvoer
 * van een bericht van toepassing zijn. En regels die voor- en na uitvoer van een actie van toepassing zijn.
 */

public class BedrijfsregelManagerImpl extends AbstractBedrijfsregelManager implements BevragingBedrijfsregelManager {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final String propertiesBestandRegelsVoorBericht;
    private final String propertiesBestandRegelsVoorVerwerking;
    private final String propertiesBestandRegelsNaVerwerking;

    @Inject
    private RegelRepository regelRepository;

    /**
     * Mapping tussen soortBericht en de regels die vóór uitvoer van het bericht uitgevoerd dienen te worden.
     */
    private final Map<Enum, List<RegelInterface>> regelsVoorBerichtPerSoortbericht = new HashMap<>();

    /**
     * Mapping tussen SoortBericht gecombineerd met SoortActie en de regels die vóór de verwerking uitgevoerd dienen te worden.
     */
    private final Map<Enum, List<RegelInterface>> regelsVoorVerwerking = new HashMap<>();

    /**
     * Mapping tussen SoortBericht gecombineerd met SoortActie en de regels die ná de verwerking uitgevoerd dienen te worden.
     */
    private final Map<Enum, List<RegelInterface>> regelsNaVerwerking = new HashMap<>();

    /**
     * Constructor.
     *
     * @param propertiesBestandRegelsVoorBericht
     *         the properties bestand regels voor bericht
     * @param propertiesBestandRegelsVoorVerwerking
     *         the properties bestand regels voor verwerking
     * @param propertiesBestandRegelsNaVerwerking
     *         the properties bestand regels na verwerking
     */
    public BedrijfsregelManagerImpl(final String propertiesBestandRegelsVoorBericht,
        final String propertiesBestandRegelsVoorVerwerking,
        final String propertiesBestandRegelsNaVerwerking)
    {
        this.propertiesBestandRegelsVoorBericht = propertiesBestandRegelsVoorBericht;
        this.propertiesBestandRegelsVoorVerwerking = propertiesBestandRegelsVoorVerwerking;
        this.propertiesBestandRegelsNaVerwerking = propertiesBestandRegelsNaVerwerking;
    }

    /**
     * Initialisatie methode die, op basis van de gezette namen voor configuratie bestanden, alle regel mappings instantieert en vult met de properties uit
     * de configuratie bestanden.
     *
     * @throws ConfigurationException indien er een fout optreedt bij het lezen van de configuratie bestanden.
     */
    @PostConstruct
    public final void init() throws ConfigurationException {
        LOGGER.info("Start van regels toevoegen aan de regelmanager");

        initialiseerRegelsEnum(propertiesBestandRegelsVoorBericht, regelsVoorBerichtPerSoortbericht,
            SoortBericht.class);
        initialiseerRegelsEnum(propertiesBestandRegelsVoorVerwerking, regelsVoorVerwerking, SoortBericht.class);
        initialiseerRegelsEnum(propertiesBestandRegelsNaVerwerking, regelsNaVerwerking, SoortBericht.class);

        LOGGER.info("Totaal regels toegevoegd aan de regelmanager.");
    }

    /**
     * Bepaal aan de hand van het soort bevraging het soort bericht.
     *
     * @param propertyNaam enum waarde van de soort bevraging
     * @return lijst met soorten bericht
     */
    protected List<SoortBericht> bepaalSoortenBericht(final String propertyNaam) {
        // De property naam is de naam van een enum instantie van het soort bevraging.
        // Daaraan kunnen we vervolgens de betreffende soorten bericht vragen.
        return Arrays.asList(SoortBevraging.valueOf(propertyNaam).getSoortenBericht());
    }

    /**
     * Geef de regels die uitgevoerd moeten worden voor het bericht wordt verwerkt.
     *
     * @param soortBericht type bericht waarvoor de regels moeten gelden
     * @return lijst met regels
     */
    public List<RegelInterface> getUitTeVoerenRegelsVoorBericht(
        @NotNull final SoortBericht soortBericht)
    {
        return Collections.unmodifiableList(dezeOfLegeLijst(regelsVoorBerichtPerSoortbericht.get(soortBericht)));
    }

    /**
     * Geef de regels uit te voeren voor verwerking.
     *
     * @param soortBericht type bericht waarvoor de regels moeten gelden
     * @return lijst met regels
     */
    public List<RegelInterface> getUitTeVoerenRegelsVoorVerwerking(
        @NotNull final SoortBericht soortBericht)
    {
        return Collections.unmodifiableList(dezeOfLegeLijst(regelsVoorVerwerking.get(soortBericht)));
    }

    /**
     * Geef de regels uit te voeren na de verwerking.
     *
     * @param soortBericht type bericht waarvoor de regels moeten gelden
     * @return lijst met regels
     */
    public List<RegelInterface> getUitTeVoerenRegelsNaVerwerking(
        @NotNull final SoortBericht soortBericht)
    {
        return Collections.unmodifiableList(dezeOfLegeLijst(regelsNaVerwerking.get(soortBericht)));
    }

    /**
     * Geef de parameters voor een regel (via de regel instantie).
     *
     * @param regel het regel object
     * @return regel parameters
     */
    @Override
    public final RegelParameters getRegelParametersVoorRegel(final RegelInterface regel) {
        return regelRepository.getRegelParametersVoorRegel(new RegelCodeAttribuut(regel.getRegel().getCode()));
    }

    /**
     * Geef de parameters voor een regel.
     *
     * @param regel het regel nummer (via de Regel enumeratie)
     * @return de regel parameters
     */
    @Override
    public final RegelParameters getRegelParametersVoorRegel(final Regel regel) {
        return regelRepository.getRegelParametersVoorRegel(new RegelCodeAttribuut(regel.getCode()));
    }

}
