/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.common.SoortHistorie;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaMutatorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieAnnotatieParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieWaardeParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.AttribuutWrapperNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.BeheerAttribuutWrapperNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.BeheerNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Generator die de Java klassen maakt voor beherbare stamgegevens.
 */
@Component("beheerJavaGenerator")
public class BeheerGenerator extends AbstractJavaGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeheerGenerator.class);

    private static final JavaType FORMELE_HISTORIE_MODEL_TYPE = new JavaType("FormeleHistorieModel", "nl.bzk.brp.model.basis");
    private static final JavaType VERWERKINGSSOORT_TYPE = new JavaType("Verwerkingssoort", "nl.bzk.brp.model.algemeen.attribuuttype.ber");

    private final BeheerNaamgevingStrategie naamgevingStrategie  = new BeheerNaamgevingStrategie();
    private final BeheerAttribuutWrapperNaamgevingStrategie attribuutWrapperStrategy = new BeheerAttribuutWrapperNaamgevingStrategie();

    @Override
	protected GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.BEHEER_JAVA_GENERATOR;
	}

	@Override
	public void genereer(GeneratorConfiguratie generatorConfiguratie) {
		List<ObjectType> objectTypen = bepaalTeGenererenObjectenTypen();
		List<Groep> historieGroepen = new ArrayList<>();

		// Maak klassen (logisch model gebaseerde lijst)
		List<JavaKlasse> klassen = new ArrayList<>();
        for (final ObjectType objectType : objectTypen) {
            final JavaKlasse klasse = genereerJavaKlasseVoorObjectType(objectType, historieGroepen);
            klassen.add(klasse);
        }

        // Maak klassen uit operationeel model waar we naar refereren als historie klassen
        for (final Groep historieGroep : historieGroepen) {
            final ObjectType hisObjectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(historieGroep);
            final JavaKlasse klasse = genereerJavaKlasseVoorObjectType(hisObjectType, null);
            klassen.add(klasse);
        }

        // Schrijf de klassen weg via een writer.
        final JavaWriter<JavaKlasse> writer = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        final List<JavaKlasse> gegenereerdeKlassen = writer.genereerEnSchrijfWeg(klassen, this);

        // Rapporteer over gegenereerde klassen.
        rapporteerOverGegenereerdeJavaTypen(gegenereerdeKlassen);
	}

	private List<ObjectType> bepaalTeGenererenObjectenTypen() {
		final List<ObjectType> objectTypen = getBmrDao().getObjectTypen();

		final List<ObjectType> result = new ArrayList<>();
		for(ObjectType objectType : objectTypen) {
			if(selecteerObjectType(objectType)) {
				result.add(objectType);
			}
		}

		return result;
    }

    private boolean selecteerObjectType(ObjectType objectType) {
        LOGGER.info(objectType.getNaam() + ": " + objectType.getElementByLaag().getId() + "/" + objectType.getCode());
    	if (BmrLaag.LOGISCH.getWaardeInBmr() != objectType.getElementByLaag().getId()) {
    		return false;
    	}

        final BmrElementSoort soort = BmrElementSoort.getBmrElementSoortBijCode(objectType.getSoortElement().getCode());
        if (soort != BmrElementSoort.OBJECTTYPE) {
        	return false;
        }

        return naamgevingStrategie.valtOnderBeheerGenerator(objectType);
	}

	private JavaKlasse genereerJavaKlasseVoorObjectType(ObjectType objectType, List<Groep> historieGroepen) {
		LOGGER.info("Maak klasse voor object type: {} (id={})", objectType.getNaam(), objectType.getId());

        final List<Attribuut> attributen = getBmrDao().getAttributenVanObjectType(objectType);
		JavaKlasse klasse = maakJavaKlasse(objectType, attributen);

		// Elk attribuut toevoegen als property (plus accessors en mutators)
        // afgeleid van operationeel model
        List<GeneriekElement> objectTypen = getBmrDao().getElementenVoorSyncId(objectType.getSyncid());
        ObjectType operationeelObjectType = objectType;
        for (GeneriekElement element : objectTypen) {
            if (element.getElementByLaag().getId() == BmrLaag.OPERATIONEEL.getWaardeInBmr()) {
                operationeelObjectType = (ObjectType) element;
                LOGGER.info("Operationeel type gevonden voor logisch type");
            }
        }

        final List<Attribuut> operationeleAttributen = getBmrDao().getAttributenVanObjectType(operationeelObjectType);
        for (final Attribuut attribuut : operationeleAttributen) {
        	toevoegenPropertyVoorAttribuut(objectType, klasse, attribuut);
        }

        // One-to-many's maken voor groepen met historie
        final List<Groep> groepen = getBmrDao().getGroepenVoorObjectType(objectType);
        for(final Groep groep : groepen) {
        	toevoegenHistorieSetVoorGroep(objectType, klasse, groep, historieGroepen);
        }

        // One-to-many's maken voor inverse relaties
        final List<Attribuut> inverseAttributen = getBmrDao().getInverseAttributenVoorObjectType(objectType);
        for(Attribuut inverseAttribuut : inverseAttributen) {
    		toevoegenPropertyVoorInverseAttribuut(objectType, klasse, inverseAttribuut);
        }


        return klasse;
	}

	/**
     * Maak de basis JavaKlasse aan voor het object type.
     *
     * @param objectType object type
     * @return java klasse
     */
	private JavaKlasse maakJavaKlasse(ObjectType objectType, List<Attribuut> attributen) {
        final JavaKlasse result = new JavaKlasse(naamgevingStrategie.getJavaTypeVoorElement(objectType), bouwJavadocVoorElement(objectType));

        // Aangepaste entity naam nodig omdat deze anders 'overlappen' met 'gewone' stamgegevens
        result.voegAnnotatieToe(new JavaAnnotatie(JavaType.ENTITY, new AnnotatieWaardeParameter("name", "beheer." + result.getNaam())));

        result.voegAnnotatieToe(new JavaAnnotatie(JavaType.TABLE,
        		new AnnotatieWaardeParameter("schema", objectType.getSchema().getNaam()),
        		new AnnotatieWaardeParameter("name", objectType.getIdentDb())
        	));

        result.voegAnnotatieToe(new JavaAnnotatie(JavaType.GENERATED, new AnnotatieWaardeParameter("value", this.getClass().getName())));

        result.voegAnnotatieToe(new JavaAnnotatie(JavaType.SUPPRESS_WARNINGS, new AnnotatieWaardeParameter(null, "PMD.AvoidDuplicateLiterals")));

        boolean heeftTsReg=false;
        boolean heeftTsVerval=false;
        for(Attribuut attribuut : attributen) {
        	if("tsreg".equalsIgnoreCase(attribuut.getIdentDb())) {
        		heeftTsReg=true;
        	}
        	if("tsverval".equalsIgnoreCase(attribuut.getIdentDb())) {
        		heeftTsVerval=true;
        	}
        }

        if(heeftTsReg && heeftTsVerval) {
        	result.getSuperInterfaces().add(FORMELE_HISTORIE_MODEL_TYPE);
        	result.getSuperInterfaces().add(new JavaType("FormeelHistorisch", "nl.bzk.brp.model.basis"));

        	JavaFunctie getFormeleHistorie = new JavaFunctie(JavaAccessModifier.PUBLIC, FORMELE_HISTORIE_MODEL_TYPE, "getFormeleHistorie", "");
        	getFormeleHistorie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            getFormeleHistorie.voegAnnotatieToe(new JavaAnnotatie(JavaType.SUPPRESS_WARNINGS, new AnnotatieWaardeParameter(null, "checkstyle:designforextension")));
        	getFormeleHistorie.setBody("return this;");
        	result.voegFunctieToe(getFormeleHistorie);

        	JavaFunctie getVerwerkingssoort =  new JavaFunctie(JavaAccessModifier.PUBLIC, VERWERKINGSSOORT_TYPE, "getVerwerkingssoort", "");
        	getVerwerkingssoort.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            getVerwerkingssoort.voegAnnotatieToe(new JavaAnnotatie(JavaType.SUPPRESS_WARNINGS, new AnnotatieWaardeParameter(null, "checkstyle:designforextension")));
        	getVerwerkingssoort.setBody("return null;");
        	result.voegFunctieToe(getVerwerkingssoort);

        	JavaFunctie setVerwerkingssoort =  new JavaFunctie(JavaAccessModifier.PUBLIC, "setVerwerkingssoort");
        	setVerwerkingssoort.voegParameterToe(new JavaFunctieParameter("verwerkingsSoort", VERWERKINGSSOORT_TYPE));
        	setVerwerkingssoort.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            setVerwerkingssoort.voegAnnotatieToe(new JavaAnnotatie(JavaType.SUPPRESS_WARNINGS, new AnnotatieWaardeParameter(null, "checkstyle:designforextension")));
        	result.voegFunctieToe(setVerwerkingssoort);

        	JavaFunctie isMagGeleverdWorden =  new JavaFunctie(JavaAccessModifier.PUBLIC, JavaType.BOOLEAN_PRIMITIVE, "isMagGeleverdWorden", "");
        	isMagGeleverdWorden.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            isMagGeleverdWorden.voegAnnotatieToe(new JavaAnnotatie(JavaType.SUPPRESS_WARNINGS, new AnnotatieWaardeParameter(null, "checkstyle:designforextension")));
        	isMagGeleverdWorden.setBody("return false;");
        	result.voegFunctieToe(isMagGeleverdWorden);
        }

        return result;
	}

    private void toevoegenPropertyVoorAttribuut(ObjectType objectType, JavaKlasse klasse, Attribuut attribuut) {
		LOGGER.debug("Maak veld voor attribuut: {} (type.soortInhoud={})", attribuut.getNaam(), attribuut.getType() == null ? null: attribuut.getType().getSoortInhoud());
    	// Maak basis veld
        final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
        final boolean isId = isIdAttribuut(attribuut);
        final boolean isWrapped;
		final boolean isAssociation;
        if(isId) {
        	isWrapped = false;
			isAssociation = false;
        } else {
        	isWrapped = isWrappedAttribuut(attribuut.getType());
			isAssociation = isAssociationAttribuut(attribuut.getType()) || "Element".equals(attribuut.getType().getNaam());
        }
        final JavaType veldType;
        if(isId) {
              veldType = getJavaTypeVoorAttribuutType(getBmrDao().getAttribuutTypeVoorAttribuut(attribuut));
        } else {
        	if(isWrapped) {
        		veldType = attribuutWrapperStrategy.getJavaTypeVoorElement(attribuut.getType(), isAssociation);
        	} else {
        		veldType = naamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());

        	}
        }

        LOGGER.debug("Veld type: {}", veldType.getFullyQualifiedClassName());
        final JavaVeld veld = new JavaVeld(veldType, veldNaam);
        klasse.voegVeldToe(veld);

        // Annotaties
        if(isId) {
        	annoteerIdVeld(objectType, veld, false);
        } else if (isWrapped) {
        	if(isAssociation) {
                veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.MANY_TO_ONE, new AnnotatieWaardeParameter("fetch",JavaType.FETCH_TYPE, "LAZY")));
                veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JOIN_COLUMN, new AnnotatieWaardeParameter("name",  attribuut.getIdentDb())));
                klasse.voegExtraImportsToe(JavaType.FETCH_TYPE);
