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
package nl.bzk.brp.ecore.bmr;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gelaagd Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getIdentifierDB <em>Identifier DB</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getIdentifierCode <em>Identifier Code</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getVersieTag <em>Versie Tag</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getVolgnummer <em>Volgnummer</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getGelaagdElement()
 * @model abstract="true"
 * @generated
 */
public interface GelaagdElement extends Element
{
    /**
     * Returns the value of the '<em><b>Identifier DB</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Identifier DB</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Identifier DB</em>' attribute.
     * @see #setIdentifierDB(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getGelaagdElement_IdentifierDB()
     * @model
     * @generated
     */
    String getIdentifierDB();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getIdentifierDB <em>Identifier DB</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Identifier DB</em>' attribute.
     * @see #getIdentifierDB()
     * @generated
     */
    void setIdentifierDB(String value);

    /**
     * Returns the value of the '<em><b>Identifier Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Identifier Code</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Identifier Code</em>' attribute.
     * @see #setIdentifierCode(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getGelaagdElement_IdentifierCode()
     * @model
     * @generated
     */
    String getIdentifierCode();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getIdentifierCode <em>Identifier Code</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Identifier Code</em>' attribute.
     * @see #getIdentifierCode()
     * @generated
     */
    void setIdentifierCode(String value);

    /**
     * Returns the value of the '<em><b>Versie Tag</b></em>' attribute.
     * The literals are from the enumeration {@link nl.bzk.brp.ecore.bmr.VersieTag}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Versie Tag</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Versie Tag</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.VersieTag
     * @see #setVersieTag(VersieTag)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getGelaagdElement_VersieTag()
     * @model
     * @generated
     */
    VersieTag getVersieTag();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getVersieTag <em>Versie Tag</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Versie Tag</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.VersieTag
     * @see #getVersieTag()
     * @generated
     */
    void setVersieTag(VersieTag value);

    /**
     * Returns the value of the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Volgnummer</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Volgnummer</em>' attribute.
     * @see #setVolgnummer(Integer)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getGelaagdElement_Volgnummer()
     * @model
     * @generated
     */
    Integer getVolgnummer();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getVolgnummer <em>Volgnummer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Volgnummer</em>' attribute.
     * @see #getVolgnummer()
     * @generated
     */
    void setVolgnummer(Integer value);

} // GelaagdElement
