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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.BedrijfsRegel;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Groep;
import nl.bzk.brp.ecore.bmr.Historie;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel;
import nl.bzk.brp.ecore.bmr.Type;

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
 * An implementation of the model object '<em><b>Attribuut</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutImpl#getGroep <em>Groep</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutImpl#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutImpl#getType <em>Type</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutImpl#getAfleidbaar <em>Afleidbaar</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutImpl#getHistorieVastleggen <em>Historie Vastleggen</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutImpl#isVerplicht <em>Verplicht</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutImpl#getInverseAssociatieNaam <em>Inverse Associatie Naam</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutImpl#getInverseAssociatie <em>Inverse Associatie</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.AttribuutImpl#getGebruiktInBedrijfsRegels <em>Gebruikt In Bedrijfs Regels</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttribuutImpl extends GelaagdElementImpl implements Attribuut {

    /**
     * The cached value of the '{@link #getGroep() <em>Groep</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroep()
     * @generated
     * @ordered
     */
    protected Groep                 groep;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected Type                  type;

    /**
     * The default value of the '{@link #getAfleidbaar() <em>Afleidbaar</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAfleidbaar()
     * @generated
     * @ordered
     */
    protected static final Boolean  AFLEIDBAAR_EDEFAULT              = Boolean.FALSE;

    /**
     * The cached value of the '{@link #getAfleidbaar() <em>Afleidbaar</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAfleidbaar()
     * @generated
     * @ordered
     */
    protected Boolean               afleidbaar                       = AFLEIDBAAR_EDEFAULT;

    /**
     * The default value of the '{@link #getHistorieVastleggen() <em>Historie Vastleggen</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHistorieVastleggen()
     * @generated
     * @ordered
     */
    protected static final Historie HISTORIE_VASTLEGGEN_EDEFAULT     = Historie.G;

    /**
     * The cached value of the '{@link #getHistorieVastleggen() <em>Historie Vastleggen</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHistorieVastleggen()
     * @generated
     * @ordered
     */
    protected Historie              historieVastleggen               = HISTORIE_VASTLEGGEN_EDEFAULT;

    /**
     * The default value of the '{@link #isVerplicht() <em>Verplicht</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isVerplicht()
     * @generated
     * @ordered
     */
    protected static final boolean  VERPLICHT_EDEFAULT               = false;

    /**
     * The cached value of the '{@link #isVerplicht() <em>Verplicht</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isVerplicht()
     * @generated
     * @ordered
     */
    protected boolean               verplicht                        = VERPLICHT_EDEFAULT;

    /**
     * The default value of the '{@link #getInverseAssociatieNaam() <em>Inverse Associatie Naam</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInverseAssociatieNaam()
     * @generated
     * @ordered
     */
    protected static final String   INVERSE_ASSOCIATIE_NAAM_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getInverseAssociatieNaam() <em>Inverse Associatie Naam</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInverseAssociatieNaam()
     * @generated
     * @ordered
     */
    protected String                inverseAssociatieNaam            = INVERSE_ASSOCIATIE_NAAM_EDEFAULT;

    /**
     * The default value of the '{@link #getInverseAssociatie() <em>Inverse Associatie</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInverseAssociatie()
     * @generated
     * @ordered
     */
    protected static final String   INVERSE_ASSOCIATIE_EDEFAULT      = null;

    /**
     * The cached value of the '{@link #getInverseAssociatie() <em>Inverse Associatie</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInverseAssociatie()
     * @generated
     * @ordered
     */
    protected String                inverseAssociatie                = INVERSE_ASSOCIATIE_EDEFAULT;

    /**
     * The cached value of the '{@link #getGebruiktInBedrijfsRegels() <em>Gebruikt In Bedrijfs Regels</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGebruiktInBedrijfsRegels()
     * @generated
     * @ordered
     */
    protected EList<BedrijfsRegel>  gebruiktInBedrijfsRegels;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AttribuutImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return BmrPackage.Literals.ATTRIBUUT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Groep getGroep() {
        if (groep != null && groep.eIsProxy())
        {
            InternalEObject oldGroep = (InternalEObject)groep;
            groep = (Groep)eResolveProxy(oldGroep);
            if (groep != oldGroep)
            {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BmrPackage.ATTRIBUUT__GROEP, oldGroep, groep));
            }
        }
        return groep;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Groep basicGetGroep() {
        return groep;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGroep(Groep newGroep, NotificationChain msgs) {
        Groep oldGroep = groep;
        groep = newGroep;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT__GROEP, oldGroep, newGroep);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setGroep(Groep newGroep) {
        if (newGroep != groep)
        {
            NotificationChain msgs = null;
            if (groep != null)
                msgs = ((InternalEObject)groep).eInverseRemove(this, BmrPackage.GROEP__ATTRIBUTEN, Groep.class, msgs);
            if (newGroep != null)
                msgs = ((InternalEObject)newGroep).eInverseAdd(this, BmrPackage.GROEP__ATTRIBUTEN, Groep.class, msgs);
            msgs = basicSetGroep(newGroep, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT__GROEP, newGroep, newGroep));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ObjectType getObjectType() {
        if (eContainerFeatureID() != BmrPackage.ATTRIBUUT__OBJECT_TYPE) return null;
        return (ObjectType)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetObjectType(ObjectType newObjectType, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newObjectType, BmrPackage.ATTRIBUUT__OBJECT_TYPE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setObjectType(ObjectType newObjectType) {
        if (newObjectType != eInternalContainer() || (eContainerFeatureID() != BmrPackage.ATTRIBUUT__OBJECT_TYPE && newObjectType != null))
        {
            if (EcoreUtil.isAncestor(this, newObjectType))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newObjectType != null)
                msgs = ((InternalEObject)newObjectType).eInverseAdd(this, BmrPackage.OBJECT_TYPE__ATTRIBUTEN, ObjectType.class, msgs);
            msgs = basicSetObjectType(newObjectType, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT__OBJECT_TYPE, newObjectType, newObjectType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Type getType() {
        if (type != null && type.eIsProxy())
        {
            InternalEObject oldType = (InternalEObject)type;
            type = (Type)eResolveProxy(oldType);
            if (type != oldType)
            {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BmrPackage.ATTRIBUUT__TYPE, oldType, type));
            }
        }
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Type basicGetType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setType(Type newType) {
        Type oldType = type;
        type = newType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT__TYPE, oldType, type));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Boolean getAfleidbaar() {
        return afleidbaar;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setAfleidbaar(Boolean newAfleidbaar) {
        Boolean oldAfleidbaar = afleidbaar;
        afleidbaar = newAfleidbaar;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT__AFLEIDBAAR, oldAfleidbaar, afleidbaar));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Historie getHistorieVastleggen() {
        return historieVastleggen;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setHistorieVastleggen(Historie newHistorieVastleggen) {
        Historie oldHistorieVastleggen = historieVastleggen;
        historieVastleggen = newHistorieVastleggen == null ? HISTORIE_VASTLEGGEN_EDEFAULT : newHistorieVastleggen;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT__HISTORIE_VASTLEGGEN, oldHistorieVastleggen, historieVastleggen));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isVerplicht()
    {
        return verplicht;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getInverseAssociatieNaam() {
        return inverseAssociatieNaam;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setInverseAssociatieNaam(String newInverseAssociatieNaam) {
        String oldInverseAssociatieNaam = inverseAssociatieNaam;
        inverseAssociatieNaam = newInverseAssociatieNaam;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT__INVERSE_ASSOCIATIE_NAAM, oldInverseAssociatieNaam, inverseAssociatieNaam));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getInverseAssociatie() {
        return inverseAssociatie;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setInverseAssociatie(String newInverseAssociatie) {
        String oldInverseAssociatie = inverseAssociatie;
        inverseAssociatie = newInverseAssociatie;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT__INVERSE_ASSOCIATIE, oldInverseAssociatie, inverseAssociatie));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<BedrijfsRegel> getGebruiktInBedrijfsRegels() {
        if (gebruiktInBedrijfsRegels == null)
        {
            gebruiktInBedrijfsRegels = new EObjectWithInverseResolvingEList.ManyInverse<BedrijfsRegel>(BedrijfsRegel.class, this, BmrPackage.ATTRIBUUT__GEBRUIKT_IN_BEDRIJFS_REGELS, BmrPackage.BEDRIJFS_REGEL__ATTRIBUTEN);
        }
        return gebruiktInBedrijfsRegels;
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
            case BmrPackage.ATTRIBUUT__GROEP:
                if (groep != null)
                    msgs = ((InternalEObject)groep).eInverseRemove(this, BmrPackage.GROEP__ATTRIBUTEN, Groep.class, msgs);
                return basicSetGroep((Groep)otherEnd, msgs);
            case BmrPackage.ATTRIBUUT__OBJECT_TYPE:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetObjectType((ObjectType)otherEnd, msgs);
            case BmrPackage.ATTRIBUUT__GEBRUIKT_IN_BEDRIJFS_REGELS:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getGebruiktInBedrijfsRegels()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID)
        {
            case BmrPackage.ATTRIBUUT__GROEP:
                return basicSetGroep(null, msgs);
            case BmrPackage.ATTRIBUUT__OBJECT_TYPE:
                return basicSetObjectType(null, msgs);
            case BmrPackage.ATTRIBUUT__GEBRUIKT_IN_BEDRIJFS_REGELS:
                return ((InternalEList<?>)getGebruiktInBedrijfsRegels()).basicRemove(otherEnd, msgs);
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
            case BmrPackage.ATTRIBUUT__OBJECT_TYPE:
                return eInternalContainer().eInverseRemove(this, BmrPackage.OBJECT_TYPE__ATTRIBUTEN, ObjectType.class, msgs);
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
            case BmrPackage.ATTRIBUUT__GROEP:
                if (resolve) return getGroep();
                return basicGetGroep();
            case BmrPackage.ATTRIBUUT__OBJECT_TYPE:
                return getObjectType();
            case BmrPackage.ATTRIBUUT__TYPE:
                if (resolve) return getType();
                return basicGetType();
            case BmrPackage.ATTRIBUUT__AFLEIDBAAR:
                return getAfleidbaar();
            case BmrPackage.ATTRIBUUT__HISTORIE_VASTLEGGEN:
                return getHistorieVastleggen();
            case BmrPackage.ATTRIBUUT__VERPLICHT:
                return isVerplicht();
            case BmrPackage.ATTRIBUUT__INVERSE_ASSOCIATIE_NAAM:
                return getInverseAssociatieNaam();
            case BmrPackage.ATTRIBUUT__INVERSE_ASSOCIATIE:
                return getInverseAssociatie();
            case BmrPackage.ATTRIBUUT__GEBRUIKT_IN_BEDRIJFS_REGELS:
                return getGebruiktInBedrijfsRegels();
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
            case BmrPackage.ATTRIBUUT__GROEP:
                setGroep((Groep)newValue);
                return;
            case BmrPackage.ATTRIBUUT__OBJECT_TYPE:
                setObjectType((ObjectType)newValue);
                return;
            case BmrPackage.ATTRIBUUT__TYPE:
                setType((Type)newValue);
                return;
            case BmrPackage.ATTRIBUUT__AFLEIDBAAR:
                setAfleidbaar((Boolean)newValue);
                return;
            case BmrPackage.ATTRIBUUT__HISTORIE_VASTLEGGEN:
                setHistorieVastleggen((Historie)newValue);
                return;
            case BmrPackage.ATTRIBUUT__VERPLICHT:
                setVerplicht((Boolean)newValue);
                return;
            case BmrPackage.ATTRIBUUT__INVERSE_ASSOCIATIE_NAAM:
                setInverseAssociatieNaam((String)newValue);
                return;
            case BmrPackage.ATTRIBUUT__INVERSE_ASSOCIATIE:
                setInverseAssociatie((String)newValue);
                return;
            case BmrPackage.ATTRIBUUT__GEBRUIKT_IN_BEDRIJFS_REGELS:
                getGebruiktInBedrijfsRegels().clear();
                getGebruiktInBedrijfsRegels().addAll((Collection<? extends BedrijfsRegel>)newValue);
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
            case BmrPackage.ATTRIBUUT__GROEP:
                setGroep((Groep)null);
                return;
            case BmrPackage.ATTRIBUUT__OBJECT_TYPE:
                setObjectType((ObjectType)null);
                return;
            case BmrPackage.ATTRIBUUT__TYPE:
                setType((Type)null);
                return;
            case BmrPackage.ATTRIBUUT__AFLEIDBAAR:
                setAfleidbaar(AFLEIDBAAR_EDEFAULT);
                return;
            case BmrPackage.ATTRIBUUT__HISTORIE_VASTLEGGEN:
                setHistorieVastleggen(HISTORIE_VASTLEGGEN_EDEFAULT);
                return;
            case BmrPackage.ATTRIBUUT__VERPLICHT:
                setVerplicht(VERPLICHT_EDEFAULT);
                return;
            case BmrPackage.ATTRIBUUT__INVERSE_ASSOCIATIE_NAAM:
                setInverseAssociatieNaam(INVERSE_ASSOCIATIE_NAAM_EDEFAULT);
                return;
            case BmrPackage.ATTRIBUUT__INVERSE_ASSOCIATIE:
                setInverseAssociatie(INVERSE_ASSOCIATIE_EDEFAULT);
                return;
            case BmrPackage.ATTRIBUUT__GEBRUIKT_IN_BEDRIJFS_REGELS:
                getGebruiktInBedrijfsRegels().clear();
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
            case BmrPackage.ATTRIBUUT__GROEP:
                return groep != null;
            case BmrPackage.ATTRIBUUT__OBJECT_TYPE:
                return getObjectType() != null;
            case BmrPackage.ATTRIBUUT__TYPE:
                return type != null;
            case BmrPackage.ATTRIBUUT__AFLEIDBAAR:
                return AFLEIDBAAR_EDEFAULT == null ? afleidbaar != null : !AFLEIDBAAR_EDEFAULT.equals(afleidbaar);
            case BmrPackage.ATTRIBUUT__HISTORIE_VASTLEGGEN:
                return historieVastleggen != HISTORIE_VASTLEGGEN_EDEFAULT;
            case BmrPackage.ATTRIBUUT__VERPLICHT:
                return verplicht != VERPLICHT_EDEFAULT;
            case BmrPackage.ATTRIBUUT__INVERSE_ASSOCIATIE_NAAM:
                return INVERSE_ASSOCIATIE_NAAM_EDEFAULT == null ? inverseAssociatieNaam != null : !INVERSE_ASSOCIATIE_NAAM_EDEFAULT.equals(inverseAssociatieNaam);
            case BmrPackage.ATTRIBUUT__INVERSE_ASSOCIATIE:
                return INVERSE_ASSOCIATIE_EDEFAULT == null ? inverseAssociatie != null : !INVERSE_ASSOCIATIE_EDEFAULT.equals(inverseAssociatie);
            case BmrPackage.ATTRIBUUT__GEBRUIKT_IN_BEDRIJFS_REGELS:
                return gebruiktInBedrijfsRegels != null && !gebruiktInBedrijfsRegels.isEmpty();
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
        result.append(" (afleidbaar: ");
        result.append(afleidbaar);
        result.append(", historieVastleggen: ");
        result.append(historieVastleggen);
        result.append(", verplicht: ");
        result.append(verplicht);
        result.append(", inverseAssociatieNaam: ");
        result.append(inverseAssociatieNaam);
        result.append(", inverseAssociatie: ");
        result.append(inverseAssociatie);
        result.append(')');
        return result.toString();
    }

    @Override
    public boolean isIdentifier() {
        return Iterables.any(getGebruiktInBedrijfsRegels(), new Predicate<BedrijfsRegel>() {

            @Override
            public boolean apply(final BedrijfsRegel bedrijfsRegel) {
                return SoortBedrijfsRegel.ID == bedrijfsRegel.getSoortBedrijfsRegel();
            }
        });
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setVerplicht(boolean newVerplicht) {
        boolean oldVerplicht = verplicht;
        verplicht = newVerplicht;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.ATTRIBUUT__VERPLICHT, oldVerplicht, verplicht));
    }

    @Override
    public boolean isAfleidbaar() {
        return getAfleidbaar();
    }

} // AttribuutImpl
