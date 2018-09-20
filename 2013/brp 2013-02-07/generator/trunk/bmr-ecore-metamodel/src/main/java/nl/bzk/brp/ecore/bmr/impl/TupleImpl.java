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

import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.Tuple;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tuple</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getId <em>Id</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getRelatiefId <em>Relatief Id</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getCode <em>Code</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getNaam <em>Naam</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getNaamMannelijk <em>Naam Mannelijk</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getNaamVrouwelijk <em>Naam Vrouwelijk</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getOmschrijving <em>Omschrijving</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getHeeftMaterieleHistorie <em>Heeft Materiele Historie</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getDatumAanvangGeldigheid <em>Datum Aanvang Geldigheid</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getDatumEindeGeldigheid <em>Datum Einde Geldigheid</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getCategorieSoortActie <em>Categorie Soort Actie</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl#getCategorieSoortDocument <em>Categorie Soort Document</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TupleImpl extends EObjectImpl implements Tuple
{
    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final Long ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected Long id = ID_EDEFAULT;

    /**
     * The default value of the '{@link #getRelatiefId() <em>Relatief Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRelatiefId()
     * @generated
     * @ordered
     */
    protected static final Integer RELATIEF_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getRelatiefId() <em>Relatief Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRelatiefId()
     * @generated
     * @ordered
     */
    protected Integer relatiefId = RELATIEF_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getCode() <em>Code</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCode()
     * @generated
     * @ordered
     */
    protected static final String CODE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCode() <em>Code</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCode()
     * @generated
     * @ordered
     */
    protected String code = CODE_EDEFAULT;

    /**
     * The default value of the '{@link #getNaam() <em>Naam</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNaam()
     * @generated
     * @ordered
     */
    protected static final String NAAM_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNaam() <em>Naam</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNaam()
     * @generated
     * @ordered
     */
    protected String naam = NAAM_EDEFAULT;

    /**
     * The default value of the '{@link #getNaamMannelijk() <em>Naam Mannelijk</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNaamMannelijk()
     * @generated
     * @ordered
     */
    protected static final String NAAM_MANNELIJK_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNaamMannelijk() <em>Naam Mannelijk</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNaamMannelijk()
     * @generated
     * @ordered
     */
    protected String naamMannelijk = NAAM_MANNELIJK_EDEFAULT;

    /**
     * The default value of the '{@link #getNaamVrouwelijk() <em>Naam Vrouwelijk</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNaamVrouwelijk()
     * @generated
     * @ordered
     */
    protected static final String NAAM_VROUWELIJK_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNaamVrouwelijk() <em>Naam Vrouwelijk</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNaamVrouwelijk()
     * @generated
     * @ordered
     */
    protected String naamVrouwelijk = NAAM_VROUWELIJK_EDEFAULT;

    /**
     * The default value of the '{@link #getOmschrijving() <em>Omschrijving</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOmschrijving()
     * @generated
     * @ordered
     */
    protected static final String OMSCHRIJVING_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOmschrijving() <em>Omschrijving</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOmschrijving()
     * @generated
     * @ordered
     */
    protected String omschrijving = OMSCHRIJVING_EDEFAULT;

    /**
     * The default value of the '{@link #getHeeftMaterieleHistorie() <em>Heeft Materiele Historie</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHeeftMaterieleHistorie()
     * @generated
     * @ordered
     */
    protected static final Boolean HEEFT_MATERIELE_HISTORIE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getHeeftMaterieleHistorie() <em>Heeft Materiele Historie</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHeeftMaterieleHistorie()
     * @generated
     * @ordered
     */
    protected Boolean heeftMaterieleHistorie = HEEFT_MATERIELE_HISTORIE_EDEFAULT;

    /**
     * The default value of the '{@link #getDatumAanvangGeldigheid() <em>Datum Aanvang Geldigheid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDatumAanvangGeldigheid()
     * @generated
     * @ordered
     */
    protected static final Integer DATUM_AANVANG_GELDIGHEID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDatumAanvangGeldigheid() <em>Datum Aanvang Geldigheid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDatumAanvangGeldigheid()
     * @generated
     * @ordered
     */
    protected Integer datumAanvangGeldigheid = DATUM_AANVANG_GELDIGHEID_EDEFAULT;

    /**
     * The default value of the '{@link #getDatumEindeGeldigheid() <em>Datum Einde Geldigheid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDatumEindeGeldigheid()
     * @generated
     * @ordered
     */
    protected static final Integer DATUM_EINDE_GELDIGHEID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDatumEindeGeldigheid() <em>Datum Einde Geldigheid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDatumEindeGeldigheid()
     * @generated
     * @ordered
     */
    protected Integer datumEindeGeldigheid = DATUM_EINDE_GELDIGHEID_EDEFAULT;

    /**
     * The default value of the '{@link #getCategorieSoortActie() <em>Categorie Soort Actie</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCategorieSoortActie()
     * @generated
     * @ordered
     */
    protected static final String CATEGORIE_SOORT_ACTIE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCategorieSoortActie() <em>Categorie Soort Actie</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCategorieSoortActie()
     * @generated
     * @ordered
     */
    protected String categorieSoortActie = CATEGORIE_SOORT_ACTIE_EDEFAULT;

    /**
     * The default value of the '{@link #getCategorieSoortDocument() <em>Categorie Soort Document</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCategorieSoortDocument()
     * @generated
     * @ordered
     */
    protected static final String CATEGORIE_SOORT_DOCUMENT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCategorieSoortDocument() <em>Categorie Soort Document</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCategorieSoortDocument()
     * @generated
     * @ordered
     */
    protected String categorieSoortDocument = CATEGORIE_SOORT_DOCUMENT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TupleImpl()
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
        return BmrPackage.Literals.TUPLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Long getId()
    {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(Long newId)
    {
        Long oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ObjectType getObjectType()
    {
        if (eContainerFeatureID() != BmrPackage.TUPLE__OBJECT_TYPE) return null;
        return (ObjectType)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetObjectType(ObjectType newObjectType, NotificationChain msgs)
    {
        msgs = eBasicSetContainer((InternalEObject)newObjectType, BmrPackage.TUPLE__OBJECT_TYPE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setObjectType(ObjectType newObjectType)
    {
        if (newObjectType != eInternalContainer() || (eContainerFeatureID() != BmrPackage.TUPLE__OBJECT_TYPE && newObjectType != null))
        {
            if (EcoreUtil.isAncestor(this, (EObject)newObjectType))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newObjectType != null)
                msgs = ((InternalEObject)newObjectType).eInverseAdd(this, BmrPackage.OBJECT_TYPE__TUPLES, ObjectType.class, msgs);
            msgs = basicSetObjectType(newObjectType, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__OBJECT_TYPE, newObjectType, newObjectType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Integer getRelatiefId()
    {
        return relatiefId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRelatiefId(Integer newRelatiefId)
    {
        Integer oldRelatiefId = relatiefId;
        relatiefId = newRelatiefId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__RELATIEF_ID, oldRelatiefId, relatiefId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCode()
    {
        return code;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCode(String newCode)
    {
        String oldCode = code;
        code = newCode;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__CODE, oldCode, code));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getNaam()
    {
        return naam;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNaam(String newNaam)
    {
        String oldNaam = naam;
        naam = newNaam;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__NAAM, oldNaam, naam));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getNaamMannelijk()
    {
        return naamMannelijk;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNaamMannelijk(String newNaamMannelijk)
    {
        String oldNaamMannelijk = naamMannelijk;
        naamMannelijk = newNaamMannelijk;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__NAAM_MANNELIJK, oldNaamMannelijk, naamMannelijk));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getNaamVrouwelijk()
    {
        return naamVrouwelijk;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNaamVrouwelijk(String newNaamVrouwelijk)
    {
        String oldNaamVrouwelijk = naamVrouwelijk;
        naamVrouwelijk = newNaamVrouwelijk;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__NAAM_VROUWELIJK, oldNaamVrouwelijk, naamVrouwelijk));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getOmschrijving()
    {
        return omschrijving;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOmschrijving(String newOmschrijving)
    {
        String oldOmschrijving = omschrijving;
        omschrijving = newOmschrijving;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__OMSCHRIJVING, oldOmschrijving, omschrijving));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Boolean getHeeftMaterieleHistorie()
    {
        return heeftMaterieleHistorie;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHeeftMaterieleHistorie(Boolean newHeeftMaterieleHistorie)
    {
        Boolean oldHeeftMaterieleHistorie = heeftMaterieleHistorie;
        heeftMaterieleHistorie = newHeeftMaterieleHistorie;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__HEEFT_MATERIELE_HISTORIE, oldHeeftMaterieleHistorie, heeftMaterieleHistorie));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Integer getDatumAanvangGeldigheid()
    {
        return datumAanvangGeldigheid;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDatumAanvangGeldigheid(Integer newDatumAanvangGeldigheid)
    {
        Integer oldDatumAanvangGeldigheid = datumAanvangGeldigheid;
        datumAanvangGeldigheid = newDatumAanvangGeldigheid;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__DATUM_AANVANG_GELDIGHEID, oldDatumAanvangGeldigheid, datumAanvangGeldigheid));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Integer getDatumEindeGeldigheid()
    {
        return datumEindeGeldigheid;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDatumEindeGeldigheid(Integer newDatumEindeGeldigheid)
    {
        Integer oldDatumEindeGeldigheid = datumEindeGeldigheid;
        datumEindeGeldigheid = newDatumEindeGeldigheid;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__DATUM_EINDE_GELDIGHEID, oldDatumEindeGeldigheid, datumEindeGeldigheid));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCategorieSoortActie()
    {
        return categorieSoortActie;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCategorieSoortActie(String newCategorieSoortActie)
    {
        String oldCategorieSoortActie = categorieSoortActie;
        categorieSoortActie = newCategorieSoortActie;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__CATEGORIE_SOORT_ACTIE, oldCategorieSoortActie, categorieSoortActie));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCategorieSoortDocument()
    {
        return categorieSoortDocument;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCategorieSoortDocument(String newCategorieSoortDocument)
    {
        String oldCategorieSoortDocument = categorieSoortDocument;
        categorieSoortDocument = newCategorieSoortDocument;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TUPLE__CATEGORIE_SOORT_DOCUMENT, oldCategorieSoortDocument, categorieSoortDocument));
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
            case BmrPackage.TUPLE__OBJECT_TYPE:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetObjectType((ObjectType)otherEnd, msgs);
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
            case BmrPackage.TUPLE__OBJECT_TYPE:
                return basicSetObjectType(null, msgs);
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
            case BmrPackage.TUPLE__OBJECT_TYPE:
                return eInternalContainer().eInverseRemove(this, BmrPackage.OBJECT_TYPE__TUPLES, ObjectType.class, msgs);
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
            case BmrPackage.TUPLE__ID:
                return getId();
            case BmrPackage.TUPLE__OBJECT_TYPE:
                return getObjectType();
            case BmrPackage.TUPLE__RELATIEF_ID:
                return getRelatiefId();
            case BmrPackage.TUPLE__CODE:
                return getCode();
            case BmrPackage.TUPLE__NAAM:
                return getNaam();
            case BmrPackage.TUPLE__NAAM_MANNELIJK:
                return getNaamMannelijk();
            case BmrPackage.TUPLE__NAAM_VROUWELIJK:
                return getNaamVrouwelijk();
            case BmrPackage.TUPLE__OMSCHRIJVING:
                return getOmschrijving();
            case BmrPackage.TUPLE__HEEFT_MATERIELE_HISTORIE:
                return getHeeftMaterieleHistorie();
            case BmrPackage.TUPLE__DATUM_AANVANG_GELDIGHEID:
                return getDatumAanvangGeldigheid();
            case BmrPackage.TUPLE__DATUM_EINDE_GELDIGHEID:
                return getDatumEindeGeldigheid();
            case BmrPackage.TUPLE__CATEGORIE_SOORT_ACTIE:
                return getCategorieSoortActie();
            case BmrPackage.TUPLE__CATEGORIE_SOORT_DOCUMENT:
                return getCategorieSoortDocument();
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
            case BmrPackage.TUPLE__ID:
                setId((Long)newValue);
                return;
            case BmrPackage.TUPLE__OBJECT_TYPE:
                setObjectType((ObjectType)newValue);
                return;
            case BmrPackage.TUPLE__RELATIEF_ID:
                setRelatiefId((Integer)newValue);
                return;
            case BmrPackage.TUPLE__CODE:
                setCode((String)newValue);
                return;
            case BmrPackage.TUPLE__NAAM:
                setNaam((String)newValue);
                return;
            case BmrPackage.TUPLE__NAAM_MANNELIJK:
                setNaamMannelijk((String)newValue);
                return;
            case BmrPackage.TUPLE__NAAM_VROUWELIJK:
                setNaamVrouwelijk((String)newValue);
                return;
            case BmrPackage.TUPLE__OMSCHRIJVING:
                setOmschrijving((String)newValue);
                return;
            case BmrPackage.TUPLE__HEEFT_MATERIELE_HISTORIE:
                setHeeftMaterieleHistorie((Boolean)newValue);
                return;
            case BmrPackage.TUPLE__DATUM_AANVANG_GELDIGHEID:
                setDatumAanvangGeldigheid((Integer)newValue);
                return;
            case BmrPackage.TUPLE__DATUM_EINDE_GELDIGHEID:
                setDatumEindeGeldigheid((Integer)newValue);
                return;
            case BmrPackage.TUPLE__CATEGORIE_SOORT_ACTIE:
                setCategorieSoortActie((String)newValue);
                return;
            case BmrPackage.TUPLE__CATEGORIE_SOORT_DOCUMENT:
                setCategorieSoortDocument((String)newValue);
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
            case BmrPackage.TUPLE__ID:
                setId(ID_EDEFAULT);
                return;
            case BmrPackage.TUPLE__OBJECT_TYPE:
                setObjectType((ObjectType)null);
                return;
            case BmrPackage.TUPLE__RELATIEF_ID:
                setRelatiefId(RELATIEF_ID_EDEFAULT);
                return;
            case BmrPackage.TUPLE__CODE:
                setCode(CODE_EDEFAULT);
                return;
            case BmrPackage.TUPLE__NAAM:
                setNaam(NAAM_EDEFAULT);
                return;
            case BmrPackage.TUPLE__NAAM_MANNELIJK:
                setNaamMannelijk(NAAM_MANNELIJK_EDEFAULT);
                return;
            case BmrPackage.TUPLE__NAAM_VROUWELIJK:
                setNaamVrouwelijk(NAAM_VROUWELIJK_EDEFAULT);
                return;
            case BmrPackage.TUPLE__OMSCHRIJVING:
                setOmschrijving(OMSCHRIJVING_EDEFAULT);
                return;
            case BmrPackage.TUPLE__HEEFT_MATERIELE_HISTORIE:
                setHeeftMaterieleHistorie(HEEFT_MATERIELE_HISTORIE_EDEFAULT);
                return;
            case BmrPackage.TUPLE__DATUM_AANVANG_GELDIGHEID:
                setDatumAanvangGeldigheid(DATUM_AANVANG_GELDIGHEID_EDEFAULT);
                return;
            case BmrPackage.TUPLE__DATUM_EINDE_GELDIGHEID:
                setDatumEindeGeldigheid(DATUM_EINDE_GELDIGHEID_EDEFAULT);
                return;
            case BmrPackage.TUPLE__CATEGORIE_SOORT_ACTIE:
                setCategorieSoortActie(CATEGORIE_SOORT_ACTIE_EDEFAULT);
                return;
            case BmrPackage.TUPLE__CATEGORIE_SOORT_DOCUMENT:
                setCategorieSoortDocument(CATEGORIE_SOORT_DOCUMENT_EDEFAULT);
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
            case BmrPackage.TUPLE__ID:
                return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case BmrPackage.TUPLE__OBJECT_TYPE:
                return getObjectType() != null;
            case BmrPackage.TUPLE__RELATIEF_ID:
                return RELATIEF_ID_EDEFAULT == null ? relatiefId != null : !RELATIEF_ID_EDEFAULT.equals(relatiefId);
            case BmrPackage.TUPLE__CODE:
                return CODE_EDEFAULT == null ? code != null : !CODE_EDEFAULT.equals(code);
            case BmrPackage.TUPLE__NAAM:
                return NAAM_EDEFAULT == null ? naam != null : !NAAM_EDEFAULT.equals(naam);
            case BmrPackage.TUPLE__NAAM_MANNELIJK:
                return NAAM_MANNELIJK_EDEFAULT == null ? naamMannelijk != null : !NAAM_MANNELIJK_EDEFAULT.equals(naamMannelijk);
            case BmrPackage.TUPLE__NAAM_VROUWELIJK:
                return NAAM_VROUWELIJK_EDEFAULT == null ? naamVrouwelijk != null : !NAAM_VROUWELIJK_EDEFAULT.equals(naamVrouwelijk);
            case BmrPackage.TUPLE__OMSCHRIJVING:
                return OMSCHRIJVING_EDEFAULT == null ? omschrijving != null : !OMSCHRIJVING_EDEFAULT.equals(omschrijving);
            case BmrPackage.TUPLE__HEEFT_MATERIELE_HISTORIE:
                return HEEFT_MATERIELE_HISTORIE_EDEFAULT == null ? heeftMaterieleHistorie != null : !HEEFT_MATERIELE_HISTORIE_EDEFAULT.equals(heeftMaterieleHistorie);
            case BmrPackage.TUPLE__DATUM_AANVANG_GELDIGHEID:
                return DATUM_AANVANG_GELDIGHEID_EDEFAULT == null ? datumAanvangGeldigheid != null : !DATUM_AANVANG_GELDIGHEID_EDEFAULT.equals(datumAanvangGeldigheid);
            case BmrPackage.TUPLE__DATUM_EINDE_GELDIGHEID:
                return DATUM_EINDE_GELDIGHEID_EDEFAULT == null ? datumEindeGeldigheid != null : !DATUM_EINDE_GELDIGHEID_EDEFAULT.equals(datumEindeGeldigheid);
            case BmrPackage.TUPLE__CATEGORIE_SOORT_ACTIE:
                return CATEGORIE_SOORT_ACTIE_EDEFAULT == null ? categorieSoortActie != null : !CATEGORIE_SOORT_ACTIE_EDEFAULT.equals(categorieSoortActie);
            case BmrPackage.TUPLE__CATEGORIE_SOORT_DOCUMENT:
                return CATEGORIE_SOORT_DOCUMENT_EDEFAULT == null ? categorieSoortDocument != null : !CATEGORIE_SOORT_DOCUMENT_EDEFAULT.equals(categorieSoortDocument);
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
        result.append(" (id: ");
        result.append(id);
        result.append(", relatiefId: ");
        result.append(relatiefId);
        result.append(", code: ");
        result.append(code);
        result.append(", naam: ");
        result.append(naam);
        result.append(", naamMannelijk: ");
        result.append(naamMannelijk);
        result.append(", naamVrouwelijk: ");
        result.append(naamVrouwelijk);
        result.append(", omschrijving: ");
        result.append(omschrijving);
        result.append(", heeftMaterieleHistorie: ");
        result.append(heeftMaterieleHistorie);
        result.append(", datumAanvangGeldigheid: ");
        result.append(datumAanvangGeldigheid);
        result.append(", datumEindeGeldigheid: ");
        result.append(datumEindeGeldigheid);
        result.append(", categorieSoortActie: ");
        result.append(categorieSoortActie);
        result.append(", categorieSoortDocument: ");
        result.append(categorieSoortDocument);
        result.append(')');
        return result.toString();
    }

} //TupleImpl
