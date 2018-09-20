/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.writer;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.java.model.JavaInterface;
import nl.bzk.brp.generatoren.java.model.JavaType;

/**
 * Class voor het genereren en wegschrijven van Java interfaces op basis van een 'Platform Specifieke Model' (de
 * {@link nl.bzk.brp.generatoren.java.model.JavaInterface} in dit geval), waarbij de interfaces conform het 'Generation
 * Gap' patroon worden opgeslagen.
 * <p>
 * Vanwege het 'Generation Gap' patroon, wordt elke Java Interface die gegenereerd dient te worden, opgesplitst in een
 * tweetal Java source bestanden. Een bevat de gegenereede code, terwijl het andere Java source bestand deze eerste
 * juist extend, maar verder geen methodes etc. bevat. Dit tweede Java source bestand is het bestand wat verder
 * gebruikt dient te worden en dus de naam heeft van de {@link nl.bzk.brp.generatoren.java.model.JavaInterface}
 * en deze is bedoeld om door gebruikers eventueel uitgebreid te worden.
 * </p>
 */
public class GenerationGapPatroonJavaInterfaceWriter extends AbstractGenerationGapPatroonJavaWriter<JavaInterface> {

    /** Suffix voor interface namen. */
    public static final String GENERATIE_INTERFACE_NAAM_SUFFIX = "Basis";

    /**
     * Maak een nieuwe generation gap patroon java interface writer aan
     * met de meegegeven configuratie.
     *
     * @param configuratie de configuratie
     */
    public GenerationGapPatroonJavaInterfaceWriter(final GeneratorConfiguratie configuratie) {
        super(configuratie);
    }

    @Override
    protected String genereerNaamVoorGeneratieType(final JavaInterface javaType) {
        return javaType.getNaam() + GENERATIE_INTERFACE_NAAM_SUFFIX;
    }

    /**
     * Zet een standaard {@link nl.bzk.brp.generatoren.java.model.JavaInterface} om naar de 'user' variant van de
     * JavaInterface, conform het 'Generation Gap' patroon. De 'user' variant is het Java source bestand dat aangepast
     * kan worden en bevat daardoor niet de gegenereerde code. Het is ook het bestand dat de 'generatie' versie
     * extends. In deze methode wordt daarom een clone gemaakt van de initiele {@link
     * nl.bzk.brp.generatoren.java.model.JavaInterface} waarna de clone wordt aangepast om geen methodes meer te hebben,
     * maar wel de benodigde 'extends'.
     *
     * @param javaInterface de Java Interface waarvoor de 'user' variant dient te worden gegenereerd.
     * @return een nieuwe Java Interface conform de 'user' variant in het 'Generation Gap' patroon.
     */
    @Override
    protected JavaInterface zetJavaTypeOmNaarUserType(final JavaInterface javaInterface) {
        final JavaInterface userInterface;
        try {
            userInterface = (JavaInterface) javaInterface.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException("Kon argument niet clonen waar dat wel was verwacht.", e);
        }

        // De user versie is aanpasbaar.
        userInterface.setCodeAanpasbaar(true);

        // Methodes leeg laten voor user interface.
        userInterface.getFuncties().clear();

        // Bovenliggende interfaces moeten worden gecleared, want die worden doorgeschoven naar de
        // generatie interface.
        userInterface.getSuperInterfaces().clear();

        // Extend van generatie interface toevoegen
        userInterface.voegSuperInterfaceToe(new JavaType(genereerNaamVoorGeneratieType(javaInterface),
                javaInterface.getPackagePad() + GENERATIE_TYPE_SUB_PACKAGE));

        return userInterface;
    }

    /**
     * Zet een standaard {@link nl.bzk.brp.generatoren.java.model.JavaInterface} om naar de 'generatie' variant van de
     * JavaInterface, conform het 'Generation Gap' patroon. De 'generatie' variant is het Java source bestand dat
     * de werkelijke gegenereerde code bevat, maar niet aangepast dient te worden door de gebruikers/programmeurs. Het
     * is daarom ook niet het bestand dat direct gerefereerd zal worden vanuit andere bestanden (behalve uit de 'user'
     * variant). In deze methode wordt daarom een clone gemaakt van de initiele {@link
     * nl.bzk.brp.generatoren.java.model.JavaInterface} waarna de naam en het package van de clone wordt aangepast.
     *
     * @param javaInterface de Java Interface waarvoor de 'generatie' variant dient te worden gegenereerd.
     * @return een nieuwe Java Interface conform de 'generatie' variant in het 'Generation Gap' patroon.
     */
    @Override
    protected JavaInterface zetJavaTypeOmNaarGeneratieType(final JavaInterface javaInterface) {
        final JavaInterface generatieInterface;
        try {
            generatieInterface = (JavaInterface) javaInterface.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException("Kon argument niet clonen waar dat wel was verwacht.", e);
        }

        // De generatie versie is niet aanpasbaar
        generatieInterface.setCodeAanpasbaar(false);

        // Package en naam zijn voor de 'generatie' versie anders
        generatieInterface.setNaam(genereerNaamVoorGeneratieType(javaInterface));
        generatieInterface.setPackagePad(genereerPackageNaamVoorGeneratieType(javaInterface));

        return generatieInterface;
    }
}
