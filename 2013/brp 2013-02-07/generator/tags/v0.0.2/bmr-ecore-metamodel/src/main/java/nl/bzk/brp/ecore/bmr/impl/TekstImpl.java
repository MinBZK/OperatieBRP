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
import nl.bzk.brp.ecore.bmr.Element;
import nl.bzk.brp.ecore.bmr.SoortTekst;
import nl.bzk.brp.ecore.bmr.Tekst;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tekst</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TekstImpl#getId <em>Id</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TekstImpl#getElement <em>Element</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TekstImpl#getSoort <em>Soort</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TekstImpl#getTekst <em>Tekst</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.TekstImpl#getHtmlTekst <em>Html Tekst</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TekstImpl extends EObjectImpl implements Tekst
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
     * The cached value of the '{@link #getElement() <em>Element</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getElement()
     * @generated
     * @ordered
     */
    protected Element element;

    /**
     * The default value of the '{@link #getSoort() <em>Soort</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSoort()
     * @generated
     * @ordered
     */
    protected static final SoortTekst SOORT_EDEFAULT = SoortTekst.DEF;

    /**
     * The cached value of the '{@link #getSoort() <em>Soort</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSoort()
     * @generated
     * @ordered
     */
    protected SoortTekst soort = SOORT_EDEFAULT;

    /**
     * The default value of the '{@link #getTekst() <em>Tekst</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTekst()
     * @generated
     * @ordered
     */
    protected static final String TEKST_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTekst() <em>Tekst</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTekst()
     * @generated
     * @ordered
     */
    protected String tekst = TEKST_EDEFAULT;

    /**
     * The default value of the '{@link #getHtmlTekst() <em>Html Tekst</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHtmlTekst()
     * @generated
     * @ordered
     */
    protected static final String HTML_TEKST_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getHtmlTekst() <em>Html Tekst</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHtmlTekst()
     * @generated
     * @ordered
     */
    protected String htmlTekst = HTML_TEKST_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TekstImpl()
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
        return BmrPackage.Literals.TEKST;
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
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TEKST__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Element getElement()
    {
        if (element != null && element.eIsProxy())
        {
            InternalEObject oldElement = (InternalEObject)element;
            element = (Element)eResolveProxy(oldElement);
            if (element != oldElement)
            {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BmrPackage.TEKST__ELEMENT, oldElement, element));
            }
        }
        return element;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Element basicGetElement()
    {
        return element;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setElement(Element newElement)
    {
        Element oldElement = element;
        element = newElement;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TEKST__ELEMENT, oldElement, element));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SoortTekst getSoort()
    {
        return soort;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSoort(SoortTekst newSoort)
    {
        SoortTekst oldSoort = soort;
        soort = newSoort == null ? SOORT_EDEFAULT : newSoort;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TEKST__SOORT, oldSoort, soort));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTekst()
    {
        return tekst;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTekst(String newTekst)
    {
        String oldTekst = tekst;
        tekst = newTekst;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TEKST__TEKST, oldTekst, tekst));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getHtmlTekst()
    {
        return htmlTekst;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHtmlTekst(String newHtmlTekst)
    {
        String oldHtmlTekst = htmlTekst;
        htmlTekst = newHtmlTekst;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BmrPackage.TEKST__HTML_TEKST, oldHtmlTekst, htmlTekst));
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
            case BmrPackage.TEKST__ID:
                return getId();
            case BmrPackage.TEKST__ELEMENT:
                if (resolve) return getElement();
                return basicGetElement();
            case BmrPackage.TEKST__SOORT:
                return getSoort();
            case BmrPackage.TEKST__TEKST:
                return getTekst();
            case BmrPackage.TEKST__HTML_TEKST:
                return getHtmlTekst();
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
            case BmrPackage.TEKST__ID:
                setId((Long)newValue);
                return;
            case BmrPackage.TEKST__ELEMENT:
                setElement((Element)newValue);
                return;
            case BmrPackage.TEKST__SOORT:
                setSoort((SoortTekst)newValue);
                return;
            case BmrPackage.TEKST__TEKST:
                setTekst((String)newValue);
                return;
            case BmrPackage.TEKST__HTML_TEKST:
                setHtmlTekst((String)newValue);
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
            case BmrPackage.TEKST__ID:
                setId(ID_EDEFAULT);
                return;
            case BmrPackage.TEKST__ELEMENT:
                setElement((Element)null);
                return;
            case BmrPackage.TEKST__SOORT:
                setSoort(SOORT_EDEFAULT);
                return;
            case BmrPackage.TEKST__TEKST:
                setTekst(TEKST_EDEFAULT);
                return;
            case BmrPackage.TEKST__HTML_TEKST:
                setHtmlTekst(HTML_TEKST_EDEFAULT);
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
            case BmrPackage.TEKST__ID:
                return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case BmrPackage.TEKST__ELEMENT:
                return element != null;
            case BmrPackage.TEKST__SOORT:
                return soort != SOORT_EDEFAULT;
            case BmrPackage.TEKST__TEKST:
                return TEKST_EDEFAULT == null ? tekst != null : !TEKST_EDEFAULT.equals(tekst);
            case BmrPackage.TEKST__HTML_TEKST:
                return HTML_TEKST_EDEFAULT == null ? htmlTekst != null : !HTML_TEKST_EDEFAULT.equals(htmlTekst);
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
        result.append(", soort: ");
        result.append(soort);
        result.append(", tekst: ");
        result.append(tekst);
        result.append(", htmlTekst: ");
        result.append(htmlTekst);
        result.append(')');
        return result.toString();
    }

} //TekstImpl
