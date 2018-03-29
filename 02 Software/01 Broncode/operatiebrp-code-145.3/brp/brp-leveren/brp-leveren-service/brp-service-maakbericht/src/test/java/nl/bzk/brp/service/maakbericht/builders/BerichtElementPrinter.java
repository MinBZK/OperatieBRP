/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BerichtElementAttribuut;

/**
 * BerichtElementPrinter.
 */
public class BerichtElementPrinter {
    private int indent;

    private Writer writer;

    public String maakAfdruk(final BerichtElement berichtElement) {
        this.writer = new StringWriter();
        print(berichtElement);
        return writer.toString();
    }


    private void print(final BerichtElement berichtElement) {
        if (berichtElement.getWaarde() != null) {
            doWrite(String.format("%s [e] %s [%s]%n", geefIndent(), berichtElement.getNaam(), berichtElement.getWaarde()));
        } else {
            doWrite(String.format("%s [e] %s%n", geefIndent(), berichtElement.getNaam()));
        }
        for (BerichtElementAttribuut berichtElementAttribuut : berichtElement.getAttributen()) {
            print(berichtElementAttribuut);
        }
        for (BerichtElement element : berichtElement.getElementen()) {
            indent++;
            print(element);
            indent--;
        }
    }

    private void print(final BerichtElementAttribuut berichtElementAttribuut) {
        indent++;
        doWrite(String.format("%s [a] %s [%s]%n", geefIndent(), berichtElementAttribuut.getNaam(), berichtElementAttribuut.getWaarde()));
        indent--;
    }

    private String geefIndent() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("\t");
        }
        return sb.toString();

    }

    private void doWrite(final String string, final Object... args) {
        try {
            writer.write(String.format(string, args));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
