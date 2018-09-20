/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de samengestelde naam.
 */
@Component
public abstract class AbstractSamengesteldeNaamMapper
        extends AbstractMaterieelMapper<PersoonHisVolledig, HisPersoonSamengesteldeNaamModel, BrpSamengesteldeNaamInhoud>
{
    private final ElementEnum predicaatCode;
    private final ElementEnum voornamen;
    private final ElementEnum voorvoegsel;
    private final ElementEnum scheidingsteken;
    private final ElementEnum adellijkeTitelCode;
    private final ElementEnum geslachtsnaamStam;
    private final ElementEnum indicatieNamenReeks;
    private final ElementEnum indicatieAfgeleid;

    /**
     * Constructor.
     * 
     * @param datumAanvangGeldigheid
     *            element voor datum aanvang geldigheid
     * @param datumEindeGeldigheid
     *            element voor datum einde geldigheid
     * @param tijdstipRegistratie
     *            element voor tijdstip registratie
     * @param tijdstipVerval
     *            element voor tijdstip verval
     * @param predicaatCode
     *            element voor predicaat
     * @param voornamen
     *            element voor voornamen
     * @param voorvoegsel
     *            element voor voorvoegsel
     * @param scheidingsteken
     *            element voor scheidingsteken
     * @param adellijkeTitelCode
     *            element voor adellijke titel
     * @param geslachtsnaamStam
     *            element voor geslachtsnaam
     * @param indicatieNamenReeks
     *            element voor indicatie namen reeks
     * @param indicatieAfgeleid
     *            element voor indicatie afgeleid
     */
    protected AbstractSamengesteldeNaamMapper(
        final ElementEnum datumAanvangGeldigheid,
        final ElementEnum datumEindeGeldigheid,
        final ElementEnum tijdstipRegistratie,
        final ElementEnum tijdstipVerval,
        final ElementEnum predicaatCode,
        final ElementEnum voornamen,
        final ElementEnum voorvoegsel,
        final ElementEnum scheidingsteken,
        final ElementEnum adellijkeTitelCode,
        final ElementEnum geslachtsnaamStam,
        final ElementEnum indicatieNamenReeks,
        final ElementEnum indicatieAfgeleid)
    {
        super(datumAanvangGeldigheid, datumEindeGeldigheid, tijdstipRegistratie, tijdstipVerval);
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
    protected final Iterable<HisPersoonSamengesteldeNaamModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonSamengesteldeNaamHistorie();
    }

    @Override
    public final BrpSamengesteldeNaamInhoud mapInhoud(final HisPersoonSamengesteldeNaamModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpSamengesteldeNaamInhoud(
            BrpMapperUtil.mapBrpPredicaatCode(historie.getPredicaat(), onderzoekMapper.bepaalOnderzoek(historie.getID(), predicaatCode, true)),
            BrpMapperUtil.mapBrpString(historie.getVoornamen(), onderzoekMapper.bepaalOnderzoek(historie.getID(), voornamen, true)),
            BrpMapperUtil.mapBrpString(historie.getVoorvoegsel(), onderzoekMapper.bepaalOnderzoek(historie.getID(), voorvoegsel, true)),
            BrpMapperUtil.mapBrpCharacter(historie.getScheidingsteken(), onderzoekMapper.bepaalOnderzoek(historie.getID(), scheidingsteken, true)),
            BrpMapperUtil.mapBrpAdellijkeTitelCode(
                historie.getAdellijkeTitel(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), adellijkeTitelCode, true)),
            BrpMapperUtil.mapBrpString(historie.getGeslachtsnaamstam(), onderzoekMapper.bepaalOnderzoek(historie.getID(), geslachtsnaamStam, true)),
            BrpMapperUtil.mapBrpBoolean(historie.getIndicatieNamenreeks(), onderzoekMapper.bepaalOnderzoek(historie.getID(), indicatieNamenReeks, true)),
            BrpMapperUtil.mapBrpBoolean(historie.getIndicatieAfgeleid(), onderzoekMapper.bepaalOnderzoek(historie.getID(), indicatieAfgeleid, true)));
    }
}
