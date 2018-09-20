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
 * A representation of the model object '<em><b>Bron</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Bron#getMeervoudsvorm <em>Meervoudsvorm</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Bron#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Bron#getVolgnummer <em>Volgnummer</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Bron#getFormulier <em>Formulier</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Bron#getFrames <em>Frames</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Bron#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Bron#getLink <em>Link</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Bron#getBronAttributen <em>Bron Attributen</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBron()
 * @model
 * @generated
 */
public interface Bron extends ModelElement
{
    /**
     * Returns the value of the '<em><b>Meervoudsvorm</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Meervoudsvorm</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Meervoudsvorm</em>' attribute.
     * @see #setMeervoudsvorm(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBron_Meervoudsvorm()
     * @model
     * @generated
     */
    String getMeervoudsvorm();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Bron#getMeervoudsvorm <em>Meervoudsvorm</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Meervoudsvorm</em>' attribute.
     * @see #getMeervoudsvorm()
     * @generated
     */
    void setMeervoudsvorm(String value);

    /**
     * Returns the value of the '<em><b>Identifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Identifier</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Identifier</em>' attribute.
     * @see #setIdentifier(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBron_Identifier()
     * @model
     * @generated
     */
    String getIdentifier();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Bron#getIdentifier <em>Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Identifier</em>' attribute.
     * @see #getIdentifier()
     * @generated
     */
    void setIdentifier(String value);

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
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBron_Volgnummer()
     * @model
     * @generated
     */
    Integer getVolgnummer();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Bron#getVolgnummer <em>Volgnummer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Volgnummer</em>' attribute.
     * @see #getVolgnummer()
     * @generated
     */
    void setVolgnummer(Integer value);

    /**
     * Returns the value of the '<em><b>Formulier</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Formulier#getBronnen <em>Bronnen</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Formulier</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Formulier</em>' container reference.
     * @see #setFormulier(Formulier)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBron_Formulier()
     * @see nl.bzk.brp.ecore.bmr.Formulier#getBronnen
     * @model opposite="bronnen" transient="false" derived="true"
     * @generated
     */
    Formulier getFormulier();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Bron#getFormulier <em>Formulier</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Formulier</em>' container reference.
     * @see #getFormulier()
     * @generated
     */
    void setFormulier(Formulier value);

    /**
     * Returns the value of the '<em><b>Frames</b></em>' reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Frame}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Frame#getBron <em>Bron</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Frames</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Frames</em>' reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBron_Frames()
     * @see nl.bzk.brp.ecore.bmr.Frame#getBron
     * @model opposite="bron" derived="true"
     * @generated
     */
    EList<Frame> getFrames();

    /**
     * Returns the value of the '<em><b>Object Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object Type</em>' reference.
     * @see #setObjectType(ObjectType)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBron_ObjectType()
     * @model
     * @generated
     */
    ObjectType getObjectType();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Bron#getObjectType <em>Object Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Object Type</em>' reference.
     * @see #getObjectType()
     * @generated
     */
    void setObjectType(ObjectType value);

    /**
     * Returns the value of the '<em><b>Link</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Link</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Link</em>' reference.
     * @see #setLink(Attribuut)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBron_Link()
     * @model
     * @generated
     */
    Attribuut getLink();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Bron#getLink <em>Link</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Link</em>' reference.
     * @see #getLink()
     * @generated
     */
    void setLink(Attribuut value);

    /**
     * Returns the value of the '<em><b>Bron Attributen</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.BronAttribuut}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.BronAttribuut#getBron <em>Bron</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Bron Attributen</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Bron Attributen</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getBron_BronAttributen()
     * @see nl.bzk.brp.ecore.bmr.BronAttribuut#getBron
     * @model opposite="bron" containment="true"
     * @generated
     */
    EList<BronAttribuut> getBronAttributen();

} // Bron
