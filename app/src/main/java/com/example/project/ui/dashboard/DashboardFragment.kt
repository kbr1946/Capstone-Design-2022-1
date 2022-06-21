package com.example.project.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project.databinding.FragmentDashboardBinding

class DashboardFragment<ArrayBinding> : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        var idealTotalCal = 1900/3 // 계산된 적정 칼로리라고 가정
        // 거식증의 적정 칼로리 기준
        var idealTanCalMin = 237 * 4 / 3
        var idealTanCalMax = 261 * 4 / 3
        var idealDanCalMin = 71 * 4 / 3
        var idealDanCalMax = 95 * 4 / 3
        var idealGiCalMin = 53 * 9 / 3
        var idealGiCalMax = 63 * 9 / 3


        // 식품 정보(1g 당 탄, 단, 지 함량(g))
        var foodInfo = arrayOf(arrayOf("쌀밥", 0.332, 0.03, 0.001), // 음식이름, 탄, 단, 지
                                arrayOf("부대찌개", 0.066, 0.031, 0.031),
                                arrayOf("배추김치", 0.033, 0.018, 0.008),
                                arrayOf("미트볼", 0.129, 0.181, 0.107),
                                arrayOf("숙주나물", 0.03, 0.022, 0.016))

        binding.cal.setOnClickListener {
//            var heightIn = binding.heightInput.text.toString()
//            var weightIn = binding.weightInput.text.toString()
//            var ageIn = binding.ageInput.text.toString()
            //val gender_radioGroup

            //각 음식별 입력 무게
            var g = arrayOf(
                binding.editTextNumberDecimal1.text.toString(),
                binding.editTextNumberDecimal2.text.toString(),
                binding.editTextNumberDecimal3.text.toString(),
                binding.editTextNumberDecimal4.text.toString(),
                binding.editTextNumberDecimal5.text.toString()
            )

            //각 음식별 탄,단,지 칼로리 정보
            var foodInput = arrayOf(
                arrayOf(foodInfo[0][1].toString().toFloat() * g[0].toFloat(),
                    foodInfo[0][2].toString().toFloat() * g[0].toFloat(),
                    foodInfo[0][3].toString().toFloat() * g[0].toFloat()),
                arrayOf(foodInfo[1][1].toString().toFloat() * g[1].toFloat(),
                    foodInfo[1][2].toString().toFloat() * g[1].toFloat(),
                    foodInfo[1][3].toString().toFloat() * g[1].toFloat()),
                arrayOf(foodInfo[2][1].toString().toFloat() * g[2].toFloat(),
                    foodInfo[2][2].toString().toFloat() * g[2].toFloat(),
                    foodInfo[2][3].toString().toFloat() * g[2].toFloat()),
                arrayOf(foodInfo[3][1].toString().toFloat() * g[3].toFloat(),
                    foodInfo[3][2].toString().toFloat() * g[3].toFloat(),
                    foodInfo[3][3].toString().toFloat() * g[3].toFloat()),
                arrayOf(foodInfo[4][1].toString().toFloat() * g[4].toFloat(),
                    foodInfo[4][2].toString().toFloat() * g[4].toFloat(),
                    foodInfo[4][3].toString().toFloat() * g[4].toFloat())
            )

            //입력된 음식의 총 칼로리
            var totalCal = ((foodInput[0][0] + foodInput[1][0] + foodInput[2][0] + foodInput[3][0] + foodInput[4][0]) * 4
                            +(foodInput[0][1] + foodInput[1][1] + foodInput[2][1] + foodInput[3][1] + foodInput[4][1]) * 4
                            +(foodInput[0][2] + foodInput[1][2] + foodInput[2][2] + foodInput[3][2] + foodInput[4][2]) * 9)

            // 적정 칼로리로 일괄 비율 조절
            var fixCal = idealTotalCal / totalCal
            var i: Int
            var j: Int
            for (i in 0..4) {
                for (j in 0..2) {
                    foodInput[i][j] *= fixCal
                }
                g[i] = (g[i].toFloat() * fixCal).toString()
            }

            //탄, 단, 지 각각 총 열량
            var totalTanCal =
                (foodInput[0][0] + foodInput[1][0] + foodInput[2][0] + foodInput[3][0] + foodInput[4][0]) * 4
            var totalDanCal =
                (foodInput[0][1] + foodInput[1][1] + foodInput[2][1] + foodInput[3][1] + foodInput[4][1]) * 4
            var totalGiCal =
                (foodInput[0][2] + foodInput[1][2] + foodInput[2][2] + foodInput[3][2] + foodInput[4][2]) * 9
            var before_totalTanCal = totalTanCal
            var before_totalDanCal = totalDanCal
            var before_totalGiCal = totalGiCal


            var k: Int
            for(k in 0..20){
                //탄수화물 칼로리 조절
                if (idealTanCalMax < totalTanCal || totalTanCal < idealTanCalMin) {
                    var max = 0;
                    for (i in 1..4)
                        if (foodInput[max][0] < foodInput[i][0])
                            max = i
                    var fixTan =
                        ((idealTanCalMin + idealTanCalMax) / 2 - totalTanCal) / (foodInput[max][0] * 4) + 1
                    foodInput[max][0] *= fixTan
                    foodInput[max][1] *= fixTan
                    foodInput[max][2] *= fixTan
                    g[max] = (g[max].toFloat() * fixTan).toString()

                    //조절 후 각 열량 확인
                    totalTanCal =
                        (foodInput[0][0] + foodInput[1][0] + foodInput[2][0] + foodInput[3][0] + foodInput[4][0]) * 4
                    totalDanCal =
                        (foodInput[0][1] + foodInput[1][1] + foodInput[2][1] + foodInput[3][1] + foodInput[4][1]) * 4
                    totalGiCal =
                        (foodInput[0][2] + foodInput[1][2] + foodInput[2][2] + foodInput[3][2] + foodInput[4][2]) * 9
                }
                //단백질 칼로리 조절
                if (idealDanCalMax < totalDanCal || totalDanCal < idealDanCalMin) {
                    var max = 0;
                    for (i in 1..4)
                        if (foodInput[max][1] < foodInput[i][1])
                            max = i
                    var fixDan =
                        ((idealDanCalMin + idealDanCalMax) / 2 - totalDanCal) / (foodInput[max][1] * 4) + 1
                    foodInput[max][0] *= fixDan
                    foodInput[max][1] *= fixDan
                    foodInput[max][2] *= fixDan
                    g[max] = (g[max].toFloat() * fixDan).toString()

                    //조절 후 각 열량 확인
                    totalTanCal =
                        (foodInput[0][0] + foodInput[1][0] + foodInput[2][0] + foodInput[3][0] + foodInput[4][0]) * 4
                    totalDanCal =
                        (foodInput[0][1] + foodInput[1][1] + foodInput[2][1] + foodInput[3][1] + foodInput[4][1]) * 4
                    totalGiCal =
                        (foodInput[0][2] + foodInput[1][2] + foodInput[2][2] + foodInput[3][2] + foodInput[4][2]) * 9
                }
                //지방 칼로리 조절
                if (idealGiCalMax < totalGiCal || totalGiCal < idealGiCalMin) {
                    var max = 0;
                    for (i in 1..4)
                        if (foodInput[max][2] < foodInput[i][2])
                            max = i
                    var fixGi =
                        ((idealGiCalMin + idealGiCalMax) / 2 - totalGiCal) / (foodInput[max][2] * 9) + 1
                    foodInput[max][0] *= fixGi
                    foodInput[max][1] *= fixGi
                    foodInput[max][2] *= fixGi
                    g[max] = (g[max].toFloat() * fixGi).toString()

                    //조절 후 각 열량 확인
                    totalTanCal =
                        (foodInput[0][0] + foodInput[1][0] + foodInput[2][0] + foodInput[3][0] + foodInput[4][0]) * 4
                    totalDanCal =
                        (foodInput[0][1] + foodInput[1][1] + foodInput[2][1] + foodInput[3][1] + foodInput[4][1]) * 4
                    totalGiCal =
                        (foodInput[0][2] + foodInput[1][2] + foodInput[2][2] + foodInput[3][2] + foodInput[4][2]) * 9
                }
            }

            var fixTotalCal = totalTanCal + totalDanCal + totalGiCal
            //식단 분석, 교정 결과 출력
            binding.resultFood1.text = "<사용자 입력 정보>\n" +
                                        "총 열량: " + totalCal.toInt() + "kcal\n" +
                                        "탄수화물: " + before_totalTanCal.toInt() + "kcal\n" +
                                        "단백질: " + before_totalDanCal.toInt() + "kcal\n" +
                                        "지방: " + before_totalGiCal.toInt() + "kcal\n"

            binding.resultFood2.text = "<권장 섭취량>\n" +
                                        "권장 열량: " + idealTotalCal + "kcal\n" +
                                        "탄수화물: " + idealTanCalMin + " ~ " + idealTanCalMax +"kcal\n" +
                                        "단백질: " + idealDanCalMin + " ~ " + idealDanCalMax +"kcal\n" +
                                        "지방: " + idealGiCalMin + " ~ " + idealGiCalMax +"kcal\n"

            binding.resultFood3.text = "<식단 교정 안내>\n" +
                                        foodInfo[0][0] + ": " + g[0].toFloat().toInt() + "g\n" +
                                        foodInfo[1][0] + ": " + g[1].toFloat().toInt() + "g\n" +
                                        foodInfo[2][0] + ": " + g[2].toFloat().toInt() + "g\n" +
                                        foodInfo[3][0] + ": " + g[3].toFloat().toInt() + "g\n" +
                                        foodInfo[4][0] + ": " + g[4].toFloat().toInt() + "g\n"
        }

        return root

    }

//    sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
//
//        override fun onItemSelected(
//            adapterView: AdapterView<*>?,
//            view: View?,
//            pos: Int,
//            id: Long
//        ) {
//            textView2.text = "섭취할 음식 : "+sortSpinner.selectedItem
////            when (pos) {
////                0 -> viewModel.sortRuns(SortType.DATE)
////                1 -> viewModel.sortRuns(SortType.RUNNING_TIME)
////                2 -> viewModel.sortRuns(SortType.DISTANCE)
////                3 -> viewModel.sortRuns(SortType.AVG_SPEED)
////            }
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        cam.setOnClickListener(this)
    }

//    override fun onClick(v: View?) {
//        when(v?.id){
//            R.id.cam -> {}
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // val items = arrayOf("1 그릇","1/2 그릇","1/4 그릇","2그릇")
    // val items: Array<String> = resources.getStringArray(R.array.sort_options)



}
