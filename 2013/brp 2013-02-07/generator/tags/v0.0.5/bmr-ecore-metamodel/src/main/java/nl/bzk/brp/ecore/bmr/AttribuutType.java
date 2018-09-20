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
 * A representation of the model object '<em><b>Attribuut Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.AttribuutType#getType <em>Type</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.AttribuutType#getVersie <em>Versie</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.AttribuutType#getMinimumLengte <em>Minimum Lengte</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.AttribuutType#getMaximumLengte <em>Maximum Lengte</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.AttribuutType#getAantalDecimalen <em>Aantal Decimalen</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuutType()
 * @model
 * @generated
 */
public interface AttribuutType extends Type
{
    /**
     * Returns the value of the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' reference.
     * @see #setType(BasisType)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuutType_Type()
     * @model
     * @generated
     */
    BasisType getType();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.AttribuutType#getType <em>Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' reference.
     * @see #getType()
     * @generated
     */
    void setType(BasisType value);

    /**
     * Returns the value of the '<em><b>Versie</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Versie#getAttribuutTypes <em>Attribuut Types</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Versie</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Versie</em>' container reference.
     * @see #setVersie(Versie)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuutType_Versie()
     * @see nl.bzk.brp.ecore.bmr.Versie#getAttribuutTypes
     * @model opposite="attribuutTypes"
     * @generated
     */
    Versie getVersie();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.AttribuutType#getVersie <em>Versie</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Versie</em>' container reference.
     * @see #getVersie()
     * @generated
     */
    void setVersie(Versie value);

    /**
     * Returns the value of the '<em><b>Minimum Lengte</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Minimum Lengte</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Minimum Lengte</em>' attribute.
     * @see #setMinimumLengte(Integer)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuutType_MinimumLengte()
     * @model
     * @generated
     */
    Integer getMinimumLengte();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.AttribuutType#getMinimumLengte <em>Minimum Lengte</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Minimum Lengte</em>' attribute.
     * @see #getMinimumLengte()
     * @generated
     */
    void setMinimumLengte(Integer value);

    /**
     * Returns the value of the '<em><b>Maximum Lengte</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Maximum Lengte</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Maximum Lengte</em>' attribute.
     * @see #setMaximumLengte(Integer)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuutType_MaximumLengte()
     * @model
     * @generated
     */
    Integer getMaximumLengte();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.AttribuutType#getMaximumLengte <em>Maximum Lengte</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Maximum Lengte</em>' attribute.
     * @see #getMaximumLengte()
     * @generated
     */
    void setMaximumLengte(Integer value);

    /**
     * Returns the value of the '<em><b>Aantal Decimalen</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Aantal Decimalen</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Aantal Decimalen</em>' attribute.
     * @see #setAantalDecimalen(Integer)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuutType_AantalDecimalen()
     * @model
     * @generated
     */
    Integer getAantalDecimalen();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.AttribuutType#getAantalDecimalen <em>Aantal Decimalen</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Aantal Decimalen</em>' attribute.
     * @see #getAantalDecimalen()
     * @generated
     */
    void setAantalDecimalen(Integer value);

} // AttribuutType
