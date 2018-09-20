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
 * A representation of the literals of the enumeration '<em><b>In Set Of Model</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getInSetOfModel()
 * @model
 * @generated
 */
public enum InSetOfModel implements Enumerator
{
    /**
     * The '<em><b>Set</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SET_VALUE
     * @generated
     * @ordered
     */
    SET(0, "Set", "S"), /**
     * The '<em><b>Model</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MODEL_VALUE
     * @generated
     * @ordered
     */
    MODEL(1, "Model", "M"), /**
     * The '<em><b>Beide</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #BEIDE_VALUE
     * @generated
     * @ordered
     */
    BEIDE(2, "Beide", "B")
    ;

    /**
     * The '<em><b>Set</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Set</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SET
     * @model name="Set" literal="S"
     * @generated
     * @ordered
     */
    public static final int SET_VALUE = 0;

/**
     * The '<em><b>Model</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Model</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MODEL
     * @model name="Model" literal="M"
     * @generated
     * @ordered
     */
    public static final int MODEL_VALUE = 1;

/**
     * The '<em><b>Beide</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Beide</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #BEIDE
     * @model name="Beide" literal="B"
     * @generated
     * @ordered
     */
    public static final int BEIDE_VALUE = 2;

    /**
     * An array of all the '<em><b>In Set Of Model</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final InSetOfModel[] VALUES_ARRAY =
        new InSetOfModel[]
        {
            SET,
            MODEL,
            BEIDE,
        };

    /**
     * A public read-only list of all the '<em><b>In Set Of Model</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<InSetOfModel> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>In Set Of Model</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static InSetOfModel get(String literal)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            InSetOfModel result = VALUES_ARRAY[i];
            if (result.toString().equals(literal))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>In Set Of Model</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static InSetOfModel getByName(String name)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            InSetOfModel result = VALUES_ARRAY[i];
            if (result.getName().equals(name))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>In Set Of Model</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static InSetOfModel get(int value)
    {
        switch (value)
        {
            case SET_VALUE: return SET;
            case MODEL_VALUE: return MODEL;
            case BEIDE_VALUE: return BEIDE;
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
    private InSetOfModel(int value, String name, String literal)
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
    
} //InSetOfModel
