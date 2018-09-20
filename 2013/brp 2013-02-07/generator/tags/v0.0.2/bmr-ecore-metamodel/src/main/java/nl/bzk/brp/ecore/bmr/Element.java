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
import org.eclipse.emf.common.util.EMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Element#getBedrijfsRegels <em>Bedrijfs Regels</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Element#getBeschrijving <em>Beschrijving</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Element#getTeksten <em>Teksten</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Element#getLaag <em>Laag</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Element#getSyncId <em>Sync Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getElement()
 * @model abstract="true"
 * @generated
 */
public interface Element extends ModelElement
{
    /**
     * Returns the value of the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.BedrijfsRegel}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getElement <em>Element</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Bedrijfs Regels</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Bedrijfs Regels</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getElement_BedrijfsRegels()
     * @see nl.bzk.brp.ecore.bmr.BedrijfsRegel#getElement
     * @model opposite="element" containment="true"
     * @generated
     */
    EList<BedrijfsRegel> getBedrijfsRegels();

    /**
     * Returns the value of the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Beschrijving</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Beschrijving</em>' attribute.
     * @see #setBeschrijving(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getElement_Beschrijving()
     * @model
     * @generated
     */
    String getBeschrijving();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Element#getBeschrijving <em>Beschrijving</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Beschrijving</em>' attribute.
     * @see #getBeschrijving()
     * @generated
     */
    void setBeschrijving(String value);

    /**
     * Returns the value of the '<em><b>Teksten</b></em>' map.
     * The key is of type {@link nl.bzk.brp.ecore.bmr.SoortTekst},
     * and the value is of type {@link nl.bzk.brp.ecore.bmr.Tekst},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Teksten</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Teksten</em>' map.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getElement_Teksten()
     * @model mapType="nl.bzk.brp.ecore.bmr.SoortTekstToTekstMapEntry<nl.bzk.brp.ecore.bmr.SoortTekst, nl.bzk.brp.ecore.bmr.Tekst>"
     * @generated
     */
    EMap<SoortTekst, Tekst> getTeksten();

    /**
     * Returns the value of the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Laag</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Laag</em>' attribute.
     * @see #setLaag(Integer)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getElement_Laag()
     * @model
     * @generated
     */
    Integer getLaag();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Element#getLaag <em>Laag</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Laag</em>' attribute.
     * @see #getLaag()
     * @generated
     */
    void setLaag(Integer value);

    /**
     * Returns the value of the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sync Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Sync Id</em>' attribute.
     * @see #setSyncId(Integer)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getElement_SyncId()
     * @model
     * @generated
     */
    Integer getSyncId();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Element#getSyncId <em>Sync Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Sync Id</em>' attribute.
     * @see #getSyncId()
     * @generated
     */
    void setSyncId(Integer value);

} // Element
