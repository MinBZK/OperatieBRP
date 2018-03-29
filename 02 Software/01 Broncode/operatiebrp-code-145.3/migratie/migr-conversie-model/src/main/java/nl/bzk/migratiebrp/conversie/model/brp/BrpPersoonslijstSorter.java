/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 * Sorteer class voor de {@link BrpPersoonslijst}.
 */
final class BrpPersoonslijstSorter {

    private BrpPersoonslijstSorter() {
    }

    static void sorteer(final BrpPersoonslijst persoonslijst) {
        sorteerStapel(persoonslijst.getAdresStapel());
        sorteerStapel(persoonslijst.getPersoonAfgeleidAdministratiefStapel());
        sorteerStapel(persoonslijst.getAdresStapel());
        sorteerStapel(persoonslijst.getPersoonAfgeleidAdministratiefStapel());
        sorteerStapel(persoonslijst.getBehandeldAlsNederlanderIndicatieStapel());
        sorteerStapel(persoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel());
        sorteerStapel(persoonslijst.getBijhoudingStapel());
        sorteerStapel(persoonslijst.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel());
        sorteerStapel(persoonslijst.getDerdeHeeftGezagIndicatieStapel());
        sorteerStapel(persoonslijst.getDeelnameEuVerkiezingenStapel());
        sorteerStapel(persoonslijst.getGeboorteStapel());
        sorteerStapel(persoonslijst.getMigratieStapel());
        sorteerStapel(persoonslijst.getInschrijvingStapel());
        sorteerStapel(persoonslijst.getNummerverwijzingStapel());
        sorteerStapel(persoonslijst.getOnderCurateleIndicatieStapel());
        sorteerStapel(persoonslijst.getOverlijdenStapel());
        sorteerStapel(persoonslijst.getPersoonskaartStapel());
        sorteerStapel(persoonslijst.getStaatloosIndicatieStapel());
        sorteerStapel(persoonslijst.getUitsluitingKiesrechtStapel());
        sorteerStapel(persoonslijst.getVastgesteldNietNederlanderIndicatieStapel());
        sorteerStapel(persoonslijst.getVerblijfsrechtStapel());
        sorteerStapel(persoonslijst.getVerstrekkingsbeperkingIndicatieStapel());
        sorteerStapel(persoonslijst.getIdentificatienummerStapel());
        sorteerStapel(persoonslijst.getNaamgebruikStapel());
        sorteerStapel(persoonslijst.getGeslachtsaanduidingStapel());
        sorteerStapel(persoonslijst.getSamengesteldeNaamStapel());
        sorteerStapel(persoonslijst.getOnverwerktDocumentAanwezigIndicatieStapel());
        // Sorteren van lijsten van stapels
        sorteerGeslachtsnaamStapels(persoonslijst.getGeslachtsnaamcomponentStapels());
        sorteerVoornaamStapels(persoonslijst.getVoornaamStapels());
        sorteerNationaliteitStapels(persoonslijst.getNationaliteitStapels());
        sorteerReisdocumentStapels(persoonslijst.getReisdocumentStapels());
        sorteerRelaties(persoonslijst.getRelaties());
        sorteerVerificatieStapel(persoonslijst.getVerificatieStapels());
        sorteerBuitenlandsPersoonsnummer(persoonslijst.getBuitenlandsPersoonsnummerStapels());
        // Sorteren van de IST stapels
        sorteerStapel(persoonslijst.getIstOuder1Stapel());
        sorteerStapel(persoonslijst.getIstOuder2Stapel());
        sorteerIstStapels(persoonslijst.getIstHuwelijkOfGpStapels());
        sorteerIstStapels(persoonslijst.getIstKindStapels());
        sorteerStapel(persoonslijst.getIstGezagsverhoudingsStapel());

    }

    private static <T extends BrpGroepInhoud> void sorteerStapel(final BrpStapel<T> stapel) {
        if (stapel != null && !stapel.isEmpty()) {
            stapel.sorteer();
        }
    }

    private static void sorteerBuitenlandsPersoonsnummer(final List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> buitenlandsPersoonsnummerStapels) {
        if (buitenlandsPersoonsnummerStapels != null && !buitenlandsPersoonsnummerStapels.isEmpty()) {
            for (final BrpStapel<BrpBuitenlandsPersoonsnummerInhoud> stapel : buitenlandsPersoonsnummerStapels) {
                stapel.sorteer();
            }

            buitenlandsPersoonsnummerStapels.sort((o1, o2) -> {
                final BrpBuitenlandsPersoonsnummerInhoud o1Inhoud = o1.get(0).getInhoud();
                final BrpBuitenlandsPersoonsnummerInhoud o2Inhoud = o2.get(0).getInhoud();
                final int autVanAfgifteResult = SortUtil.compareNulls(o1Inhoud.getAutoriteitVanAfgifte().getWaarde(),
                        o2Inhoud.getAutoriteitVanAfgifte().getWaarde());
                if (autVanAfgifteResult == 0) {
                    return SortUtil.compareNulls(o1Inhoud.getNummer(), o2Inhoud.getNummer());
                }
                return autVanAfgifteResult;
            });
        }
    }

