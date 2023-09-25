package com.example.colanportfolio.ui.homescreen

import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.base.BaseActivity
import com.example.colanportfolio.data.local.SharedPreferenceImp
import com.example.colanportfolio.databinding.ActivityHomeBinding
import com.example.colanportfolio.ui.contact.ContactFragment
import com.example.colanportfolio.ui.domain.DomainFragment
import com.example.colanportfolio.ui.findproject.FindprojectFragment
import com.example.colanportfolio.ui.technology.TechnologyFragment
import com.example.colanportfolio.ui.testimonials.TestimonialFragment

class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>() {



    private val homeVM : HomeViewModel by viewModels()
    var sharedPreferences : SharedPreferenceImp?= null

    override val bindingVariable: Int
        get() = BR.homeVM
    override val layoutId: Int
        get() = R.layout.activity_home
    override val viewModel: HomeViewModel
        get() {
            return homeVM
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = SharedPreferenceImp()

        val domainFragment = DomainFragment()
        val technologyFragment = TechnologyFragment()
        val findProjectFragment = FindprojectFragment()
        val contactUsFragment = ContactFragment()
        val testimonialFragment = TestimonialFragment()
        showFragment(domainFragment)


            viewDataBinding?.bottomNavigationView?.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.port->showFragment(domainFragment)
                R.id.about_us->showFragment(technologyFragment)
                R.id.test->showFragment(findProjectFragment)
                R.id.tesimonial -> showFragment(testimonialFragment)
                R.id.contact ->showFragment(contactUsFragment)
            }
            true
        }

    }

    override fun onResume() {
        viewDataBinding?.bottomNavigationView?.selectedItemId = R.id.port
        super.onResume()
    }



    fun dialogExit(message: String?) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Colan Portfolio")
        alertDialog.setIcon(resources.getDrawable(R.drawable.colan))
        alertDialog.setMessage(message)
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"NO") { display, which -> alertDialog.dismiss()}
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES") { dialog, which -> finish()  }
        alertDialog.show()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
            // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //change your desigen by java code
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //change your desigen by java code
        }
    }


}