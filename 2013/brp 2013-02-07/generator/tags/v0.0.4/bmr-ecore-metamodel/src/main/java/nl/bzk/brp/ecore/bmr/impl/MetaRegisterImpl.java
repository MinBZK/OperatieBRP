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
package nl.bzk.brp.ecore.bmr.impl;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import nl.bzk.brp.ecore.bmr.Applicatie;
import nl.bzk.brp.ecore.bmr.BasisType;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.Domein;
import nl.bzk.brp.ecore.bmr.MetaRegister;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Meta Register</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.MetaRegisterImpl#getDomeinen <em>Domeinen</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.MetaRegisterImpl#getBasisTypen <em>Basis Typen</em>}</li>
 *   <li>{@link nl.bzk.brp.ecore.bmr.impl.MetaRegisterImpl#getApplicaties <em>Applicaties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetaRegisterImpl extends EObjectImpl implements MetaRegister {

    /**
     * The cached value of the '{@link #getDomeinen() <em>Domeinen</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDomeinen()
     * @generated
     * @ordered
     */
    protected EList<Domein>     domeinen;

    /**
     * The cached value of the '{@link #getBasisTypen() <em>Basis Typen</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBasisTypen()
     * @generated
     * @ordered
     */
    protected EList<BasisType>  basisTypen;

    /**
     * The cached value of the '{@link #getApplicaties() <em>Applicaties</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getApplicaties()
     * @generated
     * @ordered
     */
    protected EList<Applicatie> applicaties;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MetaRegisterImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return BmrPackage.Literals.META_REGISTER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<Domein> getDomeinen() {
        if (domeinen == null)
        {
            domeinen = new EObjectContainmentEList<Domein>(Domein.class, this, BmrPackage.META_REGISTER__DOMEINEN);
        }
        return domeinen;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<BasisType> getBasisTypen() {
        if (basisTypen == null)
        {
            basisTypen = new EObjectContainmentEList<BasisType>(BasisType.class, this, BmrPackage.META_REGISTER__BASIS_TYPEN);
        }
        return basisTypen;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<Applicatie> getApplicaties() {
        if (applicaties == null)
        {
            applicaties = new EObjectContainmentEList<Applicatie>(Applicatie.class, this, BmrPackage.META_REGISTER__APPLICATIES);
        }
        return applicaties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID)
        {
            case BmrPackage.META_REGISTER__DOMEINEN:
                return ((InternalEList<?>)getDomeinen()).basicRemove(otherEnd, msgs);
            case BmrPackage.META_REGISTER__BASIS_TYPEN:
                return ((InternalEList<?>)getBasisTypen()).basicRemove(otherEnd, msgs);
            case BmrPackage.META_REGISTER__APPLICATIES:
                return ((InternalEList<?>)getApplicaties()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID)
        {
            case BmrPackage.META_REGISTER__DOMEINEN:
                return getDomeinen();
            case BmrPackage.META_REGISTER__BASIS_TYPEN:
                return getBasisTypen();
            case BmrPackage.META_REGISTER__APPLICATIES:
                return getApplicaties();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID)
        {
            case BmrPackage.META_REGISTER__DOMEINEN:
                getDomeinen().clear();
                getDomeinen().addAll((Collection<? extends Domein>)newValue);
                return;
            case BmrPackage.META_REGISTER__BASIS_TYPEN:
                getBasisTypen().clear();
                getBasisTypen().addAll((Collection<? extends BasisType>)newValue);
                return;
            case BmrPackage.META_REGISTER__APPLICATIES:
                getApplicaties().clear();
                getApplicaties().addAll((Collection<? extends Applicatie>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID)
        {
            case BmrPackage.META_REGISTER__DOMEINEN:
                getDomeinen().clear();
                return;
            case BmrPackage.META_REGISTER__BASIS_TYPEN:
                getBasisTypen().clear();
                return;
            case BmrPackage.META_REGISTER__APPLICATIES:
                getApplicaties().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID)
        {
            case BmrPackage.META_REGISTER__DOMEINEN:
                return domeinen != null && !domeinen.isEmpty();
            case BmrPackage.META_REGISTER__BASIS_TYPEN:
                return basisTypen != null && !basisTypen.isEmpty();
            case BmrPackage.META_REGISTER__APPLICATIES:
                return applicaties != null && !applicaties.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    @Override
    public Domein getDomein(final String naam) {
        return Iterables.find(getDomeinen(), new Predicate<Domein>() {

            @Override
            public boolean apply(final Domein domein) {
                return domein.getNaam().equals(naam);
            }
        });
    }

    @Override
    public Applicatie getApplicatie(final String naam) {
        return Iterables.find(getApplicaties(), new Predicate<Applicatie>() {

            @Override
            public boolean apply(final Applicatie applicatie) {
                return applicatie.getNaam().equals(naam);
            }
        });
    }
} // MetaRegisterImpl