    private static void sorteerGeslachtsnaamStapels(final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentStapels) {
        if (geslachtsnaamcomponentStapels != null && !geslachtsnaamcomponentStapels.isEmpty()) {
            for (final BrpStapel<BrpGeslachtsnaamcomponentInhoud> stapel : geslachtsnaamcomponentStapels) {
                stapel.sorteer();
            }
            geslachtsnaamcomponentStapels.sort((o1, o2) -> {
                final int volg1 = BrpInteger.unwrap(o1.get(0).getInhoud().getVolgnummer());
                final int volg2 = BrpInteger.unwrap(o2.get(0).getInhoud().getVolgnummer());
                return volg1 > volg2 ? 1 : volg2 > volg1 ? -1 : 0;
            });
        }
    }

    private static void sorteerVoornaamStapels(final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels) {
        if (voornaamStapels != null && !voornaamStapels.isEmpty()) {
            for (final BrpStapel<BrpVoornaamInhoud> stapel : voornaamStapels) {
                stapel.sorteer();
            }
            voornaamStapels.sort((o1, o2) -> {
                final int volg1 = o1 == null ? -1 : BrpInteger.unwrap(o1.get(0).getInhoud().getVolgnummer());
                final int volg2 = o2 == null ? 1 : BrpInteger.unwrap(o2.get(0).getInhoud().getVolgnummer());
                return volg1 - volg2;
            });
        }
    }

    private static void sorteerNationaliteitStapels(final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels) {
        if (nationaliteitStapels != null && !nationaliteitStapels.isEmpty()) {
            for (final BrpStapel<BrpNationaliteitInhoud> stapel : nationaliteitStapels) {
                stapel.sorteer();
            }
            nationaliteitStapels.sort((o1, o2) ->
                    SortUtil.compareNulls(o1.get(0).getInhoud().getNationaliteitCode().getWaarde(), o2.get(0).getInhoud().getNationaliteitCode().getWaarde()));
        }
    }

    private static void sorteerReisdocumentStapels(final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels) {
        if (reisdocumentStapels != null && !reisdocumentStapels.isEmpty()) {
            for (final BrpStapel<BrpReisdocumentInhoud> stapel : reisdocumentStapels) {
                stapel.sorteer();
            }

            reisdocumentStapels.sort(new BrpReisdocumentComparator());
        }
    }

    private static void sorteerRelaties(List<BrpRelatie> relaties) {
        if (relaties != null && !relaties.isEmpty()) {
            for (final BrpRelatie relatie : relaties) {
                relatie.sorteer();
            }
            relaties.sort(new BrpRelatieComparator());
        }
    }

    private static void sorteerVerificatieStapel(final List<BrpStapel<BrpVerificatieInhoud>> verificatieStapels) {
        if (verificatieStapels != null && !verificatieStapels.isEmpty()) {
            for (final BrpStapel<BrpVerificatieInhoud> stapel : verificatieStapels) {
                stapel.sorteer();
            }
            verificatieStapels.sort((o1, o2) -> {
                final String partij1 = o1.get(0).getInhoud().getPartij().getWaarde();
                final String partij2 = o2.get(0).getInhoud().getPartij().getWaarde();
                final BrpString soort1 = o1.get(0).getInhoud().getSoort();
                final BrpString soort2 = o2.get(0).getInhoud().getSoort();
                final int result = partij1.compareTo(partij2);
                if (result == 0) {
                    return SortUtil.compareNulls(soort1, soort2);
                }
                return result;
            });
        }
    }


    private static <T extends BrpGroepInhoud> void sorteerIstStapels(final List<BrpStapel<T>> istStapels) {
        for (final BrpStapel<T> istStapel : istStapels) {
            istStapel.sorteer();
        }

        istStapels.sort((BrpStapel<T> o1, BrpStapel<T> o2) ->
                new BrpIstComparator().compare(o1.get(0), o2.get(0))
        );
    }

