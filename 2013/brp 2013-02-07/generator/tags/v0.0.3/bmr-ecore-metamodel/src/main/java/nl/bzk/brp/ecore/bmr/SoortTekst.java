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
 * A representation of the literals of the enumeration '<em><b>Soort Tekst</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see nl.bzk.brp.ecore.bmr.BmrPackage#getSoortTekst()
 * @model
 * @generated
 */
public enum SoortTekst implements Enumerator
{
    /**
     * The '<em><b>DEF</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DEF_VALUE
     * @generated
     * @ordered
     */
    DEF(1, "DEF", "DEF"),

    /**
     * The '<em><b>DEFT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DEFT_VALUE
     * @generated
     * @ordered
     */
    DEFT(2, "DEFT", "DEFT"),

    /**
     * The '<em><b>POP</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #POP_VALUE
     * @generated
     * @ordered
     */
    POP(3, "POP", "POP"),

    /**
     * The '<em><b>MOB</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MOB_VALUE
     * @generated
     * @ordered
     */
    MOB(4, "MOB", "MOB"),

    /**
     * The '<em><b>UITT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #UITT_VALUE
     * @generated
     * @ordered
     */
    UITT(5, "UITT", "UITT"),

    /**
     * The '<em><b>CONT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CONT_VALUE
     * @generated
     * @ordered
     */
    CONT(6, "CONT", "CONT"),

    /**
     * The '<em><b>PSA</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PSA_VALUE
     * @generated
     * @ordered
     */
    PSA(7, "PSA", "PSA"), /**
     * The '<em><b>REAT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #REAT_VALUE
     * @generated
     * @ordered
     */
    REAT(8, "REAT", "REAT"), /**
     * The '<em><b>AAN</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #AAN_VALUE
     * @generated
     * @ordered
     */
    AAN(9, "AAN", "AAN"),

    /**
     * The '<em><b>LOG</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #LOG_VALUE
     * @generated
     * @ordered
     */
    LOG(10, "LOG", "LOG"),

    /**
     * The '<em><b>TUP</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #TUP_VALUE
     * @generated
     * @ordered
     */
    TUP(11, "TUP", "TUP"), /**
     * The '<em><b>XSD</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #XSD_VALUE
     * @generated
     * @ordered
     */
    XSD(12, "XSD", "XSD"), /**
     * The '<em><b>XML</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #XML_VALUE
     * @generated
     * @ordered
     */
    XML(13, "XML", "XML"),

    /**
     * The '<em><b>VOR</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #VOR_VALUE
     * @generated
     * @ordered
     */
    VOR(14, "VOR", "VOR"),

    /**
     * The '<em><b>BGR</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #BGR_VALUE
     * @generated
     * @ordered
     */
    BGR(15, "BGR", "BGR");

    /**
     * The '<em><b>DEF</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>DEF</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DEF
     * @model
     * @generated
     * @ordered
     */
    public static final int DEF_VALUE = 1;

    /**
     * The '<em><b>DEFT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>DEFT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DEFT
     * @model
     * @generated
     * @ordered
     */
    public static final int DEFT_VALUE = 2;

    /**
     * The '<em><b>POP</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>POP</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #POP
     * @model
     * @generated
     * @ordered
     */
    public static final int POP_VALUE = 3;

    /**
     * The '<em><b>MOB</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>MOB</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MOB
     * @model
     * @generated
     * @ordered
     */
    public static final int MOB_VALUE = 4;

    /**
     * The '<em><b>UITT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>UITT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #UITT
     * @model
     * @generated
     * @ordered
     */
    public static final int UITT_VALUE = 5;

    /**
     * The '<em><b>CONT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>CONT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CONT
     * @model
     * @generated
     * @ordered
     */
    public static final int CONT_VALUE = 6;

    /**
     * The '<em><b>PSA</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PSA</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PSA
     * @model
     * @generated
     * @ordered
     */
    public static final int PSA_VALUE = 7;

    /**
     * The '<em><b>REAT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>REAT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #REAT
     * @model
     * @generated
     * @ordered
     */
    public static final int REAT_VALUE = 8;

    /**
     * The '<em><b>AAN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>AAN</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #AAN
     * @model
     * @generated
     * @ordered
     */
    public static final int AAN_VALUE = 9;

    /**
     * The '<em><b>LOG</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>LOG</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #LOG
     * @model
     * @generated
     * @ordered
     */
    public static final int LOG_VALUE = 10;

    /**
     * The '<em><b>TUP</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>TUP</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #TUP
     * @model
     * @generated
     * @ordered
     */
    public static final int TUP_VALUE = 11;

    /**
     * The '<em><b>XSD</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>XSD</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #XSD
     * @model
     * @generated
     * @ordered
     */
    public static final int XSD_VALUE = 12;

    /**
     * The '<em><b>XML</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>XML</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #XML
     * @model
     * @generated
     * @ordered
     */
    public static final int XML_VALUE = 13;

    /**
     * The '<em><b>VOR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>VOR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #VOR
     * @model
     * @generated
     * @ordered
     */
    public static final int VOR_VALUE = 14;

    /**
     * The '<em><b>BGR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BGR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #BGR
     * @model
     * @generated
     * @ordered
     */
    public static final int BGR_VALUE = 15;

    /**
     * An array of all the '<em><b>Soort Tekst</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final SoortTekst[] VALUES_ARRAY =
        new SoortTekst[]
        {
            DEF,
            DEFT,
            POP,
            MOB,
            UITT,
            CONT,
            PSA,
            REAT,
            AAN,
            LOG,
            TUP,
            XSD,
            XML,
            VOR,
            BGR,
        };

    /**
     * A public read-only list of all the '<em><b>Soort Tekst</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<SoortTekst> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Soort Tekst</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SoortTekst get(String literal)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            SoortTekst result = VALUES_ARRAY[i];
            if (result.toString().equals(literal))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Soort Tekst</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SoortTekst getByName(String name)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            SoortTekst result = VALUES_ARRAY[i];
            if (result.getName().equals(name))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Soort Tekst</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SoortTekst get(int value)
    {
        switch (value)
        {
            case DEF_VALUE: return DEF;
            case DEFT_VALUE: return DEFT;
            case POP_VALUE: return POP;
            case MOB_VALUE: return MOB;
            case UITT_VALUE: return UITT;
            case CONT_VALUE: return CONT;
            case PSA_VALUE: return PSA;
            case REAT_VALUE: return REAT;
            case AAN_VALUE: return AAN;
            case LOG_VALUE: return LOG;
            case TUP_VALUE: return TUP;
            case XSD_VALUE: return XSD;
            case XML_VALUE: return XML;
            case VOR_VALUE: return VOR;
            case BGR_VALUE: return BGR;
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
    private SoortTekst(int value, String name, String literal)
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
    
} //SoortTekst
