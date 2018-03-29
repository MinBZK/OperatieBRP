/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.routering.runtime;

import org.apache.activemq.store.jdbc.Statements;

public class CreateActivemqStatements {

    public static final void main(String[] args) {
        final Statements statements = new Statements();
        System.out.println("activemq-create.sql");
        for (final String statement : statements.getCreateSchemaStatements()) {
            System.out.println(statement);
        }
        System.out.println("activemq-drop.sql");
        for (final String statement : statements.getDropSchemaStatements()) {
            System.out.println(statement);
        }
    }
}
