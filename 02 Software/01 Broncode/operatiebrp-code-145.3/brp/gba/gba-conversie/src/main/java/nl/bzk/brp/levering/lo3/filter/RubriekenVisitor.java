/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.levering.lo3.mapper.AbstractOuderGezagMapper;
import nl.bzk.brp.levering.lo3.mapper.AdresMapper;
import nl.bzk.brp.levering.lo3.mapper.AdressenMapper;
import nl.bzk.brp.levering.lo3.mapper.BuitenlandsPersoonsnummerMapper;
import nl.bzk.brp.levering.lo3.mapper.BuitenlandsPersoonsnummersMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapGeslachtsaanduidingMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapRelatieMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkGeslachtsaanduidingMapper;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkRelatieMapper;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.KindGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.KindIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.KindSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.NationaliteitMapper;
import nl.bzk.brp.levering.lo3.mapper.NationaliteitenMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapperImpl;
import nl.bzk.brp.levering.lo3.mapper.OuderGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderGeslachtsaanduidingMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderOuderschapMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonAfgeleidAdministratiefMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonBijhoudingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonDeelnameEuVerkiezingenMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonGeslachtsaanduidingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieBehandeldAlsNederlanderMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieDerdeHeeftGezagMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieOnderCurateleMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieOnverwerktDocumentAanwezigMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieStaatloosMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieVastgesteldNietNederlanderMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieVerstrekkingsbeperkingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesBehandeldAlsNederlanderMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesBijzondereVerblijfsrechtelijkePositieMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesDerdeHeeftGezagMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesOnderCurateleMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesOnverwerktDocumentAanwezigMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesStaatloosMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesVastgesteldNietNederlanderMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesVerstrekkingsbeperkingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonInschrijvingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonMigratieMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonNaamgebruikMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonNummerverwijzingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonOverlijdenMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonPersoonskaartMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonUitsluitingKiesrechtMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonVerblijfsrechtMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonslijstMapper;
import nl.bzk.brp.levering.lo3.mapper.ReisdocumentMapper;
import nl.bzk.brp.levering.lo3.mapper.ReisdocumentenMapper;
import nl.bzk.brp.levering.lo3.mapper.VerificatieMapper;
import nl.bzk.brp.levering.lo3.mapper.VerificatiesMapper;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;

/**
 * Rubrieken visitor.
 */
final class RubriekenVisitor {

    private final Long administratieveHandelingId;
    private final Set<Long> acties = new HashSet<>();

    private Set<String> resultaat;

    /**
     * Constructor.
     * @param administratieveHandeling administratieve handeling
     */
    RubriekenVisitor(final AdministratieveHandeling administratieveHandeling) {
        administratieveHandelingId = administratieveHandeling.getId();
        acties.addAll(administratieveHandeling.getActies().stream().map(Actie::getId).collect(Collectors.toList()));

        resultaat = new HashSet<>();
    }

