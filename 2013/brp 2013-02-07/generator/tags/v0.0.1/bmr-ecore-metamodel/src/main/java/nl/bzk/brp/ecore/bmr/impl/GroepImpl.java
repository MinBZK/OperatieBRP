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

import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Groep;
import nl.bzk.brp.ecore.bmr.InSetOfModel;
import nl.bzk.brp.ecore.bmr.ObjectType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
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
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.GroepImpl#getInSetOfModel <em>In Set Of Model</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.GroepImpl#getAttributen <em>Attributen</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GroepImpl extends GelaagdElementImpl implements Groep
{
    /**
     * The default value of the '{@link #getInSetOfModel() <em>In Set Of Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInSetOfModel()
     * @generated
     * @ordered
     */
    protected static final InSetOfModel IN_SET_OF_MODEL_EDEFAULT = InSetOfModel.SET;

    /**
     * The cached value of the '{@link #getInSetOfModel() <em>In Set Of Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInSetOfModel()
     * @generated
     * @ordered
     */
    protected InSetOfModel inSetOfModel = IN_SET_OF_MODEL_EDEFAULT;

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
            if (EcoreUtil.isAncestor(this, newObjectType))
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
    public InSetOfModel getInSetOfModel()
    {
        return inSetOfModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInSetOfModel(InSetOfModel newInSetOfModel)
    {
        InSetOfModel oldInSetOfModel = inSetOfModel;
        inSetOfModel = newInSetOfModel == null ? IN_SET_OF_MODEL_EDEFAULT : newInSetOfModel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.GROEP__IN_SET_OF_MODEL, oldInSetOfModel, inSetOfModel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Attribuut> getAttributen()
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
    public Boolean isHistorieVastleggen()
    {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Boolean isVerplicht()
    {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
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
            case BmrPackage.GROEP__IN_SET_OF_MODEL:
                return getInSetOfModel();
            case BmrPackage.GROEP__ATTRIBUTEN:
                return getAttributen();
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
            case BmrPackage.GROEP__IN_SET_OF_MODEL:
                setInSetOfModel((InSetOfModel)newValue);
                return;
            case BmrPackage.GROEP__ATTRIBUTEN:
                getAttributen().clear();
                getAttributen().addAll((Collection<? extends Attribuut>)newValue);
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
            case BmrPackage.GROEP__IN_SET_OF_MODEL:
                setInSetOfModel(IN_SET_OF_MODEL_EDEFAULT);
                return;
            case BmrPackage.GROEP__ATTRIBUTEN:
                getAttributen().clear();
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
            case BmrPackage.GROEP__IN_SET_OF_MODEL:
                return inSetOfModel != IN_SET_OF_MODEL_EDEFAULT;
            case BmrPackage.GROEP__ATTRIBUTEN:
                return attributen != null && !attributen.isEmpty();
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
        result.append(" (inSetOfModel: ");
        result.append(inSetOfModel);
        result.append(')');
        return result.toString();
    }

} //GroepImpl
