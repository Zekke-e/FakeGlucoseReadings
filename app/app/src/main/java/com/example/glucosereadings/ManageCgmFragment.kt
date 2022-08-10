package com.example.glucosereadings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.glucosereadings.databinding.FragmentManageCgmBinding

class ManageCgmFragment : Fragment() {

    private var _binding: FragmentManageCgmBinding? = null
    private val binding: FragmentManageCgmBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageCgmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnManageCgmAddCgm.setOnClickListener {
            findNavController().navigate(ManageCgmFragmentDirections.actionManageCgmFragmentToAddCgmFragment())
        }
    }

}