/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.brp.beheer.webapp.view.BerichtDetailView;
import nl.bzk.brp.beheer.webapp.view.BerichtListView;
import org.springframework.stereotype.Component;

/**
 * Bericht module.
 */
@Component
public class BerichtModule extends SimpleModule {

    /** datum tijd formaat voor timestamps. */
    public static final String DATUM_TIJD_FORMAAT = "yyyy-MM-dd HH:mm:ss";

    /** veld id. */
    public static final String ID = "id";
    /** veld soort. */
    public static final String SOORT = "soort";
    /** veld soort. */
    public static final String SOORT_NAAM = "soortNaam";
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
    /** veld soortSynchronisatie. */
    public static final String SOORT_SYNCHRONISATIE_NAAM = "soortSynchronisatieNaam";
    /** veld verwerkingswijze. */
    public static final String VERWERKINGSWIJZE = "verwerkingswijze";
    /** veld verwerkingswijze. */
    public static final String VERWERKINGSWIJZE_NAAM = "verwerkingswijzeNaam";
    /** veld bijhouding. */
    public static final String BIJHOUDING = "bijhouding";
    /** veld bijhouding. */
    public static final String BIJHOUDING_NAAM = "bijhoudingNaam";
    /** veld data. */
    public static final String DATA = "data";
    /** veld hoogsteMeldingsniveau. */
    public static final String HOOGSTE_MELDINGSNIVEAU = "hoogsteMeldingsniveau";
    /** veld verwerking. */
    public static final String VERWERKING = "verwerking";
    /** veld verwerking. */
    public static final String VERWERKING_NAAM = "verwerkingNaam";
    /** veld ontvangendePartijCode. */
    public static final String ONTVANGENDE_PARTIJ_CODE = "ontvangendePartijCode";
    /** veld ontvangendePartijNaamCode. */
    public static final String ONTVANGENDE_PARTIJ_NAAM_CODE = "ontvangendePartijNaamCode";
    /** veld zendendeSysteem. */
    public static final String ZENDENDE_SYSTEEM = "zendendeSysteem";
    /** veld zendendePartij. */
    public static final String ZENDENDE_PARTIJ_CODE = ZENDENDE_PARTIJ;
    /** veld zendendePartijNaamCode. */
    public static final String ZENDENDE_PARTIJ_NAAM_CODE = "zendendePartijNaamCode";
    /** veld richting. */
    public static final String RICHTING = "richting";
    /** veld categorieDienst. */
    public static final String DIENST = "dienst";
    /** veld categorieDienst. */
    public static final String DIENST_NAAM = "dienstNaam";
    /** veld abonnementId. */
    public static final String LEVERINGSAUTORISATIE = "leveringsautorisatie";
    /** veld abonnementId. */
    public static final String LEVERINGSAUTORISATIE_NAAM = "leveringsautorisatieNaam";
    /** veld administratieveHandelingId. */
    public static final String ADMINISTRATIEVE_HANDELING_ID = "administratieveHandelingId";
    /** veld administratieveHandelingSoortNaam. */
    public static final String ADMINISTRATIEVE_HANDELING_SOORT_NAAM = "administratieveHandelingSoortNaam";

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param berichtListSerializer
     *            specifieke serializer voor berichten in een lijst
     * @param berichtDetailSerializer
     *            specifieke serializer voor bericht details
     */
    @Inject
    public BerichtModule(final BerichtListSerializer berichtListSerializer, final BerichtDetailSerializer berichtDetailSerializer) {
        addSerializer(BerichtListView.class, berichtListSerializer);
        addSerializer(BerichtDetailView.class, berichtDetailSerializer);
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
