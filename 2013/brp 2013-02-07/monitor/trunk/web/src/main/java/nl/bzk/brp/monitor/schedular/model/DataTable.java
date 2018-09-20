/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.schedular.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Data tabel.
 *
 */
public class DataTable {

    private List<Col> cols = new ArrayList<Col>();
    private List<Row> rows = new ArrayList<Row>();

    /**
     * Constructor van een data tabel.
     *
     * @param id id van kolom
     * @param type type van kolom zie
     *            http://code.google.com/apis/chart/interactive/docs/gallery/linechart.html#Data_Format
     * @param label label van kolom
     */
    public void addColumn(final String id, final String type, final String label) {
        cols.add(new Col(id, label, type));
    }

    /**
     * Voeg rij toe. Getallen moeten als een int opgegeven worden.
     *
     * @param waarden lijst van waarden van de rij.
     */
    public void addRow(final Object... waarden) {
        Row rowswaarde = new Row();
        rowswaarde.addWaarden(waarden);
        rows.add(rowswaarde);
    }

    public List<Col> getCols() {
        return cols;
    }

    public void setCols(final List<Col> cols) {
        this.cols = cols;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(final List<Row> rows) {
        this.rows = rows;
    }
}
