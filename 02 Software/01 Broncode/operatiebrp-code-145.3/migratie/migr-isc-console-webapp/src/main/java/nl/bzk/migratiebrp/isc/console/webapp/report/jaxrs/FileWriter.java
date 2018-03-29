/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.webapp.report.jaxrs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 * To output files via JAX-RS.
 */
@Provider
@Produces({"text/html", "image/*"})
public final class FileWriter implements MessageBodyWriter<File> {

    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return type.equals(File.class);
    }

    @Override
    public long getSize(final File file, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return file.length();
    }

    @Override
    public void writeTo(
            final File file,
            final java.lang.Class<?> type,
            final Type genericType,
            final Annotation[] annotations,
            final MediaType mediaType,
            final MultivaluedMap<java.lang.String, java.lang.Object> httpHeaders,
            final java.io.OutputStream entityStream) throws IOException {
        try (FileInputStream fin = new FileInputStream(file)) {
            int c;
            while ((c = fin.read()) != -1) {
                entityStream.write(c);
            }
        }
    }
}
