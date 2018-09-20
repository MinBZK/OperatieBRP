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
 * A representation of the model object '<em><b>Attribuut</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Attribuut#getGroep <em>Groep</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Attribuut#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Attribuut#getType <em>Type</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Attribuut#getAfleidbaar <em>Afleidbaar</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Attribuut#getHistorieVastleggen <em>Historie Vastleggen</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Attribuut#isVerplicht <em>Verplicht</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Attribuut#getInverseAssociatieNaam <em>Inverse Associatie Naam</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Attribuut#getInverseAssociatie <em>Inverse Associatie</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.Attribuut#getGebruiktInBedrijfsRegels <em>Gebruikt In Bedrijfs Regels</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuut()
 * @model
 * @generated
 */
public interface Attribuut extends GelaagdElement {

    /**
     * Returns the value of the '<em><b>Groep</b></em>' reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Groep#getAttributen <em>Attributen</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Groep</em>' reference isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Groep</em>' reference.
     * @see #setGroep(Groep)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuut_Groep()
     * @see nl.bzk.brp.ecore.bmr.Groep#getAttributen
     * @model opposite="attributen"
     * @generated
     */
    Groep getGroep();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Attribuut#getGroep <em>Groep</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Groep</em>' reference.
     * @see #getGroep()
     * @generated
     */
    void setGroep(Groep value);

    /**
     * Returns the value of the '<em><b>Object Type</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.ObjectType#getAttributen <em>Attributen</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object Type</em>' container reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object Type</em>' container reference.
     * @see #setObjectType(ObjectType)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuut_ObjectType()
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getAttributen
     * @model opposite="attributen"
     * @generated
     */
    ObjectType getObjectType();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Attribuut#getObjectType <em>Object Type</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Object Type</em>' container reference.
     * @see #getObjectType()
     * @generated
     */
    void setObjectType(ObjectType value);

    /**
     * Returns the value of the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' reference.
     * @see #setType(Type)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuut_Type()
     * @model
     * @generated
     */
    Type getType();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Attribuut#getType <em>Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' reference.
     * @see #getType()
     * @generated
     */
    void setType(Type value);

    /**
     * Returns the value of the '<em><b>Afleidbaar</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Afleidbaar</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Afleidbaar</em>' attribute.
     * @see #setAfleidbaar(Boolean)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuut_Afleidbaar()
     * @model default="false"
     * @generated
     */
    Boolean getAfleidbaar();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Attribuut#getAfleidbaar <em>Afleidbaar</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Afleidbaar</em>' attribute.
     * @see #getAfleidbaar()
     * @generated
     */
    void setAfleidbaar(Boolean value);

    /**
     * Returns the value of the '<em><b>Historie Vastleggen</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * The literals are from the enumeration {@link nl.bzk.brp.ecore.bmr.Historie}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Historie Vastleggen</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Historie Vastleggen</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.Historie
     * @see #setHistorieVastleggen(Historie)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuut_HistorieVastleggen()
     * @model default="false"
     * @generated
     */
    Historie getHistorieVastleggen();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Attribuut#getHistorieVastleggen <em>Historie Vastleggen</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Historie Vastleggen</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.Historie
     * @see #getHistorieVastleggen()
     * @generated
     */
    void setHistorieVastleggen(Historie value);

    /**
     * Returns the value of the '<em><b>Verplicht</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Verplicht</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Verplicht</em>' attribute.
     * @see #setVerplicht(boolean)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuut_Verplicht()
     * @model default="false"
     * @generated
     */
    boolean isVerplicht();

    /**
     * Returns the value of the '<em><b>Inverse Associatie Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Inverse Associatie Naam</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Inverse Associatie Naam</em>' attribute.
     * @see #setInverseAssociatieNaam(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuut_InverseAssociatieNaam()
     * @model
     * @generated
     */
    String getInverseAssociatieNaam();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Attribuut#getInverseAssociatieNaam <em>Inverse Associatie Naam</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Inverse Associatie Naam</em>' attribute.
     * @see #getInverseAssociatieNaam()
     * @generated
     */
    void setInverseAssociatieNaam(String value);

    /**
     * Returns the value of the '<em><b>Inverse Associatie</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Inverse Associatie</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Inverse Associatie</em>' attribute.
     * @see #setInverseAssociatie(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuut_InverseAssociatie()
     * @model
     * @generated
     */
    String getInverseAssociatie();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Attribuut#getInverseAssociatie <em>Inverse Associatie</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Inverse Associatie</em>' attribute.
     * @see #getInverseAssociatie()
     * @generated
     */
    void setInverseAssociatie(String value);

    /**
     * Returns the value of the '<em><b>Gebruikt In Bedrijfs Regels</b></em>' reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.BedrijfsRegel}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getAttributen <em>Attributen</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Gebruikt In Bedrijfs Regels</em>' reference list isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Gebruikt In Bedrijfs Regels</em>' reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getAttribuut_GebruiktInBedrijfsRegels()
     * @see nl.bzk.brp.ecore.bmr.BedrijfsRegel#getAttributen
     * @model opposite="attributen" derived="true"
     * @generated
     */
    List<BedrijfsRegel> getGebruiktInBedrijfsRegels();

    /**
     * Of dit attribuut onderdeel is van de identifier van het objecttype waarvan het onderdeel uitmaakt.
     *
     * @return <code>true</code> als dit attribuut onderdeel is van de identifier van het objecttype waarvan het
     *         onderdeel uitmaakt, en anders <code>false</code>.
     */
    boolean isIdentifier();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.Attribuut#isVerplicht <em>Verplicht</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Verplicht</em>' attribute.
     * @see #isVerplicht()
     * @generated
     */
    void setVerplicht(boolean value);

    /**
     * Of dit attribuut afleidbaar is. Non-nullable variant van {link {@link #getAfleidbaar()}.
     *
     * @return <code>true</code> als dit attribuut afleidbaar is, en anders <code>false</code>.
     */
    boolean isAfleidbaar();

} // Attribuut
