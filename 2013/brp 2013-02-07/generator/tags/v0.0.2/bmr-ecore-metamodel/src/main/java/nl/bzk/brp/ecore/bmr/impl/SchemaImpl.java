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

import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Domein;
import nl.bzk.brp.ecore.bmr.Schema;
import nl.bzk.brp.ecore.bmr.Versie;
import nl.bzk.brp.ecore.bmr.VersieTag;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Schema</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.SchemaImpl#getVersies <em>Versies</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.SchemaImpl#getDomein <em>Domein</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SchemaImpl extends ElementImpl implements Schema {

    /**
     * The cached value of the '{@link #getVersies() <em>Versies</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersies()
     * @generated
     * @ordered
     */
    protected EList<Versie> versies;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SchemaImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return BmrPackage.Literals.SCHEMA;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Versie> getVersies() {
        if (versies == null)
        {
            versies = new EObjectContainmentWithInverseEList<Versie>(Versie.class, this, BmrPackage.SCHEMA__VERSIES, BmrPackage.VERSIE__SCHEMA);
        }
        return versies;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Domein getDomein() {
        if (eContainerFeatureID() != BmrPackage.SCHEMA__DOMEIN) return null;
        return (Domein)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDomein(Domein newDomein, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newDomein, BmrPackage.SCHEMA__DOMEIN, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDomein(Domein newDomein) {
        if (newDomein != eInternalContainer() || (eContainerFeatureID() != BmrPackage.SCHEMA__DOMEIN && newDomein != null))
        {
            if (EcoreUtil.isAncestor(this, newDomein))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newDomein != null)
                msgs = ((InternalEObject)newDomein).eInverseAdd(this, BmrPackage.DOMEIN__SCHEMAS, Domein.class, msgs);
            msgs = basicSetDomein(newDomein, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.SCHEMA__DOMEIN, newDomein, newDomein));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID)
        {
            case BmrPackage.SCHEMA__VERSIES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getVersies()).basicAdd(otherEnd, msgs);
            case BmrPackage.SCHEMA__DOMEIN:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetDomein((Domein)otherEnd, msgs);
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
            case BmrPackage.SCHEMA__VERSIES:
                return ((InternalEList<?>)getVersies()).basicRemove(otherEnd, msgs);
            case BmrPackage.SCHEMA__DOMEIN:
                return basicSetDomein(null, msgs);
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
            case BmrPackage.SCHEMA__DOMEIN:
                return eInternalContainer().eInverseRemove(this, BmrPackage.DOMEIN__SCHEMAS, Domein.class, msgs);
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
            case BmrPackage.SCHEMA__VERSIES:
                return getVersies();
            case BmrPackage.SCHEMA__DOMEIN:
                return getDomein();
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
            case BmrPackage.SCHEMA__VERSIES:
                getVersies().clear();
                getVersies().addAll((Collection<? extends Versie>)newValue);
                return;
            case BmrPackage.SCHEMA__DOMEIN:
                setDomein((Domein)newValue);
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
            case BmrPackage.SCHEMA__VERSIES:
                getVersies().clear();
                return;
            case BmrPackage.SCHEMA__DOMEIN:
                setDomein((Domein)null);
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
            case BmrPackage.SCHEMA__VERSIES:
                return versies != null && !versies.isEmpty();
            case BmrPackage.SCHEMA__DOMEIN:
                return getDomein() != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * {@inheritDoc}
     */
    public Versie getWerkVersie() {
        for (Versie versie : getVersies()) {
            if (versie.getVersieTag() == VersieTag.W) {
                return versie;
            }
        }
        return null;
    }

} // SchemaImpl
