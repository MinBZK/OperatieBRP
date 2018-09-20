/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * IO utils.
 */
public final class IOUtils {

    /**
     * Instantieert een nieuwe IO utils.
     */
    private IOUtils() {
    }

    /**
     * Size of the byffer in bytes used to pipe data from one stream to another.
     **/
    private static final int PIPE_BUFFER_SIZE = 1024;

    /**
     * Writes every byte from the provided inputstream to the provided outputstream
     * and closes the in- and outputstreams.
     *
     * @param source the inputstream to read from
     * @param result the outputstream to write to
     * @throws java.io.IOException if an I/0  error occurs
     */
    public static void pipe(final InputStream source, final OutputStream result)
        throws IOException
    {
        pipeWithoutFlush(source, result);
        result.flush();
        result.close();
        source.close();
    }

    /**
     * Writes every byte from the provided inputstream to the provided outputstream.
     *
     * @param source the inputstream to read from
     * @param result the outputstream to write to
     * @throws java.io.IOException if an I/0  error occurs
     */
    public static void pipeWithoutFlush(final InputStream source, final OutputStream result)
        throws IOException
    {
        byte[] b = new byte[PIPE_BUFFER_SIZE];
        int bytesRead = 0;

        while ((bytesRead = source.read(b)) > -1) {
            result.write(b, 0, bytesRead);
        }
    }

    /**
     * Reads the inputstream into a String. When the stream is empty, it will be closed.
     *
     * @param is the inputstream
     * @return the String
     * @throws java.io.IOException if an error occurs while reading from the inputstream
     */
    public static String toString(final InputStream is) throws IOException {
        int bytesRead = 0;
        byte[] buffer = new byte[PIPE_BUFFER_SIZE];
        StringBuffer stringBuffer = new StringBuffer();
        while ((bytesRead = is.read(buffer)) > -1) {
            stringBuffer.append(new String(buffer, 0, bytesRead));
        }
        is.close();
        return stringBuffer.toString();
    }

    /**
     * Opens an inputstream to a location on the classpath relative to the provided class.
     *
     * @param callerClass the class
     * @param classpathLocation the relative (or absolute, if it starts with a '/') classpath
     * location
     * @return an new inputstream if the resource exists
     * @throws java.io.FileNotFoundException if the resource does not exist
     */
    private static InputStream getClasspathResourceStream(final Class callerClass,
                                                          final String classpathLocation)
        throws FileNotFoundException
    {
        InputStream resourceAsStream = callerClass.getResourceAsStream(classpathLocation);
        if (resourceAsStream != null) {
            return resourceAsStream;
        } else {
            throw new FileNotFoundException(classpathLocation);
        }
    }

    /**
     * Classpath resource exists.
     *
     * @param callerClass caller class
     * @param classpathLocation classpath location
     * @return boolean
     */
    public static boolean classpathResourceExists(final Class callerClass,
                                                  final String classpathLocation)
    {
        URL resource = callerClass.getResource(classpathLocation);
        if (resource != null) {
            return true;
        }
        return false;
    }

    /**
     * Classpath resource exists.
     *
     * @param classpathLocation classpath location
     * @return boolean
     */
    public static boolean classpathResourceExists(final String classpathLocation) {
        return classpathResourceExists(IOUtils.class, classpathLocation);
    }

    /**
     * Verkrijgt buffered classpath resource stream.
     *
     * @param callerClass caller class
     * @param classpathLocation classpath location
     * @return buffered classpath resource stream
     * @throws IOException iO exception
     */
    public static ByteArrayInputStream getBufferedClasspathResourceStream(final Class callerClass,
                                                                          final String classpathLocation)
        throws IOException
    {
        InputStream resourceStream =
                getClasspathResourceStream(callerClass, classpathLocation);
        ByteArrayInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = bufferInputStream(resourceStream);
        } finally {
            resourceStream.close();
        }
        return bufferedInputStream;
    }

    /**
     * Verkrijgt buffered classpath resource stream.
     *
     * @param classpathLocation classpath location
     * @return buffered classpath resource stream
     * @throws IOException iO exception
     */
    public static ByteArrayInputStream getBufferedClasspathResourceStream(final String classpathLocation)
        throws IOException
    {
        return getBufferedClasspathResourceStream(IOUtils.class, classpathLocation);
    }

    /**
     * Verkrijgt buffered classpath resource stream.
     *
     * @param caller caller
     * @param classpathLocation classpath location
     * @return buffered classpath resource stream
     * @throws IOException iO exception
     */
    public static InputStream getBufferedClasspathResourceStream(final Object caller,
                                                                 final String classpathLocation)
        throws IOException
    {
        return getBufferedClasspathResourceStream(caller.getClass(), classpathLocation);
    }

    /**
     * Returns the classpath location minus the classname of the provided class.
     * E.g. if the class is nl.ictu.eins.dido.utils.X.class, the base classpath location
     * is /nl/ictu/eins/dido/utils/.
     *
     * @param clazz the class
     * @return the base classpath
     */
    public static String getBaseClasspath(final Class clazz) {
        return "/" + clazz.getPackage().getName().replace('.', '/') + "/";
    }

    /**
     * Retrieves the class of the provided object and delegates to
     * #getBaseClasspath(Class.class).
     *
     * @param object the object
     * @return the base classpath
     */
    public static String getBaseClasspath(final Object object) {
        return getBaseClasspath(object.getClass());
    }

    /**
     * Creates a new ByteArrayOutputStream, pipes the data from the provided input stream
     * to the outputstream and creates a new ByteArrayInputStream based on the byte array
     * of the outputstream.
     * This utility can be used if you want to use an inputstream multiple times. This is
     * due to the fact that ByteArrayInputStreams provide mark and reset functionality.
     *
     * @param inputStream the inputstream to buffer
     * @return a new ByteArrayInputStream with the data from the provided inputstream
     * @throws java.io.IOException if an error occurs while copying the data from the
     * provided inputstream to the ByteArrayOutputStream
     */
    public static ByteArrayInputStream bufferInputStream(
            final InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pipe(inputStream, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * Convenience method to write a String to file.
     * @param file File to write o.
     * @param content String to be written to file
     * @param append if true, content will be appended to end of file. Otherwise
     * any existing file content will be overridden.
     * @throws java.io.IOException if an error occurs whilst writing to file
     */
    public static void writeToFile(final File file, final String content, final boolean append) throws IOException {
        final FileWriter writer = new FileWriter(file, append);
        try {
            writer.write(content);
        } finally {
            writer.close();
        }
    }
}
