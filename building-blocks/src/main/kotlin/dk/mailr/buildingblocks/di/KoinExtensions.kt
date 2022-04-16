package dk.mailr.buildingblocks.di

import org.koin.core.scope.Scope

/**
 * [T] must be injectable for the extension to work as intended.
 */
inline fun <reified T : Any> Scope.classToLazyProvider() = T::class.java to inject<T>()
