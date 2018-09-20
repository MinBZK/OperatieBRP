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
package nl.bzk.brp.ecore.bmr.util;

import java.util.List;
import java.util.Map;

import nl.bzk.brp.ecore.bmr.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see nl.bzk.brp.ecore.bmr.BmrPackage
 * @generated
 */
public class BmrSwitch<T>
{
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static BmrPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BmrSwitch()
    {
        if (modelPackage == null)
        {
            modelPackage = BmrPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(EObject theEObject)
    {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(EClass theEClass, EObject theEObject)
    {
        if (theEClass.eContainer() == modelPackage)
        {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        }
        else
        {
            List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return
                eSuperTypes.isEmpty() ?
                    defaultCase(theEObject) :
                    doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(int classifierID, EObject theEObject)
    {
        switch (classifierID)
        {
            case BmrPackage.ATTRIBUUT:
            {
                Attribuut attribuut = (Attribuut)theEObject;
                T result = caseAttribuut(attribuut);
                if (result == null) result = caseGelaagdElement(attribuut);
                if (result == null) result = caseElement(attribuut);
                if (result == null) result = caseModelElement(attribuut);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.ATTRIBUUT_TYPE:
            {
                AttribuutType attribuutType = (AttribuutType)theEObject;
                T result = caseAttribuutType(attribuutType);
                if (result == null) result = caseType(attribuutType);
                if (result == null) result = caseGelaagdElement(attribuutType);
                if (result == null) result = caseElement(attribuutType);
                if (result == null) result = caseModelElement(attribuutType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.BASIS_TYPE:
            {
                BasisType basisType = (BasisType)theEObject;
                T result = caseBasisType(basisType);
                if (result == null) result = caseElement(basisType);
                if (result == null) result = caseModelElement(basisType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.BEDRIJFS_REGEL:
            {
                BedrijfsRegel bedrijfsRegel = (BedrijfsRegel)theEObject;
                T result = caseBedrijfsRegel(bedrijfsRegel);
                if (result == null) result = caseGelaagdElement(bedrijfsRegel);
                if (result == null) result = caseElement(bedrijfsRegel);
                if (result == null) result = caseModelElement(bedrijfsRegel);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.BERICHT_SJABLOON:
            {
                BerichtSjabloon berichtSjabloon = (BerichtSjabloon)theEObject;
                T result = caseBerichtSjabloon(berichtSjabloon);
                if (result == null) result = caseGelaagdElement(berichtSjabloon);
                if (result == null) result = caseElement(berichtSjabloon);
                if (result == null) result = caseModelElement(berichtSjabloon);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.DOMEIN:
            {
                Domein domein = (Domein)theEObject;
                T result = caseDomein(domein);
                if (result == null) result = caseElement(domein);
                if (result == null) result = caseModelElement(domein);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.ELEMENT:
            {
                Element element = (Element)theEObject;
                T result = caseElement(element);
                if (result == null) result = caseModelElement(element);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.GELAAGD_ELEMENT:
            {
                GelaagdElement gelaagdElement = (GelaagdElement)theEObject;
                T result = caseGelaagdElement(gelaagdElement);
                if (result == null) result = caseElement(gelaagdElement);
                if (result == null) result = caseModelElement(gelaagdElement);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.GROEP:
            {
                Groep groep = (Groep)theEObject;
                T result = caseGroep(groep);
                if (result == null) result = caseGelaagdElement(groep);
                if (result == null) result = caseElement(groep);
                if (result == null) result = caseModelElement(groep);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.LAAG:
            {
                Laag laag = (Laag)theEObject;
                T result = caseLaag(laag);
                if (result == null) result = caseElement(laag);
                if (result == null) result = caseModelElement(laag);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.META_REGISTER:
            {
                MetaRegister metaRegister = (MetaRegister)theEObject;
                T result = caseMetaRegister(metaRegister);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.MODEL_ELEMENT:
            {
                ModelElement modelElement = (ModelElement)theEObject;
                T result = caseModelElement(modelElement);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.OBJECT_TYPE:
            {
                ObjectType objectType = (ObjectType)theEObject;
                T result = caseObjectType(objectType);
                if (result == null) result = caseType(objectType);
                if (result == null) result = caseGelaagdElement(objectType);
                if (result == null) result = caseElement(objectType);
                if (result == null) result = caseModelElement(objectType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.SCHEMA:
            {
                Schema schema = (Schema)theEObject;
                T result = caseSchema(schema);
                if (result == null) result = caseElement(schema);
                if (result == null) result = caseModelElement(schema);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.TEKST:
            {
                Tekst tekst = (Tekst)theEObject;
                T result = caseTekst(tekst);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.TUPLE:
            {
                Tuple tuple = (Tuple)theEObject;
                T result = caseTuple(tuple);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.TYPE:
            {
                Type type = (Type)theEObject;
                T result = caseType(type);
                if (result == null) result = caseGelaagdElement(type);
                if (result == null) result = caseElement(type);
                if (result == null) result = caseModelElement(type);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.VERSIE:
            {
                Versie versie = (Versie)theEObject;
                T result = caseVersie(versie);
                if (result == null) result = caseElement(versie);
                if (result == null) result = caseModelElement(versie);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.WAARDEREGEL_WAARDE:
            {
                WaarderegelWaarde waarderegelWaarde = (WaarderegelWaarde)theEObject;
                T result = caseWaarderegelWaarde(waarderegelWaarde);
                if (result == null) result = caseGelaagdElement(waarderegelWaarde);
                if (result == null) result = caseElement(waarderegelWaarde);
                if (result == null) result = caseModelElement(waarderegelWaarde);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY:
            {
                @SuppressWarnings("unchecked") Map.Entry<SoortTekst, Tekst> soortTekstToTekstMapEntry = (Map.Entry<SoortTekst, Tekst>)theEObject;
                T result = caseSoortTekstToTekstMapEntry(soortTekstToTekstMapEntry);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.APPLICATIE:
            {
                Applicatie applicatie = (Applicatie)theEObject;
                T result = caseApplicatie(applicatie);
                if (result == null) result = caseModelElement(applicatie);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.FORMULIER:
            {
                Formulier formulier = (Formulier)theEObject;
                T result = caseFormulier(formulier);
                if (result == null) result = caseModelElement(formulier);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.FRAME:
            {
                Frame frame = (Frame)theEObject;
                T result = caseFrame(frame);
                if (result == null) result = caseModelElement(frame);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.FRAME_VELD:
            {
                FrameVeld frameVeld = (FrameVeld)theEObject;
                T result = caseFrameVeld(frameVeld);
                if (result == null) result = caseModelElement(frameVeld);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.BRON:
            {
                Bron bron = (Bron)theEObject;
                T result = caseBron(bron);
                if (result == null) result = caseModelElement(bron);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case BmrPackage.BRON_ATTRIBUUT:
            {
                BronAttribuut bronAttribuut = (BronAttribuut)theEObject;
                T result = caseBronAttribuut(bronAttribuut);
                if (result == null) result = caseModelElement(bronAttribuut);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Attribuut</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Attribuut</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAttribuut(Attribuut object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Attribuut Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Attribuut Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAttribuutType(AttribuutType object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Basis Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Basis Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBasisType(BasisType object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Bedrijfs Regel</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Bedrijfs Regel</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBedrijfsRegel(BedrijfsRegel object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Bericht Sjabloon</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Bericht Sjabloon</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBerichtSjabloon(BerichtSjabloon object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Domein</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Domein</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDomein(Domein object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseElement(Element object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Gelaagd Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Gelaagd Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGelaagdElement(GelaagdElement object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Groep</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Groep</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGroep(Groep object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Laag</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Laag</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLaag(Laag object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Meta Register</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Meta Register</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMetaRegister(MetaRegister object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Model Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Model Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseModelElement(ModelElement object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Object Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Object Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseObjectType(ObjectType object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Schema</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Schema</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSchema(Schema object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Tekst</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Tekst</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTekst(Tekst object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Tuple</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Tuple</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTuple(Tuple object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseType(Type object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Versie</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Versie</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseVersie(Versie object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Waarderegel Waarde</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Waarderegel Waarde</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWaarderegelWaarde(WaarderegelWaarde object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Soort Tekst To Tekst Map Entry</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Soort Tekst To Tekst Map Entry</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSoortTekstToTekstMapEntry(Map.Entry<SoortTekst, Tekst> object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Applicatie</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Applicatie</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseApplicatie(Applicatie object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Formulier</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Formulier</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormulier(Formulier object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Frame</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Frame</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFrame(Frame object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Frame Veld</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Frame Veld</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFrameVeld(FrameVeld object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Bron</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Bron</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBron(Bron object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Bron Attribuut</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Bron Attribuut</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBronAttribuut(BronAttribuut object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(EObject object)
    {
        return null;
    }

} //BmrSwitch
