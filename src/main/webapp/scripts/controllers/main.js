'use_strict';


angular.module('app', ['stripe'])
    .controller('MainCtrl', function($scope, $http){

        Stripe.setPublishableKey('pk_test_ERLhMS2uwqoka2PTFRImJonP');

        $scope.app = 'spring-rest-example';

        $scope.saveCustomer = function (status, response) {
            $http.post('payment', { token: response.id });
        };
    });