/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.schema;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;

import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.BedrijfsRegel;


abstract public class AbstractConstraint extends SchemaElement {

    private BedrijfsRegel bedrijfsRegel;

    public AbstractConstraint(final BedrijfsRegel bedrijfsRegel) {
        super(bedrijfsRegel.getIdentifierDB());
        this.bedrijfsRegel = bedrijfsRegel;
    }

    abstract public String getKeywords();

    public CharSequence getKolommenAlsString() {
        return Joiner.on(", ").skipNulls()
                .join(Collections2.transform(bedrijfsRegel.getAttributen(), new Function<Attribuut, String>() {

                    @Override
                    public String apply(final Attribuut attribuut) {
                        if (attribuut != null) {
                            return attribuut.getIdentifierDB();
                        }
                        return null;
                    }
                }));
    }
}
