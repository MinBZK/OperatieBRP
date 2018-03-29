/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.test.common.OnzekerResultaatExceptie;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.junit.Assert;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * Testclient hulpklasse tbv protocolleren.
 */
public class ProtocolleringHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    public static final String COL_DATEINDEMATERIELEPERIODERES = "dateindematerieleperioderes";
    public static final String COL_DATAANVMATERIELEPERIODERES = "dataanvmaterieleperioderes";
    public static final String COL_DATAANVMATERIELEPERIODE = "dataanvmaterieleperiode";
    public static final String COL_TSAANVFORMELEPERIODERES = "tsaanvformeleperioderes";
    public static final String COL_TSEINDEFORMELEPERIODERES = "tseindeformeleperioderes";
    public static final String COL_TSKLAARZETTENLEV = "tsklaarzettenlev";
    public static final String SPECIAL_COL_SOORT_SYNCHRONISATIE = "soortSynchronisatie";
    public static final String SPECIAL_COL_SOORT_DIENST = "soortDienst";
    public static final String SPECIAL_COL_BSN = "bsn";

    private final BrpOmgeving brpOmgeving;

    /**
     * Constructor.
     * @param brpOmgeving de omgeving.
     */
    public ProtocolleringHelper(final BrpOmgeving brpOmgeving) {
        this.brpOmgeving = brpOmgeving;
    }

    /**
     * Leegt de protocolleringtabellen.
     */
    public void reset() {
        LOGGER.info("Start leeg (prot) tabellen");
        brpOmgeving.protocolleringDatabase().template().readwrite(jdbcTemplate ->
                jdbcTemplate.batchUpdate("delete from prot.levsaantekpers", "delete from prot.levsaantek"));
        LOGGER.info("Einde leeg (prot) tabellen");
    }

    /**
     * Test dat er niet geprotocolleerd is voor persoon met bsn
     * @param bsn persoon bsn
     */
    public void assertNietGeprotocolleerdVoorPersoon(final String bsn) throws InterruptedException {
        LOGGER.info("assertNietGeprotocolleerdVoorPersoon");
        TimeUnit.SECONDS.sleep(3);
        final Map<String, String> filter = Maps.newHashMap();
        filter.put(SPECIAL_COL_BSN, bsn);
        final ProtocolleringVerzoek verzoek = new ProtocolleringVerzoek(null, null, filter);
        brpOmgeving.protocolleringDatabase().template().readonly(verzoek);
        Assert.assertTrue("Er is wel geprotocolleerd", verzoek.getList().isEmpty());
    }

    /**
     * Test dat er niet geprotocolleerd
     */
    public void assertNietGeprotocolleerd() throws InterruptedException {
        LOGGER.info("assertNietGeprotocolleerd");
        TimeUnit.SECONDS.sleep(3);
        final Map<String, String> filter = Maps.newHashMap();
        final ProtocolleringVerzoek verzoek = new ProtocolleringVerzoek(null, null, filter);
        brpOmgeving.protocolleringDatabase().template().readonly(verzoek);
        Assert.assertTrue("Er is wel geprotocolleerd", verzoek.getList().isEmpty());
    }

    /**
     * Controleert of de administratieve handeling correct geprotocolleerd is Controleren of de administratieve handeling voor een bepaalde BSN correct
     * geprotocolleerd is.
     * @param bsn het burgerservicenummer waarvoor de protocollering gecontroleerd moet worden
     */
    public void assertAdministratievehandelingCorrectGeprotocolleerd(final String bsn) {
        LOGGER.info("assertAdministratievehandelingCorrectGeprotocolleerd");
        brpOmgeving.asynchroonBericht().assertLeveringen();

        final Map<String, String> filterMap = Maps.newHashMap();
        filterMap.put(SPECIAL_COL_BSN, bsn);
        final PersHandelingVerzoek persHandelingVerzoek = new PersHandelingVerzoek(bsn);
        brpOmgeving.brpDatabase().template().readonly(persHandelingVerzoek);
        filterMap.put("admhnd", persHandelingVerzoek.getHandelingId().toString());

        final ProtocolleringVerzoek verzoek = new ProtocolleringVerzoek(null, null, filterMap);
        brpOmgeving.protocolleringDatabase().template().readonly(verzoek);
        if (verzoek.getList().isEmpty()) {
            throw new OnzekerResultaatExceptie("(Nog) geen protocolleringrecords gevonden voor persoon en handeling");
        }
    }

    /**
     * Controleert of er geprotocolleerd is met de volgende gegevens.
     * @param filter map met protocolleringgegevens
     */
    public void assertGeprotocolleerdMetDeVolgendeGegevens(final String leveringsautorisatie, final String partij, final Map<String, String> filter) {
        LOGGER.info("assertGeprotocolleerdMetDeVolgendeGegevens {}", filter.toString());
        final ProtocolleringVerzoek verzoek = new ProtocolleringVerzoek(leveringsautorisatie, partij, filter);
        brpOmgeving.protocolleringDatabase().template().readonly(verzoek);
        if (verzoek.getList().isEmpty()) {
            throw new OnzekerResultaatExceptie("(Nog) Geen protocolleringrecords gevonden");
        }
    }

    /**
     *
     * @param leveringsautorisatie
     * @param partij
     * @param filter
     * @return
     */
    public List<Map<String, Object>> geefProtocolleringRecords(final String leveringsautorisatie, final String partij, final Map<String, String> filter) {
        final ProtocolleringVerzoek verzoek = new ProtocolleringVerzoek(leveringsautorisatie, partij, filter);
        brpOmgeving.protocolleringDatabase().template().readonly(verzoek);
        return verzoek.getList();
    }


    /**
     *
     */
    public class ProtocolleringVerzoek implements Consumer<JdbcTemplate> {

        private static final String QUERY_PERS = "select * from prot.levsaantek l, prot.levsaantekpers lp where l.id = lp.levsaantek";
        private static final String QUERY = "select * from prot.levsaantek";

        private final Map<String, String> filterMap;
        private List<Map<String, Object>> list;

        ProtocolleringVerzoek(final String leveringsautorisatie, final String partij, final Map<String, String> filter) {

            this.filterMap = Maps.newLinkedHashMap(filter);

            if (filter.containsKey(SPECIAL_COL_BSN)) {
                this.filterMap.remove(SPECIAL_COL_BSN);

                final PersIdVerzoek persIdVerzoek = new PersIdVerzoek(filter.get(SPECIAL_COL_BSN));
                brpOmgeving.brpDatabase().template().readonly(persIdVerzoek);
                final int persId = persIdVerzoek.getPersId();
                this.filterMap.put("pers", String.valueOf(persId));
            }

            if (filter.containsKey(SPECIAL_COL_SOORT_DIENST)) {
                final String soortDienstNaam = filter.get(SPECIAL_COL_SOORT_DIENST);
                final Optional<SoortDienst> first = Lists.newArrayList(SoortDienst.values()).stream()
                        .filter(soortDienst -> soortDienst.getNaam().equals(soortDienstNaam)).findFirst();
                Assert.assertTrue("Ongeldige soort dienst: " + soortDienstNaam, first.isPresent());

                if (first.isPresent()) {
                    final ToegangLeveringsAutorisatie tla = brpOmgeving.autorisaties().geefToegangLeveringsautorisatie(partij, leveringsautorisatie);
                    final List<Dienst> gevondenDiensten = brpOmgeving.autorisaties().geefDienstenBijToegangLeveringsAutorisatie(tla, first.get());
                    Assert.assertFalse("Geen autorisatie gevonden voor dienst: " + first.get(), gevondenDiensten.isEmpty());
                    if (gevondenDiensten.size() > 1) {
                        LOGGER.warn("Meerdere diensten gevonden...eerste wordt gebruikt");
                    }
                    filterMap.put("dienst", gevondenDiensten.iterator().next().getId().toString());
                }
                this.filterMap.remove(SPECIAL_COL_SOORT_DIENST);
            }

            //filter op soortSynchronisatie
            if (filter.containsKey(SPECIAL_COL_SOORT_SYNCHRONISATIE)) {
                this.filterMap.remove(SPECIAL_COL_SOORT_SYNCHRONISATIE);
                final String waarde = filter.get(SPECIAL_COL_SOORT_SYNCHRONISATIE);

                final String filterWaarde;
                if ("null".equalsIgnoreCase(waarde)) {
                    filterWaarde = "null";
                } else {
                    final SoortSynchronisatie soortSynchronisatieEnum = "Volledigbericht".equals(waarde)
                            ? SoortSynchronisatie.VOLLEDIG_BERICHT : SoortSynchronisatie.MUTATIE_BERICHT;
                    filterWaarde = String.valueOf(soortSynchronisatieEnum.getId());
                }
                this.filterMap.put("srtsynchronisatie", filterWaarde);
            }
        }

        @Override
        public void accept(final JdbcTemplate jdbcTemplate) {

            list = jdbcTemplate.queryForList(filterMap.containsKey("pers") ? QUERY_PERS : QUERY);

            LOGGER.info("Totaal aantal prot records: {}", list.size());
            for (Map<String, Object> stringObjectMap : list) {
                LOGGER.info("record = {}", stringObjectMap.toString());
            }

            for (Map.Entry<String, String> filterwaarde : filterMap.entrySet()) {

                if (list.isEmpty()) {
                    break;
                }

                LOGGER.info("Filter op key={} value={}", filterwaarde.getKey(), filterwaarde.getValue());
                list = list.stream().filter(stringObjectMap -> {
                    if (!stringObjectMap.containsKey(filterwaarde.getKey())) {
                        return false;
                    }
                    final Object dbValue = stringObjectMap.get(filterwaarde.getKey());
                    boolean returnVal;
                    switch (filterwaarde.getKey()) {
                        case COL_DATAANVMATERIELEPERIODERES:
                        case COL_DATEINDEMATERIELEPERIODERES:
                        case COL_DATAANVMATERIELEPERIODE:
                            returnVal = controleerDatumVeld(filterwaarde.getValue(), (Integer) dbValue);
                            break;
                        case COL_TSAANVFORMELEPERIODERES:
                        case COL_TSEINDEFORMELEPERIODERES:
                        case COL_TSKLAARZETTENLEV:
                            returnVal = controleerDatumTijdVeld(filterwaarde.getValue(), (Timestamp) dbValue);
                            break;
                        default:
                            returnVal = String.valueOf(dbValue).equalsIgnoreCase(filterwaarde.getValue());
                    }
                    return returnVal;
                }).collect(Collectors.toList());

                LOGGER.info("Resultaat na filter = {} records", list.size());
            }
        }

        public List<Map<String, Object>> getList() {
            return list;
        }
    }

    private boolean controleerDatumVeld(final String opgegevenWaarde, final Integer databaseWaarde) {
        Integer checkDatum;
        if ("VANDAAG".equals(opgegevenWaarde)) {
            checkDatum = DatumUtil.vandaag();
        } else if ("MORGEN".equals(opgegevenWaarde)) {
            checkDatum = DatumUtil.morgen();
        } else if ("NULL".equals(opgegevenWaarde)) {
            checkDatum = null;
        } else {
            checkDatum = Integer.parseInt(opgegevenWaarde);
        }
        return checkDatum == null ? databaseWaarde == null : checkDatum.equals(databaseWaarde);
    }

    private boolean controleerDatumTijdVeld(final String tsWaarde, final Timestamp datum) {
        if ("NU".equals(tsWaarde)) {
            final long nu = DatumUtil.nuAlsZonedDateTime().toInstant().toEpochMilli();
            final long verschil = Math.max(datum.getTime(), nu) - Math.min(datum.getTime(), nu);
            if (verschil > 1000 * 60 * 60) {
                throw new TestclientExceptie("verschil groter dan 1 uur!!");
            }
            return true;
        } else if ("VANDAAG".equals(tsWaarde)) {
            final long nu = DatumUtil.vanIntegerNaarZonedDateTime(DatumUtil.vandaag()).toInstant().toEpochMilli();
            final long verschil = Math.max(datum.getTime(), nu) - Math.min(datum.getTime(), nu);
            if (verschil > 1000 * 60 * 60 * 2) {
                throw new TestclientExceptie("verschil groter dan 2 uur!!");
            }
            return true;
        } else if ("NULL".equals(tsWaarde)) {
            return datum == null;
        } else {
            //niet gebruikelijk...
            return tsWaarde.equals(datum.toString());
        }
    }
}
