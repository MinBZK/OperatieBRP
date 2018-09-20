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
import nl.bzk.brp.ecore.bmr.BedrijfsRegel;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Element;
import nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel;
import nl.bzk.brp.ecore.bmr.WaarderegelWaarde;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bedrijfs Regel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BedrijfsRegelImpl#getAttributen <em>Attributen</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BedrijfsRegelImpl#getElement <em>Element</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BedrijfsRegelImpl#getSoortBedrijfsRegel <em>Soort Bedrijfs Regel</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BedrijfsRegelImpl#getWaarden <em>Waarden</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BedrijfsRegelImpl extends GelaagdElementImpl implements BedrijfsRegel
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
     * The default value of the '{@link #getSoortBedrijfsRegel() <em>Soort Bedrijfs Regel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSoortBedrijfsRegel()
     * @generated
     * @ordered
     */
    protected static final SoortBedrijfsRegel SOORT_BEDRIJFS_REGEL_EDEFAULT = SoortBedrijfsRegel.ID;

    /**
     * The cached value of the '{@link #getSoortBedrijfsRegel() <em>Soort Bedrijfs Regel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSoortBedrijfsRegel()
     * @generated
     * @ordered
     */
    protected SoortBedrijfsRegel soortBedrijfsRegel = SOORT_BEDRIJFS_REGEL_EDEFAULT;

    /**
     * The cached value of the '{@link #getWaarden() <em>Waarden</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWaarden()
     * @generated
     * @ordered
     */
    protected EList<WaarderegelWaarde> waarden;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected BedrijfsRegelImpl()
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
        return BmrPackage.Literals.BEDRIJFS_REGEL;
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
            attributen = new EObjectWithInverseResolvingEList.ManyInverse<Attribuut>(Attribuut.class, this, BmrPackage.BEDRIJFS_REGEL__ATTRIBUTEN, BmrPackage.ATTRIBUUT__GEBRUIKT_IN_BEDRIJFS_REGELS);
        }
        return attributen;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Element getElement()
    {
        if (eContainerFeatureID() != BmrPackage.BEDRIJFS_REGEL__ELEMENT) return null;
        return (Element)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetElement(Element newElement, NotificationChain msgs)
    {
        msgs = eBasicSetContainer((InternalEObject)newElement, BmrPackage.BEDRIJFS_REGEL__ELEMENT, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setElement(Element newElement)
    {
        if (newElement != eInternalContainer() || (eContainerFeatureID() != BmrPackage.BEDRIJFS_REGEL__ELEMENT && newElement != null))
        {
            if (EcoreUtil.isAncestor(this, newElement))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newElement != null)
                msgs = ((InternalEObject)newElement).eInverseAdd(this, BmrPackage.ELEMENT__BEDRIJFS_REGELS, Element.class, msgs);
            msgs = basicSetElement(newElement, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.BEDRIJFS_REGEL__ELEMENT, newElement, newElement));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SoortBedrijfsRegel getSoortBedrijfsRegel()
    {
        return soortBedrijfsRegel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSoortBedrijfsRegel(SoortBedrijfsRegel newSoortBedrijfsRegel)
    {
        SoortBedrijfsRegel oldSoortBedrijfsRegel = soortBedrijfsRegel;
        soortBedrijfsRegel = newSoortBedrijfsRegel == null ? SOORT_BEDRIJFS_REGEL_EDEFAULT : newSoortBedrijfsRegel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.BEDRIJFS_REGEL__SOORT_BEDRIJFS_REGEL, oldSoortBedrijfsRegel, soortBedrijfsRegel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<WaarderegelWaarde> getWaarden()
    {
        if (waarden == null)
        {
            waarden = new EObjectContainmentWithInverseEList<WaarderegelWaarde>(WaarderegelWaarde.class, this, BmrPackage.BEDRIJFS_REGEL__WAARDEN, BmrPackage.WAARDEREGEL_WAARDE__BEDRIJFS_REGEL);
        }
        return waarden;
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
            case BmrPackage.BEDRIJFS_REGEL__ATTRIBUTEN:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getAttributen()).basicAdd(otherEnd, msgs);
            case BmrPackage.BEDRIJFS_REGEL__ELEMENT:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetElement((Element)otherEnd, msgs);
            case BmrPackage.BEDRIJFS_REGEL__WAARDEN:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getWaarden()).basicAdd(otherEnd, msgs);
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
            case BmrPackage.BEDRIJFS_REGEL__ATTRIBUTEN:
                return ((InternalEList<?>)getAttributen()).basicRemove(otherEnd, msgs);
            case BmrPackage.BEDRIJFS_REGEL__ELEMENT:
                return basicSetElement(null, msgs);
            case BmrPackage.BEDRIJFS_REGEL__WAARDEN:
                return ((InternalEList<?>)getWaarden()).basicRemove(otherEnd, msgs);
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
            case BmrPackage.BEDRIJFS_REGEL__ELEMENT:
                return eInternalContainer().eInverseRemove(this, BmrPackage.ELEMENT__BEDRIJFS_REGELS, Element.class, msgs);
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
            case BmrPackage.BEDRIJFS_REGEL__ATTRIBUTEN:
                return getAttributen();
            case BmrPackage.BEDRIJFS_REGEL__ELEMENT:
                return getElement();
            case BmrPackage.BEDRIJFS_REGEL__SOORT_BEDRIJFS_REGEL:
                return getSoortBedrijfsRegel();
            case BmrPackage.BEDRIJFS_REGEL__WAARDEN:
                return getWaarden();
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
            case BmrPackage.BEDRIJFS_REGEL__ATTRIBUTEN:
                getAttributen().clear();
                getAttributen().addAll((Collection<? extends Attribuut>)newValue);
                return;
            case BmrPackage.BEDRIJFS_REGEL__ELEMENT:
                setElement((Element)newValue);
                return;
            case BmrPackage.BEDRIJFS_REGEL__SOORT_BEDRIJFS_REGEL:
                setSoortBedrijfsRegel((SoortBedrijfsRegel)newValue);
                return;
            case BmrPackage.BEDRIJFS_REGEL__WAARDEN:
                getWaarden().clear();
                getWaarden().addAll((Collection<? extends WaarderegelWaarde>)newValue);
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
            case BmrPackage.BEDRIJFS_REGEL__ATTRIBUTEN:
                getAttributen().clear();
                return;
            case BmrPackage.BEDRIJFS_REGEL__ELEMENT:
                setElement((Element)null);
                return;
            case BmrPackage.BEDRIJFS_REGEL__SOORT_BEDRIJFS_REGEL:
                setSoortBedrijfsRegel(SOORT_BEDRIJFS_REGEL_EDEFAULT);
                return;
            case BmrPackage.BEDRIJFS_REGEL__WAARDEN:
                getWaarden().clear();
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
            case BmrPackage.BEDRIJFS_REGEL__ATTRIBUTEN:
                return attributen != null && !attributen.isEmpty();
            case BmrPackage.BEDRIJFS_REGEL__ELEMENT:
                return getElement() != null;
            case BmrPackage.BEDRIJFS_REGEL__SOORT_BEDRIJFS_REGEL:
                return soortBedrijfsRegel != SOORT_BEDRIJFS_REGEL_EDEFAULT;
            case BmrPackage.BEDRIJFS_REGEL__WAARDEN:
                return waarden != null && !waarden.isEmpty();
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
        result.append(" (soortBedrijfsRegel: ");
        result.append(soortBedrijfsRegel);
        result.append(')');
        return result.toString();
    }

} //BedrijfsRegelImpl
