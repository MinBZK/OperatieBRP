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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see nl.bzk.brp.ecore.bmr.BmrFactory
 * @model kind="package"
 * @generated
 */
public interface BmrPackage extends EPackage
{
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "bmr";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://brp.bzk.nl/schema/bmr.ecore";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "bmr";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    BmrPackage eINSTANCE = nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl.init();

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.ModelElementImpl <em>Model Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.ModelElementImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getModelElement()
     * @generated
     */
    int MODEL_ELEMENT = 11;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ELEMENT__ID = 0;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ELEMENT__NAAM = 1;

    /**
     * The number of structural features of the '<em>Model Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ELEMENT_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.ElementImpl <em>Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.ElementImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getElement()
     * @generated
     */
    int ELEMENT = 6;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ELEMENT__ID = MODEL_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ELEMENT__NAAM = MODEL_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ELEMENT__BEDRIJFS_REGELS = MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ELEMENT__BESCHRIJVING = MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ELEMENT__TEKSTEN = MODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ELEMENT__LAAG = MODEL_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ELEMENT__SYNC_ID = MODEL_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ELEMENT__SOORT = MODEL_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ELEMENT_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.GelaagdElementImpl <em>Gelaagd Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.GelaagdElementImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getGelaagdElement()
     * @generated
     */
    int GELAAGD_ELEMENT = 7;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__ID = ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__NAAM = ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__BEDRIJFS_REGELS = ELEMENT__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__BESCHRIJVING = ELEMENT__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__TEKSTEN = ELEMENT__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__LAAG = ELEMENT__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__SYNC_ID = ELEMENT__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__SOORT = ELEMENT__SOORT;

    /**
     * The feature id for the '<em><b>Identifier DB</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__IDENTIFIER_DB = ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Identifier Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__IDENTIFIER_CODE = ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Versie Tag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__VERSIE_TAG = ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__VOLGNUMMER = ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>In Set Of Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT__IN_SET_OF_MODEL = ELEMENT_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Gelaagd Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GELAAGD_ELEMENT_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.AttribuutImpl <em>Attribuut</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.AttribuutImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getAttribuut()
     * @generated
     */
    int ATTRIBUUT = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__ID = GELAAGD_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__NAAM = GELAAGD_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__BEDRIJFS_REGELS = GELAAGD_ELEMENT__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__BESCHRIJVING = GELAAGD_ELEMENT__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__TEKSTEN = GELAAGD_ELEMENT__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__LAAG = GELAAGD_ELEMENT__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__SYNC_ID = GELAAGD_ELEMENT__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__SOORT = GELAAGD_ELEMENT__SOORT;

    /**
     * The feature id for the '<em><b>Identifier DB</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__IDENTIFIER_DB = GELAAGD_ELEMENT__IDENTIFIER_DB;

    /**
     * The feature id for the '<em><b>Identifier Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__IDENTIFIER_CODE = GELAAGD_ELEMENT__IDENTIFIER_CODE;

    /**
     * The feature id for the '<em><b>Versie Tag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__VERSIE_TAG = GELAAGD_ELEMENT__VERSIE_TAG;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__VOLGNUMMER = GELAAGD_ELEMENT__VOLGNUMMER;

    /**
     * The feature id for the '<em><b>In Set Of Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__IN_SET_OF_MODEL = GELAAGD_ELEMENT__IN_SET_OF_MODEL;

    /**
     * The feature id for the '<em><b>Groep</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__GROEP = GELAAGD_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Object Type</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__OBJECT_TYPE = GELAAGD_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__TYPE = GELAAGD_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Afleidbaar</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__AFLEIDBAAR = GELAAGD_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Historie Vastleggen</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__HISTORIE_VASTLEGGEN = GELAAGD_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Verplicht</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__VERPLICHT = GELAAGD_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Inverse Associatie Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__INVERSE_ASSOCIATIE_NAAM = GELAAGD_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Inverse Associatie</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__INVERSE_ASSOCIATIE = GELAAGD_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Gebruikt In Bedrijfs Regels</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT__GEBRUIKT_IN_BEDRIJFS_REGELS = GELAAGD_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The number of structural features of the '<em>Attribuut</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_FEATURE_COUNT = GELAAGD_ELEMENT_FEATURE_COUNT + 9;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.TypeImpl <em>Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.TypeImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getType()
     * @generated
     */
    int TYPE = 16;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__ID = GELAAGD_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__NAAM = GELAAGD_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__BEDRIJFS_REGELS = GELAAGD_ELEMENT__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__BESCHRIJVING = GELAAGD_ELEMENT__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__TEKSTEN = GELAAGD_ELEMENT__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__LAAG = GELAAGD_ELEMENT__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__SYNC_ID = GELAAGD_ELEMENT__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__SOORT = GELAAGD_ELEMENT__SOORT;

    /**
     * The feature id for the '<em><b>Identifier DB</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__IDENTIFIER_DB = GELAAGD_ELEMENT__IDENTIFIER_DB;

    /**
     * The feature id for the '<em><b>Identifier Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__IDENTIFIER_CODE = GELAAGD_ELEMENT__IDENTIFIER_CODE;

    /**
     * The feature id for the '<em><b>Versie Tag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__VERSIE_TAG = GELAAGD_ELEMENT__VERSIE_TAG;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__VOLGNUMMER = GELAAGD_ELEMENT__VOLGNUMMER;

    /**
     * The feature id for the '<em><b>In Set Of Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE__IN_SET_OF_MODEL = GELAAGD_ELEMENT__IN_SET_OF_MODEL;

    /**
     * The number of structural features of the '<em>Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_FEATURE_COUNT = GELAAGD_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.AttribuutTypeImpl <em>Attribuut Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.AttribuutTypeImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getAttribuutType()
     * @generated
     */
    int ATTRIBUUT_TYPE = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__ID = TYPE__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__NAAM = TYPE__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__BEDRIJFS_REGELS = TYPE__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__BESCHRIJVING = TYPE__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__TEKSTEN = TYPE__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__LAAG = TYPE__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__SYNC_ID = TYPE__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__SOORT = TYPE__SOORT;

    /**
     * The feature id for the '<em><b>Identifier DB</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__IDENTIFIER_DB = TYPE__IDENTIFIER_DB;

    /**
     * The feature id for the '<em><b>Identifier Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__IDENTIFIER_CODE = TYPE__IDENTIFIER_CODE;

    /**
     * The feature id for the '<em><b>Versie Tag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__VERSIE_TAG = TYPE__VERSIE_TAG;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__VOLGNUMMER = TYPE__VOLGNUMMER;

    /**
     * The feature id for the '<em><b>In Set Of Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__IN_SET_OF_MODEL = TYPE__IN_SET_OF_MODEL;

    /**
     * The feature id for the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__TYPE = TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Versie</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__VERSIE = TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Minimum Lengte</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__MINIMUM_LENGTE = TYPE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Maximum Lengte</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__MAXIMUM_LENGTE = TYPE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Aantal Decimalen</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE__AANTAL_DECIMALEN = TYPE_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Attribuut Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUUT_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.BasisTypeImpl <em>Basis Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.BasisTypeImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getBasisType()
     * @generated
     */
    int BASIS_TYPE = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIS_TYPE__ID = ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIS_TYPE__NAAM = ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIS_TYPE__BEDRIJFS_REGELS = ELEMENT__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIS_TYPE__BESCHRIJVING = ELEMENT__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIS_TYPE__TEKSTEN = ELEMENT__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIS_TYPE__LAAG = ELEMENT__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIS_TYPE__SYNC_ID = ELEMENT__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIS_TYPE__SOORT = ELEMENT__SOORT;

    /**
     * The number of structural features of the '<em>Basis Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIS_TYPE_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.BedrijfsRegelImpl <em>Bedrijfs Regel</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.BedrijfsRegelImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getBedrijfsRegel()
     * @generated
     */
    int BEDRIJFS_REGEL = 3;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__ID = GELAAGD_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__NAAM = GELAAGD_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__BEDRIJFS_REGELS = GELAAGD_ELEMENT__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__BESCHRIJVING = GELAAGD_ELEMENT__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__TEKSTEN = GELAAGD_ELEMENT__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__LAAG = GELAAGD_ELEMENT__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__SYNC_ID = GELAAGD_ELEMENT__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__SOORT = GELAAGD_ELEMENT__SOORT;

    /**
     * The feature id for the '<em><b>Identifier DB</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__IDENTIFIER_DB = GELAAGD_ELEMENT__IDENTIFIER_DB;

    /**
     * The feature id for the '<em><b>Identifier Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__IDENTIFIER_CODE = GELAAGD_ELEMENT__IDENTIFIER_CODE;

    /**
     * The feature id for the '<em><b>Versie Tag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__VERSIE_TAG = GELAAGD_ELEMENT__VERSIE_TAG;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__VOLGNUMMER = GELAAGD_ELEMENT__VOLGNUMMER;

    /**
     * The feature id for the '<em><b>In Set Of Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__IN_SET_OF_MODEL = GELAAGD_ELEMENT__IN_SET_OF_MODEL;

    /**
     * The feature id for the '<em><b>Attributen</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__ATTRIBUTEN = GELAAGD_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Element</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__ELEMENT = GELAAGD_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Soort Bedrijfs Regel</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__SOORT_BEDRIJFS_REGEL = GELAAGD_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Waarden</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL__WAARDEN = GELAAGD_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Bedrijfs Regel</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BEDRIJFS_REGEL_FEATURE_COUNT = GELAAGD_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.BerichtSjabloonImpl <em>Bericht Sjabloon</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.BerichtSjabloonImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getBerichtSjabloon()
     * @generated
     */
    int BERICHT_SJABLOON = 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__ID = GELAAGD_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__NAAM = GELAAGD_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__BEDRIJFS_REGELS = GELAAGD_ELEMENT__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__BESCHRIJVING = GELAAGD_ELEMENT__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__TEKSTEN = GELAAGD_ELEMENT__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__LAAG = GELAAGD_ELEMENT__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__SYNC_ID = GELAAGD_ELEMENT__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__SOORT = GELAAGD_ELEMENT__SOORT;

