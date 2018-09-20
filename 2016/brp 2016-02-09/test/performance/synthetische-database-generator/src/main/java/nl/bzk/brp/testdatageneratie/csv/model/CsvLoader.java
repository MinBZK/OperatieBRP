/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.testdatageneratie.csv.processor.AbstractCsvProc;

import org.apache.log4j.Logger;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

public class CsvLoader<A> {
    private static Logger log = Logger.getLogger(CsvLoader.class);

    private final File file;
    private List<A> objecten;
    private final AbstractCsvProc<A> procs;

    public CsvLoader(final String path, final AbstractCsvProc<A> procs) throws IOException {
        file = new File(path, procs.getCsvFileName());
        this.procs = procs;
    }

    public List<A> leesdata() throws IOException {

        ICsvBeanReader beanReader = null;
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(file.getPath());
            beanReader = new CsvBeanReader(new InputStreamReader(in), CsvPreference.STANDARD_PREFERENCE);

            // the header elements are used to map the values to the bean (names must match)
            final String[] header = beanReader.getHeader(true);

            A object;
            objecten = new ArrayList<A>();

            while ((object = beanReader.read(procs.getLoadingClass(), header, procs.getProcessor())) != null) {
                objecten.add(object);
            }
        }
        catch (NullPointerException e) {
            log.error("Kan geen csv-resources vinden op deze locatie: [" + file.getPath() + "] Programma wordt beeindigd.");
            throw e;
        } finally {
            if (beanReader != null) {
                beanReader.close();
            }
        }
        return objecten;
    }

    public List<A> getObjecten() {
        return objecten;
    }

    public void setObjecten(final List<A> objecten) {
        this.objecten = objecten;
    }

}
