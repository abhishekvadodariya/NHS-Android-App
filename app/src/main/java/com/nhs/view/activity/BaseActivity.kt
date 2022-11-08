package com.nhs.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Fade
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nhs.IBackPressSupport
import com.nhs.R
import kotlinx.android.synthetic.main.a_base.*
import timber.log.Timber
import java.lang.System.exit
import java.util.HashMap

abstract class BaseActivity : AppCompatActivity() {
    companion object {
        var currentFragment: Fragment? = null
    }

    var disableExitAnimation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_base)
        var layoutId = getLayoutId()
        if (layoutId != -1) {
            a_base_viewstub_content.layoutResource = layoutId
            a_base_viewstub_content.inflate()
            setupUI(savedInstanceState)
        } else {
            throw IllegalStateException("Activity Layout id must be provided")
        }
    }

    fun showFragment(showConfigBuilder: ShowConfigBuilder) {
        val fragmentManager = supportFragmentManager
        if (showConfigBuilder.fragment != null) {
            val fragment = showConfigBuilder.fragment
            val tag = fragment!!.javaClass.canonicalName//fragment.getClass().getName()
            if (currentFragment == null || !currentFragment!!.javaClass.canonicalName.equals(tag)) {
                if (showConfigBuilder.removeUptoId != "") {
                    clearBackStackUpto(showConfigBuilder.removeUptoId)
                }
                if (showConfigBuilder.removeInclusiveId != "") {
                    clearBackStackInclusive(showConfigBuilder.removeInclusiveId)
                }
                var transaction = fragmentManager.beginTransaction()

                /* Handle Animation */
                if (showConfigBuilder.animate) {
                    if (showConfigBuilder.sharedElements.size > 0 && android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        /* Slide slideTransition = new Slide(Gravity.RIGHT);
                        slideTransition.setDuration(300);*/

                        val changeBoundsTransition = ChangeBounds()
                        changeBoundsTransition.duration = 300

                        val fade = Fade()
                        fade.duration = 300

                        fragment.setEnterTransition(fade)
                        fragment.setExitTransition(fade)

                        fragment.setAllowEnterTransitionOverlap(true)
                        fragment.setAllowReturnTransitionOverlap(true)
                        fragment.setSharedElementEnterTransition(changeBoundsTransition)

                        val sharedElementIterator = showConfigBuilder.sharedElements.entries.iterator()
                        while (sharedElementIterator.hasNext()) {
                            val entry = sharedElementIterator.next()
                            Timber.d("Element Name :" + entry.key)
                            ViewCompat.setTransitionName(entry.value, entry.key)
                            transaction.addSharedElement(entry.value, entry.key)
                        }
                    } else {
                        //transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        transaction.setCustomAnimations(
                            R.anim.enter_from_right,
                            R.anim.exit_to_left,
                            R.anim.enter_from_left,
                            R.anim.exit_to_right
                        )
                    }
                } else {
                    transaction.setCustomAnimations(0, 0)
                }

                if (showConfigBuilder.transactionType == ShowConfigBuilder.TransactionType.FRAGMENT_REPLACE) {
                    transaction = transaction.replace(getContainerLayoutId(), fragment, tag).addToBackStack(tag)
                } else {
                    transaction = transaction
                        .hide(currentFragment!!)
                        .show(fragment)
                        .add(getContainerLayoutId(), fragment, tag).addToBackStack(tag)
                }


                if (showConfigBuilder.requestCode != -1) {
                    fragment.setTargetFragment(currentFragment, showConfigBuilder.requestCode)
                }

                if (showConfigBuilder.allowStateLessCommit) {
                    transaction.commitAllowingStateLoss()
                } else {
                    transaction.commit()
                }
            }
        }
    }

    override fun onBackPressed() {
        val backStackEntryCount = supportFragmentManager.backStackEntryCount

        if (currentFragment is IBackPressSupport) {
            (currentFragment as IBackPressSupport).onBackPressed()
        } else {
            if (backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
            } else {
                disableExitAnimation = true
                compatFinish()
                disableExitAnimation = false
                System.exit(0)
            }
        }
    }

    fun clearBackStack() {
        disableExitAnimation = true
        try {
            supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } catch (e: Exception) {
            compatFinish()
            System.exit(0)
        }

        disableExitAnimation = false
    }

    fun noAnimPopBackStack() {
        disableExitAnimation = true
        supportFragmentManager.popBackStackImmediate()
        disableExitAnimation = false
    }

    fun clearBackStackUpto(removeFragmentUptoFragmentId: String) {
        disableExitAnimation = true
        supportFragmentManager.popBackStackImmediate(removeFragmentUptoFragmentId, 0)
        disableExitAnimation = false
    }

    fun clearBackStackInclusive(removeFragmentUptoFragmentId: String) {
        disableExitAnimation = true
        supportFragmentManager.popBackStackImmediate(
            removeFragmentUptoFragmentId,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        disableExitAnimation = false
    }

    fun compatFinish() {
        if (Build.VERSION.SDK_INT >= 21) {
            finishAndRemoveTask()
        } else {
            finish()
        }
    }

    class ShowConfigBuilder {

        var removeUptoId = ""
        var removeInclusiveId = ""
        var fragment: Fragment? = null
        var animate = true
        var reverseAnimate = false
        var allowStateLessCommit = false
        var sharedElements: MutableMap<String, View> = HashMap()
        var transactionType = TransactionType.FRAGMENT_REPLACE
        var requestCode = -1

        enum class TransactionType {
            FRAGMENT_ADD,
            FRAGMENT_REPLACE
        }

        fun fragment(fragment: Fragment): ShowConfigBuilder {
            this.fragment = fragment
            return this
        }

        fun addSharedElements(key: String, view: View): ShowConfigBuilder {
            sharedElements[key] = view
            return this
        }

        fun reverseAnimate(reverseAnimate: Boolean): ShowConfigBuilder {
            this.reverseAnimate = reverseAnimate
            return this
        }

        fun removeUptoId(removeUptoId: String): ShowConfigBuilder {
            this.removeUptoId = removeUptoId
            return this
        }

        fun removeInclusiveId(removeInclusiveId: String): ShowConfigBuilder {
            this.removeInclusiveId = removeInclusiveId
            return this
        }

        fun animate(animate: Boolean): ShowConfigBuilder {
            this.animate = animate
            return this
        }

        fun method(transactionType: TransactionType): ShowConfigBuilder {
            this.transactionType = transactionType
            return this
        }


        fun setTargetFragment(requestCode: Int): ShowConfigBuilder {
            this.requestCode = requestCode
            return this
        }


        fun allowStateLessCommit(allowStateLessCommit: Boolean): ShowConfigBuilder {
            this.allowStateLessCommit = allowStateLessCommit
            return this
        }
    }

    fun showProgress() {
    }

    fun hideProgress() {
    }

    abstract fun getLayoutId(): Int
    abstract fun setupUI(savedInstanceState: Bundle?)
    abstract fun getContainerLayoutId(): Int
}