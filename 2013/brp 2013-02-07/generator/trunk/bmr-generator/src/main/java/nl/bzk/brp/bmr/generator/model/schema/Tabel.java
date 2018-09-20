/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.schema;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.Tuple;


public class Tabel extends SchemaElement {

    private final ObjectType                    objectType;
    private Sequence                            sequence;
    private final DatabaseSchema                schema;

    private final List<Kolom>                   kolommen              = new ArrayList<Kolom>();

    private final List<AbstractTabelConstraint> constraints           = new ArrayList<AbstractTabelConstraint>();

    private final List<ForeignKeyConstraint>    foreignKeyConstraints = new ArrayList<ForeignKeyConstraint>();

    private final List<DataRegel>               dataRegels            = new ArrayList<DataRegel>();

    public Tabel(final ObjectType objectType, final DatabaseSchema schema) {
        super(objectType.getIdentifierDB());
        this.objectType = objectType;
        this.schema = schema;
    }

    public AbstractTabelConstraint addConstraint(final AbstractTabelConstraint constraint) {
        constraints.add(constraint);
        return constraint;
    }

    public ForeignKeyConstraint addForeignKeyConstraint(final Tabel tabel) {
        ForeignKeyConstraint constraint = new ForeignKeyConstraint(this, tabel);
        foreignKeyConstraints.add(constraint);
        return constraint;
    }

    public Kolom addKolom(final Attribuut attribuut) {
        Kolom kolom = new Kolom(attribuut, this);
        kolommen.add(kolom);

        if (heeftTupels()) {
            addDataRegels(kolom);
        }
        return kolom;
    }

    public Iterable<TabelFeature> getFeatures() {
        List<TabelFeature> features = new ArrayList<TabelFeature>();
        features.addAll(kolommen);
        features.addAll(constraints);
        return features;
    }

    public Kolom getIdentifierKolom() {
        return Iterables.find(getKolommen(), new Predicate<Kolom>() {

            @Override
            public boolean apply(final Kolom kolom) {
                return kolom.isIdentifierKolom();
            }
        });
    }

    public String getQualifiedNaam() {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(getSchema().getNaam());
        resultaat.append(".");
        resultaat.append(getNaam());
        return resultaat.toString();
    }

    /**
     * @return the kolommen
     */
    public List<Kolom> getKolommen() {
        return kolommen;
    }

    /**
     * @return the schema
     */
    public DatabaseSchema getSchema() {
        return schema;
    }

    public CharSequence getSyncID() {
        return Long.toString(objectType.getSyncId());
    }

    public CharSequence getJavaIdentifier() {
        return objectType.getIdentifierCode();
    }

    /**
     * @return the sequence
     */
    public Sequence getSequence() {
        return sequence;
    }

    public List<DataRegel> getDataRegels() {
        return dataRegels;
    }

    public boolean heeftKunstmatigeSleutel() {
        return objectType.isKunstmatigeSleutel();
    }

    public boolean heeftTupels() {
        return objectType.getTuples().size() > 0;
    }

    public void setSequence(final Sequence sequence) {
        this.sequence = sequence;
    }

    /**
     * @return the foreignKeyConstraints
     */
    public List<ForeignKeyConstraint> getForeignKeyConstraints() {
        return foreignKeyConstraints;
    }

    /**
     * Deze methode vult de inhoud/regels voor een kolom.
     *
     * @param kolom Kolom
     */
    private void addDataRegels(final Kolom kolom) {
        for (Tuple tuple : objectType.getTuples()) {
            int tupleIndex = objectType.getTuples().indexOf(tuple) + 1;

            DataRegel dataRegel;
            // Als de regel nog niet bestaat dan een nieuwe regel aanmaken anders de regel ophalen om een kolom toe te
            // voegen
            if (dataRegels.size() < tupleIndex) {
                dataRegel = new DataRegel(this);
                dataRegels.add(dataRegel);
            } else {
                dataRegel = dataRegels.get(objectType.getTuples().indexOf(tuple));
            }

            String waarde = null;
            String kolomNaam = kolom.getNaam();

            // Mappen van Tuple veld naar kolom
            if ("ID".equals(kolomNaam)) {
                waarde = Integer.toString(tupleIndex);
            } else if ("Naam".equals(kolomNaam)) {
                waarde = tuple.getNaam();
            } else if ("Code".equals(kolomNaam)) {
                waarde = tuple.getCode();
            } else if ("Oms".equals(kolomNaam)) {
                waarde = tuple.getOmschrijving();
            } else if ("NaamMannelijk".equals(kolomNaam)) {
                waarde = tuple.getNaamMannelijk();
            } else if ("NaamVrouwelijk".equals(kolomNaam)) {
                waarde = tuple.getNaamVrouwelijk();
            } else if ("IndMaterieleHistorieVanToepa".equals(kolomNaam)) {
                waarde = tuple.getHeeftMaterieleHistorie().toString();
            } else if ("DatAanvGel".equals(kolomNaam)) {
                waarde = String.valueOf(tuple.getDatumAanvangGeldigheid());
            } else if ("DatEindeGel".equals(kolomNaam)) {
                waarde = String.valueOf(tuple.getDatumEindeGeldigheid());
            } else if ("CategorieSrtActie".equals(kolomNaam)) {
                waarde = String.valueOf(tuple.getCategorieSoortActie());
            } else if ("CategorieSrtDoc".equals(kolomNaam)) {
                waarde = String.valueOf(tuple.getCategorieSoortDocument());
            }

            dataRegel.addWaarden(kolom, waarde);
        }
    }
}