//        		veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ASSOCIATION_OVERRIDE,
//                		new AnnotatieWaardeParameter("name", veld.getType().getNaam() + ".WAARDE_VELD_NAAM", true),
//                		new AnnotatieAnnotatieParameter("joinColumns", new JavaAnnotatie(JavaType.JOIN_COLUMN, new AnnotatieWaardeParameter("name", attribuut.getIdentDb())))));
        	} else {
                veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDED));
        		veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ATTRIBUTE_OVERRIDE,
            		new AnnotatieWaardeParameter("name", veld.getType().getNaam() + ".WAARDE_VELD_NAAM", true),
            		new AnnotatieAnnotatieParameter("column", new JavaAnnotatie(JavaType.COLUMN, new AnnotatieWaardeParameter("name", attribuut.getIdentDb())))));
        	}
        } else {
        	if(isAssociation) {
	            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.MANY_TO_ONE, new AnnotatieWaardeParameter("fetch",JavaType.FETCH_TYPE, "LAZY")));
	            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JOIN_COLUMN, new AnnotatieWaardeParameter("name",  attribuut.getIdentDb())));
	            klasse.voegExtraImportsToe(JavaType.FETCH_TYPE);
        	} else {
	            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.COLUMN, new AnnotatieWaardeParameter("name", attribuut.getIdentDb())));
	            if(attribuut.getType().getSoortInhoud() == 'X' ) {
	            	veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ENUMERATED));
	            }
        	}
        }

        // Accessor
        final JavaAccessorFunctie getter= new JavaAccessorFunctie(veld);
        genereerGetterJavaDoc(getter, objectType, attribuut);
        if("tsreg".equalsIgnoreCase(attribuut.getIdentDb())) {
        	getter.setNaam("getTijdstipRegistratie");
        } else if("tsverval".equalsIgnoreCase(attribuut.getIdentDb())) {
        	getter.setNaam("getDatumTijdVerval");
        }
        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.SUPPRESS_WARNINGS, new AnnotatieWaardeParameter(null, "checkstyle:designforextension")));
        klasse.voegGetterToe(getter);

        // Mutator
        final String parameterNaam = fixParameterNaamVoorSetter(veld.getNaam());
        final JavaMutatorFunctie setter= new JavaMutatorFunctie(JavaAccessModifier.PUBLIC,
                String.format("set%s", GeneratieUtil.upperTheFirstCharacter(veld.getNaam())), veld.getNaam(), parameterNaam);
        setter.voegParameterToe(new JavaFunctieParameter(parameterNaam, veld.getType()));
        genereerSetterJavaDoc(setter, objectType, attribuut);
        if("tsreg".equalsIgnoreCase(attribuut.getIdentDb())) {
        	setter.setNaam("setDatumTijdRegistratie");
        } else if("tsverval".equalsIgnoreCase(attribuut.getIdentDb())) {
        	setter.setNaam( "setDatumTijdVerval");
        }
        setter.voegAnnotatieToe(new JavaAnnotatie(JavaType.SUPPRESS_WARNINGS, new AnnotatieWaardeParameter(null, "checkstyle:designforextension")));
        klasse.voegSetterToe(setter);
	}

    /**
     * Als de veld naam begint met meerdere hoofdletters dan ziet checkstyle de setter niet als setter.
     *
     * @param naam veld naam
     * @return parameter naam
     */
    private String fixParameterNaamVoorSetter(String naam) {
		return "p"+ GeneratieUtil.upperTheFirstCharacter(naam);
	}

	private boolean isWrappedAttribuut(final GeneriekElement element) {

		LOGGER.debug("isWrappedAttribuut(element={})", element);

        boolean isJuisteSoortElement = false;
        final String soortCode = element.getSoortElement().getCode();
        if (BmrElementSoort.getBmrElementSoortBijCode(soortCode) == BmrElementSoort.OBJECTTYPE) {
        	LOGGER.debug("isWrappedAttribuut -> IS OBJECTTYPE");
            final BmrSoortInhoud soortInhoud = BmrSoortInhoud.getBmrSoortInhoudVoorCode(element.getSoortInhoud());
            final boolean komtUitBeheer = isBeheerSchema(element.getSchema());
            LOGGER.debug("isWrappedAttribuut -> KOMT UIT BEHEER: {}", komtUitBeheer);

            if (soortInhoud == BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN && !komtUitBeheer && !"Partij".equals(element.getNaam()) && !"His Abonnement".equals(element.getNaam())) {
                isJuisteSoortElement = true;
            }
        } else if (BmrElementSoort.getBmrElementSoortBijCode(soortCode) == BmrElementSoort.ATTRIBUUTTYPE) {
        	LOGGER.debug("isWrappedAttribuut -> IS ATTRIBUUTTYPE");
            isJuisteSoortElement = true;
        }

        return isJuisteSoortElement;
    }


    private boolean isBeheerSchema(Schema schema) {
    	String schemaNaam =schema.getNaam();
    	return schemaNaam.equals("AutAut") || schemaNaam.equals("Ber") || schemaNaam.equals("Conv")||schemaNaam.equals("Lev");
	}

	private boolean isAssociationAttribuut(final GeneriekElement element) {
        final String soortCode = element.getSoortElement().getCode();
        if (BmrElementSoort.getBmrElementSoortBijCode(soortCode) == BmrElementSoort.OBJECTTYPE) {
            final BmrSoortInhoud soortInhoud = BmrSoortInhoud.getBmrSoortInhoudVoorCode(element.getSoortInhoud());
            if (soortInhoud == BmrSoortInhoud.STATISCH_STAMGEGEVEN) {
                return false;
            } else {
				return true;
			}
        } else  {
                return false;
        }
    }

    private void toevoegenHistorieSetVoorGroep(ObjectType objectType, JavaKlasse klasse, Groep groep, List<Groep> historieGroepen) {
    	if( groep.getHistorieVastleggen() != SoortHistorie.FORMEEL.getHistorieVastleggen() && groep.getHistorieVastleggen() != SoortHistorie.MATERIEEL.getHistorieVastleggen()) {
    		return;
    	}
        historieGroepen.add(groep);
		LOGGER.debug("Maak historie set voor groep van object type: {}.{}", objectType.getNaam(), groep.getNaam());
        final ObjectType hisObjectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
        final JavaType javaTypeVoorElement = naamgevingStrategie.getJavaTypeVoorElement(hisObjectType);
        final JavaVeld veld = new JavaVeld(new JavaType(JavaType.SET, javaTypeVoorElement),
                JavaGeneratieUtil.toValidJavaVariableName(hisObjectType.getIdentCode()) + "Lijst");
        veld.setGeinstantieerdeWaarde("new HashSet<>()");
        klasse.voegExtraImportsToe(JavaType.HASH_SET);
        klasse.voegVeldToe(veld);

        // Annotaties
        // Shortcut op mappedby
        veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ONE_TO_MANY,
                new AnnotatieWaardeParameter("fetch", JavaType.FETCH_TYPE, "LAZY"),
                new AnnotatieWaardeParameter("mappedBy", GeneratieUtil.lowerTheFirstCharacter(objectType.getIdentCode())),
                new AnnotatieWaardeParameter("cascade", JavaType.CASCADE_TYPE, "ALL"),
                new AnnotatieWaardeParameter("orphanRemoval", "true", true)
                ));
        veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.FETCH,
            new AnnotatieWaardeParameter("value", JavaType.FETCH_MODE, "SELECT")));

        // Accessor
        final JavaAccessorFunctie getter= new JavaAccessorFunctie(veld);
        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.SUPPRESS_WARNINGS, new AnnotatieWaardeParameter(null, "checkstyle:designforextension")));
        genereerGetterJavaDoc(getter, objectType, groep);
        klasse.voegGetterToe(getter);
	}

	private void toevoegenPropertyVoorInverseAttribuut(ObjectType objectType,
			JavaKlasse klasse, Attribuut inverseAttribuut) {
		LOGGER.debug("Maak veld voor inverse attribuut van object type: {}.{}", inverseAttribuut.getObjectType().getNaam(), inverseAttribuut.getNaam());
        final ObjectType inverseObjectType = inverseAttribuut.getObjectType();
        final JavaType javaTypeVoorElement = naamgevingStrategie.getJavaTypeVoorElement(inverseObjectType);
        final JavaVeld veld = new JavaVeld(
            new JavaType(JavaType.SET, javaTypeVoorElement),
            JavaGeneratieUtil.toValidJavaVariableName(inverseAttribuut.getInverseAssociatieIdentCode()));
        veld.setGeinstantieerdeWaarde("new HashSet<>()");
        klasse.voegExtraImportsToe(JavaType.HASH_SET);

        veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ONE_TO_MANY,
                new AnnotatieWaardeParameter("fetch", JavaType.FETCH_TYPE, "EAGER"),
                new AnnotatieWaardeParameter("mappedBy", GeneratieUtil.lowerTheFirstCharacter( inverseAttribuut.getIdentCode())),
                new AnnotatieWaardeParameter("cascade", JavaType.CASCADE_TYPE, "ALL"),
                new AnnotatieWaardeParameter("orphanRemoval", "true", true)
        		));
        veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.FETCH,
            new AnnotatieWaardeParameter("value", JavaType.FETCH_MODE, "SELECT")));
        klasse.voegVeldToe(veld);

        //Genereer een getter.
        final JavaAccessorFunctie javaAccessorFunctie = new JavaAccessorFunctie(veld);
        javaAccessorFunctie.voegAnnotatieToe(new JavaAnnotatie(JavaType.SUPPRESS_WARNINGS, new AnnotatieWaardeParameter(null, "checkstyle:designforextension")));
        javaAccessorFunctie.setJavaDoc("Retourneert de lijst van " + inverseObjectType.getMeervoudsnaam() + ".");
        javaAccessorFunctie.setReturnWaardeJavaDoc("lijst van " + inverseObjectType.getMeervoudsnaam());
        klasse.voegGetterToe(javaAccessorFunctie);
	}

}
