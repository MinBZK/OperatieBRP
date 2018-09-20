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
import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Groep;
import nl.bzk.brp.ecore.bmr.Historie;
import nl.bzk.brp.ecore.bmr.InSetOfModel;
import nl.bzk.brp.ecore.bmr.ObjectType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Groep</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.GroepImpl#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.GroepImpl#getAttributen <em>Attributen</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.GroepImpl#getHistorieVastleggen <em>Historie Vastleggen</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.GroepImpl#isVerplicht <em>Verplicht</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GroepImpl extends GelaagdElementImpl implements Groep
{
    /**
     * The cached value of the '{@link #getAttributen() <em>Attributen</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributen()
     * @generated
     * @ordered
     */
    protected EList<Attribuut> attributen;

    /**
     * The default value of the '{@link #getHistorieVastleggen() <em>Historie Vastleggen</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHistorieVastleggen()
     * @generated
     * @ordered
     */
    protected static final Historie HISTORIE_VASTLEGGEN_EDEFAULT = Historie.G;
    /**
     * The cached value of the '{@link #getHistorieVastleggen() <em>Historie Vastleggen</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHistorieVastleggen()
     * @generated
     * @ordered
     */
    protected Historie historieVastleggen = HISTORIE_VASTLEGGEN_EDEFAULT;

    /**
     * The default value of the '{@link #isVerplicht() <em>Verplicht</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isVerplicht()
     * @generated
     * @ordered
     */
    protected static final boolean VERPLICHT_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isVerplicht() <em>Verplicht</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isVerplicht()
     * @generated
     * @ordered
     */
    protected boolean verplicht = VERPLICHT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GroepImpl()
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
        return BmrPackage.Literals.GROEP;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ObjectType getObjectType()
    {
        if (eContainerFeatureID() != BmrPackage.GROEP__OBJECT_TYPE) return null;
        return (ObjectType)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetObjectType(ObjectType newObjectType, NotificationChain msgs)
    {
        msgs = eBasicSetContainer((InternalEObject)newObjectType, BmrPackage.GROEP__OBJECT_TYPE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setObjectType(ObjectType newObjectType)
    {
        if (newObjectType != eInternalContainer() || (eContainerFeatureID() != BmrPackage.GROEP__OBJECT_TYPE && newObjectType != null))
        {
            if (EcoreUtil.isAncestor(this, (EObject)newObjectType))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newObjectType != null)
                msgs = ((InternalEObject)newObjectType).eInverseAdd(this, BmrPackage.OBJECT_TYPE__GROEPEN, ObjectType.class, msgs);
            msgs = basicSetObjectType(newObjectType, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.GROEP__OBJECT_TYPE, newObjectType, newObjectType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public List<Attribuut> getAttributen()
    {
        if (attributen == null)
        {
            attributen = new EObjectWithInverseResolvingEList<Attribuut>(Attribuut.class, this, BmrPackage.GROEP__ATTRIBUTEN, BmrPackage.ATTRIBUUT__GROEP);
        }
        return attributen;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Historie getHistorieVastleggen()
    {
        return historieVastleggen;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHistorieVastleggen(Historie newHistorieVastleggen)
    {
        Historie oldHistorieVastleggen = historieVastleggen;
        historieVastleggen = newHistorieVastleggen == null ? HISTORIE_VASTLEGGEN_EDEFAULT : newHistorieVastleggen;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.GROEP__HISTORIE_VASTLEGGEN, oldHistorieVastleggen, historieVastleggen));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isVerplicht()
    {
        return verplicht;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVerplicht(boolean newVerplicht)
    {
        boolean oldVerplicht = verplicht;
        verplicht = newVerplicht;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.GROEP__VERPLICHT, oldVerplicht, verplicht));
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
            case BmrPackage.GROEP__OBJECT_TYPE:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetObjectType((ObjectType)otherEnd, msgs);
            case BmrPackage.GROEP__ATTRIBUTEN:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getAttributen()).basicAdd(otherEnd, msgs);
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
            case BmrPackage.GROEP__OBJECT_TYPE:
                return basicSetObjectType(null, msgs);
            case BmrPackage.GROEP__ATTRIBUTEN:
                return ((InternalEList<?>)getAttributen()).basicRemove(otherEnd, msgs);
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
            case BmrPackage.GROEP__OBJECT_TYPE:
                return eInternalContainer().eInverseRemove(this, BmrPackage.OBJECT_TYPE__GROEPEN, ObjectType.class, msgs);
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
            case BmrPackage.GROEP__OBJECT_TYPE:
                return getObjectType();
            case BmrPackage.GROEP__ATTRIBUTEN:
                return getAttributen();
            case BmrPackage.GROEP__HISTORIE_VASTLEGGEN:
                return getHistorieVastleggen();
            case BmrPackage.GROEP__VERPLICHT:
                return isVerplicht();
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
            case BmrPackage.GROEP__OBJECT_TYPE:
                setObjectType((ObjectType)newValue);
                return;
            case BmrPackage.GROEP__ATTRIBUTEN:
                getAttributen().clear();
                getAttributen().addAll((Collection<? extends Attribuut>)newValue);
                return;
            case BmrPackage.GROEP__HISTORIE_VASTLEGGEN:
                setHistorieVastleggen((Historie)newValue);
                return;
            case BmrPackage.GROEP__VERPLICHT:
                setVerplicht((Boolean)newValue);
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
            case BmrPackage.GROEP__OBJECT_TYPE:
                setObjectType((ObjectType)null);
                return;
            case BmrPackage.GROEP__ATTRIBUTEN:
                getAttributen().clear();
                return;
            case BmrPackage.GROEP__HISTORIE_VASTLEGGEN:
                setHistorieVastleggen(HISTORIE_VASTLEGGEN_EDEFAULT);
                return;
            case BmrPackage.GROEP__VERPLICHT:
                setVerplicht(VERPLICHT_EDEFAULT);
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
            case BmrPackage.GROEP__OBJECT_TYPE:
                return getObjectType() != null;
            case BmrPackage.GROEP__ATTRIBUTEN:
                return attributen != null && !attributen.isEmpty();
            case BmrPackage.GROEP__HISTORIE_VASTLEGGEN:
                return historieVastleggen != HISTORIE_VASTLEGGEN_EDEFAULT;
            case BmrPackage.GROEP__VERPLICHT:
                return verplicht != VERPLICHT_EDEFAULT;
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
        result.append(" (historieVastleggen: ");
        result.append(historieVastleggen);
        result.append(", verplicht: ");
        result.append(verplicht);
        result.append(')');
        return result.toString();
    }

} //GroepImpl
