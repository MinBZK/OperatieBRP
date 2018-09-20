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
 * A representation of the model object '<em><b>Object Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getAttributen <em>Attributen</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getGroepen <em>Groepen</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getInSetOfModel <em>In Set Of Model</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getMeervoudsNaam <em>Meervouds Naam</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getSoortInhoud <em>Soort Inhoud</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getSubTypes <em>Sub Types</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getSuperType <em>Super Type</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getTuples <em>Tuples</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getVersie <em>Versie</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getAfleidbaar <em>Afleidbaar</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getHistorieVastleggen <em>Historie Vastleggen</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getKunstmatigeSleutel <em>Kunstmatige Sleutel</em>}</li>
 * <li>{@link nl.bzk.brp.ecore.bmr.ObjectType#getLookup <em>Lookup</em>}</li>
 * </ul>
 * </p>
 *
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType()
 * @model
 * @generated
 */
public interface ObjectType extends Type {

    /**
     * Returns the value of the '<em><b>Attributen</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Attribuut}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Attribuut#getObjectType
     * <em>Object Type</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attributen</em>' containment reference list isn't clear, there really should be more
     * of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Attributen</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_Attributen()
     * @see nl.bzk.brp.ecore.bmr.Attribuut#getObjectType
     * @model opposite="objectType" containment="true"
     * @generated
     */
    EList<Attribuut> getAttributen();

    /**
     * Returns the value of the '<em><b>Groepen</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Groep}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Groep#getObjectType <em>Object Type</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Groepen</em>' containment reference list isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Groepen</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_Groepen()
     * @see nl.bzk.brp.ecore.bmr.Groep#getObjectType
     * @model opposite="objectType" containment="true"
     * @generated
     */
    EList<Groep> getGroepen();

    /**
     * Returns the value of the '<em><b>In Set Of Model</b></em>' attribute.
     * The literals are from the enumeration {@link nl.bzk.brp.ecore.bmr.InSetOfModel}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>In Set Of Model</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>In Set Of Model</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.InSetOfModel
     * @see #setInSetOfModel(InSetOfModel)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_InSetOfModel()
     * @model
     * @generated
     */
    InSetOfModel getInSetOfModel();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.ObjectType#getInSetOfModel <em>In Set Of Model</em>}'
     * attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>In Set Of Model</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.InSetOfModel
     * @see #getInSetOfModel()
     * @generated
     */
    void setInSetOfModel(InSetOfModel value);

    /**
     * Returns the value of the '<em><b>Meervouds Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Meervouds Naam</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Meervouds Naam</em>' attribute.
     * @see #setMeervoudsNaam(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_MeervoudsNaam()
     * @model
     * @generated
     */
    String getMeervoudsNaam();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.ObjectType#getMeervoudsNaam <em>Meervouds Naam</em>}'
     * attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Meervouds Naam</em>' attribute.
     * @see #getMeervoudsNaam()
     * @generated
     */
    void setMeervoudsNaam(String value);

    /**
     * Returns the value of the '<em><b>Soort Inhoud</b></em>' attribute.
     * The literals are from the enumeration {@link nl.bzk.brp.ecore.bmr.SoortInhoud}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Soort Inhoud</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Soort Inhoud</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.SoortInhoud
     * @see #setSoortInhoud(SoortInhoud)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_SoortInhoud()
     * @model
     * @generated
     */
    SoortInhoud getSoortInhoud();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.ObjectType#getSoortInhoud <em>Soort Inhoud</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Soort Inhoud</em>' attribute.
     * @see nl.bzk.brp.ecore.bmr.SoortInhoud
     * @see #getSoortInhoud()
     * @generated
     */
    void setSoortInhoud(SoortInhoud value);

    /**
     * Returns the value of the '<em><b>Sub Types</b></em>' reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.ObjectType}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.ObjectType#getSuperType <em>Super Type</em>}
     * '.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sub Types</em>' reference list isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Sub Types</em>' reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_SubTypes()
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getSuperType
     * @model opposite="superType" derived="true"
     * @generated
     */
    EList<ObjectType> getSubTypes();

    /**
     * Returns the value of the '<em><b>Super Type</b></em>' reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.ObjectType#getSubTypes <em>Sub Types</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Super Type</em>' reference isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Super Type</em>' reference.
     * @see #setSuperType(ObjectType)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_SuperType()
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getSubTypes
     * @model opposite="subTypes"
     * @generated
     */
    ObjectType getSuperType();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.ObjectType#getSuperType <em>Super Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Super Type</em>' reference.
     * @see #getSuperType()
     * @generated
     */
    void setSuperType(ObjectType value);

    /**
     * Returns the value of the '<em><b>Tuples</b></em>' containment reference list.
     * The list contents are of type {@link nl.bzk.brp.ecore.bmr.Tuple}.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Tuple#getObjectType <em>Object Type</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Tuples</em>' containment reference list isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Tuples</em>' containment reference list.
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_Tuples()
     * @see nl.bzk.brp.ecore.bmr.Tuple#getObjectType
     * @model opposite="objectType" containment="true"
     * @generated
     */
    EList<Tuple> getTuples();

    /**
     * Returns the value of the '<em><b>Versie</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link nl.bzk.brp.ecore.bmr.Versie#getObjectTypes <em>Object Types</em>}
     * '.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Versie</em>' container reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Versie</em>' container reference.
     * @see #setVersie(Versie)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_Versie()
     * @see nl.bzk.brp.ecore.bmr.Versie#getObjectTypes
     * @model opposite="objectTypes"
     * @generated
     */
    Versie getVersie();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.ObjectType#getVersie <em>Versie</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Versie</em>' container reference.
     * @see #getVersie()
     * @generated
     */
    void setVersie(Versie value);

    /**
     * Returns the value of the '<em><b>Afleidbaar</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Afleidbaar</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Afleidbaar</em>' attribute.
     * @see #setAfleidbaar(Boolean)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_Afleidbaar()
     * @model default="false"
     * @generated
     */
    Boolean getAfleidbaar();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.ObjectType#getAfleidbaar <em>Afleidbaar</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Afleidbaar</em>' attribute.
     * @see #getAfleidbaar()
     * @generated
     */
    void setAfleidbaar(Boolean value);

    /**
     * Returns the value of the '<em><b>Historie Vastleggen</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Historie Vastleggen</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Historie Vastleggen</em>' attribute.
     * @see #setHistorieVastleggen(String)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_HistorieVastleggen()
     * @model default="false"
     * @generated
     */
    String getHistorieVastleggen();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.ObjectType#getHistorieVastleggen <em>Historie Vastleggen</em>}
     * ' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Historie Vastleggen</em>' attribute.
     * @see #getHistorieVastleggen()
     * @generated
     */
    void setHistorieVastleggen(String value);

    /**
     * Returns the value of the '<em><b>Kunstmatige Sleutel</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Kunstmatige Sleutel</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Kunstmatige Sleutel</em>' attribute.
     * @see #setKunstmatigeSleutel(Boolean)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_KunstmatigeSleutel()
     * @model
     * @generated
     */
    Boolean getKunstmatigeSleutel();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.ObjectType#getKunstmatigeSleutel <em>Kunstmatige Sleutel</em>}
     * ' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Kunstmatige Sleutel</em>' attribute.
     * @see #getKunstmatigeSleutel()
     * @generated
     */
    void setKunstmatigeSleutel(Boolean value);

    /**
     * Returns the value of the '<em><b>Lookup</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Lookup</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Lookup</em>' attribute.
     * @see #setLookup(Boolean)
     * @see nl.bzk.brp.ecore.bmr.BmrPackage#getObjectType_Lookup()
     * @model default="false"
     * @generated
     */
    Boolean getLookup();

    /**
     * Sets the value of the '{@link nl.bzk.brp.ecore.bmr.ObjectType#getLookup <em>Lookup</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Lookup</em>' attribute.
     * @see #getLookup()
     * @generated
     */
    void setLookup(Boolean value);

    Attribuut getAttribuut(String naam);

    Schema getSchema();

    boolean hasCode();

    boolean isKunstmatigeSleutel();

    /**
     * Bepaalt of dit een objecttype is dat een historische versie van een ander objecttype te representeren.
     *
     * @return <code>true</code> als het een historie object is, en anders <code>false</code>.
     */
    boolean isHistorieObject();

    Attribuut getIdAttribuut();

} // ObjectType
