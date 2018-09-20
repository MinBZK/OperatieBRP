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
 * A representation of the literals of the enumeration '<em><b>Element Soort</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getElementSoort()
 * @model
 * @generated
 */
public enum ElementSoort implements Enumerator
{
    /**
     * The '<em><b>A</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #A_VALUE
     * @generated
     * @ordered
     */
    A(0, "A", "A"),

    /**
     * The '<em><b>AT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #AT_VALUE
     * @generated
     * @ordered
     */
    AT(1, "AT", "AT"),

    /**
     * The '<em><b>B</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #B_VALUE
     * @generated
     * @ordered
     */
    B(2, "B", "B"),

    /**
     * The '<em><b>BR</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #BR_VALUE
     * @generated
     * @ordered
     */
    BR(3, "BR", "BR"),

    /**
     * The '<em><b>BS</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #BS_VALUE
     * @generated
     * @ordered
     */
    BS(4, "BS", "BS"),

    /**
     * The '<em><b>BT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #BT_VALUE
     * @generated
     * @ordered
     */
    BT(5, "BT", "BT"),

    /**
     * The '<em><b>D</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #D_VALUE
     * @generated
     * @ordered
     */
    D(6, "D", "D"),

    /**
     * The '<em><b>G</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #G_VALUE
     * @generated
     * @ordered
     */
    G(7, "G", "G"),

    /**
     * The '<em><b>L</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #L_VALUE
     * @generated
     * @ordered
     */
    L(8, "L", "L"),

    /**
     * The '<em><b>OT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #OT_VALUE
     * @generated
     * @ordered
     */
    OT(9, "OT", "OT"),

    /**
     * The '<em><b>S</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #S_VALUE
     * @generated
     * @ordered
     */
    S(10, "S", "S"),

    /**
     * The '<em><b>V</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #V_VALUE
     * @generated
     * @ordered
     */
    V(11, "V", "V"),

    /**
     * The '<em><b>W</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #W_VALUE
     * @generated
     * @ordered
     */
    W(12, "W", "W");

    /**
     * The '<em><b>A</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>A</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #A
     * @model
     * @generated
     * @ordered
     */
    public static final int A_VALUE = 0;

    /**
     * The '<em><b>AT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>AT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #AT
     * @model
     * @generated
     * @ordered
     */
    public static final int AT_VALUE = 1;

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
    public static final int B_VALUE = 2;

    /**
     * The '<em><b>BR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #BR
     * @model
     * @generated
     * @ordered
     */
    public static final int BR_VALUE = 3;

    /**
     * The '<em><b>BS</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BS</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #BS
     * @model
     * @generated
     * @ordered
     */
    public static final int BS_VALUE = 4;

    /**
     * The '<em><b>BT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #BT
     * @model
     * @generated
     * @ordered
     */
    public static final int BT_VALUE = 5;

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
    public static final int D_VALUE = 6;

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
    public static final int G_VALUE = 7;

    /**
     * The '<em><b>L</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>L</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #L
     * @model
     * @generated
     * @ordered
     */
    public static final int L_VALUE = 8;

    /**
     * The '<em><b>OT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>OT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #OT
     * @model
     * @generated
     * @ordered
     */
    public static final int OT_VALUE = 9;

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
    public static final int S_VALUE = 10;

    /**
     * The '<em><b>V</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>V</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #V
     * @model
     * @generated
     * @ordered
     */
    public static final int V_VALUE = 11;

    /**
     * The '<em><b>W</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>W</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #W
     * @model
     * @generated
     * @ordered
     */
    public static final int W_VALUE = 12;

    /**
     * An array of all the '<em><b>Element Soort</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ElementSoort[] VALUES_ARRAY =
        new ElementSoort[]
        {
            A,
            AT,
            B,
            BR,
            BS,
            BT,
            D,
            G,
            L,
            OT,
            S,
            V,
            W,
        };

    /**
     * A public read-only list of all the '<em><b>Element Soort</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ElementSoort> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Element Soort</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ElementSoort get(String literal)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            ElementSoort result = VALUES_ARRAY[i];
            if (result.toString().equals(literal))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Element Soort</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ElementSoort getByName(String name)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            ElementSoort result = VALUES_ARRAY[i];
            if (result.getName().equals(name))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Element Soort</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ElementSoort get(int value)
    {
        switch (value)
        {
            case A_VALUE: return A;
            case AT_VALUE: return AT;
            case B_VALUE: return B;
            case BR_VALUE: return BR;
            case BS_VALUE: return BS;
            case BT_VALUE: return BT;
            case D_VALUE: return D;
            case G_VALUE: return G;
            case L_VALUE: return L;
            case OT_VALUE: return OT;
            case S_VALUE: return S;
            case V_VALUE: return V;
            case W_VALUE: return W;
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
    private ElementSoort(int value, String name, String literal)
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
    
} //ElementSoort
