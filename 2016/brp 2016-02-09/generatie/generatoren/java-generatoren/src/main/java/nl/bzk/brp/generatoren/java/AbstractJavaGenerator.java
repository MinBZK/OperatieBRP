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
import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.BmrTargetPlatform;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.rapportage.RapportageUitvoerder;
import nl.bzk.brp.generatoren.java.model.AbstractJavaImplementatieType;
import nl.bzk.brp.generatoren.java.model.AbstractJavaType;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.EnumeratieWaarde;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaEnumeratie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaInterface;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaMutatorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaSymbolEnum;
import nl.bzk.brp.generatoren.java.model.JavaSymbolGroepEnum;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieAnnotatieParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieWaardeParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.AttribuutWrapperNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.util.JavaGeneratorConstante;
import nl.bzk.brp.generatoren.java.writer.GenerationGapPatroonJavaInterfaceWriter;
import nl.bzk.brp.generatoren.java.writer.GenerationGapPatroonJavaKlasseWriter;
import nl.bzk.brp.generatoren.java.writer.GeneriekeEnkelBestandJavaWriter;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.generatoren.rapportage.Klasse;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.BasisType;
import nl.bzk.brp.metaregister.model.ExtraWaarde;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tekst;
import nl.bzk.brp.metaregister.model.Tuple;
import nl.bzk.brp.metaregister.model.VwElement;
import org.apache.commons.lang3.StringUtils;

/**
 * Abstracte implementatie van een Java Code generator.
 */
public abstract class AbstractJavaGenerator extends AbstractGenerator {

    protected static final String NAAM_EXTRA_WAARDE_HEEFT_MATERIELE_HISTORIE = "Heeft_Materiele_Historie";
    protected static final String EXTRA_WAARDE_HEEFT_MATERIELE_HISTORIE_JA   = "J";
    protected static final String EXTRA_WAARDE_HEEFT_MATERIELE_HISTORIE_NEE  = "N";
    protected static final String PERSOON_INDICATIE_SUBSTRING_NAAM           = "PersoonIndicatie";
    protected static final String PERSOON_INDICATIE_IDENT_CODE_PREFIX        = "Indicatie";

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
     * @param clazz                 de klasse van het Java Type dat de writer schrijft.
     * @param <T>                   type Java Type dat de writer schrijft.
     * @return een {@link JavaWriter} instantie.
     */
    // Cast is nodig en veilig.
    @SuppressWarnings("unchecked")
    public <T extends AbstractJavaType> JavaWriter<T> javaWriterFactory(
        final GeneratorConfiguratie generatorConfiguratie, final Class<T> clazz)
    {
        final JavaWriter<T> writer;

        generatorConfiguratie.setGeneratorNaam(getClass().getName());
        if (generatorConfiguratie.isGenerationGapPatroon()) {
            if (clazz.equals(JavaInterface.class)) {
                writer = (JavaWriter<T>) new GenerationGapPatroonJavaInterfaceWriter(generatorConfiguratie);
            } else if (clazz.equals(JavaKlasse.class)) {
                writer = (JavaWriter<T>) new GenerationGapPatroonJavaKlasseWriter(generatorConfiguratie);
            } else if (clazz.equals(JavaEnumeratie.class)) {
                writer = new GeneriekeEnkelBestandJavaWriter<>(generatorConfiguratie.getPad(),
                    generatorConfiguratie.isOverschrijf());
            } else if (clazz.equals(JavaSymbolEnum.class)) {
                writer = new GeneriekeEnkelBestandJavaWriter<>(generatorConfiguratie.getPad(),
                    generatorConfiguratie.isOverschrijf());
            } else if (clazz.equals(JavaSymbolGroepEnum.class)) {
                writer = new GeneriekeEnkelBestandJavaWriter<>(generatorConfiguratie.getPad(),
                                                               generatorConfiguratie.isOverschrijf());
            } else {
                throw new GeneratorExceptie(
                    "Generation Gap writer wordt niet ondersteund voor type: " + clazz.getName());
            }
        } else {
            writer = new GeneriekeEnkelBestandJavaWriter<>(generatorConfiguratie.getPad(),
                generatorConfiguratie.isOverschrijf());
        }
        return writer;
    }

    /**
     * Geeft aan of dit tuple van een persoon indicatie materiele historie heeft of niet. In dat geval is er sprake van formele historie.
     *
     * @param tuple een tuple van het object type 'soort indicatie' (bij andere tuples volgt een crash)
     * @return wel of geen materiele historie
     */
    protected boolean heeftPersoonIndicatieTupleMaterieleHistorie(final Tuple tuple) {
        Boolean heeftMaterieleHistorie = null;
        for (final ExtraWaarde extraWaarde : tuple.getExtraWaarden()) {
            if (extraWaarde.getNaamExtraWaarde().getNaam().equals(NAAM_EXTRA_WAARDE_HEEFT_MATERIELE_HISTORIE)) {
                if (extraWaarde.getWaarde().equals(EXTRA_WAARDE_HEEFT_MATERIELE_HISTORIE_JA)) {
                    heeftMaterieleHistorie = true;
                } else if (extraWaarde.getWaarde().equals(EXTRA_WAARDE_HEEFT_MATERIELE_HISTORIE_NEE)) {
                    heeftMaterieleHistorie = false;
                } else {
                    throw new IllegalStateException("Ongeldige extra waarde voor heeft materiele historie: "
                        + extraWaarde.getWaarde());
                }
            }
        }
        // Bewuste kans op nullpointer bij unboxen, want dat mag nooit voorkomen, anders crash en onderzoeken.
        return heeftMaterieleHistorie;
    }

    /**
     * Maak, voor een indicatie, van een super type java type (verkregen via naamgeving strategie) een subtype java type. Voorbeeld: Staatloos,
     * HisPersoonIndicatieModel -> HisPersoonIndicatieStaatloosModel
     *
     * @param indicatieTuple    de tupel van de indicatie
     * @param supertypeJavaType het java type van het supertype (zoals verkregen uit een naamgeving strategie)
     * @return het java type voor het subtype
     */
    protected JavaType maakPersoonIndicatieSubtypeJavaType(
        final Tuple indicatieTuple, final JavaType supertypeJavaType)
    {
        // Knip het woord 'Indicatie' uit de tupel ident code.
        final String indicatieNaam = indicatieTuple.getIdentCode().replace(PERSOON_INDICATIE_IDENT_CODE_PREFIX, "");
        // Plak de indicatie naam achter het woord 'PersoonIndicatie'.
        final String subtypeNaam = supertypeJavaType.getNaam().replace(
            PERSOON_INDICATIE_SUBSTRING_NAAM, PERSOON_INDICATIE_SUBSTRING_NAAM + indicatieNaam);
        return new JavaType(subtypeNaam, supertypeJavaType.getPackagePad());
    }

    /**
     * Genereer een default lege constructor tbv JPA.
     *
     * @param klasse de java klasse
     * @return de constructor
     */
    protected Constructor genereerDefaultLegeConstructor(final JavaKlasse klasse) {
        final Constructor defaultConstructor = new Constructor(JavaAccessModifier.PROTECTED, klasse);
        defaultConstructor.setJavaDoc("Default lege constructor t.b.v. Hibernate / JPA");
        return defaultConstructor;
    }

    /**
     * Genereert een {@link nl.bzk.brp.generatoren.java.model.JavaVeld} instantie op basis van het opgegeven attribuut, wat ook een attribuuttype zou
     * moeten zijn.
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
     * Retourneert de (basis) javadoc tekst voor een Java Class. Hiervoor wordt de juiste tekst uit het BMR voor het opgegeven element opgehaald en
     * geconcateneerd.
     *
     * @param element het element waarvoor de javadoc gegenereerd dient te worden.
     * @return de javadoc voor het element/Java Class.
     */
    protected String bouwJavadocVoorElement(final GeneriekElement element) {
        final List<Tekst> tekstenVoorElement = getBmrDao().getTekstenVoorElement(element);

        final StringBuilder javaDocs = new StringBuilder();
        for (final Tekst tekst : tekstenVoorElement) {
            javaDocs.append(tekst.getTekst());
            javaDocs.append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
            javaDocs.append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
        }
        return javaDocs.toString();
    }

