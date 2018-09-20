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
 * A representation of the model object '<em><b>Formulier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Formulier#getApplicatie <em>Applicatie</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Formulier#getFrames <em>Frames</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Formulier#getBronnen <em>Bronnen</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFormulier()
 * @model
 * @generated
 */
public interface Formulier extends ModelElement {

    /**
     * Returns the value of the '<em><b>Applicatie</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Applicatie#getFormulieren <em>Formulieren</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Applicatie</em>' container reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Applicatie</em>' container reference.
     * @see #setApplicatie(Applicatie)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFormulier_Applicatie()
     * @see nl.bzk.brp.ecore.bmr.Applicatie#getFormulieren
     * @model opposite="formulieren" transient="false" derived="true"
     * @generated
     */
    Applicatie getApplicatie();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Formulier#getApplicatie <em>Applicatie</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Applicatie</em>' container reference.
     * @see #getApplicatie()
     * @generated
     */
    void setApplicatie(Applicatie value);

    /**
     * Returns the value of the '<em><b>Frames</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Frame}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Frame#getFormulier <em>Formulier</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Frames</em>' containment reference list isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Frames</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFormulier_Frames()
     * @see nl.bzk.brp.ecore.bmr.Frame#getFormulier
     * @model opposite="formulier" containment="true"
     * @generated
     */
    EList<Frame> getFrames();

    /**
     * Returns the value of the '<em><b>Bronnen</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Bron}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Bron#getFormulier <em>Formulier</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Bronnen</em>' containment reference list isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Bronnen</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFormulier_Bronnen()
     * @see nl.bzk.brp.ecore.bmr.Bron#getFormulier
     * @model opposite="formulier" containment="true"
     * @generated
     */
    EList<Bron> getBronnen();

    Bron getBron(Integer id);

} // Formulier
