/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;

import java.util.Optional;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.test.common.dsl.Ref;
import org.apache.commons.lang3.StringUtils;

/**
 */
final class ToegangLeveringautorisatieParser extends AbstractToegangautorisatieParser {

    private final int                                 id;
    private LeveringautorisatieParser leveringautorisatie;
    private final String                              naderepopulatiebeperking;
    private final String                              afleverpunt;
    private final Integer                             rol;
    private final Optional<String> ref;

    /**
     * Constructor.
     *
     * @param sectie      de sectie
     * @param idGenerator de id generator
     */
    ToegangLeveringautorisatieParser(final DslSectie sectie, final IdGenerator idGenerator) {
        super(sectie);
        this.id = idGenerator.getToegangleveringsautorisatieId().incrementAndGet();
        naderepopulatiebeperking = sectie.geefStringValue("naderepopulatiebeperking").orElse(null);
        if ("true".equals(sectie.geefStringValue("afleverpuntNull").orElse(null))) {
            afleverpunt = null;
        } else {
            afleverpunt = StringUtils.defaultString(sectie.geefStringValue("afleverpunt").orElse(null), "<afleverpunt>");
        }
        rol = sectie.geefInteger("rol").orElse(1);
        ref = sectie.geefStringValue("@ref");
    }

    public void collect(final AutorisatieData collector) {
        final PartijRol partijRol = Partijen.getPartijRol(getGeautoriseerde());
        final ToegangLeveringsAutorisatie entity = new ToegangLeveringsAutorisatie(partijRol, leveringautorisatie.getLeveringsautorisatie());
        entity.setId(id);
        entity.setDatumIngang(getDatingang());
        entity.setDatumEinde(getDateinde());
        entity.setNaderePopulatiebeperking(naderepopulatiebeperking);
        entity.setAfleverpunt(afleverpunt);
        entity.setIndicatieGeblokkeerd(isIndblok());
        entity.setActueelEnGeldig(isIndag() == null ? true : isIndag());
        if (getTransporteur() != null) {
            entity.setTransporteur(Partijen.getPartij(getTransporteur()));
        }
        if (getOndertekenaar() != null) {
            entity.setOndertekenaar(Partijen.getPartij(getOndertekenaar()));
        }
        collector.getToegangLeveringsautorisatieEntities().add(entity);

        ref.ifPresent(s -> collector.getToegangRefs().add(new Ref<>(s, entity)));
    }

    /**
     * @return het id van de toegangleveringsautorisatie
     */
    @Override
    public int getId() {
        return id;
    }

    public LeveringautorisatieParser getLeveringautorisatie() {
        return leveringautorisatie;
    }

    public void setLeveringautorisatie(final LeveringautorisatieParser leveringautorisatie) {
        this.leveringautorisatie = leveringautorisatie;
    }

    public Integer getRol() {
        return rol;
    }
}