    /**
     * Genereert de javadocs voor een getter.
     *
     * @param getter     De getter waar javadocs voor worden gegenereerd.
     * @param objectType Object type waar deze getter op zit.
     * @param groep      Groep die door deze getter wordt geretourneerd.
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
     * @param groep  Groep waar deze getter op zit.
     * @param attr   Attribuut die door deze getter wordt geretourneerd.
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
     * @param getter     De getter waar javadocs voor worden gegenereerd.
     * @param objectType Object type waar deze getter op zit.
     * @param attr       Attribuut die door deze getter wordt geretourneerd.
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
     * @param setter     De setter waar javadocs voor worden gegenereerd.
     * @param objectType Object type waar deze getter op zit.
     * @param attr       Attribuut dat door deze setter wordt gezet.
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
     * @param setter     De setter waar javadocs voor worden gegenereerd.
     * @param objectType Object type waar deze setter op zit.
     * @param groep      Groep die door deze setter wordt gezet.
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
     * @param groep  Groep waar deze setter op zit.
     * @param attr   Attribuut dat door deze setter wordt gezet.
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
     * @param getter      De setter waar javadocs voor worden gegenereerd.
     * @param objectType  Object type waar deze getter op zit.
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
     * @param setter      De setter waar javadocs voor worden gegenereerd.
     * @param objectType  Object type waar deze setter op zit.
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
     * Retourneert een standaard javadoc voor een Java setter methode op basis van de veldnaam en de naam van het object waartoe het veld behoort.
     *
     * @param veldNaam  naam van het veld dat wordt gezet.
     * @param ouderNaam naam van het object waartoe het veld behoort.
     * @return een javadoc tekst.
     */
    private String genereerSetterJavadoc(final String veldNaam, final String ouderNaam) {
        return String.format("Zet %1$s van %2$s.", veldNaam, ouderNaam);
    }

    /**
     * Retourneert een standaard javadoc voor een Java getter methode op basis van de veldnaam en de naam van het object waartoe het veld behoort.
     *
     * @param veldNaam  naam van het veld dat wordt gezet.
     * @param ouderNaam naam van het object waartoe het veld behoort.
     * @return een javadoc tekst.
     */
    protected String genereerGetterJavadoc(final String veldNaam, final String ouderNaam) {
        return String.format("Retourneert %1$s van %2$s.", veldNaam, ouderNaam);
    }

