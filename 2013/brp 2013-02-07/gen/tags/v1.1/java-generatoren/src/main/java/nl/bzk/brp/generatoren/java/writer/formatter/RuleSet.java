/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.writer.formatter;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

/**
 * Ruleset voor digester voor het parsen van Eclipse formatter configuratie bestanden.
 */
class RuleSet extends RuleSetBase {

    @Override
    public void addRuleInstances(final Digester digester) {
        digester.addObjectCreate("profiles", FormatterProfielen.class);
        digester.addObjectCreate("profiles/profile", FormatterProfiel.class);
        digester.addObjectCreate("profiles/profile/setting", ProfielSetting.class);

        digester.addSetNext("profiles/profile", "addProfile");
        digester.addSetNext("profiles/profile/setting", "addSetting");

        digester.addSetProperties("profiles/profile", "kind", "kind");
        digester.addSetProperties("profiles/profile/setting", "id", "id");
        digester.addSetProperties("profiles/profile/setting", "value", "value");
    }

}
