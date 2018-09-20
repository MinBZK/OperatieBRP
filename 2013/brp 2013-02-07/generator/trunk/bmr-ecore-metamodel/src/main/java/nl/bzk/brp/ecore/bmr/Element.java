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
import java.util.Set;

import org.eclipse.emf.common.util.EList;


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
 *   <li>{@link nl.bzk.brp.ecore.bmr.Element#getSoort <em>Soort</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getElement()
 * @model abstract="true"
 * @generated
 */
public interface Element extends ModelElement {

    /**
     * Returns the value of the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.BedrijfsRegel}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getElement <em>Element</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Bedrijfs Regels</em>' containment reference list isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Bedrijfs Regels</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getElement_BedrijfsRegels()
     * @see nl.bzk.brp.ecore.bmr.BedrijfsRegel#getElement
     * @model opposite="element" containment="true"
     * @generated
     */
    List<BedrijfsRegel> getBedrijfsRegels();

    /**
     * Returns the value of the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Beschrijving</em>' attribute isn't clear, there really should be more of a description
     * here...
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
     * Returns the value of the '<em><b>Teksten</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Tekst}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Tekst#getElement <em>Element</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Teksten</em>' map isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Teksten</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getElement_Teksten()
     * @see nl.bzk.brp.ecore.bmr.Tekst#getElement
     * @model opposite="element" containment="true"
     * @generated
     */
    List<Tekst> getTeksten();

    /**
     * Returns the value of the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Laag</em>' attribute isn't clear, there really should be more of a description here...
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
     * If the meaning of the '<em>Sync Id</em>' attribute isn't clear, there really should be more of a description
     * here...
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

    /**
     * Returns the value of the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Soort</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Soort</em>' attribute.
     * @see #setSoort(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getElement_Soort()
     * @model
     * @generated
     */
    String getSoort();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Element#getSoort <em>Soort</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Soort</em>' attribute.
     * @see #getSoort()
     * @generated
     */
    void setSoort(String value);

    /**
     * Geef de tekst van het gegeven soort tekst.
     *
     * @param def Het soort tekst.
     * @return De tekst van het gegeven soort tekst.
     */
    Tekst getTekst(SoortTekst def);

    /**
     * Een identificatie geschikt als xml:id.
     *
     * @return Een identificatie geschikt als xml:id.
     */
    String xmlId();

    /**
     * De teksten van het soort dat voorkomt in de meegegeven set.
     *
     * @param soorten de set van soorten tekst die we willen hebben.
     * @return de teksten van het soort dat voorkomt in de meegegeven set.
     */
    List<Tekst> getTeksten(Set<SoortTekst> soorten);
} // Element
