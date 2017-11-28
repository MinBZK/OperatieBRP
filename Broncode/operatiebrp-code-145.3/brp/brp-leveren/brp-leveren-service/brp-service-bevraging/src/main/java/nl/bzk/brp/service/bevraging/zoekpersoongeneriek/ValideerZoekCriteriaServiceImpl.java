/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.ElementBasisType;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.service.algemeen.request.DatumService;
import nl.bzk.brp.service.bevraging.algemeen.PeilmomentValidatieService;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVerzoek;
import nl.bzk.brp.service.cache.GeldigeAttributenElementenCache;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

/**
 * ValideerZoekCriteriaServiceImpl.
 */
@Bedrijfsregel(Regel.R2265)
@Bedrijfsregel(Regel.R2266)
@Bedrijfsregel(Regel.R2267)
@Bedrijfsregel(Regel.R2281)
@Bedrijfsregel(Regel.R2308)
@Bedrijfsregel(Regel.R2311)
@Bedrijfsregel(Regel.R2389)
@Bedrijfsregel(Regel.R2400)
@Bedrijfsregel(Regel.R2541)
@Bedrijfsregel(Regel.R2542)
@Bedrijfsregel(Regel.R2610)
@Service
final class ValideerZoekCriteriaServiceImpl implements ValideerZoekCriteriaService<ZoekPersoonGeneriekVerzoek> {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String FOUT_MELDING = "%s overtreding voor %s";

    private static final EnumSet<SoortElementAutorisatie> TOEGESTANE_SRT_ELEMENTAUTORISATIES = EnumSet.of(SoortElementAutorisatie.AANBEVOLEN,
            SoortElementAutorisatie.VERPLICHT, SoortElementAutorisatie.BIJHOUDINGSGEGEVENS, SoortElementAutorisatie.OPTIONEEL);

    @Inject
    private DatumService datumService;
    @Inject
    private GeldigeAttributenElementenCache geldigeAttributenElementenCache;
    @Inject
    private PeilmomentValidatieService peilmomentValidatieService;

    private ValideerZoekCriteriaServiceImpl() {

    }

    /**
     * @param bevragingVerzoek bevragingVerzoek
     * @param autorisatiebundel autorisatiebundel
     * @return meldingen
     */
    @Override
    public Set<Melding> valideerZoekCriteria(final ZoekPersoonGeneriekVerzoek bevragingVerzoek, final Autorisatiebundel autorisatiebundel) {
        final Set<Melding> meldingen = new HashSet<>();
        valideerGeldigeAttribuutElementen(bevragingVerzoek, meldingen);
        if (meldingen.isEmpty()) {
            //alleen verder valideren als geldige attribuut elementen
            final ZoekPersoonVerzoek.ZoekBereikParameters zoekBereikParameters = bevragingVerzoek.getParameters().getZoekBereikParameters();
            final boolean historisch = zoekBereikParameters != null && (zoekBereikParameters.getPeilmomentMaterieel() != null
                    || Zoekbereik.MATERIELE_PERIODE.equals(zoekBereikParameters.getZoekBereik()));
            final Set<AttribuutElement> geautoriseerdeElementen = geefGeautoriseerdeElementen(autorisatiebundel);
            final List<ZoekPersoonGeneriekVerzoek.ZoekCriteria> zoekCriteriaPlat = slaPlat(bevragingVerzoek.getZoekCriteriaPersoon());
            for (ZoekPersoonVerzoek.ZoekCriteria zoekCriterium : zoekCriteriaPlat) {
                if (historisch) {
                    valideerMaterieelPeilmoment(bevragingVerzoek, meldingen);
                }
                valideerZoekCriteriumLeegOptie(zoekCriterium, meldingen);
                valideerZoekOptie(zoekCriterium, meldingen);
                if (autorisatiebundel.getLeveringsautorisatie().getStelsel() == Stelsel.BRP) {
                    valideerAutorisatie(zoekCriterium, geautoriseerdeElementen, meldingen);
                }
                valideerZoekWaarde(zoekCriterium, meldingen);
            }
        }
        return meldingen;
    }

    private void valideerMaterieelPeilmoment(final ZoekPersoonGeneriekVerzoek bevragingVerzoek, final Set<Melding> meldingen) {
        try {
            peilmomentValidatieService.valideerMaterieel(bevragingVerzoek.getParameters().getZoekBereikParameters().getPeilmomentMaterieel());
        } catch (StapMeldingException e) {
            LOGGER.debug("materieel peilmoment niet geldig", e);
            meldingen.addAll(e.getMeldingen());
        }
    }

