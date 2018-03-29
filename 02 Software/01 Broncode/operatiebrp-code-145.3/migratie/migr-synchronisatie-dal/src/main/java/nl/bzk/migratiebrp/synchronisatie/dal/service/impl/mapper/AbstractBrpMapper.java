/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ActieBron;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapperUtil;

/**
 * Basis mapper.
 * @param <T> BRP database type
 * @param <G> BRP conversiemodel type
 */
public abstract class AbstractBrpMapper<T extends FormeleHistorieZonderVerantwoording, G extends BrpGroepInhoud> {

    private static BrpHistorie mapHistorie(final FormeleHistorieZonderVerantwoording historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpDatum aanvang;
        final BrpDatum einde;
        if (historie instanceof MaterieleHistorie) {
            final MaterieleHistorie matHist = (MaterieleHistorie) historie;
            aanvang =
                    BrpMapperUtil.mapDatum(
                            matHist.getDatumAanvangGeldigheid(),
                            bepaalOnderzoek(brpOnderzoekMapper, matHist, OnderzoekMapperUtil.Historieveldnaam.AANVANG, true));
            einde =
                    BrpMapperUtil.mapDatum(
                            matHist.getDatumEindeGeldigheid(),
                            bepaalOnderzoek(brpOnderzoekMapper, matHist, OnderzoekMapperUtil.Historieveldnaam.EINDE, false));
        } else {
            aanvang = null;
            einde = null;
        }
        final BrpCharacter nadereAanduidingVerval;
        if (historie instanceof FormeleHistorie) {
            nadereAanduidingVerval =
                    BrpMapperUtil.mapBrpCharacter(
                            ((FormeleHistorie) historie).getNadereAanduidingVerval(),
                            bepaalOnderzoek(brpOnderzoekMapper, historie, OnderzoekMapperUtil.Historieveldnaam.N_A_VERVAL, false));
        } else {
            nadereAanduidingVerval = null;
        }

        final BrpDatumTijd registratie =
                BrpMapperUtil.mapBrpDatumTijd(
                        historie.getDatumTijdRegistratie(),
                        bepaalOnderzoek(brpOnderzoekMapper, historie, OnderzoekMapperUtil.Historieveldnaam.REGISTRATIE, false));
        final BrpDatumTijd verval =
                BrpMapperUtil.mapBrpDatumTijd(
                        historie.getDatumTijdVerval(),
                        bepaalOnderzoek(brpOnderzoekMapper, historie, OnderzoekMapperUtil.Historieveldnaam.VERVAL, false));
        return new BrpHistorie(aanvang, einde, registratie, verval, nadereAanduidingVerval);

    }

    private static Lo3Onderzoek bepaalOnderzoek(
            final BrpOnderzoekMapper brpOnderzoekMapper,
            final Entiteit historieEntiteit,
            final OnderzoekMapperUtil.Historieveldnaam historieVeldnaam,
            final boolean elementBehoortBijGroepsOnderzoek) {
        return brpOnderzoekMapper == null ? null
                : bepaalOnderzoek(
                        brpOnderzoekMapper,
                        historieEntiteit,
                        OnderzoekMapperUtil.bepaalElement(historieEntiteit, historieVeldnaam),
                        elementBehoortBijGroepsOnderzoek);
    }

    /**
     * Map een BRP database actie naar een BRP conversiemodel actie.
     * @param actie BRP database actie
     * @param brpOnderzoekMapper De mapper voor onderzoeken
     * @return BRP conversiemodel actie
     */
    public static BrpActie mapActie(final BRPActie actie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        if (actie == null) {
            return null;
        }

        final BrpSoortActieCode soortActieCode =
                BrpMapperUtil.mapBrpSoortActieCode(actie.getSoortActie(), bepaalOnderzoek(brpOnderzoekMapper, actie, Element.ACTIE_SOORTNAAM, true));
        final BrpPartijCode partijCode =
                BrpMapperUtil.mapBrpPartijCode(actie.getPartij(), bepaalOnderzoek(brpOnderzoekMapper, actie, Element.ACTIE_PARTIJCODE, true));
        final BrpDatumTijd datumTijdRegistratie =
                BrpMapperUtil.mapBrpDatumTijd(
                        actie.getDatumTijdRegistratie(),
                        bepaalOnderzoek(brpOnderzoekMapper, actie, Element.ACTIE_TIJDSTIPREGISTRATIE, true));
        final BrpDatum datumOntlening =
                BrpMapperUtil.mapDatum(actie.getDatumOntlening(), bepaalOnderzoek(brpOnderzoekMapper, actie, Element.ACTIE_DATUMONTLENING, true));

        return new BrpActie(
                actie.getId(),
                soortActieCode,
                partijCode,
                datumTijdRegistratie,
                datumOntlening,
                AbstractBrpMapper.mapActieBronSet(actie.getActieBronSet(), brpOnderzoekMapper),
                0,
                AbstractBrpMapper.mapLo3Herkomst(actie.getLo3Voorkomen()));
    }

    private static Lo3Herkomst mapLo3Herkomst(final Lo3Voorkomen lo3Voorkomen) {
        if (lo3Voorkomen == null) {
            return null;
        }
        try {
            return new Lo3Herkomst(
                    Lo3CategorieEnum.getLO3Categorie(lo3Voorkomen.getCategorie()),
                    lo3Voorkomen.getStapelvolgnummer(),
                    lo3Voorkomen.getVoorkomenvolgnummer(),
                    lo3Voorkomen.getConversieSortering());
        } catch (final Lo3SyntaxException e) {
            throw new IllegalStateException("Onverwachte fout bij het mappen van lo3voorkomen.categorie.", e);
        }
    }

