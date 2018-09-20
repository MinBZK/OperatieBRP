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
 * A representation of the model object '<em><b>Versie</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Versie#getSchema <em>Schema</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Versie#getVersieTag <em>Versie Tag</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Versie#getObjectTypes <em>Object Types</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Versie#getAttribuutTypes <em>Attribuut Types</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Versie#getBerichtSjablonen <em>Bericht Sjablonen</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getVersie()
 * @model
 * @generated
 */
public interface Versie extends Element {

    /**
     * Returns the value of the '<em><b>Schema</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Schema#getVersies <em>Versies</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Schema</em>' container reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Schema</em>' container reference.
     * @see #setSchema(Schema)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getVersie_Schema()
     * @see nl.bzk.brp.ecore.bmr.Schema#getVersies
     * @model opposite="versies"
     * @generated
     */
    Schema getSchema();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Versie#getSchema <em>Schema</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Schema</em>' container reference.
     * @see #getSchema()
     * @generated
     */
    void setSchema(Schema value);

    /**
     * Returns the value of the '<em><b>Versie Tag</b></em>' attribute.
     * The literals are from the enumeration {@link nl.bzk.brp.ecore.bmr.VersieTag}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Versie Tag</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Versie Tag</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.VersieTag
     * @see #setVersieTag(VersieTag)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getVersie_VersieTag()
     * @model
     * @generated
     */
    VersieTag getVersieTag();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Versie#getVersieTag <em>Versie Tag</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Versie Tag</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.VersieTag
     * @see #getVersieTag()
     * @generated
     */
    void setVersieTag(VersieTag value);

    /**
     * Returns the value of the '<em><b>Object Types</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.ObjectType}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.ObjectType#getVersie <em>Versie</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object Types</em>' containment reference list isn't clear, there really should be more
     * of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object Types</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getVersie_ObjectTypes()
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getVersie
     * @model opposite="versie" containment="true"
     * @generated
     */
    List<ObjectType> getObjectTypes();

    /**
     * Returns the value of the '<em><b>Attribuut Types</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.AttribuutType}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.AttribuutType#getVersie <em>Versie</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribuut Types</em>' containment reference list isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attribuut Types</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getVersie_AttribuutTypes()
     * @see nl.bzk.brp.ecore.bmr.AttribuutType#getVersie
     * @model opposite="versie" containment="true"
     * @generated
     */
    List<AttribuutType> getAttribuutTypes();

    /**
     * Returns the value of the '<em><b>Bericht Sjablonen</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.BerichtSjabloon}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.BerichtSjabloon#getVersie <em>Versie</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Bericht Sjablonen</em>' containment reference list isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Bericht Sjablonen</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getVersie_BerichtSjablonen()
     * @see nl.bzk.brp.ecore.bmr.BerichtSjabloon#getVersie
     * @model opposite="versie" containment="true"
     * @generated
     */
    List<BerichtSjabloon> getBerichtSjablonen();

    /**
     * Alle objecten die niet "his_" tabellen zijn.
     *
     * @return Alle objecten die niet "his_" tabellen zijn.
     */
    Iterable<ObjectType> getLogischeObjectTypes();

    /**
     * Alle objecttypen behorende tot de opgegeven laag.
     *
     * @param laag De laag waarvoor objecttypen gevraagd worden.
     * @return Alle objecttypen behorende tot de opgegeven laag.
     */
    Iterable<ObjectType> getObjectTypes(int laag);

    /**
     * Alle objecttypen behorende tot de opgegeven laag en model.
     *
     * @param laag De laag waarvoor objecttypen gevraagd worden.
     * @param insom Het model waartoe het objecttype behoort. Kan ook {@link InSetOfModel#BEIDE} zijn.
     * @return Alle objecttypen behorende tot de opgegeven laag.
     */
    Iterable<ObjectType> getObjectTypes(int laag, InSetOfModel insom);

    Iterable<ObjectType> getStatischeStamgegevens();

    Iterable<ObjectType> getObjectTypes(InSetOfModel inSom);

    /**
     * Alle objecttypen behorende tot de opgegeven laag.
     *
     * @param laag De laag waarvoor objecttypen gevraagd worden.
     * @return Alle objecttypen behorende tot de opgegeven laag.
     */
    Iterable<AttribuutType> getAttribuutTypes(int laag);

} // Versie
