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

import java.util.List;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Applicatie</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Applicatie#getFormulieren <em>Formulieren</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getApplicatie()
 * @model
 * @generated
 */
public interface Applicatie extends ModelElement
{
    /**
     * Returns the value of the '<em><b>Formulieren</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Formulier}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Formulier#getApplicatie <em>Applicatie</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Formulieren</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Formulieren</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getApplicatie_Formulieren()
     * @see nl.bzk.brp.ecore.bmr.Formulier#getApplicatie
     * @model opposite="applicatie" containment="true"
     * @generated
     */
    List<Formulier> getFormulieren();

} // Applicatie