    private static List<BrpActieBron> mapActieBronSet(final Set<ActieBron> actieBronSet, final BrpOnderzoekMapper brpOnderzoekMapper) {
        if (actieBronSet == null || actieBronSet.isEmpty()) {
            return null;
        }

        final List<BrpActieBron> result = new ArrayList<>();

        for (final ActieBron actieBron : actieBronSet) {
            result.add(AbstractBrpMapper.mapActieBron(actieBron, brpOnderzoekMapper));
        }

        return result;
    }

    private static BrpActieBron mapActieBron(final ActieBron actieBron, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final List<BrpGroep<BrpDocumentInhoud>> documentGroepen = new ArrayList<>();
        if (actieBron.getDocument() != null) {
            final Document document = actieBron.getDocument();
            documentGroepen.add(
                    new BrpGroep<>(AbstractBrpMapper.mapDocument(document, brpOnderzoekMapper), null, null, null, null));
        }

        final BrpStapel<BrpDocumentInhoud> documentStapel;
        if (!documentGroepen.isEmpty()) {
            documentStapel = new BrpStapel<>(documentGroepen);
        } else {
            documentStapel = null;
        }
        final BrpString rechtsgrondOmschrijving =
                BrpMapperUtil.mapBrpString(
                        actieBron.getRechtsgrondOmschrijving(),
                        bepaalOnderzoek(brpOnderzoekMapper, actieBron, Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING, true));
        return new BrpActieBron(documentStapel, rechtsgrondOmschrijving);
    }

    private static Lo3Onderzoek bepaalOnderzoek(
            final BrpOnderzoekMapper brpOnderzoekMapper,
            final Entiteit entiteit,
            final Element element,
            final boolean elementBehoortBijGroepsOnderzoek) {
        Lo3Onderzoek onderzoek = null;
        if (brpOnderzoekMapper != null) {
            onderzoek = brpOnderzoekMapper.bepaalOnderzoek(entiteit, element, elementBehoortBijGroepsOnderzoek);
        }
        return onderzoek;
    }

    private static BrpDocumentInhoud mapDocument(final Document document, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpSoortDocumentCode soortDocumentCode =
                BrpMapperUtil.mapBrpSoortDocumentCode(
                        document.getSoortDocument(),
                        bepaalOnderzoek(brpOnderzoekMapper, document, Element.DOCUMENT_SOORTNAAM, true));
        final BrpString aktenummer =
                BrpMapperUtil.mapBrpString(document.getAktenummer(), bepaalOnderzoek(brpOnderzoekMapper, document, Element.DOCUMENT_AKTENUMMER, true));
        final BrpString omschrijving =
                BrpMapperUtil.mapBrpString(document.getOmschrijving(), bepaalOnderzoek(brpOnderzoekMapper, document, Element.DOCUMENT_OMSCHRIJVING, true));
        final BrpPartijCode partijCode =
                BrpMapperUtil.mapBrpPartijCode(document.getPartij(), bepaalOnderzoek(brpOnderzoekMapper, document, Element.DOCUMENT_PARTIJCODE, true));

        return new BrpDocumentInhoud(soortDocumentCode, aktenummer, omschrijving, partijCode);
    }

    /**
     * Map de BRP database set naar het BRP conversiemodel.
     * @param historieSet BRP database set
     * @param brpOnderzoekMapper De mapper voor onderzoeken
     * @return BRP conversiemodel stapel
     */
    public final BrpStapel<G> map(final Set<T> historieSet, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final List<BrpGroep<G>> groepen = new ArrayList<>();

        if (historieSet != null) {
            for (final T historie : historieSet) {
                // Voorkomen tbv levering mutaties worden genegeerd.
                if (!(historie instanceof FormeleHistorie && ((FormeleHistorie) historie).isVoorkomenTbvLeveringMutaties())) {
                    groepen.add(map(historie, brpOnderzoekMapper));
                }
            }
        }
        if (groepen.isEmpty()) {
            return null;
        } else {
            return new BrpStapel<>(groepen);
        }
    }

    private BrpGroep<G> map(final T historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final G inhoud = mapInhoud(historie, brpOnderzoekMapper);
        final BrpHistorie hist = AbstractBrpMapper.mapHistorie(historie, brpOnderzoekMapper);
        final BrpActie actieInhoud;
        final BrpActie actieVerval;
        if (historie instanceof FormeleHistorie) {
            actieInhoud = AbstractBrpMapper.mapActie(((FormeleHistorie) historie).getActieInhoud(), brpOnderzoekMapper);
            actieVerval = AbstractBrpMapper.mapActie(((FormeleHistorie) historie).getActieVerval(), brpOnderzoekMapper);
        } else {
            actieInhoud = null;
            actieVerval = null;
        }
        final BrpActie actieGeldigheid;
        if (historie instanceof MaterieleHistorie) {
            actieGeldigheid = AbstractBrpMapper.mapActie(((MaterieleHistorie) historie).getActieAanpassingGeldigheid(), brpOnderzoekMapper);
        } else {
            actieGeldigheid = null;
        }

        return new BrpGroep<>(inhoud, hist, actieInhoud, actieVerval, actieGeldigheid);
    }

    /**
     * Map de inhoud van een historie naar een BRP conversiemodel groep.
     * @param historie historie
     * @param brpOnderzoekMapper De mapper voor onderzoeken
     * @return BRP conversiemodel groep
     */
    protected abstract G mapInhoud(T historie, BrpOnderzoekMapper brpOnderzoekMapper);
}
