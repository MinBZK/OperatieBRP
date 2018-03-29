myApp.controller(
	'toXMLController', [
		'$scope', '$filter', '$http', function($scope, $filter, $http) {			
			$scope.list = function() {
				$http.get(URL_CONTEXT_PATH + "/to_xml_list")
					.success(function(data) {
						$scope.content=data;
					})
					.error(function(data, status, headers, config) {
						// TODO
					});
			}
			
			$scope.select = function() {
				$http.get(URL_CONTEXT_PATH + "/to_xml?dir=" + arguments[0])
					.success(function(data) {
						$scope.xml = $filter('prettyXml')(data[0]);
					})
					.error(function(data, status, headers, config) {
						// TODO
					});
			}
		}
]);
