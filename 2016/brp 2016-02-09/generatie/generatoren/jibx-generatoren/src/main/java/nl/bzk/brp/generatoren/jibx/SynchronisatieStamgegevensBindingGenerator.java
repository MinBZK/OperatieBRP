/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx;

import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrElementFilterObject;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.AttribuutWrapperNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.jibx.common.JibxBinding;
import nl.bzk.brp.generatoren.jibx.model.Binding;
import nl.bzk.brp.generatoren.jibx.model.Collection;
import nl.bzk.brp.generatoren.jibx.model.Mapping;
import nl.bzk.brp.generatoren.jibx.model.Name;
import nl.bzk.brp.generatoren.jibx.model.Namespace;
import nl.bzk.brp.generatoren.jibx.model.Structure;
import nl.bzk.brp.generatoren.jibx.model._Object;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.WaarderegelWaarde;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Genereert de binding om stamgegevens te synchroniseren.
 */
@Component("synchronisatieStamgegevensJibxBindingGenerator")
public class SynchronisatieStamgegevensBindingGenerator extends AbstractJibxGenerator {

    private static final char STATISCHE_STAM_GEGEVENS = 'S';
    private static final char DYNAMISCHE_STAM_GEGEVENS = 'X';
    private NaamgevingStrategie naamgevingStrategie        = new AlgemeneNaamgevingStrategie();
    private NaamgevingStrategie wrapperNaamgevingStrategie = new AttribuutWrapperNaamgevingStrategie();

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        Binding binding = new Binding();
        binding.setDirection("output");
        binding.setForceClasses(true);
        binding.setPackage("nl.bzk.brp.model.algemeen.stamgegeven");
        binding.setName("synchronisatiestamgegevens");
        final Namespace namespace = new Namespace();
        namespace.setUri("http://www.bzk.nl/brp/brp0200");
        namespace.setPrefix("brp");
        namespace.setDefault("all");
        binding.getNamespaceList().add(namespace);

        voegJibxBindingIncludeToe(binding, JibxBinding.ATTRIBUUT_TYPEN);

        BmrElementFilterObject filter = new BmrElementFilterObject(BmrLaag.LOGISCH);
        filter.setSoortInhoud(STATISCHE_STAM_GEGEVENS);
        final List<ObjectType> stamgegevensObjectTypen = getBmrDao().getObjectTypen(filter);

        filter.setSoortInhoud(DYNAMISCHE_STAM_GEGEVENS);
        stamgegevensObjectTypen.addAll(getBmrDao().getObjectTypen(filter));

        for (ObjectType objectType : stamgegevensObjectTypen) {
            if (objectType.getId() == AbstractGenerator.ID_ELEMENT) {
                //We willen element behandelen als dynamisch stamgegeven, aangezien de enum anders te groot wordt.
                objectType.setSoortInhoud(STATISCHE_STAM_GEGEVENS);
            }

            if (objectType.getInBericht() != null && 'J' == objectType.getInBericht()) {
                // TODO Database object voorlopig uitsluiten zie TEAMBRP-1208
                if (ID_DATABASE_OBJECT != objectType.getId()) {
                    binding.getMappingList().add(genereerContainerMappingVoorStamgegeven(objectType));
                    binding.getMappingList().add(genereerObecttypeMappingVoorStamgegeven(objectType));
                }
            }
        }