    /**
     * Genereert een constructor voor een enumeratie met de benodige parameters. op basis van de velden in de enumeratie.
     *
     * @param javaEnum De java enum waarvoor een constructor moet worden gegenereerd.
     * @return Een java enum constructor.
     */
    protected Constructor genereerConstructorVoorEnum(final JavaEnumeratie javaEnum) {
        final StringBuilder constructorBodyBuilder = new StringBuilder();
        final Constructor constructor = new Constructor(JavaAccessModifier.PRIVATE, javaEnum);
        constructor.setJavaDoc("Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.");
        for (final JavaVeld javaVeld : javaEnum.getVelden()) {
            final JavaFunctieParameter consParam = new JavaFunctieParameter(
                javaVeld.getNaam(), javaVeld.getType(),
                GeneratieUtil.upperTheFirstCharacter(javaVeld.getNaam()) + " voor " + javaEnum.getNaam());
            constructor.voegParameterToe(consParam);
            constructorBodyBuilder.append("this.").append(javaVeld.getNaam()).append(" = ")
                .append(consParam.getNaam()).append(";")
                .append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
        }
        final int lastLineFeed =
            constructorBodyBuilder.lastIndexOf(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
        constructor.setBody(constructorBodyBuilder.delete(lastLineFeed, constructorBodyBuilder.length()).toString());
        return constructor;
    }

    /**
     * Elke ordinal van een java enumeratie correspondeert met de id waarde in de database. Echter 0 ordinal waardes moeten we overslaan, want we hebben
     * geen stamgegevens met een id met waarde 0. Dit kan database technisch ook niet. Vandaar dat elke java enumeratie begin met een dummy waarde, die de
     * plaats van ordinal 0 inneemt en verder niet door de applicatie gebruikt zal worden.
     *
     * @param javaEnum De java enumeratie waarvoor een dummy moet worden gegenereerd.
     * @return Een dummy enumeratie waarde.
     */
    protected EnumeratieWaarde genereerDummyEnumWaarde(final JavaEnumeratie javaEnum) {
        final EnumeratieWaarde dummy = new EnumeratieWaarde("DUMMY",
            "Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.");
        for (final JavaFunctieParameter constructorParam : javaEnum.getConstructoren().get(0).getParameters()) {
            if (constructorParam.getNaam().equalsIgnoreCase("code")) {
                //De code hoeft niet altijd een String te zijn, zou eventueel een ander data type kunnen zijn zoals
                //een short of een byte.
                final JavaType typeVoorCode = constructorParam.getJavaType();
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
        // Gebruik hiervoor altijd het LGM attribuut.
        final Attribuut lgmAttribuut;
        if (attribuut.getElementByLaag().getId() == BmrLaag.LOGISCH.getWaardeInBmr()) {
            lgmAttribuut = attribuut;
        } else {
            lgmAttribuut = this.getBmrDao().getElementVoorSyncIdVanLaag(
                attribuut.getOrgSyncid(), BmrLaag.LOGISCH, Attribuut.class);
        }
        return !getBmrDao().getWaardeEnumeratiesVoorElement(lgmAttribuut.getType(), false, true).isEmpty();
    }

    /**
     * Maak een kopie van de meegegeven constructor en roep in de body de super constructor aan met dezelfde argumenten. Handig voor het maken van
     * constructors voor subclasses.
     *
     * @param origineleConstructor de originele constructor
     * @return de nieuwe constructor
     */
    public static Constructor kopieerConstructorMetSuperAanroep(final Constructor origineleConstructor) {
        return kopieerConstructorMetSuperAanroep(origineleConstructor, new HashMap<String, String>());
    }

    /**
     * Maak een kopie van de meegegeven constructor en roep in de body de super constructor aan met dezelfde argumenten. Handig voor het maken van
     * constructors voor subclasses. Met de specifieke argumenten map kan een directe waarde gespecificeerd worden voor de betreffende parameter naam. Die
     * parameter zal dan geen onderdeel zijn van de parameters voor de opgeleverde constructor.
     *
     * @param origineleConstructor de originele constructor
     * @param specifiekeArgumenten de specifieke argumenten, map van naam op waarde (als java expressie).
     * @return de nieuwe constructor
     */
    public static Constructor kopieerConstructorMetSuperAanroep(final Constructor origineleConstructor,
        final Map<String, String> specifiekeArgumenten)
    {
        try {
            final Constructor constructor = (Constructor) origineleConstructor.clone();
            // Verwijder alle argumenten die specifiek gezet moeten worden.
            final Iterator<JavaFunctieParameter> parameterIter = constructor.getParameters().iterator();
            while (parameterIter.hasNext()) {
                final JavaFunctieParameter parameter = parameterIter.next();
                if (specifiekeArgumenten.containsKey(parameter.getNaam())) {
                    parameterIter.remove();
                }
            }

            // Maak de aanroep van de super constructor.
            final StringBuilder constructorBody = new StringBuilder();
            constructorBody.append("super(");
            for (final JavaFunctieParameter parameter : origineleConstructor.getParameters()) {
                if (specifiekeArgumenten.containsKey(parameter.getNaam())) {
                    // Gebruik de specifieke waarde indien aanwezig.
                    constructorBody.append(specifiekeArgumenten.get(parameter.getNaam()));
                } else {
                    constructorBody.append(parameter.getNaam());
                }
                constructorBody.append(", ");
            }
            final int index = constructorBody.lastIndexOf(",");
            if (index >= 1) {
                // Laatste overbodige komma en spatie verwijderen
                constructorBody.delete(index, index + 2);
            }
            constructorBody.append(");");
            constructor.setBody(constructorBody.toString());
            return constructor;
        } catch (final CloneNotSupportedException e) {
            throw new GeneratorExceptie("Constructor ondersteunt geen clone?!?");
        }
    }

    /**
     * Voegt JPA annotaties toe aan een java veld.
     *
     * @param objectType               Object type waar het attribuut in zit.
     * @param attribuut                Het attribuut waar het veld een java representatie van is.
     * @param javaVeld                 Java representatie van het attribuut.
     * @param isDiscriminatorAttribuut of dit een discriminator attribuut is of niet.
     * @param isJsonProperty           of het een json property is of niet.
     * @param readOnly                 of dit attribuut alleen lezen is op de database.
     */
    protected void annoteerAttribuutVeld(final ObjectType objectType, final Attribuut attribuut,
        final JavaVeld javaVeld, final boolean isDiscriminatorAttribuut,
        final boolean isJsonProperty, final boolean readOnly)
    {
        // Het betreft een back reference als de inverse associatie niet leeg is.
        annoteerAttribuutVeld(objectType, attribuut, javaVeld, isDiscriminatorAttribuut, isJsonProperty, readOnly,
            bepaalOfAttribuutEenBackReferenceIs(attribuut));
    }

    /**
     * Bepaalt of een attribuut een backreference is. Het is geen stamgegeven en het zit in de identiteit groep. Verfijning in de toekomst is mogelijk.
     *
     * @param attribuut het attribuut
     * @return true indien het een backreferecen is anders false
     */
    protected boolean bepaalOfAttribuutEenBackReferenceIs(final Attribuut attribuut) {
        return !isStamgegevenAttribuut(attribuut)
            && isIdentiteitGroep(attribuut.getGroep());
    }

    /**
     * Voegt JPA annotaties toe aan een java veld.
     *
     * @param objectType               Object type waar het attribuut in zit.
     * @param attribuut                Het attribuut waar het veld een java representatie van is.
     * @param javaVeld                 Java representatie van het attribuut.
     * @param isDiscriminatorAttribuut of dit een discriminator attribuut is of niet.
     * @param isJsonProperty           of het een json property is of niet.
     * @param readOnly                 of dit attribuut alleen lezen is op de database.
     * @param isBackreference          of het een back referentie betreft.
     */
    protected void annoteerAttribuutVeld(final ObjectType objectType, final Attribuut attribuut,
        final JavaVeld javaVeld, final boolean isDiscriminatorAttribuut,
        final boolean isJsonProperty, final boolean readOnly,
        final boolean isBackreference)
    {
        final BmrElementSoort soort =
            BmrElementSoort.getBmrElementSoortBijCode(attribuut.getType().getSoortElement().getCode());
        final boolean inOgm = GeneratieUtil
            .bmrJaNeeNaarBoolean(attribuut.getInOgm(),
                GeneratieUtil.bmrJaNeeNaarBoolean(attribuut.getInLgm(), true));

        if (!inOgm) {
            javaVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.TRANSIENT));
        } else {
            switch (soort) {
                case OBJECTTYPE:
                    annoteerObjecttypeVeld(objectType, attribuut, javaVeld, isDiscriminatorAttribuut,
                        readOnly, isBackreference);
                    break;
                case ATTRIBUUTTYPE:
                    annoteerAttribuuttypeVeld(objectType, attribuut, javaVeld, readOnly);
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
     * @param attribuut  het attribuut waarvoor de annotaties aan het veld moeten worden toegevoegd.
     * @param javaVeld   het veld waaraan de annotaties moeten worden toegevoegd.
     * @param readOnly   boolean die aangeeft of het attribuut alleen leesbaar is (en niet overschrijfbaar) of niet.
     */
    private void annoteerAttribuuttypeVeld(final ObjectType objectType, final Attribuut attribuut,
        final JavaVeld javaVeld, final boolean readOnly)
    {
        final Attribuut operationeelAttribuut = getOperationeelAttribuutVoorAttribuut(attribuut);
        if (isIdAttribuut(attribuut)) {
            annoteerIdVeld(objectType, javaVeld, readOnly);
        } else {
            javaVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDED));
            javaVeld.voegAnnotatieToe(new JavaAnnotatie(
                JavaType.ATTRIBUTE_OVERRIDE, new AnnotatieWaardeParameter("name", javaVeld.getType().getNaam() + ".WAARDE_VELD_NAAM", true),
                new AnnotatieAnnotatieParameter("column",
                    new JavaAnnotatie(JavaType.COLUMN,
                        new AnnotatieWaardeParameter("name", operationeelAttribuut.getIdentDb())))));
        }
    }


    /**
     * Retourneert het attribuut uit de operationele laag voor opgegeven attribuut. Indien het attribuut reeds uit de operationele laag komt, wordt het
     * attribuut zelf geretourneerd. Indien het opgegeven attribuut uit de logische laag komt, wordt het bijbehorende attribuut uit de operationele laag
     * (en dan wel de A-Laag) geretourneerd, waarbij dan gebruik wordt gemaakt van de syncid.
     *
     * @param attribuut het attribuut waarvoor het operationele laag attribuut moet worden geretourneerd.
     * @return een operationele laag attribuut.
     */
    private Attribuut getOperationeelAttribuutVoorAttribuut(final Attribuut attribuut) {
        Attribuut resultaat = null;
        if (attribuut.getElementByLaag().getId() == BmrLaag.OPERATIONEEL.getWaardeInBmr()) {
            resultaat = attribuut;
        } else {
            final List<Attribuut> ogmAttributen =
                getBmrDao().getOperationeleLaagAttributenVoorLogischeLaagAttribuut(attribuut);
            for (final Attribuut ogmAttribuut : ogmAttributen) {
                if (ogmAttribuut.getSyncid().intValue() == attribuut.getSyncid().intValue()) {
                    resultaat = ogmAttribuut;
                    break;
                }
            }
        }
        return resultaat;
    }

    /**
     * Voegt de benodigde annotaties voor het opgegeven attribuut toe aan het bijbehorende veld.
     *
     * @param objectType               het objecttype waartoe het attribuut behoort.
     * @param attribuut                het attribuut waarvoor de annotaties aan het veld moeten worden toegevoegd.
     * @param javaVeld                 het veld waaraan de annotaties moeten worden toegevoegd.
     * @param isDiscriminatorAttribuut boolean die aangeeft of het attribuut een discriminator is of niet.
     * @param readOnly                 boolean die aangeeft of het attribuut alleen leesbaar is (en niet overschrijfbaar) of niet.
     * @param isBackreference          of het een back referentie betreft.
     *                                 <p/>
     *                                 TODO backreference parameter wordt nu niet gebruikt, mogelijk in de toekomst wel.
     */
    private void annoteerObjecttypeVeld(final ObjectType objectType, final Attribuut attribuut,
        final JavaVeld javaVeld, final boolean isDiscriminatorAttribuut,
        final boolean readOnly,
        final boolean isBackreference)
    {
        final BmrSoortInhoud soortInhoud =
            BmrSoortInhoud.getBmrSoortInhoudVoorCode(attribuut.getType().getSoortInhoud());

        if (isDatabaseKnipAttribuut(objectType, attribuut)) {
            javaVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.COLUMN,
                new AnnotatieWaardeParameter("name", attribuut.getIdentDb())));
        } else if (soortInhoud == BmrSoortInhoud.STATISCH_STAMGEGEVEN) {
            javaVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDED));
            final JavaAnnotatie kolomAnnotatie = new JavaAnnotatie(JavaType.COLUMN,
                new AnnotatieWaardeParameter("name", attribuut.getIdentDb()));
            final JavaAnnotatie attribuutOverrideAnnotatie = new JavaAnnotatie(JavaType.ATTRIBUTE_OVERRIDE,
                new AnnotatieWaardeParameter("name", javaVeld.getType().getNaam() + ".WAARDE_VELD_NAAM", true),
                new AnnotatieAnnotatieParameter("column", kolomAnnotatie));
            if (isDiscriminatorAttribuut || readOnly) {
                // Je kan niet ineens van 'identiteit' veranderen als type.
                kolomAnnotatie.voegParameterToe(new AnnotatieWaardeParameter("updatable", "false", true));
                if (isHierarchischType(objectType) || readOnly) {
                    // Bij een hierarchisch type regelt hibernate de discriminator kolom zelf.
                    kolomAnnotatie.voegParameterToe(new AnnotatieWaardeParameter("insertable", "false", true));
                }
            }
            javaVeld.voegAnnotatieToe(attribuutOverrideAnnotatie);
        } else if (BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()) {
            // Indien het attribuut een stamgegeven attribuut is wat een attribuut is van een stamgeven objecttype.
            // Dan is het geen wrapper attribuut en gebruiken we dus geen association override maar een gewone
            // ManyToOne associatie.
            if (BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode() == attribuut.getObjectType().getSoortInhoud()) {
                // Stamgegevens worden op Eager gezet, daar voor serialisatie naast id ook de code nodig is. Indien
                // alleen ID nodig zou zijn, zou LAZY met SUBSELECT hier een betere optie zijn.
                // Default = EAGER, dus geen specifieke toevoeging aan de annotatie.
                javaVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.MANY_TO_ONE));
                javaVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JOIN_COLUMN,
                    new AnnotatieWaardeParameter("name",
                        attribuut.getIdentDb())));
            } else {
                javaVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDED));
                javaVeld.voegAnnotatieToe(new JavaAnnotatie(
                    JavaType.ASSOCIATION_OVERRIDE, new AnnotatieWaardeParameter("name", javaVeld.getType().getNaam() + ".WAARDE_VELD_NAAM", true),
                    new AnnotatieAnnotatieParameter("joinColumns", new JavaAnnotatie(JavaType.JOIN_COLUMN,
                        new AnnotatieWaardeParameter(
                            "name", attribuut
                            .getIdentDb())))));
            }
        } else {
            // Indien het een back reference betreft, dan dient deze kant lazy te worden geladen.
            final JavaAnnotatie manyToOneAnnotatie = new JavaAnnotatie(JavaType.MANY_TO_ONE);
            //FIXME: een specifieke hack om de property van relatie in betrokkenheid eager te maken.
            // (ivm anders mislopende Hibernate proxy class casts)
            final String fetchType;
            if (objectType.getIdentCode().equals("Betrokkenheid") && javaVeld.getNaam().equals("relatie")) {
                fetchType = "EAGER";
                // Voeg cascade attribuut toe met cascadetype persist. Dit om vanuit betrokkenheid nieuwe
                // relaties op te kunnen slaan.
                manyToOneAnnotatie.voegParameterToe(
                    new AnnotatieWaardeParameter("cascade", JavaType.CASCADE_TYPE, "PERSIST"));
            } else {
                fetchType = "LAZY";
            }

            manyToOneAnnotatie.voegParameterToe(
                new AnnotatieWaardeParameter("fetch", JavaType.FETCH_TYPE, fetchType));
            javaVeld.voegAnnotatieToe(manyToOneAnnotatie);
            javaVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JOIN_COLUMN,
                new AnnotatieWaardeParameter("name",
                    attribuut.getIdentDb())));
        }
    }

    /**
     * Annoteert een Id veld met de benodigde JPA annotaties. Inclusief alle parameters voor de database sequence.
     *
     * @param objectType Object type waar het Id attribuut in is gedefinieerd in het BMR.
     * @param idVeld     Java representatie van het Id attribuut.
     * @param readOnly   of het een alleen lezen id is (dus dan geen sequence etc).
     */
    protected void annoteerIdVeld(final ObjectType objectType,
        final JavaVeld idVeld, final boolean readOnly)
    {
        idVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ID));
        if (!readOnly) {
            idVeld.voegAnnotatieToe(maakSequenceAnnotatie(objectType));
            idVeld.voegAnnotatieToe(maakGeneratedValueAnnotatie(objectType));
        }
        this.voegJsonPropertyAnnotatieToe(idVeld);
    }

    /**
     * Annoteert een Id getter met de benodigde JPA annotaties. Inclusief alle parameters voor de database sequence.
     *
     * @param objectType Object type waar het Id attribuut in is gedefinieerd in het BMR.
     * @param functie    getter van het Id attribuut.
     * @param readOnly   of het een alleen lezen id is (dus dan geen sequence etc).
     */
    protected void annoteerIdGetter(final ObjectType objectType,
        final JavaAccessorFunctie functie, final boolean readOnly)
    {
        functie.voegAnnotatieToe(new JavaAnnotatie(JavaType.ID));
        if (!readOnly) {
            functie.voegAnnotatieToe(maakSequenceAnnotatie(objectType));
            functie.voegAnnotatieToe(maakGeneratedValueAnnotatie(objectType));
        }
        functie.voegAnnotatieToe(new JavaAnnotatie(JavaType.ACCESS,
            new AnnotatieWaardeParameter("value", JavaType.ACCESS_TYPE, "PROPERTY")));
    }

    /**
     * Maak een sequence annotatie aan.
     *
     * @param objectType object type
     * @return de sequence annotatie
     */
    protected JavaAnnotatie maakSequenceAnnotatie(final ObjectType objectType) {
        return new JavaAnnotatie(JavaType.SEQUENCE_GENERATOR,
            new AnnotatieWaardeParameter("name", objectType.getIdentCode().toUpperCase()),
            new AnnotatieWaardeParameter("sequenceName",
                objectType.getSchema().getNaam() + ".seq_"
                    + objectType.getIdentDb()));
    }

    /**
     * Maak een generated value annotatie aan.
     *
     * @param objectType object type
     * @return de generated value annotatie
     */
    protected JavaAnnotatie maakGeneratedValueAnnotatie(final ObjectType objectType) {
        return new JavaAnnotatie(JavaType.GENERATED_VALUE,
            new AnnotatieWaardeParameter("strategy", JavaType.GENERATION_TYPE, "AUTO"),
            new AnnotatieWaardeParameter("generator", objectType.getIdentCode().toUpperCase()));
    }

    /**
     * Voegt een json property annotatie toe aan het veld (als die er niet al is).
     *
     * @param veld het veld
     */
    protected void voegJsonPropertyAnnotatieToe(final JavaVeld veld) {
        if (!veld.heeftAnnotatieVanType(JavaType.JSON_PROPERTY)) {
            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_PROPERTY));
        }
    }

    /**
     * Voegt een json backreference annotatie toe aan het veld (als die er niet al is).
     *
     * @param veld het veld
     */
    protected void voegJsonBackreferenceAnnotatieToe(final JavaVeld veld) {
        if (!veld.heeftAnnotatieVanType(JavaType.JSON_BACKREFERENCE)) {
            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_BACKREFERENCE));
        }
    }

    /**
     * Voegt een json managedreference annotatie toe aan het veld (als die er niet al is).
     *
     * @param veld het veld
     */
    protected void voegJsonManagedreferenceAnnotatieToe(final JavaVeld veld) {
        if (!veld.heeftAnnotatieVanType(JavaType.JSON_MANAGEDREFERENCE)) {
            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_MANAGEDREFERENCE));
        }
    }

    /**
     * Kijkt of het meegegeven attribuut gelijk is aan het meegegeven discriminator attribuut.
     *
     * @param attribuut      het attribuut
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
     * @param <T>       type van javatypen.
     */
    protected <T extends AbstractJavaType> void rapporteerOverGegenereerdeJavaTypen(final Iterable<T> javaTypen) {
        rapportageUitvoerder.rapporteerGegenereerdeKlassen(getGeneratorNaamVoorRapportage(),
            zetJavaTypenOmNaarRapportageKlassen(javaTypen));
    }

    /**
     * Genereert voor elk java typen in javaTypen een rapportage klasse.
     *
     * @param javaTypen lijst van java typen.
     * @param <T>       type van java typen.
     * @return lijst van klassen die in de rapportage worden gebruikt.
     */
    private <T extends AbstractJavaType> List<Klasse> zetJavaTypenOmNaarRapportageKlassen(final Iterable<T> javaTypen) {
        final List<Klasse> rapportageKlassen = new ArrayList<>();
        for (final AbstractJavaType abstractJavaType : javaTypen) {
            final Klasse klasse = new Klasse();
            klasse.setNaam(abstractJavaType.getPackagePad() + "." + abstractJavaType.getNaam());
            klasse.setGeneratieTijdstip(new Date().toString());
            klasse.setGeneratorBuildTijdstip(getBuildTimestamp());
            klasse.setGeneratorVersie(getVersie());
            klasse.setMetaregisterVersie(getMetaRegisterVersie());
            rapportageKlassen.add(klasse);
        }
        return rapportageKlassen;
    }

    /**
     * Bouwt een standaard constructor met een standaard javadoc voor de opgegeven klasse, waarbij alle identiteits velden als parameters meegegeven kunnen
     * worden (en gezet worden).
     *
     * @param clazz               de klasse waarvoor de constructor moet worden gebouwd.
     * @param objectType          het objecttype waarvoor de klasse geldt.
     * @param naamgevingStrategie te gebruiken naamgeving strategie.
     * @return de gegenereerde constructor (of null)
     */
    protected Constructor genereerConstructorMetIdentiteitParameters(final JavaKlasse clazz,
        final ObjectType objectType,
        final NaamgevingStrategie naamgevingStrategie)
    {
        boolean genereerConstructor = false;
        Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, clazz);
        final StringBuilder constructorBody = new StringBuilder();
        constructorBody.append(String.format("this();%s", JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType);
        // Bij een subtype moeten we de Identiteit groep van het finale supertype hebben.
        if (this.isSubtype(objectType)) {
            groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType.getFinaalSupertype());
        }
        if (heeftAlleenIdAttribuutInIdentiteitGroep(objectType) && !isSubtype(objectType)) {
            genereerConstructor = true;
            constructorBody.delete(0, constructorBody.length());
        } else {
            for (final Groep groep : groepenVoorObjectType) {
                if (isIdentiteitGroep(groep)) {
                    final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
                    for (final Attribuut attribuut : attributen) {
                        //ID wordt niet als parameter opgenomen.
                        if (!isIdAttribuut(attribuut) && behoortTotJavaOperationeelModel(attribuut)) {
                            genereerConstructor = true;
                            final String naam;
                            final JavaType type;
                            if (isDatabaseKnipAttribuut(objectType, attribuut)) {
                                naam = bepaalVeldnaamVoorDatabaseKnipAttribuut(attribuut);
                                type = bepaalJavaTypeVoorDatabaseKnipAttribuut(attribuut);
                            } else {
                                naam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                                type = naamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                            }
                            constructor.voegParameterToe(new JavaFunctieParameter(
                                naam, type, String.format("%1$s van %2$s.", naam, objectType.getNaam())));
                            constructorBody.append(String.format("this.%1$s = %1$s;%2$s", naam,
                                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK
                                    .getWaarde()));
                        }
                    }
                    break;
                }
            }
        }
        if (genereerConstructor) {
            if (this.isSubtype(objectType)) {
                final Attribuut discriminatorAttribuut = this.bepaalDiscriminatorAttribuut(objectType);
                final Tuple discriminatorTuple = this.bepaalDiscriminatorTuple(objectType);
                final ObjectType discriminatorObjectType =
                    this.getBmrDao().getElement(discriminatorAttribuut.getType().getId(), ObjectType.class);
                // In het geval van een finaal subtype, willen we het discriminator attribuut specifiek meegeven
                // en de super constructor aanroepen.
                if (isFinaalSubtype(objectType)) {
                    final JavaType javaWrapperType = new AttribuutWrapperNaamgevingStrategie().
                        getJavaTypeVoorElement(discriminatorObjectType);
                    final JavaType javaType = new AlgemeneNaamgevingStrategie().
                        getJavaTypeVoorElement(discriminatorObjectType);
                    clazz.voegExtraImportsToe(javaWrapperType, javaType);
                    String enumExpressie = javaType.getNaam();
                    enumExpressie += ".";
                    enumExpressie += JavaGeneratieUtil.genereerNaamVoorEnumWaarde(discriminatorTuple.getIdentCode());
                    final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(discriminatorAttribuut.getIdentCode());
                    final String enumAttribuutExpressie = String.format("new %1$s(%2$s)",
                        javaWrapperType.getNaam(), enumExpressie);
                    final Map<String, String> discriminatorOverride = new HashMap<>();
                    discriminatorOverride.put(veldNaam, enumAttribuutExpressie);
                    constructor = kopieerConstructorMetSuperAanroep(constructor, discriminatorOverride);
                } else {
                    // Een tussenliggend type geeft 'gewoon' alles door aan de super constructor.
                    constructor = kopieerConstructorMetSuperAanroep(constructor);
                }
            } else {
                // In het geval van geen subtype, geven we de 'normale' assignment body voor de constructor.
                constructor.setBody(constructorBody.toString());
            }
            constructor.setJavaDoc("Standaard constructor die direct alle identificerende attributen "
                + "instantieert of doorgeeft.");
        } else {
            constructor = null;
        }
        return constructor;
    }

    /**
     * Bepaalt of een identiteitgroep van een objecttype alleen een ID attribuut heeft.
     *
     * @param objectType object type waarin de identiteitgroep wordt gecontroleerd
     * @return {@code true} indien
     */
    private boolean heeftAlleenIdAttribuutInIdentiteitGroep(final ObjectType objectType) {
        final List<Attribuut> attributenVanGroep = getBmrDao().getAttributenVanGroep(getIdentiteitGroep(objectType));

        for (final Attribuut attribuut : attributenVanGroep) {
            if (!isIdAttribuut(attribuut)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Of het attribuut van het object type een 'knip' legt tussen 2 databases. Dat is zo als het schema van het object type en het schema van het type van
     * het attribuut in een andere database zitten en het type van het attribuut een niet statisch stamgegeven object type is.
     *
     * @param objectType object type
     * @param attribuut  attribuut
     * @return of er sprake is van een knip
     */
    protected boolean isDatabaseKnipAttribuut(final ObjectType objectType, final Attribuut attribuut) {
        return objectType.getSchema().getDatabase().getId()
            != attribuut.getType().getSchema().getDatabase().getId()
            && attribuut.getType().getSoortElement().getCode().equals("OT")
            && !attribuut.getType().getSoortInhoud().equals('X');
    }

    /**
     * Genereer een veld dat 'over de grens' gaat tussen 2 databases voor identiteit groep.
     *
     * @param attribuut attribuut
     * @return het veld
     */
    protected JavaVeld genereerDatabaseKnipVeld(final Attribuut attribuut) {
        final JavaType javaType = bepaalJavaTypeVoorDatabaseKnipAttribuut(attribuut);
        final String veldNaam = bepaalVeldnaamVoorDatabaseKnipAttribuut(attribuut);
        final JavaVeld javaVeld = new JavaVeld(javaType, veldNaam);
        this.voegJsonPropertyAnnotatieToe(javaVeld);
        return javaVeld;
    }

    protected JavaType bepaalJavaTypeVoorDatabaseKnipAttribuut(final Attribuut attribuut) {
        final ObjectType naarObjectType = this.getBmrDao().getElement(
            attribuut.getType().getId(), ObjectType.class);
        final List<Attribuut> naarAttributen =
            this.getBmrDao().getAttributenVanObjectType(naarObjectType);
        final Attribuut naarIdAttribuut = naarAttributen.get(0);
        if (!naarIdAttribuut.getIdentCode().equals("ID")) {
            throw new IllegalStateException("Geen ID attribuut gevonden voor OT: " + naarObjectType);
        }
        final AttribuutType attribuutType = this.getBmrDao().getElement(
            naarIdAttribuut.getType().getId(), AttribuutType.class);
        return getJavaTypeVoorAttribuutType(attribuutType);
    }

    protected String bepaalVeldnaamVoorDatabaseKnipAttribuut(final Attribuut attribuut) {
        return GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()) + "Id";
    }

    /**
     * Bepaalt de te gebruiken slimme set interface voor een groep.
     *
     * @param groep de groep.
     * @return Java type van de slimme set interface
     */
    protected JavaType bepaalSlimmeSetInterfaceVoorGroep(final Groep groep) {
        JavaType javaInterfaceTypeVoorSet = null;
        if (kentFormeleHistorie(groep)) {
            javaInterfaceTypeVoorSet = JavaType.FORMELE_HISTORIE_SET;
        } else if (kentMaterieleHistorie(groep)) {
            javaInterfaceTypeVoorSet = JavaType.MATERIELE_HISTORIE_SET;
        }
        return javaInterfaceTypeVoorSet;
    }

    /**
     * Bepaalt de te gebruiken slimme set interface op basis van type historie.
     *
     * @param heeftMaterieleHistorie materiele historie (true) of formele (false).
     * @return Java type van de slimme set interface
     */
    protected JavaType bepaalSlimmeSetInterface(final boolean heeftMaterieleHistorie) {
        final JavaType javaInterfaceTypeVoorSet;
        if (heeftMaterieleHistorie) {
            javaInterfaceTypeVoorSet = JavaType.MATERIELE_HISTORIE_SET;
        } else {
            javaInterfaceTypeVoorSet = JavaType.FORMELE_HISTORIE_SET;
        }
        return javaInterfaceTypeVoorSet;
    }

    /**
     * Bepaalt de te gebruiken slimme set implementatie voor een groep.
     *
     * @param groep de groep
     * @return Java type van de slimme set implementatie
     */
    protected JavaType bepaalSlimmeSetImplementatieVoorGroep(final Groep groep) {
        JavaType javaInterfaceTypeVoorSet = null;
        if (kentFormeleHistorie(groep)) {
            javaInterfaceTypeVoorSet = JavaType.FORMELE_HISTORIE_SET_IMPL;
        } else if (kentMaterieleHistorie(groep)) {
            javaInterfaceTypeVoorSet = JavaType.MATERIELE_HISTORIE_SET_IMPL;
        }
        return javaInterfaceTypeVoorSet;
    }

    /**
     * Bepaalt de te gebruiken slimme set implementatie op basis van type historie.
     *
     * @param heeftMaterieleHistorie materiele historie (true) of formele (false).
     * @return Java type van de slimme set implementatie
     */
    protected JavaType bepaalSlimmeSetImplementatie(final boolean heeftMaterieleHistorie) {
        final JavaType javaImplementatieTypeVoorSet;
        if (heeftMaterieleHistorie) {
            javaImplementatieTypeVoorSet = JavaType.MATERIELE_HISTORIE_SET_IMPL;
        } else {
            javaImplementatieTypeVoorSet = JavaType.FORMELE_HISTORIE_SET_IMPL;
        }
        return javaImplementatieTypeVoorSet;
    }

    /**
     * Bepaalt het {@link JavaType} voor het id attribuut van het opgegeven objecttype.
     *
     * @param objectType het objecttype waarvoor het type van het id attribuut benodigd is.
     * @return het JavaType van het id attribuut van het objecttype.
     */
    protected JavaType getJavaTypeVoorIdVeld(final ObjectType objectType) {
        JavaType idJavaType = null;

        final List<Attribuut> attributen = getBmrDao().getAttributenVanObjectType(objectType);
        for (final Attribuut attribuut : attributen) {
            if (behoortTotJavaOperationeelModel(attribuut) && isIdAttribuut(attribuut)) {
                final AttribuutType attribuutType = getBmrDao().getAttribuutTypeVoorAttribuut(attribuut);
                idJavaType = getJavaTypeVoorAttribuutType(attribuutType);
                break;
            }
        }
        return idJavaType;
    }

    /**
     * Genereert een constructor waarbij je alle parameters kunt meegeven.
     *
     * @param klasse de klasse waarvoor de constructor wordt gegenereerd
     * @return een constructor met alle parameters die tevens in de body worden geinitalisserd
     */
    protected Constructor genereerConstructorMetAlleParametersVoorKlasse(final JavaKlasse klasse) {
        final Constructor cons = new Constructor(JavaAccessModifier.PUBLIC, klasse);
        final StringBuilder consBody = new StringBuilder();
        for (final JavaVeld javaVeld : klasse.getVelden()) {
            if (!javaVeld.getNaam().equalsIgnoreCase("id")) {
                cons.voegParameterToe(new JavaFunctieParameter(
                    javaVeld.getNaam(), javaVeld.getType(),
                    String.format("%1$s van %2$s", javaVeld.getNaam(), klasse.getNaam())));
                consBody.append(String.format("this.%1$s = %1$s;%2$s", javaVeld.getNaam(),
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            }
        }
        cons.setBody(consBody.toString());
        return cons;
    }

    /**
     * Geeft een OS onafhankelijke newline String terug.
     *
     * @return newline
     */
    protected String newline() {
        return JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde();
    }

    /**
     * Retourneert een code fragment voor de initialisatie van een Set t.b.v. een constructor in een klasse. Hierbij wordt, indien nodig, ook een import
     * toegevoegd aan de klasse als deze wordt gebruikt in de Set initialisatie.
     *
     * @param setType             het type van de set die gebruikt moet worden.
     * @param objectType          het objecttype waarvoor de Set wordt geinitialiseerd.
     * @param javaTypeVoorElement het Java Type van de elementen in de Set.
     * @param klasse              de klasse waar het code fragment voor wordt gebruikt.
     * @return een code fragment dat een set initialiseert.
     */
    protected String bouwSetInitialisatieCodeFragmentEnVoegImportsToe(final JavaType setType,
        final ObjectType objectType,
        final JavaType javaTypeVoorElement,
        final JavaKlasse klasse,
        final boolean isGesorteerd,
        final boolean isStandaardComparator,
        final JavaType comparatorJavaType)
    {
        klasse.voegExtraImportsToe(setType);

        final StringBuilder codeFragment = new StringBuilder();
        codeFragment.append("new ");
        codeFragment.append(setType.getNaam());
        codeFragment.append("<").append(javaTypeVoorElement.getNaam()).append(">");
        codeFragment.append("(");
        if (isGesorteerd) {
            klasse.voegExtraImportsToe(comparatorJavaType);
            if (JavaType.HIS_VOLLEDIG_COMPARATOR_FACTORY.equals(comparatorJavaType)) {
                if (isStandaardComparator) {
                    codeFragment.append("HisVolledigComparatorFactory.getStandaardComparator()");
                } else {
                    codeFragment.append(String.format("HisVolledigComparatorFactory.getComparatorVoor%1$s()", objectType.getIdentCode()));
                }
            } else {
                if (isStandaardComparator) {
                    codeFragment.append("ComparatorFactory.getStandaardComparator()");
                } else {
                    codeFragment.append(String.format("ComparatorFactory.getComparatorVoor%1$s()", objectType.getIdentCode()));
                }
            }
        }
        codeFragment.append(")");
        return codeFragment.toString();
    }

    /**
     * Voegt sorterings annotatie toe aan een veld, waarbij de sortering standaard de natuurlijke sortering op het object van de collectie gebruikt. Indien
     * niet de natuurlijke sortering gebruikt dient te worden, maar een specifieke {@link java.util.Comparator}, dan dient er een JavaType meegegeven te
     * worden die verwijst naar de te gebruiken comparator.
     *
     * @param veld       het veld waar de annotatie aan toegevoegd dient te worden.
     * @param comparator optionele parameter die het Java Type opgeeft van een eventueel te gebruiken comparator.
     */
    protected void annoteerCollectieVeldVoorSortering(final JavaVeld veld, final JavaType comparator) {
        final JavaAnnotatie annotatie = new JavaAnnotatie(JavaType.SORT);
        annotatie.voegParameterToe(new AnnotatieWaardeParameter("type", JavaType.SORT_TYPE, "COMPARATOR"));

        if (comparator != null) {
            annotatie.voegParameterToe(new AnnotatieWaardeParameter("comparator", comparator, "class"));
        }

        // Indien het een JSON property is en een comparator wordt gebruikt voor sortering, dan is er een aparte
        // JSON Deserialisatie annotatie benodigd
        if (comparator != null && veld.heeftAnnotatieVanType(JavaType.JSON_PROPERTY)) {
            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_DESERIALIZE,
                new AnnotatieWaardeParameter("as", JavaType.VOLGNUMMER_SET_IMPLEMENTATIE, "class")));
        }
        veld.voegAnnotatieToe(annotatie);
    }

    /**
     * Genereer een attribuut wrapper klasse voor het onderliggende gegeven. Dat gegeven is een attribuut type of een object type en representeert een
     * statisch stamgegeven, dynamisch stamgegeven of een basis (scalair) type.
     *
     * @param teWrappenObject voor welk element een wrapper klasse gemaakt moet worden
     * @param genericParameterType   het generic parameter type
     * @return de gemaakte java wrapper klasse
     */
    protected JavaKlasse genereerAttribuutWrapperKlasse(final GeneriekElement teWrappenObject,
        final JavaType genericParameterType)
    {
        final BmrElementSoort soort =
            BmrElementSoort.getBmrElementSoortBijCode(teWrappenObject.getSoortElement().getCode());
        final BmrSoortInhoud soortInhoud;
        if (soort == BmrElementSoort.OBJECTTYPE) {
            soortInhoud = BmrSoortInhoud.getBmrSoortInhoudVoorCode(teWrappenObject.getSoortInhoud());
        } else {
            soortInhoud = null;
        }

        final AttribuutWrapperNaamgevingStrategie attribuutWrapperNaamgevingStrategie = new AttribuutWrapperNaamgevingStrategie();


        final JavaKlasse klasse = new JavaKlasse(attribuutWrapperNaamgevingStrategie.getJavaTypeVoorElement(teWrappenObject),
            "Attribuut wrapper klasse voor " + teWrappenObject.getNaam() + ".");
        // Voor Datum attribuut typen abstracte basis datum attribuuttype klasse als superklass gebruiken.
        if (DATUM_ATTRIBUUTTYPE_ID == teWrappenObject.getId()
            || DATUM_EVT_DEELS_ONBEKEND_ATTRIBUUTTYPE_ID == teWrappenObject.getId())
        {
            klasse.setExtendsFrom(JavaType.DATUM_BASIS_ATTRIBUUT);
        } else {
            // Het java type is de generic parameter voor het abstract attribuut.
            klasse.setExtendsFrom(new JavaType(JavaType.ABSTRACT_ATTRIBUUT, genericParameterType));
        }
        // Maak embeddable, zodat de properties vanuit de refererende model klasse direct opgenomen kunnen worden.
        klasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDABLE));
        // Zet de access type op property, zodat er via de getter en setter specifieke configuratie mogelijk is.
        klasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.ACCESS,
            new AnnotatieWaardeParameter("value", JavaType.ACCESS_TYPE, "PROPERTY")));
        this.genereerAttribuutWrapperConstructoren(klasse, genericParameterType, soort);
        this.genereerWaardeGetterVoorAttribuutWrapper(klasse, genericParameterType,
            soort, soortInhoud, teWrappenObject.getId());
        return klasse;
    }

    /**
     * Genereer 2 constructoren voor een attribuut klasse: een default private constructor en een constructor met een waarde parameter.
     *
     * @param klasse               de java klasse
     * @param genericParameterType het generic parameter type
     * @param soort                het BMR soort element
     */
    protected void genereerAttribuutWrapperConstructoren(final JavaKlasse klasse, final JavaType genericParameterType,
        final BmrElementSoort soort)
    {
        final String klasseNaam = klasse.getType().getNaam();

        // Private default constructor om te voorkomen dat dit attribuuttype wordt geinstantieerd met een lege waarde.
        final Constructor defaultConstructor = new Constructor(JavaAccessModifier.PRIVATE, klasse);
        defaultConstructor.setJavaDoc("Lege private constructor voor " + klasseNaam
            + ", om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.");
        defaultConstructor.setBody("super(null);");
        klasse.voegConstructorToe(defaultConstructor);

        // Constructor met waarde parameter.
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, klasse);
        constructor.setJavaDoc("Constructor voor " + klasseNaam + " die de waarde toekent.");
        if (soort == BmrElementSoort.ATTRIBUUTTYPE) {
            constructor.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_CREATOR));
        }
        constructor.voegParameterToe(new JavaFunctieParameter("waarde", genericParameterType,
            "De waarde van het attribuut."));
        constructor.setBody("super(waarde);");
        klasse.voegConstructorToe(constructor);
    }

    /**
     * Genereer een getter en een setter voor de waarde van een attribuut wrapper klasse. Deze zijn nodig voor custom configuratie, die niet generiek op
     * het veld gezet kan worden. Die custom configuratie wordt ook gezet hier, verschillend naar gelang wat voor soort element gewrapped wordt.
     *
     * @param klasse               de java klasse
     * @param genericParameterType het generic parameter type
     * @param soort                het BMR soort element
     * @param soortInhoud          de BMR soort inhoud
     * @param elementId            id van te wrappen element
     */
    protected void genereerWaardeGetterVoorAttribuutWrapper(final JavaKlasse klasse,
        final JavaType genericParameterType,
        final BmrElementSoort soort,
        final BmrSoortInhoud soortInhoud,
        final int elementId)
    {
        final JavaAccessorFunctie getter = new JavaAccessorFunctie("waarde", genericParameterType);
        getter.setJavaDoc("Retourneert de waarde van het attribuut." + newline()
            + "Bevat de specifieke configuratie voor het soort gewrapte object.");
        getter.setForceerJavaDoc(true);
        getter.setReturnWaardeJavaDoc("de waarde van het attribuut");
        // Custom getter body, als extra bevestiging dat deze getter puur voor de annotaties bestaat.
        getter.setBody("return super.getWaarde();");
        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        if (soort == BmrElementSoort.ATTRIBUUTTYPE) {
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_VALUE));
        } else {
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_PROPERTY));
            if (soortInhoud == BmrSoortInhoud.STATISCH_STAMGEGEVEN) {
                // Custom uitzondering voor enums die niet op ordinal gemapt moeten worden
                if (elementId == ID_DATABASE_OBJECT) {
                    getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.TYPE,
                        new AnnotatieWaardeParameter("type", genericParameterType.getNaam())));
                } else {
                    if (genericParameterType.getPackagePad().contains("stamgegeven")) {
                        if (elementId == ID_ELEMENT) {
                            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.TYPE,
                                new AnnotatieWaardeParameter("type", genericParameterType.getNaam())));
                        } else {
                            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.ENUMERATED));
                        }
                    } else {
                        // In dit geval hebben we te maken met een vaste waarde enum attribuut,
                        // die moet via een Hibernate custom type gemapt worden.
                        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.TYPE,
                            new AnnotatieWaardeParameter("type", genericParameterType.getNaam())));
                    }
                }
            } else if (soortInhoud == BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN) {
                // Stamgegevens worden op Eager gezet, daar voor serialisatie naast id ook de code nodig is. Indien
                // alleen ID nodig zou zijn, zou LAZY met SUBSELECT hier een betere optie zijn.
                getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.FETCH,
                    new AnnotatieWaardeParameter("value", JavaType.FETCH_MODE, "SELECT")));
                getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.MANY_TO_ONE,
                    new AnnotatieWaardeParameter("fetch", JavaType.FETCH_TYPE, "EAGER")));
            }
        }

        // Expres voegFunctieToe ipv voegGetterToe, vanwege eigen body implementatie.
        klasse.voegFunctieToe(getter);
    }

    /**
     * Voegt functies toe voor het ophalen van indicaties.
     *
     * @param javaKlasse          De java klasse waar functies aan toegevoegd worden.
     * @param naamgevingStrategie de te gebruiken naamgeving strategie.
     * @param transientAnnotatie  of er een transient annotatie op de getters moet komen
     */
    protected void voegGettersToeVoorIndicaties(final JavaKlasse javaKlasse,
        final NaamgevingStrategie naamgevingStrategie,
        final boolean transientAnnotatie)
    {
        final ObjectType soortIndicatieObjectType = this.getBmrDao().getElement(ID_SOORT_INDICATIE, ObjectType.class);
        final JavaType soortIndicatieJavaType = new AlgemeneNaamgevingStrategie()
            .getJavaTypeVoorElement(soortIndicatieObjectType);
        final ObjectType persoonIndicatieObjectType = this.getBmrDao().getElement(ID_PERSOON_INDICATIE, ObjectType.class);
        final JavaType persoonInidcatieJavaType = naamgevingStrategie.getJavaTypeVoorElement(persoonIndicatieObjectType);
        for (final Tuple tuple : soortIndicatieObjectType.getTuples()) {
            final JavaFunctie getter = new JavaFunctie(JavaAccessModifier.PUBLIC,
                persoonInidcatieJavaType, "get" + tuple.getIdentCode(), null);
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            getter.setFinal(true);
            if (transientAnnotatie) {
                getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.TRANSIENT));
            }
            final String indicatieNaam = GeneratieUtil.lowerTheFirstCharacter(tuple.getIdentCode());
            final StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append(String.format("%1$s %2$s = null;" + newline(),
                persoonInidcatieJavaType.getNaam(), indicatieNaam));
            bodyBuilder.append(String.format("for (%1$s persoonIndicatie : this.getIndicaties()) {" + newline(),
                persoonInidcatieJavaType.getNaam()));
            bodyBuilder.append(String.format("if (persoonIndicatie.getSoort().getWaarde() == %1$s.%2$s) {"
                    + newline(), soortIndicatieJavaType.getNaam(),
                JavaGeneratieUtil.genereerNaamVoorEnumWaarde(tuple.getIdentCode())));
            bodyBuilder.append(String.format("%1$s = persoonIndicatie;" + newline(), indicatieNaam));
            bodyBuilder.append("}").append(newline());
            bodyBuilder.append("}").append(newline());
            bodyBuilder.append(String.format("return %1$s;", indicatieNaam));
            getter.setBody(bodyBuilder.toString());
            javaKlasse.voegExtraImportsToe(soortIndicatieJavaType);
            javaKlasse.voegFunctieToe(getter);
        }
    }

    /**
     * Voegt een veld en bijbehorende getter toe voor een bestaansperiode veld.
     *
     * @param objectType          het stamgegeven waarvoor dit geldt.
     * @param naamgevingStrategie de naamgeving stategie voor het veld.
     */
    protected void voegBestaansPeriodeVeldenEnAccessorsToe(final AbstractJavaImplementatieType klasseOfEnum,
        final ObjectType objectType,
        final NaamgevingStrategie naamgevingStrategie)
    {
        final AttribuutType datumAttribuutType =
            this.getBmrDao().getElement(DATUM_EVT_DEELS_ONBEKEND_ATTRIBUUTTYPE_ID, AttribuutType.class);
        final JavaType datumType = naamgevingStrategie.getJavaTypeVoorElement(datumAttribuutType);

        // Datum aanvang geldigheid
        final JavaVeld datumAanvangGeldigheidVeld = new JavaVeld(datumType, "datumAanvangGeldigheid",
            "datum aanvang geldigheid");
        if (klasseOfEnum instanceof JavaKlasse) {
            datumAanvangGeldigheidVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDED));
            datumAanvangGeldigheidVeld.voegAnnotatieToe(
                new JavaAnnotatie(
                    JavaType.ATTRIBUTE_OVERRIDE,
                    new AnnotatieWaardeParameter("name", datumAanvangGeldigheidVeld.getType().getNaam() + ".WAARDE_VELD_NAAM", true),
                    new AnnotatieAnnotatieParameter(
                        "column", new JavaAnnotatie(JavaType.COLUMN,
                        new AnnotatieWaardeParameter("name", "dataanvgel")))));
        }
        klasseOfEnum.voegVeldToe(datumAanvangGeldigheidVeld);

        final JavaAccessorFunctie datumAanvangGeldiheidgetter = new JavaAccessorFunctie(datumAanvangGeldigheidVeld);
        datumAanvangGeldiheidgetter.setJavaDoc("Retourneert de datum aanvang geldigheid voor " + objectType.getNaam() + ".");
        datumAanvangGeldiheidgetter.setReturnWaardeJavaDoc("Datum aanvang geldigheid voor " + objectType.getNaam());
        datumAanvangGeldiheidgetter.setFinal(true);
        klasseOfEnum.voegGetterToe(datumAanvangGeldiheidgetter);

        // Datum einde geldigheid
        final JavaVeld datumEindeGeldigheidVeld = new JavaVeld(datumType, "datumEindeGeldigheid",
            "datum einde geldigheid");
        if (klasseOfEnum instanceof JavaKlasse) {
            datumEindeGeldigheidVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDED));
            datumEindeGeldigheidVeld.voegAnnotatieToe(
                new JavaAnnotatie(
                    JavaType.ATTRIBUTE_OVERRIDE,
                    new AnnotatieWaardeParameter("name", datumEindeGeldigheidVeld.getType().getNaam() + ".WAARDE_VELD_NAAM", true),
                    new AnnotatieAnnotatieParameter(
                        "column", new JavaAnnotatie(JavaType.COLUMN,
                        new AnnotatieWaardeParameter("name", "dateindegel")))));
        }
        klasseOfEnum.voegVeldToe(datumEindeGeldigheidVeld);

        final JavaAccessorFunctie datumEindeGeldiheidgetter = new JavaAccessorFunctie(datumEindeGeldigheidVeld);
        datumEindeGeldiheidgetter.setJavaDoc("Retourneert de datum einde geldigheid voor " + objectType.getNaam() + ".");
        datumEindeGeldiheidgetter.setReturnWaardeJavaDoc("Datum einde geldigheid voor " + objectType.getNaam());
        datumEindeGeldiheidgetter.setFinal(true);
        klasseOfEnum.voegGetterToe(datumEindeGeldiheidgetter);
    }

    /**
     * Bepaalt de naam van een enumeratiewaarde voor een vwElement. Hierbij is de structuur als volgt: OUDER_GROEP_NAAM
     *
     * @param vwElement Het vwElement.
     * @return De naam voor de enumeratiewaarde.
     */
    protected String bepaalNaamVoorVwElementEnumWaarde(final VwElement vwElement) {
        return vwElement.getIdentifier().replaceAll("\\.", "_").toUpperCase();
    }

    protected GeneriekElement getGeneriekElementVoorVwElement(final VwElement vwElement) {
        GeneriekElement generiekElement =
            getBmrDao().getElementVoorSyncIdVanLaag(vwElement.getId(), BmrLaag.LOGISCH,
                GeneriekElement.class);
        if (generiekElement == null && vwElement.getAliasvan() != null) {
            generiekElement = getBmrDao().getElementVoorSyncIdVanLaag(vwElement.getAliasvan(), BmrLaag.LOGISCH, GeneriekElement.class);
        }
        return generiekElement;
    }

    /**
     * Genereert de 'Element getElement()' functie die een instantie van de Element enum moet retourneren behorende bij de HisXXXX groep.
     *
     * @param klasse          de HisXXX klasse
     * @param generiekElement de groep waar deze klasse bij hoort.
     */
    protected void voegGetElementFunctieToe(final AbstractJavaImplementatieType klasse,
        final GeneriekElement generiekElement)
    {
        klasse.voegSuperInterfaceToe(JavaType.ELEMENT_IDENTIFICEERBAAR);
        String javaDoc = "Retourneert het Element behorende bij ";
        String returnTypeJavaDoc = "Element enum instantie behorende bij ";
        boolean functieMoetFinalZijn = true;
        if (BmrElementSoort.GROEP.hoortBijCode(generiekElement.getSoortElement().getCode())) {
            javaDoc += "deze groep.";
            returnTypeJavaDoc += "deze groep.";
        } else if (BmrElementSoort.OBJECTTYPE.hoortBijCode(generiekElement.getSoortElement().getCode())) {
            final ObjectType objectType = getBmrDao().getElement(generiekElement.getId(), ObjectType.class);
            if (isHierarchischType(objectType) && !isFinaalSubtype(objectType)) {
                functieMoetFinalZijn = false;
            }
            javaDoc += "dit objecttype.";
            returnTypeJavaDoc += "dit objecttype.";
        }
        final JavaFunctie getElementFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC, JavaType.ELEMENT_ENUM, "getElementIdentificatie",
            returnTypeJavaDoc);
        getElementFunctie.setFinal(functieMoetFinalZijn);
        getElementFunctie.setJavaDoc(javaDoc);
        //Bepaal de instantie van de Element enum die geretourneerd moet worden. Syncid van de groep is gelijk aan
        //id van de groep in de GEN_ELEMENT view.
        final VwElement vwElementVoorGroep = getBmrDao().getVwElementMetId(generiekElement.getSyncid());
        final String elementEnumInstantieVoorGroep = bepaalNaamVoorVwElementEnumWaarde(vwElementVoorGroep);
        getElementFunctie.setBody("return " + JavaType.ELEMENT_ENUM.getNaam() + "."
            + elementEnumInstantieVoorGroep + ";");
        klasse.voegFunctieToe(getElementFunctie);
    }

    /**
     * Genereer een setter voor het Id veld in de klasse, deze setter wordt verplicht door JPA.
     *
     * @param klasse de klasse waarin idVeld zich bevindt en waaraan de setter moet worden toegevoegd
     * @param idVeld het idVeld in de java klasse
     */
    protected void genereerIdVeldSetterVoorKlasse(final JavaKlasse klasse, final JavaVeld idVeld) {
        final JavaMutatorFunctie setter = new JavaMutatorFunctie(JavaAccessModifier.PRIVATE, "setID", idVeld.getNaam(), "id");
        setter.voegParameterToe(new JavaFunctieParameter("id", idVeld.getType()));
        setter.setJavaDoc("Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. "
            + "We maken de functie private voor de zekerheid.");
        setter.getParameters().get(0).setJavaDoc("Id");
        klasse.voegSetterToe(setter);
    }

    /**
     * Voegt een generated annotatie toe.
     *
     * @param abstractJavaTypes het java type
     */
    protected void voegGeneratedAnnotatiesToe(final List<? extends AbstractJavaType> abstractJavaTypes,
        final GeneratorConfiguratie generatorConfiguratie)
    {
        for (final AbstractJavaType javaType : abstractJavaTypes) {
            javaType.voegAnnotatieToe(
                new JavaAnnotatie(JavaType.GENERATED, new AnnotatieWaardeParameter("value", generatorConfiguratie.getGeneratorNaam())));
        }
    }

    protected final boolean isPersoonIndicatieObject(final ObjectType objectType) {
        return "PersIndicatie".equals(objectType.getIdentDb());
    }
}