    private List<ZoekPersoonGeneriekVerzoek.ZoekCriteria> slaPlat(final Set<ZoekPersoonGeneriekVerzoek.ZoekCriteria> zoekCriteria) {
        final List<ZoekPersoonGeneriekVerzoek.ZoekCriteria> zoekCriteriaPlat = new ArrayList<>();
        for (ZoekPersoonGeneriekVerzoek.ZoekCriteria criterium : zoekCriteria) {
            voegOfToe(criterium, zoekCriteriaPlat);
        }

        return zoekCriteriaPlat;
    }

    private void voegOfToe(final ZoekPersoonGeneriekVerzoek.ZoekCriteria criterium, final List<ZoekPersoonGeneriekVerzoek.ZoekCriteria> zoekCriteriaPlat) {
        if (criterium == null) {
            return;
        }
        zoekCriteriaPlat.add(criterium);
        voegOfToe(criterium.getOf(), zoekCriteriaPlat);
    }


    private void valideerGeldigeAttribuutElementen(final ZoekPersoonGeneriekVerzoek bevragingVerzoek, final Set<Melding> meldingen) {

        final BiConsumer<Regel, ZoekPersoonGeneriekVerzoek.ZoekCriteria> meldingAdder = (regel, zoekCriteria) -> {
            LOGGER.debug(String.format(FOUT_MELDING, regel.toString(), zoekCriteria.getElementNaam()));
            meldingen.add(new Melding(regel));
        };

        for (final ZoekPersoonVerzoek.ZoekCriteria zoekCriterium : bevragingVerzoek.getZoekCriteriaPersoon()) {
            try {
                final ElementObject elementObject = ElementHelper.getElement(zoekCriterium.getElementNaam());
                if (!(elementObject instanceof AttribuutElement)) {
                    meldingAdder.accept(Regel.R2265, zoekCriterium);
                } else if (isAttribuutUitOnderzoeksOfVerantwoordingsgroep((AttribuutElement) elementObject)) {
                    meldingAdder.accept(Regel.R2389, zoekCriterium);
                } else if (!isOpvraagbaarAttribuut((AttribuutElement) elementObject)) {
                    meldingAdder.accept(Regel.R2542, zoekCriterium);
                } else if (!isGeldigAttribuut((AttribuutElement) elementObject)) {
                    meldingAdder.accept(Regel.R2541, zoekCriterium);
                } else if (elementObject.getElement().getElementNaam().equals(AttribuutElement.DATUM_AANVANG_GELDIGHEID)) {
                    meldingAdder.accept(Regel.R2610, zoekCriterium);
                }
                if (!meldingen.isEmpty()) {
                    break;
                }
            } catch (IllegalStateException e) {
                LOGGER.debug("geen geldig attribuut gevonden in zoekcriteria", e);
                meldingen.add(new Melding(Regel.R2265));
            }
        }

    }

    //valideert R2541
    private boolean isGeldigAttribuut(final AttribuutElement attribuutElement) {
        return geldigeAttributenElementenCache.geldigVoorAttribuutAutorisatie(attribuutElement);
    }

    //valideert R2542
    private boolean isOpvraagbaarAttribuut(final AttribuutElement attribuutElement) {
        return TOEGESTANE_SRT_ELEMENTAUTORISATIES.contains(attribuutElement.getAutorisatie())
                && "kern".equalsIgnoreCase(attribuutElement.getElement().getElementWaarde().getIdentdbschema());
    }

    //valideert R2389
    private boolean isAttribuutUitOnderzoeksOfVerantwoordingsgroep(final AttribuutElement attribuutElement) {
        return attribuutElement.getGroep().isOnderzoeksgroep() || attribuutElement.getGroep().isVerantwoordingsgroep();
    }

    private void valideerZoekWaarde(final ZoekPersoonVerzoek.ZoekCriteria zoekCriterium, final Set<Melding> meldingen) {
        if (zoekCriterium.getWaarde() != null) {
            final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(zoekCriterium.getElementNaam());
            if (attribuutElement.getMaximumLengte() != null && zoekCriterium.getWaarde().length() > attribuutElement.getMaximumLengte()) {
                LOGGER.debug(String.format(FOUT_MELDING, Regel.R2311.toString(), zoekCriterium.getElementNaam()));
                meldingen.add(new Melding(Regel.R2311));
            }
            if (!attribuutElement.isStamgegevenReferentie() && attribuutElement.getDatatype() != ElementBasisType.DATUM) {
                //stamgegeven waarde wordt gevalideerd in ZoekCriteriaConverteerServiceImpl.
                //DATUM waarde wordt ivm deels onbekende datum gevalideerd in ZoekCriteriaConverteerServiceImpl.
                valideerWaardeEnDataTypeKomenOvereen(attribuutElement, zoekCriterium.getWaarde(), meldingen);
            }
        }
    }

