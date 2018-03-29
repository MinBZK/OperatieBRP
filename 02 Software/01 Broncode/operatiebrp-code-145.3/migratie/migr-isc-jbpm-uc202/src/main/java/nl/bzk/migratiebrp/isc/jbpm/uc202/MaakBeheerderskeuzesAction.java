/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Kandidaat;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.FoutUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.jsf.FoutafhandelingPaden;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc808.PersoonslijstOverzichtBuilder;
import org.springframework.stereotype.Component;

/**
 * Maak de mogelijke beheerderskeuzes obv van het sync naar brp antwoord. Zet ook alle gegevens 'klaar' voor het beheerderscherm.
 */
@Component("uc202MaakBeheerderkeuzesAction")
public final class MaakBeheerderskeuzesAction implements SpringAction {

    /**
     * Keuze: Verwerking afbreken.
     */
    public static final String KEUZE_AFBREKEN = "afbreken";
    /**
     * Keuze: Verwerking afbreken (met Pf03).
     */
    public static final String KEUZE_AFBREKEN_MET_PF03 = "afbrekenMetPf03";
    /**
     * Keuze: PL opnemen als nieuwe persoonslijst.
     */
    public static final String KEUZE_NIEUW = "toevoegen";
    /**
     * Keuze: PL negeren.
     */
    public static final String KEUZE_NEGEREN = "negeren";
    /**
     * Keuze: PL afkeuren.
     */
    public static final String KEUZE_AFKEUREN = "afkeuren";
    /**
     * Keuze: PL opnieuw proberen te verwerken.
     */
    public static final String KEUZE_OPNIEUW = "opnieuw";
    /**
     * Keuze: PL opnieuw proberen te verwerken (automatisch).
     */
    public static final String KEUZE_AUTOMATISCH_OPNIEUW = "automatisch";
    /**
     * Keuze (prefix): vervang PL.
     */
    public static final String KEUZE_VERVANGEN_PREFIX = "vervang";

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakBeheerderskeuzesAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    /**
     * OP basis van de anummers van de bericht pl (uit input) en de kandidaat pl-en worden de mogelijke keuze gemaakt. Als het actuele a-nummer van de bericht
     * PL *niet* voorkomt in de lijst van actuele anummers van de kandidaat pl-en dan zal de keuze 'opvoeren als nieuwe pl met a-nummer X' worden gegeven.
     * 'Vervang pl met a-nummer X' zal als keuze worden gegeven voor het actuele anummer van de alle pl-en. De lijst met keuze wordt gecompleteerd met de keuze
     * 'negeren'.
     * @param parameters input
     * @return output
     */
    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long verzoekId = (Long) parameters.get("synchroniseerNaarBrpVerzoekBericht");
        final SynchroniseerNaarBrpVerzoekBericht verzoek = (SynchroniseerNaarBrpVerzoekBericht) berichtenDao.leesBericht(verzoekId);
        final Long antwoordId = (Long) parameters.get("synchroniseerNaarBrpAntwoordBericht");
        final SynchroniseerNaarBrpAntwoordBericht antwoord = (SynchroniseerNaarBrpAntwoordBericht) berichtenDao.leesBericht(antwoordId);

        // Eerste PL is de persoonslijst uit het verzoek, de te verwerken PL
        final PersoonslijstOverzichtBuilder overzichtBuilder = new PersoonslijstOverzichtBuilder();
        final FoutafhandelingPaden foutafhandelingPaden = new FoutafhandelingPaden();

        verwerkPersoonslijsten(overzichtBuilder, foutafhandelingPaden, verzoek.getLo3PersoonslijstAlsTeletexString(),
                sorteerKandidaten(antwoord.getKandidaten()));

        foutafhandelingPaden.put(KEUZE_NEGEREN, "Persoonslijst negeren", false, false);
        foutafhandelingPaden.put(KEUZE_AFKEUREN, "Persoonslijst afkeuren", false, false);
        foutafhandelingPaden.put(KEUZE_OPNIEUW, "Persoonslijst opnieuw proberen te verwerken (zonder keuze)", false, false);
        foutafhandelingPaden.put(KEUZE_AFBREKEN, "Verwerking afbreken", false, false);
        foutafhandelingPaden.put(KEUZE_AFBREKEN_MET_PF03, "Verwerking afbreken (met Pf03)", true, false);
        foutafhandelingPaden.put(KEUZE_AUTOMATISCH_OPNIEUW, null, false, false);

        final Map<String, Object> result = new HashMap<>();
        result.put("foutafhandelingIndicatieBeheerder", true);
        result.put("foutafhandelingFout", "uc202.syncnaarbrp.onduidelijk");
        result.put("foutafhandelingFoutmelding", FoutUtil.beperkFoutmelding(antwoord.getMelding()));
        result.put("foutafhandelingType", "uc808");
        result.put("foutafhandelingPaden", foutafhandelingPaden);
        result.put("persoonslijstOverzicht", overzichtBuilder.build());

