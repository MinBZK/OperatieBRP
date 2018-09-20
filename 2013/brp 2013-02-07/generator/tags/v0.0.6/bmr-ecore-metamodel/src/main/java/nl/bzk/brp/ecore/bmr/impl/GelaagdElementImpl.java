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
import nl.bzk.brp.ecore.bmr.GelaagdElement;
import nl.bzk.brp.ecore.bmr.InSetOfModel;
import nl.bzk.brp.ecore.bmr.VersieTag;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gelaagd Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.GelaagdElementImpl#getIdentifierDB <em>Identifier DB</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.GelaagdElementImpl#getIdentifierCode <em>Identifier Code</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.GelaagdElementImpl#getVersieTag <em>Versie Tag</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.GelaagdElementImpl#getVolgnummer <em>Volgnummer</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.GelaagdElementImpl#getInSetOfModel <em>In Set Of Model</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class GelaagdElementImpl extends ElementImpl implements GelaagdElement
{
    /**
     * The default value of the '{@link #getIdentifierDB() <em>Identifier DB</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifierDB()
     * @generated
     * @ordered
     */
    protected static final String IDENTIFIER_DB_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIdentifierDB() <em>Identifier DB</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifierDB()
     * @generated
     * @ordered
     */
    protected String identifierDB = IDENTIFIER_DB_EDEFAULT;

    /**
     * The default value of the '{@link #getIdentifierCode() <em>Identifier Code</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifierCode()
     * @generated
     * @ordered
     */
    protected static final String IDENTIFIER_CODE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIdentifierCode() <em>Identifier Code</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifierCode()
     * @generated
     * @ordered
     */
    protected String identifierCode = IDENTIFIER_CODE_EDEFAULT;

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
    protected VersieTag versieTag = VERSIE_TAG_EDEFAULT;

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
     * The default value of the '{@link #getInSetOfModel() <em>In Set Of Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInSetOfModel()
     * @generated
     * @ordered
     */
    protected static final InSetOfModel IN_SET_OF_MODEL_EDEFAULT = InSetOfModel.SET;

    /**
     * The cached value of the '{@link #getInSetOfModel() <em>In Set Of Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInSetOfModel()
     * @generated
     * @ordered
     */
    protected InSetOfModel inSetOfModel = IN_SET_OF_MODEL_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GelaagdElementImpl()
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
        return BmrPackage.Literals.GELAAGD_ELEMENT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getIdentifierDB()
    {
        return identifierDB;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIdentifierDB(String newIdentifierDB)
    {
        String oldIdentifierDB = identifierDB;
        identifierDB = newIdentifierDB;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.GELAAGD_ELEMENT__IDENTIFIER_DB, oldIdentifierDB, identifierDB));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getIdentifierCode()
    {
        return identifierCode;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIdentifierCode(String newIdentifierCode)
    {
        String oldIdentifierCode = identifierCode;
        identifierCode = newIdentifierCode;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.GELAAGD_ELEMENT__IDENTIFIER_CODE, oldIdentifierCode, identifierCode));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public VersieTag getVersieTag()
    {
        return versieTag;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVersieTag(VersieTag newVersieTag)
    {
        VersieTag oldVersieTag = versieTag;
        versieTag = newVersieTag == null ? VERSIE_TAG_EDEFAULT : newVersieTag;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.GELAAGD_ELEMENT__VERSIE_TAG, oldVersieTag, versieTag));
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
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.GELAAGD_ELEMENT__VOLGNUMMER, oldVolgnummer, volgnummer));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public InSetOfModel getInSetOfModel()
    {
        return inSetOfModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInSetOfModel(InSetOfModel newInSetOfModel)
    {
        InSetOfModel oldInSetOfModel = inSetOfModel;
        inSetOfModel = newInSetOfModel == null ? IN_SET_OF_MODEL_EDEFAULT : newInSetOfModel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.GELAAGD_ELEMENT__IN_SET_OF_MODEL, oldInSetOfModel, inSetOfModel));
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
            case BmrPackage.GELAAGD_ELEMENT__IDENTIFIER_DB:
                return getIdentifierDB();
            case BmrPackage.GELAAGD_ELEMENT__IDENTIFIER_CODE:
                return getIdentifierCode();
            case BmrPackage.GELAAGD_ELEMENT__VERSIE_TAG:
                return getVersieTag();
            case BmrPackage.GELAAGD_ELEMENT__VOLGNUMMER:
                return getVolgnummer();
            case BmrPackage.GELAAGD_ELEMENT__IN_SET_OF_MODEL:
                return getInSetOfModel();
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
            case BmrPackage.GELAAGD_ELEMENT__IDENTIFIER_DB:
                setIdentifierDB((String)newValue);
                return;
            case BmrPackage.GELAAGD_ELEMENT__IDENTIFIER_CODE:
                setIdentifierCode((String)newValue);
                return;
            case BmrPackage.GELAAGD_ELEMENT__VERSIE_TAG:
                setVersieTag((VersieTag)newValue);
                return;
            case BmrPackage.GELAAGD_ELEMENT__VOLGNUMMER:
                setVolgnummer((Integer)newValue);
                return;
            case BmrPackage.GELAAGD_ELEMENT__IN_SET_OF_MODEL:
                setInSetOfModel((InSetOfModel)newValue);
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
            case BmrPackage.GELAAGD_ELEMENT__IDENTIFIER_DB:
                setIdentifierDB(IDENTIFIER_DB_EDEFAULT);
                return;
            case BmrPackage.GELAAGD_ELEMENT__IDENTIFIER_CODE:
                setIdentifierCode(IDENTIFIER_CODE_EDEFAULT);
                return;
            case BmrPackage.GELAAGD_ELEMENT__VERSIE_TAG:
                setVersieTag(VERSIE_TAG_EDEFAULT);
                return;
            case BmrPackage.GELAAGD_ELEMENT__VOLGNUMMER:
                setVolgnummer(VOLGNUMMER_EDEFAULT);
                return;
            case BmrPackage.GELAAGD_ELEMENT__IN_SET_OF_MODEL:
                setInSetOfModel(IN_SET_OF_MODEL_EDEFAULT);
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
            case BmrPackage.GELAAGD_ELEMENT__IDENTIFIER_DB:
                return IDENTIFIER_DB_EDEFAULT == null ? identifierDB != null : !IDENTIFIER_DB_EDEFAULT.equals(identifierDB);
            case BmrPackage.GELAAGD_ELEMENT__IDENTIFIER_CODE:
                return IDENTIFIER_CODE_EDEFAULT == null ? identifierCode != null : !IDENTIFIER_CODE_EDEFAULT.equals(identifierCode);
            case BmrPackage.GELAAGD_ELEMENT__VERSIE_TAG:
                return versieTag != VERSIE_TAG_EDEFAULT;
            case BmrPackage.GELAAGD_ELEMENT__VOLGNUMMER:
                return VOLGNUMMER_EDEFAULT == null ? volgnummer != null : !VOLGNUMMER_EDEFAULT.equals(volgnummer);
            case BmrPackage.GELAAGD_ELEMENT__IN_SET_OF_MODEL:
                return inSetOfModel != IN_SET_OF_MODEL_EDEFAULT;
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
        result.append(" (identifierDB: ");
        result.append(identifierDB);
        result.append(", identifierCode: ");
        result.append(identifierCode);
        result.append(", versieTag: ");
        result.append(versieTag);
        result.append(", volgnummer: ");
        result.append(volgnummer);
        result.append(", inSetOfModel: ");
        result.append(inSetOfModel);
        result.append(')');
        return result.toString();
    }

} //GelaagdElementImpl
