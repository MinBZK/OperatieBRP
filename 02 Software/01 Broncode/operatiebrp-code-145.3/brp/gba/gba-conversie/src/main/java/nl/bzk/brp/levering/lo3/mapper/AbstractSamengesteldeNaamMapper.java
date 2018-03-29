/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de samengestelde naam.
 */
@Component
public abstract class AbstractSamengesteldeNaamMapper extends AbstractMapper<BrpSamengesteldeNaamInhoud> {

    private final AttribuutElement predicaatCode;
    private final AttribuutElement voornamen;
    private final AttribuutElement voorvoegsel;
    private final AttribuutElement scheidingsteken;
    private final AttribuutElement adellijkeTitelCode;
    private final AttribuutElement geslachtsnaamStam;
    private final AttribuutElement indicatieNamenReeks;
    private final AttribuutElement indicatieAfgeleid;

    /**
     * Constructor.
     * @param identiteitGroep element voor identiteit groep
     * @param groep element voor te mappen groep
     * @param datumAanvangGeldigheid element voor datum aanvang geldigheid
     * @param datumEindeGeldigheid element voor datum einde geldigheid
     * @param tijdstipRegistratie element voor tijdstip registratie
     * @param tijdstipVerval element voor tijdstip verval
     * @param predicaatCode element voor predicaat
     * @param voornamen element voor voornamen
     * @param voorvoegsel element voor voorvoegsel
     * @param scheidingsteken element voor scheidingsteken
     * @param adellijkeTitelCode element voor adellijke titel
     * @param geslachtsnaamStam element voor geslachtsnaam
     * @param indicatieNamenReeks element voor indicatie namen reeks
     * @param indicatieAfgeleid element voor indicatie afgeleid
     */
    //
    /*
     * squid:S00107 Methods should not have too many parameters
     *
     * Terecht, geaccepteerd voor deze klasse.
     */
    protected AbstractSamengesteldeNaamMapper(
            final GroepElement identiteitGroep,
            final GroepElement groep,
            final AttribuutElement datumAanvangGeldigheid,
            final AttribuutElement datumEindeGeldigheid,
            final AttribuutElement tijdstipRegistratie,
            final AttribuutElement tijdstipVerval,
            final AttribuutElement predicaatCode,
            final AttribuutElement voornamen,
            final AttribuutElement voorvoegsel,
            final AttribuutElement scheidingsteken,
            final AttribuutElement adellijkeTitelCode,
            final AttribuutElement geslachtsnaamStam,
            final AttribuutElement indicatieNamenReeks,
            final AttribuutElement indicatieAfgeleid) {
        super(identiteitGroep, groep, datumAanvangGeldigheid, datumEindeGeldigheid, tijdstipRegistratie, tijdstipVerval);
        this.predicaatCode = predicaatCode;
        this.voornamen = voornamen;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.adellijkeTitelCode = adellijkeTitelCode;
        this.geslachtsnaamStam = geslachtsnaamStam;
        this.indicatieNamenReeks = indicatieNamenReeks;
        this.indicatieAfgeleid = indicatieAfgeleid;
    }

    @Override
    public final BrpSamengesteldeNaamInhoud mapInhoud(final MetaRecord identiteitsRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpSamengesteldeNaamInhoud(
                BrpMetaAttribuutMapper.mapBrpPredicaatCode(
                        record.getAttribuut(predicaatCode),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), predicaatCode, true)),
                BrpMetaAttribuutMapper
                        .mapBrpString(record.getAttribuut(voornamen), onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), voornamen, true)),
                BrpMetaAttribuutMapper
                        .mapBrpString(record.getAttribuut(voorvoegsel), onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), voorvoegsel, true)),
                BrpMetaAttribuutMapper.mapBrpCharacterScheidingsteken(
                        record.getAttribuut(scheidingsteken),
                        record.getAttribuut(voorvoegsel),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), scheidingsteken, true)),
                BrpMetaAttribuutMapper.mapBrpAdellijkeTitelCode(
                        record.getAttribuut(adellijkeTitelCode),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), adellijkeTitelCode, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(geslachtsnaamStam),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), geslachtsnaamStam, true)),
                BrpMetaAttribuutMapper.mapBrpBooleanJaNee(
                        record.getAttribuut(indicatieNamenReeks),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), indicatieNamenReeks, true)),
                BrpMetaAttribuutMapper.mapBrpBooleanJaNee(
                        record.getAttribuut(indicatieAfgeleid),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), indicatieAfgeleid, true)));
    }
}
