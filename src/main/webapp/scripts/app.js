'use_strict';


angular.module('app', ['stripe'])
    .config( function() {
        Stripe.setPublishableKey('pk_test_ERLhMS2uwqoka2PTFRImJonP');
    });
