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

import nl.bzk.brp.ecore.bmr.BedrijfsRegel;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.WaarderegelWaarde;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Waarderegel Waarde</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.WaarderegelWaardeImpl#getBedrijfsRegel <em>Bedrijfs Regel</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.WaarderegelWaardeImpl#getWaarde <em>Waarde</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.WaarderegelWaardeImpl#getWeergave <em>Weergave</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WaarderegelWaardeImpl extends GelaagdElementImpl implements WaarderegelWaarde
{
    /**
     * The default value of the '{@link #getWaarde() <em>Waarde</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWaarde()
     * @generated
     * @ordered
     */
    protected static final String WAARDE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWaarde() <em>Waarde</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWaarde()
     * @generated
     * @ordered
     */
    protected String waarde = WAARDE_EDEFAULT;

    /**
     * The default value of the '{@link #getWeergave() <em>Weergave</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWeergave()
     * @generated
     * @ordered
     */
    protected static final String WEERGAVE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWeergave() <em>Weergave</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWeergave()
     * @generated
     * @ordered
     */
    protected String weergave = WEERGAVE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WaarderegelWaardeImpl()
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
        return BmrPackage.Literals.WAARDEREGEL_WAARDE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BedrijfsRegel getBedrijfsRegel()
    {
        if (eContainerFeatureID() != BmrPackage.WAARDEREGEL_WAARDE__BEDRIJFS_REGEL) return null;
        return (BedrijfsRegel)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBedrijfsRegel(BedrijfsRegel newBedrijfsRegel, NotificationChain msgs)
    {
        msgs = eBasicSetContainer((InternalEObject)newBedrijfsRegel, BmrPackage.WAARDEREGEL_WAARDE__BEDRIJFS_REGEL, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBedrijfsRegel(BedrijfsRegel newBedrijfsRegel)
    {
        if (newBedrijfsRegel != eInternalContainer() || (eContainerFeatureID() != BmrPackage.WAARDEREGEL_WAARDE__BEDRIJFS_REGEL && newBedrijfsRegel != null))
        {
            if (EcoreUtil.isAncestor(this, newBedrijfsRegel))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newBedrijfsRegel != null)
                msgs = ((InternalEObject)newBedrijfsRegel).eInverseAdd(this, BmrPackage.BEDRIJFS_REGEL__WAARDEN, BedrijfsRegel.class, msgs);
            msgs = basicSetBedrijfsRegel(newBedrijfsRegel, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.WAARDEREGEL_WAARDE__BEDRIJFS_REGEL, newBedrijfsRegel, newBedrijfsRegel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWaarde()
    {
        return waarde;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWaarde(String newWaarde)
    {
        String oldWaarde = waarde;
        waarde = newWaarde;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.WAARDEREGEL_WAARDE__WAARDE, oldWaarde, waarde));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWeergave()
    {
        return weergave;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWeergave(String newWeergave)
    {
        String oldWeergave = weergave;
        weergave = newWeergave;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.WAARDEREGEL_WAARDE__WEERGAVE, oldWeergave, weergave));
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
            case BmrPackage.WAARDEREGEL_WAARDE__BEDRIJFS_REGEL:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetBedrijfsRegel((BedrijfsRegel)otherEnd, msgs);
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
            case BmrPackage.WAARDEREGEL_WAARDE__BEDRIJFS_REGEL:
                return basicSetBedrijfsRegel(null, msgs);
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
            case BmrPackage.WAARDEREGEL_WAARDE__BEDRIJFS_REGEL:
                return eInternalContainer().eInverseRemove(this, BmrPackage.BEDRIJFS_REGEL__WAARDEN, BedrijfsRegel.class, msgs);
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
            case BmrPackage.WAARDEREGEL_WAARDE__BEDRIJFS_REGEL:
                return getBedrijfsRegel();
            case BmrPackage.WAARDEREGEL_WAARDE__WAARDE:
                return getWaarde();
            case BmrPackage.WAARDEREGEL_WAARDE__WEERGAVE:
                return getWeergave();
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
            case BmrPackage.WAARDEREGEL_WAARDE__BEDRIJFS_REGEL:
                setBedrijfsRegel((BedrijfsRegel)newValue);
                return;
            case BmrPackage.WAARDEREGEL_WAARDE__WAARDE:
                setWaarde((String)newValue);
                return;
            case BmrPackage.WAARDEREGEL_WAARDE__WEERGAVE:
                setWeergave((String)newValue);
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
            case BmrPackage.WAARDEREGEL_WAARDE__BEDRIJFS_REGEL:
                setBedrijfsRegel((BedrijfsRegel)null);
                return;
            case BmrPackage.WAARDEREGEL_WAARDE__WAARDE:
                setWaarde(WAARDE_EDEFAULT);
                return;
            case BmrPackage.WAARDEREGEL_WAARDE__WEERGAVE:
                setWeergave(WEERGAVE_EDEFAULT);
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
            case BmrPackage.WAARDEREGEL_WAARDE__BEDRIJFS_REGEL:
                return getBedrijfsRegel() != null;
            case BmrPackage.WAARDEREGEL_WAARDE__WAARDE:
                return WAARDE_EDEFAULT == null ? waarde != null : !WAARDE_EDEFAULT.equals(waarde);
            case BmrPackage.WAARDEREGEL_WAARDE__WEERGAVE:
                return WEERGAVE_EDEFAULT == null ? weergave != null : !WEERGAVE_EDEFAULT.equals(weergave);
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
        result.append(" (waarde: ");
        result.append(waarde);
        result.append(", weergave: ");
        result.append(weergave);
        result.append(')');
        return result.toString();
    }

} //WaarderegelWaardeImpl
