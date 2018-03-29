myApp
	.factory('tidyService', ['$filter', function($filter) {

		function getHeaderLength(berichtnummer) {
			switch (berichtnummer) {
				case 'LG01':
					return LG01_HEADER_LENGTH;
				case 'GV01':
				case 'GV02':
					return GV01_HEADER_LENGTH;
				case 'AG11':
				case 'AG21':
				case 'AG31':
					return AG_HEADER_LENGTH;
				case 'NG01':
					return NG_HEADER_LENGTH;
				case 'WA11':
					return WA_HEADER_LENGTH;
				default:
					return 0;
			}
		}
		 
		function assembleHeader(type, params) {
			var header;
			type = type.toUpperCase();
			switch (type) {
				case 'LG01':
					header = LG01_BERICHT_HEADER;
					break;
				case 'GV01':
				case 'GV02':
					header = GV01_BERICHT_HEADER;
					break;
				case 'AG11':
				case 'AG21':
				case 'AG31':
					header = AG_BERICHT_HEADER;
					break;
				case 'NG01':
					header = NG_BERICHT_HEADER;
					break;
				case 'WA11':
					header = WA_BERICHT_HEADER;
					break;
				default:
					break;
			}
			
			var headerMessage = "";
			for (key in header) {	
				switch (key) {
					case 'random':
						headerMessage += "00000000";
						break;
					case 'anr':
						headerMessage += zeroFill(params.anr, BERICHT_HEADER_ANR_LENGTH);
						break;						
					case 'oldanr':	
						headerMessage += zeroFill(params.oldanr, BERICHT_HEADER_ANR_LENGTH);
						break;						
					case 'type':	
						headerMessage += type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
						break;						
					case 'datetime':
						headerMessage += $filter('date')(new Date(), 'yyyyMMddHHmmss') + "000";
						break;						
					case 'status':
						headerMessage += params.status ? params.status : 'A';
						break;						
					case 'date':	
						headerMessage += $filter('date')(new Date(), 'yyyyMMdd');
						break;
				}
			}
			
			return headerMessage;
		}
		
		function stripCharacters(inputMessage) {
			var message = inputMessage;
			message = message.replace(/^"/g, '');
			message = message.replace(/"$/g, '');
			message = message.replace(/^'/g, '');
			message = message.replace(/'$/g, '');
			message = message.replace(/\n/g, '');
			message = message.replace(/\r/g, '');
			return message;
		}
		
		/*
		 * parse and output gba messages
		 */
		function parse(inputMessage, skipIndex, groupHead, row, groupEnd) {
			var returnMessage = "";
			var message = stripCharacters(inputMessage);		
			message = message.substring(skipIndex);
			var walk = 0;
			var messageLength = message.substring(walk, (walk += BERICHT_TOTAL_LENGTH_LENGTH)) - 0;

			while (walk < messageLength) {
				var group = message.substring(walk, (walk += BERICHT_GROUP_LENGTH));
				var groupMessageLength = message.substring(walk, (walk += BERICHT_LENGTH_LENGTH)) - 0;
				var subwalk = walk;

				var isGroupShown = false;
				while (subwalk < (walk + groupMessageLength)) {
					var sub = message.substring(subwalk, (subwalk += BERICHT_SUB_LENGTH));
					var subMessageLength = message.substring(subwalk, (subwalk += BERICHT_LENGTH_LENGTH)) - 0;
					var subMessage = message.substring(subwalk, (subwalk += subMessageLength));
					// presentation
					if (!isGroupShown) {
						returnMessage += groupHead(group);
						isGroupShown = true;
					}
					returnMessage += row(group, sub, translateTeletexEncoding(subMessage));
				}

				returnMessage += groupEnd(group);
				walk += groupMessageLength;
			}
			
			return returnMessage;
		}

		function translateTeletexEncoding(message) {
			
			message = message.replace(new RegExp(String.fromCharCode(0xE7), "g"), "Ŀ");
			message = message.replace(new RegExp(String.fromCharCode(0xF7), "g"), "ŀ");
			message = message.replace(new RegExp(String.fromCharCode(0xA1), "g"), "¡");
			message = message.replace(new RegExp(String.fromCharCode(0xA2), "g"), "¢");
			message = message.replace(new RegExp(String.fromCharCode(0xE4), "g"), "Ħ");
			message = message.replace(new RegExp(String.fromCharCode(0xA3), "g"), "£");
			message = message.replace(new RegExp(String.fromCharCode(0xE8), "g"), "Ł");
			message = message.replace(new RegExp(String.fromCharCode(0xA5), "g"), "¥");
			message = message.replace(new RegExp(String.fromCharCode(0xE9), "g"), "Ø");
			message = message.replace(new RegExp(String.fromCharCode(0xA6), "g"), "#");
			message = message.replace(new RegExp(String.fromCharCode(0xEA), "g"), "Œ");
			message = message.replace(new RegExp(String.fromCharCode(0xA7), "g"), "§");
			
			message = message.replace(new RegExp(String.fromCharCode(0xEC), "g"), "Þ");
			message = message.replace(new RegExp(String.fromCharCode(0xAB), "g"), "«");
			message = message.replace(new RegExp(String.fromCharCode(0xED), "g"), "Ŧ");
			message = message.replace(new RegExp(String.fromCharCode(0xB0), "g"), "°");
			message = message.replace(new RegExp(String.fromCharCode(0xEE), "g"), "Ŋ");
			message = message.replace(new RegExp(String.fromCharCode(0xB1), "g"), "±");
			message = message.replace(new RegExp(String.fromCharCode(0xF0), "g"), "ĸ");
			message = message.replace(new RegExp(String.fromCharCode(0xF1), "g"), "æ");
			message = message.replace(new RegExp(String.fromCharCode(0xB4), "g"), "×");
			message = message.replace(new RegExp(String.fromCharCode(0xF2), "g"), "đ");
			message = message.replace(new RegExp(String.fromCharCode(0xB5), "g"), "μ");
			message = message.replace(new RegExp(String.fromCharCode(0xF3), "g"), "ð");
			message = message.replace(new RegExp(String.fromCharCode(0xF4), "g"), "ħ");
			message = message.replace(new RegExp(String.fromCharCode(0xB7), "g"), "·");
			message = message.replace(new RegExp(String.fromCharCode(0xF5), "g"), "ı");
			message = message.replace(new RegExp(String.fromCharCode(0xB8), "g"), "÷");
			message = message.replace(new RegExp(String.fromCharCode(0xBB), "g"), "»");
			message = message.replace(new RegExp(String.fromCharCode(0xF8), "g"), "ł");
			message = message.replace(new RegExp(String.fromCharCode(0xF9), "g"), "ø");
			message = message.replace(new RegExp(String.fromCharCode(0xFA), "g"), "œ");
			message = message.replace(new RegExp(String.fromCharCode(0xFB), "g"), "ß");
			message = message.replace(new RegExp(String.fromCharCode(0xBF), "g"), "¿");
			message = message.replace(new RegExp(String.fromCharCode(0xFC), "g"), "þ");
			message = message.replace(new RegExp(String.fromCharCode(0xE0), "g"), "Ω");
			message = message.replace(new RegExp(String.fromCharCode(0xFD), "g"), "ŧ");
			message = message.replace(new RegExp(String.fromCharCode(0xE1), "g"), "Æ");
			message = message.replace(new RegExp(String.fromCharCode(0xFE), "g"), "ŋ");
			message = message.replace(new RegExp(String.fromCharCode(0xE2), "g"), "Đ");
			
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x41), "g"), "Á");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x61), "g"), "á");
			message = message.replace(new RegExp(String.fromCharCode(0xC1) + String.fromCharCode(0x41), "g"), "À");
			message = message.replace(new RegExp(String.fromCharCode(0xC1) + String.fromCharCode(0x61), "g"), "à");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x41), "g"), "Â");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x61), "g"), "â");
			message = message.replace(new RegExp(String.fromCharCode(0xC8) + String.fromCharCode(0x41), "g"), "Ä");
			message = message.replace(new RegExp(String.fromCharCode(0xC8) + String.fromCharCode(0x61), "g"), "ä");
			message = message.replace(new RegExp(String.fromCharCode(0xC4) + String.fromCharCode(0x41), "g"), "Ã");
			message = message.replace(new RegExp(String.fromCharCode(0xC4) + String.fromCharCode(0x61), "g"), "ã");
			message = message.replace(new RegExp(String.fromCharCode(0xC6) + String.fromCharCode(0x41), "g"), "Ă");
			message = message.replace(new RegExp(String.fromCharCode(0xC6) + String.fromCharCode(0x61), "g"), "ă");
			message = message.replace(new RegExp(String.fromCharCode(0xCA) + String.fromCharCode(0x41), "g"), "Å");
			message = message.replace(new RegExp(String.fromCharCode(0xCA) + String.fromCharCode(0x61), "g"), "å");
			message = message.replace(new RegExp(String.fromCharCode(0xC5) + String.fromCharCode(0x41), "g"), "Ā");
			message = message.replace(new RegExp(String.fromCharCode(0xC5) + String.fromCharCode(0x61), "g"), "ā");
			message = message.replace(new RegExp(String.fromCharCode(0xCE) + String.fromCharCode(0x41), "g"), "Ą");
			message = message.replace(new RegExp(String.fromCharCode(0xCE) + String.fromCharCode(0x61), "g"), "ą");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x43), "g"), "Ć");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x63), "g"), "ć");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x43), "g"), "Ĉ");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x63), "g"), "ĉ");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x43), "g"), "Č");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x63), "g"), "č");
			message = message.replace(new RegExp(String.fromCharCode(0xC7) + String.fromCharCode(0x43), "g"), "Ċ");
			message = message.replace(new RegExp(String.fromCharCode(0xC7) + String.fromCharCode(0x63), "g"), "ċ");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x43), "g"), "Ç");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x63), "g"), "ç");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x44), "g"), "Ď");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x64), "g"), "ď");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x45), "g"), "É");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x65), "g"), "é");
			message = message.replace(new RegExp(String.fromCharCode(0xC1) + String.fromCharCode(0x45), "g"), "È");
			message = message.replace(new RegExp(String.fromCharCode(0xC1) + String.fromCharCode(0x65), "g"), "è");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x45), "g"), "Ê");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x65), "g"), "ê");
			message = message.replace(new RegExp(String.fromCharCode(0xC8) + String.fromCharCode(0x45), "g"), "Ë");
			message = message.replace(new RegExp(String.fromCharCode(0xC8) + String.fromCharCode(0x65), "g"), "ë");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x45), "g"), "Ě");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x65), "g"), "ě");
			message = message.replace(new RegExp(String.fromCharCode(0xC7) + String.fromCharCode(0x45), "g"), "Ė");
			message = message.replace(new RegExp(String.fromCharCode(0xC7) + String.fromCharCode(0x65), "g"), "ė");
			message = message.replace(new RegExp(String.fromCharCode(0xC5) + String.fromCharCode(0x45), "g"), "Ē");
			message = message.replace(new RegExp(String.fromCharCode(0xC5) + String.fromCharCode(0x65), "g"), "ē");
			message = message.replace(new RegExp(String.fromCharCode(0xCE) + String.fromCharCode(0x45), "g"), "Ę");
			message = message.replace(new RegExp(String.fromCharCode(0xCE) + String.fromCharCode(0x65), "g"), "ę");
			
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x67), "g"), "ģ");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x47), "g"), "Ĝ"); 
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x67), "g"), "ĝ"); 
			message = message.replace(new RegExp(String.fromCharCode(0xC6) + String.fromCharCode(0x47), "g"), "Ğ"); 
			message = message.replace(new RegExp(String.fromCharCode(0xC6) + String.fromCharCode(0x67), "g"), "ğ"); 
			message = message.replace(new RegExp(String.fromCharCode(0xC7) + String.fromCharCode(0x47), "g"), "Ġ");
			message = message.replace(new RegExp(String.fromCharCode(0xC7) + String.fromCharCode(0x67), "g"), "ġ");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x47), "g"), "Ģ");
			
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x48), "g"), "Ĥ");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x68), "g"), "ĥ");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x49), "g"), "Í");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x69), "g"), "í");
			message = message.replace(new RegExp(String.fromCharCode(0xC1) + String.fromCharCode(0x49), "g"), "Ì");
			message = message.replace(new RegExp(String.fromCharCode(0xC1) + String.fromCharCode(0x69), "g"), "ì");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x49), "g"), "Î");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x69), "g"), "î");
			message = message.replace(new RegExp(String.fromCharCode(0xC8) + String.fromCharCode(0x49), "g"), "Ï");
			message = message.replace(new RegExp(String.fromCharCode(0xC8) + String.fromCharCode(0x69), "g"), "ï");
			message = message.replace(new RegExp(String.fromCharCode(0xC4) + String.fromCharCode(0x49), "g"), "Ĩ");
			message = message.replace(new RegExp(String.fromCharCode(0xC4) + String.fromCharCode(0x69), "g"), "ĩ");
			message = message.replace(new RegExp(String.fromCharCode(0xC7) + String.fromCharCode(0x49), "g"), "İ");
			
			message = message.replace(new RegExp(String.fromCharCode(0xC5) + String.fromCharCode(0x49), "g"), "Ī");
			message = message.replace(new RegExp(String.fromCharCode(0xC5) + String.fromCharCode(0x69), "g"), "ī");
			message = message.replace(new RegExp(String.fromCharCode(0xCE) + String.fromCharCode(0x49), "g"), "Į");
			message = message.replace(new RegExp(String.fromCharCode(0xCE) + String.fromCharCode(0x69), "g"), "į");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x4A), "g"), "Ĵ");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x6A), "g"), "ĵ");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x4B), "g"), "Ķ");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x6B), "g"), "ķ");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x4C), "g"), "Ĺ");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x6C), "g"), "ĺ");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x4C), "g"), "Ľ");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x6C), "g"), "ľ");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x4C), "g"), "Ļ");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x6C), "g"), "ļ");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x4E), "g"), "Ń");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x6E), "g"), "ń");
			message = message.replace(new RegExp(String.fromCharCode(0xC4) + String.fromCharCode(0x4E), "g"), "Ñ");
			message = message.replace(new RegExp(String.fromCharCode(0xC4) + String.fromCharCode(0x6E), "g"), "ñ");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x4E), "g"), "Ň");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x6E), "g"), "ň");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x4E), "g"), "Ņ");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x6E), "g"), "ņ");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x4F), "g"), "Ó");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x6F), "g"), "ó");
			message = message.replace(new RegExp(String.fromCharCode(0xC1) + String.fromCharCode(0x4F), "g"), "Ò");
			message = message.replace(new RegExp(String.fromCharCode(0xC1) + String.fromCharCode(0x6F), "g"), "ò");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x4F), "g"), "Ô");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x6F), "g"), "ô");
			message = message.replace(new RegExp(String.fromCharCode(0xC8) + String.fromCharCode(0x4F), "g"), "Ö");
			message = message.replace(new RegExp(String.fromCharCode(0xC8) + String.fromCharCode(0x6F), "g"), "ö");
			message = message.replace(new RegExp(String.fromCharCode(0xC4) + String.fromCharCode(0x4F), "g"), "Õ");
			message = message.replace(new RegExp(String.fromCharCode(0xC4) + String.fromCharCode(0x6F), "g"), "õ");
			message = message.replace(new RegExp(String.fromCharCode(0xCD) + String.fromCharCode(0x4F), "g"), "Ő");
			message = message.replace(new RegExp(String.fromCharCode(0xCD) + String.fromCharCode(0x6F), "g"), "ő");
			message = message.replace(new RegExp(String.fromCharCode(0xC5) + String.fromCharCode(0x4F), "g"), "Ō");
			message = message.replace(new RegExp(String.fromCharCode(0xC5) + String.fromCharCode(0x6F), "g"), "ō");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x52), "g"), "Ŕ");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x72), "g"), "ŕ");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x52), "g"), "Ř");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x72), "g"), "ř");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x52), "g"), "Ŗ");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x72), "g"), "ŗ");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x53), "g"), "Ś");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x73), "g"), "ś");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x53), "g"), "Ŝ");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x73), "g"), "ŝ");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x53), "g"), "Š");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x73), "g"), "š");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x53), "g"), "Ş");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x73), "g"), "ş");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x54), "g"), "Ť");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x74), "g"), "ť");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x54), "g"), "Ţ");
			message = message.replace(new RegExp(String.fromCharCode(0xCB) + String.fromCharCode(0x74), "g"), "ţ");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x55), "g"), "Ú");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x75), "g"), "ú");
			message = message.replace(new RegExp(String.fromCharCode(0xC1) + String.fromCharCode(0x55), "g"), "Ù");
			message = message.replace(new RegExp(String.fromCharCode(0xC1) + String.fromCharCode(0x75), "g"), "ù");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x55), "g"), "Û");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x75), "g"), "û");
			message = message.replace(new RegExp(String.fromCharCode(0xC8) + String.fromCharCode(0x55), "g"), "Ü");
			message = message.replace(new RegExp(String.fromCharCode(0xC8) + String.fromCharCode(0x75), "g"), "ü");
			message = message.replace(new RegExp(String.fromCharCode(0xC4) + String.fromCharCode(0x55), "g"), "Ũ");
			message = message.replace(new RegExp(String.fromCharCode(0xC4) + String.fromCharCode(0x75), "g"), "ũ");
			message = message.replace(new RegExp(String.fromCharCode(0xC6) + String.fromCharCode(0x55), "g"), "Ŭ");
			message = message.replace(new RegExp(String.fromCharCode(0xC6) + String.fromCharCode(0x75), "g"), "ŭ");
			message = message.replace(new RegExp(String.fromCharCode(0xCD) + String.fromCharCode(0x55), "g"), "Ű");
			message = message.replace(new RegExp(String.fromCharCode(0xCD) + String.fromCharCode(0x75), "g"), "ű");
			message = message.replace(new RegExp(String.fromCharCode(0xCA) + String.fromCharCode(0x55), "g"), "Ů");
			message = message.replace(new RegExp(String.fromCharCode(0xCA) + String.fromCharCode(0x75), "g"), "ů");
			message = message.replace(new RegExp(String.fromCharCode(0xC5) + String.fromCharCode(0x55), "g"), "Ū");
			message = message.replace(new RegExp(String.fromCharCode(0xC5) + String.fromCharCode(0x75), "g"), "ū");
			message = message.replace(new RegExp(String.fromCharCode(0xCE) + String.fromCharCode(0x55), "g"), "Ų");
			message = message.replace(new RegExp(String.fromCharCode(0xCE) + String.fromCharCode(0x75), "g"), "ų");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x57), "g"), "Ŵ");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x77), "g"), "ŵ");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x59), "g"), "Ý");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x79), "g"), "ý");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x59), "g"), "Ŷ");
			message = message.replace(new RegExp(String.fromCharCode(0xC3) + String.fromCharCode(0x79), "g"), "ŷ");
			message = message.replace(new RegExp(String.fromCharCode(0xC8) + String.fromCharCode(0x59), "g"), "Ÿ");
			message = message.replace(new RegExp(String.fromCharCode(0xC8) + String.fromCharCode(0x79), "g"), "ÿ");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x5A), "g"), "Ź");
			message = message.replace(new RegExp(String.fromCharCode(0xC2) + String.fromCharCode(0x7A), "g"), "ź");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x5A), "g"), "Ž");
			message = message.replace(new RegExp(String.fromCharCode(0xCF) + String.fromCharCode(0x7A), "g"), "ž");
			message = message.replace(new RegExp(String.fromCharCode(0xC7) + String.fromCharCode(0x5A), "g"), "Ż");
			message = message.replace(new RegExp(String.fromCharCode(0xC7) + String.fromCharCode(0x7A), "g"), "ż");

			return message;
		}
		
		function translateTeletex(message) {
			message = message.replace(/È/g, String.fromCharCode(0xC1) + String.fromCharCode(0x45));
			message = message.replace(/Ä/g, String.fromCharCode(0xC8) + String.fromCharCode(0x41));
			message = message.replace(/Ë/g, String.fromCharCode(0xC8) + String.fromCharCode(0x45));
			message = message.replace(/Ç/g, String.fromCharCode(0xCB) + String.fromCharCode(0x43));
			message = message.replace(/Ã/g, String.fromCharCode(0xC4) + String.fromCharCode(0x41));
			message = message.replace(/Î/g, String.fromCharCode(0xC3) + String.fromCharCode(0x49));
			message = message.replace(/Â/g, String.fromCharCode(0xC3) + String.fromCharCode(0x41));
			message = message.replace(/Á/g, String.fromCharCode(0xC2) + String.fromCharCode(0x41));
			message = message.replace(/À/g, String.fromCharCode(0xC1) + String.fromCharCode(0x41));
			message = message.replace(/á/g, String.fromCharCode(0xC2) + String.fromCharCode(0x61));
			message = message.replace(/à/g, String.fromCharCode(0xC1) + String.fromCharCode(0x61));
			message = message.replace(/â/g, String.fromCharCode(0xC3) + String.fromCharCode(0x61));
			message = message.replace(/ä/g, String.fromCharCode(0xC8) + String.fromCharCode(0x61));
			message = message.replace(/ã/g, String.fromCharCode(0xC4) + String.fromCharCode(0x61));
			message = message.replace(/Ă/g, String.fromCharCode(0xC6) + String.fromCharCode(0x41));
			message = message.replace(/Ê/g, String.fromCharCode(0xC3) + String.fromCharCode(0x45));
			message = message.replace(/ă/g, String.fromCharCode(0xC6) + String.fromCharCode(0x61));
			message = message.replace(/Å/g, String.fromCharCode(0xCA) + String.fromCharCode(0x41));
			message = message.replace(/å/g, String.fromCharCode(0xCA) + String.fromCharCode(0x61));
			message = message.replace(/Ā/g, String.fromCharCode(0xC5) + String.fromCharCode(0x41));
			message = message.replace(/ā/g, String.fromCharCode(0xC5) + String.fromCharCode(0x61));
			message = message.replace(/Ą/g, String.fromCharCode(0xCE) + String.fromCharCode(0x41));
			message = message.replace(/ą/g, String.fromCharCode(0xCE) + String.fromCharCode(0x61));
			message = message.replace(/Ć/g, String.fromCharCode(0xC2) + String.fromCharCode(0x43));
			message = message.replace(/ć/g, String.fromCharCode(0xC2) + String.fromCharCode(0x63));
			message = message.replace(/Ĉ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x43));
			message = message.replace(/ĉ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x63));
			message = message.replace(/Ï/g, String.fromCharCode(0xC8) + String.fromCharCode(0x49));
			message = message.replace(/Č/g, String.fromCharCode(0xCF) + String.fromCharCode(0x43));
			message = message.replace(/č/g, String.fromCharCode(0xCF) + String.fromCharCode(0x63));
			message = message.replace(/Ċ/g, String.fromCharCode(0xC7) + String.fromCharCode(0x43));
			message = message.replace(/ċ/g, String.fromCharCode(0xC7) + String.fromCharCode(0x63));
			message = message.replace(/ç/g, String.fromCharCode(0xCB) + String.fromCharCode(0x63));
			message = message.replace(/Ď/g, String.fromCharCode(0xCF) + String.fromCharCode(0x44));
			message = message.replace(/ď/g, String.fromCharCode(0xCF) + String.fromCharCode(0x64));
			message = message.replace(/É/g, String.fromCharCode(0xC2) + String.fromCharCode(0x45));
			message = message.replace(/é/g, String.fromCharCode(0xC2) + String.fromCharCode(0x65));
			message = message.replace(/è/g, String.fromCharCode(0xC1) + String.fromCharCode(0x65));
			message = message.replace(/ê/g, String.fromCharCode(0xC3) + String.fromCharCode(0x65));
			message = message.replace(/ë/g, String.fromCharCode(0xC8) + String.fromCharCode(0x65));
			message = message.replace(/Ě/g, String.fromCharCode(0xCF) + String.fromCharCode(0x45));
			message = message.replace(/ě/g, String.fromCharCode(0xCF) + String.fromCharCode(0x65));
			message = message.replace(/Ė/g, String.fromCharCode(0xC7) + String.fromCharCode(0x45));
			message = message.replace(/ė/g, String.fromCharCode(0xC7) + String.fromCharCode(0x65));
			message = message.replace(/Ē/g, String.fromCharCode(0xC5) + String.fromCharCode(0x45));
			message = message.replace(/ē/g, String.fromCharCode(0xC5) + String.fromCharCode(0x65));
			message = message.replace(/Ę/g, String.fromCharCode(0xCE) + String.fromCharCode(0x45));
			message = message.replace(/ę/g, String.fromCharCode(0xCE) + String.fromCharCode(0x65));
			
			message = message.replace(/ģ/g, String.fromCharCode(0xC2) + String.fromCharCode(0x67));
			message = message.replace(/Ĝ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x47)); 
			message = message.replace(/ĝ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x67)); 
			message = message.replace(/Ğ/g, String.fromCharCode(0xC6) + String.fromCharCode(0x47)); 
			message = message.replace(/ğ/g, String.fromCharCode(0xC6) + String.fromCharCode(0x67)); 
			message = message.replace(/Ġ/g, String.fromCharCode(0xC7) + String.fromCharCode(0x47));
			message = message.replace(/ġ/g, String.fromCharCode(0xC7) + String.fromCharCode(0x67));
			message = message.replace(/Ģ/g, String.fromCharCode(0xCB) + String.fromCharCode(0x47));
			
			message = message.replace(/Ĥ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x48));
			message = message.replace(/ĥ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x68));
			message = message.replace(/Í/g, String.fromCharCode(0xC2) + String.fromCharCode(0x49));
			message = message.replace(/í/g, String.fromCharCode(0xC2) + String.fromCharCode(0x69));
			message = message.replace(/Ì/g, String.fromCharCode(0xC1) + String.fromCharCode(0x49));
			message = message.replace(/ì/g, String.fromCharCode(0xC1) + String.fromCharCode(0x69));
			message = message.replace(/î/g, String.fromCharCode(0xC3) + String.fromCharCode(0x69));
			message = message.replace(/ï/g, String.fromCharCode(0xC8) + String.fromCharCode(0x69));
			message = message.replace(/Ĩ/g, String.fromCharCode(0xC4) + String.fromCharCode(0x49));
			message = message.replace(/ĩ/g, String.fromCharCode(0xC4) + String.fromCharCode(0x69));
			message = message.replace(/İ/g, String.fromCharCode(0xC7) + String.fromCharCode(0x49));
		 	
			message = message.replace(/Ī/g, String.fromCharCode(0xC5) + String.fromCharCode(0x49));
			message = message.replace(/ī/g, String.fromCharCode(0xC5) + String.fromCharCode(0x69));
			message = message.replace(/Į/g, String.fromCharCode(0xCE) + String.fromCharCode(0x49));
			message = message.replace(/į/g, String.fromCharCode(0xCE) + String.fromCharCode(0x69));
			message = message.replace(/Ĵ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x4A));
			message = message.replace(/ĵ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x6A));
			message = message.replace(/Ķ/g, String.fromCharCode(0xCB) + String.fromCharCode(0x4B));
			message = message.replace(/ķ/g, String.fromCharCode(0xCB) + String.fromCharCode(0x6B));
			message = message.replace(/Ĺ/g, String.fromCharCode(0xC2) + String.fromCharCode(0x4C));
			message = message.replace(/ĺ/g, String.fromCharCode(0xC2) + String.fromCharCode(0x6C));
			message = message.replace(/Ľ/g, String.fromCharCode(0xCF) + String.fromCharCode(0x4C));
			message = message.replace(/ľ/g, String.fromCharCode(0xCF) + String.fromCharCode(0x6C));
			message = message.replace(/Ļ/g, String.fromCharCode(0xCB) + String.fromCharCode(0x4C));
			message = message.replace(/ļ/g, String.fromCharCode(0xCB) + String.fromCharCode(0x6C));
			message = message.replace(/Ń/g, String.fromCharCode(0xC2) + String.fromCharCode(0x4E));
			message = message.replace(/ń/g, String.fromCharCode(0xC2) + String.fromCharCode(0x6E));
			message = message.replace(/Ñ/g, String.fromCharCode(0xC4) + String.fromCharCode(0x4E));
			message = message.replace(/ñ/g, String.fromCharCode(0xC4) + String.fromCharCode(0x6E));
			message = message.replace(/Ň/g, String.fromCharCode(0xCF) + String.fromCharCode(0x4E));
			message = message.replace(/ň/g, String.fromCharCode(0xCF) + String.fromCharCode(0x6E));
			message = message.replace(/Ņ/g, String.fromCharCode(0xCB) + String.fromCharCode(0x4E));
			message = message.replace(/ņ/g, String.fromCharCode(0xCB) + String.fromCharCode(0x6E));
			message = message.replace(/Ó/g, String.fromCharCode(0xC2) + String.fromCharCode(0x4F));
			message = message.replace(/ó/g, String.fromCharCode(0xC2) + String.fromCharCode(0x6F));
			message = message.replace(/Ò/g, String.fromCharCode(0xC1) + String.fromCharCode(0x4F));
			message = message.replace(/ò/g, String.fromCharCode(0xC1) + String.fromCharCode(0x6F));
			message = message.replace(/Ô/g, String.fromCharCode(0xC3) + String.fromCharCode(0x4F));
			message = message.replace(/ô/g, String.fromCharCode(0xC3) + String.fromCharCode(0x6F));
			message = message.replace(/Ö/g, String.fromCharCode(0xC8) + String.fromCharCode(0x4F));
			message = message.replace(/ö/g, String.fromCharCode(0xC8) + String.fromCharCode(0x6F));
			message = message.replace(/Õ/g, String.fromCharCode(0xC4) + String.fromCharCode(0x4F));
			message = message.replace(/õ/g, String.fromCharCode(0xC4) + String.fromCharCode(0x6F));
			message = message.replace(/Ő/g, String.fromCharCode(0xCD) + String.fromCharCode(0x4F));
			message = message.replace(/ő/g, String.fromCharCode(0xCD) + String.fromCharCode(0x6F));
			message = message.replace(/Ō/g, String.fromCharCode(0xC5) + String.fromCharCode(0x4F));
			message = message.replace(/ō/g, String.fromCharCode(0xC5) + String.fromCharCode(0x6F));
			message = message.replace(/Ŕ/g, String.fromCharCode(0xC2) + String.fromCharCode(0x52));
			message = message.replace(/ŕ/g, String.fromCharCode(0xC2) + String.fromCharCode(0x72));
			message = message.replace(/Ř/g, String.fromCharCode(0xCF) + String.fromCharCode(0x52));
			message = message.replace(/ř/g, String.fromCharCode(0xCF) + String.fromCharCode(0x72));
			message = message.replace(/Ŗ/g, String.fromCharCode(0xCB) + String.fromCharCode(0x52));
			message = message.replace(/ŗ/g, String.fromCharCode(0xCB) + String.fromCharCode(0x72));
			message = message.replace(/Ś/g, String.fromCharCode(0xC2) + String.fromCharCode(0x53));
			message = message.replace(/ś/g, String.fromCharCode(0xC2) + String.fromCharCode(0x73));
			message = message.replace(/Ŝ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x53));
			message = message.replace(/ŝ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x73));
			message = message.replace(/Š/g, String.fromCharCode(0xCF) + String.fromCharCode(0x53));
			message = message.replace(/š/g, String.fromCharCode(0xCF) + String.fromCharCode(0x73));
			message = message.replace(/Ş/g, String.fromCharCode(0xCB) + String.fromCharCode(0x53));
			message = message.replace(/ş/g, String.fromCharCode(0xCB) + String.fromCharCode(0x73));
			message = message.replace(/Ť/g, String.fromCharCode(0xCF) + String.fromCharCode(0x54));
			message = message.replace(/ť/g, String.fromCharCode(0xCF) + String.fromCharCode(0x74));
			message = message.replace(/Ţ/g, String.fromCharCode(0xCB) + String.fromCharCode(0x54));
			message = message.replace(/ţ/g, String.fromCharCode(0xCB) + String.fromCharCode(0x74));
			message = message.replace(/Ú/g, String.fromCharCode(0xC2) + String.fromCharCode(0x55));
			message = message.replace(/ú/g, String.fromCharCode(0xC2) + String.fromCharCode(0x75));
			message = message.replace(/Ù/g, String.fromCharCode(0xC1) + String.fromCharCode(0x55));
			message = message.replace(/ù/g, String.fromCharCode(0xC1) + String.fromCharCode(0x75));
			message = message.replace(/Û/g, String.fromCharCode(0xC3) + String.fromCharCode(0x55));
			message = message.replace(/û/g, String.fromCharCode(0xC3) + String.fromCharCode(0x75));
			message = message.replace(/Ü/g, String.fromCharCode(0xC8) + String.fromCharCode(0x55));
			message = message.replace(/ü/g, String.fromCharCode(0xC8) + String.fromCharCode(0x75));
			message = message.replace(/Ũ/g, String.fromCharCode(0xC4) + String.fromCharCode(0x55));
			message = message.replace(/ũ/g, String.fromCharCode(0xC4) + String.fromCharCode(0x75));
			message = message.replace(/Ŭ/g, String.fromCharCode(0xC6) + String.fromCharCode(0x55));
			message = message.replace(/ŭ/g, String.fromCharCode(0xC6) + String.fromCharCode(0x75));
			message = message.replace(/Ű/g, String.fromCharCode(0xCD) + String.fromCharCode(0x55));
			message = message.replace(/ű/g, String.fromCharCode(0xCD) + String.fromCharCode(0x75));
			message = message.replace(/Ů/g, String.fromCharCode(0xCA) + String.fromCharCode(0x55));
			message = message.replace(/ů/g, String.fromCharCode(0xCA) + String.fromCharCode(0x75));
			message = message.replace(/Ū/g, String.fromCharCode(0xC5) + String.fromCharCode(0x55));
			message = message.replace(/ū/g, String.fromCharCode(0xC5) + String.fromCharCode(0x75));
			message = message.replace(/Ų/g, String.fromCharCode(0xCE) + String.fromCharCode(0x55));
			message = message.replace(/ų/g, String.fromCharCode(0xCE) + String.fromCharCode(0x75));
			message = message.replace(/Ŵ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x57));
			message = message.replace(/ŵ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x77));
			message = message.replace(/Ý/g, String.fromCharCode(0xC2) + String.fromCharCode(0x59));
			message = message.replace(/ý/g, String.fromCharCode(0xC2) + String.fromCharCode(0x79));
			message = message.replace(/Ŷ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x59));
			message = message.replace(/ŷ/g, String.fromCharCode(0xC3) + String.fromCharCode(0x79));
			message = message.replace(/Ÿ/g, String.fromCharCode(0xC8) + String.fromCharCode(0x59));
			message = message.replace(/ÿ/g, String.fromCharCode(0xC8) + String.fromCharCode(0x79));
			message = message.replace(/Ź/g, String.fromCharCode(0xC2) + String.fromCharCode(0x5A));
			message = message.replace(/ź/g, String.fromCharCode(0xC2) + String.fromCharCode(0x7A));
			message = message.replace(/Ž/g, String.fromCharCode(0xCF) + String.fromCharCode(0x5A));
			message = message.replace(/ž/g, String.fromCharCode(0xCF) + String.fromCharCode(0x7A));
			message = message.replace(/Ż/g, String.fromCharCode(0xC7) + String.fromCharCode(0x5A));
			message = message.replace(/ż/g, String.fromCharCode(0xC7) + String.fromCharCode(0x7A));

			message = message.replace(/Æ/g, String.fromCharCode(0xE1));
			message = message.replace(/æ/g, String.fromCharCode(0xF1));
			message = message.replace(/ð/g, String.fromCharCode(0xF3));
			message = message.replace(/÷/g, String.fromCharCode(0xB8));
			message = message.replace(/×/g, String.fromCharCode(0xB4));
			message = message.replace(/Ø/g, String.fromCharCode(0xE9));
			message = message.replace(/ø/g, String.fromCharCode(0xF9));
			message = message.replace(/þ/g, String.fromCharCode(0xFC));
			message = message.replace(/Þ/g, String.fromCharCode(0xEC));
			message = message.replace(/ß/g, String.fromCharCode(0xFB));
			message = message.replace(/Đ/g, String.fromCharCode(0xE2));
			message = message.replace(/đ/g, String.fromCharCode(0xF2));
			message = message.replace(/Ħ/g, String.fromCharCode(0xE4));
			message = message.replace(/Ŀ/g, String.fromCharCode(0xE7));
			message = message.replace(/ŀ/g, String.fromCharCode(0xF7));
 			message = message.replace(/Ł/g, String.fromCharCode(0xE8));
			message = message.replace(/ł/g, String.fromCharCode(0xF8));
			message = message.replace(/Ŋ/g, String.fromCharCode(0xEE));
			message = message.replace(/ŋ/g, String.fromCharCode(0xFE));
			message = message.replace(/Œ/g, String.fromCharCode(0xEA));
			message = message.replace(/œ/g, String.fromCharCode(0xFA));
			message = message.replace(/ŧ/g, String.fromCharCode(0xFD));
			message = message.replace(/Ω/g, String.fromCharCode(0xE0));
			message = message.replace(/Ŧ/g, String.fromCharCode(0xED));
			message = message.replace(/ĸ/g, String.fromCharCode(0xF0));
			message = message.replace(/ħ/g, String.fromCharCode(0xF4));
			message = message.replace(/ı/g, String.fromCharCode(0xF5));
			message = message.replace(/¡/g, String.fromCharCode(0xA1));
			message = message.replace(/¢/g, String.fromCharCode(0xA2));
			message = message.replace(/£/g, String.fromCharCode(0xA3));
			message = message.replace(/¥/g, String.fromCharCode(0xA5));
			message = message.replace(/#/g, String.fromCharCode(0xA6));
			message = message.replace(/§/g, String.fromCharCode(0xA7));
			message = message.replace(/°/g, String.fromCharCode(0xB0));
			message = message.replace(/±/g, String.fromCharCode(0xB1));
			message = message.replace(/μ/g, String.fromCharCode(0xB5));
			message = message.replace(/»/g, String.fromCharCode(0xBB));
			message = message.replace(/¿/g, String.fromCharCode(0xBF));

			return message;
		}
		
		function zeroFill(string, width) {
			if (!string) {
				string = "";
			}
			
			width -= string.toString().length;
			if (width > 0) {
				return new Array(width + 1).join('0') + string;
			}

			return string;
		}

		return {
			parse : parse,
			getHeaderLength : getHeaderLength,
			assembleHeader : assembleHeader,
			translateTeletex : translateTeletex,
			zeroFill : zeroFill
		}
	}]);