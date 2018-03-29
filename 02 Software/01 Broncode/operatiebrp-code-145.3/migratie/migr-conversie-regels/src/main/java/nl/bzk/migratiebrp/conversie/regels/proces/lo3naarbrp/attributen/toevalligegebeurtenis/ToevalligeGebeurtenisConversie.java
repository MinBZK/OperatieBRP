/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.toevalligegebeurtenis;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisNaamGeslacht;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisOverlijden;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortAkte;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import org.springframework.stereotype.Component;

/**
 * Toevallige gebeurtenis conversie.
 */
@Component
public final class ToevalligeGebeurtenisConversie {

    private final Lo3AttribuutConverteerder lo3AttribuutConverteerder;
    private final ToevalligeGebeurtenisPersoonConverteerder persoonConverteerder;
    private final ToevalligeGebeurtenisNaamGeslachtConverteerder naamGeslachtConverteerder;
    private final ToevalligeGebeurtenisFamilierechtelijkeBetrekkingConverteerder familierechtelijkeBetrekkingConverteerder;
    private final ToevalligeGebeurtenisOverlijdenConverteerder overlijdenConverteerder;
    private final ToevalligeGebeurtenisVerbintenisConverteerder verbintenisConverteerder;

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder lo3 attribuut converteerder
     * @param persoonConverteerder persoon converteerder
     * @param naamGeslachtConverteerder naam geslacht converteerder
     * @param familierechtelijkeBetrekkingConverteerder familierechtelijke betrekking converteerder
     * @param overlijdenConverteerder overlijden converteerder
     * @param verbintenisConverteerder verbintenis converteerder
     */
    @Inject
    public ToevalligeGebeurtenisConversie(final Lo3AttribuutConverteerder lo3AttribuutConverteerder,
                                          final ToevalligeGebeurtenisPersoonConverteerder persoonConverteerder,
                                          final ToevalligeGebeurtenisNaamGeslachtConverteerder naamGeslachtConverteerder,
                                          final ToevalligeGebeurtenisFamilierechtelijkeBetrekkingConverteerder familierechtelijkeBetrekkingConverteerder,
                                          final ToevalligeGebeurtenisOverlijdenConverteerder overlijdenConverteerder,
                                          final ToevalligeGebeurtenisVerbintenisConverteerder verbintenisConverteerder) {
        this.lo3AttribuutConverteerder = lo3AttribuutConverteerder;
        this.persoonConverteerder = persoonConverteerder;
        this.naamGeslachtConverteerder = naamGeslachtConverteerder;
        this.familierechtelijkeBetrekkingConverteerder = familierechtelijkeBetrekkingConverteerder;
        this.overlijdenConverteerder = overlijdenConverteerder;
        this.verbintenisConverteerder = verbintenisConverteerder;
    }

