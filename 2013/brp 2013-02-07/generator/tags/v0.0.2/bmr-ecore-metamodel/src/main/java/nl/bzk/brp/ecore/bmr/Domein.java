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
 * A representation of the model object '<em><b>Domein</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Domein#getSchemas <em>Schemas</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getDomein()
 * @model
 * @generated
 */
public interface Domein extends Element
{
    /**
     * Returns the value of the '<em><b>Schemas</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Schema}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Schema#getDomein <em>Domein</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Schemas</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Schemas</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getDomein_Schemas()
     * @see nl.bzk.brp.ecore.bmr.Schema#getDomein
     * @model opposite="domein" containment="true"
     * @generated
     */
    EList<Schema> getSchemas();

} // Domein
