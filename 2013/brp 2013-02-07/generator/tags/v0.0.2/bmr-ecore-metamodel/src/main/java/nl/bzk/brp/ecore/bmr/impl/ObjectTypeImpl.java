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
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Groep;
import nl.bzk.brp.ecore.bmr.InSetOfModel;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.Schema;
import nl.bzk.brp.ecore.bmr.SoortInhoud;
import nl.bzk.brp.ecore.bmr.Tuple;
import nl.bzk.brp.ecore.bmr.Versie;

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
 * An implementation of the model object '<em><b>Object Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getAttributen <em>Attributen</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getGroepen <em>Groepen</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getInSetOfModel <em>In Set Of Model</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getMeervoudsNaam <em>Meervouds Naam</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getSoortInhoud <em>Soort Inhoud</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getSubTypes <em>Sub Types</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getSuperType <em>Super Type</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getTuples <em>Tuples</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getVersie <em>Versie</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getAfleidbaar <em>Afleidbaar</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getHistorieVastleggen <em>Historie Vastleggen</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getKunstmatigeSleutel <em>Kunstmatige Sleutel</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl#getLookup <em>Lookup</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ObjectTypeImpl extends TypeImpl implements ObjectType {

    /**
     * The cached value of the '{@link #getAttributen() <em>Attributen</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getAttributen()
     * @generated
     * @ordered
     */
    protected EList<Attribuut>          attributen;

    /**
     * The cached value of the '{@link #getGroepen() <em>Groepen</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getGroepen()
     * @generated
     * @ordered
     */
    protected EList<Groep>              groepen;

    /**
     * The default value of the '{@link #getInSetOfModel() <em>In Set Of Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getInSetOfModel()
     * @generated
     * @ordered
     */
    protected static final InSetOfModel IN_SET_OF_MODEL_EDEFAULT     = InSetOfModel.SET;

    /**
     * The cached value of the '{@link #getInSetOfModel() <em>In Set Of Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getInSetOfModel()
     * @generated
     * @ordered
     */
    protected InSetOfModel              inSetOfModel                 = IN_SET_OF_MODEL_EDEFAULT;

    /**
     * The default value of the '{@link #getMeervoudsNaam() <em>Meervouds Naam</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getMeervoudsNaam()
     * @generated
     * @ordered
     */
    protected static final String       MEERVOUDS_NAAM_EDEFAULT      = null;

    /**
     * The cached value of the '{@link #getMeervoudsNaam() <em>Meervouds Naam</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getMeervoudsNaam()
     * @generated
     * @ordered
     */
    protected String                    meervoudsNaam                = MEERVOUDS_NAAM_EDEFAULT;

    /**
     * The default value of the '{@link #getSoortInhoud() <em>Soort Inhoud</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getSoortInhoud()
     * @generated
     * @ordered
     */
    protected static final SoortInhoud  SOORT_INHOUD_EDEFAULT        = SoortInhoud.S;

    /**
     * The cached value of the '{@link #getSoortInhoud() <em>Soort Inhoud</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getSoortInhoud()
     * @generated
     * @ordered
     */
    protected SoortInhoud               soortInhoud                  = SOORT_INHOUD_EDEFAULT;

    /**
     * The cached value of the '{@link #getSubTypes() <em>Sub Types</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getSubTypes()
     * @generated
     * @ordered
     */
    protected EList<ObjectType>         subTypes;

    /**
     * The cached value of the '{@link #getSuperType() <em>Super Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getSuperType()
     * @generated
     * @ordered
     */
    protected ObjectType                superType;

    /**
     * The cached value of the '{@link #getTuples() <em>Tuples</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getTuples()
     * @generated
     * @ordered
     */
    protected EList<Tuple>              tuples;

    /**
     * The default value of the '{@link #getAfleidbaar() <em>Afleidbaar</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getAfleidbaar()
     * @generated
     * @ordered
     */
    protected static final Boolean      AFLEIDBAAR_EDEFAULT          = Boolean.FALSE;

    /**
     * The cached value of the '{@link #getAfleidbaar() <em>Afleidbaar</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getAfleidbaar()
     * @generated
     * @ordered
     */
    protected Boolean                   afleidbaar                   = AFLEIDBAAR_EDEFAULT;

    /**
     * The default value of the '{@link #getHistorieVastleggen() <em>Historie Vastleggen</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getHistorieVastleggen()
     * @generated
     * @ordered
     */
    protected static final String       HISTORIE_VASTLEGGEN_EDEFAULT = "false";

    /**
     * The cached value of the '{@link #getHistorieVastleggen() <em>Historie Vastleggen</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getHistorieVastleggen()
     * @generated
     * @ordered
     */
    protected String                    historieVastleggen           = HISTORIE_VASTLEGGEN_EDEFAULT;

    /**
     * The default value of the '{@link #getKunstmatigeSleutel() <em>Kunstmatige Sleutel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getKunstmatigeSleutel()
     * @generated
     * @ordered
     */
    protected static final Boolean      KUNSTMATIGE_SLEUTEL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getKunstmatigeSleutel() <em>Kunstmatige Sleutel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getKunstmatigeSleutel()
     * @generated
     * @ordered
     */
    protected Boolean                   kunstmatigeSleutel           = KUNSTMATIGE_SLEUTEL_EDEFAULT;

    /**
     * The default value of the '{@link #getLookup() <em>Lookup</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getLookup()
     * @generated
     * @ordered
     */
    protected static final Boolean      LOOKUP_EDEFAULT              = Boolean.FALSE;

    /**
     * The cached value of the '{@link #getLookup() <em>Lookup</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getLookup()
     * @generated
     * @ordered
     */
    protected Boolean                   lookup                       = LOOKUP_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected ObjectTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return BmrPackage.Literals.OBJECT_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Attribuut> getAttributen() {
        if (attributen == null) {
            attributen =
                new EObjectContainmentWithInverseEList<Attribuut>(Attribuut.class, this,
                        BmrPackage.OBJECT_TYPE__ATTRIBUTEN, BmrPackage.ATTRIBUUT__OBJECT_TYPE);
        }
        return attributen;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Groep> getGroepen() {
        if (groepen == null) {
            groepen =
                new EObjectContainmentWithInverseEList<Groep>(Groep.class, this, BmrPackage.OBJECT_TYPE__GROEPEN,
                        BmrPackage.GROEP__OBJECT_TYPE);
        }
        return groepen;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public InSetOfModel getInSetOfModel() {
        return inSetOfModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setInSetOfModel(final InSetOfModel newInSetOfModel) {
        InSetOfModel oldInSetOfModel = inSetOfModel;
        inSetOfModel = newInSetOfModel == null ? IN_SET_OF_MODEL_EDEFAULT : newInSetOfModel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.OBJECT_TYPE__IN_SET_OF_MODEL,
                    oldInSetOfModel, inSetOfModel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getMeervoudsNaam() {
        return meervoudsNaam;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setMeervoudsNaam(final String newMeervoudsNaam) {
        String oldMeervoudsNaam = meervoudsNaam;
        meervoudsNaam = newMeervoudsNaam;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.OBJECT_TYPE__MEERVOUDS_NAAM,
                    oldMeervoudsNaam, meervoudsNaam));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SoortInhoud getSoortInhoud() {
        return soortInhoud;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSoortInhoud(final SoortInhoud newSoortInhoud) {
        SoortInhoud oldSoortInhoud = soortInhoud;
        soortInhoud = newSoortInhoud == null ? SOORT_INHOUD_EDEFAULT : newSoortInhoud;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.OBJECT_TYPE__SOORT_INHOUD, oldSoortInhoud,
                    soortInhoud));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ObjectType> getSubTypes() {
        if (subTypes == null) {
            subTypes =
                new EObjectWithInverseResolvingEList<ObjectType>(ObjectType.class, this,
                        BmrPackage.OBJECT_TYPE__SUB_TYPES, BmrPackage.OBJECT_TYPE__SUPER_TYPE);
        }
        return subTypes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ObjectType getSuperType() {
        if (superType != null && superType.eIsProxy()) {
            InternalEObject oldSuperType = (InternalEObject) superType;
            superType = (ObjectType) eResolveProxy(oldSuperType);
            if (superType != oldSuperType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BmrPackage.OBJECT_TYPE__SUPER_TYPE,
                            oldSuperType, superType));
            }
        }
        return superType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public ObjectType basicGetSuperType() {
        return superType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetSuperType(final ObjectType newSuperType, NotificationChain msgs) {
        ObjectType oldSuperType = superType;
        superType = newSuperType;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                new ENotificationImpl(this, Notification.SET, BmrPackage.OBJECT_TYPE__SUPER_TYPE, oldSuperType,
                        newSuperType);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSuperType(final ObjectType newSuperType) {
        if (newSuperType != superType) {
            NotificationChain msgs = null;
            if (superType != null)
                msgs =
                    ((InternalEObject) superType).eInverseRemove(this, BmrPackage.OBJECT_TYPE__SUB_TYPES,
                            ObjectType.class, msgs);
            if (newSuperType != null)
                msgs =
                    ((InternalEObject) newSuperType).eInverseAdd(this, BmrPackage.OBJECT_TYPE__SUB_TYPES,
                            ObjectType.class, msgs);
            msgs = basicSetSuperType(newSuperType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.OBJECT_TYPE__SUPER_TYPE, newSuperType,
                    newSuperType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Tuple> getTuples() {
        if (tuples == null) {
            tuples =
                new EObjectContainmentWithInverseEList<Tuple>(Tuple.class, this, BmrPackage.OBJECT_TYPE__TUPLES,
                        BmrPackage.TUPLE__OBJECT_TYPE);
        }
        return tuples;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Versie getVersie() {
        if (eContainerFeatureID() != BmrPackage.OBJECT_TYPE__VERSIE)
            return null;
        return (Versie) eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetVersie(final Versie newVersie, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject) newVersie, BmrPackage.OBJECT_TYPE__VERSIE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setVersie(final Versie newVersie) {
        if (newVersie != eInternalContainer()
            || (eContainerFeatureID() != BmrPackage.OBJECT_TYPE__VERSIE && newVersie != null))
        {
            if (EcoreUtil.isAncestor(this, newVersie))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newVersie != null)
                msgs =
                    ((InternalEObject) newVersie)
                            .eInverseAdd(this, BmrPackage.VERSIE__OBJECT_TYPES, Versie.class, msgs);
            msgs = basicSetVersie(newVersie, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.OBJECT_TYPE__VERSIE, newVersie, newVersie));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Boolean getAfleidbaar() {
        return afleidbaar;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setAfleidbaar(final Boolean newAfleidbaar) {
        Boolean oldAfleidbaar = afleidbaar;
        afleidbaar = newAfleidbaar;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.OBJECT_TYPE__AFLEIDBAAR, oldAfleidbaar,
                    afleidbaar));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getHistorieVastleggen() {
        return historieVastleggen;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setHistorieVastleggen(final String newHistorieVastleggen) {
        String oldHistorieVastleggen = historieVastleggen;
        historieVastleggen = newHistorieVastleggen;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.OBJECT_TYPE__HISTORIE_VASTLEGGEN,
                    oldHistorieVastleggen, historieVastleggen));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Boolean getKunstmatigeSleutel() {
        return kunstmatigeSleutel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setKunstmatigeSleutel(final Boolean newKunstmatigeSleutel) {
        Boolean oldKunstmatigeSleutel = kunstmatigeSleutel;
        kunstmatigeSleutel = newKunstmatigeSleutel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.OBJECT_TYPE__KUNSTMATIGE_SLEUTEL,
                    oldKunstmatigeSleutel, kunstmatigeSleutel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Boolean getLookup() {
        return lookup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLookup(final Boolean newLookup) {
        Boolean oldLookup = lookup;
        lookup = newLookup;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.OBJECT_TYPE__LOOKUP, oldLookup, lookup));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
            case BmrPackage.OBJECT_TYPE__ATTRIBUTEN:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) getAttributen()).basicAdd(otherEnd, msgs);
            case BmrPackage.OBJECT_TYPE__GROEPEN:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) getGroepen()).basicAdd(otherEnd, msgs);
            case BmrPackage.OBJECT_TYPE__SUB_TYPES:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) getSubTypes()).basicAdd(otherEnd, msgs);
            case BmrPackage.OBJECT_TYPE__SUPER_TYPE:
                if (superType != null)
                    msgs =
                        ((InternalEObject) superType).eInverseRemove(this, BmrPackage.OBJECT_TYPE__SUB_TYPES,
                                ObjectType.class, msgs);
                return basicSetSuperType((ObjectType) otherEnd, msgs);
            case BmrPackage.OBJECT_TYPE__TUPLES:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) getTuples()).basicAdd(otherEnd, msgs);
            case BmrPackage.OBJECT_TYPE__VERSIE:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetVersie((Versie) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs)
    {
        switch (featureID) {
            case BmrPackage.OBJECT_TYPE__ATTRIBUTEN:
                return ((InternalEList<?>) getAttributen()).basicRemove(otherEnd, msgs);
            case BmrPackage.OBJECT_TYPE__GROEPEN:
                return ((InternalEList<?>) getGroepen()).basicRemove(otherEnd, msgs);
            case BmrPackage.OBJECT_TYPE__SUB_TYPES:
                return ((InternalEList<?>) getSubTypes()).basicRemove(otherEnd, msgs);
            case BmrPackage.OBJECT_TYPE__SUPER_TYPE:
                return basicSetSuperType(null, msgs);
            case BmrPackage.OBJECT_TYPE__TUPLES:
                return ((InternalEList<?>) getTuples()).basicRemove(otherEnd, msgs);
            case BmrPackage.OBJECT_TYPE__VERSIE:
                return basicSetVersie(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(final NotificationChain msgs) {
        switch (eContainerFeatureID()) {
            case BmrPackage.OBJECT_TYPE__VERSIE:
                return eInternalContainer().eInverseRemove(this, BmrPackage.VERSIE__OBJECT_TYPES, Versie.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
            case BmrPackage.OBJECT_TYPE__ATTRIBUTEN:
                return getAttributen();
            case BmrPackage.OBJECT_TYPE__GROEPEN:
                return getGroepen();
            case BmrPackage.OBJECT_TYPE__IN_SET_OF_MODEL:
                return getInSetOfModel();
            case BmrPackage.OBJECT_TYPE__MEERVOUDS_NAAM:
                return getMeervoudsNaam();
            case BmrPackage.OBJECT_TYPE__SOORT_INHOUD:
                return getSoortInhoud();
            case BmrPackage.OBJECT_TYPE__SUB_TYPES:
                return getSubTypes();
            case BmrPackage.OBJECT_TYPE__SUPER_TYPE:
                if (resolve)
                    return getSuperType();
                return basicGetSuperType();
            case BmrPackage.OBJECT_TYPE__TUPLES:
                return getTuples();
            case BmrPackage.OBJECT_TYPE__VERSIE:
                return getVersie();
            case BmrPackage.OBJECT_TYPE__AFLEIDBAAR:
                return getAfleidbaar();
            case BmrPackage.OBJECT_TYPE__HISTORIE_VASTLEGGEN:
                return getHistorieVastleggen();
            case BmrPackage.OBJECT_TYPE__KUNSTMATIGE_SLEUTEL:
                return getKunstmatigeSleutel();
            case BmrPackage.OBJECT_TYPE__LOOKUP:
                return getLookup();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
            case BmrPackage.OBJECT_TYPE__ATTRIBUTEN:
                getAttributen().clear();
                getAttributen().addAll((Collection<? extends Attribuut>) newValue);
                return;
            case BmrPackage.OBJECT_TYPE__GROEPEN:
                getGroepen().clear();
                getGroepen().addAll((Collection<? extends Groep>) newValue);
                return;
            case BmrPackage.OBJECT_TYPE__IN_SET_OF_MODEL:
                setInSetOfModel((InSetOfModel) newValue);
                return;
            case BmrPackage.OBJECT_TYPE__MEERVOUDS_NAAM:
                setMeervoudsNaam((String) newValue);
                return;
            case BmrPackage.OBJECT_TYPE__SOORT_INHOUD:
                setSoortInhoud((SoortInhoud) newValue);
                return;
            case BmrPackage.OBJECT_TYPE__SUB_TYPES:
                getSubTypes().clear();
                getSubTypes().addAll((Collection<? extends ObjectType>) newValue);
                return;
            case BmrPackage.OBJECT_TYPE__SUPER_TYPE:
                setSuperType((ObjectType) newValue);
                return;
            case BmrPackage.OBJECT_TYPE__TUPLES:
                getTuples().clear();
                getTuples().addAll((Collection<? extends Tuple>) newValue);
                return;
            case BmrPackage.OBJECT_TYPE__VERSIE:
                setVersie((Versie) newValue);
                return;
            case BmrPackage.OBJECT_TYPE__AFLEIDBAAR:
                setAfleidbaar((Boolean) newValue);
                return;
            case BmrPackage.OBJECT_TYPE__HISTORIE_VASTLEGGEN:
                setHistorieVastleggen((String) newValue);
                return;
            case BmrPackage.OBJECT_TYPE__KUNSTMATIGE_SLEUTEL:
                setKunstmatigeSleutel((Boolean) newValue);
                return;
            case BmrPackage.OBJECT_TYPE__LOOKUP:
                setLookup((Boolean) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(final int featureID) {
        switch (featureID) {
            case BmrPackage.OBJECT_TYPE__ATTRIBUTEN:
                getAttributen().clear();
                return;
            case BmrPackage.OBJECT_TYPE__GROEPEN:
                getGroepen().clear();
                return;
            case BmrPackage.OBJECT_TYPE__IN_SET_OF_MODEL:
                setInSetOfModel(IN_SET_OF_MODEL_EDEFAULT);
                return;
            case BmrPackage.OBJECT_TYPE__MEERVOUDS_NAAM:
                setMeervoudsNaam(MEERVOUDS_NAAM_EDEFAULT);
                return;
            case BmrPackage.OBJECT_TYPE__SOORT_INHOUD:
                setSoortInhoud(SOORT_INHOUD_EDEFAULT);
                return;
            case BmrPackage.OBJECT_TYPE__SUB_TYPES:
                getSubTypes().clear();
                return;
            case BmrPackage.OBJECT_TYPE__SUPER_TYPE:
                setSuperType((ObjectType) null);
                return;
            case BmrPackage.OBJECT_TYPE__TUPLES:
                getTuples().clear();
                return;
            case BmrPackage.OBJECT_TYPE__VERSIE:
                setVersie((Versie) null);
                return;
            case BmrPackage.OBJECT_TYPE__AFLEIDBAAR:
                setAfleidbaar(AFLEIDBAAR_EDEFAULT);
                return;
            case BmrPackage.OBJECT_TYPE__HISTORIE_VASTLEGGEN:
                setHistorieVastleggen(HISTORIE_VASTLEGGEN_EDEFAULT);
                return;
            case BmrPackage.OBJECT_TYPE__KUNSTMATIGE_SLEUTEL:
                setKunstmatigeSleutel(KUNSTMATIGE_SLEUTEL_EDEFAULT);
                return;
            case BmrPackage.OBJECT_TYPE__LOOKUP:
                setLookup(LOOKUP_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(final int featureID) {
        switch (featureID) {
            case BmrPackage.OBJECT_TYPE__ATTRIBUTEN:
                return attributen != null && !attributen.isEmpty();
            case BmrPackage.OBJECT_TYPE__GROEPEN:
                return groepen != null && !groepen.isEmpty();
            case BmrPackage.OBJECT_TYPE__IN_SET_OF_MODEL:
                return inSetOfModel != IN_SET_OF_MODEL_EDEFAULT;
            case BmrPackage.OBJECT_TYPE__MEERVOUDS_NAAM:
                return MEERVOUDS_NAAM_EDEFAULT == null ? meervoudsNaam != null : !MEERVOUDS_NAAM_EDEFAULT
                        .equals(meervoudsNaam);
            case BmrPackage.OBJECT_TYPE__SOORT_INHOUD:
                return soortInhoud != SOORT_INHOUD_EDEFAULT;
            case BmrPackage.OBJECT_TYPE__SUB_TYPES:
                return subTypes != null && !subTypes.isEmpty();
            case BmrPackage.OBJECT_TYPE__SUPER_TYPE:
                return superType != null;
            case BmrPackage.OBJECT_TYPE__TUPLES:
                return tuples != null && !tuples.isEmpty();
            case BmrPackage.OBJECT_TYPE__VERSIE:
                return getVersie() != null;
            case BmrPackage.OBJECT_TYPE__AFLEIDBAAR:
                return AFLEIDBAAR_EDEFAULT == null ? afleidbaar != null : !AFLEIDBAAR_EDEFAULT.equals(afleidbaar);
            case BmrPackage.OBJECT_TYPE__HISTORIE_VASTLEGGEN:
                return HISTORIE_VASTLEGGEN_EDEFAULT == null ? historieVastleggen != null
                    : !HISTORIE_VASTLEGGEN_EDEFAULT.equals(historieVastleggen);
            case BmrPackage.OBJECT_TYPE__KUNSTMATIGE_SLEUTEL:
                return KUNSTMATIGE_SLEUTEL_EDEFAULT == null ? kunstmatigeSleutel != null
                    : !KUNSTMATIGE_SLEUTEL_EDEFAULT.equals(kunstmatigeSleutel);
            case BmrPackage.OBJECT_TYPE__LOOKUP:
                return LOOKUP_EDEFAULT == null ? lookup != null : !LOOKUP_EDEFAULT.equals(lookup);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (inSetOfModel: ");
        result.append(inSetOfModel);
        result.append(", meervoudsNaam: ");
        result.append(meervoudsNaam);
        result.append(", soortInhoud: ");
        result.append(soortInhoud);
        result.append(", afleidbaar: ");
        result.append(afleidbaar);
        result.append(", historieVastleggen: ");
        result.append(historieVastleggen);
        result.append(", kunstmatigeSleutel: ");
        result.append(kunstmatigeSleutel);
        result.append(", lookup: ");
        result.append(lookup);
        result.append(')');
        return result.toString();
    }

    @Override
    public Attribuut getAttribuut(final String naam) {
        for (Attribuut attribuut : attributen) {
            if (attribuut.getNaam().equals(naam)) {
                return attribuut;
            }
        }
        return null;
    }

    /**
     * Handige methode om te bepalen of dit objectType tuples heeft waarvan de code niet <code>null</code> is. De
     * aanname is dat als één code niet <code>null</code> is, de andere codes dat ook niet zijn, en vice versa.
     *
     * @return <code>true</code> als dit objectType een tuple heeft met een niet <code>null</code> code, en anders
     *         <code>false</code>.
     */
    @Override
    public boolean hasCode() {
        boolean result = false;
        if (SoortInhoud.X == getSoortInhoud()) {
            for (Tuple tuple : getTuples()) {
                result = tuple.getCode() != null;
                break;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAttribuutType() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isObjectType() {
        return true;
    }

    @Override
    public boolean isKunstmatigeSleutel() {
        return kunstmatigeSleutel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHistorieObject() {
        return getIdentifierCode().toLowerCase().startsWith("his_");
    }

    @Override
    public Schema getSchema() {
        if (getVersie() != null) {
            return getVersie().getSchema();
        }
        return null;
    }

    @Override
    public Attribuut getIdAttribuut() {
        return Iterables.find(getAttributen(), new Predicate<Attribuut>() {

            @Override
            public boolean apply(final Attribuut attribuut) {
                return attribuut.isIdentifier();
            }
        });
    }

    @Override
    public boolean isEnumeratie() {
        return getSoortInhoud() == SoortInhoud.X && !getTuples().isEmpty()
            && !Iterables.any(getAttributen(), new Predicate<Attribuut>() {

                @Override
                public boolean apply(final Attribuut attribuut) {
                    return attribuut.getType().isObjectType();
                }
            });
    }
} // ObjectTypeImpl
