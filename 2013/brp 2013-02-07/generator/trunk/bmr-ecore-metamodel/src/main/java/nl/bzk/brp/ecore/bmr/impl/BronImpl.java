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
import nl.bzk.brp.ecore.bmr.Bron;
import nl.bzk.brp.ecore.bmr.BronAttribuut;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.Frame;
import nl.bzk.brp.ecore.bmr.ObjectType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bron</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BronImpl#getMeervoudsvorm <em>Meervoudsvorm</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BronImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BronImpl#getVolgnummer <em>Volgnummer</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BronImpl#getFormulier <em>Formulier</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BronImpl#getFrames <em>Frames</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BronImpl#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BronImpl#getLink <em>Link</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.BronImpl#getBronAttributen <em>Bron Attributen</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BronImpl extends ModelElementImpl implements Bron
{
    /**
     * The default value of the '{@link #getMeervoudsvorm() <em>Meervoudsvorm</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMeervoudsvorm()
     * @generated
     * @ordered
     */
    protected static final String MEERVOUDSVORM_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMeervoudsvorm() <em>Meervoudsvorm</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMeervoudsvorm()
     * @generated
     * @ordered
     */
    protected String meervoudsvorm = MEERVOUDSVORM_EDEFAULT;

    /**
     * The default value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifier()
     * @generated
     * @ordered
     */
    protected static final String IDENTIFIER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifier()
     * @generated
     * @ordered
     */
    protected String identifier = IDENTIFIER_EDEFAULT;

    /**
     * The default value of the '{@link #getVolgnummer() <em>Volgnummer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVolgnummer()
     * @generated
     * @ordered
     */
    protected static final Integer VOLGNUMMER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getVolgnummer() <em>Volgnummer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVolgnummer()
     * @generated
     * @ordered
     */
    protected Integer volgnummer = VOLGNUMMER_EDEFAULT;

    /**
     * The cached value of the '{@link #getFrames() <em>Frames</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFrames()
     * @generated
     * @ordered
     */
    protected EList<Frame> frames;

    /**
     * The cached value of the '{@link #getObjectType() <em>Object Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getObjectType()
     * @generated
     * @ordered
     */
    protected ObjectType objectType;

    /**
     * The cached value of the '{@link #getLink() <em>Link</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLink()
     * @generated
     * @ordered
     */
    protected Attribuut link;

    /**
     * The cached value of the '{@link #getBronAttributen() <em>Bron Attributen</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBronAttributen()
     * @generated
     * @ordered
     */
    protected EList<BronAttribuut> bronAttributen;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected BronImpl()
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
        return BmrPackage.Literals.BRON;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMeervoudsvorm()
    {
        return meervoudsvorm;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMeervoudsvorm(String newMeervoudsvorm)
    {
        String oldMeervoudsvorm = meervoudsvorm;
        meervoudsvorm = newMeervoudsvorm;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.BRON__MEERVOUDSVORM, oldMeervoudsvorm, meervoudsvorm));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getIdentifier()
    {
        return identifier;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIdentifier(String newIdentifier)
    {
        String oldIdentifier = identifier;
        identifier = newIdentifier;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.BRON__IDENTIFIER, oldIdentifier, identifier));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Integer getVolgnummer()
    {
        return volgnummer;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVolgnummer(Integer newVolgnummer)
    {
        Integer oldVolgnummer = volgnummer;
        volgnummer = newVolgnummer;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.BRON__VOLGNUMMER, oldVolgnummer, volgnummer));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Formulier getFormulier()
    {
        if (eContainerFeatureID() != BmrPackage.BRON__FORMULIER) return null;
        return (Formulier)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetFormulier(Formulier newFormulier, NotificationChain msgs)
    {
        msgs = eBasicSetContainer((InternalEObject)newFormulier, BmrPackage.BRON__FORMULIER, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFormulier(Formulier newFormulier)
    {
        if (newFormulier != eInternalContainer() || (eContainerFeatureID() != BmrPackage.BRON__FORMULIER && newFormulier != null))
        {
            if (EcoreUtil.isAncestor(this, (EObject)newFormulier))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newFormulier != null)
                msgs = ((InternalEObject)newFormulier).eInverseAdd(this, BmrPackage.FORMULIER__BRONNEN, Formulier.class, msgs);
            msgs = basicSetFormulier(newFormulier, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.BRON__FORMULIER, newFormulier, newFormulier));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public List<Frame> getFrames()
    {
        if (frames == null)
        {
            frames = new EObjectWithInverseResolvingEList<Frame>(Frame.class, this, BmrPackage.BRON__FRAMES, BmrPackage.FRAME__BRON);
        }
        return frames;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ObjectType getObjectType()
    {
        if (objectType != null && ((EObject)objectType).eIsProxy())
        {
            InternalEObject oldObjectType = (InternalEObject)objectType;
            objectType = (ObjectType)eResolveProxy(oldObjectType);
            if (objectType != oldObjectType)
            {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BmrPackage.BRON__OBJECT_TYPE, oldObjectType, objectType));
            }
        }
        return objectType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ObjectType basicGetObjectType()
    {
        return objectType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setObjectType(ObjectType newObjectType)
    {
        ObjectType oldObjectType = objectType;
        objectType = newObjectType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.BRON__OBJECT_TYPE, oldObjectType, objectType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Attribuut getLink()
    {
        if (link != null && ((EObject)link).eIsProxy())
        {
            InternalEObject oldLink = (InternalEObject)link;
            link = (Attribuut)eResolveProxy(oldLink);
            if (link != oldLink)
            {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BmrPackage.BRON__LINK, oldLink, link));
            }
        }
        return link;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Attribuut basicGetLink()
    {
        return link;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLink(Attribuut newLink)
    {
        Attribuut oldLink = link;
        link = newLink;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.BRON__LINK, oldLink, link));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public List<BronAttribuut> getBronAttributen()
    {
        if (bronAttributen == null)
        {
            bronAttributen = new EObjectContainmentWithInverseEList<BronAttribuut>(BronAttribuut.class, this, BmrPackage.BRON__BRON_ATTRIBUTEN, BmrPackage.BRON_ATTRIBUUT__BRON);
        }
        return bronAttributen;
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
            case BmrPackage.BRON__FORMULIER:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetFormulier((Formulier)otherEnd, msgs);
            case BmrPackage.BRON__FRAMES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getFrames()).basicAdd(otherEnd, msgs);
            case BmrPackage.BRON__BRON_ATTRIBUTEN:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getBronAttributen()).basicAdd(otherEnd, msgs);
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
            case BmrPackage.BRON__FORMULIER:
                return basicSetFormulier(null, msgs);
            case BmrPackage.BRON__FRAMES:
                return ((InternalEList<?>)getFrames()).basicRemove(otherEnd, msgs);
            case BmrPackage.BRON__BRON_ATTRIBUTEN:
                return ((InternalEList<?>)getBronAttributen()).basicRemove(otherEnd, msgs);
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
            case BmrPackage.BRON__FORMULIER:
                return eInternalContainer().eInverseRemove(this, BmrPackage.FORMULIER__BRONNEN, Formulier.class, msgs);
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
            case BmrPackage.BRON__MEERVOUDSVORM:
                return getMeervoudsvorm();
            case BmrPackage.BRON__IDENTIFIER:
                return getIdentifier();
            case BmrPackage.BRON__VOLGNUMMER:
                return getVolgnummer();
            case BmrPackage.BRON__FORMULIER:
                return getFormulier();
            case BmrPackage.BRON__FRAMES:
                return getFrames();
            case BmrPackage.BRON__OBJECT_TYPE:
                if (resolve) return getObjectType();
                return basicGetObjectType();
            case BmrPackage.BRON__LINK:
                if (resolve) return getLink();
                return basicGetLink();
            case BmrPackage.BRON__BRON_ATTRIBUTEN:
                return getBronAttributen();
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
            case BmrPackage.BRON__MEERVOUDSVORM:
                setMeervoudsvorm((String)newValue);
                return;
            case BmrPackage.BRON__IDENTIFIER:
                setIdentifier((String)newValue);
                return;
            case BmrPackage.BRON__VOLGNUMMER:
                setVolgnummer((Integer)newValue);
                return;
            case BmrPackage.BRON__FORMULIER:
                setFormulier((Formulier)newValue);
                return;
            case BmrPackage.BRON__FRAMES:
                getFrames().clear();
                getFrames().addAll((Collection<? extends Frame>)newValue);
                return;
            case BmrPackage.BRON__OBJECT_TYPE:
                setObjectType((ObjectType)newValue);
                return;
            case BmrPackage.BRON__LINK:
                setLink((Attribuut)newValue);
                return;
            case BmrPackage.BRON__BRON_ATTRIBUTEN:
                getBronAttributen().clear();
                getBronAttributen().addAll((Collection<? extends BronAttribuut>)newValue);
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
            case BmrPackage.BRON__MEERVOUDSVORM:
                setMeervoudsvorm(MEERVOUDSVORM_EDEFAULT);
                return;
            case BmrPackage.BRON__IDENTIFIER:
                setIdentifier(IDENTIFIER_EDEFAULT);
                return;
            case BmrPackage.BRON__VOLGNUMMER:
                setVolgnummer(VOLGNUMMER_EDEFAULT);
                return;
            case BmrPackage.BRON__FORMULIER:
                setFormulier((Formulier)null);
                return;
            case BmrPackage.BRON__FRAMES:
                getFrames().clear();
                return;
            case BmrPackage.BRON__OBJECT_TYPE:
                setObjectType((ObjectType)null);
                return;
            case BmrPackage.BRON__LINK:
                setLink((Attribuut)null);
                return;
            case BmrPackage.BRON__BRON_ATTRIBUTEN:
                getBronAttributen().clear();
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
            case BmrPackage.BRON__MEERVOUDSVORM:
                return MEERVOUDSVORM_EDEFAULT == null ? meervoudsvorm != null : !MEERVOUDSVORM_EDEFAULT.equals(meervoudsvorm);
            case BmrPackage.BRON__IDENTIFIER:
                return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
            case BmrPackage.BRON__VOLGNUMMER:
                return VOLGNUMMER_EDEFAULT == null ? volgnummer != null : !VOLGNUMMER_EDEFAULT.equals(volgnummer);
            case BmrPackage.BRON__FORMULIER:
                return getFormulier() != null;
            case BmrPackage.BRON__FRAMES:
                return frames != null && !frames.isEmpty();
            case BmrPackage.BRON__OBJECT_TYPE:
                return objectType != null;
            case BmrPackage.BRON__LINK:
                return link != null;
            case BmrPackage.BRON__BRON_ATTRIBUTEN:
                return bronAttributen != null && !bronAttributen.isEmpty();
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
        result.append(" (meervoudsvorm: ");
        result.append(meervoudsvorm);
        result.append(", identifier: ");
        result.append(identifier);
        result.append(", volgnummer: ");
        result.append(volgnummer);
        result.append(')');
        return result.toString();
    }

} //BronImpl
