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
import nl.bzk.brp.ecore.bmr.SoortTekst;
import nl.bzk.brp.ecore.bmr.Tekst;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Soort Tekst To Tekst Map Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.SoortTekstToTekstMapEntryImpl#getTypedKey <em>Key</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.SoortTekstToTekstMapEntryImpl#getTypedValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SoortTekstToTekstMapEntryImpl extends EObjectImpl implements BasicEMap.Entry<SoortTekst,Tekst>
{
    /**
     * The default value of the '{@link #getTypedKey() <em>Key</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypedKey()
     * @generated
     * @ordered
     */
    protected static final SoortTekst KEY_EDEFAULT = SoortTekst.DEF;

    /**
     * The cached value of the '{@link #getTypedKey() <em>Key</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypedKey()
     * @generated
     * @ordered
     */
    protected SoortTekst key = KEY_EDEFAULT;

    /**
     * The cached value of the '{@link #getTypedValue() <em>Value</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypedValue()
     * @generated
     * @ordered
     */
    protected Tekst value;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SoortTekstToTekstMapEntryImpl()
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
        return BmrPackage.Literals.SOORT_TEKST_TO_TEKST_MAP_ENTRY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SoortTekst getTypedKey()
    {
        return key;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTypedKey(SoortTekst newKey)
    {
        SoortTekst oldKey = key;
        key = newKey == null ? KEY_EDEFAULT : newKey;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY__KEY, oldKey, key));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Tekst getTypedValue()
    {
        if (value != null && value.eIsProxy())
        {
            InternalEObject oldValue = (InternalEObject)value;
            value = (Tekst)eResolveProxy(oldValue);
            if (value != oldValue)
            {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY__VALUE, oldValue, value));
            }
        }
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Tekst basicGetTypedValue()
    {
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTypedValue(Tekst newValue)
    {
        Tekst oldValue = value;
        value = newValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY__VALUE, oldValue, value));
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
            case BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY__KEY:
                return getTypedKey();
            case BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY__VALUE:
                if (resolve) return getTypedValue();
                return basicGetTypedValue();
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
            case BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY__KEY:
                setTypedKey((SoortTekst)newValue);
                return;
            case BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY__VALUE:
                setTypedValue((Tekst)newValue);
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
            case BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY__KEY:
                setTypedKey(KEY_EDEFAULT);
                return;
            case BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY__VALUE:
                setTypedValue((Tekst)null);
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
            case BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY__KEY:
                return key != KEY_EDEFAULT;
            case BmrPackage.SOORT_TEKST_TO_TEKST_MAP_ENTRY__VALUE:
                return value != null;
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
        result.append(" (key: ");
        result.append(key);
        result.append(')');
        return result.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected int hash = -1;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getHash()
    {
        if (hash == -1)
        {
            Object theKey = getKey();
            hash = (theKey == null ? 0 : theKey.hashCode());
        }
        return hash;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHash(int hash)
    {
        this.hash = hash;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SoortTekst getKey()
    {
        return getTypedKey();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setKey(SoortTekst key)
    {
        setTypedKey(key);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Tekst getValue()
    {
        return getTypedValue();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Tekst setValue(Tekst value)
    {
        Tekst oldValue = getValue();
        setTypedValue(value);
        return oldValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    public EMap<SoortTekst, Tekst> getEMap()
    {
        EObject container = eContainer();
        return container == null ? null : (EMap<SoortTekst, Tekst>)container.eGet(eContainmentFeature());
    }

} //SoortTekstToTekstMapEntryImpl
