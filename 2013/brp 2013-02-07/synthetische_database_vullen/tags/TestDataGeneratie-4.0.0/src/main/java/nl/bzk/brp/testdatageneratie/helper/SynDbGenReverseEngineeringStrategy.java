/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.helper;

import java.util.List;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;


public class SynDbGenReverseEngineeringStrategy extends DelegatingReverseEngineeringStrategy {

    public SynDbGenReverseEngineeringStrategy(final ReverseEngineeringStrategy delegate) {
        super(delegate);
    }

    @Override
    public boolean excludeForeignKeyAsCollection(final String keyname, final TableIdentifier fromTable,
            @SuppressWarnings("rawtypes") final List fromColumns, final TableIdentifier referencedTable,
            @SuppressWarnings("rawtypes") final List referencedColumns)
    {
        return true;
    }

}
