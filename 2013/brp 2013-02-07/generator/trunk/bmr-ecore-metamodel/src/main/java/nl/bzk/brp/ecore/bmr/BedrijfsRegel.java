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
 * A representation of the model object '<em><b>Bedrijfs Regel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getAttributen <em>Attributen</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getElement <em>Element</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getSoortBedrijfsRegel <em>Soort Bedrijfs Regel</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getWaarden <em>Waarden</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBedrijfsRegel()
 * @model
 * @generated
 */
public interface BedrijfsRegel extends GelaagdElement
{
    /**
     * Returns the value of the '<em><b>Attributen</b></em>' reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Attribuut}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Attribuut#getGebruiktInBedrijfsRegels <em>Gebruikt In Bedrijfs Regels</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attributen</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attributen</em>' reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBedrijfsRegel_Attributen()
     * @see nl.bzk.brp.ecore.bmr.Attribuut#getGebruiktInBedrijfsRegels
     * @model opposite="gebruiktInBedrijfsRegels"
     * @generated
     */
    List<Attribuut> getAttributen();

    /**
     * Returns the value of the '<em><b>Element</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Element#getBedrijfsRegels <em>Bedrijfs Regels</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Element</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Element</em>' container reference.
     * @see #setElement(Element)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBedrijfsRegel_Element()
     * @see nl.bzk.brp.ecore.bmr.Element#getBedrijfsRegels
     * @model opposite="bedrijfsRegels"
     * @generated
     */
    Element getElement();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getElement <em>Element</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Element</em>' container reference.
     * @see #getElement()
     * @generated
     */
    void setElement(Element value);

    /**
     * Returns the value of the '<em><b>Soort Bedrijfs Regel</b></em>' attribute.
     * The literals are from the enumeration {@link nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Soort Bedrijfs Regel</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Soort Bedrijfs Regel</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel
     * @see #setSoortBedrijfsRegel(SoortBedrijfsRegel)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBedrijfsRegel_SoortBedrijfsRegel()
     * @model
     * @generated
     */
    SoortBedrijfsRegel getSoortBedrijfsRegel();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getSoortBedrijfsRegel <em>Soort Bedrijfs Regel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Soort Bedrijfs Regel</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel
     * @see #getSoortBedrijfsRegel()
     * @generated
     */
    void setSoortBedrijfsRegel(SoortBedrijfsRegel value);

    /**
     * Returns the value of the '<em><b>Waarden</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getBedrijfsRegel <em>Bedrijfs Regel</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Waarden</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Waarden</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBedrijfsRegel_Waarden()
     * @see nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getBedrijfsRegel
     * @model opposite="bedrijfsRegel" containment="true"
     * @generated
     */
    List<WaarderegelWaarde> getWaarden();

} // BedrijfsRegel
