package com.example.project.ui.home

import android.R
import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Half.toFloat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.FragmentHomeBinding
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


//        binding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
//            val value = when (checkedId) {
//                binding.GenderM.text -> Log.d("hello")
//            }
//        }

        val disease = arrayOf("비만", "거식증", "폭식증")
        //val spinner: Spinner = binding.GenderM.findViewById(binding.disease)
        
        binding.analyse.setOnClickListener{
            var heightIn = binding.heightInput.text.toString()
            var weightIn = binding.weightInput.text.toString()
            var ageIn = binding.ageInput.text.toString()
            //val gender_radioGroup

            var eat = 135.3-30.8* ageIn.toFloat() + 1.16 * (10 * weightIn.toFloat() + 934 * heightIn.toFloat()/100)
            binding.result.text = "<섭취량 분석 결과>\n권장 섭취량 : " + eat.roundToInt() + "Kcal\n탄수화물: " + (eat*0.5/4).roundToInt() + "~" + (eat*0.55/4).roundToInt() +
                                    "g\n단백질: " + (eat*0.15/4).roundToInt() + "~" + (eat*0.2/4).roundToInt() + "g\n지방: " + (eat*0.3/9).roundToInt() + "g이내"
        }


/*
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

 */

        return root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}