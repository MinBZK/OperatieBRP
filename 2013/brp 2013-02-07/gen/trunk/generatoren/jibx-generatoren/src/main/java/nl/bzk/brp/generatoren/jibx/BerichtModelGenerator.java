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

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrElementFilterObject;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.BerichtModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.writer.GenerationGapPatroonJavaKlasseWriter;
import nl.bzk.brp.generatoren.jibx.common.JibxBinding;
import nl.bzk.brp.generatoren.jibx.model.Binding;
import nl.bzk.brp.generatoren.jibx.model.Collection;
import nl.bzk.brp.generatoren.jibx.model.Mapping;
import nl.bzk.brp.generatoren.jibx.model.Property;
import nl.bzk.brp.generatoren.jibx.model.Structure;
import nl.bzk.brp.generatoren.jibx.model._Object;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import nl.bzk.brp.metaregister.model.WaarderegelWaarde;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

/** Genereert Binding tussen het Java berichten model en de XSD. */
@Component("berichtModelJibxGenerator")
public class BerichtModelGenerator extends AbstractJibxGenerator {

    private final BerichtModelNaamgevingStrategie berichtModelNaamgevingStrategie =
            new BerichtModelNaamgevingStrategie();
    private NaamgevingStrategie naamgevingStrategie = new AlgemeneNaamgevingStrategie();

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final Binding bindingVoorGroepen = genereerBindingVoorGroepen();
        final Binding bindingVoorObjectTypen = genereerBindingVoorObjectTypen();

