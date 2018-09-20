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
import java.util.List;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Bron;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.Frame;

import nl.bzk.brp.ecore.bmr.FrameVeld;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Frame</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.FrameImpl#getVolgnummer <em>Volgnummer</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.FrameImpl#getFormulier <em>Formulier</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.FrameImpl#getBron <em>Bron</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.FrameImpl#getVelden <em>Velden</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FrameImpl extends ModelElementImpl implements Frame
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
     * The cached value of the '{@link #getBron() <em>Bron</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBron()
     * @generated
     * @ordered
     */
    protected Bron bron;

    /**
     * The cached value of the '{@link #getVelden() <em>Velden</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVelden()
     * @generated
     * @ordered
     */
    protected EList<FrameVeld> velden;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FrameImpl()
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
        return BmrPackage.Literals.FRAME;
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
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.FRAME__VOLGNUMMER, oldVolgnummer, volgnummer));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Formulier getFormulier()
    {
        if (eContainerFeatureID() != BmrPackage.FRAME__FORMULIER) return null;
        return (Formulier)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetFormulier(Formulier newFormulier, NotificationChain msgs)
    {
        msgs = eBasicSetContainer((InternalEObject)newFormulier, BmrPackage.FRAME__FORMULIER, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFormulier(Formulier newFormulier)
    {
        if (newFormulier != eInternalContainer() || (eContainerFeatureID() != BmrPackage.FRAME__FORMULIER && newFormulier != null))
        {
            if (EcoreUtil.isAncestor(this, (EObject)newFormulier))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newFormulier != null)
                msgs = ((InternalEObject)newFormulier).eInverseAdd(this, BmrPackage.FORMULIER__FRAMES, Formulier.class, msgs);
            msgs = basicSetFormulier(newFormulier, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.FRAME__FORMULIER, newFormulier, newFormulier));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Bron getBron()
    {
        if (bron != null && ((EObject)bron).eIsProxy())
        {
            InternalEObject oldBron = (InternalEObject)bron;
            bron = (Bron)eResolveProxy(oldBron);
            if (bron != oldBron)
            {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BmrPackage.FRAME__BRON, oldBron, bron));
            }
        }
        return bron;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Bron basicGetBron()
    {
        return bron;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBron(Bron newBron, NotificationChain msgs)
    {
        Bron oldBron = bron;
        bron = newBron;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BmrPackage.FRAME__BRON, oldBron, newBron);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBron(Bron newBron)
    {
        if (newBron != bron)
        {
            NotificationChain msgs = null;
            if (bron != null)
                msgs = ((InternalEObject)bron).eInverseRemove(this, BmrPackage.BRON__FRAMES, Bron.class, msgs);
            if (newBron != null)
                msgs = ((InternalEObject)newBron).eInverseAdd(this, BmrPackage.BRON__FRAMES, Bron.class, msgs);
            msgs = basicSetBron(newBron, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.FRAME__BRON, newBron, newBron));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public List<FrameVeld> getVelden()
    {
        if (velden == null)
        {
            velden = new EObjectContainmentWithInverseEList<FrameVeld>(FrameVeld.class, this, BmrPackage.FRAME__VELDEN, BmrPackage.FRAME_VELD__FRAME);
        }
        return velden;
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
            case BmrPackage.FRAME__FORMULIER:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetFormulier((Formulier)otherEnd, msgs);
            case BmrPackage.FRAME__BRON:
                if (bron != null)
                    msgs = ((InternalEObject)bron).eInverseRemove(this, BmrPackage.BRON__FRAMES, Bron.class, msgs);
                return basicSetBron((Bron)otherEnd, msgs);
            case BmrPackage.FRAME__VELDEN:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getVelden()).basicAdd(otherEnd, msgs);
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
            case BmrPackage.FRAME__FORMULIER:
                return basicSetFormulier(null, msgs);
            case BmrPackage.FRAME__BRON:
                return basicSetBron(null, msgs);
            case BmrPackage.FRAME__VELDEN:
                return ((InternalEList<?>)getVelden()).basicRemove(otherEnd, msgs);
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
            case BmrPackage.FRAME__FORMULIER:
                return eInternalContainer().eInverseRemove(this, BmrPackage.FORMULIER__FRAMES, Formulier.class, msgs);
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
            case BmrPackage.FRAME__VOLGNUMMER:
                return getVolgnummer();
            case BmrPackage.FRAME__FORMULIER:
                return getFormulier();
            case BmrPackage.FRAME__BRON:
                if (resolve) return getBron();
                return basicGetBron();
            case BmrPackage.FRAME__VELDEN:
                return getVelden();
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
            case BmrPackage.FRAME__VOLGNUMMER:
                setVolgnummer((Integer)newValue);
                return;
            case BmrPackage.FRAME__FORMULIER:
                setFormulier((Formulier)newValue);
                return;
            case BmrPackage.FRAME__BRON:
                setBron((Bron)newValue);
                return;
            case BmrPackage.FRAME__VELDEN:
                getVelden().clear();
                getVelden().addAll((Collection<? extends FrameVeld>)newValue);
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
            case BmrPackage.FRAME__VOLGNUMMER:
                setVolgnummer(VOLGNUMMER_EDEFAULT);
                return;
            case BmrPackage.FRAME__FORMULIER:
                setFormulier((Formulier)null);
                return;
            case BmrPackage.FRAME__BRON:
                setBron((Bron)null);
                return;
            case BmrPackage.FRAME__VELDEN:
                getVelden().clear();
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
            case BmrPackage.FRAME__VOLGNUMMER:
                return VOLGNUMMER_EDEFAULT == null ? volgnummer != null : !VOLGNUMMER_EDEFAULT.equals(volgnummer);
            case BmrPackage.FRAME__FORMULIER:
                return getFormulier() != null;
            case BmrPackage.FRAME__BRON:
                return bron != null;
            case BmrPackage.FRAME__VELDEN:
                return velden != null && !velden.isEmpty();
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

} //FrameImpl
