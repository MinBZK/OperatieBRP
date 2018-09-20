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

import java.util.Collection;

import nl.bzk.brp.ecore.bmr.Applicatie;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Formulier;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Applicatie</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.ApplicatieImpl#getFormulieren <em>Formulieren</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ApplicatieImpl extends ModelElementImpl implements Applicatie
{
    /**
     * The cached value of the '{@link #getFormulieren() <em>Formulieren</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormulieren()
     * @generated
     * @ordered
     */
    protected EList<Formulier> formulieren;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ApplicatieImpl()
    {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass()
    {
        return BmrPackage.Literals.APPLICATIE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Formulier> getFormulieren()
    {
        if (formulieren == null)
        {
            formulieren = new EObjectContainmentWithInverseEList<Formulier>(Formulier.class, this, BmrPackage.APPLICATIE__FORMULIEREN, BmrPackage.FORMULIER__APPLICATIE);
        }
        return formulieren;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
    {
        switch (featureID)
        {
            case BmrPackage.APPLICATIE__FORMULIEREN:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getFormulieren()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
    {
        switch (featureID)
        {
            case BmrPackage.APPLICATIE__FORMULIEREN:
                return ((InternalEList<?>)getFormulieren()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType)
    {
        switch (featureID)
        {
            case BmrPackage.APPLICATIE__FORMULIEREN:
                return getFormulieren();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue)
    {
        switch (featureID)
        {
            case BmrPackage.APPLICATIE__FORMULIEREN:
                getFormulieren().clear();
                getFormulieren().addAll((Collection<? extends Formulier>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID)
    {
        switch (featureID)
        {
            case BmrPackage.APPLICATIE__FORMULIEREN:
                getFormulieren().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID)
    {
        switch (featureID)
        {
            case BmrPackage.APPLICATIE__FORMULIEREN:
                return formulieren != null && !formulieren.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //ApplicatieImpl
