/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.jbehave.JBehaveState;
import nl.bzk.brp.dockertest.service.datatoegang.QueryForListVerzoek;
import nl.bzk.brp.dockertest.util.ResourceUtils;
import nl.bzk.brp.domain.leveringmodel.ModelAfdruk;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.PersoonData;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Hulpklasse voor het opvoeren / muteren en verwijderen van testdata.
 */
public final class TestdataHelper {

    private static final int QUERY_TIMEOUT_VERWIJDEREN_ALLES = 300;

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final BrpOmgeving brpOmgeving;

    /**
     * Constructor.
     * @param brpOmgeving de omgeving
     */
    public TestdataHelper(final BrpOmgeving brpOmgeving) {
        this.brpOmgeving = brpOmgeving;
    }

    /**
     * Verwijder alle personen.
     */
    public void verwijderAllePersonen() {
        LOGGER.debug("Schoon alle personen");
        brpOmgeving.brpDatabase().template().readwrite(jdbcTemplate -> {
            verwijderAllePersonen(jdbcTemplate);
            verwijderAlleBlokkeringen(jdbcTemplate);
        });
    }

    /**
     * Verwijder alle personen uit de selectie database.
     */
    public void verwijderAlleSelectiePersonen() {
        LOGGER.debug("Schoon alle selectie personen");
        brpOmgeving.selectieDatabase().template().readwrite(jdbcTemplate -> {
            verwijderAllePersonen(jdbcTemplate);
            verwijderAlleBlokkeringen(jdbcTemplate);
        });
        brpOmgeving.brpDatabase().template().readwrite(jdbcTemplate -> {
            verwijderAllePersonen(jdbcTemplate);
            verwijderAlleBlokkeringen(jdbcTemplate);
        });
    }

    /**
     * Vervangt, bij alle betrokkenheden waarbij het opgegeven anr van de pseudo persoon is geregistreerd, door de met
     * anr opgegevens ingeschreven persoon.
     * @param anrPseudo anr van de pseudo persoon die vervangen moet worden
     * @param anrIngeschreven anr van de ingeschreven persoon
     */
    public void vervangPseudoPersoonDoorIngeschrevenPersoon(final String anrPseudo, final String anrIngeschreven) {
        LOGGER.debug("vervangPseudoPersoonDoorIngeschrevenPersoon");
        brpOmgeving.brpDatabase().template().readwrite(jdbcTemplate -> {
            final String query = "update kern.betr set pers = (select p.id from kern.pers p where p.anr = ?) where id = (select b.id from kern.betr "
                    + "b join kern.pers p on b.pers = p.id where anr = ?)";
            jdbcTemplate.update(query, anrIngeschreven, anrPseudo);
        });
    }

    /**
     * Verwijder alle selectietaken.
     */
    public void verwijderAlleSelectietaken() {
        LOGGER.debug("Schoon alle selectietaken");
        brpOmgeving.brpDatabase().template().readwrite(jdbcTemplate -> jdbcTemplate.batchUpdate(
                "truncate autaut.seltaak cascade"
        ));
    }


    /**
     * Geeft van de gegeven persoon het aantal betrokkenheden van een gegeven type relatie.
     * @param bsn een bsn
     * @param betrokkenheidSoort een type betrokkenheid
     * @param relatieSoort een type relatie
     * @return het aantal betrokkenheden
     */
    public int geefVanPersoonAantalBetrokkenhedenBijRelatie(String bsn, String betrokkenheidSoort, String relatieSoort) {
        LOGGER.info("Zoekt persoon met bsn {} die als {} betrokken is bij een {}", bsn, betrokkenheidSoort, relatieSoort);
        final MutableInt mutableInt = new MutableInt();
        brpOmgeving.brpDatabase().template().readonly(jdbcTemplate -> {
            final String query = "Select b.relatie from kern.pers p, kern.betr b, kern.relatie r" +
                    " where p.id=b.pers" +
                    " and r.id=b.relatie" +
                    " and p.bsn= ?" +
                    " and b.rol= ?" +
                    " and r.srt= ?" +
                    " and r.dateinde IS NULL";

            final List<Map<String, Object>> list = jdbcTemplate.queryForList(query, bsn,
                    SoortBetrokkenheid.valueOf(betrokkenheidSoort).getId(), SoortRelatie.valueOf(relatieSoort).getId());
            mutableInt.setValue(list.size());
        });
        return mutableInt.getValue();
    }