    /**
     * Converteer een Lo3ToevalligeGebeurtenis naar een BrpToevalligeGebeurtenis.
     * @param lo3ToevalligeGebeurtenis lo3 toevallige gebeurtenis
     * @return brp toevallige gebeurtenis
     */
    public BrpToevalligeGebeurtenis converteer(final Lo3ToevalligeGebeurtenis lo3ToevalligeGebeurtenis) {
        final BrpDatum datumAanvang;
        final BrpPartijCode doelPartijCode =
                lo3AttribuutConverteerder.converteerLo3GemeenteCodeNaarBrpPartijCode(lo3ToevalligeGebeurtenis.getOntvangendeGemeente());
        final BrpToevalligeGebeurtenisPersoon persoon = persoonConverteerder.converteer(lo3ToevalligeGebeurtenis.getPersoon());

        BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking familierechtelijkeBetrekking = null;
        BrpToevalligeGebeurtenisNaamGeslacht voornaamWijziging = null;
        BrpToevalligeGebeurtenisNaamGeslacht geslachtsnaamWijziging = null;
        BrpToevalligeGebeurtenisNaamGeslacht geslachtsaanduidingWijziging = null;
        BrpToevalligeGebeurtenisOverlijden overlijden = null;
        BrpToevalligeGebeurtenisVerbintenis verbintenis = null;
        final Lo3SoortAkte soortAkte = Lo3SoortAkte.bepaalSoortAkteObvAktenummer(lo3ToevalligeGebeurtenis.getNummerAkte().getWaarde());
        switch (soortAkte) {
            case AKTE_1C:
            case AKTE_1J:
                familierechtelijkeBetrekking = familierechtelijkeBetrekkingConverteerder.converteer(
                        soortAkte, lo3ToevalligeGebeurtenis.getPersoon(),
                        lo3ToevalligeGebeurtenis.getOuder1(), lo3ToevalligeGebeurtenis.getOuder2());
                datumAanvang = bepaalDatamAanvangBijErkenning(familierechtelijkeBetrekking);
                break;
            case AKTE_1E:
            case AKTE_1U:
                familierechtelijkeBetrekking = familierechtelijkeBetrekkingConverteerder.converteer(
                        soortAkte, lo3ToevalligeGebeurtenis.getPersoon(),
                        lo3ToevalligeGebeurtenis.getOuder1(), lo3ToevalligeGebeurtenis.getOuder2());
                datumAanvang = bepaalDatumAanvangBijOntkenning(familierechtelijkeBetrekking);
                break;
            case AKTE_1N:
                familierechtelijkeBetrekking = familierechtelijkeBetrekkingConverteerder.converteer(
                        soortAkte, lo3ToevalligeGebeurtenis.getPersoon(),
                        lo3ToevalligeGebeurtenis.getOuder1(), lo3ToevalligeGebeurtenis.getOuder2());
                datumAanvang = bepaalDatumAanvangBijVernietiging(familierechtelijkeBetrekking);
                break;
            case AKTE_1Q:
            case AKTE_1V:
                familierechtelijkeBetrekking = familierechtelijkeBetrekkingConverteerder.converteer(
                        soortAkte, lo3ToevalligeGebeurtenis.getPersoon(),
                        lo3ToevalligeGebeurtenis.getOuder1(), lo3ToevalligeGebeurtenis.getOuder2());
                datumAanvang = bepaalDatumAanvangBijAdoptie(familierechtelijkeBetrekking);
                break;
            case AKTE_1H:
                geslachtsnaamWijziging = naamGeslachtConverteerder.converteer(lo3ToevalligeGebeurtenis.getPersoon());
                datumAanvang = bepaalDatumAanvangBijNaamGeslacht(geslachtsnaamWijziging, lo3ToevalligeGebeurtenis);
                break;
            case AKTE_1M:
                voornaamWijziging = naamGeslachtConverteerder.converteer(lo3ToevalligeGebeurtenis.getPersoon());
                datumAanvang = bepaalDatumAanvangBijNaamGeslacht(voornaamWijziging, lo3ToevalligeGebeurtenis);
                break;
            case AKTE_1S:
                geslachtsaanduidingWijziging = naamGeslachtConverteerder.converteer(lo3ToevalligeGebeurtenis.getPersoon());
                datumAanvang = bepaalDatumAanvangBijNaamGeslacht(geslachtsaanduidingWijziging, lo3ToevalligeGebeurtenis);
                break;
            case AKTE_2A:
            case AKTE_2G:
                overlijden = overlijdenConverteerder.converteer(lo3ToevalligeGebeurtenis.getOverlijden());
                datumAanvang = bepaalDatumAanvangOverlijden(overlijden, lo3ToevalligeGebeurtenis);
                break;
            case AKTE_3A:
            case AKTE_3B:
            case AKTE_3H:
            case AKTE_5A:
            case AKTE_5B:
            case AKTE_5H:
                verbintenis = verbintenisConverteerder.converteer(lo3ToevalligeGebeurtenis.getVerbintenis());
                datumAanvang = bepaalDatumAanvangVerbintenis(verbintenis, lo3ToevalligeGebeurtenis);
                break;
            default:
                // Kan niet voorkomen
                throw new IllegalStateException("Onbekende SoortAkte");
        }
        final BrpPartijCode registerGemeente =
                lo3AttribuutConverteerder.converteerLo3GemeenteCodeNaarBrpPartijCode(lo3ToevalligeGebeurtenis.getVerzendendeGemeente());
        final BrpString nummerAkte = lo3AttribuutConverteerder.converteerString(lo3ToevalligeGebeurtenis.getNummerAkte());

        return new BrpToevalligeGebeurtenis(
                doelPartijCode,
                persoon,
                voornaamWijziging,
                geslachtsnaamWijziging,
                geslachtsaanduidingWijziging,
                familierechtelijkeBetrekking,
                overlijden,
                verbintenis,
                registerGemeente,
                nummerAkte,
                datumAanvang);
    }

