package com.example.coronatrackersamstag2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.coronatrackersamstag2.Model.CoronaReportAppModell
import com.example.coronatrackersamstag2.databinding.FragmentWelcomeBinding
import java.time.LocalDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {

    val coronaReportAppModell : CoronaReportAppModell by activityViewModels()
    val now : LocalDateTime = LocalDateTime.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding : FragmentWelcomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome, container, false
        )

        binding.btNewReport.setOnClickListener { view ->
            view?.findNavController()?.navigate(R.id.action_welcomeFragment2_to_newReport)
        }

        binding.btReportList.setOnClickListener { view ->
            view?.findNavController()?.navigate(R.id.action_welcomeFragment2_to_reportList)
        }

        var anzLeonding = 0
        var anzLinz = 0
        var anzHaid = 0

        var anzPos = 0
        var anzNeg = 0

        coronaReportAppModell.reportlist.value?.forEach {
            if(it.isPositive){
                anzPos++
            }else {
                anzNeg++
            }

            when(it.office) {
                "Linz-Stadtplatz" -> anzLinz++
                "Leonding-Meixnerkreuzung" -> anzLeonding++
                "Parkplatz-Haidcenter" -> anzHaid++
            }
        }

        binding.tvAnzLeonding.text = anzLeonding.toString()
        binding.tvAnzLinz.text = anzLinz.toString()
        binding.tvAnzParkplatzHaid.text = anzHaid.toString()

        binding.pbPositivNegativ.max = anzPos + anzNeg
        binding.pbPositivNegativ.min = 0
        binding.pbPositivNegativ.progress = anzPos


        return binding.root

    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WelcomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}