    /**
     * The feature id for the '<em><b>Identifier DB</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__IDENTIFIER_DB = GELAAGD_ELEMENT__IDENTIFIER_DB;

    /**
     * The feature id for the '<em><b>Identifier Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__IDENTIFIER_CODE = GELAAGD_ELEMENT__IDENTIFIER_CODE;

    /**
     * The feature id for the '<em><b>Versie Tag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__VERSIE_TAG = GELAAGD_ELEMENT__VERSIE_TAG;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__VOLGNUMMER = GELAAGD_ELEMENT__VOLGNUMMER;

    /**
     * The feature id for the '<em><b>In Set Of Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__IN_SET_OF_MODEL = GELAAGD_ELEMENT__IN_SET_OF_MODEL;

    /**
     * The feature id for the '<em><b>Versie</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON__VERSIE = GELAAGD_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Bericht Sjabloon</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BERICHT_SJABLOON_FEATURE_COUNT = GELAAGD_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.DomeinImpl <em>Domein</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.DomeinImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getDomein()
     * @generated
     */
    int DOMEIN = 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOMEIN__ID = ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOMEIN__NAAM = ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOMEIN__BEDRIJFS_REGELS = ELEMENT__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOMEIN__BESCHRIJVING = ELEMENT__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOMEIN__TEKSTEN = ELEMENT__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOMEIN__LAAG = ELEMENT__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOMEIN__SYNC_ID = ELEMENT__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOMEIN__SOORT = ELEMENT__SOORT;

    /**
     * The feature id for the '<em><b>Schemas</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOMEIN__SCHEMAS = ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Domein</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOMEIN_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.GroepImpl <em>Groep</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.GroepImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getGroep()
     * @generated
     */
    int GROEP = 8;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__ID = GELAAGD_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__NAAM = GELAAGD_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__BEDRIJFS_REGELS = GELAAGD_ELEMENT__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__BESCHRIJVING = GELAAGD_ELEMENT__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__TEKSTEN = GELAAGD_ELEMENT__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__LAAG = GELAAGD_ELEMENT__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__SYNC_ID = GELAAGD_ELEMENT__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__SOORT = GELAAGD_ELEMENT__SOORT;

    /**
     * The feature id for the '<em><b>Identifier DB</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__IDENTIFIER_DB = GELAAGD_ELEMENT__IDENTIFIER_DB;

    /**
     * The feature id for the '<em><b>Identifier Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__IDENTIFIER_CODE = GELAAGD_ELEMENT__IDENTIFIER_CODE;

    /**
     * The feature id for the '<em><b>Versie Tag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__VERSIE_TAG = GELAAGD_ELEMENT__VERSIE_TAG;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__VOLGNUMMER = GELAAGD_ELEMENT__VOLGNUMMER;

    /**
     * The feature id for the '<em><b>In Set Of Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__IN_SET_OF_MODEL = GELAAGD_ELEMENT__IN_SET_OF_MODEL;

    /**
     * The feature id for the '<em><b>Object Type</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__OBJECT_TYPE = GELAAGD_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Attributen</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__ATTRIBUTEN = GELAAGD_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Historie Vastleggen</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__HISTORIE_VASTLEGGEN = GELAAGD_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Verplicht</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP__VERPLICHT = GELAAGD_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Groep</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROEP_FEATURE_COUNT = GELAAGD_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.LaagImpl <em>Laag</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.LaagImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getLaag()
     * @generated
     */
    int LAAG = 9;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LAAG__ID = ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LAAG__NAAM = ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LAAG__BEDRIJFS_REGELS = ELEMENT__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LAAG__BESCHRIJVING = ELEMENT__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LAAG__TEKSTEN = ELEMENT__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LAAG__LAAG = ELEMENT__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LAAG__SYNC_ID = ELEMENT__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LAAG__SOORT = ELEMENT__SOORT;

    /**
     * The number of structural features of the '<em>Laag</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LAAG_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.MetaRegisterImpl <em>Meta Register</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.MetaRegisterImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getMetaRegister()
     * @generated
     */
    int META_REGISTER = 10;

    /**
     * The feature id for the '<em><b>Domeinen</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int META_REGISTER__DOMEINEN = 0;

    /**
     * The feature id for the '<em><b>Basis Typen</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int META_REGISTER__BASIS_TYPEN = 1;

    /**
     * The feature id for the '<em><b>Applicaties</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int META_REGISTER__APPLICATIES = 2;

    /**
     * The number of structural features of the '<em>Meta Register</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int META_REGISTER_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl <em>Object Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getObjectType()
     * @generated
     */
    int OBJECT_TYPE = 12;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__ID = TYPE__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__NAAM = TYPE__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__BEDRIJFS_REGELS = TYPE__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__BESCHRIJVING = TYPE__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__TEKSTEN = TYPE__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__LAAG = TYPE__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__SYNC_ID = TYPE__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__SOORT = TYPE__SOORT;

    /**
     * The feature id for the '<em><b>Identifier DB</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__IDENTIFIER_DB = TYPE__IDENTIFIER_DB;

    /**
     * The feature id for the '<em><b>Identifier Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__IDENTIFIER_CODE = TYPE__IDENTIFIER_CODE;

    /**
     * The feature id for the '<em><b>Versie Tag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__VERSIE_TAG = TYPE__VERSIE_TAG;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__VOLGNUMMER = TYPE__VOLGNUMMER;

    /**
     * The feature id for the '<em><b>In Set Of Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__IN_SET_OF_MODEL = TYPE__IN_SET_OF_MODEL;

    /**
     * The feature id for the '<em><b>Attributen</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__ATTRIBUTEN = TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Groepen</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__GROEPEN = TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Meervouds Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__MEERVOUDS_NAAM = TYPE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Soort Inhoud</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__SOORT_INHOUD = TYPE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Sub Types</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__SUB_TYPES = TYPE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Super Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__SUPER_TYPE = TYPE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Tuples</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__TUPLES = TYPE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Versie</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__VERSIE = TYPE_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Afleidbaar</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__AFLEIDBAAR = TYPE_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Historie Vastleggen</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__HISTORIE_VASTLEGGEN = TYPE_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Kunstmatige Sleutel</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__KUNSTMATIGE_SLEUTEL = TYPE_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Lookup</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE__LOOKUP = TYPE_FEATURE_COUNT + 11;

    /**
     * The number of structural features of the '<em>Object Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 12;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.SchemaImpl <em>Schema</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.SchemaImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getSchema()
     * @generated
     */
    int SCHEMA = 13;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA__ID = ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA__NAAM = ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA__BEDRIJFS_REGELS = ELEMENT__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA__BESCHRIJVING = ELEMENT__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA__TEKSTEN = ELEMENT__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA__LAAG = ELEMENT__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA__SYNC_ID = ELEMENT__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA__SOORT = ELEMENT__SOORT;

    /**
     * The feature id for the '<em><b>Versies</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA__VERSIES = ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Domein</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA__DOMEIN = ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Schema</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.TekstImpl <em>Tekst</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.TekstImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getTekst()
     * @generated
     */
    int TEKST = 14;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TEKST__ID = 0;

    /**
     * The feature id for the '<em><b>Element</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TEKST__ELEMENT = 1;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TEKST__SOORT = 2;

    /**
     * The feature id for the '<em><b>Tekst</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TEKST__TEKST = 3;

    /**
     * The feature id for the '<em><b>Html Tekst</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TEKST__HTML_TEKST = 4;

    /**
     * The number of structural features of the '<em>Tekst</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TEKST_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl <em>Tuple</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.TupleImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getTuple()
     * @generated
     */
    int TUPLE = 15;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__ID = 0;

    /**
     * The feature id for the '<em><b>Object Type</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__OBJECT_TYPE = 1;

    /**
     * The feature id for the '<em><b>Relatief Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__RELATIEF_ID = 2;

    /**
     * The feature id for the '<em><b>Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__CODE = 3;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__NAAM = 4;

    /**
     * The feature id for the '<em><b>Naam Mannelijk</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__NAAM_MANNELIJK = 5;

    /**
     * The feature id for the '<em><b>Naam Vrouwelijk</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__NAAM_VROUWELIJK = 6;

    /**
     * The feature id for the '<em><b>Omschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__OMSCHRIJVING = 7;

    /**
     * The feature id for the '<em><b>Heeft Materiele Historie</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__HEEFT_MATERIELE_HISTORIE = 8;

    /**
     * The feature id for the '<em><b>Datum Aanvang Geldigheid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__DATUM_AANVANG_GELDIGHEID = 9;

    /**
     * The feature id for the '<em><b>Datum Einde Geldigheid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__DATUM_EINDE_GELDIGHEID = 10;

    /**
     * The feature id for the '<em><b>Categorie Soort Actie</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__CATEGORIE_SOORT_ACTIE = 11;

    /**
     * The feature id for the '<em><b>Categorie Soort Document</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE__CATEGORIE_SOORT_DOCUMENT = 12;

    /**
     * The number of structural features of the '<em>Tuple</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TUPLE_FEATURE_COUNT = 13;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.VersieImpl <em>Versie</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.VersieImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getVersie()
     * @generated
     */
    int VERSIE = 17;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__ID = ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__NAAM = ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__BEDRIJFS_REGELS = ELEMENT__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__BESCHRIJVING = ELEMENT__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__TEKSTEN = ELEMENT__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__LAAG = ELEMENT__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__SYNC_ID = ELEMENT__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__SOORT = ELEMENT__SOORT;

    /**
     * The feature id for the '<em><b>Schema</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__SCHEMA = ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Versie Tag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__VERSIE_TAG = ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Object Types</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__OBJECT_TYPES = ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Attribuut Types</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__ATTRIBUUT_TYPES = ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Bericht Sjablonen</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE__BERICHT_SJABLONEN = ELEMENT_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Versie</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERSIE_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.WaarderegelWaardeImpl <em>Waarderegel Waarde</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.WaarderegelWaardeImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getWaarderegelWaarde()
     * @generated
     */
    int WAARDEREGEL_WAARDE = 18;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__ID = GELAAGD_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__NAAM = GELAAGD_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bedrijfs Regels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__BEDRIJFS_REGELS = GELAAGD_ELEMENT__BEDRIJFS_REGELS;

