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

import nl.bzk.brp.ecore.bmr.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BmrFactoryImpl extends EFactoryImpl implements BmrFactory
{
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static BmrFactory init()
    {
        try
        {
            BmrFactory theBmrFactory = (BmrFactory)EPackage.Registry.INSTANCE.getEFactory("http://brp.bzk.nl/schema/bmr.ecore"); 
            if (theBmrFactory != null)
            {
                return theBmrFactory;
            }
        }
        catch (Exception exception)
        {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new BmrFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BmrFactoryImpl()
    {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass)
    {
        switch (eClass.getClassifierID())
        {
            case BmrPackage.ATTRIBUUT: return (EObject)createAttribuut();
            case BmrPackage.ATTRIBUUT_TYPE: return (EObject)createAttribuutType();
            case BmrPackage.BASIS_TYPE: return (EObject)createBasisType();
            case BmrPackage.BEDRIJFS_REGEL: return (EObject)createBedrijfsRegel();
            case BmrPackage.BERICHT_SJABLOON: return (EObject)createBerichtSjabloon();
            case BmrPackage.DOMEIN: return (EObject)createDomein();
            case BmrPackage.GROEP: return (EObject)createGroep();
            case BmrPackage.LAAG: return (EObject)createLaag();
            case BmrPackage.META_REGISTER: return (EObject)createMetaRegister();
            case BmrPackage.OBJECT_TYPE: return (EObject)createObjectType();
            case BmrPackage.SCHEMA: return (EObject)createSchema();
            case BmrPackage.TEKST: return (EObject)createTekst();
            case BmrPackage.TUPLE: return (EObject)createTuple();
            case BmrPackage.VERSIE: return (EObject)createVersie();
            case BmrPackage.WAARDEREGEL_WAARDE: return (EObject)createWaarderegelWaarde();
            case BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY: return (EObject)createSoortTekstToTekstMapEntry();
            case BmrPackage.APPLICATIE: return (EObject)createApplicatie();
            case BmrPackage.FORMULIER: return (EObject)createFormulier();
            case BmrPackage.FRAME: return (EObject)createFrame();
            case BmrPackage.FRAME_VELD: return (EObject)createFrameVeld();
            case BmrPackage.BRON: return (EObject)createBron();
            case BmrPackage.BRON_ATTRIBUUT: return (EObject)createBronAttribuut();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue)
    {
        switch (eDataType.getClassifierID())
        {
            case BmrPackage.ELEMENT_SOORT:
                return createElementSoortFromString(eDataType, initialValue);
            case BmrPackage.SOORT_BEDRIJFS_REGEL:
                return createSoortBedrijfsRegelFromString(eDataType, initialValue);
            case BmrPackage.SOORT_INHOUD:
                return createSoortInhoudFromString(eDataType, initialValue);
            case BmrPackage.SOORT_TEKST:
                return createSoortTekstFromString(eDataType, initialValue);
            case BmrPackage.VERSIE_TAG:
                return createVersieTagFromString(eDataType, initialValue);
            case BmrPackage.IN_SET_OF_MODEL:
                return createInSetOfModelFromString(eDataType, initialValue);
            case BmrPackage.HISTORIE:
                return createHistorieFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue)
    {
        switch (eDataType.getClassifierID())
        {
            case BmrPackage.ELEMENT_SOORT:
                return convertElementSoortToString(eDataType, instanceValue);
            case BmrPackage.SOORT_BEDRIJFS_REGEL:
                return convertSoortBedrijfsRegelToString(eDataType, instanceValue);
            case BmrPackage.SOORT_INHOUD:
                return convertSoortInhoudToString(eDataType, instanceValue);
            case BmrPackage.SOORT_TEKST:
                return convertSoortTekstToString(eDataType, instanceValue);
            case BmrPackage.VERSIE_TAG:
                return convertVersieTagToString(eDataType, instanceValue);
            case BmrPackage.IN_SET_OF_MODEL:
                return convertInSetOfModelToString(eDataType, instanceValue);
            case BmrPackage.HISTORIE:
                return convertHistorieToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Attribuut createAttribuut()
    {
        AttribuutImpl attribuut = new AttribuutImpl();
        return attribuut;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttribuutType createAttribuutType()
    {
        AttribuutTypeImpl attribuutType = new AttribuutTypeImpl();
        return attribuutType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BasisType createBasisType()
    {
        BasisTypeImpl basisType = new BasisTypeImpl();
        return basisType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BedrijfsRegel createBedrijfsRegel()
    {
        BedrijfsRegelImpl bedrijfsRegel = new BedrijfsRegelImpl();
        return bedrijfsRegel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BerichtSjabloon createBerichtSjabloon()
    {
        BerichtSjabloonImpl berichtSjabloon = new BerichtSjabloonImpl();
        return berichtSjabloon;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Domein createDomein()
    {
        DomeinImpl domein = new DomeinImpl();
        return domein;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Groep createGroep()
    {
        GroepImpl groep = new GroepImpl();
        return groep;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Laag createLaag()
    {
        LaagImpl laag = new LaagImpl();
        return laag;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MetaRegister createMetaRegister()
    {
        MetaRegisterImpl metaRegister = new MetaRegisterImpl();
        return metaRegister;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ObjectType createObjectType()
    {
        ObjectTypeImpl objectType = new ObjectTypeImpl();
        return objectType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Schema createSchema()
    {
        SchemaImpl schema = new SchemaImpl();
        return schema;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Tekst createTekst()
    {
        TekstImpl tekst = new TekstImpl();
        return tekst;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Tuple createTuple()
    {
        TupleImpl tuple = new TupleImpl();
        return tuple;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Versie createVersie()
    {
        VersieImpl versie = new VersieImpl();
        return versie;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WaarderegelWaarde createWaarderegelWaarde()
    {
        WaarderegelWaardeImpl waarderegelWaarde = new WaarderegelWaardeImpl();
        return waarderegelWaarde;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Map.Entry<SoortTekst, Tekst> createSoortTekstToTekstMapEntry()
    {
        SoortTekstToTekstMapEntryImpl soortTekstToTekstMapEntry = new SoortTekstToTekstMapEntryImpl();
        return soortTekstToTekstMapEntry;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Applicatie createApplicatie()
    {
        ApplicatieImpl applicatie = new ApplicatieImpl();
        return applicatie;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Formulier createFormulier()
    {
        FormulierImpl formulier = new FormulierImpl();
        return formulier;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Frame createFrame()
    {
        FrameImpl frame = new FrameImpl();
        return frame;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FrameVeld createFrameVeld()
    {
        FrameVeldImpl frameVeld = new FrameVeldImpl();
        return frameVeld;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Bron createBron()
    {
        BronImpl bron = new BronImpl();
        return bron;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BronAttribuut createBronAttribuut()
    {
        BronAttribuutImpl bronAttribuut = new BronAttribuutImpl();
        return bronAttribuut;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ElementSoort createElementSoortFromString(EDataType eDataType, String initialValue)
    {
        ElementSoort result = ElementSoort.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertElementSoortToString(EDataType eDataType, Object instanceValue)
    {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SoortBedrijfsRegel createSoortBedrijfsRegelFromString(EDataType eDataType, String initialValue)
    {
        SoortBedrijfsRegel result = SoortBedrijfsRegel.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSoortBedrijfsRegelToString(EDataType eDataType, Object instanceValue)
    {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SoortInhoud createSoortInhoudFromString(EDataType eDataType, String initialValue)
    {
        SoortInhoud result = SoortInhoud.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSoortInhoudToString(EDataType eDataType, Object instanceValue)
    {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SoortTekst createSoortTekstFromString(EDataType eDataType, String initialValue)
    {
        SoortTekst result = SoortTekst.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSoortTekstToString(EDataType eDataType, Object instanceValue)
    {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public VersieTag createVersieTagFromString(EDataType eDataType, String initialValue)
    {
        VersieTag result = VersieTag.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertVersieTagToString(EDataType eDataType, Object instanceValue)
    {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public InSetOfModel createInSetOfModelFromString(EDataType eDataType, String initialValue)
    {
        InSetOfModel result = InSetOfModel.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertInSetOfModelToString(EDataType eDataType, Object instanceValue)
    {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Historie createHistorieFromString(EDataType eDataType, String initialValue)
    {
        Historie result = Historie.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertHistorieToString(EDataType eDataType, Object instanceValue)
    {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BmrPackage getBmrPackage()
    {
        return (BmrPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static BmrPackage getPackage()
    {
        return BmrPackage.eINSTANCE;
    }

} //BmrFactoryImpl
