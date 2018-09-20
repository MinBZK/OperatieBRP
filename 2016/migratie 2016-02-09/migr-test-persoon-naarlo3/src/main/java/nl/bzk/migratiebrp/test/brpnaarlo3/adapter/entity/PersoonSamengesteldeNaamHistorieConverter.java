/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import java.util.Set;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdellijkeTitel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Predicaat;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import org.springframework.stereotype.Component;

/**
 * SamengesteldeNaam converter.
 */
@Component
public final class PersoonSamengesteldeNaamHistorieConverter extends AbstractEntityHistorieConverter<PersoonSamengesteldeNaamHistorie> {
    private static final String HEADER_TYPE = "kern.his_perssamengesteldenaam";
    private static final String HEADER_INDICATIE_AFGELEID = "indafgeleid";
    private static final String HEADER_INDICATIE_NAMENREEKS = "indnreeks";
    private static final String HEADER_PREDICAAT = "predicaat";
    private static final String HEADER_VOORNAMEN = "voornamen";
    private static final String HEADER_ADELLIJKE_TITEL = "adellijketitel";
    private static final String HEADER_VOORVOEGSEL = "voorvoegsel";
    private static final String HEADER_SCHEIDINGSTEKEN = "scheidingsteken";
    private static final String HEADER_GESLACHTSNAAMSTAM = "geslnaamstam";

    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private String geslachtsnaamstam;
    private Boolean indicatieAlgoritmischAfgeleid;
    private Boolean indicatieNamenreeks;
    private Character scheidingsteken;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private String voornamen;
    private String voorvoegsel;
    private BRPActie actieAanpassingGeldigheid;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private AdellijkeTitel adellijkeTitel;
    private Persoon persoon;
    private Predicaat predicaat;

    /**
     * Default constructor.
     */
    public PersoonSamengesteldeNaamHistorieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                break;
            case HEADER_PERSOON:
                persoon = context.getPersoon(Integer.parseInt(value));
                break;
            case HEADER_INDICATIE_AFGELEID:
                indicatieAlgoritmischAfgeleid = Boolean.valueOf(value);
                break;
            case HEADER_INDICATIE_NAMENREEKS:
                indicatieNamenreeks = Boolean.valueOf(value);
                break;
            case HEADER_PREDICAAT:
                predicaat = Predicaat.parseId(Short.valueOf(value));
                break;
            case HEADER_VOORNAMEN:
                voornamen = value;
                break;
            case HEADER_ADELLIJKE_TITEL:
                adellijkeTitel = AdellijkeTitel.parseId(Short.valueOf(value));
                break;
            case HEADER_VOORVOEGSEL:
                voorvoegsel = value;
                break;
            case HEADER_SCHEIDINGSTEKEN:
                scheidingsteken = isEmpty(value) ? null : value.charAt(0);
                break;
            case HEADER_GESLACHTSNAAMSTAM:
                geslachtsnaamstam = value;
                break;
            case HEADER_DATUM_AANVANG_GELDIGHEID:
                datumAanvangGeldigheid = Integer.valueOf(value);
                break;
            case HEADER_DATUM_EINDE_GELDIGHEID:
                datumEindeGeldigheid = Integer.valueOf(value);
                break;
            case HEADER_TIJDSTIP_REGISTRATIE:
                datumTijdRegistratie = maakTimestamp(value);
                break;
            case HEADER_TIJDSTIP_VERVAL:
                datumTijdVerval = maakTimestamp(value);
                break;
            case HEADER_ACTIE_INHOUD:
                actieInhoud = context.getActie(Integer.parseInt(value));
                break;
            case HEADER_ACTIE_VERVAL:
                actieVerval = context.getActie(Integer.parseInt(value));
                break;
            case HEADER_ACTIE_AANPASSING_GELDIGHEID:
                actieAanpassingGeldigheid = context.getActie(Integer.parseInt(value));
                break;
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonSamengesteldeNaamHistorie historie =
                new PersoonSamengesteldeNaamHistorie(persoon, geslachtsnaamstam, indicatieAlgoritmischAfgeleid, indicatieNamenreeks);
        historie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        if (adellijkeTitel != null) {
            historie.setAdellijkeTitel(adellijkeTitel);
        }
        historie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        historie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);
        if (predicaat != null) {
            historie.setPredicaat(predicaat);
        }
        historie.setScheidingsteken(scheidingsteken);
        historie.setVoornamen(voornamen);
        historie.setVoorvoegsel(voorvoegsel);

        persoon.addPersoonSamengesteldeNaamHistorie(historie);
    }

    @Override
    protected void resetConverter() {
        datumAanvangGeldigheid = null;
        datumEindeGeldigheid = null;
        geslachtsnaamstam = null;
        indicatieAlgoritmischAfgeleid = null;
        indicatieNamenreeks = null;
        scheidingsteken = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        voornamen = null;
        voorvoegsel = null;
        actieAanpassingGeldigheid = null;
        actieVerval = null;
        actieInhoud = null;
        adellijkeTitel = null;
        persoon = null;
        predicaat = null;
    }

    @Override
    protected void vulActueelLaag() {
        final Set<PersoonSamengesteldeNaamHistorie> historieSet = persoon.getPersoonSamengesteldeNaamHistorieSet();
        vulActueelVanuit(persoon, getActueel(historieSet));
    }
}
