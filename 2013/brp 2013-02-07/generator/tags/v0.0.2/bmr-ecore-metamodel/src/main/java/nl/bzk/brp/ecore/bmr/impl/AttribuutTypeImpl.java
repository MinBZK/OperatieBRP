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

import nl.bzk.brp.ecore.bmr.AttribuutType;
import nl.bzk.brp.ecore.bmr.BasisType;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Versie;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribuut Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutTypeImpl#getType <em>Type</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutTypeImpl#getVersie <em>Versie</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutTypeImpl#getMinimumLengte <em>Minimum Lengte</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutTypeImpl#getMaximumLengte <em>Maximum Lengte</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutTypeImpl#getAantalDecimalen <em>Aantal Decimalen</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttribuutTypeImpl extends TypeImpl implements AttribuutType {

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected BasisType            type;

    /**
     * The default value of the '{@link #getMinimumLengte() <em>Minimum Lengte</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMinimumLengte()
     * @generated
     * @ordered
     */
    protected static final Integer MINIMUM_LENGTE_EDEFAULT   = null;
    /**
     * The cached value of the '{@link #getMinimumLengte() <em>Minimum Lengte</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMinimumLengte()
     * @generated
     * @ordered
     */
    protected Integer              minimumLengte             = MINIMUM_LENGTE_EDEFAULT;
    /**
     * The default value of the '{@link #getMaximumLengte() <em>Maximum Lengte</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaximumLengte()
     * @generated
     * @ordered
     */
    protected static final Integer MAXIMUM_LENGTE_EDEFAULT   = null;
    /**
     * The cached value of the '{@link #getMaximumLengte() <em>Maximum Lengte</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaximumLengte()
     * @generated
     * @ordered
     */
    protected Integer              maximumLengte             = MAXIMUM_LENGTE_EDEFAULT;
    /**
     * The default value of the '{@link #getAantalDecimalen() <em>Aantal Decimalen</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAantalDecimalen()
     * @generated
     * @ordered
     */
    protected static final Integer AANTAL_DECIMALEN_EDEFAULT = null;
    /**
     * The cached value of the '{@link #getAantalDecimalen() <em>Aantal Decimalen</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAantalDecimalen()
     * @generated
     * @ordered
     */
    protected Integer              aantalDecimalen           = AANTAL_DECIMALEN_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AttribuutTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return BmrPackage.Literals.ATTRIBUUT_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public BasisType getType() {
        if (type != null && type.eIsProxy())
        {
            InternalEObject oldType = (InternalEObject)type;
            type = (BasisType)eResolveProxy(oldType);
            if (type != oldType)
            {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BmrPackage.ATTRIBUUT_TYPE__TYPE, oldType, type));
            }
        }
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BasisType basicGetType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setType(BasisType newType) {
        BasisType oldType = type;
        type = newType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT_TYPE__TYPE, oldType, type));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Versie getVersie() {
        if (eContainerFeatureID() != BmrPackage.ATTRIBUUT_TYPE__VERSIE) return null;
        return (Versie)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetVersie(Versie newVersie, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newVersie, BmrPackage.ATTRIBUUT_TYPE__VERSIE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setVersie(Versie newVersie) {
        if (newVersie != eInternalContainer() || (eContainerFeatureID() != BmrPackage.ATTRIBUUT_TYPE__VERSIE && newVersie != null))
        {
            if (EcoreUtil.isAncestor(this, newVersie))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newVersie != null)
                msgs = ((InternalEObject)newVersie).eInverseAdd(this, BmrPackage.VERSIE__ATTRIBUUT_TYPES, Versie.class, msgs);
            msgs = basicSetVersie(newVersie, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT_TYPE__VERSIE, newVersie, newVersie));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Integer getMinimumLengte() {
        return minimumLengte;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setMinimumLengte(Integer newMinimumLengte) {
        Integer oldMinimumLengte = minimumLengte;
        minimumLengte = newMinimumLengte;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT_TYPE__MINIMUM_LENGTE, oldMinimumLengte, minimumLengte));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Integer getMaximumLengte() {
        return maximumLengte;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setMaximumLengte(Integer newMaximumLengte) {
        Integer oldMaximumLengte = maximumLengte;
        maximumLengte = newMaximumLengte;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT_TYPE__MAXIMUM_LENGTE, oldMaximumLengte, maximumLengte));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Integer getAantalDecimalen() {
        return aantalDecimalen;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setAantalDecimalen(Integer newAantalDecimalen) {
        Integer oldAantalDecimalen = aantalDecimalen;
        aantalDecimalen = newAantalDecimalen;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT_TYPE__AANTAL_DECIMALEN, oldAantalDecimalen, aantalDecimalen));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID)
        {
            case BmrPackage.ATTRIBUUT_TYPE__VERSIE:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetVersie((Versie)otherEnd, msgs);
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
            case BmrPackage.ATTRIBUUT_TYPE__VERSIE:
                return basicSetVersie(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
        switch (eContainerFeatureID())
        {
            case BmrPackage.ATTRIBUUT_TYPE__VERSIE:
                return eInternalContainer().eInverseRemove(this, BmrPackage.VERSIE__ATTRIBUUT_TYPES, Versie.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID)
        {
            case BmrPackage.ATTRIBUUT_TYPE__TYPE:
                if (resolve) return getType();
                return basicGetType();
            case BmrPackage.ATTRIBUUT_TYPE__VERSIE:
                return getVersie();
            case BmrPackage.ATTRIBUUT_TYPE__MINIMUM_LENGTE:
                return getMinimumLengte();
            case BmrPackage.ATTRIBUUT_TYPE__MAXIMUM_LENGTE:
                return getMaximumLengte();
            case BmrPackage.ATTRIBUUT_TYPE__AANTAL_DECIMALEN:
                return getAantalDecimalen();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID)
        {
            case BmrPackage.ATTRIBUUT_TYPE__TYPE:
                setType((BasisType)newValue);
                return;
            case BmrPackage.ATTRIBUUT_TYPE__VERSIE:
                setVersie((Versie)newValue);
                return;
            case BmrPackage.ATTRIBUUT_TYPE__MINIMUM_LENGTE:
                setMinimumLengte((Integer)newValue);
                return;
            case BmrPackage.ATTRIBUUT_TYPE__MAXIMUM_LENGTE:
                setMaximumLengte((Integer)newValue);
                return;
            case BmrPackage.ATTRIBUUT_TYPE__AANTAL_DECIMALEN:
                setAantalDecimalen((Integer)newValue);
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
    public void eUnset(int featureID) {
        switch (featureID)
        {
            case BmrPackage.ATTRIBUUT_TYPE__TYPE:
                setType((BasisType)null);
                return;
            case BmrPackage.ATTRIBUUT_TYPE__VERSIE:
                setVersie((Versie)null);
                return;
            case BmrPackage.ATTRIBUUT_TYPE__MINIMUM_LENGTE:
                setMinimumLengte(MINIMUM_LENGTE_EDEFAULT);
                return;
            case BmrPackage.ATTRIBUUT_TYPE__MAXIMUM_LENGTE:
                setMaximumLengte(MAXIMUM_LENGTE_EDEFAULT);
                return;
            case BmrPackage.ATTRIBUUT_TYPE__AANTAL_DECIMALEN:
                setAantalDecimalen(AANTAL_DECIMALEN_EDEFAULT);
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
    public boolean eIsSet(int featureID) {
        switch (featureID)
        {
            case BmrPackage.ATTRIBUUT_TYPE__TYPE:
                return type != null;
            case BmrPackage.ATTRIBUUT_TYPE__VERSIE:
                return getVersie() != null;
            case BmrPackage.ATTRIBUUT_TYPE__MINIMUM_LENGTE:
                return MINIMUM_LENGTE_EDEFAULT == null ? minimumLengte != null : !MINIMUM_LENGTE_EDEFAULT.equals(minimumLengte);
            case BmrPackage.ATTRIBUUT_TYPE__MAXIMUM_LENGTE:
                return MAXIMUM_LENGTE_EDEFAULT == null ? maximumLengte != null : !MAXIMUM_LENGTE_EDEFAULT.equals(maximumLengte);
            case BmrPackage.ATTRIBUUT_TYPE__AANTAL_DECIMALEN:
                return AANTAL_DECIMALEN_EDEFAULT == null ? aantalDecimalen != null : !AANTAL_DECIMALEN_EDEFAULT.equals(aantalDecimalen);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (minimumLengte: ");
        result.append(minimumLengte);
        result.append(", maximumLengte: ");
        result.append(maximumLengte);
        result.append(", aantalDecimalen: ");
        result.append(aantalDecimalen);
        result.append(')');
        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAttribuutType() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isObjectType() {
        return false;
    }

} // AttribuutTypeImpl
