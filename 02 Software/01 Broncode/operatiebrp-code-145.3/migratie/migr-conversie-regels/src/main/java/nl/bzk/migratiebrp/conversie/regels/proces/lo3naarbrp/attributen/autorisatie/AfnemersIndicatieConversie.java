/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;
import org.springframework.stereotype.Component;

/**
 * Converteert een LO3 Autorisatie naar de corresponderende BRP groepen.
 */
@Component
public final class AfnemersIndicatieConversie {

    /**
     * Nabewerking: vul afgeleide gegevens.
     *
     * BRP Stapel<Afnemersindicatie>:
     * @param brpAfnemersindicaties BRP afnemersindicaties
     * @return bewerkte BRP afnemersindicaties
     */
    public BrpAfnemersindicaties vulAfgeleideGegevens(final BrpAfnemersindicaties brpAfnemersindicaties) {
        final List<BrpAfnemersindicatie> afnemersindicaties = new ArrayList<>();

        for (final BrpAfnemersindicatie afnemersindicatie : brpAfnemersindicaties.getAfnemersindicaties()) {
            final List<BrpGroep<BrpAfnemersindicatieInhoud>> groepen = new ArrayList<>();

            if (afnemersindicatie.getAfnemersindicatieStapel() != null) {
                for (final BrpGroep<BrpAfnemersindicatieInhoud> groep : afnemersindicatie.getAfnemersindicatieStapel()) {

                    final BrpAfnemersindicatieInhoud inhoud =
                            new BrpAfnemersindicatieInhoud(
                                    groep.getInhoud().getDatumAanvangMaterielePeriode(),
                                    groep.getInhoud().getDatumEindeVolgen(),
                                    groep.getInhoud().isLeeg());

                    final BrpHistorie historie =
                            new BrpHistorie(
                                    groep.getHistorie().getDatumTijdRegistratie(),
                                    groep.getHistorie().getDatumTijdVerval(),
                                    groep.getHistorie().getNadereAanduidingVerval());

                    groepen.add(new BrpGroep<>(inhoud, historie, groep.getActieInhoud(), groep.getActieVerval(), null));
                }
                // Op dit moment is levering autorisatie *NOOIT* gevuld in de afnemersindicatie, omdat die alleen
                // gebruikt wordt bij het uitlezen tbv testen.
                afnemersindicaties.add(new BrpAfnemersindicatie(afnemersindicatie.getPartijCode(), new BrpStapel<>(groepen), null));
            }

        }

        return new BrpAfnemersindicaties(brpAfnemersindicaties.getAdministratienummer(), afnemersindicaties);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Samenvoegen van stapel met dezelfde afnemersindicatie.
     * @param lo3Afnemersindicatie lo3 afnemersindicaties
     * @return lo3 afnemersindicaties
     */
    public Lo3Afnemersindicatie filterStapels(final Lo3Afnemersindicatie lo3Afnemersindicatie) {
        final Map<String, Lo3Stapel<Lo3AfnemersindicatieInhoud>> perPartij = new HashMap<>();

        // Stapels verzamelen per afnemer
        for (final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatie : lo3Afnemersindicatie.getAfnemersindicatieStapels()) {
            final String partij = getAfnemer(afnemersindicatie);
            if (partij == null) {
                Foutmelding.logMeldingFout(maakHerkomstVoorStapel(afnemersindicatie), LogSeverity.ERROR, SoortMeldingCode.AFN002, null);
                continue;
            }

            final Lo3Stapel<Lo3AfnemersindicatieInhoud> gecontroleerdeStapel = controleerDatumsBinnenStapel(afnemersindicatie);
            if (gecontroleerdeStapel == null) {
                continue;
            }

            if (perPartij.containsKey(partij)) {
                if (vergelijkAfnemersindicaties(perPartij.get(partij), gecontroleerdeStapel)) {
                    // true, betekend dat we 'afnemersindicatie' negeren
                    Foutmelding.logMeldingFout(maakHerkomstVoorStapel(afnemersindicatie), LogSeverity.ERROR, SoortMeldingCode.AFN004, null);
                } else {
                    // false, betekent dat we 'perPartij.get(partij)' negeren.
                    Foutmelding.logMeldingFout(maakHerkomstVoorStapel(perPartij.get(partij)), LogSeverity.ERROR, SoortMeldingCode.AFN004, null);
                    perPartij.put(partij, gecontroleerdeStapel);
                }
            } else {
                // Geen eerdere stapel, gebruik deze
                perPartij.put(partij, gecontroleerdeStapel);
            }
        }

        // Per afnemer stapels samenvoegen en nabewerken
        return new Lo3Afnemersindicatie(lo3Afnemersindicatie.getANummer(), new ArrayList<>(perPartij.values()));
    }

    /**
     * Vergelijk afnemersindicaties.
     * @param afnemersindicatie1 stapel 1
     * @param afnemersindicatie2 stapel 2
     * @return true, als afnemersindicatie1 relevanter (of even relevant) is dan afnemersindicatie2
     */
    private boolean vergelijkAfnemersindicaties(
            final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatie1,
            final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatie2) {
        final boolean actueel1 = bepaalActueel(afnemersindicatie1);
        final boolean actueel2 = bepaalActueel(afnemersindicatie2);

        final boolean resultaat;
        if (actueel1) {
            if (actueel2) {
                // Beide actueel, dan oudste datum relevantst
                final int ingang1 = bepaalIngang(afnemersindicatie1);
                final int ingang2 = bepaalIngang(afnemersindicatie2);

                resultaat = ingang1 <= ingang2;
            } else {
                resultaat = true;
            }
        } else {
            if (actueel2) {
                resultaat = false;
            } else {
                // Beide actueel, dan meest recente datum relevantst
                final int ingang1 = bepaalIngang(afnemersindicatie1);
                final int ingang2 = bepaalIngang(afnemersindicatie2);

                resultaat = ingang1 >= ingang2;
            }
        }
        return resultaat;
    }

    private boolean bepaalActueel(final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatie) {
        return afnemersindicatie.get(0).getInhoud().getAfnemersindicatie() != null;
    }

    private int bepaalIngang(final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatie) {
        return afnemersindicatie.get(0).getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde();
    }

    /**
     * Controleer datums binnen stapel
     * @param afnemersindicatie stapel
     * @return stapel, of null als de hele stapel is verwijderd
     */
    private Lo3Stapel<Lo3AfnemersindicatieInhoud> controleerDatumsBinnenStapel(final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatie) {
        final List<Lo3Categorie<Lo3AfnemersindicatieInhoud>> categorieen = afnemersindicatie.getCategorieen();
        Collections.sort(categorieen, new AflopendHerkomstComparator());

        boolean ongeldig = false;
        int laatsteDatum = 0;
        for (final Lo3Categorie<Lo3AfnemersindicatieInhoud> categorie : categorieen) {
            final int datum = categorie.getHistorie().getDatumVanOpneming().getIntegerWaarde();
            if (datum < laatsteDatum) {
                ongeldig = true;
            }
            laatsteDatum = datum;
        }

        final Lo3Stapel<Lo3AfnemersindicatieInhoud> result;
        if (ongeldig) {
            final Lo3Categorie<Lo3AfnemersindicatieInhoud> eersteVoorkomen = categorieen.get(categorieen.size() - 1);

            if (eersteVoorkomen.getInhoud().isLeeg()) {
                Foutmelding.logMeldingFout(maakHerkomstVoorStapel(afnemersindicatie), LogSeverity.ERROR, SoortMeldingCode.AFN006, null);
                result = null;
            } else {
                Foutmelding.logMeldingFout(maakHerkomstVoorStapel(afnemersindicatie), LogSeverity.SUPPRESSED, SoortMeldingCode.AFN007, null);
                result = new Lo3Stapel<>(Collections.singletonList(eersteVoorkomen));
            }
        } else {
            result = afnemersindicatie;
        }
        return result;
    }

    private String getAfnemer(final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatieStapel) {
        for (final Lo3Categorie<Lo3AfnemersindicatieInhoud> categorie : afnemersindicatieStapel) {
            final String afnemersindicatie = categorie.getInhoud().getAfnemersindicatie();
            if (afnemersindicatie != null) {
                return afnemersindicatie;
            }
        }

        return null;
    }

    /* ************************************************************************************************************* */

    private Lo3Herkomst maakHerkomstVoorStapel(final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        final Lo3Herkomst herkomst = stapel.get(0).getLo3Herkomst();
        return new Lo3Herkomst(herkomst.getCategorie(), herkomst.getStapel(), -1);
    }

    /**
     * Sorteer aflopend op datum ingang geldigheid.
     */
    public static final class AflopendHerkomstComparator implements Comparator<Lo3Categorie<? extends Lo3CategorieInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Categorie<? extends Lo3CategorieInhoud> o1, final Lo3Categorie<? extends Lo3CategorieInhoud> o2) {
            return Integer.valueOf(o2.getLo3Herkomst().getVoorkomen()).compareTo(o1.getLo3Herkomst().getVoorkomen());
        }
    }
}
