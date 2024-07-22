package vnt.dip.rpg_to_do_kurs.controllers

import android.os.Bundle
import android.view.View
import androidx.collection.arraySetOf
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.google.android.material.bottomnavigation.BottomNavigationView
import vnt.dip.rpg_to_do_kurs.R

class NavVisibilityController(
    private val bottomNavigationView: BottomNavigationView
) : NavController.OnDestinationChangedListener {

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        // Список ID фрагментів, для яких Bottom Navigation View буде видимим.
        val visibleBottomNavDestinations = arraySetOf(R.id.navigation_hero, R.id.navigation_tasks, R.id.navigation_admin)
//        val visibleActionBarDestinations = arraySetOf(R.id.tasksFragment, R.id.notesFragment, R.id.nav_bar_item_settings)

        if (destination.id in visibleBottomNavDestinations) {
            bottomNavigationView.visibility = View.VISIBLE
        } else {
            bottomNavigationView.visibility = View.GONE
        }

//        if (destination.id in visibleActionBarDestinations) {
//            MAIN.supportActionBar?.show()
//        } else {
//            MAIN.supportActionBar?.hide()
//        }


    }
}