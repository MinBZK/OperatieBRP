/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.EnumeratieWaarde;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaEnumeratie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaResource;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.ElementEnumNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.java.writer.ResourceWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.VwElement;

import org.springframework.stereotype.Component;

/**
 * Genereert de java-enumeratie klasse voor elementen (Element). Als bron wordt een view in het BMR gebruikt. Deze klasse wijkt af van de normale enums en
 * is daarom ge-exclude in de StatischeStamgegevensGenerator.
 */
@Component("elementGenerator")
public class ElementGenerator extends AbstractJavaGenerator {

    private final NaamgevingStrategie elementEnumNaamgevingStrategie = new AlgemeneNaamgevingStrategie();

    @Override
    protected GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.ELEMENT_GENERATOR;
    }

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {


        final JavaEnumeratie javaEnumeratie = genereerElementEnumeratie();
        final StringBuilder naamProperties = new StringBuilder();
        final StringBuilder elementNaamProperties = new StringBuilder();
        final StringBuilder soortProperties = new StringBuilder();
        final StringBuilder groepProperties = new StringBuilder();
        final StringBuilder objectIdProperties = new StringBuilder();
        final StringBuilder hisDbObjectProperties = new StringBuilder();
        final StringBuilder volgnummerProperties = new StringBuilder();
        genereerWaardenVoorEnumeratie(javaEnumeratie, naamProperties, elementNaamProperties, soortProperties, groepProperties, objectIdProperties,
                hisDbObjectProperties, volgnummerProperties);


        // Enum
        final JavaWriter<JavaEnumeratie> enumWriter = javaWriterFactory(generatorConfiguratie, JavaEnumeratie.class);
        final List<JavaEnumeratie> javaEnumeraties = Collections.singletonList(javaEnumeratie);
        voegGeneratedAnnotatiesToe(javaEnumeraties, generatorConfiguratie);
        final List<JavaEnumeratie> geschrevenEnumeraties = enumWriter.genereerEnSchrijfWeg(javaEnumeraties, this);
        rapporteerOverGegenereerdeJavaTypen(geschrevenEnumeraties);

        // Resources
        final JavaResource naamResource = new JavaResource("element.naam.properties", "gegevens");
        naamResource.setContents(naamProperties.toString());
        final JavaResource elementNaamResource = new JavaResource("element.elementnaam.properties", "gegevens");
        elementNaamResource.setContents(elementNaamProperties.toString());
        final JavaResource soortResource = new JavaResource("element.soort.properties", "gegevens");
        soortResource.setContents(soortProperties.toString());
        final JavaResource groepResource = new JavaResource("element.groep.properties", "gegevens");
        groepResource.setContents(groepProperties.toString());
        final JavaResource objectIdResource = new JavaResource("element.object.properties", "gegevens");
        objectIdResource.setContents(objectIdProperties.toString());
        final JavaResource hisDbObjectResource = new JavaResource("element.hisdbobject.properties", "gegevens");
        hisDbObjectResource.setContents(hisDbObjectProperties.toString());
        final JavaResource volgnummerResource = new JavaResource("element.volgnummer.properties", "gegevens");
        volgnummerResource.setContents(volgnummerProperties.toString());

        final List<JavaResource> javaResources = Arrays.asList(naamResource, elementNaamResource, soortResource, groepResource, objectIdResource, hisDbObjectResource,
            volgnummerResource);
        final ResourceWriter resourceWriter = new ResourceWriter(zetPadOmNaarResources(generatorConfiguratie.getPad()), generatorConfiguratie.isOverschrijf());
        resourceWriter.schrijfWeg(javaResources);
    }

    private String zetPadOmNaarResources(final String pad) {
		return pad.replaceAll("java", "resources");
	}

	/**
     * Genereert de enumeraties zelf voor het type Element.
     *
     * @return De JavaEnumeratie met enumeraties er in.
     */
    private JavaEnumeratie genereerElementEnumeratie() {
        final ObjectType objectType = getBmrDao().getElement(ID_ELEMENT, ObjectType.class);

        final JavaType javaTypeVoorElementNormaalgesproken = elementEnumNaamgevingStrategie.getJavaTypeVoorElement(objectType);
        final JavaType javaTypeVoorElementMetAfwijkendeNaam = new JavaType("ElementEnum", javaTypeVoorElementNormaalgesproken.getPackagePad());
        final JavaEnumeratie javaEnum = new JavaEnumeratie(
            javaTypeVoorElementMetAfwijkendeNaam,
            bouwJavadocVoorElement(objectType));

        javaEnum.voegSuperInterfaceToe(JavaType.PERSISTENT_ENUM);
        this.voegVeldEnGetterToeAanEnum(javaEnum, JavaType.INTEGER, "id");

        //Kan het stamgegeven gesynchroniseerd worden?
        if (objectType.getInBericht() != null && objectType.getInBericht() == 'J') {
            javaEnum.voegSuperInterfaceToe(JavaType.SYNCHRONISEERBAAR_STAMGEGEVEN);
        }

        //Constructor die alle velden initialiseert
        final Constructor enumConstructor = genereerConstructorVoorEnum(javaEnum);

		maakInitialisatieVeld(javaEnum, enumConstructor, objectType, "naam", "naam", null);
        maakInitialisatieVeld(javaEnum, enumConstructor, objectType, "elementNaam", "elementNaam", null);
		maakInitialisatieVeld(javaEnum, enumConstructor, objectType, "soort", "soort", null);
		maakInitialisatieVeld(javaEnum, enumConstructor, objectType, "groep", "groepId", JavaType.INTEGER);
		maakInitialisatieVeld(javaEnum, enumConstructor, objectType, "objecttype", "objectTypeId", JavaType.INTEGER);
		maakInitialisatieVeld(javaEnum, enumConstructor, objectType, "HisDatabaseObject", "hisDbObjectId", JavaType.INTEGER);
		maakInitialisatieVeld(javaEnum, enumConstructor, objectType, "volgnummer", "volgnummer", JavaType.INTEGER);

		javaEnum.voegConstructorToe(enumConstructor);

        // Getter voor groep obv ElementEnum
        final JavaFunctie groepFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC, JavaType.ELEMENT_ENUM, "getGroep", "de groep element enum, null indien dit element niet tot een groep behoort");
        groepFunctie.setJavaDoc("Geef de groep waar dit element toe behoort.");
        groepFunctie.setBody("if(groepId == null) { return null; } for(final ElementEnum element : ElementEnum.values()) { if(element.id.equals(groepId)) { return element; } } throw new IllegalArgumentException(\"Groep niet gevonden.\");");
        javaEnum.voegFunctieToe(groepFunctie);

        // Getter voor objectType obv ElementEnum
        final JavaFunctie objectTypeFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC, JavaType.ELEMENT_ENUM, "getObjectType", "objecttype element "
            + "enum, null indien "
            + "dit element niet tot een objecttype behoort");
        objectTypeFunctie.setJavaDoc("Geef het objecttype waar dit element toe behoort.");
        objectTypeFunctie.setBody("if (objectTypeId == null) { return null; } for(final ElementEnum element : ElementEnum.values()) { if(element.id"
            + ".equals(objectTypeId)"
            + ") { return element; } } throw new IllegalArgumentException(\"Objecttype niet gevonden.\");");
        javaEnum.voegFunctieToe(objectTypeFunctie);

        javaEnum.setHandmatigeCodeOnderin("/** Helper voor de element enum om waarden op te halen uit de gegenereerde properties bestanden. */\n" +

                "private final static class Helper {\n"+
                "private final static Helper HELPER = new Helper();\n"+
        		"private final Properties naamProperties = new Properties();\n"+
                "private final Properties elementNaamProperties = new Properties();\n"+
        		"private final Properties soortProperties = new Properties();\n"+
		        "private final Properties groepProperties = new Properties();\n"+
		        "private final Properties objectProperties = new Properties();\n"+
		        "private final Properties hisDbObjectProperties = new Properties();\n"+
		        "private final Properties volgnummerProperties = new Properties();\n"+
		        "{\n"+

		        	"try(final InputStream isNaam = ElementEnum.class.getResourceAsStream(\"/gegevens/element.naam.properties\");\n" +
                        "final InputStream isElementNaam = ElementEnum.class.getResourceAsStream(\"/gegevens/element.elementnaam.properties\"); \n" +
		        		"final InputStream isSoort = ElementEnum.class.getResourceAsStream(\"/gegevens/element.soort.properties\");\n" +
		        		"final InputStream isGroep = ElementEnum.class.getResourceAsStream(\"/gegevens/element.groep.properties\");\n"+
		        		"final InputStream isObject = ElementEnum.class.getResourceAsStream(\"/gegevens/element.object.properties\");\n"+
		        		"final InputStream isHisDbObject = ElementEnum.class.getResourceAsStream(\"/gegevens/element.hisdbobject.properties\");\n"+
		        		"final InputStream isVolgnummer = ElementEnum.class.getResourceAsStream(\"/gegevens/element.volgnummer.properties\")) {\n"+
		        		"naamProperties.load(isNaam);\n"+
                        "elementNaamProperties.load(isElementNaam);\n"+
		    			"soortProperties.load(isSoort);\n"+
		    	    	"groepProperties.load(isGroep);\n"+
		    	    	"objectProperties.load(isObject);\n"+
		    	    	"hisDbObjectProperties.load(isHisDbObject);\n"+
		    	    	"volgnummerProperties.load(isVolgnummer);\n"+
		    		"} catch (final IOException e) {\n"+
		    			"throw new IllegalArgumentException(\"Kan element properties niet laden.\", e);\n"+
		    		"}\n"+
		        "}\n"+
		        "/**\n"+
		        " * Geef de naam voor het element.\n"+
		        " *\n"+
		        " * @param id id voor het element\n"+
		        " * @return naam voor het element\n"+
		        " */\n"+
		    	"public String geefNaamVoorElementId(final Integer id) {\n"+
		    	"	return naamProperties.getProperty(id.toString());\n"+
		    	"}\n"+
                "/**\n"+
                " * Geef de elementnaam voor het element.\n"+
                " *\n"+
                " * @param id id voor het element\n"+
                " * @return elementnaam voor het element\n"+
                " */\n"+
                "public String geefElementNaamVoorElementId(final Integer id) {\n"+
                "	return elementNaamProperties.getProperty(id.toString());\n"+
                "}\n"+
		        "/**\n"+
		        " * Geef de soort voor het element.\n"+
		        " *\n"+
		        " * @param id id voor het element\n"+
		        " * @return soort voor het element\n"+
		        " */\n"+
		    	"public SoortElement geefSoortVoorElementId(final Integer id) {\n"+
		    	"	final String value = soortProperties.getProperty(id.toString());\n"+
		    	"	return value == null ? null : SoortElement.valueOf(value);\n"+
		    	"}\n"+
		        "/**\n"+
		        " * Geef de groep voor het element.\n"+
		        " *\n"+
		        " * @param id id voor het element\n"+
		        " * @return groep voor het element\n"+
		        " */\n"+
		    	"public Integer geefGroepIdVoorElementId(final Integer id) {\n"+
		    	"	final String value = groepProperties.getProperty(id.toString());\n"+
		    	"	return value == null ? null : Integer.valueOf(value);\n"+
		    	"}\n"+
                "/**\n"+
                " * Geef de object voor het element.\n"+
                " *\n"+
                " * @param id id voor het element\n"+
                " * @return object voor het element\n"+
                " */\n"+
                "public Integer geefObjectTypeIdVoorElementId(final Integer id) {\n"+
                "	final String value = objectProperties.getProperty(id.toString());\n"+
                "	return value == null ? null : Integer.valueOf(value);\n"+
                "}\n"+
		        "/**\n"+
		        " * Geef de his db object voor het element.\n"+
		        " *\n"+
		        " * @param id id voor het element\n"+
		        " * @return his db object voor het element\n"+
		        " */\n"+
		    	"public Integer geefHisDbObjectIdVoorElementId(final Integer id) {\n"+
		    	"	final String value = hisDbObjectProperties.getProperty(id.toString());\n"+
		    	"	return value == null ? null : Integer.valueOf(value);\n"+
		    	"}\n"+
		        "/**\n"+
		        " * Geef het volgnummer voor het element.\n"+
		        " *\n"+
		        " * @param id id voor het element\n"+
		        " * @return volgnummer voor het element\n"+
		        " */\n"+
		    	"public Integer geefVolgnummerVoorElementId(final Integer id) {\n"+
		    	"	final String value = volgnummerProperties.getProperty(id.toString());\n"+
		    	"	return value == null ? null : Integer.valueOf(value);\n"+
		    	"}\n"+
		    "}");
        javaEnum.voegExtraImportsToe(JavaType.PROPERTIES, JavaType.IO_EXCEPTION);
        javaEnum.voegExtraImportsToe(JavaType.PROPERTIES, JavaType.INPUTSTREAM);

        return javaEnum;
    }


	private void maakInitialisatieVeld(final JavaEnumeratie javaEnum, final Constructor javaEnumConstructor,
        final ObjectType objectType, final String attribuutNaam, final String veldNaam, final JavaType overrideVeldType) {
        final Attribuut attribuut = getAttribuut(objectType, attribuutNaam);
        final JavaVeld veld;
        final BmrElementSoort soort = BmrElementSoort.getBmrElementSoortBijCode(attribuut.getType().getSoortElement().getCode());
        switch (soort) {
            case OBJECTTYPE:
            	veld = genereerJavaVeldVoorObjectType(attribuut, veldNaam, overrideVeldType);
                break;
            case ATTRIBUUTTYPE:
            	veld = genereerJavaVeldVoorAttribuutType(attribuut, veldNaam, overrideVeldType);
            	break;
            default:
                throw new UnsupportedOperationException("Kan het type attribuut niet verwerken: "
                    + attribuut.getType().getSoortElement().getCode());
        }

        veld.setFinal(true);
        javaEnum.voegVeldToe(veld);

        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        genereerGetterJavaDoc(getter, objectType, attribuut);
        javaEnum.voegGetterToe(getter);

        javaEnumConstructor.setBody(javaEnumConstructor.getBody() + veldNaam + " = Helper.HELPER.geef" + GeneratieUtil.upperTheFirstCharacter(veldNaam) +
            "VoorElementId(id);");
	}


    private Attribuut getAttribuut(final ObjectType objectType, final String attribuutNaam ) {
        final List<Attribuut> attributen = getBmrDao().getAttributenVanObjectType(objectType);
        for (final Attribuut attribuut : attributen) {
            if (attribuutNaam.equalsIgnoreCase(attribuut.getIdentCode())) {
            	return attribuut;
            }
        }
        throw new IllegalArgumentException("Attribuut '" + attribuutNaam + "' niet gevonden. ");

	}

	/**
     * Voeg een veld en een getter toe aan de meegegeven enum, van het meegegeven type en met de meegegeven naam.
     *
     * @param javaEnum de enum
     * @param type     het type van het veld
     * @param naam     de naam van het veld
     */
    private void voegVeldEnGetterToeAanEnum(final JavaEnumeratie javaEnum, final JavaType type, final String naam) {
        final JavaVeld veld = new JavaVeld(type, naam);
        veld.setFinal(true);
        javaEnum.voegVeldToe(veld);

        // Elk veld krijgt een getter.
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        getter.setJavaDoc(genereerGetterJavadoc(naam, javaEnum.getNaam()));
        getter.setReturnWaardeJavaDoc(naam + ".");
        javaEnum.voegGetterToe(getter);
    }

    /**
     * Genereert de waarden woorde de enumeraties. Als bron hiervoor wordt de view in het BMR gebruikt.
     *  @param javaEnumeratie De JavaEnumeratie.
     * @param naamProperties
     * @param elementNaamProperties
     * @param soortProperties
     * @param groepIdProperties
     * @param objectIdProperties
     */
    private void genereerWaardenVoorEnumeratie(final JavaEnumeratie javaEnumeratie, final StringBuilder naamProperties, final StringBuilder elementNaamProperties,
        final StringBuilder soortProperties, final StringBuilder groepIdProperties, final StringBuilder objectIdProperties, final StringBuilder
        hisDbObjectProperties,
        final StringBuilder volgnummerProperties) {
        final List<VwElement> vwElementen = getBmrDao().getVwElementen();

        for (final VwElement vwElement : vwElementen) {

            final String enumWaardeNaam =
                bepaalNaamVoorVwElementEnumWaarde(vwElement);
            final String enumWaardeJavaDoc =
                bepaalJavaDocVoorEnumWaarde(vwElement);

            final EnumeratieWaarde enumeratieWaarde =
                new EnumeratieWaarde(enumWaardeNaam, enumWaardeJavaDoc);

            enumeratieWaarde.voegConstructorParameterToe(vwElement.getId().toString(), false);
            javaEnumeratie.voegEnumeratieWaardeToe(enumeratieWaarde);

            naamProperties.append(vwElement.getId()).append("=").append(vwElement.getIdentifier()).append("\n");
            elementNaamProperties.append(vwElement.getId()).append("=").append(vwElement.getNaam()).append("\n");
            soortProperties.append(vwElement.getId()).append("=").append(bepaalSoortElement(vwElement)).append("\n");
            if (vwElement.getGroep() != null) {
            	groepIdProperties.append(vwElement.getId()).append("=").append(vwElement.getGroep()).append("\n");
            }
            if (vwElement.getObjecttype() != null) {
                objectIdProperties.append(vwElement.getId()).append("=").append(vwElement.getObjecttype()).append("\n");
            }
            if (vwElement.getHisdbobject() != null) {
            	hisDbObjectProperties.append(vwElement.getId()).append("=").append(vwElement.getHisdbobject()).append("\n");
            }
        	final Integer volgnummer = bepaalVolgnummer(vwElement);
        	if (volgnummer != null) {
        		volgnummerProperties.append(vwElement.getId()).append("=").append(volgnummer).append("\n");
        	}
        }
    }

	private Integer bepaalVolgnummer(final VwElement vwElement) {
		final GeneriekElement element;
		if (vwElement.getDbobject() != null) {
			element = getBmrDao().getElementVoorSyncIdVanLaag(vwElement.getDbobject(), BmrLaag.LOGISCH, GeneriekElement.class);
		} else {
			element = null;
		}
		return element == null ? null : element.getVolgnummer();
	}

	/**
     * Bepaalt de javadoc voor een enum-waarde. Deze betrekt de eventuele ouder en groep in de omschrijving.
     *
     * @param vwElement Het vwElement waarvoor de javadoc bepaald moet worden.
     * @return De java-doc als string.
     */
    private String bepaalJavaDocVoorEnumWaarde(final VwElement vwElement) {
        return String.format("Waarde voor: %s.", vwElement.getIdentifier().replaceAll("\\.", " - "));
    }

    /**
     * Genereert een {@link JavaVeld} instantie op basis van het opgegeven attribuut, wat een objecttype zou moeten zijn.
     *
     * @param attribuut het attribuut waarvoor een JavaVeld instantie moet worden gegenereerd.
     * @return het gegenereerde JavaVeld.
     */
    private JavaVeld genereerJavaVeldVoorObjectType(final Attribuut attribuut, final String veldNaam, final JavaType overrideVeldType) {
    	final JavaType javaVeldTypeVoorAttribuut;
    	if (overrideVeldType == null) {
    		final NaamgevingStrategie naamgevingStrategie;
			if ("Element".equals(attribuut.getType().getIdentCode())) {
				//Pas de naamgevingstragie aan naar ObjectType of Groep
				if ("Groep".equals(attribuut.getIdentCode()) || "HisTabel".equals(attribuut.getIdentCode())) {
					naamgevingStrategie = new ElementEnumNaamgevingStrategie(BmrElementSoort.GROEP);
				} else if ("Objecttype".equals(attribuut.getIdentCode())) {
					naamgevingStrategie = new ElementEnumNaamgevingStrategie(BmrElementSoort.OBJECTTYPE);
				} else if ("AliasVan".equals(attribuut.getIdentCode())) {
					naamgevingStrategie = elementEnumNaamgevingStrategie;
				} else {
					naamgevingStrategie = new AlgemeneNaamgevingStrategie();
				}
			} else {
				naamgevingStrategie = new AlgemeneNaamgevingStrategie();
			}

			// Haal het objecttype op
			final ObjectType objectTypeAttribuut = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
			javaVeldTypeVoorAttribuut = naamgevingStrategie.getJavaTypeVoorElement(objectTypeAttribuut);
    	} else {
    		javaVeldTypeVoorAttribuut = overrideVeldType;
    	}

        return new JavaVeld(javaVeldTypeVoorAttribuut, veldNaam);
    }

    /**
     * Bepaalt het soort element (objectType, groep of attribuut).
     *
     * @param vwElement Het element waarvoor de soort bepaalt moet worden.
     * @return De java-code voor het soort element.
     */
    private String bepaalSoortElement(final VwElement vwElement) {
        final String resultaat;
        switch (vwElement.getSoort()) {
            case "OT":
                resultaat = "OBJECTTYPE";
                break;
            case "G":
                resultaat = "GROEP";
                break;
            case "A":
                resultaat = "ATTRIBUUT";
                break;
            default:
                throw new IllegalArgumentException("Niet legaal! Soort: " + vwElement.getSoort());
        }
        return resultaat;
    }

    /**
     * Genereert een {@link nl.bzk.brp.generatoren.java.model.JavaVeld} instantie op basis van het opgegeven attribuut, wat ook een attribuuttype zou
     * moeten zijn.
     *
     * @param attribuut het attribuut waarvoor een JavaVeld instantie moet worden gegenereerd.
     * @param veldNaam
     * @param overrideVeldType
     * @return het gegenereerde JavaVeld.
     */
    protected final JavaVeld genereerJavaVeldVoorAttribuutType(final Attribuut attribuut, final String veldNaam, final JavaType overrideVeldType) {
        final JavaType javaVeldTypeVoorAttribuut;
        if (overrideVeldType == null) {
            final AttribuutType attribuutType = getBmrDao().getAttribuutTypeVoorAttribuut(attribuut);
            javaVeldTypeVoorAttribuut = getJavaTypeVoorAttribuutType(attribuutType);
        } else {
        	javaVeldTypeVoorAttribuut = overrideVeldType;
        }
        return new JavaVeld(javaVeldTypeVoorAttribuut, veldNaam);
    }
}
