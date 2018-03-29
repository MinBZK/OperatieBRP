/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.jmxcollector;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "nl.bzk.algemeen.test:name=TEST", description = "JMX Service voor test.")
public class JmxObject {

    private int writableAttribuut = 10;

    @ManagedOperation(description = "Methode zonder return")
    public void methodNoReturn() {}

    @ManagedOperation(description = "Methode met return")
    public String methodWithReturn() {
        return "All ok";
    }

    @ManagedAttribute(description = "Readonly attribuut")
    public int getReadonlyAttribuut() {
        return 42;
    }

    @ManagedAttribute(description = "Null attribuut")
    public String getNullAttribuut() {
        return null;
    }

    @ManagedAttribute(description = "Writable attribuut")
    public int getWritableAttribuut() {
        return writableAttribuut;
    }

    @ManagedAttribute(description = "Writable attribuut")
    public void setWritableAttribuut(final int value) {
        writableAttribuut = value;
    }

    @ManagedAttribute(description = "Serialization problem attribuut (class cast)")
    public Object getSerializationProblem() {
        return new Object();
    }

    @ManagedAttribute(description = "Serialization problem attribuut (not serializable)")
    public Object getSerializationWriteObjectProblem() {
        return new Serializable() {
            private static final long serialVersionUID = 1L;

            private void writeObject(final java.io.ObjectOutputStream out) throws IOException {
                out.writeObject(new Object());
            }

            private void readObject(final java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {}

            private void readObjectNoData() throws ObjectStreamException {}
        };
    }



}
