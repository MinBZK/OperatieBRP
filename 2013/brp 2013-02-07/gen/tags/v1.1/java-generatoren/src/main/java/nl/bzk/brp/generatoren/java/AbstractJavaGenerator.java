/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import nl.bprbzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bprbzk.brp.generatoren.rapportage.Klasse;
import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.BmrTargetPlatform;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.rapportage.RapportageUitvoerder;
import nl.bzk.brp.generatoren.java.model.AbstractJavaType;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.EnumeratieWaarde;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaEnumeratie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaInterface;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaMutatorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaSymbolEnum;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieAnnotatieParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieWaardeParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.util.JavaGeneratorConstante;
import nl.bzk.brp.generatoren.java.writer.GenerationGapPatroonJavaInterfaceWriter;
import nl.bzk.brp.generatoren.java.writer.GenerationGapPatroonJavaKlasseWriter;
import nl.bzk.brp.generatoren.java.writer.GeneriekeEnkelBestandJavaWriter;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.BasisType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tekst;
import org.apache.commons.lang3.StringUtils;

/** Abstracte implementatie van een Java Code generator. */
public abstract class AbstractJavaGenerator extends AbstractGenerator {

    @Inject
    private RapportageUitvoerder rapportageUitvoerder;

    /**
     * Retourneert de naam van deze generator zoals deze in de rapportage voorkomt.
     *
     * @return naam van deze generator voor rapportage.
     */
    protected abstract GeneratorNaam getGeneratorNaamVoorRapportage();

    /**
     * Genereert de juist {@link JavaWriter} instantie op basis van de generator configuratie.
     *
     * @param generatorConfiguratie de generator configuratie.
     * @param clazz de klasse van het Java Type dat de writer schrijft.
     * @param <T> type Java Type dat de writer schrijft.
     * @return een {@link JavaWriter} instantie.
     */
    // Cast is nodig en veilig.
    @SuppressWarnings("unchecked")
    public <T extends AbstractJavaType> JavaWriter<T> javaWriterFactory(
        final GeneratorConfiguratie generatorConfiguratie, final Class<T> clazz)
    {
        final JavaWriter<T> writer;
        if (generatorConfiguratie.isGenerationGapPatroon()) {
            if (clazz.equals(JavaInterface.class)) {
                writer = (JavaWriter<T>) new GenerationGapPatroonJavaInterfaceWriter(generatorConfiguratie);
            } else if (clazz.equals(JavaKlasse.class)) {
                writer = (JavaWriter<T>) new GenerationGapPatroonJavaKlasseWriter(generatorConfiguratie);
            } else if (clazz.equals(JavaEnumeratie.class)) {
                writer = new GeneriekeEnkelBestandJavaWriter<T>(generatorConfiguratie.getPad(),
                    generatorConfiguratie.isOverschrijf());
            } else if (clazz.equals(JavaSymbolEnum.class)) {
                writer = new GeneriekeEnkelBestandJavaWriter<T>(generatorConfiguratie.getPad(),
                    generatorConfiguratie.isOverschrijf());
            } else {
                throw new GeneratorExceptie(
                    "Generation Gap writer wordt niet ondersteund voor type: " + clazz.getName());
            }
        } else {
            writer = new GeneriekeEnkelBestandJavaWriter<T>(generatorConfiguratie.getPad(),
                generatorConfiguratie.isOverschrijf());
        }
        return writer;
    }

    /**
     * Genereert een {@link nl.bzk.brp.generatoren.java.model.JavaVeld} instantie op basis van het opgegeven attribuut,
     * wat ook een attribuuttype zou moeten zijn.
     *
     * @param attribuut het attribuut waarvoor een JavaVeld instantie moet worden gegenereerd.
     * @return het gegenereerde JavaVeld.
     */
    protected JavaVeld genereerJavaVeldVoorAttribuutType(final Attribuut attribuut) {
        final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());

