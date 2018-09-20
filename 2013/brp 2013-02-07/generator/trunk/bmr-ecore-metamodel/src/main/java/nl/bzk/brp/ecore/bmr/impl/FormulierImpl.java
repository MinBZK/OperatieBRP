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
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import nl.bzk.brp.ecore.bmr.Applicatie;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Bron;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.Frame;

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
 * An implementation of the model object '<em><b>Formulier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.FormulierImpl#getApplicatie <em>Applicatie</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.FormulierImpl#getFrames <em>Frames</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.FormulierImpl#getBronnen <em>Bronnen</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FormulierImpl extends ModelElementImpl implements Formulier {

    /**
     * The cached value of the '{@link #getFrames() <em>Frames</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFrames()
     * @generated
     * @ordered
     */
    protected EList<Frame> frames;

    /**
     * The cached value of the '{@link #getBronnen() <em>Bronnen</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBronnen()
     * @generated
     * @ordered
     */
    protected EList<Bron>  bronnen;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FormulierImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return BmrPackage.Literals.FORMULIER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Applicatie getApplicatie() {
        if (eContainerFeatureID() != BmrPackage.FORMULIER__APPLICATIE) return null;
        return (Applicatie)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetApplicatie(Applicatie newApplicatie, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newApplicatie, BmrPackage.FORMULIER__APPLICATIE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setApplicatie(Applicatie newApplicatie) {
        if (newApplicatie != eInternalContainer() || (eContainerFeatureID() != BmrPackage.FORMULIER__APPLICATIE && newApplicatie != null))
        {
            if (EcoreUtil.isAncestor(this, (EObject)newApplicatie))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newApplicatie != null)
                msgs = ((InternalEObject)newApplicatie).eInverseAdd(this, BmrPackage.APPLICATIE__FORMULIEREN, Applicatie.class, msgs);
            msgs = basicSetApplicatie(newApplicatie, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.FORMULIER__APPLICATIE, newApplicatie, newApplicatie));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<Frame> getFrames() {
        if (frames == null)
        {
            frames = new EObjectContainmentWithInverseEList<Frame>(Frame.class, this, BmrPackage.FORMULIER__FRAMES, BmrPackage.FRAME__FORMULIER);
        }
        return frames;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<Bron> getBronnen() {
        if (bronnen == null)
        {
            bronnen = new EObjectContainmentWithInverseEList<Bron>(Bron.class, this, BmrPackage.FORMULIER__BRONNEN, BmrPackage.BRON__FORMULIER);
        }
        return bronnen;
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
            case BmrPackage.FORMULIER__APPLICATIE:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetApplicatie((Applicatie)otherEnd, msgs);
            case BmrPackage.FORMULIER__FRAMES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getFrames()).basicAdd(otherEnd, msgs);
            case BmrPackage.FORMULIER__BRONNEN:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getBronnen()).basicAdd(otherEnd, msgs);
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
            case BmrPackage.FORMULIER__APPLICATIE:
                return basicSetApplicatie(null, msgs);
            case BmrPackage.FORMULIER__FRAMES:
                return ((InternalEList<?>)getFrames()).basicRemove(otherEnd, msgs);
            case BmrPackage.FORMULIER__BRONNEN:
                return ((InternalEList<?>)getBronnen()).basicRemove(otherEnd, msgs);
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
            case BmrPackage.FORMULIER__APPLICATIE:
                return eInternalContainer().eInverseRemove(this, BmrPackage.APPLICATIE__FORMULIEREN, Applicatie.class, msgs);
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
            case BmrPackage.FORMULIER__APPLICATIE:
                return getApplicatie();
            case BmrPackage.FORMULIER__FRAMES:
                return getFrames();
            case BmrPackage.FORMULIER__BRONNEN:
                return getBronnen();
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
            case BmrPackage.FORMULIER__APPLICATIE:
                setApplicatie((Applicatie)newValue);
                return;
            case BmrPackage.FORMULIER__FRAMES:
                getFrames().clear();
                getFrames().addAll((Collection<? extends Frame>)newValue);
                return;
            case BmrPackage.FORMULIER__BRONNEN:
                getBronnen().clear();
                getBronnen().addAll((Collection<? extends Bron>)newValue);
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
            case BmrPackage.FORMULIER__APPLICATIE:
                setApplicatie((Applicatie)null);
                return;
            case BmrPackage.FORMULIER__FRAMES:
                getFrames().clear();
                return;
            case BmrPackage.FORMULIER__BRONNEN:
                getBronnen().clear();
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
            case BmrPackage.FORMULIER__APPLICATIE:
                return getApplicatie() != null;
            case BmrPackage.FORMULIER__FRAMES:
                return frames != null && !frames.isEmpty();
            case BmrPackage.FORMULIER__BRONNEN:
                return bronnen != null && !bronnen.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    @Override
    public Bron getBron(final Integer id) {
        return Iterables.find(getBronnen(), new Predicate<Bron>() {

            @Override
            public boolean apply(final Bron input) {
                return input.getId().equals(id);
            }
        }, null);
    }
} // FormulierImpl
