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
 * A representation of the model object '<em><b>Bericht Sjabloon</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.BerichtSjabloon#getVersie <em>Versie</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBerichtSjabloon()
 * @model
 * @generated
 */
public interface BerichtSjabloon extends GelaagdElement
{
    /**
     * Returns the value of the '<em><b>Versie</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Versie#getBerichtSjablonen <em>Bericht Sjablonen</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Versie</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Versie</em>' container reference.
     * @see #setVersie(Versie)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBerichtSjabloon_Versie()
     * @see nl.bzk.brp.ecore.bmr.Versie#getBerichtSjablonen
     * @model opposite="berichtSjablonen"
     * @generated
     */
    Versie getVersie();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.BerichtSjabloon#getVersie <em>Versie</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Versie</em>' container reference.
     * @see #getVersie()
     * @generated
     */
    void setVersie(Versie value);

} // BerichtSjabloon
