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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tekst</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tekst#getId <em>Id</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tekst#getElement <em>Element</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tekst#getSoort <em>Soort</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tekst#getTekst <em>Tekst</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tekst#getHtmlTekst <em>Html Tekst</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTekst()
 * @model
 * @generated
 */
public interface Tekst extends EObject
{
    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(Long)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTekst_Id()
     * @model
     * @generated
     */
    Long getId();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tekst#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(Long value);

    /**
     * Returns the value of the '<em><b>Element</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Element#getTeksten <em>Teksten</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Element</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Element</em>' container reference.
     * @see #setElement(Element)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTekst_Element()
     * @see nl.bzk.brp.ecore.bmr.Element#getTeksten
     * @model opposite="teksten" transient="false" derived="true"
     * @generated
     */
    Element getElement();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tekst#getElement <em>Element</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Element</em>' container reference.
     * @see #getElement()
     * @generated
     */
    void setElement(Element value);

    /**
     * Returns the value of the '<em><b>Soort</b></em>' attribute.
     * The literals are from the enumeration {@link nl.bzk.brp.ecore.bmr.SoortTekst}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Soort</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Soort</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.SoortTekst
     * @see #setSoort(SoortTekst)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTekst_Soort()
     * @model
     * @generated
     */
    SoortTekst getSoort();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tekst#getSoort <em>Soort</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Soort</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.SoortTekst
     * @see #getSoort()
     * @generated
     */
    void setSoort(SoortTekst value);

    /**
     * Returns the value of the '<em><b>Tekst</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Tekst</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Tekst</em>' attribute.
     * @see #setTekst(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTekst_Tekst()
     * @model
     * @generated
     */
    String getTekst();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tekst#getTekst <em>Tekst</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Tekst</em>' attribute.
     * @see #getTekst()
     * @generated
     */
    void setTekst(String value);

    /**
     * Returns the value of the '<em><b>Html Tekst</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Html Tekst</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Html Tekst</em>' attribute.
     * @see #setHtmlTekst(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTekst_HtmlTekst()
     * @model
     * @generated
     */
    String getHtmlTekst();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tekst#getHtmlTekst <em>Html Tekst</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Html Tekst</em>' attribute.
     * @see #getHtmlTekst()
     * @generated
     */
    void setHtmlTekst(String value);

} // Tekst
