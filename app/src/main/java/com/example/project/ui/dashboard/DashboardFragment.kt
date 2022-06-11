package com.example.project.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project.R
import com.example.project.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
