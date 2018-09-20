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
import nl.bzk.brp.ecore.bmr.Frame;
import nl.bzk.brp.ecore.bmr.FrameVeld;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Frame Veld</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.FrameVeldImpl#getVolgnummer <em>Volgnummer</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.FrameVeldImpl#getFrame <em>Frame</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.FrameVeldImpl#getAttribuut <em>Attribuut</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FrameVeldImpl extends ModelElementImpl implements FrameVeld
{
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
     * The cached value of the '{@link #getAttribuut() <em>Attribuut</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribuut()
     * @generated
     * @ordered
     */
    protected Attribuut attribuut;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FrameVeldImpl()
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
        return BmrPackage.Literals.FRAME_VELD;
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
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.FRAME_VELD__VOLGNUMMER, oldVolgnummer, volgnummer));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Frame getFrame()
    {
        if (eContainerFeatureID() != BmrPackage.FRAME_VELD__FRAME) return null;
        return (Frame)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetFrame(Frame newFrame, NotificationChain msgs)
    {
        msgs = eBasicSetContainer((InternalEObject)newFrame, BmrPackage.FRAME_VELD__FRAME, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFrame(Frame newFrame)
    {
        if (newFrame != eInternalContainer() || (eContainerFeatureID() != BmrPackage.FRAME_VELD__FRAME && newFrame != null))
        {
            if (EcoreUtil.isAncestor(this, (EObject)newFrame))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newFrame != null)
                msgs = ((InternalEObject)newFrame).eInverseAdd(this, BmrPackage.FRAME__VELDEN, Frame.class, msgs);
            msgs = basicSetFrame(newFrame, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.FRAME_VELD__FRAME, newFrame, newFrame));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Attribuut getAttribuut()
    {
        if (attribuut != null && ((EObject)attribuut).eIsProxy())
        {
            InternalEObject oldAttribuut = (InternalEObject)attribuut;
            attribuut = (Attribuut)eResolveProxy(oldAttribuut);
            if (attribuut != oldAttribuut)
            {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BmrPackage.FRAME_VELD__ATTRIBUUT, oldAttribuut, attribuut));
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
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.FRAME_VELD__ATTRIBUUT, oldAttribuut, attribuut));
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
            case BmrPackage.FRAME_VELD__FRAME:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetFrame((Frame)otherEnd, msgs);
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
            case BmrPackage.FRAME_VELD__FRAME:
                return basicSetFrame(null, msgs);
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
            case BmrPackage.FRAME_VELD__FRAME:
                return eInternalContainer().eInverseRemove(this, BmrPackage.FRAME__VELDEN, Frame.class, msgs);
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
            case BmrPackage.FRAME_VELD__VOLGNUMMER:
                return getVolgnummer();
            case BmrPackage.FRAME_VELD__FRAME:
                return getFrame();
            case BmrPackage.FRAME_VELD__ATTRIBUUT:
                if (resolve) return getAttribuut();
                return basicGetAttribuut();
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
            case BmrPackage.FRAME_VELD__VOLGNUMMER:
                setVolgnummer((Integer)newValue);
                return;
            case BmrPackage.FRAME_VELD__FRAME:
                setFrame((Frame)newValue);
                return;
            case BmrPackage.FRAME_VELD__ATTRIBUUT:
                setAttribuut((Attribuut)newValue);
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
            case BmrPackage.FRAME_VELD__VOLGNUMMER:
                setVolgnummer(VOLGNUMMER_EDEFAULT);
                return;
            case BmrPackage.FRAME_VELD__FRAME:
                setFrame((Frame)null);
                return;
            case BmrPackage.FRAME_VELD__ATTRIBUUT:
                setAttribuut((Attribuut)null);
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
            case BmrPackage.FRAME_VELD__VOLGNUMMER:
                return VOLGNUMMER_EDEFAULT == null ? volgnummer != null : !VOLGNUMMER_EDEFAULT.equals(volgnummer);
            case BmrPackage.FRAME_VELD__FRAME:
                return getFrame() != null;
            case BmrPackage.FRAME_VELD__ATTRIBUUT:
                return attribuut != null;
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

} //FrameVeldImpl
