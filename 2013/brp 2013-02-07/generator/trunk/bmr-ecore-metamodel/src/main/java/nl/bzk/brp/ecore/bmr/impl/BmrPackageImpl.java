/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package nl.bzk.brp.ecore.bmr.impl;

import java.util.Map;

import nl.bzk.brp.ecore.bmr.Applicatie;
import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.AttribuutType;
import nl.bzk.brp.ecore.bmr.BasisType;
import nl.bzk.brp.ecore.bmr.BedrijfsRegel;
import nl.bzk.brp.ecore.bmr.BerichtSjabloon;
import nl.bzk.brp.ecore.bmr.BmrFactory;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Bron;
import nl.bzk.brp.ecore.bmr.BronAttribuut;
import nl.bzk.brp.ecore.bmr.Domein;
import nl.bzk.brp.ecore.bmr.Element;
import nl.bzk.brp.ecore.bmr.ElementSoort;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.Frame;
import nl.bzk.brp.ecore.bmr.FrameVeld;
import nl.bzk.brp.ecore.bmr.GelaagdElement;
import nl.bzk.brp.ecore.bmr.Groep;
import nl.bzk.brp.ecore.bmr.Historie;
import nl.bzk.brp.ecore.bmr.InSetOfModel;
import nl.bzk.brp.ecore.bmr.Laag;
import nl.bzk.brp.ecore.bmr.MetaRegister;
import nl.bzk.brp.ecore.bmr.ModelElement;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.Schema;
import nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel;
import nl.bzk.brp.ecore.bmr.SoortInhoud;
import nl.bzk.brp.ecore.bmr.SoortTekst;
import nl.bzk.brp.ecore.bmr.Tekst;
import nl.bzk.brp.ecore.bmr.Tuple;
import nl.bzk.brp.ecore.bmr.Type;
import nl.bzk.brp.ecore.bmr.Versie;
import nl.bzk.brp.ecore.bmr.VersieTag;
import nl.bzk.brp.ecore.bmr.WaarderegelWaarde;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BmrPackageImpl extends EPackageImpl implements BmrPackage {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass attribuutEClass                 = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass attribuutTypeEClass             = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass basisTypeEClass                 = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass bedrijfsRegelEClass             = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass berichtSjabloonEClass           = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass domeinEClass                    = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass elementEClass                   = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass gelaagdElementEClass            = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass groepEClass                     = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass laagEClass                      = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass metaRegisterEClass              = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass modelElementEClass              = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass objectTypeEClass                = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass schemaEClass                    = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass tekstEClass                     = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass tupleEClass                     = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass typeEClass                      = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass versieEClass                    = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass waarderegelWaardeEClass         = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass soortTekstToTekstMapEntryEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass applicatieEClass                = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass formulierEClass                 = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass frameEClass                     = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass frameVeldEClass                 = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass bronEClass                      = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass bronAttribuutEClass             = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum  elementSoortEEnum               = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum  soortBedrijfsRegelEEnum         = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum  soortInhoudEEnum                = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum  soortTekstEEnum                 = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum  versieTagEEnum                  = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum  inSetOfModelEEnum               = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum historieEEnum = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package
     * package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private BmrPackageImpl() {
        super(eNS_URI, BmrFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link BmrPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static BmrPackage init() {
        if (isInited) return (BmrPackage)EPackage.Registry.INSTANCE.getEPackage(BmrPackage.eNS_URI);

        // Obtain or create and register package
        BmrPackageImpl theBmrPackage = (BmrPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BmrPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BmrPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theBmrPackage.createPackageContents();

        // Initialize created meta-data
        theBmrPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theBmrPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(BmrPackage.eNS_URI, theBmrPackage);
        return theBmrPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getAttribuut() {
        return attribuutEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAttribuut_Groep() {
        return (EReference)attribuutEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAttribuut_ObjectType() {
        return (EReference)attribuutEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAttribuut_Type() {
        return (EReference)attribuutEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAttribuut_Afleidbaar() {
        return (EAttribute)attribuutEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAttribuut_HistorieVastleggen() {
        return (EAttribute)attribuutEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAttribuut_Verplicht() {
        return (EAttribute)attribuutEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAttribuut_InverseAssociatieNaam() {
        return (EAttribute)attribuutEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAttribuut_InverseAssociatie() {
        return (EAttribute)attribuutEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAttribuut_GebruiktInBedrijfsRegels() {
        return (EReference)attribuutEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getAttribuutType() {
        return attribuutTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAttribuutType_Type() {
        return (EReference)attribuutTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAttribuutType_Versie() {
        return (EReference)attribuutTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAttribuutType_MinimumLengte() {
        return (EAttribute)attribuutTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAttribuutType_MaximumLengte() {
        return (EAttribute)attribuutTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAttribuutType_AantalDecimalen() {
        return (EAttribute)attribuutTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getBasisType() {
        return basisTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getBedrijfsRegel() {
        return bedrijfsRegelEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getBedrijfsRegel_Attributen() {
        return (EReference)bedrijfsRegelEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getBedrijfsRegel_Element() {
        return (EReference)bedrijfsRegelEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getBedrijfsRegel_SoortBedrijfsRegel() {
        return (EAttribute)bedrijfsRegelEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getBedrijfsRegel_Waarden() {
        return (EReference)bedrijfsRegelEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getBerichtSjabloon() {
        return berichtSjabloonEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getBerichtSjabloon_Versie() {
        return (EReference)berichtSjabloonEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDomein() {
        return domeinEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDomein_Schemas() {
        return (EReference)domeinEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getElement() {
        return elementEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getElement_BedrijfsRegels() {
        return (EReference)elementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getElement_Beschrijving() {
        return (EAttribute)elementEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getElement_Teksten() {
        return (EReference)elementEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getElement_Laag() {
        return (EAttribute)elementEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getElement_SyncId() {
        return (EAttribute)elementEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getElement_Soort() {
        return (EAttribute)elementEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getGelaagdElement() {
        return gelaagdElementEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getGelaagdElement_IdentifierDB() {
        return (EAttribute)gelaagdElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getGelaagdElement_IdentifierCode() {
        return (EAttribute)gelaagdElementEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getGelaagdElement_VersieTag() {
        return (EAttribute)gelaagdElementEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getGelaagdElement_Volgnummer() {
        return (EAttribute)gelaagdElementEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getGelaagdElement_InSetOfModel() {
        return (EAttribute)gelaagdElementEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getGroep() {
        return groepEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getGroep_ObjectType() {
        return (EReference)groepEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getGroep_Attributen() {
        return (EReference)groepEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGroep_HistorieVastleggen()
    {
        return (EAttribute)groepEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGroep_Verplicht()
    {
        return (EAttribute)groepEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getLaag() {
        return laagEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getMetaRegister() {
        return metaRegisterEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getMetaRegister_Domeinen() {
        return (EReference)metaRegisterEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getMetaRegister_BasisTypen() {
        return (EReference)metaRegisterEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getMetaRegister_Applicaties() {
        return (EReference)metaRegisterEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getModelElement() {
        return modelElementEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getModelElement_Id() {
        return (EAttribute)modelElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getModelElement_Naam() {
        return (EAttribute)modelElementEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getObjectType() {
        return objectTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getObjectType_Attributen() {
        return (EReference)objectTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getObjectType_Groepen() {
        return (EReference)objectTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getObjectType_MeervoudsNaam() {
        return (EAttribute)objectTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getObjectType_SoortInhoud() {
        return (EAttribute)objectTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getObjectType_SubTypes() {
        return (EReference)objectTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getObjectType_SuperType() {
        return (EReference)objectTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getObjectType_Tuples() {
        return (EReference)objectTypeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getObjectType_Versie() {
        return (EReference)objectTypeEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getObjectType_Afleidbaar() {
        return (EAttribute)objectTypeEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getObjectType_HistorieVastleggen() {
        return (EAttribute)objectTypeEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getObjectType_KunstmatigeSleutel() {
        return (EAttribute)objectTypeEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getObjectType_Lookup() {
        return (EAttribute)objectTypeEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getSchema() {
        return schemaEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getSchema_Versies() {
        return (EReference)schemaEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getSchema_Domein() {
        return (EReference)schemaEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getTekst() {
        return tekstEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTekst_Id() {
        return (EAttribute)tekstEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getTekst_Element() {
        return (EReference)tekstEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTekst_Soort() {
        return (EAttribute)tekstEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTekst_Tekst() {
        return (EAttribute)tekstEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTekst_HtmlTekst() {
        return (EAttribute)tekstEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getTuple() {
        return tupleEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTuple_Id() {
        return (EAttribute)tupleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getTuple_ObjectType() {
        return (EReference)tupleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTuple_RelatiefId() {
        return (EAttribute)tupleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTuple_Code() {
        return (EAttribute)tupleEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTuple_Naam() {
        return (EAttribute)tupleEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTuple_NaamMannelijk() {
        return (EAttribute)tupleEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTuple_NaamVrouwelijk() {
        return (EAttribute)tupleEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTuple_Omschrijving() {
        return (EAttribute)tupleEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTuple_HeeftMaterieleHistorie() {
        return (EAttribute)tupleEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTuple_DatumAanvangGeldigheid() {
        return (EAttribute)tupleEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTuple_DatumEindeGeldigheid() {
        return (EAttribute)tupleEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTuple_CategorieSoortActie()
    {
        return (EAttribute)tupleEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTuple_CategorieSoortDocument()
    {
        return (EAttribute)tupleEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getType() {
        return typeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getVersie() {
        return versieEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getVersie_Schema() {
        return (EReference)versieEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getVersie_VersieTag() {
        return (EAttribute)versieEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getVersie_ObjectTypes() {
        return (EReference)versieEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getVersie_AttribuutTypes() {
        return (EReference)versieEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getVersie_BerichtSjablonen() {
        return (EReference)versieEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWaarderegelWaarde() {
        return waarderegelWaardeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWaarderegelWaarde_BedrijfsRegel() {
        return (EReference)waarderegelWaardeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWaarderegelWaarde_Waarde() {
        return (EAttribute)waarderegelWaardeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWaarderegelWaarde_Weergave() {
        return (EAttribute)waarderegelWaardeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getSoortTekstToTekstMapEntry() {
        return soortTekstToTekstMapEntryEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getSoortTekstToTekstMapEntry_Key() {
        return (EAttribute)soortTekstToTekstMapEntryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getSoortTekstToTekstMapEntry_Value() {
        return (EReference)soortTekstToTekstMapEntryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getApplicatie() {
        return applicatieEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getApplicatie_Formulieren() {
        return (EReference)applicatieEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getFormulier() {
        return formulierEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getFormulier_Applicatie() {
        return (EReference)formulierEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getFormulier_Frames() {
        return (EReference)formulierEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getFormulier_Bronnen() {
        return (EReference)formulierEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getFrame() {
        return frameEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getFrame_Volgnummer() {
        return (EAttribute)frameEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getFrame_Formulier() {
        return (EReference)frameEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getFrame_Bron() {
        return (EReference)frameEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getFrame_Velden() {
        return (EReference)frameEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getFrameVeld() {
        return frameVeldEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getFrameVeld_Volgnummer() {
        return (EAttribute)frameVeldEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getFrameVeld_Frame() {
        return (EReference)frameVeldEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getFrameVeld_Attribuut() {
        return (EReference)frameVeldEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getBron() {
        return bronEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getBron_Meervoudsvorm() {
        return (EAttribute)bronEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getBron_Identifier() {
        return (EAttribute)bronEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getBron_Volgnummer() {
        return (EAttribute)bronEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getBron_Formulier() {
        return (EReference)bronEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getBron_Frames() {
        return (EReference)bronEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getBron_ObjectType() {
        return (EReference)bronEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getBron_Link() {
        return (EReference)bronEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getBron_BronAttributen() {
        return (EReference)bronEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getBronAttribuut() {
        return bronAttribuutEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getBronAttribuut_Bron() {
        return (EReference)bronAttribuutEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getBronAttribuut_Attribuut() {
        return (EReference)bronAttribuutEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getBronAttribuut_Volgnummer() {
        return (EAttribute)bronAttribuutEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getElementSoort() {
        return elementSoortEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getSoortBedrijfsRegel() {
        return soortBedrijfsRegelEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getSoortInhoud() {
        return soortInhoudEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getSoortTekst() {
        return soortTekstEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getVersieTag() {
        return versieTagEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getInSetOfModel() {
        return inSetOfModelEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getHistorie()
    {
        return historieEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public BmrFactory getBmrFactory() {
        return (BmrFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        attribuutEClass = createEClass(ATTRIBUUT);
        createEReference(attribuutEClass, ATTRIBUUT__GROEP);
        createEReference(attribuutEClass, ATTRIBUUT__OBJECT_TYPE);
        createEReference(attribuutEClass, ATTRIBUUT__TYPE);
        createEAttribute(attribuutEClass, ATTRIBUUT__AFLEIDBAAR);
        createEAttribute(attribuutEClass, ATTRIBUUT__HISTORIE_VASTLEGGEN);
        createEAttribute(attribuutEClass, ATTRIBUUT__VERPLICHT);
        createEAttribute(attribuutEClass, ATTRIBUUT__INVERSE_ASSOCIATIE_NAAM);
        createEAttribute(attribuutEClass, ATTRIBUUT__INVERSE_ASSOCIATIE);
        createEReference(attribuutEClass, ATTRIBUUT__GEBRUIKT_IN_BEDRIJFS_REGELS);

        attribuutTypeEClass = createEClass(ATTRIBUUT_TYPE);
        createEReference(attribuutTypeEClass, ATTRIBUUT_TYPE__TYPE);
        createEReference(attribuutTypeEClass, ATTRIBUUT_TYPE__VERSIE);
        createEAttribute(attribuutTypeEClass, ATTRIBUUT_TYPE__MINIMUM_LENGTE);
        createEAttribute(attribuutTypeEClass, ATTRIBUUT_TYPE__MAXIMUM_LENGTE);
        createEAttribute(attribuutTypeEClass, ATTRIBUUT_TYPE__AANTAL_DECIMALEN);

        basisTypeEClass = createEClass(BASIS_TYPE);

        bedrijfsRegelEClass = createEClass(BEDRIJFS_REGEL);
        createEReference(bedrijfsRegelEClass, BEDRIJFS_REGEL__ATTRIBUTEN);
        createEReference(bedrijfsRegelEClass, BEDRIJFS_REGEL__ELEMENT);
        createEAttribute(bedrijfsRegelEClass, BEDRIJFS_REGEL__SOORT_BEDRIJFS_REGEL);
        createEReference(bedrijfsRegelEClass, BEDRIJFS_REGEL__WAARDEN);

        berichtSjabloonEClass = createEClass(BERICHT_SJABLOON);
        createEReference(berichtSjabloonEClass, BERICHT_SJABLOON__VERSIE);

        domeinEClass = createEClass(DOMEIN);
        createEReference(domeinEClass, DOMEIN__SCHEMAS);

        elementEClass = createEClass(ELEMENT);
        createEReference(elementEClass, ELEMENT__BEDRIJFS_REGELS);
        createEAttribute(elementEClass, ELEMENT__BESCHRIJVING);
        createEReference(elementEClass, ELEMENT__TEKSTEN);
        createEAttribute(elementEClass, ELEMENT__LAAG);
        createEAttribute(elementEClass, ELEMENT__SYNC_ID);
        createEAttribute(elementEClass, ELEMENT__SOORT);

        gelaagdElementEClass = createEClass(GELAAGD_ELEMENT);
        createEAttribute(gelaagdElementEClass, GELAAGD_ELEMENT__IDENTIFIER_DB);
        createEAttribute(gelaagdElementEClass, GELAAGD_ELEMENT__IDENTIFIER_CODE);
        createEAttribute(gelaagdElementEClass, GELAAGD_ELEMENT__VERSIE_TAG);
        createEAttribute(gelaagdElementEClass, GELAAGD_ELEMENT__VOLGNUMMER);
        createEAttribute(gelaagdElementEClass, GELAAGD_ELEMENT__IN_SET_OF_MODEL);

        groepEClass = createEClass(GROEP);
        createEReference(groepEClass, GROEP__OBJECT_TYPE);
        createEReference(groepEClass, GROEP__ATTRIBUTEN);
        createEAttribute(groepEClass, GROEP__HISTORIE_VASTLEGGEN);
        createEAttribute(groepEClass, GROEP__VERPLICHT);

        laagEClass = createEClass(LAAG);

        metaRegisterEClass = createEClass(META_REGISTER);
        createEReference(metaRegisterEClass, META_REGISTER__DOMEINEN);
        createEReference(metaRegisterEClass, META_REGISTER__BASIS_TYPEN);
        createEReference(metaRegisterEClass, META_REGISTER__APPLICATIES);

        modelElementEClass = createEClass(MODEL_ELEMENT);
        createEAttribute(modelElementEClass, MODEL_ELEMENT__ID);
        createEAttribute(modelElementEClass, MODEL_ELEMENT__NAAM);

        objectTypeEClass = createEClass(OBJECT_TYPE);
        createEReference(objectTypeEClass, OBJECT_TYPE__ATTRIBUTEN);
        createEReference(objectTypeEClass, OBJECT_TYPE__GROEPEN);
        createEAttribute(objectTypeEClass, OBJECT_TYPE__MEERVOUDS_NAAM);
        createEAttribute(objectTypeEClass, OBJECT_TYPE__SOORT_INHOUD);
        createEReference(objectTypeEClass, OBJECT_TYPE__SUB_TYPES);
        createEReference(objectTypeEClass, OBJECT_TYPE__SUPER_TYPE);
        createEReference(objectTypeEClass, OBJECT_TYPE__TUPLES);
        createEReference(objectTypeEClass, OBJECT_TYPE__VERSIE);
        createEAttribute(objectTypeEClass, OBJECT_TYPE__AFLEIDBAAR);
        createEAttribute(objectTypeEClass, OBJECT_TYPE__HISTORIE_VASTLEGGEN);
        createEAttribute(objectTypeEClass, OBJECT_TYPE__KUNSTMATIGE_SLEUTEL);
        createEAttribute(objectTypeEClass, OBJECT_TYPE__LOOKUP);

        schemaEClass = createEClass(SCHEMA);
        createEReference(schemaEClass, SCHEMA__VERSIES);
        createEReference(schemaEClass, SCHEMA__DOMEIN);

        tekstEClass = createEClass(TEKST);
        createEAttribute(tekstEClass, TEKST__ID);
        createEReference(tekstEClass, TEKST__ELEMENT);
        createEAttribute(tekstEClass, TEKST__SOORT);
        createEAttribute(tekstEClass, TEKST__TEKST);
        createEAttribute(tekstEClass, TEKST__HTML_TEKST);

        tupleEClass = createEClass(TUPLE);
        createEAttribute(tupleEClass, TUPLE__ID);
        createEReference(tupleEClass, TUPLE__OBJECT_TYPE);
        createEAttribute(tupleEClass, TUPLE__RELATIEF_ID);
        createEAttribute(tupleEClass, TUPLE__CODE);
        createEAttribute(tupleEClass, TUPLE__NAAM);
        createEAttribute(tupleEClass, TUPLE__NAAM_MANNELIJK);
        createEAttribute(tupleEClass, TUPLE__NAAM_VROUWELIJK);
        createEAttribute(tupleEClass, TUPLE__OMSCHRIJVING);
        createEAttribute(tupleEClass, TUPLE__HEEFT_MATERIELE_HISTORIE);
        createEAttribute(tupleEClass, TUPLE__DATUM_AANVANG_GELDIGHEID);
        createEAttribute(tupleEClass, TUPLE__DATUM_EINDE_GELDIGHEID);
        createEAttribute(tupleEClass, TUPLE__CATEGORIE_SOORT_ACTIE);
        createEAttribute(tupleEClass, TUPLE__CATEGORIE_SOORT_DOCUMENT);

        typeEClass = createEClass(TYPE);

        versieEClass = createEClass(VERSIE);
        createEReference(versieEClass, VERSIE__SCHEMA);
        createEAttribute(versieEClass, VERSIE__VERSIE_TAG);
        createEReference(versieEClass, VERSIE__OBJECT_TYPES);
        createEReference(versieEClass, VERSIE__ATTRIBUUT_TYPES);
        createEReference(versieEClass, VERSIE__BERICHT_SJABLONEN);

        waarderegelWaardeEClass = createEClass(WAARDEREGEL_WAARDE);
        createEReference(waarderegelWaardeEClass, WAARDEREGEL_WAARDE__BEDRIJFS_REGEL);
        createEAttribute(waarderegelWaardeEClass, WAARDEREGEL_WAARDE__WAARDE);
        createEAttribute(waarderegelWaardeEClass, WAARDEREGEL_WAARDE__WEERGAVE);

        soortTekstToTekstMapEntryEClass = createEClass(SOORT_TEKST_TO_TEKST_MAP_ENTRY);
        createEAttribute(soortTekstToTekstMapEntryEClass, SOORT_TEKST_TO_TEKST_MAP_ENTRY__KEY);
        createEReference(soortTekstToTekstMapEntryEClass, SOORT_TEKST_TO_TEKST_MAP_ENTRY__VALUE);

        applicatieEClass = createEClass(APPLICATIE);
        createEReference(applicatieEClass, APPLICATIE__FORMULIEREN);

        formulierEClass = createEClass(FORMULIER);
        createEReference(formulierEClass, FORMULIER__APPLICATIE);
        createEReference(formulierEClass, FORMULIER__FRAMES);
        createEReference(formulierEClass, FORMULIER__BRONNEN);

        frameEClass = createEClass(FRAME);
        createEAttribute(frameEClass, FRAME__VOLGNUMMER);
        createEReference(frameEClass, FRAME__FORMULIER);
        createEReference(frameEClass, FRAME__BRON);
        createEReference(frameEClass, FRAME__VELDEN);

        frameVeldEClass = createEClass(FRAME_VELD);
        createEAttribute(frameVeldEClass, FRAME_VELD__VOLGNUMMER);
        createEReference(frameVeldEClass, FRAME_VELD__FRAME);
        createEReference(frameVeldEClass, FRAME_VELD__ATTRIBUUT);

        bronEClass = createEClass(BRON);
        createEAttribute(bronEClass, BRON__MEERVOUDSVORM);
        createEAttribute(bronEClass, BRON__IDENTIFIER);
        createEAttribute(bronEClass, BRON__VOLGNUMMER);
        createEReference(bronEClass, BRON__FORMULIER);
        createEReference(bronEClass, BRON__FRAMES);
        createEReference(bronEClass, BRON__OBJECT_TYPE);
        createEReference(bronEClass, BRON__LINK);
        createEReference(bronEClass, BRON__BRON_ATTRIBUTEN);

        bronAttribuutEClass = createEClass(BRON_ATTRIBUUT);
        createEReference(bronAttribuutEClass, BRON_ATTRIBUUT__BRON);
        createEReference(bronAttribuutEClass, BRON_ATTRIBUUT__ATTRIBUUT);
        createEAttribute(bronAttribuutEClass, BRON_ATTRIBUUT__VOLGNUMMER);

        // Create enums
        elementSoortEEnum = createEEnum(ELEMENT_SOORT);
        soortBedrijfsRegelEEnum = createEEnum(SOORT_BEDRIJFS_REGEL);
        soortInhoudEEnum = createEEnum(SOORT_INHOUD);
        soortTekstEEnum = createEEnum(SOORT_TEKST);
        versieTagEEnum = createEEnum(VERSIE_TAG);
        inSetOfModelEEnum = createEEnum(IN_SET_OF_MODEL);
        historieEEnum = createEEnum(HISTORIE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        attribuutEClass.getESuperTypes().add(this.getGelaagdElement());
        attribuutTypeEClass.getESuperTypes().add(this.getType());
        basisTypeEClass.getESuperTypes().add(this.getElement());
        bedrijfsRegelEClass.getESuperTypes().add(this.getGelaagdElement());
        berichtSjabloonEClass.getESuperTypes().add(this.getGelaagdElement());
        domeinEClass.getESuperTypes().add(this.getElement());
        elementEClass.getESuperTypes().add(this.getModelElement());
        gelaagdElementEClass.getESuperTypes().add(this.getElement());
        groepEClass.getESuperTypes().add(this.getGelaagdElement());
        laagEClass.getESuperTypes().add(this.getElement());
        objectTypeEClass.getESuperTypes().add(this.getType());
        schemaEClass.getESuperTypes().add(this.getElement());
        typeEClass.getESuperTypes().add(this.getGelaagdElement());
        versieEClass.getESuperTypes().add(this.getElement());
        waarderegelWaardeEClass.getESuperTypes().add(this.getGelaagdElement());
        applicatieEClass.getESuperTypes().add(this.getModelElement());
        formulierEClass.getESuperTypes().add(this.getModelElement());
        frameEClass.getESuperTypes().add(this.getModelElement());
        frameVeldEClass.getESuperTypes().add(this.getModelElement());
        bronEClass.getESuperTypes().add(this.getModelElement());
        bronAttribuutEClass.getESuperTypes().add(this.getModelElement());

        // Initialize classes and features; add operations and parameters
        initEClass(attribuutEClass, Attribuut.class, "Attribuut", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAttribuut_Groep(), this.getGroep(), this.getGroep_Attributen(), "groep", null, 0, 1, Attribuut.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAttribuut_ObjectType(), this.getObjectType(), this.getObjectType_Attributen(), "objectType", null, 0, 1, Attribuut.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAttribuut_Type(), this.getType(), null, "type", null, 0, 1, Attribuut.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribuut_Afleidbaar(), ecorePackage.getEBooleanObject(), "afleidbaar", "false", 0, 1, Attribuut.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribuut_HistorieVastleggen(), this.getHistorie(), "historieVastleggen", "false", 0, 1, Attribuut.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribuut_Verplicht(), ecorePackage.getEBoolean(), "verplicht", "false", 0, 1, Attribuut.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribuut_InverseAssociatieNaam(), ecorePackage.getEString(), "inverseAssociatieNaam", null, 0, 1, Attribuut.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribuut_InverseAssociatie(), ecorePackage.getEString(), "inverseAssociatie", null, 0, 1, Attribuut.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAttribuut_GebruiktInBedrijfsRegels(), this.getBedrijfsRegel(), this.getBedrijfsRegel_Attributen(), "gebruiktInBedrijfsRegels", null, 0, -1, Attribuut.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(attribuutTypeEClass, AttribuutType.class, "AttribuutType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAttribuutType_Type(), this.getBasisType(), null, "type", null, 0, 1, AttribuutType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAttribuutType_Versie(), this.getVersie(), this.getVersie_AttribuutTypes(), "versie", null, 0, 1, AttribuutType.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribuutType_MinimumLengte(), ecorePackage.getEIntegerObject(), "minimumLengte", null, 0, 1, AttribuutType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribuutType_MaximumLengte(), ecorePackage.getEIntegerObject(), "maximumLengte", null, 0, 1, AttribuutType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribuutType_AantalDecimalen(), ecorePackage.getEIntegerObject(), "aantalDecimalen", null, 0, 1, AttribuutType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(basisTypeEClass, BasisType.class, "BasisType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(bedrijfsRegelEClass, BedrijfsRegel.class, "BedrijfsRegel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getBedrijfsRegel_Attributen(), this.getAttribuut(), this.getAttribuut_GebruiktInBedrijfsRegels(), "attributen", null, 0, -1, BedrijfsRegel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getBedrijfsRegel_Element(), this.getElement(), this.getElement_BedrijfsRegels(), "element", null, 0, 1, BedrijfsRegel.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBedrijfsRegel_SoortBedrijfsRegel(), this.getSoortBedrijfsRegel(), "soortBedrijfsRegel", null, 0, 1, BedrijfsRegel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getBedrijfsRegel_Waarden(), this.getWaarderegelWaarde(), this.getWaarderegelWaarde_BedrijfsRegel(), "waarden", null, 0, -1, BedrijfsRegel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(berichtSjabloonEClass, BerichtSjabloon.class, "BerichtSjabloon", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getBerichtSjabloon_Versie(), this.getVersie(), this.getVersie_BerichtSjablonen(), "versie", null, 0, 1, BerichtSjabloon.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(domeinEClass, Domein.class, "Domein", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDomein_Schemas(), this.getSchema(), this.getSchema_Domein(), "schemas", null, 0, -1, Domein.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(elementEClass, Element.class, "Element", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getElement_BedrijfsRegels(), this.getBedrijfsRegel(), this.getBedrijfsRegel_Element(), "bedrijfsRegels", null, 0, -1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getElement_Beschrijving(), ecorePackage.getEString(), "beschrijving", null, 0, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getElement_Teksten(), this.getTekst(), this.getTekst_Element(), "teksten", null, 0, -1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getElement_Laag(), ecorePackage.getEIntegerObject(), "laag", null, 0, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getElement_SyncId(), ecorePackage.getEIntegerObject(), "syncId", null, 0, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getElement_Soort(), ecorePackage.getEString(), "soort", null, 0, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(gelaagdElementEClass, GelaagdElement.class, "GelaagdElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGelaagdElement_IdentifierDB(), ecorePackage.getEString(), "identifierDB", null, 0, 1, GelaagdElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGelaagdElement_IdentifierCode(), ecorePackage.getEString(), "identifierCode", null, 0, 1, GelaagdElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGelaagdElement_VersieTag(), this.getVersieTag(), "versieTag", null, 0, 1, GelaagdElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGelaagdElement_Volgnummer(), ecorePackage.getEIntegerObject(), "volgnummer", null, 0, 1, GelaagdElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGelaagdElement_InSetOfModel(), this.getInSetOfModel(), "inSetOfModel", null, 0, 1, GelaagdElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(groepEClass, Groep.class, "Groep", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGroep_ObjectType(), this.getObjectType(), this.getObjectType_Groepen(), "objectType", null, 0, 1, Groep.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGroep_Attributen(), this.getAttribuut(), this.getAttribuut_Groep(), "attributen", null, 0, -1, Groep.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGroep_HistorieVastleggen(), this.getHistorie(), "historieVastleggen", null, 0, 1, Groep.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGroep_Verplicht(), ecorePackage.getEBoolean(), "verplicht", null, 0, 1, Groep.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(laagEClass, Laag.class, "Laag", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(metaRegisterEClass, MetaRegister.class, "MetaRegister", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getMetaRegister_Domeinen(), this.getDomein(), null, "domeinen", null, 0, -1, MetaRegister.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getMetaRegister_BasisTypen(), this.getBasisType(), null, "basisTypen", null, 0, -1, MetaRegister.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getMetaRegister_Applicaties(), this.getApplicatie(), null, "applicaties", null, 0, -1, MetaRegister.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(modelElementEClass, ModelElement.class, "ModelElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getModelElement_Id(), ecorePackage.getEIntegerObject(), "id", null, 0, 1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getModelElement_Naam(), ecorePackage.getEString(), "naam", null, 0, 1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(objectTypeEClass, ObjectType.class, "ObjectType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getObjectType_Attributen(), this.getAttribuut(), this.getAttribuut_ObjectType(), "attributen", null, 0, -1, ObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getObjectType_Groepen(), this.getGroep(), this.getGroep_ObjectType(), "groepen", null, 0, -1, ObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getObjectType_MeervoudsNaam(), ecorePackage.getEString(), "meervoudsNaam", null, 0, 1, ObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getObjectType_SoortInhoud(), this.getSoortInhoud(), "soortInhoud", null, 0, 1, ObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getObjectType_SubTypes(), this.getObjectType(), this.getObjectType_SuperType(), "subTypes", null, 0, -1, ObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getObjectType_SuperType(), this.getObjectType(), this.getObjectType_SubTypes(), "superType", null, 0, 1, ObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getObjectType_Tuples(), this.getTuple(), this.getTuple_ObjectType(), "tuples", null, 0, -1, ObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getObjectType_Versie(), this.getVersie(), this.getVersie_ObjectTypes(), "versie", null, 0, 1, ObjectType.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getObjectType_Afleidbaar(), ecorePackage.getEBooleanObject(), "afleidbaar", "false", 0, 1, ObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getObjectType_HistorieVastleggen(), this.getHistorie(), "historieVastleggen", "false", 0, 1, ObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getObjectType_KunstmatigeSleutel(), ecorePackage.getEBooleanObject(), "kunstmatigeSleutel", null, 0, 1, ObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getObjectType_Lookup(), ecorePackage.getEBooleanObject(), "lookup", "false", 0, 1, ObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(schemaEClass, Schema.class, "Schema", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSchema_Versies(), this.getVersie(), this.getVersie_Schema(), "versies", null, 0, -1, Schema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSchema_Domein(), this.getDomein(), this.getDomein_Schemas(), "domein", null, 0, 1, Schema.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(tekstEClass, Tekst.class, "Tekst", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getTekst_Id(), ecorePackage.getELongObject(), "id", null, 0, 1, Tekst.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getTekst_Element(), this.getElement(), this.getElement_Teksten(), "element", null, 0, 1, Tekst.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getTekst_Soort(), this.getSoortTekst(), "soort", null, 0, 1, Tekst.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTekst_Tekst(), ecorePackage.getEString(), "tekst", null, 0, 1, Tekst.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTekst_HtmlTekst(), ecorePackage.getEString(), "htmlTekst", null, 0, 1, Tekst.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(tupleEClass, Tuple.class, "Tuple", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getTuple_Id(), ecorePackage.getELongObject(), "id", null, 0, 1, Tuple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getTuple_ObjectType(), this.getObjectType(), this.getObjectType_Tuples(), "objectType", null, 0, 1, Tuple.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTuple_RelatiefId(), ecorePackage.getEIntegerObject(), "relatiefId", null, 0, 1, Tuple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTuple_Code(), ecorePackage.getEString(), "code", null, 0, 1, Tuple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTuple_Naam(), ecorePackage.getEString(), "naam", null, 0, 1, Tuple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTuple_NaamMannelijk(), ecorePackage.getEString(), "naamMannelijk", null, 0, 1, Tuple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTuple_NaamVrouwelijk(), ecorePackage.getEString(), "naamVrouwelijk", null, 0, 1, Tuple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTuple_Omschrijving(), ecorePackage.getEString(), "omschrijving", null, 0, 1, Tuple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTuple_HeeftMaterieleHistorie(), ecorePackage.getEBooleanObject(), "heeftMaterieleHistorie", null, 0, 1, Tuple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTuple_DatumAanvangGeldigheid(), ecorePackage.getEIntegerObject(), "datumAanvangGeldigheid", null, 0, 1, Tuple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTuple_DatumEindeGeldigheid(), ecorePackage.getEIntegerObject(), "datumEindeGeldigheid", null, 0, 1, Tuple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTuple_CategorieSoortActie(), ecorePackage.getEString(), "categorieSoortActie", null, 0, 1, Tuple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTuple_CategorieSoortDocument(), ecorePackage.getEString(), "categorieSoortDocument", null, 0, 1, Tuple.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(typeEClass, Type.class, "Type", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(versieEClass, Versie.class, "Versie", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getVersie_Schema(), this.getSchema(), this.getSchema_Versies(), "schema", null, 0, 1, Versie.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getVersie_VersieTag(), this.getVersieTag(), "versieTag", null, 0, 1, Versie.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getVersie_ObjectTypes(), this.getObjectType(), this.getObjectType_Versie(), "objectTypes", null, 0, -1, Versie.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getVersie_AttribuutTypes(), this.getAttribuutType(), this.getAttribuutType_Versie(), "attribuutTypes", null, 0, -1, Versie.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getVersie_BerichtSjablonen(), this.getBerichtSjabloon(), this.getBerichtSjabloon_Versie(), "berichtSjablonen", null, 0, -1, Versie.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(waarderegelWaardeEClass, WaarderegelWaarde.class, "WaarderegelWaarde", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWaarderegelWaarde_BedrijfsRegel(), this.getBedrijfsRegel(), this.getBedrijfsRegel_Waarden(), "bedrijfsRegel", null, 0, 1, WaarderegelWaarde.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWaarderegelWaarde_Waarde(), ecorePackage.getEString(), "waarde", null, 0, 1, WaarderegelWaarde.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWaarderegelWaarde_Weergave(), ecorePackage.getEString(), "weergave", null, 0, 1, WaarderegelWaarde.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(soortTekstToTekstMapEntryEClass, Map.Entry.class, "SoortTekstToTekstMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSoortTekstToTekstMapEntry_Key(), this.getSoortTekst(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSoortTekstToTekstMapEntry_Value(), this.getTekst(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(applicatieEClass, Applicatie.class, "Applicatie", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getApplicatie_Formulieren(), this.getFormulier(), this.getFormulier_Applicatie(), "formulieren", null, 0, -1, Applicatie.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(formulierEClass, Formulier.class, "Formulier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getFormulier_Applicatie(), this.getApplicatie(), this.getApplicatie_Formulieren(), "applicatie", null, 0, 1, Formulier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getFormulier_Frames(), this.getFrame(), this.getFrame_Formulier(), "frames", null, 0, -1, Formulier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getFormulier_Bronnen(), this.getBron(), this.getBron_Formulier(), "bronnen", null, 0, -1, Formulier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(frameEClass, Frame.class, "Frame", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getFrame_Volgnummer(), ecorePackage.getEIntegerObject(), "volgnummer", null, 0, 1, Frame.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getFrame_Formulier(), this.getFormulier(), this.getFormulier_Frames(), "formulier", null, 0, 1, Frame.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getFrame_Bron(), this.getBron(), this.getBron_Frames(), "bron", null, 0, 1, Frame.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getFrame_Velden(), this.getFrameVeld(), this.getFrameVeld_Frame(), "velden", null, 0, -1, Frame.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(frameVeldEClass, FrameVeld.class, "FrameVeld", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getFrameVeld_Volgnummer(), ecorePackage.getEIntegerObject(), "volgnummer", null, 0, 1, FrameVeld.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getFrameVeld_Frame(), this.getFrame(), this.getFrame_Velden(), "frame", null, 0, 1, FrameVeld.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getFrameVeld_Attribuut(), this.getAttribuut(), null, "attribuut", null, 0, 1, FrameVeld.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(bronEClass, Bron.class, "Bron", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getBron_Meervoudsvorm(), ecorePackage.getEString(), "meervoudsvorm", null, 0, 1, Bron.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBron_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, Bron.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBron_Volgnummer(), ecorePackage.getEIntegerObject(), "volgnummer", null, 0, 1, Bron.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getBron_Formulier(), this.getFormulier(), this.getFormulier_Bronnen(), "formulier", null, 0, 1, Bron.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getBron_Frames(), this.getFrame(), this.getFrame_Bron(), "frames", null, 0, -1, Bron.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getBron_ObjectType(), this.getObjectType(), null, "objectType", null, 0, 1, Bron.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getBron_Link(), this.getAttribuut(), null, "link", null, 0, 1, Bron.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getBron_BronAttributen(), this.getBronAttribuut(), this.getBronAttribuut_Bron(), "bronAttributen", null, 0, -1, Bron.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(bronAttribuutEClass, BronAttribuut.class, "BronAttribuut", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getBronAttribuut_Bron(), this.getBron(), this.getBron_BronAttributen(), "bron", null, 0, 1, BronAttribuut.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getBronAttribuut_Attribuut(), this.getAttribuut(), null, "attribuut", null, 0, 1, BronAttribuut.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBronAttribuut_Volgnummer(), ecorePackage.getEIntegerObject(), "volgnummer", null, 0, 1, BronAttribuut.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(elementSoortEEnum, ElementSoort.class, "ElementSoort");
        addEEnumLiteral(elementSoortEEnum, ElementSoort.A);
        addEEnumLiteral(elementSoortEEnum, ElementSoort.AT);
        addEEnumLiteral(elementSoortEEnum, ElementSoort.B);
        addEEnumLiteral(elementSoortEEnum, ElementSoort.BR);
        addEEnumLiteral(elementSoortEEnum, ElementSoort.BS);
        addEEnumLiteral(elementSoortEEnum, ElementSoort.BT);
        addEEnumLiteral(elementSoortEEnum, ElementSoort.D);
        addEEnumLiteral(elementSoortEEnum, ElementSoort.G);
        addEEnumLiteral(elementSoortEEnum, ElementSoort.L);
        addEEnumLiteral(elementSoortEEnum, ElementSoort.OT);
        addEEnumLiteral(elementSoortEEnum, ElementSoort.S);
        addEEnumLiteral(elementSoortEEnum, ElementSoort.V);
        addEEnumLiteral(elementSoortEEnum, ElementSoort.W);

        initEEnum(soortBedrijfsRegelEEnum, SoortBedrijfsRegel.class, "SoortBedrijfsRegel");
        addEEnumLiteral(soortBedrijfsRegelEEnum, SoortBedrijfsRegel.ID);
        addEEnumLiteral(soortBedrijfsRegelEEnum, SoortBedrijfsRegel.UC);
        addEEnumLiteral(soortBedrijfsRegelEEnum, SoortBedrijfsRegel.WB);
        addEEnumLiteral(soortBedrijfsRegelEEnum, SoortBedrijfsRegel.VK);
        addEEnumLiteral(soortBedrijfsRegelEEnum, SoortBedrijfsRegel.OV);
        addEEnumLiteral(soortBedrijfsRegelEEnum, SoortBedrijfsRegel.LI);

        initEEnum(soortInhoudEEnum, SoortInhoud.class, "SoortInhoud");
        addEEnumLiteral(soortInhoudEEnum, SoortInhoud.S);
        addEEnumLiteral(soortInhoudEEnum, SoortInhoud.D);
        addEEnumLiteral(soortInhoudEEnum, SoortInhoud.X);

        initEEnum(soortTekstEEnum, SoortTekst.class, "SoortTekst");
        addEEnumLiteral(soortTekstEEnum, SoortTekst.DEF);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.DEFT);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.POP);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.MOB);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.UITT);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.CONT);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.PSA);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.REAT);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.AAN);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.LOG);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.TUP);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.XSD);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.XML);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.VOR);
        addEEnumLiteral(soortTekstEEnum, SoortTekst.BGR);

        initEEnum(versieTagEEnum, VersieTag.class, "VersieTag");
        addEEnumLiteral(versieTagEEnum, VersieTag.W);
        addEEnumLiteral(versieTagEEnum, VersieTag.V);
        addEEnumLiteral(versieTagEEnum, VersieTag.C);
        addEEnumLiteral(versieTagEEnum, VersieTag.B);
        addEEnumLiteral(versieTagEEnum, VersieTag.O);

        initEEnum(inSetOfModelEEnum, InSetOfModel.class, "InSetOfModel");
        addEEnumLiteral(inSetOfModelEEnum, InSetOfModel.SET);
        addEEnumLiteral(inSetOfModelEEnum, InSetOfModel.MODEL);
        addEEnumLiteral(inSetOfModelEEnum, InSetOfModel.BEIDE);

        initEEnum(historieEEnum, Historie.class, "Historie");
        addEEnumLiteral(historieEEnum, Historie.G);
        addEEnumLiteral(historieEEnum, Historie.F);
        addEEnumLiteral(historieEEnum, Historie.M);
        addEEnumLiteral(historieEEnum, Historie.B);
        addEEnumLiteral(historieEEnum, Historie.P);

        // Create resource
        createResource(eNS_URI);
    }

} // BmrPackageImpl
