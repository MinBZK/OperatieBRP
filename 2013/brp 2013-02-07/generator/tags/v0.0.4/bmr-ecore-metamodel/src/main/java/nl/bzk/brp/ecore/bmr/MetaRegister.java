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
import org.eclipse.emf.ecore.EObject;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Meta Register</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.MetaRegister#getDomeinen <em>Domeinen</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.MetaRegister#getBasisTypen <em>Basis Typen</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.MetaRegister#getApplicaties <em>Applicaties</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getMetaRegister()
 * @model
 * @generated
 */
public interface MetaRegister extends EObject {

    /**
     * Returns the value of the '<em><b>Domeinen</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Domein}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Domeinen</em>' containment reference list isn't clear, there really should be more of
     * a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Domeinen</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getMetaRegister_Domeinen()
     * @model containment="true"
     * @generated
     */
    EList<Domein> getDomeinen();

    /**
     * Returns the value of the '<em><b>Basis Typen</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.BasisType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Basis Typen</em>' containment reference list isn't clear, there really should be more
     * of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Basis Typen</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getMetaRegister_BasisTypen()
     * @model containment="true"
     * @generated
     */
    EList<BasisType> getBasisTypen();

    /**
     * Returns the value of the '<em><b>Applicaties</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Applicatie}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Applicaties</em>' containment reference list isn't clear, there really should be more
     * of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Applicaties</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getMetaRegister_Applicaties()
     * @model containment="true"
     * @generated
     */
    EList<Applicatie> getApplicaties();

    Domein getDomein(final String naam);

    Applicatie getApplicatie(final String naam);

} // MetaRegister
