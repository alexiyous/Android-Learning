package com.dicoding.tourismapp.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import com.dicoding.tourismapp.detail.DetailTourismViewModel
import com.dicoding.tourismapp.di.AppScope
import com.dicoding.tourismapp.favorite.FavoriteViewModel
import com.dicoding.tourismapp.home.HomeViewModel
import javax.inject.Inject

// Alasan kenapa hanya ViewModelFactory yang diberi anotasi @AppScope adalah karena ViewModelFactory
// Itu singleton dan supaya  memenuhi aturan Dagger berikut:

// Hal lain yang perlu diperhatikan adalah scope. Ada dua aturan yang harus dipenuhi yaitu :
//
//Component tanpa scope tidak boleh bergantung (depend) pada component yang memiliki scope.
//
//Component dengan scope tidak boleh bergantung (depend) pada component dengan scope yang sama.
//
//Intinya untuk hubungan antara dua Component harus memiliki Scope yang berbeda. Apabila kedua component memiliki scope yang sama,
// misalnya sama-sama @Singleton, maka akan muncul eror This @Singleton component cannot depend on scoped components.

//Intinya, ini cara lain untuk mengatakan bahwa class ViewModelFactory ini adalah singleton tapi berbeda scope dengan CoreComponent (liat aturan scop di atas).
// Hal ini karena AppComponent memiliki dependency pada CoreComponent
@AppScope
class ViewModelFactory @Inject constructor(private val tourismUseCase: TourismUseCase) :
    ViewModelProvider.NewInstanceFactory() {

//    companion object {
//        @Volatile
//        private var instance: ViewModelFactory? = null
//
//        fun getInstance(context: Context): ViewModelFactory =
//            instance ?: synchronized(this) {
//                instance ?: ViewModelFactory(
//                    Injection.provideTourismUseCase(context)
//                )
//            }
//    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(tourismUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(tourismUseCase) as T
            }
            modelClass.isAssignableFrom(DetailTourismViewModel::class.java) -> {
                DetailTourismViewModel(tourismUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}