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

import org.eclipse.emf.ecore.EObject;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.ModelElement#getId <em>Id</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.ModelElement#getNaam <em>Naam</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getModelElement()
 * @model abstract="true"
 * @generated
 */
public interface ModelElement {

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(Integer)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getModelElement_Id()
     * @model
     * @generated
     */
    Integer getId();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.ModelElement#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(Integer value);

    /**
     * Returns the value of the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Naam</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Naam</em>' attribute.
     * @see #setNaam(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getModelElement_Naam()
     * @model
     * @generated
     */
    String getNaam();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.ModelElement#getNaam <em>Naam</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Naam</em>' attribute.
     * @see #getNaam()
     * @generated
     */
    void setNaam(String value);

    /**
     * De naam van dit element, voorafgegaan door alle omvattende namespaces.
     *
     * @return De naam van dit element, voorafgegaan door alle omvattende namespaces.
     */
    String getGekwalificeerdeNaam();

    public abstract String getOuderNaam();

} // ModelElement
