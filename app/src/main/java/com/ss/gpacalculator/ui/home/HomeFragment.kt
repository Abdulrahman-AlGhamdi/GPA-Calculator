package com.ss.gpacalculator.ui.home

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ss.gpacalculator.R
import com.ss.gpacalculator.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        init()
        navigateTo()

        return binding.root
    }

    private fun init() {
        val appearAnimation = loadAnimation(requireContext(), R.anim.appear_animation)
        val bottomAnimation = loadAnimation(requireContext(), R.anim.animation_from_button)
        binding.logo.animation = appearAnimation
        binding.totalCalculate.animation = bottomAnimation
        binding.semesterCalculate.animation = bottomAnimation

        val vectorDrawable = binding.logo.drawable as AnimatedVectorDrawable
        vectorDrawable.start()
        vectorDrawable.registerAnimationCallback(object : Animatable2.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                vectorDrawable.start()
            }
        })
    }

    private fun navigateTo() {
        binding.semesterCalculate.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSemesterCalculateFragment()
            findNavController().navigate(action)
        }

        binding.totalCalculate.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToTotalCalculateFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}