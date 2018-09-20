/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.java.model.AbstractJavaType;
import nl.bzk.brp.generatoren.java.model.JavaGenericParameter;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.util.JavaGeneratorConstante;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Abstract super klasse voor de generation gap writers voor interfaces en klassen.
 * Bevat alle gemeenschappelijke functionaliteit.
 *
 * @param <T> Het type (JavaInterface of JavaKlasse)
 */
public abstract class AbstractGenerationGapPatroonJavaWriter<T extends AbstractJavaType> extends AbstractJavaWriter<T> {

    /**
     * Package suffix voor types van gegenereerde klassen. *
     */
    public static final String GENERATIE_TYPE_SUB_PACKAGE = "";

    private GeneratorConfiguratie configuratie;
    private ASTParser parser;

    /**
     * Constructor die de basis configuratie meekrijgt.
     *
     * @param configuratie de configuratie.
     */
    public AbstractGenerationGapPatroonJavaWriter(final GeneratorConfiguratie configuratie) {
        super(configuratie.getPad());
        this.configuratie = configuratie;

        this.parser = ASTParser.newParser(AST.JLS4);
        this.parser.setKind(ASTParser.K_COMPILATION_UNIT);
    }

    public GeneratorConfiguratie getConfiguratie() {
        return this.configuratie;
    }

    /**
     * Neem eventueel in de bestaande user klasse aanpassingen mee in de nieuwe user klasse.
     *
     * @param nieuweUserType het java type.
     */
    private void voegBestaandeUserTypeAanpassingenToe(final T nieuweUserType) {
        File bestaandBronBestand = bouwBronBestand(nieuweUserType);
        if (bestaandBronBestand.exists()) {
            String inhoud = null;
            try {
                inhoud = FileUtils.readFileToString(bestaandBronBestand);
            } catch (IOException e) {
                // Stoppen maar, want dit zou geen fout mogen opleveren.
                throw new IllegalStateException("IOException tijden het inlezen van een bron bestand.");
            }
            final String bestaandBronBestandInhoud = inhoud;
            this.parser.setSource(bestaandBronBestandInhoud.toCharArray());

            final StringBuffer handmatigeCodeBoveninBuffer = new StringBuffer();
            final StringBuffer handmatigeCodeOnderinBuffer = new StringBuffer();
            this.parser.createAST(null).accept(new ASTVisitor() {
                @SuppressWarnings("unchecked")
                @Override
                // Neem additioneel toegevoegde interfaces over, waar van toepassing.
                public boolean visit(final TypeDeclaration type) {
                    for (Type interfaceType : (List<Type>) type.superInterfaceTypes()) {
                        if (interfaceType instanceof ParameterizedType) {
                            ParameterizedType parameterizedType = ((ParameterizedType) interfaceType);
                            // De Comparable interface is (op dit moment) altijd handmatig toegevoegd, dus behouden.
                            if (parameterizedType.getType().toString().equals("Comparable")) {
                                String genericTypeNaam = parameterizedType.typeArguments().get(0).toString();
                                // De AST objecten hebben geen package, dus we gebruiken 'null' als package.
                                // We weten dat de imports echter wel aanwezig zijn, vanwege behoud van imports.
                                JavaType toeTeVoegenInterfaceType = new JavaType(parameterizedType.getType().toString(),
                                        null, new JavaGenericParameter(new JavaType(genericTypeNaam, null)));
                                nieuweUserType.voegSuperInterfaceToe(toeTeVoegenInterfaceType);
                            }
                        }
                    }
                    return true;
                }

                @Override
                // Neem alle imports over, aangezien die kennelijk nodig zijn in het user type.
                public boolean visit(final ImportDeclaration importStatement) {
                    nieuweUserType.voegExtraImportsToe(JavaType
                            .bouwVoorFullyQualifiedClassName(importStatement.getName().toString()));
                    return true;
                }

                @Override
                // Neem alle velden over, aangezien een user type normaal gesproken geen velden bevat.
                public boolean visit(final FieldDeclaration veld) {
                    this.voegNodeInhoudToe(veld, handmatigeCodeBoveninBuffer);
                    return true;
                }

                @Override
                // Neem alle methodes over, aangezien een user type normaal gesproken geen methodes bevat.
                public boolean visit(final MethodDeclaration methode) {
                    // De parser ziet een constructor ook als methode, dus die moeten we uitsluiten.
                    // Tevens moeten we methodes van inner klasses uitsluiten.
                    if (!isConstructor(nieuweUserType, methode) && isMethodeBinnenType(nieuweUserType, methode)) {
                        this.voegNodeInhoudToe(methode, handmatigeCodeOnderinBuffer);
                    }
                    return true;
                }

                private void voegNodeInhoudToe(final ASTNode node, final StringBuffer buffer) {
                    buffer.append(bestaandBronBestandInhoud.substring(
                            node.getStartPosition(), node.getStartPosition() + node.getLength()));
                    // Een newline voor het einde van de regel, en een als scheiding van deze node naar de volgende.
                    buffer.append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
                    buffer.append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
                }
            });

            nieuweUserType.setHandmatigeCodeBovenin(handmatigeCodeBoveninBuffer.toString());
            nieuweUserType.setHandmatigeCodeOnderin(handmatigeCodeOnderinBuffer.toString());
        }
    }

    /**
     * Bepaalt of de methode een constructor is van het type.
     * Dat is zo als de methode naam gelijk is aan de type naam.
     *
     * @param type        het type
     * @param javaMethode de methode
     * @return ja of nee
     */
    private boolean isConstructor(final T type, final MethodDeclaration javaMethode) {
        return javaMethode.getName().toString().equals(type.getNaam());
    }

    /**
     * Checkt of de methode binnen het type valt.
     * Dat is zo als de bovenliggende type declaratie de naam van het type heeft.
     *
     * @param type        het type
     * @param javaMethode de methode
     * @return ja of nee
     */
    private boolean isMethodeBinnenType(final T type, final MethodDeclaration javaMethode) {
        boolean binnenType = false;
        boolean parentIsType = javaMethode.getParent() instanceof TypeDeclaration;
        if (parentIsType) {
            binnenType = ((TypeDeclaration) javaMethode.getParent()).getName().toString().
                    equals(type.getNaam());
        }
        return binnenType;
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
        final List<T> geschrevenBroncodeBestanden = new ArrayList<>();
        final List<T> userTypes = new ArrayList<>();
        final List<T> generatieTypes = new ArrayList<>();

        for (T javaType : javaTypes) {
            T userJavaType = zetJavaTypeOmNaarUserType(javaType);
            if (this.configuratie.isBehoudUserTypeAanpassingen()) {
                this.voegBestaandeUserTypeAanpassingenToe(userJavaType);
            }
            userTypes.add(userJavaType);
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
