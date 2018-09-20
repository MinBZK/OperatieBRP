/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.util.BrpStapelSorter;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStap;

/**
 * Sorteren van de BrpPersoonslijst op basis van geldigheid. Anders komt het slecht overeen met de LO3 weergave.
 *
 * N.B.: helaas is dit een copy paste van de BrpStapelSorter in src/test/java van migr-conversie-model .
 */
public final class GgoBrpStapelSorter {

    /**
     * Constructor
     */
    private GgoBrpStapelSorter() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Sorteer de brpPersoonsLijst. Inhoud groepen worden gesorteerd op geldigheid en daarna opname.
     *
     * @param persoonslijst
     *            De brp persoonslijst.
     * @param foutMelder
     *            fout melder
     * @return BrpPersoonslijst De gesorteerde brp persoonslijst.
     */
    public static BrpPersoonslijst sorteerPersoonslijst(final BrpPersoonslijst persoonslijst, final FoutMelder foutMelder) {
        if (persoonslijst == null) {
            foutMelder.setHuidigeStap(GgoStap.BRP);
            foutMelder.log(LogSeverity.ERROR, "Fout bij sorteren BRP Persoonslijst", "BRP Persoonslijst mag niet leeg zijn.");
            return null;
        }

        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        builder.naamgebruikStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getNaamgebruikStapel()));
        builder.adresStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getAdresStapel()));
        builder.persoonAfgeleidAdministratiefStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getPersoonAfgeleidAdministratiefStapel()));
        builder.behandeldAlsNederlanderIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getBehandeldAlsNederlanderIndicatieStapel()));
        final BrpStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> signaleringMetBetrekkingTotVerstrekkenReisdocumentInhoudBrpStapel =
                BrpStapelSorter.sorteerStapel(persoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel());
        builder.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(signaleringMetBetrekkingTotVerstrekkenReisdocumentInhoudBrpStapel);
        builder.bijhoudingStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getBijhoudingStapel()));
        builder.bijzondereVerblijfsrechtelijkePositieIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel()));
        builder.derdeHeeftGezagIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getDerdeHeeftGezagIndicatieStapel()));
        builder.deelnameEuVerkiezingenStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getDeelnameEuVerkiezingenStapel()));
        builder.geboorteStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getGeboorteStapel()));
        builder.geslachtsaanduidingStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getGeslachtsaanduidingStapel()));
        builder.geslachtsnaamcomponentStapels(BrpStapelSorter.sorteerGeslachtsnaamStapels(persoonslijst.getGeslachtsnaamcomponentStapels()));
        builder.identificatienummersStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getIdentificatienummerStapel()));
        builder.migratieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getMigratieStapel()));
        builder.inschrijvingStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getInschrijvingStapel()));
        builder.nationaliteitStapels(BrpStapelSorter.sorteerNationaliteitStapels(persoonslijst.getNationaliteitStapels()));
        builder.nummerverwijzingStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getNummerverwijzingStapel()));
        builder.onderCurateleIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getOnderCurateleIndicatieStapel()));
        builder.overlijdenStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getOverlijdenStapel()));
        builder.persoonskaartStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getPersoonskaartStapel()));
        builder.reisdocumentStapels(BrpStapelSorter.sorteerReisdocumentStapels(persoonslijst.getReisdocumentStapels()));
        builder.relaties(BrpStapelSorter.sorteerRelaties(persoonslijst.getRelaties()));
        builder.samengesteldeNaamStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getSamengesteldeNaamStapel()));
        builder.staatloosIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getStaatloosIndicatieStapel()));
        builder.uitsluitingKiesrechtStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getUitsluitingKiesrechtStapel()));
        builder.vastgesteldNietNederlanderIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getVastgesteldNietNederlanderIndicatieStapel()));
        builder.verblijfsrechtStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getVerblijfsrechtStapel()));
        builder.verificatieStapels(BrpStapelSorter.sorteerVerificatieStapels(persoonslijst.getVerificatieStapels()));
        builder.verstrekkingsbeperkingIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getVerstrekkingsbeperkingIndicatieStapel()));
        builder.voornaamStapels(BrpStapelSorter.sorteerVoornaamStapels(persoonslijst.getVoornaamStapels()));
        builder.istGezagsverhoudingStapel(BrpStapelSorter.sorteerIstStapel(persoonslijst.getIstGezagsverhoudingsStapel()));
        builder.istHuwelijkOfGpStapels(BrpStapelSorter.sorteerIstStapels(persoonslijst.getIstHuwelijkOfGpStapels()));
        builder.istKindStapels(BrpStapelSorter.sorteerIstStapels(persoonslijst.getIstKindStapels()));
        builder.istOuder1Stapel(BrpStapelSorter.sorteerIstStapel(persoonslijst.getIstOuder1Stapel()));
        builder.istOuder2Stapel(BrpStapelSorter.sorteerIstStapel(persoonslijst.getIstOuder2Stapel()));

        return builder.build();
    }

}