    /**
     * The feature id for the '<em><b>Beschrijving</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__BESCHRIJVING = GELAAGD_ELEMENT__BESCHRIJVING;

    /**
     * The feature id for the '<em><b>Teksten</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__TEKSTEN = GELAAGD_ELEMENT__TEKSTEN;

    /**
     * The feature id for the '<em><b>Laag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__LAAG = GELAAGD_ELEMENT__LAAG;

    /**
     * The feature id for the '<em><b>Sync Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__SYNC_ID = GELAAGD_ELEMENT__SYNC_ID;

    /**
     * The feature id for the '<em><b>Soort</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__SOORT = GELAAGD_ELEMENT__SOORT;

    /**
     * The feature id for the '<em><b>Identifier DB</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__IDENTIFIER_DB = GELAAGD_ELEMENT__IDENTIFIER_DB;

    /**
     * The feature id for the '<em><b>Identifier Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__IDENTIFIER_CODE = GELAAGD_ELEMENT__IDENTIFIER_CODE;

    /**
     * The feature id for the '<em><b>Versie Tag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__VERSIE_TAG = GELAAGD_ELEMENT__VERSIE_TAG;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__VOLGNUMMER = GELAAGD_ELEMENT__VOLGNUMMER;

    /**
     * The feature id for the '<em><b>In Set Of Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__IN_SET_OF_MODEL = GELAAGD_ELEMENT__IN_SET_OF_MODEL;

    /**
     * The feature id for the '<em><b>Bedrijfs Regel</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__BEDRIJFS_REGEL = GELAAGD_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Waarde</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__WAARDE = GELAAGD_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Weergave</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE__WEERGAVE = GELAAGD_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Waarderegel Waarde</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAARDEREGEL_WAARDE_FEATURE_COUNT = GELAAGD_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.SoortTekstToTekstMapEntryImpl <em>Soort Tekst To Tekst Map Entry</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.SoortTekstToTekstMapEntryImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getSoortTekstToTekstMapEntry()
     * @generated
     */
    int SOORT_TEKST_TO_TEKST_MAP_ENTRY = 19;

    /**
     * The feature id for the '<em><b>Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SOORT_TEKST_TO_TEKST_MAP_ENTRY__KEY = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SOORT_TEKST_TO_TEKST_MAP_ENTRY__VALUE = 1;

    /**
     * The number of structural features of the '<em>Soort Tekst To Tekst Map Entry</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SOORT_TEKST_TO_TEKST_MAP_ENTRY_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.ApplicatieImpl <em>Applicatie</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.ApplicatieImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getApplicatie()
     * @generated
     */
    int APPLICATIE = 20;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATIE__ID = MODEL_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATIE__NAAM = MODEL_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Formulieren</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATIE__FORMULIEREN = MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Applicatie</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATIE_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.FormulierImpl <em>Formulier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.FormulierImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getFormulier()
     * @generated
     */
    int FORMULIER = 21;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMULIER__ID = MODEL_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMULIER__NAAM = MODEL_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Applicatie</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMULIER__APPLICATIE = MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Frames</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMULIER__FRAMES = MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bronnen</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMULIER__BRONNEN = MODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Formulier</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMULIER_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.FrameImpl <em>Frame</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.FrameImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getFrame()
     * @generated
     */
    int FRAME = 22;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME__ID = MODEL_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME__NAAM = MODEL_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME__VOLGNUMMER = MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Formulier</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME__FORMULIER = MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bron</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME__BRON = MODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Velden</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME__VELDEN = MODEL_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Frame</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.FrameVeldImpl <em>Frame Veld</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.FrameVeldImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getFrameVeld()
     * @generated
     */
    int FRAME_VELD = 23;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME_VELD__ID = MODEL_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME_VELD__NAAM = MODEL_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME_VELD__VOLGNUMMER = MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Frame</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME_VELD__FRAME = MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Attribuut</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME_VELD__ATTRIBUUT = MODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Frame Veld</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FRAME_VELD_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.BronImpl <em>Bron</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.BronImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getBron()
     * @generated
     */
    int BRON = 24;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON__ID = MODEL_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON__NAAM = MODEL_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Meervoudsvorm</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON__MEERVOUDSVORM = MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Identifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON__IDENTIFIER = MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON__VOLGNUMMER = MODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Formulier</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON__FORMULIER = MODEL_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Frames</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON__FRAMES = MODEL_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Object Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON__OBJECT_TYPE = MODEL_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Link</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON__LINK = MODEL_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Bron Attributen</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON__BRON_ATTRIBUTEN = MODEL_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Bron</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.impl.BronAttribuutImpl <em>Bron Attribuut</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.impl.BronAttribuutImpl
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getBronAttribuut()
     * @generated
     */
    int BRON_ATTRIBUUT = 25;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON_ATTRIBUUT__ID = MODEL_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Naam</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON_ATTRIBUUT__NAAM = MODEL_ELEMENT__NAAM;

    /**
     * The feature id for the '<em><b>Bron</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON_ATTRIBUUT__BRON = MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Attribuut</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON_ATTRIBUUT__ATTRIBUUT = MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Volgnummer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON_ATTRIBUUT__VOLGNUMMER = MODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Bron Attribuut</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BRON_ATTRIBUUT_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.ElementSoort <em>Element Soort</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.ElementSoort
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getElementSoort()
     * @generated
     */
    int ELEMENT_SOORT = 26;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel <em>Soort Bedrijfs Regel</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getSoortBedrijfsRegel()
     * @generated
     */
    int SOORT_BEDRIJFS_REGEL = 27;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.SoortInhoud <em>Soort Inhoud</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.SoortInhoud
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getSoortInhoud()
     * @generated
     */
    int SOORT_INHOUD = 28;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.SoortTekst <em>Soort Tekst</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.SoortTekst
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getSoortTekst()
     * @generated
     */
    int SOORT_TEKST = 29;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.VersieTag <em>Versie Tag</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.VersieTag
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getVersieTag()
     * @generated
     */
    int VERSIE_TAG = 30;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.InSetOfModel <em>In Set Of Model</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.InSetOfModel
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getInSetOfModel()
     * @generated
     */
    int IN_SET_OF_MODEL = 31;

    /**
     * The meta object id for the '{@link nl.bzk.brp.ecore.bmr.Historie <em>Historie</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see nl.bzk.brp.ecore.bmr.Historie
     * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getHistorie()
     * @generated
     */
    int HISTORIE = 32;


    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Attribuut <em>Attribuut</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Attribuut</em>'.
     * @see nl.bzk.brp.ecore.bmr.Attribuut
     * @generated
     */
    EClass getAttribuut();

    /**
     * Returns the meta object for the reference '{@link nl.bzk.brp.ecore.bmr.Attribuut#getGroep <em>Groep</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Groep</em>'.
     * @see nl.bzk.brp.ecore.bmr.Attribuut#getGroep()
     * @see #getAttribuut()
     * @generated
     */
    EReference getAttribuut_Groep();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.Attribuut#getObjectType <em>Object Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Object Type</em>'.
     * @see nl.bzk.brp.ecore.bmr.Attribuut#getObjectType()
     * @see #getAttribuut()
     * @generated
     */
    EReference getAttribuut_ObjectType();

    /**
     * Returns the meta object for the reference '{@link nl.bzk.brp.ecore.bmr.Attribuut#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Type</em>'.
     * @see nl.bzk.brp.ecore.bmr.Attribuut#getType()
     * @see #getAttribuut()
     * @generated
     */
    EReference getAttribuut_Type();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Attribuut#getAfleidbaar <em>Afleidbaar</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Afleidbaar</em>'.
     * @see nl.bzk.brp.ecore.bmr.Attribuut#getAfleidbaar()
     * @see #getAttribuut()
     * @generated
     */
    EAttribute getAttribuut_Afleidbaar();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Attribuut#getHistorieVastleggen <em>Historie Vastleggen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Historie Vastleggen</em>'.
     * @see nl.bzk.brp.ecore.bmr.Attribuut#getHistorieVastleggen()
     * @see #getAttribuut()
     * @generated
     */
    EAttribute getAttribuut_HistorieVastleggen();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Attribuut#isVerplicht <em>Verplicht</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Verplicht</em>'.
     * @see nl.bzk.brp.ecore.bmr.Attribuut#isVerplicht()
     * @see #getAttribuut()
     * @generated
     */
    EAttribute getAttribuut_Verplicht();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Attribuut#getInverseAssociatieNaam <em>Inverse Associatie Naam</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Inverse Associatie Naam</em>'.
     * @see nl.bzk.brp.ecore.bmr.Attribuut#getInverseAssociatieNaam()
     * @see #getAttribuut()
     * @generated
     */
    EAttribute getAttribuut_InverseAssociatieNaam();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Attribuut#getInverseAssociatie <em>Inverse Associatie</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Inverse Associatie</em>'.
     * @see nl.bzk.brp.ecore.bmr.Attribuut#getInverseAssociatie()
     * @see #getAttribuut()
     * @generated
     */
    EAttribute getAttribuut_InverseAssociatie();

    /**
     * Returns the meta object for the reference list '{@link nl.bzk.brp.ecore.bmr.Attribuut#getGebruiktInBedrijfsRegels <em>Gebruikt In Bedrijfs Regels</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Gebruikt In Bedrijfs Regels</em>'.
     * @see nl.bzk.brp.ecore.bmr.Attribuut#getGebruiktInBedrijfsRegels()
     * @see #getAttribuut()
     * @generated
     */
    EReference getAttribuut_GebruiktInBedrijfsRegels();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.AttribuutType <em>Attribuut Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Attribuut Type</em>'.
     * @see nl.bzk.brp.ecore.bmr.AttribuutType
     * @generated
     */
    EClass getAttribuutType();

    /**
     * Returns the meta object for the reference '{@link nl.bzk.brp.ecore.bmr.AttribuutType#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Type</em>'.
     * @see nl.bzk.brp.ecore.bmr.AttribuutType#getType()
     * @see #getAttribuutType()
     * @generated
     */
    EReference getAttribuutType_Type();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.AttribuutType#getVersie <em>Versie</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Versie</em>'.
     * @see nl.bzk.brp.ecore.bmr.AttribuutType#getVersie()
     * @see #getAttribuutType()
     * @generated
     */
    EReference getAttribuutType_Versie();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.AttribuutType#getMinimumLengte <em>Minimum Lengte</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Minimum Lengte</em>'.
     * @see nl.bzk.brp.ecore.bmr.AttribuutType#getMinimumLengte()
     * @see #getAttribuutType()
     * @generated
     */
    EAttribute getAttribuutType_MinimumLengte();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.AttribuutType#getMaximumLengte <em>Maximum Lengte</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Maximum Lengte</em>'.
     * @see nl.bzk.brp.ecore.bmr.AttribuutType#getMaximumLengte()
     * @see #getAttribuutType()
     * @generated
     */
    EAttribute getAttribuutType_MaximumLengte();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.AttribuutType#getAantalDecimalen <em>Aantal Decimalen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Aantal Decimalen</em>'.
     * @see nl.bzk.brp.ecore.bmr.AttribuutType#getAantalDecimalen()
     * @see #getAttribuutType()
     * @generated
     */
    EAttribute getAttribuutType_AantalDecimalen();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.BasisType <em>Basis Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Basis Type</em>'.
     * @see nl.bzk.brp.ecore.bmr.BasisType
     * @generated
     */
    EClass getBasisType();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel <em>Bedrijfs Regel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Bedrijfs Regel</em>'.
     * @see nl.bzk.brp.ecore.bmr.BedrijfsRegel
     * @generated
     */
    EClass getBedrijfsRegel();

