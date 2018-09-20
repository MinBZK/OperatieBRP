/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.List;

import nl.bprbzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrElementFilterObject;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
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
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/** Generator die de Java Classes genereert voor het bericht model. */
@Component("berichtModelJavaGenerator")
public class BerichtModelGenerator extends AbstractJavaGenerator {

    private final NaamgevingStrategie naamgevingStrategie               = new BerichtModelNaamgevingStrategie();
    private final NaamgevingStrategie superInterfaceNaamgevingStrategie = new LogischModelNaamgevingStrategie();

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        // Filter op logische laag, op 'in bericht' en geen stamgegevens
        final BmrElementFilterObject filter = new BmrElementFilterObject(BmrLaag.LOGISCH);
        filter.setSoortInhoud('D');

        // Haal objecttypen op
        final List<ObjectType> objectTypen = getBmrDao().getObjectTypen(filter);

        // Genereer voor alle objecttypen de benodigde Bericht Model classes.
        final List<JavaKlasse> objectClasses = new ArrayList<JavaKlasse>();
        for (ObjectType objectType : objectTypen) {
            if (behoortTotJavaBerichtModel(objectType)) {
                //Bouw java klasse voor objecttype.
                final JavaKlasse javaKlasse = bouwJavaKlasseVoorObjectType(objectType);

                //Als het object xsd views per discriminator waarde heeft dan moeten we voor de binding per
                //discriminator waarde een aparte klasse genereren. Dus per Tuple krijg je een klasse die extend
                //van dit objecttype.
                if (objectType.getXsdViewsPerDiscriminatorWaarde() != null
                        && 'J' == objectType.getXsdViewsPerDiscriminatorWaarde())
                {
                    //Maak de superklasse van de tuples klassen abstract.
                    javaKlasse.setAbstractClass(true);

                    //Als het objecttype een attribuut heeft waarvan het attribuuttype tuples kent, bouw dan per tuple
                    //een class. Deze class zal dan het objecttype extenden. Dit t.b.v. binding van xsd views.
                    final List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType);
                    for (Groep groep : groepenVoorObjectType) {
                        if (IDENTITEIT.equals(groep.getNaam())) {
                            final List<Attribuut> attributenVanGroep = getBmrDao().getAttributenVanGroep(groep);
                            for (Attribuut attribuut : attributenVanGroep) {
                                //Tuples behoren enkel tot statische stamgegevens
                                if (attribuut.getType().getSoortInhoud() != null
                                        && BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode()
                                        == attribuut.getType().getSoortInhoud())
                                {
                                    final ObjectType stamgegevenDatTuplesBevat =
                                            getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                                    for (Tuple tuple : stamgegevenDatTuplesBevat.getTuples()) {
                                        objectClasses.add(bouwJavaKlasseVoorTuple(objectType, tuple));
                                    }
                                }
                            }
                        }
                    }
                }

                //Voeg de java klasse toe aan de collectie.
                objectClasses.add(javaKlasse);
            }
        }

        //Haal groepen op
        final List<Groep> groepen = getBmrDao().getGroepen();
        //Genereer voor alle groepen de benodigde Bericht Model classes.
        final List<JavaKlasse> groepClasses = new ArrayList<JavaKlasse>();
        for (Groep groep : groepen) {
            if (behoortTotJavaBerichtModel(groep.getObjectType())
                && behoortTotJavaBerichtModel(groep))
            {
                groepClasses.add(bouwJavaKlasseVoorGroep(groep));
            }
        }

        final JavaWriter<JavaKlasse> writer = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        final List<JavaKlasse> gegenereerdeJavaKlasses = new ArrayList<JavaKlasse>();
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
        JavaType typeVoorTuple = naamgevingStrategie.getJavaTypeVoorElement(tuple);
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
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, klasseNaam);
        JavaType javaTypeVoorTuple = naamgevingStrategie.getJavaTypeVoorElement(tuple.getObjectType());
        klasse.voegExtraImportToe(javaTypeVoorTuple.getFullyQualifiedClassName());
        constructor.setBody(String.format("super(%1$s.%2$s);", javaTypeVoorTuple.getNaam(),
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

        clazz.setExtendsFrom(JavaType.ABSTRACT_GROEP_BERICHT);

        // Voeg super interface toe indien de groep tot het java logische model behoort.
        if (behoortTotJavaLogischModel(groep)) {
            clazz.voegSuperInterfaceToe(superInterfaceNaamgevingStrategie.getJavaTypeVoorElement(groep));
        }

        bouwAttributenMetGettersEnSettersVoorGroep(groep, clazz);
        return clazz;
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
        for (Attribuut attribuut : attributen) {
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
                if (behoortTotJavaLogischModel(groep) && behoortTotJavaLogischModel(attribuut)) {
                    getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
                }
                clazz.voegGetterToe(getter);

                //Maak een mutator aan oftewel een setter.
                final JavaMutatorFunctie setter = new JavaMutatorFunctie(attribuutVeldInGroep);
                genereerSetterJavaDoc(setter, groep, attribuut);
                clazz.voegSetterToe(setter);
            }
        }
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

        // Zet super klasse: de BMR super klasse, abstract object type bericht of geen.
        if (heeftSupertype(objectType)) {
            clazz.setExtendsFrom(naamgevingStrategie.getJavaTypeVoorElement(objectType.getSuperType()));
        } else if (ID_BERICHT_LGM != objectType.getId()) {
            //Enkel objecttype bericht is niet identificeerbaar in de xml en moet de superklasse niet extenden.
            clazz.setExtendsFrom(JavaType.ABSTRACT_OBJECT_TYPE_BERICHT);
        }

        // Als een klasse een super type is, oftewel zelf geen finaal subtype is, dan moet hij abstract zijn.
        if (isSupertype(objectType)) {
            clazz.setAbstractClass(true);
        }

        // Voeg super interface toe, inclusief import
        if (behoortTotJavaLogischModel(objectType)) {
            clazz.voegSuperInterfaceToe(superInterfaceNaamgevingStrategie.getJavaTypeVoorElement(objectType));
        }

        //TODO: Veel overlap met operationeel model generator stuk, samenvoegen?
        // Als het een hierarchisch type is, maak een constructor aan voor het discriminator attribuut.
        if (isHierarchischType(objectType) || StringUtils.isNotBlank(objectType.getDiscriminatorAttribuut())) {
            Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, clazz.getNaam());
            constructor.setJavaDoc("Constructor die het discriminator attribuut zet of doorgeeft.");
            Attribuut discriminatorAttribuut = this.bepaalDiscriminatorAttribuut(objectType);
            Tuple discriminatorTuple = this.bepaalDiscriminatorTuple(objectType);
            String veldNaam = GeneratieUtil.lowerTheFirstCharacter(discriminatorAttribuut.getIdentCode());
            final ObjectType discriminatorObjectType =
                    this.getBmrDao().getElement(discriminatorAttribuut.getType().getId(), ObjectType.class);
            final JavaType discriminatorJavaType = new AlgemeneNaamgevingStrategie().
                    getJavaTypeVoorElement(discriminatorObjectType);
            clazz.voegExtraImportToe(discriminatorJavaType.getFullyQualifiedClassName());
            if (isSubtype(objectType)) {
                // In het geval van een finaal subtype, willen we het discriminator attribuut specifiek meegeven
                // en de super constructor aanroepen.
                if (this.isFinaalSubtype(objectType)) {
                    String enumExpressie = discriminatorJavaType.getNaam();
                    enumExpressie += ".";
                    enumExpressie += JavaGeneratieUtil.genereerNaamVoorEnumWaarde(discriminatorTuple.getIdentCode());
                    constructor.setBody("super(" + enumExpressie + ");");
                } else {
                    // Een tussenliggend type geeft 'gewoon' alles door aan de super constructor.
                    constructor.voegParameterToe(new JavaFunctieParameter(veldNaam, discriminatorJavaType,
                            "de waarde van het discriminator attribuut"));
                    constructor.setBody("super(" + veldNaam + ");");
                }
            } else if (isFinaalSupertype(objectType) || isNietHierarchischType(objectType)) {
                constructor.voegParameterToe(new JavaFunctieParameter(veldNaam, discriminatorJavaType,
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

        for (Attribuut inverseAttr : inverseAttrVoorObjectType) {
            final String inverseTypeNaam = inverseAttr.getObjectType().getIdentCode() + "Bericht";
            final String inverseTypePackage = naamgevingStrategie.
                    getJavaTypeVoorElement(inverseAttr.getObjectType()).getPackagePad();
            JavaType inverseType = new JavaType(inverseTypeNaam, inverseTypePackage);
            final JavaType javaType = new JavaType(JavaType.LIST, inverseType);
            final String classVariabele =
                    GeneratieUtil.lowerTheFirstCharacter(inverseAttr.getInverseAssociatieIdentCode());
            final JavaVeld veld = new JavaVeld(javaType, classVariabele);
            clazz.voegVeldToe(veld);

            //Getter
            final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
            if (clazz.getSuperInterfaces().isEmpty()
                || 'N' == inverseAttr.getObjectType().getInSet())
            {
                //Klasse implementeert niet het logisch model. Dus ook geen functies daarvan. Of het
                //object type zit niet in het logisch model. (in_set = Nee)
                genereerInverseAssociatieGetterJavaDoc(getter, objectType, inverseAttr);
            } else {
                getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            }
            clazz.voegGetterToe(getter);

            //Setter
            final JavaMutatorFunctie setter = new JavaMutatorFunctie(veld);
            genereerInverseAssociatieSetterJavaDoc(setter, objectType, inverseAttr);
            clazz.voegSetterToe(setter);
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
        for (Groep groep : groepenVoorObjectType) {
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
        JavaVeld veld = new JavaVeld(javaTypeVoorVeld, GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode()));
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
        for (Attribuut attribuut : attributen) {
            if (behoortTotJavaBerichtModel(attribuut)) {
                this.voegEventueelLogischeIdentiteitVeldToe(clazz, attribuut);

                final JavaType veldType = naamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                final JavaVeld veld = new JavaVeld(veldType, veldNaam);
                clazz.voegVeldToe(veld);

                //Implementeer getter voor veld.
                final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);

                //Als het attribuut opgenomen is in het java logische model, dan is deze getter een override.
                if (behoortTotJavaLogischModel(attribuut)
                    && !clazz.getSuperInterfaces().isEmpty())
                {
                    getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
                } else {
                    genereerGetterJavaDoc(getter, objectType, attribuut);
                }
                clazz.voegGetterToe(getter);

                // Implementeer setter voor veld, behalve als het hier het discriminator attribuut betreft.
                Attribuut discriminatorAttribuut = bepaalDiscriminatorAttribuut(objectType);
                if (discriminatorAttribuut == null || (discriminatorAttribuut.getId() != attribuut.getId())) {
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
     * @param clazz     de java klasse
     * @param attribuut het attribuut
     */
    private void voegEventueelLogischeIdentiteitVeldToe(final JavaKlasse clazz, final Attribuut attribuut) {
        final boolean isKoppelingObjectType = attribuut.getObjectType().getKoppeling() != null
                && 'J' == attribuut.getObjectType().getKoppeling();
        // Alleen toevoegen als dit attribuut ook in de xsd voorkomt, oftewel gemapt moet worden.
        //Attributen in koppeling objecttypen moeten voorlopig altijd worden gemapped.
        if (attribuut.getType().getSoortElement().getCode().equals(BmrElementSoort.OBJECTTYPE.getCode())
                && (behoortInXsd(attribuut) || isKoppelingObjectType))
        {
            ObjectType attribuutTypeObjectType = this.getBmrDao().
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
                JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
                genereerGetterJavaDoc(getter, attribuut.getGroep(), attribuut);
                clazz.voegGetterToe(getter);

                JavaMutatorFunctie setter = new JavaMutatorFunctie(veld);
                this.genereerSetterJavaDoc(setter, attribuut.getGroep(), attribuut);
                clazz.voegSetterToe(setter);
            }
        }
    }

}
