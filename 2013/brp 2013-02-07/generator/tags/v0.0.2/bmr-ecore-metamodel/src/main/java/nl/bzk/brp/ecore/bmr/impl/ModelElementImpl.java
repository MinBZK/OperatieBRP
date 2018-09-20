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

import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.ModelElement;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.ModelElementImpl#getId <em>Id</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.ModelElementImpl#getNaam <em>Naam</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ModelElementImpl extends EObjectImpl implements ModelElement
{
    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final Integer ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected Integer id = ID_EDEFAULT;

    /**
     * The default value of the '{@link #getNaam() <em>Naam</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNaam()
     * @generated
     * @ordered
     */
    protected static final String NAAM_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNaam() <em>Naam</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNaam()
     * @generated
     * @ordered
     */
    protected String naam = NAAM_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ModelElementImpl()
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
        return BmrPackage.Literals.MODEL_ELEMENT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(Integer newId)
    {
        Integer oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.MODEL_ELEMENT__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getNaam()
    {
        return naam;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNaam(String newNaam)
    {
        String oldNaam = naam;
        naam = newNaam;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.MODEL_ELEMENT__NAAM, oldNaam, naam));
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
            case BmrPackage.MODEL_ELEMENT__ID:
                return getId();
            case BmrPackage.MODEL_ELEMENT__NAAM:
                return getNaam();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue)
    {
        switch (featureID)
        {
            case BmrPackage.MODEL_ELEMENT__ID:
                setId((Integer)newValue);
                return;
            case BmrPackage.MODEL_ELEMENT__NAAM:
                setNaam((String)newValue);
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
            case BmrPackage.MODEL_ELEMENT__ID:
                setId(ID_EDEFAULT);
                return;
            case BmrPackage.MODEL_ELEMENT__NAAM:
                setNaam(NAAM_EDEFAULT);
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
            case BmrPackage.MODEL_ELEMENT__ID:
                return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case BmrPackage.MODEL_ELEMENT__NAAM:
                return NAAM_EDEFAULT == null ? naam != null : !NAAM_EDEFAULT.equals(naam);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString()
    {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (id: ");
        result.append(id);
        result.append(", naam: ");
        result.append(naam);
        result.append(')');
        return result.toString();
    }

} //ModelElementImpl
