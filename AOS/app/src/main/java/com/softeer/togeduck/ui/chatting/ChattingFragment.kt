package com.softeer.togeduck.ui.chatting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentChattingBinding
import com.softeer.togeduck.ui.reserve_status.ReservationStatusAdapter
import com.softeer.togeduck.utils.ItemClick

class ChattingFragment : Fragment() {
    private lateinit var binding: FragmentChattingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChattingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.apply {

        }
    }
}