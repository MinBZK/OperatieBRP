/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.common.SoortHistorie;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaGenericParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigInterfaceModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigMomentPredikaatViewModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.OperationeelModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.util.JavaGeneratorConstante;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.springframework.stereotype.Component;

/**
 * Deze generator genereert de model predikaat moment views. LET OP!! Deze klasse is gekopieerd vanuit de HisVolledigMomentViewModelGenerator en vervolgens
 * aangepast. We moeten kijken of we uiteindelijk de HisVolledigMomentViewModelGenerator uit kunnen faseren door de code die gebruikt maakt van de
 * PersoonView om te schrijven naar de PersoonPredikaatView.
 */
@Component("hisVolledigMomentPredikaatViewModelGenerator")
public class HisVolledigMomentPredikaatViewModelGenerator extends AbstractHisVolledigGenerator {

    private static final String ACTUELE_GEGEVENS_CHECK_METHODE_NAAM = "heeftActueleGegevens";
    private static final String NAAM_PREDIKAAT_VELD                 = "predikaat";
    private static final String NAAM_PREDIKAAT_VELD_GETTER          = "getPredikaat";

    private final NaamgevingStrategie                          hisVolledigMomentViewModelNaamgevingStrategie =
        new HisVolledigMomentPredikaatViewModelNaamgevingStrategie();
    private final NaamgevingStrategie                          operationeelModelNaamgevingStrategie          = new OperationeelModelNaamgevingStrategie();
    private final HisVolledigInterfaceModelNaamgevingStrategie hisVolledigInterfaceModelNaamgevingStrategie  =
        new HisVolledigInterfaceModelNaamgevingStrategie();

