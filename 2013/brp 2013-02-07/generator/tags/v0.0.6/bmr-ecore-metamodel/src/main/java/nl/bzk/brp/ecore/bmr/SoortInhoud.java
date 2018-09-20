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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Soort Inhoud</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getSoortInhoud()
 * @model
 * @generated
 */
public enum SoortInhoud implements Enumerator
{
    /**
     * The '<em><b>S</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #S_VALUE
     * @generated
     * @ordered
     */
    S(0, "S", "S"),

    /**
     * The '<em><b>D</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #D_VALUE
     * @generated
     * @ordered
     */
    D(1, "D", "D"),

    /**
     * The '<em><b>X</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #X_VALUE
     * @generated
     * @ordered
     */
    X(2, "X", "X");

    /**
     * The '<em><b>S</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>S</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #S
     * @model
     * @generated
     * @ordered
     */
    public static final int S_VALUE = 0;

    /**
     * The '<em><b>D</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>D</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #D
     * @model
     * @generated
     * @ordered
     */
    public static final int D_VALUE = 1;

    /**
     * The '<em><b>X</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>X</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #X
     * @model
     * @generated
     * @ordered
     */
    public static final int X_VALUE = 2;

    /**
     * An array of all the '<em><b>Soort Inhoud</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final SoortInhoud[] VALUES_ARRAY =
        new SoortInhoud[]
        {
            S,
            D,
            X,
        };

    /**
     * A public read-only list of all the '<em><b>Soort Inhoud</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<SoortInhoud> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Soort Inhoud</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SoortInhoud get(String literal)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            SoortInhoud result = VALUES_ARRAY[i];
            if (result.toString().equals(literal))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Soort Inhoud</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SoortInhoud getByName(String name)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            SoortInhoud result = VALUES_ARRAY[i];
            if (result.getName().equals(name))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Soort Inhoud</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SoortInhoud get(int value)
    {
        switch (value)
        {
            case S_VALUE: return S;
            case D_VALUE: return D;
            case X_VALUE: return X;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private SoortInhoud(int value, String name, String literal)
    {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getValue()
    {
      return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName()
    {
      return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLiteral()
    {
      return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString()
    {
        return literal;
    }
    
} //SoortInhoud
