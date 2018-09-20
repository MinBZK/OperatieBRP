/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.AbstractHisVolledigGenerator;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.naamgeving.AttribuutWrapperNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigPredikaatViewModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.OperationeelModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.jibx.common.JibxBinding;
import nl.bzk.brp.generatoren.jibx.model.Binding;
import nl.bzk.brp.generatoren.jibx.model.Collection;
import nl.bzk.brp.generatoren.jibx.model.Mapping;
import nl.bzk.brp.generatoren.jibx.model.Property;
import nl.bzk.brp.generatoren.jibx.model.Structure;
import nl.bzk.brp.generatoren.jibx.model._Object;
import nl.bzk.brp.generatoren.xsd.util.XsdNaamgevingUtil;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.WaarderegelWaarde;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generator die de binding genereert voor het Levering Basis xsd schema. Dit schema wordt zowel door Leveringen als
 * Bevraging gebruikt.
 */
public abstract class AbstractLeveringBasisBindingGenerator extends AbstractJibxGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLeveringBasisBindingGenerator.class);

    protected static final String ATTR_OBJECTSLEUTEL = "objectSleutel";
    protected static final String ATTR_VOORKOMENSLEUTEL = "voorkomenSleutel";

    private static final String MATERIELE_HISTORIE_SET_HISTORIE_GETTER = "getHistorie";
    private static final String FORMELE_HISTORIE_SET_HISTORIE_GETTER = "getHistorie";

    protected static final String MAPPING_TYPE_NAME_FORMELEHISTORIE_MET_VERANTWOORDING =
            "FormeleHistorieEntiteitMetVerantwoording";
    protected static final String MAPPING_TYPE_NAME_MATERIELEHISTORIE_MET_VERANTWOORDING =
            "MaterieleHistorieEntiteitMetVerantwoording";

    protected static final String MAPPING_TYPE_NAME_FORMELEHISTORIE = "FormeleHistorieEntiteit";
    protected static final String MAPPING_TYPE_NAME_MATERIELEHISTORIE = "MaterieleHistorieEntiteit";

    private final NaamgevingStrategie operationeelModelNaamgevingStrategie = new OperationeelModelNaamgevingStrategie();
    private final NaamgevingStrategie attribuutWrapperNaamgevingStrategie  = new AttribuutWrapperNaamgevingStrategie();
    protected final NaamgevingStrategie hisVolledigModelNaamgevingStrategie  = new HisVolledigModelNaamgevingStrategie();
    private final NaamgevingStrategie hisVolledigViewModelNaamgevingStrategie =
            new HisVolledigPredikaatViewModelNaamgevingStrategie();

    /**
     * Genereert de binding voor objecttypen die in leveringen worden gebruikt. Op dit moment is dit enkel Persoon.
     *
     * @return Binding voor Persoon.
     */
    protected Binding genereerBindingVoorObjecttypen() {
        final Binding binding = new Binding();
        binding.setDirection("output");

        //Ons startpunt is Persoon
        final ObjectType persoonObjectType = getBmrDao().getElement(ID_PERSOON_LGM, ObjectType.class);
        final List<Groep> groepenVoorObjectTypePersoon = getBmrDao().getGroepenVoorObjectType(persoonObjectType);
        Mapping persoonMapping = new Mapping();
        persoonMapping.set_Class(
                hisVolledigViewModelNaamgevingStrategie.getJavaTypeVoorElement(
                        persoonObjectType).getFullyQualifiedClassName());
        persoonMapping.setAbstract(true);
        persoonMapping.setTypeName(getTypeNameVoorPersoonMapping());
        persoonMapping.setObject(new _Object());

        //Voeg attributen toe aan de Persoon mapping:
        voegChoiceToeVoorXsdAttribuut(persoonMapping, ATTR_OBJECTTYPE, null, false, persoonObjectType.getIdentCode(),
                                      null);

        voegChoicesToeVoorXsdAttributenOnderObjecttypen(persoonMapping);

        //The choices in de mapping voor persoon stoppen we in een map samen met naam van de elementen om xsd sortering
        //later te kunnen toepassen.
        final List<Pair<String, Mapping.Choice>> mappingsVoorPersoon = new ArrayList<>();

        //Attributen van de Identiteit groep van persoon:
        for (Groep groep : groepenVoorObjectTypePersoon) {
            if (isIdentiteitGroep(groep)) {
                bouwStructuresVoorAttributenIdentiteitGroepPersoon(persoonMapping, groep);
            }
        }

        //Groepen van persoon:
        for (Groep groep : groepenVoorObjectTypePersoon) {
            if (behoortInXsd(groep) && !isIdentiteitGroep(groep)) {
                if (groepKentHistorie(groep)) {
                    mappingsVoorPersoon.add(genereerMappingChoiceVoorGroepHistorieLijst(groep));
                }
            }
        }

        //Inverse associaties van persoon:
        final List<Attribuut> inverseAttributenVoorObjectType =
                getBmrDao().getInverseAttributenVoorObjectType(persoonObjectType);

        //Genereer de choices voor de inverse associaties.
        final List<Attribuut> gebruiktInverseAttributen = new ArrayList<>();
        //we kijken hier bewust niet naar attribuut inbericht vlaggetje, omdat bmr op attribuut nivo nog niet alles correct is veastgelegd
        for (Attribuut invAttribuut : inverseAttributenVoorObjectType) {
            if (invAttribuut.getObjectType().getSchema().getId() == ID_KERN_SCHEMA
                    && invAttribuut.getObjectType().getInBericht() != null
                    && invAttribuut.getObjectType().getInBericht() == 'J')
            {
                mappingsVoorPersoon.add(genereerChoiceVoorInverseAttribuutVanPersoon(invAttribuut));
                gebruiktInverseAttributen.add(invAttribuut);
            }
        }

        if (genereerMappingVoorVerantwoordingVanPersoon()) {
            mappingsVoorPersoon.add(genereerChoiceVoorVerantwoordingVanPersoon());
        }

        //Genereer de container mappings:
        binding.getMappingList().addAll(genereerContainerMappings(gebruiktInverseAttributen));

        //Genereer mappings voor 1-N associaties:
        binding.getMappingList().addAll(genereerInverseAssociatiesObjecttypeMappings(gebruiktInverseAttributen));

        //Sorteer de choices voor persoon naar de xsd sortering.
        sorteerChoicesVoorPersoon(mappingsVoorPersoon, persoonObjectType.getXsdSortering());

        //Voeg de choices toe aan de mapping voor persoon.
        for (Pair<String, Mapping.Choice> choiceVoorPersoon : mappingsVoorPersoon) {
            persoonMapping.getChoiceList().add(choiceVoorPersoon.getValue());
        }

        binding.getMappingList().add(persoonMapping);
        return binding;
    }

    /**
     * Voor de verantwoording van de persoon wordt hier een extra structure mapping gegenereerd.
     * @return Mapping choice voor de verantwoording van persoon.
     */
    private Pair<String, Mapping.Choice> genereerChoiceVoorVerantwoordingVanPersoon() {
        final Mapping.Choice choice =
                maakStructureMappingChoice(null, AbstractHisVolledigGenerator.PERSOON_VERANTWOORDING_GETTER_NAAM,
                                           "administratieveHandelingen",
                                           "Container_PersoonAdministratieveHandelingen_Levering", true);

        //test-method om te bepalen of container tags ge-marshalled moeten worden. (bijv: <adressen> )
        choice.getStructure().getProperty().setTestMethod(AbstractHisVolledigGenerator.PERSOON_HEEFT_VERANTWOORDING_FUNCTIE_NAAM);
        return new ImmutablePair<>("AdministratieveHandelingen", choice);
    }

    /**
     * Moet de mapping voor Persoon ook verantwoording bevatten?
     * @return true indien voor persoon ook de verantwoording moet worden gemarshalled.
     */
    protected abstract boolean genereerMappingVoorVerantwoordingVanPersoon();

    /**
     * Voegt aan de mapping structures toe voor het binden van de attributen van de identiteit groep.
     *
     * @param mapping De mapping waaraan structures moeten worden toegevoegd.
     * @param groep De identiteit groep.
     */
    private void bouwStructuresVoorAttributenIdentiteitGroepPersoon(final Mapping mapping, final Groep groep) {
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
        for (Attribuut attribuut : attributen) {
            final String elementNaam;
            String mapAs = null;
            String enumValueMethod = null;
            boolean moetValueChoiceZijn = false;
            String getMethod;

            if (behoortInXsd(attribuut)) {
                boolean optional = true;

                if (isElementVerplichtInXsd(attribuut)) {
                    optional = false;
                }

                if (attribuut.getType().getSoortInhoud() != null
                        && (BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()
                        || BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()))
                {
                    moetValueChoiceZijn = true;
                    final ObjectType stamgegeven = getBmrDao().getElement(
                            attribuut.getType().getId(), ObjectType.class);
                    final Attribuut logischIdentiteitAttr = bepaalLogischeIdentiteitVoorStamgegeven(stamgegeven);
                    elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorStamgegevenAttribuut(attribuut,
                                                                                              logischIdentiteitAttr);

                    if (BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()) {
                        getMethod = "get" + attribuut.getIdentCode();
                        enumValueMethod = "getCode";
                    } else {
                        getMethod = "get" + attribuut.getIdentCode() + logischIdentiteitAttr.getIdentCode();
                    }
                } else {
                    elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorAttribuutTypeAttribuut(attribuut);
                    mapAs = attribuutWrapperNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType()).getNaam();
                    getMethod = "get" + attribuut.getIdentCode();
                }

                if (moetValueChoiceZijn) {
                    if (enumValueMethod != null) {
                        // Een enum moet via een extra structure gewrapt worden, vanwege de attribuut indirectie.
                        Mapping.Choice choice = maakStructureMappingChoice(
                                null, getMethod, null, null, optional);
                        final Structure.Choice valueChoice = maakValueStructureChoice(
                                null, elementNaam, null, null, enumValueMethod, null,
                                "getWaarde", null, false, null);
                        if (getTestMethodVoorAttributen() != null) {
                            valueChoice.getValue().getProperty().setTestMethod(getTestMethodVoorAttributen());
                            // Test method dus optional:
                            valueChoice.getValue().getProperty().setUsage("optional");
                        }
                        choice.getStructure().getChoiceList().add(valueChoice);
                        mapping.getChoiceList().add(choice);
                    } else {
                        mapping.getChoiceList().add(
                                maakValueMappingChoice(
                                        null,
                                        elementNaam,
                                        null,
                                        null,
                                        enumValueMethod,
                                        null,
                                        getMethod,
                                        null,
                                        optional, null));
                    }
                } else {
                    mapping.getChoiceList().add(
                            maakStructureMappingChoice(
                                    null,
                                    getMethod,
                                    elementNaam,
                                    mapAs,
                                    optional));
                }
            }
        }
    }

    /**
     * Past de XSD sortering toe aan de mapping voor Persoon.
     *
     * @param mappingsVoorPersoon de losse choices voor de mapping van Persoon.
     * @param xsdSortering de xsd sortering zoals vastgelegd in het BMR.
     */
    private void sorteerChoicesVoorPersoon(final List<Pair<String, Mapping.Choice>> mappingsVoorPersoon,
                                           final String xsdSortering)
    {
        Collections.sort(mappingsVoorPersoon, new Comparator<Pair<String, Mapping.Choice>>() {
            @Override
            public int compare(final Pair<String, Mapping.Choice> g1, final Pair<String, Mapping.Choice> g2) {
                return xsdSortering.toUpperCase().indexOf(g1.getKey().toUpperCase())
                        - xsdSortering.toUpperCase().indexOf(g2.getKey().toUpperCase());
            }
        });
    }

    /**
     * Genereert choice voor de inverse attribuut van persoon. Deze choices zullen verwijzen naar de Container
     * mapping.
     *
     * @param invAttribuut het inverse attribuut van Persoon.
     * @return Jibx Choice voor het inverse attribuut.
     */
    private Pair<String, Mapping.Choice> genereerChoiceVoorInverseAttribuutVanPersoon(final Attribuut invAttribuut) {
        final Mapping.Choice choice =
                maakStructureMappingChoice(null, "get" + invAttribuut.getInverseAssociatieIdentCode(),
                                           GeneratieUtil.lowerTheFirstCharacter(
                                                   invAttribuut.getInverseAssociatieIdentCode()),
                                           bepaalTypeNameVoorContainer(invAttribuut), true);

        //test-method om te bepalen of container tags ge-marshalled moeten worden. (bijv: <adressen> )
        choice.getStructure().getProperty().setTestMethod(getTestMethodVoorInverseAssociatiesVanPersoon(invAttribuut));
        return new ImmutablePair<>(invAttribuut.getInverseAssociatieIdentCode(), choice);
    }

    /**
     * Voor elk inverse associatie attribuut wordt een mapping gemaakt voor het objecttype waar dat inverse associatie
     * onderdeel van is. Bijvoorbeeld Persoon / Voornaam.
     *
     * @param inverseAttributenVoorObjectType inverse attributen van het Persoon objecttype.
     * @return Lijst met mappings voor alle inverse associatie objecttypen.
     */
    private List<Mapping> genereerInverseAssociatiesObjecttypeMappings(
            final List<Attribuut> inverseAttributenVoorObjectType)
    {
        final List<Mapping> objectTypeMappings = new ArrayList<>();
        for (Attribuut invAttribuut : inverseAttributenVoorObjectType) {
            if (invAttribuut.getObjectType().getSchema().getId() == ID_KERN_SCHEMA) {
                final ObjectType objecttype = invAttribuut.getObjectType();
                if (isSupertype(objecttype)) {
                    continue;
                }
                final Mapping mapping = new Mapping();
                mapping.setAbstract(true);
                mapping.set_Class(
                        hisVolledigViewModelNaamgevingStrategie.getJavaTypeVoorElement(
                                objecttype).getFullyQualifiedClassName());
                mapping.setTypeName(bepaalTypeNameVoorObjecttype(objecttype));

                final Mapping.Choice choice = maakStructureMappingChoice(
                        null,
                        "get" + objecttype.getIdentCode() + "Historie",
                        null,
                        null,
                        false
                );

                final Groep groepDieHistorieBevat = bepaalGroepMetHistorie(objecttype);
                final Structure.Choice collectionChoice = new Structure.Choice();
                final Collection collection = new Collection();
                final Property property = new Property();
                property.setGetMethod(bepaalGetMethodVoorHistorieLijst(groepDieHistorieBevat));
                collection.setProperty(property);
                collectionChoice.setCollection(collection);
                final ObjectType hisObjectType =
                        getBmrDao().getOperationeelModelObjectTypeVoorGroep(groepDieHistorieBevat);
                //Kijk of een custom marshaller moet worden gebruikt voor het historie objecttype!
                if (SYNC_ID_HIS_PERSOON_INDICATIE == hisObjectType.getSyncid()) {
                    final Collection.Choice choice1 = maakStructureCollectionChoice(null, null, false);
                    choice1.getStructure().setObject(new _Object());
                    choice1.getStructure().getObject().setMarshaller(getClassNaamVanIndicatieMarshaller());
                    collection.getChoiceList().add(choice1);
                } else {
                    collection.getChoiceList().add(
                            maakStructureCollectionChoice(
                                    null, bepaalTypeNameVoorGroep(groepDieHistorieBevat), false));
                }
                choice.getStructure().getChoiceList().add(collectionChoice);
                mapping.getChoiceList().add(choice);
                objectTypeMappings.add(mapping);
            }
        }
        return objectTypeMappings;
    }

    /**
     * Bepaalt welke groep in het objecttype historie bevat. Let op: dit werkt alleen voor objecttypen met een
     * standaard groep.
     *
     * @param objecttype het objecttype.
     * @return de groep die historie kent.
     */
    private Groep bepaalGroepMetHistorie(final ObjectType objecttype) {
        Groep resultaat = null;
        final List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objecttype);
        for (Groep groep : groepenVoorObjectType) {
            if (groepKentHistorie(groep)) {
                resultaat = groep;
            }
        }
        return resultaat;
    }

    /**
     * Genereert container mappings voor inverse assoicatie attributen. Bijvoorbeel een Container mapping voor adressen.
     * Dit zijn mappings op java collection classes.
     *
     * @param inverseAttributenVoorObjectType alle inverse associaties van een objecttype waarvoor dus container
     *                                        mappings nodig zijn.
     * @return Lijst met container mappings.
     */
    private List<Mapping> genereerContainerMappings(final List<Attribuut> inverseAttributenVoorObjectType) {
        final List<Mapping> containerMappings = new ArrayList<>();
        for (Attribuut invAttribuut : inverseAttributenVoorObjectType) {
            if (invAttribuut.getObjectType().getSchema().getId() == ID_KERN_SCHEMA) {
                //Betrokkenheden en onderzoeken containers slaan we over, want betrokkenheden worden met de hand ge-bind.
                if (ID_BETROKKENHEID_LGM != invAttribuut.getObjectType().getId()
                    && ID_PERSOON_ONDERZOEK_LGM != invAttribuut.getObjectType().getId())
                {
                    final Mapping mapping = new Mapping();
                    mapping.setAbstract(true);
                    mapping.setTypeName(bepaalTypeNameVoorContainer(invAttribuut));
                    mapping.set_Class(JavaType.SET.getFullyQualifiedClassName());
                    _Object obj = new _Object();
                    mapping.setObject(obj);
                    containerMappings.add(mapping);

                    final Mapping.Choice collectionChoice = new Mapping.Choice();
                    final Collection collection = new Collection();
                    final Property collProp = new Property();
                    collProp.setUsage("optional");
                    collection.setProperty(collProp);
                    collectionChoice.setCollection(collection);

                    /*final String elementNaam = GeneratieUtil.lowerTheFirstCharacter(
                        invAttribuut.getObjectType().getIdentCode().replace(persoonObjectType.getNaam(), ""));*/
                    final Collection.Choice collectieItemChoice = maakStructureCollectionChoice(
                            null, bepaalTypeNameVoorObjecttype(invAttribuut.getObjectType()), true
                    );
                    collection.getChoiceList().add(collectieItemChoice);
                    mapping.getChoiceList().add(collectionChoice);
                }
            }
        }
        return containerMappings;
    }

    /**
     * Voor elke Historie lijst in persoon wordt hier een mapping choice gemaakt. Bijvoorbeeld voor de
     * HisPersoonGeboorteLijst.
     *
     * @param groep de groep waar de historie bij hoort.
     * @return mapping choice voor de historie lijst.
     */
    private Pair<String, Mapping.Choice> genereerMappingChoiceVoorGroepHistorieLijst(final Groep groep) {
        final Mapping.Choice choice = maakStructureMappingChoice(
                null,
                "get" + groep.getObjectType().getIdentCode() + groep.getIdentCode() + "Historie",
                null,
                null,
                false
        );

        final String elementNaam = GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode());
        final Structure.Choice collectionChoice = new Structure.Choice();
        final Collection collection = new Collection();
        final Property property = new Property();
        property.setGetMethod(bepaalGetMethodVoorHistorieLijst(groep));
        collection.setProperty(property);
        collectionChoice.setCollection(collection);
        collectionChoice.getCollection().getChoiceList()
                        .add(maakStructureCollectionChoice(null, bepaalTypeNameVoorGroep(groep), false));
        choice.getStructure().getChoiceList().add(collectionChoice);
        return new ImmutablePair<>(elementNaam, choice);
    }

    /**
     * Bepaalt wat de naam van de getter is in onze eigen Set implementatie voor de internet set.
     *
     * @param groep groep waar de historie van is.
     * @return naam van de getter in de Set inplementatie.
     */
    private String bepaalGetMethodVoorHistorieLijst(final Groep groep) {
        String resultaat = null;
        if (kentFormeleHistorie(groep)) {
            resultaat = FORMELE_HISTORIE_SET_HISTORIE_GETTER;
        } else if (kentMaterieleHistorie(groep)) {
            resultaat = MATERIELE_HISTORIE_SET_HISTORIE_GETTER;
        }
        return resultaat;
    }

    /**
     * Genereert de binding voor de C/D laag objecttypen.
     *
     * @return Binding voor alle historie objecttypen.
     */
    protected Binding genereerBindingVoorHistorieObjecttypen() {
        final Binding binding = new Binding();
        //Leveringen, dus direction = ouput.
        binding.setDirection("output");
        voegJibxBindingIncludeToe(binding, JibxBinding.OBJECTTYPEN_STATISCH);
        voegIncludeToeVoorHistorieMappings(binding);
        final List<Groep> groepen = getBmrDao().getGroepen();
        //Genereer mappings voor alle historie objecten van groepen.
        for (Groep groep : groepen) {
            if (BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == groep.getObjectType().getSoortInhoud()
                    && behoortInXsd(groep)
                    && (kentFormeleHistorie(groep) || kentMaterieleHistorie(groep)))
            {
                final ObjectType hisObjectType =
                        getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
                //Als het HistorieObjecttype een custom marshaller kent, dan geen mapping genereren.
                //Alleen mappings genereren voor kern schema his objecttypen want dat is wat we leveren.
                if (SYNC_ID_HIS_PERSOON_INDICATIE != hisObjectType.getSyncid()
                        && hisObjectType.getSchema().getId() == ID_KERN_SCHEMA)
                {
                    binding.getMappingList().add(genereerMappingVoorHisObjectType(groep, hisObjectType));
                }
            }
        }
        return binding;
    }

    /**
     * Voeg een include toe met het binding bestand dat mappings bevat voor historie velden.
     * @param binding de binding waarvoor de include wordt toegevoegd.
     */
    protected abstract void voegIncludeToeVoorHistorieMappings(final Binding binding);

    /**
     * Voor elk Historie objecttype uit het operationele model van het BMR wordt een Mapping gebouwd in deze functie.
     *
     * @param groep de groep die historie kent uit het logische model.
     * @param hisObjecttype het historie objecttype uit het operationele model van BMR.
     * @return Jibx mapping voor historie objecttype.
     */
    private Mapping genereerMappingVoorHisObjectType(final Groep groep, final ObjectType hisObjecttype) {
        final Mapping mapping = new Mapping();
        mapping.setAbstract(true);
        mapping.set_Class(operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(
                hisObjecttype).getFullyQualifiedClassName());
        mapping.setTypeName(bepaalTypeNameVoorGroep(groep));

        //Voor standaard en identiteit groepen
        //moeten we een structure eerst toevoegen voor de extra tag van het objecttype.
        if (isStandaardGroep(groep) || isIdentiteitGroep(groep)) {
            breidMappingUitMetChoicesVoorStandaardOfIdentiteitGroepObjecttype(mapping, groep, hisObjecttype);
        } else {
            breidMappingUitMetChoicesVoorHisObjecttype(mapping, groep, hisObjecttype);
        }

        return mapping;
    }

    /**
     * Historie objecttypen die horen bij een groep, die een standaard groep is worden anders gebind.
     * Er wordt namelijk nog een extra structure element toegevoegd voor de naam.
     * bijvoorbeeld &lt;structure name="adres"&gt;...&lt;/structure&gt;
     * Daarna komen pas de choices voor de historie velden. (postcode, land, etc..)
     * Dit gebeurt allemaal in deze functie.
     *
     * @param mapping       de mapping voor het Historie objecttype.
     * @param groep         de groep waar de historie bij hoort.
     * @param hisObjecttype het historie objecttype. (Operationeel model)
     */
    private void breidMappingUitMetChoicesVoorStandaardOfIdentiteitGroepObjecttype(
            final Mapping mapping, final Groep groep, final ObjectType hisObjecttype)
    {
        final String elementNaam;
        if (isSubtype(groep.getObjectType())) {
            elementNaam = GeneratieUtil.lowerTheFirstCharacter(
                    groep.getObjectType().getFinaalSupertype().getIdentCode().replace("Persoon", ""));
        } else {
            elementNaam = GeneratieUtil.lowerTheFirstCharacter(
                    groep.getObjectType().getIdentCode().replace("Persoon", ""));
        }

        final Mapping.Choice choice =
                maakStructureMappingChoice(null, null, elementNaam, null, false);

        if (kentFormeleHistorie(groep)) {
            //Voeg structure mapping toe voor formele historie attributen:
            choice.getStructure().getChoiceList().add(maakStructureStructureChoice(
                    null, null, null, getMappingTypeNameVoorFormeleHistorie(), false));
        } else if (kentMaterieleHistorie(groep)) {
            //Voeg structure mapping toe voor materiele historie attributen:
            choice.getStructure().getChoiceList().add(maakStructureStructureChoice(
                    null, null, null, getMappingTypeNameVoorMaterieleHistorie(), false));
        }

        // Voeg choices toe voor (xsd) objecttype attribuut:
        if (!isHierarchischType(groep.getObjectType()) && ID_ONDERZOEK_LGM != groep.getObjectType().getId()) {
            voegChoiceToeVoorXsdAttribuut(choice.getStructure(), ATTR_OBJECTTYPE, null, false,
                                          groep.getObjectType().getIdentCode(), null);
        }

        // Voeg choices toe voor andere attributen:
        voegChoicesToeVoorXsdAttributenOnderGroepen(choice.getStructure(), groep);

        // We binden hier puur op C/D laag his_PersoonXXX objecten, echter in de XSD zitten er ook elementen van
        // attributen die in de A laag zitten, zoals volgnummer van Voornaam en Geslachtsnaamcomponent. Deze moeten we
        // dus aan de mapping toevoegen:
        voegChoicesToeVoorAttributenVanIdentiteitGroep(choice.getStructure(), groep);

        //Voeg strucure mappings toe voor alle attributen van de groep:
        final List<Attribuut> attributen = getBmrDao().getAttributenVanObjectType(hisObjecttype);
        for (Attribuut attribuut : attributen) {
            /*
            - Historie gerelateerde attributen slaan we over, want die zitten in een andere abstracte mapping.
              Daarvoor is de verwijzing hierboven al gemaakt.
            - Id attributen slaan we over want die worden niet geleverd.
            - Attributen waarvan het type een Dynamisch objecttype is, bijvoorbeeld persoon in geboorte, slaan we over,
              want deze zitten niet in de levering.
            */
            if (!isHistorieGerelateerdAttribuut(attribuut)
                    && !isIdAttribuut(attribuut) && !isDynamischObjecttypeAttribuut(attribuut))
            {
                //Omdat we hier het operationele laag attribuut binnen krijgen, gaan we verder met de Logische laag
                //versie van het attribuut, omdat de operationele laag niet alle informatie bevat.
                //(Bijvoorbeeld XSD_IDENT)
                final Attribuut logischeLaagAttribuut =
                        getBmrDao().getLogischeLaagAttribuutVoorOperationeleLaagAttribuut(attribuut);

                choice.getStructure().getChoiceList().add(bouwStructureChoiceVoorattribuut(logischeLaagAttribuut));
            }
        }
        mapping.getChoiceList().add(choice);
    }

    /**
     * Retourneert de type-name van de mapping die de materiele historie velden mapt.
     * @return type name van de mapping
     */
    protected abstract String getMappingTypeNameVoorMaterieleHistorie();

    /**
     * Retourneert de type-name van de mapping die de formele historie velden mapt.
     * @return type name van de mapping
     */
    protected abstract String getMappingTypeNameVoorFormeleHistorie();

    /**
     * Mappings voor historie objecttypen worden hier uitgebreid met choices.
     *
     * @param mapping de mapping voor het historie objecttype.
     * @param groep de groep waar de historie van is.
     * @param hisObjecttype het historie objecttype. (Operationeel model)
     */
    private void breidMappingUitMetChoicesVoorHisObjecttype(final Mapping mapping, final Groep groep,
                                                            final ObjectType hisObjecttype)
    {
        String tagName = GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode());
        Mapping.Choice choice = maakStructureMappingChoice(null, null, tagName, null, true);
        mapping.getChoiceList().add(choice);

        if (kentFormeleHistorie(groep)) {
            //Voeg structure mapping toe voor formele historie attributen:
            choice.getStructure().getChoiceList().add(
                    maakStructureStructureChoice(null, null, null, getMappingTypeNameVoorFormeleHistorie(), false));
        } else if (kentMaterieleHistorie(groep)) {
            //Voeg structure mapping toe voor materiele historie attributen:
            choice.getStructure().getChoiceList().add(
                    maakStructureStructureChoice(null, null, null, getMappingTypeNameVoorMaterieleHistorie(), false));
        }

        // Voeg choices toe voor andere attributen:
        voegChoicesToeVoorXsdAttributenOnderGroepen(choice.getStructure(), groep);

        //Voeg strucure mappings toe voor alle attributen van de groep:
        final List<Attribuut> attributen = getBmrDao().getAttributenVanObjectType(hisObjecttype);


        for (Attribuut attribuut : attributen) {
            /*
            - Historie gerelateerde attributen slaan we over, want die zitten in een andere abstracte mapping.
              Daarvoor is de verwijzing hierboven al gemaakt.
            - Id attributen slaan we over want die worden niet geleverd.
            - Attributen waarvan het type een Dynamisch objecttype is, bijvoorbeeld persoon in geboorte, slaan we over,
              want deze zitten niet in de levering.
            */
            if (!isHistorieGerelateerdAttribuut(attribuut)
                    && !isIdAttribuut(attribuut) && !isDynamischObjecttypeAttribuut(attribuut))
            {
                //Omdat we hier het operationele laag attribuut binnen krijgen, gaan we verder met de Logische laag
                //versie van het attribuut, omdat de operationele laag niet alle informatie bevat.
                //(Bijvoorbeeld XSD_IDENT)
                final Attribuut logischeLaagAttribuut =
                        getBmrDao().getLogischeLaagAttribuutVoorOperationeleLaagAttribuut(attribuut);

                //In bericht vlag is hier van belang.
                if (behoortInXsd(logischeLaagAttribuut)) {
                    choice.getStructure().getChoiceList().add(bouwStructureChoiceVoorattribuut(logischeLaagAttribuut));
                }
            }
        }
    }

    /**
     * Voegt aan de Structure choices toe voor o.a. de attributen 'objecttype' en 'verwerkingssoort'.
     *
     * @param struct de structure.
     */
    protected void voegChoiceToeVoorXsdAttribuut(final Structure struct, final String attribuutNaam,
                                               final String getter, final boolean optioneel, final String constante,
                                               final String enumValueMethod)
    {
        final Structure.Choice attribuutChoice = maakValueStructureChoice("attribute", attribuutNaam,
                                                                null, null, enumValueMethod, null,
                                                                getter, null, optioneel, constante);
        struct.getChoiceList().add(attribuutChoice);
    }

    protected void voegChoiceToeVoorXsdAttribuut(final Mapping mapping, final String attribuutNaam,
                                               final String getter, final boolean optioneel, final String constante,
                                               final String enumValueMethod)
    {
        mapping.getChoiceList().add(maakValueMappingChoice("attribute", attribuutNaam,
                                                                null, null, enumValueMethod, null,
                                                                getter, null, optioneel, constante));
    }

    protected abstract void voegChoicesToeVoorXsdAttributenOnderGroepen(final Structure struct, final Groep groep);

    /**
     * Standaard groep: bovenliggende objecttype heeft A laag tabel dus objectsleutel attribuut nodig..
     * Identiteit groep: bovenliggende objecttype heeft A laag tabel dus objectsleutel attribuut nodig.
     * Subtype: hebben meestal een aparte groep.
     */
    protected boolean isGroepWaarvoorObjectAttributenGewenstZijn(final Groep groep) {
        boolean isGroepWaarvoorObjectSleutelAttribuutGewenstIs = isStandaardGroep(groep) || isIdentiteitGroep(groep);
        isGroepWaarvoorObjectSleutelAttribuutGewenstIs &= !isSubtype(groep.getObjectType());
        isGroepWaarvoorObjectSleutelAttribuutGewenstIs &= !"Relatie".equals(groep.getObjectType().getIdentCode());
        isGroepWaarvoorObjectSleutelAttribuutGewenstIs &= !"Onderzoek".equals(groep.getObjectType().getIdentCode());
        return isGroepWaarvoorObjectSleutelAttribuutGewenstIs;
    }

    protected abstract void voegChoicesToeVoorXsdAttributenOnderObjecttypen(final Mapping objectTypeMapping);

    /**
     * Bouwt een structure choice voor een attribuut.
     *
     * @param logischeLaagAttribuut het attribuut.
     * @return een structure choice voor het attribuut.
     */
    private Structure.Choice bouwStructureChoiceVoorattribuut(final Attribuut logischeLaagAttribuut) {
        final Structure.Choice choice;
        String elementNaam = null;
        String mapAs = null;
        String getMethod = "get";
        String enumValueMethod = null;
        String serializer = null;
        String deserializer = null;
        boolean moetValueMappingZijn = false;
        boolean usageOptional = true;

        if (this.isElementVerplichtInXsd(logischeLaagAttribuut)) {
            usageOptional = false;
        }

        if (isAttribuutTypeAttribuut(logischeLaagAttribuut)) {
            //Attribuuttype:
            elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorAttribuutTypeAttribuut(logischeLaagAttribuut);
            getMethod += logischeLaagAttribuut.getIdentCode();
            //Uitzondering op attribuuttypen die waarderegels kennen, bijv. Ja en JaNee,
            //gebruik serializer en deserializers methods in BindingUtil:
            //Check voor extra waarden
            final List<WaarderegelWaarde> waardes = getBmrDao().getWaardeEnumeratiesVoorElement(
                    logischeLaagAttribuut.getType(), false, true);

            if (!waardes.isEmpty()) {
                moetValueMappingZijn = true;
                serializer = "nl.bzk.brp.model.binding.BindingUtilAttribuutTypen."
                        + GeneratieUtil.lowerTheFirstCharacter(logischeLaagAttribuut.getType().getIdentCode())
                        + "NaarXml";
                deserializer = "nl.bzk.brp.model.binding.BindingUtilAttribuutTypen.xmlNaar"
                        + logischeLaagAttribuut.getType().getIdentCode();
            } else {
                mapAs = attribuutWrapperNaamgevingStrategie.getJavaTypeVoorElement(
                        logischeLaagAttribuut.getType()).getNaam();
            }
        } else if (isStamgegevenAttribuut(logischeLaagAttribuut)) {
            final ObjectType stamgegeven = getBmrDao().getElement(
                    logischeLaagAttribuut.getType().getId(), ObjectType.class);
            final Attribuut logischIdentiteitAttr = bepaalLogischeIdentiteitVoorStamgegeven(stamgegeven);
            elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorStamgegevenAttribuut(logischeLaagAttribuut,
                                                                                      logischIdentiteitAttr);
            getMethod += logischeLaagAttribuut.getIdentCode();

            //Enum-value-method invullen voor enums.
            if (isStatischStamgegevenAttribuut(logischeLaagAttribuut)) {
                moetValueMappingZijn = true;
                final Attribuut logischeIdentiteitAttribuutStamgegeven =
                        getBmrDao().getLogischeIdentiteitAttributenVoorObjectType(stamgegeven).get(0);
                enumValueMethod = "get" + logischeIdentiteitAttribuutStamgegeven.getIdentCode();
                //Verwijzing naar het enum veld opnemen.
            } else {
                //Mappen als stamgegeven wat in een extra binding zit.
                mapAs = attribuutWrapperNaamgevingStrategie.getJavaTypeVoorElement(
                        logischeLaagAttribuut.getType()).getNaam();
            }
        }

        if (moetValueMappingZijn) {
            // Een enum of serializer moet via een extra structure gewrapt worden, vanwege de attribuut indirectie.
            choice = maakStructureStructureChoice(
                    null, getMethod, null, null, usageOptional);
            final Structure.Choice valueChoice = maakValueStructureChoice(
                    null, elementNaam, null, null, enumValueMethod, null,
                    "getWaarde", null, true, serializer, deserializer);
            if (getTestMethodVoorAttributen() != null) {
                valueChoice.getValue().getProperty().setTestMethod(getTestMethodVoorAttributen());
                valueChoice.getValue().getProperty().setUsage("optional");
            }
            choice.getStructure().getChoiceList().add(valueChoice);
        } else {
            choice = maakStructureStructureChoice(null, getMethod, null, null, true);
            //Let op: Nillable is niet toegestaan, zetten we dus hier op false:
            final Structure.Choice structureChoice =
                    maakStructureStructureChoice(null, null, elementNaam, mapAs, false);
            if (getTestMethodVoorAttributen() != null) {
                structureChoice.getStructure().getProperty().setTestMethod(getTestMethodVoorAttributen());
                structureChoice.getStructure().getProperty().setUsage("optional");
            }
            choice.getStructure().getChoiceList().add(structureChoice);
        }
        return choice;
    }

     /**
     * We binden puur op C/D laag HIS_PersXXX classes, echter in de XSD zitten er ook elementen van attributen die in
     * de A laag zitten, zoals volgnummer van Voornaam en Geslachtsnaamcomponent. Deze moeten we dus aan de mapping
     * toevoegen. Dit gebeurt in deze functie.
     *
     * @param structure de mapping waaraan de choice moet worden toegevoegd.
     * @param groep de groep uit het logische model die gekoppeld is aan het historie objecttype.
     */
    private void voegChoicesToeVoorAttributenVanIdentiteitGroep(final Structure structure, final Groep groep) {
        final ObjectType logischModelObjectType = groep.getObjectType();
        if (ID_PERSOON_LGM != logischModelObjectType.getId()) {
            final List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(logischModelObjectType);
            for (Groep otGroep : groepenVoorObjectType) {
                if (isIdentiteitGroep(otGroep)) {
                    final List<Attribuut> attributenVanGroep = getBmrDao().getAttributenVanGroep(otGroep);
                    for (Attribuut attribuut : attributenVanGroep) {
                        // Let op Dynamische objecttypen attributen negeren we hier: omdat we alleen attributen
                        // binden. Bijvoorbeeld Persoon in PersonOnderzoek doen we niet, want het koppelvlak kent deze
                        // constructie nog niet.
                        if (attribuut.getInBericht() != null && 'J' == attribuut.getInBericht()
                                && !isDynamischObjecttypeAttribuut(attribuut))
                        {
                            final Structure.Choice choice =
                                    maakStructureStructureChoice(null,
                                                                 "get" + attribuut.getObjectType().getIdentCode(),
                                                                 null, null, false);

                            //Zet het type naar de implementatie zodat jibx alle getters kan vinden.
                            if (isHisVolledigType(attribuut.getObjectType())) {
                                choice.getStructure().getProperty().setType(
                                        hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(
                                                attribuut.getObjectType()).getFullyQualifiedClassName());
                            } else {
                                choice.getStructure().getProperty().setType(
                                        operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(
                                                attribuut.getObjectType()).getFullyQualifiedClassName());
                            }

                            String elementNaam =
                                    XsdNaamgevingUtil.bepaalElementNaamVoorAttribuutTypeAttribuut(attribuut);
                            if (isStamgegevenAttribuut(attribuut)) {
                                final ObjectType stamgegeven = getBmrDao().getElement(
                                        attribuut.getType().getId(), ObjectType.class);
                                final Attribuut logischIdentiteitAttr =
                                        bepaalLogischeIdentiteitVoorStamgegeven(stamgegeven);
                                elementNaam =
                                        XsdNaamgevingUtil.bepaalElementNaamVoorStamgegevenAttribuut(attribuut,
                                                                                                 logischIdentiteitAttr);
                            }
                            String mapAs = attribuutWrapperNaamgevingStrategie.
                                    getJavaTypeVoorElement(attribuut.getType()).getNaam();

                            /**
                             * De opzet voor dit attribuut is als volgt. Er is een Structure choice die terug
                             * navigeert van het His_PersXXX object naar het A-Laag object, dat is de choice die
                             * hierboven is aangemaakt.
                             * In deze choice komt een structure die navigeert naar het attribuut via get-method.
                             * En in deze structure komt nog een geneste structure die het attribuutje
                             * uiteindelijk bindt en gebruik maakt van de test-method.
                             */
                            final Structure.Choice structureChoice = maakStructureStructureChoice(
                                    null,
                                    "get" + attribuut.getIdentCode(),
                                    null,
                                    null,
                                    true);

                            final Structure.Choice attribuutStructureChoice = maakStructureStructureChoice(
                                    null,
                                    null,
                                    elementNaam,
                                    mapAs,
                                    true);

                            if (getTestMethodVoorAttributen() != null) {
                                attribuutStructureChoice.getStructure().getProperty().setTestMethod(
                                        getTestMethodVoorAttributen());
                                attribuutStructureChoice.getStructure().getProperty().setUsage("optional");
                            }
                            structureChoice.getStructure().getChoiceList().add(attribuutStructureChoice);
                            choice.getStructure().getChoiceList().add(structureChoice);
                            structure.getChoiceList().add(choice);
                        }
                    }
                }
            }
        }
    }

    /**
     * Bepaalt het type-name attribuut voor een Jibx mapping, deze moet matchen aan de naam van de XSD complex type
     * waar deze mapping voor gemaakt is.
     *
     * @param groep de groep die gekoppeld is aan het historie objecttype.
     * @return de type-name voor de mapping.
     */
    private String bepaalTypeNameVoorGroep(final Groep groep) {
        String resultaat = "Groep_" + groep.getObjectType().getIdentCode();
        if (!isStandaardGroep(groep)) {
            resultaat += groep.getIdentCode();
        }
        resultaat += "_Levering";
        return resultaat;
    }

    /**
     * Bepaalt type-name attribuut voor een mapping van een objecttype.
     *
     * @param objectType het objecttype waar de mapping voor is.
     * @return type-name voor de mapping.
     */
    private String bepaalTypeNameVoorObjecttype(final ObjectType objectType) {
        return "Objecttype_" + objectType.getIdentCode() + "_Levering";
    }

    protected abstract String getTypeNameVoorPersoonMapping();

    /**
     * Bepaalt type-name attribuut voor een mapping van een container.
     *
     * @param invAttribuut het inverse attribuut waarvoor een container mapping wordt gebouwd.
     * @return type-name voor container mapping.
     */
    private String bepaalTypeNameVoorContainer(final Attribuut invAttribuut) {

        final String identCode;
        if (invAttribuut.getXsdInverseAssociatieIdentCode() != null && !invAttribuut.getXsdInverseAssociatieIdentCode().equals("")) {
            identCode = invAttribuut.getXsdInverseAssociatieIdentCode();
        } else if (invAttribuut.getInverseAssociatieIdentCode() != null && !invAttribuut.getInverseAssociatieIdentCode().equals("")) {
            identCode = invAttribuut.getInverseAssociatieIdentCode();
        } else {
            identCode = "ONBEKEND";
            LOGGER.error("De inverseAssociatie dient een ident code te hebben voor het opstellen van de container type naam voor inverse attribuut: {} "
                             + "van objecttype {}.",
                         invAttribuut.getNaam(), invAttribuut.getObjectType().getNaam());
        }

        String resultaat = "Container_Persoon" + identCode;
        if (ID_BETROKKENHEID_LGM == invAttribuut.getObjectType().getId()) {
            resultaat += "_DetailsPersoon";
        }
        resultaat += "_Levering";
        return resultaat;
    }

    protected abstract String getTestMethodVoorAttributen();

    protected abstract String getClassNaamVanIndicatieMarshaller();

    protected abstract String getTestMethodVoorInverseAssociatiesVanPersoon(final Attribuut invAttribuut);
}
