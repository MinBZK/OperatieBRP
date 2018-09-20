/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsgemeenteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpImmigratieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Verblijfplaats converteerder.
 */
@Component
@Requirement(Requirements.CCA08)
public final class BrpVerblijfplaatsConverteerder extends BrpCategorieConverteerder<Lo3VerblijfplaatsInhoud> {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private AdresConverteerder adresConverteerder;
    @Inject
    private BijhoudingsgemeenteConverteerder bijhoudingsgemeenteConverteerder;
    @Inject
    private ImmigratieConverteerder immigratieConverteerder;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3VerblijfplaatsInhoud> bepaalConverteerder(
            final B inhoud) {
        final BrpGroepConverteerder<B, Lo3VerblijfplaatsInhoud> result;

        if (inhoud instanceof BrpAdresInhoud) {
            result = (BrpGroepConverteerder<B, Lo3VerblijfplaatsInhoud>) adresConverteerder;
        } else if (inhoud instanceof BrpBijhoudingsgemeenteInhoud) {
            result = (BrpGroepConverteerder<B, Lo3VerblijfplaatsInhoud>) bijhoudingsgemeenteConverteerder;
        } else if (inhoud instanceof BrpImmigratieInhoud) {
            result = (BrpGroepConverteerder<B, Lo3VerblijfplaatsInhoud>) immigratieConverteerder;
        } else {
            throw new IllegalArgumentException("BrpVerblijfplaatsConverteerder bevat geen Groep converteerder voor: "
                    + inhoud);
        }

        return result;
    }

    /**
     * Converteerder die weet hoe je een Lo3VerblijfplaatsInhoud rij moet aanmaken.
     */

    private abstract static class AbstractConverteerder<T extends BrpGroepInhoud> extends
            BrpGroepConverteerder<T, Lo3VerblijfplaatsInhoud> {

        @Override
        protected Lo3VerblijfplaatsInhoud maakNieuweInhoud() {
            return new Lo3VerblijfplaatsInhoud(null, null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null, null, null,
                    Lo3AangifteAdreshoudingEnum.ONBEKEND.asElement(), null);
        }
    }

