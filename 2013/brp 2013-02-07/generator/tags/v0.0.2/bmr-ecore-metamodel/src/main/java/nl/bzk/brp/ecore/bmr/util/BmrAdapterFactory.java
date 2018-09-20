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

import java.util.Map;

import nl.bzk.brp.ecore.bmr.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see nl.bzk.brp.ecore.bmr.BmrPackage
 * @generated
 */
public class BmrAdapterFactory extends AdapterFactoryImpl
{
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static BmrPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BmrAdapterFactory()
    {
        if (modelPackage == null)
        {
            modelPackage = BmrPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object)
    {
        if (object == modelPackage)
        {
            return true;
        }
        if (object instanceof EObject)
        {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected BmrSwitch<Adapter> modelSwitch =
        new BmrSwitch<Adapter>()
        {
            @Override
            public Adapter caseAttribuut(Attribuut object)
            {
                return createAttribuutAdapter();
            }
            @Override
            public Adapter caseAttribuutType(AttribuutType object)
            {
                return createAttribuutTypeAdapter();
            }
            @Override
            public Adapter caseBasisType(BasisType object)
            {
                return createBasisTypeAdapter();
            }
            @Override
            public Adapter caseBedrijfsRegel(BedrijfsRegel object)
            {
                return createBedrijfsRegelAdapter();
            }
            @Override
            public Adapter caseBerichtSjabloon(BerichtSjabloon object)
            {
                return createBerichtSjabloonAdapter();
            }
            @Override
            public Adapter caseDomein(Domein object)
            {
                return createDomeinAdapter();
            }
            @Override
            public Adapter caseElement(Element object)
            {
                return createElementAdapter();
            }
            @Override
            public Adapter caseGelaagdElement(GelaagdElement object)
            {
                return createGelaagdElementAdapter();
            }
            @Override
            public Adapter caseGroep(Groep object)
            {
                return createGroepAdapter();
            }
            @Override
            public Adapter caseLaag(Laag object)
            {
                return createLaagAdapter();
            }
            @Override
            public Adapter caseMetaRegister(MetaRegister object)
            {
                return createMetaRegisterAdapter();
            }
            @Override
            public Adapter caseModelElement(ModelElement object)
            {
                return createModelElementAdapter();
            }
            @Override
            public Adapter caseObjectType(ObjectType object)
            {
                return createObjectTypeAdapter();
            }
            @Override
            public Adapter caseSchema(Schema object)
            {
                return createSchemaAdapter();
            }
            @Override
            public Adapter caseTekst(Tekst object)
            {
                return createTekstAdapter();
            }
            @Override
            public Adapter caseTuple(Tuple object)
            {
                return createTupleAdapter();
            }
            @Override
            public Adapter caseType(Type object)
            {
                return createTypeAdapter();
            }
            @Override
            public Adapter caseVersie(Versie object)
            {
                return createVersieAdapter();
            }
            @Override
            public Adapter caseWaarderegelWaarde(WaarderegelWaarde object)
            {
                return createWaarderegelWaardeAdapter();
            }
            @Override
            public Adapter caseSoortTekstToTekstMapEntry(Map.Entry<SoortTekst, Tekst> object)
            {
                return createSoortTekstToTekstMapEntryAdapter();
            }
            @Override
            public Adapter caseApplicatie(Applicatie object)
            {
                return createApplicatieAdapter();
            }
            @Override
            public Adapter caseFormulier(Formulier object)
            {
                return createFormulierAdapter();
            }
            @Override
            public Adapter caseFrame(Frame object)
            {
                return createFrameAdapter();
            }
            @Override
            public Adapter caseFrameVeld(FrameVeld object)
            {
                return createFrameVeldAdapter();
            }
            @Override
            public Adapter caseBron(Bron object)
            {
                return createBronAdapter();
            }
            @Override
            public Adapter caseBronAttribuut(BronAttribuut object)
            {
                return createBronAttribuutAdapter();
            }
            @Override
            public Adapter defaultCase(EObject object)
            {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target)
    {
        return modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Attribuut <em>Attribuut</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Attribuut
     * @generated
     */
    public Adapter createAttribuutAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.AttribuutType <em>Attribuut Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.AttribuutType
     * @generated
     */
    public Adapter createAttribuutTypeAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.BasisType <em>Basis Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.BasisType
     * @generated
     */
    public Adapter createBasisTypeAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel <em>Bedrijfs Regel</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.BedrijfsRegel
     * @generated
     */
    public Adapter createBedrijfsRegelAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.BerichtSjabloon <em>Bericht Sjabloon</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.BerichtSjabloon
     * @generated
     */
    public Adapter createBerichtSjabloonAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Domein <em>Domein</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Domein
     * @generated
     */
    public Adapter createDomeinAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Element <em>Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Element
     * @generated
     */
    public Adapter createElementAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.GelaagdElement <em>Gelaagd Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.GelaagdElement
     * @generated
     */
    public Adapter createGelaagdElementAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Groep <em>Groep</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Groep
     * @generated
     */
    public Adapter createGroepAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Laag <em>Laag</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Laag
     * @generated
     */
    public Adapter createLaagAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.MetaRegister <em>Meta Register</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.MetaRegister
     * @generated
     */
    public Adapter createMetaRegisterAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.ModelElement <em>Model Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.ModelElement
     * @generated
     */
    public Adapter createModelElementAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.ObjectType <em>Object Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.ObjectType
     * @generated
     */
    public Adapter createObjectTypeAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Schema <em>Schema</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Schema
     * @generated
     */
    public Adapter createSchemaAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Tekst <em>Tekst</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Tekst
     * @generated
     */
    public Adapter createTekstAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Tuple <em>Tuple</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Tuple
     * @generated
     */
    public Adapter createTupleAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Type <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Type
     * @generated
     */
    public Adapter createTypeAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Versie <em>Versie</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Versie
     * @generated
     */
    public Adapter createVersieAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde <em>Waarderegel Waarde</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.WaarderegelWaarde
     * @generated
     */
    public Adapter createWaarderegelWaardeAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Soort Tekst To Tekst Map Entry</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see java.util.Map.Entry
     * @generated
     */
    public Adapter createSoortTekstToTekstMapEntryAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Applicatie <em>Applicatie</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Applicatie
     * @generated
     */
    public Adapter createApplicatieAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Formulier <em>Formulier</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Formulier
     * @generated
     */
    public Adapter createFormulierAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Frame <em>Frame</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Frame
     * @generated
     */
    public Adapter createFrameAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.FrameVeld <em>Frame Veld</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.FrameVeld
     * @generated
     */
    public Adapter createFrameVeldAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.Bron <em>Bron</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.Bron
     * @generated
     */
    public Adapter createBronAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link nl.bzk.brp.ecore.bmr.BronAttribuut <em>Bron Attribuut</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see nl.bzk.brp.ecore.bmr.BronAttribuut
     * @generated
     */
    public Adapter createBronAttribuutAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter()
    {
        return null;
    }

} //BmrAdapterFactory