    private BrpDatum bepaalDatumAanvangVerbintenis(
            final BrpToevalligeGebeurtenisVerbintenis verbintenis,
            final Lo3ToevalligeGebeurtenis lo3ToevalligeGebeurtenis) {
        BrpDatum datumAanvang = null;
        if (verbintenis != null) {
            datumAanvang =
                    lo3AttribuutConverteerder.converteerDatum(
                            lo3ToevalligeGebeurtenis.getVerbintenis().getCategorieen().get(0).getHistorie().getIngangsdatumGeldigheid());
        }
        return datumAanvang;
    }

    private BrpDatum bepaalDatumAanvangOverlijden(
            final BrpToevalligeGebeurtenisOverlijden overlijden,
            final Lo3ToevalligeGebeurtenis lo3ToevalligeGebeurtenis) {
        BrpDatum datumAanvang = null;
        if (overlijden != null) {
            datumAanvang = lo3AttribuutConverteerder.converteerDatum(lo3ToevalligeGebeurtenis.getOverlijden().getHistorie().getIngangsdatumGeldigheid());
        }
        return datumAanvang;
    }

    private BrpDatum bepaalDatumAanvangBijNaamGeslacht(
            final BrpToevalligeGebeurtenisNaamGeslacht naamGeslacht,
            final Lo3ToevalligeGebeurtenis lo3ToevalligeGebeurtenis) {
        BrpDatum datumAanvang = null;
        if (naamGeslacht != null) {
            datumAanvang =
                    lo3AttribuutConverteerder.converteerDatum(
                            lo3ToevalligeGebeurtenis.getPersoon().getCategorieen().get(0).getHistorie().getIngangsdatumGeldigheid());
        }
        return datumAanvang;
    }

    private BrpDatum bepaalDatumAanvangBijAdoptie(final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking familierechtelijkeBetrekking) {
        BrpDatum datumAanvang = null;
        if (familierechtelijkeBetrekking != null && familierechtelijkeBetrekking.getAdoptie() != null) {
            if (familierechtelijkeBetrekking.getAdoptie().getOuder1() != null) {
                datumAanvang = familierechtelijkeBetrekking.getAdoptie().getOuder1().getDatumIngangFamilierechtelijkeBetrekking();
            } else if (familierechtelijkeBetrekking.getAdoptie().getOuder2() != null) {
                datumAanvang = familierechtelijkeBetrekking.getAdoptie().getOuder2().getDatumIngangFamilierechtelijkeBetrekking();
            }
        }
        return datumAanvang;
    }

    private BrpDatum bepaalDatumAanvangBijVernietiging(final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking familierechtelijkeBetrekking) {
        BrpDatum datumAanvang = null;
        if (familierechtelijkeBetrekking != null) {
            datumAanvang = familierechtelijkeBetrekking.getVernietiging().getDatumIngangFamilierechtelijkeBetrekking();
        }
        return datumAanvang;
    }

    private BrpDatum bepaalDatumAanvangBijOntkenning(final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking familierechtelijkeBetrekking) {
        BrpDatum datumAanvang = null;
        if (familierechtelijkeBetrekking != null) {
            datumAanvang = familierechtelijkeBetrekking.getOntkenning().getDatumIngangFamilierechtelijkeBetrekking();
        }
        return datumAanvang;
    }

    private BrpDatum bepaalDatamAanvangBijErkenning(final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking familierechtelijkeBetrekking) {
        BrpDatum datumAanvang = null;
        if (familierechtelijkeBetrekking != null) {
            datumAanvang = familierechtelijkeBetrekking.getErkenning().getDatumIngangFamilierechtelijkeBetrekking();
        }
        return datumAanvang;
    }

}
