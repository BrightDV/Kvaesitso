package de.mm20.launcher2.files

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val filesModule = module {
    single<FileRepository> { FileRepositoryImpl(androidContext(), get(), get(), get()) }
}