    private static class BrpReisdocumentComparator implements Comparator<BrpStapel<BrpReisdocumentInhoud>>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpStapel<BrpReisdocumentInhoud> o1, final BrpStapel<BrpReisdocumentInhoud> o2) {
            final BrpReisdocumentInhoud o1Inhoud = o1.get(0).getInhoud();
            final BrpReisdocumentInhoud o2Inhoud = o2.get(0).getInhoud();
            int result = SortUtil.compareNulls(BrpString.unwrap(o1Inhoud.getNummer()), BrpString.unwrap(o2Inhoud.getNummer()));
            if (result == 0) {
                result = SortUtil.compareNulls(o1Inhoud.getSoort().getWaarde(), o2Inhoud.getSoort().getWaarde());
            }
            if (result == 0) {
                result = SortUtil.vergelijkDatums(o1Inhoud.getDatumIngangDocument(), o2Inhoud.getDatumIngangDocument());
            }
            if (result == 0) {
                result = SortUtil.vergelijkDatums(o1Inhoud.getDatumUitgifte(), o2Inhoud.getDatumUitgifte());
            }
            if (result == 0) {
                result = SortUtil.compareNulls(o1Inhoud.getAutoriteitVanAfgifte().getWaarde(), o2Inhoud.getAutoriteitVanAfgifte().getWaarde());
            }
            if (result == 0) {
                result = SortUtil.vergelijkDatums(o1Inhoud.getDatumEindeDocument(), o2Inhoud.getDatumEindeDocument());
            }
            if (result == 0) {
                 result = SortUtil.vergelijkDatums(o1Inhoud.getDatumInhoudingOfVermissing(), o2Inhoud.getDatumInhoudingOfVermissing());
            }
            if (result == 0) {
                final Character waarde1 =
                        o1Inhoud.getAanduidingInhoudingOfVermissing() == null ? null : o1Inhoud.getAanduidingInhoudingOfVermissing().getWaarde();
                final Character
                        waarde2 =
                        o2Inhoud.getAanduidingInhoudingOfVermissing() == null ? null : o2Inhoud.getAanduidingInhoudingOfVermissing().getWaarde();
                result =
                        SortUtil.compareNulls(waarde1, waarde2);
            }
            if (result == 0) {
                final Lo3Herkomst o1Herkomst = o1.get(0).getActieInhoud().getLo3Herkomst();
                final Lo3Herkomst o2Herkomst = o2.get(0).getActieInhoud().getLo3Herkomst();
                result = SortUtil.compareNulls(o1Herkomst, o2Herkomst);
            }
            return result;
        }
    }

    private static class BrpRelatieComparator implements Comparator<BrpRelatie>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpRelatie o1, final BrpRelatie o2) {
            // Sorteer volgorde F -> H -> P
            int result = o1.getSoortRelatieCode().compareTo(o2.getSoortRelatieCode());

            // Binnen de soort relatie sorteren op rol code (binenn F -> K, O, en H/P -> P)
            if (result == 0) {
                result = o1.getRolCode().compareTo(o2.getRolCode());
            }

            if (result == 0) {
                // Binnen dezelfde rolcode sorteren op actieInhoud-Tsreg van de relatie historie
                final BrpStapel<BrpRelatieInhoud> o1RelatieStapel = o1.getRelatieStapel();
                final BrpStapel<BrpRelatieInhoud> o2RelatieStapel = o2.getRelatieStapel();
                if (o1RelatieStapel == null) {
                    result = o2RelatieStapel == null ? 0 : -1;
                } else {
                    if (o2RelatieStapel == null) {
                        result = 1;
                    } else {
                        result = vergelijkActies(o1RelatieStapel, o2RelatieStapel);
                    }
                }

                if (result == 0) {
                    LoggerFactory.getLogger()
                            .warn("Alle gegevens van de relaties waarop gesorteerd moet worden zijn gelijk. Volgorde is onbepaald");
                }
            }

            return result;
        }

        private int vergelijkActies(final BrpStapel<BrpRelatieInhoud> o1RelatieStapel, final BrpStapel<BrpRelatieInhoud> o2RelatieStapel) {
            int result;
            final BrpActie o1ActieInhoud = o1RelatieStapel.getGroepen().get(0).getActieInhoud();
            final BrpActie o2ActieInhoud = o2RelatieStapel.getGroepen().get(0).getActieInhoud();
            final BrpDatumTijd o1DatumTijdRegistratie = o1ActieInhoud.getDatumTijdRegistratie();
            final BrpDatumTijd o2DatumTijdRegistratie = o2ActieInhoud.getDatumTijdRegistratie();
            result = o1DatumTijdRegistratie.compareTo(o2DatumTijdRegistratie);

            if (result == 0) {
                // Als de tsReg van de actieInhoud gelijk zijn, dan kijken of de lo3herkomst gelijk is
                final Lo3Herkomst o1Herkomst = o1ActieInhoud.getLo3Herkomst();
                final Lo3Herkomst o2Herkomst = o2ActieInhoud.getLo3Herkomst();
                result = SortUtil.compareNulls(o1Herkomst, o2Herkomst);

            }
            return result;
        }
    }
}
