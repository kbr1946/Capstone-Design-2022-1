package com.example.project.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project.R
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

        binding.analyse.setOnClickListener{
            var heightIn = binding.heightInput.text.toString()
            var weightIn = binding.weightInput.text.toString()
            var ageIn = binding.ageInput.text.toString()

        //성별 설정
            //var genderIn = "male"
            var genderIn = "female"

        // 질환명 설정
            //var diseaseIn = "비만"
            var diseaseIn = "거식증"
            //var diseaseIn = "폭식증"

        //질환 단계 설정
        // 비만
            //var diseaseLevelIn = "체중감소시기"
            //var diseaseLevelIn = "체중유지시기"
        //거식증
            //var diseaseLevelIn = "초기"
            var diseaseLevelIn = "체중증가시기"
            //var diseaseLevelIn = "체중유지시기"
        //폭식증
            //var diseaseLevelIn = "대사율정상"
            //var diseaseLevelIn = "대사율낮음"

        //사용자 정보를 바탕으로 에너지 요구량 분석(저활동적이라고 가정)
            var eat = 0.0 // 에너지 요구량
            if(genderIn == "male")
                eat = 88.5 - 69.1 * ageIn.toFloat() + 1.13 * (26.7 * weightIn.toFloat() + 903 * heightIn.toFloat()/100)
            else if(genderIn == "female")
                eat = 135.3 - 30.8 * ageIn.toFloat() + 1.16 * (10 * weightIn.toFloat() + 934 * heightIn.toFloat()/100)

        //생애주기별 부가량
            if(ageIn.toInt() in 3..8)
                eat += 20
            else if(ageIn.toInt() in 9..18)
                eat += 25
            else
                eat = -1.0

        //질환별 섭취량 분석
            var tanMin = 0
            var tanMax = 0
            var danMin = 0
            var danMax = 0
            var giMin = 0
            var giMax = 0

            if(diseaseIn == "비만"){
                if(diseaseLevelIn == "체중감소시기")
                    eat -= 500
                tanMin = (eat*0.50/4).roundToInt()
                tanMax = (eat*0.60/4).roundToInt()
                danMin = (eat*0.15/4).roundToInt()
                danMax = (eat*0.25/4).roundToInt()
                giMin = (eat*0.15/9).roundToInt()
                giMax = (eat*0.25/9).roundToInt()
            }
            else if(diseaseIn == "거식증"){
                if(diseaseLevelIn == "초기")
                    eat = 35 * weightIn.toDouble() // 35kacl/kg/day
                else if(diseaseLevelIn == "체중증가시기")
                    eat = eat
                    //eat += 100 // 1일당 100kcal씩 증가

                else if(diseaseLevelIn == "체중유지시기")
                    eat += 70 // 1일당 70kcal씩 증가 (총 여성 3000~4000kcal, 남성 4000~4500kcal가 되도록)
                tanMin = (eat*0.50/4).roundToInt()
                tanMax = (eat*0.55/4).roundToInt()
                danMin = (eat*0.15/4).roundToInt()
                danMax = (eat*0.20/4).roundToInt()
                giMin = (eat*0.25/9).roundToInt()
                giMax = (eat*0.30/9).roundToInt()
            }
            else if(diseaseIn == "폭식증"){
                if(diseaseLevelIn == "대사율낮음") {
                    eat = 1500.0
                    eat += 150 // 1일당 150kcal씩 증가
                }
            }


            binding.result.text = "<섭취량 분석 결과>\n" +
                                    "1일 권장 섭취량 : " + eat.roundToInt() + "Kcal\n" +
                                    "탄수화물: " + tanMin + "~" + tanMax + "g\n" +
                                    "단백질: " + danMin + "~" + danMax + "g\n" +
                                    "지방: " + giMin + "~" + giMax + "g\n"
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