    /**
     * Then
     * log persoonsbeelden.
     * @param prefix the prefix
     * @throws BlobException the blob exception
     */
    public void thenLogPersoonsbeelden(final String prefix, final String bsn) throws BlobException {
        LOGGER.info("Start log persoonsbeelden");

        final String filePrefix = StringUtils.defaultIfBlank(prefix, "default");
        // maak blobs van alle personen
        String idQuery = "select id from kern.pers";
        String cacheQuery = "select pers, pershistorievollediggegevens, afnemerindicatiegegevens from kern.perscache";
        if (bsn != null) {
            idQuery = String.format("%s where bsn = '%s'", idQuery, bsn);
            cacheQuery = String.format("%s where pers in (%s)", cacheQuery, idQuery);
        }
        final QueryForListVerzoek queryForListVerzoek = new QueryForListVerzoek(idQuery);
        brpOmgeving.brpDatabase().template().readonly(queryForListVerzoek);

        LOGGER.info("Aantal gevonden personen {}", queryForListVerzoek.getRecords().size());

        // sla blobs op van alle personen
        final QueryForListVerzoek perscacheVerzoek
                = new QueryForListVerzoek(cacheQuery);
        brpOmgeving.brpDatabase().template().readonly(perscacheVerzoek);
        LOGGER.info("Aantal gevonden blobs {}", perscacheVerzoek.getRecords().size());

        for (final Map<String, Object> persCacheMap : perscacheVerzoek.getRecords()) {

            final Number persId = (Number) persCacheMap.get("pers");
            final byte[] persoonBytes = (byte[]) persCacheMap.get("pershistorievollediggegevens");
            final byte[] afnemerindicatieBytes = (byte[]) persCacheMap.get("afnemerindicatiegegevens");

            ResourceUtils.schrijfBlobNaarBestand(new String(persoonBytes, Charset.defaultCharset()),
                    JBehaveState.getScenarioState().getCurrentStory().getPath(),
                    JBehaveState.getScenarioState().getCurrectScenario(),
                    String.format("%d-%s-persoon.blob.json", persId.intValue(), filePrefix));

            AfnemerindicatiesBlob afnemerindicatiesBlob = null;
            final PersoonBlob persoonBlob = Blobber.deserializeNaarPersoonBlob(persoonBytes);

            if (afnemerindicatieBytes != null && afnemerindicatieBytes.length > 0) {
                ResourceUtils.schrijfBlobNaarBestand(new String(afnemerindicatieBytes, Charset.defaultCharset()),
                        JBehaveState.getScenarioState().getCurrentStory().getPath(),
                        JBehaveState.getScenarioState().getCurrectScenario(),
                        String.format("%d-%s-afnemerindicatie.blob.json", persId.intValue(), filePrefix));
                afnemerindicatiesBlob = Blobber.deserializeNaarAfnemerindicatiesBlob(afnemerindicatieBytes);
            }
            final PersoonData persoonData = new PersoonData(persoonBlob, afnemerindicatiesBlob, 0L);

            final Persoonslijst persoonslijst = PersoonslijstFactory.maak(persoonData);
            final String afdruk = ModelAfdruk.maakAfdruk(persoonslijst.getMetaObject());
            ResourceUtils.schrijfModelAfdrukNaarTxtBestand(afdruk,
                    JBehaveState.getScenarioState().getCurrentStory().getPath(),
                    JBehaveState.getScenarioState().getCurrectScenario(),
                    String.format("%d-%s-persoon.modelafdruk.txt", persId.intValue(), filePrefix));
        }
    }


    private void verwijderAllePersonen(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.setQueryTimeout(QUERY_TIMEOUT_VERWIJDEREN_ALLES);
        jdbcTemplate.batchUpdate(
                "truncate kern.betr cascade",
                "truncate kern.relatie cascade",
                "truncate kern.onderzoek cascade",
                "truncate kern.pers cascade",
                "truncate verconv.lo3melding cascade",
                "truncate verconv.lo3voorkomen cascade",
                "truncate verconv.lo3ber cascade",
                "truncate ist.autorisatietabel cascade",
                "truncate ist.stapelrelatie cascade",
                "truncate ist.stapelvoorkomen cascade",
                "truncate ist.stapel cascade");
    }

    private void verwijderAlleBlokkeringen(final JdbcTemplate jdbcTemplate) {
        jdbcTemplate.setQueryTimeout(QUERY_TIMEOUT_VERWIJDEREN_ALLES);
        jdbcTemplate.execute("truncate migblok.blokkering cascade;");
    }
}
