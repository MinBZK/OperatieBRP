package nl.bzk.brp.funqmachine.datalezers

import com.google.common.base.Preconditions
import groovy.sql.Sql
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.datalezers.excel.DataSheets
import nl.bzk.brp.funqmachine.processors.SqlProcessor

/**
 * Kan data uit een ART inputsheet inlezen.
 * Zoekt de sheet 'Data' en de gegeven kolom.
 */
class ExcelDataLezer implements DataLezer {

    private String kolom
    private Sql sql

    /**
     * Creates an instance of this reader.
     *
     * @param sql (optionele) {@link Sql} instantie om te gebruiken
     *      om data op te halen
     * @param col De kolom die uitgelezen moet worden
     */
    ExcelDataLezer(Sql sql, String col) {
        Preconditions.checkArgument(col != null, 'Verwacht een waarde voor kolom [%s]', col)

        this.kolom = col
        this.sql = sql
    }

    /**
     * Creates an instance of this reader.
     * Gebruikt een verbinding met de Kern database om data op te halen.
     *
     * @param col De kolom die uitgelezen moet worden
     */
    ExcelDataLezer(String col) {
        this(new SqlProcessor(Database.KERN).sql, col)
    }

    @Override
    Map<String, Object> lees(final File file) {
        return read(file)
    }

    @Override
    Map<String, Object> lees(String bestandsNaam) {
        throw new UnsupportedOperationException('Kan alleen excel lezen als bekend is welke kolom')
    }

    private Map<String, Object> read(final File file) {
        def result = [:]
        DataSheets sheets = new DataSheets(file, false)
        sheets.laadKolomVanTestGeval(kolom, sql, result)

        return result
    }
}
