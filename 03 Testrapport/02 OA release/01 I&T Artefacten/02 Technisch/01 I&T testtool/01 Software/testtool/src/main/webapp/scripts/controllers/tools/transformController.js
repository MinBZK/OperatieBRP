myApp.controller(
	'transformController', [
		'$scope', '$filter', function($scope, $filter) {			
			$scope.strip = function() {
				var xml = $scope.xml;
				xml = xml.replace ("\n", '');
				
				var lines = xml.split('\n');
				var end = false;
				var begin = true;
				
				for(var i = 0; i < lines.length; i++) {
					if ((begin && (lines[i].indexOf('<?xml') == -1)) || end) {
						xml = xml.replace (lines[i] + "", '');
					} else if (lines[i].indexOf('<?xml') != -1) {
						begin = false;
					} else if (lines[i].indexOf('<\/soap:Envelope>') != -1) {
						end = true;
					}
				}

				//ber.ber message
				xml = xml.replace(/.*<\?xml/, '<?xml');
				
				//brp namespace
				xml = xml.replace(/brp:/g, '');
				xml = xml.replace(/<\/soap:Envelope>.*/, '<\/soap:Envelope>');
				
				$scope.xml = $filter('prettyXml')(xml);
			}		
}]);
