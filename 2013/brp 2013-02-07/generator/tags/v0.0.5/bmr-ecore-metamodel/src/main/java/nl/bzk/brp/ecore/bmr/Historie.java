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
 * A representation of the literals of the enumeration '<em><b>Historie</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getHistorie()
 * @model
 * @generated
 */
public enum Historie implements Enumerator
{
    /**
     * The '<em><b>G</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #G_VALUE
     * @generated
     * @ordered
     */
    G(0, "G", "G"),

    /**
     * The '<em><b>F</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #F_VALUE
     * @generated
     * @ordered
     */
    F(1, "F", "F"),

    /**
     * The '<em><b>M</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #M_VALUE
     * @generated
     * @ordered
     */
    M(2, "M", "M"),

    /**
     * The '<em><b>B</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #B_VALUE
     * @generated
     * @ordered
     */
    B(3, "B", "B"),

    /**
     * The '<em><b>P</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #P_VALUE
     * @generated
     * @ordered
     */
    P(4, "P", "P");

    /**
     * The '<em><b>G</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>G</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #G
     * @model
     * @generated
     * @ordered
     */
    public static final int G_VALUE = 0;

    /**
     * The '<em><b>F</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>F</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #F
     * @model
     * @generated
     * @ordered
     */
    public static final int F_VALUE = 1;

    /**
     * The '<em><b>M</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>M</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #M
     * @model
     * @generated
     * @ordered
     */
    public static final int M_VALUE = 2;

    /**
     * The '<em><b>B</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>B</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #B
     * @model
     * @generated
     * @ordered
     */
    public static final int B_VALUE = 3;

    /**
     * The '<em><b>P</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>P</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #P
     * @model
     * @generated
     * @ordered
     */
    public static final int P_VALUE = 4;

    /**
     * An array of all the '<em><b>Historie</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final Historie[] VALUES_ARRAY =
        new Historie[]
        {
            G,
            F,
            M,
            B,
            P,
        };

    /**
     * A public read-only list of all the '<em><b>Historie</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<Historie> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Historie</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static Historie get(String literal)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            Historie result = VALUES_ARRAY[i];
            if (result.toString().equals(literal))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Historie</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static Historie getByName(String name)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            Historie result = VALUES_ARRAY[i];
            if (result.getName().equals(name))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Historie</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static Historie get(int value)
    {
        switch (value)
        {
            case G_VALUE: return G;
            case F_VALUE: return F;
            case M_VALUE: return M;
            case B_VALUE: return B;
            case P_VALUE: return P;
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
    private Historie(int value, String name, String literal)
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
    
} //Historie
