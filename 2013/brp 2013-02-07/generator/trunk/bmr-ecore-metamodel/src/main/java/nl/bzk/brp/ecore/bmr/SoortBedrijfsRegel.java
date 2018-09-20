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
 * A representation of the literals of the enumeration '<em><b>Soort Bedrijfs Regel</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getSoortBedrijfsRegel()
 * @model
 * @generated
 */
public enum SoortBedrijfsRegel implements Enumerator
{
    /**
     * The '<em><b>ID</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ID_VALUE
     * @generated
     * @ordered
     */
    ID(0, "ID", "ID"),

    /**
     * The '<em><b>UC</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #UC_VALUE
     * @generated
     * @ordered
     */
    UC(1, "UC", "UC"),

    /**
     * The '<em><b>WB</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #WB_VALUE
     * @generated
     * @ordered
     */
    WB(2, "WB", "WB"),

    /**
     * The '<em><b>VK</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #VK_VALUE
     * @generated
     * @ordered
     */
    VK(3, "VK", "VK"),

    /**
     * The '<em><b>OV</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #OV_VALUE
     * @generated
     * @ordered
     */
    OV(4, "OV", "OV"),

    /**
     * The '<em><b>LI</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #LI_VALUE
     * @generated
     * @ordered
     */
    LI(5, "LI", "LI");

    /**
     * The '<em><b>ID</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ID</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ID
     * @model
     * @generated
     * @ordered
     */
    public static final int ID_VALUE = 0;

    /**
     * The '<em><b>UC</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>UC</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #UC
     * @model
     * @generated
     * @ordered
     */
    public static final int UC_VALUE = 1;

    /**
     * The '<em><b>WB</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>WB</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #WB
     * @model
     * @generated
     * @ordered
     */
    public static final int WB_VALUE = 2;

    /**
     * The '<em><b>VK</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>VK</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #VK
     * @model
     * @generated
     * @ordered
     */
    public static final int VK_VALUE = 3;

    /**
     * The '<em><b>OV</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>OV</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #OV
     * @model
     * @generated
     * @ordered
     */
    public static final int OV_VALUE = 4;

    /**
     * The '<em><b>LI</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>LI</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #LI
     * @model
     * @generated
     * @ordered
     */
    public static final int LI_VALUE = 5;

    /**
     * An array of all the '<em><b>Soort Bedrijfs Regel</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final SoortBedrijfsRegel[] VALUES_ARRAY =
        new SoortBedrijfsRegel[]
        {
            ID,
            UC,
            WB,
            VK,
            OV,
            LI,
        };

    /**
     * A public read-only list of all the '<em><b>Soort Bedrijfs Regel</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<SoortBedrijfsRegel> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Soort Bedrijfs Regel</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SoortBedrijfsRegel get(String literal)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            SoortBedrijfsRegel result = VALUES_ARRAY[i];
            if (result.toString().equals(literal))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Soort Bedrijfs Regel</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SoortBedrijfsRegel getByName(String name)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            SoortBedrijfsRegel result = VALUES_ARRAY[i];
            if (result.getName().equals(name))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Soort Bedrijfs Regel</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SoortBedrijfsRegel get(int value)
    {
        switch (value)
        {
            case ID_VALUE: return ID;
            case UC_VALUE: return UC;
            case WB_VALUE: return WB;
            case VK_VALUE: return VK;
            case OV_VALUE: return OV;
            case LI_VALUE: return LI;
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
    private SoortBedrijfsRegel(int value, String name, String literal)
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
    
} //SoortBedrijfsRegel
