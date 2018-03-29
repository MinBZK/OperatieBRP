/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.junit.Assert;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Hulpklasse voor het controleren van afnemerindicaties.
 */
public class AfnemerindicatieAsserts {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final BrpOmgeving brpOmgeving;

    /**
     * Constructor.
     * @param brpOmgeving de omgeving.
     */
    public AfnemerindicatieAsserts(final BrpOmgeving brpOmgeving) {
        this.brpOmgeving = brpOmgeving;
    }


    /**
     * ==== Controleren of de afnemerindicatie bij een persoon is gezet
     * Controleert of er een afnemerindicatie bij de opgegeven persoon is gezet voor de opgegeven leveringsautorisatie
     * @param bsn de bsn van de persoon waarvoor een afnemerindicatie gecontroleert word
     * @param leveringautorisatienaam leveringautorisatie waarvoor een afnemerindicatie is geplaatst
     * @param partijnaam partij waarvoor een afnemerindicatie is geplaatst
     * @param soortDienst soortDienst
     */
    public void assertAfnemerIndicatieGeplaatst(final String bsn, final String leveringautorisatienaam, final String partijnaam, final String soortDienst) {
        final ToegangLeveringsAutorisatie
                toegangLeveringsAutorisatie =
                brpOmgeving.autorisaties().geefToegangLeveringsautorisatie(partijnaam, leveringautorisatienaam);
        final List<Dienst>
                diensten =
                brpOmgeving.autorisaties().geefDienstenBijToegangLeveringsAutorisatie(toegangLeveringsAutorisatie, SoortDienst.valueOf(soortDienst));
        if (diensten.size() > 1) {
            throw new TestclientExceptie("meerdere diensten gevonden met zelfde soort, stap niet ondersteund");
        }
        final Dienst dienst = diensten.stream().findFirst().orElseThrow(() -> new TestclientExceptie("dienst niet gevonden"));
        LOGGER.info("assertAfnemerIndicatieGeplaatst bsn = {} leveringautorisatienaam = {}", bsn, leveringautorisatienaam);
        final AfnemerindicatieVerzoek verzoek = new AfnemerindicatieVerzoek(bsn, leveringautorisatienaam, partijnaam, dienst.getId());
        brpOmgeving.brpDatabase().template().readonly(verzoek);
        Assert.assertFalse( "Afnemerindicatie niet gevonden", verzoek.getList().isEmpty());
    }

    /**
     * ==== Controleren of de afnemerindicatie bij een persoon is verwijderd
     * Controleert of er een afnemerindicatie bij de opgegeven persoon is verwijderd voor het opgegeven leveringsautorisatie
     * @param bsn de bsn van de persoon waarvoor een afnemerindicatie gecontroleert word
     * @param leveringautorisatienaam leveringautorisatie waarvoor een afnemerindicatie is geplaatst
     * @param partijnaam partij waarvoor een afnemerindicatie is geplaatst
     */
    public void assertAfnemerIndicatieNietGeplaatst(final String bsn, final String leveringautorisatienaam, final String partijnaam) {
        LOGGER.info("assertAfnemerIndicatieNietGeplaatst bsn = {} leveringautorisatienaam = {}", bsn, leveringautorisatienaam);
        final AfnemerindicatieVerzoek verzoek = new AfnemerindicatieVerzoek(bsn, leveringautorisatienaam, partijnaam);
        brpOmgeving.brpDatabase().template().readonly(verzoek);
        Assert.assertTrue("Afnemerindicatie niet gevonden", verzoek.getList().isEmpty());
    }

    /**
     * Geeft de resultaten terug waarbij gecontroleerd wordt of de afnemer indicatie is vervallen.
     * @param bsn het bsn waarvoor de afnemer indicatie is geplaatst
     * @param leveringautorisatienaam de naam van de leverings autorisatie
     * @param partijCode partijCode waarvoor een afnemerindicatie is geplaatst
     */
    public void assertAfnemerIndicatieVervallen(final String bsn, final String leveringautorisatienaam, final String partijCode) {
        LOGGER.info("assertAfnemerIndicatieVervallen bsn = {} leveringautorisatienaam = {}", bsn, leveringautorisatienaam);
        brpOmgeving.brpDatabase().template().readonly(jdbcTemplate -> {
            final String query =
                    "SELECT tsverval from autaut.his_persafnemerindicatie where id=(SELECT id from autaut.persafnemerindicatie WHERE pers=(select id from "
                            + "kern.pers where bsn = ?) "
                            + "AND levsautorisatie=(select id from autaut.levsautorisatie where naam = ?) "
                            + "and partij=(select id from kern.partij where code=?))";
            final List<Map<String, Object>> list = jdbcTemplate.queryForList(query, bsn, leveringautorisatienaam, partijCode);
            Assert.assertTrue("Vervallen afnemerindicatie niet gevonden",
                    !list.isEmpty() && list.iterator().next().get("tsverval") != null);
        });
    }

    private static final class AfnemerindicatieVerzoek implements Consumer<JdbcTemplate> {

        private final String bsn;
        private final String leveringautorisatienaam;
        private final String partijnaam;
        private Integer dienstId;
        private List<Map<String, Object>> list;

        private AfnemerindicatieVerzoek(final String bsn, final String leveringautorisatienaam, final String partijnaam) {
            this.bsn = bsn;
            this.leveringautorisatienaam = leveringautorisatienaam;
            this.partijnaam = partijnaam;
        }

        private AfnemerindicatieVerzoek(final String bsn, final String leveringautorisatienaam, final String partijnaam, final Integer dienstId) {
            this(bsn, leveringautorisatienaam, partijnaam);
            this.dienstId = dienstId;
        }


        @Override
        public void accept(final JdbcTemplate jdbcTemplate) {
            final String query =
                    "SELECT * from autaut.persafnemerindicatie pa, autaut.his_persafnemerindicatie hpa WHERE pa.id = hpa.persafnemerindicatie "
                            + "AND pa.pers=(select id from kern.pers where bsn= ?) "
                            + "AND  pa.levsautorisatie=(select id from autaut.levsautorisatie where naam= ?) "
                            + "and  pa.partij=(select id from kern.partij where naam=?) and hpa.dienstinh = COALESCE(?, hpa.dienstinh)";
            list = jdbcTemplate.queryForList(query, bsn, leveringautorisatienaam, partijnaam, dienstId);
        }

        public List<Map<String, Object>> getList() {
            return list;
        }
    }
}
