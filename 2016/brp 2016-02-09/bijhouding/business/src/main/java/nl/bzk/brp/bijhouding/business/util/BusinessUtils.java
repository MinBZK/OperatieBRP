/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.util;

import java.util.List;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


/** utility class voor business laag. */
public final class BusinessUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /** . */
    private BusinessUtils() {
    }

    /**
     * Test of alle personen in de lijst van bijpersonen voorkomen in de lijst van de hoofdpersonen.
     *
     * @param hoofdPersonen de lijst van personen uit de hoofdactie
     * @param bijPersonen de lijst van personen uit deze subactie
     * @param code de melding code.
     * @param meldingen wordt aan de lijst een melding toegevoegd als er iets fout gaat.
     * @return true als alle personen hier in zitten, false anders.
     */
    public static boolean checkPersonenInHoofdpersonen(final List<Persoon> hoofdPersonen,
            final List<Persoon> bijPersonen, final Regel code, final List<Melding> meldingen)
    {
        if (CollectionUtils.isNotEmpty(bijPersonen)) {
            for (final Persoon pers : bijPersonen) {
                if (!persoonInLijst(hoofdPersonen, pers)) {
                    // foutmelding.
                    // TODO, we weten nog niet welk attribuut nu in de fout is.
                    meldingen
                            .add(new Melding(SoortMelding.FOUT, code, (BerichtIdentificeerbaar) pers, "identificatie"));
                }
            }
        }
        return true;
    }

    /**
     * Vergelijk of de een (bij) persoon overeenkomt met een van de hoofdpersonen.
     *
     * @param hoofdPersonen de lijst van de hoofdpersonen.
     * @param teTestenPersoon de te testen bij persoon.
     * @return true als bijpersoon onderdeel is van hoofd, false anders.
     */
    public static boolean persoonInLijst(final List<Persoon> hoofdPersonen, final Persoon teTestenPersoon) {
        final Integer objectSleutelDbId = ((PersoonBericht) teTestenPersoon).getObjectSleutelDatabaseID();
        final String referentieID = ((PersoonBericht) teTestenPersoon).getReferentieID();
        for (final Persoon pers : hoofdPersonen) {
            if (StringUtils.isNotBlank(referentieID)
                    && StringUtils.equals(referentieID, ((PersoonBericht) pers).getCommunicatieID())
                    || objectSleutelDbId != null
                    && objectSleutelDbId.equals(((PersoonBericht) pers).getObjectSleutelDatabaseID()))
            {
                // wordt vanuit de bij persoon gerefereert naar een hoofd persoon commID => true.
                // technische sleutel zijn beide zelfde.
                return true;
            }
        }
        return false;
    }

    /**
     * Retourneert het identificeerbare object in het bericht. Hiervoor worden een groep en object meegegeven, waarbij
     * het identificeerbare object standaard de groep is, maar als deze <code>null</code> is, dan het object als
     * identificeerbaar object wordt geretourneerd.
     *
     * @param object het object.
     * @param groep de groep.
     * @return het identificeerbare object.
     */
    public static BerichtIdentificeerbaar identificeerbaarObjectInBericht(final BerichtIdentificeerbaar object,
            final BerichtIdentificeerbaar groep)
    {
        if (groep == null) {
            return object;
        } else {
            return groep;
        }
    }

    /**
     * Bepaalt aan de hand van een persoon die al in de BRP staat (huidige situatie) de persoon die daarmee overeenkomt
     * in het bericht (nieuwe situatie).
     *
     * @param huidigeSituatie de persoon die al in de BRP staat geregistreerd.
     * @param nieuweSituatie een relatie uit een bericht.
     * @return PersoonBericht uit nieuwesituatie die overeenkomt met de persoon in de BRP.
     */
    public static PersoonBericht matchPersoonInRelatieBericht(final PersoonView huidigeSituatie,
            final RelatieBericht nieuweSituatie)
    {
        PersoonBericht gevondenPersoon;
        if (huidigeSituatie.getSoort().getWaarde() == SoortPersoon.NIET_INGESCHREVENE) {
            // Zoek de niet ingeschreven persoon in het bericht.
            gevondenPersoon = zoekNietIngeschrevenBetrokkenPersoon(nieuweSituatie);
        } else {
            gevondenPersoon = zoekBetrokkenPersoon(huidigeSituatie, nieuweSituatie);
        }

        if (gevondenPersoon == null) {
            LOGGER.warn(String.format("Kon persoon niet matchen met een persoon in het bericht."));
        }
        return gevondenPersoon;
    }

    private static PersoonBericht zoekBetrokkenPersoon(final PersoonView persoon, final RelatieBericht relatie) {
        PersoonBericht result = null;
        final Integer databaseId = persoon.getID();
        for (final BetrokkenheidBericht betrokkenheidBericht : relatie.getBetrokkenheden()) {
            final PersoonBericht persoonBericht = betrokkenheidBericht.getPersoon();
            // DatabaseId kan null zijn in het geval van een Kind dat wordt ingeschreven via het bericht.
            if (databaseId != null && databaseId.equals(persoonBericht.getObjectSleutelDatabaseID())) {
                result = persoonBericht;
            } else if (persoonBericht.getIdentificatienummers() != null
                && persoonBericht.getIdentificatienummers().getBurgerservicenummer() != null
                && persoonBericht.getIdentificatienummers().getBurgerservicenummer()
                        .equals(persoon.getIdentificatienummers().getBurgerservicenummer()))
            {
                result = persoonBericht;
            }
        }
        return result;
    }

    private static PersoonBericht zoekNietIngeschrevenBetrokkenPersoon(final RelatieBericht relatie) {
        PersoonBericht result = null;
        // TODO hier retourneren we een willekeurige (de laatste) betrokken niet-ingeschrevene; uitzoeken of dit gewenst
        // is
        for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
            if (betrokkenheid.getPersoon().getSoort().getWaarde() == SoortPersoon.NIET_INGESCHREVENE) {
                result = (PersoonBericht) betrokkenheid.getPersoon();
            }
        }
        return result;
    }
}
