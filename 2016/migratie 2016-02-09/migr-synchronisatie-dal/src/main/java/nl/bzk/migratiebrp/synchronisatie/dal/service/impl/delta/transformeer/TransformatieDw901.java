/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import java.util.Collections;
import java.util.List;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractFormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.SleutelUtil;

/**
 * Tranformatie aanpassingen aan groepen met historie uit Cat 07 en Cat 13, waarbij alleen de datum-tijd registratie is
 * aangepast. Voor deze gevallen worden de aanpassingen niet doorgevoerd, en wordt een lege VerschilGroep opgeleverd.
 */
public final class TransformatieDw901 extends AbstractTransformatie {

    private static final int INT_2 = 2;
    private static final int INT_3 = 3;

    /**
     * @param verschillen
     *            de lijst met verschillen
     * @return true als de verschillen een tsreg en actie inhoud verschil hebben afkomstig van een datumtijdstempel
     *         wijziging, anders false
     */
    static boolean isCorrectVerschilPaar(final VerschilGroep verschillen) {
        final Verschil historieTsRegVerschil = zoekHistorieTsRegVerschil(verschillen);

        final boolean isCorrectHistorieVerschil = historieTsRegVerschil != null && isHistorieDatumTijdRegistratieVerschil(historieTsRegVerschil);

        return isCorrectHistorieVerschil && isCorrectActieVerschil(verschillen);
    }

    private static boolean isCorrectVerschilPaarInclusiefDocumentatieHistorie(final VerschilGroep verschillen) {
        final Verschil historieTsRegVerschil = zoekHistorieTsRegVerschil(verschillen);
        final Verschil documentHistorieVerschil = zoekDocumentHistorieVerschil(verschillen);

        final boolean isCorrectHistorieVerschil = historieTsRegVerschil != null && isHistorieDatumTijdRegistratieVerschil(historieTsRegVerschil);
        final boolean isCorrectDocumentHistorieVerschil =
                documentHistorieVerschil != null && isDocumentHistorieDatumTijdRegistratieVerschil(documentHistorieVerschil);

        return isCorrectHistorieVerschil && isCorrectActieVerschil(verschillen) && isCorrectDocumentHistorieVerschil;
    }

    private static boolean isHistorieDatumTijdRegistratieVerschil(final Verschil verschil) {
        return VerschilType.ELEMENT_AANGEPAST.equals(verschil.getVerschilType())
               && AbstractFormeleHistorie.DATUM_TIJD_REGISTRATIE.equals(verschil.getSleutel().getVeld())
               && SleutelUtil.isBrpGroepMetTsRegAfkomstigUitDatumTijdStempel(
                   verschil.getBestaandeHistorieRij(),
                   ((EntiteitSleutel) verschil.getSleutel()).getEigenaarSleutel());
    }

    private static boolean isDocumentHistorieDatumTijdRegistratieVerschil(final Verschil verschil) {
        return VerschilType.ELEMENT_AANGEPAST.equals(verschil.getVerschilType())
               && AbstractFormeleHistorie.DATUM_TIJD_REGISTRATIE.equals(verschil.getSleutel().getVeld())
               && DocumentHistorie.class.isAssignableFrom(((EntiteitSleutel) verschil.getSleutel()).getEntiteit());
    }

    private static Verschil zoekHistorieTsRegVerschil(final VerschilGroep verschillen) {
        for (final Verschil verschil : verschillen) {
            if (AbstractFormeleHistorie.DATUM_TIJD_REGISTRATIE.equals(verschil.getSleutel().getVeld())
                && SleutelUtil.isBrpGroepMetTsRegAfkomstigUitDatumTijdStempel(
                    verschil.getBestaandeHistorieRij(),
                    ((EntiteitSleutel) verschil.getSleutel()).getEigenaarSleutel()))
            {
                return verschil;
            }
        }
        return null;
    }

    private static boolean isCorrectActieVerschil(final VerschilGroep verschillen) {
        for (final Verschil verschil : verschillen) {
            if (AbstractFormeleHistorie.ACTIE_INHOUD.equals(verschil.getSleutel().getVeld())
                && VerschilType.ELEMENT_AANGEPAST.equals(verschil.getVerschilType()))
            {
                return true;
            }
        }
        return false;
    }

    private static Verschil zoekDocumentHistorieVerschil(final VerschilGroep verschillen) {
        for (final Verschil verschil : verschillen) {
            if (DocumentHistorie.class.isAssignableFrom(((EntiteitSleutel) verschil.getSleutel()).getEntiteit())) {
                return verschil;
            }
        }
        return null;
    }

    @Override
    public boolean accept(final VerschilGroep verschillen) {
        return verschillen.size() == INT_2 && TransformatieDw901.isCorrectVerschilPaar(verschillen)
               || verschillen.size() == INT_3 && TransformatieDw901.isCorrectVerschilPaarInclusiefDocumentatieHistorie(verschillen);
    }

    @Override
    public VerschilGroep execute(
        final VerschilGroep verschilGroep,
        final BRPActie actieVervalTbvLeveringMuts,
        final DeltaBepalingContext deltaBepalingContext)
    {
        final List<Verschil> geenVerschillen = Collections.emptyList();
        final VerschilGroep kopieVerschilGroep = VerschilGroep.maakKopieZonderVerschillen(verschilGroep);
        kopieVerschilGroep.addVerschillen(geenVerschillen);
        return kopieVerschilGroep;
    }

    @Override
    public DeltaWijziging getDeltaWijziging() {
        return DeltaWijziging.DW_901;
    }
}
