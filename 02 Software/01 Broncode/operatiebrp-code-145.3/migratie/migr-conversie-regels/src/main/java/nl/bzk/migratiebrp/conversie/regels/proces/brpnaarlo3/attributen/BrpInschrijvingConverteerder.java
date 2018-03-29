/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 * Inschrijving converteerder.
 */
@Requirement(Requirements.CCA07)
public final class BrpInschrijvingConverteerder extends AbstractBrpImmaterieleCategorienConverteerder<Lo3InschrijvingInhoud> {
    private BrpAttribuutConverteerder attribuutConverteerder;
    /**
     * Constructor.
     * @param attribuutConverteerder attribuut converteerder
     */
    public BrpInschrijvingConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
        this.attribuutConverteerder = attribuutConverteerder;
    }

    @Override
    protected <T extends BrpGroepInhoud> BrpGroep<T> bepaalGroep(final BrpStapel<T> brpStapel) {
        if (brpStapel != null && brpStapel.get(0) != null) {
            final T inhoudType = brpStapel.get(0).getInhoud();
            if (inhoudType instanceof BrpBijhoudingInhoud) {
                final List<BrpGroep<BrpBijhoudingInhoud>> bijhoudingGroepen = new ArrayList<>();
                for (final BrpGroep<T> groep : brpStapel.getGroepen()) {
                    // cast inhoud naar BrpBijhoudingInhoud en vul een nieuwe stapel met dezelfde groepen
                    final BrpGroep<BrpBijhoudingInhoud> bijhoudingGroep =
                            new BrpGroep<>(
                                    (BrpBijhoudingInhoud) groep.getInhoud(),
                                    groep.getHistorie(),
                                    groep.getActieInhoud(),
                                    groep.getActieVerval(),
                                    groep.getActieGeldigheid());
                    bijhoudingGroepen.add(bijhoudingGroep);
                }
                // bepaal groep, niet perse de actueelste
                return bepaalBijhoudingGroep((BrpStapel<T>) new BrpStapel<>(bijhoudingGroepen));
            }
        }
        // bepaal de actueelste groep
        return bepaalActueleGroep(brpStapel);
    }

    @Override
    protected Lo3Documentatie bepaalDocumentatie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        // Converteer de RNI Deelnemer code uit een eventuele Verificatie groep
        for (final BrpGroep<? extends BrpGroepInhoud> brpGroep : groepen) {
            if (brpGroep.getInhoud() instanceof BrpVerificatieInhoud) {
                return new VerificatieConverteerder(attribuutConverteerder)
                        .converteerNaarDocumentatie((BrpVerificatieInhoud) brpGroep.getInhoud(), brpGroep.getActieInhoud());
            }
        }

        // Geen Verificatie groep aanwezig
        return null;
    }

    @Override
    protected Lo3Herkomst bepaalHerkomst(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        // Dit zou er steeds maar een moeten zijn.
        if (groepen == null || groepen.isEmpty() || groepen.get(0) == null) {
            return null;
        }
        return groepen.get(0).getActieInhoud().getLo3Herkomst();
    }

    @Override
    protected Lo3Historie bepaalHistorie(final List<BrpGroep<? extends BrpGroepInhoud>> groepen) {
        // Inschrijving heeft geen historie
        return new Lo3Historie(null, null, null);
    }

    @Override
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3InschrijvingInhoud> bepaalConverteerder(final B inhoud) {
        final BrpGroepConverteerder<B, Lo3InschrijvingInhoud> result;

        if (inhoud instanceof BrpInschrijvingInhoud) {
            result = (BrpGroepConverteerder<B, Lo3InschrijvingInhoud>) new InschrijvingConverteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpPersoonskaartInhoud) {
            result = (BrpGroepConverteerder<B, Lo3InschrijvingInhoud>) new PersoonskaartConverteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpVerstrekkingsbeperkingIndicatieInhoud) {
            result = (BrpGroepConverteerder<B, Lo3InschrijvingInhoud>) new VerstrekkingbeperkingConverteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpPersoonAfgeleidAdministratiefInhoud) {
            result = (BrpGroepConverteerder<B, Lo3InschrijvingInhoud>) new PersoonAfgeleidAdministratiefConverteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpVerificatieInhoud) {
            result = (BrpGroepConverteerder<B, Lo3InschrijvingInhoud>) new VerificatieConverteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpBijhoudingInhoud) {
            result = (BrpGroepConverteerder<B, Lo3InschrijvingInhoud>) new BijhoudingConverteerder(attribuutConverteerder);
        } else {
            throw new IllegalArgumentException("BrpInschrijvingConverteerder bevat geen Groep converteerder voor: " + inhoud);
        }

        return result;
    }

    /**
     * Doet een nabewerking stap voor het vullen van datum velden in de inhoud vanuit de historie.
     * @param inschrijvingStapel inschrijvingStapel
     * @param brpBijhoudingStapel brpBijhoudingStapel
     * @return inschrijvingStapel waarin de inhoud is aangevuld vanuit de historie.
     */
    public Lo3Stapel<Lo3InschrijvingInhoud> nabewerking(
            final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel,
            final BrpStapel<BrpBijhoudingInhoud> brpBijhoudingStapel) {
        Lo3Stapel<Lo3InschrijvingInhoud> result = null;

        if (inschrijvingStapel != null && brpBijhoudingStapel != null) {
            final BrpGroep<BrpBijhoudingInhoud> actueleGroep = bepaalGroep(brpBijhoudingStapel);
            if (actueleGroep != null) {
                final Lo3Datum datumAanvang = actueleGroep.getHistorie().getDatumAanvangGeldigheid().converteerNaarLo3Datum();

                // er is maar 1 cat7 voorkomen
                final Lo3Categorie<Lo3InschrijvingInhoud> lo3Categorie = inschrijvingStapel.getLaatsteElement();
                final Lo3InschrijvingInhoud lo3Inhoud = lo3Categorie.getInhoud();
                final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(lo3Inhoud);

                // vul datumOpschorting met datumAanvangGeldigheid als deze ook echt een opschortReden heeft.
                if (lo3Inhoud.getRedenOpschortingBijhoudingCode() != null) {
                    builder.setDatumOpschortingBijhouding(datumAanvang);
                }

                result =
                        new Lo3Stapel<>(
                                Collections.singletonList(new Lo3Categorie<>(builder.build(), lo3Categorie.getDocumentatie(), lo3Categorie.getHistorie(),
                                        lo3Categorie.getLo3Herkomst())));
            }
        }

        return result;
    }

    /**
     * Bepaal de te converteren Bijhouding groep. Dit is de groep met de oudste datumAnvangGeldigheid en de recentste
     * NadereBijhoudingsaard.
     * @param bijhoudingStapel De bijhouding stapel
     * @return De te converteren bijhouding groep.
     */
    @Requirement(Requirements.CCA07_BL05)
    private <T extends BrpGroepInhoud> BrpGroep<T> bepaalBijhoudingGroep(final BrpStapel<T> bijhoudingStapel) {
        if (bijhoudingStapel == null || (bijhoudingStapel.isEmpty() || !(bijhoudingStapel.get(0).getInhoud() instanceof BrpBijhoudingInhoud))) {
            return null;
        }

        BrpGroep<T> result = null;
        // sorteren op datumAanvangGeldigheid
        final List<BrpGroep<T>> groepen = bijhoudingStapel.getGroepen();
        groepen.sort(new BrpBijhoudingComparator<>());

        // loop om juiste info te verkrijgen
        BrpNadereBijhoudingsaardCode brpNadereBijhoudingsaardCode = null;
        for (final BrpGroep<T> bijhoudingGroep : groepen) {
            if (!bijhoudingGroep.getHistorie().isVervallen()) {
                final BrpNadereBijhoudingsaardCode
                        brpNadereBijhoudingsaardCodeInGroep =
                        ((BrpBijhoudingInhoud) bijhoudingGroep.getInhoud()).getNadereBijhoudingsaardCode();
                if (brpNadereBijhoudingsaardCode == null) {
                    // eerste groep (actueel)
                    brpNadereBijhoudingsaardCode = brpNadereBijhoudingsaardCodeInGroep;
                } else if (!brpNadereBijhoudingsaardCode.equals(brpNadereBijhoudingsaardCodeInGroep)) {
                    // bijhouding is veranderd. Vorige groep is dus de oudste groep met de actuele
                    // nadereBijhoudingsaard. We gebruiken de vorige groep om te converteren.
                    break;
                }
                result = bijhoudingGroep;
            }
        }

        return result;
    }

    /**
     * Bepaal de te converteren Verificatie stapel. Alleen niet-vervallen verificaties worden geconverteerd. Bovendien
     * kan LO3 maar 1 verificatie opslaan, dus als er meerdere niet-vervallen verificaties zijn dan wordt degene met de
     * meest recente DatumVerificatie geselecteerd.
     * @param verificatieStapels De verificatie stapels
     * @return De geselecteerde verificatie stapel, of null als er geen niet-vervallen verificatie stapels zijn.
     */
    @Requirement(Requirements.CCA07_BL06)
    public BrpStapel<BrpVerificatieInhoud> bepaalVerificatieStapel(final List<BrpStapel<BrpVerificatieInhoud>> verificatieStapels) {
        final List<BrpStapel<BrpVerificatieInhoud>> actueleVerificaties = new ArrayList<>();
        for (final BrpStapel<BrpVerificatieInhoud> verificatieStapel : verificatieStapels) {
            if (verificatieStapel.bevatActueel()) {
                actueleVerificaties.add(verificatieStapel);
            }
        }

        BrpStapel<BrpVerificatieInhoud> resultaat = null;

        for (final BrpStapel<BrpVerificatieInhoud> verificatieStapel : actueleVerificaties) {
            if (resultaat == null || resultaat.getActueel().getInhoud().getDatum().compareTo(verificatieStapel.getActueel().getInhoud().getDatum()) < 0) {
                resultaat = verificatieStapel;
            }
        }

        return resultaat;
    }

    /**
     * Converteerder die weet hoe je een Lo3InschrijvingInhoud rij moet aanmaken.
     */

    private abstract static class AbstractConverteerder<T extends BrpGroepInhoud> extends AbstractBrpGroepConverteerder<T, Lo3InschrijvingInhoud> {

        public AbstractConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        public final Lo3InschrijvingInhoud maakNieuweInhoud() {
            return new Lo3InschrijvingInhoud(
                    null,
                    null,
                    null,
                    null,
                    null,
                    Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement(),
                    null,
                    null,
                    null,
                    null,
                    null);
        }
    }

    /**
     * Converteerder die weet hoe een BrpPersoonskaartInhoud omgezet moet worden naar Lo3InschrijvingInhoud.
     */
    @Requirement(Requirements.CCA07_BL02)
    public static final class PersoonskaartConverteerder extends AbstractConverteerder<BrpPersoonskaartInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public PersoonskaartConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3InschrijvingInhoud vulInhoud(
                final Lo3InschrijvingInhoud lo3Inhoud,
                final BrpPersoonskaartInhoud brpInhoud,
                final BrpPersoonskaartInhoud brpVorigeInhoud) {

            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setGemeentePKCode(null);
                builder.setIndicatiePKVolledigGeconverteerdCode(null);
            } else {
                builder.setGemeentePKCode(getAttribuutConverteerder().converteerGemeenteCode(brpInhoud.getGemeentePKCode()));
                final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerd;
                indicatiePKVolledigGeconverteerd =
                        getAttribuutConverteerder().converteerIndicatiePKVolledigGeconverteerd(brpInhoud.getIndicatiePKVolledigGeconverteerd());
                builder.setIndicatiePKVolledigGeconverteerdCode(indicatiePKVolledigGeconverteerd);
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpVerstrekkingsbeperkingInhoud omgezet moet worden naar Lo3InschrijvingInhoud.
     */
    @Requirement(Requirements.CCA07_BL04)
    public static final class VerstrekkingbeperkingConverteerder extends AbstractConverteerder<BrpVerstrekkingsbeperkingIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public VerstrekkingbeperkingConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3InschrijvingInhoud vulInhoud(
                final Lo3InschrijvingInhoud lo3Inhoud,
                final BrpVerstrekkingsbeperkingIndicatieInhoud brpInhoud,
                final BrpVerstrekkingsbeperkingIndicatieInhoud brpVorigeInhoud) {

            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setIndicatieGeheimCode(null);
            } else {
                builder.setIndicatieGeheimCode(getAttribuutConverteerder().converteerIndicatieGeheim(new BrpBoolean(brpInhoud.heeftIndicatie())));
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpPersoonAfgeleidAdministratiefInhoud omgezet moet worden naar
     * Lo3InschrijvingInhoud.
     */
    @Requirement(Requirements.CCA07_BL03)
    public static final class PersoonAfgeleidAdministratiefConverteerder extends AbstractConverteerder<BrpPersoonAfgeleidAdministratiefInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public PersoonAfgeleidAdministratiefConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3InschrijvingInhoud vulInhoud(
                final Lo3InschrijvingInhoud lo3Inhoud,
                final BrpPersoonAfgeleidAdministratiefInhoud brpInhoud,
                final BrpPersoonAfgeleidAdministratiefInhoud brpVorigeInhoud) {

            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(lo3Inhoud);

            // Conversie van indicatieOnderzoekNaarNietOpgenomenGegevens en
            // indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig
            // nog nader uit te werken in specs.

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpVerificatieInhoud omgezet moet worden naar Lo3InschrijvingInhoud.
     */
    @Requirement(Requirements.CCA07_BL06)
    public static final class VerificatieConverteerder extends AbstractConverteerder<BrpVerificatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public VerificatieConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3InschrijvingInhoud vulInhoud(
                final Lo3InschrijvingInhoud lo3Inhoud,
                final BrpVerificatieInhoud brpInhoud,
                final BrpVerificatieInhoud brpVorigeInhoud) {

            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setDatumVerificatie(null);
                builder.setOmschrijvingVerificatie(null);
            } else {
                builder.setDatumVerificatie(getAttribuutConverteerder().converteerDatum(brpInhoud.getDatum()));
                builder.setOmschrijvingVerificatie(getAttribuutConverteerder().converteerString(brpInhoud.getSoort()));
            }

            return builder.build();
        }

        /**
         * Converteert verificatieInhoud naar LO3 documentatie.
         * @param inhoud inhoud.
         * @param actieInhoud actieInhoud.
         * @return De geconverteerde LO3 documentatie.
         */
        Lo3Documentatie converteerNaarDocumentatie(final BrpVerificatieInhoud inhoud, final BrpActie actieInhoud) {
            return new Lo3Documentatie(
                    actieInhoud.getId(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    getAttribuutConverteerder().converteerRNIDeelnemer(inhoud.getPartij()),
                    null);
        }
    }

    /**
     * Converteerder die weet hoe een BrpInschrijvingInhoud omgezet moet worden naar Lo3InschrijvingInhoud.
     */
    @Requirement(Requirements.CCA07_BL01)
    public static final class InschrijvingConverteerder extends AbstractConverteerder<BrpInschrijvingInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public InschrijvingConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3InschrijvingInhoud vulInhoud(
                final Lo3InschrijvingInhoud lo3Inhoud,
                final BrpInschrijvingInhoud brpInhoud,
                final BrpInschrijvingInhoud brpVorigeInhoud) {

            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setDatumEersteInschrijving(null);
                builder.setVersienummer(new Lo3Integer("0000", null));
                builder.setDatumtijdstempel(null);
            } else {
                builder.setDatumEersteInschrijving(getAttribuutConverteerder().converteerDatum(brpInhoud.getDatumInschrijving()));
                builder.setVersienummer(getAttribuutConverteerder().converteerVersienummer(brpInhoud.getVersienummer()));
                builder.setDatumtijdstempel(getAttribuutConverteerder().converteerDatumtijdstempel(brpInhoud.getDatumtijdstempel()));
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpBijhoudingInhoud omgezet moet worden naar Lo3InschrijvingInhoud.
     */
    @Requirement(Requirements.CCA07_BL05)
    public static final class BijhoudingConverteerder extends AbstractConverteerder<BrpBijhoudingInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public BijhoudingConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        @Definitie({Definities.DEF058, Definities.DEF079, Definities.DEF080})
        public Lo3InschrijvingInhoud vulInhoud(
                final Lo3InschrijvingInhoud lo3Inhoud,
                final BrpBijhoudingInhoud brpInhoud,
                final BrpBijhoudingInhoud brpVorigeInhoud) {

            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setDatumOpschortingBijhouding(null);
                builder.setRedenOpschortingBijhoudingCode(null);
            } else {
                final BrpNadereBijhoudingsaardCode nadereBijhoudingsaardCode = brpInhoud.getNadereBijhoudingsaardCode();

                // datumOpschorting wordt in de nabewerking stap gevuld met de datumIngangGeldigheid uit de
                // BrpHistorie, zie #nabewerking()
                builder.setDatumOpschortingBijhouding(null);
                final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode;

                if (AbstractBrpAttribuutMetOnderzoek.equalsWaarde(BrpNadereBijhoudingsaardCode.ACTUEEL, nadereBijhoudingsaardCode)) {
                    // DEF079
                    redenOpschortingBijhoudingCode = null;
                } else if (AbstractBrpAttribuutMetOnderzoek.equalsWaarde(
                        BrpNadereBijhoudingsaardCode.VERTROKKEN_ONBEKEND_WAARHEEN,
                        nadereBijhoudingsaardCode)) {
                    // DEF080
                    redenOpschortingBijhoudingCode = Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE.asElement();
                } else {
                    redenOpschortingBijhoudingCode = getAttribuutConverteerder().converteerRedenOpschortingBijhouding(brpInhoud.getNadereBijhoudingsaardCode());
                }

                builder.setRedenOpschortingBijhoudingCode(redenOpschortingBijhoudingCode);
            }
            return builder.build();
        }
    }

    /**
     * Comparator welke de BrpBijhoudingInhoud vergelijkt op basis van de datumAanvangGeldigheid.
     * @param <T> {@link BrpGroepInhoud}
     */
    public static class BrpBijhoudingComparator<T extends BrpGroepInhoud> implements Comparator<BrpGroep<T>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpGroep<T> o1, final BrpGroep<T> o2) {
            final BrpDatum o1DatumAanvang = o1.getHistorie().getDatumAanvangGeldigheid();
            final BrpDatum o2DatumAanvang = o2.getHistorie().getDatumAanvangGeldigheid();
            return o2DatumAanvang.compareTo(o1DatumAanvang);
        }
    }
}
