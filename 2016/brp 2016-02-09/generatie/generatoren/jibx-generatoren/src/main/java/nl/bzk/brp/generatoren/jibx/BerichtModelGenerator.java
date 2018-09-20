/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx;

import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrElementFilterObject;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.naamgeving.AttribuutWrapperNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.BerichtModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.writer.GenerationGapPatroonJavaKlasseWriter;
import nl.bzk.brp.generatoren.jibx.common.JibxBinding;
import nl.bzk.brp.generatoren.jibx.model.Binding;
import nl.bzk.brp.generatoren.jibx.model.Collection;
import nl.bzk.brp.generatoren.jibx.model.Mapping;
import nl.bzk.brp.generatoren.jibx.model.Property;
import nl.bzk.brp.generatoren.jibx.model.Structure;
import nl.bzk.brp.generatoren.jibx.model.Structure1;
import nl.bzk.brp.generatoren.jibx.model._Object;
import nl.bzk.brp.generatoren.xsd.util.XsdNaamgevingUtil;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import nl.bzk.brp.metaregister.model.WaarderegelWaarde;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static nl.bzk.brp.generatoren.jibx.model.Mapping.Choice;

/**
 * Genereert Binding tussen het Java berichten model en de XSD.
 */
@Component("berichtModelJibxGenerator")
public class BerichtModelGenerator extends AbstractJibxGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(BerichtModelGenerator.class);

	private final BerichtModelNaamgevingStrategie berichtModelNaamgevingStrategie =
			new BerichtModelNaamgevingStrategie();
	private NaamgevingStrategie naamgevingStrategie =
			new AttribuutWrapperNaamgevingStrategie();
	private static final String JIBX_POSTSET_FUNCTIE_STANDAARDGROEPEN = "jibxPostSetCommunicatieId";

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
					&& 'J' == objectType.getXsdViewsPerDiscriminatorWaarde()) {
				final List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType);
				for (Groep groep : groepenVoorObjectType) {
					if (IDENTITEIT.equals(groep.getNaam())) {
						final List<Attribuut> attributenVanGroep = getBmrDao().getAttributenVanGroep(groep);
						for (Attribuut attribuut : attributenVanGroep) {
							//Tuples behoren enkel tot statische stamgegevens
							if (attribuut.getType().getSoortInhoud() != null
									&& BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode()
									== attribuut.getType().getSoortInhoud()) {
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
	 *
	 * @param objectType Objecttype dat een attribuut kent van het type objecttype dat ouder is van deze tuple.
	 * @param tuple      De tuple.
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
						null,
						getTypeNameVoorObjectType(objectType),
						true));
		return mapping;
	}

	/**
	 * Bouwt een mapping voor de user klasse dat hoort bij een BMR Tuple.
	 *
	 * @param objectType Objecttype dat een attribuut kent van het type objecttype dat ouder is van deze tuple.
	 * @param tuple      De tuple.
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
						null,
						"Abstract" + objectType.getXsdViewPrefix() + "_"
								+ berichtModelNaamgevingStrategie.getJavaTypeVoorElement(tuple).getNaam(),
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
		final List<Mapping> mappings = new ArrayList<>();

		for (Attribuut invAttribuut : inverseAssociatieAttributen) {
			final boolean isAdministratieveHandelingActiesUitzondering =
					ID_ADMHND_LGM == objectType.getId() && ID_ACTIE_LGM == invAttribuut.getObjectType().getId();

			final Mapping mapping = new Mapping();
			mapping.setAbstract(true);

			final String identCode;
			if (invAttribuut.getXsdInverseAssociatieIdentCode() != null && !invAttribuut.getXsdInverseAssociatieIdentCode().equals("")) {
				identCode = invAttribuut.getXsdInverseAssociatieIdentCode();
			}
			else if (invAttribuut.getInverseAssociatieIdentCode() != null && !invAttribuut.getInverseAssociatieIdentCode().equals("")) {
				identCode = invAttribuut.getInverseAssociatieIdentCode();
			}
			else {
				LOGGER.error("De inverse associatie dient een identcode te hebben voor de naamgeving van de container voor inverse attribuut: {} "
						+ "en objecttype: {}.", invAttribuut.getNaam(), objectType.getNaam());
				identCode = "ONBEKEND";
			}
			mapping.setTypeName("Container_" + objectType.getIdentCode() + identCode);

			mapping.set_Class(JavaType.LIST.getFullyQualifiedClassName());
			_Object obj = new _Object();
			obj.setCreateType(JavaType.ARRAY_LIST.getFullyQualifiedClassName());
			mapping.setObject(obj);
			mappings.add(mapping);

			if (isAdministratieveHandelingActiesUitzondering
					|| invAttribuut.getObjectType().getXsdViewsPerDiscriminatorWaarde() != null
					&& invAttribuut.getObjectType().getXsdViewsPerDiscriminatorWaarde() == 'J') {
				final ObjectType discriminatorObjectType =
						getBmrDao().getDiscriminatorObjectType(invAttribuut.getObjectType());
				final Choice structure = maakStructureMappingChoice(null, null, null, null, false);
				//De acties zijn herhaalbaar en hoeven niet in een bepaalde volgorde te worden ge-bind.
				final Structure struct = new Structure();
				struct.setStructure(new Structure1());
				struct.getStructure().setAllowRepeats(true);
				struct.getStructure().setOrdered(false);
				structure.setStructure(struct);
				mapping.getChoiceList().add(structure);
				for (Tuple tuple : discriminatorObjectType.getTuples()) {
					//MapAs moet een verwijzing zijn naar de specifieke mapping voor deze tuple.
					String prefix;
					if (StringUtils.isNotBlank(invAttribuut.getObjectType().getXsdViewPrefix())) {
						prefix = invAttribuut.getObjectType().getXsdViewPrefix();
					}
					else {
						prefix = invAttribuut.getObjectType().getIdentCode();
					}
					final String mapAs = prefix + "_" + tuple.getIdentCode();
					final Structure.Choice collectionChoice = maakStructureChoiceCollectie(true);
					String naam = tuple.getIdentCode();
					//Als de naam van de tuple in het BMR een vraagteken bevat dan wordt het woordje Indicatie
					//toegevoegd aan de ident_code. In de XML hoort dit woordje er weer niet bij dus moeten
					//we het hier weghalen.
					if (tuple.getNaam().contains("?")) {
						naam = naam.replace("Indicatie", "");
					}
					voegChoiceToeAanStructureChoiceCollectie(collectionChoice, naam, mapAs, false);
					struct.getChoiceList().add(collectionChoice);
				}
			}
			else if (isKoppelingObjectType(invAttribuut.getObjectType())) {
				// Specifiek geval voor een koppeling object type die naar 1 of meer
				// andere object typen kan verwijzen (al dan niet stamgegevens)
				final List<ObjectType> naarObjectTypen = bepaalGekoppeldeObjectTypen(invAttribuut);
				for (ObjectType naarType : naarObjectTypen) {
					final boolean naarObjectTypeIsStamgegeven =
							BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode() == naarType.getSoortInhoud()
									|| BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode() == naarType.getSoortInhoud();

					final Choice collectionChoice = maakMappingChoiceCollectie(true);
					if (naarObjectTypeIsStamgegeven) {
						final Attribuut logischeIdAttr = bepaalLogischeIdentiteitVoorStamgegeven(naarType);
						final JavaType javaType = berichtModelNaamgevingStrategie.getJavaTypeVoorElement(
								invAttribuut.getObjectType());
						collectionChoice.getCollection().setItemType(javaType.getPackagePad()
								+ ".basis.Abstract" + javaType.getNaam());
						final Collection.Choice choice = maakStructureCollectionChoice(
								null, null, true
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
						collectionChoice.getCollection().getChoiceList().add(choice);
					}
					else {
						voegChoiceToeAanMappingChoiceCollectie(collectionChoice, naarType.getIdentCode(),
								invAttribuut.getObjectType(), true);
					}
					mapping.getChoiceList().add(collectionChoice);
				}
			}
			else if (invAttribuut.getObjectType().getSubtypen().isEmpty()) {
				String naam = invAttribuut.getObjectType().getIdentCode().replace(objectType.getNaam(), "");
				if ("AdministratieveHandelingBron".equals(invAttribuut.getObjectType().getIdentCode())) {
					naam = "bron";
				}
				final Choice collectionChoice = maakMappingChoiceCollectie(true);
				voegChoiceToeAanMappingChoiceCollectie(collectionChoice, naam, invAttribuut.getObjectType(), true);
				mapping.getChoiceList().add(collectionChoice);
			}
			else {
				//Er is sprake van subtypering, maak voor elke subtype een collection.
				final List<ObjectType> subtypen = verzamelFinaleSubtypen(invAttribuut.getObjectType());
				for (ObjectType subtype : subtypen) {
					final Choice collectionChoice = maakMappingChoiceCollectie(true);
					voegChoiceToeAanMappingChoiceCollectie(collectionChoice, subtype.getIdentCode(), subtype, true);
					mapping.getChoiceList().add(collectionChoice);
				}
			}
		}
		return mappings;
	}

	/**
	 * Voegt een choice toe aan een collectie van choices, waarbij de choice de opgegeven naam krijgt.
	 *
	 * @param choiceCollectie de choice collectie structure waar de choice aan toegevoegd dient te worden.
	 * @param naam            de naam van het choice element.
	 * @param objectType      het objecttype waarnaar de choice verwijst.
	 * @param optioneel       of de choice optioneel is of niet.
	 */
	private void voegChoiceToeAanMappingChoiceCollectie(final Choice choiceCollectie, final String naam,
														final ObjectType objectType, final boolean optioneel) {
		voegChoiceToeAanMappingChoiceCollectie(choiceCollectie, naam, getTypeNameVoorObjectType(objectType), optioneel);
	}

	/**
	 * Voegt een choice toe aan een collectie van choices, waarbij de choice de opgegeven naam krijgt.
	 *
	 * @param choiceCollectie de choice collectie structure waar de choice aan toegevoegd dient te worden.
	 * @param naam            de naam van het choice element.
	 * @param mapAs           de naam van het objecttype waarnaar de choice verwijst.
	 * @param optioneel       of de choice optioneel is of niet.
	 */
	private void voegChoiceToeAanMappingChoiceCollectie(final Choice choiceCollectie, final String naam,
														final String mapAs, final boolean optioneel) {
		final Collection.Choice choice = maakStructureCollectionChoice(GeneratieUtil.lowerTheFirstCharacter(naam),
				mapAs, optioneel);
		choiceCollectie.getCollection().getChoiceList().add(choice);
	}

	/**
	 * Voegt een choice toe aan een collectie van choices, waarbij de choice de opgegeven naam krijgt.
	 *
	 * @param choiceCollectie de choice collectie structure waar de choice aan toegevoegd dient te worden.
	 * @param naam            de naam van het choice element.
	 * @param mapAs           de naam van het objecttype waarnaar de choice verwijst.
	 * @param optioneel       of de choice optioneel is of niet.
	 */
	private void voegChoiceToeAanStructureChoiceCollectie(final Structure.Choice choiceCollectie, final String naam,
														  final String mapAs, final boolean optioneel) {
		final Collection.Choice choice = maakStructureCollectionChoice(GeneratieUtil.lowerTheFirstCharacter(naam),
				mapAs, optioneel);
		choiceCollectie.getCollection().getChoiceList().add(choice);
	}

	/**
	 * Maakt en retourneert een XML Choice collectie element met een reeds (leeg) geinitialiseerde collectie van
	 * choices.
	 *
	 * @param isOptioneel of de choice collectie optioneel is of niet.
	 * @return de aangemaakte choice collectie.
	 */
	private Structure.Choice maakStructureChoiceCollectie(final boolean isOptioneel) {
		final Structure.Choice collectionChoice = new Structure.Choice();
		final Collection collection = new Collection();
		final Property collProp = new Property();
		if (isOptioneel) {
			collProp.setUsage("optional");
		}
		collection.setProperty(collProp);
		collectionChoice.setCollection(collection);
		return collectionChoice;
	}

	/**
	 * Maakt en retourneert een XML Choice collectie element met een reeds (leeg) geinitialiseerde collectie van
	 * choices.
	 *
	 * @param isOptioneel of de choice collectie optioneel is of niet.
	 * @return de aangemaakte choice collectie.
	 */
	private Choice maakMappingChoiceCollectie(final boolean isOptioneel) {
		final Choice collectionChoice = new Choice();
		final Collection collection = new Collection();
		final Property collProp = new Property();
		if (isOptioneel) {
			collProp.setUsage("optional");
		}
		collection.setProperty(collProp);
		collectionChoice.setCollection(collection);
		return collectionChoice;
	}

	/**
	 * Bouwt jibx mapping voor een gegenereerde object type klasse.
	 *
	 * @param objectType Het object type.
	 * @return Jibx mapping voor object type.
	 */
	private Mapping bouwGeneratedClassMappingVoorObjectType(final ObjectType objectType) {
		final Mapping mapping = new Mapping();
		final boolean isAdministratieveHandelingObjectType = ID_ADMHND_LGM == objectType.getId();
		mapping.setAbstract(true);
		mapping.setTypeName(GenerationGapPatroonJavaKlasseWriter.GENERATIE_KLASSE_NAAM_PREFIX
				+ berichtModelNaamgevingStrategie.getJavaTypeVoorElement(objectType).getNaam());

		mapping.set_Class(berichtModelNaamgevingStrategie.getJavaTypeVoorElement(objectType).getPackagePad()
				+ GenerationGapPatroonJavaKlasseWriter.GENERATIE_TYPE_SUB_PACKAGE
				+ "."
				+ GenerationGapPatroonJavaKlasseWriter.GENERATIE_KLASSE_NAAM_PREFIX
				+ berichtModelNaamgevingStrategie.getJavaTypeVoorElement(objectType).getNaam());

		//Alleen objecttype bericht is niet identificeerbaar. Verder moet de structure verwijzing naar
		//ObjectTypeIdentificeerbaar in de finale subtypen en NIET in supertypen.
		//Koppeling objecttypen moeten ook niet de verwijzing naar ObjectTypeIdentificeerbaar krijgen.
		if (isFinaalSubtype(objectType)
				||
				(ID_BERICHT_LGM != objectType.getId()
						&& !isSupertype(objectType))
						&& !isKoppelingObjectType(objectType)) {
			mapping.getChoiceList().add(
					maakStructureMappingChoice(null, null, null, "ObjectTypeIdentificeerbaar", false));
		}

		if (objectType.getSuperType() != null) {
			//Dit is een subtype, maak een structure aan dat verwijst naar de mappping van het supertype. (mapAs)
			mapping.getChoiceList().add(
					maakStructureMappingChoice(
							null,
							null,
							null,
							getTypeNameVoorObjectType(objectType.getSuperType()),
							false));
		}

		final List<Pair<String, GeneriekElement>> teGenererenMappings = new ArrayList<>();
		final List<Groep> groepen = getBmrDao().getGroepenVoorObjectType(objectType);
		final List<Attribuut> inverseAssociatieAttributen =
				getBmrDao().getXsdInverseAttributenVoorObjectType(objectType);

		//Voeg de groepen toe.
		for (Groep groep : groepen) {
			teGenererenMappings.add(new ImmutablePair<String, GeneriekElement>(groep.getIdentCode(), groep));
		}

		//Voeg de inverse attributen toe.
		for (Attribuut attribuut : inverseAssociatieAttributen) {
			teGenererenMappings.add(
					new ImmutablePair<String, GeneriekElement>(attribuut.getXsdInverseAssociatieIdentCode(), attribuut));
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
						}
						else {
							bouwStructureVoorGroep(mapping, groep);
						}
					}
				}
				else if (teGenererenMapping.getValue() instanceof Attribuut) {
					final Attribuut attr = (Attribuut) teGenererenMapping.getValue();
					//Voeg inverse associaties toe aan de mapping.
					bouwInverseAssociatiesInMapping(objectType, attr, mapping);
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
							&& StringUtils.isBlank(attribuut.getXsdInverseAssociatieIdentCode())) {
						final ObjectType ot =
								getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
						if (BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == ot.getSoortInhoud()) {
							mapping.getChoiceList().add(
									maakStructureMappingChoice(
											GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
											//Element naam moet null zijn want deze is opgenomen in de
											//container mapping.
											null,
											null,
											"Objecttype_" + ot.getIdentCode(),
											true
									)
							);
						}
						else {
							//Stamgegeven moet via een logische identeit attribuut worden ge-mapped. Omdat het type
							//van deze attributen altijd een String is hebben we een value choice nodig.
							final Attribuut logischIdentiteitAttr;
							logischIdentiteitAttr = bepaalLogischeIdentiteitVoorStamgegeven(ot);
							String name = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
							String field = GeneratieUtil.lowerTheFirstCharacter(
									attribuut.getIdentCode() + logischIdentiteitAttr.getIdentCode());

							mapping.getChoiceList().add(
									maakValueMappingChoice(
											null,
											name,
											field,
											null,
											//Element naam moet null zijn want deze is opgenomen in de
											//container mapping.
											null,
											null,
											null,
											null,
											true,
											null
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
	 * @param elementen    elementen.
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
	 * @param groep   De groep waarvoor een structure wordt aangemaakt.
	 */
	private void bouwStructureVoorGroep(final Mapping mapping, final Groep groep) {
		boolean optional = true;

		if (isElementVerplichtInXsd(groep)) {
			optional = false;
		}

		final String xmlElementNaam;
		if (behoortInXsdAlsLosStaandType(groep)) {
			xmlElementNaam = GeneratieUtil.lowerTheFirstCharacter(bepaalGroepsNaamVoorXsd(groep));
		}
		else {
			//Standaard groep wordt plat geslagen in de xsd. Dus geen element naam.
			xmlElementNaam = null;
		}

		//Structure aanmaken voor groep.
		mapping.getChoiceList().add(
				maakStructureMappingChoice(
						GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode()),
						null,
						xmlElementNaam,
						getTypeNameVoorGroep(groep),
						optional));
	}

	/**
	 * Voegt aan de mapping structures toe voor het binden van de attributen van de identiteit groep.
	 *
	 * @param mapping De mapping waaraan structures moeten worden toegevoegd.
	 * @param groep   De identiteit groep.
	 */
	private void bouwStructuresVoorAttributenIdentiteitGroep(final Mapping mapping, final Groep groep) {
		final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
		for (Attribuut attribuut : attributen) {
			final String elementNaam;
			String mapAs = null;
			final String field;
			String getter = null;
			String setter = null;
			String enumValueMethod = null;
			boolean heeftStructureWrapping = false;
			boolean heeftValueMapping = false;

			//We doen voor Element net alsof het een dynamisch stamgegeven is. Dit is omdat
			//de tabel te groot is om er een Enum van te genereren....
			if (attribuut.getType().getId() == AbstractGenerator.ID_ELEMENT) {
				attribuut.getType().setSoortInhoud(BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode());
			}

			if (behoortInXsd(attribuut)) {
				boolean optional = true;

				if (isElementVerplichtInXsd(attribuut)) {
					optional = false;
				}

				if (attribuut.getType().getSoortInhoud() != null
						&& BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == attribuut.getType().getSoortInhoud()) {
					//Haal het object type op
					final ObjectType ot = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
					if (!ot.getSubtypen().isEmpty()) {
						//Maak de binding voor de choice:
						bouwbindingVoorChoice(mapping, ot);
						continue;
					}
					else {
						// In dit geval is de element naam afleiding gelijk aan die voor een attribuut type.
						elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorAttribuutTypeAttribuut(attribuut);
						field = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
						mapAs = "Objecttype_" + attribuut.getType().getIdentCode();
					}
				}
				else if (attribuut.getType().getSoortInhoud() != null
						&& (BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()
						|| BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud())) {
					final ObjectType stamgegeven = getBmrDao().getElement(
							attribuut.getType().getId(), ObjectType.class);
					final Attribuut logischIdentiteitAttr = bepaalLogischeIdentiteitVoorStamgegeven(stamgegeven);
					elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorStamgegevenAttribuut(attribuut,
							logischIdentiteitAttr);

					if (BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()) {
						heeftStructureWrapping = true;
						getter = "getWaarde";
						setter = "setWaarde";
						field = GeneratieUtil.lowerTheFirstCharacter(
								attribuut.getIdentCode());
						if (logischIdentiteitAttr.getNaam().equals("Code")) {
							enumValueMethod = "getCode";
						}
						else if (logischIdentiteitAttr.getNaam().equals("Naam")) {
							enumValueMethod = "getNaam";
						}
						else {
							throw new GeneratorExceptie("JiBX generator ondersteunt geen logische "
									+ "identiteit attributen met een naam anders"
									+ "dan 'Naam' of 'Code'.");
						}
					}
					else {
						heeftValueMapping = true;
						field = GeneratieUtil.lowerTheFirstCharacter(
								attribuut.getIdentCode() + logischIdentiteitAttr.getIdentCode());
					}
				}
				else {
					elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorAttribuutTypeAttribuut(attribuut);
					mapAs = naamgevingStrategie.getJavaTypeVoorElement(attribuut.getType()).getNaam();
					field = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
				}

				//Indien er sprake is van views per discriminator waarde, dan moeten we de structure voor elke
				//view herhalen.
				if (attribuut.getType().getXsdViewsPerDiscriminatorWaarde() != null
						&& 'J' == attribuut.getType().getXsdViewsPerDiscriminatorWaarde()) {
					final ObjectType ot = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
					final ObjectType discriminatorObjectType = getBmrDao().getDiscriminatorObjectType(ot);
					for (Tuple tuple : discriminatorObjectType.getTuples()) {

						//Maak een aparte structure aan voor de naam van het element dat bij deze tuple hoort.
						Choice choice = maakStructureMappingChoice(
								null,
								null,
								GeneratieUtil.lowerTheFirstCharacter(tuple.getIdentCode()),
								null,
								true
						);

						//Maak in de structure een structure mapping aan naar de basis mapping voor deze tuple.
						final Structure.Choice structStructChoice = maakStructureStructureChoice(
								field,
								null,
								null,
								ot.getXsdViewPrefix() + "_" + tuple.getIdentCode(),
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
				}
				else {
					if (heeftStructureWrapping) {
						Choice choice = maakStructureMappingChoice(
								field, null, null, null, optional);
						choice.getStructure().getChoiceList().add(maakValueStructureChoice(
								null, elementNaam, null, null, enumValueMethod, null, getter, setter, false, null));
						mapping.getChoiceList().add(choice);
					}
					else if (heeftValueMapping) {
						mapping.getChoiceList().add(maakValueMappingChoice(
								null, elementNaam, field, null, enumValueMethod, null, getter, setter, optional, null));
					}
					else {
						mapping.getChoiceList().add(maakStructureMappingChoice(
								field,
								null,
								elementNaam,
								mapAs,
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
	 * @param mapping   De mapping waar de choice subtypering in geimplementeerd moet worden.
	 * @param superType Super type.
	 */
	private void bouwbindingVoorChoice(final Mapping mapping, final ObjectType superType) {
		final String field = GeneratieUtil.lowerTheFirstCharacter(superType.getIdentCode());
		for (ObjectType subType : superType.getSubtypen()) {
			if (!subType.getSubtypen().isEmpty()) {
				for (ObjectType subSubType : subType.getSubtypen()) {
					bouwHierarchieMapping(mapping, field, subSubType);
				}
			}
			else {
				bouwHierarchieMapping(mapping, field, subType);
			}
		}
	}

	/**
	 * Bouw de mapping van een attribuut van een hierarchische type.
	 * Hierbij wordt een extra structure wrapper om de structure met het field gezet,
	 * zodat de jibx binding het aankan dat er meerdere xml elementen naar hetzelfde field kunnen mappen.
	 *
	 * @param mapping de mapping
	 * @param field   het veld
	 * @param subType het subtype
	 */
	private void bouwHierarchieMapping(final Mapping mapping, final String field, final ObjectType subType) {
		final String elementNaam = GeneratieUtil.lowerTheFirstCharacter(subType.getIdentCode());
		final String mapAs = "Objecttype_" + subType.getIdentCode();
		String type = berichtModelNaamgevingStrategie.getJavaTypeVoorElement(subType).getFullyQualifiedClassName();
		Choice mappingChoice = maakStructureMappingChoice(null, null, elementNaam, null, true);
		mapping.getChoiceList().add(mappingChoice);
		Structure.Choice structureChoice = maakStructureStructureChoice(field, null, null, mapAs, true);
		structureChoice.getStructure().getProperty().setType(type);
		mappingChoice.getStructure().getChoiceList().add(structureChoice);
	}

	/**
	 * Voegt structures aan de mapping toe voor alle inverse associatie van object type.
	 *
	 * @param objectType Het object type dat inverse associaties kent.
	 * @param attribuut  Het attribuut.
	 * @param mapping    De mapping waaraan structures moeten worden toegevoegd.
	 */
	private void bouwInverseAssociatiesInMapping(final ObjectType objectType, final Attribuut attribuut, final Mapping mapping) {
		final String elementNaam =
				GeneratieUtil.lowerTheFirstCharacter(attribuut.getXsdInverseAssociatieIdentCode());
		final String mapAs =
				"Container_" + objectType.getIdentCode() + attribuut.getXsdInverseAssociatieIdentCode();

		final String field;
		if (attribuut.getInverseAssociatieIdentCode() != null && !attribuut.getInverseAssociatieIdentCode().equals("")) {
			field = GeneratieUtil.lowerTheFirstCharacter(attribuut.getInverseAssociatieIdentCode());
		}
		else if (attribuut.getXsdInverseAssociatieIdentCode() != null && !attribuut.getXsdInverseAssociatieIdentCode().equals("")) {
			field = GeneratieUtil.lowerTheFirstCharacter(attribuut.getXsdInverseAssociatieIdentCode());
		}
		else {
			field = null;
		}
		mapping.getChoiceList().add(maakStructureMappingChoice(field, null, elementNaam, mapAs, true));
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
				null, null, null, GenerationGapPatroonJavaKlasseWriter.GENERATIE_KLASSE_NAAM_PREFIX
						+ berichtModelNaamgevingStrategie.getJavaTypeVoorElement(objectType).getNaam(), false));

		if (ID_ACTIE_LGM == objectType.getId()) {
			//HACK: Actie kent root objecten. Maak een mapping aan naar deze collectie.
			mapping.getChoiceList().add(
					maakStructureMappingChoice("rootObjecten", null, null, "Container_RootObjecten", true)
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
					|| behoortInXsdOnderEenObjectType(groep) && STANDAARD.equals(groep.getNaam())) {
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

		//Alleen groepen die los voorkomen in de XSD kunnen Identificeerbaar zijn.
		if (behoortInXsdAlsLosStaandType(groep)) {
			mapping.getChoiceList().add(maakStructureMappingChoice(null, null, null, "GroepIdentificeerbaar", true));
		}
		// Voeg een post-set methode toe aan alle materieel historische groepen om zo de DAG en DEG te vullen.
		if (minstensEenGroepKentMaterieleHistorie(Arrays.asList(groep))) {
			if (mapping.getObject() == null) {
				mapping.setObject(new _Object());
			}
			mapping.getObject().setPostSet("zetMaterieleHistorieDatums");
		}

		final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
		for (Attribuut attribuut : attributen) {
			// Zoals in: MinOccurs = 0
			boolean optional = true;

			if (this.isElementVerplichtInXsd(attribuut)) {
				// Zoals in: MinOccurs = 1
				optional = false;
			}

			if (attribuut.getType().getSoortInhoud() != null
					&& BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == attribuut.getType().getSoortInhoud()) {

				//Indien er sprake is van views per discriminator waarde voor het objecttype, dan moeten we de structure voor elke
				//view herhalen.
				if (attribuut.getType().getXsdViewsPerDiscriminatorWaarde() != null
						&& 'J' == attribuut.getType().getXsdViewsPerDiscriminatorWaarde()) {
					final ObjectType ot = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
					final ObjectType discriminatorObjectType = getBmrDao().getDiscriminatorObjectType(ot);
					for (Tuple tuple : discriminatorObjectType.getTuples()) {

						//Maak een aparte structure aan voor de naam van het element dat bij deze tuple hoort.
						Choice choice = maakStructureMappingChoice(
								null,
								null,
								GeneratieUtil.lowerTheFirstCharacter(tuple.getIdentCode()),
								null,
								true
						);

						//Maak in de structure een structure mapping aan naar de basis mapping voor deze tuple.
						final Structure.Choice structStructChoice = maakStructureStructureChoice(
								GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
								null,
								null,
								ot.getXsdViewPrefix() + "_" + tuple.getIdentCode(),
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
				}
				else {
					//@TODO Dynamische objecttype attribuut zonder view per discriminator waarde in een groep.
					//@TODO Nog niet duidelijk wat we in Jibx hiermee doen.
					LOGGER.warn(String.format("Nog niet gedefineerde functionaliteit in Jibx generator voor "
									+ "groepsbinding met attribuut '%s' in groep '%s'.",
							attribuut.getNaam(), groep.getNaam()));
				}
			}
			else {
				if (behoortInXsd(attribuut)) {
					final String elementNaam;
					String mapAs = null;
					final String field;
					String enumValueMethod = null;
					String getter = null;
					String setter = null;
					String serializer = null;
					String deserializer = null;
					boolean heeftStructureWrapping = false;
					boolean heeftValueMapping = false;
					if (attribuut.getType().getSoortInhoud() != null
							&&
							(BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode()
									== attribuut.getType().getSoortInhoud()
									|| BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode()
									== attribuut.getType().getSoortInhoud())) {
						//Voor stamgegevens mappen we een logische identiteit attribuut. Dit attribuut mappen we
						//op een String veld in de Java POJO. Dit vanwege voorloop-nullen. Als we op een attribuuttype
						//zouden mappen, dan moeten we direct op een Short of Integer mappen en dat gaat niet met String
						//XSD typen die voorloopnullen kunnen bevatten.
						final ObjectType stamgegeven = getBmrDao().getElement(
								attribuut.getType().getId(), ObjectType.class);
						final Attribuut logischIdentiteitAttr = bepaalLogischeIdentiteitVoorStamgegeven(stamgegeven);
						elementNaam = XsdNaamgevingUtil.bepaalElementNaamVoorStamgegevenAttribuut(attribuut,
								logischIdentiteitAttr);

						//Enum-value-method invullen voor enums.
						if (BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()) {
							if (logischIdentiteitAttr.getNaam().equals("Code")) {
								enumValueMethod = "getCode";
							}
							else if (logischIdentiteitAttr.getNaam().equals("Naam")) {
								enumValueMethod = "getNaam";
							}
							else {
								throw new GeneratorExceptie("JiBX generator ondersteunt geen logische "
										+ "identiteit attributen met een naam anders"
										+ "dan 'Naam' of 'Code'.");
							}
							//Verwijzing naar het enum veld opnemen.
							field = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
							heeftStructureWrapping = true;
							getter = "getWaarde";
							setter = "setWaarde";
						}
						else {
							heeftValueMapping = true;
							//Verwijzing naar het extra logische identiteit veld opnemen van het type String.
							field = GeneratieUtil.lowerTheFirstCharacter(
									attribuut.getIdentCode() + logischIdentiteitAttr.getIdentCode());
						}
					}
					else {
						//Attribuuttype:
						if (StringUtils.isNotBlank(attribuut.getXsdIdentificatie())) {
							elementNaam = GeneratieUtil.lowerTheFirstCharacter(
									attribuut.getXsdIdentificatie());
						}
						else {
							elementNaam = GeneratieUtil.lowerTheFirstCharacter(
									attribuut.getIdentCode().replace(groep.getIdentCode(), ""));
						}
						field = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
						//Uitzondering op attribuuttypen die waarderegels kennen, bijv. Ja en JaNee,
						//gebruik serializer en deserializers methods in BindingUtil:
						//Check voor extra waarden
						final List<WaarderegelWaarde> waardes = getBmrDao().getWaardeEnumeratiesVoorElement(
								attribuut.getType(), false, true);

						if (!waardes.isEmpty()) {
							heeftStructureWrapping = true;
							getter = "getWaarde";
							setter = "setWaarde";
							serializer = "nl.bzk.brp.model.binding.BindingUtilAttribuutTypen."
									+ GeneratieUtil.lowerTheFirstCharacter(attribuut.getType().getIdentCode())
									+ "NaarXml";
							deserializer = "nl.bzk.brp.model.binding.BindingUtilAttribuutTypen.xmlNaar"
									+ attribuut.getType().getIdentCode();
						}
						else {
							mapAs = naamgevingStrategie.getJavaTypeVoorElement(attribuut.getType()).getNaam();
						}
					}

					if (heeftStructureWrapping) {
						Choice choice = maakStructureMappingChoice(
								field, null, null, null, optional);
						choice.getStructure().getChoiceList().add(maakValueStructureChoice(
								null, elementNaam, null, null, enumValueMethod, null,
								getter, setter, false, serializer, deserializer));
						mapping.getChoiceList().add(choice);
					}
					else if (heeftValueMapping) {
						mapping.getChoiceList().add(maakValueMappingChoice(
								null, elementNaam, field, null, enumValueMethod, null,
								getter, setter, optional, serializer, deserializer));
					}
					else {
						mapping.getChoiceList().add(maakStructureMappingChoice(
								field,
								null,
								elementNaam,
								mapAs,
								optional));
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

		//Bij een standaard groep roepen we een post-set functie aan op de user class om een communicatie-id te
		//registreren in de Communicatie-ids map. (Zie code)
		if (isStandaardGroep(groep)) {
			mapping.setObject(new _Object());
			mapping.getObject().setPostSet(JIBX_POSTSET_FUNCTIE_STANDAARDGROEPEN);
		}
		mapping.getChoiceList().add(maakStructureMappingChoice(null, null, null,
				GenerationGapPatroonJavaKlasseWriter.GENERATIE_KLASSE_NAAM_PREFIX
						+ berichtModelNaamgevingStrategie.getJavaTypeVoorElement(groep).getNaam(), false));
		return mapping;
	}

	/**
	 * Maakt de mapping voor de rootobjecten container die in actie zit.
	 *
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
	 *
	 * @param elementNaam Element naam wat in de collection kan voorkomen.
	 * @param mapAs       De mapAs string die bij het element hoort.
	 * @return Mapping voor collection choice.
	 */
	private Choice bouwCollectionChoiceVoorActie(final String elementNaam, final String mapAs) {
		final Choice collectionChoice = new Choice();
		final Collection collection = new Collection();
		final Property collProp = new Property();
		collProp.setUsage("optional");
		collection.setProperty(collProp);
		collectionChoice.setCollection(collection);
		final Collection.Choice huwelijk = maakStructureCollectionChoice(
				elementNaam, mapAs, true);
		collection.getChoiceList().add(huwelijk);
		return collectionChoice;
	}
}
