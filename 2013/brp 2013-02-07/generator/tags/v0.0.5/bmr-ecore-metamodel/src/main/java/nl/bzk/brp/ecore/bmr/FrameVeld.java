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
 * A representation of the model object '<em><b>Frame Veld</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.FrameVeld#getVolgnummer <em>Volgnummer</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.FrameVeld#getFrame <em>Frame</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.FrameVeld#getAttribuut <em>Attribuut</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFrameVeld()
 * @model
 * @generated
 */
public interface FrameVeld extends ModelElement
{
    /**
     * Returns the value of the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Volgnummer</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Volgnummer</em>' attribute.
     * @see #setVolgnummer(Integer)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFrameVeld_Volgnummer()
     * @model
     * @generated
     */
    Integer getVolgnummer();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.FrameVeld#getVolgnummer <em>Volgnummer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Volgnummer</em>' attribute.
     * @see #getVolgnummer()
     * @generated
     */
    void setVolgnummer(Integer value);

    /**
     * Returns the value of the '<em><b>Frame</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Frame#getVelden <em>Velden</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Frame</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Frame</em>' container reference.
     * @see #setFrame(Frame)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFrameVeld_Frame()
     * @see nl.bzk.brp.ecore.bmr.Frame#getVelden
     * @model opposite="velden" transient="false" derived="true"
     * @generated
     */
    Frame getFrame();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.FrameVeld#getFrame <em>Frame</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Frame</em>' container reference.
     * @see #getFrame()
     * @generated
     */
    void setFrame(Frame value);

    /**
     * Returns the value of the '<em><b>Attribuut</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribuut</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attribuut</em>' reference.
     * @see #setAttribuut(Attribuut)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFrameVeld_Attribuut()
     * @model
     * @generated
     */
    Attribuut getAttribuut();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.FrameVeld#getAttribuut <em>Attribuut</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribuut</em>' reference.
     * @see #getAttribuut()
     * @generated
     */
    void setAttribuut(Attribuut value);

} // FrameVeld
