package transferwise.test.technical.restaurants.data.access


import transferwise.test.technical.restaurants.injection.component.ApiRepositoryInjector
import transferwise.test.technical.restaurants.injection.component.DaggerApiRepositoryInjector
import transferwise.test.technical.restaurants.injection.module.ApiModule

abstract class ApiInjector {

    constructor(context: ApiModule.Context) {
        makeInjetion(DaggerApiRepositoryInjector.builder().apiModule(context.getModule()).build())
    }

    private fun makeInjetion(injector: ApiRepositoryInjector) {
        when(this) {
            is RestaurantsRepository -> injector.inject(this)
        }
    }

}