    /**
     * Returns the meta object for the reference list '{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getAttributen <em>Attributen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Attributen</em>'.
     * @see nl.bzk.brp.ecore.bmr.BedrijfsRegel#getAttributen()
     * @see #getBedrijfsRegel()
     * @generated
     */
    EReference getBedrijfsRegel_Attributen();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getElement <em>Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Element</em>'.
     * @see nl.bzk.brp.ecore.bmr.BedrijfsRegel#getElement()
     * @see #getBedrijfsRegel()
     * @generated
     */
    EReference getBedrijfsRegel_Element();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getSoortBedrijfsRegel <em>Soort Bedrijfs Regel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Soort Bedrijfs Regel</em>'.
     * @see nl.bzk.brp.ecore.bmr.BedrijfsRegel#getSoortBedrijfsRegel()
     * @see #getBedrijfsRegel()
     * @generated
     */
    EAttribute getBedrijfsRegel_SoortBedrijfsRegel();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.BedrijfsRegel#getWaarden <em>Waarden</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Waarden</em>'.
     * @see nl.bzk.brp.ecore.bmr.BedrijfsRegel#getWaarden()
     * @see #getBedrijfsRegel()
     * @generated
     */
    EReference getBedrijfsRegel_Waarden();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.BerichtSjabloon <em>Bericht Sjabloon</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Bericht Sjabloon</em>'.
     * @see nl.bzk.brp.ecore.bmr.BerichtSjabloon
     * @generated
     */
    EClass getBerichtSjabloon();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.BerichtSjabloon#getVersie <em>Versie</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Versie</em>'.
     * @see nl.bzk.brp.ecore.bmr.BerichtSjabloon#getVersie()
     * @see #getBerichtSjabloon()
     * @generated
     */
    EReference getBerichtSjabloon_Versie();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Domein <em>Domein</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Domein</em>'.
     * @see nl.bzk.brp.ecore.bmr.Domein
     * @generated
     */
    EClass getDomein();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.Domein#getSchemas <em>Schemas</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Schemas</em>'.
     * @see nl.bzk.brp.ecore.bmr.Domein#getSchemas()
     * @see #getDomein()
     * @generated
     */
    EReference getDomein_Schemas();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Element <em>Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Element</em>'.
     * @see nl.bzk.brp.ecore.bmr.Element
     * @generated
     */
    EClass getElement();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.Element#getBedrijfsRegels <em>Bedrijfs Regels</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Bedrijfs Regels</em>'.
     * @see nl.bzk.brp.ecore.bmr.Element#getBedrijfsRegels()
     * @see #getElement()
     * @generated
     */
    EReference getElement_BedrijfsRegels();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Element#getBeschrijving <em>Beschrijving</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Beschrijving</em>'.
     * @see nl.bzk.brp.ecore.bmr.Element#getBeschrijving()
     * @see #getElement()
     * @generated
     */
    EAttribute getElement_Beschrijving();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.Element#getTeksten <em>Teksten</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Teksten</em>'.
     * @see nl.bzk.brp.ecore.bmr.Element#getTeksten()
     * @see #getElement()
     * @generated
     */
    EReference getElement_Teksten();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Element#getLaag <em>Laag</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Laag</em>'.
     * @see nl.bzk.brp.ecore.bmr.Element#getLaag()
     * @see #getElement()
     * @generated
     */
    EAttribute getElement_Laag();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Element#getSyncId <em>Sync Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Sync Id</em>'.
     * @see nl.bzk.brp.ecore.bmr.Element#getSyncId()
     * @see #getElement()
     * @generated
     */
    EAttribute getElement_SyncId();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Element#getSoort <em>Soort</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Soort</em>'.
     * @see nl.bzk.brp.ecore.bmr.Element#getSoort()
     * @see #getElement()
     * @generated
     */
    EAttribute getElement_Soort();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.GelaagdElement <em>Gelaagd Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Gelaagd Element</em>'.
     * @see nl.bzk.brp.ecore.bmr.GelaagdElement
     * @generated
     */
    EClass getGelaagdElement();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getIdentifierDB <em>Identifier DB</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Identifier DB</em>'.
     * @see nl.bzk.brp.ecore.bmr.GelaagdElement#getIdentifierDB()
     * @see #getGelaagdElement()
     * @generated
     */
    EAttribute getGelaagdElement_IdentifierDB();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getIdentifierCode <em>Identifier Code</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Identifier Code</em>'.
     * @see nl.bzk.brp.ecore.bmr.GelaagdElement#getIdentifierCode()
     * @see #getGelaagdElement()
     * @generated
     */
    EAttribute getGelaagdElement_IdentifierCode();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getVersieTag <em>Versie Tag</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Versie Tag</em>'.
     * @see nl.bzk.brp.ecore.bmr.GelaagdElement#getVersieTag()
     * @see #getGelaagdElement()
     * @generated
     */
    EAttribute getGelaagdElement_VersieTag();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getVolgnummer <em>Volgnummer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Volgnummer</em>'.
     * @see nl.bzk.brp.ecore.bmr.GelaagdElement#getVolgnummer()
     * @see #getGelaagdElement()
     * @generated
     */
    EAttribute getGelaagdElement_Volgnummer();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.GelaagdElement#getInSetOfModel <em>In Set Of Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>In Set Of Model</em>'.
     * @see nl.bzk.brp.ecore.bmr.GelaagdElement#getInSetOfModel()
     * @see #getGelaagdElement()
     * @generated
     */
    EAttribute getGelaagdElement_InSetOfModel();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Groep <em>Groep</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Groep</em>'.
     * @see nl.bzk.brp.ecore.bmr.Groep
     * @generated
     */
    EClass getGroep();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.Groep#getObjectType <em>Object Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Object Type</em>'.
     * @see nl.bzk.brp.ecore.bmr.Groep#getObjectType()
     * @see #getGroep()
     * @generated
     */
    EReference getGroep_ObjectType();

    /**
     * Returns the meta object for the reference list '{@link nl.bzk.brp.ecore.bmr.Groep#getAttributen <em>Attributen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Attributen</em>'.
     * @see nl.bzk.brp.ecore.bmr.Groep#getAttributen()
     * @see #getGroep()
     * @generated
     */
    EReference getGroep_Attributen();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Groep#getHistorieVastleggen <em>Historie Vastleggen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Historie Vastleggen</em>'.
     * @see nl.bzk.brp.ecore.bmr.Groep#getHistorieVastleggen()
     * @see #getGroep()
     * @generated
     */
    EAttribute getGroep_HistorieVastleggen();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Groep#isVerplicht <em>Verplicht</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Verplicht</em>'.
     * @see nl.bzk.brp.ecore.bmr.Groep#isVerplicht()
     * @see #getGroep()
     * @generated
     */
    EAttribute getGroep_Verplicht();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Laag <em>Laag</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Laag</em>'.
     * @see nl.bzk.brp.ecore.bmr.Laag
     * @generated
     */
    EClass getLaag();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.MetaRegister <em>Meta Register</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Meta Register</em>'.
     * @see nl.bzk.brp.ecore.bmr.MetaRegister
     * @generated
     */
    EClass getMetaRegister();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.MetaRegister#getDomeinen <em>Domeinen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Domeinen</em>'.
     * @see nl.bzk.brp.ecore.bmr.MetaRegister#getDomeinen()
     * @see #getMetaRegister()
     * @generated
     */
    EReference getMetaRegister_Domeinen();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.MetaRegister#getBasisTypen <em>Basis Typen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Basis Typen</em>'.
     * @see nl.bzk.brp.ecore.bmr.MetaRegister#getBasisTypen()
     * @see #getMetaRegister()
     * @generated
     */
    EReference getMetaRegister_BasisTypen();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.MetaRegister#getApplicaties <em>Applicaties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Applicaties</em>'.
     * @see nl.bzk.brp.ecore.bmr.MetaRegister#getApplicaties()
     * @see #getMetaRegister()
     * @generated
     */
    EReference getMetaRegister_Applicaties();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.ModelElement <em>Model Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Model Element</em>'.
     * @see nl.bzk.brp.ecore.bmr.ModelElement
     * @generated
     */
    EClass getModelElement();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.ModelElement#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see nl.bzk.brp.ecore.bmr.ModelElement#getId()
     * @see #getModelElement()
     * @generated
     */
    EAttribute getModelElement_Id();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.ModelElement#getNaam <em>Naam</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Naam</em>'.
     * @see nl.bzk.brp.ecore.bmr.ModelElement#getNaam()
     * @see #getModelElement()
     * @generated
     */
    EAttribute getModelElement_Naam();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.ObjectType <em>Object Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Object Type</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType
     * @generated
     */
    EClass getObjectType();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.ObjectType#getAttributen <em>Attributen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Attributen</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getAttributen()
     * @see #getObjectType()
     * @generated
     */
    EReference getObjectType_Attributen();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.ObjectType#getGroepen <em>Groepen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Groepen</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getGroepen()
     * @see #getObjectType()
     * @generated
     */
    EReference getObjectType_Groepen();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.ObjectType#getMeervoudsNaam <em>Meervouds Naam</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Meervouds Naam</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getMeervoudsNaam()
     * @see #getObjectType()
     * @generated
     */
    EAttribute getObjectType_MeervoudsNaam();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.ObjectType#getSoortInhoud <em>Soort Inhoud</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Soort Inhoud</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getSoortInhoud()
     * @see #getObjectType()
     * @generated
     */
    EAttribute getObjectType_SoortInhoud();

    /**
     * Returns the meta object for the reference list '{@link nl.bzk.brp.ecore.bmr.ObjectType#getSubTypes <em>Sub Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Sub Types</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getSubTypes()
     * @see #getObjectType()
     * @generated
     */
    EReference getObjectType_SubTypes();

