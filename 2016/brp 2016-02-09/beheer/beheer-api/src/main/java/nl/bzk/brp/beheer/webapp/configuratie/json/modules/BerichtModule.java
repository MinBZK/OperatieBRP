/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import nl.bzk.brp.beheer.webapp.view.BerichtDetailView;
import nl.bzk.brp.beheer.webapp.view.BerichtListView;

/**
 * Bericht module.
 */
public class BerichtModule extends SimpleModule {

    /** datum tijd formaat voor timestamps. */
    public static final String DATUM_TIJD_FORMAAT = "yyyy-MM-dd HH:mm:ss";

    /** veld iD. */
    public static final String ID = "iD";
    /** veld soort. */
    public static final String SOORT = "soort";
    /** veld zendendePartij. */
    public static final String ZENDENDE_PARTIJ = "zendendePartij";
    /** veld ontvangendePartij. */
    public static final String ONTVANGENDE_PARTIJ = "ontvangendePartij";
    /** veld referentienummer. */
    public static final String REFERENTIENUMMER = "referentienummer";
    /** veld crossReferentienummer. */
    public static final String CROSS_REFERENTIENUMMER = "crossReferentienummer";
    /** veld verzenddatum. */
    public static final String VERZENDDATUM = "verzenddatum";
    /** veld ontvangstdatum. */
    public static final String ONTVANGSTDATUM = "ontvangstdatum";
    /** veld soortSynchronisatie. */
    public static final String SOORT_SYNCHRONISATIE = "soortSynchronisatie";
    /** veld verwerkingswijze. */
    public static final String VERWERKINGSWIJZE = "verwerkingswijze";
    /** veld bijhouding. */
    public static final String BIJHOUDING = "bijhouding";
    /** veld data. */
    public static final String DATA = "data";
    /** veld hoogsteMeldingsniveau. */
    public static final String HOOGSTE_MELDINGSNIVEAU = "hoogsteMeldingsniveau";
    /** veld verwerking. */
    public static final String VERWERKING = "verwerking";
    /** veld ontvangendeSysteem. */
    public static final String ONTVANGENDE_SYSTEEM = "ontvangendeSysteem";
    /** veld ontvangendePartijCode. */
    public static final String ONTVANGENDE_PARTIJ_CODE = "ontvangendePartijCode";
    /** veld zendendeSysteem. */
    public static final String ZENDENDE_SYSTEEM = "zendendeSysteem";
    /** veld zendendePartijCode. */
    public static final String ZENDENDE_PARTIJ_CODE = ZENDENDE_PARTIJ;
    /** veld richting. */
    public static final String RICHTING = "richting";
    /** veld categorieDienst. */
    public static final String CATEGORIE_DIENST = "categorieDienst";
    /** veld abonnementId. */
    public static final String ABONNEMENT_ID = "abonnementId";
    /** veld abonnementId. */
    public static final String ABONNEMENT_NAAM = "abonnementNaam";
    /** veld antwoordOpId. */
    public static final String ANTWOORD_OP_ID = "antwoordOpId";
    /** veld antwoordOpSoortNaam. */
    public static final String ANTWOORD_OP_SOORT_NAAM = "antwoordOpSoortNaam";
    /** veld administratieveHandelingId. */
    public static final String ADMINISTRATIEVE_HANDELING_ID = "administratieveHandelingId";
    /** veld administratieveHandelingSoortNaam. */
    public static final String ADMINISTRATIEVE_HANDELING_SOORT_NAAM = "administratieveHandelingSoortNaam";

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public BerichtModule() {
        addSerializer(BerichtListView.class, new BerichtListSerializer());
        addSerializer(BerichtDetailView.class, new BerichtDetailSerializer());
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
