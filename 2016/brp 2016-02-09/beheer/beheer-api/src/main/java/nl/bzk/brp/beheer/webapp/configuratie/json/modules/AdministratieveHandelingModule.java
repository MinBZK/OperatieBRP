/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import nl.bzk.brp.model.beheer.view.ActieView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingGedeblokkeerdeMeldingModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

/**
 * AdministratieveHandeling module voor serializen en deserializen van AdministratieveHandeling en Actie.
 */
public class AdministratieveHandelingModule extends SimpleModule {

    /** datum tijd formaat voor timestamps. */
    public static final String DATUM_TIJD_FORMAAT = "yyyy-MM-dd HH:mm:ss";

    private static final long serialVersionUID = 1L;

    /**
     * contructor voor nieuwe AdministratieveHandelingModule.
     */
    public AdministratieveHandelingModule() {
        addSerializer(AdministratieveHandelingModel.class, new AdministratieveHandelingSerializer());
        addSerializer(AdministratieveHandelingGedeblokkeerdeMeldingModel.class, new GedeblokkeerdeMeldingSerializer());
        addSerializer(ActieModel.class, new ActieSerializer());
        addSerializer(ActieView.class, new ActieViewSerializer());
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
