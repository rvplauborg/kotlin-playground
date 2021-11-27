package dk.mailr.buildingblocks.mediator

import com.trendyol.kediatr.DependencyProvider

@Suppress("UNCHECKED_CAST")
class ManuelDependencyProvider(
    private val handlerMap: HashMap<Class<*>, Any>,
) : DependencyProvider {
    override fun <T> getSingleInstanceOf(clazz: Class<T>): T {
        return handlerMap[clazz] as T
    }

    override fun <T> getSubTypesOf(clazz: Class<T>): Collection<Class<T>> {
        return handlerMap
            .filter { it.key.interfaces.contains(clazz) }
            .map { it.key as Class<T> }
    }
}
