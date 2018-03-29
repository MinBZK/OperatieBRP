/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl.BzmBrpService;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.sql.SqlHelper;

/**
 * Abstract BZM kanaal implementatie.
 */
public abstract class AbstractBzmKanaal extends AbstractKanaal {

    private static final String SELECT_OIN_FROM_PARTIJ = "SELECT oin FROM kern.partij WHERE id = ";
    private static final Logger LOG = LoggerFactory.getLogger();

    private final Map<String, Bericht> antwoorden = new HashMap<>();

    @Inject
    private SqlHelper sqlHelper;

    @Inject
    @Named("brpDataSource")
    private DataSource brpDataSource;

    private String oinTransporteur;
    private String oinOndertekenaar;

    /**
     * Geeft de BZM service.
     * @return BZM service
     */
    protected abstract BzmBrpService getBzmBrpService();

    @Override
    public final void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
        LOG.info("Versturen bericht via BZM koppelvlak ...");

        // Bepaal de OIN's.
        if (bericht.getOinOndertekenaar() != null && bericht.getOinTransporteur() != null) {
            oinOndertekenaar =
                    String.valueOf(
                            sqlHelper.readSingleSelectSqlReturnObject(
                                    brpDataSource,
                                    "SELECT oin FROM kern.partij WHERE code = '" + bericht.getOinOndertekenaar() + "'"));
            oinTransporteur =
                    String.valueOf(
                            sqlHelper.readSingleSelectSqlReturnObject(
                                    brpDataSource,
                                    "SELECT oin FROM kern.partij WHERE code = '" + bericht.getOinTransporteur() + "'"));
            LOG.info("Ondertekenaar " + oinOndertekenaar + " transporteur " + oinTransporteur);
        } else {
            bepaalPartijenVoorBijhouding(bericht);
        }

        final String response = getBzmBrpService().verstuurBzmBericht(bericht.getInhoud(), oinTransporteur, oinOndertekenaar);
        LOG.info("Bericht verstuurd. Registreren antwoord.");

        final Bericht antwoord = new Bericht();
        antwoord.setInhoud(response);
        antwoord.setCorrelatieReferentie(antwoord.getBerichtReferentie());

        antwoorden.put(bericht.getBerichtReferentie(), antwoord);
    }

    private void bepaalPartijenVoorBijhouding(final Bericht bericht) throws KanaalException {

        // Reset de OIN's.
        oinTransporteur = null;
        oinOndertekenaar = null;

        // Haal het partij Id op.
        final Integer verzendendePartijId =
                (Integer) sqlHelper.readSingleSelectSqlReturnObject(
                        brpDataSource,
                        "SELECT id FROM kern.partij WHERE code = '" + bericht.getVerzendendePartij() + "'");

        // Haal de bijhouders rol op.
        final Integer geautoriseerdeId =
                (Integer) sqlHelper.readSingleSelectSqlReturnObject(
                        brpDataSource,
                        "SELECT id FROM kern.partijrol WHERE (rol = 2 OR rol = 3) AND partij = '" + verzendendePartijId + "'");

        // Haal de transporteur op.
        final Integer transporteurResultaat =
                (Integer) sqlHelper.readSingleSelectSqlReturnObject(
                        brpDataSource,
                        "SELECT transporteur FROM autaut.toegangbijhautorisatie WHERE geautoriseerde = '" + geautoriseerdeId + "'");

        // Haal de ondertekenaar op.
        final Integer ondertekenaarResultaat =
                (Integer) sqlHelper.readSingleSelectSqlReturnObject(
                        brpDataSource,
                        "SELECT ondertekenaar FROM autaut.toegangbijhautorisatie WHERE geautoriseerde = '" + geautoriseerdeId + "'");

        // Indien de verzendende partij ook de transporteur en de ondertekenaar is, zijn transporteur en ondertekenaar
        // null; vervang deze door de geautoriseerde.
        final Integer transporteurId = transporteurResultaat != null ? transporteurResultaat : verzendendePartijId;
        final Integer ondertekenaarId = ondertekenaarResultaat != null ? ondertekenaarResultaat : verzendendePartijId;

        // Bepaal de OIN's voor de transporteur en de ondertekenaar.
        oinTransporteur = (String) sqlHelper.readSingleSelectSqlReturnObject(brpDataSource, SELECT_OIN_FROM_PARTIJ + transporteurId);
        oinOndertekenaar = (String) sqlHelper.readSingleSelectSqlReturnObject(brpDataSource, SELECT_OIN_FROM_PARTIJ + ondertekenaarId);

    }

    @Override
    public final Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) {
        LOG.info("Ophalen geregistreerd antwoord uit BZM koppelvlak");
        final Bericht ontvangenBericht = antwoorden.remove(verwachtBericht.getCorrelatieReferentie());

        if (ontvangenBericht != null) {
            ontvangenBericht.setBerichtReferentie(verwachtBericht.getBerichtReferentie());
        }

        return ontvangenBericht;
    }

    @Override
    protected final boolean vergelijkInhoud(final String verwachteInhoud, final String ontvangenInhoud) {
        return VergelijkXml.vergelijkXml(verwachteInhoud, ontvangenInhoud);
    }

    @Override
    public final List<Bericht> naTestcase(final TestCasusContext testCasus) {
        final List<Bericht> result = new ArrayList<>(antwoorden.values());
        antwoorden.clear();

        return result;
    }
}