    @Override
    protected GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.HIS_VOLLEDIG_MOMENT_VIEW_MODEL_GENERATOR;
    }

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final List<Integer> idsVanHisVolledigRootObjecten =
            Arrays.asList(ID_PERSOON_LGM, ID_RELATIE_LGM, ID_BETROKKENHEID_LGM, ID_ONDERZOEK_LGM);

        final List<JavaKlasse> klassen = new ArrayList<>();
        for (Integer idVanHisVolledigRootObject : idsVanHisVolledigRootObjecten) {
            final ObjectType objectType = getBmrDao().getElement(idVanHisVolledigRootObject, ObjectType.class);
            final List<ObjectType> teGenererenObjecttypen = verzamelSubtypen(objectType, false);

            for (ObjectType type : teGenererenObjecttypen) {
                // Genereer klasse voor het betreffende de objecttype.
                klassen.add(genereerViewKlasseVoorObjectType(type));

                final List<Attribuut> inverseAttributenVoorObjectType =
                    getBmrDao().getInverseAttributenVoorObjectType(type);
                // Genereer ook klassen voor alle inverse object typen (indien niet al gedaan)
                for (Attribuut attribuut : inverseAttributenVoorObjectType) {
                    final ObjectType attribuutObjectType = attribuut.getObjectType();
                    /**
                     * Objecttypen die we al genereren hier niet meenemen. En inverse associaties alleen maar aanleggen
                     * als de objecttypen van die inverse associatie tot het operationeel model behoren. Dus niet enkel
                     * bericht model. (in_OGM != NEE)
                     */
                    if (!idsVanHisVolledigRootObjecten.contains(attribuutObjectType.getId())
                        && behoortTotJavaOperationeelModel(attribuutObjectType))
                    {
                        klassen.add(genereerViewKlasseVoorObjectType(attribuutObjectType));
                    }
                }
            }
        }

        //Schrijf weg en rapporteer.
        final JavaWriter<JavaKlasse> writer = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        voegGeneratedAnnotatiesToe(klassen, generatorConfiguratie);
        final List<JavaKlasse> gegenereerdeKlassen = writer.genereerEnSchrijfWeg(klassen, this);

        rapporteerOverGegenereerdeJavaTypen(gegenereerdeKlassen);
    }

    /**
     * Genereer een view/proxy klasse voor het objecttype.
     *
     * @param objectType het objecttype.
     * @return Java klasse voor de view.
     */
    private JavaKlasse genereerViewKlasseVoorObjectType(final ObjectType objectType) {
        final boolean isSubType = isSubtype(objectType);
        //Haal groepen op.
        final List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType);
        final JavaKlasse klasse = new JavaKlasse(
            hisVolledigMomentViewModelNaamgevingStrategie.getJavaTypeVoorElement(objectType),
            "View klasse voor " + objectType.getNaam() + ".");

        // Voeg de super tagging interface toe.
        klasse.voegSuperInterfaceToe(JavaType.MODEL_MOMENT_INTERFACE);
        //Voeg de superinterface toe van het logische model.
        klasse.voegSuperInterfaceToe(bepaalLogischModelSuperInterface(objectType));

        if (isSubType) {
            klasse.setExtendsFrom(
                hisVolledigMomentViewModelNaamgevingStrategie.getJavaTypeVoorElement(objectType.getSuperType()));
        } else if (isSupertype(objectType)) {
            // Als het een supertype is, kunnen er nooit direct instanties van deze klasse aangemaakt worden.
            klasse.setAbstractClass(true);
        }

        //Voeg een veld toe voor het HisVolledigXXX proxy object.
        ObjectType typeVoorProxyObjectVeld = objectType;
        //Voor subtypen blijft het type het finale super type. Want er is in de hierarchie maar één proxy object veld.
        if (isSubType) {
            typeVoorProxyObjectVeld = objectType.getFinaalSupertype();
        }

        final JavaVeld hisVolledigProxyObjectVeld = new JavaVeld(
            hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(typeVoorProxyObjectVeld),
            GeneratieUtil.lowerTheFirstCharacter(typeVoorProxyObjectVeld.getIdentCode()),
            true);

        if (!isSubType) {
            //Het proxy object veld hoort alleen in superklassen thuis.
            klasse.voegVeldToe(hisVolledigProxyObjectVeld);

            //Voor supertypen voegen we een getter toe, want subtypen moeten het veld kunnen benaderen.
            //Let wel op: dit MOET een protected getter zijn, we willen niet dat van buitenaf het proxy object veld
            //benadert wordt.
            if (!objectType.getSubtypen().isEmpty()) {
                final JavaAccessorFunctie javaAccessorFunctie = new JavaAccessorFunctie(hisVolledigProxyObjectVeld);
                javaAccessorFunctie.setAccessModifier(JavaAccessModifier.PROTECTED);
                javaAccessorFunctie.setFinal(true);
                javaAccessorFunctie.setJavaDoc("Retourneert het proxyobject dat achter deze view hangt.");
                javaAccessorFunctie.setReturnWaardeJavaDoc("het proxyobject dat achter deze view hangt");
                klasse.voegGetterToe(javaAccessorFunctie);
            }
        }

        final boolean isFormeelPeilmomentVeldNodig = bepaalOfFormeelPeilmomentVeldNodigIs(objectType);
        final boolean isMaterieelPeilmomentVeldNodig = bepaalOfMaterieelPeilmomentVeldNodigIs(objectType);

        // Voeg predikaat veld toe, waar dat van toepassing is qua historie patroon.
        final JavaVeld predikaatVeld = new JavaVeld(JavaType.PREDIKAAT_APACHE, NAAM_PREDIKAAT_VELD, true);
        predikaatVeld.setAccessModifier(JavaAccessModifier.PRIVATE);
        if (isFormeelPeilmomentVeldNodig || isMaterieelPeilmomentVeldNodig) {
            klasse.voegVeldToe(predikaatVeld);
        }

        /**
         * Protected getters nodig voor het predikaat veld, omdat subtypen erbij moeten kunnen.
         */
        if (isFormeelPeilmomentVeldNodig || isMaterieelPeilmomentVeldNodig) {
            JavaAccessorFunctie predikaatVeldGetter = new JavaAccessorFunctie(predikaatVeld);
            predikaatVeldGetter.setAccessModifier(JavaAccessModifier.PROTECTED);
            predikaatVeldGetter.setFinal(true);
            predikaatVeldGetter.setJavaDoc("Retourneert het predikaat voor deze view.");
            predikaatVeldGetter.setReturnWaardeJavaDoc("Het predikaat voor deze view.");
            klasse.voegGetterToe(predikaatVeldGetter);
        }


        // Voeg constructoren toe.
        voegConstructorToeAanViewKlasse(klasse, objectType, hisVolledigProxyObjectVeld);

        // Voeg een methode toe die checkt of er actuele gegevens in deze view zitten.
        voegActueleGegevensCheckFunctieToe(klasse, objectType);

        // Implementeer de logische interface functies voor de groepen.
        implementeerLogischeInterfaceFunctiesVoorGroepen(objectType, groepenVoorObjectType, klasse,
            hisVolledigProxyObjectVeld);

        // Implementeer de logische interface functies voor de inverse associaties.
        implementeerLogischeInterfaceFunctiesVoorInverseAssociaties(objectType, klasse, hisVolledigProxyObjectVeld);

        if (objectType.getId() == ID_PERSOON_LGM) {
            voegGettersToeVoorIndicaties(klasse, hisVolledigMomentViewModelNaamgevingStrategie, false);
        }

        // Voeg de 'Element getElement()' functie toe, alleen voor objecttypen van het KERN schema.
        if (objectType.getSchema().getId() == ID_KERN_SCHEMA) {
            voegGetElementFunctieToe(klasse, objectType);
        }

        return klasse;
    }

    /**
     * Bepaalt of in een klasse voor een objecttype een formeel/materieel peilmoment veld nodig is. Je zou in eerste instantie denken dat dit alleen nodig
     * is als het objecttype een groep heeft met formele/materiele historie. Dit is echter niet voldoende. Het kan zijn dat het objecttype een inverse
     * associatie objecttype heeft met formele/materiele historie, of het objecttype heeft een subtype dat een groep kent met formele/materiele historie.
     * Kort samengevat komt dit neer op het volgende: (Van simpel naar complex)
     * <p/>
     * - Het objecttype zelf heeft een groep met formele/materiele historie - OF Het objecttype heeft minstens één groep waarvan een attribuut een
     * objecttype is, en dit objecttype op zijn beurt een groep kent met formele/materiele historie.(Dit is bij 99% van de gevallen de identiteit groep die
     * dus vaak een backreference kent naar bijvoorbeeld persoon) - OF het objecttype kent een inverse assocatie attribuut dat een objecttype is met
     * formele/materiele historie. - EN het objecttype is géén subtype, dit omdat we het formeel/materieel peilmoment veld opnemen in de superklasse en
     * protected maken, anders krijgen we dubbele velden in de klasse hierarchie.
     * <p/>
     * Aanpassingen in deze functie dienen met zorgvuldigheid te worden gemaakt.
     *
     * @param objectType    het objecttype
     * @param soortHistorie het soort historie
     * @return true indien het veld moet worden opgenomen, anders false.
     */
    private boolean bepaalOfPeilmomentVeldNodigIsVoorHistorie(
        final ObjectType objectType, final SoortHistorie soortHistorie)
    {
        boolean resultaat = false;
        final List<Groep> groepenVoorObjectType;

        if (isHierarchischType(objectType)) {
            groepenVoorObjectType = verzamelAlleGroepenVanObjecttypeHierarchie(objectType);
        } else {
            groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType);
        }

        //Formeel/materieel peilmoment veld komt nooit voor in subtypen, deze wordt opgenomen in het supertype.
        if (isSubtype(objectType)) {
            resultaat = false;
        } else if (minstensEenGroepKentHistorie(groepenVoorObjectType, soortHistorie)) {
            //Check of er een groep is met formele historie:
            resultaat = true;
        } else if (objectType.getId() == ID_ONDERZOEK_LGM) {
            // Onderzoek is een lastige klant, daarom specifieke override hier, altijd peilmoment velden.
            return true;
        } else {
            //Check of één van de objecttype attributen een groep hebben met formele/materiele historie
            for (Groep groep : groepenVoorObjectType) {
                // Kijk alleen naar de identiteit groep. Op moment van schrijven is de enige andere groep met een dynamisch object type
                // attribuut de 'afgeleid administratief'. Die geeft echter problemen door geen historie op adm.hand. Dus: uitsluiten!
                if (groep.getIdentCode().equals(IDENTITEIT)) {
                    final List<Attribuut> attributenVanGroep = getBmrDao().getAttributenVanGroep(groep);
                    for (Attribuut attribuut : attributenVanGroep) {
                        if (isDynamischObjecttypeAttribuut(attribuut)) {
                            final ObjectType ot = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                            resultaat = bepaalOfPeilmomentVeldNodigIsVoorHistorie(ot, soortHistorie);
                            if (resultaat) {
                                break;
                            }
                        }
                    }
                }
                if (resultaat) {
                    break;
                }
            }
        }

        //Laatste check: een inverse associatie attribuut is van het type objecttype met historie:
        if (!resultaat) {
            final List<Attribuut> inverseAttributenVoorObjectType =
                getBmrDao().getInverseAttributenVoorObjectType(objectType);
            for (Attribuut attribuut : inverseAttributenVoorObjectType) {
                if (isDynamischObjecttypeAttribuut(attribuut)) {
                    resultaat = bepaalOfPeilmomentVeldNodigIsVoorHistorie(attribuut.getObjectType(), soortHistorie);
                    if (resultaat) {
                        break;
                    }
                }
            }
        }
        return resultaat;
    }

    /**
     * Voegt een constructor toe aan de view/proxy klasse.
     *
     * @param klasse          de klasse waaraan een constructor moet worden toegevoegd.
     * @param objectType      het objecttype dat bij deze view klasse hoort.
     * @param hisVolledigVeld hisVolledig veld in de java klasse.
     */
    private void voegConstructorToeAanViewKlasse(final JavaKlasse klasse, final ObjectType objectType,
        final JavaVeld hisVolledigVeld)
    {
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, klasse);

        //Voor subtypen moet de eerste parameter overeenkomen met de klasse die bij dat subtype hoort.
        if (isSubtype(objectType)) {
            final JavaType javaTypeVoorSubtype =
                hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(objectType);
            constructor.voegParameterToe(new JavaFunctieParameter(hisVolledigVeld.getNaam(),
                javaTypeVoorSubtype,
                "hisVolledig instantie voor deze view."));
        } else {
            constructor.voegParameterToe(new JavaFunctieParameter(hisVolledigVeld.getNaam(),
                hisVolledigVeld.getType(),
                "hisVolledig instantie voor deze view."));
        }

        //Formeel peilmoment parameter.
        constructor.voegParameterToe(new JavaFunctieParameter(NAAM_PREDIKAAT_VELD, JavaType.PREDIKAAT_APACHE, "het predikaat."));

        constructor.setBody(genereerConstructorBodyVoorViewKlasse(objectType, constructor, hisVolledigVeld));
        constructor.setJavaDoc("Constructor die het HisVolledig object achter de view proxied.");
        klasse.voegConstructorToe(constructor);
    }

    /**
     * Genereert de body voor de contructor van een view/proxy klasse.
     *
     * @param objectType      objecttype uit BMR waarop de view gebaseerd is.
     * @param constructor     de constructor.
     * @param hisVolledigVeld hisVolledig veld in de java klasse.
     * @return String met daarin de body voor de contructor.
     */
    private String genereerConstructorBodyVoorViewKlasse(final ObjectType objectType, final Constructor constructor,
        final JavaVeld hisVolledigVeld)
    {
        final boolean isSubType = objectType.getSuperType() != null;
        final StringBuilder consBody = new StringBuilder();
        if (isSubType) {
            consBody.append("super(");
            for (JavaFunctieParameter consParam : constructor.getParameters()) {
                if (NAAM_PREDIKAAT_VELD.equals(consParam.getNaam())) {
                    consBody.append(consParam.getNaam()).append(", ");
                } else {
                    consBody.append(consParam.getNaam()).append(", ");
                }
            }
            consBody.deleteCharAt(consBody.lastIndexOf(","));
            consBody.append(");");
            consBody.append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
        } else {
            if (hisVolledigVeld != null) {
                consBody.append("this.").append(hisVolledigVeld.getNaam()).append(" = ")
                    .append(hisVolledigVeld.getNaam()).append(";")
                    .append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
            }
            consBody.append("this.").append(NAAM_PREDIKAAT_VELD).append(" = ")
                .append(NAAM_PREDIKAAT_VELD).append(";")
                .append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());

        }

        return consBody.toString();
    }

    /**
     * Voegt een functie toe die checkt of deze view wel actuele gegevens bevat. Het kan namelijk zijn dan je een slice maakt op het moment dat deze
     * gegevens helemaal niet geldig zijn. Bijvoorbeeld een slice op 'nu/nu' op een nationaliteit die een persoon alleen in het verleden had. Als die al is
     * beeindigd op het moment van slices, moet hij dus ook niet terug komen in het resultaat. De check wordt gedaan door te kijken of er minstens 1 groep
     * is met een geldigheid binnen de slice.
     *
     * @param klasse     de klasse
     * @param objectType het object type
     */
    private void voegActueleGegevensCheckFunctieToe(final JavaKlasse klasse, final ObjectType objectType) {
        final JavaFunctie actueleGegevensCheckFunctie =
            new JavaFunctie(JavaAccessModifier.PUBLIC, ACTUELE_GEGEVENS_CHECK_METHODE_NAAM);
        actueleGegevensCheckFunctie.setJavaDoc("Functie die aangeeft of er actuele gegevens zijn in deze view.");
        actueleGegevensCheckFunctie.setReturnType(JavaType.BOOLEAN_PRIMITIVE);
        actueleGegevensCheckFunctie.setReturnWaardeJavaDoc("true indien actuele gegevens aanwezig, anders false");

        final StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("return ");
        boolean minstensEenGroepGevonden = false;
        for (Groep groep : this.getBmrDao().getGroepenVoorObjectType(objectType)) {
            if (behoortTotJavaLogischModel(groep) && kentHistorie(groep)) {
                minstensEenGroepGevonden = true;
                bodyBuilder.append("this.get").append(groep.getIdentCode()).append("() != null");
                bodyBuilder.append("|| ");
            }
        }
        // Alleen daadwerkelijk toevoegen als er groepen zijn om op te checken.
        if (minstensEenGroepGevonden) {
            // Verwijder de laatste '|| '.
            bodyBuilder.delete(bodyBuilder.length() - "|| ".length(), bodyBuilder.length());
            bodyBuilder.append(";");

            actueleGegevensCheckFunctie.setBody(bodyBuilder.toString());
            klasse.voegFunctieToe(actueleGegevensCheckFunctie);
        }
    }

    /**
     * Maakt functies aan in de klasse om te voldoen aan de logische interface.
     *
     * @param objectType                 het objecttype dat bij de klasse hoort.
     * @param klasse                     de klasse die aan de interface moet voldoen.
     * @param hisVolledigProxyObjectVeld proxy object veld in de klasse
     */
    private void implementeerLogischeInterfaceFunctiesVoorInverseAssociaties(final ObjectType objectType,
        final JavaKlasse klasse,
        final JavaVeld hisVolledigProxyObjectVeld)
    {
        final List<Attribuut> inverseAttrVoorObjectType = getBmrDao().getInverseAttributenVoorObjectType(objectType);
        for (Attribuut inverseAttr : inverseAttrVoorObjectType) {
            if (this.behoortTotJavaLogischModel(inverseAttr.getObjectType())
                && behoortTotJavaOperationeelModel(inverseAttr.getObjectType()))
            {
                final JavaType viewInverseType =
                    hisVolledigMomentViewModelNaamgevingStrategie.getJavaTypeVoorElement(
                        inverseAttr.getObjectType());
                final JavaType returnJavaType =
                    new JavaType(JavaType.COLLECTION, new JavaGenericParameter(viewInverseType));

                final JavaAccessorFunctie getter = new JavaAccessorFunctie(
                    inverseAttr.getInverseAssociatieIdentCode(), returnJavaType);
                genereerInverseAssociatieGetterJavaDoc(getter, objectType, inverseAttr);
                klasse.voegFunctieToe(getter);

                getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));

                final JavaType hisVolledigInverseType =
                    hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(
                        inverseAttr.getObjectType());

                final String body = bouwMethodeBodyVoorLogischeInterfaceFunctiesVoorInverseAssociaties(
                    klasse, hisVolledigProxyObjectVeld, inverseAttr,
                    getter, hisVolledigInverseType, viewInverseType);
                getter.setBody(body);

                klasse.voegExtraImportsToe(JavaType.SET, JavaType.HASH_SET,
                    hisVolledigInverseType, viewInverseType);
            }
        }
    }

    /**
     * Bouw de body voor de methode, zie implementeerLogischeInterfaceFunctiesVoorInverseAssociaties.
     *
     * @param klasse                     de klasse
     * @param hisVolledigProxyObjectVeld het his volledig proxy veld
     * @param inverseAttr                inverse attr
     * @param getter                     de getter
     * @param hisVolledigInverseType     het volledig inverse type
     * @param viewInverseType            view inverse type
     * @return de methode body
     */
    private String bouwMethodeBodyVoorLogischeInterfaceFunctiesVoorInverseAssociaties(
        final JavaKlasse klasse, final JavaVeld hisVolledigProxyObjectVeld, final Attribuut inverseAttr,
        final JavaAccessorFunctie getter, final JavaType hisVolledigInverseType, final JavaType viewInverseType)
    {
        final StringBuilder body = new StringBuilder();
        body.append("final Set<").append(viewInverseType.getNaam()).append("> result = new HashSet<")
            .append(viewInverseType.getNaam()).append(">();")
            .append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
        body.append("for (final ").append(hisVolledigInverseType.getNaam()).append(" ")
            .append(GeneratieUtil.lowerTheFirstCharacter(hisVolledigInverseType.getNaam()))
            .append(" : ").append(hisVolledigProxyObjectVeld.getNaam())
            .append(".").append(getter.getNaam()).append("()) {")
            .append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());

        if (isSupertype(inverseAttr.getObjectType())) {
            final ObjectType inverseObjectType = inverseAttr.getObjectType();
            //Bij supertypen kijken we welke finale subtypen van toepassing zijn, voor elke subtype doen we een
            //instanceOf check en een downcast zodat we het view subtype klasse kunnen instantieren.
            final List<ObjectType> subTypen = verzamelFinaleSubtypen(inverseObjectType);
            final JavaType viewType = hisVolledigMomentViewModelNaamgevingStrategie.getJavaTypeVoorElement(
                inverseObjectType);
            body.append(String.format("%1$s view = null;%2$s", viewType.getNaam(),
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            for (int i = 0; i < subTypen.size(); i++) {
                final ObjectType subType = subTypen.get(i);
                if (i > 0) {
                    body.append(" else ");
                }
                final JavaType viewSubType =
                    hisVolledigMomentViewModelNaamgevingStrategie.getJavaTypeVoorElement(subType);
                final JavaType downCastType =
                    hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(subType);

                body.append(
                    String.format("if (%1$s instanceof %2$s) {%3$s",
                        GeneratieUtil.lowerTheFirstCharacter(hisVolledigInverseType.getNaam()),
                        downCastType.getNaam(),
                        JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

                body.append(
                    String.format("view = new %1$s((%2$s) %3$s",
                        viewSubType.getNaam(),
                        downCastType.getNaam(),
                        GeneratieUtil.lowerTheFirstCharacter(hisVolledigInverseType.getNaam())));

                body.append(", ").append(NAAM_PREDIKAAT_VELD_GETTER).append("()");

                body.append(String.format(");%1$s",
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

                body.append(String.format("}%s",
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                klasse.voegExtraImportsToe(viewSubType, viewType, downCastType);
            }
            body.append("else {")
                .append(String.format("throw new IllegalArgumentException(\"Onbekend type %1$s.\");", inverseObjectType.getNaam()))
                .append("}");
        } else {
            body.append("final ").append(viewInverseType.getNaam()).append(" view = new ")
                .append(viewInverseType.getNaam()).append("(")
                .append(GeneratieUtil.lowerTheFirstCharacter(hisVolledigInverseType.getNaam()));

            body.append(", ").append(NAAM_PREDIKAAT_VELD_GETTER).append("()");

            body.append(");");
            body.append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
        }
        // Nu moeten we ook nog checken of de resulterende view wel historie heeft EN actuele gegevens bevat.
        // Zonder actuele gegevens is deze namelijk niet van toepassing op de gemaakte slice. Zonder historie
        // retourneren we toch de inverse associatie view met de peilmomenten, want wie weet heeft die op zijn beurt
        // een inverse associatie met historie!
        if (minstensEenGroepKentHistorie(verzamelAlleGroepenVanObjecttypeHierarchie(inverseAttr.getObjectType()))) {
            body.append("if (view.").append(ACTUELE_GEGEVENS_CHECK_METHODE_NAAM).append("()) {");
            body.append("    result.add(view);");
            body.append("}");
        } else {
            body.append("result.add(view);");
        }

        body.append("}");
        body.append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
        body.append("return result;");
        return body.toString();
    }

    /**
     * Maakt functies aan in de klasse om te voldoen aan de logische interface.
     *
     * @param objectType                 het objecttype dat bij de klasse hoort.
     * @param groepenVoorObjectType      groepen die bij het objecttype horen.
     * @param klasse                     de klasse die aan de interface moet voldoen.
     * @param hisVolledigProxyObjectVeld proxy object veld in de klasse
     */
    private void implementeerLogischeInterfaceFunctiesVoorGroepen(final ObjectType objectType,
        final List<Groep> groepenVoorObjectType,
        final JavaKlasse klasse,
        final JavaVeld hisVolledigProxyObjectVeld)
    {
        final boolean isSubType = objectType.getSuperType() != null;
        for (Groep groep : groepenVoorObjectType) {
            if (behoortTotJavaLogischModel(groep)) {
                if (IDENTITEIT.equals(groep.getNaam())) {
                    implementeerLogischeInterfaceFunctiesVoorIdentiteitGroep(objectType, klasse, groep,
                        hisVolledigProxyObjectVeld);

                    // Indien de Identiteit groep historie kent (wat een vreemde constructie is) dan toch een functie
                    // genereren om de 'slice' van die identiteit groep op te kunnen vragen.
                    if (kentHistorie(groep)) {
                        implementeerLogischeInterfaceFunctiesVoorGroep(klasse, isSubType, groep,
                            hisVolledigProxyObjectVeld);
                    }
                } else {
                    implementeerLogischeInterfaceFunctiesVoorGroep(klasse, isSubType, groep,
                        hisVolledigProxyObjectVeld);
                }
            }
        }
    }

    /**
     * Maakt functie aan in de klasse om te voldoen aan de logische interface t.b.v. niet identiteits groepen.
     *
     * @param klasse                     de klasse die aan de interface moet voldoen.
     * @param subType                    of het type een subtype is of niet.
     * @param groep                      de groep waarvoor de functie(s) aangemaakt dienen te worden.
     * @param hisVolledigProxyObjectVeld proxy object veld
     */
    private void implementeerLogischeInterfaceFunctiesVoorGroep(final JavaKlasse klasse, final boolean subType,
        final Groep groep,
        final JavaVeld hisVolledigProxyObjectVeld)
    {
        final JavaType javaTypeVoorVeld = logischModelNaamgevingStrategie.getJavaTypeVoorElement(groep);

        //Getter voor attribuut van object type
        final JavaAccessorFunctie getterVoorVeld = new JavaAccessorFunctie(groep.getIdentCode(), javaTypeVoorVeld);
        getterVoorVeld.setFinal(true);
        if (!isIdentiteitGroep(groep)) {
            // Identiteit groepen zijn platgeslagen in het logische interfaces java model.
            getterVoorVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            getterVoorVeld.setForceerJavaDoc(true);
            final StringBuilder javaDoc = new StringBuilder();
            javaDoc.append("Retourneert ")
                .append(groep.getNaam())
                .append(" van ")
                .append(groep.getObjectType().getNaam())
                .append(". ")
                .append("Deze methode bepaalt met behulp van het predikaat het geldige record. Dit zou altijd 1 resultaat op")
                .append(" moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records tegelijkertijd geldig")
                .append(" zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast kiezen we")
                .append(" het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd consistent")
                .append(" wordt teruggegeven, worden de records eerst gesorteerd.");
            getterVoorVeld.setJavaDoc(javaDoc.toString());
            getterVoorVeld.setReturnWaardeJavaDoc("Retourneert " + groep.getNaam() + " van " + groep.getObjectType().getNaam());
        } else {
            // Geen InheritJavadoc dus, want het is geen override, even zelf javadocs toevoegen.
            getterVoorVeld.setJavaDoc("Retourneert Identiteit van " + groep.getObjectType().getNaam());
            getterVoorVeld.setReturnWaardeJavaDoc("Retourneert Identiteit van " + groep.getObjectType().getNaam());
        }
        klasse.voegFunctieToe(getterVoorVeld);

        final boolean kentFormeleHistorie = 'F' == groep.getHistorieVastleggen();
        final boolean kentMaterieleHistorie = 'B' == groep.getHistorieVastleggen();

        final StringBuilder body = new StringBuilder();
        if (kentFormeleHistorie || kentMaterieleHistorie) {
            final ObjectType operationeelModelObjectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
            final JavaType castType =
                operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(operationeelModelObjectType);
            getterVoorVeld.setReturnType(castType);

            klasse.voegExtraImportsToe(JavaType.LIST);
            klasse.voegExtraImportsToe(JavaType.ARRAY_LIST);
            klasse.voegExtraImportsToe(JavaType.COLLECTION_UTILS);

            body.append("final List<")
                .append(castType.getNaam())
                .append("> geldigRecord = new ArrayList(CollectionUtils.select(");

            if (subType) {
                final JavaType downCastType =
                    hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(groep.getObjectType());
                body.append("((").append(downCastType.getNaam()).append(")");
                body.append("get")
                    .append(GeneratieUtil.upperTheFirstCharacter(hisVolledigProxyObjectVeld.getNaam()))
                    .append("())");
                klasse.voegExtraImportsToe(downCastType);
            } else {
                //Speciaal geval voor Persoon / Indicatie, downcast benodigd naar HisPersoonIndicatieModel.
                if (ID_PERSOON_INDICATIE == groep.getObjectType().getId()) {
                    //body.append("(").append(castType.getNaam()).append(")");
                }
                body.append(hisVolledigProxyObjectVeld.getNaam());
            }

            body.append(".get")
                .append(JavaGeneratieUtil.cleanUpInvalidJavaCharacters(
                    // Knip 'his' uit de naam.
                    operationeelModelObjectType.getIdentCode().substring("his".length())))
                .append("Historie()")
                .append(".getHistorie()")
                .append(", ")
                .append(NAAM_PREDIKAAT_VELD_GETTER)
                .append("()")
                .append("));");

            if (!klasse.getGebruikteTypes().contains(JavaType.LOGGER)) {
                klasse.voegExtraImportsToe(JavaType.LOGGER_FACTORY);
                klasse.voegExtraImportsToe(JavaType.LOGGER);
                klasse.voegExtraImportsToe(JavaType.COLLECTIONS);
                klasse.voegExtraImportsToe(JavaType.ID_COMPARATOR);

                final JavaVeld loggerVeld = new JavaVeld(JavaType.LOGGER, "LOGGER", true);
                loggerVeld.setStatic(true);
                loggerVeld.setAccessModifier(JavaAccessModifier.PRIVATE);
                loggerVeld.setGeinstantieerdeWaarde("LoggerFactory.getLogger()");
                klasse.voegVeldToe(loggerVeld);
            }

            body.append("if (geldigRecord.size() > 1) {")
                .append("LOGGER.error(\"Kon geen moment-view bepalen voor ")
                .append(hisVolledigProxyObjectVeld.getNaam())
                .append(" met id {}. Er zijn meerdere ({}) \"")
                .append("+ \"records geldig. Dit wijst op een fout in de data. Het toegepaste predikaat was {}.\",")
                .append("getID(),geldigRecord.size(),getPredikaat().getClass().getName());")
                .append("Collections.sort(geldigRecord, new IdComparator());")
                .append("}");

            body.append("if (geldigRecord.size() == 0) {")
                .append("return null;")
                .append("}");

            body.append("return geldigRecord.get(0);");

        } else {
            body.append("return null;");
        }

        getterVoorVeld.setBody(body.toString());
    }

    /**
     * Maakt functie aan in de klasse om te voldoen aan de logische interface t.b.v. identiteits groepen.
     *
     * @param objectType                 het objecttype dat bij de klasse hoort.
     * @param klasse                     de klasse die aan de interface moet voldoen.
     * @param groep                      de groep waarvoor de functie(s) aangemaakt dienen te worden.
     * @param hisVolledigProxyObjectVeld proxy object veld
     */
    private void implementeerLogischeInterfaceFunctiesVoorIdentiteitGroep(final ObjectType objectType,
        final JavaKlasse klasse, final Groep groep,
        final JavaVeld hisVolledigProxyObjectVeld)
    {
        //Identiteit groep is platgeslagen.
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
        for (Attribuut attribuut : attributen) {
            // Specifieke uitzondering: getID is wel gewenst op alle views.
            if (behoortTotJavaLogischModel(attribuut) && behoortTotJavaOperationeelModel(attribuut)
                || attribuut.getNaam().equals(ID_ATTRIBUUT_NAAM))
            {
                final JavaType javaTypeVoorVeld;
                final GeneriekElement attribuutType = attribuut.getType();
                String getterBody;
                if (isIdAttribuut(attribuut)) {
                    final AttribuutType attrType =
                        getBmrDao().getElement(attribuut.getType().getId(), AttribuutType.class);
                    javaTypeVoorVeld = getJavaTypeVoorAttribuutType(attrType);
                    getterBody = String.format("return %1$s.get%2$s();",
                        hisVolledigProxyObjectVeld.getNaam(), attribuut.getIdentCode());
                } else if (BmrElementSoort.OBJECTTYPE.hoortBijCode(attribuutType.getSoortElement().getCode())
                    && BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == attribuutType.getSoortInhoud())
                {
                    final ObjectType returnObjectType =
                        getBmrDao().getElement(attribuutType.getId(), ObjectType.class);
                    javaTypeVoorVeld = hisVolledigMomentViewModelNaamgevingStrategie.getJavaTypeVoorElement(returnObjectType);
                    if (isSupertype(returnObjectType)) {
                        getterBody = genereerBodyVoorGetterMetSupertypeAlsReturnWaarde(klasse, returnObjectType,
                            hisVolledigProxyObjectVeld);
                    } else {
                        getterBody = String.format("return new %1$s(%2$s.get%3$s()",
                            javaTypeVoorVeld.getNaam(),
                            hisVolledigProxyObjectVeld.getNaam(),
                            attribuut.getIdentCode());

                        getterBody += ", " + NAAM_PREDIKAAT_VELD_GETTER + "()";

                        getterBody += ");";
                    }
                } else {
                    javaTypeVoorVeld = logischModelNaamgevingStrategie.getJavaTypeVoorElement(attribuutType);
                    getterBody = String.format("return %1$s.get%2$s();",
                        hisVolledigProxyObjectVeld.getNaam(), attribuut.getIdentCode());
                }

                //Genereer getter voor veld.
                final JavaAccessorFunctie getter = new JavaAccessorFunctie(attribuut.getIdentCode(), javaTypeVoorVeld);
                getter.setFinal(true);
                genereerGetterJavaDoc(getter, objectType, attribuut);
                klasse.voegFunctieToe(getter);
                getter.setBody(getterBody);
            }
        }
    }

    /**
     * Deze functie genereert de body voor een getter waarbij er rekening wordt gehouden met subtypen die geretourneerd moeten worden. In dat geval worden
     * er instanceof checks gegenereerd in een if...else if... blok, en worden de constructoren van subtypen aangeroepen. Bij het aanroepen van de
     * constructoren moet er ook weer rekening gehouden worden met de parameters van die constructor voor wat betreft formele- en materiele peilmoment
     * parameters. Zie bijvoorbeeld getBetrokkenheden in Relatie en Persoon. (De views)
     *
     * @param klasse                     de klasse waarin de getter staat
     * @param returnObjectType           return objecttype van de functie
     * @param hisVolledigProxyObjectVeld het proxy object veld
     * @return de body voor een getter
     */
    private String genereerBodyVoorGetterMetSupertypeAlsReturnWaarde(final JavaKlasse klasse,
        final ObjectType returnObjectType,
        final JavaVeld hisVolledigProxyObjectVeld)
    {
        final StringBuilder functieBody = new StringBuilder();
        final JavaType viewType =
            hisVolledigMomentViewModelNaamgevingStrategie.getJavaTypeVoorElement(returnObjectType);
        //Genereer body die een subtype retourneert.
        klasse.voegExtraImportsToe(viewType);
        final List<ObjectType> subTypen = verzamelFinaleSubtypen(returnObjectType);
        functieBody.append(String.format(
            "final %1$s %2$s = %3$s.%4$s();%5$s",
            hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(returnObjectType).getNaam(),
            GeneratieUtil.lowerTheFirstCharacter(returnObjectType.getIdentCode()),
            hisVolledigProxyObjectVeld.getNaam(),
            "get" + returnObjectType.getIdentCode(),
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        klasse.voegExtraImportsToe(
            hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(returnObjectType));

        functieBody.append(String.format("%1$s view = null;%2$s", viewType.getNaam(),
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK
                .getWaarde()));
        for (int i = 0; i < subTypen.size(); i++) {
            final ObjectType subType = subTypen.get(i);
            if (i > 0) {
                functieBody.append(" else ");
            }
            final JavaType viewSubType =
                hisVolledigMomentViewModelNaamgevingStrategie.getJavaTypeVoorElement(subType);
            final JavaType downCastType =
                hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(subType);
            functieBody.append(
                String.format("if (%1$s instanceof %2$s) {%3$s",
                    GeneratieUtil
                        .lowerTheFirstCharacter(returnObjectType.getIdentCode()),
                    downCastType.getNaam(),
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

            functieBody.append(
                String.format("view = new %1$s((%2$s) %3$s",
                    viewSubType.getNaam(),
                    downCastType.getNaam(),
                    GeneratieUtil.lowerTheFirstCharacter(returnObjectType.getIdentCode())));

            functieBody.append(String.format(", %1$s()", NAAM_PREDIKAAT_VELD_GETTER));

            functieBody.append(String.format(");%1$s", JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            functieBody.append(String.format("}%s",
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK
                    .getWaarde()));
            klasse.voegExtraImportsToe(downCastType, viewSubType);
        }
        functieBody.append(String.format("return view;%1$s",
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK
                .getWaarde()));
        return functieBody.toString();
    }

    /**
     * Zie bepaalOfPeilmomentVeldNodigIsVoorHistorie.
     *
     * @param objectType het object type
     * @return of het veld wel of niet nodig is.
     */
    private boolean bepaalOfFormeelPeilmomentVeldNodigIs(final ObjectType objectType) {
        return this.bepaalOfPeilmomentVeldNodigIsVoorHistorie(objectType, SoortHistorie.FORMEEL);
    }

    /**
     * Zie bepaalOfPeilmomentVeldNodigIsVoorHistorie.
     *
     * @param objectType het object type
     * @return of het veld wel of niet nodig is.
     */
    private boolean bepaalOfMaterieelPeilmomentVeldNodigIs(final ObjectType objectType) {
        return this.bepaalOfPeilmomentVeldNodigIsVoorHistorie(objectType, SoortHistorie.MATERIEEL);
    }

}
