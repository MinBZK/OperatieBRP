/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte.GeboorteGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsaanduiding.GeslachtsaanduidingGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsnaamcomponent.GeslachtsnaamcomponentVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identificatienummers.IdentificatienummersGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam.SamengesteldeNaamGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.voornaam.VoornamenVerwerker;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;


/**
 * Utility klasse voor het verzamelen van verwerkers voor de groepen van een persoon.
 * De opzet is simpel: loop alle mogelijke groepen van het persoon bericht af
 * en maak voor elke groep die op het bericht aanwezig is de juiste verwerker aan.
 * <p/>
 * NB: Deze klasse bevat nog niet alle groepen uit het model. Het doel is deze naar behoefte steeds verder uit te
 * breiden tot alle groepen toegevoegd zijn.
 */
public final class PersoonGroepVerwerkersUtil {

    /**
     * Util klasse, dus private constructor.
     */
    private PersoonGroepVerwerkersUtil() {
    }

    /**
     * Zie javadoc voor de methode (hieronder) met extra parameter.
     *
     * @param persoonUitBericht .
     * @param persoonHisVolledig .
     * @param actie .
     * @return .
     */
    public static List<Verwerkingsregel> bepaalAlleVerwerkingsregels(final PersoonBericht persoonUitBericht,
            final PersoonHisVolledigImpl persoonHisVolledig, final ActieModel actie)
    {
        return bepaalAlleVerwerkingsregels(persoonUitBericht, persoonHisVolledig, actie, null);
    }

    /**
     * Bepaal alle verwerkingsregels die voor een nieuw of bestaand persoon uitgevoerd moeten worden.
     * <p/>
     * <p>
     * <b>Let op:</b> deze methode voegt eventueel voornamen en/of geslachtsnaamcomponenten toe aan de
     * <code>persoonHisVolledig</code> voor elke wel in het bericht aanwezige instantie van deze objecten die nog niet
     * in de <code>persoonHisVolledig</code> aanwezig zijn.
     * </p>
     *
     * @param persoonUitBericht de persoon zoals in het bericht
     * @param persoonHisVolledig de persoon his volledig (nieuw aangemaakt of uit de database)
     * @param actie de opgeslagen actie
     * @param adresgevendeOuder de adresgevendeOuder (bij een nieuwe inschrijving) of 'null' indien niet van toepassing
     * @return een lijst met verwerkingsregels voor de data van de persoon
     */
    public static List<Verwerkingsregel> bepaalAlleVerwerkingsregels(final PersoonBericht persoonUitBericht,
            final PersoonHisVolledigImpl persoonHisVolledig, final ActieModel actie,
            final PersoonHisVolledig adresgevendeOuder)
    {
        final List<Verwerkingsregel> verwerkingsregels = new ArrayList<>();

        if (persoonUitBericht.getIdentificatienummers() != null) {
            verwerkingsregels.add(new IdentificatienummersGroepVerwerker(persoonUitBericht, persoonHisVolledig, actie));
        }

        if (persoonUitBericht.getSamengesteldeNaam() != null) {
            verwerkingsregels.add(new SamengesteldeNaamGroepVerwerker(persoonUitBericht, persoonHisVolledig, actie));
        }

        if (persoonUitBericht.getGeboorte() != null) {
            verwerkingsregels.add(new GeboorteGroepVerwerker(persoonUitBericht, persoonHisVolledig, actie,
                    adresgevendeOuder));
        }

        if (persoonUitBericht.getGeslachtsaanduiding() != null) {
            verwerkingsregels.add(new GeslachtsaanduidingGroepVerwerker(persoonUitBericht, persoonHisVolledig, actie));
        }

        // Voornamen worden altijd 'met zijn allen' tegelijk verwerkt.
        if (persoonUitBericht.getVoornamen() != null) {
            verwerkingsregels.add(new VoornamenVerwerker(persoonUitBericht, persoonHisVolledig, actie));
        }

        if (persoonUitBericht.getGeslachtsnaamcomponenten() != null) {
            verwerkingsregels.addAll(localiseerGeslachtsnaamVerwerkers(persoonUitBericht, persoonHisVolledig, actie));
        }

        return verwerkingsregels;
    }

    private static List<Verwerkingsregel> localiseerGeslachtsnaamVerwerkers(final PersoonBericht persoonUitBericht,
            final PersoonHisVolledigImpl persoonHisVolledig, final ActieModel actie)
    {

        final List<Verwerkingsregel> gevondenRegels = new ArrayList<>();

        for (final PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponentBericht : persoonUitBericht
                .getGeslachtsnaamcomponenten())
        {
            PersoonGeslachtsnaamcomponentHisVolledigImpl persoonGeslachtsnaamcomponentHisVolledig = null;
            for (final PersoonGeslachtsnaamcomponentHisVolledigImpl geslachtsnaamcomponentHisVolledig : persoonHisVolledig
                    .getGeslachtsnaamcomponenten())
            {
                if (geslachtsnaamcomponentBericht.getVolgnummer().equals(
                        geslachtsnaamcomponentHisVolledig.getVolgnummer()))
                {
                    persoonGeslachtsnaamcomponentHisVolledig = geslachtsnaamcomponentHisVolledig;
                    break;
                }
            }

            if (persoonGeslachtsnaamcomponentHisVolledig == null) {
                persoonGeslachtsnaamcomponentHisVolledig =
                        new PersoonGeslachtsnaamcomponentHisVolledigImpl(persoonHisVolledig,
                                geslachtsnaamcomponentBericht.getVolgnummer());
                persoonHisVolledig.getGeslachtsnaamcomponenten().add(persoonGeslachtsnaamcomponentHisVolledig);
            }

            gevondenRegels.add(new GeslachtsnaamcomponentVerwerker(geslachtsnaamcomponentBericht,
                    persoonGeslachtsnaamcomponentHisVolledig, actie));
        }
        return gevondenRegels;
    }
}
