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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Groep</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Groep#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Groep#getInSetOfModel <em>In Set Of Model</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Groep#getAttributen <em>Attributen</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getGroep()
 * @model
 * @generated
 */
public interface Groep extends GelaagdElement
{
    /**
     * Returns the value of the '<em><b>Object Type</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.ObjectType#getGroepen <em>Groepen</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object Type</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object Type</em>' container reference.
     * @see #setObjectType(ObjectType)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getGroep_ObjectType()
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getGroepen
     * @model opposite="groepen"
     * @generated
     */
    ObjectType getObjectType();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Groep#getObjectType <em>Object Type</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Object Type</em>' container reference.
     * @see #getObjectType()
     * @generated
     */
    void setObjectType(ObjectType value);

    /**
     * Returns the value of the '<em><b>In Set Of Model</b></em>' attribute.
     * The literals are from the enumeration {@link nl.bzk.brp.ecore.bmr.InSetOfModel}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>In Set Of Model</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>In Set Of Model</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.InSetOfModel
     * @see #setInSetOfModel(InSetOfModel)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getGroep_InSetOfModel()
     * @model
     * @generated
     */
    InSetOfModel getInSetOfModel();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Groep#getInSetOfModel <em>In Set Of Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>In Set Of Model</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.InSetOfModel
     * @see #getInSetOfModel()
     * @generated
     */
    void setInSetOfModel(InSetOfModel value);

    /**
     * Returns the value of the '<em><b>Attributen</b></em>' reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Attribuut}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Attribuut#getGroep <em>Groep</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attributen</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attributen</em>' reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getGroep_Attributen()
     * @see nl.bzk.brp.ecore.bmr.Attribuut#getGroep
     * @model opposite="groep"
     * @generated
     */
    EList<Attribuut> getAttributen();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    Boolean isHistorieVastleggen();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    Boolean isVerplicht();

} // Groep
