/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.wa11;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.Actiebron;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.brp.levering.lo3.conversie.VervalBepaler;
import nl.bzk.brp.levering.lo3.conversie.mutatie.AbstractMutatieVerwerker;
import nl.bzk.brp.levering.lo3.decorator.Persoon;
import nl.bzk.brp.levering.lo3.mapper.AbstractMapper;
import nl.bzk.brp.levering.lo3.mapper.BrpMapperUtil;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapperImpl;
import nl.bzk.brp.levering.lo3.mapper.PersoonGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonslijstMapper;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieWaardeFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpOnderzoekLo3;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpPersoonConverteerder.GeboorteConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpPersoonConverteerder.SamengesteldeNaamConverteerder;
import org.springframework.stereotype.Component;

/**
 * Converteert de gegevens specifiek voor een a-nummerwijziging (eventueel oude) naam en geboorte.
 */
@Component("lo3_wa11Converteerder")
public class Wa11Converteerder implements Converteerder {

    private static final Lo3CategorieFormatter<Lo3PersoonInhoud> PERSOON_FORMAT = new Lo3PersoonFormatter();
    private static final int GRENSWAARDE_AANDUIDING_GEGEVENS_IN_ONDERZOEK = 500_000;

    private PersoonSamengesteldeNaamMapper samengesteldeNaamMapper;
    private PersoonGeboorteMapper geboorteMapper;
    private SamengesteldeNaamConverteerder samengesteldeNaamConverteerder;
    private GeboorteConverteerder geboorteConverteerder;
    private BrpOnderzoekLo3 onderzoekConverteerder;
    private ConversietabelFactory conversietabelFactory;

    /**
     * Constructor.
     * @param samengesteldeNaamMapper Mapt de samengestelde naam (van een persoon)
     * @param geboorteMapper Mapt de geboorte (van een persoon)
     * @param onderzoekConverteerder Converteert het onderzoek
     * @param conversietabelFactory Factory voor verschillende conversietabellen
     * @param attributenConverteerder converteert de BRP attributen
     */
    @Inject
    public Wa11Converteerder(
            final PersoonSamengesteldeNaamMapper samengesteldeNaamMapper,
            final PersoonGeboorteMapper geboorteMapper,
            final BrpOnderzoekLo3 onderzoekConverteerder,
            final ConversietabelFactory conversietabelFactory,
            final BrpAttribuutConverteerder attributenConverteerder) {
        this.samengesteldeNaamMapper = samengesteldeNaamMapper;
        this.geboorteMapper = geboorteMapper;
        this.samengesteldeNaamConverteerder = new SamengesteldeNaamConverteerder(attributenConverteerder);
        this.geboorteConverteerder = new GeboorteConverteerder(attributenConverteerder);
        this.onderzoekConverteerder = onderzoekConverteerder;
        this.conversietabelFactory = conversietabelFactory;
    }

    @Override
    public final List<Lo3CategorieWaarde> converteer(
            final Persoonslijst persoonslijst,
            final List<Stapel> istStapels,
            final AdministratieveHandeling administratieveHandeling,
            final IdentificatienummerMutatie identificatienummerMutatie,
            final ConversieCache conversieCache) {
        List<Lo3CategorieWaarde> resultaat = conversieCache.getAnummerwijzigingCategorien();

        if (resultaat == null) {
            resultaat = converteer(persoonslijst, administratieveHandeling);
            conversieCache.setAnummerwijzigingCategorien(resultaat);
        }

        return resultaat;
    }

