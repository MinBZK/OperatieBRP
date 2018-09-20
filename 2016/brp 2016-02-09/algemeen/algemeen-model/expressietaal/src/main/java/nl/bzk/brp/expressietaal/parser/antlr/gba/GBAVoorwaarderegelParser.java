/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

// Generated from GBAVoorwaarderegel.g4 by ANTLR 4.1

package nl.bzk.brp.expressietaal.parser.antlr.gba;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GBAVoorwaarderegelParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__426=1, T__425=2, T__424=3, T__423=4, T__422=5, T__421=6, T__420=7, 
		T__419=8, T__418=9, T__417=10, T__416=11, T__415=12, T__414=13, T__413=14, 
		T__412=15, T__411=16, T__410=17, T__409=18, T__408=19, T__407=20, T__406=21, 
		T__405=22, T__404=23, T__403=24, T__402=25, T__401=26, T__400=27, T__399=28, 
		T__398=29, T__397=30, T__396=31, T__395=32, T__394=33, T__393=34, T__392=35, 
		T__391=36, T__390=37, T__389=38, T__388=39, T__387=40, T__386=41, T__385=42, 
		T__384=43, T__383=44, T__382=45, T__381=46, T__380=47, T__379=48, T__378=49, 
		T__377=50, T__376=51, T__375=52, T__374=53, T__373=54, T__372=55, T__371=56, 
		T__370=57, T__369=58, T__368=59, T__367=60, T__366=61, T__365=62, T__364=63, 
		T__363=64, T__362=65, T__361=66, T__360=67, T__359=68, T__358=69, T__357=70, 
		T__356=71, T__355=72, T__354=73, T__353=74, T__352=75, T__351=76, T__350=77, 
		T__349=78, T__348=79, T__347=80, T__346=81, T__345=82, T__344=83, T__343=84, 
		T__342=85, T__341=86, T__340=87, T__339=88, T__338=89, T__337=90, T__336=91, 
		T__335=92, T__334=93, T__333=94, T__332=95, T__331=96, T__330=97, T__329=98, 
		T__328=99, T__327=100, T__326=101, T__325=102, T__324=103, T__323=104, 
		T__322=105, T__321=106, T__320=107, T__319=108, T__318=109, T__317=110, 
		T__316=111, T__315=112, T__314=113, T__313=114, T__312=115, T__311=116, 
		T__310=117, T__309=118, T__308=119, T__307=120, T__306=121, T__305=122, 
		T__304=123, T__303=124, T__302=125, T__301=126, T__300=127, T__299=128, 
		T__298=129, T__297=130, T__296=131, T__295=132, T__294=133, T__293=134, 
		T__292=135, T__291=136, T__290=137, T__289=138, T__288=139, T__287=140, 
		T__286=141, T__285=142, T__284=143, T__283=144, T__282=145, T__281=146, 
		T__280=147, T__279=148, T__278=149, T__277=150, T__276=151, T__275=152, 
		T__274=153, T__273=154, T__272=155, T__271=156, T__270=157, T__269=158, 
		T__268=159, T__267=160, T__266=161, T__265=162, T__264=163, T__263=164, 
		T__262=165, T__261=166, T__260=167, T__259=168, T__258=169, T__257=170, 
		T__256=171, T__255=172, T__254=173, T__253=174, T__252=175, T__251=176, 
		T__250=177, T__249=178, T__248=179, T__247=180, T__246=181, T__245=182, 
		T__244=183, T__243=184, T__242=185, T__241=186, T__240=187, T__239=188, 
		T__238=189, T__237=190, T__236=191, T__235=192, T__234=193, T__233=194, 
		T__232=195, T__231=196, T__230=197, T__229=198, T__228=199, T__227=200, 
		T__226=201, T__225=202, T__224=203, T__223=204, T__222=205, T__221=206, 
		T__220=207, T__219=208, T__218=209, T__217=210, T__216=211, T__215=212, 
		T__214=213, T__213=214, T__212=215, T__211=216, T__210=217, T__209=218, 
		T__208=219, T__207=220, T__206=221, T__205=222, T__204=223, T__203=224, 
		T__202=225, T__201=226, T__200=227, T__199=228, T__198=229, T__197=230, 
		T__196=231, T__195=232, T__194=233, T__193=234, T__192=235, T__191=236, 
		T__190=237, T__189=238, T__188=239, T__187=240, T__186=241, T__185=242, 
		T__184=243, T__183=244, T__182=245, T__181=246, T__180=247, T__179=248, 
		T__178=249, T__177=250, T__176=251, T__175=252, T__174=253, T__173=254, 
		T__172=255, T__171=256, T__170=257, T__169=258, T__168=259, T__167=260, 
		T__166=261, T__165=262, T__164=263, T__163=264, T__162=265, T__161=266, 
		T__160=267, T__159=268, T__158=269, T__157=270, T__156=271, T__155=272, 
		T__154=273, T__153=274, T__152=275, T__151=276, T__150=277, T__149=278, 
		T__148=279, T__147=280, T__146=281, T__145=282, T__144=283, T__143=284, 
		T__142=285, T__141=286, T__140=287, T__139=288, T__138=289, T__137=290, 
		T__136=291, T__135=292, T__134=293, T__133=294, T__132=295, T__131=296, 
		T__130=297, T__129=298, T__128=299, T__127=300, T__126=301, T__125=302, 
		T__124=303, T__123=304, T__122=305, T__121=306, T__120=307, T__119=308, 
		T__118=309, T__117=310, T__116=311, T__115=312, T__114=313, T__113=314, 
		T__112=315, T__111=316, T__110=317, T__109=318, T__108=319, T__107=320, 
		T__106=321, T__105=322, T__104=323, T__103=324, T__102=325, T__101=326, 
		T__100=327, T__99=328, T__98=329, T__97=330, T__96=331, T__95=332, T__94=333, 
		T__93=334, T__92=335, T__91=336, T__90=337, T__89=338, T__88=339, T__87=340, 
		T__86=341, T__85=342, T__84=343, T__83=344, T__82=345, T__81=346, T__80=347, 
		T__79=348, T__78=349, T__77=350, T__76=351, T__75=352, T__74=353, T__73=354, 
		T__72=355, T__71=356, T__70=357, T__69=358, T__68=359, T__67=360, T__66=361, 
		T__65=362, T__64=363, T__63=364, T__62=365, T__61=366, T__60=367, T__59=368, 
		T__58=369, T__57=370, T__56=371, T__55=372, T__54=373, T__53=374, T__52=375, 
		T__51=376, T__50=377, T__49=378, T__48=379, T__47=380, T__46=381, T__45=382, 
		T__44=383, T__43=384, T__42=385, T__41=386, T__40=387, T__39=388, T__38=389, 
		T__37=390, T__36=391, T__35=392, T__34=393, T__33=394, T__32=395, T__31=396, 
		T__30=397, T__29=398, T__28=399, T__27=400, T__26=401, T__25=402, T__24=403, 
		T__23=404, T__22=405, T__21=406, T__20=407, T__19=408, T__18=409, T__17=410, 
		T__16=411, T__15=412, T__14=413, T__13=414, T__12=415, T__11=416, T__10=417, 
		T__9=418, T__8=419, T__7=420, T__6=421, T__5=422, T__4=423, T__3=424, 
		T__2=425, T__1=426, T__0=427, OP_GA1=428, OP_GAA=429, OP_OGA1=430, OP_OGAA=431, 
		OP_GD1=432, OP_GDA=433, OP_KD1=434, OP_KDA=435, OP_GDOG1=436, OP_GDOGA=437, 
		OP_KDOG1=438, OP_KDOGA=439, OP_ENVGL=440, OP_OFVGL=441, OP_ENVWD=442, 
		OP_OFVWD=443, OP_KV=444, OP_KNV=445, OP_PLUS=446, OP_MIN=447, CIJFER=448, 
		LETTER=449, SYMBOOL=450, WILDCARD=451, WS=452;
	public static final String[] tokenNames = {
		"<INVALID>", "'01.03.30'", "'56.81.10'", "'12.35.70'", "'02.85.10'", "'12.35.60'", 
		"'11.86.10'", "'01.04.10'", "'59.81.10'", "'59.03.20'", "'52.02.40'", 
		"'04.64.10'", "'12.35.20'", "'09.81.10'", "'09.83.30'", "'55.85.10'", 
		"'04.83.30'", "')'", "'12.85.10'", "'09.83.20'", "'02.83.20'", "'10.39.20'", 
		"'13.38.20'", "'13.38.10'", "'02.83.30'", "'09.83.10'", "'02.82.10'", 
		"'01.88.10'", "'05.01.10'", "'09.02.10'", "'51.83.10'", "'12.82.20'", 
		"'51.83.20'", "'64.40.10'", "'12.82.30'", "'53.02.30'", "'56.86.10'", 
		"'56.83.10'", "'52.81.20'", "'03.86.10'", "'53.02.10'", "'07.67.10'", 
		"'06.82.30'", "'09.02.30'", "'51.01.10'", "'54.85.10'", "'01.01.20'", 
		"'52.83.10'", "'53.03.20'", "'58.75.10'", "'12.35.30'", "'07.69.10'", 
		"'09.81.20'", "'52.02.30'", "'56.08.20'", "'12.35.10'", "'59.03.30'", 
		"'59.81.20'", "'12.83.10'", "'61.83.10'", "'56.08.30'", "'07.87.10'", 
		"'01.86.10'", "'59.85.10'", "'61.32.10'", "'09.86.10'", "'10.39.10'", 
		"'55.86.10'", "'51.82.30'", "'10.39.30'", "'53.02.40'", "'61.33.10'", 
		"'09.85.10'", "'('", "'53.86.10'", "'52.83.20'", "'51.85.10'", "'58.13.30'", 
		"'01.88.20'", "'52.81.10'", "'54.82.10'", "'07.67.20'", "'59.86.10'", 
		"'53.03.30'", "'51.01.20'", "'09.02.40'", "'54.82.20'", "'53.04.10'", 
		"'53.03.10'", "'08.83.30'", "'02.83.10'", "'06.88.10'", "'12.35.80'", 
		"'58.86.10'", "'08.11.90'", "'52.02.10'", "'56.88.20'", "'03.04.10'", 
		"'06.88.20'", "'04.86.10'", "'\"'", "'08.11.80'", "'55.03.10'", "'58.11.40'", 
		"'04.88.20'", "'59.01.10'", "'11.82.30'", "'12.86.10'", "'11.82.20'", 
		"'04.83.10'", "'54.64.10'", "'60.86.10'", "'55.03.20'", "'02.01.20'", 
		"'55.04.10'", "'58.11.60'", "'04.85.10'", "'60.39.10'", "'58.13.40'", 
		"'12.82.10'", "'04.88.10'", "'59.83.20'", "'08.13.40'", "'52.01.20'", 
		"'13.31.10'", "'06.82.10'", "'54.86.10'", "'52.01.10'", "'02.62.10'", 
		"'59.02.30'", "'07.70.10'", "'08.11.15'", "'04.63.10'", "'09.01.20'", 
		"'05.06.20'", "'55.03.30'", "'55.81.20'", "'06.81.20'", "'06.85.10'", 
		"'05.83.30'", "'05.82.10'", "'51.83.30'", "'08.11.10'", "'07.66.20'", 
		"'55.83.10'", "'02.04.10'", "'56.88.10'", "'02.01.10'", "'05.02.30'", 
		"'59.03.10'", "'52.85.10'", "'04.83.20'", "'58.11.50'", "'01.02.30'", 
		"'53.62.10'", "'01.01.10'", "'55.07.30'", "'59.83.10'", "'05.02.10'", 
		"'01.02.40'", "'08.13.50'", "'09.02.20'", "'06.82.20'", "'08.72.10'", 
		"'54.63.10'", "'08.13.30'", "'59.83.30'", "'05.02.20'", "'58.13.50'", 
		"'59.82.30'", "'09.82.10'", "'52.86.10'", "'08.88.20'", "'05.83.20'", 
		"'54.83.20'", "'52.04.10'", "'10.85.10'", "'55.15.10'", "'09.01.10'", 
		"'54.83.10'", "'08.88.10'", "'05.06.30'", "'05.06.10'", "'53.02.20'", 
		"'08.11.70'", "'59.02.10'", "'05.03.30'", "'05.82.20'", "'08.10.10'", 
		"'58.83.30'", "'05.82.30'", "'05.83.10'", "'58.83.10'", "'08.10.30'", 
		"'03.85.10'", "'52.62.10'", "'56.83.30'", "'06.86.10'", "'55.01.10'", 
		"'58.13.10'", "'60.83.30'", "'05.07.40'", "'09.82.20'", "'59.02.40'", 
		"'07.80.10'", "'58.11.15'", "'55.01.20'", "'53.83.10'", "'53.01.10'", 
		"'14.40.10'", "'09.82.30'", "'55.06.30'", "'53.82.30'", "'61.82.30'", 
		"'53.81.10'", "'55.07.20'", "'03.83.10'", "'51.03.20'", "'59.82.10'", 
		"'55.06.10'", "'03.83.20'", "'55.06.20'", "'01.02.20'", "'55.07.10'", 
		"'11.83.20'", "'51.02.40'", "'02.03.30'", "'04.65.10'", "'13.82.10'", 
		"'60.39.20'", "'02.02.10'", "'53.85.10'", "'60.85.10'", "'54.65.10'", 
		"'08.11.60'", "'02.81.20'", "'13.82.20'", "'08.86.10'", "'11.83.30'", 
		"'59.01.20'", "'05.02.40'", "'13.82.30'", "'05.03.10'", "'01.83.20'", 
		"'06.08.10'", "'54.88.10'", "'03.01.20'", "'03.02.30'", "'03.82.30'", 
		"'09.03.30'", "'08.13.20'", "'52.03.30'", "'56.85.10'", "'56.81.20'", 
		"'03.02.20'", "'04.82.20'", "'58.14.10'", "'05.85.10'", "'03.82.20'", 
		"'03.02.40'", "'09.03.10'", "'03.82.10'", "'03.83.30'", "'19.89.20'", 
		"'52.82.30'", "'52.03.10'", "'08.11.20'", "'06.08.30'", "'02.81.10'", 
		"'08.85.10'", "'03.01.10'", "'05.81.10'", "'55.81.10'", "'58.83.20'", 
		"'58.11.30'", "'08.10.20'", "'58.09.10'", "'05.07.10'", "'01.02.10'", 
		"'11.85.10'", "'60.83.20'", "'58.13.20'", "'53.01.20'", "'51.03.10'", 
		"'01.82.20'", "'13.31.20'", "'03.81.10'", "'03.81.20'", "'53.81.20'", 
		"'51.03.30'", "'53.83.20'", "'11.33.10'", "'53.82.20'", "'05.86.10'", 
		"'07.80.20'", "'05.07.30'", "'06.81.10'", "'60.39.30'", "'01.83.30'", 
		"'58.11.70'", "'53.83.30'", "'54.83.30'", "'11.82.10'", "'12.36.10'", 
		"'58.12.10'", "'52.82.20'", "'01.83.10'", "'51.02.30'", "'03.62.10'", 
		"'06.08.20'", "'19.89.30'", "'11.32.10'", "'52.02.20'", "'59.02.20'", 
		"'08.83.20'", "'04.82.30'", "'06.83.10'", "'52.03.20'", "'09.03.20'", 
		"'51.02.10'", "'54.88.20'", "'08.83.10'", "'51.61.10'", "'03.03.20'", 
		"'55.02.20'", "'51.88.20'", "'51.88.10'", "'55.02.10'", "'15.42.10'", 
		"'53.82.10'", "'01.85.10'", "'03.03.30'", "'01.81.10'", "'02.82.30'", 
		"'03.03.10'", "'01.82.30'", "'55.02.30'", "'55.02.40'", "'01.20.10'", 
		"'60.83.10'", "'08.14.20'", "'02.82.20'", "'58.09.20'", "'05.07.20'", 
		"'52.83.30'", "'01.82.10'", "'61.82.10'", "'13.31.30'", "'08.09.10'", 
		"'51.02.20'", "'51.82.20'", "'08.09.20'", "'52.82.10'", "'02.02.30'", 
		"'58.11.20'", "'58.11.80'", "'58.11.10'", "'51.81.10'", "'58.85.10'", 
		"'06.83.20'", "'58.88.10'", "'56.08.10'", "'02.86.10'", "'58.88.20'", 
		"'01.61.10'", "'08.11.40'", "'11.83.10'", "'12.35.40'", "'12.35.50'", 
		"'14.85.10'", "'02.03.10'", "'51.04.10'", "'07.71.10'", "'54.05.10'", 
		"'01.20.20'", "'08.14.10'", "'05.81.20'", "'07.68.10'", "'01.81.20'", 
		"'54.82.30'", "'05.03.20'", "'12.37.10'", "'03.02.10'", "'56.83.20'", 
		"'07.88.10'", "'58.10.30'", "'07.88.20'", "'55.82.10'", "'51.82.10'", 
		"'56.82.10'", "'59.82.20'", "'56.82.30'", "'51.81.20'", "'58.10.10'", 
		"'55.82.30'", "'55.82.20'", "'55.07.40'", "'51.86.10'", "'05.01.20'", 
		"'56.82.20'", "'58.10.20'", "'05.15.10'", "'04.82.10'", "'05.04.10'", 
		"'10.83.30'", "'58.11.90'", "'10.86.10'", "'02.02.40'", "'02.02.20'", 
		"'10.83.20'", "'04.05.10'", "'58.72.10'", "'08.13.10'", "'10.83.10'", 
		"'55.83.30'", "'07.71.20'", "'58.14.20'", "'12.83.20'", "'08.11.30'", 
		"'12.83.30'", "'55.83.20'", "'06.83.30'", "'01.03.20'", "'08.75.10'", 
		"'08.11.50'", "'02.03.20'", "'08.12.10'", "'01.03.10'", "'GA1'", "'GAA'", 
		"'OGA1'", "'OGAA'", "'GD1'", "'GDA'", "'KD1'", "'KDA'", "'GDOG1'", "'GDOGA'", 
		"'KDOG1'", "'KDOGA'", "'ENVGL'", "'OFVGL'", "'ENVWD'", "'OFVWD'", "'KV'", 
		"'KNV'", "'+'", "'-'", "CIJFER", "LETTER", "SYMBOOL", "'/*'", "WS"
	};
	public static final int
		RULE_voorwaarde = 0, RULE_term = 1, RULE_haakjes = 2, RULE_alfanumrubriekwaarde = 3, 
		RULE_alfanumrubriekterm = 4, RULE_datrubriekwaarde = 5, RULE_datum = 6, 
		RULE_datumconstante = 7, RULE_logopvgl = 8, RULE_logopvwd = 9, RULE_matop = 10, 
		RULE_numrubriekwaarde = 11, RULE_numrubriekterm = 12, RULE_getal = 13, 
		RULE_periode = 14, RULE_periodeDag = 15, RULE_periodeMaand = 16, RULE_periodeJaar = 17, 
		RULE_relop = 18, RULE_selDatrubrieknummer = 19, RULE_selDatrubriekwaarde = 20, 
		RULE_selectiedatum = 21, RULE_selectievergelijking = 22, RULE_tekst = 23, 
		RULE_string = 24, RULE_txtop = 25, RULE_vandaagdatum = 26, RULE_vandaagvergelijking = 27, 
		RULE_vergelijking = 28, RULE_numVergelijking = 29, RULE_alfanumVergelijking = 30, 
		RULE_datVergelijking = 31, RULE_voorkomenvraag = 32, RULE_vrkop = 33, 
		RULE_rubrieknummer = 34, RULE_alfanumrubrieknummer = 35, RULE_datrubrieknummer = 36, 
		RULE_numrubrieknummer = 37;
	public static final String[] ruleNames = {
		"voorwaarde", "term", "haakjes", "alfanumrubriekwaarde", "alfanumrubriekterm", 
		"datrubriekwaarde", "datum", "datumconstante", "logopvgl", "logopvwd", 
		"matop", "numrubriekwaarde", "numrubriekterm", "getal", "periode", "periodeDag", 
		"periodeMaand", "periodeJaar", "relop", "selDatrubrieknummer", "selDatrubriekwaarde", 
		"selectiedatum", "selectievergelijking", "tekst", "string", "txtop", "vandaagdatum", 
		"vandaagvergelijking", "vergelijking", "numVergelijking", "alfanumVergelijking", 
		"datVergelijking", "voorkomenvraag", "vrkop", "rubrieknummer", "alfanumrubrieknummer", 
		"datrubrieknummer", "numrubrieknummer"
	};

	@Override
	public String getGrammarFileName() { return "GBAVoorwaarderegel.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public GBAVoorwaarderegelParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class VoorwaardeContext extends ParserRuleContext {
		public VoorwaardeContext voorwaarde() {
			return getRuleContext(VoorwaardeContext.class,0);
		}
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public LogopvwdContext logopvwd() {
			return getRuleContext(LogopvwdContext.class,0);
		}
		public VoorwaardeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_voorwaarde; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitVoorwaarde(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VoorwaardeContext voorwaarde() throws RecognitionException {
		VoorwaardeContext _localctx = new VoorwaardeContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_voorwaarde);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76); term();
			setState(80);
			_la = _input.LA(1);
			if (_la==OP_ENVWD || _la==OP_OFVWD) {
				{
				setState(77); logopvwd();
				setState(78); voorwaarde();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public HaakjesContext haakjes() {
			return getRuleContext(HaakjesContext.class,0);
		}
		public VergelijkingContext vergelijking() {
			return getRuleContext(VergelijkingContext.class,0);
		}
		public VoorkomenvraagContext voorkomenvraag() {
			return getRuleContext(VoorkomenvraagContext.class,0);
		}
		public SelectievergelijkingContext selectievergelijking() {
			return getRuleContext(SelectievergelijkingContext.class,0);
		}
		public VandaagvergelijkingContext vandaagvergelijking() {
			return getRuleContext(VandaagvergelijkingContext.class,0);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_term);
		try {
			setState(87);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(82); vergelijking();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(83); vandaagvergelijking();
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(84); voorkomenvraag();
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(85); selectievergelijking();
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(86); haakjes();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HaakjesContext extends ParserRuleContext {
		public VoorwaardeContext voorwaarde() {
			return getRuleContext(VoorwaardeContext.class,0);
		}
		public HaakjesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_haakjes; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitHaakjes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HaakjesContext haakjes() throws RecognitionException {
		HaakjesContext _localctx = new HaakjesContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_haakjes);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89); match(73);
			setState(90); voorwaarde();
			setState(91); match(17);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AlfanumrubriekwaardeContext extends ParserRuleContext {
		public List<LogopvglContext> logopvgl() {
			return getRuleContexts(LogopvglContext.class);
		}
		public List<AlfanumrubriektermContext> alfanumrubriekterm() {
			return getRuleContexts(AlfanumrubriektermContext.class);
		}
		public AlfanumrubriektermContext alfanumrubriekterm(int i) {
			return getRuleContext(AlfanumrubriektermContext.class,i);
		}
		public LogopvglContext logopvgl(int i) {
			return getRuleContext(LogopvglContext.class,i);
		}
		public AlfanumrubriekwaardeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alfanumrubriekwaarde; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitAlfanumrubriekwaarde(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlfanumrubriekwaardeContext alfanumrubriekwaarde() throws RecognitionException {
		AlfanumrubriekwaardeContext _localctx = new AlfanumrubriekwaardeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_alfanumrubriekwaarde);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93); alfanumrubriekterm();
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OP_ENVGL || _la==OP_OFVGL) {
				{
				{
				setState(94); logopvgl();
				setState(95); alfanumrubriekterm();
				}
				}
				setState(101);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AlfanumrubriektermContext extends ParserRuleContext {
		public TekstContext tekst() {
			return getRuleContext(TekstContext.class,0);
		}
		public AlfanumrubrieknummerContext alfanumrubrieknummer() {
			return getRuleContext(AlfanumrubrieknummerContext.class,0);
		}
		public AlfanumrubriektermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alfanumrubriekterm; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitAlfanumrubriekterm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlfanumrubriektermContext alfanumrubriekterm() throws RecognitionException {
		AlfanumrubriektermContext _localctx = new AlfanumrubriektermContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_alfanumrubriekterm);
		try {
			setState(104);
			switch (_input.LA(1)) {
			case 3:
			case 7:
			case 9:
			case 10:
			case 12:
			case 23:
			case 29:
			case 34:
			case 35:
			case 38:
			case 40:
			case 42:
			case 43:
			case 48:
			case 52:
			case 53:
			case 54:
			case 55:
			case 57:
			case 61:
			case 64:
			case 68:
			case 70:
			case 77:
			case 78:
			case 81:
			case 85:
			case 87:
			case 94:
			case 95:
			case 96:
			case 97:
			case 98:
			case 101:
			case 103:
			case 104:
			case 106:
			case 112:
			case 114:
			case 115:
			case 118:
			case 122:
			case 129:
			case 131:
			case 134:
			case 136:
			case 137:
			case 142:
			case 145:
			case 148:
			case 152:
			case 153:
			case 158:
			case 159:
			case 160:
			case 161:
			case 163:
			case 165:
			case 167:
			case 168:
			case 169:
			case 172:
			case 175:
			case 177:
			case 183:
			case 184:
			case 185:
			case 188:
			case 190:
			case 201:
			case 203:
			case 205:
			case 210:
			case 212:
			case 213:
			case 215:
			case 217:
			case 221:
			case 222:
			case 225:
			case 227:
			case 230:
			case 233:
			case 234:
			case 235:
			case 240:
			case 241:
			case 247:
			case 248:
			case 253:
			case 254:
			case 259:
			case 264:
			case 274:
			case 275:
			case 278:
			case 287:
			case 288:
			case 299:
			case 304:
			case 307:
			case 309:
			case 311:
			case 312:
			case 313:
			case 315:
			case 317:
			case 318:
			case 319:
			case 320:
			case 322:
			case 323:
			case 324:
			case 325:
			case 327:
			case 328:
			case 333:
			case 335:
			case 336:
			case 337:
			case 343:
			case 349:
			case 353:
			case 355:
			case 356:
			case 363:
			case 364:
			case 365:
			case 367:
			case 371:
			case 376:
			case 378:
			case 379:
			case 380:
			case 382:
			case 386:
			case 391:
			case 392:
			case 393:
			case 394:
			case 396:
			case 400:
			case 401:
			case 403:
			case 405:
			case 407:
			case 408:
			case 411:
			case 415:
			case 418:
			case 422:
			case 424:
			case 425:
			case 426:
				enterOuterAlt(_localctx, 1);
				{
				setState(102); alfanumrubrieknummer();
				}
				break;
			case 100:
				enterOuterAlt(_localctx, 2);
				{
				setState(103); tekst();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DatrubriekwaardeContext extends ParserRuleContext {
		public PeriodeContext periode() {
			return getRuleContext(PeriodeContext.class,0);
		}
		public DatumContext datum() {
			return getRuleContext(DatumContext.class,0);
		}
		public MatopContext matop() {
			return getRuleContext(MatopContext.class,0);
		}
		public DatrubriekwaardeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_datrubriekwaarde; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitDatrubriekwaarde(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DatrubriekwaardeContext datrubriekwaarde() throws RecognitionException {
		DatrubriekwaardeContext _localctx = new DatrubriekwaardeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_datrubriekwaarde);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106); datum();
			setState(110);
			_la = _input.LA(1);
			if (_la==OP_PLUS || _la==OP_MIN) {
				{
				setState(107); matop();
				setState(108); periode();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DatumContext extends ParserRuleContext {
		public DatumconstanteContext datumconstante() {
			return getRuleContext(DatumconstanteContext.class,0);
		}
		public DatrubrieknummerContext datrubrieknummer() {
			return getRuleContext(DatrubrieknummerContext.class,0);
		}
		public DatumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_datum; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitDatum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DatumContext datum() throws RecognitionException {
		DatumContext _localctx = new DatumContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_datum);
		try {
			setState(114);
			switch (_input.LA(1)) {
			case 4:
			case 5:
			case 6:
			case 14:
			case 15:
			case 16:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 24:
			case 31:
			case 32:
			case 36:
			case 39:
			case 41:
			case 45:
			case 50:
			case 62:
			case 63:
			case 65:
			case 67:
			case 69:
			case 72:
			case 74:
			case 75:
			case 76:
			case 82:
			case 86:
			case 88:
			case 89:
			case 93:
			case 99:
			case 102:
			case 107:
			case 108:
			case 111:
			case 116:
			case 121:
			case 126:
			case 128:
			case 138:
			case 139:
			case 141:
			case 143:
			case 149:
			case 150:
			case 151:
			case 154:
			case 162:
			case 166:
			case 171:
			case 173:
			case 174:
			case 176:
			case 182:
			case 187:
			case 189:
			case 193:
			case 194:
			case 195:
			case 196:
			case 197:
			case 200:
			case 202:
			case 219:
			case 220:
			case 223:
			case 224:
			case 229:
			case 231:
			case 232:
			case 236:
			case 237:
			case 238:
			case 242:
			case 243:
			case 244:
			case 250:
			case 252:
			case 255:
			case 257:
			case 258:
			case 260:
			case 262:
			case 265:
			case 269:
			case 273:
			case 277:
			case 279:
			case 280:
			case 281:
			case 283:
			case 284:
			case 285:
			case 290:
			case 292:
			case 293:
			case 297:
			case 298:
			case 300:
			case 301:
			case 305:
			case 308:
			case 314:
			case 330:
			case 334:
			case 340:
			case 341:
			case 342:
			case 344:
			case 347:
			case 350:
			case 351:
			case 358:
			case 359:
			case 361:
			case 362:
			case 368:
			case 369:
			case 370:
			case 372:
			case 377:
			case 383:
			case 385:
			case 390:
			case 395:
			case 397:
			case 399:
			case 404:
			case 406:
			case 409:
			case 414:
			case 416:
			case 417:
			case 419:
			case 420:
			case 421:
			case 427:
				enterOuterAlt(_localctx, 1);
				{
				setState(112); datrubrieknummer();
				}
				break;
			case CIJFER:
				enterOuterAlt(_localctx, 2);
				{
				setState(113); datumconstante();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DatumconstanteContext extends ParserRuleContext {
		public List<TerminalNode> CIJFER() { return getTokens(GBAVoorwaarderegelParser.CIJFER); }
		public TerminalNode CIJFER(int i) {
			return getToken(GBAVoorwaarderegelParser.CIJFER, i);
		}
		public DatumconstanteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_datumconstante; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitDatumconstante(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DatumconstanteContext datumconstante() throws RecognitionException {
		DatumconstanteContext _localctx = new DatumconstanteContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_datumconstante);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116); match(CIJFER);
			setState(117); match(CIJFER);
			setState(118); match(CIJFER);
			setState(119); match(CIJFER);
			setState(120); match(CIJFER);
			setState(121); match(CIJFER);
			setState(122); match(CIJFER);
			setState(123); match(CIJFER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogopvglContext extends ParserRuleContext {
		public TerminalNode OP_ENVGL() { return getToken(GBAVoorwaarderegelParser.OP_ENVGL, 0); }
		public TerminalNode OP_OFVGL() { return getToken(GBAVoorwaarderegelParser.OP_OFVGL, 0); }
		public LogopvglContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logopvgl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitLogopvgl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogopvglContext logopvgl() throws RecognitionException {
		LogopvglContext _localctx = new LogopvglContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_logopvgl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			_la = _input.LA(1);
			if ( !(_la==OP_ENVGL || _la==OP_OFVGL) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogopvwdContext extends ParserRuleContext {
		public TerminalNode OP_OFVWD() { return getToken(GBAVoorwaarderegelParser.OP_OFVWD, 0); }
		public TerminalNode OP_ENVWD() { return getToken(GBAVoorwaarderegelParser.OP_ENVWD, 0); }
		public LogopvwdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logopvwd; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitLogopvwd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogopvwdContext logopvwd() throws RecognitionException {
		LogopvwdContext _localctx = new LogopvwdContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_logopvwd);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			_la = _input.LA(1);
			if ( !(_la==OP_ENVWD || _la==OP_OFVWD) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MatopContext extends ParserRuleContext {
		public TerminalNode OP_MIN() { return getToken(GBAVoorwaarderegelParser.OP_MIN, 0); }
		public TerminalNode OP_PLUS() { return getToken(GBAVoorwaarderegelParser.OP_PLUS, 0); }
		public MatopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_matop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitMatop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MatopContext matop() throws RecognitionException {
		MatopContext _localctx = new MatopContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_matop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			_la = _input.LA(1);
			if ( !(_la==OP_PLUS || _la==OP_MIN) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumrubriekwaardeContext extends ParserRuleContext {
		public List<LogopvglContext> logopvgl() {
			return getRuleContexts(LogopvglContext.class);
		}
		public List<NumrubriektermContext> numrubriekterm() {
			return getRuleContexts(NumrubriektermContext.class);
		}
		public NumrubriektermContext numrubriekterm(int i) {
			return getRuleContext(NumrubriektermContext.class,i);
		}
		public LogopvglContext logopvgl(int i) {
			return getRuleContext(LogopvglContext.class,i);
		}
		public NumrubriekwaardeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numrubriekwaarde; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitNumrubriekwaarde(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumrubriekwaardeContext numrubriekwaarde() throws RecognitionException {
		NumrubriekwaardeContext _localctx = new NumrubriekwaardeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_numrubriekwaarde);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131); numrubriekterm();
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OP_ENVGL || _la==OP_OFVGL) {
				{
				{
				setState(132); logopvgl();
				setState(133); numrubriekterm();
				}
				}
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumrubriektermContext extends ParserRuleContext {
		public NumrubrieknummerContext numrubrieknummer() {
			return getRuleContext(NumrubrieknummerContext.class,0);
		}
		public GetalContext getal() {
			return getRuleContext(GetalContext.class,0);
		}
		public NumrubriektermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numrubriekterm; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitNumrubriekterm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumrubriektermContext numrubriekterm() throws RecognitionException {
		NumrubriektermContext _localctx = new NumrubriektermContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_numrubriekterm);
		try {
			setState(142);
			switch (_input.LA(1)) {
			case 1:
			case 2:
			case 8:
			case 11:
			case 13:
			case 25:
			case 26:
			case 27:
			case 28:
			case 30:
			case 33:
			case 37:
			case 44:
			case 46:
			case 47:
			case 49:
			case 51:
			case 56:
			case 58:
			case 59:
			case 60:
			case 66:
			case 71:
			case 79:
			case 80:
			case 83:
			case 84:
			case 90:
			case 91:
			case 92:
			case 105:
			case 109:
			case 110:
			case 113:
			case 117:
			case 119:
			case 120:
			case 123:
			case 124:
			case 125:
			case 127:
			case 130:
			case 132:
			case 133:
			case 135:
			case 140:
			case 144:
			case 146:
			case 147:
			case 155:
			case 156:
			case 157:
			case 164:
			case 170:
			case 178:
			case 179:
			case 180:
			case 181:
			case 186:
			case 191:
			case 192:
			case 198:
			case 199:
			case 204:
			case 206:
			case 207:
			case 208:
			case 209:
			case 211:
			case 214:
			case 216:
			case 218:
			case 226:
			case 228:
			case 239:
			case 245:
			case 246:
			case 249:
			case 251:
			case 256:
			case 261:
			case 266:
			case 267:
			case 268:
			case 270:
			case 271:
			case 272:
			case 276:
			case 282:
			case 286:
			case 289:
			case 291:
			case 294:
			case 295:
			case 296:
			case 302:
			case 303:
			case 306:
			case 316:
			case 321:
			case 326:
			case 329:
			case 331:
			case 332:
			case 338:
			case 339:
			case 345:
			case 346:
			case 348:
			case 352:
			case 354:
			case 357:
			case 360:
			case 366:
			case 373:
			case 374:
			case 375:
			case 381:
			case 384:
			case 387:
			case 388:
			case 389:
			case 398:
			case 402:
			case 410:
			case 412:
			case 413:
			case 423:
				enterOuterAlt(_localctx, 1);
				{
				setState(140); numrubrieknummer();
				}
				break;
			case CIJFER:
				enterOuterAlt(_localctx, 2);
				{
				setState(141); getal();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GetalContext extends ParserRuleContext {
		public List<TerminalNode> CIJFER() { return getTokens(GBAVoorwaarderegelParser.CIJFER); }
		public TerminalNode CIJFER(int i) {
			return getToken(GBAVoorwaarderegelParser.CIJFER, i);
		}
		public GetalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_getal; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitGetal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GetalContext getal() throws RecognitionException {
		GetalContext _localctx = new GetalContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_getal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(144); match(CIJFER);
				}
				}
				setState(147); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CIJFER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PeriodeContext extends ParserRuleContext {
		public PeriodeMaandContext periodeMaand() {
			return getRuleContext(PeriodeMaandContext.class,0);
		}
		public PeriodeDagContext periodeDag() {
			return getRuleContext(PeriodeDagContext.class,0);
		}
		public PeriodeJaarContext periodeJaar() {
			return getRuleContext(PeriodeJaarContext.class,0);
		}
		public PeriodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_periode; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitPeriode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PeriodeContext periode() throws RecognitionException {
		PeriodeContext _localctx = new PeriodeContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_periode);
		try {
			setState(152);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(149); periodeDag();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(150); periodeMaand();
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(151); periodeJaar();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PeriodeDagContext extends ParserRuleContext {
		public List<TerminalNode> CIJFER() { return getTokens(GBAVoorwaarderegelParser.CIJFER); }
		public TerminalNode CIJFER(int i) {
			return getToken(GBAVoorwaarderegelParser.CIJFER, i);
		}
		public PeriodeDagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_periodeDag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitPeriodeDag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PeriodeDagContext periodeDag() throws RecognitionException {
		PeriodeDagContext _localctx = new PeriodeDagContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_periodeDag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154); match(CIJFER);
			setState(155); match(CIJFER);
			setState(156); match(CIJFER);
			setState(157); match(CIJFER);
			setState(158); match(CIJFER);
			setState(159); match(CIJFER);
			setState(160); match(CIJFER);
			setState(161); match(CIJFER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PeriodeMaandContext extends ParserRuleContext {
		public List<TerminalNode> CIJFER() { return getTokens(GBAVoorwaarderegelParser.CIJFER); }
		public TerminalNode CIJFER(int i) {
			return getToken(GBAVoorwaarderegelParser.CIJFER, i);
		}
		public PeriodeMaandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_periodeMaand; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitPeriodeMaand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PeriodeMaandContext periodeMaand() throws RecognitionException {
		PeriodeMaandContext _localctx = new PeriodeMaandContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_periodeMaand);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163); match(CIJFER);
			setState(164); match(CIJFER);
			setState(165); match(CIJFER);
			setState(166); match(CIJFER);
			setState(167); match(CIJFER);
			setState(168); match(CIJFER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PeriodeJaarContext extends ParserRuleContext {
		public List<TerminalNode> CIJFER() { return getTokens(GBAVoorwaarderegelParser.CIJFER); }
		public TerminalNode CIJFER(int i) {
			return getToken(GBAVoorwaarderegelParser.CIJFER, i);
		}
		public PeriodeJaarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_periodeJaar; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitPeriodeJaar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PeriodeJaarContext periodeJaar() throws RecognitionException {
		PeriodeJaarContext _localctx = new PeriodeJaarContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_periodeJaar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170); match(CIJFER);
			setState(171); match(CIJFER);
			setState(172); match(CIJFER);
			setState(173); match(CIJFER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelopContext extends ParserRuleContext {
		public TerminalNode OP_KDOGA() { return getToken(GBAVoorwaarderegelParser.OP_KDOGA, 0); }
		public TerminalNode OP_KD1() { return getToken(GBAVoorwaarderegelParser.OP_KD1, 0); }
		public TerminalNode OP_GDOG1() { return getToken(GBAVoorwaarderegelParser.OP_GDOG1, 0); }
		public TerminalNode OP_GDA() { return getToken(GBAVoorwaarderegelParser.OP_GDA, 0); }
		public TxtopContext txtop() {
			return getRuleContext(TxtopContext.class,0);
		}
		public TerminalNode OP_KDOG1() { return getToken(GBAVoorwaarderegelParser.OP_KDOG1, 0); }
		public TerminalNode OP_KDA() { return getToken(GBAVoorwaarderegelParser.OP_KDA, 0); }
		public TerminalNode OP_GD1() { return getToken(GBAVoorwaarderegelParser.OP_GD1, 0); }
		public TerminalNode OP_GDOGA() { return getToken(GBAVoorwaarderegelParser.OP_GDOGA, 0); }
		public RelopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitRelop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelopContext relop() throws RecognitionException {
		RelopContext _localctx = new RelopContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_relop);
		try {
			setState(184);
			switch (_input.LA(1)) {
			case OP_GD1:
				enterOuterAlt(_localctx, 1);
				{
				setState(175); match(OP_GD1);
				}
				break;
			case OP_GDA:
				enterOuterAlt(_localctx, 2);
				{
				setState(176); match(OP_GDA);
				}
				break;
			case OP_KD1:
				enterOuterAlt(_localctx, 3);
				{
				setState(177); match(OP_KD1);
				}
				break;
			case OP_KDA:
				enterOuterAlt(_localctx, 4);
				{
				setState(178); match(OP_KDA);
				}
				break;
			case OP_GDOG1:
				enterOuterAlt(_localctx, 5);
				{
				setState(179); match(OP_GDOG1);
				}
				break;
			case OP_GDOGA:
				enterOuterAlt(_localctx, 6);
				{
				setState(180); match(OP_GDOGA);
				}
				break;
			case OP_KDOG1:
				enterOuterAlt(_localctx, 7);
				{
				setState(181); match(OP_KDOG1);
				}
				break;
			case OP_KDOGA:
				enterOuterAlt(_localctx, 8);
				{
				setState(182); match(OP_KDOGA);
				}
				break;
			case OP_GA1:
			case OP_GAA:
			case OP_OGA1:
			case OP_OGAA:
				enterOuterAlt(_localctx, 9);
				{
				setState(183); txtop();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelDatrubrieknummerContext extends ParserRuleContext {
		public SelectiedatumContext selectiedatum() {
			return getRuleContext(SelectiedatumContext.class,0);
		}
		public DatrubrieknummerContext datrubrieknummer() {
			return getRuleContext(DatrubrieknummerContext.class,0);
		}
		public SelDatrubrieknummerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selDatrubrieknummer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitSelDatrubrieknummer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelDatrubrieknummerContext selDatrubrieknummer() throws RecognitionException {
		SelDatrubrieknummerContext _localctx = new SelDatrubrieknummerContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_selDatrubrieknummer);
		try {
			setState(188);
			switch (_input.LA(1)) {
			case 263:
				enterOuterAlt(_localctx, 1);
				{
				setState(186); selectiedatum();
				}
				break;
			case 4:
			case 5:
			case 6:
			case 14:
			case 15:
			case 16:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 24:
			case 31:
			case 32:
			case 36:
			case 39:
			case 41:
			case 45:
			case 50:
			case 62:
			case 63:
			case 65:
			case 67:
			case 69:
			case 72:
			case 74:
			case 75:
			case 76:
			case 82:
			case 86:
			case 88:
			case 89:
			case 93:
			case 99:
			case 102:
			case 107:
			case 108:
			case 111:
			case 116:
			case 121:
			case 126:
			case 128:
			case 138:
			case 139:
			case 141:
			case 143:
			case 149:
			case 150:
			case 151:
			case 154:
			case 162:
			case 166:
			case 171:
			case 173:
			case 174:
			case 176:
			case 182:
			case 187:
			case 189:
			case 193:
			case 194:
			case 195:
			case 196:
			case 197:
			case 200:
			case 202:
			case 219:
			case 220:
			case 223:
			case 224:
			case 229:
			case 231:
			case 232:
			case 236:
			case 237:
			case 238:
			case 242:
			case 243:
			case 244:
			case 250:
			case 252:
			case 255:
			case 257:
			case 258:
			case 260:
			case 262:
			case 265:
			case 269:
			case 273:
			case 277:
			case 279:
			case 280:
			case 281:
			case 283:
			case 284:
			case 285:
			case 290:
			case 292:
			case 293:
			case 297:
			case 298:
			case 300:
			case 301:
			case 305:
			case 308:
			case 314:
			case 330:
			case 334:
			case 340:
			case 341:
			case 342:
			case 344:
			case 347:
			case 350:
			case 351:
			case 358:
			case 359:
			case 361:
			case 362:
			case 368:
			case 369:
			case 370:
			case 372:
			case 377:
			case 383:
			case 385:
			case 390:
			case 395:
			case 397:
			case 399:
			case 404:
			case 406:
			case 409:
			case 414:
			case 416:
			case 417:
			case 419:
			case 420:
			case 421:
			case 427:
				enterOuterAlt(_localctx, 2);
				{
				setState(187); datrubrieknummer();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelDatrubriekwaardeContext extends ParserRuleContext {
		public DatrubriekwaardeContext datrubriekwaarde() {
			return getRuleContext(DatrubriekwaardeContext.class,0);
		}
		public PeriodeContext periode() {
			return getRuleContext(PeriodeContext.class,0);
		}
		public SelectiedatumContext selectiedatum() {
			return getRuleContext(SelectiedatumContext.class,0);
		}
		public MatopContext matop() {
			return getRuleContext(MatopContext.class,0);
		}
		public SelDatrubriekwaardeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selDatrubriekwaarde; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitSelDatrubriekwaarde(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelDatrubriekwaardeContext selDatrubriekwaarde() throws RecognitionException {
		SelDatrubriekwaardeContext _localctx = new SelDatrubriekwaardeContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_selDatrubriekwaarde);
		try {
			setState(195);
			switch (_input.LA(1)) {
			case 4:
			case 5:
			case 6:
			case 14:
			case 15:
			case 16:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 24:
			case 31:
			case 32:
			case 36:
			case 39:
			case 41:
			case 45:
			case 50:
			case 62:
			case 63:
			case 65:
			case 67:
			case 69:
			case 72:
			case 74:
			case 75:
			case 76:
			case 82:
			case 86:
			case 88:
			case 89:
			case 93:
			case 99:
			case 102:
			case 107:
			case 108:
			case 111:
			case 116:
			case 121:
			case 126:
			case 128:
			case 138:
			case 139:
			case 141:
			case 143:
			case 149:
			case 150:
			case 151:
			case 154:
			case 162:
			case 166:
			case 171:
			case 173:
			case 174:
			case 176:
			case 182:
			case 187:
			case 189:
			case 193:
			case 194:
			case 195:
			case 196:
			case 197:
			case 200:
			case 202:
			case 219:
			case 220:
			case 223:
			case 224:
			case 229:
			case 231:
			case 232:
			case 236:
			case 237:
			case 238:
			case 242:
			case 243:
			case 244:
			case 250:
			case 252:
			case 255:
			case 257:
			case 258:
			case 260:
			case 262:
			case 265:
			case 269:
			case 273:
			case 277:
			case 279:
			case 280:
			case 281:
			case 283:
			case 284:
			case 285:
			case 290:
			case 292:
			case 293:
			case 297:
			case 298:
			case 300:
			case 301:
			case 305:
			case 308:
			case 314:
			case 330:
			case 334:
			case 340:
			case 341:
			case 342:
			case 344:
			case 347:
			case 350:
			case 351:
			case 358:
			case 359:
			case 361:
			case 362:
			case 368:
			case 369:
			case 370:
			case 372:
			case 377:
			case 383:
			case 385:
			case 390:
			case 395:
			case 397:
			case 399:
			case 404:
			case 406:
			case 409:
			case 414:
			case 416:
			case 417:
			case 419:
			case 420:
			case 421:
			case 427:
			case CIJFER:
				enterOuterAlt(_localctx, 1);
				{
				setState(190); datrubriekwaarde();
				}
				break;
			case 263:
				enterOuterAlt(_localctx, 2);
				{
				setState(191); selectiedatum();
				setState(192); matop();
				setState(193); periode();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectiedatumContext extends ParserRuleContext {
		public SelectiedatumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectiedatum; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitSelectiedatum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectiedatumContext selectiedatum() throws RecognitionException {
		SelectiedatumContext _localctx = new SelectiedatumContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_selectiedatum);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197); match(263);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectievergelijkingContext extends ParserRuleContext {
		public SelDatrubriekwaardeContext selDatrubriekwaarde() {
			return getRuleContext(SelDatrubriekwaardeContext.class,0);
		}
		public RelopContext relop() {
			return getRuleContext(RelopContext.class,0);
		}
		public SelDatrubrieknummerContext selDatrubrieknummer() {
			return getRuleContext(SelDatrubrieknummerContext.class,0);
		}
		public SelectievergelijkingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectievergelijking; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitSelectievergelijking(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectievergelijkingContext selectievergelijking() throws RecognitionException {
		SelectievergelijkingContext _localctx = new SelectievergelijkingContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_selectievergelijking);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199); selDatrubrieknummer();
			setState(200); relop();
			setState(201); selDatrubriekwaarde();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TekstContext extends ParserRuleContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode WILDCARD() { return getToken(GBAVoorwaarderegelParser.WILDCARD, 0); }
		public TekstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tekst; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitTekst(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TekstContext tekst() throws RecognitionException {
		TekstContext _localctx = new TekstContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_tekst);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203); match(100);
			setState(204); string();
			setState(206);
			_la = _input.LA(1);
			if (_la==WILDCARD) {
				{
				setState(205); match(WILDCARD);
				}
			}

			setState(208); match(100);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public TerminalNode LETTER(int i) {
			return getToken(GBAVoorwaarderegelParser.LETTER, i);
		}
		public List<TerminalNode> CIJFER() { return getTokens(GBAVoorwaarderegelParser.CIJFER); }
		public TerminalNode CIJFER(int i) {
			return getToken(GBAVoorwaarderegelParser.CIJFER, i);
		}
		public List<TerminalNode> LETTER() { return getTokens(GBAVoorwaarderegelParser.LETTER); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_string);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(210);
				_la = _input.LA(1);
				if ( !(_la==CIJFER || _la==LETTER) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
				}
				setState(213); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CIJFER || _la==LETTER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TxtopContext extends ParserRuleContext {
		public TerminalNode OP_OGAA() { return getToken(GBAVoorwaarderegelParser.OP_OGAA, 0); }
		public TerminalNode OP_OGA1() { return getToken(GBAVoorwaarderegelParser.OP_OGA1, 0); }
		public TerminalNode OP_GAA() { return getToken(GBAVoorwaarderegelParser.OP_GAA, 0); }
		public TerminalNode OP_GA1() { return getToken(GBAVoorwaarderegelParser.OP_GA1, 0); }
		public TxtopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_txtop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitTxtop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TxtopContext txtop() throws RecognitionException {
		TxtopContext _localctx = new TxtopContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_txtop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			_la = _input.LA(1);
			if ( !(((((_la - 428)) & ~0x3f) == 0 && ((1L << (_la - 428)) & ((1L << (OP_GA1 - 428)) | (1L << (OP_GAA - 428)) | (1L << (OP_OGA1 - 428)) | (1L << (OP_OGAA - 428)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VandaagdatumContext extends ParserRuleContext {
		public VandaagdatumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vandaagdatum; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitVandaagdatum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VandaagdatumContext vandaagdatum() throws RecognitionException {
		VandaagdatumContext _localctx = new VandaagdatumContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_vandaagdatum);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217); match(310);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VandaagvergelijkingContext extends ParserRuleContext {
		public RelopContext relop() {
			return getRuleContext(RelopContext.class,0);
		}
		public PeriodeContext periode() {
			return getRuleContext(PeriodeContext.class,0);
		}
		public MatopContext matop() {
			return getRuleContext(MatopContext.class,0);
		}
		public VandaagdatumContext vandaagdatum() {
			return getRuleContext(VandaagdatumContext.class,0);
		}
		public DatrubrieknummerContext datrubrieknummer() {
			return getRuleContext(DatrubrieknummerContext.class,0);
		}
		public VandaagvergelijkingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vandaagvergelijking; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitVandaagvergelijking(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VandaagvergelijkingContext vandaagvergelijking() throws RecognitionException {
		VandaagvergelijkingContext _localctx = new VandaagvergelijkingContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_vandaagvergelijking);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219); datrubrieknummer();
			setState(220); relop();
			setState(221); vandaagdatum();
			setState(222); matop();
			setState(223); periode();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VergelijkingContext extends ParserRuleContext {
		public NumVergelijkingContext numVergelijking() {
			return getRuleContext(NumVergelijkingContext.class,0);
		}
		public DatVergelijkingContext datVergelijking() {
			return getRuleContext(DatVergelijkingContext.class,0);
		}
		public AlfanumVergelijkingContext alfanumVergelijking() {
			return getRuleContext(AlfanumVergelijkingContext.class,0);
		}
		public VergelijkingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vergelijking; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitVergelijking(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VergelijkingContext vergelijking() throws RecognitionException {
		VergelijkingContext _localctx = new VergelijkingContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_vergelijking);
		try {
			setState(228);
			switch (_input.LA(1)) {
			case 1:
			case 2:
			case 8:
			case 11:
			case 13:
			case 25:
			case 26:
			case 27:
			case 28:
			case 30:
			case 33:
			case 37:
			case 44:
			case 46:
			case 47:
			case 49:
			case 51:
			case 56:
			case 58:
			case 59:
			case 60:
			case 66:
			case 71:
			case 79:
			case 80:
			case 83:
			case 84:
			case 90:
			case 91:
			case 92:
			case 105:
			case 109:
			case 110:
			case 113:
			case 117:
			case 119:
			case 120:
			case 123:
			case 124:
			case 125:
			case 127:
			case 130:
			case 132:
			case 133:
			case 135:
			case 140:
			case 144:
			case 146:
			case 147:
			case 155:
			case 156:
			case 157:
			case 164:
			case 170:
			case 178:
			case 179:
			case 180:
			case 181:
			case 186:
			case 191:
			case 192:
			case 198:
			case 199:
			case 204:
			case 206:
			case 207:
			case 208:
			case 209:
			case 211:
			case 214:
			case 216:
			case 218:
			case 226:
			case 228:
			case 239:
			case 245:
			case 246:
			case 249:
			case 251:
			case 256:
			case 261:
			case 266:
			case 267:
			case 268:
			case 270:
			case 271:
			case 272:
			case 276:
			case 282:
			case 286:
			case 289:
			case 291:
			case 294:
			case 295:
			case 296:
			case 302:
			case 303:
			case 306:
			case 316:
			case 321:
			case 326:
			case 329:
			case 331:
			case 332:
			case 338:
			case 339:
			case 345:
			case 346:
			case 348:
			case 352:
			case 354:
			case 357:
			case 360:
			case 366:
			case 373:
			case 374:
			case 375:
			case 381:
			case 384:
			case 387:
			case 388:
			case 389:
			case 398:
			case 402:
			case 410:
			case 412:
			case 413:
			case 423:
				enterOuterAlt(_localctx, 1);
				{
				setState(225); numVergelijking();
				}
				break;
			case 3:
			case 7:
			case 9:
			case 10:
			case 12:
			case 23:
			case 29:
			case 34:
			case 35:
			case 38:
			case 40:
			case 42:
			case 43:
			case 48:
			case 52:
			case 53:
			case 54:
			case 55:
			case 57:
			case 61:
			case 64:
			case 68:
			case 70:
			case 77:
			case 78:
			case 81:
			case 85:
			case 87:
			case 94:
			case 95:
			case 96:
			case 97:
			case 98:
			case 101:
			case 103:
			case 104:
			case 106:
			case 112:
			case 114:
			case 115:
			case 118:
			case 122:
			case 129:
			case 131:
			case 134:
			case 136:
			case 137:
			case 142:
			case 145:
			case 148:
			case 152:
			case 153:
			case 158:
			case 159:
			case 160:
			case 161:
			case 163:
			case 165:
			case 167:
			case 168:
			case 169:
			case 172:
			case 175:
			case 177:
			case 183:
			case 184:
			case 185:
			case 188:
			case 190:
			case 201:
			case 203:
			case 205:
			case 210:
			case 212:
			case 213:
			case 215:
			case 217:
			case 221:
			case 222:
			case 225:
			case 227:
			case 230:
			case 233:
			case 234:
			case 235:
			case 240:
			case 241:
			case 247:
			case 248:
			case 253:
			case 254:
			case 259:
			case 264:
			case 274:
			case 275:
			case 278:
			case 287:
			case 288:
			case 299:
			case 304:
			case 307:
			case 309:
			case 311:
			case 312:
			case 313:
			case 315:
			case 317:
			case 318:
			case 319:
			case 320:
			case 322:
			case 323:
			case 324:
			case 325:
			case 327:
			case 328:
			case 333:
			case 335:
			case 336:
			case 337:
			case 343:
			case 349:
			case 353:
			case 355:
			case 356:
			case 363:
			case 364:
			case 365:
			case 367:
			case 371:
			case 376:
			case 378:
			case 379:
			case 380:
			case 382:
			case 386:
			case 391:
			case 392:
			case 393:
			case 394:
			case 396:
			case 400:
			case 401:
			case 403:
			case 405:
			case 407:
			case 408:
			case 411:
			case 415:
			case 418:
			case 422:
			case 424:
			case 425:
			case 426:
				enterOuterAlt(_localctx, 2);
				{
				setState(226); alfanumVergelijking();
				}
				break;
			case 4:
			case 5:
			case 6:
			case 14:
			case 15:
			case 16:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 24:
			case 31:
			case 32:
			case 36:
			case 39:
			case 41:
			case 45:
			case 50:
			case 62:
			case 63:
			case 65:
			case 67:
			case 69:
			case 72:
			case 74:
			case 75:
			case 76:
			case 82:
			case 86:
			case 88:
			case 89:
			case 93:
			case 99:
			case 102:
			case 107:
			case 108:
			case 111:
			case 116:
			case 121:
			case 126:
			case 128:
			case 138:
			case 139:
			case 141:
			case 143:
			case 149:
			case 150:
			case 151:
			case 154:
			case 162:
			case 166:
			case 171:
			case 173:
			case 174:
			case 176:
			case 182:
			case 187:
			case 189:
			case 193:
			case 194:
			case 195:
			case 196:
			case 197:
			case 200:
			case 202:
			case 219:
			case 220:
			case 223:
			case 224:
			case 229:
			case 231:
			case 232:
			case 236:
			case 237:
			case 238:
			case 242:
			case 243:
			case 244:
			case 250:
			case 252:
			case 255:
			case 257:
			case 258:
			case 260:
			case 262:
			case 265:
			case 269:
			case 273:
			case 277:
			case 279:
			case 280:
			case 281:
			case 283:
			case 284:
			case 285:
			case 290:
			case 292:
			case 293:
			case 297:
			case 298:
			case 300:
			case 301:
			case 305:
			case 308:
			case 314:
			case 330:
			case 334:
			case 340:
			case 341:
			case 342:
			case 344:
			case 347:
			case 350:
			case 351:
			case 358:
			case 359:
			case 361:
			case 362:
			case 368:
			case 369:
			case 370:
			case 372:
			case 377:
			case 383:
			case 385:
			case 390:
			case 395:
			case 397:
			case 399:
			case 404:
			case 406:
			case 409:
			case 414:
			case 416:
			case 417:
			case 419:
			case 420:
			case 421:
			case 427:
				enterOuterAlt(_localctx, 3);
				{
				setState(227); datVergelijking();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumVergelijkingContext extends ParserRuleContext {
		public NumrubriekwaardeContext numrubriekwaarde() {
			return getRuleContext(NumrubriekwaardeContext.class,0);
		}
		public TxtopContext txtop() {
			return getRuleContext(TxtopContext.class,0);
		}
		public AlfanumrubrieknummerContext alfanumrubrieknummer() {
			return getRuleContext(AlfanumrubrieknummerContext.class,0);
		}
		public NumrubrieknummerContext numrubrieknummer() {
			return getRuleContext(NumrubrieknummerContext.class,0);
		}
		public NumVergelijkingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numVergelijking; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitNumVergelijking(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumVergelijkingContext numVergelijking() throws RecognitionException {
		NumVergelijkingContext _localctx = new NumVergelijkingContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_numVergelijking);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230); numrubrieknummer();
			setState(231); txtop();
			setState(234);
			switch (_input.LA(1)) {
			case 1:
			case 2:
			case 8:
			case 11:
			case 13:
			case 25:
			case 26:
			case 27:
			case 28:
			case 30:
			case 33:
			case 37:
			case 44:
			case 46:
			case 47:
			case 49:
			case 51:
			case 56:
			case 58:
			case 59:
			case 60:
			case 66:
			case 71:
			case 79:
			case 80:
			case 83:
			case 84:
			case 90:
			case 91:
			case 92:
			case 105:
			case 109:
			case 110:
			case 113:
			case 117:
			case 119:
			case 120:
			case 123:
			case 124:
			case 125:
			case 127:
			case 130:
			case 132:
			case 133:
			case 135:
			case 140:
			case 144:
			case 146:
			case 147:
			case 155:
			case 156:
			case 157:
			case 164:
			case 170:
			case 178:
			case 179:
			case 180:
			case 181:
			case 186:
			case 191:
			case 192:
			case 198:
			case 199:
			case 204:
			case 206:
			case 207:
			case 208:
			case 209:
			case 211:
			case 214:
			case 216:
			case 218:
			case 226:
			case 228:
			case 239:
			case 245:
			case 246:
			case 249:
			case 251:
			case 256:
			case 261:
			case 266:
			case 267:
			case 268:
			case 270:
			case 271:
			case 272:
			case 276:
			case 282:
			case 286:
			case 289:
			case 291:
			case 294:
			case 295:
			case 296:
			case 302:
			case 303:
			case 306:
			case 316:
			case 321:
			case 326:
			case 329:
			case 331:
			case 332:
			case 338:
			case 339:
			case 345:
			case 346:
			case 348:
			case 352:
			case 354:
			case 357:
			case 360:
			case 366:
			case 373:
			case 374:
			case 375:
			case 381:
			case 384:
			case 387:
			case 388:
			case 389:
			case 398:
			case 402:
			case 410:
			case 412:
			case 413:
			case 423:
			case CIJFER:
				{
				setState(232); numrubriekwaarde();
				}
				break;
			case 3:
			case 7:
			case 9:
			case 10:
			case 12:
			case 23:
			case 29:
			case 34:
			case 35:
			case 38:
			case 40:
			case 42:
			case 43:
			case 48:
			case 52:
			case 53:
			case 54:
			case 55:
			case 57:
			case 61:
			case 64:
			case 68:
			case 70:
			case 77:
			case 78:
			case 81:
			case 85:
			case 87:
			case 94:
			case 95:
			case 96:
			case 97:
			case 98:
			case 101:
			case 103:
			case 104:
			case 106:
			case 112:
			case 114:
			case 115:
			case 118:
			case 122:
			case 129:
			case 131:
			case 134:
			case 136:
			case 137:
			case 142:
			case 145:
			case 148:
			case 152:
			case 153:
			case 158:
			case 159:
			case 160:
			case 161:
			case 163:
			case 165:
			case 167:
			case 168:
			case 169:
			case 172:
			case 175:
			case 177:
			case 183:
			case 184:
			case 185:
			case 188:
			case 190:
			case 201:
			case 203:
			case 205:
			case 210:
			case 212:
			case 213:
			case 215:
			case 217:
			case 221:
			case 222:
			case 225:
			case 227:
			case 230:
			case 233:
			case 234:
			case 235:
			case 240:
			case 241:
			case 247:
			case 248:
			case 253:
			case 254:
			case 259:
			case 264:
			case 274:
			case 275:
			case 278:
			case 287:
			case 288:
			case 299:
			case 304:
			case 307:
			case 309:
			case 311:
			case 312:
			case 313:
			case 315:
			case 317:
			case 318:
			case 319:
			case 320:
			case 322:
			case 323:
			case 324:
			case 325:
			case 327:
			case 328:
			case 333:
			case 335:
			case 336:
			case 337:
			case 343:
			case 349:
			case 353:
			case 355:
			case 356:
			case 363:
			case 364:
			case 365:
			case 367:
			case 371:
			case 376:
			case 378:
			case 379:
			case 380:
			case 382:
			case 386:
			case 391:
			case 392:
			case 393:
			case 394:
			case 396:
			case 400:
			case 401:
			case 403:
			case 405:
			case 407:
			case 408:
			case 411:
			case 415:
			case 418:
			case 422:
			case 424:
			case 425:
			case 426:
				{
				setState(233); alfanumrubrieknummer();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AlfanumVergelijkingContext extends ParserRuleContext {
		public TxtopContext txtop() {
			return getRuleContext(TxtopContext.class,0);
		}
		public AlfanumrubriekwaardeContext alfanumrubriekwaarde() {
			return getRuleContext(AlfanumrubriekwaardeContext.class,0);
		}
		public AlfanumrubrieknummerContext alfanumrubrieknummer() {
			return getRuleContext(AlfanumrubrieknummerContext.class,0);
		}
		public AlfanumVergelijkingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alfanumVergelijking; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitAlfanumVergelijking(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlfanumVergelijkingContext alfanumVergelijking() throws RecognitionException {
		AlfanumVergelijkingContext _localctx = new AlfanumVergelijkingContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_alfanumVergelijking);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236); alfanumrubrieknummer();
			setState(237); txtop();
			setState(238); alfanumrubriekwaarde();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DatVergelijkingContext extends ParserRuleContext {
		public RelopContext relop() {
			return getRuleContext(RelopContext.class,0);
		}
		public DatrubriekwaardeContext datrubriekwaarde() {
			return getRuleContext(DatrubriekwaardeContext.class,0);
		}
		public DatrubrieknummerContext datrubrieknummer() {
			return getRuleContext(DatrubrieknummerContext.class,0);
		}
		public DatVergelijkingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_datVergelijking; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitDatVergelijking(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DatVergelijkingContext datVergelijking() throws RecognitionException {
		DatVergelijkingContext _localctx = new DatVergelijkingContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_datVergelijking);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240); datrubrieknummer();
			setState(241); relop();
			setState(242); datrubriekwaarde();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VoorkomenvraagContext extends ParserRuleContext {
		public RubrieknummerContext rubrieknummer() {
			return getRuleContext(RubrieknummerContext.class,0);
		}
		public VrkopContext vrkop() {
			return getRuleContext(VrkopContext.class,0);
		}
		public VoorkomenvraagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_voorkomenvraag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitVoorkomenvraag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VoorkomenvraagContext voorkomenvraag() throws RecognitionException {
		VoorkomenvraagContext _localctx = new VoorkomenvraagContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_voorkomenvraag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244); vrkop();
			setState(245); rubrieknummer();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VrkopContext extends ParserRuleContext {
		public TerminalNode OP_KNV() { return getToken(GBAVoorwaarderegelParser.OP_KNV, 0); }
		public TerminalNode OP_KV() { return getToken(GBAVoorwaarderegelParser.OP_KV, 0); }
		public VrkopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vrkop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitVrkop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VrkopContext vrkop() throws RecognitionException {
		VrkopContext _localctx = new VrkopContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_vrkop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			_la = _input.LA(1);
			if ( !(_la==OP_KV || _la==OP_KNV) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RubrieknummerContext extends ParserRuleContext {
		public DatrubrieknummerContext datrubrieknummer() {
			return getRuleContext(DatrubrieknummerContext.class,0);
		}
		public AlfanumrubrieknummerContext alfanumrubrieknummer() {
			return getRuleContext(AlfanumrubrieknummerContext.class,0);
		}
		public NumrubrieknummerContext numrubrieknummer() {
			return getRuleContext(NumrubrieknummerContext.class,0);
		}
		public RubrieknummerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rubrieknummer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitRubrieknummer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RubrieknummerContext rubrieknummer() throws RecognitionException {
		RubrieknummerContext _localctx = new RubrieknummerContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_rubrieknummer);
		try {
			setState(252);
			switch (_input.LA(1)) {
			case 1:
			case 2:
			case 8:
			case 11:
			case 13:
			case 25:
			case 26:
			case 27:
			case 28:
			case 30:
			case 33:
			case 37:
			case 44:
			case 46:
			case 47:
			case 49:
			case 51:
			case 56:
			case 58:
			case 59:
			case 60:
			case 66:
			case 71:
			case 79:
			case 80:
			case 83:
			case 84:
			case 90:
			case 91:
			case 92:
			case 105:
			case 109:
			case 110:
			case 113:
			case 117:
			case 119:
			case 120:
			case 123:
			case 124:
			case 125:
			case 127:
			case 130:
			case 132:
			case 133:
			case 135:
			case 140:
			case 144:
			case 146:
			case 147:
			case 155:
			case 156:
			case 157:
			case 164:
			case 170:
			case 178:
			case 179:
			case 180:
			case 181:
			case 186:
			case 191:
			case 192:
			case 198:
			case 199:
			case 204:
			case 206:
			case 207:
			case 208:
			case 209:
			case 211:
			case 214:
			case 216:
			case 218:
			case 226:
			case 228:
			case 239:
			case 245:
			case 246:
			case 249:
			case 251:
			case 256:
			case 261:
			case 266:
			case 267:
			case 268:
			case 270:
			case 271:
			case 272:
			case 276:
			case 282:
			case 286:
			case 289:
			case 291:
			case 294:
			case 295:
			case 296:
			case 302:
			case 303:
			case 306:
			case 316:
			case 321:
			case 326:
			case 329:
			case 331:
			case 332:
			case 338:
			case 339:
			case 345:
			case 346:
			case 348:
			case 352:
			case 354:
			case 357:
			case 360:
			case 366:
			case 373:
			case 374:
			case 375:
			case 381:
			case 384:
			case 387:
			case 388:
			case 389:
			case 398:
			case 402:
			case 410:
			case 412:
			case 413:
			case 423:
				enterOuterAlt(_localctx, 1);
				{
				setState(249); numrubrieknummer();
				}
				break;
			case 3:
			case 7:
			case 9:
			case 10:
			case 12:
			case 23:
			case 29:
			case 34:
			case 35:
			case 38:
			case 40:
			case 42:
			case 43:
			case 48:
			case 52:
			case 53:
			case 54:
			case 55:
			case 57:
			case 61:
			case 64:
			case 68:
			case 70:
			case 77:
			case 78:
			case 81:
			case 85:
			case 87:
			case 94:
			case 95:
			case 96:
			case 97:
			case 98:
			case 101:
			case 103:
			case 104:
			case 106:
			case 112:
			case 114:
			case 115:
			case 118:
			case 122:
			case 129:
			case 131:
			case 134:
			case 136:
			case 137:
			case 142:
			case 145:
			case 148:
			case 152:
			case 153:
			case 158:
			case 159:
			case 160:
			case 161:
			case 163:
			case 165:
			case 167:
			case 168:
			case 169:
			case 172:
			case 175:
			case 177:
			case 183:
			case 184:
			case 185:
			case 188:
			case 190:
			case 201:
			case 203:
			case 205:
			case 210:
			case 212:
			case 213:
			case 215:
			case 217:
			case 221:
			case 222:
			case 225:
			case 227:
			case 230:
			case 233:
			case 234:
			case 235:
			case 240:
			case 241:
			case 247:
			case 248:
			case 253:
			case 254:
			case 259:
			case 264:
			case 274:
			case 275:
			case 278:
			case 287:
			case 288:
			case 299:
			case 304:
			case 307:
			case 309:
			case 311:
			case 312:
			case 313:
			case 315:
			case 317:
			case 318:
			case 319:
			case 320:
			case 322:
			case 323:
			case 324:
			case 325:
			case 327:
			case 328:
			case 333:
			case 335:
			case 336:
			case 337:
			case 343:
			case 349:
			case 353:
			case 355:
			case 356:
			case 363:
			case 364:
			case 365:
			case 367:
			case 371:
			case 376:
			case 378:
			case 379:
			case 380:
			case 382:
			case 386:
			case 391:
			case 392:
			case 393:
			case 394:
			case 396:
			case 400:
			case 401:
			case 403:
			case 405:
			case 407:
			case 408:
			case 411:
			case 415:
			case 418:
			case 422:
			case 424:
			case 425:
			case 426:
				enterOuterAlt(_localctx, 2);
				{
				setState(250); alfanumrubrieknummer();
				}
				break;
			case 4:
			case 5:
			case 6:
			case 14:
			case 15:
			case 16:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 24:
			case 31:
			case 32:
			case 36:
			case 39:
			case 41:
			case 45:
			case 50:
			case 62:
			case 63:
			case 65:
			case 67:
			case 69:
			case 72:
			case 74:
			case 75:
			case 76:
			case 82:
			case 86:
			case 88:
			case 89:
			case 93:
			case 99:
			case 102:
			case 107:
			case 108:
			case 111:
			case 116:
			case 121:
			case 126:
			case 128:
			case 138:
			case 139:
			case 141:
			case 143:
			case 149:
			case 150:
			case 151:
			case 154:
			case 162:
			case 166:
			case 171:
			case 173:
			case 174:
			case 176:
			case 182:
			case 187:
			case 189:
			case 193:
			case 194:
			case 195:
			case 196:
			case 197:
			case 200:
			case 202:
			case 219:
			case 220:
			case 223:
			case 224:
			case 229:
			case 231:
			case 232:
			case 236:
			case 237:
			case 238:
			case 242:
			case 243:
			case 244:
			case 250:
			case 252:
			case 255:
			case 257:
			case 258:
			case 260:
			case 262:
			case 265:
			case 269:
			case 273:
			case 277:
			case 279:
			case 280:
			case 281:
			case 283:
			case 284:
			case 285:
			case 290:
			case 292:
			case 293:
			case 297:
			case 298:
			case 300:
			case 301:
			case 305:
			case 308:
			case 314:
			case 330:
			case 334:
			case 340:
			case 341:
			case 342:
			case 344:
			case 347:
			case 350:
			case 351:
			case 358:
			case 359:
			case 361:
			case 362:
			case 368:
			case 369:
			case 370:
			case 372:
			case 377:
			case 383:
			case 385:
			case 390:
			case 395:
			case 397:
			case 399:
			case 404:
			case 406:
			case 409:
			case 414:
			case 416:
			case 417:
			case 419:
			case 420:
			case 421:
			case 427:
				enterOuterAlt(_localctx, 3);
				{
				setState(251); datrubrieknummer();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AlfanumrubrieknummerContext extends ParserRuleContext {
		public AlfanumrubrieknummerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alfanumrubrieknummer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitAlfanumrubrieknummer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlfanumrubrieknummerContext alfanumrubrieknummer() throws RecognitionException {
		AlfanumrubrieknummerContext _localctx = new AlfanumrubrieknummerContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_alfanumrubrieknummer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 7) | (1L << 9) | (1L << 10) | (1L << 12) | (1L << 23) | (1L << 29) | (1L << 34) | (1L << 35) | (1L << 38) | (1L << 40) | (1L << 42) | (1L << 43) | (1L << 48) | (1L << 52) | (1L << 53) | (1L << 54) | (1L << 55) | (1L << 57) | (1L << 61))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (64 - 64)) | (1L << (68 - 64)) | (1L << (70 - 64)) | (1L << (77 - 64)) | (1L << (78 - 64)) | (1L << (81 - 64)) | (1L << (85 - 64)) | (1L << (87 - 64)) | (1L << (94 - 64)) | (1L << (95 - 64)) | (1L << (96 - 64)) | (1L << (97 - 64)) | (1L << (98 - 64)) | (1L << (101 - 64)) | (1L << (103 - 64)) | (1L << (104 - 64)) | (1L << (106 - 64)) | (1L << (112 - 64)) | (1L << (114 - 64)) | (1L << (115 - 64)) | (1L << (118 - 64)) | (1L << (122 - 64)))) != 0) || ((((_la - 129)) & ~0x3f) == 0 && ((1L << (_la - 129)) & ((1L << (129 - 129)) | (1L << (131 - 129)) | (1L << (134 - 129)) | (1L << (136 - 129)) | (1L << (137 - 129)) | (1L << (142 - 129)) | (1L << (145 - 129)) | (1L << (148 - 129)) | (1L << (152 - 129)) | (1L << (153 - 129)) | (1L << (158 - 129)) | (1L << (159 - 129)) | (1L << (160 - 129)) | (1L << (161 - 129)) | (1L << (163 - 129)) | (1L << (165 - 129)) | (1L << (167 - 129)) | (1L << (168 - 129)) | (1L << (169 - 129)) | (1L << (172 - 129)) | (1L << (175 - 129)) | (1L << (177 - 129)) | (1L << (183 - 129)) | (1L << (184 - 129)) | (1L << (185 - 129)) | (1L << (188 - 129)) | (1L << (190 - 129)))) != 0) || ((((_la - 201)) & ~0x3f) == 0 && ((1L << (_la - 201)) & ((1L << (201 - 201)) | (1L << (203 - 201)) | (1L << (205 - 201)) | (1L << (210 - 201)) | (1L << (212 - 201)) | (1L << (213 - 201)) | (1L << (215 - 201)) | (1L << (217 - 201)) | (1L << (221 - 201)) | (1L << (222 - 201)) | (1L << (225 - 201)) | (1L << (227 - 201)) | (1L << (230 - 201)) | (1L << (233 - 201)) | (1L << (234 - 201)) | (1L << (235 - 201)) | (1L << (240 - 201)) | (1L << (241 - 201)) | (1L << (247 - 201)) | (1L << (248 - 201)) | (1L << (253 - 201)) | (1L << (254 - 201)) | (1L << (259 - 201)) | (1L << (264 - 201)))) != 0) || ((((_la - 274)) & ~0x3f) == 0 && ((1L << (_la - 274)) & ((1L << (274 - 274)) | (1L << (275 - 274)) | (1L << (278 - 274)) | (1L << (287 - 274)) | (1L << (288 - 274)) | (1L << (299 - 274)) | (1L << (304 - 274)) | (1L << (307 - 274)) | (1L << (309 - 274)) | (1L << (311 - 274)) | (1L << (312 - 274)) | (1L << (313 - 274)) | (1L << (315 - 274)) | (1L << (317 - 274)) | (1L << (318 - 274)) | (1L << (319 - 274)) | (1L << (320 - 274)) | (1L << (322 - 274)) | (1L << (323 - 274)) | (1L << (324 - 274)) | (1L << (325 - 274)) | (1L << (327 - 274)) | (1L << (328 - 274)) | (1L << (333 - 274)) | (1L << (335 - 274)) | (1L << (336 - 274)) | (1L << (337 - 274)))) != 0) || ((((_la - 343)) & ~0x3f) == 0 && ((1L << (_la - 343)) & ((1L << (343 - 343)) | (1L << (349 - 343)) | (1L << (353 - 343)) | (1L << (355 - 343)) | (1L << (356 - 343)) | (1L << (363 - 343)) | (1L << (364 - 343)) | (1L << (365 - 343)) | (1L << (367 - 343)) | (1L << (371 - 343)) | (1L << (376 - 343)) | (1L << (378 - 343)) | (1L << (379 - 343)) | (1L << (380 - 343)) | (1L << (382 - 343)) | (1L << (386 - 343)) | (1L << (391 - 343)) | (1L << (392 - 343)) | (1L << (393 - 343)) | (1L << (394 - 343)) | (1L << (396 - 343)) | (1L << (400 - 343)) | (1L << (401 - 343)) | (1L << (403 - 343)) | (1L << (405 - 343)))) != 0) || ((((_la - 407)) & ~0x3f) == 0 && ((1L << (_la - 407)) & ((1L << (407 - 407)) | (1L << (408 - 407)) | (1L << (411 - 407)) | (1L << (415 - 407)) | (1L << (418 - 407)) | (1L << (422 - 407)) | (1L << (424 - 407)) | (1L << (425 - 407)) | (1L << (426 - 407)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DatrubrieknummerContext extends ParserRuleContext {
		public DatrubrieknummerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_datrubrieknummer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitDatrubrieknummer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DatrubrieknummerContext datrubrieknummer() throws RecognitionException {
		DatrubrieknummerContext _localctx = new DatrubrieknummerContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_datrubrieknummer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 4) | (1L << 5) | (1L << 6) | (1L << 14) | (1L << 15) | (1L << 16) | (1L << 18) | (1L << 19) | (1L << 20) | (1L << 21) | (1L << 22) | (1L << 24) | (1L << 31) | (1L << 32) | (1L << 36) | (1L << 39) | (1L << 41) | (1L << 45) | (1L << 50) | (1L << 62) | (1L << 63))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (65 - 65)) | (1L << (67 - 65)) | (1L << (69 - 65)) | (1L << (72 - 65)) | (1L << (74 - 65)) | (1L << (75 - 65)) | (1L << (76 - 65)) | (1L << (82 - 65)) | (1L << (86 - 65)) | (1L << (88 - 65)) | (1L << (89 - 65)) | (1L << (93 - 65)) | (1L << (99 - 65)) | (1L << (102 - 65)) | (1L << (107 - 65)) | (1L << (108 - 65)) | (1L << (111 - 65)) | (1L << (116 - 65)) | (1L << (121 - 65)) | (1L << (126 - 65)) | (1L << (128 - 65)))) != 0) || ((((_la - 138)) & ~0x3f) == 0 && ((1L << (_la - 138)) & ((1L << (138 - 138)) | (1L << (139 - 138)) | (1L << (141 - 138)) | (1L << (143 - 138)) | (1L << (149 - 138)) | (1L << (150 - 138)) | (1L << (151 - 138)) | (1L << (154 - 138)) | (1L << (162 - 138)) | (1L << (166 - 138)) | (1L << (171 - 138)) | (1L << (173 - 138)) | (1L << (174 - 138)) | (1L << (176 - 138)) | (1L << (182 - 138)) | (1L << (187 - 138)) | (1L << (189 - 138)) | (1L << (193 - 138)) | (1L << (194 - 138)) | (1L << (195 - 138)) | (1L << (196 - 138)) | (1L << (197 - 138)) | (1L << (200 - 138)))) != 0) || ((((_la - 202)) & ~0x3f) == 0 && ((1L << (_la - 202)) & ((1L << (202 - 202)) | (1L << (219 - 202)) | (1L << (220 - 202)) | (1L << (223 - 202)) | (1L << (224 - 202)) | (1L << (229 - 202)) | (1L << (231 - 202)) | (1L << (232 - 202)) | (1L << (236 - 202)) | (1L << (237 - 202)) | (1L << (238 - 202)) | (1L << (242 - 202)) | (1L << (243 - 202)) | (1L << (244 - 202)) | (1L << (250 - 202)) | (1L << (252 - 202)) | (1L << (255 - 202)) | (1L << (257 - 202)) | (1L << (258 - 202)) | (1L << (260 - 202)) | (1L << (262 - 202)) | (1L << (265 - 202)))) != 0) || ((((_la - 269)) & ~0x3f) == 0 && ((1L << (_la - 269)) & ((1L << (269 - 269)) | (1L << (273 - 269)) | (1L << (277 - 269)) | (1L << (279 - 269)) | (1L << (280 - 269)) | (1L << (281 - 269)) | (1L << (283 - 269)) | (1L << (284 - 269)) | (1L << (285 - 269)) | (1L << (290 - 269)) | (1L << (292 - 269)) | (1L << (293 - 269)) | (1L << (297 - 269)) | (1L << (298 - 269)) | (1L << (300 - 269)) | (1L << (301 - 269)) | (1L << (305 - 269)) | (1L << (308 - 269)) | (1L << (314 - 269)) | (1L << (330 - 269)))) != 0) || ((((_la - 334)) & ~0x3f) == 0 && ((1L << (_la - 334)) & ((1L << (334 - 334)) | (1L << (340 - 334)) | (1L << (341 - 334)) | (1L << (342 - 334)) | (1L << (344 - 334)) | (1L << (347 - 334)) | (1L << (350 - 334)) | (1L << (351 - 334)) | (1L << (358 - 334)) | (1L << (359 - 334)) | (1L << (361 - 334)) | (1L << (362 - 334)) | (1L << (368 - 334)) | (1L << (369 - 334)) | (1L << (370 - 334)) | (1L << (372 - 334)) | (1L << (377 - 334)) | (1L << (383 - 334)) | (1L << (385 - 334)) | (1L << (390 - 334)) | (1L << (395 - 334)) | (1L << (397 - 334)))) != 0) || ((((_la - 399)) & ~0x3f) == 0 && ((1L << (_la - 399)) & ((1L << (399 - 399)) | (1L << (404 - 399)) | (1L << (406 - 399)) | (1L << (409 - 399)) | (1L << (414 - 399)) | (1L << (416 - 399)) | (1L << (417 - 399)) | (1L << (419 - 399)) | (1L << (420 - 399)) | (1L << (421 - 399)) | (1L << (427 - 399)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumrubrieknummerContext extends ParserRuleContext {
		public NumrubrieknummerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numrubrieknummer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GBAVoorwaarderegelVisitor ) return ((GBAVoorwaarderegelVisitor<? extends T>)visitor).visitNumrubrieknummer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumrubrieknummerContext numrubrieknummer() throws RecognitionException {
		NumrubrieknummerContext _localctx = new NumrubrieknummerContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_numrubrieknummer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << 2) | (1L << 8) | (1L << 11) | (1L << 13) | (1L << 25) | (1L << 26) | (1L << 27) | (1L << 28) | (1L << 30) | (1L << 33) | (1L << 37) | (1L << 44) | (1L << 46) | (1L << 47) | (1L << 49) | (1L << 51) | (1L << 56) | (1L << 58) | (1L << 59) | (1L << 60))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (66 - 66)) | (1L << (71 - 66)) | (1L << (79 - 66)) | (1L << (80 - 66)) | (1L << (83 - 66)) | (1L << (84 - 66)) | (1L << (90 - 66)) | (1L << (91 - 66)) | (1L << (92 - 66)) | (1L << (105 - 66)) | (1L << (109 - 66)) | (1L << (110 - 66)) | (1L << (113 - 66)) | (1L << (117 - 66)) | (1L << (119 - 66)) | (1L << (120 - 66)) | (1L << (123 - 66)) | (1L << (124 - 66)) | (1L << (125 - 66)) | (1L << (127 - 66)))) != 0) || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (130 - 130)) | (1L << (132 - 130)) | (1L << (133 - 130)) | (1L << (135 - 130)) | (1L << (140 - 130)) | (1L << (144 - 130)) | (1L << (146 - 130)) | (1L << (147 - 130)) | (1L << (155 - 130)) | (1L << (156 - 130)) | (1L << (157 - 130)) | (1L << (164 - 130)) | (1L << (170 - 130)) | (1L << (178 - 130)) | (1L << (179 - 130)) | (1L << (180 - 130)) | (1L << (181 - 130)) | (1L << (186 - 130)) | (1L << (191 - 130)) | (1L << (192 - 130)))) != 0) || ((((_la - 198)) & ~0x3f) == 0 && ((1L << (_la - 198)) & ((1L << (198 - 198)) | (1L << (199 - 198)) | (1L << (204 - 198)) | (1L << (206 - 198)) | (1L << (207 - 198)) | (1L << (208 - 198)) | (1L << (209 - 198)) | (1L << (211 - 198)) | (1L << (214 - 198)) | (1L << (216 - 198)) | (1L << (218 - 198)) | (1L << (226 - 198)) | (1L << (228 - 198)) | (1L << (239 - 198)) | (1L << (245 - 198)) | (1L << (246 - 198)) | (1L << (249 - 198)) | (1L << (251 - 198)) | (1L << (256 - 198)) | (1L << (261 - 198)))) != 0) || ((((_la - 266)) & ~0x3f) == 0 && ((1L << (_la - 266)) & ((1L << (266 - 266)) | (1L << (267 - 266)) | (1L << (268 - 266)) | (1L << (270 - 266)) | (1L << (271 - 266)) | (1L << (272 - 266)) | (1L << (276 - 266)) | (1L << (282 - 266)) | (1L << (286 - 266)) | (1L << (289 - 266)) | (1L << (291 - 266)) | (1L << (294 - 266)) | (1L << (295 - 266)) | (1L << (296 - 266)) | (1L << (302 - 266)) | (1L << (303 - 266)) | (1L << (306 - 266)) | (1L << (316 - 266)) | (1L << (321 - 266)) | (1L << (326 - 266)) | (1L << (329 - 266)))) != 0) || ((((_la - 331)) & ~0x3f) == 0 && ((1L << (_la - 331)) & ((1L << (331 - 331)) | (1L << (332 - 331)) | (1L << (338 - 331)) | (1L << (339 - 331)) | (1L << (345 - 331)) | (1L << (346 - 331)) | (1L << (348 - 331)) | (1L << (352 - 331)) | (1L << (354 - 331)) | (1L << (357 - 331)) | (1L << (360 - 331)) | (1L << (366 - 331)) | (1L << (373 - 331)) | (1L << (374 - 331)) | (1L << (375 - 331)) | (1L << (381 - 331)) | (1L << (384 - 331)) | (1L << (387 - 331)) | (1L << (388 - 331)) | (1L << (389 - 331)))) != 0) || ((((_la - 398)) & ~0x3f) == 0 && ((1L << (_la - 398)) & ((1L << (398 - 398)) | (1L << (402 - 398)) | (1L << (410 - 398)) | (1L << (412 - 398)) | (1L << (413 - 398)) | (1L << (423 - 398)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\u01c6\u0107\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\3\2\3\2\3\2\5\2S\n\2\3"+
		"\3\3\3\3\3\3\3\3\3\5\3Z\n\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\7\5d\n\5\f"+
		"\5\16\5g\13\5\3\6\3\6\5\6k\n\6\3\7\3\7\3\7\3\7\5\7q\n\7\3\b\3\b\5\bu\n"+
		"\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3"+
		"\r\3\r\3\r\7\r\u008a\n\r\f\r\16\r\u008d\13\r\3\16\3\16\5\16\u0091\n\16"+
		"\3\17\6\17\u0094\n\17\r\17\16\17\u0095\3\20\3\20\3\20\5\20\u009b\n\20"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\5\24\u00bb\n\24\3\25\3\25\5\25\u00bf\n\25\3\26\3\26\3\26\3"+
		"\26\3\26\5\26\u00c6\n\26\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31"+
		"\5\31\u00d1\n\31\3\31\3\31\3\32\6\32\u00d6\n\32\r\32\16\32\u00d7\3\33"+
		"\3\33\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\5\36\u00e7"+
		"\n\36\3\37\3\37\3\37\3\37\5\37\u00ed\n\37\3 \3 \3 \3 \3!\3!\3!\3!\3\""+
		"\3\"\3\"\3#\3#\3$\3$\3$\5$\u00ff\n$\3%\3%\3&\3&\3\'\3\'\3\'\2(\2\4\6\b"+
		"\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJL\2\13\3"+
		"\2\u01ba\u01bb\3\2\u01bc\u01bd\3\2\u01c0\u01c1\3\2\u01c2\u01c3\3\2\u01ae"+
		"\u01b1\3\2\u01be\u01bff\2\5\5\t\t\13\f\16\16\31\31\37\37$%((**,-\62\62"+
		"\669;;??BBFFHHOPSSWWYY`dggijllrrtuxx||\u0083\u0083\u0085\u0085\u0088\u0088"+
		"\u008a\u008b\u0090\u0090\u0093\u0093\u0096\u0096\u009a\u009b\u00a0\u00a3"+
		"\u00a5\u00a5\u00a7\u00a7\u00a9\u00ab\u00ae\u00ae\u00b1\u00b1\u00b3\u00b3"+
		"\u00b9\u00bb\u00be\u00be\u00c0\u00c0\u00cb\u00cb\u00cd\u00cd\u00cf\u00cf"+
		"\u00d4\u00d4\u00d6\u00d7\u00d9\u00d9\u00db\u00db\u00df\u00e0\u00e3\u00e3"+
		"\u00e5\u00e5\u00e8\u00e8\u00eb\u00ed\u00f2\u00f3\u00f9\u00fa\u00ff\u0100"+
		"\u0105\u0105\u010a\u010a\u0114\u0115\u0118\u0118\u0121\u0122\u012d\u012d"+
		"\u0132\u0132\u0135\u0135\u0137\u0137\u0139\u013b\u013d\u013d\u013f\u0142"+
		"\u0144\u0147\u0149\u014a\u014f\u014f\u0151\u0153\u0159\u0159\u015f\u015f"+
		"\u0163\u0163\u0165\u0166\u016d\u016f\u0171\u0171\u0175\u0175\u017a\u017a"+
		"\u017c\u017e\u0180\u0180\u0184\u0184\u0189\u018c\u018e\u018e\u0192\u0193"+
		"\u0195\u0195\u0197\u0197\u0199\u019a\u019d\u019d\u01a1\u01a1\u01a4\u01a4"+
		"\u01a8\u01a8\u01aa\u01ac_\2\6\b\20\22\24\30\32\32!\"&&))++//\64\64@AC"+
		"CEEGGJJLNTTXXZ[__eehhmnqqvv{{\u0080\u0080\u0082\u0082\u008c\u008d\u008f"+
		"\u008f\u0091\u0091\u0097\u0099\u009c\u009c\u00a4\u00a4\u00a8\u00a8\u00ad"+
		"\u00ad\u00af\u00b0\u00b2\u00b2\u00b8\u00b8\u00bd\u00bd\u00bf\u00bf\u00c3"+
		"\u00c7\u00ca\u00ca\u00cc\u00cc\u00dd\u00de\u00e1\u00e2\u00e7\u00e7\u00e9"+
		"\u00ea\u00ee\u00f0\u00f4\u00f6\u00fc\u00fc\u00fe\u00fe\u0101\u0101\u0103"+
		"\u0104\u0106\u0106\u0108\u0108\u010b\u010b\u010f\u010f\u0113\u0113\u0117"+
		"\u0117\u0119\u011b\u011d\u011f\u0124\u0124\u0126\u0127\u012b\u012c\u012e"+
		"\u012f\u0133\u0133\u0136\u0136\u013c\u013c\u014c\u014c\u0150\u0150\u0156"+
		"\u0158\u015a\u015a\u015d\u015d\u0160\u0161\u0168\u0169\u016b\u016c\u0172"+
		"\u0174\u0176\u0176\u017b\u017b\u0181\u0181\u0183\u0183\u0188\u0188\u018d"+
		"\u018d\u018f\u018f\u0191\u0191\u0196\u0196\u0198\u0198\u019b\u019b\u01a0"+
		"\u01a0\u01a2\u01a3\u01a5\u01a7\u01ad\u01adW\2\3\4\n\n\r\r\17\17\33\36"+
		"  ##\'\'..\60\61\63\63\65\65::<>DDIIQRUV\\^kkopsswwyz}\177\u0081\u0081"+
		"\u0084\u0084\u0086\u0087\u0089\u0089\u008e\u008e\u0092\u0092\u0094\u0095"+
		"\u009d\u009f\u00a6\u00a6\u00ac\u00ac\u00b4\u00b7\u00bc\u00bc\u00c1\u00c2"+
		"\u00c8\u00c9\u00ce\u00ce\u00d0\u00d3\u00d5\u00d5\u00d8\u00d8\u00da\u00da"+
		"\u00dc\u00dc\u00e4\u00e4\u00e6\u00e6\u00f1\u00f1\u00f7\u00f8\u00fb\u00fb"+
		"\u00fd\u00fd\u0102\u0102\u0107\u0107\u010c\u010e\u0110\u0112\u0116\u0116"+
		"\u011c\u011c\u0120\u0120\u0123\u0123\u0125\u0125\u0128\u012a\u0130\u0131"+
		"\u0134\u0134\u013e\u013e\u0143\u0143\u0148\u0148\u014b\u014b\u014d\u014e"+
		"\u0154\u0155\u015b\u015c\u015e\u015e\u0162\u0162\u0164\u0164\u0167\u0167"+
		"\u016a\u016a\u0170\u0170\u0177\u0179\u017f\u017f\u0182\u0182\u0185\u0187"+
		"\u0190\u0190\u0194\u0194\u019c\u019c\u019e\u019f\u01a9\u01a9\u00ff\2N"+
		"\3\2\2\2\4Y\3\2\2\2\6[\3\2\2\2\b_\3\2\2\2\nj\3\2\2\2\fl\3\2\2\2\16t\3"+
		"\2\2\2\20v\3\2\2\2\22\177\3\2\2\2\24\u0081\3\2\2\2\26\u0083\3\2\2\2\30"+
		"\u0085\3\2\2\2\32\u0090\3\2\2\2\34\u0093\3\2\2\2\36\u009a\3\2\2\2 \u009c"+
		"\3\2\2\2\"\u00a5\3\2\2\2$\u00ac\3\2\2\2&\u00ba\3\2\2\2(\u00be\3\2\2\2"+
		"*\u00c5\3\2\2\2,\u00c7\3\2\2\2.\u00c9\3\2\2\2\60\u00cd\3\2\2\2\62\u00d5"+
		"\3\2\2\2\64\u00d9\3\2\2\2\66\u00db\3\2\2\28\u00dd\3\2\2\2:\u00e6\3\2\2"+
		"\2<\u00e8\3\2\2\2>\u00ee\3\2\2\2@\u00f2\3\2\2\2B\u00f6\3\2\2\2D\u00f9"+
		"\3\2\2\2F\u00fe\3\2\2\2H\u0100\3\2\2\2J\u0102\3\2\2\2L\u0104\3\2\2\2N"+
		"R\5\4\3\2OP\5\24\13\2PQ\5\2\2\2QS\3\2\2\2RO\3\2\2\2RS\3\2\2\2S\3\3\2\2"+
		"\2TZ\5:\36\2UZ\58\35\2VZ\5B\"\2WZ\5.\30\2XZ\5\6\4\2YT\3\2\2\2YU\3\2\2"+
		"\2YV\3\2\2\2YW\3\2\2\2YX\3\2\2\2Z\5\3\2\2\2[\\\7K\2\2\\]\5\2\2\2]^\7\23"+
		"\2\2^\7\3\2\2\2_e\5\n\6\2`a\5\22\n\2ab\5\n\6\2bd\3\2\2\2c`\3\2\2\2dg\3"+
		"\2\2\2ec\3\2\2\2ef\3\2\2\2f\t\3\2\2\2ge\3\2\2\2hk\5H%\2ik\5\60\31\2jh"+
		"\3\2\2\2ji\3\2\2\2k\13\3\2\2\2lp\5\16\b\2mn\5\26\f\2no\5\36\20\2oq\3\2"+
		"\2\2pm\3\2\2\2pq\3\2\2\2q\r\3\2\2\2ru\5J&\2su\5\20\t\2tr\3\2\2\2ts\3\2"+
		"\2\2u\17\3\2\2\2vw\7\u01c2\2\2wx\7\u01c2\2\2xy\7\u01c2\2\2yz\7\u01c2\2"+
		"\2z{\7\u01c2\2\2{|\7\u01c2\2\2|}\7\u01c2\2\2}~\7\u01c2\2\2~\21\3\2\2\2"+
		"\177\u0080\t\2\2\2\u0080\23\3\2\2\2\u0081\u0082\t\3\2\2\u0082\25\3\2\2"+
		"\2\u0083\u0084\t\4\2\2\u0084\27\3\2\2\2\u0085\u008b\5\32\16\2\u0086\u0087"+
		"\5\22\n\2\u0087\u0088\5\32\16\2\u0088\u008a\3\2\2\2\u0089\u0086\3\2\2"+
		"\2\u008a\u008d\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\31"+
		"\3\2\2\2\u008d\u008b\3\2\2\2\u008e\u0091\5L\'\2\u008f\u0091\5\34\17\2"+
		"\u0090\u008e\3\2\2\2\u0090\u008f\3\2\2\2\u0091\33\3\2\2\2\u0092\u0094"+
		"\7\u01c2\2\2\u0093\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0093\3\2\2"+
		"\2\u0095\u0096\3\2\2\2\u0096\35\3\2\2\2\u0097\u009b\5 \21\2\u0098\u009b"+
		"\5\"\22\2\u0099\u009b\5$\23\2\u009a\u0097\3\2\2\2\u009a\u0098\3\2\2\2"+
		"\u009a\u0099\3\2\2\2\u009b\37\3\2\2\2\u009c\u009d\7\u01c2\2\2\u009d\u009e"+
		"\7\u01c2\2\2\u009e\u009f\7\u01c2\2\2\u009f\u00a0\7\u01c2\2\2\u00a0\u00a1"+
		"\7\u01c2\2\2\u00a1\u00a2\7\u01c2\2\2\u00a2\u00a3\7\u01c2\2\2\u00a3\u00a4"+
		"\7\u01c2\2\2\u00a4!\3\2\2\2\u00a5\u00a6\7\u01c2\2\2\u00a6\u00a7\7\u01c2"+
		"\2\2\u00a7\u00a8\7\u01c2\2\2\u00a8\u00a9\7\u01c2\2\2\u00a9\u00aa\7\u01c2"+
		"\2\2\u00aa\u00ab\7\u01c2\2\2\u00ab#\3\2\2\2\u00ac\u00ad\7\u01c2\2\2\u00ad"+
		"\u00ae\7\u01c2\2\2\u00ae\u00af\7\u01c2\2\2\u00af\u00b0\7\u01c2\2\2\u00b0"+
		"%\3\2\2\2\u00b1\u00bb\7\u01b2\2\2\u00b2\u00bb\7\u01b3\2\2\u00b3\u00bb"+
		"\7\u01b4\2\2\u00b4\u00bb\7\u01b5\2\2\u00b5\u00bb\7\u01b6\2\2\u00b6\u00bb"+
		"\7\u01b7\2\2\u00b7\u00bb\7\u01b8\2\2\u00b8\u00bb\7\u01b9\2\2\u00b9\u00bb"+
		"\5\64\33\2\u00ba\u00b1\3\2\2\2\u00ba\u00b2\3\2\2\2\u00ba\u00b3\3\2\2\2"+
		"\u00ba\u00b4\3\2\2\2\u00ba\u00b5\3\2\2\2\u00ba\u00b6\3\2\2\2\u00ba\u00b7"+
		"\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00b9\3\2\2\2\u00bb\'\3\2\2\2\u00bc"+
		"\u00bf\5,\27\2\u00bd\u00bf\5J&\2\u00be\u00bc\3\2\2\2\u00be\u00bd\3\2\2"+
		"\2\u00bf)\3\2\2\2\u00c0\u00c6\5\f\7\2\u00c1\u00c2\5,\27\2\u00c2\u00c3"+
		"\5\26\f\2\u00c3\u00c4\5\36\20\2\u00c4\u00c6\3\2\2\2\u00c5\u00c0\3\2\2"+
		"\2\u00c5\u00c1\3\2\2\2\u00c6+\3\2\2\2\u00c7\u00c8\7\u0109\2\2\u00c8-\3"+
		"\2\2\2\u00c9\u00ca\5(\25\2\u00ca\u00cb\5&\24\2\u00cb\u00cc\5*\26\2\u00cc"+
		"/\3\2\2\2\u00cd\u00ce\7f\2\2\u00ce\u00d0\5\62\32\2\u00cf\u00d1\7\u01c5"+
		"\2\2\u00d0\u00cf\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2"+
		"\u00d3\7f\2\2\u00d3\61\3\2\2\2\u00d4\u00d6\t\5\2\2\u00d5\u00d4\3\2\2\2"+
		"\u00d6\u00d7\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\63"+
		"\3\2\2\2\u00d9\u00da\t\6\2\2\u00da\65\3\2\2\2\u00db\u00dc\7\u0138\2\2"+
		"\u00dc\67\3\2\2\2\u00dd\u00de\5J&\2\u00de\u00df\5&\24\2\u00df\u00e0\5"+
		"\66\34\2\u00e0\u00e1\5\26\f\2\u00e1\u00e2\5\36\20\2\u00e29\3\2\2\2\u00e3"+
		"\u00e7\5<\37\2\u00e4\u00e7\5> \2\u00e5\u00e7\5@!\2\u00e6\u00e3\3\2\2\2"+
		"\u00e6\u00e4\3\2\2\2\u00e6\u00e5\3\2\2\2\u00e7;\3\2\2\2\u00e8\u00e9\5"+
		"L\'\2\u00e9\u00ec\5\64\33\2\u00ea\u00ed\5\30\r\2\u00eb\u00ed\5H%\2\u00ec"+
		"\u00ea\3\2\2\2\u00ec\u00eb\3\2\2\2\u00ed=\3\2\2\2\u00ee\u00ef\5H%\2\u00ef"+
		"\u00f0\5\64\33\2\u00f0\u00f1\5\b\5\2\u00f1?\3\2\2\2\u00f2\u00f3\5J&\2"+
		"\u00f3\u00f4\5&\24\2\u00f4\u00f5\5\f\7\2\u00f5A\3\2\2\2\u00f6\u00f7\5"+
		"D#\2\u00f7\u00f8\5F$\2\u00f8C\3\2\2\2\u00f9\u00fa\t\7\2\2\u00faE\3\2\2"+
		"\2\u00fb\u00ff\5L\'\2\u00fc\u00ff\5H%\2\u00fd\u00ff\5J&\2\u00fe\u00fb"+
		"\3\2\2\2\u00fe\u00fc\3\2\2\2\u00fe\u00fd\3\2\2\2\u00ffG\3\2\2\2\u0100"+
		"\u0101\t\b\2\2\u0101I\3\2\2\2\u0102\u0103\t\t\2\2\u0103K\3\2\2\2\u0104"+
		"\u0105\t\n\2\2\u0105M\3\2\2\2\24RYejpt\u008b\u0090\u0095\u009a\u00ba\u00be"+
		"\u00c5\u00d0\u00d7\u00e6\u00ec\u00fe";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}