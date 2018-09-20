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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Naamgebruik;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNaamgebruikHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Predicaat;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import org.springframework.stereotype.Component;

/**
 * Naamgebruik converter.
 */
@Component
public final class PersoonNaamgebruikHistorieConverter extends AbstractEntityHistorieConverter<PersoonNaamgebruikHistorie> {
    private static final String HEADER_TYPE = "kern.his_persnaamgebruik";
    private static final String HEADER_WIJZE_GEBRUIK_GESLACHTSNAAM = "naamgebruik";
    private static final String HEADER_INDICATIE_NAAMGEBRUIK_AFGELEID = "indnaamgebruikafgeleid";
    private static final String HEADER_PREDICAAT_NAAMGEBRUIK = "predicaatnaamgebruik";
    private static final String HEADER_VOORNAMEN_NAAMGEBRUIK = "voornamennaamgebruik";
    private static final String HEADER_ADELLIJKETITEL_NAAMGEBRUIK = "adellijketitelnaamgebruik";
    private static final String HEADER_VOORVOEGSEL_NAAMGEBRUIK = "voorvoegselnaamgebruik";
    private static final String HEADER_SCHEIDINGSTEKEN_NAAMGEBRUIK = "scheidingstekennaamgebruik";
    private static final String HEADER_GESLACHTSNAAM_NAAMGEBRUIK = "geslnaamstamnaamgebruik";

    private String geslachtsnaamNaamgebruik;
    private Boolean indicatieNaamgebruikAfgeleid;
    private Character scheidingstekenNaamgebruik;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private String voornamenNaamgebruik;
    private String voorvoegselNaamgebruik;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private Persoon persoon;
    private Predicaat predikaat;
    private AdellijkeTitel adellijkeTitel;
    private Naamgebruik naamgebruik;

    /**
     * Default constructor.
     */
    public PersoonNaamgebruikHistorieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void resetConverter() {
        persoon = null;
        geslachtsnaamNaamgebruik = null;
        indicatieNaamgebruikAfgeleid = null;
        naamgebruik = null;
        scheidingstekenNaamgebruik = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        voornamenNaamgebruik = null;
        voorvoegselNaamgebruik = null;
        actieVerval = null;
        actieInhoud = null;
        predikaat = null;
        adellijkeTitel = null;
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonNaamgebruikHistorie persoonNaamgebruikHistorie =
                new PersoonNaamgebruikHistorie(persoon, geslachtsnaamNaamgebruik, indicatieNaamgebruikAfgeleid, naamgebruik);
        persoonNaamgebruikHistorie.setScheidingstekenNaamgebruik(scheidingstekenNaamgebruik);
        persoonNaamgebruikHistorie.setDatumTijdRegistratie(datumTijdRegistratie);
        persoonNaamgebruikHistorie.setDatumTijdVerval(datumTijdVerval);
        persoonNaamgebruikHistorie.setVoornamenNaamgebruik(voornamenNaamgebruik);
        persoonNaamgebruikHistorie.setVoorvoegselNaamgebruik(voorvoegselNaamgebruik);
        persoonNaamgebruikHistorie.setActieVerval(actieVerval);
        persoonNaamgebruikHistorie.setActieInhoud(actieInhoud);
        persoonNaamgebruikHistorie.setPredicaat(predikaat);
        persoonNaamgebruikHistorie.setAdellijkeTitel(adellijkeTitel);
        persoon.addPersoonNaamgebruikHistorie(persoonNaamgebruikHistorie);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                break;
            case HEADER_PERSOON:
                persoon = context.getPersoon(Integer.parseInt(value));
                break;
            case HEADER_WIJZE_GEBRUIK_GESLACHTSNAAM:
                naamgebruik = Naamgebruik.parseId(Short.valueOf(value));
                break;
            case HEADER_INDICATIE_NAAMGEBRUIK_AFGELEID:
                indicatieNaamgebruikAfgeleid = Boolean.valueOf(value);
                break;
            case HEADER_PREDICAAT_NAAMGEBRUIK:
                predikaat = Predicaat.parseId(Short.valueOf(value));
                break;
            case HEADER_VOORNAMEN_NAAMGEBRUIK:
                voornamenNaamgebruik = value;
                break;
            case HEADER_ADELLIJKETITEL_NAAMGEBRUIK:
                adellijkeTitel = AdellijkeTitel.parseId(Short.valueOf(value));
                break;
            case HEADER_VOORVOEGSEL_NAAMGEBRUIK:
                voorvoegselNaamgebruik = value;
                break;
            case HEADER_SCHEIDINGSTEKEN_NAAMGEBRUIK:
                scheidingstekenNaamgebruik = value == null || value.isEmpty() ? null : value.charAt(0);
                break;
            case HEADER_GESLACHTSNAAM_NAAMGEBRUIK:
                geslachtsnaamNaamgebruik = value;
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
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }

    @Override
    protected void vulActueelLaag() {
        final Set<PersoonNaamgebruikHistorie> historieSet = persoon.getPersoonNaamgebruikHistorieSet();
        vulActueelVanuit(persoon, getActueel(historieSet));
    }
}
