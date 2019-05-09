package transferwise.test.technical.restaurants.injection.component

import dagger.Component
import transferwise.test.technical.restaurants.data.access.RestaurantsRepository
import transferwise.test.technical.restaurants.injection.module.ApiModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApiModule::class)])
interface ApiRepositoryInjector {
    fun inject(repository: RestaurantsRepository)
}