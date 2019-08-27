package uk.co.compendiumdev.examples.environmentabstraction;


public class TodoMVCEnvironment {

    public String getURL(){
        return "http://todomvc.com/examples/backbone/";
        //return "http://todomvc.com/examples/vanillajs/";
        //return "http://todomvc.com/examples/angularjs/#/";

        // local on mac via virtualHostX
        //return "http://todomvc.site/architecture-examples/backbone/";
        // local on pc
        //return "file:///C:/Users/Alan/Downloads/todomvc-f57e0b773db14f094ef09274af90042f83328412/todomvc-f57e0b773db14f094ef09274af90042f83328412/architecture-examples/backbone/index.html";
    }


    // TODO:
    //       Exercise:
    //                  Change the URL and run the test Environment Abstraction Test
    //                  to double check it does change

    // TODO:
    //       Exercise:
    //                  Make the URL configurable from a System property "TODOMVCENV"
    //                  with a default value of backbone if the property does not exist

    // TODO:
    //       Exercise:
    //                  Make the URL configurable from an Environment property "TODOMVCENV"
    //                  with a default value of backbone if the property does not exist

    // TODO:
    //       Exercise:
    //                  Allow both System Property and Environment property but have the
    //                  System property take precedence if both exist.

}
