package dk.mailr.buildingblocks.di

import org.koin.core.scope.Scope

inline fun <reified T : Any> Scope.classToLazyProvider() = T::class.java to inject<T>()
