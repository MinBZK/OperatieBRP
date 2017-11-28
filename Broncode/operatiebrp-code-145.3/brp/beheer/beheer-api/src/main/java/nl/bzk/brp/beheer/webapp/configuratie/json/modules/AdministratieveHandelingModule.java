/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandelingGedeblokkeerdeRegel;
import nl.bzk.brp.beheer.webapp.view.ActieDetailView;
import nl.bzk.brp.beheer.webapp.view.ActieListView;
import org.springframework.stereotype.Component;

/**
 * AdministratieveHandeling module voor serializen en deserializen van AdministratieveHandeling en Actie.
 */
@Component
public class AdministratieveHandelingModule extends SimpleModule {

    /**
     * datum  tijd formaat voor timestamps.
     */
    public static final String DATUM_TIJD_FORMAAT = "yyyy-MM-dd HH:mm:ss";

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param administratieveHandelingSerializer specifieke serializer voor administratieve handelingen
     * @param actieListViewSerializer specifieke serializer voor acties in een lijst
     * @param gedeblokkeerdeMeldingSerializer specifieke serializer voor gedeblokkeerde meldingen
     * @param actieDetailViewSerializer specifieke serializer voor actie details
     */
    @Inject
    public AdministratieveHandelingModule(
            final AdministratieveHandelingSerializer administratieveHandelingSerializer,
            final ActieListViewSerializer actieListViewSerializer,
            final GedeblokkeerdeMeldingSerializer gedeblokkeerdeMeldingSerializer,
            final ActieDetailViewSerializer actieDetailViewSerializer) {
        addSerializer(AdministratieveHandeling.class, administratieveHandelingSerializer);
        addSerializer(ActieListView.class, actieListViewSerializer);
        addSerializer(AdministratieveHandelingGedeblokkeerdeRegel.class, gedeblokkeerdeMeldingSerializer);
        addSerializer(ActieDetailView.class, actieDetailViewSerializer);
    }

    @Override
    public final String getModuleName() {
        return "AdministratieveHandelingModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