    private void valideerWaardeEnDataTypeKomenOvereen(final AttribuutElement attribuutElement, final String waarde, final Set<Melding> meldingen) {
        final ElementBasisType datatype = attribuutElement.getDatatype();
        boolean logMelding = false;
        if (datatype == ElementBasisType.DATUMTIJD) {
            try {
                datumService.parseDateTime(waarde);
            } catch (StapMeldingException e) {
                LOGGER.debug("datum tijd in zoekcriteria niet geldig", e);
                logMelding = true;
            }
        } else if (datatype == ElementBasisType.BOOLEAN) {
            if (!"n".equalsIgnoreCase(waarde) && !"j".equalsIgnoreCase(waarde)) {
                logMelding = true;
            }
        } else if (attribuutElement.isGetal() && !NumberUtils.isDigits(waarde)) {
            logMelding = true;
        }

        if (logMelding) {
            LOGGER.debug(String.format(FOUT_MELDING, Regel.R2308.toString(), attribuutElement.getElementNaam()));
            meldingen.add(new Melding(Regel.R2308));
        }
    }

    private void valideerZoekCriteriumLeegOptie(final ZoekPersoonVerzoek.ZoekCriteria zoekCriterium, final Set<Melding> meldingen) {
        if (Zoekoptie.LEEG.equals(zoekCriterium.getZoekOptie()) && StringUtils.isNotEmpty(zoekCriterium.getWaarde())) {
            LOGGER.debug(String.format(FOUT_MELDING, Regel.R2266.toString(), zoekCriterium.getElementNaam()));
            meldingen.add(new Melding(Regel.R2266));
        }
        if (!Zoekoptie.LEEG.equals(zoekCriterium.getZoekOptie()) && StringUtils.isEmpty(zoekCriterium.getWaarde())) {
            LOGGER.debug(String.format(FOUT_MELDING, Regel.R2267.toString(), zoekCriterium.getElementNaam()));
            meldingen.add(new Melding(Regel.R2267));
        }
    }

    private void valideerZoekOptie(final ZoekPersoonVerzoek.ZoekCriteria zoekCriterium, final Set<Melding> meldingen) {
        final AttribuutElement attribuut = ElementHelper.getAttribuutElement(zoekCriterium.getElementNaam());
        //controleer vanaf en klein
        final BooleanSupplier valideerKleinZoeken = () -> !Zoekoptie.KLEIN.equals(zoekCriterium.getZoekOptie()) || attribuut.isString();
        final BooleanSupplier
                valideerVanafZoeken =
                () -> !(Zoekoptie.VANAF_KLEIN.equals(zoekCriterium.getZoekOptie()) || Zoekoptie.VANAF_EXACT.equals(zoekCriterium.getZoekOptie())) || attribuut
                        .isString() || attribuut.isDatum();
        if (!(valideerKleinZoeken.getAsBoolean() && valideerVanafZoeken.getAsBoolean())) {
            LOGGER.debug(String.format(FOUT_MELDING, Regel.R2281.toString(), zoekCriterium.getElementNaam()));
            meldingen.add(new Melding(Regel.R2281));
        }
    }


    private void valideerAutorisatie(final ZoekPersoonVerzoek.ZoekCriteria zoekCriterium, final Set<AttribuutElement> geautoriseerdeElementen,
                                     final Set<Melding> meldingen) {
        //Alle Elementen die je binnen je Autorisatie hebt
        // waarvan de Element.Autorisatie is: Optioneel, Verplicht, Aanbevolen of Uitzondering.
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(zoekCriterium.getElementNaam());
        if (attribuutElement.getAutorisatie() == null || !geautoriseerdeElementen.contains(attribuutElement)) {
            LOGGER.debug(String.format(FOUT_MELDING, Regel.R2290.toString(), zoekCriterium.getElementNaam()));
            meldingen.add(new Melding(Regel.R2290));
        }
    }

    private Set<AttribuutElement> geefGeautoriseerdeElementen(final Autorisatiebundel autorisatiebundel) {
        final Set<AttribuutElement> geautoriseerdeElementen = new HashSet<>();
        final Set<DienstbundelGroep> dienstbundelGroepen = autorisatiebundel.getDienst().getDienstbundel().getDienstbundelGroepSet();
        if (dienstbundelGroepen != null) {
            for (final DienstbundelGroep dienstbundelGroep : dienstbundelGroepen) {
                voegGeautoriseerdeElementenToe(dienstbundelGroep, geautoriseerdeElementen);
            }
        }
        return geautoriseerdeElementen;
    }

    private void voegGeautoriseerdeElementenToe(final DienstbundelGroep dienstbundelGroep, final Set<AttribuutElement> geautoriseerdeElementen) {
        if (dienstbundelGroep.getDienstbundelGroepAttribuutSet() != null) {
            for (final DienstbundelGroepAttribuut attribuutModel : dienstbundelGroep.getDienstbundelGroepAttribuutSet()) {
                final AttribuutElement element = ElementHelper.getAttribuutElement(attribuutModel.getAttribuut().getId());
                geautoriseerdeElementen.add(element);
            }
        }
    }

}
