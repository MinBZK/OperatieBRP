/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.component.DatabaseDocker;
import nl.bzk.brp.dockertest.component.Docker;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.brp.dockertest.component.Poorten;
import nl.bzk.brp.test.common.DienstSleutel;
import nl.bzk.brp.test.common.dsl.autorisatie.AutorisatieDslVerzoek;
import nl.bzk.brp.test.common.dsl.autorisatie.BijhoudingAutorisatieDslVerzoek;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * Hulpklasse voor het opvoeren van autorisaties.
 */
public class AutorisatieHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final BrpOmgeving brpOmgeving;

    /**
     * Lijst met laatst toegevoegde autorisaties
     */
    private List<ToegangLeveringsAutorisatie> toegangLeveringsAutorisatieList = Lists.newArrayList();

    /**
     * Lijst met laatst toegevoegde bijhoudingautorisaties
     */
    private List<ToegangBijhoudingsautorisatie> toegangBijhoudingsAutorisatieList = Lists.newArrayList();
    private Map<String, ToegangLeveringsAutorisatie> toegangRefs = Collections.emptyMap();
    private Map<String, Dienst> dienstRefs;

    /**
     * Constructor.
     * @param brpOmgeving de brpOmgeving
     */
    public AutorisatieHelper(BrpOmgeving brpOmgeving) {
        this.brpOmgeving = brpOmgeving;
    }

    /**
     * Voert nieuwe autorisaties, verwijdert de bestaande.
     * @param bestanden lijst met autorisatie DSL bestanden
     */
    public void laadAutorisaties(List<Resource> bestanden, final DatabaseDocker databaseDocker) {
        LOGGER.info("Laad autorisaties {}", bestanden);
        final String afleverpunt;
        if (brpOmgeving.bevat(DockerNaam.AFNEMERVOORBEELD)) {
            afleverpunt = String.format("http://%s:%s/afnemervoorbeeld//BrpBerichtVerwerkingService/VerwerkPersoonBare",
                    brpOmgeving.getDockerHostname(), brpOmgeving.geefDocker(DockerNaam.AFNEMERVOORBEELD).getPoortMap().get(Poorten.APPSERVER_PORT));
        } else {
            afleverpunt = "dummyafleverpunt";
        }

        final AutorisatieDslVerzoek verzoek = new AutorisatieDslVerzoek(bestanden, afleverpunt);
        databaseDocker.entityManagerVerzoek().voerUitTransactioneel(verzoek);

        toegangLeveringsAutorisatieList = verzoek.getToegangen();
        toegangRefs = verzoek.getToegangRefs();
        dienstRefs = verzoek.getDienstRefs();
    }

    /**
     * Laad alle bijhoudingsautorisaties in bijhoudingsautorisaties dir.
     */
    public void laadBijhoudingAutorisaties(final DatabaseDocker databaseDocker) {
        final List<String> autorisatieBestanden = new ArrayList<>();
        //nog geen in gebruik, alle bijh.aut. worden geladen via sql
        laadBijhoudingAutorisaties(autorisatieBestanden.stream().map(ClassPathResource::new).collect(Collectors.toList()), databaseDocker);
    }

    /**
     * Voert nw bijhoudingautorisaties op, verwijdert bestaande.
     * @param bestanden lijst met autorisatie DSL bestanden
     */
    public void laadBijhoudingAutorisaties(Iterable<Resource> bestanden, final DatabaseDocker databaseDocker) {
        LOGGER.info("Laad bijhoudingautorisaties {}", bestanden);
        String afleverpunt;
        if (brpOmgeving.bevat(DockerNaam.AFNEMERVOORBEELD)) {
            afleverpunt = String.format("http://%s:%s/afnemervoorbeeld/BrpBerichtVerwerkingService/BijhoudingsNotificatieBare",
                    brpOmgeving.getDockerHostname(), brpOmgeving.<Docker>geefDocker(DockerNaam.AFNEMERVOORBEELD).getPoortMap().get(Poorten.APPSERVER_PORT));
        } else {
            afleverpunt = "dummyafleverpunt";
        }

        //laad autorisatie uit sql + voer correcties uit
        databaseDocker.template().readwrite(jdbcTemplate -> {
            jdbcTemplate.update(String.format("UPDATE autaut.toegangbijhautorisatie SET afleverpunt = '%s'", afleverpunt));
            jdbcTemplate.update(String.format("UPDATE autaut.his_toegangbijhautorisatie SET afleverpunt = '%s'", afleverpunt));
        });

        //voer nieuwe autorisaties op
        toegangBijhoudingsAutorisatieList.clear();
        for (Resource autorisatiebestand : bestanden) {
            final BijhoudingAutorisatieDslVerzoek verzoek = new BijhoudingAutorisatieDslVerzoek(autorisatiebestand, afleverpunt);
            databaseDocker.entityManagerVerzoek().voerUitTransactioneel(verzoek);
            toegangBijhoudingsAutorisatieList.addAll(verzoek.getAutorisaties());
        }
    }

    /**
     * Geef {@link ToegangLeveringsAutorisatie} bij een partijnaam en leveringautorisatienaam.
     * @param partijNaam naam v partij
     * @param leveringsautorisatieNaam naam v leveringsautorisatie
     * @return een {@link ToegangLeveringsAutorisatie}
     */
    public ToegangLeveringsAutorisatie geefToegangLeveringsautorisatie(final String partijNaam, final String leveringsautorisatieNaam) {
        Assert.notNull(partijNaam, "Partij moet bekend zijn voor het doen van een verzoek");
        Assert.notNull(leveringsautorisatieNaam, "LeveringsautorisatieNaam moet bekend zijn voor het doen van een verzoek");
        return toegangLeveringsAutorisatieList.stream()
                .filter(toegangLeveringsAutorisatie -> toegangLeveringsAutorisatie.getGeautoriseerde().getPartij().getNaam().equals(partijNaam)
                        && toegangLeveringsAutorisatie.getLeveringsautorisatie().getNaam().equals(leveringsautorisatieNaam))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Toegangleveringsautorisatie bestaat voor partij [%s] en leveringsautorisatie [%s]",
                                partijNaam, leveringsautorisatieNaam)));
    }

    /**
     * Geef {@link ToegangLeveringsAutorisatie} obv een id.
     * @param leveringsAutorisatieId id van de leveringsautorisatie
     * @return een {@link ToegangLeveringsAutorisatie}
     */
    public Integer geefToegangLeveringsautorisatie(final int leveringsAutorisatieId) {
        return toegangLeveringsAutorisatieList.stream()
                .filter(toegangLeveringsAutorisatie -> toegangLeveringsAutorisatie.getLeveringsautorisatie().getId() == leveringsAutorisatieId)
                .findFirst()
                .map(ToegangLeveringsAutorisatie::getId)
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Geeft de {@link ToegangLeveringsAutorisatie} obv referentie.
     * @param ref een @ref  waarde
     * @return een toegangLeveringsAutorisatie
     */
    public ToegangLeveringsAutorisatie geefToegangleveringsautorisatieVoorRef(final DienstSleutel ref) {
        return toegangRefs.computeIfAbsent(ref.getToegangRef(), s -> {
            throw new IllegalStateException("Referentie niet gevonden Toegangleveringsautorsatie: " + ref);
        });
    }

    /**
     * Geeft de {@link Dienst} obv referentie.
     * @param ref een @ref waarde
     * @return een Dienst
     */
    public Dienst geefDienstVoorRef(final DienstSleutel ref) {
        return dienstRefs.computeIfAbsent(ref.getDienstRef(), s -> {throw new IllegalStateException("Referentie niet gevonden voor Dienst: " + ref);});
    }


    /**
     * Geeft indicatie of de leveringsautorisatie bestaat met de gegeven naam en id.
     * @param autorisatieId id van de leveringsautorisatie
     * @param leveringsautorisatieNaam naam van de leveringsautorisatie
     * @return boolean indidatie
     */
    boolean bestaatLeveringsautorisatie(final int autorisatieId, final String leveringsautorisatieNaam) {
        return toegangLeveringsAutorisatieList.stream().anyMatch(toegangLeveringsAutorisatie ->
                toegangLeveringsAutorisatie.getLeveringsautorisatie().getNaam().equals(leveringsautorisatieNaam)
                        && toegangLeveringsAutorisatie.getLeveringsautorisatie().getId().equals(autorisatieId));
    }

    /**
     * Geef diensten van {@link SoortDienst} bij een alle beschikbare toegangLeveringsAutorisaties
     * @param soortDienst soort dienst
     * @return lijst met diensten
     */
    List<Dienst> geefDiensten(final SoortDienst soortDienst) {
        return toegangLeveringsAutorisatieList.stream()
                .map(ToegangLeveringsAutorisatie::getLeveringsautorisatie)
                .map(Leveringsautorisatie::getDienstbundelSet)
                .flatMap(Collection::stream)
                .map(Dienstbundel::getDienstSet)
                .flatMap(Collection::stream)
                .filter(dienst -> dienst.getSoortDienst() == soortDienst)
                .collect(Collectors.toList());
    }

    /**
     * Geef diensten van {@link SoortDienst} bij een {@link ToegangLeveringsAutorisatie}
     * @param toegangLeveringsAutorisatie toegangLeveringsautorisatie
     * @param soortDienst soort dienst
     * @return lijst met diensten
     */
    List<Dienst> geefDienstenBijToegangLeveringsAutorisatie(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, final SoortDienst soortDienst) {
        return toegangLeveringsAutorisatie.getLeveringsautorisatie().getDienstbundelSet()
                .stream()
                .map(Dienstbundel::getDienstSet)
                .flatMap(Collection::stream)
                .filter(dienst -> dienst.getSoortDienst() == soortDienst)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param autorisatieBestanden
     */
    public void laadAutorisatiesEnCustomSql(List<String> autorisatieBestanden) {
        leegAutAutTabellen(brpOmgeving.brpDatabase());

        //laad autorisatie sql verzoeken
        LOGGER.info("Start uitvoeren custom SQL");
        final AutorisatieDataSqlVerzoek autorisatieDataSqlVerzoek = new AutorisatieDataSqlVerzoek();

        LOGGER.info("Uitvoeren SQL tbv BRP database");
        brpOmgeving.brpDatabase().template().readwrite(autorisatieDataSqlVerzoek);
        if (brpOmgeving.bevat(DockerNaam.SELECTIEBLOB_DATABASE)) {
            leegAutAutTabellen(brpOmgeving.selectieDatabase());
            LOGGER.info("Uitvoeren SQL tbv SELECTIE database");
            brpOmgeving.selectieDatabase().template().readwrite(autorisatieDataSqlVerzoek);
        }

        if (brpOmgeving.bevat(DockerNaam.AFNEMERVOORBEELD) && brpOmgeving.bevat(DockerNaam.VRIJBERICHT)) {
            LOGGER.info("Start uitvoeren custom SQL voor vrijbericht");
            final VrijBerichtDataSqlVerzoek vrijBerichtDataSqlVerzoek = new VrijBerichtDataSqlVerzoek(
                    brpOmgeving.getDockerHostname(), brpOmgeving.geefDocker(DockerNaam.AFNEMERVOORBEELD).getPoortMap().get(Poorten.APPSERVER_PORT));
            brpOmgeving.brpDatabase().template().readwrite(vrijBerichtDataSqlVerzoek);
            LOGGER.info("Einde uitvoeren custom SQL voor vrijbericht");
        }
        LOGGER.info("Einde uitvoeren custom SQL");

        //laad autorisatie dsl verzoeken
        LOGGER.info("Start uitvoeren DSL tbv leveringsautorisatie BRP database");
        final AutorisatieHelper autorisatieHelper = brpOmgeving.autorisaties();
        autorisatieHelper.laadAutorisaties(autorisatieBestanden.stream().map(ClassPathResource::new).collect(Collectors.toList()), brpOmgeving.brpDatabase());
        if (brpOmgeving.bevat(DockerNaam.SELECTIEBLOB_DATABASE)) {
            LOGGER.info("Start uitvoeren DSL tbv leveringsautorisatie SELECTIE database");
            autorisatieHelper
                    .laadAutorisaties(autorisatieBestanden.stream().map(ClassPathResource::new).collect(Collectors.toList()), brpOmgeving.selectieDatabase());
        }

        if (brpOmgeving.bevat(DockerNaam.BIJHOUDING)) {
            LOGGER.info("Uitvoeren DSL tbv bijhoudingsautorisatie BRP database");
            autorisatieHelper.laadBijhoudingAutorisaties(brpOmgeving.brpDatabase());
            if (brpOmgeving.bevat(DockerNaam.SELECTIEBLOB_DATABASE)) {
                LOGGER.info("Uitvoeren DSL tbv bijhoudingsautorisatie SELECTIE database");
                autorisatieHelper.laadBijhoudingAutorisaties(brpOmgeving.selectieDatabase());
            }
        }
        LOGGER.info("Eind uitvoeren autorisatie DSL");

        //refresh caches parallel
        LOGGER.info("Start cache refreshes");
        brpOmgeving.cache().refresh();
        LOGGER.info("Einde cache refreshes");
    }

    private void leegAutAutTabellen(final DatabaseDocker databaseDocker) {
        databaseDocker.template().readwrite(jdbcTemplate -> jdbcTemplate.batchUpdate(
                "TRUNCATE autaut.seltaak CASCADE",
                "DELETE FROM autaut.toeganglevsautorisatie",
                "TRUNCATE autaut.levsautorisatie CASCADE",
                "UPDATE kern.perscache SET afnemerindicatiegegevens = NULL"
        ));
        //leeg alle autaut tabellen mbt bijhoudingautorisaties
        databaseDocker.template().readwrite(jdbcTemplate ->
                jdbcTemplate.batchUpdate(
                        "DELETE FROM autaut.his_bijhautorisatiesrtadmhnd",
                        "DELETE FROM autaut.bijhautorisatiesrtadmhnd",
                        "DELETE FROM autaut.his_bijhautorisatie",
                        "DELETE FROM autaut.his_toegangbijhautorisatie",
                        "DELETE FROM autaut.toegangbijhautorisatie",
                        "DELETE FROM autaut.bijhautorisatie"));
    }
}
