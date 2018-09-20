/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.regels;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.business.regels.AbstractBedrijfsregelManager;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.dataaccess.repository.RegelRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.attribuuttype.brm.RegelCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;

import org.apache.commons.configuration.ConfigurationException;

/**
 * Implementatie van de bedrijfsregelmanager. Kan voor 6 verschillende contexten de uit te voeren regels retourneren. Namelijk de regels die voor- en na
 * uitvoer van een bericht van toepassing zijn, de regels die voor- en na uitvoer van een actie van toepassing zijn en de regels die voor en na verwerking
 * van toepassing zijn.
 */
public class BedrijfsregelManagerImpl extends AbstractBedrijfsregelManager implements SynchronisatieBedrijfsregelManager {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String FOUTMELDING_NIET_ONDERSTEUND = "In Synchronisatie worden actie regels niet ondersteund.";

    private final String propertiesBestandRegelsVoorBericht;
    private final String propertiesBestandRegelsVoorVerwerking;
    private final String propertiesBestandRegelsNaVerwerking;

    @Inject
    private RegelRepository regelRepository;

    /**
     * Mapping tussen SoortBericht gecombineerd met de regels die vóór bericht verwerking uitgevoerd dienen te worden.
     */
    private Map<Enum, List<RegelInterface>> regelsVoorBericht = new HashMap<>();

    /**
     * Mapping tussen SoortBericht gecombineerd met SoortActie en de regels die vóór de verwerking uitgevoerd dienen te worden.
     */
    private Map<Enum, List<RegelInterface>> regelsVoorVerwerking = new HashMap<>();

    /**
     * Mapping tussen SoortBericht gecombineerd met SoortActie en de regels die ná de verwerking uitgevoerd dienen te worden.
     */
    private Map<Enum, List<RegelInterface>> regelsNaVerwerking = new HashMap<>();

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

        initialiseerRegelsEnum(propertiesBestandRegelsVoorBericht, regelsVoorBericht, SoortBericht.class);
        initialiseerRegelsEnum(propertiesBestandRegelsVoorVerwerking, regelsVoorVerwerking, SoortBericht.class);
        initialiseerRegelsEnum(propertiesBestandRegelsNaVerwerking, regelsNaVerwerking, SoortBericht.class);

        LOGGER.info("Totaal van {} regels toegevoegd aan de regelmanager.", berekenTotaalAantalRegels());
    }

    /**
     * Geeft de uit te voeren regels voor bericht.
     *
     * @param soortBericht soort bericht
     * @return uit te voeren regels voor bericht
     */
    public final List<RegelInterface> getUitTeVoerenRegelsVoorBericht(
        @NotNull final SoortBericht soortBericht)
    {
        return Collections.unmodifiableList(dezeOfLegeLijst(regelsVoorBericht.get(soortBericht)));
    }

    /**
     * Geeft de uit te voeren regels na bericht.
     *
     * @param soortBericht soort bericht
     * @return uit te voeren regels na bericht
     */
    public final List<RegelInterface> getUitTeVoerenRegelsNaBericht(
        @NotNull final SoortBericht soortBericht)
    {
        throw new UnsupportedOperationException("In Synchronisatie worden bericht regels niet ondersteund.");
    }

    /**
     * Geeft de uit te voeren regels voor actie.
     *
     * @param soortAdministratieveHandeling soort administratieve handeling
     * @param soortActie                    soort actie
     * @return uit te voeren regels voor actie
     */
    public final List<RegelInterface> getUitTeVoerenRegelsVoorActie(
        @NotNull final SoortAdministratieveHandeling soortAdministratieveHandeling,
        @NotNull final SoortActie soortActie)
    {
        throw new UnsupportedOperationException(FOUTMELDING_NIET_ONDERSTEUND);
    }

    /**
     * Geeft de uit te voeren regels na actie.
     *
     * @param soortAdministratieveHandeling soort administratieve handeling
     * @param soortActie                    soort actie
     * @return uit te voeren regels na actie
     */
    public final List<RegelInterface> getUitTeVoerenRegelsNaActie(
        @NotNull final SoortAdministratieveHandeling soortAdministratieveHandeling,
        @NotNull final SoortActie soortActie)
    {
        throw new UnsupportedOperationException(FOUTMELDING_NIET_ONDERSTEUND);
    }

    /**
     * Geeft de uit te voeren regels voor verwerking.
     *
     * @param soortBericht soort bericht
     * @return uit te voeren regels voor verwerking
     */
    public final List<RegelInterface> getUitTeVoerenRegelsVoorVerwerking(
        @NotNull final SoortBericht soortBericht)
    {
        return Collections.unmodifiableList(dezeOfLegeLijst(regelsVoorVerwerking.get(soortBericht)));
    }

    /**
     * Geeft de uit te voeren regels na verwerking.
     *
     * @param soortBericht soort bericht
     * @return uit te voeren regels na verwerking
     */
    public final List<RegelInterface> getUitTeVoerenRegelsNaVerwerking(@NotNull final SoortBericht soortBericht) {
        return Collections.unmodifiableList(dezeOfLegeLijst(regelsNaVerwerking.get(soortBericht)));
    }

    /**
     * Geeft de regel parameters voor regel.
     *
     * @param regel regel
     * @return regel parameters voor regel
     */
    public final RegelParameters getRegelParametersVoorRegel(final RegelInterface regel) {
        return regelRepository.getRegelParametersVoorRegel(new RegelCodeAttribuut(regel.getRegel().getCode()));
    }

    /**
     * Geeft de regel parameters voor regel.
     *
     * @param regel regel
     * @return regel parameters voor regel
     */
    public final RegelParameters getRegelParametersVoorRegel(final Regel regel) {
        return regelRepository.getRegelParametersVoorRegel(new RegelCodeAttribuut(regel.getCode()));
    }

    /**
     * Berekent het totaal aantal regels dat aan de regel manager is toegevoegd; dus alle verschillende regels.
     *
     * @return het totaal aantal regels dat aan de regel manager is toegevoegd.
     */
    private int berekenTotaalAantalRegels() {
        int totaal = 0;
        totaal += berekenTotaalAantalRegelsVoorRegelSoort(regelsVoorBericht);
        totaal += berekenTotaalAantalRegelsVoorRegelSoort(regelsVoorVerwerking);
        totaal += berekenTotaalAantalRegelsVoorRegelSoort(regelsNaVerwerking);
        return totaal;
    }

    /**
     * Retourneert het totaal aantal regels uit de verschillende lijsten per key van een {@link Map}.
     *
     * @param regelMapping de regel map, waar per soort/key een lijst van regels in is gedefinieerd.
     * @return het totaal aantal regels uit de verschillende lijsten per key van een {@link Map}.
     */
    private int berekenTotaalAantalRegelsVoorRegelSoort(final Map<?, ? extends List<?>> regelMapping) {
        int totaal = 0;
        for (Map.Entry<?, ? extends List<?>> entry : regelMapping.entrySet()) {
            totaal += entry.getValue().size();
        }
        return totaal;
    }
}
