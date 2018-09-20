/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.mojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.uitvoering.GeneratorenUitvoerder;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

// CHECKSTYLE:OFF
// Checkstyle checkt op geldigheid tags in javadoc en klaagt daarom over de onderstaande preformatted XML.
// De leesbaarheid is echter veel hoger zo, vandaar deze exclude van checkstyle hier.
/**
 * Maven Mojo voor het aanroepen van de generatoren middels Maven.
 * <p>
 * Deze Mojo zoekt standaard naar de configuraties voor de verschillende generatoren, welke mogelijk beschikbaar zijn
 * via het classpath (middels '{@value GeneratorenUitvoerder#GENERATOR_CONFIG_STANDAARD_NAAM}') en zal alle gevonden
 * generatoren conform bijbehorende configuraties uitvoeren. Eventueel kan middels configuratie bepaald worden welke
 * generatoren van de gevonden generatoren wel en welke niet uit de standaard configuraties gebruikt dienen te worden,
 * waarbij dan wel de standaard configuratie van de generatoren gebruikt wordt. Zo kan middels 'includes'
 * expliciet worden bepaald welke generatoren er gebruikt dienen te worden of kan middels 'excludes' alle gevonden
 * generatoren in de standaard configuratie(s) worden gebruikt exclusief de gene die zijn opgegeven.
 * <br/><br/>
 * Voorbeeld:
 * <pre>
 * {@code
 *
 * <includes>
 *     <include>attribuutTypenGenerator</include>
 *     <include>berichtModelGenerator</include>
 * </includes>
 * }
 * </pre>
 * of
 * <pre>
 * {@code
 *
 * <excludes>
 *     <exclude>attribuutTypenGenerator</exclude>
 *     <exclude>berichtModelGenerator</exclude>
 * </excludes>
 * }
 * </pre>
 * </p>
 * <p>
 * Indien de standaard configuratie niet gebruikt dient te worden, kunnen generatoren ook expliciet worden opgenomen
 * en geconfigureerd. Dit kan door de volgende configuratie op te nemen:
 * </p>
 * <pre>
 * {@code
 *
 * <generatorenConfiguraties>
 *     <configuratie implementation="nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie">
 *         <naam>attribuutTypenGenerator</naam>
 *         <pad>src/main/java</pad>
 *         <overschrijf>true</overschrijf>
 *         <generationGapPatroon>true</generationGapPatroon>
 *         <generationGapPatroonOverschrijf>false</generationGapPatroonOverschrijf>
 *         <behoudUserTypeAanpassingen>false</behoudUserTypeAanpassingen>
 *     </configuratie>
 *     <configuratie implementation="nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie">
 *         <naam>berichtModelGenerator</naam>
 *         <pad>src/main/java</pad>
 *         <overschrijf>true</overschrijf>
 *         <generationGapPatroon>true</generationGapPatroon>
 *         <generationGapPatroonOverschrijf>false</generationGapPatroonOverschrijf>
 *         <behoudUserTypeAanpassingen>false</behoudUserTypeAanpassingen>
 *     </configuratie>
 * </generatorenConfiguraties>
 * }
 * </pre>
 * </p>
 *
 * @goal genereer
 * @phase generate-sources
 * @requiresDependencyResolution compile
 * @see GeneratorenUitvoerder
 */
// CHECKSTYLE:ON
public class GeneratorenMojo extends AbstractMojo {


    /** @parameter */
    private List<GeneratorConfiguratie> generatorenConfiguraties;
    /** @parameter */
    private List<String>                includes;
    /** @parameter */
    private List<String>                excludes;

