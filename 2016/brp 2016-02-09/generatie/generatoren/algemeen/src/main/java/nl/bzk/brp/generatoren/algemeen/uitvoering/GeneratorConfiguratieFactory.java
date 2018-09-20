/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.uitvoering;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Helper klasse voor het inlezen van generator configuratie(s). */
public final class GeneratorConfiguratieFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorConfiguratieFactory.class);

    private static final String CONFIG_GENERATOR                     = "generator";
    private static final String CONFIG_GENERATOR_NAAM                = "naam";
    private static final String CONFIG_GENERATOR_PAD                 = "doelpad";
    private static final String CONFIG_GENERATOR_OVERSCHRIJF         = "overschrijf";
    private static final String CONFIG_GENERATOR_GEN_GAP_ENABLE      = "generatiegap.enable";
    private static final String CONFIG_GENERATOR_GEN_GAP_OVERSCHRIJF = "generatiegap.overschrijf";
    private static final String CONFIG_GENERATOR_BEHOUD_USER_TYPE_AANPASSINGEN
        = "generatiegap.behoudusertypeaanpassingen";

    /** Standaard private constructor daar utility klasses niet geinstantieerd dienen te worden. */
    private GeneratorConfiguratieFactory() {
        LOGGER.warn("Onverwachte (en officieel niet toegestane) instantiatie van " + this.getClass());
    }

    /**
     * Leest het opgegeven bestand in en retourneert hieruit een {@link Map} van generatoren en hun bijbehorende
     * configuratie.
     *
     * @param configuratieBestand het bestand dat de configuratie van een of meerdere generatoren bevat.
     * @return een Map van generator namen en bijbehorende configuratie.
     */
    public static Map<String, GeneratorConfiguratie> leesGeneratorenConfiguratie(final String configuratieBestand) {
        final HierarchicalConfiguration configuratie = laadConfiguratie(configuratieBestand);
        return bouwGeneratorenMap(configuratie);
    }

    /**
     * Leest van de opgegeven URL de XML configuratie in en retourneert hieruit een {@link Map} van generatoren en hun
     * bijbehorende configuratie.
     *
     * @param configuratieUrl de url waar de configuratie van een of meerdere generatoren kan worden uitgelezen.
     * @return een Map van generator namen en bijbehorende configuratie.
     */
    public static Map<String, GeneratorConfiguratie> leesGeneratorenConfiguratie(final URL configuratieUrl) {
        final HierarchicalConfiguration configuratie = laadConfiguratie(configuratieUrl);
        return bouwGeneratorenMap(configuratie);
    }

    /**
     * Laadt de generator configuratie (welke generatoren en hun configuratie) en retourneert deze.
     *
     * @param configuratieBestand het configuratie bestand met daarin de generatoren configuratie.
     * @return de (ingelezen) configuratie (of <code>null</code> indien het inlezen mislukt).
     */
    private static HierarchicalConfiguration laadConfiguratie(final String configuratieBestand) {
        LOGGER.debug("Start laden configuratie bestand.");

        HierarchicalConfiguration configuratie;
        try {
            configuratie = new XMLConfiguration(configuratieBestand);
        } catch (ConfigurationException e) {
            configuratie = null;
            LOGGER.error(
                String.format("Fout bij het inlezen van het configuratie bestand '%s'.", configuratieBestand), e);
        }
        return configuratie;
    }

    /**
     * Laadt de generator configuratie (welke generatoren en hun configuratie) en retourneert deze.
     *
     * @param configuratieUrl de configuratie URL waar de generatoren configuratie kan worden uitgelezen.
     * @return de (ingelezen) configuratie (of <code>null</code> indien het inlezen mislukt).
     */
    private static HierarchicalConfiguration laadConfiguratie(final URL configuratieUrl) {
        LOGGER.debug("Start laden configuratie URL.");

        HierarchicalConfiguration configuratie;
        try {
            configuratie = new XMLConfiguration(configuratieUrl);
        } catch (ConfigurationException e) {
            configuratie = null;
            LOGGER.error(
                String.format("Fout bij het inlezen van de configuratie url '%s'.", configuratieUrl), e);
        }
        return configuratie;
    }

    /**
     * Bouwt een map van generatoren op basis van de ingelezen configuratie. Hierbij is de key de naam van de generator
     * en de value de configuratie waarmee die generator aangeroepen dient te worden.
     *
     * @param configuratie de configuratie zoals gelezen uit een configuratie bestand.
     * @return een Map van generatoren, waarbij de key de naam van de generator is en de value de configuratie
     *         waarmee die generator aangeroepen dient te worden.
     */
    private static Map<String, GeneratorConfiguratie> bouwGeneratorenMap(final HierarchicalConfiguration configuratie) {
        final Map<String, GeneratorConfiguratie> generatorenMap = new HashMap<>();

        final List<HierarchicalConfiguration> generatoren = configuratie.configurationsAt(CONFIG_GENERATOR);
        if (generatoren.isEmpty()) {
            LOGGER.warn("Geen generatoren geconfigureerd.");
        } else {
            LOGGER.info(String.format("Totaal van %d generatoren gevonden (in configuratie).", generatoren.size()));

            for (HierarchicalConfiguration generator : generatoren) {
                final String generatorNaam = generator.getString(CONFIG_GENERATOR_NAAM);
                final GeneratorConfiguratie generatorConfiguratie = bouwGeneratorConfiguratie(generator);

                LOGGER.info(String.format("Generator '%s' toevoegen met configuratie uit bestand (%s)", generatorNaam,
                    generatorConfiguratie));
                generatorenMap.put(generatorNaam, generatorConfiguratie);
            }
        }
        return generatorenMap;
    }

    /**
     * Bouwt een nieuwe {@link GeneratorConfiguratie} instantie op basis van de opgegeven configuratie. Indien een
     * configuratie parameter niet aanwezig is in de configuratie, dan wordt de standaard waarde gebruikt.
     *
     * @param configuratie de configuratie voor een specifieke generator.
     * @return de nieuwe {@link GeneratorConfiguratie} instantie.
     */
    private static GeneratorConfiguratie bouwGeneratorConfiguratie(final HierarchicalConfiguration configuratie) {
        final GeneratorConfiguratie generatorConfiguratie = new GeneratorConfiguratie();
        generatorConfiguratie.setNaam(configuratie.getString(CONFIG_GENERATOR_NAAM, generatorConfiguratie.getNaam()));
        generatorConfiguratie.setPad(configuratie.getString(CONFIG_GENERATOR_PAD, generatorConfiguratie.getPad()));
        generatorConfiguratie.setOverschrijf(
            configuratie.getBoolean(CONFIG_GENERATOR_OVERSCHRIJF, generatorConfiguratie.isOverschrijf()));
        generatorConfiguratie.setGenerationGapPatroon(
            configuratie.getBoolean(CONFIG_GENERATOR_GEN_GAP_ENABLE, generatorConfiguratie.isGenerationGapPatroon()));
        generatorConfiguratie.setGenerationGapPatroonOverschrijf(configuratie
            .getBoolean(CONFIG_GENERATOR_GEN_GAP_OVERSCHRIJF,
                generatorConfiguratie.isGenerationGapPatroonOverschrijf()));
        generatorConfiguratie.setBehoudUserTypeAanpassingen(configuratie
                .getBoolean(CONFIG_GENERATOR_BEHOUD_USER_TYPE_AANPASSINGEN,
                        generatorConfiguratie.isBehoudUserTypeAanpassingen()));
        return generatorConfiguratie;
    }
}
