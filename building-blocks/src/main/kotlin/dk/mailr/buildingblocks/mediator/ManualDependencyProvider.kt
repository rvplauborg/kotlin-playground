package dk.mailr.buildingblocks.mediator

import com.trendyol.kediatr.DependencyProvider

@Suppress("UNCHECKED_CAST")
class ManualDependencyProvider(
    private val handlerMap: Map<Class<*>, Lazy<Any>>,
) : DependencyProvider {
    override fun <T> getSingleInstanceOf(clazz: Class<T>): T {
        return (handlerMap[clazz] as Lazy<T>).value
    }

    override fun <T> getSubTypesOf(clazz: Class<T>): Collection<Class<T>> {
        return handlerMap
            .filter { it.key.interfaces.contains(clazz) }
            .map { it.key as Class<T> }
    }
}
