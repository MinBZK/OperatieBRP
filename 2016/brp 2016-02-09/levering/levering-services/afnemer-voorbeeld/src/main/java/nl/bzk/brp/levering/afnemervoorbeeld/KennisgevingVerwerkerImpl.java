/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemervoorbeeld;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.brp0200.ContainerAdministratieveHandelingBijgehoudenPersonen;
import nl.bzk.brp.brp0200.GroepBerichtStuurgegevens;
import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandeling;
import nl.bzk.brp.brp0200.ObjecttypePersoon;
import nl.bzk.brp.brp0200.ObjecttypePersoonGeslachtsnaamcomponent;
import nl.bzk.brp.brp0200.ObjecttypePersoonVoornaam;
import nl.bzk.brp.brp0200.SynchronisatieVerwerkPersoon;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

/**
 * De verwerking van de BRP0200 ontvangen van de BRP.
 */
@Component
public class KennisgevingVerwerkerImpl implements KennisgevingVerwerker {
    private static final Logger LOGGER   = LoggerFactory.getLogger();
    private static final String STREEPJE = "-";
    private static final String SPATIE   = " ";

    @Value("${brp.afnemervoorbeeld.ispersistent:false}")
    private boolean isPersistent;

    @Inject
    private ApplicationContext applicationContext;