        this.jibxWriterFactory(generatorConfiguratie).
            marshallXmlEnSchrijfWeg(JibxBinding.SYNCHRONISATIE_STAMGEGEVENS, binding, this);

    }

    /**
     * Zie methode naam :).
     *
     * @param objectType object type
     * @return mapping
     */
    private Mapping genereerObecttypeMappingVoorStamgegeven(final ObjectType objectType) {
        Mapping mapping = new Mapping();
        mapping.setAbstract(true);
        mapping.set_Class(naamgevingStrategie.getJavaTypeVoorElement(objectType).getFullyQualifiedClassName());
        mapping.setTypeName(bepaalTypeNameVoorStamgegevenObjecttype(objectType));

        //Maak een structure voor de naam van het stamgegeven:
        final Mapping.Choice choice =
            maakStructureMappingChoice(null, null, GeneratieUtil.lowerTheFirstCharacter(objectType.getIdentCode()),
                null, false);

        // Attribuut objectType:
        choice.getStructure().getChoiceList().add(maakValueStructureChoice("attribute",
            ATTR_OBJECTTYPE, null, null,
            null, null, null, null,
            false, objectType.getIdentCode()));

        mapping.getChoiceList().add(choice);

        //Breid de choice uit met de attributen
        final List<Attribuut> attributenVanObjectType = getBmrDao().getAttributenVanObjectType(objectType);
        for (Attribuut attribuut : attributenVanObjectType) {
            if (!isIdAttribuut(attribuut)
                && (attribuut.getInBericht() == null || attribuut.getInBericht() == 'J'))
            {
                // Bij dynamische stamgegevens hebben we de wrapper naamgeving strategie nodig.
                if (isStamgegevenAttribuut(attribuut)) {
                    final ObjectType stamgegevenOt =
                        getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                    if ((attribuut.getInBericht() == null || attribuut.getInBericht() == 'J')
                            || (stamgegevenOt.getInBericht() != null && 'J' == stamgegevenOt.getInBericht())) {
                        final Attribuut liAttr = bepaalLogischeIdentiteitVoorStamgegeven(stamgegevenOt);
                        // Maak eerst een structure met get-method op het stamgegeven attribuut
                        final Structure.Choice stamgegevenChoice =
                            maakStructureStructureChoice(null, "get" + attribuut.getIdentCode(), null, null, true);
                        String name = bepaalNameVoorAttribuut(attribuut) + liAttr.getIdentCode();
                        if (StringUtils.isNotBlank(attribuut.getXsdIdentificatie())) {
                            name = GeneratieUtil.lowerTheFirstCharacter(
                                attribuut.getXsdIdentificatie());
                        }
                        final Structure.Choice stamgegevenLiChoice =
                            maakValueStructureChoice(null,
                                name,
                                null,
                                null,
                                null,
                                null,
                                "get" + liAttr.getIdentCode(),
                                null,
                                true,
                                null);
                        stamgegevenChoice.getStructure().getChoiceList().add(stamgegevenLiChoice);
                        choice.getStructure().getChoiceList().add(stamgegevenChoice);
                    }
                } else {
                    //Uitzondering op attribuuttypen die waarderegels kennen, bijv. Ja en JaNee,
                    //gebruik serializer en deserializers methods in BindingUtil:
                    //Check voor extra waarden
                    final List<WaarderegelWaarde> waardes = getBmrDao().getWaardeEnumeratiesVoorElement(
                        attribuut.getType(), false, true);

                    if (waardes.isEmpty()) {
                        if (isStatischStamgegeven(objectType)) {
                            // Hier hebben we te maken met statische stamgegevens oftewel enumeraties,
                            // die hebben String waarden dus value mappings:
                            final Structure.Choice attribuutChoice =
                                maakStructureValueChoice(
                                    bepaalNameVoorAttribuut(attribuut),
                                    GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                                    true);
                            choice.getStructure().getChoiceList().add(attribuutChoice);
                        } else {
                            // Dynamische stamgegevens hebben wrapper attributen dus structure mapping:
                            final Structure.Choice attribuutChoice = maakStructureStructureChoice(
                                null, "get" + attribuut.getIdentCode(), GeneratieUtil.lowerTheFirstCharacter(
                                    attribuut.getIdentCode()),
                                wrapperNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType()).getNaam(),
                                true);
                            choice.getStructure().getChoiceList().add(attribuutChoice);
                        }
                    } else {
                        final String serializer = "nl.bzk.brp.model.binding.BindingUtilAttribuutTypen."
                            + GeneratieUtil.lowerTheFirstCharacter(attribuut.getType().getIdentCode())
                            + "NaarXml";
                        Structure.Choice attribuutWrapperChoice = maakStructureStructureChoice(
                            null, "get" + attribuut.getIdentCode(), null, null, true);

                        attribuutWrapperChoice.getStructure().getChoiceList().add(maakValueStructureChoice(
                            null, bepaalNameVoorAttribuut(attribuut), null, null, null, null,
                            "getWaarde", null, true, serializer, null));
                        choice.getStructure().getChoiceList().add(attribuutWrapperChoice);
                    }
                }
            }
        }

        // Genereer voor bestaansperiode stamgegevens datumAanvangGeldigheid en datumEindeGeldigheid structures.
        if (isBestaansPeriodeStamgegeven(objectType)) {
            choice.getStructure().getChoiceList().add(
                maakStructureStructureChoice(null,
                    "getDatumAanvangGeldigheid",
                    "datumAanvangGeldigheid",
                    "DatumAttribuut",
                    true));

            choice.getStructure().getChoiceList().add(
                maakStructureStructureChoice(null,
                    "getDatumEindeGeldigheid",
                    "datumEindeGeldigheid",
                    "DatumAttribuut",
                    true));
        }
        return mapping;
    }

    /**
     * Bepaal het name attribute voor een attribuut.
     *
     * @param attribuut attribuut
     * @return name van een asstribuut.
     */
    private String bepaalNameVoorAttribuut(final Attribuut attribuut) {
        String name = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
        if (StringUtils.isNotBlank(attribuut.getXsdIdentificatie())) {
            name = GeneratieUtil.lowerTheFirstCharacter(
                attribuut.getXsdIdentificatie());
        }
        return name;
    }

    /**
     * Zie methode naam :).
     *
     * @param objectType object type
     * @return mapping
     */
    private Mapping genereerContainerMappingVoorStamgegeven(final ObjectType objectType) {
        //Maak de mapping aan op de Lijst.
        Mapping mapping = new Mapping();
        mapping.setAbstract(true);
        mapping.setTypeName(bepaalTypeNameVoorStamgegevenContainerMapping(objectType));
        mapping.set_Class(JavaType.LIST.getFullyQualifiedClassName());
        _Object obj = new _Object();
        mapping.setObject(obj);

        //Maak een collection choice aan voor de stamgegevens in de lijst.
        final Mapping.Choice collectionChoice = new Mapping.Choice();
        final Collection collection = new Collection();
        collection.setName(new Name());
        collection.getName().setName(GeneratieUtil.lowerTheFirstCharacter(objectType.getIdentCode()) + "Tabel");
        collectionChoice.setCollection(collection);

        final Collection.Choice collectieItemChoice = maakStructureCollectionChoice(
            null, bepaalTypeNameVoorStamgegevenObjecttype(objectType), true);
        collection.getChoiceList().add(collectieItemChoice);
        mapping.getChoiceList().add(collectionChoice);
        return mapping;
    }

    /**
     * Zie methode naam :).
     *
     * @param objectType object type
     * @return mapping
     */
    private String bepaalTypeNameVoorStamgegevenObjecttype(final ObjectType objectType) {
        return "Objecttype_" + objectType.getIdentCode() + "_Levering";
    }

    /**
     * Zie methode naam :).
     *
     * @param objectType object type
     * @return mapping
     */
    private String bepaalTypeNameVoorStamgegevenContainerMapping(final ObjectType objectType) {
        return "Container_" + objectType.getIdentCode() + "_Levering";
    }
}
