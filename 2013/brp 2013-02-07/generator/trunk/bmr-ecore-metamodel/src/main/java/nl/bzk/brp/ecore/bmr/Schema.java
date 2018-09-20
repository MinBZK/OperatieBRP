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
 * A representation of the model object '<em><b>Schema</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Schema#getVersies <em>Versies</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Schema#getDomein <em>Domein</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getSchema()
 * @model
 * @generated
 */
public interface Schema extends Element {

    /**
     * Returns the value of the '<em><b>Versies</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Versie}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Versie#getSchema <em>Schema</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Versies</em>' containment reference list isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Versies</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getSchema_Versies()
     * @see nl.bzk.brp.ecore.bmr.Versie#getSchema
     * @model opposite="schema" containment="true"
     * @generated
     */
    List<Versie> getVersies();

    /**
     * Returns the value of the '<em><b>Domein</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Domein#getSchemas <em>Schemas</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Domein</em>' container reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Domein</em>' container reference.
     * @see #setDomein(Domein)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getSchema_Domein()
     * @see nl.bzk.brp.ecore.bmr.Domein#getSchemas
     * @model opposite="schemas"
     * @generated
     */
    Domein getDomein();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Schema#getDomein <em>Domein</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Domein</em>' container reference.
     * @see #getDomein()
     * @generated
     */
    void setDomein(Domein value);

    /**
     * Haal de 'werk' versie van dit schema op. Dat is de versie met versie-tag {@link VersieTag#W}.
     *
     * @return De 'werk' versie van dit schema.
     */
    Versie getWerkVersie();

} // Schema