    @Override
    public final void verwerkKennisgeving(final SynchronisatieVerwerkPersoon synchronisatieVerwerkPersoon) {

        final ObjecttypeAdministratieveHandeling objecttypeAdministratieveHandeling = synchronisatieVerwerkPersoon.getSynchronisatie().getValue();

        final String zendendePartij = objecttypeAdministratieveHandeling.getPartijCode().getValue().getValue();

        LOGGER.info("Ontvangen kennisgeving van administratieve handeling '{} ({})' door partij '{}'",
            objecttypeAdministratieveHandeling.getNaam().getValue().getValue(),
            objecttypeAdministratieveHandeling.getCode().getValue().getValue(),
            zendendePartij);
        objecttypeAdministratieveHandeling.setReferentieID("dummy");
        final ContainerAdministratieveHandelingBijgehoudenPersonen personen = objecttypeAdministratieveHandeling.getBijgehoudenPersonen().getValue();
        if (personen != null && personen.getPersoon() != null) {
            verwerkBijgehoudenPersonen(personen.getPersoon());
        }

        final String berichtinhoud = (String) PhaseInterceptorChain.getCurrentMessage().get("berichtinhoud");
        final GroepBerichtStuurgegevens stuurgegevens = synchronisatieVerwerkPersoon.getStuurgegevens().getValue();

        if (isPersistent) {
            final JdbcTemplate jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(final Connection con) {
                    return maakPreparedStatement(con, berichtinhoud, stuurgegevens, synchronisatieVerwerkPersoon);
                }
            });
            LOGGER.info("Leveringbericht naar database geschreven voor partij: {}, dienst: {} en referentienummer: {}", zendendePartij,
                synchronisatieVerwerkPersoon.getParameters().getValue().getDienstIdentificatie().getValue().getValue(),
                stuurgegevens.getReferentienummer().getValue().getValue());
        }
    }

    /**
     * Maakt de prepared statement om een leveringbericht weg te schrijven.
     *
     * @param con                          de connectie met de database
     * @param berichtinhoud                de berichtinhoud
     * @param stuurgegevens                de stuurgegevens
     * @param synchronisatieVerwerkPersoon de synchronisatie verwerk persoon
     * @return de prepared statement of null als het mislukt is om er eentje op te stellen
     */
    private PreparedStatement maakPreparedStatement(final Connection con, final String berichtinhoud, final GroepBerichtStuurgegevens stuurgegevens,
        final SynchronisatieVerwerkPersoon synchronisatieVerwerkPersoon)
    {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(
                "INSERT INTO afnemer.leverbericht (bericht, dienst, tsontv, zendendepartij, ontvangendepartij, referentienummer) "
                    + "VALUES (?, ?, ?, ?, ?, ?)");
            int index = 0;
            preparedStatement.setString(++index, berichtinhoud);
            preparedStatement
                .setString(++index, String.valueOf(synchronisatieVerwerkPersoon.getParameters().getValue().getDienstIdentificatie().getValue().getValue()));
            preparedStatement.setDate(++index, new Date(System.currentTimeMillis()));
            preparedStatement.setInt(++index, Integer.parseInt(stuurgegevens.getZendendePartij().getValue().getValue()));
            preparedStatement.setInt(++index, Integer.parseInt(stuurgegevens.getOntvangendePartij().getValue().getValue()));
            preparedStatement.setString(++index, stuurgegevens.getReferentienummer().getValue().getValue());
            return preparedStatement;
        } catch (final SQLException e) {
            LOGGER.error("Fout opgetreden bij wegschrijven van leveringbericht.", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (final SQLException e) {
                    LOGGER.error("Fout opgetreden bij sluiten van connectie.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (final SQLException e) {
                    LOGGER.error("Fout opgetreden bij sluiten van statement.", e);
                }
            }
        }
        return null;
    }

    /**
     * Loop door alle bijgehouden personen en verwerk de nieuwe situatie.
     *
     * @param personen de lijst van bijgehouden personen.
     */
    private void verwerkBijgehoudenPersonen(final List<ObjecttypePersoon> personen) {
        LOGGER.info("Aantal bijgehouden persoon(en): {}", personen.size());

        for (final ObjecttypePersoon persoon : personen) {
            final String naam = geefNaam(persoon);
            final String geslacht = geefGeslacht(persoon);
            final String bsn = geefBsn(persoon);
            final String verwerkingssoort = geefVerwerkingssoort(persoon);

            LOGGER.debug("Bijgehouden persoon:{}({}), BSN={}, verwerkingssoort={}", naam, geslacht, bsn, verwerkingssoort);
        }
    }

    /**
     * Geeft de verwerkingssoort van een persoon.
     *
     * @param persoon de persoon
     * @return verwerkingsssoort van de persoon
     */
    private String geefVerwerkingssoort(final ObjecttypePersoon persoon) {
        final String verwerkingssoort;
        if (persoon.getVerwerkingssoort() != null) {
            verwerkingssoort = persoon.getVerwerkingssoort().value();
        } else {
            verwerkingssoort = "geen, waarschijnlijk een volledigbericht.";
        }
        return verwerkingssoort;
    }

    /**
     * Geeft de bsn van een persoon.
     *
     * @param persoon de persoon
     * @return bsn van de persoon
     */
    private String geefBsn(final ObjecttypePersoon persoon) {
        final String bsn;
        if (persoon.getIdentificatienummers() != null
            && !persoon.getIdentificatienummers().isEmpty()
            && persoon.getIdentificatienummers().get(0) != null
            && persoon.getIdentificatienummers().get(0).getBurgerservicenummer() != null)
        {
            bsn = persoon.getIdentificatienummers().get(0).getBurgerservicenummer().getValue().getValue();
        } else {
            bsn = STREEPJE;
        }
        return bsn;
    }

    /**
     * Geeft het geslacht van de persoon.
     *
     * @param persoon de persoon
     * @return het geslacht van de persoon
     */
    private String geefGeslacht(final ObjecttypePersoon persoon) {
        final String geslacht;
        if (persoon.getGeslachtsaanduiding() != null
            && !persoon.getGeslachtsaanduiding().isEmpty()
            && persoon.getGeslachtsaanduiding().get(0) != null
            && persoon.getGeslachtsaanduiding().get(0).getCode() != null)
        {
            geslacht = persoon.getGeslachtsaanduiding().get(0).getCode().getValue().getValue().name();
        } else {
            geslacht = STREEPJE;
        }
        return geslacht;
    }

    /**
     * Geeft de naam van de persoon.
     *
     * @param persoon de persoon
     * @return de naam
     */
    private String geefNaam(final ObjecttypePersoon persoon) {
        final String naam;
        if (persoon.getGeslachtsnaamcomponenten() != null
            && persoon.getGeslachtsnaamcomponenten().getValue() != null
            && persoon.getVoornamen() != null && persoon.getVoornamen().getValue() != null)
        {
            naam = String.format("%s %s",
                stelVoornamenSamen(persoon),
                stelAchternaamSamen(persoon.getGeslachtsnaamcomponenten().getValue().getGeslachtsnaamcomponent()));
        } else {
            naam = Arrays.toString(persoon.getSamengesteldeNaam().toArray());
        }
        return naam;
    }

    /**
     * Maakt een leesbare string van de geslachtsnamen.
     *
     * @param geslachtsnaamcomponenten set geslachtsnamen
     * @return achternamen string
     */
    private String stelAchternaamSamen(final List<ObjecttypePersoonGeslachtsnaamcomponent> geslachtsnaamcomponenten) {
        final StringBuilder achternaam = new StringBuilder();
        for (final ObjecttypePersoonGeslachtsnaamcomponent component : geslachtsnaamcomponenten) {
            if (component.getVoorvoegsel() != null && component.getVoorvoegsel().getValue() != null) {
                achternaam.append(component.getVoorvoegsel().getValue().getValue());
            }
            if (component.getScheidingsteken() != null && component.getScheidingsteken().getValue() != null) {
                achternaam.append(component.getScheidingsteken().getValue().getValue());
            }
            achternaam.append(component.getStam().getValue().getValue());
            achternaam.append(SPATIE);
        }
        return achternaam.toString();
    }

    /**
     * Maakt een leesbare string van de voornamen.
     *
     * @param persoon de persoon
     * @return voornamen string
     */
    private String stelVoornamenSamen(final ObjecttypePersoon persoon) {
        if (persoon.getVoornamen() != null && persoon.getVoornamen().getValue() != null
            && persoon.getVoornamen().getValue().getVoornaam() != null)
        {
            final StringBuilder voornamen = new StringBuilder();
            for (final ObjecttypePersoonVoornaam voornaam : persoon.getVoornamen().getValue().getVoornaam()) {
                voornamen.append(voornaam.getNaam().getValue().getValue());
                voornamen.append(SPATIE);
            }
            return voornamen.toString();
        }
        return "";
    }

}
