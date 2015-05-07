'use_strict';


angular.module('app', ['stripe'])
    .config( function() {
        Stripe.setPublishableKey('sk_test_d1o87gtp2OGsGXbBA9vMGnFW');
    });
