/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaGenericParameter;
import nl.bzk.brp.generatoren.java.model.JavaInterface;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigInterfaceModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.OperationeelModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;

import org.springframework.stereotype.Component;

/**
 * Generator die interfaces genereert voor objecttypen met per groep de gehele historie of een deel daarvan in de
 * vorm van een collection.
 */
@Component("hisVolledigInterfaceModelGenerator")
public class HisVolledigInterfaceModelGenerator extends AbstractHisVolledigGenerator {

    private final NaamgevingStrategie operationeelModelNaamgevingStrategie = new OperationeelModelNaamgevingStrategie();
    private final NaamgevingStrategie interfaceNaamgevingStrategie = new HisVolledigInterfaceModelNaamgevingStrategie();

    @Override
    protected GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.HIS_VOLLEDIG_INTERFACE_MODEL_GENERATOR;
    }

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final List<JavaInterface> interfaces = new ArrayList<>();

        for (ObjectType objectType : getHisVolledigObjectTypen()) {
            if (objectType.getId() == ID_PERSOON_INDICATIE) {
                interfaces.addAll(genereerPersoonIndicatieSubtypes(objectType));
            } else {
                interfaces.add(genereerHisVolledigInterface(objectType));
            }
        }

        final JavaWriter<JavaInterface> writer = javaWriterFactory(generatorConfiguratie, JavaInterface.class);
        voegGeneratedAnnotatiesToe(interfaces, generatorConfiguratie);
        final List<JavaInterface> gegenereerdeInterfaces = writer.genereerEnSchrijfWeg(interfaces, this);
        rapporteerOverGegenereerdeJavaTypen(gegenereerdeInterfaces);
    }

    /**
     * Genereer de his volledig interface voor het meegegeven object type.
     *
     * @param objectType het object type
     * @return de gegenereerde interface
     */
    private JavaInterface genereerHisVolledigInterface(final ObjectType objectType) {
        final JavaInterface javaInterface = genereerInterfaceVoorObjecttype(objectType);

        final List<Groep> groepen = getBmrDao().getGroepenVoorObjectType(objectType);

        // Genereer getters voor de lijsten met historie.
        voegGettersToeVoorHisSets(javaInterface, groepen);

        //Genereer getters voor attributen identiteit groep.
        voegGettersToeVoorIdentiteitGroepAttributen(javaInterface, groepen);

        final List<Attribuut> inverseAttrVoorObjectType = getBmrDao().getInverseAttributenVoorObjectType(objectType);
        filterIndicatiesUitInverseAssociaties(inverseAttrVoorObjectType);
        // Genereer getters voor lijsten met inverse associaties.
        voegGettersToeVoorInverseAssociatieAttributen(javaInterface, inverseAttrVoorObjectType);

        if (objectType.getId() == ID_PERSOON_LGM) {
            genereerGettersVoorPersoonIndicaties(javaInterface);
        }

        return javaInterface;
    }

    /**
     * Genereer de getters die nodig zijn voor de indicaties en voeg ze toe aan de interface.
     *
     * @param javaInterface de persoon his volledig interface
     */
    private void genereerGettersVoorPersoonIndicaties(final JavaInterface javaInterface) {
        ObjectType soortIndicatieObjectType = this.getBmrDao().getElement(ID_SOORT_INDICATIE, ObjectType.class);
        ObjectType persoonIndicatieObjectType = this.getBmrDao().getElement(ID_PERSOON_INDICATIE, ObjectType.class);
        JavaType indicatieSuperType = interfaceNaamgevingStrategie
                .getJavaTypeVoorElement(persoonIndicatieObjectType);
        for (Tuple tuple : soortIndicatieObjectType.getTuples()) {
            // Getters voor alle velden.
            JavaType indicatieJavaType = maakPersoonIndicatieSubtypeJavaType(tuple, indicatieSuperType);
            JavaAccessorFunctie getter = new JavaAccessorFunctie(tuple.getIdentCode(), indicatieJavaType,
                    "indicatie " + tuple.getNaam());
            getter.setJavaDoc("Geeft indicatie " + tuple.getNaam() + " terug.");
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            javaInterface.voegFunctieToe(getter);
        }

        // Custom getIndicaties, die alle getters in een lijstje bij elkaar zet.
        JavaType returnType = new JavaType(JavaType.SET, new JavaGenericParameter(indicatieSuperType, true));
        JavaFunctie functie = new JavaFunctie(JavaAccessModifier.PUBLIC,
                returnType, "getIndicaties", "set met indicaties");
        functie.setJavaDoc("Geeft alle indicaties terug in een set.");
        javaInterface.voegFunctieToe(functie);
    }

    /**
     * Specifieke methode voor het genereren van de interface hierarchie voor persoon indicaties.
     * Hierbij wordt voor elke soort indicatie een subinterface gegenereerd.
     * De super interface is al in het model aanwezig.
     *
     * @param persoonIndicatieObjectType het object type in het BMR van persoon indicatie
     * @return de lijst met historie (sub)interfaces voor de persoon indicatie his volledig
     */
    private List<JavaInterface> genereerPersoonIndicatieSubtypes(final ObjectType persoonIndicatieObjectType) {
        List<JavaInterface> javaInterfaces = new ArrayList<>();
        ObjectType soortIndicatieObjectType = this.getBmrDao().getElement(ID_SOORT_INDICATIE, ObjectType.class);
        for (Tuple tuple : soortIndicatieObjectType.getTuples()) {
            boolean heeftMaterieleHistorie = heeftPersoonIndicatieTupleMaterieleHistorie(tuple);

            // Bijbehorend his subtype
            ObjectType hisIndicatieObjectType = this.getBmrDao().getElementVoorSyncIdVanLaag(
                    SYNC_ID_HIS_PERSOON_INDICATIE, BmrLaag.OPERATIONEEL, ObjectType.class);
            JavaType defaultHisIndicatieJavaType = operationeelModelNaamgevingStrategie
                    .getJavaTypeVoorElement(hisIndicatieObjectType);
            JavaType hisIndicatieJavaType = maakPersoonIndicatieSubtypeJavaType(tuple, defaultHisIndicatieJavaType);

            // Algemene zaken en mappings
            JavaType defaultInterfaceIndicatieJavaType = new HisVolledigInterfaceModelNaamgevingStrategie()
                    .getJavaTypeVoorElement(persoonIndicatieObjectType);
            JavaType interfaceIndicatieJavaType =
                        maakPersoonIndicatieSubtypeJavaType(tuple, defaultInterfaceIndicatieJavaType);
            JavaInterface javaInterface = new JavaInterface(interfaceIndicatieJavaType,
                    "Subtype interface voor indicatie " + tuple.getNaam());
            JavaType superInterface = new JavaType(defaultInterfaceIndicatieJavaType,
                    new JavaGenericParameter(hisIndicatieJavaType));
            javaInterface.voegSuperInterfaceToe(superInterface);

            // Methode voor ophalen historie
            JavaType smartSetInterfaceType;
            if (heeftMaterieleHistorie) {
                smartSetInterfaceType = JavaType.MATERIELE_HISTORIE_SET;
            } else {
                smartSetInterfaceType = JavaType.FORMELE_HISTORIE_SET;
            }
            JavaType returnType = new JavaType(smartSetInterfaceType, new JavaGenericParameter(hisIndicatieJavaType));
            JavaFunctie historieFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC, returnType,
                    "getPersoonIndicatieHistorie", null);
            historieFunctie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            javaInterface.voegFunctieToe(historieFunctie);

            javaInterfaces.add(javaInterface);
        }
        return javaInterfaces;
    }

    /**
     * Genereert interface functies voor attributen van de identiteit groep.
     *
     * @param javaInterface de java interface.
     * @param groepen de groepen van het objecttype waar de interface op gebaseerd is.
     */
    private void voegGettersToeVoorIdentiteitGroepAttributen(final JavaInterface javaInterface,
                                                             final List<Groep> groepen)
    {
        for (Groep groep : groepen) {
            if (isIdentiteitGroep(groep)) {
                final List<Attribuut> attributenVanGroep = getBmrDao().getAttributenVanGroep(groep);
                for (Attribuut attribuut : attributenVanGroep) {
                    if (behoortTotJavaOperationeelModel(attribuut) && !isIdAttribuut(attribuut)) {
                        final JavaType javaType;
                        final boolean isDatabaseKnipAttribuut = isDatabaseKnipAttribuut(groep.getObjectType(), attribuut);
                        if (isIdAttribuut(attribuut)) {
                            final AttribuutType attrType =
                                getBmrDao().getElement(attribuut.getType().getId(), AttribuutType.class);
                            javaType = getJavaTypeVoorAttribuutType(attrType);
                        } else if (isDatabaseKnipAttribuut) {
                            javaType = bepaalJavaTypeVoorDatabaseKnipAttribuut(attribuut);
                        } else if (attribuut.getType().getSoortInhoud() != null
                            && BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == attribuut.getType().getSoortInhoud())
                        {
                            final ObjectType element =
                                getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                            javaType = interfaceNaamgevingStrategie.getJavaTypeVoorElement(element);
                        } else {
                            javaType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                        }

                        String getterNaam = "get";
                        if (isDatabaseKnipAttribuut) {
                            getterNaam += GeneratieUtil.upperTheFirstCharacter(bepaalVeldnaamVoorDatabaseKnipAttribuut(attribuut));
                        } else {
                            getterNaam += attribuut.getIdentCode();
                        }

                        final JavaFunctie getter =
                            new JavaFunctie(JavaAccessModifier.PUBLIC, javaType, getterNaam, attribuut.getNaam()  + " van " + groep.getObjectType()
                                .getNaam());
                        getter.setJavaDoc(
                            "Retourneert " + attribuut.getNaam()  + " van " + groep.getObjectType().getNaam() + ".");
                        javaInterface.voegFunctieToe(getter);
                    }
                }
            }
        }
    }

    /**
     * Voegt voor alle 1-n relaties een interface getter toe.
     *
     * @param javaInterface java interface waar getters aan moeten worden toegevoegd.
     * @param inverseAttributenVoorObjectType alle inverse associatie attributen.
     */
    private void voegGettersToeVoorInverseAssociatieAttributen(final JavaInterface javaInterface,
                                                               final List<Attribuut> inverseAttributenVoorObjectType)
    {
        for (Attribuut attribuut : inverseAttributenVoorObjectType) {
            final ObjectType inverseObjectType = attribuut.getObjectType();
            if (behoortTotJavaOperationeelModel(inverseObjectType)) {
                final JavaType javaTypeVoorElement = interfaceNaamgevingStrategie.getJavaTypeVoorElement(inverseObjectType);

                //Genereer een getter.
                final String getterNaam =
                        GeneratieUtil.upperTheFirstCharacter(attribuut.getInverseAssociatieIdentCode());
                final JavaAccessorFunctie javaAccessorFunctie =
                        new JavaAccessorFunctie(getterNaam, new JavaType(JavaType.SET,
                                                                         javaTypeVoorElement, true));
                javaAccessorFunctie.setJavaDoc("Retourneert lijst van " + inverseObjectType.getNaam() + ".");
                javaAccessorFunctie.setReturnWaardeJavaDoc("lijst van " + inverseObjectType.getNaam());
                javaInterface.voegFunctieToe(javaAccessorFunctie);
            }
        }
    }

    /**
     * Voor elke groep met historie wordt een getter toegevoegd met als return type een collection geparametriseerd met
     * de juiste His entity.
     *
     * @param javaInterface java interface waar de getters aan toegevoegd moeten worden.
     * @param groepen de groepen.
     */
    private void voegGettersToeVoorHisSets(final JavaInterface javaInterface, final List<Groep> groepen) {
        for (Groep groep : groepen) {
            if (groepKentHistorie(groep)) {
                final ObjectType hisObjectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
                if (hisObjectType != null) {

                    final JavaType javaTypeVoorElement = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(
                            hisObjectType);

                    JavaType javaInterfaceTypeVoorSet = bepaalSlimmeSetInterfaceVoorGroep(groep);

                    final String getterNaam =
                            "get" + hisObjectType.getIdentCode().replace("His_", "") + "Historie";
                    JavaFunctie getter = new JavaFunctie(JavaAccessModifier.PUBLIC,
                                                         new JavaType(javaInterfaceTypeVoorSet,
                                                                      javaTypeVoorElement, false),
                                                         getterNaam,
                                                         "Historie met " + hisObjectType.getNaam());
                    getter.setJavaDoc("Retourneert de historie van " + hisObjectType.getNaam() + ".");
                    javaInterface.voegFunctieToe(getter);
                }
            }
        }
    }

    /**
     * Genereert een interface voor een objecttype uit het BMR.
     *
     * @param objectType het objecttype.
     * @return Java interface.
     */
    private JavaInterface genereerInterfaceVoorObjecttype(final ObjectType objectType) {
        final JavaType javaTypeVoorElement = interfaceNaamgevingStrategie.getJavaTypeVoorElement(objectType);
        final JavaInterface javaInterface =
                new JavaInterface(javaTypeVoorElement, "Interface voor " + objectType.getNaam() + ".");
        // Voeg de super tagging interface toe.
        javaInterface.voegSuperInterfaceToe(JavaType.MODEL_PERIODE_INTERFACE);
        if (isSubtype(objectType)) {
            javaInterface.voegSuperInterfaceToe(
                    interfaceNaamgevingStrategie.getJavaTypeVoorElement(objectType.getSuperType()));
        }

        // Indien het object een volgnummer heeft, dan de VolgnummerBevattend interface extenden.
        if (bevatVolgnummer(objectType)) {
            javaInterface.voegSuperInterfaceToe(JavaType.VOLGNUMMER_BEVATTEND);
        }

        final JavaType idJavaType = getJavaTypeVoorIdVeld(objectType);
        if (idJavaType != null && idJavaType.isNumeriek()) {
            javaInterface.voegSuperInterfaceToe(new JavaType(JavaType.MODEL_IDENTIFICEERBAAR, idJavaType));
        }

        if (isBRPRootObject(objectType)) {
            javaInterface.voegSuperInterfaceToe(JavaType.HISVOLLEDIG_ROOT_OBJECT);
        } else {
            javaInterface.voegSuperInterfaceToe(JavaType.BRP_OBJECT);
        }
        return javaInterface;
    }
}
