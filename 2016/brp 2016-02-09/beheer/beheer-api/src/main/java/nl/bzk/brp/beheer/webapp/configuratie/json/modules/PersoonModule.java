/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import nl.bzk.brp.beheer.webapp.view.PersoonDetailView;
import nl.bzk.brp.beheer.webapp.view.PersoonListView;

/**
 * Persoon Module.
 */
public class PersoonModule extends SimpleModule {

    /**
     * Default constructor.
     */
    public PersoonModule() {
        addSerializer(PersoonListView.class, new PersoonListSerializer());
        addSerializer(PersoonDetailView.class, new PersoonDetailSerializer());
    }

    @Override
    public final String getModuleName() {
        return "BerichtModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
