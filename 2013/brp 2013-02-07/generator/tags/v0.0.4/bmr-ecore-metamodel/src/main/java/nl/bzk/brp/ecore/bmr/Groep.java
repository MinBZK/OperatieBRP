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
 *   <li>{@link nl.bzk.brp.ecore.bmr.Groep#getAttributen <em>Attributen</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Groep#getHistorieVastleggen <em>Historie Vastleggen</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Groep#isVerplicht <em>Verplicht</em>}</li>
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
     * Returns the value of the '<em><b>Historie Vastleggen</b></em>' attribute.
     * The literals are from the enumeration {@link nl.bzk.brp.ecore.bmr.Historie}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Historie Vastleggen</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Historie Vastleggen</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.Historie
     * @see #setHistorieVastleggen(Historie)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getGroep_HistorieVastleggen()
     * @model
     * @generated
     */
    Historie getHistorieVastleggen();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Groep#getHistorieVastleggen <em>Historie Vastleggen</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Historie Vastleggen</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.Historie
     * @see #getHistorieVastleggen()
     * @generated
     */
    void setHistorieVastleggen(Historie value);

    /**
     * Returns the value of the '<em><b>Verplicht</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Verplicht</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Verplicht</em>' attribute.
     * @see #setVerplicht(boolean)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getGroep_Verplicht()
     * @model
     * @generated
     */
    boolean isVerplicht();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Groep#isVerplicht <em>Verplicht</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Verplicht</em>' attribute.
     * @see #isVerplicht()
     * @generated
     */
    void setVerplicht(boolean value);

} // Groep