        LOG.debug("result: {}", result);
        return result;
    }

    private List<KandidaatDTO> sorteerKandidaten(List<Kandidaat> kandidaten) {
        List<KandidaatDTO> resultaat = new ArrayList<>();
        for (Kandidaat kandidaat : kandidaten) {
            resultaat.add(new KandidaatDTO(kandidaat));
        }

        Collections.sort(resultaat);
        return resultaat;
    }

    /**
     * Verwerkt de persoonslijsten; voegt ze toe aan het overzicht en bepaalt de mogelijke foutpaden (toevoegen en vervangen).
     * @param overzichtBuilder overzicht builder
     * @param foutafhandelingPaden foutpaden
     * @param aangebodenPl aangeboden pl
     * @param kandidaten kandidaten
     */
    private void verwerkPersoonslijsten(final PersoonslijstOverzichtBuilder overzichtBuilder, final FoutafhandelingPaden foutafhandelingPaden,
                                        final String aangebodenPl, final List<KandidaatDTO> kandidaten) {
        final List<Lo3CategorieWaarde> verzoekPl = parse(aangebodenPl);
        final String verzoekAnummer = bepaalAnummer(verzoekPl);
        final String verzoekBurgerServicenummer = bepaalBsn(verzoekPl);
        overzichtBuilder.voegPersoonslijstToe(verzoekPl);

        boolean anummerKomtVoor = false;
        boolean bsnKomtVoor = false;

        final List<String> andereAnummers = new ArrayList<>();
        final List<String> andereBsns = new ArrayList<>();

        for (final KandidaatDTO kandidaat : kandidaten) {
            final List<Lo3CategorieWaarde> kandidaatPl = kandidaat.getPl();

            final String kandidaatAnummer = kandidaat.getAnummer();
            final String kandidaatBurgerServicenummer = kandidaat.getBsn();

            if (Objects.equals(verzoekAnummer, kandidaatAnummer)) {
                anummerKomtVoor = true;
            } else {
                andereAnummers.add(KEUZE_VERVANGEN_PREFIX + kandidaat.getPersoonId());
            }
            if (Objects.equals(verzoekBurgerServicenummer, kandidaatBurgerServicenummer)) {
                bsnKomtVoor = true;
            } else {
                andereBsns.add(KEUZE_VERVANGEN_PREFIX + kandidaat.getPersoonId());
            }

            overzichtBuilder.voegPersoonslijstToe(kandidaatPl);
            foutafhandelingPaden.put(KEUZE_VERVANGEN_PREFIX + kandidaat.getPersoonId(), "Vervang persoonslijst met a-nummer " + kandidaatAnummer, false, false);
        }

        if (anummerKomtVoor) {
            // Als het aangeboden a-nummer actueel voorkomt dan moeten we alle 'vervang' keuzes verwijderen die een
            // 'ander' a-nummer zouden wijzigen in het reeds actueel voorkomende aangeboden a-nummer.
            foutafhandelingPaden.removeAll(andereAnummers);
        }
        if (bsnKomtVoor) {
            // Als het aangeboden burgerservicenummer actueel voorkomt dan moeten we alle 'vervang' keuzes verwijderen die een
            // 'ander' burgerservicenummer zouden wijzigen in het reeds actueel voorkomende aangeboden burgerservicenummer.
            foutafhandelingPaden.removeAll(andereBsns);
        }

        if (!anummerKomtVoor && !bsnKomtVoor) {
            // Als het beide het aangeboden a-nummer en burgerservicenummer niet actueel voorkomen, dan kan de persoonslijst
            // worden opgevoerd als nieuwe persoonslijst
            foutafhandelingPaden.put(KEUZE_NIEUW,
                    "Opvoeren als nieuwe persoonslijst (met a-nummer " + verzoekAnummer + " en burgerservicenummer " + verzoekBurgerServicenummer + ")", false,
                    false);
        }
    }

    private String bepaalBsn(List<Lo3CategorieWaarde> persoonslijst) {
        return Lo3CategorieWaardeUtil.getElementWaarde(persoonslijst, Lo3CategorieEnum.PERSOON, 0, 0, Lo3ElementEnum.BURGERSERVICENUMMER);
    }

    private String bepaalAnummer(List<Lo3CategorieWaarde> persoonslijst) {
        return Lo3CategorieWaardeUtil.getElementWaarde(persoonslijst, Lo3CategorieEnum.PERSOON, 0, 0, Lo3ElementEnum.ANUMMER);
    }

    private List<Lo3CategorieWaarde> parse(final String lo3BerichtAsTeletexString) {
        try {
            return Lo3Inhoud.parseInhoud(lo3BerichtAsTeletexString);
        } catch (final BerichtSyntaxException e) {
            LOG.debug("Ongeldig bericht. Parse retourneert lege lijst", e);
            return Collections.emptyList();
        }
    }


    private final class KandidaatDTO implements Comparable<KandidaatDTO> {

        private final List<Lo3CategorieWaarde> pl;
        private final Long persoonId;
        private final String anummer;
        private final String bsn;

        KandidaatDTO(Kandidaat kandidaat) {
            pl = parse(kandidaat.getLo3PersoonslijstAlsTeletexString());
            persoonId = kandidaat.getPersoonId();

            anummer = bepaalAnummer(pl);
            bsn = bepaalBsn(pl);
        }

        public List<Lo3CategorieWaarde> getPl() {
            return pl;
        }

        public Long getPersoonId() {
            return persoonId;
        }

        public String getAnummer() {
            return anummer;
        }

        public String getBsn() {
            return bsn;
        }

        @Override
        public int compareTo(final KandidaatDTO that) {
            int result = anummer.compareTo(that.anummer);
            if (result == 0) {
                result = persoonId.compareTo(that.persoonId);
            }
            return result;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final KandidaatDTO that = (KandidaatDTO) o;
            return persoonId.equals(that.persoonId) && anummer.equals(that.anummer);
        }

        @Override
        public int hashCode() {
            int result = persoonId.hashCode();
            result = 31 * result + anummer.hashCode();
            return result;
        }
    }


}