    /**
     * Geef de rubrieken.
     * @return lijst met rubrieken (kan leeg zijn), null als de rubrieken niet bepaald konden worden
     */
    public List<String> getRubrieken() {
        if (resultaat == null) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(resultaat);
        }
    }

    /**
     * Bepaal de mutaties voor een persoon.
     * @param persoon persoon
     */
    void visit(final MetaObject persoon) {
        verwerkPersoonGroepen(persoon,
                Arrays.asList(PersoonAfgeleidAdministratiefMapper.GROEP_ELEMENT, PersoonBijhoudingMapper.GROEP_ELEMENT,
                        PersoonDeelnameEuVerkiezingenMapper.GROEP_ELEMENT, PersoonGeboorteMapper.GROEP_ELEMENT, PersoonGeslachtsaanduidingMapper.GROEP_ELEMENT,
                        PersoonIdentificatienummersMapper.GROEP_ELEMENT, PersoonInschrijvingMapper.GROEP_ELEMENT, PersoonMigratieMapper.GROEP_ELEMENT,
                        PersoonNaamgebruikMapper.GROEP_ELEMENT, PersoonNummerverwijzingMapper.GROEP_ELEMENT, PersoonOverlijdenMapper.GROEP_ELEMENT,
                        PersoonPersoonskaartMapper.GROEP_ELEMENT, PersoonSamengesteldeNaamMapper.GROEP_ELEMENT, PersoonUitsluitingKiesrechtMapper.GROEP_ELEMENT,
                        PersoonVerblijfsrechtMapper.GROEP_ELEMENT));

        // indicaties
        verwerkObject(persoon, PersoonIndicatiesBehandeldAlsNederlanderMapper.OBJECT_ELEMENT, PersoonIndicatieBehandeldAlsNederlanderMapper.GROEP_ELEMENT);
        verwerkObject(persoon, PersoonIndicatiesBijzondereVerblijfsrechtelijkePositieMapper.OBJECT_ELEMENT,
                PersoonIndicatieBijzondereVerblijfsrechtelijkePositieMapper.GROEP_ELEMENT);
        verwerkObject(persoon, PersoonIndicatiesDerdeHeeftGezagMapper.OBJECT_ELEMENT, PersoonIndicatieDerdeHeeftGezagMapper.GROEP_ELEMENT);
        verwerkObject(persoon, PersoonIndicatiesOnderCurateleMapper.OBJECT_ELEMENT, PersoonIndicatieOnderCurateleMapper.GROEP_ELEMENT);
        verwerkObject(persoon, PersoonIndicatiesOnverwerktDocumentAanwezigMapper.OBJECT_ELEMENT,
                PersoonIndicatieOnverwerktDocumentAanwezigMapper.GROEP_ELEMENT);
        verwerkObject(persoon, PersoonIndicatiesSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper.OBJECT_ELEMENT,
                PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper.GROEP_ELEMENT);
        verwerkObject(persoon, PersoonIndicatiesStaatloosMapper.OBJECT_ELEMENT, PersoonIndicatieStaatloosMapper.GROEP_ELEMENT);
        verwerkObject(persoon, PersoonIndicatiesVastgesteldNietNederlanderMapper.OBJECT_ELEMENT,
                PersoonIndicatieVastgesteldNietNederlanderMapper.GROEP_ELEMENT);
        verwerkObject(persoon, PersoonIndicatiesVerstrekkingsbeperkingMapper.OBJECT_ELEMENT, PersoonIndicatieVerstrekkingsbeperkingMapper.GROEP_ELEMENT);
        verwerkObject(persoon, VerificatiesMapper.OBJECT_ELEMENT, VerificatieMapper.GROEP_ELEMENT);

        verwerkObject(persoon, AdressenMapper.OBJECT_ELEMENT, AdresMapper.GROEP_ELEMENT);
        verwerkObject(persoon, NationaliteitenMapper.OBJECT_ELEMENT, NationaliteitMapper.GROEP_ELEMENT);
        verwerkObject(persoon, BuitenlandsPersoonsnummersMapper.OBJECT_ELEMENT, BuitenlandsPersoonsnummerMapper.GROEP_ELEMENT);
        verwerkObject(persoon, ReisdocumentenMapper.OBJECT_ELEMENT, ReisdocumentMapper.GROEP_ELEMENT);

        verwerkOuders(persoon);
        verwerkKind(persoon);
        verwerkHuwelijkPartnerschap(persoon);
        verwerkOnderzoek(persoon);
    }

    private void verwerkPersoonGroepen(final MetaObject persoon, final List<GroepElement> groepen) {
        groepen.forEach(groep -> visitRecords(MetaModelUtil.getRecords(persoon, groep)));
    }

    private void verwerkOnderzoek(final MetaObject persoon) {
        // Onderzoeken
        MetaModelUtil.getObjecten(persoon, OnderzoekMapperImpl.OBJECT_ELEMENT)
                .forEach(onderzoek -> visitRecords(MetaModelUtil.getRecords(onderzoek, OnderzoekMapperImpl.GROEP_ELEMENT)));
    }

    private void verwerkHuwelijkPartnerschap(final MetaObject persoon) {
        // Verwerken huwelijken/gerelateerd partnerschappen
        MetaModelUtil.getObjecten(persoon, PersoonslijstMapper.PARTNER_ELEMENT).forEach(this::verwerkPartner);
    }

    private void verwerkPartner(final MetaObject partner) {
        MetaModelUtil.getObjecten(partner, PersoonslijstMapper.HUWELIJK_ELEMENT).forEach(this::verwerkHuwelijk);

        MetaModelUtil.getObjecten(partner, PersoonslijstMapper.GEREGISTREERD_PARTNERSCHAP_ELEMENT).forEach(this::verwerkGeregistreerdPartnerschap);
    }

    private void verwerkGeregistreerdPartnerschap(final MetaObject geregistreerdPartnerschap) {
        visitRecords(MetaModelUtil.getRecords(geregistreerdPartnerschap, GeregistreerdPartnerschapRelatieMapper.GROEP_ELEMENT));

        MetaModelUtil.getObjecten(geregistreerdPartnerschap, PersoonslijstMapper.GERELATEERDE_GEREGISTREERDEPARTNER_ELEMENT)
                .forEach(new GerelateerdeGeregistreerdePartnerPersoonVisitor());
    }

    private void verwerkHuwelijk(final MetaObject huwelijk) {
        visitRecords(MetaModelUtil.getRecords(huwelijk, HuwelijkRelatieMapper.GROEP_ELEMENT));

        MetaModelUtil.getObjecten(huwelijk, PersoonslijstMapper.GERELATEERDE_HUWELIJKSPARTNER_ELEMENT)
                .forEach(new GerelateerdeHuwelijksPartnerPersoonVisitor());
    }

    private void verwerkKind(final MetaObject persoon) {
        // Verwerk kinderen
        MetaModelUtil.getObjecten(persoon, PersoonslijstMapper.OUDER_ELEMENT).forEach(this::verwerkOudersVanKind);
    }

    private void verwerkOudersVanKind(final MetaObject ouder) {
        MetaModelUtil.getObjecten(ouder, PersoonslijstMapper.FAMILIERECHTELIJKE_BETREKKEING_ELEMENT).forEach(this::verwerkFamilieRechtelijkeBetrekkingVanKind);
    }

    private void verwerkFamilieRechtelijkeBetrekkingVanKind(final MetaObject familierechtelijkeBetrekking) {
        MetaModelUtil.getObjecten(familierechtelijkeBetrekking, PersoonslijstMapper.GERELATEERDE_KIND_ELEMENT).forEach(new VerwerkGerelateerdKindVisitor());
    }

    private void verwerkOuders(final MetaObject persoon) {
        // Verwerk ouders
        MetaModelUtil.getObjecten(persoon, PersoonslijstMapper.KIND_ELEMENT).forEach(this::verwerkKinderenVanOuder);
    }

    private void verwerkKinderenVanOuder(final MetaObject kind) {
        MetaModelUtil.getObjecten(kind, PersoonslijstMapper.FAMILIERECHTELIJKE_BETREKKEING_ELEMENT).forEach(this::verwerkFamilieRechtelijkeBetrekkingVanOuder);
    }

    private void verwerkFamilieRechtelijkeBetrekkingVanOuder(final MetaObject familierechtelijkeBetrekking) {
        MetaModelUtil.getObjecten(familierechtelijkeBetrekking, PersoonslijstMapper.GERELATEERDE_OUDER_ELEMENT).forEach(new VerwerkGerelateerdeOuderVisitor());
    }

    private void verwerkObject(final MetaObject persoon, final ObjectElement teVerwerkenObjectElement, final GroepElement teVerwerkenGroepElement) {
        MetaModelUtil.getObjecten(persoon, teVerwerkenObjectElement)
                .forEach(indicatie -> visitRecords(MetaModelUtil.getRecords(indicatie, teVerwerkenGroepElement)));
    }

    private void visitRecords(final Collection<MetaRecord> records) {
        for (final MetaRecord record : records) {
            if (isRecordGeraakt(record) && (resultaat != null)) {
                final List<String> rubrieken = RubriekenMap.INSTANCE.getRubrieken(record.getParentGroep().getGroepElement());
                if (rubrieken == null) {
                    resultaat = null;
                } else {
                    resultaat.addAll(rubrieken);
                }
                break;
            }
        }
    }

    private boolean isRecordGeraakt(final MetaRecord record) {
        return isGeraakt(record.getActieInhoud()) || isGeraakt(record.getActieAanpassingGeldigheid()) || isGeraakt(record.getActieVerval())
                || isGeraakt(record.getActieVervalTbvLeveringMutaties());
    }

    private boolean isGeraakt(final Actie actie) {
        return (actie != null) && acties.contains(actie.getId());
    }

    /**
     * Bepaal de mutaties obv de IST stapels.
     * @param istStapels IST stapels
     */
    void visit(final List<Stapel> istStapels) {
        if (istStapels != null) {
            istStapels.forEach(this::verwerkIstStapel);
        }
    }

    private void verwerkIstStapel(final Stapel istStapel) {
        istStapel.getStapelvoorkomens().forEach(stapelVoorkomen -> verwerkIstStapelVoorkomen(istStapel, stapelVoorkomen));
    }

    private void verwerkIstStapelVoorkomen(final Stapel istStapel, final StapelVoorkomen stapelVoorkomen) {
        final nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling ah = stapelVoorkomen.getAdministratieveHandeling();
        if (administratieveHandelingId.equals(ah.getId()) && (resultaat != null)) {
            resultaat.add(istStapel.getCategorie() + ".");
        }
    }

    /**
     * Verwerk Gerelateerde Huwelijkspartner persoon.
     */
    private class GerelateerdeHuwelijksPartnerPersoonVisitor implements Consumer<MetaObject> {
        @Override
        public void accept(final MetaObject gerelateerdePartner) {
            MetaModelUtil.getObjecten(gerelateerdePartner, PersoonslijstMapper.GERELATEERDE_HUWELIJKSPARTNER_PERSOON_ELEMENT)
                    .forEach(this::verwerkGerelateerdePartnerPersoon);
        }

        private void verwerkGerelateerdePartnerPersoon(final MetaObject gerelateerdePartnerPersoon) {
            visitRecords(MetaModelUtil.getRecords(gerelateerdePartnerPersoon, HuwelijkIdentificatienummersMapper.GROEP_ELEMENT));
            visitRecords(MetaModelUtil.getRecords(gerelateerdePartnerPersoon, HuwelijkSamengesteldeNaamMapper.GROEP_ELEMENT));
            visitRecords(MetaModelUtil.getRecords(gerelateerdePartnerPersoon, HuwelijkGeboorteMapper.GROEP_ELEMENT));
            visitRecords(MetaModelUtil.getRecords(gerelateerdePartnerPersoon, HuwelijkGeslachtsaanduidingMapper.GROEP_ELEMENT));
        }
    }

    /**
     * Verwerk Geregistreerd partnerschapspartner persoon.
     */
    private class GerelateerdeGeregistreerdePartnerPersoonVisitor implements Consumer<MetaObject> {
        @Override
        public void accept(final MetaObject gerelateerdePartner) {
            MetaModelUtil.getObjecten(gerelateerdePartner, PersoonslijstMapper.GERELATEERDE_GEREGISTREERDEPARTNER_PERSOON_ELEMENT)
                    .forEach(this::verwerkGerelateerdePartnerPersoon);
        }

        private void verwerkGerelateerdePartnerPersoon(final MetaObject gerelateerdePartnerPersoon) {
            visitRecords(MetaModelUtil.getRecords(gerelateerdePartnerPersoon, GeregistreerdPartnerschapIdentificatienummersMapper.GROEP_ELEMENT));
            visitRecords(MetaModelUtil.getRecords(gerelateerdePartnerPersoon, GeregistreerdPartnerschapSamengesteldeNaamMapper.GROEP_ELEMENT));
            visitRecords(MetaModelUtil.getRecords(gerelateerdePartnerPersoon, GeregistreerdPartnerschapGeboorteMapper.GROEP_ELEMENT));
            visitRecords(MetaModelUtil.getRecords(gerelateerdePartnerPersoon, GeregistreerdPartnerschapGeslachtsaanduidingMapper.GROEP_ELEMENT));
        }
    }

    /**
     * Verwerk Gerelateerd Kind.
     */
    private class VerwerkGerelateerdKindVisitor implements Consumer<MetaObject> {

        @Override
        public void accept(final MetaObject gerelateerdeKind) {
            visitRecords(MetaModelUtil.getRecords(gerelateerdeKind, ElementHelper.getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT.getId())));

            MetaModelUtil.getObjecten(gerelateerdeKind, PersoonslijstMapper.GERELATEERDE_KIND_PERSOON_ELEMENT).forEach(this::verwerkGerelateerdKindPersoon);
        }

        private void verwerkGerelateerdKindPersoon(final MetaObject gerelateerdeKindPersoon) {
            visitRecords(MetaModelUtil.getRecords(gerelateerdeKindPersoon, KindGeboorteMapper.GROEP_ELEMENT));
            visitRecords(MetaModelUtil.getRecords(gerelateerdeKindPersoon, KindSamengesteldeNaamMapper.GROEP_ELEMENT));
            visitRecords(MetaModelUtil.getRecords(gerelateerdeKindPersoon, KindIdentificatienummersMapper.GROEP_ELEMENT));
        }

    }

    /**
     * Verwerk gerelateerde Ouder.
     */
    private class VerwerkGerelateerdeOuderVisitor implements Consumer<MetaObject> {

        @Override
        public void accept(final MetaObject gerelateerdeOuder) {
            visitRecords(MetaModelUtil.getRecords(gerelateerdeOuder, ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT.getId())));
            visitRecords(MetaModelUtil.getRecords(gerelateerdeOuder, OuderOuderschapMapper.GROEP_ELEMENT));
            visitRecords(MetaModelUtil.getRecords(gerelateerdeOuder, AbstractOuderGezagMapper.GROEP_ELEMENT));

            MetaModelUtil.getObjecten(gerelateerdeOuder, PersoonslijstMapper.GERELATEERDE_OUDER_PERSOON_ELEMENT).forEach(this::verwerkGerelateerdeOuderPersoon);
        }

        private void verwerkGerelateerdeOuderPersoon(final MetaObject gerelateerdeOuderPersoon) {
            visitRecords(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderGeboorteMapper.GROEP_ELEMENT));
            visitRecords(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderSamengesteldeNaamMapper.GROEP_ELEMENT));
            visitRecords(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderIdentificatienummersMapper.GROEP_ELEMENT));
            visitRecords(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderGeslachtsaanduidingMapper.GROEP_ELEMENT));
        }

    }
}
