/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.service.impl;

import java.math.BigDecimal;
import java.util.Set;

import nl.bzk.brp.poc.berichtenverwerker.dataaccess.ActieDAO;
import nl.bzk.brp.poc.berichtenverwerker.dataaccess.PersoonDAO;
import nl.bzk.brp.poc.berichtenverwerker.dataaccess.PersoonsNationaliteitDAO;
import nl.bzk.brp.poc.berichtenverwerker.model.Actie;
import nl.bzk.brp.poc.berichtenverwerker.model.Bron;
import nl.bzk.brp.poc.berichtenverwerker.model.Nation;
import nl.bzk.brp.poc.berichtenverwerker.model.Pers;
import nl.bzk.brp.poc.berichtenverwerker.model.Persnation;
import nl.bzk.brp.poc.berichtenverwerker.service.PersoonEnNationaliteitService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * Standaard implementatie van de {@link PersoonEnNationaliteitService} service. Deze service biedt operaties voor het
 * toevoegen, verwijderen en wijzigen van de nationaliteit van een persoon.
 */
public class PersoonEnNationaliteitServiceImpl implements PersoonEnNationaliteitService {

    private final Logger             logger = LoggerFactory.getLogger(PersoonEnNationaliteitServiceImpl.class);

    @Autowired
    private PersoonDAO               persoonDAO;
    @Autowired
    private PersoonsNationaliteitDAO persoonsNationaliteitDAO;
    @Autowired
    private ActieDAO                 actieDAO;

    @Override
    @Transactional
    public final void toevoegenNationaliteit(final Actie actie, final Set<Bron> bronnen, final BigDecimal bsn,
            final int nationId, final BigDecimal datumAanvangGeldigheid)
    {
        logger.debug(String.format("Toevoegen Nationaliteit aangeroepen"));

        actieDAO.voegToeActie(actie);
        logger.debug(String.format("Actie toegevoegd met id '%s'", actie.getId()));

        Pers persoon = persoonDAO.vindPersoonOpBasisVanBsn(bsn);
        logger.debug(String.format("Persoon opgehaald: %s", persoon));

        Nation nation = new Nation();
        nation.setId(nationId);

        Persnation persoonsNationaliteit = bouwPersoonsNationaliteit(actie, persoon, nation, datumAanvangGeldigheid);
        persoonsNationaliteitDAO.voegToePersoonsNationaliteit(persoonsNationaliteit);
        logger.debug(String.format("PersoonsNationaliteit toegevoegd met id: %s", persoonsNationaliteit.getId()));
    }

    /**
     * Bouwt een nieuwe {@link Persnation} instantie op basis van de opgegeven, {@code actie}, {@code persoon} en
     * {@code nation}.
     *
     * @param actie de actie die leidt tot het creeren van de persoonsnationaliteit.
     * @param persoon de persoon voor wie de persoonsnationaliteit wordt gebouwd.
     * @param nation de nationaliteit waarvoor een persoonsnationaliteit wordt gebouwd.
     * @param datumAanvangGeldigheid de datum vanaf wanneer de persoonsnationaliteit geldig is.
     * @return de nieuwe persoonsnationaliteit.
     */
    private Persnation bouwPersoonsNationaliteit(final Actie actie, final Pers persoon, final Nation nation,
            final BigDecimal datumAanvangGeldigheid)
    {
        Persnation persoonsNationaliteit = new Persnation();
        persoonsNationaliteit.setActie(actie);
        persoonsNationaliteit.setPers(persoon);
        persoonsNationaliteit.setNation(nation);
        persoonsNationaliteit.setDattijdreg(actie.getTijdstipreg());
        persoonsNationaliteit.setDataanvgel(datumAanvangGeldigheid);
        return persoonsNationaliteit;
    }

    @Override
    @Transactional
    public final void opheffenNationaliteit(final Actie actie, final Set<Bron> bronnen,
            final long persoonsNationaliteitId)
    {
        logger.debug(String.format("Opheffen Nationaliteit aangeroepen"));

        actieDAO.voegToeActie(actie);
        logger.debug(String.format("Actie toegevoegd met id '%s'", actie.getId()));

        Persnation persoonsNationaliteit =
                persoonsNationaliteitDAO.vindPersoonsNationaliteitOpBasisVanId(persoonsNationaliteitId);
        persoonsNationaliteitDAO.verwijderPersoonsNationaliteit(actie, persoonsNationaliteit);
        logger.debug(String.format("PersoonsNationaliteit verwijderd met id: %s", persoonsNationaliteit.getId()));
    }

    @Override
    @Transactional
    public final void wijzigPersoonNationaliteit(final Actie actie, final Set<Bron> bronnen,
            final long persoonsNationaliteitId)
    {
        // TODO Auto-generated method stub

    }

}
