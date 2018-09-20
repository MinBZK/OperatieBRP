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
 * A representation of the model object '<em><b>Frame</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Frame#getVolgnummer <em>Volgnummer</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Frame#getFormulier <em>Formulier</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Frame#getBron <em>Bron</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Frame#getVelden <em>Velden</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFrame()
 * @model
 * @generated
 */
public interface Frame extends ModelElement
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
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFrame_Volgnummer()
     * @model
     * @generated
     */
    Integer getVolgnummer();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Frame#getVolgnummer <em>Volgnummer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Volgnummer</em>' attribute.
     * @see #getVolgnummer()
     * @generated
     */
    void setVolgnummer(Integer value);

    /**
     * Returns the value of the '<em><b>Formulier</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Formulier#getFrames <em>Frames</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Formulier</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Formulier</em>' container reference.
     * @see #setFormulier(Formulier)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFrame_Formulier()
     * @see nl.bzk.brp.ecore.bmr.Formulier#getFrames
     * @model opposite="frames" transient="false" derived="true"
     * @generated
     */
    Formulier getFormulier();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Frame#getFormulier <em>Formulier</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Formulier</em>' container reference.
     * @see #getFormulier()
     * @generated
     */
    void setFormulier(Formulier value);

    /**
     * Returns the value of the '<em><b>Bron</b></em>' reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Bron#getFrames <em>Frames</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Bron</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Bron</em>' reference.
     * @see #setBron(Bron)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFrame_Bron()
     * @see nl.bzk.brp.ecore.bmr.Bron#getFrames
     * @model opposite="frames"
     * @generated
     */
    Bron getBron();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Frame#getBron <em>Bron</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Bron</em>' reference.
     * @see #getBron()
     * @generated
     */
    void setBron(Bron value);

    /**
     * Returns the value of the '<em><b>Velden</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.FrameVeld}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.FrameVeld#getFrame <em>Frame</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Velden</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Velden</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getFrame_Velden()
     * @see nl.bzk.brp.ecore.bmr.FrameVeld#getFrame
     * @model opposite="frame" containment="true"
     * @generated
     */
    EList<FrameVeld> getVelden();

} // Frame
