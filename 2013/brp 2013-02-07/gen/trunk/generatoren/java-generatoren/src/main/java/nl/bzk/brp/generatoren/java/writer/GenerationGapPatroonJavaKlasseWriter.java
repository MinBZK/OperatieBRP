/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.writer;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.java.AbstractJavaGenerator;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;

/**
 * Class voor het genereren en wegschrijven van Java klasses op basis van een 'Platform Specifieke Model' (de
 * {@link nl.bzk.brp.generatoren.java.model.JavaKlasse} in dit geval), waarbij de klasses conform het 'Generation Gap'
 * patroon worden opgeslagen.
 * <p>
 * Vanwege het 'Generation Gap' patroon, wordt elke Java Klasse die gegenereerd dient te worden, opgesplitst in een
 * tweetal Java source bestanden. Een bevat de gegenereede code, maar wordt als abstract gedefinieerd, terwijl het
 * andere Java source bestand deze eerste juist extend, maar verder geen methodes etc. bevat. Dit tweede Java source
 * bestand is het bestand wat verder gebruikt dient te worden en dus de naam heeft van de {@link JavaKlasse}
 * en deze is bedoeld om door gebruikers eventueel uitgebreid te worden.
 * </p>
 */
public class GenerationGapPatroonJavaKlasseWriter extends AbstractGenerationGapPatroonJavaWriter<JavaKlasse> {

    /** Prefix voor klasse namen. */
    public static final String GENERATIE_KLASSE_NAAM_PREFIX = "Abstract";

    /**
     * Maak een nieuwe generation gap patroon java klasse writer aan
     * met de meegegeven configuratie.
     *
     * @param configuratie de configuratie
     */
    public GenerationGapPatroonJavaKlasseWriter(final GeneratorConfiguratie configuratie) {
        super(configuratie);
    }

    @Override
    protected String genereerNaamVoorGeneratieType(final JavaKlasse javaType) {
        return GENERATIE_KLASSE_NAAM_PREFIX + javaType.getNaam();
    }

    /**
     * Zet een standaard {@link nl.bzk.brp.generatoren.java.model.JavaKlasse} om naar de 'user' variant van de
     * JavaKlasse, conform het 'Generation Gap' patroon. De 'user' variant is het Java source bestand dat aangepast
     * kan worden en bevat daardoor niet de gegenereerde code. Het is ook het bestand dat de 'generatie' versie
     * extends. In deze methode wordt daarom een clone gemaakt van de initiele {@link
     * nl.bzk.brp.generatoren.java.model.JavaKlasse} waarna de clone wordt aangepast om geen methodes meer te hebben,
     * maar wel de benodigde 'extends'.
     *
     * @param javaKlasse de Java Klasse waarvoor de 'user' variant dient te worden gegenereerd.
     * @return een nieuwe Java Klasse conform de 'user' variant in het 'Generation Gap' patroon.
     */
    @Override
    protected JavaKlasse zetJavaTypeOmNaarUserType(final JavaKlasse javaKlasse) {
        final JavaKlasse userKlasse;
        try {
            userKlasse = (JavaKlasse) javaKlasse.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException("Kon argument niet clonen waar dat wel was verwacht.", e);
        }

        // De user versie is aanpasbaar
        userKlasse.setCodeAanpasbaar(true);

        // Velden en methodes leeglaten voor user klasse
        // Annotaties niet leeg maken!!
        userKlasse.getVelden().clear();
        userKlasse.getFuncties().clear();
        userKlasse.getGetters().clear();
        userKlasse.getSetters().clear();

        // Constructoren kopieren, maar met aanroep naar constructor gegenereerde basis/abstracte klasse.
        delegeerConstructoren(userKlasse);

        // Alle extra imports verwijderen. Aanname: extra imports zijn niet nodig in de user klasse.
        userKlasse.maakExtraImportsLeeg();

        // Verwijder de @EntityListeners, @Access annotatie.
        for (JavaAnnotatie annotatie : javaKlasse.getAnnotaties()) {
            if (annotatie.getType().getNaam().equals("EntityListeners")
                    || annotatie.getType().getNaam().equals("Access"))
            {
                userKlasse.getAnnotaties().remove(annotatie);
            }
        }

        // Extend van generatie klasse toevoegen
        userKlasse.setExtendsFrom(new JavaType(genereerNaamVoorGeneratieType(javaKlasse),
                javaKlasse.getPackagePad() + GENERATIE_TYPE_SUB_PACKAGE));

        return userKlasse;
    }

    /**
     * Delegeert alle constructoren naar de super klasse (de gegenereerde klasse).
     *
     * @param klasse de klasse waarvoor de constructoren moeten delegeren naar de onderliggende super constructor.
     */
    private void delegeerConstructoren(final JavaKlasse klasse) {
        // Constructoren kopieren, maar met aanroep naar constructor gegenereerde basis/abstracte klasse.
        List<Constructor> origineleConstructoren = new ArrayList<Constructor>(klasse.getConstructoren());
        klasse.getConstructoren().clear();
        for (Constructor origineleConstructor : origineleConstructoren) {
            klasse.voegConstructorToe(AbstractJavaGenerator.kopieerConstructorMetSuperAanroep(origineleConstructor));
        }
    }

