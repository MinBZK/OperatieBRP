/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.writer;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.java.model.AbstractJavaType;

/**
 * Abstract super klasse voor de generation gap writers voor interfaces en klassen.
 * Bevat alle gemeenschappelijke functionaliteit.
 *
 * @param <T> Het type (JavaInterface of JavaKlasse)
 */
public abstract class AbstractGenerationGapPatroonJavaWriter<T extends AbstractJavaType> implements JavaWriter<T> {

    /** Package suffix voor types van gegenereerde klassen. **/
    public static final String GENERATIE_TYPE_SUB_PACKAGE = ".basis";

    private GeneratorConfiguratie configuratie;

    /**
     * Constructor die de basis configuratie meekrijgt.
     *
     * @param configuratie de configuratie.
     */
    public AbstractGenerationGapPatroonJavaWriter(final GeneratorConfiguratie configuratie) {
        this.configuratie = configuratie;
    }

    public GeneratorConfiguratie getConfiguratie() {
        return this.configuratie;
    }

    /**
     * Maak een user type van het algemene java type.
     *
     * @param javaType het java type
     * @return het user type
     */
    protected abstract T zetJavaTypeOmNaarUserType(T javaType);

    /**
     * Maak een generatie type van het algemene java type.
     *
     * @param javaType het java type
     * @return het generatie type
     */
    protected abstract T zetJavaTypeOmNaarGeneratieType(T javaType);

    /**
     * Retourneert de naam voor de generatie versie van het Java type (conform 'Generation Gap' patroon).
     *
     * @param javaType het Java type waarvoor de generatie versie naam moet worden afgeleid.
     * @return de van het Java type afgeleide naam voor de generatie versie
     */
    protected abstract String genereerNaamVoorGeneratieType(final T javaType);

    /**
     * Retourneert de naam voor de package van de generatie versie van het Java type (conform 'Generation Gap' patroon).
     *
     * @param javaType het Java type waarvoor de package van de generatie versie moet worden afgeleid.
     * @return de van het Java type afgeleide package naam voor de generatie versie
     */
    protected String genereerPackageNaamVoorGeneratieType(final T javaType) {
        return javaType.getPackagePad() + GENERATIE_TYPE_SUB_PACKAGE;
    }
    /**
     * Genereert voor alle Java types twee bestanden (vanwege 'Generation Gap' patroon). Hierbij bevat een van de
     * twee types de gegenereerde code, waarbij de andere deze eerste 'extends' en zo de mogelijkheid biedt om
     * nog aan te passen, zonder de gegenereerde code te verwijderen.
     *
     * @param javaTypes de Java types waarvoor de Java source bestanden gegenereerd en opgeslagen dienen te worden.
     * @param generator de generator die de opgegeven Java interfaces heeft geinstantieerd/gegenereerd.
     * @return Lijst van weggeschreven java typen.
     */
    @Override
    public List<T> genereerEnSchrijfWeg(final List<T> javaTypes, final AbstractGenerator generator) {
        final List<T> geschrevenBroncodeBestanden = new ArrayList<T>();
        final List<T> userTypes = new ArrayList<T>();
        final List<T> generatieTypes = new ArrayList<T>();

        for (T javaType : javaTypes) {
            userTypes.add(zetJavaTypeOmNaarUserType(javaType));
            generatieTypes.add(zetJavaTypeOmNaarGeneratieType(javaType));
        }

        geschrevenBroncodeBestanden.addAll(
                new GeneriekeEnkelBestandJavaWriter<T>(
                        configuratie.getPad(), configuratie.isGenerationGapPatroonOverschrijf())
                        .genereerEnSchrijfWeg(userTypes, generator)
        );

        geschrevenBroncodeBestanden.addAll(
                new GeneriekeEnkelBestandJavaWriter<T>(configuratie.getPad(), configuratie.isOverschrijf())
                        .genereerEnSchrijfWeg(generatieTypes, generator)
        );
        return geschrevenBroncodeBestanden;
    }

}
