package vnt.dip.rpg_to_do_kurs.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vnt.dip.rpg_to_do_kurs.ADMIN_MODE
import vnt.dip.rpg_to_do_kurs.MAIN
import vnt.dip.rpg_to_do_kurs.R
import vnt.dip.rpg_to_do_kurs.controllers.PasswordManager
import vnt.dip.rpg_to_do_kurs.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {

    private var _binding: FragmentAdminBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel : AdminViewModel
    val passwordManager = PasswordManager(MAIN)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        _binding = FragmentAdminBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initDataAndInterface()
        initListeners()
    }

    private fun initListeners() {
        binding.fragmentAdminBtnSetResetPassword.setOnClickListener(){
            lifecycleScope.launch(Dispatchers.Main) {

                withContext(Dispatchers.Default) {
                    if (passwordManager.isPasswordSaved()) {
                        if (passwordManager.isPasswordCorrect(binding.fragmentAdminEtPassword.text.toString())) {
                            passwordManager.deletePassword()
                        } else {
                            binding.fragmentAdminEtPassword.setText("")
                            binding.fragmentAdminEtPassword.hint = "Incorrect password!"
                        }
                    } else {
                        passwordManager.savePassword(binding.fragmentAdminEtPassword.text.toString())
                    }
                }

                initDataAndInterface()
            }
        }

        binding.fragmentAdminBtnToggleAdmin.setOnClickListener(){
            lifecycleScope.launch(Dispatchers.Main) {

                withContext(Dispatchers.Default) {
                    if (!ADMIN_MODE) {
                        if (passwordManager.isPasswordCorrect(binding.fragmentAdminEtPassword.text.toString())) {
                            ADMIN_MODE = true
                        } else {
                            binding.fragmentAdminEtPassword.setText("")
                            binding.fragmentAdminEtPassword.hint = "Incorrect password!"
                        }

                    } else {
                        ADMIN_MODE = false
                    }
                }

                initDataAndInterface()
            }
        }

        binding.adminFragmentButtonTakeCurrency.setOnClickListener{
            viewModel.withdrawCurrency(
                MAIN,
                binding.fragmentAdminEtCurrency.text.toString()
            )

            initDataAndInterface()
        }
    }

    private fun initDataAndInterface() {
        if(passwordManager.isPasswordSaved()){
            binding.fragmentAdminBtnSetResetPassword.setText(R.string.admin_password_reset)
            binding.fragmentAdminBtnToggleAdmin.visibility = View.VISIBLE
        }else{
            binding.fragmentAdminBtnSetResetPassword.setText(R.string.admin_password_set)
            binding.fragmentAdminBtnToggleAdmin.visibility = View.GONE
        }

        if (ADMIN_MODE){
            binding.fragmentAdminBtnToggleAdmin.setText(R.string.admin_btn_toggle_turned_on)
        }else{
            binding.fragmentAdminBtnToggleAdmin.setText(R.string.admin_btn_toggle_turned_off)
        }

        binding.fragmentAdminTvCurrency.setText(viewModel.getCurrencyAmount(requireContext()))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}