    /**
     * Returns the meta object for the reference '{@link nl.bzk.brp.ecore.bmr.ObjectType#getSuperType <em>Super Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Super Type</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getSuperType()
     * @see #getObjectType()
     * @generated
     */
    EReference getObjectType_SuperType();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.ObjectType#getTuples <em>Tuples</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Tuples</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getTuples()
     * @see #getObjectType()
     * @generated
     */
    EReference getObjectType_Tuples();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.ObjectType#getVersie <em>Versie</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Versie</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getVersie()
     * @see #getObjectType()
     * @generated
     */
    EReference getObjectType_Versie();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.ObjectType#getAfleidbaar <em>Afleidbaar</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Afleidbaar</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getAfleidbaar()
     * @see #getObjectType()
     * @generated
     */
    EAttribute getObjectType_Afleidbaar();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.ObjectType#getHistorieVastleggen <em>Historie Vastleggen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Historie Vastleggen</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getHistorieVastleggen()
     * @see #getObjectType()
     * @generated
     */
    EAttribute getObjectType_HistorieVastleggen();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.ObjectType#getKunstmatigeSleutel <em>Kunstmatige Sleutel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Kunstmatige Sleutel</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getKunstmatigeSleutel()
     * @see #getObjectType()
     * @generated
     */
    EAttribute getObjectType_KunstmatigeSleutel();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.ObjectType#getLookup <em>Lookup</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Lookup</em>'.
     * @see nl.bzk.brp.ecore.bmr.ObjectType#getLookup()
     * @see #getObjectType()
     * @generated
     */
    EAttribute getObjectType_Lookup();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Schema <em>Schema</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Schema</em>'.
     * @see nl.bzk.brp.ecore.bmr.Schema
     * @generated
     */
    EClass getSchema();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.Schema#getVersies <em>Versies</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Versies</em>'.
     * @see nl.bzk.brp.ecore.bmr.Schema#getVersies()
     * @see #getSchema()
     * @generated
     */
    EReference getSchema_Versies();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.Schema#getDomein <em>Domein</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Domein</em>'.
     * @see nl.bzk.brp.ecore.bmr.Schema#getDomein()
     * @see #getSchema()
     * @generated
     */
    EReference getSchema_Domein();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Tekst <em>Tekst</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Tekst</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tekst
     * @generated
     */
    EClass getTekst();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tekst#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tekst#getId()
     * @see #getTekst()
     * @generated
     */
    EAttribute getTekst_Id();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.Tekst#getElement <em>Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Element</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tekst#getElement()
     * @see #getTekst()
     * @generated
     */
    EReference getTekst_Element();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tekst#getSoort <em>Soort</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Soort</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tekst#getSoort()
     * @see #getTekst()
     * @generated
     */
    EAttribute getTekst_Soort();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tekst#getTekst <em>Tekst</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Tekst</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tekst#getTekst()
     * @see #getTekst()
     * @generated
     */
    EAttribute getTekst_Tekst();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tekst#getHtmlTekst <em>Html Tekst</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Html Tekst</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tekst#getHtmlTekst()
     * @see #getTekst()
     * @generated
     */
    EAttribute getTekst_HtmlTekst();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Tuple <em>Tuple</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Tuple</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple
     * @generated
     */
    EClass getTuple();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tuple#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getId()
     * @see #getTuple()
     * @generated
     */
    EAttribute getTuple_Id();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.Tuple#getObjectType <em>Object Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Object Type</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getObjectType()
     * @see #getTuple()
     * @generated
     */
    EReference getTuple_ObjectType();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tuple#getRelatiefId <em>Relatief Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Relatief Id</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getRelatiefId()
     * @see #getTuple()
     * @generated
     */
    EAttribute getTuple_RelatiefId();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tuple#getCode <em>Code</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Code</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getCode()
     * @see #getTuple()
     * @generated
     */
    EAttribute getTuple_Code();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tuple#getNaam <em>Naam</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Naam</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getNaam()
     * @see #getTuple()
     * @generated
     */
    EAttribute getTuple_Naam();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tuple#getNaamMannelijk <em>Naam Mannelijk</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Naam Mannelijk</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getNaamMannelijk()
     * @see #getTuple()
     * @generated
     */
    EAttribute getTuple_NaamMannelijk();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tuple#getNaamVrouwelijk <em>Naam Vrouwelijk</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Naam Vrouwelijk</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getNaamVrouwelijk()
     * @see #getTuple()
     * @generated
     */
    EAttribute getTuple_NaamVrouwelijk();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tuple#getOmschrijving <em>Omschrijving</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Omschrijving</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getOmschrijving()
     * @see #getTuple()
     * @generated
     */
    EAttribute getTuple_Omschrijving();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tuple#getHeeftMaterieleHistorie <em>Heeft Materiele Historie</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Heeft Materiele Historie</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getHeeftMaterieleHistorie()
     * @see #getTuple()
     * @generated
     */
    EAttribute getTuple_HeeftMaterieleHistorie();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tuple#getDatumAanvangGeldigheid <em>Datum Aanvang Geldigheid</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Datum Aanvang Geldigheid</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getDatumAanvangGeldigheid()
     * @see #getTuple()
     * @generated
     */
    EAttribute getTuple_DatumAanvangGeldigheid();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tuple#getDatumEindeGeldigheid <em>Datum Einde Geldigheid</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Datum Einde Geldigheid</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getDatumEindeGeldigheid()
     * @see #getTuple()
     * @generated
     */
    EAttribute getTuple_DatumEindeGeldigheid();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tuple#getCategorieSoortActie <em>Categorie Soort Actie</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Categorie Soort Actie</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getCategorieSoortActie()
     * @see #getTuple()
     * @generated
     */
    EAttribute getTuple_CategorieSoortActie();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Tuple#getCategorieSoortDocument <em>Categorie Soort Document</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Categorie Soort Document</em>'.
     * @see nl.bzk.brp.ecore.bmr.Tuple#getCategorieSoortDocument()
     * @see #getTuple()
     * @generated
     */
    EAttribute getTuple_CategorieSoortDocument();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Type <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Type</em>'.
     * @see nl.bzk.brp.ecore.bmr.Type
     * @generated
     */
    EClass getType();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Versie <em>Versie</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Versie</em>'.
     * @see nl.bzk.brp.ecore.bmr.Versie
     * @generated
     */
    EClass getVersie();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.Versie#getSchema <em>Schema</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Schema</em>'.
     * @see nl.bzk.brp.ecore.bmr.Versie#getSchema()
     * @see #getVersie()
     * @generated
     */
    EReference getVersie_Schema();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Versie#getVersieTag <em>Versie Tag</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Versie Tag</em>'.
     * @see nl.bzk.brp.ecore.bmr.Versie#getVersieTag()
     * @see #getVersie()
     * @generated
     */
    EAttribute getVersie_VersieTag();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.Versie#getObjectTypes <em>Object Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Object Types</em>'.
     * @see nl.bzk.brp.ecore.bmr.Versie#getObjectTypes()
     * @see #getVersie()
     * @generated
     */
    EReference getVersie_ObjectTypes();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.Versie#getAttribuutTypes <em>Attribuut Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Attribuut Types</em>'.
     * @see nl.bzk.brp.ecore.bmr.Versie#getAttribuutTypes()
     * @see #getVersie()
     * @generated
     */
    EReference getVersie_AttribuutTypes();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.Versie#getBerichtSjablonen <em>Bericht Sjablonen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Bericht Sjablonen</em>'.
     * @see nl.bzk.brp.ecore.bmr.Versie#getBerichtSjablonen()
     * @see #getVersie()
     * @generated
     */
    EReference getVersie_BerichtSjablonen();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde <em>Waarderegel Waarde</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Waarderegel Waarde</em>'.
     * @see nl.bzk.brp.ecore.bmr.WaarderegelWaarde
     * @generated
     */
    EClass getWaarderegelWaarde();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getBedrijfsRegel <em>Bedrijfs Regel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Bedrijfs Regel</em>'.
     * @see nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getBedrijfsRegel()
     * @see #getWaarderegelWaarde()
     * @generated
     */
    EReference getWaarderegelWaarde_BedrijfsRegel();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getWaarde <em>Waarde</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Waarde</em>'.
     * @see nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getWaarde()
     * @see #getWaarderegelWaarde()
     * @generated
     */
    EAttribute getWaarderegelWaarde_Waarde();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getWeergave <em>Weergave</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Weergave</em>'.
     * @see nl.bzk.brp.ecore.bmr.WaarderegelWaarde#getWeergave()
     * @see #getWaarderegelWaarde()
     * @generated
     */
    EAttribute getWaarderegelWaarde_Weergave();

    /**
     * Returns the meta object for class '{@link java.util.Map.Entry <em>Soort Tekst To Tekst Map Entry</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Soort Tekst To Tekst Map Entry</em>'.
     * @see java.util.Map.Entry
     * @model keyDataType="nl.bzk.brp.ecore.bmr.SoortTekst"
     *        valueType="nl.bzk.brp.ecore.bmr.Tekst"
     * @generated
     */
    EClass getSoortTekstToTekstMapEntry();

    /**
     * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Key</em>'.
     * @see java.util.Map.Entry
     * @see #getSoortTekstToTekstMapEntry()
     * @generated
     */
    EAttribute getSoortTekstToTekstMapEntry_Key();

    /**
     * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Value</em>'.
     * @see java.util.Map.Entry
     * @see #getSoortTekstToTekstMapEntry()
     * @generated
     */
    EReference getSoortTekstToTekstMapEntry_Value();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Applicatie <em>Applicatie</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Applicatie</em>'.
     * @see nl.bzk.brp.ecore.bmr.Applicatie
     * @generated
     */
    EClass getApplicatie();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.Applicatie#getFormulieren <em>Formulieren</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Formulieren</em>'.
     * @see nl.bzk.brp.ecore.bmr.Applicatie#getFormulieren()
     * @see #getApplicatie()
     * @generated
     */
    EReference getApplicatie_Formulieren();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Formulier <em>Formulier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Formulier</em>'.
     * @see nl.bzk.brp.ecore.bmr.Formulier
     * @generated
     */
    EClass getFormulier();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.Formulier#getApplicatie <em>Applicatie</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Applicatie</em>'.
     * @see nl.bzk.brp.ecore.bmr.Formulier#getApplicatie()
     * @see #getFormulier()
     * @generated
     */
    EReference getFormulier_Applicatie();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.Formulier#getFrames <em>Frames</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Frames</em>'.
     * @see nl.bzk.brp.ecore.bmr.Formulier#getFrames()
     * @see #getFormulier()
     * @generated
     */
    EReference getFormulier_Frames();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.Formulier#getBronnen <em>Bronnen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Bronnen</em>'.
     * @see nl.bzk.brp.ecore.bmr.Formulier#getBronnen()
     * @see #getFormulier()
     * @generated
     */
    EReference getFormulier_Bronnen();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Frame <em>Frame</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Frame</em>'.
     * @see nl.bzk.brp.ecore.bmr.Frame
     * @generated
     */
    EClass getFrame();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Frame#getVolgnummer <em>Volgnummer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Volgnummer</em>'.
     * @see nl.bzk.brp.ecore.bmr.Frame#getVolgnummer()
     * @see #getFrame()
     * @generated
     */
    EAttribute getFrame_Volgnummer();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.Frame#getFormulier <em>Formulier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Formulier</em>'.
     * @see nl.bzk.brp.ecore.bmr.Frame#getFormulier()
     * @see #getFrame()
     * @generated
     */
    EReference getFrame_Formulier();

