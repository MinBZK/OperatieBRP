/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.regels;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import nl.bzk.brp.business.regels.AbstractBedrijfsregelManager;
import nl.bzk.brp.business.regels.AutorisatieBedrijfsregel;
import nl.bzk.brp.business.regels.Bedrijfsregel;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.dataaccess.repository.RegelRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.attribuuttype.brm.RegelCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import org.apache.commons.configuration.ConfigurationException;


/**
 * Implementatie van de regel manager. Kan voor 6 verschillende contexten de uit te voeren regels retourneren. Namelijk de regels die voor- en na uitvoer
 * van een bericht van toepassing zijn, de regels die voor- en na uitvoer van een actie van toepassing zijn en de regels die voor en na verwerking van
 * toepassing zijn.
 */
public class AfnemerindicatiesBedrijfsregelManagerImpl extends AbstractBedrijfsregelManager implements AfnemerindicatiesBedrijfsregelManager {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final String propertiesBestandRegelsVoorVerwerking;

    @Inject
    private RegelRepository regelRepository;

    /**
     * Mapping tussen SoortBericht gecombineerd met SoortActie en de regels die vóór de verwerking uitgevoerd dienen te worden.
     */
    private final Map<Enum, List<AutorisatieBedrijfsregel>> regelsVoorVerwerking = new HashMap<>();

    /**
     * Constructor.
     *
     * @param propertiesBestandRegelsVoorVerwerking
     *         the properties bestand regels voor verwerking
     */
    public AfnemerindicatiesBedrijfsregelManagerImpl(final String propertiesBestandRegelsVoorVerwerking) {
        this.propertiesBestandRegelsVoorVerwerking = propertiesBestandRegelsVoorVerwerking;
    }

    /**
     * Initialisatie methode die, op basis van de gezette namen voor configuratie bestanden, alle regel mappings instantieert en vult met de properties uit
     * de configuratie bestanden.
     *
     * @throws ConfigurationException indien er een fout optreedt bij het lezen van de configuratie bestanden.
     */
    @PostConstruct
    public final void init() throws ConfigurationException {
        initialiseerRegelsEnum(propertiesBestandRegelsVoorVerwerking, regelsVoorVerwerking,
            EffectAfnemerindicaties.class);

        LOGGER.info("Totaal van {} regels toegevoegd aan de regelmanager.", berekenTotaalAantalRegels());
    }

    /**
     * Geeft een lijst van uit te voeren bedrijfsregels voor een gegeven effect afnemerindicatie (plaatsen/verwijderen).
     *
     * @param effectAfnemerindicaties Het effect afnemerindicaties.
     * @return De lijst van bedrijfsregels.
     */
    public final List<? extends Bedrijfsregel> getUitTeVoerenRegelsVoorVerwerking(@NotNull final EffectAfnemerindicaties effectAfnemerindicaties) {
        return Collections.unmodifiableList(dezeOfLegeLijst(regelsVoorVerwerking.get(effectAfnemerindicaties)));
    }

    /**
     * Berekent het totaal aantal regels dat aan de regel manager is toegevoegd; dus alle verschillende regels.
     *
     * @return het totaal aantal regels dat aan de regel manager is toegevoegd.
     */
    private int berekenTotaalAantalRegels() {
        int totaal = 0;
        totaal += berekenTotaalAantalRegelsVoorRegelSoort(regelsVoorVerwerking);
        return totaal;
    }

    /**
     * Retourneert het totaal aantal regels uit de verschillende lijsten per key van een {@link java.util.Map}.
     *
     * @param regelMapping de regel map, waar per soort/key een lijst van regels in is gedefinieerd.
     * @return het totaal aantal regels uit de verschillende lijsten per key van een {@link java.util.Map}.
     */
    private int berekenTotaalAantalRegelsVoorRegelSoort(final Map<?, ? extends List<?>> regelMapping) {
        int totaal = 0;
        for (final Map.Entry<?, ? extends List<?>> entry : regelMapping.entrySet()) {
            totaal += entry.getValue().size();
        }
        return totaal;
    }

    @Override
    public final RegelParameters getRegelParametersVoorRegel(final RegelInterface bedrijfsregel) {
        return regelRepository
            .getRegelParametersVoorRegel(new RegelCodeAttribuut(bedrijfsregel.getRegel().getCode()));
    }

    @Override
    public final RegelParameters getRegelParametersVoorRegel(final Regel regel) {
        return regelRepository
            .getRegelParametersVoorRegel(new RegelCodeAttribuut(regel.getCode()));
    }
}
