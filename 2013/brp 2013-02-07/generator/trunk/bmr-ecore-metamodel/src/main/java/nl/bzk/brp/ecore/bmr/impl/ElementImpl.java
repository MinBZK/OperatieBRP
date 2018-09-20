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
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import nl.bzk.brp.ecore.bmr.BedrijfsRegel;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Element;
import nl.bzk.brp.ecore.bmr.SoortTekst;
import nl.bzk.brp.ecore.bmr.Tekst;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
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
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.ElementImpl#getSoort <em>Soort</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ElementImpl extends ModelElementImpl implements Element {

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
    protected static final String  BESCHRIJVING_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getBeschrijving() <em>Beschrijving</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBeschrijving()
     * @generated
     * @ordered
     */
    protected String               beschrijving          = BESCHRIJVING_EDEFAULT;

    /**
     * The cached value of the '{@link #getTeksten() <em>Teksten</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTeksten()
     * @generated
     * @ordered
     */
    protected EList<Tekst>         teksten;

    /**
     * The default value of the '{@link #getLaag() <em>Laag</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLaag()
     * @generated
     * @ordered
     */
    protected static final Integer LAAG_EDEFAULT         = null;

    /**
     * The cached value of the '{@link #getLaag() <em>Laag</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLaag()
     * @generated
     * @ordered
     */
    protected Integer              laag                  = LAAG_EDEFAULT;

    /**
     * The default value of the '{@link #getSyncId() <em>Sync Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSyncId()
     * @generated
     * @ordered
     */
    protected static final Integer SYNC_ID_EDEFAULT      = null;

    /**
     * The cached value of the '{@link #getSyncId() <em>Sync Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSyncId()
     * @generated
     * @ordered
     */
    protected Integer              syncId                = SYNC_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getSoort() <em>Soort</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSoort()
     * @generated
     * @ordered
     */
    protected static final String  SOORT_EDEFAULT        = null;

    /**
     * The cached value of the '{@link #getSoort() <em>Soort</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSoort()
     * @generated
     * @ordered
     */
    protected String               soort                 = SOORT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ElementImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return BmrPackage.Literals.ELEMENT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<BedrijfsRegel> getBedrijfsRegels() {
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
    @Override
    public String getBeschrijving() {
        return beschrijving;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setBeschrijving(String newBeschrijving) {
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
    @Override
    public List<Tekst> getTeksten() {
        if (teksten == null)
        {
            teksten = new EObjectContainmentWithInverseEList<Tekst>(Tekst.class, this, BmrPackage.ELEMENT__TEKSTEN, BmrPackage.TEKST__ELEMENT);
        }
        return teksten;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Integer getLaag() {
        return laag;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setLaag(Integer newLaag) {
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
    @Override
    public Integer getSyncId() {
        return syncId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setSyncId(Integer newSyncId) {
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
    @Override
    public String getSoort() {
        return soort;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setSoort(String newSoort) {
        String oldSoort = soort;
        soort = newSoort;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ELEMENT__SOORT, oldSoort, soort));
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
            case BmrPackage.ELEMENT__TEKSTEN:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getTeksten()).basicAdd(otherEnd, msgs);
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
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID)
        {
            case BmrPackage.ELEMENT__BEDRIJFS_REGELS:
                return getBedrijfsRegels();
            case BmrPackage.ELEMENT__BESCHRIJVING:
                return getBeschrijving();
            case BmrPackage.ELEMENT__TEKSTEN:
                return getTeksten();
            case BmrPackage.ELEMENT__LAAG:
                return getLaag();
            case BmrPackage.ELEMENT__SYNC_ID:
                return getSyncId();
            case BmrPackage.ELEMENT__SOORT:
                return getSoort();
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
    public void eSet(int featureID, Object newValue) {
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
                getTeksten().clear();
                getTeksten().addAll((Collection<? extends Tekst>)newValue);
                return;
            case BmrPackage.ELEMENT__LAAG:
                setLaag((Integer)newValue);
                return;
            case BmrPackage.ELEMENT__SYNC_ID:
                setSyncId((Integer)newValue);
                return;
            case BmrPackage.ELEMENT__SOORT:
                setSoort((String)newValue);
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
            case BmrPackage.ELEMENT__SOORT:
                setSoort(SOORT_EDEFAULT);
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
            case BmrPackage.ELEMENT__SOORT:
                return SOORT_EDEFAULT == null ? soort != null : !SOORT_EDEFAULT.equals(soort);
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
        result.append(" (beschrijving: ");
        result.append(beschrijving);
        result.append(", laag: ");
        result.append(laag);
        result.append(", syncId: ");
        result.append(syncId);
        result.append(", soort: ");
        result.append(soort);
        result.append(')');
        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tekst getTekst(final SoortTekst soortTekst) {
        return Iterables.find(getTeksten(), new Predicate<Tekst>() {

            @Override
            public boolean apply(final Tekst tekst) {
                return tekst.getSoort() == soortTekst;
            }
        }, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String xmlId() {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(getSoort());
        resultaat.append(getSyncId());
        return resultaat.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tekst> getTeksten(final Set<SoortTekst> soorten) {
        return Lists.newArrayList(Iterables.filter(getTeksten(), new Predicate<Tekst>() {

            @Override
            public boolean apply(final Tekst tekst) {
                return soorten.contains(tekst.getSoort());
            }
        }));
    }
} // ElementImpl
