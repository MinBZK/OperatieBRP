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

import nl.bzk.brp.ecore.bmr.BedrijfsRegel;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Element;
import nl.bzk.brp.ecore.bmr.SoortTekst;
import nl.bzk.brp.ecore.bmr.Tekst;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.ElementImpl#getBedrijfsRegels <em>Bedrijfs Regels</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.ElementImpl#getBeschrijving <em>Beschrijving</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.ElementImpl#getTeksten <em>Teksten</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.ElementImpl#getLaag <em>Laag</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.ElementImpl#getSyncId <em>Sync Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ElementImpl extends ModelElementImpl implements Element
{
    /**
     * The cached value of the '{@link #getBedrijfsRegels() <em>Bedrijfs Regels</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBedrijfsRegels()
     * @generated
     * @ordered
     */
    protected EList<BedrijfsRegel> bedrijfsRegels;

    /**
     * The default value of the '{@link #getBeschrijving() <em>Beschrijving</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBeschrijving()
     * @generated
     * @ordered
     */
    protected static final String BESCHRIJVING_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getBeschrijving() <em>Beschrijving</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBeschrijving()
     * @generated
     * @ordered
     */
    protected String beschrijving = BESCHRIJVING_EDEFAULT;

    /**
     * The cached value of the '{@link #getTeksten() <em>Teksten</em>}' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTeksten()
     * @generated
     * @ordered
     */
    protected EMap<SoortTekst, Tekst> teksten;

    /**
     * The default value of the '{@link #getLaag() <em>Laag</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLaag()
     * @generated
     * @ordered
     */
    protected static final Integer LAAG_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLaag() <em>Laag</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLaag()
     * @generated
     * @ordered
     */
    protected Integer laag = LAAG_EDEFAULT;

    /**
     * The default value of the '{@link #getSyncId() <em>Sync Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSyncId()
     * @generated
     * @ordered
     */
    protected static final Integer SYNC_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSyncId() <em>Sync Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSyncId()
     * @generated
     * @ordered
     */
    protected Integer syncId = SYNC_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ElementImpl()
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
        return BmrPackage.Literals.ELEMENT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<BedrijfsRegel> getBedrijfsRegels()
    {
        if (bedrijfsRegels == null)
        {
            bedrijfsRegels = new EObjectContainmentWithInverseEList<BedrijfsRegel>(BedrijfsRegel.class, this, BmrPackage.ELEMENT__BEDRIJFS_REGELS, BmrPackage.BEDRIJFS_REGEL__ELEMENT);
        }
        return bedrijfsRegels;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getBeschrijving()
    {
        return beschrijving;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBeschrijving(String newBeschrijving)
    {
        String oldBeschrijving = beschrijving;
        beschrijving = newBeschrijving;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ELEMENT__BESCHRIJVING, oldBeschrijving, beschrijving));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EMap<SoortTekst, Tekst> getTeksten()
    {
        if (teksten == null)
        {
            teksten = new EcoreEMap<SoortTekst,Tekst>(BmrPackage.Literals.SOORT_TEKST_TO_TEKST_MAP_ENTRY, SoortTekstToTekstMapEntryImpl.class, this, BmrPackage.ELEMENT__TEKSTEN);
        }
        return teksten;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Integer getLaag()
    {
        return laag;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLaag(Integer newLaag)
    {
        Integer oldLaag = laag;
        laag = newLaag;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ELEMENT__LAAG, oldLaag, laag));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Integer getSyncId()
    {
        return syncId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSyncId(Integer newSyncId)
    {
        Integer oldSyncId = syncId;
        syncId = newSyncId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ELEMENT__SYNC_ID, oldSyncId, syncId));
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
            case BmrPackage.ELEMENT__BEDRIJFS_REGELS:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getBedrijfsRegels()).basicAdd(otherEnd, msgs);
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
            case BmrPackage.ELEMENT__BEDRIJFS_REGELS:
                return ((InternalEList<?>)getBedrijfsRegels()).basicRemove(otherEnd, msgs);
            case BmrPackage.ELEMENT__TEKSTEN:
                return ((InternalEList<?>)getTeksten()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
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
            case BmrPackage.ELEMENT__BEDRIJFS_REGELS:
                return getBedrijfsRegels();
            case BmrPackage.ELEMENT__BESCHRIJVING:
                return getBeschrijving();
            case BmrPackage.ELEMENT__TEKSTEN:
                if (coreType) return getTeksten();
                else return getTeksten().map();
            case BmrPackage.ELEMENT__LAAG:
                return getLaag();
            case BmrPackage.ELEMENT__SYNC_ID:
                return getSyncId();
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
            case BmrPackage.ELEMENT__BEDRIJFS_REGELS:
                getBedrijfsRegels().clear();
                getBedrijfsRegels().addAll((Collection<? extends BedrijfsRegel>)newValue);
                return;
            case BmrPackage.ELEMENT__BESCHRIJVING:
                setBeschrijving((String)newValue);
                return;
            case BmrPackage.ELEMENT__TEKSTEN:
                ((EStructuralFeature.Setting)getTeksten()).set(newValue);
                return;
            case BmrPackage.ELEMENT__LAAG:
                setLaag((Integer)newValue);
                return;
            case BmrPackage.ELEMENT__SYNC_ID:
                setSyncId((Integer)newValue);
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
            case BmrPackage.ELEMENT__BEDRIJFS_REGELS:
                getBedrijfsRegels().clear();
                return;
            case BmrPackage.ELEMENT__BESCHRIJVING:
                setBeschrijving(BESCHRIJVING_EDEFAULT);
                return;
            case BmrPackage.ELEMENT__TEKSTEN:
                getTeksten().clear();
                return;
            case BmrPackage.ELEMENT__LAAG:
                setLaag(LAAG_EDEFAULT);
                return;
            case BmrPackage.ELEMENT__SYNC_ID:
                setSyncId(SYNC_ID_EDEFAULT);
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
            case BmrPackage.ELEMENT__BEDRIJFS_REGELS:
                return bedrijfsRegels != null && !bedrijfsRegels.isEmpty();
            case BmrPackage.ELEMENT__BESCHRIJVING:
                return BESCHRIJVING_EDEFAULT == null ? beschrijving != null : !BESCHRIJVING_EDEFAULT.equals(beschrijving);
            case BmrPackage.ELEMENT__TEKSTEN:
                return teksten != null && !teksten.isEmpty();
            case BmrPackage.ELEMENT__LAAG:
                return LAAG_EDEFAULT == null ? laag != null : !LAAG_EDEFAULT.equals(laag);
            case BmrPackage.ELEMENT__SYNC_ID:
                return SYNC_ID_EDEFAULT == null ? syncId != null : !SYNC_ID_EDEFAULT.equals(syncId);
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
        result.append(" (beschrijving: ");
        result.append(beschrijving);
        result.append(", laag: ");
        result.append(laag);
        result.append(", syncId: ");
        result.append(syncId);
        result.append(')');
        return result.toString();
    }

} //ElementImpl
