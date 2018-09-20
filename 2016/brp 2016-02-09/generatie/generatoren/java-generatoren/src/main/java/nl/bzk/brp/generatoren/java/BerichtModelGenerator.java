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
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrElementFilterObject;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaMutatorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.BerichtModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.LogischModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.symbols.SymbolTableConstants;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/** Generator die de Java Classes genereert voor het bericht model. */
@Component("berichtModelJavaGenerator")
public class BerichtModelGenerator extends AbstractJavaGenerator {

    private final NaamgevingStrategie naamgevingStrategie               = new BerichtModelNaamgevingStrategie();
    private final NaamgevingStrategie algemeneNaamgevingStrategie       = new AlgemeneNaamgevingStrategie();
    private final NaamgevingStrategie superInterfaceNaamgevingStrategie = new LogischModelNaamgevingStrategie();

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        // Filter op logische laag, op 'in bericht' en geen stamgegevens
        final BmrElementFilterObject filter = new BmrElementFilterObject(BmrLaag.LOGISCH);
        filter.setSoortInhoud('D');

        // Haal objecttypen op
        final List<ObjectType> objectTypen = getBmrDao().getObjectTypen(filter);

        // Genereer voor alle objecttypen de benodigde Bericht Model classes.
        final List<JavaKlasse> objectClasses = new ArrayList<>();
        for (final ObjectType objectType : objectTypen) {
            if (behoortTotJavaBerichtModel(objectType)) {
                //Bouw java klasse voor objecttype.
                final JavaKlasse javaKlasse = bouwJavaKlasseVoorObjectType(objectType);

                //Als het object xsd views per discriminator waarde heeft dan moeten we voor de binding per
                //discriminator waarde een aparte klasse genereren. Dus per Tuple krijg je een klasse die extend
                //van dit objecttype.
                if (objectType.getXsdViewsPerDiscriminatorWaarde() != null && 'J' == objectType.getXsdViewsPerDiscriminatorWaarde()) {
                    //Maak de superklasse van de tuples klassen abstract.
                    javaKlasse.setAbstractClass(true);

                    //Als het objecttype een attribuut heeft waarvan het attribuuttype tuples kent, bouw dan per tuple
                    //een class. Deze class zal dan het objecttype extenden. Dit t.b.v. binding van xsd views.
                    final List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType);
                    for (final Groep groep : groepenVoorObjectType) {
                        if (IDENTITEIT.equals(groep.getNaam())) {
                            final List<Attribuut> attributenVanGroep = getBmrDao().getAttributenVanGroep(groep);
                            for (final Attribuut attribuut : attributenVanGroep) {
                                //Tuples behoren enkel tot statische stamgegevens
                                if (attribuut.getType().getSoortInhoud() != null
                                    && BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud())
                                {
                                    final ObjectType stamgegevenDatTuplesBevat = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                                    for (final Tuple tuple : stamgegevenDatTuplesBevat.getTuples()) {
                                        objectClasses.add(bouwJavaKlasseVoorTuple(objectType, tuple));
                                    }
                                }
                            }
                        }
                    }
                }

                if (objectType.getId() == ID_PERSOON_LGM) {
                    voegGettersToeVoorIndicaties(javaKlasse, naamgevingStrategie, false);
                }

                //Voeg de java klasse toe aan de collectie.
                objectClasses.add(javaKlasse);
            }
        }

        //Haal groepen op
        final List<Groep> groepen = getBmrDao().getGroepen();
        //Genereer voor alle groepen de benodigde Bericht Model classes.
        final List<JavaKlasse> groepClasses = new ArrayList<>();
        for (final Groep groep : groepen) {
            if (behoortTotJavaBerichtModel(groep.getObjectType())
                && behoortTotJavaBerichtModel(groep))
            {
                groepClasses.add(bouwJavaKlasseVoorGroep(groep));
            }
        }

        final JavaWriter<JavaKlasse> writer = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        final List<JavaKlasse> gegenereerdeJavaKlasses = new ArrayList<>();
        voegGeneratedAnnotatiesToe(objectClasses, generatorConfiguratie);
        voegGeneratedAnnotatiesToe(groepClasses, generatorConfiguratie);
        gegenereerdeJavaKlasses.addAll(writer.genereerEnSchrijfWeg(objectClasses, this));
        gegenereerdeJavaKlasses.addAll(writer.genereerEnSchrijfWeg(groepClasses, this));

        rapporteerOverGegenereerdeJavaTypen(gegenereerdeJavaKlasses);
    }

    @Override
    public GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.BERICHT_MODEL_JAVA_GENERATOR;
    }

    /**
     * Bouwt een java klasse voor een tuple. Tuples behoren tot een statisch stamgegeven objecttype.
     * Dit objecttype wordt als attribuut opgenomen in een ander objecttype. Deze functie maakt voor elke tuple een
     * uitbreidingsklasse die het objecttype extend waarin een constructor zit die de juiste tuple waarde in het
     * objecttype instantieert.
     *
     * @param objectType Het objecttype dat een attribuut kent van het type statische stamgegeven met tuples.
     * @param tuple De tuple waar een klasse voor wordt gemaakt.
     * @return Java klasse voor de tuple.
     */
    private JavaKlasse bouwJavaKlasseVoorTuple(final ObjectType objectType, final Tuple tuple) {
        final JavaType typeVoorTuple = naamgevingStrategie.getJavaTypeVoorElement(tuple);
        final String klasseNaam = objectType.getXsdViewPrefix()
            + typeVoorTuple.getNaam();
        final JavaKlasse klasse = new JavaKlasse(klasseNaam, bouwJavadocVoorElement(tuple),
            typeVoorTuple.getPackagePad());

        //De klasse voor de tuple moet extenden van het objecttype. Het objecttype kent een attribuut waarvan het type
        //een stamgegeven is met tuples. Elke klasse dat voor een tuple wordt gemaakt moet dus extenden van objecttype.
        klasse.setExtendsFrom(naamgevingStrategie.getJavaTypeVoorElement(objectType));

        //Voeg een constructor toe die automatisch het attribuut in de superclass naar de juiste waarde zet.
        //Bijvoorbeeld: InschrijvingDoorGeboorte is een tuple van soort administratieve handeling. De class die bij deze
        //tuple hoort extends van AdministratieveHandeling, we moet in de constructor automatisch het veld 'soort' in
        //administratieve handeling setten.
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, klasse);
        final JavaType javaWrapperTypeVoorTuple = naamgevingStrategie.getJavaTypeVoorElement(tuple.getObjectType());
        final JavaType javaTypeVoorTuple = algemeneNaamgevingStrategie.getJavaTypeVoorElement(tuple.getObjectType());
        klasse.voegExtraImportsToe(javaWrapperTypeVoorTuple, javaTypeVoorTuple);
        constructor.setBody(String.format("super(new %1$s(%2$s.%3$s));",
                javaWrapperTypeVoorTuple.getNaam(),
                javaTypeVoorTuple.getNaam(),
                JavaGeneratieUtil.genereerNaamVoorEnumWaarde(tuple.getIdentCode())));
        constructor.setJavaDoc("Default constructor instantieert met de juiste " + javaTypeVoorTuple.getNaam() + ".");
        klasse.voegConstructorToe(constructor);
        return klasse;
    }

    /**
     * Bouwt een JavaKlasse object wat correspondeert met een groep uit het BMR.
     *
     * @param groep De groep uit het BMR.
     * @return Een java klasse object.
     */
    private JavaKlasse bouwJavaKlasseVoorGroep(final Groep groep) {
        final JavaKlasse clazz = new JavaKlasse(naamgevingStrategie.getJavaTypeVoorElement(groep),
            bouwJavadocVoorElement(groep));
        clazz.voegSuperInterfaceToe(JavaType.GROEP);

        // Zet de juiste super klasse, aan het hand van het historie patroon.
        final Character historieVastleggen = groep.getHistorieVastleggen();
        if (historieVastleggen == null || historieVastleggen == 'G') {
            if (behoortInXsdAlsLosStaandType(groep)) {
                clazz.setExtendsFrom(JavaType.ABSTRACT_BERICHT_IDENTIFICEERBAAR);
            }
        } else if (historieVastleggen == 'F') {
            clazz.setExtendsFrom(JavaType.ABSTRACT_FORMELE_HISTORIE_GROEP_BERICHT);
        } else if (historieVastleggen == 'B') {
            clazz.setExtendsFrom(JavaType.ABSTRACT_MATERIELE_HISTORIE_GROEP_BERICHT);
        } else {
            throw new IllegalStateException("Ongeldige waarde voor historie vastleggen: '"
                + historieVastleggen + "' voor groep: '" + groep.getNaam() + "'.");
        }

        // Voeg super interface toe indien de groep tot het java logische model behoort.
        if (behoortTotJavaLogischModel(groep)) {
            clazz.voegSuperInterfaceToe(superInterfaceNaamgevingStrategie.getJavaTypeVoorElement(groep));
        }
        clazz.voegSuperInterfaceToe(JavaType.META_IDENTIFICEERBAAR);

        bouwAttributenMetGettersEnSettersVoorGroep(groep, clazz);
        voegMetaIdGetterToe(groep, clazz);
        voegAttribuutMetaIdCheckMethodeToe(groep, clazz);
        return clazz;
    }

    /**
     * Voegt een statische methode toe op alle Bericht Entiteit groepen die retourneert of een opgegeven Meta ID een
     * attribuut is binnen deze groep.
     *
     * @param groep de groep waarvoor de methode toegevoegd dient te worden.
     * @param clazz de Java klasse waar de methode aan toegevoegd dient te worden.
     */
    private void voegAttribuutMetaIdCheckMethodeToe(final Groep groep, final JavaKlasse clazz) {
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);

        final List<Integer> syncIds = new ArrayList<>();
        for (final Attribuut attribuut : attributen) {
            if (behoortTotJavaBerichtModel(attribuut)) {
                syncIds.add(attribuut.getSyncid());
            }
        }

        final JavaVeld attributenJavaVeld = new JavaVeld(new JavaType(JavaType.LIST, JavaType.INTEGER),
            "ONDERLIGGENDE_ATTRIBUTEN", true);
        attributenJavaVeld.setAccessModifier(JavaAccessModifier.PRIVATE);
        attributenJavaVeld.setStatic(true);
        attributenJavaVeld.setGeinstantieerdeWaarde(
            String.format("Arrays.asList(%s)", StringUtils.join(syncIds, ", ")));
        clazz.voegVeldToe(attributenJavaVeld);
        clazz.voegExtraImportsToe(JavaType.ARRAYS);

        final JavaFunctie heeftAttribuutFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC,
            JavaType.BOOLEAN_PRIMITIVE, "bevatElementMetMetaId", null, false);
        heeftAttribuutFunctie.voegParameterToe(new JavaFunctieParameter("metaId", JavaType.INTEGER));
        heeftAttribuutFunctie.setBody("return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);");
        heeftAttribuutFunctie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        clazz.voegFunctieToe(heeftAttribuutFunctie);
    }

    /**
     * Bouwt alle attributen die horen bij de groep inclusief de getters en setters die daarbij horen.
     *
     * @param groep De groep waarvoor attributen moeten worden gebouwd.
     * @param clazz De Java klasse waar de attributen, getters en setters in moeten.
     */
    private void bouwAttributenMetGettersEnSettersVoorGroep(final Groep groep, final JavaKlasse clazz) {
        //Haal attributen van groep op.
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);

        for (final Attribuut attribuut : attributen) {
            if (behoortTotJavaBerichtModel(attribuut)) {
                this.voegEventueelLogischeIdentiteitVeldToe(clazz, attribuut);

                final JavaType javaTypeVoorVeld = naamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                final JavaVeld attribuutVeldInGroep = new JavaVeld(javaTypeVoorVeld, veldNaam);
                clazz.voegVeldToe(attribuutVeldInGroep);

                //Maak een accessor aan oftewel een getter.
                final JavaAccessorFunctie getter = new JavaAccessorFunctie(attribuutVeldInGroep);

                //Als de groep behoort tot het logisch java model, oftewel er hangt een groep interface boven, en het
                //attribuut is ook opgenomen in datzelfde model dan weten we dat deze getter een override is.
                if (behoortTotJavaLogischModel(groep)
                    && behoortTotJavaLogischModel(attribuut) && behoortTotJavaOperationeelModel(attribuut))
                {
                    // Let op voor database knip attributen geldt de override niet, logische interface gebruikt Id.
                    if (!isDatabaseKnipAttribuut(groep.getObjectType(), attribuut)) {
                        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
                    }
                }
                clazz.voegGetterToe(getter);
                genereerGetterJavaDoc(getter, groep, attribuut);

                //Maak een mutator aan oftewel een setter.
                final JavaMutatorFunctie setter = new JavaMutatorFunctie(attribuutVeldInGroep);
                genereerSetterJavaDoc(setter, groep, attribuut);
                clazz.voegSetterToe(setter);


            }
        }

        // Genereert een getter waarin een lijst van alle attributen wordt teruggegeven.
        genereerGetterVoorAlleAttributen(clazz, attributen);
    }

    /**
     * Genereert een getter waarmee alle attributen van de groep kunnen worden opgehaald. Deze getter wordt aan de klasse toegevoegd.
     *
     * @param clazz klasse van groep
     * @param attributen lijst van attributen
     */
    private void genereerGetterVoorAlleAttributen(final JavaKlasse clazz, final List<Attribuut> attributen) {
        final JavaFunctie attributenGetter = new JavaFunctie(JavaAccessModifier.PUBLIC,
                                                             new JavaType(JavaType.LIST, SymbolTableConstants.ATTRIBUUT_JAVATYPE),
                                                             "getAttributen", "Lijst met attributen van de groep.");
        attributenGetter.setFinal(true);
        attributenGetter.setJavaDoc("Geeft alle attributen van de groep met uitzondering van attributen die null zijn.");

        final StringBuilder stringBuilder = new StringBuilder("final List<Attribuut> attributen = new ArrayList<>();");
        for (final Attribuut attribuut : attributen) {
            // Sommige groepen bevatten attributen die niet de Attribuut-interface implementeren
            final JavaType javaTypeVoorElement = naamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
            if (javaTypeVoorElement.getNaam().toLowerCase().contains("attribuut")) {
                final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                stringBuilder.append("if (");
                stringBuilder.append(veldNaam);
                stringBuilder.append(" != null) {\n");
                stringBuilder.append("attributen.add(");
                stringBuilder.append(veldNaam);
                stringBuilder.append(");\n");
                stringBuilder.append("}\n");
            }
        }
        stringBuilder.append("return attributen;");
        attributenGetter.setBody(stringBuilder.toString());

        clazz.voegExtraImportsToe(JavaType.LIST);
        clazz.voegExtraImportsToe(JavaType.ARRAY_LIST);

        clazz.voegFunctieToe(attributenGetter);
    }

    /**
     * Bouwt een nieuwe Java {@link JavaKlasse}, welke de voor het opgegeven {@link ObjectType} benodigde informatie
     * aangaande velden, methodes, javadoc etc. bevat.
     *
     * @param objectType het objectType waarvoor een Java ObjectClass gegenereerd moet worden.
     * @return een {@link JavaKlasse} met daarin de voor het objectType geldende informatie m.b.t. methodes, javadoc,
     *         velden etc.
     */
    private JavaKlasse bouwJavaKlasseVoorObjectType(final ObjectType objectType) {
        final JavaKlasse clazz = new JavaKlasse(naamgevingStrategie.getJavaTypeVoorElement(objectType),
            bouwJavadocVoorElement(objectType));
        clazz.voegSuperInterfaceToe(JavaType.BRP_OBJECT);

        // Zet super klasse: de BMR super klasse, abstract object type bericht of geen.
        if (heeftSupertype(objectType)) {
            clazz.setExtendsFrom(naamgevingStrategie.getJavaTypeVoorElement(objectType.getSuperType()));
        } else if (ID_BERICHT_LGM != objectType.getId() && !isKoppelingObjectType(objectType)) {
            clazz.setExtendsFrom(JavaType.ABSTRACT_BERICHT_ENTITEIT);
            clazz.voegSuperInterfaceToe(JavaType.BERICHT_ENTITEIT);
            clazz.voegSuperInterfaceToe(JavaType.META_IDENTIFICEERBAAR);
            voegMetaIdGetterToe(objectType, clazz);
            voegBerichtEntiteitenGetterToe(objectType, clazz);
            voegBerichtGroepEntiteitenGetterToe(objectType, clazz);
        }

        // Als een klasse een super type is, oftewel zelf geen finaal subtype is, dan moet hij abstract zijn.
        if (isSupertype(objectType)) {
            clazz.setAbstractClass(true);
        }

        // Voeg super interface toe.
        if (behoortTotJavaLogischModel(objectType)) {
            clazz.voegSuperInterfaceToe(superInterfaceNaamgevingStrategie.getJavaTypeVoorElement(objectType));
        }

        //TODO: Veel overlap met operationeel model generator stuk, samenvoegen?
        // Als het een hierarchisch type is, maak een constructor aan voor het discriminator attribuut.
        if (isHierarchischType(objectType) || StringUtils.isNotBlank(objectType.getDiscriminatorAttribuut())) {
            final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, clazz);
            constructor.setJavaDoc("Constructor die het discriminator attribuut zet of doorgeeft.");
            final Attribuut discriminatorAttribuut = this.bepaalDiscriminatorAttribuut(objectType);
            final Tuple discriminatorTuple = this.bepaalDiscriminatorTuple(objectType);
            final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(discriminatorAttribuut.getIdentCode());
            final ObjectType discriminatorObjectType =
                this.getBmrDao().getElement(discriminatorAttribuut.getType().getId(), ObjectType.class);
            final JavaType discriminatorWrapperJavaType =
                naamgevingStrategie.getJavaTypeVoorElement(discriminatorObjectType);
            clazz.voegExtraImportsToe(discriminatorWrapperJavaType);
            if (isSubtype(objectType)) {
                // In het geval van een finaal subtype, willen we het discriminator attribuut specifiek meegeven
                // en de super constructor aanroepen.
                if (this.isFinaalSubtype(objectType)) {
                    final JavaType discriminatorJavaType =
                            new AlgemeneNaamgevingStrategie().getJavaTypeVoorElement(discriminatorObjectType);
                    clazz.voegExtraImportsToe(discriminatorJavaType);

                    String enumExpressie = discriminatorJavaType.getNaam();
                    enumExpressie += ".";
                    enumExpressie += JavaGeneratieUtil.genereerNaamVoorEnumWaarde(discriminatorTuple.getIdentCode());

                    final String enumAttribuutExpressie = String.format("new %1$s(%2$s)",
                            discriminatorWrapperJavaType.getNaam(), enumExpressie);
                    constructor.setBody("super(" + enumAttribuutExpressie + ");");
                } else {
                    // Een tussenliggend type geeft 'gewoon' alles door aan de super constructor.
                    constructor.voegParameterToe(new JavaFunctieParameter(veldNaam, discriminatorWrapperJavaType,
                        "de waarde van het discriminator attribuut"));
                    constructor.setBody("super(" + veldNaam + ");");
                }
            } else if (isFinaalSupertype(objectType) || isNietHierarchischType(objectType)) {
                constructor.voegParameterToe(new JavaFunctieParameter(veldNaam, discriminatorWrapperJavaType,
                    "de waarde van het discriminator attribuut"));
                // In de finale supertype en niet hierarchisch type constructor moet het veld assigned worden.
                constructor.setBody("this." + veldNaam + " = " + veldNaam + ";");
            }
            clazz.voegConstructorToe(constructor);
        }

        bouwAttributenMetGettersEnSettersVoorObjectType(objectType, clazz);
        bouwInverseAssociatiesVoorObjectType(objectType, clazz);
        return clazz;
    }

    /**
     * Voegt een methode toe aan de opgegeven <code>clazz</code> die alle groepen retourneert binnen de klasse.
     *
     * @param objectType het objecttype waarvoor de groepen bepaald worden.
     * @param clazz de klasse waar de methode aan toegevoegd dient te worden.
     */
    private void voegBerichtGroepEntiteitenGetterToe(final ObjectType objectType, final JavaKlasse clazz) {
        final List<Groep> groepen = getBmrDao().getGroepenVoorObjectType(objectType, true);
        final StringBuilder methodeBody = new StringBuilder();
        methodeBody.append("final List<BerichtEntiteitGroep> berichtGroepen = new ArrayList<>();");

        boolean heeftBerichtEntiteitGroepen = false;
        for (final Groep groep : groepen) {
            if (!isIdentiteitGroep(groep) && behoortTotJavaBerichtModel(groep)
                && groep.getHistorieVastleggen() != null && groep.getHistorieVastleggen() != 'G')
            {
                methodeBody.append("berichtGroepen.add(get").append(groep.getIdentCode()).append("());");
                heeftBerichtEntiteitGroepen = true;
            }
        }
        methodeBody.append("return berichtGroepen;");

        final JavaAccessorFunctie groepenGetter = new JavaAccessorFunctie("berichtEntiteitGroepen",
            new JavaType(JavaType.LIST, new JavaType("BerichtEntiteitGroep", "nl.bzk.brp.model.basis")));
        groepenGetter.setJavaDoc("Retourneert alle groepen binnen deze entiteit.");
        groepenGetter.setReturnWaardeJavaDoc("een lijst met alle groepen binnen deze entiteit.");
        if (heeftBerichtEntiteitGroepen) {
            groepenGetter.setBody(methodeBody.toString());
            clazz.voegExtraImportsToe(JavaType.ARRAY_LIST);
        } else {
            // Indien er geen berichtentiteitgroepen zijn, dan is het puur retourneren van een lege lijst voldoende.
            groepenGetter.setBody("return Collections.emptyList();");
            clazz.voegExtraImportsToe(JavaType.COLLECTIONS);
        }
        groepenGetter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));

        clazz.voegFunctieToe(groepenGetter);
    }

    /**
     * Voegt een methode toe aan de opgegeven <code>clazz</code> die alle onderliggende/geassocieerde bericht
     * entiteiten retourneert binnen de klasse.
     *
     * @param objectType het objecttype waarvoor de bericht entiteiten bepaald worden.
     * @param clazz de klasse waar de methode aan toegevoegd dient te worden.
     */
    private void voegBerichtEntiteitenGetterToe(final ObjectType objectType, final JavaKlasse clazz) {
        final List<Attribuut> inverseAttrVoorObjectType = getBmrDao().getInverseAttributenVoorObjectType(objectType);
        final StringBuilder methodeBody = new StringBuilder();
        methodeBody.append("final List<BerichtEntiteit> berichtEntiteiten = new ArrayList<>();");

        boolean heeftBerichtEntiteiten = false;
        for (final Attribuut inverseAttr : inverseAttrVoorObjectType) {
            if (ID_BERICHT_LGM != inverseAttr.getObjectType().getId()
                && !isKoppelingObjectType(inverseAttr.getObjectType()))
            {
                methodeBody.append("if (")
                           .append(GeneratieUtil.lowerTheFirstCharacter(inverseAttr.getInverseAssociatieIdentCode()))
                           .append("!= null) {");
                methodeBody.append("berichtEntiteiten.addAll(get")
                           .append(inverseAttr.getInverseAssociatieIdentCode())
                           .append("());");
                methodeBody.append("}");
                heeftBerichtEntiteiten = true;
            }
        }
        methodeBody.append("return berichtEntiteiten;");

        final JavaAccessorFunctie entiteitenGetter = new JavaAccessorFunctie("berichtEntiteiten",
            new JavaType(JavaType.LIST, JavaType.BERICHT_ENTITEIT));
        entiteitenGetter.setJavaDoc("Retourneert alle bericht entiteiten binnen deze entiteit.");
        entiteitenGetter.setReturnWaardeJavaDoc("een lijst met alle bericht entiteiten binnen deze entiteit.");

        if (heeftBerichtEntiteiten) {
            entiteitenGetter.setBody(methodeBody.toString());
            clazz.voegExtraImportsToe(JavaType.ARRAY_LIST);
        } else {
            // Indien er geen berichtentiteiten zijn, dan is het puur retourneren van een lege lijst voldoende.
            entiteitenGetter.setBody("return Collections.emptyList();");
            clazz.voegExtraImportsToe(JavaType.COLLECTIONS);
        }
        entiteitenGetter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));

        clazz.voegFunctieToe(entiteitenGetter);
    }

    /**
     * Voegt een statische methode toe die de Meta ID van het ObjectType of Groep (type) retourneert.
     *
     * @param element het element waarvoor de Meta ID moet worden geretourneerd in de methode.
     * @param clazz de klasse waaraan de methode moet worden toegevoegd.
     */
    private void voegMetaIdGetterToe(final GeneriekElement element, final JavaKlasse clazz) {
        final JavaVeld metaIdVeld = new JavaVeld(JavaType.INTEGER, "META_ID", "De Meta ID van dit element (type).");
        metaIdVeld.setAccessModifier(JavaAccessModifier.PRIVATE);
        metaIdVeld.setStatic(true);
        metaIdVeld.setFinal(true);
        metaIdVeld.setGeinstantieerdeWaarde(element.getSyncid().toString());
        clazz.voegVeldToe(metaIdVeld);

        final JavaAccessorFunctie metaIdGetter = new JavaAccessorFunctie("MetaId", JavaType.INTEGER);
        metaIdGetter.setJavaDoc("Retourneert de Meta ID van dit element (type).");
        metaIdGetter.setReturnWaardeJavaDoc("de Meta ID van dit element (type).");
        metaIdGetter.setBody("return META_ID;");
        metaIdGetter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        clazz.voegFunctieToe(metaIdGetter);
    }

    /**
     * Bouwt de inverse associaties voor een object type. Adres kent een Persoon dus hier wordt aan Persoon een lijst
     * van adressen toegevoegd.
     * LET OP: Inverse associatie wordt enkel gegenereerd indien de inverse associatie een identCode waarde heeft
     * in het BMR.
     *
     * @param objectType Object type dat mogelijk inverse associaties kent.
     * @param clazz De Java klasse representatie van het object type waar de inverse associatie in gemaakt moet worden.
     */
    private void bouwInverseAssociatiesVoorObjectType(final ObjectType objectType, final JavaKlasse clazz) {
        final List<Attribuut> inverseAttrVoorObjectType = getBmrDao().getInverseAttributenVoorObjectType(objectType);

        for (final Attribuut inverseAttr : inverseAttrVoorObjectType) {
            if (behoortTotJavaBerichtModel(inverseAttr.getObjectType())) {
                final String inverseTypeNaam = inverseAttr.getObjectType().getIdentCode() + "Bericht";
                final String inverseTypePackage =
                    naamgevingStrategie.getJavaTypeVoorElement(inverseAttr.getObjectType()).getPackagePad();
                final JavaType inverseType = new JavaType(inverseTypeNaam, inverseTypePackage);
                final JavaType javaType = new JavaType(JavaType.LIST, inverseType);
                final String classVariabele =
                    GeneratieUtil.lowerTheFirstCharacter(inverseAttr.getInverseAssociatieIdentCode());
                final JavaVeld veld = new JavaVeld(javaType, classVariabele);
                clazz.voegVeldToe(veld);

                //Getter
                final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
                //Deze conditie moet gelijk zijn aan de conditie in de LogischModelGenerator voor het wel of niet genereren
                //van de inverse assocatie getter.
                if ((inverseAttr.getObjectType().getInOgm() == null || inverseAttr.getObjectType().getInOgm() == 'J')
                        && this.behoortTotJavaLogischModel(inverseAttr.getObjectType()))
                {
                    getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
                } else {
                    //Klasse implementeert niet het logisch model. Dus ook geen functies daarvan. Of het
                    //object type zit niet in het logisch model. (in_lgm = Nee)
                    genereerInverseAssociatieGetterJavaDoc(getter, objectType, inverseAttr);
                }
                clazz.voegGetterToe(getter);

                //Setter
                final JavaMutatorFunctie setter = new JavaMutatorFunctie(veld);
                genereerInverseAssociatieSetterJavaDoc(setter, objectType, inverseAttr);
                clazz.voegSetterToe(setter);
            }
        }
    }

    /**
     * Bouwt alle groep attributen van het objecttype inclusief de getters en setters die daarbij horen.
     *
     * @param objectType Het objecttype waar attributen voor worden gebouwd.
     * @param clazz De Java klasse waar deze getters en setters aan toegevoegd moeten worden.
     */
    private void bouwAttributenMetGettersEnSettersVoorObjectType(final ObjectType objectType, final JavaKlasse clazz) {
        //Itereer over de groepen en maak java members aan
        final List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType);
        for (final Groep groep : groepenVoorObjectType) {
            if ("Identiteit".equals(groep.getIdentCode())) {
                //De identiteit groep slaan we plat.
                bouwAttributenMetGettersEnSettersVoorIdentiteitGroep(clazz, objectType, groep);
            } else {
                if (behoortTotJavaBerichtModel(groep)) {
                    //Voor andere groepen nemen we een verwijzing op naar de groep.
                    bouwGroepAttribuutMetGettersEnSettersVoorObjectType(clazz, objectType, groep);
                }
            }

        }
    }

    /**
     * Voegt aan de java klasse een attribuut met getters en setters toe voor de groep.
     *
     * @param clazz De aan te passen java klasse.
     * @param objectType Object type waar de groep aan toebehoort.
     * @param groep De groep waarvoor eem attribuut wordt gemaakt.
     */
    private void bouwGroepAttribuutMetGettersEnSettersVoorObjectType(final JavaKlasse clazz,
        final ObjectType objectType,
        final Groep groep)
    {
        final JavaType javaTypeVoorVeld = naamgevingStrategie.getJavaTypeVoorElement(groep);

        //Veld
        final JavaVeld veld = new JavaVeld(javaTypeVoorVeld, GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode()));
        clazz.voegVeldToe(veld);

        //Getter voor veld
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        if (clazz.getSuperInterfaces().isEmpty()) {
            //Klasse implementeert niet het logisch model. Dus ook geen functies daarvan.
            genereerGetterJavaDoc(getter, objectType, groep);
        } else {
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        }
        clazz.voegGetterToe(getter);

        //Setter voor veld
        final JavaMutatorFunctie setter = new JavaMutatorFunctie(veld);
        genereerSetterJavaDoc(setter, objectType, groep);
        clazz.voegSetterToe(setter);
    }

    /**
     * Voegt aan de java klasse attributen toe die uit de identiteit groep komen. De identiteit groep kan attributen
     * bevatten die in het bericht model nodig zijn.
     * Tevens worden getters en setters toegevoegd aan de java klasse.
     *
     * @param clazz De java klasse waaraan de attributen moeten worden toegevoegd.
     * @param objectType Object type waar de groep toebehoort.
     * @param groep De identiteit groep.
     */
    private void bouwAttributenMetGettersEnSettersVoorIdentiteitGroep(final JavaKlasse clazz,
        final ObjectType objectType, final Groep groep)
    {
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
        for (final Attribuut attribuut : attributen) {
            if (behoortTotJavaBerichtModel(attribuut)) {
//                if(attribuut.getType().getId() == AbstractGenerator.ID_ELEMENT){
//                    attribuut.getType().setSoortInhoud(BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode());
//                }
//
                this.voegEventueelLogischeIdentiteitVeldToe(clazz, attribuut);

                final JavaType veldType = naamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                final JavaVeld veld = new JavaVeld(veldType, veldNaam);
                clazz.voegVeldToe(veld);

                //Implementeer getter voor veld.
                final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);

                //Als het attribuut opgenomen is in het java logische model, dan is deze getter een override.
                if (behoortTotJavaLogischModel(attribuut) && behoortTotJavaOperationeelModel(attribuut)
                    && !clazz.getSuperInterfaces().isEmpty())
                {
                    getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
                } else {
                    genereerGetterJavaDoc(getter, objectType, attribuut);
                }
                clazz.voegGetterToe(getter);

                // Implementeer setter voor veld, behalve als het hier het discriminator attribuut betreft.
                final Attribuut discriminatorAttribuut = bepaalDiscriminatorAttribuut(objectType);
                if (discriminatorAttribuut == null || discriminatorAttribuut.getId() != attribuut.getId()) {
                    final JavaMutatorFunctie setter = new JavaMutatorFunctie(veld);
                    genereerSetterJavaDoc(setter, objectType, attribuut);
                    clazz.voegSetterToe(setter);
                }
            }
        }
    }

    /**
     * Als het type van dit attribuut een object type is en dat object type is een stamgegeven,
     * voeg dan een veld toe voor de logische identiteit, voor de binding vanuit XML.
     *
     * @param clazz de java klasse
     * @param attribuut het attribuut
     */
    private void voegEventueelLogischeIdentiteitVeldToe(final JavaKlasse clazz, final Attribuut attribuut) {
        final boolean isKoppelingObjectType = attribuut.getObjectType().getKoppeling() != null
            && 'J' == attribuut.getObjectType().getKoppeling();
        // Alleen toevoegen als dit attribuut ook in de xsd voorkomt, oftewel gemapt moet worden.
        //Attributen in koppeling objecttypen moeten voorlopig altijd worden gemapped.
        if (BmrElementSoort.OBJECTTYPE.hoortBijCode(attribuut.getType().getSoortElement().getCode())
            && (behoortInXsd(attribuut) || isKoppelingObjectType))
        {
            final ObjectType attribuutTypeObjectType = this.getBmrDao().
                getElement(attribuut.getType().getId(), ObjectType.class);
            // Extra veld wordt alleen gegenereerd voor dynamische stamgegevens.
            if (attribuutTypeObjectType.getSoortInhoud() == BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode()) {
                //Het is een stamgegeven wat aangeduid wordt via een logische identiteit attribuut. We gebruiken
                //hier het veldtype String ivm voorloopnullen. (Voor bijv. landcode en nationaliteitcode)
                final JavaType veldType = JavaType.STRING;
                final Attribuut logischeIdentiteitAttribuut =
                    this.bepaalLogischeIdentiteitVoorStamgegeven(attribuutTypeObjectType);
                final String veldNaam =
                    GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()
                        + logischeIdentiteitAttribuut.getIdentCode());

                final JavaVeld veld = new JavaVeld(veldType, veldNaam);
                clazz.voegVeldToe(veld);
                final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
                genereerGetterJavaDoc(getter, attribuut.getGroep(), attribuut);
                clazz.voegGetterToe(getter);

                final JavaMutatorFunctie setter = new JavaMutatorFunctie(veld);
                this.genereerSetterJavaDoc(setter, attribuut.getGroep(), attribuut);
                clazz.voegSetterToe(setter);
            }
        }
    }

}
