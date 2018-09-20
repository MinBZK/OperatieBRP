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
 * A representation of the model object '<em><b>Tuple</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tuple#getId <em>Id</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tuple#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tuple#getRelatiefId <em>Relatief Id</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tuple#getCode <em>Code</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tuple#getNaam <em>Naam</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tuple#getNaamMannelijk <em>Naam Mannelijk</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tuple#getNaamVrouwelijk <em>Naam Vrouwelijk</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tuple#getOmschrijving <em>Omschrijving</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tuple#getHeeftMaterieleHistorie <em>Heeft Materiele Historie</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tuple#getDatumAanvangGeldigheid <em>Datum Aanvang Geldigheid</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Tuple#getDatumEindeGeldigheid <em>Datum Einde Geldigheid</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTuple()
 * @model
 * @generated
 */
public interface Tuple extends EObject
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
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTuple_Id()
     * @model
     * @generated
     */
    Long getId();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tuple#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(Long value);

    /**
     * Returns the value of the '<em><b>Object Type</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.ObjectType#getTuples <em>Tuples</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object Type</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object Type</em>' container reference.
     * @see #setObjectType(ObjectType)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTuple_ObjectType()
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getTuples
     * @model opposite="tuples"
     * @generated
     */
    ObjectType getObjectType();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tuple#getObjectType <em>Object Type</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Object Type</em>' container reference.
     * @see #getObjectType()
     * @generated
     */
    void setObjectType(ObjectType value);

    /**
     * Returns the value of the '<em><b>Relatief Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Relatief Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Relatief Id</em>' attribute.
     * @see #setRelatiefId(Integer)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTuple_RelatiefId()
     * @model
     * @generated
     */
    Integer getRelatiefId();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tuple#getRelatiefId <em>Relatief Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Relatief Id</em>' attribute.
     * @see #getRelatiefId()
     * @generated
     */
    void setRelatiefId(Integer value);

    /**
     * Returns the value of the '<em><b>Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Code</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Code</em>' attribute.
     * @see #setCode(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTuple_Code()
     * @model
     * @generated
     */
    String getCode();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tuple#getCode <em>Code</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Code</em>' attribute.
     * @see #getCode()
     * @generated
     */
    void setCode(String value);

    /**
     * Returns the value of the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Naam</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Naam</em>' attribute.
     * @see #setNaam(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTuple_Naam()
     * @model
     * @generated
     */
    String getNaam();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tuple#getNaam <em>Naam</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Naam</em>' attribute.
     * @see #getNaam()
     * @generated
     */
    void setNaam(String value);

    /**
     * Returns the value of the '<em><b>Naam Mannelijk</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Naam Mannelijk</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Naam Mannelijk</em>' attribute.
     * @see #setNaamMannelijk(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTuple_NaamMannelijk()
     * @model
     * @generated
     */
    String getNaamMannelijk();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tuple#getNaamMannelijk <em>Naam Mannelijk</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Naam Mannelijk</em>' attribute.
     * @see #getNaamMannelijk()
     * @generated
     */
    void setNaamMannelijk(String value);

    /**
     * Returns the value of the '<em><b>Naam Vrouwelijk</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Naam Vrouwelijk</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Naam Vrouwelijk</em>' attribute.
     * @see #setNaamVrouwelijk(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTuple_NaamVrouwelijk()
     * @model
     * @generated
     */
    String getNaamVrouwelijk();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tuple#getNaamVrouwelijk <em>Naam Vrouwelijk</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Naam Vrouwelijk</em>' attribute.
     * @see #getNaamVrouwelijk()
     * @generated
     */
    void setNaamVrouwelijk(String value);

    /**
     * Returns the value of the '<em><b>Omschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Omschrijving</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Omschrijving</em>' attribute.
     * @see #setOmschrijving(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTuple_Omschrijving()
     * @model
     * @generated
     */
    String getOmschrijving();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tuple#getOmschrijving <em>Omschrijving</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Omschrijving</em>' attribute.
     * @see #getOmschrijving()
     * @generated
     */
    void setOmschrijving(String value);

    /**
     * Returns the value of the '<em><b>Heeft Materiele Historie</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Heeft Materiele Historie</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Heeft Materiele Historie</em>' attribute.
     * @see #setHeeftMaterieleHistorie(Boolean)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTuple_HeeftMaterieleHistorie()
     * @model
     * @generated
     */
    Boolean getHeeftMaterieleHistorie();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tuple#getHeeftMaterieleHistorie <em>Heeft Materiele Historie</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Heeft Materiele Historie</em>' attribute.
     * @see #getHeeftMaterieleHistorie()
     * @generated
     */
    void setHeeftMaterieleHistorie(Boolean value);

    /**
     * Returns the value of the '<em><b>Datum Aanvang Geldigheid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Datum Aanvang Geldigheid</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Datum Aanvang Geldigheid</em>' attribute.
     * @see #setDatumAanvangGeldigheid(Integer)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTuple_DatumAanvangGeldigheid()
     * @model
     * @generated
     */
    Integer getDatumAanvangGeldigheid();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tuple#getDatumAanvangGeldigheid <em>Datum Aanvang Geldigheid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Datum Aanvang Geldigheid</em>' attribute.
     * @see #getDatumAanvangGeldigheid()
     * @generated
     */
    void setDatumAanvangGeldigheid(Integer value);

    /**
     * Returns the value of the '<em><b>Datum Einde Geldigheid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Datum Einde Geldigheid</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Datum Einde Geldigheid</em>' attribute.
     * @see #setDatumEindeGeldigheid(Integer)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getTuple_DatumEindeGeldigheid()
     * @model
     * @generated
     */
    Integer getDatumEindeGeldigheid();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Tuple#getDatumEindeGeldigheid <em>Datum Einde Geldigheid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Datum Einde Geldigheid</em>' attribute.
     * @see #getDatumEindeGeldigheid()
     * @generated
     */
    void setDatumEindeGeldigheid(Integer value);

} // Tuple