    /**
     * Returns the meta object for the reference '{@link nl.bzk.brp.ecore.bmr.Frame#getBron <em>Bron</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Bron</em>'.
     * @see nl.bzk.brp.ecore.bmr.Frame#getBron()
     * @see #getFrame()
     * @generated
     */
    EReference getFrame_Bron();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.Frame#getVelden <em>Velden</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Velden</em>'.
     * @see nl.bzk.brp.ecore.bmr.Frame#getVelden()
     * @see #getFrame()
     * @generated
     */
    EReference getFrame_Velden();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.FrameVeld <em>Frame Veld</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Frame Veld</em>'.
     * @see nl.bzk.brp.ecore.bmr.FrameVeld
     * @generated
     */
    EClass getFrameVeld();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.FrameVeld#getVolgnummer <em>Volgnummer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Volgnummer</em>'.
     * @see nl.bzk.brp.ecore.bmr.FrameVeld#getVolgnummer()
     * @see #getFrameVeld()
     * @generated
     */
    EAttribute getFrameVeld_Volgnummer();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.FrameVeld#getFrame <em>Frame</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Frame</em>'.
     * @see nl.bzk.brp.ecore.bmr.FrameVeld#getFrame()
     * @see #getFrameVeld()
     * @generated
     */
    EReference getFrameVeld_Frame();

    /**
     * Returns the meta object for the reference '{@link nl.bzk.brp.ecore.bmr.FrameVeld#getAttribuut <em>Attribuut</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Attribuut</em>'.
     * @see nl.bzk.brp.ecore.bmr.FrameVeld#getAttribuut()
     * @see #getFrameVeld()
     * @generated
     */
    EReference getFrameVeld_Attribuut();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.Bron <em>Bron</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Bron</em>'.
     * @see nl.bzk.brp.ecore.bmr.Bron
     * @generated
     */
    EClass getBron();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Bron#getMeervoudsvorm <em>Meervoudsvorm</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Meervoudsvorm</em>'.
     * @see nl.bzk.brp.ecore.bmr.Bron#getMeervoudsvorm()
     * @see #getBron()
     * @generated
     */
    EAttribute getBron_Meervoudsvorm();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Bron#getIdentifier <em>Identifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Identifier</em>'.
     * @see nl.bzk.brp.ecore.bmr.Bron#getIdentifier()
     * @see #getBron()
     * @generated
     */
    EAttribute getBron_Identifier();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.Bron#getVolgnummer <em>Volgnummer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Volgnummer</em>'.
     * @see nl.bzk.brp.ecore.bmr.Bron#getVolgnummer()
     * @see #getBron()
     * @generated
     */
    EAttribute getBron_Volgnummer();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.Bron#getFormulier <em>Formulier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Formulier</em>'.
     * @see nl.bzk.brp.ecore.bmr.Bron#getFormulier()
     * @see #getBron()
     * @generated
     */
    EReference getBron_Formulier();

    /**
     * Returns the meta object for the reference list '{@link nl.bzk.brp.ecore.bmr.Bron#getFrames <em>Frames</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Frames</em>'.
     * @see nl.bzk.brp.ecore.bmr.Bron#getFrames()
     * @see #getBron()
     * @generated
     */
    EReference getBron_Frames();

    /**
     * Returns the meta object for the reference '{@link nl.bzk.brp.ecore.bmr.Bron#getObjectType <em>Object Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Object Type</em>'.
     * @see nl.bzk.brp.ecore.bmr.Bron#getObjectType()
     * @see #getBron()
     * @generated
     */
    EReference getBron_ObjectType();

    /**
     * Returns the meta object for the reference '{@link nl.bzk.brp.ecore.bmr.Bron#getLink <em>Link</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Link</em>'.
     * @see nl.bzk.brp.ecore.bmr.Bron#getLink()
     * @see #getBron()
     * @generated
     */
    EReference getBron_Link();

    /**
     * Returns the meta object for the containment reference list '{@link nl.bzk.brp.ecore.bmr.Bron#getBronAttributen <em>Bron Attributen</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Bron Attributen</em>'.
     * @see nl.bzk.brp.ecore.bmr.Bron#getBronAttributen()
     * @see #getBron()
     * @generated
     */
    EReference getBron_BronAttributen();

    /**
     * Returns the meta object for class '{@link nl.bzk.brp.ecore.bmr.BronAttribuut <em>Bron Attribuut</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Bron Attribuut</em>'.
     * @see nl.bzk.brp.ecore.bmr.BronAttribuut
     * @generated
     */
    EClass getBronAttribuut();

    /**
     * Returns the meta object for the container reference '{@link nl.bzk.brp.ecore.bmr.BronAttribuut#getBron <em>Bron</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Bron</em>'.
     * @see nl.bzk.brp.ecore.bmr.BronAttribuut#getBron()
     * @see #getBronAttribuut()
     * @generated
     */
    EReference getBronAttribuut_Bron();

    /**
     * Returns the meta object for the reference '{@link nl.bzk.brp.ecore.bmr.BronAttribuut#getAttribuut <em>Attribuut</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Attribuut</em>'.
     * @see nl.bzk.brp.ecore.bmr.BronAttribuut#getAttribuut()
     * @see #getBronAttribuut()
     * @generated
     */
    EReference getBronAttribuut_Attribuut();

    /**
     * Returns the meta object for the attribute '{@link nl.bzk.brp.ecore.bmr.BronAttribuut#getVolgnummer <em>Volgnummer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Volgnummer</em>'.
     * @see nl.bzk.brp.ecore.bmr.BronAttribuut#getVolgnummer()
     * @see #getBronAttribuut()
     * @generated
     */
    EAttribute getBronAttribuut_Volgnummer();

    /**
     * Returns the meta object for enum '{@link nl.bzk.brp.ecore.bmr.ElementSoort <em>Element Soort</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Element Soort</em>'.
     * @see nl.bzk.brp.ecore.bmr.ElementSoort
     * @generated
     */
    EEnum getElementSoort();

    /**
     * Returns the meta object for enum '{@link nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel <em>Soort Bedrijfs Regel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Soort Bedrijfs Regel</em>'.
     * @see nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel
     * @generated
     */
    EEnum getSoortBedrijfsRegel();

    /**
     * Returns the meta object for enum '{@link nl.bzk.brp.ecore.bmr.SoortInhoud <em>Soort Inhoud</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Soort Inhoud</em>'.
     * @see nl.bzk.brp.ecore.bmr.SoortInhoud
     * @generated
     */
    EEnum getSoortInhoud();

    /**
     * Returns the meta object for enum '{@link nl.bzk.brp.ecore.bmr.SoortTekst <em>Soort Tekst</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Soort Tekst</em>'.
     * @see nl.bzk.brp.ecore.bmr.SoortTekst
     * @generated
     */
    EEnum getSoortTekst();

    /**
     * Returns the meta object for enum '{@link nl.bzk.brp.ecore.bmr.VersieTag <em>Versie Tag</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Versie Tag</em>'.
     * @see nl.bzk.brp.ecore.bmr.VersieTag
     * @generated
     */
    EEnum getVersieTag();

    /**
     * Returns the meta object for enum '{@link nl.bzk.brp.ecore.bmr.InSetOfModel <em>In Set Of Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>In Set Of Model</em>'.
     * @see nl.bzk.brp.ecore.bmr.InSetOfModel
     * @generated
     */
    EEnum getInSetOfModel();

