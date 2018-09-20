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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see nl.bzk.brp.ecore.bmr.BmrPackage
 * @generated
 */
public interface BmrFactory extends EFactory
{
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    BmrFactory eINSTANCE = nl.bzk.brp.ecore.bmr.impl.BmrFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Attribuut</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Attribuut</em>'.
     * @generated
     */
    Attribuut createAttribuut();

    /**
     * Returns a new object of class '<em>Attribuut Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Attribuut Type</em>'.
     * @generated
     */
    AttribuutType createAttribuutType();

    /**
     * Returns a new object of class '<em>Basis Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Basis Type</em>'.
     * @generated
     */
    BasisType createBasisType();

    /**
     * Returns a new object of class '<em>Bedrijfs Regel</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Bedrijfs Regel</em>'.
     * @generated
     */
    BedrijfsRegel createBedrijfsRegel();

    /**
     * Returns a new object of class '<em>Bericht Sjabloon</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Bericht Sjabloon</em>'.
     * @generated
     */
    BerichtSjabloon createBerichtSjabloon();

    /**
     * Returns a new object of class '<em>Domein</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Domein</em>'.
     * @generated
     */
    Domein createDomein();

    /**
     * Returns a new object of class '<em>Groep</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Groep</em>'.
     * @generated
     */
    Groep createGroep();

    /**
     * Returns a new object of class '<em>Laag</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Laag</em>'.
     * @generated
     */
    Laag createLaag();

    /**
     * Returns a new object of class '<em>Meta Register</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Meta Register</em>'.
     * @generated
     */
    MetaRegister createMetaRegister();

    /**
     * Returns a new object of class '<em>Object Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Object Type</em>'.
     * @generated
     */
    ObjectType createObjectType();

    /**
     * Returns a new object of class '<em>Schema</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Schema</em>'.
     * @generated
     */
    Schema createSchema();

    /**
     * Returns a new object of class '<em>Tekst</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Tekst</em>'.
     * @generated
     */
    Tekst createTekst();

    /**
     * Returns a new object of class '<em>Tuple</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Tuple</em>'.
     * @generated
     */
    Tuple createTuple();

    /**
     * Returns a new object of class '<em>Versie</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Versie</em>'.
     * @generated
     */
    Versie createVersie();

    /**
     * Returns a new object of class '<em>Waarderegel Waarde</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Waarderegel Waarde</em>'.
     * @generated
     */
    WaarderegelWaarde createWaarderegelWaarde();

    /**
     * Returns a new object of class '<em>Applicatie</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Applicatie</em>'.
     * @generated
     */
    Applicatie createApplicatie();

    /**
     * Returns a new object of class '<em>Formulier</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Formulier</em>'.
     * @generated
     */
    Formulier createFormulier();

    /**
     * Returns a new object of class '<em>Frame</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Frame</em>'.
     * @generated
     */
    Frame createFrame();

    /**
     * Returns a new object of class '<em>Frame Veld</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Frame Veld</em>'.
     * @generated
     */
    FrameVeld createFrameVeld();

    /**
     * Returns a new object of class '<em>Bron</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Bron</em>'.
     * @generated
     */
    Bron createBron();

    /**
     * Returns a new object of class '<em>Bron Attribuut</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Bron Attribuut</em>'.
     * @generated
     */
    BronAttribuut createBronAttribuut();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    BmrPackage getBmrPackage();

} //BmrFactory