    /**
     * Zet een standaard {@link nl.bzk.brp.generatoren.java.model.JavaKlasse} om naar de 'generatie' variant van de
     * JavaKlasse, conform het 'Generation Gap' patroon. De 'generatie' variant is het Java source bestand dat
     * de werkelijke gegenereerde code bevat, maar niet aangepast dient te worden door de gebruikers/programmeurs. Het
     * is daarom ook niet het bestand dat direct gerefereerd zal worden vanuit andere bestanden (behalve uit de 'user'
     * variant). In deze methode wordt daarom een clone gemaakt van de initiele {@link
     * nl.bzk.brp.generatoren.java.model.JavaKlasse} waarna de naam en het package van de clone wordt aangepast.
     *
     * @param javaKlasse de Java Klasse waarvoor de 'generatie' variant dient te worden gegenereerd.
     * @return een nieuwe Java Klasse conform de 'generatie' variant in het 'Generation Gap' patroon.
     */
    @Override
    protected JavaKlasse zetJavaTypeOmNaarGeneratieType(final JavaKlasse javaKlasse) {
        final JavaKlasse generatieKlasse;
        try {
            generatieKlasse = (JavaKlasse) javaKlasse.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException("Kon argument niet clonen waar dat wel was verwacht.", e);
        }

        // Super interfaces eindigen op "Basis" voor de generatie klasse. Dit zijn de LGM interfaces.
        generatieKlasse.getSuperInterfaces().clear();
        for (JavaType superInterface : javaKlasse.getSuperInterfaces()) {
            //Indien de super interface uit de model basis package komt, dus het NIET gegenereerde deel, behoud dan
            //gewoon de interface op de generatie klasse.
            if (superInterface.getPackagePad().equals(GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage())) {
                generatieKlasse.voegSuperInterfaceToe(superInterface);
            } else {
                final String superInterfaceNaam =
                        superInterface.getNaam() + GenerationGapPatroonJavaInterfaceWriter.GENERATIE_INTERFACE_NAAM_SUFFIX;
                generatieKlasse.voegSuperInterfaceToe(new JavaType(superInterfaceNaam,
                                                                   superInterface.getPackagePad() + GENERATIE_TYPE_SUB_PACKAGE));
            }
        }

        // De generatie versie is niet aanpasbaar, maar wel abstract
        generatieKlasse.setCodeAanpasbaar(false);
        generatieKlasse.setAbstractClass(true);

        // Package en naam zijn voor de 'generatie' versie anders
        generatieKlasse.setNaam(genereerNaamVoorGeneratieType(javaKlasse));
        generatieKlasse.setPackagePad(genereerPackageNaamVoorGeneratieType(javaKlasse));

        for (Constructor constructor : generatieKlasse.getConstructoren()) {
            constructor.setNaam(generatieKlasse.getNaam());
            if (constructor.getAccessModifier() == JavaAccessModifier.PRIVATE) {
                constructor.setAccessModifier(JavaAccessModifier.PROTECTED);
            }
            // Verwijder eventuele JsonCreator annotaties op de generatie klasse constructor,
            // ze zijn alleen nodig op de user klasse.
            constructor.getAnnotaties().remove(new JavaAnnotatie(JavaType.JSON_CREATOR));
        }

        //Als de klasse een @Embeddable annotatie heeft, dan moet de generatie klasse een @MappedSuperclass
        //annotatie hebben en de import daarvoor.
        final boolean klasseIsEmbeddable = javaKlasse.getAnnotaties().contains(new JavaAnnotatie(JavaType.EMBEDDABLE));
        boolean gemapteSuperClass = false;

        // Verwijder de @Table, @Inheritance, @DiscriminatorColumn, @DiscriminatorValue annotatie.
        // NB: Blader over de java klasse om concurrent modification exceptions te voorkomen.
        for (JavaAnnotatie annotatie : javaKlasse.getAnnotaties()) {
            if (annotatie.getType().getNaam().equals("Table")
                    || annotatie.getType().getNaam().equals("Inheritance")
                    || annotatie.getType().getNaam().equals("DiscriminatorColumn")
                    || annotatie.getType().getNaam().equals("DiscriminatorValue"))
            {
                gemapteSuperClass = true;
                generatieKlasse.getAnnotaties().remove(annotatie);
            }
        }

        //Verwijder de @Entity annotatie
        generatieKlasse.getAnnotaties().remove(new JavaAnnotatie(JavaType.ENTITY));

        if (klasseIsEmbeddable || gemapteSuperClass) {
            generatieKlasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.MAPPED_SUPER_CLASS));
            generatieKlasse.getAnnotaties().remove(new JavaAnnotatie(JavaType.EMBEDDABLE));
        }

        return generatieKlasse;
    }
}