        this.jibxWriterFactory(generatorConfiguratie).
                marshallXmlEnSchrijfWeg(JibxBinding.GROEPEN_BERICHT, bindingVoorGroepen, this);
        this.jibxWriterFactory(generatorConfiguratie).
                marshallXmlEnSchrijfWeg(JibxBinding.OBJECT_TYPEN_BERICHT, bindingVoorObjectTypen, this);
    }

    /**
     * Genereert binding voor object typen.
     *
     * @return Binding voor object typen.
     */
    private Binding genereerBindingVoorObjectTypen() {
        final Binding binding = new Binding();
        binding.setPackage("nl.bzk.brp");

        // Include de extra handmatige binding.
        this.voegJibxBindingIncludeToe(binding, JibxBinding.EXTRA_HANDMATIGE_BINDING);
        // Include de groepen bindings.
        this.voegJibxBindingIncludeToe(binding, JibxBinding.GROEPEN_BERICHT);

        final BmrElementFilterObject filter = new BmrElementFilterObject(BmrLaag.LOGISCH);
        filter.setInBericht(true);
        filter.setSoortInhoud(BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode());
        final List<ObjectType> objectTypen = this.getBmrDao().getObjectTypen(filter);

        for (ObjectType objectType : objectTypen) {
            if (ID_ACTIE_LGM == objectType.getId()) {
                binding.getMappingList().add(maakRootObjectenContainerMappingVoorActie());
            }
            binding.getMappingList().add(bouwUserClassMappingVoorObjectType(objectType));
            binding.getMappingList().add(bouwGeneratedClassMappingVoorObjectType(objectType));
            binding.getMappingList().addAll(bouwContainerMappingsVoorInverseAssociaties(objectType));

            //Als het object xsd views per discriminator waarde heeft dan moeten we voor de binding per
            //discriminator waarde een aparte mapping genereren. Dus per Tuple krijg je een mapping.
            if (objectType.getXsdViewsPerDiscriminatorWaarde() != null
                    && 'J' == objectType.getXsdViewsPerDiscriminatorWaarde())
            {
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
                                    binding.getMappingList().add(bouwUserClassMappingVoorTuple(objectType, tuple));
                                    binding.getMappingList().add(bouwGeneratedClassMappingVoorTuple(objectType, tuple));
                                }
                            }
                        }
                    }
                }
            }
        }
        return binding;
    }

    /**
     * Bouwt een mapping voor de gegenereerde klasse dat hoort bij een BMR Tuple.
     * @param objectType Objecttype dat een attribuut kent van het type objecttype dat ouder is van deze tuple.
     * @param tuple De tuple.
     * @return Mapping voor tuple.
     */
    private Mapping bouwGeneratedClassMappingVoorTuple(final ObjectType objectType, final Tuple tuple) {
        final Mapping mapping = new Mapping();
        mapping.setAbstract(true);
        mapping.set_Class(berichtModelNaamgevingStrategie.getJavaTypeVoorElement(tuple).getPackagePad()
                                  + GenerationGapPatroonJavaKlasseWriter.GENERATIE_TYPE_SUB_PACKAGE
                                  + "." + GenerationGapPatroonJavaKlasseWriter.GENERATIE_KLASSE_NAAM_PREFIX
                                  + objectType.getXsdViewPrefix()
                                  + berichtModelNaamgevingStrategie.getJavaTypeVoorElement(tuple).getNaam());
        mapping.setTypeName(GenerationGapPatroonJavaKlasseWriter.GENERATIE_KLASSE_NAAM_PREFIX
                                    + objectType.getXsdViewPrefix() + "_"
                                    + berichtModelNaamgevingStrategie.getJavaTypeVoorElement(tuple).getNaam());
        mapping.getChoiceList().add(
                maakStructureMappingChoice(
                        null,
                        null,
                        getTypeNameVoorObjectType(objectType),
                        false,
                        true));
        return mapping;
    }

    /**
     * Bouwt een mapping voor de user klasse dat hoort bij een BMR Tuple.
     * @param objectType Objecttype dat een attribuut kent van het type objecttype dat ouder is van deze tuple.
     * @param tuple De tuple.
     * @return Mapping voor tuple.
     */
    private Mapping bouwUserClassMappingVoorTuple(final ObjectType objectType, final Tuple tuple) {
        final Mapping mapping = new Mapping();
        mapping.setAbstract(true);
        mapping.set_Class(berichtModelNaamgevingStrategie.getJavaTypeVoorElement(tuple).getPackagePad()
                          + "." + objectType.getXsdViewPrefix()
                          + berichtModelNaamgevingStrategie.getJavaTypeVoorElement(tuple).getNaam());
        mapping.setTypeName(objectType.getXsdViewPrefix() + "_"
                                    + tuple.getIdentCode());
        mapping.getChoiceList().add(
                maakStructureMappingChoice(
                        null,
                        null,
                        "Abstract" + objectType.getXsdViewPrefix() + "_"
                         + berichtModelNaamgevingStrategie.getJavaTypeVoorElement(tuple).getNaam(),
                        false,
                        true));
        return mapping;
    }

    /**
     * Bouwt voor elk inverse associatie een mapping voor de container die in de XSD staat gedefinieerd.
     *
     * @param objectType Object type waar de inverse associaties bij horen.
     * @return Lijst met mappings voor de xsd containers.
     */
    private List<Mapping> bouwContainerMappingsVoorInverseAssociaties(final ObjectType objectType) {
        final List<Attribuut> inverseAssociatieAttributen =
            getBmrDao().getInverseAttributenVoorObjectType(objectType);
        final List<Mapping> mappings = new ArrayList<Mapping>();

        for (Attribuut invAttribuut : inverseAssociatieAttributen) {
            final boolean isAdministratieveHandelingActiesUitzondering =
                ("Administratieve handeling".equalsIgnoreCase(objectType.getNaam())
                    && ID_ACTIE_LGM == invAttribuut.getObjectType().getId());

            if (invAttribuut.getXsdInverseAssociatieOpnemen() == null
                || invAttribuut.getXsdInverseAssociatieOpnemen() != 'N'
                || isAdministratieveHandelingActiesUitzondering)
            {
                final Mapping mapping = new Mapping();
                mapping.setAbstract(true);
                mapping.setTypeName("Container_" + objectType.getIdentCode()
                    + invAttribuut.getInverseAssociatieIdentCode());
                mapping.set_Class("java.util.List");
                _Object obj = new _Object();
                obj.setCreateType("java.util.ArrayList");
                mapping.setObject(obj);
                mappings.add(mapping);

                if (isAdministratieveHandelingActiesUitzondering) {
                    final ObjectType discriminatorObjectType =
                        getBmrDao().getDiscriminatorObjectType(invAttribuut.getObjectType());
                    for (Tuple tuple : discriminatorObjectType.getTuples()) {
                        final String name = GeneratieUtil.lowerTheFirstCharacter(tuple.getIdentCode());
                        //MapAs moet een verwijzing zijn naar de specifieke mapping voor deze tuple.
                        final String mapAs = invAttribuut.getObjectType().getIdentCode() + "_" + tuple.getIdentCode();

                        final Collection.Choice choice = maakStructureCollectionChoice(
                            name, mapAs, true, false
                        );

                        final Mapping.Choice collectionChoice = new Mapping.Choice();
                        final Collection collection = new Collection();
                        final Property collProp = new Property();
                        collProp.setUsage("optional");
                        collection.setProperty(collProp);
                        collectionChoice.setCollection(collection);

                        collection.getChoiceList().add(choice);
                        mapping.getChoiceList().add(collectionChoice);
                    }
                } else if (isKoppelingObjectType(invAttribuut.getObjectType())) {
                    // Specifiek geval voor een koppeling object type die naar 1 of meer
                    // andere object typen kan verwijzen (al dan niet stamgegevens)
                    final List<ObjectType> naarObjectTypen = bepaalGekoppeldeObjectTypen(invAttribuut);
                    for (ObjectType naarType : naarObjectTypen) {
                        final boolean naarObjectTypeIsStamgegeven =
                                BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode() == naarType.getSoortInhoud()
                                || BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode() == naarType.getSoortInhoud();

                        final Mapping.Choice collectionChoice = new Mapping.Choice();
                        final Collection collection = new Collection();
                        final Property collProp = new Property();
                        collProp.setUsage("optional");
                        collection.setProperty(collProp);
                        collectionChoice.setCollection(collection);
                        if (naarObjectTypeIsStamgegeven) {
                            final Attribuut logischeIdAttr = bepaalLogischeIdentiteitVoorStamgegeven(naarType);
                            final JavaType javaType =
                                    berichtModelNaamgevingStrategie.getJavaTypeVoorElement(
                                            invAttribuut.getObjectType());
                            collection.setItemType(javaType.getPackagePad() + ".basis.Abstract" + javaType.getNaam());
                            final Collection.Choice choice = maakStructureCollectionChoice(
                                    null, null, false, true
                            );
                            choice.getStructure().setObject(new _Object());
                            choice.getStructure().getObject().setCreateType(javaType.getFullyQualifiedClassName());
                            choice.getStructure().getChoiceList().add(
                                    maakStructureValueChoice(
                                            GeneratieUtil.lowerTheFirstCharacter(
                                                    naarType.getIdentCode() + logischeIdAttr.getIdentCode()),
                                            GeneratieUtil.lowerTheFirstCharacter(
                                                    naarType.getIdentCode() + logischeIdAttr.getIdentCode()), true)
                            );
                            collection.getChoiceList().add(choice);
                        } else {
                            final String name = GeneratieUtil.lowerTheFirstCharacter(
                                    naarType.getIdentCode());
                            final String mapAs = getTypeNameVoorObjectType(invAttribuut.getObjectType());

                            final Collection.Choice choice = maakStructureCollectionChoice(
                                    name, mapAs, true, true
                            );
                            collection.getChoiceList().add(choice);
                        }
                        mapping.getChoiceList().add(collectionChoice);
                    }
                } else if (objectType.getSubtypen().isEmpty()) {
                    final Mapping.Choice collectionChoice = new Mapping.Choice();
                    final Collection collection = new Collection();
                    final Property collProp = new Property();
                    collProp.setUsage("optional");
                    collection.setProperty(collProp);
                    collectionChoice.setCollection(collection);
                    final String name = GeneratieUtil.lowerTheFirstCharacter(
                            invAttribuut.getObjectType().getIdentCode().replace(objectType.getNaam(), ""));
                    final String mapAs = getTypeNameVoorObjectType(invAttribuut.getObjectType());

                    final Collection.Choice choice = maakStructureCollectionChoice(
                            name, mapAs, true, true
                    );
                    collection.getChoiceList().add(choice);
                    mapping.getChoiceList().add(collectionChoice);
                } else {
                    //Er is sprake van subtypering, maak voor elke subtype een collection.
                    final List<ObjectType> subtypen = verzamelFinaleSubtypen(invAttribuut.getObjectType());
                    for (ObjectType subtype : subtypen) {
                        final Mapping.Choice collectionChoice = new Mapping.Choice();
                        final Collection collection = new Collection();
                        final Property collProp = new Property();
                        collProp.setUsage("optional");
                        collection.setProperty(collProp);
                        collectionChoice.setCollection(collection);

                        final String name = GeneratieUtil.lowerTheFirstCharacter(subtype.getIdentCode());
                        final String mapAs = getTypeNameVoorObjectType(subtype);

                        final Collection.Choice choice = maakStructureCollectionChoice(
                            name, mapAs, true, true
                        );
                        collection.getChoiceList().add(choice);
                        mapping.getChoiceList().add(collectionChoice);
                    }
                }
            }
        }
        return mappings;
    }

    /**
     * Bouwt jibx mapping voor een gegenereerde object type klasse.
     *
     * @param objectType Het object type.
     * @return Jibx mapping voor object type.
     */
    private Mapping bouwGeneratedClassMappingVoorObjectType(final ObjectType objectType) {
        final Mapping mapping = new Mapping();
        final boolean isAdministratieveHandelingObjectType =
            "Administratieve handeling".equalsIgnoreCase(objectType.getNaam());
        mapping.setAbstract(true);
        mapping.setTypeName(GenerationGapPatroonJavaKlasseWriter.GENERATIE_KLASSE_NAAM_PREFIX
            + berichtModelNaamgevingStrategie.getJavaTypeVoorElement(objectType).getNaam());

        mapping.set_Class(berichtModelNaamgevingStrategie.getJavaTypeVoorElement(objectType).getPackagePad()
            + GenerationGapPatroonJavaKlasseWriter.GENERATIE_TYPE_SUB_PACKAGE
            + "."
            + GenerationGapPatroonJavaKlasseWriter.GENERATIE_KLASSE_NAAM_PREFIX
            + berichtModelNaamgevingStrategie.getJavaTypeVoorElement(objectType).getNaam());

        if (objectType.getSuperType() != null) {
            //Dit is een subtype, maak een structure aan dat verwijst naar de mappping van het supertype. (mapAs)
            mapping.getChoiceList().add(
                maakStructureMappingChoice(
                    null,
                    null,
                    getTypeNameVoorObjectType(objectType.getSuperType()),
                    false,
                    false));
        } else if (ID_BERICHT_LGM != objectType.getId()) {
            // Alleen object type bericht is niet identificeerbaar.
            mapping.getChoiceList().add(
                maakStructureMappingChoice(null, null, "ObjectTypeIdentificeerbaar", false, false));
        }

        final List<Pair<String, GeneriekElement>> teGenererenMappings = new ArrayList<Pair<String, GeneriekElement>>();
        final List<Groep> groepen = getBmrDao().getGroepenVoorObjectType(objectType);
        final List<Attribuut> inverseAssociatieAttributen =
            getBmrDao().getInverseAttributenVoorObjectType(objectType);

        //Voeg de groepen toe.
        for (Groep groep : groepen) {
            teGenererenMappings.add(new ImmutablePair<String, GeneriekElement>(groep.getIdentCode(), groep));
        }

        //Voeg de inverse attributen toe.
        for (Attribuut attribuut : inverseAssociatieAttributen) {
            teGenererenMappings.add(
                new ImmutablePair<String, GeneriekElement>(attribuut.getInverseAssociatieIdentCode(), attribuut));
        }

        //Pas de XSD sortering toe, indien nodig.
        if (objectType.getXsdSortering() != null) {
            String xsdSortering = objectType.getXsdSortering();
            //HACK: Bij Administratieve handeling hoort de "acties" container aan het eind toegevoegd.
            //Dit doen we hier door de xsd sortering te manipuleren.
            if (isAdministratieveHandelingObjectType) {
                xsdSortering += " acties";
            }
            sorteerElementen(xsdSortering, teGenererenMappings);
        }

        for (Pair<String, GeneriekElement> teGenererenMapping : teGenererenMappings) {
            //Custom xsd typen niet opnemen in de binding. (Bijv. Stuurgegevens)
            if (teGenererenMapping.getValue().getXsdType() == null) {
                if (teGenererenMapping.getValue() instanceof Groep) {
                    final Groep groep = (Groep) teGenererenMapping.getValue();
                    if (behoortInXsdAlsLosStaandType(groep) || behoortInXsdOnderEenObjectType(groep)) {
                        if (isIdentiteitGroep(groep)) {
                            //Koppeling objecttypen worden hier overgeslagen en verderop behandeld.
                            if (!isKoppelingObjectType(objectType)) {
                                //Identiteit groep plat slaan.
                                bouwStructuresVoorAttributenIdentiteitGroep(mapping, groep);
                            }
                        } else {
                            bouwStructureVoorGroep(mapping, groep);
                        }
                    }
                } else if (teGenererenMapping.getValue() instanceof Attribuut) {
                    final Attribuut attr = (Attribuut) teGenererenMapping.getValue();
                    //Voeg inverse associaties toe aan de mapping.
                    bouwInverseAssociatiesInMapping(objectType, isAdministratieveHandelingObjectType, attr, mapping);
                }
            }
        }

        //Koppeling objecttypen moeten ook worden ge-mapped.
        if (isKoppelingObjectType(objectType)) {
            bouwStructuresVoorKoppelingObjectType(mapping, groepen);
        }
        return mapping;
    }

    /**
     * Genereert structure mappings voor koppel objecttypen.
     *
     * @param mapping De mapping waar de structures in moeten.
     * @param groepen De groepen van het koppel objecttype.
     */
    private void bouwStructuresVoorKoppelingObjectType(final Mapping mapping, final List<Groep> groepen) {
        for (Groep groep : groepen) {
            //De koppeling attributen zitten in de Identiteit groep.
            if (IDENTITEIT.equals(groep.getNaam())) {
                final List<Attribuut> attributenVanGroep = getBmrDao().getAttributenVanGroep(groep);
                for (Attribuut attribuut : attributenVanGroep) {
                    //Attribuut moet een objecttype zijn
                    if ("OT".equals(attribuut.getType().getSoortElement().getCode())
                        && StringUtils.isBlank(attribuut.getInverseAssociatieIdentCode()))
                    {
                        final ObjectType ot =
                            getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                        if (BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == ot.getSoortInhoud()) {
                            mapping.getChoiceList().add(
                                maakStructureMappingChoice(
                                    GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                                    //Element naam moet null zijn want deze is opgenomen in de
                                    //container mapping.
                                    null,
                                    "Objecttype_" + ot.getIdentCode(),
                                    false,
                                    true
                                )
                            );
                        } else {
                            //Stamgegeven moet via een logische identeit attribuut worden ge-mapped. Omdat het type
                            //van deze attributen altijd een String is hebben we een value choice nodig.
                            final Attribuut logischIdentiteitAttr;
                            logischIdentiteitAttr = bepaalLogischeIdentiteitVoorStamgegeven(ot);
                            String name = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                            String field = GeneratieUtil.lowerTheFirstCharacter(
                                    attribuut.getIdentCode() + logischIdentiteitAttr.getIdentCode());

                            mapping.getChoiceList().add(
                                maakValueChoice(
                                        null,
                                        name,
                                        field,
                                        //Element naam moet null zijn want deze is opgenomen in de
                                        //container mapping.
                                        null,
                                        null,
                                        null,
                                        null,
                                        true
                                )
                            );
                        }
                    }
                }
            }
        }
    }

    /**
     * Sorteert de element volgens de XSD sortering string uit het BMR.
     *
     * @param xsdSortering xsd sortering uit BMR.
     * @param elementen elementen.
     */
    private void sorteerElementen(final String xsdSortering, final List<Pair<String, GeneriekElement>> elementen) {
        Collections.sort(elementen, new Comparator<Pair<String, GeneriekElement>>() {
            @Override
            public int compare(final Pair<String, GeneriekElement> g1, final Pair<String, GeneriekElement> g2) {
                return xsdSortering.toUpperCase().indexOf(g1.getKey().toUpperCase())
                    - xsdSortering.toUpperCase().indexOf(g2.getKey().toUpperCase());
            }
        });
    }

    /**
     * Bouwt een structure die verwijst (mapAs) naar de mapping voor de groep.
     *
     * @param mapping De mapping waar de structure in moet komen.
     * @param groep De groep waarvoor een structure wordt aangemaakt.
     */
    private void bouwStructureVoorGroep(final Mapping mapping, final Groep groep) {
        boolean nillabe = true;
        boolean optional = true;

        if (isElementVerplichtInXsd(groep)) {
            nillabe = false;
            optional = false;
        }

        String xmlElementNaam = GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode());
        if (STANDAARD.equals(groep.getNaam())) {
            //Standaard groep wordt plat geslagen in de xsd. Dus geen element naam.
            xmlElementNaam = null;
            //..dus nillable kan nooit true zijn. (JiBX)
            nillabe = false;
        }

        //Structure aanmaken voor groep.
        mapping.getChoiceList().add(
            maakStructureMappingChoice(
                GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode()),
                xmlElementNaam,
                getTypeNameVoorGroep(groep),
                nillabe,
                optional));
    }

    /**
     * Voegt aan de mapping structures toe voor het binden van de attributen van de identiteit groep.
     *
     * @param mapping De mapping waaraan structures moeten worden toegevoegd.
     * @param groep De identiteit groep.
     */
    private void bouwStructuresVoorAttributenIdentiteitGroep(final Mapping mapping, final Groep groep) {
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
        for (Attribuut attribuut : attributen) {
            final String elementNaam;
            String mapAs = null;
            final String field;
            String enumValueMethod = null;
            boolean moetValueChoiceZijn = false;

            if (behoortInXsd(attribuut)) {
                boolean nillabe = true;
                boolean optional = true;

                if (isElementVerplichtInXsd(attribuut)) {
                    nillabe = false;
                    optional = false;
                }

                if (attribuut.getType().getSoortInhoud() != null
                    && BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == attribuut.getType().getSoortInhoud())
                {
                    //Haal het object type op
                    final ObjectType ot = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                    if (!ot.getSubtypen().isEmpty()) {
                        //Maak de binding voor de choice:
                        bouwbindingVoorChoice(mapping, ot);
                        continue;
                    } else {
                        elementNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getType().getIdentCode());
                        field = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                        mapAs = "Objecttype_" + attribuut.getType().getIdentCode();
                    }
                } else if (attribuut.getType().getSoortInhoud() != null
                    && (BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()
                    || BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()))
                {
                    moetValueChoiceZijn = true;
                    elementNaam = bepaalElementNaamVoorStamgegevenAttribuut(attribuut);
                    final ObjectType stamgegeven = getBmrDao().getElement(
                        attribuut.getType().getId(), ObjectType.class);
                    final Attribuut logischIdentiteitAttr =
                        bepaalLogischeIdentiteitVoorStamgegeven(stamgegeven);

                    if (BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()) {
                        field = GeneratieUtil.lowerTheFirstCharacter(
                            attribuut.getIdentCode());
                        enumValueMethod = "getCode";
                    } else {
                        field = GeneratieUtil.lowerTheFirstCharacter(
                            attribuut.getIdentCode() + logischIdentiteitAttr.getIdentCode());
                    }
                } else {
                    elementNaam = GeneratieUtil.lowerTheFirstCharacter(
                        attribuut.getIdentCode().replace(groep.getIdentCode(), ""));
                    mapAs = naamgevingStrategie.getJavaTypeVoorElement(attribuut.getType()).getNaam();
                    field = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                }

                //Indien er sprake is van views per discriminator waarde, dan moeten we de structure voor elke
                //view herhalen.
                if (attribuut.getType().getXsdViewsPerDiscriminatorWaarde() != null
                    && 'J' == attribuut.getType().getXsdViewsPerDiscriminatorWaarde())
                {
                    final ObjectType ot = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                    final ObjectType discriminatorObjectType = getBmrDao().getDiscriminatorObjectType(ot);
                    for (Tuple tuple : discriminatorObjectType.getTuples()) {

                        //Maak een aparte structure aan voor de naam van het element dat bij deze tuple hoort.
                        Mapping.Choice choice = maakStructureMappingChoice(
                            null,
                            GeneratieUtil.lowerTheFirstCharacter(tuple.getIdentCode()),
                            null,
                            false,
                            true
                        );

                        //Maak in de structure een structure mapping aan naar de basis mapping voor deze tuple.
                        final Structure.Choice structStructChoice = maakStructureStructureChoice(
                                field,
                                null,
                                ot.getXsdViewPrefix() + "_" + tuple.getIdentCode(),
                                false,
                                optional
                        );

                        //Forceer het type naar de class die bij de Tuple hoort:
                        structStructChoice.getStructure().getProperty().setType(
                                berichtModelNaamgevingStrategie.getJavaTypeVoorElement(tuple).getPackagePad()
                                + "." + ot.getXsdViewPrefix()
                                + berichtModelNaamgevingStrategie.getJavaTypeVoorElement(tuple).getNaam()
                        );
                        choice.getStructure().getChoiceList().add(structStructChoice);

                        mapping.getChoiceList().add(choice);
                    }
                } else {
                    if (moetValueChoiceZijn) {
                        mapping.getChoiceList().add(
                            maakValueChoice(
                                null,
                                elementNaam,
                                field,
                                null,
                                enumValueMethod,
                                null,
                                null,
                                optional
                            )
                        );
                    } else {
                        mapping.getChoiceList().add(
                            maakStructureMappingChoice(
                                field,
                                elementNaam,
                                mapAs,
                                nillabe,
                                optional));
                    }
                }
            }
        }
    }

    /**
     * Implementatie van de subtypering in de binding. Deze functie maakt een choice aan in de binding.
     * In JiBX kennen we geen choice dus dit worden structures per subtype die alleen optioneel zijn.
     *
     * @param mapping De mapping waar de choice subtypering in geimplementeerd moet worden.
     * @param superType Super type.
     */
    private void bouwbindingVoorChoice(final Mapping mapping, final ObjectType superType) {
        final String field = GeneratieUtil.lowerTheFirstCharacter(superType.getIdentCode());
        for (ObjectType subType : superType.getSubtypen()) {
            if (!subType.getSubtypen().isEmpty()) {
                for (ObjectType subSubType : subType.getSubtypen()) {
                    final String elementNaam = GeneratieUtil.lowerTheFirstCharacter(subSubType.getIdentCode());
                    final String mapAs = "Objecttype_" + subSubType.getIdentCode();
                    mapping.getChoiceList().add(maakStructureMappingChoice(field, elementNaam, mapAs, true, true));
                }
            } else {
                final String elementNaam = GeneratieUtil.lowerTheFirstCharacter(subType.getIdentCode());
                final String mapAs = "Objecttype_" + subType.getIdentCode();
                mapping.getChoiceList().add(maakStructureMappingChoice(field, elementNaam, mapAs, true, true));
            }
        }
    }

    /**
     * Voegt structures aan de mapping toe voor alle inverse associatie van object type.
     *
     * @param objectType Het object type dat inverse associaties kent.
     * @param isAdministratieveHandelingObjectType Geeft aan of het om de administrieve handeling objecttype gaat.
     * @param attribuut Het attribuut.
     * @param mapping    De mapping waaraan structures moeten worden toegevoegd.
     */
    private void bouwInverseAssociatiesInMapping(final ObjectType objectType,
        final boolean isAdministratieveHandelingObjectType,
        final Attribuut attribuut,
        final Mapping mapping)
    {
        if (attribuut.getXsdInverseAssociatieOpnemen() == null
            || attribuut.getXsdInverseAssociatieOpnemen() != 'N'
            //HACK: Bij Administratieve handeling moeten we de "acties" inverse WEL opnemen.
            || isAdministratieveHandelingObjectType)
        {
            final String elementNaam =
                GeneratieUtil.lowerTheFirstCharacter(attribuut.getInverseAssociatieIdentCode());
            final String mapAs =
                "Container_" + objectType.getIdentCode() + attribuut.getInverseAssociatieIdentCode();
            final String field = GeneratieUtil.lowerTheFirstCharacter(attribuut.getInverseAssociatieIdentCode());
            mapping.getChoiceList().add(maakStructureMappingChoice(field, elementNaam, mapAs, true, true));
        }
    }

    /**
     * Bouwt de jibx mapping voor de user klasse van een object type.
     *
     * @param objectType Het object type.
     * @return Jibx mapping voor object type.
     */
    private Mapping bouwUserClassMappingVoorObjectType(final ObjectType objectType) {
        final Mapping mapping = new Mapping();
        mapping.setAbstract(true);
        mapping.setTypeName(getTypeNameVoorObjectType(objectType));
        mapping.set_Class(
                berichtModelNaamgevingStrategie.getJavaTypeVoorElement(objectType).getFullyQualifiedClassName());
        mapping.getChoiceList().add(maakStructureMappingChoice(
                null, null, GenerationGapPatroonJavaKlasseWriter.GENERATIE_KLASSE_NAAM_PREFIX
                + berichtModelNaamgevingStrategie.getJavaTypeVoorElement(objectType).getNaam(), false, false));

        if (ID_ACTIE_LGM == objectType.getId()) {
            //HACK: Actie kent root objecten. Maak een mapping aan naar deze collectie.
            mapping.getChoiceList().add(
                maakStructureMappingChoice("rootObjecten", null, "Container_RootObjecten", false, true)
            );
        }
        return mapping;
    }

    /**
     * Genereert de binding voor groepen met alle mappings.
     *
     * @return Jibx binding.
     */
    private Binding genereerBindingVoorGroepen() {
        final Binding binding = new Binding();

        // Include de extra handmatige binding.
        this.voegJibxBindingIncludeToe(binding, JibxBinding.EXTRA_HANDMATIGE_BINDING);
        // Include de attribuut typen bindings.
        this.voegJibxBindingIncludeToe(binding, JibxBinding.ATTRIBUUT_TYPEN);

        final List<Groep> groepen = getBmrDao().getGroepen();

        for (Groep groep : groepen) {
            if (behoortInXsdAlsLosStaandType(groep)
                || (behoortInXsdOnderEenObjectType(groep) && STANDAARD.equals(groep.getNaam())))
            {
                binding.getMappingList().add(bouwUserClassMappingVoorGroep(groep));
                binding.getMappingList().add(bouwGeneratedClassMappingVoorGroep(groep));
            }
        }
        return binding;
    }

    /**
     * Bouwt jibx mapping voor een gegenereerde groeps klasse.
     *
     * @param groep De groep.
     * @return Jibx Mapping voor groep.
     */
    private Mapping bouwGeneratedClassMappingVoorGroep(final Groep groep) {
        final Mapping mapping = new Mapping();
        mapping.setAbstract(true);
        mapping.setTypeName(GenerationGapPatroonJavaKlasseWriter.GENERATIE_KLASSE_NAAM_PREFIX
            + berichtModelNaamgevingStrategie.getJavaTypeVoorElement(groep).getNaam());

        mapping.set_Class(berichtModelNaamgevingStrategie.getJavaTypeVoorElement(groep).getPackagePad()
            + GenerationGapPatroonJavaKlasseWriter.GENERATIE_TYPE_SUB_PACKAGE
            + "."
            + GenerationGapPatroonJavaKlasseWriter.GENERATIE_KLASSE_NAAM_PREFIX
            + berichtModelNaamgevingStrategie.getJavaTypeVoorElement(groep).getNaam());

        //Standaard groepen zijn geen complex types in de xsd, dus kunnen ook niet Identificeerbaar zijn.
        if (!STANDAARD.equals(groep.getNaam())) {
            mapping.getChoiceList().add(maakStructureMappingChoice(null, null, "GroepIdentificeerbaar", false, true));
        }

        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
        for (Attribuut attribuut : attributen) {
            // Zoals in: MinOccurs = 0
            boolean usageOptional = true;
            boolean nillable = true;

            if (this.isElementVerplichtInXsd(attribuut)) {
                nillable = false;
                // Zoals in: MinOccurs = 1
                usageOptional = false;
            }

            if (attribuut.getType().getSoortInhoud() != null
                && BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == attribuut.getType().getSoortInhoud())
            {
                //@TODO  Nog even kijken wat we hiermee doen.
                //@TODO, dit geeft voor Groep_PersoonInschrijving een probleem met de attributen vorige- en
                //@TODO  volgende persoon, deze verwijzen namelijk naar Objecttype_Persoon , een mapping die in de
                //@TODO  objecttypen binding
                //@TODO  staat. Groepen binding mag niet verwijzen naar object typen binding. (Andersom wel)
                //@TODO  Mogelijke oplossing: Alle mappings in 1 binding ipv 2.
            } else {
                if (behoortInXsd(attribuut)) {
                    final String elementNaam;
                    String mapAs = null;
                    final String field;
                    String enumValueMethod = null;
                    String serializer = null;
                    String deserializer = null;
                    boolean moetValueMappingZijn = false;
                    if (attribuut.getType().getSoortInhoud() != null
                            &&
                            (BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode()
                                    == attribuut.getType().getSoortInhoud()
                                    || BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode()
                                    == attribuut.getType().getSoortInhoud()))
                    {
                        //Voor stamgegevens mappen we een logische identiteit attribuut. Dit attribuut mappen we
                        //op een String veld in de Java POJO. Dit vanwege vcorloop-nullen. Als we op een attribuuttype
                        //zouden mappen, dan moeten we direct op een Short of Integer mappen en dat gaat niet met String
                        //XSD typen die voorloopnullen kunnen bevatten.
                        moetValueMappingZijn = true;
                        elementNaam = bepaalElementNaamVoorStamgegevenAttribuut(attribuut);
                        final ObjectType stamgegeven = getBmrDao().getElement(
                            attribuut.getType().getId(), ObjectType.class);
                        final Attribuut logischIdentiteitAttr = bepaalLogischeIdentiteitVoorStamgegeven(stamgegeven);

                        //Enum-value-method invullen voor enums.
                        if (BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()) {
                            enumValueMethod = "getCode";
                            //Verwijzing naar het enum veld opnemen.
                            field = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                        } else {
                            //Verwijzing naar het extra logische identiteit veld opnemen van het type String.
                            field = GeneratieUtil.lowerTheFirstCharacter(
                                attribuut.getIdentCode() + logischIdentiteitAttr.getIdentCode());
                        }
                    } else {
                        //Attribuuttype:
                        elementNaam = GeneratieUtil.lowerTheFirstCharacter(
                            attribuut.getIdentCode().replace(groep.getIdentCode(), ""));
                        field = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                        //Uitzondering op attribuuttypen die waarderegels kennen, bijv. Ja en JaNee,
                        //gebruik serializer en deserializers methods in BindingUtil:
                        //Check voor extra waarden
                        final List<WaarderegelWaarde> waardes = getBmrDao().getWaardeEnumeratiesVoorElement(
                            attribuut.getType(), false, true);

                        if (!waardes.isEmpty()) {
                            moetValueMappingZijn = true;
                            serializer = "nl.bzk.brp.model.binding.BindingUtilAttribuutTypen."
                                + GeneratieUtil.lowerTheFirstCharacter(attribuut.getType().getIdentCode())
                                + "NaarXml";
                            deserializer = "nl.bzk.brp.model.binding.BindingUtilAttribuutTypen.xmlNaar"
                                    + attribuut.getType().getIdentCode();
                        } else {
                            mapAs = naamgevingStrategie.getJavaTypeVoorElement(attribuut.getType()).getNaam();
                        }
                    }

                    if (moetValueMappingZijn) {
                        mapping.getChoiceList().add(maakValueChoice(
                            null,
                            elementNaam,
                            field,
                            null,
                            enumValueMethod,
                            null,
                            null,
                            usageOptional,
                            serializer,
                            deserializer));
                    } else {
                        mapping.getChoiceList().add(maakStructureMappingChoice(
                            field,
                            elementNaam,
                            mapAs,
                            nillable,
                            usageOptional));
                    }
                }
            }
        }
        return mapping;
    }

    /**
     * Bouwt de jibx mapping voor de user klasse van een groep.
     *
     * @param groep De groep.
     * @return Jibx mapping voor groep.
     */
    private Mapping bouwUserClassMappingVoorGroep(final Groep groep) {
        final Mapping mapping = new Mapping();
        mapping.setAbstract(true);
        mapping.setTypeName(getTypeNameVoorGroep(groep));
        mapping.set_Class(berichtModelNaamgevingStrategie.getJavaTypeVoorElement(groep).getFullyQualifiedClassName());
        mapping.getChoiceList().add(maakStructureMappingChoice(null, null,
            GenerationGapPatroonJavaKlasseWriter.GENERATIE_KLASSE_NAAM_PREFIX
                + berichtModelNaamgevingStrategie.getJavaTypeVoorElement(groep).getNaam(),
            false, false));
        return mapping;
    }

    /**
     * Maakt de mapping voor de rootobjecten container die in actie zit.
     * @return Mapping voor de rootobjecten.
     */
    private Mapping maakRootObjectenContainerMappingVoorActie() {
        final Mapping mapping = new Mapping();
        mapping.setAbstract(true);
        mapping.setTypeName("Container_RootObjecten");
        mapping.set_Class("java.util.List");
        _Object obj = new _Object();
        obj.setCreateType("java.util.ArrayList");
        mapping.setObject(obj);

        mapping.getChoiceList().add(bouwCollectionChoiceVoorActie("huwelijk", "Objecttype_Huwelijk"));
        mapping.getChoiceList().add(bouwCollectionChoiceVoorActie(
            "geregistreerdPartnerschap", "Objecttype_GeregistreerdPartnerschap"));
        mapping.getChoiceList().add(bouwCollectionChoiceVoorActie(
            "familierechtelijkeBetrekking", "Objecttype_FamilierechtelijkeBetrekking"));
        mapping.getChoiceList().add(bouwCollectionChoiceVoorActie("persoon", "Objecttype_Persoon"));

        return mapping;
    }

    /**
     * Bouwt collection mapping voor actie.
     * @param elementNaam Element naam wat in de collection kan voorkomen.
     * @param mapAs De mapAs string die bij het element hoort.
     * @return Mapping voor collection choice.
     */
    private Mapping.Choice bouwCollectionChoiceVoorActie(final String elementNaam, final String mapAs) {
        final Mapping.Choice collectionChoice = new Mapping.Choice();
        final Collection collection = new Collection();
        final Property collProp = new Property();
        collProp.setUsage("optional");
        collection.setProperty(collProp);
        collectionChoice.setCollection(collection);
        final Collection.Choice huwelijk = maakStructureCollectionChoice(
            elementNaam, mapAs, true, true
        );
        collection.getChoiceList().add(huwelijk);
        return collectionChoice;
    }

}
