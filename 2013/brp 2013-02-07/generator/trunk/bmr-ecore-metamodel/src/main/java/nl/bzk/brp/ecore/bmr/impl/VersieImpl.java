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

import java.util.Arrays;
import java.util.Collection;

import java.util.List;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import nl.bzk.brp.ecore.bmr.AttribuutType;
import nl.bzk.brp.ecore.bmr.BerichtSjabloon;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.InSetOfModel;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.Schema;
import nl.bzk.brp.ecore.bmr.SoortInhoud;
import nl.bzk.brp.ecore.bmr.Versie;
import nl.bzk.brp.ecore.bmr.VersieTag;

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
 * An implementation of the model object '<em><b>Versie</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.VersieImpl#getSchema <em>Schema</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.VersieImpl#getVersieTag <em>Versie Tag</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.VersieImpl#getObjectTypes <em>Object Types</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.VersieImpl#getAttribuutTypes <em>Attribuut Types</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.VersieImpl#getBerichtSjablonen <em>Bericht Sjablonen</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VersieImpl extends ElementImpl implements Versie {

    /**
     * The default value of the '{@link #getVersieTag() <em>Versie Tag</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersieTag()
     * @generated
     * @ordered
     */
    protected static final VersieTag VERSIE_TAG_EDEFAULT = VersieTag.W;

    /**
     * The cached value of the '{@link #getVersieTag() <em>Versie Tag</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersieTag()
     * @generated
     * @ordered
     */
    protected VersieTag              versieTag           = VERSIE_TAG_EDEFAULT;

    /**
     * The cached value of the '{@link #getObjectTypes() <em>Object Types</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getObjectTypes()
     * @generated
     * @ordered
     */
    protected EList<ObjectType>      objectTypes;

    /**
     * The cached value of the '{@link #getAttribuutTypes() <em>Attribuut Types</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribuutTypes()
     * @generated
     * @ordered
     */
    protected EList<AttribuutType>   attribuutTypes;

    /**
     * The cached value of the '{@link #getBerichtSjablonen() <em>Bericht Sjablonen</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBerichtSjablonen()
     * @generated
     * @ordered
     */
    protected EList<BerichtSjabloon> berichtSjablonen;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected VersieImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return BmrPackage.Literals.VERSIE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Schema getSchema() {
        if (eContainerFeatureID() != BmrPackage.VERSIE__SCHEMA) return null;
        return (Schema)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSchema(Schema newSchema, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newSchema, BmrPackage.VERSIE__SCHEMA, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setSchema(Schema newSchema) {
        if (newSchema != eInternalContainer() || (eContainerFeatureID() != BmrPackage.VERSIE__SCHEMA && newSchema != null))
        {
            if (EcoreUtil.isAncestor(this, (EObject)newSchema))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newSchema != null)
                msgs = ((InternalEObject)newSchema).eInverseAdd(this, BmrPackage.SCHEMA__VERSIES, Schema.class, msgs);
            msgs = basicSetSchema(newSchema, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.VERSIE__SCHEMA, newSchema, newSchema));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public VersieTag getVersieTag() {
        return versieTag;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setVersieTag(VersieTag newVersieTag) {
        VersieTag oldVersieTag = versieTag;
        versieTag = newVersieTag == null ? VERSIE_TAG_EDEFAULT : newVersieTag;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.VERSIE__VERSIE_TAG, oldVersieTag, versieTag));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<ObjectType> getObjectTypes() {
        if (objectTypes == null)
        {
            objectTypes = new EObjectContainmentWithInverseEList<ObjectType>(ObjectType.class, this, BmrPackage.VERSIE__OBJECT_TYPES, BmrPackage.OBJECT_TYPE__VERSIE);
        }
        return objectTypes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<AttribuutType> getAttribuutTypes() {
        if (attribuutTypes == null)
        {
            attribuutTypes = new EObjectContainmentWithInverseEList<AttribuutType>(AttribuutType.class, this, BmrPackage.VERSIE__ATTRIBUUT_TYPES, BmrPackage.ATTRIBUUT_TYPE__VERSIE);
        }
        return attribuutTypes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<BerichtSjabloon> getBerichtSjablonen() {
        if (berichtSjablonen == null)
        {
            berichtSjablonen = new EObjectContainmentWithInverseEList<BerichtSjabloon>(BerichtSjabloon.class, this, BmrPackage.VERSIE__BERICHT_SJABLONEN, BmrPackage.BERICHT_SJABLOON__VERSIE);
        }
        return berichtSjablonen;
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
            case BmrPackage.VERSIE__SCHEMA:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetSchema((Schema)otherEnd, msgs);
            case BmrPackage.VERSIE__OBJECT_TYPES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getObjectTypes()).basicAdd(otherEnd, msgs);
            case BmrPackage.VERSIE__ATTRIBUUT_TYPES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getAttribuutTypes()).basicAdd(otherEnd, msgs);
            case BmrPackage.VERSIE__BERICHT_SJABLONEN:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getBerichtSjablonen()).basicAdd(otherEnd, msgs);
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
            case BmrPackage.VERSIE__SCHEMA:
                return basicSetSchema(null, msgs);
            case BmrPackage.VERSIE__OBJECT_TYPES:
                return ((InternalEList<?>)getObjectTypes()).basicRemove(otherEnd, msgs);
            case BmrPackage.VERSIE__ATTRIBUUT_TYPES:
                return ((InternalEList<?>)getAttribuutTypes()).basicRemove(otherEnd, msgs);
            case BmrPackage.VERSIE__BERICHT_SJABLONEN:
                return ((InternalEList<?>)getBerichtSjablonen()).basicRemove(otherEnd, msgs);
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
            case BmrPackage.VERSIE__SCHEMA:
                return eInternalContainer().eInverseRemove(this, BmrPackage.SCHEMA__VERSIES, Schema.class, msgs);
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
            case BmrPackage.VERSIE__SCHEMA:
                return getSchema();
            case BmrPackage.VERSIE__VERSIE_TAG:
                return getVersieTag();
            case BmrPackage.VERSIE__OBJECT_TYPES:
                return getObjectTypes();
            case BmrPackage.VERSIE__ATTRIBUUT_TYPES:
                return getAttribuutTypes();
            case BmrPackage.VERSIE__BERICHT_SJABLONEN:
                return getBerichtSjablonen();
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
            case BmrPackage.VERSIE__SCHEMA:
                setSchema((Schema)newValue);
                return;
            case BmrPackage.VERSIE__VERSIE_TAG:
                setVersieTag((VersieTag)newValue);
                return;
            case BmrPackage.VERSIE__OBJECT_TYPES:
                getObjectTypes().clear();
                getObjectTypes().addAll((Collection<? extends ObjectType>)newValue);
                return;
            case BmrPackage.VERSIE__ATTRIBUUT_TYPES:
                getAttribuutTypes().clear();
                getAttribuutTypes().addAll((Collection<? extends AttribuutType>)newValue);
                return;
            case BmrPackage.VERSIE__BERICHT_SJABLONEN:
                getBerichtSjablonen().clear();
                getBerichtSjablonen().addAll((Collection<? extends BerichtSjabloon>)newValue);
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
            case BmrPackage.VERSIE__SCHEMA:
                setSchema((Schema)null);
                return;
            case BmrPackage.VERSIE__VERSIE_TAG:
                setVersieTag(VERSIE_TAG_EDEFAULT);
                return;
            case BmrPackage.VERSIE__OBJECT_TYPES:
                getObjectTypes().clear();
                return;
            case BmrPackage.VERSIE__ATTRIBUUT_TYPES:
                getAttribuutTypes().clear();
                return;
            case BmrPackage.VERSIE__BERICHT_SJABLONEN:
                getBerichtSjablonen().clear();
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
            case BmrPackage.VERSIE__SCHEMA:
                return getSchema() != null;
            case BmrPackage.VERSIE__VERSIE_TAG:
                return versieTag != VERSIE_TAG_EDEFAULT;
            case BmrPackage.VERSIE__OBJECT_TYPES:
                return objectTypes != null && !objectTypes.isEmpty();
            case BmrPackage.VERSIE__ATTRIBUUT_TYPES:
                return attribuutTypes != null && !attribuutTypes.isEmpty();
            case BmrPackage.VERSIE__BERICHT_SJABLONEN:
                return berichtSjablonen != null && !berichtSjablonen.isEmpty();
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
        result.append(" (versieTag: ");
        result.append(versieTag);
        result.append(')');
        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<ObjectType> getLogischeObjectTypes() {
        return Iterables.filter(getObjectTypes(), new Predicate<ObjectType>() {

            @Override
            public boolean apply(final ObjectType objectType) {
                return !objectType.isHistorieObject();
            }

        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<ObjectType> getStatischeStamgegevens() {
        return Iterables.filter(getLogischeObjectTypes(), new Predicate<ObjectType>() {

            @Override
            public boolean apply(final ObjectType objectType) {
                return objectType.getSoortInhoud() == SoortInhoud.X && !objectType.getTuples().isEmpty();
            }
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<ObjectType> getObjectTypes(final int laag) {
        return Iterables.filter(getObjectTypes(), new Predicate<ObjectType>() {

            @Override
            public boolean apply(final ObjectType objectType) {
                return objectType.getLaag().equals(laag);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<ObjectType> getObjectTypes(final InSetOfModel inSom) {
        return Iterables.filter(getObjectTypes(), new Predicate<ObjectType>() {

            @Override
            public boolean apply(final ObjectType objectType) {
                return Arrays.asList(inSom, InSetOfModel.BEIDE).contains(objectType.getInSetOfModel());
            }

        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<ObjectType> getObjectTypes(final int laag, final InSetOfModel inSom) {
        return Iterables.filter(getObjectTypes(), new Predicate<ObjectType>() {

            @Override
            public boolean apply(final ObjectType objectType) {
                return objectType.getLaag().equals(laag)
                    && Arrays.asList(inSom, InSetOfModel.BEIDE).contains(objectType.getInSetOfModel());
            }

        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<AttribuutType> getAttribuutTypes(final int laag) {
        return Iterables.filter(getAttribuutTypes(), new Predicate<AttribuutType>() {

            @Override
            public boolean apply(final AttribuutType type) {
                return type.getLaag().equals(laag);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getGekwalificeerdeNaam() {
        return getOuderNaam();
    }

} // VersieImpl
