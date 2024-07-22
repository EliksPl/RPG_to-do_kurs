package vnt.dip.rpg_to_do_kurs.ui.hero

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import vnt.dip.rpg_to_do_kurs.MAIN
import vnt.dip.rpg_to_do_kurs.databinding.FragmentHeroBinding
import vnt.dip.rpg_to_do_kurs.models.hero.HeroModelUI

class HeroFragment : Fragment() {

    private var _binding: FragmentHeroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var heroViewModel : HeroViewModel
    private lateinit var hero : HeroModelUI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        heroViewModel = ViewModelProvider(this).get(HeroViewModel::class.java)
        _binding = FragmentHeroBinding.inflate(inflater, container, false)
        val root: View = binding.root
        init()

        return root
    }

    private fun init() {
        initDataAndInterface()
        initListeners()
    }

    private fun initDataAndInterface() {
        hero = heroViewModel.getCharacter(MAIN)
        Log.d("heroPage", " " + hero.exp + " " + hero.expNeeded)

        binding.fragmentHeroTvStatStrengthCounter.text = hero.strength.toString()
        binding.fragmentHeroTvStatIntelligenceCounter.text = hero.intelligence.toString()
        binding.fragmentHeroTvStatEnduranceCounter.text = hero.endurance.toString()
        binding.fragmentHeroTvStatAgilityCounter.text = hero.agility.toString()
        binding.fragmentHeroTvStatWisdomCounter.text = hero.wisdom.toString()

        binding.fragmentHeroPbExpLvlupBar.post(
            Runnable {
                binding.fragmentHeroPbExpLvlupBar.max = hero.expNeeded
                binding.fragmentHeroPbStrengthLvlupBar.max = 100
                binding.fragmentHeroPbIntelligenceLvlupBar.max = 100
                binding.fragmentHeroPbEnduranceLvlupBar.max = 100
                binding.fragmentHeroPbAgilityLvlupBar.max = 100
                binding.fragmentHeroPbWisdomLvlupBar.max = 100

                binding.fragmentHeroPbExpLvlupBar.progress = hero.exp
                binding.fragmentHeroPbStrengthLvlupBar.progress = hero.strengthExp
                binding.fragmentHeroPbIntelligenceLvlupBar.progress = hero.intelligenceExp
                binding.fragmentHeroPbEnduranceLvlupBar.progress = hero.enduranceExp
                binding.fragmentHeroPbAgilityLvlupBar.progress = hero.agilityExp
                binding.fragmentHeroPbWisdomLvlupBar.progress = hero.wisdomExp
            }
        )

        binding.fragmentHeroTvStatLevelCounter.text = hero.lvl.toString()

    }

//    private fun calculateLvlAndExpForHeroAndUpdateInterface(){
//        var expForLvlUp = 100
//        var currentExp = hero.exp
//        var currentLvl = 1
//        while (currentExp>expForLvlUp){
//            currentExp -= expForLvlUp
//            ++currentLvl
//            expForLvlUp = (expForLvlUp*1.1).toInt()
//        }
//
//        binding.fragmentHeroTvStatLevelCounter.text = currentLvl.toString()
//        binding.fragmentHeroPbExpLvlupBar.max = expForLvlUp
//        binding.fragmentHeroPbExpLvlupBar.progress = currentExp
//
//    }

    private fun initListeners() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}