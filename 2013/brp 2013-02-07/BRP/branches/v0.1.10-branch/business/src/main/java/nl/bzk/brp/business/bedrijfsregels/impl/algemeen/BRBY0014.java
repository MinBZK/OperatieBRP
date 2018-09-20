/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.algemeen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementatie van bedrijfsregel BRBY0014.
 * <p/>
 * De persoon van wie persoonsgegevens worden bijgehouden moet aanwezig zijn in het systeem.
 * De persoon moet geïdentificeerd worden met 1 van de volgende gegevens:
 * - BSN
 * - A-nummer
 * - Persoon ID
 * <p/>
 * Merk op dat op dit moment alleen een BSN als identificerend wordt beschouwd.
 *
 * @brp.bedrijfsregel BRBY0014
 */
public class BRBY0014 implements ActieBedrijfsRegel<RootObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0014.class);

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BRBY0014";
    }

    @Override
    public List<Melding> executeer(final RootObject huidigeSituatie, final RootObject nieuweSituatie,
        final Actie actie)
    {
        final List<Melding> meldingen;

        if (nieuweSituatie == null) {
            meldingen = new ArrayList<Melding>();
            LOGGER.warn("Bedrijfsregel BRBY0014 aangeroepen met null waarde voor nieuwe situatie. Bedrijfsregel "
                + "daarom niet verder uitgevoerd (geen meldingen naar gebruiker).");
        } else {
            if (nieuweSituatie instanceof Persoon) {
                meldingen = controleerPersoon((Persoon) nieuweSituatie);
            } else if (nieuweSituatie instanceof Relatie) {
                meldingen = controleerRelatie((Relatie) nieuweSituatie);
            } else {
                meldingen = Arrays.asList(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001));
                LOGGER.error("Onbekend type van rootobject gedetecteerd. Algemene fout/melding gegenereerd.");
            }
        }
        return meldingen;
    }

    /**
     * Controleert de opgegeven persoon of deze gevonden kan worden in het systeem. Hiervoor wordt gekeken naar de
     * identificeerbare gegevens in de persoon en op basis daarvan wordt bepaald of deze persoon in het systeem
     * bekend is.
     * <p/>
     * Indien er geen identificeerbare gegevens aanwezig zijn (of geen persoon), dan zal deze bedrijfsregel ook
     * aangeven dat de persoon niet kan worden gevonden.
     *
     * @param persoon de persoon met daarin de identificerende gegevens.
     * @return een lijst van meldingen; leeg indien er geen fouten zijn gedetecteerd, of gevuld met een of meerdere
     *         meldingen indien er fouten zijn opgetreden.
     */
    private List<Melding> controleerPersoon(final Persoon persoon) {
        final List<Melding> meldingen = new ArrayList<Melding>();

        if (geenIdentificerendeGegevensBeschikbaar(persoon)) {
            meldingen.add(bouwMelding(persoon, "Er is geen identificatie opgegeven van de betreffende persoon."));
        } else {
            Burgerservicenummer bsn = persoon.getIdentificatienummers().getBurgerservicenummer();
            if (persoonRepository.findByBurgerservicenummer(bsn) == null) {
                meldingen.add(bouwMelding(persoon,
                    String.format("De persoon met BSN %s komt niet voor in het systeem.", bsn.getWaarde())));
            }
        }
        return meldingen;
    }

    /**
     * Controleert of er voor de persoon identificerende gegevens aanwezig zijn.
     *
     * @param persoon de persoon die gecontroleerd dient te worden.
     * @return of er voor de persoon identificerende gegevens aanwezig zijn.
     */
    private boolean geenIdentificerendeGegevensBeschikbaar(final Persoon persoon) {
        return persoon.getIdentificatienummers() == null
            || persoon.getIdentificatienummers().getBurgerservicenummer() == null;
    }

    /**
     * Retourneert een nieuwe melding voor deze bedrijfsregel met de opgegeven omschrijving en de persoon waarvoor
     * de melding wordt gebouwd.
     *
     * @param persoon de persoon die niet in het systeem bekend is (of geidentificeerd kon worden).
     * @param meldingOmschrijving de omschrijving van de melding.
     * @return een nieuwe melding.
     */
    private Melding bouwMelding(final Persoon persoon, final String meldingOmschrijving) {
        final Melding melding;
        if (persoon instanceof Identificeerbaar) {
            melding = new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.BRBY0014, meldingOmschrijving,
                (Identificeerbaar) persoon, "persoon");
        } else {
            melding = new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.BRBY0014, meldingOmschrijving);
        }
        return melding;
    }

    /**
     * Controleert de opgegeven relatie of alle betrokkenen in de relatie in het systeem gevonden kunnen worden.
     * Hiervoor wordt voor alle betrokkenen gekeken naar de identificeerbare gegevens in de persoon en op basis
     * daarvan wordt bepaald of deze persoon in het systeem bekend is.
     * <p/>
     * Indien er geen identificeerbare gegevens aanwezig zijn (of geen persoon), dan zal deze bedrijfsregel ook
     * aangeven dat de persoon niet kan worden gevonden.
     *
     * @param relatie de relatie met daarin de personen met identificerende gegevens.
     * @return een lijst van meldingen; leeg indien er geen fouten zijn gedetecteerd, of gevuld met een of meerdere
     *         meldingen indien er fouten zijn opgetreden.
     */
    private List<Melding> controleerRelatie(final Relatie relatie) {
        final List<Melding> meldingen = new ArrayList<Melding>();

        if (relatie != null && relatie.getBetrokkenheden() != null) {
            for (Betrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
                Persoon betrokkene = betrokkenheid.getBetrokkene();
                meldingen.addAll(controleerPersoon(betrokkene));
            }
        }
        return meldingen;
    }


}