    /**
     * Converteerder die weet hoe een BrpAdresInhoud omgezet moet worden naar Lo3VerblijfplaatsInhoud.
     */
    @Component
    @Requirement({ Requirements.CCA08_BL01, Requirements.CCA08_BL02, Requirements.CCA08_BL03, Requirements.CCA08_BL05 })
    private static final class AdresConverteerder extends AbstractConverteerder<BrpAdresInhoud> {

        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        // CHECKSTYLE:OFF - Executable statement count - gewoon veel attributen, niet complex
        @Override
        @Definitie({ Definities.DEF023, Definities.DEF024, Definities.DEF025, Definities.DEF026, Definities.DEF045,
                Definities.DEF046 })
        protected Lo3VerblijfplaatsInhoud vulInhoud(
        // CHECKSTYLE:ON
                final Lo3VerblijfplaatsInhoud lo3Inhoud,
                final BrpAdresInhoud brpInhoud,
                final BrpAdresInhoud brpVorigeInhoud) {

            final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setFunctieAdres(null);
                builder.setAangifteAdreshouding(null);
                builder.setAanvangAdreshouding(null);
                builder.setIdentificatiecodeVerblijfplaats(null);
                builder.setIdentificatiecodeNummeraanduiding(null);
                builder.setNaamOpenbareRuimte(null);
                builder.setStraatnaam(null);
                builder.setGemeenteDeel(null);
                builder.setHuisnummer(null);
                builder.setHuisletter(null);
                builder.setHuisnummertoevoeging(null);
                builder.setPostcode(null);
                builder.setWoonplaatsnaam(null);
                builder.setAanduidingHuisnummer(null);
                builder.setLocatieBeschrijving(null);
                builder.setAdresBuitenland1(null);
                builder.setAdresBuitenland2(null);
                builder.setAdresBuitenland3(null);
                builder.setLandWaarnaarVertrokken(null);
                builder.setVertrekUitNederland(null);
            } else {
                // Zowel DEF023, DEF024, DEF025, DEF026
                builder.setFunctieAdres(converteerder.converteerFunctieAdres(brpInhoud.getFunctieAdresCode()));
                builder.setAangifteAdreshouding(converteerder.converteerAangifteAdreshouding(
                        brpInhoud.getRedenWijzigingAdresCode(), brpInhoud.getAangeverAdreshoudingCode()));
                builder.setAanvangAdreshouding(converteerder.converteerDatum(brpInhoud.getDatumAanvangAdreshouding()));
                builder.setIdentificatiecodeVerblijfplaats(brpInhoud.getAdresseerbaarObject());
                builder.setIdentificatiecodeNummeraanduiding(brpInhoud.getIdentificatiecodeNummeraanduiding());
                builder.setNaamOpenbareRuimte(brpInhoud.getNaamOpenbareRuimte());
                builder.setStraatnaam(brpInhoud.getAfgekorteNaamOpenbareRuimte());
                builder.setGemeenteDeel(brpInhoud.getGemeentedeel());
                builder.setHuisnummer(converteerder.converteerHuisnummer(brpInhoud.getHuisnummer()));
                builder.setHuisletter(brpInhoud.getHuisletter());
                builder.setHuisnummertoevoeging(brpInhoud.getHuisnummertoevoeging());
                builder.setPostcode(brpInhoud.getPostcode());
                builder.setWoonplaatsnaam(converteerder.converteerWoonplaatsnaam(brpInhoud.getPlaatsCode()));
                builder.setAanduidingHuisnummer(converteerder.converteerAanduidingHuisnummer(brpInhoud
                        .getLocatieTovAdres()));
                builder.setLocatieBeschrijving(brpInhoud.getLocatieOmschrijving());

                if (!ValidationUtils.isEmpty(brpInhoud.getBuitenlandsAdresRegel1())
                        && ValidationUtils.isEmpty(brpInhoud.getBuitenlandsAdresRegel2())
                        && ValidationUtils.isEmpty(brpInhoud.getBuitenlandsAdresRegel3())) {
                    // DEF045
                    builder.setAdresBuitenland1(null);
                    builder.setAdresBuitenland2(brpInhoud.getBuitenlandsAdresRegel1());
                    builder.setAdresBuitenland3(null);

                } else {
                    // DEF046
                    builder.setAdresBuitenland1(brpInhoud.getBuitenlandsAdresRegel1());
                    builder.setAdresBuitenland2(brpInhoud.getBuitenlandsAdresRegel2());
                    builder.setAdresBuitenland3(brpInhoud.getBuitenlandsAdresRegel3());

                }

                final Lo3LandCode land = converteerder.converteerLandCode(brpInhoud.getLandCode());
                builder.setLandWaarnaarVertrokken(Lo3LandCode.NEDERLAND.equals(land) ? null : land);
                builder.setVertrekUitNederland(converteerder.converteerDatum(brpInhoud.getDatumVertrekUitNederland()));

                if ((Lo3LandCode.NEDERLAND.equals(land) || land == null)
                        && (brpInhoud.getLocatieOmschrijving() == null)) {
                    // Indien de groep (11) voorkomt, komt 11.10 (straat) verplicht voor
                    // In geval van DEF023, vul straatnaam met punt
                    if (brpInhoud.getAfgekorteNaamOpenbareRuimte() == null
                            || "".equals(brpInhoud.getAfgekorteNaamOpenbareRuimte())) {
                        builder.setStraatnaam(".");
                    }
                }
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpBijhoudingsgemeenteInhoud omgezet moet worden naar Lo3VerblijfplaatsInhoud.
     */
    @Component
    @Requirement(Requirements.CCA08_BL05)
    private static final class BijhoudingsgemeenteConverteerder extends
            AbstractConverteerder<BrpBijhoudingsgemeenteInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3VerblijfplaatsInhoud vulInhoud(
                final Lo3VerblijfplaatsInhoud lo3Inhoud,
                final BrpBijhoudingsgemeenteInhoud brpInhoud,
                final BrpBijhoudingsgemeenteInhoud brpVorigeInhoud) {

            final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setGemeenteInschrijving(null);
                builder.setDatumInschrijving(null);
                builder.setIndicatieDocument(null);
            } else {
                builder.setGemeenteInschrijving(converteerder.converteerGemeenteCode(brpInhoud
                        .getBijhoudingsgemeenteCode()));
                builder.setDatumInschrijving(converteerder.converteerDatum(brpInhoud.getDatumInschrijvingInGemeente()));
                builder.setIndicatieDocument(converteerder.converteerIndicatieDocument(brpInhoud
                        .getOnverwerktDocumentAanwezig()));
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpImmigratieInhoud omgezet moet worden naar Lo3VerblijfplaatsInhoud.
     */
    @Component
    @Requirement(Requirements.CCA08_BL04)
    private static final class ImmigratieConverteerder extends AbstractConverteerder<BrpImmigratieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();

        @Inject
        private BrpAttribuutConverteerder converteerder;

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        protected Lo3VerblijfplaatsInhoud vulInhoud(
                final Lo3VerblijfplaatsInhoud lo3Inhoud,
                final BrpImmigratieInhoud brpInhoud,
                final BrpImmigratieInhoud brpVorigeInhoud) {
            final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setLandVanwaarIngeschreven(null);
                builder.setVestigingInNederland(null);
            } else {
                builder.setLandVanwaarIngeschreven(converteerder.converteerLandCode(brpInhoud
                        .getLandVanwaarIngeschreven()));
                builder.setVestigingInNederland(converteerder.converteerDatum(brpInhoud
                        .getDatumVestigingInNederland()));

            }

            return builder.build();
        }
    }

}