        //Haal het Attribuut type op
        final AttribuutType attribuutType = getBmrDao().getAttribuutTypeVoorAttribuut(attribuut);
        final JavaType javaVeldTypeVoorAttribuut = getJavaTypeVoorAttribuutType(attribuutType);
        return new JavaVeld(javaVeldTypeVoorAttribuut, veldNaam);
    }

    /**
     * Retourneert de (basis) javadoc tekst voor een Java Class. Hiervoor wordt de juiste tekst uit het BMR voor het
     * opgegeven element opgehaald en geconcateneerd.
     *
     * @param element het element waarvoor de javadoc gegenereerd dient te worden.
     * @return de javadoc voor het element/Java Class.
     */
    protected String bouwJavadocVoorElement(final GeneriekElement element) {
        List<Tekst> tekstenVoorElement = getBmrDao().getTekstenVoorElement(element);

        final StringBuilder javaDocs = new StringBuilder();
        for (Tekst tekst : tekstenVoorElement) {
            javaDocs.append(tekst.getTekst());
            javaDocs.append(JavaGeneratorConstante.NEWLINE.getWaarde());
            javaDocs.append(JavaGeneratorConstante.NEWLINE.getWaarde());
        }
        return javaDocs.toString();
    }

    /**
     * Genereert de javadocs voor een getter.
     *
     * @param getter De getter waar javadocs voor worden gegenereerd.
     * @param objectType Object type waar deze getter op zit.
     * @param groep Groep die door deze getter wordt geretourneerd.
     */
    protected void genereerGetterJavaDoc(final JavaAccessorFunctie getter,
        final ObjectType objectType,
        final Groep groep)
    {
        getter.setJavaDoc(genereerGetterJavadoc(groep.getNaam(), objectType.getNaam()));
        getter.setReturnWaardeJavaDoc(groep.getNaam() + ".");
    }

    /**
     * Genereert de javadocs voor een getter.
     *
     * @param getter De getter waar javadocs voor worden gegenereerd.
     * @param groep Groep waar deze getter op zit.
     * @param attr Attribuut die door deze getter wordt geretourneerd.
     */
    protected void genereerGetterJavaDoc(final JavaAccessorFunctie getter,
        final Groep groep,
        final Attribuut attr)
    {
        getter.setJavaDoc(genereerGetterJavadoc(attr.getNaam(), groep.getNaam()));
        getter.setReturnWaardeJavaDoc(attr.getNaam() + ".");
    }

    /**
     * Genereert de javadoc voor een getter.
     *
     * @param getter De getter waar javadocs voor worden gegenereerd.
     * @param objectType Object type waar deze getter op zit.
     * @param attr Attribuut die door deze getter wordt geretourneerd.
     */
    protected void genereerGetterJavaDoc(final JavaAccessorFunctie getter,
        final ObjectType objectType,
        final Attribuut attr)
    {
        getter.setJavaDoc(genereerGetterJavadoc(attr.getNaam(), objectType.getNaam()));
        getter.setReturnWaardeJavaDoc(attr.getNaam() + ".");
    }

    /**
     * Genereert de javadoc voor een setter, inclusief de javadocs voor de parameter van de setter.
     *
     * @param setter De setter waar javadocs voor worden gegenereerd.
     * @param objectType Object type waar deze getter op zit.
     * @param attr Attribuut dat door deze setter wordt gezet.
     */
    protected void genereerSetterJavaDoc(final JavaMutatorFunctie setter,
        final ObjectType objectType,
        final Attribuut attr)
    {
        setter.setJavaDoc(genereerSetterJavadoc(attr.getNaam(), objectType.getNaam()));
        setter.getParameters().get(0).setJavaDoc(attr.getNaam() + ".");
    }

    /**
     * Genereert de javadoc voor een setter, inclusief de javadocs voor de parameter van de setter.
     *
     * @param setter De setter waar javadocs voor worden gegenereerd.
     * @param objectType Object type waar deze setter op zit.
     * @param groep Groep die door deze setter wordt gezet.
     */
    protected void genereerSetterJavaDoc(final JavaMutatorFunctie setter,
        final ObjectType objectType,
        final Groep groep)
    {
        setter.setJavaDoc(genereerSetterJavadoc(groep.getNaam(), objectType.getNaam()));
        setter.getParameters().get(0).setJavaDoc(groep.getNaam() + ".");
    }

    /**
     * Genereert de javadoc voor een setter, inclusief de javadocs voor de parameter van de setter.
     *
     * @param setter De setter waar javadocs voor worden gegenereerd.
     * @param groep Groep waar deze setter op zit.
     * @param attr Attribuut dat door deze setter wordt gezet.
     */
    protected void genereerSetterJavaDoc(final JavaMutatorFunctie setter,
        final Groep groep,
        final Attribuut attr)
    {
        setter.setJavaDoc(genereerSetterJavadoc(attr.getNaam(), groep.getNaam()));
        setter.getParameters().get(0).setJavaDoc(attr.getNaam() + ".");
    }

    /**
     * Genereert de javadoc voor een inverse associatie getter.
     *
     * @param getter De setter waar javadocs voor worden gegenereerd.
     * @param objectType Object type waar deze getter op zit.
     * @param inverseAttr Attribuut dat door deze getter wordt geretourneerd.
     */
    protected void genereerInverseAssociatieGetterJavaDoc(final JavaAccessorFunctie getter,
        final ObjectType objectType,
        final Attribuut inverseAttr)
    {
        getter.setJavaDoc(genereerGetterJavadoc(inverseAttr.getObjectType().getMeervoudsnaam(), objectType.getNaam()));
        getter.setReturnWaardeJavaDoc(inverseAttr.getObjectType().getMeervoudsnaam() + " van "
            + objectType.getNaam() + ".");
    }

    /**
     * Genereert de javadoc voor een inverse associatie setter.
     *
     * @param setter De setter waar javadocs voor worden gegenereerd.
     * @param objectType Object type waar deze setter op zit.
     * @param inverseAttr Attribuut dat door deze setter wordt gezet.
     */
    protected void genereerInverseAssociatieSetterJavaDoc(final JavaMutatorFunctie setter,
        final ObjectType objectType,
        final Attribuut inverseAttr)
    {
        setter.setJavaDoc(genereerSetterJavadoc(inverseAttr.getObjectType().getMeervoudsnaam(), objectType.getNaam()));
        setter.getParameters().get(0).setJavaDoc(inverseAttr.getObjectType().getMeervoudsnaam() + ".");
    }

    /**
     * Haal het java type (inclusief fully qualified class name) op voor dit attribuut type.
     *
     * @param attribuutType het attribuut type
     * @return het te gebruiken Java Type voor opgegeven attribuut type.
     */
    protected JavaType getJavaTypeVoorAttribuutType(final AttribuutType attribuutType) {
        final BasisType basisType =
            this.getBmrDao().getBasisTypeVoorAttribuutType(attribuutType, BmrTargetPlatform.JAVA).
                getBasisType();
        return JavaGeneratieUtil.bepaalJavaBasisTypeVoorAttribuutType(attribuutType, basisType);
    }


    /**
     * Retourneert een standaard javadoc voor een Java setter methode op basis van de veldnaam en de naam van het
     * object waartoe het veld behoort.
     *
     * @param veldNaam naam van het veld dat wordt gezet.
     * @param ouderNaam naam van het object waartoe het veld behoort.
     * @return een javadoc tekst.
     */
    private String genereerSetterJavadoc(final String veldNaam, final String ouderNaam) {
        return String.format("Zet %1$s van %2$s.", veldNaam, ouderNaam);
    }

    /**
     * Retourneert een standaard javadoc voor een Java getter methode op basis van de veldnaam en de naam van het
     * object waartoe het veld behoort.
     *
     * @param veldNaam naam van het veld dat wordt gezet.
     * @param ouderNaam naam van het object waartoe het veld behoort.
     * @return een javadoc tekst.
     */
    private String genereerGetterJavadoc(final String veldNaam, final String ouderNaam) {
        return String.format("Retourneert %1$s van %2$s.", veldNaam, ouderNaam);
    }

    /**
     * Genereert een constructor voor een enumeratie met de benodige parameters. op basis van de velden in de
     * enumeratie.
     *
     * @param javaEnum De java enum waarvoor een constructor moet worden gegenereerd.
     * @return Een java enum constructor.
     */
    protected Constructor genereerConstructorVoorEnum(final JavaEnumeratie javaEnum) {
        final StringBuilder constructorBodyBuilder = new StringBuilder();
        final Constructor constructor = new Constructor(JavaAccessModifier.PRIVATE, javaEnum.getNaam());
        constructor.setJavaDoc("Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.");
        for (JavaVeld javaVeld : javaEnum.getVelden()) {
            final JavaFunctieParameter consParam = new JavaFunctieParameter(
                javaVeld.getNaam(), javaVeld.getType(),
                GeneratieUtil.upperTheFirstCharacter(javaVeld.getNaam()) + " voor " + javaEnum.getNaam());
            constructor.voegParameterToe(consParam);
            constructorBodyBuilder.append("this.").append(javaVeld.getNaam())
                                  .append(" = ").append(consParam.getNaam()).append(";\n");
        }
        constructor.setBody(constructorBodyBuilder.deleteCharAt(constructorBodyBuilder.lastIndexOf("\n")).toString());
        return constructor;
    }

    /**
     * Elke ordinal van een java enumeratie correspondeert met de id waarde in de database.
     * Echter 0 ordinal waardes moeten we overslaan, want we hebben geen stamgegevens met een id
     * met waarde 0. Dit kan database technisch ook niet. Vandaar dat elke java enumeratie begin met een dummy waarde,
     * die de plaats van ordinal 0 inneemt en verder niet door de applicatie gebruikt zal worden.
     *
     * @param javaEnum De java enumeratie waarvoor een dummy moet worden gegenereerd.
     * @return Een dummy enumeratie waarde.
     */
    protected EnumeratieWaarde genereerDummyEnumWaarde(final JavaEnumeratie javaEnum) {
        EnumeratieWaarde dummy = new EnumeratieWaarde("DUMMY",
            "Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.");
        for (JavaFunctieParameter constructorParam : javaEnum.getConstructoren().get(0).getParameters()) {
            if (constructorParam.getNaam().equalsIgnoreCase("code")) {
                //De code hoeft niet altijd een String te zijn, zou eventueel een ander data type kunnen zijn zoals
                //een short of een byte.
                JavaType typeVoorCode = constructorParam.getJavaType();
                String parameterWaarde = "-1";

                //JAVA ziet een decimaal literal standaard als een Integer, dus er zijn wat casts nodig voor typen
                //die kleiner zijn dan een Integer; de Short en de Byte.
                if (typeVoorCode.getNaam().equalsIgnoreCase("Short")) {
                    //Getver..
                    parameterWaarde = "((short) " + parameterWaarde + ")";
                } else if (typeVoorCode.getNaam().equalsIgnoreCase("Byte")) {
                    //Getver..
                    parameterWaarde = "((byte) " + parameterWaarde + ")";
                } else if (typeVoorCode.getNaam().equalsIgnoreCase("Boolean")) {
                    parameterWaarde = "null";
                }
                dummy.voegConstructorParameterToe(parameterWaarde, typeVoorCode.getNaam().equals("String"));
            } else if (constructorParam.getNaam().equalsIgnoreCase("naam")) {
                dummy.voegConstructorParameterToe("Dummy", true);
            } else if (constructorParam.getNaam().equalsIgnoreCase("omschrijving")) {
                dummy.voegConstructorParameterToe("Dummy", true);
            } else {
                // Wat het verder ook voor parameter mag zijn, null volstaat hier want het is een dummy.
                dummy.voegConstructorParameterToe("null", false);
            }
        }
        return dummy;
    }

    /**
     * Geeft aan of een attribuut van een type is dat een enumeratie is.
     *
     * @param attribuut het attribuut.
     * @return indicatie of het attribuut een enumeratie waarde is.
     */
    protected boolean isEnumeratieAttribuut(final Attribuut attribuut) {
        return !getBmrDao().getWaardeEnumeratiesVoorElement(attribuut.getType(), false, true).isEmpty();
    }

    /**
     * Maak een kopie van de meegegeven constructor en roep in de body de super constructor
     * aan met dezelfde argumenten. Handig voor het maken van constructors voor subclasses.
     *
     * @param origineleConstructor de originele constructor
     * @return de nieuwe constructor
     */
    public static Constructor kopieerConstructorMetSuperAanroep(final Constructor origineleConstructor) {
        return kopieerConstructorMetSuperAanroep(origineleConstructor, new HashMap<String, String>());
    }

    /**
     * Maak een kopie van de meegegeven constructor en roep in de body de super constructor
     * aan met dezelfde argumenten. Handig voor het maken van constructors voor subclasses.
     * Met de specifieke argumenten map kan een directe waarde gespecificeerd worden voor
     * de betreffende parameter naam. Die parameter zal dan geen onderdeel zijn van de
     * parameters voor de opgeleverde constructor.
     *
     * @param origineleConstructor de originele constructor
     * @param specifiekeArgumenten de specifieke argumenten, map van naam op waarde (als java expressie).
     * @return de nieuwe constructor
     */
    public static Constructor kopieerConstructorMetSuperAanroep(final Constructor origineleConstructor,
        final Map<String, String> specifiekeArgumenten)
    {
        try {
            Constructor constructor = (Constructor) origineleConstructor.clone();
            // Verwijder alle argumenten die specifiek gezet moeten worden.
            Iterator<JavaFunctieParameter> parameterIter = constructor.getParameters().iterator();
            while (parameterIter.hasNext()) {
                JavaFunctieParameter parameter = parameterIter.next();
                if (specifiekeArgumenten.containsKey(parameter.getNaam())) {
                    parameterIter.remove();
                }
            }

            // Maak de aanroep van de super constructor.
            final StringBuilder constructorBody = new StringBuilder();
            constructorBody.append("super(");
            for (JavaFunctieParameter parameter : origineleConstructor.getParameters()) {
                if (specifiekeArgumenten.containsKey(parameter.getNaam())) {
                    // Gebruik de specifieke waarde indien aanwezig.
                    constructorBody.append(specifiekeArgumenten.get(parameter.getNaam()));
                } else {
                    constructorBody.append(parameter.getNaam());
                }
                constructorBody.append(", ");
            }
            int index = constructorBody.lastIndexOf(",");
            if (index >= 1) {
                // Laatste overbodige komma en spatie verwijderen
                constructorBody.delete(index, index + 2);
            }
            constructorBody.append(");");
            constructor.setBody(constructorBody.toString());
            return constructor;
        } catch (CloneNotSupportedException e) {
            throw new GeneratorExceptie("Constructor ondersteunt geen clone?!?");
        }
    }

    /**
     * Voegt JPA annotaties toe aan een java veld.
     *
     * @param objectType Object type waar het attribuut in zit.
     * @param attribuut Het attribuut waar het veld een java representatie van is.
     * @param javaVeld Java representatie van het attribuut.
     * @param isDiscriminatorAttribuut of dit een discriminator attribuut is of niet.
     * @param isJsonProperty of het een json property is of niet.
     * @param readOnly of dit attribuut alleen lezen is op de database.
     */
    protected void annoteerAttribuutVeld(final ObjectType objectType, final Attribuut attribuut,
        final JavaVeld javaVeld, final boolean isDiscriminatorAttribuut,
        final boolean isJsonProperty, final boolean readOnly)
    {
        final BmrElementSoort soort =
            BmrElementSoort.getBmrElementSoortVoorCode(attribuut.getType().getSoortElement().getCode());
        final boolean inOgm = GeneratieUtil
            .bmrJaNeeNaarBoolean(attribuut.getInOgm(), GeneratieUtil.bmrJaNeeNaarBoolean(attribuut.getInLgm(), true));

        if (!inOgm) {
            javaVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.TRANSIENT));
        } else {
            switch (soort) {
                case OBJECTTYPE:
                    annoteerObjecttypeVeld(objectType, attribuut, javaVeld, isDiscriminatorAttribuut,
                        readOnly);
                    break;
                case ATTRIBUUTTYPE:
                    annoteerAttribuuttypeVeld(objectType, attribuut, javaVeld, isDiscriminatorAttribuut,
                        readOnly);
                    break;
                default:
                    // Do Nothing
            }
        }
        if (isJsonProperty) {
            this.voegJsonPropertyAnnotatieToe(javaVeld);
        }
    }

    /**
     * Voegt de benodigde annotaties voor het opgegeven attribuut toe aan het bijbehorende veld.
     *
     * @param objectType het objecttype waartoe het attribuut behoort.
     * @param attribuut het attribuut waarvoor de annotaties aan het veld moeten worden toegevoegd.
     * @param javaVeld het veld waaraan de annotaties moeten worden toegevoegd.
     * @param isDiscriminatorAttribuut boolean die aangeeft of het attribuut een discriminator is of niet.
     * @param readOnly boolean die aangeeft of het attribuut alleen leesbaar is (en niet overschrijfbaar) of niet.
     */
    private void annoteerAttribuuttypeVeld(final ObjectType objectType, final Attribuut attribuut,
        final JavaVeld javaVeld, final boolean isDiscriminatorAttribuut, final boolean readOnly)
    {
        if (isIdAttribuut(attribuut)) {
            annoteerIdVeld(objectType, javaVeld, readOnly);
        } else if (isEnumeratieAttribuut(attribuut)) {
            final JavaAnnotatie enumAnn = new JavaAnnotatie(JavaType.TYPE);
            enumAnn.voegParameterToe(new AnnotatieWaardeParameter("type", javaVeld.getType().getNaam()));
            javaVeld.voegAnnotatieToe(enumAnn);

            final JavaAnnotatie kolomAnn = new JavaAnnotatie(JavaType.COLUMN,
                new AnnotatieWaardeParameter("name", attribuut.getIdentDb()));
            if (isDiscriminatorAttribuut) {
                kolomAnn.getParameters().add(new AnnotatieWaardeParameter("insertable", "false", true));
                kolomAnn.getParameters().add(new AnnotatieWaardeParameter("updatable", "false", true));
            }
            javaVeld.voegAnnotatieToe(kolomAnn);
        } else {
            javaVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDED));
            final JavaAnnotatie attrOverrideAnn =
                new JavaAnnotatie(
                    JavaType.ATTRIBUTE_OVERRIDE, new AnnotatieWaardeParameter("name", "waarde"),
                    new AnnotatieAnnotatieParameter("column",
                        new JavaAnnotatie(JavaType.COLUMN,
                            new AnnotatieWaardeParameter("name", attribuut.getIdentDb()))));
            javaVeld.voegAnnotatieToe(attrOverrideAnn);
        }
    }

    /**
     * Voegt de benodigde annotaties voor het opgegeven attribuut toe aan het bijbehorende veld.
     *
     * @param objectType het objecttype waartoe het attribuut behoort.
     * @param attribuut het attribuut waarvoor de annotaties aan het veld moeten worden toegevoegd.
     * @param javaVeld het veld waaraan de annotaties moeten worden toegevoegd.
     * @param isDiscriminatorAttribuut boolean die aangeeft of het attribuut een discriminator is of niet.
     * @param readOnly boolean die aangeeft of het attribuut alleen leesbaar is (en niet overschrijfbaar) of niet.
     */
    private void annoteerObjecttypeVeld(final ObjectType objectType, final Attribuut attribuut,
        final JavaVeld javaVeld, final boolean isDiscriminatorAttribuut, final boolean readOnly)
    {
        final BmrSoortInhoud soortInhoud =
            BmrSoortInhoud.getBmrSoortInhoudVoorCode(attribuut.getType().getSoortInhoud());

        if (soortInhoud == BmrSoortInhoud.STATISCH_STAMGEGEVEN) {
            javaVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ENUMERATED));
            final JavaAnnotatie kolomAnn = new JavaAnnotatie(JavaType.COLUMN,
                new AnnotatieWaardeParameter("name", attribuut.getIdentDb()));
            if (isDiscriminatorAttribuut || readOnly) {
                // Je kan niet ineens van 'identiteit' veranderen als type.
                kolomAnn.voegParameterToe(new AnnotatieWaardeParameter("updatable", "false", true));
                if (isHierarchischType(objectType) || readOnly) {
                    // Bij een hierarchisch type regelt hibernate de discriminator kolom zelf.
                    kolomAnn.voegParameterToe(new AnnotatieWaardeParameter("insertable", "false", true));
                }
            }
            javaVeld.voegAnnotatieToe(kolomAnn);
        } else {
            final JavaAnnotatie manyToOneAnnotatie = new JavaAnnotatie(JavaType.MANY_TO_ONE);
            // Indien er een inverse associatie is, dan dient deze kant lazy te worden geladen.
            if (!StringUtils.isEmpty(attribuut.getInverseAssociatieNaam())) {
                //FIXME: een specifieke hack om de property van relatie in betrokkenheid eager te maken.
                // (ivm anders mislopende Hibernate proxy class casts)
                final String fetchType;
                if (objectType.getIdentCode().equals("Betrokkenheid") && javaVeld.getNaam().equals("relatie")) {
                    fetchType = "EAGER";
                } else {
                    fetchType = "LAZY";
                }

                manyToOneAnnotatie.voegParameterToe(
                    new AnnotatieWaardeParameter("fetch", JavaType.FETCH_TYPE, fetchType));
            }
            javaVeld.voegAnnotatieToe(manyToOneAnnotatie);

            javaVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JOIN_COLUMN,
                new AnnotatieWaardeParameter("name", attribuut.getIdentDb())));
            if (BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()) {
                // Hibernate specifieke annotatie toevoegen voor het direct fetchen
                // van dynamische stamgegevens.
                final JavaAnnotatie fetchAnnotatie = new JavaAnnotatie(JavaType.FETCH);
                fetchAnnotatie
                    .voegParameterToe(new AnnotatieWaardeParameter("value", JavaType.FETCH_MODE, "JOIN"));
                javaVeld.voegAnnotatieToe(fetchAnnotatie);
            }
        }
    }

    /**
     * Annoteert een Id veld met de benodigde JPA annotaties. Inclusief alle parameters voor de database sequence.
     *
     * @param objectType Object type waar het Id attribuut in is gedefinieerd in het BMR.
     * @param idVeld Java representatie van het Id attribuut.
     * @param readOnly of het een alleen lezen id is (dus dan geen sequence etc).
     */
    protected void annoteerIdVeld(final ObjectType objectType,
        final JavaVeld idVeld, final boolean readOnly)
    {
        idVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ID));
        if (!readOnly) {
            final JavaAnnotatie seqAnn =
                new JavaAnnotatie(JavaType.SEQUENCE_GENERATOR,
                    new AnnotatieWaardeParameter("name", objectType.getIdentCode().toUpperCase()),
                    new AnnotatieWaardeParameter("sequenceName",
                        objectType.getSchema().getNaam() + ".seq_"
                            + objectType.getIdentDb()));
            idVeld.voegAnnotatieToe(seqAnn);

            final JavaAnnotatie generateValueAnn =
                new JavaAnnotatie(JavaType.GENERATED_VALUE,
                    new AnnotatieWaardeParameter("strategy", JavaType.GENERATION_TYPE, "AUTO"),
                    new AnnotatieWaardeParameter("generator", objectType.getIdentCode().toUpperCase()));
            idVeld.voegAnnotatieToe(generateValueAnn);
        }
        this.voegJsonPropertyAnnotatieToe(idVeld);
    }

    /**
     * Voegt een json property annotatie toe aan het veld (als die er niet al is).
     *
     * @param veld het veld
     */
    protected void voegJsonPropertyAnnotatieToe(final JavaVeld veld) {
        if (!veld.heeftAnnotatieVanType("JsonProperty")) {
            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_PROPERTY));
        }
    }

    /**
     * Kijkt of het meegegeven attribuut gelijk is aan het meegegeven
     * discriminator attribuut.
     *
     * @param attribuut het attribuut
     * @param discrAttribuut het discriminator attribuut
     * @return true indien gelijk, anders false
     */
    protected boolean isDiscriminatorAttribuut(final Attribuut attribuut, final Attribuut discrAttribuut) {
        boolean isDiscriminatorAttribuut = false;
        if (discrAttribuut != null
            && discrAttribuut.getId() == attribuut.getId())
        {
            isDiscriminatorAttribuut = true;
        }
        return isDiscriminatorAttribuut;
    }

    /**
     * Bepaalt of een attribuut een json property is.
     *
     * @param attribuut het attribuut
     * @return te ja of te nee
     */
    protected boolean isJsonProperty(final Attribuut attribuut) {
        // Het is geen json property als het een back reference is.
        boolean isJsonProperty = StringUtils.isBlank(attribuut.getInverseAssociatieNaam());
        // Custom uitzonderingen: Betrokkenheid->Relatie, omdat je anders niet van een persoon bij
        // de relatie van een betrokkenheid kan komen.
        if (attribuut.getType().getId() == AbstractGenerator.ID_RELATIE_LGM
            && attribuut.getObjectType().getId() == AbstractGenerator.ID_BETROKKENHEID_LGM)
        {
            isJsonProperty = true;
        }
        return isJsonProperty;
    }

    /**
     * Rapporteert aan de raportage uitvoerder over gegenereerde java typen.
     *
     * @param javaTypen de gegenereerde java typen.
     * @param <T> type van javatypen.
     */
    protected <T extends AbstractJavaType> void rapporteerOverGegenereerdeJavaTypen(final Iterable<T> javaTypen) {
        rapportageUitvoerder.rapporteerGegenereerdeKlassen(getGeneratorNaamVoorRapportage(),
            zetJavaTypenOmNaarRapportageKlassen(javaTypen));
    }

    /**
     * Genereert voor elk java typen in javaTypen een rapportage klasse.
     *
     * @param javaTypen lijst van java typen.
     * @param <T> type van java typen.
     * @return lijst van klassen die in de rapportage worden gebruikt.
     */
    private <T extends AbstractJavaType> List<Klasse> zetJavaTypenOmNaarRapportageKlassen(final Iterable<T> javaTypen) {
        final List<Klasse> rapportageKlassen = new ArrayList<Klasse>();
        for (AbstractJavaType abstractJavaType : javaTypen) {
            Klasse klasse = new Klasse();
            klasse.setNaam(abstractJavaType.getPackagePad() + "." + abstractJavaType.getNaam());
            klasse.setGeneratieTijdstip(new Date().toString());
            klasse.setGeneratorBuildTijdstip(getBuildTimestamp());
            klasse.setGeneratorVersie(getVersie());
            klasse.setMetaregisterVersie(getMetaRegisterVersie());
            rapportageKlassen.add(klasse);
        }
        return rapportageKlassen;
    }

}