    /**
     * Returns the meta object for enum '{@link nl.bzk.brp.ecore.bmr.Historie <em>Historie</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Historie</em>'.
     * @see nl.bzk.brp.ecore.bmr.Historie
     * @generated
     */
    EEnum getHistorie();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    BmrFactory getBmrFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals
    {
        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.AttribuutImpl <em>Attribuut</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.AttribuutImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getAttribuut()
         * @generated
         */
        EClass ATTRIBUUT = eINSTANCE.getAttribuut();

        /**
         * The meta object literal for the '<em><b>Groep</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUUT__GROEP = eINSTANCE.getAttribuut_Groep();

        /**
         * The meta object literal for the '<em><b>Object Type</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUUT__OBJECT_TYPE = eINSTANCE.getAttribuut_ObjectType();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUUT__TYPE = eINSTANCE.getAttribuut_Type();

        /**
         * The meta object literal for the '<em><b>Afleidbaar</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUUT__AFLEIDBAAR = eINSTANCE.getAttribuut_Afleidbaar();

        /**
         * The meta object literal for the '<em><b>Historie Vastleggen</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUUT__HISTORIE_VASTLEGGEN = eINSTANCE.getAttribuut_HistorieVastleggen();

        /**
         * The meta object literal for the '<em><b>Verplicht</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUUT__VERPLICHT = eINSTANCE.getAttribuut_Verplicht();

        /**
         * The meta object literal for the '<em><b>Inverse Associatie Naam</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUUT__INVERSE_ASSOCIATIE_NAAM = eINSTANCE.getAttribuut_InverseAssociatieNaam();

        /**
         * The meta object literal for the '<em><b>Inverse Associatie</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUUT__INVERSE_ASSOCIATIE = eINSTANCE.getAttribuut_InverseAssociatie();

        /**
         * The meta object literal for the '<em><b>Gebruikt In Bedrijfs Regels</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUUT__GEBRUIKT_IN_BEDRIJFS_REGELS = eINSTANCE.getAttribuut_GebruiktInBedrijfsRegels();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.AttribuutTypeImpl <em>Attribuut Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.AttribuutTypeImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getAttribuutType()
         * @generated
         */
        EClass ATTRIBUUT_TYPE = eINSTANCE.getAttribuutType();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUUT_TYPE__TYPE = eINSTANCE.getAttribuutType_Type();

        /**
         * The meta object literal for the '<em><b>Versie</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUUT_TYPE__VERSIE = eINSTANCE.getAttribuutType_Versie();

        /**
         * The meta object literal for the '<em><b>Minimum Lengte</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUUT_TYPE__MINIMUM_LENGTE = eINSTANCE.getAttribuutType_MinimumLengte();

        /**
         * The meta object literal for the '<em><b>Maximum Lengte</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUUT_TYPE__MAXIMUM_LENGTE = eINSTANCE.getAttribuutType_MaximumLengte();

        /**
         * The meta object literal for the '<em><b>Aantal Decimalen</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUUT_TYPE__AANTAL_DECIMALEN = eINSTANCE.getAttribuutType_AantalDecimalen();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.BasisTypeImpl <em>Basis Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.BasisTypeImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getBasisType()
         * @generated
         */
        EClass BASIS_TYPE = eINSTANCE.getBasisType();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.BedrijfsRegelImpl <em>Bedrijfs Regel</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.BedrijfsRegelImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getBedrijfsRegel()
         * @generated
         */
        EClass BEDRIJFS_REGEL = eINSTANCE.getBedrijfsRegel();

        /**
         * The meta object literal for the '<em><b>Attributen</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BEDRIJFS_REGEL__ATTRIBUTEN = eINSTANCE.getBedrijfsRegel_Attributen();

        /**
         * The meta object literal for the '<em><b>Element</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BEDRIJFS_REGEL__ELEMENT = eINSTANCE.getBedrijfsRegel_Element();

        /**
         * The meta object literal for the '<em><b>Soort Bedrijfs Regel</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BEDRIJFS_REGEL__SOORT_BEDRIJFS_REGEL = eINSTANCE.getBedrijfsRegel_SoortBedrijfsRegel();

        /**
         * The meta object literal for the '<em><b>Waarden</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BEDRIJFS_REGEL__WAARDEN = eINSTANCE.getBedrijfsRegel_Waarden();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.BerichtSjabloonImpl <em>Bericht Sjabloon</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.BerichtSjabloonImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getBerichtSjabloon()
         * @generated
         */
        EClass BERICHT_SJABLOON = eINSTANCE.getBerichtSjabloon();

        /**
         * The meta object literal for the '<em><b>Versie</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BERICHT_SJABLOON__VERSIE = eINSTANCE.getBerichtSjabloon_Versie();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.DomeinImpl <em>Domein</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.DomeinImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getDomein()
         * @generated
         */
        EClass DOMEIN = eINSTANCE.getDomein();

        /**
         * The meta object literal for the '<em><b>Schemas</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOMEIN__SCHEMAS = eINSTANCE.getDomein_Schemas();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.ElementImpl <em>Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.ElementImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getElement()
         * @generated
         */
        EClass ELEMENT = eINSTANCE.getElement();

        /**
         * The meta object literal for the '<em><b>Bedrijfs Regels</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ELEMENT__BEDRIJFS_REGELS = eINSTANCE.getElement_BedrijfsRegels();

        /**
         * The meta object literal for the '<em><b>Beschrijving</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ELEMENT__BESCHRIJVING = eINSTANCE.getElement_Beschrijving();

        /**
         * The meta object literal for the '<em><b>Teksten</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ELEMENT__TEKSTEN = eINSTANCE.getElement_Teksten();

        /**
         * The meta object literal for the '<em><b>Laag</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ELEMENT__LAAG = eINSTANCE.getElement_Laag();

        /**
         * The meta object literal for the '<em><b>Sync Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ELEMENT__SYNC_ID = eINSTANCE.getElement_SyncId();

        /**
         * The meta object literal for the '<em><b>Soort</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ELEMENT__SOORT = eINSTANCE.getElement_Soort();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.GelaagdElementImpl <em>Gelaagd Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.GelaagdElementImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getGelaagdElement()
         * @generated
         */
        EClass GELAAGD_ELEMENT = eINSTANCE.getGelaagdElement();

        /**
         * The meta object literal for the '<em><b>Identifier DB</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GELAAGD_ELEMENT__IDENTIFIER_DB = eINSTANCE.getGelaagdElement_IdentifierDB();

        /**
         * The meta object literal for the '<em><b>Identifier Code</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GELAAGD_ELEMENT__IDENTIFIER_CODE = eINSTANCE.getGelaagdElement_IdentifierCode();

        /**
         * The meta object literal for the '<em><b>Versie Tag</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GELAAGD_ELEMENT__VERSIE_TAG = eINSTANCE.getGelaagdElement_VersieTag();

        /**
         * The meta object literal for the '<em><b>Volgnummer</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GELAAGD_ELEMENT__VOLGNUMMER = eINSTANCE.getGelaagdElement_Volgnummer();

        /**
         * The meta object literal for the '<em><b>In Set Of Model</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GELAAGD_ELEMENT__IN_SET_OF_MODEL = eINSTANCE.getGelaagdElement_InSetOfModel();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.GroepImpl <em>Groep</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.GroepImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getGroep()
         * @generated
         */
        EClass GROEP = eINSTANCE.getGroep();

        /**
         * The meta object literal for the '<em><b>Object Type</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GROEP__OBJECT_TYPE = eINSTANCE.getGroep_ObjectType();

        /**
         * The meta object literal for the '<em><b>Attributen</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GROEP__ATTRIBUTEN = eINSTANCE.getGroep_Attributen();

        /**
         * The meta object literal for the '<em><b>Historie Vastleggen</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GROEP__HISTORIE_VASTLEGGEN = eINSTANCE.getGroep_HistorieVastleggen();

        /**
         * The meta object literal for the '<em><b>Verplicht</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GROEP__VERPLICHT = eINSTANCE.getGroep_Verplicht();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.LaagImpl <em>Laag</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.LaagImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getLaag()
         * @generated
         */
        EClass LAAG = eINSTANCE.getLaag();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.MetaRegisterImpl <em>Meta Register</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.MetaRegisterImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getMetaRegister()
         * @generated
         */
        EClass META_REGISTER = eINSTANCE.getMetaRegister();

        /**
         * The meta object literal for the '<em><b>Domeinen</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference META_REGISTER__DOMEINEN = eINSTANCE.getMetaRegister_Domeinen();

        /**
         * The meta object literal for the '<em><b>Basis Typen</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference META_REGISTER__BASIS_TYPEN = eINSTANCE.getMetaRegister_BasisTypen();

        /**
         * The meta object literal for the '<em><b>Applicaties</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference META_REGISTER__APPLICATIES = eINSTANCE.getMetaRegister_Applicaties();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.ModelElementImpl <em>Model Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.ModelElementImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getModelElement()
         * @generated
         */
        EClass MODEL_ELEMENT = eINSTANCE.getModelElement();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MODEL_ELEMENT__ID = eINSTANCE.getModelElement_Id();

        /**
         * The meta object literal for the '<em><b>Naam</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MODEL_ELEMENT__NAAM = eINSTANCE.getModelElement_Naam();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl <em>Object Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.ObjectTypeImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getObjectType()
         * @generated
         */
        EClass OBJECT_TYPE = eINSTANCE.getObjectType();

        /**
         * The meta object literal for the '<em><b>Attributen</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OBJECT_TYPE__ATTRIBUTEN = eINSTANCE.getObjectType_Attributen();

        /**
         * The meta object literal for the '<em><b>Groepen</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OBJECT_TYPE__GROEPEN = eINSTANCE.getObjectType_Groepen();

        /**
         * The meta object literal for the '<em><b>Meervouds Naam</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OBJECT_TYPE__MEERVOUDS_NAAM = eINSTANCE.getObjectType_MeervoudsNaam();

        /**
         * The meta object literal for the '<em><b>Soort Inhoud</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OBJECT_TYPE__SOORT_INHOUD = eINSTANCE.getObjectType_SoortInhoud();

        /**
         * The meta object literal for the '<em><b>Sub Types</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OBJECT_TYPE__SUB_TYPES = eINSTANCE.getObjectType_SubTypes();

        /**
         * The meta object literal for the '<em><b>Super Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OBJECT_TYPE__SUPER_TYPE = eINSTANCE.getObjectType_SuperType();

        /**
         * The meta object literal for the '<em><b>Tuples</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OBJECT_TYPE__TUPLES = eINSTANCE.getObjectType_Tuples();

        /**
         * The meta object literal for the '<em><b>Versie</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OBJECT_TYPE__VERSIE = eINSTANCE.getObjectType_Versie();

        /**
         * The meta object literal for the '<em><b>Afleidbaar</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OBJECT_TYPE__AFLEIDBAAR = eINSTANCE.getObjectType_Afleidbaar();

        /**
         * The meta object literal for the '<em><b>Historie Vastleggen</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OBJECT_TYPE__HISTORIE_VASTLEGGEN = eINSTANCE.getObjectType_HistorieVastleggen();

        /**
         * The meta object literal for the '<em><b>Kunstmatige Sleutel</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OBJECT_TYPE__KUNSTMATIGE_SLEUTEL = eINSTANCE.getObjectType_KunstmatigeSleutel();

        /**
         * The meta object literal for the '<em><b>Lookup</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OBJECT_TYPE__LOOKUP = eINSTANCE.getObjectType_Lookup();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.SchemaImpl <em>Schema</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.SchemaImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getSchema()
         * @generated
         */
        EClass SCHEMA = eINSTANCE.getSchema();

        /**
         * The meta object literal for the '<em><b>Versies</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCHEMA__VERSIES = eINSTANCE.getSchema_Versies();

        /**
         * The meta object literal for the '<em><b>Domein</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCHEMA__DOMEIN = eINSTANCE.getSchema_Domein();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.TekstImpl <em>Tekst</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.TekstImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getTekst()
         * @generated
         */
        EClass TEKST = eINSTANCE.getTekst();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TEKST__ID = eINSTANCE.getTekst_Id();

        /**
         * The meta object literal for the '<em><b>Element</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TEKST__ELEMENT = eINSTANCE.getTekst_Element();

        /**
         * The meta object literal for the '<em><b>Soort</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TEKST__SOORT = eINSTANCE.getTekst_Soort();

        /**
         * The meta object literal for the '<em><b>Tekst</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TEKST__TEKST = eINSTANCE.getTekst_Tekst();

        /**
         * The meta object literal for the '<em><b>Html Tekst</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TEKST__HTML_TEKST = eINSTANCE.getTekst_HtmlTekst();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.TupleImpl <em>Tuple</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.TupleImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getTuple()
         * @generated
         */
        EClass TUPLE = eINSTANCE.getTuple();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TUPLE__ID = eINSTANCE.getTuple_Id();

        /**
         * The meta object literal for the '<em><b>Object Type</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TUPLE__OBJECT_TYPE = eINSTANCE.getTuple_ObjectType();

        /**
         * The meta object literal for the '<em><b>Relatief Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TUPLE__RELATIEF_ID = eINSTANCE.getTuple_RelatiefId();

        /**
         * The meta object literal for the '<em><b>Code</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TUPLE__CODE = eINSTANCE.getTuple_Code();

        /**
         * The meta object literal for the '<em><b>Naam</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TUPLE__NAAM = eINSTANCE.getTuple_Naam();

        /**
         * The meta object literal for the '<em><b>Naam Mannelijk</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TUPLE__NAAM_MANNELIJK = eINSTANCE.getTuple_NaamMannelijk();

        /**
         * The meta object literal for the '<em><b>Naam Vrouwelijk</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TUPLE__NAAM_VROUWELIJK = eINSTANCE.getTuple_NaamVrouwelijk();

        /**
         * The meta object literal for the '<em><b>Omschrijving</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TUPLE__OMSCHRIJVING = eINSTANCE.getTuple_Omschrijving();

        /**
         * The meta object literal for the '<em><b>Heeft Materiele Historie</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TUPLE__HEEFT_MATERIELE_HISTORIE = eINSTANCE.getTuple_HeeftMaterieleHistorie();

        /**
         * The meta object literal for the '<em><b>Datum Aanvang Geldigheid</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TUPLE__DATUM_AANVANG_GELDIGHEID = eINSTANCE.getTuple_DatumAanvangGeldigheid();

        /**
         * The meta object literal for the '<em><b>Datum Einde Geldigheid</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TUPLE__DATUM_EINDE_GELDIGHEID = eINSTANCE.getTuple_DatumEindeGeldigheid();

        /**
         * The meta object literal for the '<em><b>Categorie Soort Actie</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TUPLE__CATEGORIE_SOORT_ACTIE = eINSTANCE.getTuple_CategorieSoortActie();

        /**
         * The meta object literal for the '<em><b>Categorie Soort Document</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TUPLE__CATEGORIE_SOORT_DOCUMENT = eINSTANCE.getTuple_CategorieSoortDocument();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.TypeImpl <em>Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.TypeImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getType()
         * @generated
         */
        EClass TYPE = eINSTANCE.getType();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.VersieImpl <em>Versie</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.VersieImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getVersie()
         * @generated
         */
        EClass VERSIE = eINSTANCE.getVersie();

        /**
         * The meta object literal for the '<em><b>Schema</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference VERSIE__SCHEMA = eINSTANCE.getVersie_Schema();

        /**
         * The meta object literal for the '<em><b>Versie Tag</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VERSIE__VERSIE_TAG = eINSTANCE.getVersie_VersieTag();

        /**
         * The meta object literal for the '<em><b>Object Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference VERSIE__OBJECT_TYPES = eINSTANCE.getVersie_ObjectTypes();

        /**
         * The meta object literal for the '<em><b>Attribuut Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference VERSIE__ATTRIBUUT_TYPES = eINSTANCE.getVersie_AttribuutTypes();

        /**
         * The meta object literal for the '<em><b>Bericht Sjablonen</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference VERSIE__BERICHT_SJABLONEN = eINSTANCE.getVersie_BerichtSjablonen();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.WaarderegelWaardeImpl <em>Waarderegel Waarde</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.WaarderegelWaardeImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getWaarderegelWaarde()
         * @generated
         */
        EClass WAARDEREGEL_WAARDE = eINSTANCE.getWaarderegelWaarde();

        /**
         * The meta object literal for the '<em><b>Bedrijfs Regel</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WAARDEREGEL_WAARDE__BEDRIJFS_REGEL = eINSTANCE.getWaarderegelWaarde_BedrijfsRegel();

        /**
         * The meta object literal for the '<em><b>Waarde</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WAARDEREGEL_WAARDE__WAARDE = eINSTANCE.getWaarderegelWaarde_Waarde();

        /**
         * The meta object literal for the '<em><b>Weergave</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WAARDEREGEL_WAARDE__WEERGAVE = eINSTANCE.getWaarderegelWaarde_Weergave();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.SoortTekstToTekstMapEntryImpl <em>Soort Tekst To Tekst Map Entry</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.SoortTekstToTekstMapEntryImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getSoortTekstToTekstMapEntry()
         * @generated
         */
        EClass SOORT_TEKST_TO_TEKST_MAP_ENTRY = eINSTANCE.getSoortTekstToTekstMapEntry();

        /**
         * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SOORT_TEKST_TO_TEKST_MAP_ENTRY__KEY = eINSTANCE.getSoortTekstToTekstMapEntry_Key();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SOORT_TEKST_TO_TEKST_MAP_ENTRY__VALUE = eINSTANCE.getSoortTekstToTekstMapEntry_Value();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.ApplicatieImpl <em>Applicatie</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.ApplicatieImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getApplicatie()
         * @generated
         */
        EClass APPLICATIE = eINSTANCE.getApplicatie();

        /**
         * The meta object literal for the '<em><b>Formulieren</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference APPLICATIE__FORMULIEREN = eINSTANCE.getApplicatie_Formulieren();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.FormulierImpl <em>Formulier</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.FormulierImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getFormulier()
         * @generated
         */
        EClass FORMULIER = eINSTANCE.getFormulier();

        /**
         * The meta object literal for the '<em><b>Applicatie</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FORMULIER__APPLICATIE = eINSTANCE.getFormulier_Applicatie();

        /**
         * The meta object literal for the '<em><b>Frames</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FORMULIER__FRAMES = eINSTANCE.getFormulier_Frames();

        /**
         * The meta object literal for the '<em><b>Bronnen</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FORMULIER__BRONNEN = eINSTANCE.getFormulier_Bronnen();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.FrameImpl <em>Frame</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.FrameImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getFrame()
         * @generated
         */
        EClass FRAME = eINSTANCE.getFrame();

        /**
         * The meta object literal for the '<em><b>Volgnummer</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FRAME__VOLGNUMMER = eINSTANCE.getFrame_Volgnummer();

        /**
         * The meta object literal for the '<em><b>Formulier</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FRAME__FORMULIER = eINSTANCE.getFrame_Formulier();

        /**
         * The meta object literal for the '<em><b>Bron</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FRAME__BRON = eINSTANCE.getFrame_Bron();

        /**
         * The meta object literal for the '<em><b>Velden</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FRAME__VELDEN = eINSTANCE.getFrame_Velden();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.FrameVeldImpl <em>Frame Veld</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.FrameVeldImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getFrameVeld()
         * @generated
         */
        EClass FRAME_VELD = eINSTANCE.getFrameVeld();

        /**
         * The meta object literal for the '<em><b>Volgnummer</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FRAME_VELD__VOLGNUMMER = eINSTANCE.getFrameVeld_Volgnummer();

        /**
         * The meta object literal for the '<em><b>Frame</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FRAME_VELD__FRAME = eINSTANCE.getFrameVeld_Frame();

        /**
         * The meta object literal for the '<em><b>Attribuut</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FRAME_VELD__ATTRIBUUT = eINSTANCE.getFrameVeld_Attribuut();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.BronImpl <em>Bron</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.BronImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getBron()
         * @generated
         */
        EClass BRON = eINSTANCE.getBron();

        /**
         * The meta object literal for the '<em><b>Meervoudsvorm</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BRON__MEERVOUDSVORM = eINSTANCE.getBron_Meervoudsvorm();

        /**
         * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BRON__IDENTIFIER = eINSTANCE.getBron_Identifier();

        /**
         * The meta object literal for the '<em><b>Volgnummer</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BRON__VOLGNUMMER = eINSTANCE.getBron_Volgnummer();

        /**
         * The meta object literal for the '<em><b>Formulier</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BRON__FORMULIER = eINSTANCE.getBron_Formulier();

        /**
         * The meta object literal for the '<em><b>Frames</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BRON__FRAMES = eINSTANCE.getBron_Frames();

        /**
         * The meta object literal for the '<em><b>Object Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BRON__OBJECT_TYPE = eINSTANCE.getBron_ObjectType();

        /**
         * The meta object literal for the '<em><b>Link</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BRON__LINK = eINSTANCE.getBron_Link();

        /**
         * The meta object literal for the '<em><b>Bron Attributen</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BRON__BRON_ATTRIBUTEN = eINSTANCE.getBron_BronAttributen();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.impl.BronAttribuutImpl <em>Bron Attribuut</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.impl.BronAttribuutImpl
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getBronAttribuut()
         * @generated
         */
        EClass BRON_ATTRIBUUT = eINSTANCE.getBronAttribuut();

        /**
         * The meta object literal for the '<em><b>Bron</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BRON_ATTRIBUUT__BRON = eINSTANCE.getBronAttribuut_Bron();

        /**
         * The meta object literal for the '<em><b>Attribuut</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BRON_ATTRIBUUT__ATTRIBUUT = eINSTANCE.getBronAttribuut_Attribuut();

        /**
         * The meta object literal for the '<em><b>Volgnummer</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BRON_ATTRIBUUT__VOLGNUMMER = eINSTANCE.getBronAttribuut_Volgnummer();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.ElementSoort <em>Element Soort</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.ElementSoort
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getElementSoort()
         * @generated
         */
        EEnum ELEMENT_SOORT = eINSTANCE.getElementSoort();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel <em>Soort Bedrijfs Regel</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getSoortBedrijfsRegel()
         * @generated
         */
        EEnum SOORT_BEDRIJFS_REGEL = eINSTANCE.getSoortBedrijfsRegel();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.SoortInhoud <em>Soort Inhoud</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.SoortInhoud
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getSoortInhoud()
         * @generated
         */
        EEnum SOORT_INHOUD = eINSTANCE.getSoortInhoud();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.SoortTekst <em>Soort Tekst</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.SoortTekst
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getSoortTekst()
         * @generated
         */
        EEnum SOORT_TEKST = eINSTANCE.getSoortTekst();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.VersieTag <em>Versie Tag</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.VersieTag
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getVersieTag()
         * @generated
         */
        EEnum VERSIE_TAG = eINSTANCE.getVersieTag();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.InSetOfModel <em>In Set Of Model</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.InSetOfModel
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getInSetOfModel()
         * @generated
         */
        EEnum IN_SET_OF_MODEL = eINSTANCE.getInSetOfModel();

        /**
         * The meta object literal for the '{@link nl.bzk.brp.ecore.bmr.Historie <em>Historie</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see nl.bzk.brp.ecore.bmr.Historie
         * @see nl.bzk.brp.ecore.bmr.impl.BmrPackageImpl#getHistorie()
         * @generated
         */
        EEnum HISTORIE = eINSTANCE.getHistorie();

    }

} //BmrPackage
