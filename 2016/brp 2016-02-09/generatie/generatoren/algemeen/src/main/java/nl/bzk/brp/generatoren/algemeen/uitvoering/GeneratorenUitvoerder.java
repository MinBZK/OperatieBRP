/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.uitvoering;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.generatoren.algemeen.basis.Generator;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.rapportage.RapportageUitvoerder;
import nl.bzk.brp.generatoren.algemeen.rapportage.RapportageUitvoerderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Centrale klasse voor het genereren van code/bestanden. De generatie gebeurt via de methode {@link #genereer}, die
 * op basis van een property bestand generatoren aanroept en zo nieuwe artefacten genereert.
 */
public class GeneratorenUitvoerder {

    /** Bestandpad patroon voor de standaard generatoren configuratie bestanden. */
    public static final  String GENERATOR_CONFIG_STANDAARD_NAAM = "classpath*:/config/*-generatoren-config.xml";
    private static final String SPRING_CONTEXT_BESTAND          = "generatoren-context.xml";
    private static final Logger LOGGER                          = LoggerFactory.getLogger(GeneratorenUitvoerder.class);

    private final ApplicationContext applicatieContext;

    /** Standaard constructor die o.a. de Spring context laadt. */
    public GeneratorenUitvoerder() {
        applicatieContext = laadSpringContext();
    }

    /**
     * Standaard ingang voor het executeren van deze Java klasse; deze instantieert de klasse en roept daarna de
     * generatie aan op deze instantie. Indien er een of meerdere argumenten worden meegegeven (en dus als parameter
     * aanwezig zijn) zal elk argument gezien worden als een pad naar een configuratie resource (eventueel op
     * classpath) en zal de configuratie van die resource worden toegevoegd en zal daarvoor de juiste generator
     * worden aangeroepen.
     *
     * @param args argumenten voor het uitvoeren van de Java klasse; verwacht wordt een bestandsnaam van de
     * generatoren configuratie of een patroon dat leidt tot een of meerdere configuratie bestanden (eventueel op
     * het classpath).
     */
    public static void main(final String[] args) {
        final List<String> configuratiePatronen = getConfiguratieResourcePatronen(args);
        final Map<String, GeneratorConfiguratie> configuratieMap = bouwConfiguratieMap(configuratiePatronen);

        final GeneratorenUitvoerder uitvoerder = new GeneratorenUitvoerder();
        uitvoerder.genereer(configuratieMap);
    }

    /**
     * Retourneert de patronen en/of lokaties van de configuratie resources. Standaard is dit de opgegeven array
     * van Strings, maar indien die leeg is (of <code>null</code>), zal er op het classpad gezocht worden naar
     * een aantal standaard configuratie resources.
     *
     * @param args eventueel opgegeven paden/namen van configuratie resources.
     * @return een lijst van configuratie resource namen en/of patronen.
     */
    private static List<String> getConfiguratieResourcePatronen(final String[] args) {
        final List<String> configuratiePatronen;
        if (args != null && args.length > 0) {
            configuratiePatronen = Arrays.asList(args);
        } else {
            configuratiePatronen = Collections.singletonList(GENERATOR_CONFIG_STANDAARD_NAAM);
        }
        return configuratiePatronen;
    }

    /**
     * Bouwt een Map waarin een generator (naam) wordt gemapt naar zijn configuratie. Deze Map wordt opgebouwd op
     * basis van de configuratie zoals gevonden in de resources zoals gevonden voor de opgegeven lijst van
     * patronen.
     *
     * @param configuratiePatronen lijst van patronen/namen die verwijzen naar configuratie resources.
     * @return een map met generatoren en hun configuratie.
     */
    private static Map<String, GeneratorConfiguratie> bouwConfiguratieMap(final List<String> configuratiePatronen) {
        final Map<String, GeneratorConfiguratie> configuratieMap = new HashMap<>();

        for (String configuratiePatroon : configuratiePatronen) {
            configuratieMap.putAll(bouwConfiguratieMap(configuratiePatroon));
        }
        return configuratieMap;
    }

    /**
     * Bouwt een Map waarin een generator (naam) wordt gemapt naar zijn configuratie. Deze Map wordt opgebouwd op
     * basis van de configuratie zoals gevonden in de resources zoals gevonden voor de opgegeven naam/patroon voor
     * configuratie resource(s).
     *
     * @param configuratiePatroon patroon/naam die verwijst naar een of meerdere configuratie resources.
     * @return een map met generatoren en hun configuratie.
     */
    public static Map<String, GeneratorConfiguratie> bouwConfiguratieMap(final String configuratiePatroon) {
        final Map<String, GeneratorConfiguratie> configuratieMap = new HashMap<>();
        final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        try {
            final Resource[] resources = resourcePatternResolver.getResources(configuratiePatroon);
            for (final Resource resource : resources) {
                final URL url = resource.getURL();
                configuratieMap.putAll(GeneratorConfiguratieFactory.leesGeneratorenConfiguratie(url));
            }
        } catch (IOException e) {
            LOGGER.error(String.format("Configuratie niet kunnen lezen/bepalen voor '%s'.", configuratiePatroon), e);
        }
        return configuratieMap;
    }



    /**
     * Laadt de (standaard) Spring context en retourneert deze.
     *
     * @return de (ingeladen) Spring context (of <code>null</code> indien het inladen mislukt).
     */
    private ApplicationContext laadSpringContext() {
        LOGGER.debug("Start laden Spring context.");

        ApplicationContext context;
        try {
            context = new ClassPathXmlApplicationContext(SPRING_CONTEXT_BESTAND);
        } catch (BeansException e) {
            context = null;
            LOGGER.error("Fout bij inlezen van de Spring configuratie.", e);
        }
        return context;
    }

    /**
     * Methode voor het genereren van de verschillende artefacten en broncode bestanden. Deze methode roept voor alle
     * (in de configuratie geconfigureerde) generatoren de generatie methode aan, waarbij de bij de generator behorende
     * configuratie wordt meegegeven.
     *
     * @param generatorConfiguratieMap Map met daarin alle generatoren (de naam) en hun bijbehorende configuratie.
     */
    public final void genereer(final Map<String, GeneratorConfiguratie> generatorConfiguratieMap)
    {
        if (applicatieContext == null || generatorConfiguratieMap == null) {
            LOGGER.error("Benodigde configuratie niet aanwezig.");
        } else if (generatorConfiguratieMap.isEmpty()) {
            LOGGER.warn("Geen generatoren gevonden in de configuratie; geen generatie uitgevoerd.");
        } else {
            final File generatieXmlRapportageBestand = initialiseerGeneratieRapportageBestand();
            //Initialiseer de rapporteur.
            final RapportageUitvoerder rapportageUitvoerder =
                    applicatieContext.getBean("rapportageUitvoerder", RapportageUitvoerderImpl.class);
            rapportageUitvoerder.initialiseerRapportageBestand(generatieXmlRapportageBestand);

            //Voer de generatie uit.
            for (Map.Entry<String, GeneratorConfiguratie> generatorConfiguratie : generatorConfiguratieMap.entrySet()) {
                genereerVoorGenerator(generatorConfiguratie.getKey(), generatorConfiguratie.getValue());
            }

            if (rapportageUitvoerder.heeftRapportageInhoud()) {
                // Als er daadwerkelijk inhoud in de file is, schrijf dan de rapportage weg.
                rapportageUitvoerder.schrijfRapportageWeg(generatieXmlRapportageBestand);
            } else {
                // Anders verwijderen we het bestand, want dan valt er kennelijk niets te melden.
                final boolean verwijderingSuccesvol = generatieXmlRapportageBestand.delete();
                if (!verwijderingSuccesvol) {
                    LOGGER.error("Fout bij verwijderen van rapportage bestand.");
                }
            }
        }
    }

    /**
     * Initialiseert het bestand waarin gerapporteerd wordt over de gegenereerde artefacten.
     * Als het bestand niet bestaat, dan wordt dit aangemaakt.
     *
     * @return bestand voor rapportage.
     */
    private File initialiseerGeneratieRapportageBestand() {
        final String filePad = "src/main/resources/generatie-info.xml";
        final File file = new File(filePad);
        try {
            final boolean fileIsNew = file.createNewFile();
            if (fileIsNew) {
                LOGGER.warn("Generatie rapportage bestand 'generatie-info.xml' is niet gevonden, "
                                    + "het bestand is hier aangemaakt: '" + filePad + "'");
            }
        } catch (IOException e) {
            LOGGER.error("Fout bij het aanmaken van generatie rapportage bestand.", e);
            throw new IllegalStateException(e);
        }
        return file;
    }

    /**
     * Haalt de generator met de opgegeven naam op en roept daarvan de generatie aan met de opgegeven configuratie.
     *
     * @param naam de naam van de generator.
     * @param configuratie de configuratie die geldt voor die generator.
     */
    private void genereerVoorGenerator(final String naam, final GeneratorConfiguratie configuratie) {
        try {
            final Generator generator = applicatieContext.getBean(naam, Generator.class);
            LOGGER.info(String.format("Generator '%s' uitvoeren met configuratie (%s)", naam, configuratie));
            generator.genereer(configuratie);
        } catch (NoSuchBeanDefinitionException e) {
            LOGGER.error(String.format("Generator '%s' niet gevonden; generator wordt overgeslagen.", naam));
        } catch (BeanNotOfRequiredTypeException e) {
            LOGGER.error(String.format("Generator '%s' is geen generator (onverwacht Type); "
                + "generator wordt overgeslagen.", naam));
        } catch (BeansException e) {
            LOGGER.error(String.format("Generator '%s' kon niet worden geinstantieerd; generator wordt overgeslagen",
                naam), e);
        }
    }

}
