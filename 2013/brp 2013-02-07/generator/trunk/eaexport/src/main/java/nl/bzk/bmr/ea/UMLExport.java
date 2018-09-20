/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.bmr.ea;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.bzk.brp.bmr.metamodel.Attribuut;
import nl.bzk.brp.bmr.metamodel.AttribuutType;
import nl.bzk.brp.bmr.metamodel.BasisType;
import nl.bzk.brp.bmr.metamodel.Domein;
import nl.bzk.brp.bmr.metamodel.ExportRegel;
import nl.bzk.brp.bmr.metamodel.Groep;
import nl.bzk.brp.bmr.metamodel.InSetOfModel;
import nl.bzk.brp.bmr.metamodel.Laag;
import nl.bzk.brp.bmr.metamodel.ModelElement;
import nl.bzk.brp.bmr.metamodel.ObjectType;
import nl.bzk.brp.bmr.metamodel.Schema;
import nl.bzk.brp.bmr.metamodel.SoortExport;
import nl.bzk.brp.bmr.metamodel.Tekst;
import nl.bzk.brp.bmr.metamodel.Versie;
import nl.bzk.brp.bmr.metamodel.repository.ApplicatieRepository;
import nl.bzk.brp.bmr.metamodel.repository.BasisTypeRepository;
import nl.bzk.brp.bmr.metamodel.repository.DomeinRepository;
import nl.bzk.brp.bmr.metamodel.repository.ExportRegelRepository;
import nl.bzk.brp.bmr.metamodel.ui.Applicatie;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;
import org.eclipse.uml2.uml.resource.UML212UMLResource;
import org.eclipse.uml2.uml.resource.UML22UMLResource;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * Genereert een XMI bestand uit het model in de database.
 */
@Component
public class UMLExport {

    private static final String              AFGELEIDE_INVERSE_ASSOCIATIES = "AfgeleideInverseAssociaties";

    @Inject
    private DomeinRepository                 domeinRepository;

    @Inject
    private BasisTypeRepository              basisTypeRepository;

    @Inject
    private ApplicatieRepository             applicatieRepository;

    @Inject
    private ExportRegelRepository            exportRegelRepository;

    private static final Logger              LOGGER                        = LoggerFactory.getLogger(UMLExport.class);

    private final Map<ModelElement, Element> elementen                     = new HashMap<ModelElement, Element>();

    /**
     * Hebben we overal nodig om 'Extrinsic IDs' (Zie: "Eclipse Modeling Framework, Second Edition", pagina 486) aan
     * alle modelelementen te kunnen koppelen.
     */
    private XMLResource                      modelResource;

    private final ResourceSet                RESOURCE_SET                  = initializeResourceSet();

    private final Profile                    profile                       = createUmlProfile();

