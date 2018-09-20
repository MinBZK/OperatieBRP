/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.schema;

import java.util.NoSuchElementException;

import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.AttribuutType;
import nl.bzk.brp.ecore.bmr.BasisType;
import nl.bzk.brp.ecore.bmr.ObjectType;


public class Kolom extends SchemaElement implements TabelFeature {

    private final Tabel        tabel;
    private final Attribuut    attribuut;
    private AttribuutType      attribuutType;
    private final CharSequence nullability;
    private final boolean      idKolom;

    public Kolom(final Attribuut attribuut, final Tabel tabel) {
        super(attribuut.getIdentifierDB());
        this.tabel = tabel;
        this.attribuut = attribuut;
        if (attribuut.getType() instanceof AttribuutType) {
            attribuutType = (AttribuutType) attribuut.getType();

        } else if (attribuut.getType() instanceof ObjectType) {
            try {
                attribuutType = (AttribuutType) ((ObjectType) attribuut.getType()).getIdAttribuut().getType();
            } catch (NoSuchElementException e) {
                throw e;
            }
        }
        nullability = nullability();
        idKolom = attribuut.isIdentifier();
    }

    public CharSequence getSyncID() {
        return Long.toString(attribuut.getSyncId());
    }

    public CharSequence getJavaIdentifier() {
        return attribuut.getIdentifierCode();
    }

    public boolean isForeignKey() {
        return attribuut.getType().isObjectType();
    }

    public ObjectType getForeignObjectType() {
        if (isForeignKey()) {
            return (ObjectType) attribuut.getType();
        }
        return null;
    }

    public CharSequence getAttribuutTypeAlsCommentaar() {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append("/* ").append(attribuutType.getIdentifierDB()).append(" */");
        return resultaat;
    }

    /**
     * @return the dataType
     */
    public CharSequence getDataType() {
        return dataType();
    }

    /**
     * @return the nullability
     */
    public CharSequence getNullability() {
        return nullability;
    }

    /**
     * @return the idKolom
     */
    public boolean isIdentifierKolom() {
        return idKolom;
    }

    private CharSequence dataType() {
        BasisType basisType = attribuutType.getType();
        DataType datatype = DataType.getDataType(basisType.getNaam().toLowerCase());
        if (datatype == null) {
            throw new IllegalStateException("Geen datatype gevonden voor: " + basisType.getNaam().toLowerCase());
        }
        return datatype.getPostgresType(attribuutType.getMaximumLengte(), attribuutType.getAantalDecimalen());
    }

    private CharSequence nullability() {
        String resultaat;
        if (attribuut.isVerplicht()) {
            resultaat = " NOT NULL ";
        } else {
            resultaat = "";
        }
        return resultaat;
    }

    /**
     * @return the tabel
     */
    public Tabel getTabel() {
        return tabel;
    }

    /**
     * @return the attribuut
     */
    public Attribuut getAttribuut() {
        return attribuut;
    }
}
