/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bprbzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaMutatorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieWaardeParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.OperationeelModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.springframework.stereotype.Component;

/**
 * Deze generator genereert een model dat een volledige historie bevat.
 * Dat his volledig model wordt onder andere gebruikt voor de serialisatie van PL's.
 */
@Component("hisVolledigModelJavaGenerator")
public class HisVolledigModelGenerator extends AbstractJavaGenerator {

    private final NaamgevingStrategie hisVolledigModelNaamgevingStrategie  = new HisVolledigModelNaamgevingStrategie();
    private final NaamgevingStrategie operationeelModelNaamgevingStrategie = new OperationeelModelNaamgevingStrategie();

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final List<Integer> idsVanVolledigRootObjecten =
            Arrays.asList(ID_PERSOON_LGM, ID_RELATIE_LGM, ID_BETROKKENHEID_LGM);

        final List<JavaKlasse> klassen = new ArrayList<JavaKlasse>();
        for (Integer idVanVolledigRootObject : idsVanVolledigRootObjecten) {
            final ObjectType objectType = getBmrDao().getElement(idVanVolledigRootObject, ObjectType.class);

            // Genereer klasse voor het betreffende volledig root objecttype.
            final JavaKlasse klasse = genereerHisVolledigKlasseVoorObjectType(objectType);
            klassen.add(klasse);

            // Haal de boom op van alle object typen die in de boom van dit type zitten (zichzelf en alle subtypen).
            List<ObjectType> typenBoom = this.verzamelSubtypen(objectType, false);
            final List<Attribuut> inverseAttributenVoorObjectTypeBoom = new ArrayList<Attribuut>();
            final List<Groep> groepenVoorObjectTypeBoom = new ArrayList<Groep>();
            for (ObjectType typeNode : typenBoom) {
                inverseAttributenVoorObjectTypeBoom.addAll(
                    this.getBmrDao().getInverseAttributenVoorObjectType(typeNode));
                groepenVoorObjectTypeBoom.addAll(this.getBmrDao().getGroepenVoorObjectType(typeNode));
            }

            // Genereer de velden voor alle groepen die op dit object type van toepassing zijn.
            voegVeldenToeVoorGroepen(klasse, objectType, groepenVoorObjectTypeBoom);

            // Genereer velden/lijsten voor de inverse associaties.
            voegVeldenToeVoorInverseAssociatieAttributen(klasse, objectType, inverseAttributenVoorObjectTypeBoom);

            // Genereer his volledig klassen voor de inverse associatie objecttypen.
            for (Attribuut attribuut : inverseAttributenVoorObjectTypeBoom) {
                ObjectType attribuutObjectType = attribuut.getObjectType();
                JavaKlasse attribuutObjectTypeJavaKlasse =
                    genereerHisVolledigKlasseVoorObjectType(attribuutObjectType);
                voegVeldenToeVoorGroepen(attribuutObjectTypeJavaKlasse, attribuutObjectType,
                    this.getBmrDao().getGroepenVoorObjectType(attribuutObjectType));
                klassen.add(attribuutObjectTypeJavaKlasse);
            }
        }
        final JavaWriter<JavaKlasse> writer = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        final List<JavaKlasse> gegenereerdeKlassen = writer.genereerEnSchrijfWeg(klassen, this);
        rapporteerOverGegenereerdeJavaTypen(gegenereerdeKlassen);
    }

    /**
     * Voegt velden toe voor de inverse associatie attributen velden in de Persoon klasse. Tevens worden getters en
     * setters toegevoegd en bijbehorende annotaties.
     *
     * @param persoonVolledigKlasse de persoon klasse
     * @param objectType het object type
     * @param inverseAttrVoorObjectType de inverse associatie attributen van persoon
     */
    private void voegVeldenToeVoorInverseAssociatieAttributen(final JavaKlasse persoonVolledigKlasse,
        final ObjectType objectType, final List<Attribuut> inverseAttrVoorObjectType)
    {
        for (Attribuut attribuut : inverseAttrVoorObjectType) {
            final ObjectType inverseObjectType = attribuut.getObjectType();
            final JavaType javaTypeVoorElement = hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(
                inverseObjectType);
            final JavaVeld veld = new JavaVeld(
                new JavaType(JavaType.SET, javaTypeVoorElement),
                JavaGeneratieUtil.toValidJavaVariableName(attribuut.getInverseAssociatieIdentCode()));
            boolean jsonPropertyOpnemenEnEagerCollectie = true;
            // Custom hack om in specifieke gevallen geen property annotatie op te nemen en
            // de collectie lazy te loaden, zie ROMEO-131.
            if (objectType.getId() == ID_RELATIE_LGM && veld.getNaam().equals("betrokkenheden")) {
                jsonPropertyOpnemenEnEagerCollectie = false;
            }
            annoteerHisCollectieVeld(veld, inverseObjectType, objectType,
                jsonPropertyOpnemenEnEagerCollectie, jsonPropertyOpnemenEnEagerCollectie, false);


            persoonVolledigKlasse.voegVeldToe(veld);

            //Genereer een getter.
            final JavaAccessorFunctie javaAccessorFunctie = new JavaAccessorFunctie(veld);
            javaAccessorFunctie.setJavaDoc("Retourneert lijst van " + inverseObjectType.getNaam() + ".");
            javaAccessorFunctie.setReturnWaardeJavaDoc("lijst van " + inverseObjectType.getNaam());
            persoonVolledigKlasse.voegGetterToe(javaAccessorFunctie);

            //Genereer een setter.
            final JavaMutatorFunctie javaMutatorFunctie = new JavaMutatorFunctie(veld);
            javaMutatorFunctie.setJavaDoc("Zet lijst van " + inverseObjectType.getNaam() + ".");
            javaMutatorFunctie.getParameters().get(0).setJavaDoc("lijst van " + inverseObjectType.getNaam());
            persoonVolledigKlasse.voegSetterToe(javaMutatorFunctie);
        }
    }

    /**
     * Genereert een klasse voor een objectType.
     * Voegt nog geen velden toe.
     *
     * @param objectType het objecttype
     * @return java klasse
     */
    private JavaKlasse genereerHisVolledigKlasseVoorObjectType(final ObjectType objectType) {
        JavaKlasse hisVolledigJavaKlasse = new JavaKlasse(
            hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(objectType),
            "HisVolledig klasse voor " + objectType.getNaam() + ".");
        hisVolledigJavaKlasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.ENTITY));
        final JavaAnnotatie tabelAnnotatie = new JavaAnnotatie(JavaType.TABLE,
            new AnnotatieWaardeParameter("schema", objectType.getSchema().getNaam()),
            new AnnotatieWaardeParameter("name", objectType.getIdentDb()));
        hisVolledigJavaKlasse.voegAnnotatieToe(tabelAnnotatie);
        return hisVolledigJavaKlasse;
    }

    /**
     * Genereer de velden voor de groepen die horen bij dit object type in de meegegeven klasse en voeg deze toe
     * aan de klasse. Voegt tevens benodigde annotaties en getters en setters toe.
     *
     * @param klasse de java klasse
     * @param objectType het object type
     * @param groepen de groepen
     */
    private void voegVeldenToeVoorGroepen(final JavaKlasse klasse, final ObjectType objectType,
        final List<Groep> groepen)
    {
        for (Groep groep : groepen) {
            if (isIdentiteitGroep(groep)) {
                //Identiteit groep platslaan en annoteren.
                voegVeldenToeVoorAttributenInIdentiteitsGroep(klasse, objectType, groep);
            } else if (groepKentHistorie(groep)) {
                //Genereer voor de groep een historie set.
                voegVeldToeVoorHisSet(klasse, objectType, groep);
            }
        }
    }

    /**
     * Genereert een lijst in javaKlasse waar historie objecttypen in zitten die horen bij groep. Voegt tevens
     * de benodigde annotaties toe en getter en setter methodes.
     *
     * @param javaKlasse klasse waar de lijst in moet.
     * @param objectType objecttype uit BMR.
     * @param groep groep die historie kent.
     */
    private void voegVeldToeVoorHisSet(final JavaKlasse javaKlasse, final ObjectType objectType, final Groep groep) {
        final ObjectType hisObjectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);

        if (hisObjectType != null) {
            final JavaType javaTypeVoorElement = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(
                hisObjectType);
            final JavaVeld veld = new JavaVeld(new JavaType(JavaType.SET, javaTypeVoorElement),
                JavaGeneratieUtil.toValidJavaVariableName(hisObjectType.getIdentCode()) + "Lijst");

            annoteerHisCollectieVeld(veld, hisObjectType, objectType, true, true, true);
            javaKlasse.voegVeldToe(veld);

            // Genereer een getter.
            final JavaAccessorFunctie javaAccessorFunctie = new JavaAccessorFunctie(veld);
            javaAccessorFunctie.setJavaDoc("Retourneert lijst van historie van " + groep.getNaam() + " van "
                + objectType.getNaam() + ".");
            javaAccessorFunctie.setReturnWaardeJavaDoc("lijst van historie voor groep " + groep.getNaam());
            javaKlasse.voegGetterToe(javaAccessorFunctie);

            // Genereer een setter.
            final JavaMutatorFunctie javaMutatorFunctie = new JavaMutatorFunctie(veld);
            javaMutatorFunctie.setJavaDoc("Zet lijst van historie van " + groep.getNaam() + " van "
                + objectType.getNaam() + ".");
            javaMutatorFunctie.getParameters().get(0).setJavaDoc("lijst van historie voor groep " + groep.getNaam());
            javaKlasse.voegSetterToe(javaMutatorFunctie);
        }
    }

    /**
     * Annoteer het veld van een his collectie.
     * Geldt voor zowel inverse associaties als voor groepen.
     *
     * @param veld het veld
     * @param hisObjectType het (his) object type dat in de collectie zit
     * @param objectType het object type van de klasse waar het veld in zit
     * @param eager of het een eager annotatie moet worden of niet
     * @param jsonAnnotatie of er een json property annotatie moet worden toegevoegd
     * @param komtUitGroep of het his object afkomstig is van een groep
     */
    private void annoteerHisCollectieVeld(final JavaVeld veld,
        final ObjectType hisObjectType, final ObjectType objectType,
        final boolean eager, final boolean jsonAnnotatie, final boolean komtUitGroep)
    {
        String fetchTypeEnumWaarde = "EAGER";
        if (!eager) {
            fetchTypeEnumWaarde = "LAZY";
        }
        veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ONE_TO_MANY,
            new AnnotatieWaardeParameter("fetch", JavaType.FETCH_TYPE, fetchTypeEnumWaarde),
            new AnnotatieWaardeParameter("mappedBy", getMappedBy(hisObjectType, objectType, komtUitGroep))));
        // Fetch mode join is niet van toepassing by fetch mode lazy.
        if (eager) {
            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.FETCH,
                new AnnotatieWaardeParameter("value", JavaType.FETCH_MODE, "JOIN")));
        }
        if (jsonAnnotatie) {
            voegJsonPropertyAnnotatieToe(veld);
        }
    }

    /**
     * Bepaal de waarde van de mapped by voor de JPA annotatie.
     * Doe dit als volgt: zoek het attribuut van het van object type dat wijst naar het naar object type
     * en neem daarvan de ident code.
     *
     * @param vanObjectType het van object type
     * @param naarObjectType het naar object type
     * @param komtUitGroep of het his object afkomstig is van een groep
     * @return de mapped by
     */
    private String getMappedBy(final ObjectType vanObjectType, final ObjectType naarObjectType,
        final boolean komtUitGroep)
    {
        String mappedBy = null;

        // We lopen door de lijst van attributen totdat we het juiste attribuut vinden.
        for (Attribuut attribuut : this.getBmrDao().getAttributenVanObjectType(vanObjectType)) {
            final Attribuut lgmAttribuut;
            if (attribuut.getElementByLaag().getId() == BmrLaag.OPERATIONEEL.getWaardeInBmr()) {
                // Haal het equivalente logische laag attribuut op. Gebruik daarvoor de org sync id,
                // omdat die de link legt vanuit het his groep attribuut in de operationele laag.
                lgmAttribuut = this.getBmrDao().getElementVoorSyncIdVanLaag(
                    attribuut.getOrgSyncid(), BmrLaag.LOGISCH, Attribuut.class);
            } else {
                lgmAttribuut = attribuut;
            }

            // We hebben de backreferene gevonden als het type van het attribuut het object type is
            // en het equivalente lgm attribuut niet bestaat of in de identiteit groep zit.
            // (dat laatste is nodig ivm lgm attributen met het 'back-reference type', zoals vorige/volgende persoon).
            if (attribuut.getType().getSyncid().equals(naarObjectType.getSyncid())
                && (komtUitGroep && lgmAttribuut == null
                || !komtUitGroep && isIdentiteitGroep(attribuut.getGroep())))
            {
                mappedBy = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                break;
            }
        }
        return mappedBy;
    }

    /**
     * Genereert velden voor elk attribuut dat voorkomt in de identiteit groep. Voor elk veld wordt ook een getter
     * toegevoegd, maar geen setter.
     *
     * @param clazz Java klasse waarin de velden terecht komen.
     * @param objectType Object type uit het BMR waar deze groep bij hoort.
     * @param groep De identiteit groep.
     */
    private void voegVeldenToeVoorAttributenInIdentiteitsGroep(final JavaKlasse clazz, final ObjectType objectType,
        final Groep groep)
    {
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
        for (Attribuut attribuut : attributen) {
            if (behoortTotJavaOperationeelModel(attribuut)) {
                if (isIdAttribuut(attribuut)) {
                    voegVeldToeVoorIdAttribuutInIdentiteitGroep(clazz, objectType, attribuut);
                } else {
                    voegVeldToeVoorAttribuutInIdentiteitGroep(clazz, objectType, attribuut);
                }
            }
        }
    }

    /**
     * Voegt veld toe voor een id attribuut dat voorkomt in de identiteit groep. Tevens wordt er een getter methode
     * toegevoegd voor het veld.
     *
     * @param klasse Java klasse waarin de velden terecht komen.
     * @param objectType Object type uit het BMR waar deze groep bij hoort.
     * @param attribuut Het attribuut.
     */
    private void voegVeldToeVoorIdAttribuutInIdentiteitGroep(final JavaKlasse klasse,
        final ObjectType objectType, final Attribuut attribuut)
    {
        final JavaVeld idVeld = genereerJavaVeldVoorAttribuutType(attribuut);
        this.annoteerIdVeld(objectType, idVeld, true);
        klasse.voegVeldToe(idVeld);
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(idVeld);
        genereerGetterJavaDoc(getter, objectType, attribuut);
        klasse.voegGetterToe(getter);
    }

    /**
     * Genereert velden voor een niet id attribuut dat voorkomt in de identiteit groep.
     * Voor elk veld wordt ook een getter toegevoegd, maar geen setter.
     *
     * @param klasse Java klasse waarin de velden terecht komen.
     * @param objectType Object type uit het BMR waar deze groep bij hoort.
     * @param attribuut Het attribuut.
     */
    private void voegVeldToeVoorAttribuutInIdentiteitGroep(final JavaKlasse klasse,
        final ObjectType objectType, final Attribuut attribuut)
    {
        final JavaType javaType;
        if (BmrElementSoort.OBJECTTYPE.getCode().equals(attribuut.getType().getSoortElement().getCode())) {
            if (BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == attribuut.getType().getSoortInhoud()) {
                final ObjectType element = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                javaType = hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(element);
            } else {
                javaType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
            }
            final JavaVeld veld =
                new JavaVeld(javaType, GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()));
            this.annoteerAttribuutVeld(objectType, attribuut, veld,
                isDiscriminatorAttribuut(attribuut, bepaalDiscriminatorAttribuut(objectType)),
                isJsonProperty(attribuut), true);
            klasse.voegVeldToe(veld);

            final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
            genereerGetterJavaDoc(getter, objectType, attribuut);
            klasse.voegGetterToe(getter);
        }
    }

    /**
     * Checkt of een groep historie kent.
     *
     * @param groep de te checken groep.
     * @return true indien de groep formele dan wel materiele historie kent, in alle andere gevallen false.
     */
    private boolean groepKentHistorie(final Groep groep) {
        return groep.getHistorieVastleggen() == 'F' || groep.getHistorieVastleggen() == 'B';
    }

    @Override
    protected GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.HIS_VOLLEDIG_MODEL_JAVA_GENERATOR;
    }
}