    @Transactional
    public void exportXMI(final SoortExport soortExport, final File file) {
        LOGGER.info("Genereren Enterprise Architect XMI export '{}' naar file '{}'", soortExport.getOmschrijving(),
                file.getAbsolutePath());
        Laag.setHuidigeLaag(Laag.LOGISCH_MODEL);

        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        String absolutePath = file.getAbsolutePath();

        URI uri = org.eclipse.emf.common.util.URI.createURI(absolutePath);
        modelResource = (XMLResource) RESOURCE_SET.createResource(uri);

        Model model = UMLFactory.eINSTANCE.createModel();
        modelResource.getContents().add(model);

        transformeer(model);

        modelResource.getContents().add(profile);

        try {
            Map<String, String> options = new HashMap<String, String>();
            options.put(XMLResource.OPTION_ENCODING, "UTF-8");
            modelResource.save(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Klaar.");
    }

    private ResourceSet initializeResourceSet() {
        ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put(Resource.Factory.Registry.DEFAULT_EXTENSION, new UMLResourceFactoryImpl());
        rs.getPackageRegistry().put(UMLPackage.eNS_URI, UMLFactory.eINSTANCE);
        return rs;
    }

    private void transformeer(final Model model) {
        model.setName("EA_Model");

        Package modelPackage = model.createNestedPackage(SoortExport.getHuidigeSoort().getOmschrijving());
        modelResource.setID(modelPackage,
                getId(ExportRegel.II_MODEL, modelPackage.getName(), SoortExport.getHuidigeSoort().ordinal()));

        if (SoortExport.getHuidigeSoort() != SoortExport.GS) {
            Package basisTypen = modelPackage.createNestedPackage("BasisTypen");
            modelResource.setID(basisTypen, getId(ExportRegel.II_BASISTYPEN, "BasisTypen", 1));

            for (BasisType basisType : basisTypeRepository.findAll()) {
                createBasisType(basisTypen, basisType);
            }
        }

        InSetOfModel inSom;
        if (SoortExport.getHuidigeSoort() == SoortExport.LM) {
            inSom = InSetOfModel.M;
        } else {
            inSom = InSetOfModel.S;
        }

        List<Domein> domeinen = domeinRepository.findAll();
        createStereotypesVoorGroepen(domeinen, inSom);
        profile.define();
        model.applyProfile(profile);

        for (Domein domein : domeinen) {
            Package domeinPackage = createPackage(modelPackage, domein);
            /*
             * Eerst het schema met alleen de object typen, omdat de attributen daarnaar kunnen verwijzen.
             */
            for (Schema schema : domein.getNonEmptySchemas(inSom)) {
                Package schemaPackage = createPackage(domeinPackage, schema);
                Versie werkVersie = schema.getWerkVersie();
                if (SoortExport.getHuidigeSoort() != SoortExport.GS) {
                    for (AttribuutType attribuutType : werkVersie.getAttribuutTypes()) {
                        createDataType(schemaPackage, attribuutType);
                    }
                }
                for (ObjectType objectType : werkVersie.getObjectTypes(inSom)) {
                    createClass(schemaPackage, objectType);
                }
                for (ObjectType objectType : werkVersie.getObjectTypes(inSom)) {
                    if (objectType.getSuperType() != null) {
                        createGeneralization((Classifier) elementen.get(objectType), objectType);
                    }
                }
            }
            /*
             * Nu de attributen en associaties erbij.
             */
            for (Schema schema : domein.getNonEmptySchemas(inSom)) {
                Package schemaPackage = (Package) elementen.get(schema);
                for (ObjectType objectType : schema.getWerkVersie().getObjectTypes(inSom)) {
                    Class umlClass = (Class) elementen.get(objectType);
                    for (Groep groep : objectType.getGroepen(inSom)) {
                        Stereotype groepStereotype = profile.getOwnedStereotype(groep.getNaam());
                        for (Attribuut attribuut : groep.getAttributen(inSom)) {
                            Property property = createAttribute(umlClass, attribuut);
                            property.applyStereotype(groepStereotype);
                        }
                    }
                    for (Attribuut attribuut : objectType.getObjectTypeAttributen(inSom)) {
                        createAssociation(schemaPackage, attribuut);
                    }
                }
            }
        }
        for (Applicatie applicatie : applicatieRepository.findAll()) {
            LOGGER.debug("Applicatie {}", applicatie.getNaam());
        }
    }

    private Profile createUmlProfile() {
        String umlProfile = "metamodels/UML.metamodel.uml";
        String baseUrl = UMLExport.class.getClassLoader().getResource(umlProfile).toString();
        baseUrl = baseUrl.substring(0, baseUrl.length() - umlProfile.length());
        URI baseUri = URI.createURI(baseUrl);
        URIConverter.URI_MAP.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), baseUri.appendSegment("libraries")
                .appendSegment(""));
        URIConverter.URI_MAP.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), baseUri.appendSegment("metamodels")
                .appendSegment(""));
        URIConverter.URI_MAP.put(URI.createURI(UMLResource.PROFILES_PATHMAP), baseUri.appendSegment("profiles")
                .appendSegment(""));

        Profile profile = UMLFactory.eINSTANCE.createProfile();
        profile.setName("BMR Profile");
        Resource umlResource = RESOURCE_SET.getResource(URI.createURI(UML22UMLResource.UML_METAMODEL_URI), true);
        Model umlMetamodel = (Model) EcoreUtil.getObjectByType(umlResource.getContents(), UMLPackage.Literals.PACKAGE);
        Class metaclass = (Class) umlMetamodel.getOwnedType("Property");
        profile.createMetaclassReference(metaclass);
        return profile;
    }

    private void createStereotypesVoorGroepen(final List<Domein> domeinen, final InSetOfModel inSom) {
        /*
         * Eerst stereotypes maken voor alle groepen in alle objecttypen. Die moeten vooraf gedefiniëerd worden, zodat
         * we ze later kunnen toepassen op de attributen.
         */
        Resource umlResource = RESOURCE_SET.getResource(URI.createURI(UML212UMLResource.UML_METAMODEL_URI), true);
        Model umlMetamodel = (Model) EcoreUtil.getObjectByType(umlResource.getContents(), UMLPackage.Literals.PACKAGE);
        Model uml = umlMetamodel;
        Class metaclass = (Class) uml.getOwnedType("Property");

        Stereotype stereotype = createStereotype(profile, AFGELEIDE_INVERSE_ASSOCIATIES);
        stereotype.createExtension(metaclass, false);

        for (Domein domein : domeinen) {
            for (Schema schema : domein.getNonEmptySchemas(inSom)) {
                for (ObjectType objectType : schema.getWerkVersie().getObjectTypes(inSom)) {
                    for (Groep groep : objectType.getGroepen(inSom)) {
                        String naam = groep.getNaam();
                        stereotype = profile.getOwnedStereotype(naam);
                        if (stereotype == null) {
                            stereotype = createStereotype(profile, naam);
                            stereotype.createExtension(metaclass, false);
                        }
                    }
                }
            }
        }
    }

    private Stereotype createStereotype(final Profile profile, final String naam) {
        Stereotype stereotype = profile.createOwnedStereotype(naam);
        stereotype.setVisibility(VisibilityKind.PUBLIC_LITERAL);
        stereotype.setIsAbstract(false);
        stereotype.setIsLeaf(true);
        stereotype.setIsActive(true);
        return stereotype;
    }

    private void createGeneralization(final Classifier classifier, final ObjectType objectType) {
        Generalization generalization =
            classifier.createGeneralization((Classifier) elementen.get(objectType.getSuperType()));
        modelResource.setID(generalization,
                getId(ExportRegel.II_SUPERTYPE, objectType.getNaam(), objectType.getSyncId()));
    }

    private Type createAssociation(final Package container, final Attribuut attribuut) {
        Class srcType = (Class) elementen.get(attribuut.getObjectType());
        Class dstType = (Class) elementen.get(attribuut.getType());
        String srcNaam = null;
        if (attribuut.getInverseAssociatieNaam() != null) {
            srcNaam = attribuut.getInverseAssociatieNaam();
            /*
             * Inversie associatie attribuut.
             */
            Property inverse = dstType.createOwnedAttribute(srcNaam, srcType, 0, LiteralUnlimitedNatural.UNLIMITED);
            modelResource.setID(inverse,
                    getId(ExportRegel.II_INVERSE_ATTRIBUTE, dstType.getName() + "." + srcNaam, attribuut.getSyncId()));
            modelResource.setID(inverse.getLowerValue(),
                    getId(ExportRegel.II_ATTRIBUTE_CARD_LO, dstType.getName() + "." + srcNaam, attribuut.getSyncId()));
            modelResource.setID(inverse.getUpperValue(),
                    getId(ExportRegel.II_ATTRIBUTE_CARD_HI, dstType.getName() + "." + srcNaam, attribuut.getSyncId()));
            inverse.setVisibility(VisibilityKind.PRIVATE_LITERAL);
            inverse.setIsDerived(true);
            Stereotype stereotype = profile.getOwnedStereotype(AFGELEIDE_INVERSE_ASSOCIATIES);
            inverse.applyStereotype(stereotype);
        } else {
            srcNaam = attribuut.getObjectType().getNaam();
        }
        String naam = associatieNaam(attribuut);

        Association association =
            (Association) container.createPackagedElement(naam, UMLPackage.eINSTANCE.getAssociation());
        modelResource.setID(association, getId(ExportRegel.II_ASSOCIATION, naam, attribuut.getSyncId()));

        Property srcEnd = association.createOwnedEnd(srcNaam, srcType);
        modelResource.setID(srcEnd, getId(ExportRegel.II_ASSOCIATION_SRC, naam, attribuut.getSyncId()));
        srcEnd.setVisibility(VisibilityKind.PRIVATE_LITERAL);
        srcEnd.setLower(0);
        srcEnd.setUpper(LiteralUnlimitedNatural.UNLIMITED);
        modelResource.setID(srcEnd.getLowerValue(),
                getId(ExportRegel.II_ASSOCIATION_SRC_CARD_LO, naam, attribuut.getSyncId()));
        modelResource.setID(srcEnd.getUpperValue(),
                getId(ExportRegel.II_ASSOCIATION_SRC_CARD_HI, naam, attribuut.getSyncId()));

        Property dstEnd = association.createOwnedEnd(attribuut.getNaam(), dstType);
        modelResource.setID(dstEnd, getId(ExportRegel.II_ASSOCIATION_DST, naam, attribuut.getSyncId()));
        dstEnd.setVisibility(VisibilityKind.PRIVATE_LITERAL);
        dstEnd.setLower(1);
        dstEnd.setUpper(1);
        modelResource.setID(dstEnd.getLowerValue(),
                getId(ExportRegel.II_ASSOCIATION_DST_CARD_LO, naam, attribuut.getSyncId()));
        modelResource.setID(dstEnd.getUpperValue(),
                getId(ExportRegel.II_ASSOCIATION_DST_CARD_HI, naam, attribuut.getSyncId()));

        return association;
    }

    private String associatieNaam(final Attribuut attribuut) {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(attribuut.getObjectType().getNaam());
        resultaat.append(".");
        resultaat.append(attribuut.getNaam());
        return resultaat.toString();
    }

    private Property createAttribute(final Class umlClass, final Attribuut attribuut) {
        Property property = (Property) elementen.get(attribuut);
        if (property == null) {
            LOGGER.debug("Attribuut {}", attribuut.getNaam());
            Type type = (Type) elementen.get(attribuut.getType());
            try {
                property = umlClass.createOwnedAttribute(attribuut.getNaam(), type);
            } catch (Exception e) {
                if (umlClass == null) {
                    LOGGER.warn("umlClass is null");
                }
                if (type == null) {
                    LOGGER.warn("type is null");
                }
                throw new RuntimeException(e);
            }
            String naam = attribuut.getObjectType().getNaam() + "." + attribuut.getNaam();
            modelResource.setID(property, getId(ExportRegel.II_ATTRIBUTE, naam, attribuut.getSyncId()));
            property.setIsDerived(attribuut.isAfleidbaar());
            property.setVisibility(VisibilityKind.PRIVATE_LITERAL);
            addTeksten(property, attribuut);
        }
        return property;
    }

    private org.eclipse.uml2.uml.Class createClass(final Package container, final ObjectType objectType) {
        org.eclipse.uml2.uml.Class umlClass = (Class) elementen.get(objectType);
        if (umlClass == null) {
            LOGGER.debug("ObjectType {}", objectType.getNaam());
            umlClass = container.createOwnedClass(objectType.getNaam(), false);
            modelResource.setID(umlClass,
                    getId(ExportRegel.II_OBJECTTYPE, objectType.getNaam(), objectType.getSyncId()));
            elementen.put(objectType, umlClass);
            addTeksten(umlClass, objectType);
        }
        return umlClass;
    }

    /**
     * @param objectType
     * @param umlClass
     */
    private void addTeksten(final org.eclipse.uml2.uml.Element umlElement,
        final nl.bzk.brp.bmr.metamodel.Element element)
    {
        if (!element.getTeksten().isEmpty()) {
            for (Tekst tekst : element.getTeksten().values()) {
                Comment comment = umlElement.createOwnedComment();
                comment.setBody(tekst.getTekst());
                comment.getAnnotatedElements().add(umlElement);
            }
        }
    }

    private DataType createDataType(final Package container, final AttribuutType attribuutType) {
        DataType dataType = (DataType) elementen.get(attribuutType);
        if (dataType == null) {
            LOGGER.debug("AttribuutType {}", attribuutType.getNaam());
            dataType =
                (DataType) container.createOwnedType(attribuutType.getNaam(), UMLPackage.eINSTANCE.getDataType());
            modelResource.setID(dataType,
                    getId(ExportRegel.II_ATTRIBUUTTYPE, attribuutType.getNaam(), attribuutType.getSyncId()));
            Generalization generalization =
                dataType.createGeneralization((Classifier) elementen.get(attribuutType.getType()));
            modelResource.setID(generalization,
                    getId(ExportRegel.II_ATTRIBUUTTYPE_TYPE, attribuutType.getNaam(), attribuutType.getSyncId()));
            elementen.put(attribuutType, dataType);
            addTeksten(dataType, attribuutType);
        }
        return dataType;
    }

    private Package createPackage(final Package container, final Schema schema) {
        Package schemaPackage = (Package) elementen.get(schema);
        if (schemaPackage == null) {
            LOGGER.debug("Schema {}", schema.getNaam());
            schemaPackage = container.createNestedPackage(schema.getNaam());
            modelResource.setID(schemaPackage, getId(ExportRegel.II_SCHEMA, schema.getNaam(), schema.getSyncId()));
            elementen.put(schema, schemaPackage);
        }
        return schemaPackage;
    }

    private Package createPackage(final Package container, final Domein domein) {
        Package domeinPackage = (Package) elementen.get(domein);
        if (domeinPackage == null) {
            LOGGER.debug("Domein {}", domein.getNaam());
            domeinPackage = container.createNestedPackage(domein.getNaam());
            modelResource.setID(domeinPackage, getId(ExportRegel.II_DOMEIN, domein.getNaam(), domein.getSyncId()));
            elementen.put(domein, domeinPackage);
        }
        return domeinPackage;
    }

    private PrimitiveType createBasisType(final Package namespace, final BasisType basisType) {
        PrimitiveType primitiveType = (PrimitiveType) elementen.get(basisType);
        if (primitiveType == null) {
            LOGGER.debug("BasisType {}", basisType.getNaam());
            primitiveType = namespace.createOwnedPrimitiveType(basisType.getNaam());
            modelResource.setID(primitiveType,
                    getId(ExportRegel.II_BASISTYPE, basisType.getNaam(), basisType.getSyncId()));
            elementen.put(basisType, primitiveType);
            addTeksten(primitiveType, basisType);
        }
        return primitiveType;
    }

    private String getId(final String interneIdentifier, final String naam, final Integer syncId) {
        LOGGER.debug("getId({}, {}, {})", new Object[] { interneIdentifier, naam, syncId });
        return exportRegelRepository.getExportIdentifier(interneIdentifier, naam, syncId);
    }
}
