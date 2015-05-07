'use_strict';


angular.module('app', ['stripe'])
.controller('MainCtrl', function($scope, $http){

    Stripe.setPublishableKey('pk_test_ERLhMS2uwqoka2PTFRImJonP');

    $scope.app = 'spring-rest-example';

    $scope.stripeMessage = "";

    $scope.saveCustomer = function (status, response) {
        $http.post('payment', { token: response.id })
        .success(function(data, status, headers, config) {
        // this callback will be called asynchronously
        // when the response is available

            $scope.stripeMessage = "Status: " + status + " Data: " + data.token + " Message: " + data.message;
        })
        . error(function(data, status, headers, config) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        
            $scope.stripeMessage = "Status: " + status + " Data: " + data.token + " Message: " + data.message;
        });
    };


});