    /** {@inheritDoc} */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (isConfiguratieValide()) {
            final Map<String, GeneratorConfiguratie> generatorenConfiguratiesMap;
            if (generatorenConfiguraties != null) {
                generatorenConfiguratiesMap = bouwGeneratorenConfiguratiesMapVoorLijst();
            } else {
                generatorenConfiguratiesMap = bouwStandaardGeneratorenConfiguratiesMap();
            }

            final GeneratorenUitvoerder uitvoerder = new GeneratorenUitvoerder();
            uitvoerder.genereer(generatorenConfiguratiesMap);
        } else {
            throw new MojoExecutionException("Generatie niet uitgevoerd vanwege incorrect/invalide configuratie.");
        }
    }

    /**
     * Checkt of de configuratie valide is; er kunnen niet en 'generatorenConfiguraties' en includes/excludes worden
     * opgegeven.
     *
     * @return of de configuratie valide is of niet.
     */
    private boolean isConfiguratieValide() {
        final boolean configuratieValide;
        if (generatorenConfiguraties != null && (includes != null || excludes != null)) {
            configuratieValide = false;
            getLog().error("Kan geen 'generatorenConfiguraties' opgeven en tevens 'includes' en/of 'excludes'.");
        } else {
            configuratieValide = true;
        }
        return configuratieValide;
    }

    /**
     * Bouwt een Map van generatoren en hun configuraties op door op het classpath te zoeken naar de standaard
     * generatoren configuraties en deze in te lezen. Deze map wordt verder nog gefilterd op basis van de
     * geconfigureerde lijst van 'includes' en 'excludes' en dan geretourneerd.
     *
     * @return map met generatoren en bijbehorende configuratie.
     */
    private Map<String, GeneratorConfiguratie> bouwStandaardGeneratorenConfiguratiesMap() {
        Map<String, GeneratorConfiguratie> configuraties = GeneratorenUitvoerder.bouwConfiguratieMap(
            GeneratorenUitvoerder.GENERATOR_CONFIG_STANDAARD_NAAM);

        configuraties = filterConfiguratiesVoorIncludes(configuraties);
        configuraties = filterConfiguratiesVoorExcludes(configuraties);

        return configuraties;
    }

    /**
     * Retourneert een map, gebaseerd op opgegeven map, maar dan gefiltert naar de lijst van includes. Dus alleen als
     * een generator uit de opgegeven map in de 'includes' lijst voorkomt, zal deze in de uiteindelijke map terug
     * komen.
     *
     * @param configuraties initiele map van generatoren en bijbehorende configuratie.
     * @return map van generatoren en bijbehorende configuratie.
     */
    private Map<String, GeneratorConfiguratie> filterConfiguratiesVoorIncludes(
        final Map<String, GeneratorConfiguratie> configuraties)
    {
        final Map<String, GeneratorConfiguratie> includeConfiguraties = new HashMap<>();

        if (includes != null) {
            this.getLog().info("De volgende generatoren zijn geinclude: ");
            for (Map.Entry<String, GeneratorConfiguratie> configuratie : configuraties.entrySet()) {
                if (includes.contains(configuratie.getKey())) {
                    includeConfiguraties.put(configuratie.getKey(), configuratie.getValue());
                    this.getLog().info(" - " + configuratie.getKey());
                }
            }
        } else {
            includeConfiguraties.putAll(configuraties);
        }
        return includeConfiguraties;
    }

    /**
     * Retourneert een map, gebaseerd op opgegeven map, maar dan gefiltert met de lijst van excludes. Dus als een
     * generator uit de opgegeven map in de 'excludes' lijst voorkomt, zal deze niet in de uiteindelijke map terug
     * komen. Kortom, alle generatoren die niet in de 'excludes' lijst voorkomen, zullen in de geretourneerde map
     * terug komen.
     *
     * @param configuraties initiele map van generatoren en bijbehorende configuratie.
     * @return map van generatoren en bijbehorende configuratie.
     */
    private Map<String, GeneratorConfiguratie> filterConfiguratiesVoorExcludes(
        final Map<String, GeneratorConfiguratie> configuraties)
    {
        final Map<String, GeneratorConfiguratie> excludeConfiguraties = new HashMap<>();

        excludeConfiguraties.putAll(configuraties);
        if (excludes != null) {
            this.getLog().info("De volgende generatoren zijn geexclude: ");
            for (String generator : excludes) {
                excludeConfiguraties.remove(generator);
                this.getLog().info(" - " + generator);
            }
        }
        return excludeConfiguraties;
    }

    /**
     * Bouwt een Map op voor de lijst van {@link GeneratorConfiguratie} instanties, waarbij de naam van de generator
     * wordt gemapt naar de configuratie.
     *
     * @return een map van generator namen naar generator configuraties.
     * @throws MojoExecutionException indien er geen generatoren configuraties zijn.
     */
    private Map<String, GeneratorConfiguratie> bouwGeneratorenConfiguratiesMapVoorLijst()
        throws MojoExecutionException
    {
        if (generatorenConfiguraties == null) {
            throw new MojoExecutionException("Generatoren configuraties niet aanwezig.");
        } else {
            final Map<String, GeneratorConfiguratie> configuraties = new HashMap<>();
            for (GeneratorConfiguratie configuratie : generatorenConfiguraties) {
                configuraties.put(configuratie.getNaam(), configuratie);
            }

            return configuraties;
        }
    }


    /**
     * Retourneert de generatoren configuratie(s) (indien gezet).
     *
     * @return de generatoren configuratie(s).
     */
    public List<GeneratorConfiguratie> getGeneratorenConfiguraties() {
        return generatorenConfiguraties;
    }

    /**
     * Zet de generatoren configuratie(s).
     *
     * @param generatorenConfiguraties de generatoren configuratie(s).
     */
    public void setGeneratorenConfiguraties(final List<GeneratorConfiguratie> generatorenConfiguraties) {
        this.generatorenConfiguraties = generatorenConfiguraties;
    }

    /**
     * Retourneert lijst van generatoren (uit de standaard configuratie) die moeten worden gebruikt.
     *
     * @return lijst van generatoren (uit de standaard configuratie) die moeten worden gebruikt.
     */
    public List<String> getIncludes() {
        return includes;
    }

    /**
     * Zet welke generatoren (uit de standaard configuratie) moeten worden gebruikt.
     *
     * @param includes lijst van generatoren (uit de standaard configuratie) die moeten worden gebruikt.
     */
    public void setIncludes(final List<String> includes) {
        this.includes = includes;
    }

    /**
     * Retourneert lijst van generatoren (uit de standaard configuratie) die niet moeten worden gebruikt.
     *
     * @return lijst van generatoren (uit de standaard configuratie) die niet moeten worden gebruikt.
     */
    public List<String> getExcludes() {
        return excludes;
    }

    /**
     * Zet welke generatoren (uit de standaard configuratie) niet moeten worden gebruikt.
     *
     * @param excludes lijst van generatoren (uit de standaard configuratie) die niet moeten worden gebruikt.
     */
    public void setExcludes(final List<String> excludes) {
        this.excludes = excludes;
    }
}