    private List<Lo3CategorieWaarde> converteer(final Persoonslijst persoonslijst, final AdministratieveHandeling administratieveHandeling) {
        // Verzamel acties
        final List<Long> acties = administratieveHandeling.getActies().stream().map(Actie::getId).collect(Collectors.toList());

        // Bepaal persoonsgegevens voor Wa11 bericht
        final MetaObject persoonObject = persoonslijst.getMetaObject();
        final MetaRecord identiteitsRecord = MetaModelUtil.getIdentiteitRecord(persoonObject, PersoonslijstMapper.IDENTITEIT_GROEP_ELEMENT);
        MetaRecord samengesteldeNaamRecord =
                VervalBepaler.bepaalVerval(MetaModelUtil.getRecords(persoonObject, ElementConstants.PERSOON_SAMENGESTELDENAAM), acties);
        if (samengesteldeNaamRecord == null) {
            samengesteldeNaamRecord = MetaModelUtil.getActueleRecord(persoonObject, ElementConstants.PERSOON_SAMENGESTELDENAAM);
        }
        MetaRecord geboorteRecord = VervalBepaler.bepaalVerval(MetaModelUtil.getRecords(persoonObject, ElementConstants.PERSOON_GEBOORTE), acties);
        if (geboorteRecord == null) {
            geboorteRecord = MetaModelUtil.getActueleRecord(persoonObject, ElementConstants.PERSOON_GEBOORTE);
        }

        // Map persoonsgegevens naar conversie brp model
        final Persoon persoon = new Persoon(persoonObject);
        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(persoon);
        final BrpSamengesteldeNaamInhoud brpNaam = samengesteldeNaamMapper.mapInhoud(identiteitsRecord, samengesteldeNaamRecord, onderzoekMapper);
        final BrpGeboorteInhoud brpGeboorte = geboorteMapper.mapInhoud(identiteitsRecord, geboorteRecord, onderzoekMapper);

        // Converteer persoonsgegevens naar lo3 model
        Lo3PersoonInhoud persoonInhoud = samengesteldeNaamConverteerder.maakNieuweInhoud();
        persoonInhoud = samengesteldeNaamConverteerder.vulInhoud(persoonInhoud, brpNaam, null);
        persoonInhoud = geboorteConverteerder.vulInhoud(persoonInhoud, brpGeboorte, null);

        // Converteer documentatie (voor 88.10/20)
        Lo3Documentatie persoonDocumentatie = mergeRniGegevensObvActie(null, samengesteldeNaamRecord.getActieInhoud(), onderzoekMapper);
        persoonDocumentatie = mergeRniGegevensObvActie(persoonDocumentatie, geboorteRecord.getActieInhoud(), onderzoekMapper);

        // Bepaal onderzoek op persoonsgegevens
        final Lo3Onderzoek persoonOnderzoek =
                maakOnderzoekActueel(
                        onderzoekConverteerder.bepaalOnderzoekUitElementen(
                                new Lo3Categorie<>(persoonInhoud, persoonDocumentatie, new Lo3Historie(), null),
                                Lo3CategorieEnum.CATEGORIE_01));

        // Formatter persoonsgegevens naar lo3 categoriewaarde model
        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        PERSOON_FORMAT.format(persoonInhoud, formatter);
        Lo3PersoonslijstFormatter.formatOnderzoek(persoonOnderzoek, formatter);
        Lo3PersoonslijstFormatter.formatDocumentatie(persoonDocumentatie, formatter);

        // Result
        return formatter.getList();
    }

    private Lo3Documentatie mergeRniGegevensObvActie(final Lo3Documentatie oudeDocumentatie, final Actie actie, final OnderzoekMapper onderzoekMapper) {
        final Lo3Documentatie documentatie = bepaalRniGegevensObvActie(actie, onderzoekMapper);
        return AbstractMutatieVerwerker.mergeRni(documentatie, oudeDocumentatie);
    }

    private Lo3Documentatie bepaalRniGegevensObvActie(final Actie actie, final OnderzoekMapper onderzoekMapper) {

        Lo3RNIDeelnemerCode rniDeelnemer = null;
        Lo3String rechtsgrondOmschrijving = null;

        final BrpPartijCode brpPartijCode =
                BrpMapperUtil.mapBrpPartijCode(
                        actie.getPartijCode(),
                        onderzoekMapper.bepaalOnderzoek(actie.getId(), AbstractMapper.ACTIE_PARTIJCODE_ELEMENT, false));
        if (conversietabelFactory.createRNIDeelnemerConversietabel().valideerBrp(brpPartijCode)) {
            rniDeelnemer = conversietabelFactory.createRNIDeelnemerConversietabel().converteerNaarLo3(brpPartijCode);

            for (final Actiebron actieBron : actie.getBronnen()) {
                // 1 van de actiebronnen bevat de rechtsgrondomschrijving
                if (actieBron.getRechtsgrondomschrijving() != null) {
                    rechtsgrondOmschrijving =
                            new Lo3String(
                                    actieBron.getRechtsgrondomschrijving(),
                                    onderzoekMapper.bepaalOnderzoek(actieBron.getId(), AbstractMapper.ACTIEBRON_RECHTSGROND_OMSCHRIJVING_ELEMENT, false));
                }
            }
        }

        return new Lo3Documentatie(actie.getId(), null, null, null, null, null, rniDeelnemer, rechtsgrondOmschrijving);

    }

    private Lo3Onderzoek maakOnderzoekActueel(final Lo3Onderzoek onderzoek) {
        if (onderzoek != null
                && onderzoek.getAanduidingGegevensInOnderzoek() != null
                && onderzoek.getAanduidingGegevensInOnderzoek().getIntegerWaarde() > GRENSWAARDE_AANDUIDING_GEGEVENS_IN_ONDERZOEK) {
            return new Lo3Onderzoek(
                    new Lo3Integer(onderzoek.getAanduidingGegevensInOnderzoek().getIntegerWaarde() - GRENSWAARDE_AANDUIDING_GEGEVENS_IN_ONDERZOEK),
                    onderzoek.getDatumIngangOnderzoek(),
                    onderzoek.getDatumEindeOnderzoek());
        } else {
            return onderzoek;
        }
    }
}
