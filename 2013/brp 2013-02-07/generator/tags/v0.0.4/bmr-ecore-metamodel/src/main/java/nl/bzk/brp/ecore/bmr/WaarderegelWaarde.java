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
 * A representation of the model object '<em><b>Waarderegel Waarde</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getBedrijfsRegel <em>Bedrijfs Regel</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getWaarde <em>Waarde</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getWeergave <em>Weergave</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getWaarderegelWaarde()
 * @model
 * @generated
 */
public interface WaarderegelWaarde extends GelaagdElement
{
    /**
     * Returns the value of the '<em><b>Bedrijfs Regel</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getWaarden <em>Waarden</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Bedrijfs Regel</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Bedrijfs Regel</em>' container reference.
     * @see #setBedrijfsRegel(BedrijfsRegel)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getWaarderegelWaarde_BedrijfsRegel()
     * @see nl.bzk.brp.ecore.bmr.BedrijfsRegel#getWaarden
     * @model opposite="waarden" transient="false"
     * @generated
     */
    BedrijfsRegel getBedrijfsRegel();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getBedrijfsRegel <em>Bedrijfs Regel</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Bedrijfs Regel</em>' container reference.
     * @see #getBedrijfsRegel()
     * @generated
     */
    void setBedrijfsRegel(BedrijfsRegel value);

    /**
     * Returns the value of the '<em><b>Waarde</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Waarde</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Waarde</em>' attribute.
     * @see #setWaarde(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getWaarderegelWaarde_Waarde()
     * @model
     * @generated
     */
    String getWaarde();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getWaarde <em>Waarde</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Waarde</em>' attribute.
     * @see #getWaarde()
     * @generated
     */
    void setWaarde(String value);

    /**
     * Returns the value of the '<em><b>Weergave</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Weergave</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Weergave</em>' attribute.
     * @see #setWeergave(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getWaarderegelWaarde_Weergave()
     * @model
     * @generated
     */
    String getWeergave();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getWeergave <em>Weergave</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Weergave</em>' attribute.
     * @see #getWeergave()
     * @generated
     */
    void setWeergave(String value);

} // WaarderegelWaarde
