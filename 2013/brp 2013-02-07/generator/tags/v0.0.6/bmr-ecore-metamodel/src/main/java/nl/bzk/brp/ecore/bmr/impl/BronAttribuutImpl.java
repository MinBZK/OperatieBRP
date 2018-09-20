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

import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Bron;
import nl.bzk.brp.ecore.bmr.BronAttribuut;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bron Attribuut</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BronAttribuutImpl#getBron <em>Bron</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BronAttribuutImpl#getAttribuut <em>Attribuut</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BronAttribuutImpl#getVolgnummer <em>Volgnummer</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BronAttribuutImpl extends ModelElementImpl implements BronAttribuut
{
    /**
     * The cached value of the '{@link #getAttribuut() <em>Attribuut</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribuut()
     * @generated
     * @ordered
     */
    protected Attribuut attribuut;

    /**
     * The default value of the '{@link #getVolgnummer() <em>Volgnummer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVolgnummer()
     * @generated
     * @ordered
     */
    protected static final Integer VOLGNUMMER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getVolgnummer() <em>Volgnummer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVolgnummer()
     * @generated
     * @ordered
     */
    protected Integer volgnummer = VOLGNUMMER_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected BronAttribuutImpl()
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
        return BmrPackage.Literals.BRON_ATTRIBUUT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Bron getBron()
    {
        if (eContainerFeatureID() != BmrPackage.BRON_ATTRIBUUT__BRON) return null;
        return (Bron)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBron(Bron newBron, NotificationChain msgs)
    {
        msgs = eBasicSetContainer((InternalEObject)newBron, BmrPackage.BRON_ATTRIBUUT__BRON, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBron(Bron newBron)
    {
        if (newBron != eInternalContainer() || (eContainerFeatureID() != BmrPackage.BRON_ATTRIBUUT__BRON && newBron != null))
        {
            if (EcoreUtil.isAncestor(this, newBron))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newBron != null)
                msgs = ((InternalEObject)newBron).eInverseAdd(this, BmrPackage.BRON__BRON_ATTRIBUTEN, Bron.class, msgs);
            msgs = basicSetBron(newBron, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.BRON_ATTRIBUUT__BRON, newBron, newBron));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Attribuut getAttribuut()
    {
        if (attribuut != null && attribuut.eIsProxy())
        {
            InternalEObject oldAttribuut = (InternalEObject)attribuut;
            attribuut = (Attribuut)eResolveProxy(oldAttribuut);
            if (attribuut != oldAttribuut)
            {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BmrPackage.BRON_ATTRIBUUT__ATTRIBUUT, oldAttribuut, attribuut));
            }
        }
        return attribuut;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Attribuut basicGetAttribuut()
    {
        return attribuut;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribuut(Attribuut newAttribuut)
    {
        Attribuut oldAttribuut = attribuut;
        attribuut = newAttribuut;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.BRON_ATTRIBUUT__ATTRIBUUT, oldAttribuut, attribuut));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Integer getVolgnummer()
    {
        return volgnummer;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVolgnummer(Integer newVolgnummer)
    {
        Integer oldVolgnummer = volgnummer;
        volgnummer = newVolgnummer;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.BRON_ATTRIBUUT__VOLGNUMMER, oldVolgnummer, volgnummer));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
    {
        switch (featureID)
        {
            case BmrPackage.BRON_ATTRIBUUT__BRON:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetBron((Bron)otherEnd, msgs);
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
            case BmrPackage.BRON_ATTRIBUUT__BRON:
                return basicSetBron(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs)
    {
        switch (eContainerFeatureID())
        {
            case BmrPackage.BRON_ATTRIBUUT__BRON:
                return eInternalContainer().eInverseRemove(this, BmrPackage.BRON__BRON_ATTRIBUTEN, Bron.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
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
            case BmrPackage.BRON_ATTRIBUUT__BRON:
                return getBron();
            case BmrPackage.BRON_ATTRIBUUT__ATTRIBUUT:
                if (resolve) return getAttribuut();
                return basicGetAttribuut();
            case BmrPackage.BRON_ATTRIBUUT__VOLGNUMMER:
                return getVolgnummer();
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
            case BmrPackage.BRON_ATTRIBUUT__BRON:
                setBron((Bron)newValue);
                return;
            case BmrPackage.BRON_ATTRIBUUT__ATTRIBUUT:
                setAttribuut((Attribuut)newValue);
                return;
            case BmrPackage.BRON_ATTRIBUUT__VOLGNUMMER:
                setVolgnummer((Integer)newValue);
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
            case BmrPackage.BRON_ATTRIBUUT__BRON:
                setBron((Bron)null);
                return;
            case BmrPackage.BRON_ATTRIBUUT__ATTRIBUUT:
                setAttribuut((Attribuut)null);
                return;
            case BmrPackage.BRON_ATTRIBUUT__VOLGNUMMER:
                setVolgnummer(VOLGNUMMER_EDEFAULT);
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
            case BmrPackage.BRON_ATTRIBUUT__BRON:
                return getBron() != null;
            case BmrPackage.BRON_ATTRIBUUT__ATTRIBUUT:
                return attribuut != null;
            case BmrPackage.BRON_ATTRIBUUT__VOLGNUMMER:
                return VOLGNUMMER_EDEFAULT == null ? volgnummer != null : !VOLGNUMMER_EDEFAULT.equals(volgnummer);
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
        result.append(" (volgnummer: ");
        result.append(volgnummer);
        result.append(')');
        return result.toString();
    }

} //BronAttribuutImpl
